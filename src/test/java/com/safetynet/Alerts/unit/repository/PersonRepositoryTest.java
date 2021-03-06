package com.safetynet.Alerts.unit.repository;

import com.safetynet.Alerts.data.DataStore;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonRepositoryTest {

    private PersonRepository personRepository;

    @Mock
    private DataStore dataStore;

    private static Person person1;
    private static Person person2;

    @Before
    public void setUp() {
        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
        person2 = new Person("Mark", "Boyd", "1509 Culver St", "Culver",
                97451, "898-353-6978", "maboyd@email.com");

        when(dataStore.getPersonList()).thenReturn(Arrays.asList(person1, person2));

        personRepository = new PersonRepository(dataStore);
    }

    @Test
    @Tag("GetPersonList")
    @DisplayName("Given a Person list, when getPersonList, then return expected Person list")
    public void givenAPersonList_whenGetPersonList_thenExpectedReturnPersonList() {
        List<Person> personList = personRepository.getPersonList();

        assertThat(personList).isEqualTo(Arrays.asList(person1, person2));
    }

    @Test
    @Tag("Save")
    @DisplayName("Given a Person, when save, then Person should be saved successfully")
    public void givenAPerson_whenSave_thenPersonShouldBeSaveCorrectly() {
        Person person3 = new Person("Andy", "Cooper", "112 Steppes Pl", "Culver",
                97451, "833-855-6860", "acoop@ymail.com");

        Person personSaved = personRepository.save(person3);

        assertThat(personSaved).isEqualTo(person3);
    }

    @Test
    @Tag("Delete")
    @DisplayName("Given a Person, when delete, then Person should be deleted successfully")
    public void givenAPerson_whenDelete_thenPersonShouldBeDeleteCorrectly() {
        personRepository.delete(person1);

        assertThat(personRepository.findByIdentity("John", "Boyd")).isEqualTo(null);
    }

    @Test
    @Tag("FindByIdentity")
    @DisplayName("Given a Person Id, when findByIdentity, then return expected Person")
    public void givenAPersonIdentity_whenFindByIdentity_thenReturnExpectedPerson() {
        Person personFound = personRepository.findByIdentity("John", "Boyd");

        assertThat(personFound).isEqualTo(person1);
    }

    @Test
    @Tag("FindByIdentity")
    @DisplayName("Given an unregistered Person, when findByIdentity, then return null")
    public void givenAnUnRegisteredIdentity_whenFindByIdentity_thenReturnNull() {
        Person personFound = personRepository.findByIdentity("Paul", "Duncan");

        assertThat(personFound).isEqualTo(null);
    }

    @Test
    @Tag("FindByAddress")
    @DisplayName("Given an address, when findByAddress, then return persons at this address")
    public void givenAnAddress_whenFindByAddress_thenReturnPersonsAtThisAddress() {
        List<Person> personsByAddress = personRepository.findByAddress("1509 Culver St");

        assertThat(personsByAddress).isEqualTo(Arrays.asList(person1, person2));
    }

    @Test
    @Tag("FindByAddress")
    @DisplayName("Given an unregistered address, when findByAddress, then an empty list should be returned")
    public void givenAnUnRegisteredAddress_whenFindByAddress_thenReturnEmptyPersonsByAddressList() {
        List<Person> personsByAddress = personRepository.findByAddress("947 E. Rose Dr");

        assertThat(personsByAddress).isEmpty();
    }

    @Test
    @Tag("FindByCity")
    @DisplayName("Given a city, when findByCity, then return Persons living at this city")
    public void givenACity_whenFindByCity_thenReturnPersonsFromThisCity() {
        List<Person> personsByCity = personRepository.findByCity("Culver");

        assertThat(personsByCity).isEqualTo(Arrays.asList(person1, person2));
    }

    @Test
    @Tag("FindByCity")
    @DisplayName("Given an unregistered city, when findByCity, then an empty list should be returned")
    public void givenAnUnRegisteredCity_whenFindByCity_thenReturnEmptyPersonByCityList() {
        List<Person> personsByCity = personRepository.findByCity("Paris");

        assertThat(personsByCity).isEmpty();
    }
}
