package com.soraksh.sampleuserbase.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soraksh.sampleuserbase.data.entity.User;
import com.soraksh.sampleuserbase.service.UserControllerManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @TestConfiguration
    static class UserControllerTestConfiguration {
        @Bean(UserControllerManager.NAME)
        public UserControllerManager userControllerManager() {
            return new UserControllerManager() {
                private static final User testUser;

                static {
                    testUser = new User();
                    testUser.setId(1L);
                }

                @Override
                public Iterable<User> findAll() {
                    return Collections.singletonList(testUser);
                }

                @Override
                public User findOne(Long id) {
                    return testUser;
                }

                @Override
                public User create(User user) {
                    return testUser;
                }

                @Override
                public User updateOne(Long id, User user) {
                    return testUser;
                }

                @Override
                public void deleteOne(Long id) {
                }
            };
        }
    }

    @Test
    public void getAllUsers_success() throws Exception {
        String uri = "/user";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User[] users = objectMapper.readValue(content, User[].class);
        assertTrue(users.length > 0);
    }

    @Test
    public void getSingleUser_success() throws Exception {
        String uri = "/user/1";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(content, User.class);
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    public void createUser_success() throws Exception {
        String uri = "/user";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .content("{\n" +
                                        "    \"name\": \"user-1\",\n" +
                                        "    \"email\": \"user@sample.sample\",\n" +
                                        "    \"password\": \"password\"\n" +
                                        "}")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(content, User.class);
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    public void updateUser_success() throws Exception {
        String uri = "/user/1";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .content("{\n" +
                                        "    \"name\": \"user-1-updated\",\n" +
                                        "    \"email\": \"user@sample.sample\",\n" +
                                        "    \"password\": \"password\"\n" +
                                        "}")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(content, User.class);
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    public void deleteUser_success() throws Exception {
        String uri = "/user/1";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
