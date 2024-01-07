package com.internetprogramming.mbip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.OilData;

@Repository
public interface OilRepository extends JpaRepository <OilData, Long>{}
