package com.miao.im.service.group.model.req;

import com.miao.im.common.model.RequestBase;
import lombok.Data;

import java.util.List;

/**
 * 
 * @description:
 **/
@Data
public class GetRoleInGroupReq extends RequestBase {

    private String groupId;

    private List<String> memberId;
}
