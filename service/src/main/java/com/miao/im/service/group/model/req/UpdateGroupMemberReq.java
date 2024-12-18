package com.miao.im.service.group.model.req;

import com.miao.im.common.model.RequestBase;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @description:
 **/
@Data
public class UpdateGroupMemberReq extends RequestBase {

    @NotBlank(message = "群id不能为空")
    private String groupId;

    @NotBlank(message = "memberId不能为空")
    private String memberId;

    private String alias;

    private Integer role;

    private String extra;

}
