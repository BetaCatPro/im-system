package com.miao.im.service.user.model.resp;

import lombok.Data;

import java.util.List;

/**
 * 
 * @description:
 **/
@Data
public class ImportUserResp {

    private List<String> successId;

    private List<String> errorId;

}
