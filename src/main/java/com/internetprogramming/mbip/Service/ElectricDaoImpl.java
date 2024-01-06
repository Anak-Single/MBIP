package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Repository.ElectricRepository;

@Service
public class ElectricDaoImpl implements ElectricDao{

    private ElectricRepository repository;

    public ElectricDaoImpl(ElectricRepository repository)
    {
        super();
        this.repository = repository;
    }

    @Override
    public ElectricData findBillsByUserId(Long id)
    {
        Optional <ElectricData> optData = repository.findById(id);
        ElectricData data = optData.orElse(null);

        return data;
    }

    @Override
    public void saveData(ElectricData data)
    {
        repository.save(data);
    }

    @Override
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

    @Override
    public void deleteData(Long id)
    {
        Optional <ElectricData> optData = repository.findById(id);
        ElectricData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }

    @Override
    public List <ElectricData> findAllData()
    {
        List <ElectricData> data = repository.findAll();
        return data;
    }
    
}
