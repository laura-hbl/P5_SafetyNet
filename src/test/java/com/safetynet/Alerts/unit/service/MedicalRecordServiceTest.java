package com.safetynet.Alerts.unit.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.repository.MedicalRecordRepository;
import com.safetynet.Alerts.service.MedicalRecordService;
import com.safetynet.Alerts.util.DTOConverter;
import com.safetynet.Alerts.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepositoryMock;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private static MedicalRecord med;

    private static MedicalRecordDTO medDTO;

    @Before
    public void setUp() {
        medDTO = new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
        med = new MedicalRecord("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan"));
    }

    @Test
    @Tag("CreateMedicalRecord")
    @DisplayName("Given a medicalRecord, when createMedicalRecord, then medicalRecord should be saved correctly")
    public void givenAMedicalRecord_whenCreateMedicalRecord_thenMedicalRecordShouldBeSavedCorrectly() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);
        when(modelConverter.toMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(med);
        when(medicalRecordRepositoryMock.save(any(MedicalRecord.class))).thenReturn(med);
        when(dtoConverter.toMedicalRecordDTO(any(MedicalRecord.class))).thenReturn(medDTO);

        MedicalRecordDTO medCreated = medicalRecordService.createMedicalRecord(medDTO);

        assertNotNull(medCreated);
        assertThat(medCreated).isEqualToComparingFieldByField(medDTO);
        InOrder inOrder = inOrder(medicalRecordRepositoryMock, modelConverter, dtoConverter);
        inOrder.verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(modelConverter).toMedicalRecord(any(MedicalRecordDTO.class));
        inOrder.verify(medicalRecordRepositoryMock).save(any(MedicalRecord.class));
        inOrder.verify(dtoConverter).toMedicalRecordDTO(any(MedicalRecord.class));

    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("CreateMedicalRecord")
    @DisplayName("Given a registered medicalRecord, when createMedicalRecord, then throw DataAlreadyRegisteredException")
    public void givenARegisteredMedicalRecord_whenCreateMedicalRecord_thenDataAlreadyRegisteredExceptionIsThrown() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);

        medicalRecordService.createMedicalRecord(medDTO);
    }

    @Test
    @Tag("UpdateMedicalRecord")
    @DisplayName("Given a registered medicalRecord, when updateMedicalRecord, then medicalRecord should be updated" +
            " correctly")
    public void givenAMedicalRecord_whenUpdateMedicalRecord_thenMedicalRecordShouldBeUpdateCorrectly() {
        medDTO = new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);
        when(dtoConverter.toMedicalRecordDTO(any(MedicalRecord.class))).thenReturn(medDTO);

        MedicalRecordDTO medUpdated = medicalRecordService.updateMedicalRecord(medDTO);

        assertThat(medUpdated.getAllergies().contains("peanut"));
        InOrder inOrder = inOrder(medicalRecordRepositoryMock, dtoConverter);
        inOrder.verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(dtoConverter).toMedicalRecordDTO(any(MedicalRecord.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("UpdateMedicalRecord")
    @DisplayName("If medicalRecord is not registered, when updateMedicalRecord, then throw DataNotFoundException")
    public void givenUnFoundMedicalRecord_whenUpdateMedicalRecord_thenDataNotFoundExceptionIsThrown() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        medicalRecordService.updateMedicalRecord(medDTO);
    }

    @Test
    @Tag("DeleteMedicalRecord")
    @DisplayName("Given a person Id, when deleteMedicalRecord, then delete process should be done " +
            "in correct order")
    public void givenValidId_whenDeleteMedicalRecord_thenDeletingShouldBeDoneInCorrectOrder() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);

        medicalRecordService.deleteMedicalRecord(med.getFirstName(), med.getLastName());

        InOrder inOrder = inOrder(medicalRecordRepositoryMock);
        inOrder.verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(medicalRecordRepositoryMock).delete(any(MedicalRecord.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("DeleteMedicalRecord")
    @DisplayName("If medicalRecord is not found, when deleteMedicalRecord, then throw DataNotFoundException")
    public void givenUnFoundMedicalRecord_whenDeleteMedicalRecord_thenDataNotFoundExceptionIsThrown() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        medicalRecordService.deleteMedicalRecord(med.getFirstName(), med.getLastName());
    }

    @Test
    @Tag("GetMedicalRecordById")
    @DisplayName("Given a person ID, when getMedicalRecordById, then expected medicalRecord should be " +
            "returned correctly")
    public void givenAMedicalRecordById_whenGetMedicalRecordById_thenExpectedMedicalRecordShouldBeReturnCorrectly() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);
        when(dtoConverter.toMedicalRecordDTO(any(MedicalRecord.class))).thenReturn(medDTO);

        MedicalRecordDTO medByIdFound = medicalRecordService.getMedicalRecordById(medDTO.getFirstName(),
                medDTO.getLastName());

        assertThat(medByIdFound).isEqualTo(medDTO);
        InOrder inOrder = inOrder(medicalRecordRepositoryMock, dtoConverter);
        inOrder.verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(dtoConverter).toMedicalRecordDTO(any(MedicalRecord.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetMedicalRecordById")
    @DisplayName("If medicalRecord by ID can't be found, when getMedicalRecordById, then throw DataNotFoundException")
    public void givenUnFoundMedicalRecordById_whenGetMedicalRecordById_thenDataNotFoundExceptionIsThrown() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        medicalRecordService.getMedicalRecordById(anyString(), anyString());
    }
}
