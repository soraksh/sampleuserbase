package com.soraksh.sampleuserbase.data;

import com.soraksh.sampleuserbase.data.entity.ExternalProject;
import com.soraksh.sampleuserbase.data.entity.User;
import com.soraksh.sampleuserbase.data.repository.ExternalProjectRepository;
import com.soraksh.sampleuserbase.data.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class ExternalProjectRepositoryTest {
    private User testUser;
    private ExternalProject testProject;

    @Autowired
    ExternalProjectRepository repository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setName("user");
        testUser.setPassword("password");
        testUser.setEmail("sample@sample.sample");
        userRepository.save(testUser);

        testProject = new ExternalProject();
        testProject.setId("project_01");
        testProject.setName("project");
        testProject.setUser(testUser);
        repository.save(testProject);
    }

    @AfterEach
    public void clear() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void savedProject_canBeFound() {
        ExternalProject savedProject = repository.findById(testProject.getId()).orElse(null);
        assertNotNull(savedProject);
        assertEquals(testProject.getName(), savedProject.getName());
        User savedUser = savedProject.getUser();
        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
    }

    @Test
    void savedProject_canBeFound_all() {
        List<ExternalProject> savedProjects = repository.findAll();
        assertNotNull(savedProjects);
        assertEquals(1, savedProjects.size());
        ExternalProject savedProject = savedProjects.get(0);
        assertNotNull(savedProject);
        assertEquals(testProject.getName(), savedProject.getName());
        User savedUser = savedProject.getUser();
        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
    }

    @Test
    void savedProject_canBeFound_byUser() {
        List<ExternalProject> savedProjects = repository.findAllByUserId(testUser.getId());
        assertNotNull(savedProjects);
        assertEquals(1, savedProjects.size());
        ExternalProject savedProject = savedProjects.get(0);
        assertNotNull(savedProject);
        assertEquals(testProject.getName(), savedProject.getName());
        User savedUser = savedProject.getUser();
        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
    }
}
