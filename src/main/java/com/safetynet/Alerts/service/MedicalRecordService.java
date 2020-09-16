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

/**
 * Contains methods that allow interaction between MedicalRecord business logic and MedicalRecord repository.
 *
 * @author Laura Habdul
 * @see IMedicalRecordService
 */
@Service
public class MedicalRecordService implements IMedicalRecordService {

    /**
     * MedicalRecordService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordService.class);

    /**
     * MedicalRecordRepository instance.
     */
    private final MedicalRecordRepository medicalRecordRepository;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * Constructor of class MedicalRecordService.
     * Initialize medicalRecordRepository, modelConverter and dtoConverter.
     *
     * @param medicalRecordRepository MedicalRecordRepository instance
     * @param modelConverter          ModelConverter instance
     * @param dtoConverter            DTOConverter instance
     */
    @Autowired
    public MedicalRecordService(final MedicalRecordRepository medicalRecordRepository, final ModelConverter
            modelConverter, final DTOConverter dtoConverter) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Calls MedicalRecordRepository's findByIdentity method to check if the given medical record to add is already
     * registered, if so DataAlreadyRegisteredException is thrown. Else, it is converted to model object by calling
     * ModelConverter's toMedicalRecord and then saved by calling MedicalRecordRepository's save method.
     *
     * @param med the medical record to be saved
     * @return The medical record saved converted to a medicalRecordDTO object
     */
    public MedicalRecordDTO createMedicalRecord(final MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.createMedicalRecord for: " + med.getFirstName(), med.getLastName());

        MedicalRecord medFound = medicalRecordRepository.findByIdentity(med.getFirstName(),
                med.getLastName());

        if (medFound != null) {
            throw new DataAlreadyRegisteredException("MedicalRecord already registered");
        }
        MedicalRecord medToSave = modelConverter.toMedicalRecord(med);
        MedicalRecord medSaved = medicalRecordRepository.save(medToSave);

        return dtoConverter.toMedicalRecordDTO(medSaved);
    }

    /**
     * Calls MedicalRecordRepository's findByIdentity method to check if the given medical record to update is
     * registered, if so it is updated. Else, DataNotFoundException is thrown.
     *
     * @param med the medical record to be updated
     * @return The medical record updated converted to a medicalRecordDTO object
     */
    public MedicalRecordDTO updateMedicalRecord(final MedicalRecordDTO med) {
        LOGGER.debug("Inside MedicalRecordService.updateMedicalRecord for: " + med.getFirstName(), med.getLastName());
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

    /**
     * Calls MedicalRecordRepository's findByIdentity method to check if the given medical record to delete is
     * registered, if so it is deleted by calling MedicalRecordRepository's delete method.
     * Else, DataNotFoundException is thrown.
     *
     * @param firstName firstName of the person whose medical record belong to
     * @param lastName  lastName of the person whose medical record belong to
     */
    public void deleteMedicalRecord(final String firstName, final String lastName) {
        LOGGER.debug("Inside MedicalRecordService.deleteMedicalRecord for {} {}", firstName, lastName);
        MedicalRecord medicalRecordToDelete = medicalRecordRepository.findByIdentity(firstName, lastName);

        if (medicalRecordToDelete == null) {
            throw new DataNotFoundException("MedicalRecord not found");
        }

        medicalRecordRepository.delete(medicalRecordToDelete);
    }

    /**
     * Retrieves the medical record of the person with the given identity by calling MedicalRecordRepository's
     * findByIdentity method and returned by being first converted to a model object by calling ModelConverter's
     * toMedicalRecord. If it is not found, DataNotFoundException is thrown.
     *
     * @param firstName firstName of the person whose medical record belong to
     * @param lastName  lastName of the person whose medical record belong to
     * @return the medicalRecord retrieved converted to a medicalRecordDTO object
     */
    public MedicalRecordDTO getMedicalRecordById(final String firstName, final String lastName) {
        LOGGER.debug("Inside MedicalRecordService.getMedicalRecordByID for {} {}",
                firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findByIdentity(firstName, lastName);

        if (medicalRecord == null) {
            throw new DataNotFoundException("Failed to get medicalRecord for : " + firstName + " " + lastName);
        }

        return dtoConverter.toMedicalRecordDTO(medicalRecord);
    }
}
