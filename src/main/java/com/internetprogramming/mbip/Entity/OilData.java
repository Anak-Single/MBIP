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
@Table(name = "WasteOil")
public class OilData
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Category")
    private String category;

    @Column(name = "Weight")
    private double weight;

    @Column(name = "update_Time")
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    // Default Constructor
    public OilData(){}

    public OilData(String category, double weight)
    {
        this.category = category;
        this.weight = weight;
        this.updateTime = LocalDateTime.now();
    }

    //Getter
    public String getCategory()
    {
        return category;
    }
    public double getWeight() 
    {
        return weight;
    }
    
    //Setter
    public void setCategory(String category)
    {
        this.category = category;
    }
    public void setWeight(double weight)
    {
        this.weight = weight;
    }
    public void setUpdateTime()
    {
        this.updateTime = LocalDateTime.now();
    }
}
