package com.safetynet.Alerts.unit.repository;

import com.safetynet.Alerts.data.DataStore;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FireStationRepositoryTest {

    private FireStationRepository fireStationRepository;

    private static FireStation fire1;
    private static FireStation fire2;

    @Mock
    private DataStore dataStore;

    @Before
    public void setUp() {
        fire1 = new FireStation("29 15th St", 2);
        fire2 = new FireStation("644 Gershwin Cir", 1);

        when(dataStore.getFireStationList()).thenReturn(Arrays.asList(fire1, fire2));

        fireStationRepository = new FireStationRepository(dataStore);
    }

    @Test
    @Tag("Save")
    @DisplayName("Given a FireStation, when save, then FireStation should be saved successfully")
    public void givenAFireStation_whenSave_thenFireStationShouldBeSaveCorrectly() {
        FireStation fireStationToSave = new FireStation("480 Manchester St", 2);

        FireStation fireStationSaved = fireStationRepository.save(fireStationToSave);

        assertThat(fireStationSaved).isEqualTo(fireStationToSave);
    }

    @Test
    @Tag("Delete")
    @DisplayName("Given a FireStation, when delete, then FireStation should be deleted successfully")
    public void givenAFireStation_whenDelete_thenFireStationShouldBeDeleteCorrectly() {
        fireStationRepository.delete(fire2);

        assertThat(fireStationRepository.find(fire2.getAddress(), fire2.getStation())).isEqualTo(null);
    }

    @Test
    @Tag("Find")
    @DisplayName("Given an expected FireStation, when find, then fireStation found should match expected FireStation")
    public void givenAFireStationToFind_whenFind_thenReturnExpectedFireStationFound() {
        FireStation fireStationFound = fireStationRepository.find(fire2.getAddress(), fire2.getStation());

        assertThat(fireStationFound).isEqualTo(fire2);
    }

    @Test
    @Tag("Find")
    @DisplayName("Given an unregistered FireStation, when find, then return null")
    public void givenAUnRegisteredFireStation_whenFind_thenReturnNull() {
        FireStation unRegisteredFireStation = new FireStation("489 Manchester St", 1);

        FireStation fireStationFound = fireStationRepository.find(unRegisteredFireStation.getAddress(),
                unRegisteredFireStation.getStation());

        assertThat(fireStationFound).isEqualTo(null);
    }

    @Test
    @Tag("FindByAddress")
    @DisplayName("Given an address, when findByAddress, then return FireStation associated with this address")
    public void givenAndAddress_whenFindByAddress_thenReturnFireStationAssociatedWithThisAddress() {
        String address = "29 15th St";

        FireStation fireFoundByAddress = fireStationRepository.findByAddress(address);

        assertThat(fireFoundByAddress).isEqualTo(fire1);
    }

    @Test
    @Tag("FindByAddress")
    @DisplayName("Given an unregistered address, when findByAddress, then return null")
    public void givenAnUnRegisteredAddress_whenFindByAddress_thenReturnNull() {
        String unRegisteredAddress = "947 E. Rose Dr";

        FireStation fireSFoundByAddress = fireStationRepository.findByAddress(unRegisteredAddress);

        assertThat(fireSFoundByAddress).isEqualTo(null);
    }

    @Test
    @Tag("FindByStation")
    @DisplayName("Given a station number, when findByStation, then return FireStation associated with that station number")
    public void givenAStationNumber_whenFindByStation_thenReturnFireStationAssociatedWithThatStationNumber() {
        List<FireStation> fireSFoundByStation = fireStationRepository.findByStation(1);

        assertThat(fireSFoundByStation).isNotEmpty();
        assertThat(fireSFoundByStation.contains(fire2));
    }

    @Test
    @Tag("FindByStation")
    @DisplayName("Given an invalid station number, when findByStation, then return an empty fireStation list")
    public void givenAnInvalidStationNumber_whenFindByStation_thenReturnEmptyFireStationList() {
        List<FireStation> fireSFoundByStation = fireStationRepository.findByStation(5);

        assertThat(fireSFoundByStation).isEqualTo(Collections.emptyList());
    }

    @Test
    @Tag("FindByStation")
    @DisplayName("Given a negative station number, when findByStation, then return an empty fireStation list")
    public void givenANegativeStation_whenFindByStation_thenReturnEmptyFireStationList() {
        List<FireStation> fireSFoundByStation = fireStationRepository.findByStation(-5);

        assertThat(fireSFoundByStation).isEqualTo(Collections.emptyList());
    }
}
