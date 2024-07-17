package com.soraksh.sampleuserbase.service;

import com.soraksh.sampleuserbase.data.entity.ExternalProject;
import com.soraksh.sampleuserbase.data.repository.ExternalProjectRepository;
import com.soraksh.sampleuserbase.rest.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(ExternalProjectControllerManager.NAME)
public class ExternalProjectControllerManager {
    public static final String NAME = "ExternalProjectControllerManager";

    @Autowired
    protected ExternalProjectRepository repository;

    public Iterable<ExternalProject> findAll() {
        return repository.findAll();
    }

    public Iterable<ExternalProject> findAllByUserId(Long id) {
        return repository.findAllByUserId(id);
    }

    public ExternalProject findOne(String id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("External Project", id));
    }

    public ExternalProject create(ExternalProject project) {
        return repository.save(project);
    }

    public void deleteOne(String id) {
        repository.deleteById(id);
    }
}
