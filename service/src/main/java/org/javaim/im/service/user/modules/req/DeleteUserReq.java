package org.javaim.im.service.user.modules.req;

import lombok.Data;
import org.javaim.im.common.modal.RequestBase;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
public class DeleteUserReq extends RequestBase {

    @NotEmpty(message = "用户id不能为空")
    private List<String> userId;
}
