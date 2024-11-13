package com.miao.im.service.user.model.req;

import com.miao.im.common.model.RequestBase;
import com.miao.im.service.user.dao.ImUserDataEntity;
import lombok.Data;

import java.util.List;


@Data
public class ImportUserReq extends RequestBase {

    private List<ImUserDataEntity> userData;


}