package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    StoredData storedData;

    public void save(MedicalRecord medicalRecord) {
        storedData.getMedicalRecordList().add(medicalRecord);
    }

    public void delete(MedicalRecord medicalRecord) {
        storedData.getMedicalRecordList().remove(medicalRecord);
    }

    public MedicalRecord findByIdentity(String firstName, String lastName) {
        List<MedicalRecord> medicalRecords = storedData.getMedicalRecordList();
        for (MedicalRecord medicalRecord : medicalRecords) {

            if (medicalRecord.getFirstName().equals(firstName)
                    && medicalRecord.getLastName().equals(lastName)) {

                return medicalRecord;
            }
        }
        return null;
    }

}
