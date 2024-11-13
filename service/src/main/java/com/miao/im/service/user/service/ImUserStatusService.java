package com.miao.im.service.user.service;

import com.miao.im.common.ResponseVO;
import com.miao.im.service.user.model.UserStatusChangeNotifyContent;
import com.miao.im.service.user.model.req.PullFriendOnlineStatusReq;
import com.miao.im.service.user.model.req.PullUserOnlineStatusReq;
import com.miao.im.service.user.model.req.SetUserCustomerStatusReq;
import com.miao.im.service.user.model.req.SubscribeUserOnlineStatusReq;
import com.miao.im.service.user.model.resp.UserOnlineStatusResp;

import java.util.Map;


public interface ImUserStatusService {

    public void processUserOnlineStatusNotify(UserStatusChangeNotifyContent content);

    void subscribeUserOnlineStatus(SubscribeUserOnlineStatusReq req);

    void setUserCustomerStatus(SetUserCustomerStatusReq req);

    Map<String, UserOnlineStatusResp> queryFriendOnlineStatus(PullFriendOnlineStatusReq req);

    Map<String, UserOnlineStatusResp> queryUserOnlineStatus(PullUserOnlineStatusReq req);
}
