package com.tdtu.finalproject.repository;

import com.tdtu.finalproject.entity.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {
    List<RealEstate> findByUserId(String user_id);
    List<RealEstate> findByLocation(String location);

}
