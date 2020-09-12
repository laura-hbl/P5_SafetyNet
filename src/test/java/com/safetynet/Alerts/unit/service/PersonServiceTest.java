package com.safetynet.Alerts.unit.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.repository.PersonRepository;
import com.safetynet.Alerts.service.PersonService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepositoryMock;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    @InjectMocks
    private PersonService personService;

    private static Person person1;

    private static Person person2;

    private static Person person3;

    private static PersonDTO personDTO;

    private static List<Person> personList;

    @Before
    public void setUp() {
        personDTO = new PersonDTO("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
        person2 = new Person("Mark", "Boyd", "1509 Culver St", "Culver",
                97451, "841-874-6512", "maboyd@email.com");
        person3 = new Person("Andy", "Cooper", "112 Steppes Pl", "Culver",
                97451, "833-855-6860", "acoop@ymail.com");
        personList = Arrays.asList(person1, person2, person3);
    }

    @Test
    @Tag("GetList")
    @DisplayName("Given a person list, when getList, then result should match expected person list")
    public void givenAPersonList_whenGetList_thenReturnExpectedPersonList() {
        when(personRepositoryMock.getPersonList()).thenReturn(personList);

        List<Person> result = personService.getPersonList();

        assertThat(result).isEqualTo(personList);
        verify(personRepositoryMock).getPersonList();
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetList")
    @DisplayName("Given an empty person list, when getList, then throw DataNotFoundException")
    public void givenAnEmptyPersonList_whenGetList_thenDataNotFoundExceptionIsThrown() {
        when(personRepositoryMock.getPersonList()).thenReturn(Collections.emptyList());

        personService.getPersonList();
    }

    @Test
    @Tag("CreatePerson")
    @DisplayName("If person is not registered, when createPerson, then person should be saved correctly")
    public void givenAnUnRegisteredPerson_whenCreatePerson_thenPersonShouldBeSavedCorrectly() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);
        when(modelConverter.toPerson(any(PersonDTO.class))).thenReturn(person1);
        when(personRepositoryMock.save(person1)).thenReturn(person1);
        when(dtoConverter.toPersonDTO(any(Person.class))).thenReturn(personDTO);

        PersonDTO personSaved = personService.createPerson(personDTO);

        assertNotNull(personSaved);
        assertThat(personSaved).isEqualToComparingFieldByField(person1);
        InOrder inOrder = inOrder(personRepositoryMock, modelConverter, dtoConverter);
        inOrder.verify(personRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(modelConverter).toPerson(any(PersonDTO.class));
        inOrder.verify(personRepositoryMock).save(any(Person.class));
        inOrder.verify(dtoConverter).toPersonDTO(any(Person.class));
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("CreatePerson")
    @DisplayName("If person is already registered, when createPerson, then throw DataAlreadyRegisteredException")
    public void givenARegisteredPerson_whenCreatePerson_thenDataAlreadyRegisteredExceptionIsThrown() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(person1);

        personService.createPerson(personDTO);
    }

    @Test
    @Tag("UpdatePerson")
    @DisplayName("Given a registered person, when updatePerson, then person should be updated correctly")
    public void givenAPerson_whenUpdatePerson_thenPersonShouldBeUpdateCorrectly() {
        personDTO = new PersonDTO("John", "Boyd", "892 Downing Ct", "Culver",
                97451, "841-874-6512", "jaboyd@email.com");
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(person1);
        when(dtoConverter.toPersonDTO(any(Person.class))).thenReturn(personDTO);

        PersonDTO personUpdated = personService.updatePerson(personDTO);

        assertThat(personUpdated).isEqualToComparingFieldByField(personDTO);
        InOrder inOrder = inOrder(personRepositoryMock, dtoConverter);
        inOrder.verify(personRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(dtoConverter).toPersonDTO(any(Person.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("UpdatePerson")
    @DisplayName("If person is not registered, when updatePerson, then throw DataNotFoundException")
    public void givenUnFoundPerson_whenUpdatePerson_thenDataNotFoundExceptionIsThrown() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        personService.updatePerson(personDTO);
    }

    @Test
    @Tag("DeletePerson")
    @DisplayName("Given person Id, when deletePerson, then delete process should be done in correct order")
    public void givenAPersonId_whenDeletePerson_thenDeletingShouldBeDoneInCorrectOrder() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(person1);

        personService.deletePerson(person1.getFirstName(), person1.getLastName());

        InOrder inOrder = inOrder(personRepositoryMock);
        inOrder.verify(personRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(personRepositoryMock).delete(any(Person.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("DeletePerson")
    @DisplayName("If person is not found, when deletePerson, then throw DataNotFoundException")
    public void givenUnFoundPerson_whenDeletePerson_thenDataNotFoundExceptionIsThrown() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        personService.deletePerson(person1.getFirstName(), person1.getLastName());
    }

    @Test
    @Tag("GetPersonsByCity")
    @DisplayName("Given a persons by city list, when getPersonsByCity, then the persons by city list should be " +
            "returned correctly")
    public void givenPersonsByCityList_whenGetPersonsByCity_thenPersonsByCityListShouldBeReturnCorrectly() {
        when(personRepositoryMock.findByCity(anyString())).thenReturn(personList);

        List<Person> personsByCity = personService.getPersonsByCity(anyString());

        assertThat(personsByCity).isEqualTo(personList);
        verify(personRepositoryMock).findByCity(anyString());
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetPersonsByCity")
    @DisplayName("If persons by city list is empty, when getPersonsByCity, then throw DataNotFoundException")
    public void givenUnFoundPersonListByCity_whenGetPersonsByCity_thenDataNotFoundExceptionIsThrown() {
        when(personRepositoryMock.findByCity(anyString())).thenReturn(Collections.emptyList());

        personService.getPersonsByCity(anyString());
    }

    @Test
    @Tag("GetPersonsByAddress")
    @DisplayName("Given a persons by address list, when getPersonsByAddress, then the persons by address list should" +
            " be returned correctly")
    public void givenPersonsByAddressList_whenGetPersonsByAddress_thenPersonsByAddressListShouldBeReturnCorrectly() {
        List<Person> personsByAddressExpected = Arrays.asList(person2, person3);
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(personsByAddressExpected);

        List<Person> personsByAddress = personService.getPersonsByAddress(anyString());

        assertThat(personsByAddress).isEqualTo(personsByAddressExpected);
        verify(personRepositoryMock).findByAddress(anyString());
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetPersonsByAddress")
    @DisplayName("If persons by address list is empty, when getPersonsByAddress, then throw DataNotFoundException")
    public void givenUnFoundPersonListByAddress_whenGetPersonsByAddress_thenDataNotFoundExceptionIsThrown() {
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(Collections.emptyList());

        personService.getPersonsByAddress(anyString());
    }

    @Test
    @Tag("GetPersonsById")
    @DisplayName("Given a Person id, when getPersonsById, then expected person should be returned correctly")
    public void givenAPersonId_whenGetPersonsById_thenExpectedPersonShouldBeReturnCorrectly() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(person1);
        when(dtoConverter.toPersonDTO(any(Person.class))).thenReturn(personDTO);

        PersonDTO personById = personService.getPersonById(person1.getFirstName(), person1.getLastName());

        assertThat(personById).isEqualTo(personDTO);
        InOrder inOrder = inOrder(personRepositoryMock, dtoConverter);
        inOrder.verify(personRepositoryMock).findByIdentity(anyString(), anyString());
        inOrder.verify(dtoConverter).toPersonDTO(any(Person.class));
    }

    @Test(expected = DataNotFoundException.class)
    @Tag("GetPersonsById")
    @DisplayName("If person by id is not found, when getPersonById, then throw DataNotFoundException")
    public void givenUnFoundPerson_whenGetPersonsByCity_thenDataNotFoundExceptionIsThrown() {
        when(personRepositoryMock.findByIdentity(anyString(), anyString())).thenReturn(null);

        personService.getPersonById(person1.getFirstName(), person1.getLastName());
    }
}
