package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;

public interface IMedicalRecordService {

    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecord);

    MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO medicalRecord);

    void deleteMedicalRecord(String firstName, String lastName);

    MedicalRecordDTO getMedicalRecordById(String firstName, String lastName);
}
