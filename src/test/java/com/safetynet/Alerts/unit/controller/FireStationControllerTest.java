package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.FireStationController;
import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.service.FireStationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private ObjectMapper objectMapper;

    private FireStationDTO fireDTO;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        fireDTO = new FireStationDTO("29 15th St", 2);
    }

    @Test
    @Tag("POST-FireStation")
    @DisplayName("Given a FireStation to add, when createFireStation, then return Created status")
    public void givenAFireStationToAdd_whenCreateFireStation_thenReturnCreatedStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(fireDTO);

        when(fireStationService.createFireStation(any(FireStationDTO.class))).thenReturn(any(FireStation.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated());

        verify(fireStationService).createFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("POST-FireStation")
    @DisplayName("Given a null FireStation, when createFireStation, then return BadRequest status")
    public void givenANullFireStation_whenCreateFireStation_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).createFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("PUT-FireStation")
    @DisplayName("Given a FireStation to update, when updateFireStation, then return Ok status")
    public void givenAFireStationToUpdate_whenUpdateFireStation_thenReturnOkStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(fireDTO);

        when(fireStationService.updateFireStation(any(FireStationDTO.class))).thenReturn(any(FireStation.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(fireStationService).updateFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("PUT-FireStation")
    @DisplayName("Given a null FireStation, when updateFireStation, then return BadRequest status")
    public void givenANullFireStation_whenUpdateFireStation_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).updateFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("DELETE-FireStation")
    @DisplayName("Given a FireStation to delete, when deleteFireStation, then return Ok status")
    public void givenAFireStationToDelete_whenDeleteFireStation_thenReturnOkStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(fireDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(fireStationService).deleteFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("DELETE-FireStation")
    @DisplayName("Given a null FireStation, when deleteFireStation, then return BadRequest status")
    public void givenANullFireStation_whenDeleteFireStation_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).deleteFireStation(any(FireStationDTO.class));
    }
}
