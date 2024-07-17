package com.soraksh.sampleuserbase.rest.controller;

import com.soraksh.sampleuserbase.data.entity.User;
import com.soraksh.sampleuserbase.service.UserControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    protected UserControllerManager manager;

    @GetMapping()
    protected Iterable<User> findAll() {
        return manager.findAll();
    }

    @PostMapping()
    protected User create(@RequestBody User user) {
        return manager.create(user);
    }

    @GetMapping("/{id}")
    protected User findOne(@PathVariable Long id) {
        return manager.findOne(id);
    }

    @PutMapping("/{id}")
    protected User updateOne(@PathVariable Long id,
                             @RequestBody User user) {
        return manager.updateOne(id, user);
    }

    @DeleteMapping("/{id}")
    protected void delete(@PathVariable Long id) {
        manager.deleteOne(id);
    }
}
