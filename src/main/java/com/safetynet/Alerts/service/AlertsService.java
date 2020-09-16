package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.model.*;
import com.safetynet.Alerts.util.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that allow interaction between Alerts service and all other services.
 *
 * @author Laura Habdul
 * @see IAlertsService
 * @see IFireStationService
 * @see IPersonService
 * @see IMedicalRecordService
 */
@Service
public class AlertsService implements IAlertsService {

    /**
     * AlertsService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AlertsService.class);

    /**
     * IPersonService's implement class reference.
     */
    private final IPersonService personService;

    /**
     * IFireStationService's implement class reference.
     */
    private final IFireStationService fireStationService;

    /**
     * IMedicalRecordService's implement class reference.
     */
    private final IMedicalRecordService medicalRecordService;

    /**
     * AgeCalculator instance.
     */
    private final AgeCalculator ageCalculator;

    /**
     * Age from which the person is considered an adult
     */
    private static final int ADULT_AGE = 19;

    /**
     * Constructor of class AlertsService.
     * Initialize personService, fireStationService, medicalRecordService and ageCalculator.
     *
     * @param personService        IPersonService's implement class reference.
     * @param fireStationService   IFireStationService's implement class reference.
     * @param medicalRecordService IMedicalRecordService's implement class reference.
     * @param ageCalculator        AgeCalculator instance
     */
    @Autowired
    public AlertsService(final IPersonService personService, final IFireStationService fireStationService,
                         final IMedicalRecordService medicalRecordService, final AgeCalculator ageCalculator) {
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
        this.ageCalculator = ageCalculator;
    }

    /**
     * Retrieves the list of all persons covered by the given fire station number and the count of children and
     * adults inside that list of people.
     *
     * @param station the fire station number
     * @return PersonsByStationDTO instance
     */
    public PersonsByStationDTO getPersonsByStation(final int station) {
        LOGGER.debug("Inside AlertsService.getPersonsByStation for station number : " + station);
        int adultCount = 0;
        int childCount = 0;

        List<Person> persons = personService.getPersonList();
        // Retrieves addresses covered by the given station number
        List<String> addresses = fireStationService.getAddressesByStation(station);
        List<PersonStation> list = new ArrayList<>();

        // Loop through the list of person to find the persons living at these addresses.
        for (Person pers : persons) {
            for (String address : addresses) {

                if (pers.getAddress().equals(address)) {
                    // Determines if it's an adult or child by retrieving his medical record to obtain his date of birth
                    // and calculate his age.
                    MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                            pers.getLastName());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                    LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
                    int age = ageCalculator.getAge(birthDate);
                    if (age < ADULT_AGE) {
                        childCount++;
                    } else {
                        adultCount++;
                    }
                    // Creates PersonStation object that contains specific data required from each person covered
                    // by the given fire station number and adds it to an ArrayList.
                    list.add(new PersonStation(pers.getFirstName(), pers.getLastName(), pers.getAddress(),
                            pers.getPhone()));
                }
            }
        }

