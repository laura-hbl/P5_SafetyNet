package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.CommunityEmailDTO;
import com.safetynet.Alerts.dto.PhoneAlertDTO;
import com.safetynet.Alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PhoneAlertDTO getPhonesByStation(int station) {
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
        List<Person> personsByCity = personService.getPersonsByCity(city);
        List<String> emails = new ArrayList<>();

        for (Person person : personsByCity) {
            emails.add(person.getEmail());
        }

        return new CommunityEmailDTO(emails);
    }
}
