package com.miao.im.codec.pack.friendship;

import lombok.Data;

/**
 * 
 * @description: 删除好友通知报文
 **/
@Data
public class DeleteFriendPack {

    private String fromId;

    private String toId;

    private Long sequence;
}
