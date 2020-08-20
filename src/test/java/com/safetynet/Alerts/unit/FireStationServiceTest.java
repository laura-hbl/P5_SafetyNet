package com.safetynet.Alerts.unit;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
import com.safetynet.Alerts.service.FireStationService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepositoryMock;

    @InjectMocks
    private FireStationService fireStationService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenAFireStation_whenCreateFireStation_thenFireStationShouldBeSavedCorrectly() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        FireStation fireToSave = new FireStation("29 15th St", 2);
        when(fireStationRepositoryMock.find(any(FireStation.class))).thenReturn(null);
        when(fireStationRepositoryMock.save(any(FireStation.class))).thenReturn(fireToSave);

        FireStation fireCreated = fireStationService.createFireStation(fireDTO);

        assertThat(fireCreated).isEqualTo(fireToSave);
        InOrder inOrder = inOrder(fireStationRepositoryMock);
        inOrder.verify(fireStationRepositoryMock).find(any(FireStation.class));
        inOrder.verify(fireStationRepositoryMock).save(any(FireStation.class));
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    public void givenARegisteredFireStation_whenCreateFireStation_thenDataAlreadyRegisteredExceptionIsThrown() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        FireStation fireToSave = new FireStation("29 15th St", 2);
        when(fireStationRepositoryMock.find(any(FireStation.class))).thenReturn(fireToSave);

        fireStationService.createFireStation(fireDTO);

        verify(fireStationRepositoryMock).find(any(FireStation.class));
        verify(fireStationRepositoryMock, Mockito.times(0)).save(any(FireStation.class));
    }

    @Test
    public void givenAFireStation_whenUpdateFireStation_thenFireStationShouldBeUpdateCorrectly() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        FireStation fireToUpdate = new FireStation("29 15th St", 1);
        when(fireStationRepositoryMock.findByAddress("29 15th St")).thenReturn(fireToUpdate);

        fireStationService.updateFireStation(fireDTO);

        assertThat(fireToUpdate.getStation()).isEqualTo(2);
        verify(fireStationRepositoryMock).findByAddress(anyString());
    }

    @Test(expected = DataNotFoundException.class)
    public void givenAnUnRegisteredFireStation_whenUpdateFireStation_thenDataNotFoundExceptionIsThrown() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        FireStation fireToUpdate = new FireStation("29 15th St", 1);
        when(fireStationRepositoryMock.findByAddress(anyString())).thenReturn(null);

        fireStationService.updateFireStation(fireDTO);

        assertThat(fireToUpdate.getStation()).isEqualTo(2);
        verify(fireStationRepositoryMock).find(any(FireStation.class));
    }

    @Test
    public void givenARegisteredFireStation_whenDeleteFireStation_thenFireStationShouldBeDeleteCorrectly() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        FireStation fireToDelete = new FireStation("29 15th St", 2);
        when(fireStationRepositoryMock.find(any(FireStation.class))).thenReturn(fireToDelete);

        fireStationService.deleteFireStation(fireDTO);

        InOrder inOrder = inOrder(fireStationRepositoryMock);
        inOrder.verify(fireStationRepositoryMock).find(any(FireStation.class));
        inOrder.verify(fireStationRepositoryMock).delete(any(FireStation.class));
    }

    @Test(expected = DataNotFoundException.class)
    public void givenAnUnRegisteredFireStation_whenDeleteFireStation_thenDataNotFoundExceptionIsThrown() {
        FireStationDTO fireDTO = new FireStationDTO("29 15th St", 2);
        when(fireStationRepositoryMock.find(any(FireStation.class))).thenReturn(null);

        fireStationService.deleteFireStation(fireDTO);

        verify(fireStationRepositoryMock).find(any(FireStation.class));
        verify(fireStationRepositoryMock, Mockito.times(0)).delete(any(FireStation.class));
    }

    @Test
    public void givenAFireStation_whenGetFireStationByAddress_thenReturnExpectedFireStation() {
        FireStation expectedFireStation = new FireStation("29 15th St", 2);
        when(fireStationRepositoryMock.findByAddress("29 15th St")).thenReturn(expectedFireStation);

        FireStation fireStationByAddress = fireStationService.getFireStationByAddress("29 15th St");

        assertThat(fireStationByAddress).isEqualTo(expectedFireStation);
        verify(fireStationRepositoryMock).findByAddress(anyString());
    }

    @Test(expected = DataNotFoundException.class)
    public void givenAnUnFoundFireStation_whenGetFireStationByAddress_thenDataNotFoundExceptionIsThrown() {
        when(fireStationRepositoryMock.findByAddress("29 15th St")).thenReturn(null);

        fireStationService.getFireStationByAddress("29 15th St");

        verify(fireStationRepositoryMock).findByAddress(anyString());
    }

    @Test
    public void givenAddressesList_whenGetAddressesByStation_thenReturnExpectedFireStations() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("29 15th St", 2));
        fireStations.add(new FireStation("83 Binoc Ave", 2));
        when(fireStationRepositoryMock.findByStation(2)).thenReturn(fireStations);

        List<String> result = fireStationService.getAddressesByStation(2);

        assertThat(result).size().isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("29 15th St");
        assertThat(result.get(1)).isEqualTo("83 Binoc Ave");
        verify(fireStationRepositoryMock).findByStation(anyInt());
    }

    @Test(expected = DataNotFoundException.class)
    public void givenEmptyFireStations_whenGetAddressesByStation_thenDataNotFoundExceptionIsThrown() {
        List<FireStation> fireStations = new ArrayList<>();
        when(fireStationRepositoryMock.findByStation(2)).thenReturn(fireStations);

        fireStationService.getAddressesByStation(2);

        verify(fireStationRepositoryMock).findByStation(anyInt());
    }
}
