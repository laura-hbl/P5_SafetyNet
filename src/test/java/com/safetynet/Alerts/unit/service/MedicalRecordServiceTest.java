package com.safetynet.Alerts.unit.service;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.repository.MedicalRecordRepository;
import com.safetynet.Alerts.service.MedicalRecordService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepositoryMock;

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
    @DisplayName("Given a medicalRecord, when createMedicalRecord, then medicalRecord should be saved successfully")
    public void givenAMedicalRecord_whenCreateMedicalRecord_thenMedicalRecordShouldBeSavedCorrectly() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);
        when(medicalRecordRepositoryMock.save(any(MedicalRecord.class))).thenReturn(med);

        MedicalRecord medCreated = medicalRecordService.createMedicalRecord(medDTO);

        assertThat(medCreated).isEqualTo(med);
        InOrder inOrder = inOrder(medicalRecordRepositoryMock);
        inOrder.verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(medicalRecordRepositoryMock).save(any(MedicalRecord.class));

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
    @DisplayName("Given a registered medicalRecord, when updateMedicalRecord, then medicalRecord should be updated successfully")
    public void givenAMedicalRecord_whenUpdateMedicalRecord_thenMedicalRecordShouldBeUpdateCorrectly() {
        medDTO = new MedicalRecordDTO("John", "Boyd", "03/06/1984",
                Arrays.asList("aznol:350mg"), Arrays.asList("nillacilan", "peanut"));
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);

        MedicalRecord med1Updated = medicalRecordService.updateMedicalRecord(medDTO);

        assertThat(med1Updated.getAllergies().contains("peanut"));
        verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
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
    @DisplayName("Given a registered medicalRecord, when deleteMedicalRecord, then the delete process should be done in correct order")
    public void givenARegisteredMedicalRecord_whenDeleteMedicalRecord_thenDeletingShouldBeDoneInCorrectOrder() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);

        medicalRecordService.deleteMedicalRecord(medDTO);

        InOrder inOrder = inOrder(medicalRecordRepositoryMock);
        inOrder.verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(medicalRecordRepositoryMock).delete(any(MedicalRecord.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("DeleteMedicalRecord")
    @DisplayName("If medicalRecord is not registered, when deleteMedicalRecord, then throw DataNotFoundException")
    public void givenUnFoundMedicalRecord_whenDeleteMedicalRecord_thenDataNotFoundExceptionIsThrown() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        medicalRecordService.deleteMedicalRecord(medDTO);
    }

    @Test
    @Tag("GetMedicalRecordById")
    @DisplayName("Given a medicalRecord by ID, when getMedicalRecordById, then the medicalRecord by ID should be returned successfully")
    public void givenAMedicalRecordById_whenGetMedicalRecordById_thenMedicalRecordByIdShouldBeReturnCorrectly() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(med);

        MedicalRecord medByIdFound = medicalRecordService.getMedicalRecordById(anyString(), anyString());

        assertThat(medByIdFound).isEqualTo(med);
        verify(medicalRecordRepositoryMock).findByIdentity(anyString(), anyString());
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetMedicalRecordById")
    @DisplayName("If medicalRecord by ID can't be found, when getMedicalRecordById, then throw DataNotFoundException")
    public void givenUnFoundMedicalRecordById_whenGetMedicalRecordById_thenDataNotFoundExceptionIsThrown() {
        when(medicalRecordRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        medicalRecordService.getMedicalRecordById(anyString(), anyString());
    }
}
