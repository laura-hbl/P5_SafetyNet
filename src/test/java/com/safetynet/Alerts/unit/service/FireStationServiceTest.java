package com.safetynet.Alerts.unit.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
import com.safetynet.Alerts.service.FireStationService;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepositoryMock;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    @InjectMocks
    private FireStationService fireStationService;

    private static FireStationDTO fireSDTO;

    private static FireStation fire1;

    private static FireStation fire2;

    List<FireStation> fireStations;

    @Before
    public void setUp() {
        fireSDTO = new FireStationDTO("29 15th St", 2);
        fire1 = new FireStation("29 15th St", 2);
        fire2 = new FireStation("83 Binoc Ave", 2);
        fireStations = Arrays.asList(fire1, fire2);
    }

    @Test
    @Tag("CreateFireStation")
    @DisplayName("Given a FireStation, when createFireStation, then FireStation should be saved successfully")
    public void givenAFireStation_whenCreateFireStation_thenFireStationShouldBeSavedCorrectly() {
        when(fireStationRepositoryMock.find(anyString(), anyInt())).thenReturn(null);
        when(modelConverter.toFireStation(any(FireStationDTO.class))).thenReturn(fire1);
        when(fireStationRepositoryMock.save(any(FireStation.class))).thenReturn(fire1);
        when(dtoConverter.toFireStationDTO(any(FireStation.class))).thenReturn(fireSDTO);

        FireStationDTO fireCreated = fireStationService.createFireStation(fireSDTO);

        assertNotNull(fireCreated);
        assertThat(fireCreated).isEqualToComparingFieldByField(fireSDTO);
        InOrder inOrder = inOrder(fireStationRepositoryMock, modelConverter, dtoConverter);
        inOrder.verify(fireStationRepositoryMock).find(anyString(), anyInt());
        inOrder.verify(modelConverter).toFireStation(any(FireStationDTO.class));
        inOrder.verify(fireStationRepositoryMock).save(any(FireStation.class));
        inOrder.verify(dtoConverter).toFireStationDTO(any(FireStation.class));
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("CreateFireStation")
    @DisplayName("Given a registered FireStation, when createFireStation, then throw DataAlreadyRegisteredException")
    public void givenARegisteredFireStation_whenCreateFireStation_thenDataAlreadyRegisteredExceptionIsThrown() {
        when(fireStationRepositoryMock.find(anyString(), anyInt())).thenReturn(fire1);

        fireStationService.createFireStation(fireSDTO);
    }

    @Test
    @Tag("UpdateFireStation")
    @DisplayName("Given a registered FireStation, when updateFireStation, then FireStation should be updated " +
            "correctly")
    public void givenARegisteredFireStation_whenUpdateFireStation_thenFireStationShouldBeUpdatedCorrectly() {
        fireSDTO = new FireStationDTO("29 15th St", 1);
        when(fireStationRepositoryMock.findByAddress(anyString())).thenReturn(fire1);
        when(dtoConverter.toFireStationDTO(any(FireStation.class))).thenReturn(fireSDTO);

        FireStationDTO fireUpdated = fireStationService.updateFireStation(fireSDTO);

        assertThat(fireUpdated.getStation()).isEqualTo(1);
        InOrder inOrder = inOrder(fireStationRepositoryMock, dtoConverter);
        inOrder.verify(fireStationRepositoryMock).findByAddress(anyString());
        inOrder.verify(dtoConverter).toFireStationDTO(any(FireStation.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("UpdateFireStation")
    @DisplayName("If FireStation is not registered, when updateFireStation, then throw DataNotFoundException")
    public void givenUnFoundFireStation_whenUpdateFireStation_thenDataNotFoundExceptionIsThrown() {
        when(fireStationRepositoryMock.findByAddress(anyString())).thenReturn(null);

        fireStationService.updateFireStation(fireSDTO);
    }

    @Test
    @Tag("DeleteFireStation")
    @DisplayName("Given a registered FireStation, when deleteFireStation, then delete process should be done in " +
            "correct order")
    public void givenARegisteredFireStation_whenDeleteFireStation_thenDeletingShouldBeDoneInCorrectOrder() {
        when(fireStationRepositoryMock.find(anyString(), anyInt())).thenReturn(fire1);

        fireStationService.deleteFireStation(fire1.getAddress(), fire1.getStation());

        InOrder inOrder = inOrder(fireStationRepositoryMock);
        inOrder.verify(fireStationRepositoryMock).find(anyString(), anyInt());
        inOrder.verify(fireStationRepositoryMock).delete(any(FireStation.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("DeleteFireStation")
    @DisplayName("If FireStation is not registered, when deleteFireStation, then throw DataNotFoundException")
    public void givenUnFoundFireStation_whenDeleteFireStation_thenDataNotFoundExceptionIsThrown() {
        when(fireStationRepositoryMock.find(anyString(), anyInt())).thenReturn(null);

        fireStationService.deleteFireStation(fire1.getAddress(), fire1.getStation());
    }

    @Test
    @Tag("GetFireStationByAddress")
    @DisplayName("Given a FireStation by address, when getFireStationByAddress, then the FireStation by address " +
            "should be Returned correctly")
    public void givenAFireStationByAddress_whenGetFireStationByAddress_thenFireStationByAddressShouldBeReturnCorrectly() {
        when(fireStationRepositoryMock.findByAddress(anyString())).thenReturn(fire1);

        FireStation fireByAddress = fireStationService.getFireStationByAddress(anyString());

        assertThat(fireByAddress).isEqualTo(fire1);
        verify(fireStationRepositoryMock).findByAddress(anyString());
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetFireStationByAddress")
    @DisplayName("If FireStation by address can't be found, when getFireStationByAddress, then throw " +
            "DataNotFoundException")
    public void givenUnFoundFireStationByAddress_whenGetFireStationByAddress_thenDataNotFoundExceptionIsThrown() {
        when(fireStationRepositoryMock.findByAddress(anyString())).thenReturn(null);

        fireStationService.getFireStationByAddress(anyString());
    }

    @Test
    @Tag("GetAddressesByStation")
    @DisplayName("Given an addresses by station list, when getAddressesByStation, then the addresses by station list " +
            "should be returned correctly")
    public void givenAnAddressList_whenGetAddressesByStation_thenReturnExpectedAddressList() {
        when(fireStationRepositoryMock.findByStation(anyInt())).thenReturn(fireStations);
        List<String> expectedAddresses = Arrays.asList("29 15th St", "83 Binoc Ave");

        List<String> addresses = fireStationService.getAddressesByStation(anyInt());

        assertThat(addresses).isEqualTo(expectedAddresses);
        assertThat(addresses.get(1)).isEqualTo("83 Binoc Ave");
        verify(fireStationRepositoryMock).findByStation(anyInt());
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetAddressesByStation")
    @DisplayName("If addresses by station list is empty, when getAddressesByStation, then throw DataNotFoundException")
    public void givenUnFoundFireStationByStation_whenGetAddressesByStation_thenDataNotFoundExceptionIsThrown() {
        when(fireStationRepositoryMock.findByStation(anyInt())).thenReturn(Collections.emptyList());

        fireStationService.getAddressesByStation(anyInt());
    }

    @Test
    @Tag("GetFireStationByKeyId")
    @DisplayName("Given a FireStation key ID, when getFireStationByKeyId, then expected FireStation " +
            "should be returned correctly")
    public void givenFireStationKeyId_whenGetFireStationByKeyId_thenExpectedFireStationShouldBeReturnCorrectly() {
        when(fireStationRepositoryMock.find(anyString() ,anyInt())).thenReturn(fire1);
        when(dtoConverter.toFireStationDTO(any(FireStation.class))).thenReturn(fireSDTO);

        FireStationDTO fireByIdFound = fireStationService.getFireStationByKeyId(fire1.getAddress(), fire1.getStation());

        assertThat(fireByIdFound).isEqualTo(fireSDTO);
        InOrder inOrder = inOrder(fireStationRepositoryMock, dtoConverter);
        inOrder.verify(fireStationRepositoryMock).find(anyString(), anyInt());
        inOrder.verify(dtoConverter).toFireStationDTO(any(FireStation.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetFireStationByKeyId")
    @DisplayName("If FireStation by ID can't be found, when getFireStationByKeyId, then throw " +
            "DataNotFoundException")
    public void givenUnFoundFireStationByKeyId_whenGetFireStationByKeyId_thenDataNotFoundExceptionIsThrown() {
        when(fireStationRepositoryMock.find(anyString() ,anyInt())).thenReturn(null);

        fireStationService.getFireStationByKeyId(fire1.getAddress(), fire1.getStation());
    }
}
