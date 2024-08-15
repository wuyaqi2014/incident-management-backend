package com.example.incidentmanagement.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Data
@SuperBuilder
public class Page<T> {
    private List<T> data;
    private int currentPage;
    private int pageSize;
    private long totalCount;

    public Page() {
    }

    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Page(int currentPage, int pageSize, long totalCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    @JsonIgnore
    public int getFrom() {
        return (this.currentPage - 1) * this.pageSize;
    }
}
