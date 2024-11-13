package com.miao.im.common.model.message;

import lombok.Data;
import sun.dc.pr.PRError;


@Data
public class CheckSendMessageReq {

    private String fromId;

    private String toId;

    private Integer appId;

    private Integer command;

}
