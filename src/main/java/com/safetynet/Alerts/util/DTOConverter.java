package com.safetynet.Alerts.util;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of DTO class to its equivalent Model class.
 *
 * @author Laura Habdul
 */
@Component
public class DTOConverter {

    /**
     * Converts Person to PersonDTO.
     *
     * @param person Person object to convert
     * @return The PersonDTO object
     */
    public PersonDTO toPersonDTO(final Person person) {

        return new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
                person.getCity(), person.getZip(), person.getPhone(), person.getEmail());
    }

    /**
     * Converts MedicalRecord to MedicalRecordDTO.
     *
     * @param med MedicalRecord object to convert
     * @return The MedicalRecordDTO object
     */
    public MedicalRecordDTO toMedicalRecordDTO(final MedicalRecord med) {

        return new MedicalRecordDTO(med.getFirstName(), med.getLastName(), med.getBirthDate(),
                med.getMedications(), med.getAllergies());
    }

    /**
     * Converts FireStation to FireStationDTO.
     *
     * @param fireStation FireStation object to convert
     * @return The FireStationDTO object
     */
    public FireStationDTO toFireStationDTO(final FireStation fireStation) {

        return new FireStationDTO(fireStation.getAddress(), fireStation.getStation());
    }
}
