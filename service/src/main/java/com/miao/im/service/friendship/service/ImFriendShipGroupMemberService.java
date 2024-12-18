package com.miao.im.service.friendship.service;

import com.miao.im.common.ResponseVO;
import com.miao.im.service.friendship.model.req.AddFriendShipGroupMemberReq;
import com.miao.im.service.friendship.model.req.DeleteFriendShipGroupMemberReq;

/**
 * 
 * @description:
 **/
public interface ImFriendShipGroupMemberService {

    public ResponseVO addGroupMember(AddFriendShipGroupMemberReq req);

    public ResponseVO delGroupMember(DeleteFriendShipGroupMemberReq req);

    public int doAddGroupMember(Long groupId, String toId);

    public int clearGroupMember(Long groupId);
}
