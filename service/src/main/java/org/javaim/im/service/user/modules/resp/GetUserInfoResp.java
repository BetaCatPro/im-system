package org.javaim.im.service.user.modules.resp;

import lombok.Data;
import org.javaim.im.service.user.dao.ImUserDataEntity;

import java.util.List;

@Data
public class GetUserInfoResp {

    private List<ImUserDataEntity> userDataItem;

    private List<String> failUser;


}
