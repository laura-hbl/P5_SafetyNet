package com.safetynet.Alerts.data;

import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;

import java.util.List;

public class StoredData {

    private List<Person> personList;
    private List<FireStation> fireStationList;
    private List<MedicalRecord> medicalRecordList;

    public StoredData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord>
            medicalRecordList) {
        this.personList = personList;
        this.fireStationList = fireStationList;
        this.medicalRecordList = medicalRecordList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public List<FireStation> getFireStationList() {
        return fireStationList;
    }

    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }

}
