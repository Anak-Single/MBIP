package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Repository.RubbishRepository;

public class RubbishDao
{
    private RubbishRepository repository;

    public RubbishDao(RubbishRepository repository)
    {
        this.repository = repository;
    }

    public RubbishData findByUserId(Long id)
    {
        Optional <RubbishData> optData = repository.findById(id);
        RubbishData data = optData.orElse(null);

        return data;
    }

    public void saveData(RubbishData data)
    {
        repository.save(data); 
    }

    public void updateData(Long id, RubbishData data)
    {
        Optional <RubbishData> optData = repository.findById(id);
        RubbishData oldData = optData.orElse(null);

        if (oldData != null)
        {
            oldData.setType(data.getType());
            oldData.setWeight(data.getWeight());
            oldData.setUpdateTime();
            
            repository.saveAndFlush(oldData);
        }
    }

    public void deleteData(Long id)
    {
        Optional <RubbishData> optData = repository.findById(id);
        RubbishData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }

    public List <RubbishData> findAllData()
    {
        List <RubbishData> data = repository.findAll();
        return data;
    }
}