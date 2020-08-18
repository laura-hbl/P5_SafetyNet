package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MedicalRecordRepository {

    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordRepository.class);

    Map<String, MedicalRecord> medicalRecordsMap = new HashMap<>();

    @Autowired
    public MedicalRecordRepository(StoredData storedData) {
        LOGGER.debug("Map MedicalRecordList");
        storedData.getMedicalRecordList().forEach(med -> medicalRecordsMap.put(med.getFirstName()
                + med.getLastName(), med));
    }

    public MedicalRecord save(MedicalRecord med) {
        LOGGER.debug("Inside MedicalRecordRepository.save for : " +med.getFirstName(), med.getLastName());
        medicalRecordsMap.put(med.getFirstName() + med.getLastName(), med);

        return med;
    }

    public void delete(MedicalRecord med) {
        LOGGER.debug("Inside MedicalRecordRepository.delete for : " +med.getFirstName(), med.getLastName());
        medicalRecordsMap.remove(med.getFirstName() + med.getLastName());
    }

    public MedicalRecord findByIdentity(String firstName, String lastName) {
        LOGGER.debug("Inside MedicalRecordRepository.findByIdentity for : " +firstName, lastName);
        return medicalRecordsMap.get(firstName + lastName);
    }

}
