package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.config.ProfileConfig;
import com.smartcity.dto.TransactionDto;
import com.smartcity.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = {ProfileConfig.class})
class TransactionControllerTest {
    @MockBean
    private TransactionService transService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private TransactionDto transDto;
    private final ObjectMapper objMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        transDto = new TransactionDto(1L, 1L,
                5000L, 3000L,
                null, null);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        Mockito.when(transService.create(transDto)).thenReturn(transDto);
        String json = objMapper.writeValueAsString(transDto);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(transDto.getId()))
                .andExpect(jsonPath("taskId").value(transDto.getTaskId()))
                .andExpect(jsonPath("currentBudget").value(transDto.getCurrentBudget()))
                .andExpect(jsonPath("transactionBudget").value(transDto.getTransactionBudget()));
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        TransactionDto updatedTrans = new TransactionDto(1L, 1L, 400L, 200L, null, null);
        Mockito.when(transService.update(updatedTrans)).thenReturn(updatedTrans);
        String json = objMapper.writeValueAsString(updatedTrans);
        mockMvc.perform(put("/transactions/" + updatedTrans.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedTrans.getId()))
                .andExpect(jsonPath("taskId").value(updatedTrans.getTaskId()))
                .andExpect(jsonPath("currentBudget").value(updatedTrans.getCurrentBudget()))
                .andExpect(jsonPath("transactionBudget").value(updatedTrans.getTransactionBudget()));
        assertEquals(transDto.getId(), updatedTrans.getId());
        assertNotEquals(transDto.getCurrentBudget(), updatedTrans.getCurrentBudget());
        assertNotEquals(transDto.getTransactionBudget(), updatedTrans.getTransactionBudget());
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        Mockito.when(transService.delete(transDto.getId())).thenReturn(true);
        mockMvc.perform(delete("/transactions/" + transDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdTransaction() throws Exception {
        Mockito.when(transService.findById(transDto.getId())).thenReturn(transDto);
        mockMvc.perform(get("/transactions/" + transDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(transDto.getId()))
                .andExpect(jsonPath("taskId").value(transDto.getTaskId()))
                .andExpect(jsonPath("currentBudget").value(transDto.getCurrentBudget()))
                .andExpect(jsonPath("transactionBudget").value(transDto.getTransactionBudget()));
    }

    @Test
    public void testFindByTaskIdTransaction() throws Exception {
        List<TransactionDto> startList = Collections.singletonList(transDto);
        Mockito.when(transService.findByTaskId(transDto.getTaskId())).thenReturn(startList);
        final MvcResult result = mockMvc.perform(get("/transactions/findByTask?id=" + transDto.getTaskId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        TransactionDto[] arrTransaction = objMapper
                .readValue(result.getResponse().getContentAsString(), TransactionDto[].class);
        List<TransactionDto> resList = Arrays.asList(arrTransaction);
        assertEquals(startList, resList);
    }

}