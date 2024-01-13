package com.internetprogramming.mbip.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
    
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "age")
    private int age;
    
    @Column(name = "homeaddress")
    private String homeAddress;
    
    @Column(name = "homearea")
    private String homeArea;

    @Column(name = "houseHold")
    private int houseHold;

    @Column(name = "creation_time")
    private LocalDate creationTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <ElectricData> electricData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <WaterData> waterData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <OilData> oilData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List <RubbishData> rubbishData;

    // Default Constructor
    public User(){}

    // Parameterized Constructor
    public User(String userName, String password, String fullName, int age, String homeAddress, HomeArea homeArea) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.homeAddress = homeAddress;
        this.houseHold = 1;
        this.homeArea = homeArea.getDisplayName();
        this.creationTime = LocalDate.now();
    }

    // Getters
    public long getId()
	{
        return id;
    }
    public String getUserName()
	{
        return userName;
    }
    public String getPassword()
	{
        return password;
    }
    public String getFullName()
	{
        return fullName;
    }
    public int getAge()
	{
        return age;
    }
    public String getHomeAddress()
	{
        return homeAddress;
    }
    public String getHomeArea()
	{
        return homeArea;
    }
    public int getHouseHold()
	{
        return houseHold;
    }
    public LocalDate getCreationTime()
    {
        return creationTime;
    }
    public String getAddress()
	{
        return homeAddress + " " + homeArea;
    }
	
	//Setter
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
    public void setAge(int age)
	{
		this.age = age;
	}
	public void setHomeAddress(String homeAddress)
	{
		this.homeAddress = homeAddress;
	}
	public void setHomeArea(String homeArea)
	{
		this.homeArea = homeArea;
	}
    public void setHouseHold(int houseHold)
	{
        this.houseHold = houseHold;
    }
}

