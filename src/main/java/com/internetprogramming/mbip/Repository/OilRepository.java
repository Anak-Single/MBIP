package com.internetprogramming.mbip.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.OilData;

@Repository
public interface OilRepository extends JpaRepository <OilData, Long>
{
    //find all Oil data using user id
    List <OilData> findAllByUserId(Long userId);
}
