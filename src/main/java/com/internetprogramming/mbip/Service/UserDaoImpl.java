package com.internetprogramming.mbip.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Repository.UserRepository;

@Service
public class UserDaoImpl implements UserDao{

    private UserRepository repository;

    public UserDaoImpl(UserRepository repository)
    {
        super();
        this.repository = repository;
    }
    
    @Override
    public User findUserById(Long id) {
        Optional <User> optUser = repository.findById(id);
        User user = optUser.orElse(null);
        return user;
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public void updateUser(Long id, User user) {

        Optional <User> optUser = repository.findById(id);
        User existingUser = optUser.orElse(null);

        if (existingUser != null) {
            existingUser.setFullName(user.getFullName());
            existingUser.setUserName(user.getUserName());
            existingUser.setPassword(user.getPassword());
            existingUser.setAge(user.getAge());
            existingUser.setHomeAddress(user.getHomeAddress());
            existingUser.setHomeArea(user.getHomeArea());
            
            repository.saveAndFlush(existingUser);
        }
    }

    @Override
    public void deleteUser(long id) {

        Optional <User> optUser = repository.findById(id);
        User userToDelete = optUser.orElse(null);

        if (userToDelete != null) {
            repository.delete(userToDelete);
        }
    }

    @Override
    public List <User> findAllUser() {

        List <User> existingUser = repository.findAll();
        return existingUser;
    }
}