package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.MedicalRecordController;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.service.MedicalRecordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private ObjectMapper objectMapper;

    private MedicalRecordDTO medDTO;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        medDTO = new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));
    }

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given a MedicalRecord, when POST request, then return Created status")
    public void givenAMedicalRecord_whenPostRequest_thenReturnCreatedStatus() throws Exception {
        when(medicalRecordService.createMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(any(MedicalRecordDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medDTO)))
                .andExpect(status().isCreated());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given a MedicalRecord with incomplete ID, when POST request, then return BadRequest status")
    public void givenAMedicalRecordWithIncompleteID_whenPostRequest_thenReturnBadRequestStatus() throws Exception {
        MedicalRecordDTO medDTO = new MedicalRecordDTO("", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medDTO)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).createMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given an empty body request, when POST request, then return BadRequest status")
    public void givenAnEmptyBodyRequest_whenPostRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).createMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given a MedicalRecord to update, when PUT request, then return Ok status")
    public void givenAMedicalRecordToUpdate_whenPutRequest_thenReturnOkStatus() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(any(MedicalRecordDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medDTO)))
                .andExpect(status().isOk());

        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given a MedicalRecord with incomplete ID, when PUT request, then return BadRequest status")
    public void givenAMedicalRecordWithIncompleteID_whenPutRequest_thenReturnBadRequestStatus() throws Exception {
        MedicalRecordDTO medDTO = new MedicalRecordDTO("", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medDTO)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).updateMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given empty body request, when PUT request, then return BadRequest status")
    public void givenEmptyBodyRequest_whenPutRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).updateMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("DELETE-MedicalRecord")
    @DisplayName("Given valid Id param, when DELETE request, then return OK status")
    public void givenValidIdParam_whenDeleteRequest_thenReturnOkStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .param("firstName", "John")
                .param("lastName", "Boyd"))
                .andExpect(status().isOk());

        verify(medicalRecordService).deleteMedicalRecord(anyString(), anyString());
    }

    @Test
    @Tag("DELETE-MedicalRecord")
    @DisplayName("Given incomplete Id param, when DELETE request, then return BadRequest status")
    public void givenIncompleteIdParam_whenDeleteRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .param("firstName", "")
                .param("lastName", "Boyd"))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).deleteMedicalRecord(anyString(), anyString());
    }

    @Test
    @Tag("GET-MedicalRecord")
    @DisplayName("Given valid Id param, when GET request, then return OK status")
    public void givenValidIdParam_whenGetRequest_thenReturnOkStatus() throws Exception {
        when(medicalRecordService.getMedicalRecordById(anyString(), anyString())).thenReturn(medDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord")
                .param("firstName", "John")
                .param("lastName", "Boyd"))
                .andExpect(status().isOk());

        verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
    }

    @Test
    @Tag("GET-MedicalRecord")
    @DisplayName("Given incomplete Id param, when GET request, then return BadRequest status")
    public void givenIncompleteIdParam_whenGetRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord")
                .param("firstName", "John")
                .param("lastName", ""))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).getMedicalRecordById(anyString(), anyString());
    }
}
