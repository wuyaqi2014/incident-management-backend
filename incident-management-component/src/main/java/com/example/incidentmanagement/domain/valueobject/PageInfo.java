package com.example.incidentmanagement.domain.valueobject;

import lombok.Builder;
import lombok.Data;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
@Builder
public class PageInfo {

    //页号
    private int page;
    //每页大小
    private int pageSize;

}
