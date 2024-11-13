package com.miao.im.codec.pack.user;

import com.miao.im.common.model.UserSession;
import lombok.Data;

import java.util.List;


@Data
public class UserCustomStatusChangeNotifyPack {

    private String customText;

    private Integer customStatus;

    private String userId;

}
