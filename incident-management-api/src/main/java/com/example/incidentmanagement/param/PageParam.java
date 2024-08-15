package com.example.incidentmanagement.param;

import javax.validation.constraints.Positive;

import com.example.incidentmanagement.domain.valueobject.PageInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
public class PageParam {
    //页号
    @ApiModelProperty(value = "页号")
    private int page;
    //每页大小
    @ApiModelProperty(value = "每页大小")
    @Positive
    private int pageSize;

    public PageInfo convert() {
        return PageInfo.builder()
                .page(this.getPage())
                .pageSize(this.getPageSize())
                .build();
    }
}
