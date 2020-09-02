package com.safetynet.Alerts.unit.service;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.service.AlertsService;
import com.safetynet.Alerts.service.FireStationService;
import com.safetynet.Alerts.service.MedicalRecordService;
import com.safetynet.Alerts.service.PersonService;
import com.safetynet.Alerts.util.AgeCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlertsServiceTest {

    @InjectMocks
    private AlertsService alertsService;

    @Mock
    private FireStationService fireStationService;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Mock
    private PersonService personService;

    @Mock
    private AgeCalculator ageCalculator;

    private static List<Person> personList;
    private static Person person1;
    private static Person person2;
    private static Person person3;

    private static MedicalRecord med1;
    private static MedicalRecord med2;
    private static MedicalRecord med3;

    @Before
    public void setUp() {
        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
        person2 = new Person("Tony", "Cooper", "1509 Culver St", "Culver",
                97451, "841-874-6874", "tcoop@ymail.com");
        person3 = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver",
                97451, "841-874-7458", "gramps@email.com");
        personList = Arrays.asList(person1, person2, person3);

        med1 = new MedicalRecord("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        med2 = new MedicalRecord("Tony", "Cooper", "09/18/2015",
                Arrays.asList("noxidian:100mg"), Arrays.asList(""));
        med3 = new MedicalRecord("Eric", "Cadigan", "02/07/1991",
                Arrays.asList(""), Arrays.asList("peanut"));
    }

    @Test
    @Tag("PersonsByStation")
    @DisplayName("Given a station number, when getPersonsByStation, then return expected persons by station with correct adult" +
            " and child number")
    public void givenAStationNumber_whenGetPersonsByStation_thenReturnExpectedPersonsByStation() {
        when(personService.getPersonList()).thenReturn(personList);
        when(fireStationService.getAddressesByStation(2)).thenReturn(Arrays.asList("1509 Culver St"));
        when(medicalRecordService.getMedicalRecordById("John", "Boyd")).thenReturn(med1);
        when(medicalRecordService.getMedicalRecordById("Tony", "Cooper")).thenReturn(med2);
        when(ageCalculator.getAge(LocalDate.of(1984,3,6))).thenReturn(36);
        when(ageCalculator.getAge(LocalDate.of(2015,9,18))).thenReturn(5);

        PersonsByStationDTO result = alertsService.getPersonsByStation(2);

        assertThat(result.getPersonsByStation().size()).isEqualTo(2);
        assertThat(result.getAdultNumber()).isEqualTo(1);
        assertThat(result.getChildNumber()).isEqualTo(1);
        InOrder inOrder = inOrder(personService, fireStationService,medicalRecordService, ageCalculator);
        inOrder.verify(personService).getPersonList();
        inOrder.verify(fireStationService).getAddressesByStation(anyInt());
        inOrder.verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
        inOrder.verify(ageCalculator).getAge(any(LocalDate.class));
    }

    @Test
    @Tag("PersonInfo")
    @DisplayName("Given a person Id, when getInfoByIdentity, then result should match expected person info")
    public void givenAPersonId_whenGetInfoByIdentity_thenReturnExpectedPersonInfo() {
        when(personService.getPersonList()).thenReturn(personList);
        when(medicalRecordService.getMedicalRecordById(anyString(), anyString())).thenReturn(med1);
        when(ageCalculator.getAge(any(LocalDate.class))).thenReturn(36);

        PersonInfoDTO result = alertsService.getInfoByIdentity("John", "Boyd");

        assertThat(result).isNotNull();
        assertThat(result.getPersonsInfo().get(0).getAge()).isEqualTo(36);
        InOrder inOrder = inOrder(personService, medicalRecordService, ageCalculator);
        inOrder.verify(personService).getPersonList();
        inOrder.verify(medicalRecordService).getMedicalRecordById(anyString(), anyString());
        inOrder.verify(ageCalculator).getAge(any(LocalDate.class));
    }

    @Test
    @Tag("PhoneAlert")
    @DisplayName("Given an expected phones list, when getPhonesByStation, then result should match expected phones list")
    public void givenExpectedPhonesList_whenGetPhonesByStation_thenReturnExpectedPhonesList() {
        List<String> expectedPhones = Arrays.asList("841-874-6512", "841-874-6874", "841-874-7458");
        when(personService.getPersonList()).thenReturn(personList);
        when(fireStationService.getAddressesByStation(2)).thenReturn(Arrays.asList("1509 Culver St", "951 LoneTree Rd"));

        PhoneAlertDTO result = alertsService.getPhonesByStation(2);

        assertThat(result).isNotNull();
        assertThat(result.getPhones()).isEqualTo(expectedPhones);
        InOrder inOrder = inOrder(personService, fireStationService);
        inOrder.verify(personService).getPersonList();
        inOrder.verify(fireStationService).getAddressesByStation(anyInt());
    }

    @Test
    @Tag("CommunityEmail")
    @DisplayName("Given an expected emails list, when getEmailsByCity, then result should match expected emails list")
    public void givenExpectedEmailsList_whenGetEmailsByCity_thenReturnExpectedEmails() {
        List<String> expectedEmails = Arrays.asList("jaboyd@email.com", "tcoop@ymail.com", "gramps@email.com");

        when(personService.getPersonsByCity(anyString())).thenReturn(personList);

        CommunityEmailDTO result = alertsService.getEmailsByCity(anyString());

        assertThat(result).isNotNull();
        assertThat(result.getEmails()).isEqualTo(expectedEmails);
        verify(personService).getPersonsByCity(anyString());
    }

    @Test
    @Tag("ChildAlert")
    @DisplayName("Given an address, when getChildByAddress, then result should match expected child and home members")
    public void givenAnAddress_whenGetChildByAddress_thenReturnExpectedChildAndHomeMembers() {
        when(personService.getPersonsByAddress(anyString())).thenReturn(Arrays.asList(person1,
                person2));
        when(medicalRecordService.getMedicalRecordById("John", "Boyd")).thenReturn(med1);
        when(medicalRecordService.getMedicalRecordById("Tony", "Cooper")).thenReturn(med2);
        when(ageCalculator.getAge(LocalDate.of(1984,3,6))).thenReturn(36);
        when(ageCalculator.getAge(LocalDate.of(2015,9,18))).thenReturn(5);

        ChildAlertDTO result = alertsService.getChildByAddress("1509 Culver St");

        assertThat(result.getChild().size()).isEqualTo(1);
        assertThat(result.getHomeMembers().size()).isEqualTo(1);
        verify(personService).getPersonsByAddress(anyString());
        verify(medicalRecordService, times(2)).getMedicalRecordById(anyString(), anyString());
        verify(ageCalculator,times(2)).getAge(any(LocalDate.class));
    }

    @Test
    @Tag("Fire")
    @DisplayName("Given an address, when getPersonsByAddress, then result should match expected persons by address and station number")
    public void givenAnAddress_whenGetPersonsByAddress_thenReturnExpectedPersonsByAddressAndStationNumber() {
        when(personService.getPersonsByAddress("1509 Culver St")).thenReturn(Arrays.asList(person1,
                person2));
        when(medicalRecordService.getMedicalRecordById("John", "Boyd")).thenReturn(med1);
        when(medicalRecordService.getMedicalRecordById("Tony", "Cooper")).thenReturn(med2);
        when(ageCalculator.getAge(LocalDate.of(1984,3,6))).thenReturn(36);
        when(ageCalculator.getAge(LocalDate.of(2015,9,18))).thenReturn(5);
        when(fireStationService.getFireStationByAddress("1509 Culver St")).thenReturn(new FireStation(
                "1509 Culver St", 1));

        FireDTO result = alertsService.getPersonsByAddress("1509 Culver St");

        assertThat(result.getStation()).isEqualTo(1);
        assertThat(result.getPersons().size()).isEqualTo(2);
        verify(personService).getPersonsByAddress(anyString());
        verify(medicalRecordService, times(2)).getMedicalRecordById(anyString(), anyString());
        verify(ageCalculator, times(2)).getAge(any(LocalDate.class));
        verify(fireStationService).getFireStationByAddress(anyString());
    }

    @Test
    @Tag("Flood")
    @DisplayName("Given a stations list, when getHouseholdsByStation, then result should match expected households by station")
    public void givenAStationList_whenGetHouseholdsByStation_thenReturnExpectedHouseholdsByStation() {
        when(fireStationService.getAddressesByStation(3)).thenReturn(Arrays.asList("1509 Culver St"));
        when(fireStationService.getAddressesByStation(1)).thenReturn(Arrays.asList("951 LoneTree Rd"));
        when(personService.getPersonsByAddress("1509 Culver St")).thenReturn(Arrays.asList(person1, person2));
        when(personService.getPersonsByAddress("951 LoneTree Rd")).thenReturn(Arrays.asList(person3));
        when(medicalRecordService.getMedicalRecordById("John", "Boyd")).thenReturn(med1);
        when(medicalRecordService.getMedicalRecordById("Tony", "Cooper")).thenReturn(med2);
        when(medicalRecordService.getMedicalRecordById("Eric", "Cadigan")).thenReturn(med3);
        when(ageCalculator.getAge(any(LocalDate.class))).thenReturn(anyInt());

        FloodDTO result = alertsService.getHouseholdsByStation(Arrays.asList(3, 1));

        assertThat(result.getHouseholdsByStation()).size().isEqualTo(2);
        assertThat(result.getHouseholdsByStation().get(0).getStation()).isEqualTo(3);
        verify(fireStationService, times(2)).getAddressesByStation(anyInt());
        verify(personService, times(2)).getPersonsByAddress(anyString());
        verify(medicalRecordService, times(3)).getMedicalRecordById(anyString(), anyString());
        verify(ageCalculator, times(3)).getAge(any(LocalDate.class));
    }
}
