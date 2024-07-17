package com.soraksh.sampleuserbase.rest.controller;

import com.soraksh.sampleuserbase.data.entity.ExternalProject;
import com.soraksh.sampleuserbase.service.ExternalProjectControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/externalproject")
public class ExternalProjectController {
    @Autowired
    protected ExternalProjectControllerManager manager;

    @GetMapping()
    protected Iterable<ExternalProject> findAll() {
        return manager.findAll();
    }

    @PostMapping()
    protected ExternalProject create(@RequestBody ExternalProject project) {
        return manager.create(project);
    }

    @GetMapping("/{id}")
    protected ExternalProject findOne(@PathVariable String id) {
        return manager.findOne(id);
    }

    @GetMapping("/byuser/{userId}")
    protected Iterable<ExternalProject> findAllByUserId(@PathVariable Long userId) {
        return manager.findAllByUserId(userId);
    }

    @DeleteMapping("/{id}")
    protected void delete(@PathVariable String id) {
        manager.deleteOne(id);
    }
}
