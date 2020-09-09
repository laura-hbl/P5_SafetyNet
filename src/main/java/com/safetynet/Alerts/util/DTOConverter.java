package com.safetynet.Alerts.util;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    public PersonDTO toPersonDTO(final Person person) {

        return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
                person.getCity(), person.getZip(), person.getPhone(), person.getEmail());
    }

    public MedicalRecordDTO toMedicalRecordDTO(final MedicalRecord med) {

        return new MedicalRecordDTO(med.getFirstName(), med.getLastName(), med.getBirthDate(),
                med.getMedications(), med.getAllergies());
    }

    public FireStationDTO toFireStationDTO(final FireStation fireStation) {

        return  new FireStationDTO(fireStation.getAddress(), fireStation.getStation());
    }
}
