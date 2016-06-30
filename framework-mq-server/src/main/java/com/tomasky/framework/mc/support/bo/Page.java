package com.tomasky.framework.mc.support.bo;

import java.util.List;

/**
 * @author Hunhun
 */
public class Page<T> {

    /**
     * 页码
     */
    private Integer pageIndex = 0;

    /**
     * 每页大小
     */
    private Integer pageSize = 20;

    /**
     * 总条目数
     */
    private Long totalCount;

    /**
     * 结果集
     */
    private List<T> result;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
