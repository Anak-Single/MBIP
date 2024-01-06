package com.internetprogramming.mbip.Service;

import com.internetprogramming.mbip.Entity.User;

import java.util.List;

public interface UserDao
{
    public User findUserById(Long id);

    public void saveUser(User user);

    public void updateUser(Long id, User user);

    public void deleteUser(long id);

    public List<User> findAllUser();
}

