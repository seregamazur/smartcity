package com.smartcity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.dto.TransactionDto;
import com.smartcity.service.TransactionServiceImpl;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    private TransactionServiceImpl transService;

    @InjectMocks
    private TransactionController controller;

    private MockMvc mockMvc;
    private TransactionDto transDto;
    private final ObjectMapper objMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        transDto = new TransactionDto(1L, 1L,
                5000L, 3000L,
                null, null);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        doReturn(transDto).when(transService).create(transDto);
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
        doReturn(updatedTrans).when(transService).update(updatedTrans);
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
        doReturn(true).when(transService).delete(transDto.getId());
        mockMvc.perform(delete("/transactions/" + transDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdTransaction() throws Exception {
        doReturn(transDto).when(transService).findById(transDto.getId());
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
        doReturn(startList).when(transService).findByTaskId(transDto.getTaskId());
        final MvcResult result = mockMvc.perform(get("/transactions/findByTask?id=" + transDto.getTaskId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        TransactionDto[] arrTransaction = objMapper
                .readValue(result.getResponse().getContentAsString(), TransactionDto[].class);
        List<TransactionDto> resList = Arrays.asList(arrTransaction);
        assertEquals(startList, resList);
    }

}