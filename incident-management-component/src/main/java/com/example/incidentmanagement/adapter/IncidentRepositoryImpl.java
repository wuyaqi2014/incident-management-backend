package com.example.incidentmanagement.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.incidentmanagement.common.DataAuthUtil;
import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.domain.valueobject.PageInfo;
import com.example.incidentmanagement.persisitence.IncidentJpaRepository;
import com.example.incidentmanagement.persisitence.entity.Incident;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Slf4j
@Service
public class IncidentRepositoryImpl implements IncidentRepository {

    @Autowired
    private IncidentJpaRepository incidentJpaRepository;

    @Override
    public void createIncident(Incident incident) {
        incidentJpaRepository.save(incident);
    }

    @Override
    public void updateIncident(Incident incident) {
        incidentJpaRepository.save(incident);
    }

    @Override
    public boolean deleteIncident(Long id) {
        incidentJpaRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<Incident> listAll(PageInfo pageInfo, String operator) {
        Page<Incident> page = new Page(pageInfo.getPage(), pageInfo.getPageSize());
        Pageable pageable = PageRequest.of(pageInfo.getPage() == 0 ? 1 :pageInfo.getPage() - 1, pageInfo.getPageSize());
        org.springframework.data.domain.Page<Incident> dataPage = incidentJpaRepository.findAllIncidents(pageable);
        page.setTotalCount(dataPage.getTotalElements());
        page.setData(dataPage.getContent());
        return page;
    }

    @Override
    public Incident getIncident(Long id, String operator) {
        if (DataAuthUtil.isSupreme(operator)) {
            Optional<Incident> incidentOptional = incidentJpaRepository.findById(id);
            return incidentOptional.orElse(null);
        }
        Optional<Incident> incidentOptional = incidentJpaRepository.findByIdAndOperator(id, operator);
        return incidentOptional.orElse(null);
    }

    @Override
    public Incident queryIncidentByOperator(String operator, String title) {
        Optional<Incident> incidentOptional = incidentJpaRepository.findByOperatorAndTitle(operator, title);
        return incidentOptional.orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return incidentJpaRepository.existsById(id);
    }
}
