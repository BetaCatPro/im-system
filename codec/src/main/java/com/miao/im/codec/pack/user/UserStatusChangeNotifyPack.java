package com.miao.im.codec.pack.user;

import com.miao.im.common.model.UserSession;
import lombok.Data;
import sun.dc.pr.PRError;

import java.util.List;


@Data
public class UserStatusChangeNotifyPack {

    private Integer appId;

    private String userId;

    private Integer status;

    private List<UserSession> client;

}
