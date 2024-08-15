package com.example.incidentmanagement.persisitence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.incidentmanagement.persisitence.entity.Incident;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Repository
public interface IncidentJpaRepository extends JpaRepository<Incident, Long> {

    @Query()
    Optional<Incident> findByIdAndOperator(Long id, String operator);

    @Query("SELECT i FROM Incident i WHERE i.operator = :operator and i.title = :title")
    Optional<Incident> findByOperatorAndTitle(String operator, String title);

    @Query("SELECT i FROM Incident i WHERE i.operator = :operator")
    List<Incident> findByOperator(String operator);

    @Query("SELECT i FROM Incident i")
    Page<Incident> findAllIncidents(Pageable pageable);
}
