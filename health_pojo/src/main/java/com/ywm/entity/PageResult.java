package com.ywm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/22 13:42
 */
public class PageResult<Object> implements Serializable {

    private Long total;//总记录数
    private List rows;//当前页结果
    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }

    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
}
