package com.soraksh.sampleuserbase.data;

import com.soraksh.sampleuserbase.data.entity.User;
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
public class UserRepositoryTest {
    private User testUser;

    @Autowired
    UserRepository repository;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setName("user");
        testUser.setPassword("password");
        testUser.setEmail("sample@sample.sample");
        repository.save(testUser);
    }

    @AfterEach
    public void clear() {
        repository.deleteAll();
    }

    @Test
    void savedUser_canBeFound() {
        User savedUser = repository.findById(testUser.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals(testUser.getName(), savedUser.getName());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        assertEquals(testUser.getPassword(), savedUser.getPassword());
    }

    @Test
    void savedUser_canBeFound_all() {
        List<User> savedUsers = repository.findAll();
        assertNotNull(savedUsers);
        assertEquals(1, savedUsers.size());
        User savedUser = savedUsers.get(0);
        assertEquals(testUser.getName(), savedUser.getName());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        assertEquals(testUser.getPassword(), savedUser.getPassword());
    }

    @Test
    void savedUser_canBeUpdated() {
        testUser.setName("updatedName");
        repository.save(testUser);

        User updatedUser = repository.findById(testUser.getId()).orElse(null);

        assertNotNull(updatedUser);
        assertEquals("updatedName", updatedUser.getName());
    }
}
