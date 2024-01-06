package com.internetprogramming.mbip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.RubbishData;

@Repository
public interface RubbishRepository extends JpaRepository <RubbishData, Long>{}
