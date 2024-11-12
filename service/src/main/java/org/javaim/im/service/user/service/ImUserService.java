package org.javaim.im.service.user.service;

import org.javaim.im.common.ResponseVO;
import org.javaim.im.service.user.dao.ImUserDataEntity;
import org.javaim.im.service.user.modules.req.DeleteUserReq;
import org.javaim.im.service.user.modules.req.GetUserInfoReq;
import org.javaim.im.service.user.modules.req.ImportUserReq;
import org.javaim.im.service.user.modules.req.ModifyUserInfoReq;
import org.javaim.im.service.user.modules.resp.GetUserInfoResp;

public interface ImUserService {
    public ResponseVO importUser(ImportUserReq req);

    public ResponseVO<GetUserInfoResp> getUserInfo(GetUserInfoReq req);

    public ResponseVO<ImUserDataEntity> getSingleUserInfo(String userId , Integer appId);

    public ResponseVO deleteUser(DeleteUserReq req);

    public ResponseVO modifyUserInfo(ModifyUserInfoReq req);
}
