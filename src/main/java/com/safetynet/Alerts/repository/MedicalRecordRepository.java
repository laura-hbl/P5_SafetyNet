package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.DataStore;
import com.safetynet.Alerts.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * MedicalRecordRepository class.
 * Contains set of methods to interact with medicalRecordsMap, a HashMap where each MedicalRecord retrieved
 * from data store is mapped, firstName+lastName of the person whose medicalRecord belongs to as key identifier and
 * the MedicalRecord object as value.
 *
 * @author Laura Habdul
 */
@Repository
public class MedicalRecordRepository {

    /**
     * MedicalRecordRepository logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordRepository.class);

    /**
     * Creates a HashMap instance to map MedicalRecords data.
     */
    private final Map<String, MedicalRecord> medicalRecordsMap = new HashMap<>();

    /**
     * Constructor of class MedicalRecordRepository.
     * Adds each MedicalRecord from DataStore to a HashMap, firstName+lastName of the person whose
     * medicalRecord belongs to as key identifier and the MedicalRecord object as value.
     *
     * @param dataStore DataStore instance
     */
    @Autowired
    public MedicalRecordRepository(final DataStore dataStore) {
        LOGGER.debug("Map MedicalRecordList");
        dataStore.getMedicalRecordList().forEach(med -> medicalRecordsMap.put(med.getFirstName()
                + med.getLastName(), med));
    }

    /**
     * Adds the given MedicalRecord to medicalRecordsMap.
     *
     * @param med the medicalRecord to be saved
     * @return The medicalRecord saved
     */
    public MedicalRecord save(final MedicalRecord med) {
        LOGGER.debug("Inside MedicalRecordRepository.save for : " + med.getFirstName(), med.getLastName());
        medicalRecordsMap.put(med.getFirstName() + med.getLastName(), med);

        return medicalRecordsMap.get(med.getFirstName() + med.getLastName());
    }

    /**
     * Deletes the given MedicalRecord from medicalRecordsMap.
     *
     * @param med the medicalRecord to be deleted
     */
    public void delete(final MedicalRecord med) {
        LOGGER.debug("Inside MedicalRecordRepository.delete for : " + med.getFirstName(), med.getLastName());
        medicalRecordsMap.remove(med.getFirstName() + med.getLastName());
    }

    /**
     * Gets the MedicalRecord with the given key Id from medicalRecordsMap.
     *
     * @param firstName firstName of the person whose medicalRecord is to be retrieved
     * @param lastName  lastName of the person whose medicalRecord is to be retrieved
     * @return The MedicalRecord retrieved
     */
    public MedicalRecord findByIdentity(final String firstName, String lastName) {
        LOGGER.debug("Inside MedicalRecordRepository.findByIdentity for : " + firstName, lastName);
        return medicalRecordsMap.get(firstName + lastName);
    }
}
