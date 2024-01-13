package com.internetprogramming.mbip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.internetprogramming.mbip.Entity.WaterData;
import com.internetprogramming.mbip.Entity.ElectricData;
import com.internetprogramming.mbip.Entity.OilData;
import com.internetprogramming.mbip.Entity.RubbishData;
import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Service.ElectricDao;
import com.internetprogramming.mbip.Service.OilDao;
import com.internetprogramming.mbip.Service.RubbishDao;
import com.internetprogramming.mbip.Service.UserDao;
import com.internetprogramming.mbip.Service.WaterDao;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/masukkanData")
public class MasukDataController {

    @Resource(name = "userDao")
	private UserDao userDao;

    @Resource(name = "waterDao")
	private WaterDao waterDao;

    @Resource(name = "electricDao")
	private ElectricDao electricDao;

    @Resource(name = "oilDao")
	private OilDao oilDao;

    @Resource(name = "rubbishDao")
	private RubbishDao rubbishDao;

    @GetMapping("")
    public String masukkanData() {
        //this.userDao = 
        return "MasukkanData/masukkanData";
    }

    @GetMapping("/pilihSisa")
    public String pilihSisa() {
        return "MasukkanData/PilihSisa";
    }

    @GetMapping("/pilihUtiliti")
    public String pilihUtiliti() {
        return "MasukkanData/PilihUtiliti";
    }

    @GetMapping("/air")
    public String Air(Model model)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        List <LocalDate> createtime = new ArrayList <LocalDate>();
        List <WaterData> waterdata = waterDao.findBillsByUserId(user.getId());

