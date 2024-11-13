package com.miao.im.service.conversation.controller;

import com.miao.im.common.ResponseVO;
import com.miao.im.common.model.SyncReq;
import com.miao.im.service.conversation.model.DeleteConversationReq;
import com.miao.im.service.conversation.model.UpdateConversationReq;
import com.miao.im.service.conversation.service.ConversationService;
import com.miao.im.service.group.model.req.ImportGroupReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("v1/conversation")
public class ConversationController {

    @Autowired
    ConversationService conversationService;

    @RequestMapping("/deleteConversation")
    public ResponseVO deleteConversation(@RequestBody @Validated DeleteConversationReq
                                                 req, Integer appId, String identifier) {
        req.setAppId(appId);
//        req.setOperater(identifier);
        return conversationService.deleteConversation(req);
    }

    @RequestMapping("/updateConversation")
    public ResponseVO updateConversation(@RequestBody @Validated UpdateConversationReq
                                                 req, Integer appId, String identifier) {
        req.setAppId(appId);
//        req.setOperater(identifier);
        return conversationService.updateConversation(req);
    }

    @RequestMapping("/syncConversationList")
    public ResponseVO syncFriendShipList(@RequestBody @Validated SyncReq req, Integer appId) {
        req.setAppId(appId);
        return conversationService.syncConversationSet(req);
    }

}
