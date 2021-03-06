package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.AlertsController;
import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.model.PersonInfo;
import com.safetynet.Alerts.service.AlertsService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlertsController.class)
public class AlertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertsService alertsService;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Tag("GET-PersonsByStation")
    @DisplayName("Given a valid station number, when PersonsByStation request, then return Ok status")
    public void givenAValidStationNumber_whenPersonsByStationRequest_thenReturnOKStatus() throws Exception {
        when(alertsService.getPersonsByStation(anyInt())).thenReturn(any(PersonsByStationDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stationNumber", "1"))
                .andExpect(status().isOk());

        verify(alertsService).getPersonsByStation(anyInt());
    }

    @Test
    @Tag("GET-PersonsByStation")
    @DisplayName("Given an empty station number, when PersonsByStation request, then return BadRequest status")
    public void givenAnEmptyStationNumber_whenPersonsByStationRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stationNumber", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getPersonsByStation(anyInt());
    }

    @Test
    @Tag("GET-ChildAlert")
    @DisplayName("Given valid address, when ChildAlert request, then return Ok status")
    public void givenAValidAddress_whenChildAlertRequest_thenReturnOKStatus() throws Exception {
        when(alertsService.getChildByAddress(anyString())).thenReturn(any(ChildAlertDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "29 15th St"))
                .andExpect(status().isOk());

        verify(alertsService).getChildByAddress(anyString());
    }

    @Test
    @Tag("GET-ChildAlert")
    @DisplayName("Given an empty address, when ChildAlert request, then return BadRequest status")
    public void givenAnEmptyAddress_whenChildAlertRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getChildByAddress(anyString());
    }

    @Test
    @Tag("GET-PhoneAlert")
    @DisplayName("Given a valid station number, when PhoneAlert request, then return Ok status")
    public void givenValidStationNumber_whenPhoneAlertRequest_thenReturnOKStatus() throws Exception {
        when(alertsService.getPhonesByStation(anyInt())).thenReturn(any(PhoneAlertDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firestation", "2"))
                .andExpect(status().isOk());

        verify(alertsService).getPhonesByStation(anyInt());
    }

    @Test
    @Tag("GET-PhoneAlert")
    @DisplayName("Given an empty station number,  when PhoneAlert request, then return BadRequest status")
    public void givenAnEmptyStationNumber_whenPhoneAlertRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firestation", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getPhonesByStation(anyInt());
    }

    @Test
    @Tag("GET-Fire")
    @DisplayName("Given an address, when Fire request, then return Ok status")
    public void givenAValidAddress_whenFireRequest_thenReturnOKStatus() throws Exception {
        when(alertsService.getPersonsByAddress(anyString())).thenReturn(any(FireDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "29 15th St"))
                .andExpect(status().isOk());

        verify(alertsService).getPersonsByAddress(anyString());
    }

    @Test
    @Tag("GET-Fire")
    @DisplayName("Given an empty address, when Fire request, then return BadRequest status")
    public void givenAnEmptyAddress_whenFireRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getPersonsByAddress(anyString());
    }

    @Test
    @Tag("GET-Flood")
    @DisplayName("Given a station number, when Flood request, then return Ok status")
    public void givenStationsNumber_whenFloodRequest_thenReturnOKStatus() throws Exception {
        when(alertsService.getHouseholdsByStation(anyList())).thenReturn(any(FloodDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stations", "1", "2"))
                .andExpect(status().isOk());

        verify(alertsService).getHouseholdsByStation(anyList());
    }

    @Test
    @Tag("GET-Flood")
    @DisplayName("Given an empty station number, when Flood request, then return BadRequest status")
    public void givenAnEmptyStationsNumber_whenFloodRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stations", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getHouseholdsByStation(anyList());
    }

    @Test
    @Tag("GET-PersonInfo")
    @DisplayName("Given valid person Id, when PersonInfo request, then return Ok status")
    public void givenAPersonId_whenPersonInfoRequest_thenReturnOKStatus() throws Exception {
        PersonInfoDTO personInfo = new PersonInfoDTO(Arrays.asList(new PersonInfo("Boyd", "1509 Culver St",
                22, "jaboyd@email.com", Arrays.asList(""), Arrays.asList("peanut"))));
        when(alertsService.getInfoByIdentity(anyString(), anyString())).thenReturn(personInfo);

        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "John")
                .param("lastName", "Boyd"))
                .andExpect(status().isOk());

        verify(alertsService).getInfoByIdentity(anyString(), anyString());
    }

    @Test
    @Tag("GET-PersonInfo")
    @DisplayName("Given incomplete person Id, when PersonInfo request, then return BadRequest status")
    public void givenIncompletePersonId_whenPersonInfoRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "John")
                .param("lastName", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getInfoByIdentity(anyString(), anyString());
    }

    @Test
    @Tag("GET-CommunityEmail")
    @DisplayName("Given a valid city, when CommunityEmail request, then return Ok status")
    public void givenAValidCity_whenCommunityEmailRequest_thenReturnOKStatus() throws Exception {
        when(alertsService.getEmailsByCity(anyString())).thenReturn(any(CommunityEmailDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("city", "Culver"))
                .andExpect(status().isOk());

        verify(alertsService).getEmailsByCity(anyString());
    }

    @Test
    @Tag("GET-CommunityEmail")
    @DisplayName("Given an empty city, when CommunityEmail request, then return BadRequest status")
    public void givenAnEmptyCity_whenCommunityEmailRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("city", ""))
                .andExpect(status().isBadRequest());

        verify(alertsService, times(0)).getEmailsByCity(anyString());
    }
}
