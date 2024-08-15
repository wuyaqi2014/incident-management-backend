package com.example.incidentmanagement.application.service;

import com.example.incidentmanagement.persisitence.entity.Incident;
import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.domain.valueobject.IncidentRequest;
import com.example.incidentmanagement.domain.valueobject.IncidentResult;
import com.example.incidentmanagement.domain.valueobject.PageInfo;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
public interface IncidentService {

    IncidentResult createIncident(IncidentRequest incidentRequest, String operator);

    IncidentResult updateIncident(Long incidentID, IncidentRequest incidentRequest, String operator);

    boolean deleteIncident(Long id, String operator);

//    Incident getIncident(Long id);

    Page<Incident> listAll(PageInfo pageInfo, String operator);


}
