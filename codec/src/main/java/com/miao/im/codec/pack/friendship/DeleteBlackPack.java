package com.miao.im.codec.pack.friendship;

import lombok.Data;

/**
 * 
 * @description: 删除黑名单通知报文
 **/
@Data
public class DeleteBlackPack {

    private String fromId;

    private String toId;

    private Long sequence;
}
