package org.iish.hsn.invoer.domain.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the static attributes of a person (name, date of birth etc.)
 */
@Entity
@Table(name = "b2", indexes = {
        @Index(columnList = "IDNR, B1IDBG, B2DIBG, B2MIBG, B2JIBG, B2RNBG"), @Index(columnList = "ONDRZKO, OPDRNRI")
})
public class Person extends Invoer implements Serializable {
    public enum NatureOfPerson {
        FIRST_RP(1), NO_RP(2), NEXT_RP(5);

        private int natureOfPerson;

        NatureOfPerson(int natureOfPerson) {
            this.natureOfPerson = natureOfPerson;
        }

        public int getNatureOfPerson() {
            return natureOfPerson;
        }
    }

    @Embedded private RegistrationId registrationId = new RegistrationId();

    @Column(name = "B2RNBG", nullable = false) private int keyToRegistrationPersons;

    @Column(name = "B2FCBG", nullable = false) private int    natureOfPerson;
    @Column(name = "B2RDNR", nullable = false) private int    dayOfRegistration;
    @Column(name = "B2RMNR", nullable = false) private int    monthOfRegistration;
    @Column(name = "B2RJNR", nullable = false) private int    yearOfRegistration;
    @Column(name = "B2RDCR", nullable = false) private int    dayOfRegistrationAfterInterpretation;
    @Column(name = "B2RMCR", nullable = false) private int    monthOfRegistrationAfterInterpretation;
    @Column(name = "B2RJCR", nullable = false) private int    yearOfRegistrationAfterInterpretation;
    @Column(name = "B2ANNR", nullable = false, length = 50) private String familyName;
    @Column(name = "B2VNNR", nullable = false, length = 50) private String firstName;
    @Column(name = "B2GSNR", nullable = false, length = 1) private String sex;

    @Column(name = "B2GDNR", nullable = false) private int    dayOfBirth;
    @Column(name = "B2GMNR", nullable = false) private int    monthOfBirth;
    @Column(name = "B2GJNR", nullable = false) private int    yearOfBirth;
    @Column(name = "B2GDCR", nullable = false) private int    dayOfBirthAfterInterpretation;
    @Column(name = "B2GMCR", nullable = false) private int    monthOfBirthAfterInterpretation;
    @Column(name = "B2GJCR", nullable = false) private int    yearOfBirthAfterInterpretation;
    @Column(name = "B2GNNR", nullable = false, length = 50) private String placeOfBirth;

    @Column(name = "B2ODNR", nullable = false) private int    dayOfDecease;
    @Column(name = "B2OMNR", nullable = false) private int    monthOfDecease;
    @Column(name = "B2OJNR", nullable = false) private int    yearOfDecease;
    @Column(name = "B2ODCR", nullable = false) private int    dayOfDeceaseAfterInterpretation;
    @Column(name = "B2OMCR", nullable = false) private int    monthOfDeceaseAfterInterpretation;
    @Column(name = "B2OJCR", nullable = false) private int    yearOfDeceaseAfterInterpretation;
    @Column(name = "B2ONNR", nullable = false, length = 50) private String placeOfDecease;

    @Column(name = "B2NANR", nullable = false, length = 40) private String nationality;
    @Column(name = "B2WDNR", nullable = false, length = 50) private String legalPlaceOfLiving;
    @Column(name = "B2VWNR", nullable = false, length = 2) private String legalPlaceOfLivingInCodes;
    @Column(name = "B2AANR", nullable = false, length = 100) private String remarks;
    @Column(name = "B2AAN", nullable = false, length = 128) private  String remarks2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Person() {
    }

    public Person(int keyToRegistrationPersons) {
        this.keyToRegistrationPersons = keyToRegistrationPersons;
    }

    public RegistrationId getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(RegistrationId registrationId) {
        this.registrationId = registrationId;
    }

    // Alias of getKeyToRegistrationPersons()
    public int getRp() {
        return keyToRegistrationPersons;
    }

    public int getKeyToRegistrationPersons() {
        return keyToRegistrationPersons;
    }

    public void setKeyToRegistrationPersons(int keyToRegistrationPersons) {
        this.keyToRegistrationPersons = keyToRegistrationPersons;
    }

    public int getNatureOfPerson() {
        return natureOfPerson;
    }

    public void setNatureOfPerson(int natureOfPerson) {
        this.natureOfPerson = natureOfPerson;
    }

    public int getDayOfRegistration() {
        return dayOfRegistration;
    }

    public void setDayOfRegistration(int dayOfRegistration) {
        this.dayOfRegistration = dayOfRegistration;
    }