        if (waterdata != null && !waterdata.isEmpty()) {
            for (WaterData tempdata : waterdata) {
                if (!createtime.contains(tempdata.getCreationTime())) {
                    createtime.add(tempdata.getCreationTime());
                }
            }

            Collections.sort(createtime, Collections.reverseOrder());

            double [] pricePerDate = new double [createtime.size()];
            
            for (WaterData tempdataa : waterdata) {
                for (int count = 0; count < createtime.size(); count++) {
                    if (createtime.get(count).equals(tempdataa.getCreationTime())) {
                        pricePerDate[count] += tempdataa.getBillAmount();
                        System.out.println("\n\n" + pricePerDate[count] + "\n\n");
                    }
                }
            }

            int index = 0;

            model.addAttribute("pricePerDate", pricePerDate);
            model.addAttribute("createtime", createtime);
            model.addAttribute("index", index);

        }
        return "MasukkanData/Air";
    }

    @GetMapping("/elektrik")
    public String Elektrik(Model model)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        List <LocalDate> createtime = new ArrayList <LocalDate>();
        List <ElectricData> electricdata = electricDao.findBillsByUserId(user.getId());

        if (electricdata != null && !electricdata.isEmpty()) {
            for (ElectricData tempdata : electricdata) {
                if (!createtime.contains(tempdata.getCreationTime())) {
                    createtime.add(tempdata.getCreationTime());
                }
            }

            Collections.sort(createtime, Collections.reverseOrder());

            double [] pricePerDate = new double [createtime.size()];
            
            for (ElectricData tempdataa : electricdata) {
                for (int count = 0; count < createtime.size(); count++) {
                    if (createtime.get(count).equals(tempdataa.getCreationTime())) {
                        pricePerDate[count] += tempdataa.getBillAmount();
                        System.out.println("\n\n" + pricePerDate[count] + "\n\n");
                    }
                }
            }

            int index = 0;

            model.addAttribute("pricePerDate", pricePerDate);
            model.addAttribute("createtime", createtime);
            model.addAttribute("index", index);

        }
        return "MasukkanData/Elektrik";
    }

    @GetMapping("/sampah")
    public String Sampah(Model model)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        List <RubbishData> rubbishdata = rubbishDao.findDataByUserId(user.getId());
        double totalweight = 0;

        if (rubbishdata != null && !rubbishdata.isEmpty()) {
          
            for (RubbishData tempdata : rubbishdata) {
                totalweight += tempdata.getWeight();
            }

            model.addAttribute("totalweight", totalweight);

        }
        return "MasukkanData/Sampah";
    }

    @GetMapping("/minyak")
    public String Minyak(Model model)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        List <OilData> oildata = oilDao.findDataByUserId(user.getId());
        double totalweight = 0;

        if (oildata != null && !oildata.isEmpty()) {
          
            for (OilData tempdata : oildata) {
                totalweight += tempdata.getWeight();
            }

            model.addAttribute("totalweight", totalweight);

        }
        return "MasukkanData/Minyak";
    }

    @GetMapping("/muatNaikBilAir")
    public String muatNaikBilAir(Model model,
                                 @RequestParam("billID") String billID,
                                 @RequestParam("tarikhBill") String tarikhBill,
                                 @RequestParam("billAmount") Double billAmount)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        WaterData water = new WaterData(billID, tarikhBill, billAmount);
        water.setUser(user);
        waterDao.saveData(water);

        List <LocalDate> createtime = new ArrayList <LocalDate>();
        List<WaterData> waterdata = waterDao.findBillsByUserId(user.getId());

        if (waterdata != null && !waterdata.isEmpty()) {
            for (WaterData tempdata : waterdata) {
                if (!createtime.contains(tempdata.getCreationTime())) {
                    createtime.add(tempdata.getCreationTime());
                }
            }

            Collections.sort(createtime, Collections.reverseOrder());

            double [] pricePerDate = new double [createtime.size()];
            
            for (WaterData tempdataa : waterdata) {
                for (int count = 0; count < createtime.size(); count++) {
                    if (createtime.get(count).equals(tempdataa.getCreationTime())) {
                        pricePerDate[count] += tempdataa.getBillAmount();
                        System.out.println("\n\n" + pricePerDate[count] + "\n\n");
                    }
                }
            }

            int index = 0;

            model.addAttribute("pricePerDate", pricePerDate);
            model.addAttribute("createtime", createtime);
            model.addAttribute("index", index);

            return "MasukkanData/Air";
        }
        else
        {
            return "MasukkanData/masukkanData";
        }
        
    }

    @GetMapping("/muatNaikBilElektrik")
    public String muatNaikBilElektrik(Model model,
                                      @RequestParam("billID") String billID,
                                      @RequestParam("tarikhBill") String tarikhBill,
                                      @RequestParam("billAmount") Double billAmount)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        ElectricData electric = new ElectricData(billID, tarikhBill, billAmount);
        electric.setUser(user);

        electricDao.saveData(electric);

        List <LocalDate> createtime = new ArrayList <LocalDate>();
        List <ElectricData> electricdata = electricDao.findBillsByUserId(user.getId());

        if (electricdata != null && !electricdata.isEmpty()) {
            for (ElectricData tempdata : electricdata) {
                if (!createtime.contains(tempdata.getCreationTime())) {
                    createtime.add(tempdata.getCreationTime());
                }
            }

            Collections.sort(createtime, Collections.reverseOrder());

            double [] pricePerDate = new double [createtime.size()];
            
            for (ElectricData tempdataa : electricdata) {
                for (int count = 0; count < createtime.size(); count++) {
                    if (createtime.get(count).equals(tempdataa.getCreationTime())) {
                        pricePerDate[count] += tempdataa.getBillAmount();
                        System.out.println("\n\n" + pricePerDate[count] + "\n\n");
                    }
                }
            }

            int index = 0;

            model.addAttribute("pricePerDate", pricePerDate);
            model.addAttribute("createtime", createtime);
            model.addAttribute("index", index);

        }
        return "MasukkanData/Elektrik";
    }

    @GetMapping("/muatNaikDataSampah")
    public String muatNaikDataSampah(Model model,
                                     @RequestParam("Weight") Double weight,
                                     @RequestParam("Category") String category)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        RubbishData rubbish = new RubbishData(category, weight);
        rubbish.setUser(user);

        rubbishDao.saveData(rubbish);

        List <RubbishData> rubbishdata = rubbishDao.findDataByUserId(user.getId());
        double totalweight = 0;

        if (rubbishdata != null && !rubbishdata.isEmpty()) {
          
            for (RubbishData tempdata : rubbishdata) {
                System.out.println("\n\n" + totalweight + "\n\n");
                totalweight += tempdata.getWeight();
            }

            model.addAttribute("totalweight", totalweight);

        }
        return "MasukkanData/Sampah";
    }

    @GetMapping("/muatNaikDataMinyak")
    public String muatNaikDataMinyak(Model model,
                                     @RequestParam("Weight") Double weight)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUserName(username);

        OilData oil = new OilData(weight);
        oil.setUser(user);

        oilDao.saveData(oil);

        List <OilData> oildata = oilDao.findDataByUserId(user.getId());
        double totalweight = 0;

        if (oildata != null && !oildata.isEmpty()) {
          
            for (OilData tempdata : oildata) {
                totalweight += tempdata.getWeight();
            }

            model.addAttribute("totalweight", totalweight);

        }
        return "MasukkanData/Minyak";
    }
}