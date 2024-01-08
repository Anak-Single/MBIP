package com.internetprogramming.mbip.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.internetprogramming.mbip.Entity.User;
import com.internetprogramming.mbip.Repository.UserRepository;

@Service
public class UserDao implements UserDetailsService{

    private UserRepository repository;

     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    public UserDao(UserRepository repository)
    {
        this.repository = repository;
    }
    
    public User findUserById(Long id) {
        Optional <User> optUser = repository.findById(id);
        User user = optUser.orElse(null);
        return user;
    }

    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public void saveUser(User user) {
        repository.save(user);
    }

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

    public void deleteUser(long id) {

        Optional <User> optUser = repository.findById(id);
        User userToDelete = optUser.orElse(null);

        if (userToDelete != null) {
            repository.delete(userToDelete);
        }
    }

    public List <User> findAllUser() {

        List <User> existingUser = repository.findAll();
        return existingUser;
    }
}