package com.internetprogramming.mbip.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Repository.WaterRepository;

@Service
public class WaterDao{

    private WaterRepository repository;

    public WaterDao(WaterRepository repository)
    {
        this.repository = repository;
    }

    //find all Water data
    public List <WaterData> findAllData()
    {
        List <WaterData> data = repository.findAll();
        return data;
    }

    //find Water data by their id
    public WaterData findBillsByUserId(Long id)
    {
        Optional <WaterData> optData = repository.findById(id);
        WaterData data = optData.orElse(null);

        return data;
    }

    //find All Water data based on User Id
    public List <WaterData> findDataByUserId(Long userId)
    {
        List <WaterData> data = repository.findAllByUserId(userId);
        return data;
    }

    //categorize the data from user based on date
    public Map<LocalDate, List<WaterData>> categorizeOrdersByDay(Long userId) {
        List<WaterData> allData = repository.findAllByUserId(userId);

        return allData.stream()
                .collect(Collectors.groupingBy(WaterData::getCreationTime));
    }

    // New method to get a list of WaterData objects categorized by creation time
    public List<WaterData> getCategorizedWaterData(Long userId) {
        Map<LocalDate, List<WaterData>> categorizedDataMap = categorizeOrdersByDay(userId);

        // Combine the lists from the map into a single list
        return categorizedDataMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    //save data
    public void saveData(WaterData data)
    {
        repository.save(data);
    }

    //update data
    public void updateData(Long id, WaterData data)
    {
        Optional <WaterData> optData = repository.findById(id);
        WaterData oldData = optData.orElse(null);

        if (oldData != null) {
            // Update user properties...
            oldData.setBillID(data.getBillID());
            oldData.setBillDate(data.getBillDate());
            oldData.setBillAmount(data.getBillAmount());
            
            repository.saveAndFlush(oldData);
        }
    }

    //delete data
    public void deleteData(Long id)
    {
        Optional <WaterData> optData = repository.findById(id);
        WaterData oldData = optData.orElse(null);

        if (oldData != null) {
            repository.delete(oldData);
        }
    }    
}