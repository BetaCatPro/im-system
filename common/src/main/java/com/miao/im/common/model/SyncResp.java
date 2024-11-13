package com.miao.im.common.model;

import lombok.Data;

import java.util.List;

/**
 * 
 * @description:
 **/
@Data
public class SyncResp<T> {

    private Long maxSequence;

    private boolean isCompleted;

    private List<T> dataList;

}
