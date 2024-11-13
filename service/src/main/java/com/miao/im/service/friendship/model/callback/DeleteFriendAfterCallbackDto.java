package com.miao.im.service.friendship.model.callback;

import com.miao.im.service.friendship.model.req.FriendDto;
import lombok.Data;


@Data
public class DeleteFriendAfterCallbackDto {

    private String fromId;

    private String toId;
}
