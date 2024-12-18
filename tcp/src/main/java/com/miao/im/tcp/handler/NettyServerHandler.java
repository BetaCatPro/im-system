package com.miao.im.tcp.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miao.im.codec.pack.LoginPack;
import com.miao.im.codec.pack.message.ChatMessageAck;
import com.miao.im.codec.pack.user.LoginAckPack;
import com.miao.im.codec.pack.user.UserStatusChangeNotifyPack;
import com.miao.im.codec.proto.Message;
import com.miao.im.codec.proto.MessagePack;
import com.miao.im.common.ResponseVO;
import com.miao.im.common.constant.Constants;
import com.miao.im.common.enums.ImConnectStatusEnum;
import com.miao.im.common.enums.command.GroupEventCommand;
import com.miao.im.common.enums.command.MessageCommand;
import com.miao.im.common.enums.command.SystemCommand;
import com.miao.im.common.enums.command.UserEventCommand;
import com.miao.im.common.model.UserClientDto;
import com.miao.im.common.model.UserSession;
import com.miao.im.common.model.message.CheckSendMessageReq;
import com.miao.im.tcp.feign.FeignMessageService;
import com.miao.im.tcp.publish.MqMessageProducer;
import com.miao.im.tcp.redis.RedisManager;
import com.miao.im.tcp.utils.SessionSocketHolder;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;


public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private final static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private Integer brokerId;

    private String logicUrl;

    private FeignMessageService feignMessageService;

    public NettyServerHandler(Integer brokerId, String logicUrl) {
        this.brokerId = brokerId;
        feignMessageService = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))//设置超时时间
                .target(FeignMessageService.class, logicUrl);
    }

    //String
    //Map
    // userId client1 session
    // userId client2 session
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        Integer command = msg.getMessageHeader().getCommand();
        //登录command
        if (command == SystemCommand.LOGIN.getCommand()) {

            LoginPack loginPack = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()),
                    new TypeReference<LoginPack>() {
                    }.getType());
            /** 登陸事件 **/
            String userId = loginPack.getUserId();
            /** 为channel设置用户id **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.UserId)).set(userId);
            String clientImei = msg.getMessageHeader().getClientType() + ":" + msg.getMessageHeader().getImei();
            /** 为channel设置client和imel **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.ClientImei)).set(clientImei);
            /** 为channel设置appId **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.AppId)).set(msg.getMessageHeader().getAppId());
            /** 为channel设置ClientType **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.ClientType))
                    .set(msg.getMessageHeader().getClientType());
            /** 为channel设置Imei **/
            ctx.channel().attr(AttributeKey.valueOf(Constants.Imei))
                    .set(msg.getMessageHeader().getImei());
            //将channel存起来

            //Redis map

            UserSession userSession = new UserSession();
            userSession.setAppId(msg.getMessageHeader().getAppId());
            userSession.setClientType(msg.getMessageHeader().getClientType());
            userSession.setUserId(loginPack.getUserId());
            userSession.setConnectState(ImConnectStatusEnum.ONLINE_STATUS.getCode());
            userSession.setBrokerId(brokerId);
            userSession.setImei(msg.getMessageHeader().getImei());
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                userSession.setBrokerHost(localHost.getHostAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RedissonClient redissonClient = RedisManager.getRedissonClient();
            RMap<String, String> map = redissonClient.getMap(msg.getMessageHeader().getAppId() + Constants.RedisConstants.UserSessionConstants + loginPack.getUserId());
            map.put(msg.getMessageHeader().getClientType() + ":" + msg.getMessageHeader().getImei()
                    , JSONObject.toJSONString(userSession));
            SessionSocketHolder
                    .put(msg.getMessageHeader().getAppId()
                            , loginPack.getUserId(),
                            msg.getMessageHeader().getClientType(), msg.getMessageHeader().getImei(), (NioSocketChannel) ctx.channel());

            UserClientDto dto = new UserClientDto();
            dto.setImei(msg.getMessageHeader().getImei());
            dto.setUserId(loginPack.getUserId());
            dto.setClientType(msg.getMessageHeader().getClientType());
            dto.setAppId(msg.getMessageHeader().getAppId());
            RTopic topic = redissonClient.getTopic(Constants.RedisConstants.UserLoginChannel);
            topic.publish(JSONObject.toJSONString(dto));

            UserStatusChangeNotifyPack userStatusChangeNotifyPack = new UserStatusChangeNotifyPack();
            userStatusChangeNotifyPack.setAppId(msg.getMessageHeader().getAppId());
            userStatusChangeNotifyPack.setUserId(loginPack.getUserId());
            userStatusChangeNotifyPack.setStatus(ImConnectStatusEnum.ONLINE_STATUS.getCode());
            MqMessageProducer.sendMessage(userStatusChangeNotifyPack, msg.getMessageHeader(), UserEventCommand.USER_ONLINE_STATUS_CHANGE.getCommand());

            MessagePack<LoginAckPack> loginSuccess = new MessagePack<>();
            LoginAckPack loginAckPack = new LoginAckPack();
            loginAckPack.setUserId(loginPack.getUserId());
            loginSuccess.setCommand(SystemCommand.LOGINACK.getCommand());
            loginSuccess.setData(loginAckPack);
            loginSuccess.setImei(msg.getMessageHeader().getImei());
            loginSuccess.setAppId(msg.getMessageHeader().getAppId());
            ctx.channel().writeAndFlush(loginSuccess);

        } else if (command == SystemCommand.LOGOUT.getCommand()) {
            //删除session
            //redis 删除
            SessionSocketHolder.removeUserSession((NioSocketChannel) ctx.channel());
        } else if (command == SystemCommand.PING.getCommand()) {
            ctx.channel()
                    .attr(AttributeKey.valueOf(Constants.ReadTime)).set(System.currentTimeMillis());
        } else if (command == MessageCommand.MSG_P2P.getCommand()
                || command == GroupEventCommand.MSG_GROUP.getCommand()) {

            try {
                String toId = "";
                CheckSendMessageReq req = new CheckSendMessageReq();
                req.setAppId(msg.getMessageHeader().getAppId());
                req.setCommand(msg.getMessageHeader().getCommand());
                JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()));
                String fromId = jsonObject.getString("fromId");
                if (command == MessageCommand.MSG_P2P.getCommand()) {
                    toId = jsonObject.getString("toId");
                } else {
                    toId = jsonObject.getString("groupId");
                }
                req.setToId(toId);
                req.setFromId(fromId);

                ResponseVO responseVO = feignMessageService.checkSendMessage(req);
                if (responseVO.isOk()) {
                    MqMessageProducer.sendMessage(msg, command);
                } else {
                    Integer ackCommand = 0;
                    if (command == MessageCommand.MSG_P2P.getCommand()) {
                        ackCommand = MessageCommand.MSG_ACK.getCommand();
                    } else {
                        ackCommand = GroupEventCommand.GROUP_MSG_ACK.getCommand();
                    }

                    ChatMessageAck chatMessageAck = new ChatMessageAck(jsonObject.getString("messageId"));
                    responseVO.setData(chatMessageAck);
                    MessagePack<ResponseVO> ack = new MessagePack<>();
                    ack.setData(responseVO);
                    ack.setCommand(ackCommand);
                    ctx.channel().writeAndFlush(ack);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MqMessageProducer.sendMessage(msg, command);
        }

    }

    //表示 channel 处于不活动状态
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) {
//        //设置离线
//        SessionSocketHolder.offlineUserSession((NioSocketChannel) ctx.channel());
//        ctx.close();
//    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);

    }
}
