package org.javaim.im.service.user.modules.resp;

import lombok.Data;

import java.util.List;

@Data
public class ImportUserResp {
    private List<String> successId;
    private List<String> errorId;
}
