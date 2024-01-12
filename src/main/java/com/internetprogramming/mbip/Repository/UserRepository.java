package com.internetprogramming.mbip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internetprogramming.mbip.Entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository <User, Long>{

    User findByUserName(String username);
}

