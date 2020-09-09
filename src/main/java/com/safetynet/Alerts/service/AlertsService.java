package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.model.*;
import com.safetynet.Alerts.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertsService implements IAlertsService {

    private static final Logger LOGGER = LogManager.getLogger(AlertsService.class);

    private final IPersonService personService;

    private final IFireStationService fireStationService;

    private final IMedicalRecordService medicalRecordService;

    private final AgeCalculator ageCalculator;

    private static final int adultAge = 19;

    @Autowired
    public AlertsService(PersonService personService, FireStationService fireStationService, MedicalRecordService
            medicalRecordService, AgeCalculator ageCalculator) {
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
        this.ageCalculator = ageCalculator;
    }

    public PersonsByStationDTO getPersonsByStation(int station) {
        LOGGER.debug("Inside AlertsService.getPersonsByStation for station number : " +station);
        int adultCount = 0;
        int childCount = 0;

        List<Person> persons = personService.getPersonList();
        List<String> addresses = fireStationService.getAddressesByStation(station);
        List<PersonStation> list = new ArrayList<>();

        for (Person pers : persons) {
            for (String address : addresses) {
                if (pers.getAddress().equals(address)) {
                    MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                            pers.getLastName());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                    LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
                    int age = ageCalculator.getAge(birthDate);
                    if (age < adultAge) {
                        childCount++;
                    } else {
                        adultCount++;
                    }
                    list.add(new PersonStation(pers.getFirstName(), pers.getLastName(), pers.getAddress(),
                            pers.getPhone()));
                }
            }
        }

        return new PersonsByStationDTO(list, adultCount, childCount);
    }

    public PersonInfoDTO getInfoByIdentity(String firstName, String lastName) {
        LOGGER.debug("Inside AlertsService.getInfoByIdentity for : " +firstName, lastName);
        List<Person> persons = personService.getPersonList();
        List<PersonInfo> personsInfo = new ArrayList<>();

        for (Person pers : persons) {

            if (pers.getLastName().equals(lastName)) {
                MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                        pers.getLastName());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
                int age = ageCalculator.getAge(birthDate);

                personsInfo.add(new PersonInfo(pers.getLastName(), pers.getAddress(),
                        age, pers.getEmail(), med.getMedications(), med.getAllergies()));
            }
        }

        return new PersonInfoDTO(personsInfo);
    }

    public PhoneAlertDTO getPhonesByStation(int station) {
        LOGGER.debug("Inside AlertsService.getPhonesByStation for station : " +station);
        List<Person> persons = personService.getPersonList();
        List<String> addresses = fireStationService.getAddressesByStation(station);
        List<String> phones = new ArrayList<>();

        for (Person pers : persons) {
            for (String address : addresses) {
                if (pers.getAddress().equals(address)) {
                    phones.add(pers.getPhone());
                }
            }
        }

        return new PhoneAlertDTO(phones);
    }

    public CommunityEmailDTO getEmailsByCity(String city) {
        LOGGER.debug("Inside FireStation.getEmailsByCity method for city : " +city);
        List<Person> personsByCity = personService.getPersonsByCity(city);
        List<String> emails = new ArrayList<>();

        for (Person person : personsByCity) {
            emails.add(person.getEmail());
        }

        return new CommunityEmailDTO(emails);
    }

    public ChildAlertDTO getChildByAddress(String address) {
        LOGGER.debug("Inside AlertsService.getChildByAddress for adress : " +address);
        List<Person> personsByAddress = personService.getPersonsByAddress(address);
        List<Child> childList = new ArrayList<>();
        List<String> adultList = new ArrayList<>();

        for (Person pers : personsByAddress) {
            MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                    pers.getLastName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
            int age = ageCalculator.getAge(birthDate);

            if (age < adultAge) {
                childList.add(new Child(pers.getFirstName(), pers.getLastName(), age));
            } else {
                adultList.add("FirstName : " + pers.getFirstName() + " LastName : " + pers.getLastName());
            }
        }

        return new ChildAlertDTO(childList, adultList);
    }

    public FireDTO getPersonsByAddress(String address) {
        LOGGER.debug("Inside AlertsService.getPersonsByAddress for address : " +address);
        List<Person> personsByAddress = personService.getPersonsByAddress(address);
        List<PersonAddress> persons = new ArrayList<>();

        for (Person pers : personsByAddress) {
            MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                    pers.getLastName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
            int age = ageCalculator.getAge(birthDate);
            persons.add(new PersonAddress(pers.getLastName(), pers.getPhone(),
                    age, med.getMedications(), med.getAllergies()));
        }

        FireStation fireStation = fireStationService.getFireStationByAddress(address);
        int station = fireStation.getStation();

        return new FireDTO(station, persons);
    }

    public FloodDTO getHouseholdsByStation(List<Integer> stations) {
        LOGGER.debug("Inside AlertsService.getHouseholdsByStation for stations : " +stations);
        List<HouseholdsByStationDTO> householdsByStationDTO = new ArrayList<>();
        List<String> addressAll = new ArrayList<>();

        for (int station : stations) {
            List<String> addressesByStation = fireStationService.getAddressesByStation(station);
            List<Household> households = new ArrayList<>();

            for (String address : addressesByStation) {
                if (!addressAll.contains(address)) {
                    addressAll.add(address);
                    List<Person> persons = personService.getPersonsByAddress(address);
                    List<PersonAddress> personAddresses = new ArrayList<>();

                    for (Person pers : persons) {
                        MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                                pers.getLastName());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                        LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
                        int age = ageCalculator.getAge(birthDate);
                        personAddresses.add(new PersonAddress(pers.getLastName(), pers.getPhone(),
                                age, med.getMedications(), med.getAllergies()));
                    }
                    households.add(new Household(address, personAddresses));
                }
            }
            householdsByStationDTO.add(new HouseholdsByStationDTO(station, households));
        }

        return new FloodDTO(householdsByStationDTO);
    }
}
