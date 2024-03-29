package com.internetprogramming.mbip.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WaterUsage")
public class WaterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "water_Total")
    private double waterTotal;

    @Column(name = "bill_ID")
    private String billID;

    @Column(name = "bill_Date")
    private String billDate;

    @Column(name = "bill_Amount")
    private double billAmount;

    @Column(name = "update_Time")
    private LocalDateTime updateTime;

    @Column(name = "creation_time")
    private LocalDate creationTime;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    // Default Constructor
    public WaterData(){}

    public WaterData(double waterTotal, String billID, String billDate, double billAmount)
    {
        this.waterTotal = waterTotal;
        this.billID = billID;
        this.billDate = billDate;
        this.billAmount = billAmount;
        this.creationTime = LocalDate.parse(billDate);
        this.updateTime = LocalDateTime.now();
    }

    //Getter
    public double getWaterTotal()
    {
        return waterTotal;
    }
    public String getBillID()
    {
        return billID;
    }
    public String getBillDate()
    {
        return billDate;
    }
    public double getBillAmount()
    {
        return billAmount;
    }
    public LocalDateTime getUpdateTime()
    {
        return updateTime;
    }
    public LocalDate getCreationTime()
    {
        return creationTime;
    }

    //Setter
    public void setUser(User user)
    {
        this.user = user;
    }
    public void setWaterTotal(double waterTotal)
    {
        this.updateTime = LocalDateTime.now();
        this.waterTotal = waterTotal;
    }
    public void setBillID(String billID)
    {
        this.updateTime = LocalDateTime.now();
        this.billID = billID;
    }
    public void setBillDate(String billDate)
    {
        this.updateTime = LocalDateTime.now();
        this.billDate = billDate;
    }
    public void setBillAmount(double billAmount)
    {
        this.updateTime = LocalDateTime.now();
        this.billAmount = billAmount;
    }
}
