package com.safetynet.Alerts.unit.util;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.util.ModelConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    @Tag("Valid")
    @DisplayName("Given a PersonDTO, when ToPerson, then result should match expected Person")
    public void givenAPersonDTO_whenToPerson_thenReturnExpectedPerson() {
        PersonDTO personDTO = new PersonDTO("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
        Person expectedPerson = new Person("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");

        Person result = modelConverter.toPerson(personDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedPerson);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null PersonDTO, then toPerson should raise an NullPointerException")
    public void givenAnNullPersonDTO_whenToPerson_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toPerson(null));
    }

    @Test
    @DisplayName("Given a MedicalRecordDTO, when ToMedicalRecord, then result should match expected MedicalRecord")
    public void givenAMedicalRecordDTO_whenToMedicalRecord_thenReturnExpectedMedicalRecord() {
        MedicalRecordDTO medDTO = new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        MedicalRecord expectedMed = new MedicalRecord("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));

        MedicalRecord result = modelConverter.toMedicalRecord(medDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedMed);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null MedicalRecordDTO, then toMedicalRecord should raise an NullPointerException")
    public void givenAnNullMedicalRecordDTO_whenToMedicalRecord_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toMedicalRecord(null));
    }

    @Test
    @DisplayName("Given a FireStationDTO, when ToFireStation, then result should match expected FireStation")
    public void givenAFireStationDTO_whenToFireStation_thenReturnExpectedFireStation() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        FireStation expectedFire = new FireStation("29 15th St", 2);

        FireStation result = modelConverter.toFireStation(fireDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedFire);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null FireStationDTO, then toFireStation should raise an NullPointerException")
    public void givenAnNullFireStationDTO_whenToFireStation_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toFireStation(null));
    }
}
