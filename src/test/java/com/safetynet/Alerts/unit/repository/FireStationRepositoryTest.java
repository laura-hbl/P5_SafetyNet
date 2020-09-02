package com.safetynet.Alerts.unit.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FireStationRepositoryTest {

   private FireStationRepository fireStationRepository;

   private static FireStation fireStation1;
   private static FireStation fireStation2;

   @Before
    public void setUp() {
        fireStation1 = new FireStation("29 15th St", 2);
        fireStation2 = new FireStation("644 Gershwin Cir", 1);

        StoredData storedData = new StoredData();
        storedData.setFireStationList(Arrays.asList(fireStation1, fireStation2));

        fireStationRepository = new FireStationRepository(storedData);
    }

    @Test
    @Tag("Save")
    @DisplayName("Given a FireStation, when save, then FireStation should be saved successfully")
    public void givenAFireStation_whenSave_thenFireStationShouldBeSaveCorrectly() {
        FireStation fireStationToSave = new FireStation("480 Manchester St", 2);

        FireStation fireStationSaved = fireStationRepository.save(fireStationToSave);

        assertThat(fireStationSaved).isEqualTo(fireStationToSave);
    }

    /*@Test
    @Tag("Save")
    @DisplayName("Given a null FireStation, when save, then throw NullPointerException")
    public void givenANullFireStation_whenSave_thenNullPointerExceptionIsThrown() {
        FireStation fireStation = null;

        assertThatNullPointerException().isThrownBy(() -> fireStationRepository.save(fireStation));
    }
*/
    @Test
    @Tag("Delete")
    @DisplayName("Given a FireStation, when delete, then FireStation should be deleted successfully")
    public void givenAFireStation_whenDelete_thenFireStationShouldBeDeleteCorrectly() {
        fireStationRepository.delete(fireStation2);

        assertThat(fireStationRepository.find(fireStation2)).isEqualTo(null);
    }

    /*@Test
    @Tag("Delete")
    @DisplayName("Given a null FireStation, when save, then throw NullPointerException")
    public void givenANullFireStation_whenDelete_thenNullPointerExceptionIsThrown() {
        FireStation fireStation = null;

        assertThatNullPointerException().isThrownBy(() -> fireStationRepository.delete(fireStation));
    }*/

    @Test
    @Tag("Find")
    @DisplayName("Given an expected FireStation, when find, then fireStation found should match expected FireStation")
    public void givenAFireStationToFind_whenFind_thenReturnExpectedFireStationFound() {
        FireStation fireStationFound = fireStationRepository.find(fireStation2);

        assertThat(fireStationFound).isEqualTo(fireStation2);
    }

    /*@Test
    @Tag("Find")
    @DisplayName("Given a null FireStation, when find, then throw NullPointerException")
    public void givenANullFireStation_whenFind_thenNullPointerExceptionIsThrown() {
        FireStation fireStation = null;

        assertThatNullPointerException().isThrownBy(() -> fireStationRepository.find(fireStation));
    }*/

    @Test
    @Tag("Find")
    @DisplayName("Given an unregistered FireStation, when find, then return null")
    public void givenAUnRegisteredFireStationToFind_whenFind_thenReturnNull() {
        FireStation UnRegisteredFireStation = new FireStation("489 Manchester St", 1);

        FireStation fireStationFound = fireStationRepository.find(UnRegisteredFireStation);

        assertThat(fireStationFound).isEqualTo(null);
    }

    /*@Test
    public void givenAFireStationWithEmptyAddress_whenFind_thenReturnNull() {
        FireStation invalidFireStation = new FireStation("", 1);

        FireStation fireStationFound = fireStationRepository.find(invalidFireStation);

        assertThat(fireStationFound).isEqualTo(null);
    }

    @Test
    public void givenAFireStationWithNullAddress_whenFind_thenReturnNull() {
        FireStation invalidFireStation = new FireStation(null, 1);

        FireStation fireStationFound = fireStationRepository.find(invalidFireStation);

        assertThat(fireStationFound).isEqualTo(null);
    }

    @Test
    public void givenAFireStationWithInvalidStationNumber_whenFind_thenReturnNull() {
        FireStation invalidFireStation = new FireStation("29 15th St", 6);

        FireStation fireStationFound = fireStationRepository.find(invalidFireStation);

        assertThat(fireStationFound).isEqualTo(null);
    }

    @Test
    public void givenAFireStationWithNegativeStationNumber_whenFind_thenReturnNull() {
        FireStation invalidFireStation = new FireStation("29 15th St", -3);

        FireStation fireStationFound = fireStationRepository.find(invalidFireStation);

        assertThat(fireStationFound).isEqualTo(null);
    }*/

    @Test
    @Tag("FindByAddress")
    @DisplayName("Given an address, when findByAddress, then return FireStation associated with this address")
    public void givenAndAddress_whenFindByAddress_thenReturnFireStationAssociatedWithThisAddress() {
        String address = "29 15th St";

        FireStation fireFoundByAddress = fireStationRepository.findByAddress(address);

        assertThat(fireFoundByAddress).isEqualTo(fireStation1);
    }

    @Test
    @Tag("FindByAddress")
    @DisplayName("Given an unregistered address, when findByAddress, then return null")
    public void givenAnUnRegisteredAddress_whenFindByAddress_thenReturnNull() {
        String unRegisteredAddress = "947 E. Rose Dr";

        FireStation fireSFoundByAddress = fireStationRepository.findByAddress(unRegisteredAddress);

        assertThat(fireSFoundByAddress).isEqualTo(null);
    }

    /*@Test
    public void givenANullAddress_whenFindByAddress_thenReturnNull() {
        String address = null;

        FireStation fireStationFoundByAddress = fireStationRepository.findByAddress(address);

        assertThat(fireStationFoundByAddress).isEqualTo(null);
    }

    @Test
    public void givenAnEmptyAddress_whenFindByAddress_thenReturnNull() {
        String address = "";

        FireStation fireStationFoundByAddress = fireStationRepository.findByAddress(address);

        assertThat(fireStationFoundByAddress).isEqualTo(null);
    }*/


    @Test
    @Tag("FindByStation")
    @DisplayName("Given a station number, when findByStation, then return FireStation associated with that station number")
    public void givenAStationNumber_whenFindByStation_thenReturnFireStationAssociatedWithThatStationNumber() {
        List<FireStation> fireSFoundByStation = fireStationRepository.findByStation(1);

        assertThat(fireSFoundByStation).isNotEmpty();
        assertThat(fireSFoundByStation.contains(fireStation2));
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
