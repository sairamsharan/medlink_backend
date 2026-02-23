package com.medlink.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medlink.hospital.models.HospitalModel;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalModel, Long> {
    List<HospitalModel> findAllByLocation(String location);
}
