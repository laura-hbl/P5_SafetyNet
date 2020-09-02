package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.MedicalRecordController;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.model.MedicalRecord;
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
    @DisplayName("Given a MedicalRecord to add, when createMedicalRecord, then return Created status")
    public void givenAMedicalRecordToAdd_whenCreateMedicalRecord_thenReturnCreatedStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(medDTO);

        when(medicalRecordService.createMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(any(MedicalRecord.class));

        mockMvc.perform(MockMvcRequestBuilders.post( "/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given a null MedicalRecord, when createMedicalRecord, then return BadRequest status")
    public void givenANullMedicalRecord_whenCreateMedicalRecord_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).createMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given a MedicalRecord to update, when updateMedicalRecord, then return Ok status")
    public void givenAMedicalRecordToUpdate_whenUpdateMedicalRecord_thenReturnOkStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(medDTO);
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(any(MedicalRecord.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given a null MedicalRecord, when updateMedicalRecord, then return BadRequest status")
    public void givenANullMedicalRecord_whenUpdateMedicalRecord_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).updateMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("DELETE-MedicalRecord")
    @DisplayName("Given a MedicalRecord to delete, when deleteMedicalRecord, then return Ok status")
    public void givenAMedicalRecordToDelete_whenDeleteMedicalRecord_thenReturnOkStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(medDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(medicalRecordService).deleteMedicalRecord(any(MedicalRecordDTO.class));
    }

    @Test
    @Tag("DELETE-MedicalRecord")
    @DisplayName("Given a null MedicalRecord, when deleteMedicalRecord, then return BadRequest status")
    public void givenANullMedicalRecord_whenDeleteMedicalRecord_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService, times(0)).deleteMedicalRecord(any(MedicalRecordDTO.class));
    }
}
