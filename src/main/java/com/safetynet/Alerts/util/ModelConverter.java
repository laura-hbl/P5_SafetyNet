package com.safetynet.Alerts.util;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of Model class to its equivalent DTO class.
 *
 * @author Laura Habdul
 */
@Component
public class ModelConverter {

    /**
     * Converts PersonDTO to Person.
     *
     * @param personDTO PersonDTO object to convert
     * @return The Person object
     */
    public Person toPerson(final PersonDTO personDTO) {

        return new Person(personDTO.getFirstName(), personDTO.getLastName(), personDTO.getAddress(),
                personDTO.getCity(), personDTO.getZip(), personDTO.getPhone(), personDTO.getEmail());
    }

    /**
     * Converts MedicalRecordDTO to MedicalRecord.
     *
     * @param medDTO MedicalRecordDTO object to convert
     * @return The MedicalRecord object
     */
    public MedicalRecord toMedicalRecord(final MedicalRecordDTO medDTO) {

        return new MedicalRecord(medDTO.getFirstName(), medDTO.getLastName(), medDTO.getBirthDate(),
                medDTO.getMedications(), medDTO.getAllergies());
    }

    /**
     * Converts FireStationDTO to FireStation.
     *
     * @param fireStationDTO FireStationDTO object to convert
     * @return The FireStation object
     */
    public FireStation toFireStation(final FireStationDTO fireStationDTO) {

        return new FireStation(fireStationDTO.getAddress(), fireStationDTO.getStation());
    }
}
