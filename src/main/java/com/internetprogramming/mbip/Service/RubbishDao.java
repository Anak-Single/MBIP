package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Repository.RubbishRepository;

@Service
public class RubbishDao
{
    private RubbishRepository repository;

    public RubbishDao(RubbishRepository repository)
    {
        this.repository = repository;
    }

    //find all Rubbish data
    public List <RubbishData> findAllData()
    {
        List <RubbishData> data = repository.findAll();
        return data;
    }

    //find Rubbish data by their id
    public RubbishData findDataById(Long id)
    {
        Optional <RubbishData> optData = repository.findById(id);
        RubbishData data = optData.orElse(null);

        return data;
    }

    //find All Rubbish data based on User id
    public List <RubbishData> findDataByUserId(Long userId)
    {
        List <RubbishData> data = repository.findAllByUserId(userId);
        return data;
    }

    //save data
    public void saveData(RubbishData data)
    {
        repository.save(data); 
    }

    //update data
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

    //delete data
    public void deleteData(Long id)
    {
        Optional <RubbishData> optData = repository.findById(id);
        RubbishData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }
}