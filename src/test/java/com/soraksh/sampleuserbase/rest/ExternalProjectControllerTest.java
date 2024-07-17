package com.soraksh.sampleuserbase.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soraksh.sampleuserbase.data.entity.ExternalProject;
import com.soraksh.sampleuserbase.service.ExternalProjectControllerManager;
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
public class ExternalProjectControllerTest {
    @Autowired
    private MockMvc mvc;

    @TestConfiguration
    static class ExternalProjectTestConfiguration {
        @Bean(ExternalProjectControllerManager.NAME)
        public ExternalProjectControllerManager externalProjectControllerManager() {
            return new ExternalProjectControllerManager() {
                private static final ExternalProject testProject;

                static {
                    testProject = new ExternalProject();
                    testProject.setId("test_project_01");
                }

                @Override
                public Iterable<ExternalProject> findAll() {
                    return Collections.singletonList(testProject);
                }

                @Override
                public Iterable<ExternalProject> findAllByUserId(Long userId) {
                    return Collections.singletonList(testProject);
                }

                @Override
                public ExternalProject findOne(String id) {
                    return testProject;
                }

                @Override
                public ExternalProject create(ExternalProject project) {
                    return testProject;
                }

                @Override
                public void deleteOne(String id) {
                }
            };
        }
    }

    @Test
    public void getAllExternalProjects_success() throws Exception {
        String uri = "/externalproject";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalProject[] projects = objectMapper.readValue(content, ExternalProject[].class);
        assertTrue(projects.length > 0);
    }

    @Test
    public void getExternalProjectsByUser_success() throws Exception {
        String uri = "/externalproject/byuser/1";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalProject[] projects = objectMapper.readValue(content, ExternalProject[].class);
        assertTrue(projects.length > 0);
    }

    @Test
    public void getSingleExternalProject_success() throws Exception {
        String uri = "/externalproject/test_project_01";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalProject project = objectMapper.readValue(content, ExternalProject.class);
        assertNotNull(project);
        assertEquals("test_project_01", project.getId());
    }

    @Test
    public void createExternalProject_success() throws Exception {
        String uri = "/externalproject";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .content("{\n" +
                                        "    \"id\": \"project-1\",\n" +
                                        "    \"name\": \"Sample project\",\n" +
                                        "    \"user\": {\n" +
                                        "        \"id\":\"1\"\n" +
                                        "        }\n" +
                                        "}")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalProject project = objectMapper.readValue(content, ExternalProject.class);
        assertNotNull(project);
        assertEquals("test_project_01", project.getId());
    }

    @Test
    public void deleteExternalProject_success() throws Exception {
        String uri = "/externalproject/test_project_01";
        MvcResult mvcResult = mvc.perform(
                        MockMvcRequestBuilders
                                .delete(uri))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
