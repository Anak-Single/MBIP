package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Repository.OilRepository;

@Service
public class OilDaoImpl implements OilDao{

    private OilRepository repository;

    public OilDaoImpl(OilRepository repository)
    {
        super();
        this.repository = repository;
    }

    @Override
    public OilData findByUserId(Long id)
    {
        Optional <OilData> optData = repository.findById(id);
        OilData data = optData.orElse(null);

        return data;
    }

    @Override
    public void saveData(OilData data)
    {
        repository.save(data);
    }

    @Override
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

    @Override
    public void deleteData(Long id)
    {
        Optional <OilData> optData = repository.findById(id);
        OilData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }

    @Override
    public List <OilData> findAllData()
    {
        List <OilData> data = repository.findAll();
        return data;
    }
}
