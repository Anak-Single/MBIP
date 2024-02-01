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
@Table(name = "ElectricUsage")
public class ElectricData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "electric_Total")
    private double electricTotal;

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
    public ElectricData(){}

    public ElectricData(double electric_Total, String billID, String billDate, double billAmount)
    {
        this.electricTotal = electric_Total;
        this.billID = billID;
        this.billDate = billDate;
        this.billAmount = billAmount;
        this.creationTime = LocalDate.parse(billDate);
        this.updateTime = LocalDateTime.now();
    }

    //Getter
    public double getElectricTotal()
    {
        return electricTotal;
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
    public void setElectricTotal(double electricTotal)
    {
        this.updateTime = LocalDateTime.now();
        this.electricTotal = electricTotal;
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