    public int getMonthOfRegistration() {
        return monthOfRegistration;
    }

    public void setMonthOfRegistration(int monthOfRegistration) {
        this.monthOfRegistration = monthOfRegistration;
    }

    public int getYearOfRegistration() {
        return yearOfRegistration;
    }

    public void setYearOfRegistration(int yearOfRegistration) {
        this.yearOfRegistration = yearOfRegistration;
    }

    public int getDayOfRegistrationAfterInterpretation() {
        return dayOfRegistrationAfterInterpretation;
    }

    public void setDayOfRegistrationAfterInterpretation(int dayOfRegistrationAfterInterpretation) {
        this.dayOfRegistrationAfterInterpretation = dayOfRegistrationAfterInterpretation;
    }

    public int getMonthOfRegistrationAfterInterpretation() {
        return monthOfRegistrationAfterInterpretation;
    }

    public void setMonthOfRegistrationAfterInterpretation(int monthOfRegistrationAfterInterpretation) {
        this.monthOfRegistrationAfterInterpretation = monthOfRegistrationAfterInterpretation;
    }

    public int getYearOfRegistrationAfterInterpretation() {
        return yearOfRegistrationAfterInterpretation;
    }

    public void setYearOfRegistrationAfterInterpretation(int yearOfRegistrationAfterInterpretation) {
        this.yearOfRegistrationAfterInterpretation = yearOfRegistrationAfterInterpretation;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getDayOfBirthAfterInterpretation() {
        return dayOfBirthAfterInterpretation;
    }

    public void setDayOfBirthAfterInterpretation(int dayOfBirthAfterInterpretation) {
        this.dayOfBirthAfterInterpretation = dayOfBirthAfterInterpretation;
    }

    public int getMonthOfBirthAfterInterpretation() {
        return monthOfBirthAfterInterpretation;
    }

    public void setMonthOfBirthAfterInterpretation(int monthOfBirthAfterInterpretation) {
        this.monthOfBirthAfterInterpretation = monthOfBirthAfterInterpretation;
    }

    public int getYearOfBirthAfterInterpretation() {
        return yearOfBirthAfterInterpretation;
    }

    public void setYearOfBirthAfterInterpretation(int yearOfBirthAfterInterpretation) {
        this.yearOfBirthAfterInterpretation = yearOfBirthAfterInterpretation;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public int getDayOfDecease() {
        return dayOfDecease;
    }

    public void setDayOfDecease(int dayOfDecease) {
        this.dayOfDecease = dayOfDecease;
    }

    public int getMonthOfDecease() {
        return monthOfDecease;
    }

    public void setMonthOfDecease(int monthOfDecease) {
        this.monthOfDecease = monthOfDecease;
    }

    public int getYearOfDecease() {
        return yearOfDecease;
    }

    public void setYearOfDecease(int yearOfDecease) {
        this.yearOfDecease = yearOfDecease;
    }

    public int getDayOfDeceaseAfterInterpretation() {
        return dayOfDeceaseAfterInterpretation;
    }

    public void setDayOfDeceaseAfterInterpretation(int dayOfDeceaseAfterInterpretation) {
        this.dayOfDeceaseAfterInterpretation = dayOfDeceaseAfterInterpretation;
    }

    public int getMonthOfDeceaseAfterInterpretation() {
        return monthOfDeceaseAfterInterpretation;
    }

    public void setMonthOfDeceaseAfterInterpretation(int monthOfDeceaseAfterInterpretation) {
        this.monthOfDeceaseAfterInterpretation = monthOfDeceaseAfterInterpretation;
    }

    public int getYearOfDeceaseAfterInterpretation() {
        return yearOfDeceaseAfterInterpretation;
    }

    public void setYearOfDeceaseAfterInterpretation(int yearOfDeceaseAfterInterpretation) {
        this.yearOfDeceaseAfterInterpretation = yearOfDeceaseAfterInterpretation;
    }

    public String getPlaceOfDecease() {
        return placeOfDecease;
    }

    public void setPlaceOfDecease(String placeOfDecease) {
        this.placeOfDecease = placeOfDecease;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLegalPlaceOfLiving() {
        return legalPlaceOfLiving;
    }

    public void setLegalPlaceOfLiving(String legalPlaceOfLiving) {
        this.legalPlaceOfLiving = legalPlaceOfLiving;
    }

    public String getLegalPlaceOfLivingInCodes() {
        return legalPlaceOfLivingInCodes;
    }

    public void setLegalPlaceOfLivingInCodes(String legalPlaceOfLivingInCodes) {
        this.legalPlaceOfLivingInCodes = legalPlaceOfLivingInCodes;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = remarks2;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
