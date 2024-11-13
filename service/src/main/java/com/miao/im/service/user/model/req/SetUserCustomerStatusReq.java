package com.miao.im.service.user.model.req;

import com.miao.im.common.model.RequestBase;
import lombok.Data;


@Data
public class SetUserCustomerStatusReq extends RequestBase {

    private String userId;

    private String customText;

    private Integer customStatus;

}
