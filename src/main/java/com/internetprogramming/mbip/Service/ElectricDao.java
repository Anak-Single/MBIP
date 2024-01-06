package com.internetprogramming.mbip.Service;

import java.util.List;

import com.internetprogramming.mbip.Entity.ElectricData;

public interface ElectricDao
{
    public ElectricData findBillsByUserId(Long id);

    public void saveData(ElectricData data);

    public void updateData(Long id, ElectricData data);

    public void deleteData(Long id);

    public List <ElectricData> findAllData();
}
