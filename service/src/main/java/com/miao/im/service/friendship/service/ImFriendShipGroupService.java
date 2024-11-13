package com.miao.im.service.friendship.service;

import com.miao.im.common.ResponseVO;
import com.miao.im.service.friendship.dao.ImFriendShipGroupEntity;
import com.miao.im.service.friendship.model.req.AddFriendShipGroupReq;
import com.miao.im.service.friendship.model.req.DeleteFriendShipGroupReq;

/**
 * 
 * @description:
 **/
public interface ImFriendShipGroupService {

    public ResponseVO addGroup(AddFriendShipGroupReq req);

    public ResponseVO deleteGroup(DeleteFriendShipGroupReq req);

    public ResponseVO<ImFriendShipGroupEntity> getGroup(String fromId, String groupName, Integer appId);

    public Long updateSeq(String fromId, String groupName, Integer appId);
}
