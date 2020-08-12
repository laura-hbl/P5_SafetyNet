package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MedicalRecordRepository {

    Map<String, MedicalRecord> medicalRecordsMap = new HashMap<>();

    @Autowired
    public MedicalRecordRepository(StoredData storedData) {
        storedData.getMedicalRecordList().forEach(med -> medicalRecordsMap.put(med.getFirstName()
                + med.getLastName(), med));
    }

    public MedicalRecord save(MedicalRecord med) {
        medicalRecordsMap.put(med.getFirstName() + med.getLastName(), med);

        return med;
    }

    public void delete(MedicalRecord med) {
        medicalRecordsMap.remove(med.getFirstName() + med.getLastName());
    }

    public MedicalRecord findByIdentity(String firstName, String lastName) {
        return medicalRecordsMap.get(firstName + lastName);
    }

}
