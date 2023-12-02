package com.wtk.xiban.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 *
 * @author wtk
 */

@Data
public class PageRequset implements Serializable {

    private static final long serialVersionUID = -8187302009647184594L;

    /**
     * 每页数据条数
     */
    protected int pageSize = 10;

    /**
     * 当前页码
     */
    protected int pageNum = 1;
}
