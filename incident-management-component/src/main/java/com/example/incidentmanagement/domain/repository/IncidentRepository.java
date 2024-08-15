package com.example.incidentmanagement.domain.repository;

import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.domain.valueobject.PageInfo;
import com.example.incidentmanagement.persisitence.entity.Incident;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
public interface IncidentRepository {

    void createIncident(Incident incident);

    void updateIncident(Incident incident);

    boolean deleteIncident(Long id);

    Page<Incident> listAll(PageInfo pageInfo, String operator);

    Incident getIncident(Long id, String operator);

    Incident queryIncidentByOperator(String operator, String title);

    boolean existsById(Long id);
}
