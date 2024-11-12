package org.javaim.im.common.modal;

import lombok.Data;

@Data
public class RequestBase {
    private Integer appId;

    public Integer getAppId() {
        return appId;
    }
}
