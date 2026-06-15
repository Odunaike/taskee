package com.naike.taskee;

import com.naike.taskee.domain.TaskStatus;
import com.naike.taskee.domain.dto.TaskDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
   @Autowired
   private ObjectMapper objectMapper;

    @Test
    public void testThatCreateTaskWorks() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .userId(102)
                .title("Task1")
                .build();
       mockMvc.perform(
               MockMvcRequestBuilders.post("/tasks")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(
                               objectMapper.writeValueAsString(taskDto)
                       )
       ).andExpect(
               MockMvcResultMatchers.status().isCreated()
       );
    }
    @Test
    public void testThatTaskCreatedHasDefaultPendingStatus() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .userId(102)
                .title("Task1")
                .build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data.status").value(TaskStatus.PENDING.name())
        );
    }

    @Test
    public void testThatFindAllTasksWorks() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data", hasSize(4))
        );
    }

    @Test
    public void testThatFindTaskByIdWorks() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/3")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCommpleteTaskWorks() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/tasks/3")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.data.status").value(TaskStatus.COMPLETED.name())
        );
    }

    @Test
    public void testThatDeleteTaskWorks() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/3")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


}
