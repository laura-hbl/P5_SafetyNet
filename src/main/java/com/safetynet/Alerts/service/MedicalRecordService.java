package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordService.class);

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecord createMedicalRecord(MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.createMedicalRecord for: " +med.getFirstName(), med.getLastName());
        MedicalRecord medicalRecordToSave = new MedicalRecord(med.getFirstName(), med.getLastName(),
                med.getBirthDate(), med.getMedications(), med.getAllergies());
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdentity(med.getFirstName(),
                med.getLastName());

        if (medicalRecord != null) {
            throw new DataAlreadyRegisteredException("MedicalRecord already registered");
        }

        return medicalRecordRepository.save(medicalRecordToSave);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.updateMedicalRecord for: " +med.getFirstName(), med.getLastName());
        MedicalRecord medicalRecordToUpdate = medicalRecordRepository.findByIdentity(med.getFirstName(),
                med.getLastName());

        if (medicalRecordToUpdate == null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }
        medicalRecordToUpdate.setBirthDate(med.getBirthDate());
        medicalRecordToUpdate.setMedications(med.getMedications());
        medicalRecordToUpdate.setAllergies(med.getAllergies());
        return medicalRecordToUpdate;
    }

    public void deleteMedicalRecord(MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.deleteMedicalRecord for: "+med.getFirstName(), med.getLastName());
        MedicalRecord medicalRecordToDelete = medicalRecordRepository.findByIdentity(med.getFirstName(),
                med.getLastName());

        if (medicalRecordToDelete== null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }

        medicalRecordRepository.delete(medicalRecordToDelete);
    }

    public MedicalRecord getMedicalRecordById(String firstName, String lastName) {
        LOGGER.debug("Inside MedicalRecordService.getMedicalRecordByID for {} {}",
                firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdentity(firstName, lastName);

        if (medicalRecord == null) {
            throw new DataNotFoundException("Failed to get medicalRecord for : "+firstName +" "+lastName);
        }

        return medicalRecord;
    }
}
