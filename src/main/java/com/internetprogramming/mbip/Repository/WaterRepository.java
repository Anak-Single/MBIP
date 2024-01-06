package com.internetprogramming.mbip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.WaterData;

@Repository
public interface WaterRepository extends JpaRepository <WaterData, Long>{}
