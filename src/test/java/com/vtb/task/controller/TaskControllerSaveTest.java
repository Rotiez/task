package com.vtb.task.controller;

import com.vtb.task.entity.Task;
import com.vtb.task.repository.AuditLogRepository;
import com.vtb.task.service.TaskServiceSave;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TaskControllerSave.class)
class TaskControllerSaveTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    public TaskServiceSave taskServiceSave;
    @MockBean
    public AuditLogRepository auditRepository;


   /* @Test
    public void saveTask() throws Exception {
        Task task = new Task();

        task.setName("Task1");
        task.setStatus("Waiting");
        task.setType("Developing");
        task.setOwner("Mikhail");
        task.setExecutor("Mikhail");

        //String json = objectMapper;

        mockMvc.perform(post("/tasks"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.status").isString())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(jsonPath("$[*].status", containsInAnyOrder("Waiting", "In process", "Closed")))
                .andExpect(jsonPath("$[*].status", containsInAnyOrder("Developing", "Analytics")));
    }*/


}