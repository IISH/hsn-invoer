package org.iish.hsn.invoer.domain.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class handles the address attributes of a registration
 */
@Entity
@Table(name = "b6", indexes = {
        @Index(columnList = "IDNR, B1IDBG, B2DIBG, B2MIBG, B2JIBG"), @Index(columnList = "ONDRZKO, OPDRNRI")
})
public class RegistrationAddress extends Invoer implements Serializable, Comparable<RegistrationAddress> {
    @Embedded private RegistrationId registrationId = new RegistrationId();

    @Column(name = "B2RNBG", nullable = false) private int keyToRegistrationPersons;
    @Column(name = "B6VRNR", nullable = false) private int sequenceNumberToAddresses;

    @Column(name = "B6MDNR", nullable = false) private int dayOfAddress;
    @Column(name = "B6MMNR", nullable = false) private int monthOfAddress;
    @Column(name = "B6MJNR", nullable = false) private int yearOfAddress;
    @Column(name = "B6MDCR", nullable = false) private int dayOfAddressAfterInterpretation;
    @Column(name = "B6MMCR", nullable = false) private int monthOfAddressAfterInterpretation;
    @Column(name = "B6MJCR", nullable = false) private int yearOfAddressAfterInterpretation;

    @Column(name = "B6TPNR", nullable = false, length = 2) private  String addressType = "";
    @Column(name = "B6SINR", nullable = false) private              int    synchroneNumber;
    @Column(name = "B6STNR", nullable = false, length = 40) private String nameOfStreet = "";
    @Column(name = "B6NRNR", nullable = false, length = 9) private  String number = "";
    @Column(name = "B6TVNR", nullable = false, length = 15) private String additionToNumber = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public RegistrationAddress() {
    }

    public RegistrationAddress(int keyToRegistrationPersons, int sequenceNumberToAddresses) {
        this.keyToRegistrationPersons = keyToRegistrationPersons;
        this.sequenceNumberToAddresses = sequenceNumberToAddresses;
    }

    public RegistrationId getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(RegistrationId registrationId) {
        this.registrationId = registrationId;
    }

    public int getKeyToRegistrationPersons() {
        return keyToRegistrationPersons;
    }

    public void setKeyToRegistrationPersons(int keyToRegistrationPersons) {
        this.keyToRegistrationPersons = keyToRegistrationPersons;
    }

    public int getSequenceNumberToAddresses() {
        return sequenceNumberToAddresses;
    }

    public void setSequenceNumberToAddresses(int sequenceNumberToAddresses) {
        this.sequenceNumberToAddresses = sequenceNumberToAddresses;
    }

    public int getDayOfAddress() {
        return dayOfAddress;
    }

    public void setDayOfAddress(int dayOfAddress) {
        this.dayOfAddress = dayOfAddress;
    }

    public int getMonthOfAddress() {
        return monthOfAddress;
    }

    public void setMonthOfAddress(int monthOfAddress) {
        this.monthOfAddress = monthOfAddress;
    }

    public int getYearOfAddress() {
        return yearOfAddress;
    }

    public void setYearOfAddress(int yearOfAddress) {
        this.yearOfAddress = yearOfAddress;
    }

    public int getDayOfAddressAfterInterpretation() {
        return dayOfAddressAfterInterpretation;
    }

    public void setDayOfAddressAfterInterpretation(int dayOfAddressAfterInterpretation) {
        this.dayOfAddressAfterInterpretation = dayOfAddressAfterInterpretation;
    }

    public int getMonthOfAddressAfterInterpretation() {
        return monthOfAddressAfterInterpretation;
    }

    public void setMonthOfAddressAfterInterpretation(int monthOfAddressAfterInterpretation) {
        this.monthOfAddressAfterInterpretation = monthOfAddressAfterInterpretation;
    }

    public int getYearOfAddressAfterInterpretation() {
        return yearOfAddressAfterInterpretation;
    }

    public void setYearOfAddressAfterInterpretation(int yearOfAddressAfterInterpretation) {
        this.yearOfAddressAfterInterpretation = yearOfAddressAfterInterpretation;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public int getSynchroneNumber() {
        return synchroneNumber;
    }

    public void setSynchroneNumber(int synchroneNumber) {
        this.synchroneNumber = synchroneNumber;
    }

    public String getNameOfStreet() {
        return nameOfStreet;
    }

    public void setNameOfStreet(String nameOfStreet) {
        this.nameOfStreet = nameOfStreet;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdditionToNumber() {
        return additionToNumber;
    }

    public void setAdditionToNumber(String additionToNumber) {
        this.additionToNumber = additionToNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(RegistrationAddress registrationAddress) {
        // If the person numbers are the same, then sort on sequence number
        if (this.keyToRegistrationPersons == registrationAddress.keyToRegistrationPersons) {
            if (this.sequenceNumberToAddresses < registrationAddress.sequenceNumberToAddresses) {
                return -1;
            }
            if (this.sequenceNumberToAddresses > registrationAddress.sequenceNumberToAddresses) {
                return 1;
            }
        }

        // Otherwise just sort on person numbers
        if (this.keyToRegistrationPersons < registrationAddress.keyToRegistrationPersons) {
            return -1;
        }
        return 1;
    }
}