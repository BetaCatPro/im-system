package com.miao.im.service.user.model.resp;

import com.miao.im.common.model.UserSession;
import lombok.Data;

import java.util.List;


@Data
public class UserOnlineStatusResp {

    private List<UserSession> session;

    private String customText;

    private Integer customStatus;

}
