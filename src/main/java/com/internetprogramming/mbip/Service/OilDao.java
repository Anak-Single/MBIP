package com.internetprogramming.mbip.Service;

import java.util.List;

import com.internetprogramming.mbip.Entity.OilData;

public interface OilDao
{
    public OilData findByUserId(Long id);

    public void saveData(OilData data);

    public void updateData(Long id, OilData data);

    public void deleteData(Long id);

    public List <OilData> findAllData();
}
