package com.miao.im.service.user.controller;

import com.miao.im.common.ResponseVO;
import com.miao.im.service.user.model.req.GetUserInfoReq;
import com.miao.im.service.user.model.req.ModifyUserInfoReq;
import com.miao.im.service.user.model.req.UserId;
import com.miao.im.service.user.service.ImUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/user/data")
public class ImUserDataController {

    private static Logger logger = LoggerFactory.getLogger(ImUserDataController.class);

    @Autowired
    ImUserService imUserService;

    @RequestMapping("/getUserInfo")
    public ResponseVO getUserInfo(@RequestBody GetUserInfoReq req, Integer appId) {//@Validated
        req.setAppId(appId);
        return imUserService.getUserInfo(req);
    }

    @RequestMapping("/getSingleUserInfo")
    public ResponseVO getSingleUserInfo(@RequestBody @Validated UserId req, Integer appId) {
        req.setAppId(appId);
        return imUserService.getSingleUserInfo(req.getUserId(), req.getAppId());
    }

    @RequestMapping("/modifyUserInfo")
    public ResponseVO modifyUserInfo(@RequestBody @Validated ModifyUserInfoReq req, Integer appId) {
        req.setAppId(appId);
        return imUserService.modifyUserInfo(req);
    }
}
