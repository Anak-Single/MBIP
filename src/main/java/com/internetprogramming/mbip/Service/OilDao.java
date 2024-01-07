package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Repository.OilRepository;

@Service
public class OilDao{

    private OilRepository repository;

    public OilDao(OilRepository repository)
    {
        this.repository = repository;
    }

    //find all Oil data
    public List <OilData> findAllData()
    {
        List <OilData> data = repository.findAll();
        return data;
    }

    //find Oil data by their id
    public OilData findDataById(Long id)
    {
        Optional <OilData> optData = repository.findById(id);
        OilData data = optData.orElse(null);

        return data;
    }

    //find All Oil data based on User id
    public List <OilData> findDataByUserId(Long userId)
    {
        List <OilData> data = repository.findAllByUserId(userId);
        return data;
    }

    //save data
    public void saveData(OilData data)
    {
        repository.save(data);
    }

    //update data
    public void updateData(Long id, OilData data)
    {
        Optional <OilData> optData = repository.findById(id);
        OilData oldData = optData.orElse(null);

        if (oldData != null)
        {
            oldData.setCategory(data.getCategory());
            oldData.setWeight(data.getWeight());
            oldData.setUpdateTime();
            
            repository.saveAndFlush(oldData);
        }
    }

    //delete data
    public void deleteData(Long id)
    {
        Optional <OilData> optData = repository.findById(id);
        OilData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }
}
