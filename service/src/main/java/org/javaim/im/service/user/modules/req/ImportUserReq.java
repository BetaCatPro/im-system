package org.javaim.im.service.user.modules.req;

import lombok.Data;
import org.javaim.im.common.modal.RequestBase;
import org.javaim.im.service.user.dao.ImUserDataEntity;

import java.util.List;

@Data
public class ImportUserReq extends RequestBase {
    private List<ImUserDataEntity> userData;
}
