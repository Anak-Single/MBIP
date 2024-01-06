package com.internetprogramming.mbip.Service;

import java.util.List;

import com.internetprogramming.mbip.Entity.WaterData;

public interface WaterDao
{
    public WaterData findBillsByUserId(Long id);

    public void saveData(WaterData data);

    public void updateData(Long id, WaterData data);

    public void deleteData(Long id);

    public List <WaterData> findAllData();
}
