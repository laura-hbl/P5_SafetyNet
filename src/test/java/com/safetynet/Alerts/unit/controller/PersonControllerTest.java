package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.PersonController;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.service.PersonService;
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
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private ObjectMapper objectMapper;

    private PersonDTO personDTO;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        personDTO = new PersonDTO("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
    }

    @Test
    @Tag("POST-Person")
    @DisplayName("Given a Person to add, when POST request, then return Created status")
    public void givenAValidPersonToAdd_whenPostRequest_thenReturnCreatedStatus() throws Exception {
        when(personService.createPerson(any(PersonDTO.class))).thenReturn(any(PersonDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());

        verify(personService).createPerson(any(PersonDTO.class));
    }

    @Test
    @Tag("POST-Person")
    @DisplayName("Given a person to add with empty Id, when POST request, then return BadRequest status")
    public void givenAPersonToAddWithMissingId_whenPostRequest_thenReturnBadRequestStatus() throws Exception {
        personDTO = new PersonDTO("", "", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).createPerson(any(PersonDTO.class));
    }

    @Test
    @Tag("POST-Person")
    @DisplayName("Given an empty body request, when POST request, then return BadRequest status")
    public void givenAnEmptyBodyRequest_whenPostRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).createPerson(any(PersonDTO.class));
    }

    @Test
    @Tag("PUT-Person")
    @DisplayName("Given a Person to update, when PUT request, then return OK status")
    public void givenAValidPersonToUpdate_whenPutRequest_thenReturnOkStatus() throws Exception {
        when(personService.updatePerson(any(PersonDTO.class))).thenReturn(any(PersonDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isOk());

        verify(personService).updatePerson(any(PersonDTO.class));
    }

    @Test
    @Tag("PUT-Person")
    @DisplayName("Given a person to update with empty Id, when PUT request, then return BadRequest status")
    public void givenAPersonWithMissingId_whenPutRequest_thenReturnBadRequestStatus() throws Exception {
        personDTO = new PersonDTO("", "", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).updatePerson(any(PersonDTO.class));
    }

    @Test
    @Tag("PUT-Person")
    @DisplayName("Given an empty body request, when PUT request, then return BadRequest status")
    public void givenAnEmptyBodyRequest_whenPutRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).updatePerson(any(PersonDTO.class));
    }

    @Test
    @Tag("DELETE-Person")
    @DisplayName("Given valid person Id param, when DELETE request, then return OK status")
    public void givenValidPersonIdParam_whenDeleteRequest_thenReturnOkStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .param("firstName", "John")
                .param("lastName", "Boyd"))
                .andExpect(status().isOk());

        verify(personService).deletePerson(anyString(), anyString());
    }

    @Test
    @Tag("DELETE-Person")
    @DisplayName("Given incomplete Id param, when DELETE request, then return BadRequest status")
    public void givenIncompleteIdParam_whenDeleteRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .param("firstName", "John")
                .param("lastName", ""))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).deletePerson(anyString(), anyString());
    }

    @Test
    @Tag("GET-Person")
    @DisplayName("Given valid person Id param, when GET request, then return OK status")
    public void givenValidPersonIdParam_whenGetRequest_thenReturnOkStatus() throws Exception {
        when(personService.getPersonById(anyString(), anyString())).thenReturn(personDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/person")
                .param("firstName", "John")
                .param("lastName", "Boyd"))
                .andExpect(status().isOk());

        verify(personService).getPersonById(anyString(), anyString());
    }

    @Test
    @Tag("GET-Person")
    @DisplayName("Given incomplete Id param, when GET request, then return BadRequest status")
    public void givenIncompleteIdParam_whenGetRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person")
                .param("firstName", "John")
                .param("lastName", ""))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).getPersonById(anyString(), anyString());
    }
}
