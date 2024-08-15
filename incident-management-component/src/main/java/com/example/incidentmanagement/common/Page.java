package com.example.incidentmanagement.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
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

    @JsonIgnore
    public int getFrom() {
        return (this.currentPage - 1) * this.pageSize;
    }
}
