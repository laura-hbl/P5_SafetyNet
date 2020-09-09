package com.safetynet.Alerts.unit.util;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.util.DTOConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @Tag("Valid")
    @DisplayName("Given a Person, when ToPersonDTO, then result should match expected PersonDTO")
    public void givenAPerson_whenToPersonDTO_thenReturnExpectedPersonDTO() {
        PersonDTO expectedPersonDTO = new PersonDTO("John", "Boyd", "1509 Culver St",
                "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        Person person = new Person("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");

        PersonDTO result = dtoConverter.toPersonDTO(person);

        assertThat(result).isEqualToComparingFieldByField(expectedPersonDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null Person, then toPersonDTO should raise an NullPointerException")
    public void givenAnNullPerson_whenToPersonDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toPersonDTO(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given a MedicalRecord, when ToMedicalRecordDTO, then result should match expected MedicalRecordDTO")
    public void givenAMedicalRecord_whenToMedicalRecordDTO_thenReturnExpectedMedicalRecordDTO() {
        MedicalRecordDTO expectedMedDTO = new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        MedicalRecord med = new MedicalRecord("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));

        MedicalRecordDTO result = dtoConverter.toMedicalRecordDTO(med);

        assertThat(result).isEqualToComparingFieldByField(expectedMedDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null MedicalRecord, then toMedicalRecordDTO should raise an NullPointerException")
    public void givenAnNullMedicalRecord_whenToMedicalRecordDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toMedicalRecordDTO(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given a FireStation, when ToFireStationDTO, then result should match expected FireStationDTO")
    public void givenAFireStation_whenToFireStationDTO_thenReturnExpectedFireStationDTO() {
        FireStationDTO expectedFireDTO = new FireStationDTO("29 15th St", 2);
        FireStation fire = new FireStation("29 15th St", 2);

        FireStationDTO result = dtoConverter.toFireStationDTO(fire);

        assertThat(result).isEqualToComparingFieldByField(expectedFireDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null FireStation, then toFireStationDTO should raise an NullPointerException")
    public void givenAnNullFireStation_whenToFireStationDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toFireStationDTO(null));
    }
}
