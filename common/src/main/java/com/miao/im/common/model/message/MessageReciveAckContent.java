package com.miao.im.common.model.message;

import com.miao.im.common.model.ClientInfo;
import lombok.Data;
import sun.dc.pr.PRError;


@Data
public class MessageReciveAckContent extends ClientInfo {

    private Long messageKey;

    private String fromId;

    private String toId;

    private Long messageSequence;


}
