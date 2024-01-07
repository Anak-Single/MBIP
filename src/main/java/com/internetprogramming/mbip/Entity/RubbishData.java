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
@Table(name = "WasteRubbish")
public class RubbishData
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Type")
    private String type;

    @Column(name = "Weight")
    private double weight;

    @Column(name = "update_Time")
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    // Default Constructor
    public RubbishData(){}

    public RubbishData(String type, double weight)
    {
        this.type = type;
        this.weight = weight;
        this.updateTime = LocalDateTime.now();
    }

    //Getter
    public String getType()
    {
        return type;
    }
    public double getWeight() 
    {
        return weight;
    }
    
    //Setter
    public void setType(String type)
    {
        this.type = type;
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
