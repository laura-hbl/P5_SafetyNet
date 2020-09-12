package com.safetynet.Alerts.integration;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.model.FireStation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FireStationControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final static String FIRE_KEY_ID_URL = "/fireStation?address={address}&station={station}";

    @Test
    @Tag("POST-FireStation")
    @DisplayName("Given a FireStation to add, when POST request, then FireStation added and CREATED status should be " +
            "returned")
    public void givenAFireStation_whenPostRequest_thenReturnFireStationAddedAndCreatedStatus() {
        FireStationDTO fireToAdd = new FireStationDTO("addressAdd", 3);

        ResponseEntity<FireStationDTO> response = restTemplate.postForEntity("http://localhost:" + port +
                "/firestation", fireToAdd, FireStationDTO.class);

        assertNotNull(response);
        assertEquals("request status", HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(fireToAdd);
    }

    @Test
    @Tag("POST-FireStation")
    @DisplayName("Given a FireStation with empty address, when POST request, then return BAD REQUEST status")
    public void givenAFireStationWithMissingAddress_whenPostRequest_thenReturnBadRequestStatus() {
        FireStationDTO fireToAdd = new FireStationDTO("", 3);

        ResponseEntity<FireStationDTO> response = restTemplate.postForEntity("http://localhost:" + port +
                "/firestation", fireToAdd, FireStationDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("POST-FireStation")
    @DisplayName("If FireStation is registered, when POST request, then FireStation is not added and CONFLICT" +
            " status is returned")
    public void givenARegisteredFireStation_whenPostRequest_thenFireStationIsNotAddedAndConflictStatusIsReturned() {
        FireStationDTO fireToAdd = new FireStationDTO("addressAdd", 2);

        restTemplate.postForEntity("http://localhost:" + port + "/firestation", fireToAdd, FireStationDTO.class);
        ResponseEntity<FireStation> response = restTemplate.postForEntity("http://localhost:" + port + "/firestation",
                fireToAdd, FireStation.class);

        assertThat(response.getBody()).isNotSameAs(fireToAdd);
        assertEquals("request status", HttpStatus.CONFLICT.value(), response.getStatusCodeValue());

    }

    @Test
    @Tag("PUT-FireStation")
    @DisplayName("Given a FireStation to update, when PUT request, then fireStation updated is returned")
    public void givenAFireStationToUpdate_whenPutRequest_thenResponseShouldMatchFireStationUpdated() {
        FireStationDTO fireToUpdate = new FireStationDTO("addressPut", 2);
        //FireStation updated with new station
        FireStationDTO fireUpdated = new FireStationDTO("addressPut", 1);

        restTemplate.postForEntity("http://localhost:" + port + "/firestation", fireToUpdate, FireStationDTO.class);
        restTemplate.put("http://localhost:" + port + "/firestation", fireUpdated);

        // Checking that existing FireStation has been modified
        ResponseEntity<FireStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                FIRE_KEY_ID_URL, FireStationDTO.class, fireToUpdate.getAddress(), fireToUpdate.getStation());

        assertNotNull(response);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(fireUpdated);
    }

    @Test
    @Tag("DELETE-FireStation")
    @DisplayName("Given a FireStation to delete, when DELETE request, then fireStation should be deleted")
    public void givenAFireStationToDelete_whenDeleteRequest_thenFireStationShouldBeDeleted() {
        FireStationDTO fire = new FireStationDTO("addressDel", 2);

        restTemplate.postForEntity("http://localhost:" + port + "/firestation", fire, FireStationDTO.class);
        restTemplate.delete("http://localhost:" + port + "/firestation?address={address}&station={station}"
                , fire.getAddress(), fire.getStation());

        // Checking that existing FireStation has been deleted
        ResponseEntity<FireStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                FIRE_KEY_ID_URL, FireStationDTO.class, fire.getAddress(), fire.getStation());

        assertThat(response.getBody().getAddress()).isNull();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

    }

    @Test
    @Tag("DELETE-FireStation")
    @DisplayName("IF address param is empty, when DELETE request, then fireStation is not deleted")
    public void givenIncompleteParam_whenDeleteRequest_thenFireStationIsNotDeleted() {
        FireStationDTO fire = new FireStationDTO("addressDel", 2);
        restTemplate.postForEntity("http://localhost:" + port + "/firestation", fire, FireStationDTO.class);

        // delete request with missing address
        restTemplate.delete("http://localhost:" + port + "/firestation?address={address}&station={station}",
                "", 2);

        // Checking that existing FireStation has not been deleted
        ResponseEntity<FireStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                FIRE_KEY_ID_URL, FireStationDTO.class, fire.getAddress(), fire.getStation());

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(fire);
    }

    @Test
    @Tag("GET-FireStation")
    @DisplayName("Given valid key ID param, when GET request, then according fireStation and OK status should be returned")
    public void givenAValidFireStationKeyIdParam_whenGetRequest_thenExpectedFireStationAndOkStatusIsReturned() {
        FireStationDTO fire = new FireStationDTO("addressGet", 1);
        restTemplate.postForEntity("http://localhost:" + port + "/firestation", fire, FireStationDTO.class);

        ResponseEntity<FireStationDTO> response = restTemplate.getForEntity("http://localhost:" +
                port + FIRE_KEY_ID_URL, FireStationDTO.class, fire.getAddress(), fire.getStation());

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(fire);
    }

    @Test
    @Tag("GET-FireStation")
    @DisplayName("IF key ID param is incomplete, when GET request, then BAD REQUEST status is returned")
    public void givenIncompleteParam_whenGetRequest_thenReturnBadRequestStatus() {
        FireStationDTO fire = new FireStationDTO("addressGet", 1);

        restTemplate.postForEntity("http://localhost:" + port + "/firestation", fire, FireStationDTO.class);
        // get request with missing address param
        ResponseEntity<FireStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                FIRE_KEY_ID_URL, FireStationDTO.class, "", fire.getStation());

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-FireStation")
    @DisplayName("IF FireStation is unregistered, when GET request, then NOT FOUND status is returned")
    public void givenAnUnRegisteredFireStation_whenGetRequest_thenReturnNotFoundStatus() {
        FireStationDTO fire = new FireStationDTO("foo", 1);

        ResponseEntity<FireStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                FIRE_KEY_ID_URL, FireStationDTO.class, fire.getAddress(), fire.getStation());

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
}
