package com.miao.im.codec.pack.message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @description: 撤回消息通知报文
 **/
@Data
@NoArgsConstructor
public class RecallMessageNotifyPack {

    private String fromId;

    private String toId;

    private Long messageKey;

    private Long messageSequence;
}
