
package com.safetynet.Alerts.integration;

import com.safetynet.Alerts.dto.*;
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
public class AlertsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URLS
    private final static String PERSONS_BY_STATION_URL = "/firestation?stationNumber={stationNumber}";
    private final static String CHILD_ALERT_URL = "/childAlert?address={address}";
    private final static String PHONE_ALERT_URL = "/phoneAlert?firestation={firestation}";
    private final static String FIRE_URL = "/fire?address={address}";
    private final static String FLOOD_URL = "/flood/stations?stations={stations}";
    private final static String FLOOD_PARAM_URL = "&stations={stations}";
    private final static String PERSON_INFO_URL = "/personInfo?firstName={firstName}&lastName={lastName}";
    private final static String COMMUNITY_EMAIL_URL = "/communityEmail?city={city}";

    @Test
    @Tag("GET-PersonsByStation")
    @DisplayName("Given a valid station number, when PersonsByStation request, then return OK status and according DTO")
    public void givenAValidStationNumber_whenPersonsByStationRequest_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<PersonsByStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PERSONS_BY_STATION_URL, PersonsByStationDTO.class, 2);

        assertNotNull(response);
        assertThat(response.getBody().getPersonsByStation().size()).isEqualTo(response.getBody().getAdultNumber() +
                response.getBody().getChildNumber());
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PersonsByStation")
    @DisplayName("Given an invalid station number, when PersonsByStation request, then return NOT FOUND status")
    public void givenInvalidStationNumber_whenPersonsByStationRequest_thenReturnBadRequestStatus() {
        ResponseEntity<PersonsByStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PERSONS_BY_STATION_URL, PersonsByStationDTO.class, 0);

        assertThat(response.getBody().getPersonsByStation()).isNull();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PersonsByStation")
    @DisplayName("Given a negative station number, when PersonsByStation request, then return NOT FOUND status")
    public void givenNegativeStationNumber_whenPersonsByStationRequest_thenReturnNotFoundStatus() {
        ResponseEntity<PersonsByStationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PERSONS_BY_STATION_URL, PersonsByStationDTO.class, -2);

        assertThat(response.getBody().getPersonsByStation()).isNull();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-ChildAlert")
    @DisplayName("Given a valid address, when ChildAlert request, then return OK status and according DTO")
    public void givenAValidAddress_whenChildAlertRequest_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<ChildAlertDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                CHILD_ALERT_URL, ChildAlertDTO.class, "1509 Culver St");

        assertNotNull(response.getBody());
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-ChildAlert")
    @DisplayName("Given an empty address, when ChildAlert request, then return BAD REQUEST status")
    public void givenAnEmptyAddress_whenChildAlertRequest_thenReturnBadRequestStatus() {
        ResponseEntity<ChildAlertDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                CHILD_ALERT_URL, ChildAlertDTO.class, "");

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-ChildAlert")
    @DisplayName("Given an unregistered address, when ChildAlert request, then return NOT FOUND status")
    public void givenAnUnRegisteredAddress_whenChildAlertRequest_thenReturnNotFoundStatus() {
        ResponseEntity<ChildAlertDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                CHILD_ALERT_URL, ChildAlertDTO.class, "unregistered address");

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PhoneAlert")
    @DisplayName("Given a valid station number, when PhoneAlert request, then return OK status and according DTO")
    public void givenAStationNumber_whenPhoneAlertRequest_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<PhoneAlertDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PHONE_ALERT_URL, PhoneAlertDTO.class, 1);

        assertThat(response.getBody().getPhones()).isNotEmpty();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PhoneAlert")
    @DisplayName("Given a negative station number, when PhoneAlert request, then return NOT FOUND status")
    public void givenNegativeStationNumber_whenPhoneAlertRequest_thenReturnNotFoundStatus() {
        ResponseEntity<PhoneAlertDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PHONE_ALERT_URL, PhoneAlertDTO.class, -2);

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PhoneAlert")
    @DisplayName("Given an invalid station number, when PhoneAlert request, then return NOT FOUND status")
    public void givenInvalidStationNumber_whenPhoneAlertRequest_thenReturnNotFoundStatus() {
        ResponseEntity<PhoneAlertDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PHONE_ALERT_URL, PhoneAlertDTO.class, 0);

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Fire")
    @DisplayName("Given a valid address, when Fire request, then return OK status and according DTO")
    public void givenAValidAddress_whenFireRequest_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<FireDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                FIRE_URL, FireDTO.class, "892 Downing Ct");

        assertThat(response.getBody().getPersons()).isNotEmpty();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Fire")
    @DisplayName("Given an empty address, when Fire request, then return BAD REQUEST status")
    public void GivenEmptyAddress_whenFireRequest_thenReturnBadRequestStatus() {
        ResponseEntity<FireDTO> response = restTemplate.getForEntity(FIRE_URL, FireDTO.class,
                "");

        assertThat(response.getBody().getPersons()).isNull();
        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Fire")
    @DisplayName("Given a unregistered address, when Fire request, then return NOT FOUND status")
    public void GivenAnUnRegisteredAddress_whenFireRequest_thenReturnNotFoundStatus() {
        ResponseEntity<FireDTO> response = restTemplate.getForEntity(FIRE_URL, FireDTO.class,
                "unregistered address");

        assertThat(response.getBody().getPersons()).isNull();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Flood")
    @DisplayName("Given valid stations numbers, when Flood request, then return OK status and according DTO")
    public void givenValidStationsNumbers_whenFloodRequest_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<FloodDTO> response = restTemplate.getForEntity(
                "http://localhost:" + port + FLOOD_URL + FLOOD_PARAM_URL, FloodDTO.class, 2, 4);

        assertThat(response.getBody().getHouseholdsByStation()).isNotEmpty();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Flood")
    @DisplayName("Given invalid stations numbers, when Flood request, then return NOT FOUND status")
    public void givenInvalidStationsNumbers_whenFloodRequest_thenReturnNotFoundStatus() {
        ResponseEntity<FloodDTO> response = restTemplate.getForEntity("http://localhost:" + port + FLOOD_URL +
                FLOOD_PARAM_URL, FloodDTO.class, 0, 1000);

        assertThat(response.getBody().getHouseholdsByStation()).isNull();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-Flood")
    @DisplayName("Given negative stations numbers, when Flood request, then return NOT FOUND status")
    public void givenNegativeStationsNumbers_whenFloodRequest_thenReturnNotFoundStatus() {
        ResponseEntity<FloodDTO> response = restTemplate.getForEntity("http://localhost:" + port + FLOOD_URL +
                FLOOD_PARAM_URL, FloodDTO.class, -1, -4);

        assertThat(response.getBody().getHouseholdsByStation()).isNull();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PersonInfo")
    @DisplayName("Given a valid person ID, when Flood request, then return OK status and according DTO")
    public void givenAValidPersonId_whenPersonInfoRequest_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<PersonInfoDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PERSON_INFO_URL, PersonInfoDTO.class, "John", "Boyd");

        assertThat(response.getBody().getPersonsInfo()).isNotEmpty();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-PersonInfo")
    @DisplayName("Given empty person ID, when Flood request, then return BAD REQUEST status")
    public void givenEmptyPersonId_whenPersonInfoRequest_thenReturnBadRequestStatus() {
        ResponseEntity<PersonInfoDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PERSON_INFO_URL, PersonInfoDTO.class, "", "");

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-CommunityEmail")
    @DisplayName("Given a valid city, when CommunityEmail request, then return OK status and according DTO")
    public void GivenAValidCity_whenCommunityEmailURL_thenAccordingDTOAndOKStatusIsReturned() {
        ResponseEntity<CommunityEmailDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                COMMUNITY_EMAIL_URL, CommunityEmailDTO.class, "Culver");

        assertThat(response.getBody().getEmails()).isNotEmpty();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-CommunityEmail")
    @DisplayName("Given an unregistered city, when CommunityEmail request, then return NOT FOUND status")
    public void givenAnUnregisteredCity_whenCommunityEmailRequest_thenReturnNotFoundStatus() {
        ResponseEntity<CommunityEmailDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                COMMUNITY_EMAIL_URL, CommunityEmailDTO.class, "unregistered city");

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-CommunityEmail")
    @DisplayName("Given empty city, when CommunityEmail request, then return BAD REQUEST status")
    public void givenEmptyCity_whenCommunityEmailRequest_thenReturnBadRequestStatus() {
        ResponseEntity<CommunityEmailDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                COMMUNITY_EMAIL_URL, CommunityEmailDTO.class, "");

        assertThat(response.getBody()).hasAllNullFieldsOrProperties();
        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }
}
