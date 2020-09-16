package com.safetynet.Alerts.integration;

import com.safetynet.Alerts.data.DataStore;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStoreIT {

    @Autowired
    private DataStore dataStore;

    @Test
    public void whenGetPersonList_thenReturnExpectedPersonList() {
        List<Person> personList = dataStore.getPersonList();

        assertThat(personList).isNotEmpty();
        assertThat(personList.size()).isEqualTo(23);
    }

    @Test
    public void whenGetFireStationList_thenReturnExpectedFireStationList() {
        List<FireStation> fireStationList = dataStore.getFireStationList();

        assertThat(fireStationList).isNotEmpty();
        assertThat(fireStationList.size()).isEqualTo(13);
    }

    @Test
    public void whenGetMedicalRecordList_thenReturnExpectedMedicalRecordList() {
        List<MedicalRecord> medicalRecordList = dataStore.getMedicalRecordList();

        assertThat(medicalRecordList).isNotEmpty();
        assertThat(medicalRecordList.size()).isEqualTo(23);
    }
}
