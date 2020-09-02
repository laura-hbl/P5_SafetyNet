package com.safetynet.Alerts.unit.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.repository.MedicalRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicalRecordRepositoryTest {

    private MedicalRecordRepository medicalRecordRepository;

    private static MedicalRecord med1;
    private static MedicalRecord med2;

    @Before
    public void setUp() {
        med1 = new MedicalRecord("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        med2 = new MedicalRecord("Tony", "Cooper", "04/20/2002",
                Arrays.asList("ibupurin:200mg"), Arrays.asList("peanut"));

        StoredData storedData = new StoredData();
        storedData.setMedicalRecordList(Arrays.asList(med1, med2));

        medicalRecordRepository = new MedicalRecordRepository(storedData);
    }

    @Test
    @Tag("Save")
    @DisplayName("Given a MedicalRecord, when save, then MedicalRecord should be saved successfully")
    public void givenAMedicalRecord_whenSave_thenMedicalRecordShouldBeSaveCorrectly() {
        MedicalRecord med3 = new MedicalRecord("Paul", "Duncan", "02/11/1980",
                Arrays.asList(""), Arrays.asList("shellfish"));

        MedicalRecord medSaved = medicalRecordRepository.save(med3);

        assertThat(medSaved).isEqualTo(med3);
    }

    /*@Test
    @Tag("Save")
    @DisplayName("Given a null MedicalRecord, when save, then throw NullPointerException")
    public void givenANullMedicalRecord_whenSave_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> medicalRecordRepository.save(null));
    }*/

    @Test
    @Tag("Delete")
    @DisplayName("Given a MedicalRecord, when delete, then MedicalRecord should be deleted successfully")
    public void givenAMedicalRecord_whenDelete_thenMedicalRecordShouldBeDeleteCorrectly() {
        medicalRecordRepository.delete(med1);

        assertThat(medicalRecordRepository.findByIdentity("John", "Boyd")).isEqualTo(null);
    }

    /*@Test
    @Tag("Delete")
    @DisplayName("Given a null MedicalRecord, when delete, then throw NullPointerException")
    public void givenANullMedicalRecord_whenDelete_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> medicalRecordRepository.delete(null));
    }*/

    @Test
    @Tag("FindByIdentity")
    @DisplayName("Given a person Id, when findByIdentity, then medicalRecord of that person should be returned")
    public void givenAPersonIdentity_whenFindByIdentity_thenReturnExpectedMedicalRecordOfThisPerson() {
        MedicalRecord medFound = medicalRecordRepository.findByIdentity("John", "Boyd");

        assertThat(medFound).isEqualTo(med1);
    }

    @Test
    @Tag("FindByIdentity")
    @DisplayName("Given an unregistered person, when findByIdentity, then return null")
    public void givenAnUnRegisteredIdentity_whenFindByIdentity_thenReturnNull() {
        MedicalRecord medFound = medicalRecordRepository.findByIdentity("Paul", "Duncan");

        assertThat(medFound).isEqualTo(null);
    }

    /*@Test
    public void givenAnEmptyFirstName_whenFindByIdentity_thenReturnNull() {
        MedicalRecord medFound = medicalRecordRepository.findByIdentity("", "Boyd");

        assertThat(medFound).isEqualTo(null);
    }

    @Test
    public void givenAnEmptyLastName_whenFindByIdentity_thenReturnNull() {
        MedicalRecord medFound = medicalRecordRepository.findByIdentity("John", "");

        assertThat(medFound).isEqualTo(null);
    }

    @Test
    public void givenANullFirstName_whenFindByIdentity_thenReturnNull() {
        MedicalRecord medFound = medicalRecordRepository.findByIdentity(null, "Boyd");

        assertThat(medFound).isEqualTo(null);
    }

    @Test
    public void givenANullLastName_whenFindByIdentity_thenReturnNull() {
        MedicalRecord medFound = medicalRecordRepository.findByIdentity("John", null);

        assertThat(medFound).isEqualTo(null);
    }*/
}
