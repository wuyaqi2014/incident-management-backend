package com.example.incidentmanagement.persisitence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

/**
 * incident entity
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
@Entity
@Table(name = "incident", indexes = {
        @Index(name = "idx_operator", columnList = "operator")
})
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 标题
    private String title;

    // 描述
    private String description;

    // 状态
    private int status;

    // 事件开始时间
    private Long startTime;

    // 事件结束时间
    private Long endTime;

    // 备注
    private String remark;

    // 创建人
    @Column(name = "operator")
    private String operator;

    // 更新人
    private String updatedBy;

    // 创建时间 减少时区转换
    private Long createdTime;

    // 更新时间 减少时区转换
    private Long updatedTime;

}
