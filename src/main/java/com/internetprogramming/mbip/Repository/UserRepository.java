package com.internetprogramming.mbip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.internetprogramming.mbip.Entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{}

