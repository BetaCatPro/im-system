package org.javaim.im.service.user.modules.req;

import lombok.Data;
import org.javaim.im.common.modal.RequestBase;

import java.util.List;


@Data
public class GetUserInfoReq extends RequestBase {

    private List<String> userIds;


}
