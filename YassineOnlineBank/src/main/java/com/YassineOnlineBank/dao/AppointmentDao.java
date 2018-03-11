package com.YassineOnlineBank.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.YassineOnlineBank.models.Appointment;

public interface AppointmentDao extends CrudRepository<Appointment, Long> {

    List<Appointment> findAll();
}
