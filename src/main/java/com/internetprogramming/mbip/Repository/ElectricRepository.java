package com.internetprogramming.mbip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.ElectricData;

@Repository
public interface ElectricRepository extends JpaRepository <ElectricData, Long>{}