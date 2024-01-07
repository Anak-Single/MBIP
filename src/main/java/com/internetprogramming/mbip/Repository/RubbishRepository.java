package com.internetprogramming.mbip.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.RubbishData;

@Repository
public interface RubbishRepository extends JpaRepository <RubbishData, Long>
{
    //find all Rubbish data using user id
    List <RubbishData> findAllByUserId(Long userId);
}