        return new PersonsByStationDTO(list, adultCount, childCount);
    }

    /**
     * Retrieves a list composed of the person with the given identity as well as persons with the same name.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     * @return PersonInfoDTO instance
     */
    public PersonInfoDTO getInfoByIdentity(final String firstName, final String lastName) {
        LOGGER.debug("Inside AlertsService.getInfoByIdentity for : " + firstName, lastName);
        List<Person> persons = personService.getPersonList();
        List<PersonInfo> personsInfo = new ArrayList<>();

        // Loops the person list to detect persons with the given last name and calculates their age.
        for (Person pers : persons) {

            // Calculates person's age by retrieving his medical record to obtain his date of birth.
            if (pers.getLastName().equals(lastName)) {
                MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                        pers.getLastName());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
                int age = ageCalculator.getAge(birthDate);

                // Creates PersonInfo object that contains specific data required from each person with given
                // last name and adds it to an ArrayList.
                personsInfo.add(new PersonInfo(pers.getLastName(), pers.getAddress(),
                        age, pers.getEmail(), med.getMedications(), med.getAllergies()));
            }
        }

        return new PersonInfoDTO(personsInfo);
    }

    /**
     * Retrieves the list of phone numbers of all inhabitants covered by the given station number.
     *
     * @param station fire station number
     * @return PhoneAlertDTO instance
     */
    public PhoneAlertDTO getPhonesByStation(final int station) {
        LOGGER.debug("Inside AlertsService.getPhonesByStation for station : " + station);
        List<Person> persons = personService.getPersonList();
        // Retrieves addresses covered by the given station number
        List<String> addresses = fireStationService.getAddressesByStation(station);
        List<String> phones = new ArrayList<>();

        // Loops the person list to find the persons living at these addresses to get their phone number and
        // adds it to an ArrayList.
        for (Person pers : persons) {
            for (String address : addresses) {
                if (pers.getAddress().equals(address)) {
                    phones.add(pers.getPhone());
                }
            }
        }

        return new PhoneAlertDTO(phones);
    }

    /**
     * Retrieves the list of e-mail of all inhabitants living at the given city.
     *
     * @param city the city that we want inhabitants e-mails.
     * @return CommunityEmailDTO instance
     */
    public CommunityEmailDTO getEmailsByCity(final String city) {
        LOGGER.debug("Inside FireStation.getEmailsByCity method for city : " + city);
        List<Person> personsByCity = personService.getPersonsByCity(city);
        List<String> emails = new ArrayList<>();

        for (Person person : personsByCity) {
            emails.add(person.getEmail());
        }

        return new CommunityEmailDTO(emails);
    }

    /**
     * Retrieves the list of children and other household members living at the given address.
     *
     * @param address household address
     * @return ChildAlertDTO instance
     */
    public ChildAlertDTO getChildByAddress(final String address) {
        LOGGER.debug("Inside AlertsService.getChildByAddress for adress : " + address);
        List<Person> personsByAddress = personService.getPersonsByAddress(address);
        List<Child> childList = new ArrayList<>();
        List<String> adultList = new ArrayList<>();

        // Loops the person list to detect the persons with given last name and calculates their age.
        for (Person pers : personsByAddress) {

            // Determines if it's an adult or child by retrieving his medical record to obtain his date of birth.
            MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                    pers.getLastName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
            int age = ageCalculator.getAge(birthDate);

            // Creates Child object that contains specific data required and it to a child list or adult list
            // depending the age calculated.
            if (age < ADULT_AGE) {
                childList.add(new Child(pers.getFirstName(), pers.getLastName(), age));
            } else {
                adultList.add("FirstName : " + pers.getFirstName() + " LastName : " + pers.getLastName());
            }
        }

        return new ChildAlertDTO(childList, adultList);
    }

    /**
     * Retrieves the list of inhabitants living at the given address as well as fire station number which
     * covers it.
     *
     * @param address household address
     * @return FireDTO instance
     */
    public FireDTO getPersonsByAddress(final String address) {
        LOGGER.debug("Inside AlertsService.getPersonsByAddress for address : " + address);
        // Retrieves persons living at given address
        List<Person> personsByAddress = personService.getPersonsByAddress(address);
        List<PersonAddress> persons = new ArrayList<>();

        for (Person pers : personsByAddress) {

            // Calculates person's age by retrieving his medical record to obtain his date of birth.
            MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                    pers.getLastName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
            int age = ageCalculator.getAge(birthDate);
            // Creates PersonAddress object that contains specific data required from each person living at given
            // address and adds it to an ArrayList
            persons.add(new PersonAddress(pers.getLastName(), pers.getPhone(),
                    age, med.getMedications(), med.getAllergies()));
        }

        // Retrieves fire station that covered the given address to get it station number
        FireStation fireStation = fireStationService.getFireStationByAddress(address);
        int station = fireStation.getStation();

        return new FireDTO(station, persons);
    }

    /**
     * Retrieves the list of all households covered by given fire stations, grouping persons by address.
     *
     * @param stations fire station numbers
     * @return FloodDTO instance
     */
    public FloodDTO getHouseholdsByStation(final List<Integer> stations) {
        LOGGER.debug("Inside AlertsService.getHouseholdsByStation for stations : " + stations);
        List<HouseholdsByStationDTO> householdsByStationDTO = new ArrayList<>();
        List<String> addressAll = new ArrayList<>();

        for (int station : stations) {
            // For each given fire station numbers retrieves addresses covered by it.
            List<String> addressesByStation = fireStationService.getAddressesByStation(station);
            List<Household> households = new ArrayList<>();

            for (String address : addressesByStation) {

                // For each address check if is not already managed by another station to avoid having same address
                // for two station number. if so, adds it to an ArrayList and process further.
                if (!addressAll.contains(address)) {
                    addressAll.add(address);

                    // Retrieves persons living at given address.
                    List<Person> persons = personService.getPersonsByAddress(address);
                    List<PersonAddress> personAddress = new ArrayList<>();

                    // Calculates age for each person from household.
                    for (Person pers : persons) {
                        MedicalRecordDTO med = medicalRecordService.getMedicalRecordById(pers.getFirstName(),
                                pers.getLastName());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                        LocalDate birthDate = LocalDate.parse(med.getBirthDate(), formatter);
                        int age = ageCalculator.getAge(birthDate);
                        // Creates a PersonAddress object that contains specific data requires for each person from
                        // household and adds it to an ArrayList.
                        personAddress.add(new PersonAddress(pers.getLastName(), pers.getPhone(),
                                age, med.getMedications(), med.getAllergies()));
                    }
                    // Creates a Household object that contains its address and list of persons living in it.
                    households.add(new Household(address, personAddress));
                }
            }
            householdsByStationDTO.add(new HouseholdsByStationDTO(station, households));
        }

        return new FloodDTO(householdsByStationDTO);
    }
}
