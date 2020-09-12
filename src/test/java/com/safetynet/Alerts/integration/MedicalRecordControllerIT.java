package com.safetynet.Alerts.integration;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalRecordControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final static String MED_ID_URL = "/medicalRecord?firstName={firstName}&lastName={lastName}";

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given a MedicalRecord, when POST request, then MedicalRecord added and CREATED status should" +
            " be returned")
    public void givenAMedicalRecord_whenPostRequest_thenReturnMedicalRecordAddedAndCreatedStatus() {
        MedicalRecordDTO medToAdd = new MedicalRecordDTO("firstNameAdd", "lastNameAdd",
                "03/06/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));

        ResponseEntity<MedicalRecordDTO> response = restTemplate.postForEntity("http://localhost:" + port +
                "/medicalRecord", medToAdd, MedicalRecordDTO.class);

        assertNotNull(response);
        assertEquals("request status", HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(medToAdd);
    }

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given a MedicalRecord with incomplete ID, when POST request, then return BAD REQUEST status")
    public void givenAMedicalRecordWithIncompleteId_whenPostRequest_thenReturnBadRequestStatus() {
        MedicalRecordDTO medToAdd = new MedicalRecordDTO("firstNameAdd", "", "03/06/1991",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        ResponseEntity<MedicalRecordDTO> response = restTemplate.postForEntity("http://localhost:" + port +
                "/medicalRecord", medToAdd, MedicalRecordDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("POST-MedicalRecord")
    @DisplayName("Given a registered MedicalRecord, when POST request, then MedicalRecord is not Added and CONFLICT " +
            "status is returned")
    public void givenARegisteredMedicalRecord_whenPostRequest_thenMedicalRecordIsNotAddedAndConflictStatusIsReturned() {
        MedicalRecordDTO med = new MedicalRecordDTO("firstNameAdd", "lastNameAdd",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", med, MedicalRecordDTO.class);
        ResponseEntity<MedicalRecordDTO> response = restTemplate.postForEntity("http://localhost:" + port +
                "/medicalRecord", med, MedicalRecordDTO.class);

        assertThat(response.getBody()).isNotSameAs(med);
        assertEquals("request status", HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given a MedicalRecord to update, when PUT request, then MedicalRecord updated and OK status should" +
            " be returned")
    public void givenAMedicalRecordToUpdate_whenPutRequest_thenResponseShouldMatchMedicalRecordUpdatedAndReturnOKStatus() {
        MedicalRecordDTO medToUpdate = new MedicalRecordDTO("firstNamePut", "lastNamePut",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        //medicalRecord updated with more allergies
        MedicalRecordDTO medUpdated = new MedicalRecordDTO("firstNamePut", "lastNamePut",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));

        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", medToUpdate, MedicalRecordDTO.class);
        restTemplate.put("http://localhost:" + port + "/medicalRecord", medUpdated);

        // Checking that existing medicalRecord has been modified
        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, medToUpdate.getFirstName(), medToUpdate.getLastName());

        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(medUpdated);
    }

    @Test
    @Tag("PUT-MedicalRecord")
    @DisplayName("Given a MedicalRecord with incomplete ID, when PUT request, then MedicalRecord is not updated")
    public void givenAMedicalRecordWithIncompleteId_whenPutRequest_thenMedicalRecordIsNotUpdated() {
        MedicalRecordDTO medToUpdate = new MedicalRecordDTO("firstNamePut", "lastNamePut",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        //medicalRecord updated with missing firstName
        MedicalRecordDTO medUpdated = new MedicalRecordDTO("", "lastNamePut", "01/08/1991",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));

        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", medToUpdate, MedicalRecordDTO.class);
        restTemplate.put("http://localhost:" + port + "/medicalRecord", medUpdated);

        // Checking that existing person has not been modified
        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, medToUpdate.getFirstName(), medToUpdate.getLastName());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAllergies()).doesNotContain("peanut");
    }

    @Test
    @Tag("DELETE-MedicalRecord")
    @DisplayName("Given MedicalRecord to delete, when DELETE request, then MedicalRecord is not deleted")
    public void givenAMedicalRecordToDelete_whenDeleteRequest_thenMedicalRecordShouldBeDeleted() {
        MedicalRecordDTO med = new MedicalRecordDTO("firstNameDel", "lastNameDel",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));

        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", med, MedicalRecordDTO.class);
        restTemplate.delete("http://localhost:" + port + MED_ID_URL, med.getFirstName(), med.getLastName());

        // Checking that existing MedicalRecord has been deleted
        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, med.getFirstName(), med.getLastName());

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

    }

    @Test
    @Tag("DELETE-MedicalRecord")
    @DisplayName("If ID param is incomplete, when DELETE request, then MedicalRecord is not deleted")
    public void givenIncompleteIdParam_whenDeleteRequest_thenMedicalRecordIsNotDeleted() {
        MedicalRecordDTO med = new MedicalRecordDTO("firstNameDel", "lastNameDel",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", med, MedicalRecordDTO.class);

        // delete request with missing lastName param
        restTemplate.delete("http://localhost:" + port + MED_ID_URL, "firstNameDel", "");

        // Checking that existing person has not been deleted
        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, med.getFirstName(), med.getLastName());

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(med);
    }

    @Test
    @Tag("GET-MedicalRecord")
    @DisplayName("Given valid ID param, when GET request, then expected MedicalRecord and OK status should be returned")
    public void givenValidId_whenGetRequest_thenExpectedMedicalRecordAndOkStatusIsReturned() {
        MedicalRecordDTO med = new MedicalRecordDTO("firstNameGet", "lastNameGet",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", med, MedicalRecordDTO.class);

        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, med.getFirstName(), med.getLastName());

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(med);
    }

    @Test
    @Tag("GET-MedicalRecord")
    @DisplayName("Given incomplete ID param, when GET request, then BAD REQUEST status should be returned")
    public void givenIncompleteIdParam_whenGetRequest_thenReturnBadRequestStatus() {
        MedicalRecordDTO med = new MedicalRecordDTO("firstNameGet", "lastNameGet",
                "01/08/1991", Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));

        restTemplate.postForEntity("http://localhost:" + port + "/medicalRecord", med, MedicalRecordDTO.class);
        // get request with missing firstName param
        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, "", med.getLastName());

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-MedicalRecord")
    @DisplayName("if MedicalRecord is unregistered, when GET request, then NOT FOUND should be returned")
    public void givenAnUnRegisteredMedicalRecord_whenGetRequest_thenReturnNotFoundStatus() {
        MedicalRecordDTO med = new MedicalRecordDTO("foo", "foo", "01/08/1991",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));

        ResponseEntity<MedicalRecordDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                MED_ID_URL, MedicalRecordDTO.class, med.getFirstName(), med.getLastName());

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
}
