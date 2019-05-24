package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smartcity.config.ProfileConfig;
import com.smartcity.dto.TaskDto;
import com.smartcity.mapperDto.TaskDtoMapper;
import com.smartcity.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
class TaskControllerTest {
    private TaskDto task = new TaskDto(2L, "Santa", "Task for Santa",
            LocalDateTime.now(), "TODO",
            1000L, 1000L, LocalDateTime.now(),
            LocalDateTime.now(), 1L);

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Autowired
    TaskDtoMapper taskDtoMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(taskController)
                .build();
    }

    @Test
    public void testCreateTask() throws Exception {
        doReturn(task).when(taskService).create(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String resultJson = objectMapper.writeValueAsString(task);
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resultJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(task.getId()))
                .andExpect(jsonPath("title").value(task.getTitle()))
                .andExpect(jsonPath("description").value(task.getDescription()))
                .andExpect(jsonPath("taskStatus").value(task.getTaskStatus()))
                .andExpect(jsonPath("budget").value(task.getBudget()))
                .andExpect(jsonPath("approvedBudget").value(task.getApprovedBudget()))
                .andExpect(jsonPath("usersOrganizationsId").value(task.getUsersOrganizationsId()));
    }

    @Test
    public void testFindTaskById() throws Exception {
        doReturn(task).when(taskService).findById(task.getId());

        mockMvc.perform(get("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(task.getId()))
                .andExpect(jsonPath("title").value(task.getTitle()))
                .andExpect(jsonPath("description").value(task.getDescription()))
                .andExpect(jsonPath("taskStatus").value(task.getTaskStatus()))
                .andExpect(jsonPath("budget").value(task.getBudget()))
                .andExpect(jsonPath("approvedBudget").value(task.getApprovedBudget()))
                .andExpect(jsonPath("usersOrganizationsId").value(task.getUsersOrganizationsId()));
    }

    @Test
    public void testDeleteTask() throws Exception {
        doReturn(true).when(taskService).delete(task.getId());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/" + task.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateTask() throws Exception {
        doReturn(task).when(taskService).update(task);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String resultJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resultJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(task.getId()))
                .andExpect(jsonPath("title").value(task.getTitle()))
                .andExpect(jsonPath("description").value(task.getDescription()))
                .andExpect(jsonPath("taskStatus").value(task.getTaskStatus()))
                .andExpect(jsonPath("budget").value(task.getBudget()))
                .andExpect(jsonPath("approvedBudget").value(task.getApprovedBudget()))
                .andExpect(jsonPath("usersOrganizationsId").value(task.getUsersOrganizationsId()));
    }

    @Test
    public void testFindTaskByOrganizationId() throws Exception {
        List<TaskDto> expectedTaskDtoList = Arrays.asList(task);
        doReturn(expectedTaskDtoList).when(taskService).findByOrganizationId(task.getUsersOrganizationsId());

        MvcResult mvcResult = mockMvc.perform(get("/tasks/findByOrg")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("findByOrganizationId", task.getUsersOrganizationsId().toString()))
                .andReturn();

        assertEquals(expectedTaskDtoList.toString(), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testFindTaskByUserId() throws Exception {
        List<TaskDto> expectedTaskDtoList = Arrays.asList(task);
        doReturn(expectedTaskDtoList).when(taskService).findByUserId(task.getUsersOrganizationsId());

        MvcResult mvcResult = mockMvc.perform(get("/tasks/findByUser")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("findByUserId", task.getUsersOrganizationsId().toString()))
                .andReturn();

        assertEquals(expectedTaskDtoList.toString(), mvcResult.getResponse().getContentAsString());
    }
}