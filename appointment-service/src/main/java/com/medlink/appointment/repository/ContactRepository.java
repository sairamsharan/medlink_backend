package com.medlink.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medlink.appointment.models.ContactModel;

@Repository
public interface ContactRepository extends JpaRepository<ContactModel, Integer> {
}
