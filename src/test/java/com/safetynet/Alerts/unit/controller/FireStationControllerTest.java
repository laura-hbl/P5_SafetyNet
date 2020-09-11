package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.FireStationController;
import com.safetynet.Alerts.dto.FireStationDTO;
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
    @DisplayName("Given a FireStation to add, when POST request, then return Created status")
    public void givenAFireStationToAdd_whenPostRequest_thenReturnCreatedStatus() throws Exception {
        when(fireStationService.createFireStation(any(FireStationDTO.class))).thenReturn(any(FireStationDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireDTO)))
                .andExpect(status().isCreated());

        verify(fireStationService).createFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("POST-FireStation")
    @DisplayName("Given a FireStation with missing address, when POST request, then return BadRequest status")
    public void givenAFireStationWithMissingAddress_whenPostRequest_thenReturnBadRequestStatus() throws Exception {
        FireStationDTO fireDTO = new FireStationDTO("", 2);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireDTO)))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).createFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("POST-FireStation")
    @DisplayName("Given an empty body request, when POST request, then return BadRequest status")
    public void givenAnEmptyBodyRequest_whenPostRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).createFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("PUT-FireStation")
    @DisplayName("Given a FireStation to update, when PUT request, then return Ok status")
    public void givenAFireStationToUpdate_whenPutRequest_thenReturnOkStatus() throws Exception {
        when(fireStationService.updateFireStation(any(FireStationDTO.class))).thenReturn(any(FireStationDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireDTO)))
                .andExpect(status().isOk());

        verify(fireStationService).updateFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("PUT-FireStation")
    @DisplayName("Given a FireStation with incomplete key ID, when PUT request, then return BadRequest status")
    public void givenAFireStationWithIncompleteKeyID_whenPutRequest_thenReturnBadRequestStatus() throws Exception {
        FireStationDTO fireDTO = new FireStationDTO("", 2);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fireDTO)))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).updateFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("PUT-FireStation")
    @DisplayName("Given empty body request, when PUT request, then return BadRequest status")
    public void givenEmptyBodyRequest_whenPutRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).updateFireStation(any(FireStationDTO.class));
    }

    @Test
    @Tag("DELETE-FireStation")
    @DisplayName("Given a valid FireStation key ID, when DELETE request, then return OK status")
    public void givenValidIdKeyId_whenDeleteRequest_thenReturnOkStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .param("address", "29 15th St")
                .param("station", "2"))
                .andExpect(status().isOk());

        verify(fireStationService).deleteFireStation(anyString(), anyInt());
    }

    @Test
    @Tag("DELETE-FireStation")
    @DisplayName("Given incomplete Key ID, when DELETE request, then return BadRequest status")
    public void givenIncompleteKeyId_whenDeleteRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                .param("address", "")
                .param("station", "2"))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).deleteFireStation(anyString(), anyInt());
    }

    @Test
    @Tag("GET-FireStation")
    @DisplayName("Given valid key ID, when GET request, then return OK status")
    public void givenValidIdParam_whenGetRequest_thenReturnOkStatus() throws Exception {
        when(fireStationService.getFireStationByKeyId(anyString(), anyInt())).thenReturn(fireDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/fireStation")
                .param("address", "29 15th St")
                .param("station", "2"))
                .andExpect(status().isOk());

        verify(fireStationService).getFireStationByKeyId(anyString(), anyInt());
    }

    @Test
    @Tag("GET-FireStation")
    @DisplayName("Given incomplete key ID, when GET request, then return BadRequest status")
    public void givenIncompleteKeyId_whenGetRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fireStation")
                .param("address", "")
                .param("station", "2"))
                .andExpect(status().isBadRequest());

        verify(fireStationService, times(0)).getFireStationByKeyId(anyString(), anyInt());
    }
}
