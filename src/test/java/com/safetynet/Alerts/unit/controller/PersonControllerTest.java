package com.safetynet.Alerts.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.controller.PersonController;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.Person;
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
    @Tag("POST")
    @DisplayName("Given a Person to add, when createPerson, then return Created status")
    public void givenAPersonToAdd_whenCreatePerson_thenReturnCreatedStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(personDTO);

        when(personService.createPerson(any(PersonDTO.class))).thenReturn(any(Person.class));

        mockMvc.perform(MockMvcRequestBuilders.post( "/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated());

        verify(personService).createPerson(any(PersonDTO.class));
    }

    @Test
    @Tag("POST")
    @DisplayName("Given a null Person, when createPerson, then return BadRequest status")
    public void givenANullPerson_whenCreatePerson_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).createPerson(any(PersonDTO.class));
    }

    @Test
    @Tag("PUT")
    @DisplayName("Given a Person to update, when updatePerson, then return OK status")
    public void givenAPersonToUpdate_whenUpdatePerson_thenReturnOkStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(personDTO);
        when(personService.updatePerson(any(PersonDTO.class))).thenReturn(any(Person.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(personService).updatePerson(any(PersonDTO.class));
    }

    @Test
    @Tag("PUT")
    @DisplayName("Given a null Person, when updatePerson, then return BadRequest status")
    public void givenANullPerson_whenUpdatePerson_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).updatePerson(any(PersonDTO.class));
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Given a Person to delete, when deletePerson, then return Ok status")
    public void givenAPersonToDelete_whenDeletePerson_thenReturnOkStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(personDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(personService).deletePerson(any(PersonDTO.class));
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Given a null Person, when deletePerson, then return BadRequest status")
    public void givenANullPerson_whenDeletePerson_thenReturnBadRequestStatus() throws Exception {
        String jsonContent = objectMapper.writeValueAsString(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).deletePerson(any(PersonDTO.class));
    }
}
