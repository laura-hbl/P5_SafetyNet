package com.safetynet.Alerts.integration;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final static String PERSON_ID_URL = "/person?firstName={firstName}&lastName={lastName}";

    @Test
    @Tag("POST-Person")
    @DisplayName("Given a Person, when POST request, then person added and CREATED status should be returned")
    public void givenAPersonToAdd_whenPostRequest_thenReturnPersonAddedAndCreatedStatus() {
        PersonDTO personToAdd = new PersonDTO("firstNameAdd", "lastNameAdd", "13 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");

        ResponseEntity<PersonDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/person",
                personToAdd, PersonDTO.class);

        assertNotNull(response);
        assertEquals("request status", HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(personToAdd);
    }

    @Test
    @Tag("POST-Person")
    @DisplayName("Given a Person with missing ID, when POST request, then BAD REQUEST status should be returned")
    public void givenAPersonToAddWithMissingId_whenPostRequest_thenReturnBadRequestStatus() {
        PersonDTO personToAdd = new PersonDTO("", "", "15 rue Paris", "Paris",
                97451, "844-854-6577", "laurahbl@email.com");

        ResponseEntity<PersonDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/person",
                personToAdd, PersonDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("POST-Person")
    @DisplayName("Given a registered Person, when POST request, then person is not added and CONFLICT status " +
            "should be returned")
    public void givenARegisteredPerson_whenPostRequest_thenPersonIsNotAddedAndConflictStatusIsReturned() {
        PersonDTO personToAdd = new PersonDTO("firstNameAdd", "lastNameAdd", "15 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");

        restTemplate.postForEntity("http://localhost:" + port + "/person", personToAdd, PersonDTO.class);
        ResponseEntity<Person> response = restTemplate.postForEntity("http://localhost:" + port + "/person",
                personToAdd, Person.class);

        assertThat(response.getBody()).isNotSameAs(personToAdd);
        assertEquals("request status", HttpStatus.CONFLICT.value(), response.getStatusCodeValue());

    }

    @Test
    @Tag("PUT-Person")
    @DisplayName("Given a Person to update, when PUT request, then person updated should be returned")
    public void givenAPersonToUpdate_whenPutRequest_thenResponseShouldMatchPersonUpdated() {
        PersonDTO personToUpdate = new PersonDTO("firstNamePut", "lastNamePut", "11 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");
        //person updated with new address
        PersonDTO personUpdated = new PersonDTO("firstNamePut", "lastNamePut", "3 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");

        restTemplate.postForEntity("http://localhost:" + port + "/person", personToUpdate, PersonDTO.class);
        restTemplate.put("http://localhost:" + port + "/person", personUpdated);

        // Checking that existing person has been modified
        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, personToUpdate.getFirstName(), personToUpdate.getLastName());

        assertNotNull(response);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(personUpdated);
    }

    @Test
    @Tag("PUT-Person")
    @DisplayName("Given a Person with incomplete ID, when PUT request, then person is not updated")
    public void givenAPersonWithIncompleteId_whenPutRequest_thenPersonIsNotUpdated() {
        PersonDTO personToUpdate = new PersonDTO("firstNamePut", "lastNamePut", "11 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");
        //person updated with missing firstName
        PersonDTO personUpdated = new PersonDTO("", "lastNamePut", "2 rue Paris", "Paris",
                97451, "844-854-6577", "laurahbl@email.com");

        restTemplate.postForEntity("http://localhost:" + port + "/person", personToUpdate, PersonDTO.class);
        restTemplate.put("http://localhost:" + port + "/person", personUpdated);

        // Checking that existing person has not been modified
        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, personToUpdate.getFirstName(), personToUpdate.getLastName());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAddress()).isNotEqualTo("2 rue Paris");
    }

    @Test
    @Tag("DELETE-Person")
    @DisplayName("Given a Person to delete, when DELETE request, then person should be deleted")
    public void givenAPersonToDelete_whenDeleteRequest_thenPersonShouldBeDeleted() {
        PersonDTO person = new PersonDTO("firstNameDel", "lastNameDel", "10 rue Paris",
                "Paris", 97451, "844-854-6577", "lauraabl@email.com");

        restTemplate.postForEntity("http://localhost:" + port + "/person", person, PersonDTO.class);
        restTemplate.delete("http://localhost:" + port + PERSON_ID_URL, person.getFirstName(), person.getLastName());

        // Checking that existing person has been deleted
        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, person.getFirstName(), person.getLastName());

        assertThat(response.getBody()).isNotSameAs(person);
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

    }

    @Test
    @Tag("DELETE-Person")
    @DisplayName("If Person ID param is incomplete, when DELETE request, then person is not deleted")
    public void givenIncompleteIdParam_whenDeleteRequest_thenPersonIsNotDeleted() {
        PersonDTO person = new PersonDTO("firstNameDel", "lastNameDel", "11 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");
        restTemplate.postForEntity("http://localhost:" + port + "/person", person, PersonDTO.class);

        // delete request with missing lastName param
        restTemplate.delete("http://localhost:" + port + PERSON_ID_URL, "firstNameDel", "");

        // Checking that existing person has not been deleted
        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, person.getFirstName(), person.getLastName());

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(person);
    }

    @Test
    @Tag("GET-Person")
    @DisplayName("Given a Person ID, when GET request, then expected person added and OK status should be returned")
    public void givenAPersonId_whenGetRequest_thenExpectedPersonAndOkStatusIsReturned() {
        PersonDTO person = new PersonDTO("firstNameGet", "lastNameGet", "11 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");
        restTemplate.postForEntity("http://localhost:" + port + "/person", person, PersonDTO.class);

        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, person.getFirstName(), person.getLastName());

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(person);
    }

    @Test
    @Tag("GET-Person")
    @DisplayName("If Person ID param is incomplete, when GET request, then BAD REQUEST status should be returned")
    public void givenIncompleteParam_whenGetRequest_thenReturnBadRequestStatus() {
        PersonDTO person = new PersonDTO("firstNameGet", "lastNameGet", "11 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");
        restTemplate.postForEntity("http://localhost:" + port + "/person", person, PersonDTO.class);
        // get request with missing firstName param
        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, "", person.getLastName());

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Person")
    @DisplayName("If Person is unregistered, when GET request, then NOT FOUND status should be returned")
    public void givenAnUnRegisteredPerson_whenGetRequest_thenReturnNotFoundStatus() {
        PersonDTO person = new PersonDTO("firstNameGet", "lastNameGet", "11 rue Paris",
                "Paris", 97451, "844-854-6577", "laurahbl@email.com");

        ResponseEntity<PersonDTO> response = restTemplate.getForEntity("http://localhost:" + port + PERSON_ID_URL,
                PersonDTO.class, person.getFirstName(), person.getLastName());

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
}