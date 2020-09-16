package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;

public interface IMedicalRecordService {

    /**
     * Saves a new medical record.
     *
     * @param med the medical record to be saved
     * @return The medical record saved converted to a medicalRecordDTO object
     */
    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO med);

    /**
     * Updates a registered medical record.
     *
     * @param med the medical record to be updated
     * @return The medical record updated converted to a medicalRecordDTO object
     */
    MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO med);

    /**
     * Deletes the medical record of the person with the given identity.
     *
     * @param firstName firstName of the person whose medical record belong to
     * @param lastName  lastName of the person whose medical record belong to
     */
    void deleteMedicalRecord(String firstName, String lastName);

    /**
     * Retrieves the medical record of the person with the given identity.
     *
     * @param firstName firstName of the person whose medical record belong to
     * @param lastName  lastName of the person whose medical record belong to
     * @return the medicalRecord retrieved converted to a medicalRecordDTO object
     */
    MedicalRecordDTO getMedicalRecordById(String firstName, String lastName);
}
