package com.medlink.appointment.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medlink.appointment.models.PatientInfo;

@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Integer> {
    List<PatientInfo> findAllByPatientId(Long patientId);
}
