package com.miao.message.model;

import com.miao.im.common.model.message.GroupChatMessageContent;
import com.miao.im.common.model.message.MessageContent;
import com.miao.message.dao.ImMessageBodyEntity;
import lombok.Data;

/**
 * 
 * @description:
 **/
@Data
public class DoStoreGroupMessageDto {

    private GroupChatMessageContent groupChatMessageContent;

    private ImMessageBodyEntity imMessageBodyEntity;

}
