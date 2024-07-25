package com.tdtu.finalproject.repository;

import com.tdtu.finalproject.entity.Appointment;
import com.tdtu.finalproject.entity.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByBuyerId(String buyer_id);
    List<Appointment> findBySellerId(String seller_id);


}
