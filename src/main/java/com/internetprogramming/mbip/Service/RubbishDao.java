package com.internetprogramming.mbip.Service;

import java.util.List;

import com.internetprogramming.mbip.Entity.RubbishData;

public interface RubbishDao
{
    public RubbishData findByUserId(Long id);

    public void saveData(RubbishData data);

    public void updateData(Long id, RubbishData data);

    public void deleteData(Long id);

    public List <RubbishData> findAllData();
}
