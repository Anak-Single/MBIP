package com.internetprogramming.mbip.Controller.Api;

import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.UserDao;
import com.internetprogramming.mbip.Service.WaterDao;
import com.internetprogramming.mbip.Service.ElectricDao;
import com.internetprogramming.mbip.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WaterDao waterDao;

    @Autowired
    private ElectricDao electricDao;

    @Autowired
    private OilDao oilDao;

    @Autowired
    private RubbishDao rubbishDao;

    // User Management API
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userDao.findAllUser());
    }
        
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userDao.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userDao.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Water Data API
    @GetMapping("/water")
    public ResponseEntity<List<WaterData>> getWaterData() {
        return ResponseEntity.ok(waterDao.findAllData());
    }

    @PostMapping("/water")
    public ResponseEntity<WaterData> createWaterData(@RequestBody WaterData waterData) {
        waterDao.saveData(waterData);
        return ResponseEntity.status(HttpStatus.CREATED).body(waterData);
    }

    // Electric Data API
    @GetMapping("/electric")
    public ResponseEntity<List<ElectricData>> getElectricData() {
        return ResponseEntity.ok(electricDao.findAllData());
    }

    @PostMapping("/electric")
    public ResponseEntity<ElectricData> createElectricData(@RequestBody ElectricData electricData) {
        electricDao.saveData(electricData);
        return ResponseEntity.status(HttpStatus.CREATED).body(electricData);
    }

    // Oil Data API
    @GetMapping("/oil")
    public ResponseEntity<List<OilData>> getOilData() {
        return ResponseEntity.ok(oilDao.findAllData());
    }

    @PostMapping("/oil")
    public ResponseEntity<OilData> createOilData(@RequestBody OilData oilData) {
        oilDao.saveData(oilData);
        return ResponseEntity.status(HttpStatus.CREATED).body(oilData);
    }

    // Rubbish Data API
    @GetMapping("/rubbish")
    public ResponseEntity<List<RubbishData>> getRubbishData() {
        return ResponseEntity.ok(rubbishDao.findAllData());
    }

    @PostMapping("/rubbish")
    public ResponseEntity<RubbishData> createRubbishData(@RequestBody RubbishData rubbishData) {
        rubbishDao.saveData(rubbishData);
        return ResponseEntity.status(HttpStatus.CREATED).body(rubbishData);
    }

    // Carbon Report API
    @GetMapping("/carbon/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getCarbonReport(@RequestParam String area) {
        double rubbishWeight = 0.0;
        double oilWeight = 0.0;
        double waterBill = 0.0;
        double electricBill = 0.0;
        int totalPopulation = 0;

        List<RubbishData> rubbishDataInArea = new ArrayList<>();
        List<OilData> oilDataInArea = new ArrayList<>();
        List<WaterData> waterDataInArea = new ArrayList<>();
        List<ElectricData> electricDataInArea = new ArrayList<>();

        for (User user : userDao.findAllUser()) {
            if (user.getHomeArea().equals(area)) {
                totalPopulation += user.getHouseHold();

                rubbishDataInArea.addAll(rubbishDao.findDataByUserId(user.getId()));
                oilDataInArea.addAll(oilDao.findDataByUserId(user.getId()));
                waterDataInArea.addAll(waterDao.findBillsByUserId(user.getId()));
                electricDataInArea.addAll(electricDao.findBillsByUserId(user.getId()));
            }
        }

        // Calculate totals
        for (RubbishData data : rubbishDataInArea) {
            rubbishWeight += data.getWeight();
        }
        for (OilData data : oilDataInArea) {
            oilWeight += data.getWeight();
        }
        for (WaterData data : waterDataInArea) {
            waterBill += data.getBillAmount();
        }
        for (ElectricData data : electricDataInArea) {
            electricBill += data.getBillAmount();
        }

        // Calculate carbon emission
        double carbonEmission = (rubbishWeight * 2.86) + 
                               (waterBill * 0.419) + 
                               (electricBill * 0.584);

        return ResponseEntity.ok(Map.of(
            "area", area,
            "totalPopulation", totalPopulation,
            "rubbishWeight", rubbishWeight,
            "oilWeight", oilWeight,
            "waterBill", waterBill,
            "electricBill", electricBill,
            "carbonEmission", carbonEmission
        ));
    }
}
