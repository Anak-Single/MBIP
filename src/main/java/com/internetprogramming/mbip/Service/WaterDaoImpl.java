package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Repository.WaterRepository;

@Service
public class WaterDaoImpl implements WaterDao{

    private WaterRepository repository;

    public WaterDaoImpl(WaterRepository repository)
    {
        super();
        this.repository = repository;
    }

    @Override
    public WaterData findBillsByUserId(Long id)
    {
        Optional <WaterData> optData = repository.findById(id);
        WaterData data = optData.orElse(null);

        return data;
    }

    @Override
    public void saveData(WaterData data)
    {
        repository.save(data);
    }

    @Override
    public void updateData(Long id, WaterData data)
    {
        Optional <WaterData> optData = repository.findById(id);
        WaterData oldData = optData.orElse(null);

        if (oldData != null) {
            // Update user properties...
            oldData.setBillID(data.getBillID());
            oldData.setBillDate(data.getBillDate());
            oldData.setBillAmount(data.getBillAmount());
            oldData.setUpdateTime();
            
            repository.saveAndFlush(oldData);
        }
    }

    @Override
    public void deleteData(Long id)
    {
        Optional <WaterData> optData = repository.findById(id);
        WaterData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }

    @Override
    public List <WaterData> findAllData()
    {
        List <WaterData> data = repository.findAll();
        return data;
    }
    
}
