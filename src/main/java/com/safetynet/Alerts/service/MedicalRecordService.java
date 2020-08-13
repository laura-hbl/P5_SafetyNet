package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord createMedicalRecord(MedicalRecordDTO med) {
        MedicalRecord medicalRecordToSave = new MedicalRecord(med.getFirstName(), med.getLastName(), med.getBirthDate(),
                med.getMedications(), med.getAllergies());
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdentity(med.getFirstName(), med.getLastName());

        if (medicalRecord == null) {
            medicalRecordRepository.save(medicalRecordToSave);
            return medicalRecordToSave;
        }

        return null;
    }

    public MedicalRecord updateMedicalRecord(MedicalRecordDTO med) {

        MedicalRecord medicalRecordToUpdate = medicalRecordRepository.findByIdentity(med.getFirstName(), med.getLastName());

        if (medicalRecordToUpdate != null) {
            medicalRecordToUpdate.setBirthDate(med.getBirthDate());
            medicalRecordToUpdate.setMedications(med.getMedications());
            medicalRecordToUpdate.setAllergies(med.getAllergies());
            return medicalRecordToUpdate;
        }

        return null;
    }

    public void deleteMedicalRecord(MedicalRecordDTO med) {
        MedicalRecord medicalRecordToDelete = medicalRecordRepository.findByIdentity(med.getFirstName(), med.getLastName());

        if (medicalRecordToDelete != null) {
            medicalRecordRepository.delete(medicalRecordToDelete);
        }
    }

    public MedicalRecord getMedicalRecordById(String firstName, String lastName) {

       MedicalRecord medicalRecord = medicalRecordRepository.findByIdentity(firstName, lastName);

       if (medicalRecord != null) {
           return medicalRecord;
       }

       return null;
    }
}
