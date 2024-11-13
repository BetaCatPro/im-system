package com.miao.im.codec.pack.group;

import lombok.Data;

/**
 * 
 * @description: 转让群主通知报文
 **/
@Data
public class TransferGroupPack {

    private String groupId;

    private String ownerId;

}
