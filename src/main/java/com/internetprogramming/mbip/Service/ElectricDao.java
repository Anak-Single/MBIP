package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Repository.ElectricRepository;

@Service
public class ElectricDao{

    private ElectricRepository repository;

    public ElectricDao(ElectricRepository repository)
    {
        this.repository = repository;
    }

    //find all Electric bills
    public List <ElectricData> findAllData()
    {
        List <ElectricData> data = repository.findAll();
        return data;
    }

    //find Electric bills by their id
    public ElectricData findBillsById(Long id)
    {
        Optional <ElectricData> optData = repository.findById(id);
        ElectricData data = optData.orElse(null);

        return data;
    }

    //find all Electric bills based on User id
    public List <ElectricData> findBillsByUserId(Long userId)
    {
        List <ElectricData> data = repository.findAllByUserId(userId);
        return data;
    }

    //save data
    public void saveData(ElectricData data)
    {
        repository.save(data);
    }

    //update data
    public void updateData(Long id, ElectricData data)
    {
        Optional <ElectricData> optData = repository.findById(id);
        ElectricData oldData = optData.orElse(null);

        if (oldData != null)
        {
            oldData.setBillID(data.getBillID());
            oldData.setBillDate(data.getBillDate());
            oldData.setBillAmount(data.getBillAmount());
            oldData.setUpdateTime();
            
            repository.saveAndFlush(oldData);
        }
    }

    //delete data
    public void deleteData(Long id)
    {
        Optional <ElectricData> optData = repository.findById(id);
        ElectricData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }    
}