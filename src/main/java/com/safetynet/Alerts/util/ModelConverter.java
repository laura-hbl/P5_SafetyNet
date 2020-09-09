package com.safetynet.Alerts.util;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public Person toPerson(final PersonDTO personDTO) {

        return new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAddress(),
                personDTO.getCity(), personDTO.getZip(), personDTO.getPhone(), personDTO.getEmail());
    }

    public MedicalRecord toMedicalRecord(final MedicalRecordDTO medDTO) {

        return new MedicalRecord(medDTO.getFirstName(), medDTO.getLastName(), medDTO.getBirthDate(),
                medDTO.getMedications(), medDTO.getAllergies());
    }

    public FireStation toFireStation(final FireStationDTO fireStationDTO) {

        return new FireStation(fireStationDTO.getAddress(), fireStationDTO.getStation());
    }
}
