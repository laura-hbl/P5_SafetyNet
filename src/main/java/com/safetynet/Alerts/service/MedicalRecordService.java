package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.repository.MedicalRecordRepository;
import com.safetynet.Alerts.util.DTOConverter;
import com.safetynet.Alerts.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordService.class);

    private final MedicalRecordRepository medicalRecordRepository;

    private final ModelConverter modelConverter;

    private final DTOConverter dtoConverter;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, ModelConverter modelConverter, DTOConverter dtoConverter) {
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.createMedicalRecord for: " +med.getFirstName(), med.getLastName());

        MedicalRecord medFound = medicalRecordRepository.findByIdentity(med.getFirstName(),
                med.getLastName());

        if (medFound != null) {
            throw new DataAlreadyRegisteredException("MedicalRecord already registered");
        }
        MedicalRecord medToSave = modelConverter.toMedicalRecord(med);
        MedicalRecord medSaved = medicalRecordRepository.save(medToSave);

        return dtoConverter.toMedicalRecordDTO(medSaved);
    }

    public MedicalRecordDTO updateMedicalRecord(MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.updateMedicalRecord for: " +med.getFirstName(), med.getLastName());
        MedicalRecord medFound = medicalRecordRepository.findByIdentity(med.getFirstName(),
                med.getLastName());

        if (medFound == null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }
        medFound.setBirthDate(med.getBirthDate());
        medFound.setMedications(med.getMedications());
        medFound.setAllergies(med.getAllergies());

        return dtoConverter.toMedicalRecordDTO(medFound);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        LOGGER.debug("Inside MedicalRecordService.deleteMedicalRecord for {} {}", firstName, lastName);
        MedicalRecord medicalRecordToDelete = medicalRecordRepository.findByIdentity(firstName, lastName);

        if (medicalRecordToDelete == null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }

        medicalRecordRepository.delete(medicalRecordToDelete);
    }

    public MedicalRecordDTO getMedicalRecordById(String firstName, String lastName) {
        LOGGER.debug("Inside MedicalRecordService.getMedicalRecordByID for {} {}",
                firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdentity(firstName, lastName);

        if (medicalRecord == null) {
            throw new DataNotFoundException("Failed to get medicalRecord for : "+firstName +" "+lastName);
        }

        return dtoConverter.toMedicalRecordDTO(medicalRecord);
    }
}
