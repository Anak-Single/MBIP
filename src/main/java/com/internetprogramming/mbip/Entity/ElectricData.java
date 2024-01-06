package com.internetprogramming.mbip.Entity;

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
@Table(name = "ElectricUsage")
public class ElectricData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_ID")
    private String billID;

    @Column(name = "bill_Date")
    private String billDate;

    @Column(name = "bill_Amount")
    private double billAmount;

    @Column(name = "update_Time")
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default Constructor
    public ElectricData(){}

    public ElectricData(String billID, String billDate, double billAmount)
    {
        this.billID = billID;
        this.billDate = billDate;
        this.billAmount = billAmount;
        this.updateTime = LocalDateTime.now();
    }

    //Getter
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

    //Setter
    public void setBillID(String billID)
    {
        this.billID = billID;
    }
    public void setBillDate(String billDate)
    {
        this.billDate = billDate;
    }
    public void setBillAmount(double billAmount)
    {
        this.billAmount = billAmount;
    }
    public void setUpdateTime()
    {
        this.updateTime = LocalDateTime.now();
    }
    
}
