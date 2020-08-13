package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.model.MedicalRecord;

public interface IMedicalRecordService {

    MedicalRecord createMedicalRecord(MedicalRecordDTO medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecord);

    MedicalRecord getMedicalRecordById(String firstName, String lastName);
}
