package com.internetprogramming.mbip.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.WaterData;

@Repository
public interface WaterRepository extends JpaRepository <WaterData, Long>
{
    //find all Rubbish data using user id
    List <WaterData> findAllByUserId(Long userId);
}
