package com.miao.im.codec;

import com.alibaba.fastjson.JSONObject;
import com.miao.im.codec.proto.Message;
import com.miao.im.codec.proto.MessageHeader;
import com.miao.im.codec.utils.ByteBufToMessageUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description: 消息解码类
 * @author: miao
 * @version: 1.0
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {
        //请求头（指令
        // 版本
        // clientType
        // 消息解析类型
        // appId
        // imei长度
        // bodylen）+ imei号 + 请求体

        if (in.readableBytes() < 28) {
            return;
        }

        Message message = ByteBufToMessageUtils.transition(in);
        if (message == null) {
            return;
        }

        out.add(message);
    }
}
