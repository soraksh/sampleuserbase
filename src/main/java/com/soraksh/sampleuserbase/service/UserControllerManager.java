package com.soraksh.sampleuserbase.service;

import com.soraksh.sampleuserbase.data.entity.User;
import com.soraksh.sampleuserbase.data.repository.UserRepository;
import com.soraksh.sampleuserbase.rest.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(UserControllerManager.NAME)
public class UserControllerManager {
    public static final String NAME = "UserControllerManager";

    @Autowired
    protected UserRepository repository;

    public Iterable<User> findAll() {
        return repository.findAll();
    }

    public User findOne(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User", id));
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User updateOne(Long id, User user) {
        User foundUser = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User", id));
        foundUser.setName(user.getName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser = repository.save(foundUser);
        return foundUser;
    }

    public void deleteOne(Long id) {
        repository.deleteById(id);
    }
}
