package com.soraksh.sampleuserbase.data.repository;

import com.soraksh.sampleuserbase.data.entity.ExternalProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalProjectRepository  extends JpaRepository<ExternalProject, String> {
    Optional<ExternalProject> findById(String id);

    void deleteById(String id);

    List<ExternalProject> findAllByUserId(Long userId);
}
