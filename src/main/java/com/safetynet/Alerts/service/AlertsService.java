package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.model.*;
import com.safetynet.Alerts.util.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertsService {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static AgeCalculator ageCalculator = new AgeCalculator();
    private static final int adultAge = 19;

    public PersonsByStationDTO getPersonsByStation(int station) {
        int adultCount = 0;
        int childCount = 0;

        List<Person> persons = personService.getPersonList();
        List<String> addresses = fireStationService.getAddressesByStation(station);
        List<PersonStation> list = new ArrayList<>();

        for (Person pers : persons) {
            for (String address : addresses) {
                if (pers.getAddress().equals(address)) {
                    MedicalRecord med = medicalRecordService.getMedicalRecordById(pers.getFirstName(), pers.getLastName());
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

    public ChildAlertDTO getChildByAddress(String address) {
        List<Person> personsByAddress = personService.getPersonsByAddress(address);
        List<Child> childList = new ArrayList<>();
        List<String> adultList = new ArrayList<>();

        for (Person pers : personsByAddress) {
            MedicalRecord med = medicalRecordService.getMedicalRecordById(pers.getFirstName(), pers.getLastName());
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
}
