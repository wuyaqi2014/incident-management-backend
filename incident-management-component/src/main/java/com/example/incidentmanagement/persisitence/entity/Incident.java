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

    private String title;

    private String description;

    private int status;

    private Long startTime;

    private Long endTime;

    private String remark;

    @Column(name = "operator")
    private String operator;

    private String updatedBy;

    private Long createdTime;

    private Long updatedTime;

}
