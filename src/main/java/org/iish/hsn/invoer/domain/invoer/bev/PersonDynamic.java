package org.iish.hsn.invoer.domain.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the dynamic attributes of a person (relation to head, civil status etc.)
 */
@Entity
@Table(name = "b3", indexes = {
        @Index(columnList = "IDNR, B1IDBG, B2DIBG, B2MIBG, B2JIBG, B2RNBG"), @Index(columnList = "ONDRZKO, OPDRNRI")
})
public class PersonDynamic extends Invoer implements Serializable {
    public enum Type {
        RELATIE_TOV_HOOFD(1), BURGELIJKE_STAND(2), KERKGENOOTSCHAP(3), BEROEP(5), HERKOMST(6), VERTREK(7);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static Type getType(int type) {
            for (Type t : Type.values()) {
                if (t.getType() == type) {
                    return t;
                }
            }
            return null;
        }
    }

    @Embedded private RegistrationId registrationId = new RegistrationId();

    @Column(name = "B2RNBG", nullable = false) private int keyToRegistrationPersons;
    @Column(name = "B3TYPE", nullable = false) private int dynamicDataType;
    @Column(name = "B3VRNR", nullable = false) private int dynamicDataSequenceNumber;

    @Column(name = "B3KODE", nullable = false) private int contentOfDynamicData;
    @Column(name = "B3RGLN", nullable = false) private int valueOfRelatedPerson;
    @Column(name = "B2FCBG", nullable = false) private int natureOfPerson;
    @Column(name = "B3MDNR", nullable = false) private int dayOfMutation;
    @Column(name = "B3MMNR", nullable = false) private int monthOfMutation;
    @Column(name = "B3MJNR", nullable = false) private int yearOfMutation;
    @Column(name = "B3MDCR", nullable = false) private int dayOfMutationAfterInterpretation;
    @Column(name = "B3MMCR", nullable = false) private int monthOfMutationAfterInterpretation;
    @Column(name = "B3MJCR", nullable = false) private int yearOfMutationAfterInterpretation;

    @Column(name = "B3GEGEVEN", nullable = false, length = 50) private String dynamicData2 = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public PersonDynamic() {
    }

    public PersonDynamic(int keyToRegistrationPersons, Type dynamicDataType, int dynamicDataSequenceNumber) {
        this.keyToRegistrationPersons = keyToRegistrationPersons;
        this.dynamicDataType = dynamicDataType.getType();
        this.dynamicDataSequenceNumber = dynamicDataSequenceNumber;
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

    public int getDynamicDataType() {
        return dynamicDataType;
    }

    public void setDynamicDataType(int dynamicDataType) {
        this.dynamicDataType = dynamicDataType;
    }

    public int getDynamicDataSequenceNumber() {
        return dynamicDataSequenceNumber;
    }

    public void setDynamicDataSequenceNumber(int dynamicDataSequenceNumber) {
        this.dynamicDataSequenceNumber = dynamicDataSequenceNumber;
    }

    public int getContentOfDynamicData() {
        return contentOfDynamicData;
    }

    public void setContentOfDynamicData(int contentOfDynamicData) {
        this.contentOfDynamicData = contentOfDynamicData;
    }

    public int getValueOfRelatedPerson() {
        return valueOfRelatedPerson;
    }

    public void setValueOfRelatedPerson(int valueOfRelatedPerson) {
        this.valueOfRelatedPerson = valueOfRelatedPerson;
    }

    public int getNatureOfPerson() {
        return natureOfPerson;
    }

    public void setNatureOfPerson(int natureOfPerson) {
        this.natureOfPerson = natureOfPerson;
    }

    public int getDayOfMutation() {
        return dayOfMutation;
    }

    public void setDayOfMutation(int dayOfMutation) {
        this.dayOfMutation = dayOfMutation;
    }

    public int getMonthOfMutation() {
        return monthOfMutation;
    }

    public void setMonthOfMutation(int monthOfMutation) {
        this.monthOfMutation = monthOfMutation;
    }

    public int getYearOfMutation() {
        return yearOfMutation;
    }

    public void setYearOfMutation(int yearOfMutation) {
        this.yearOfMutation = yearOfMutation;
    }

    public int getDayOfMutationAfterInterpretation() {
        return dayOfMutationAfterInterpretation;
    }

    public void setDayOfMutationAfterInterpretation(int dayOfMutationAfterInterpretation) {
        this.dayOfMutationAfterInterpretation = dayOfMutationAfterInterpretation;
    }

    public int getMonthOfMutationAfterInterpretation() {
        return monthOfMutationAfterInterpretation;
    }

    public void setMonthOfMutationAfterInterpretation(int monthOfMutationAfterInterpretation) {
        this.monthOfMutationAfterInterpretation = monthOfMutationAfterInterpretation;
    }

    public int getYearOfMutationAfterInterpretation() {
        return yearOfMutationAfterInterpretation;
    }

    public void setYearOfMutationAfterInterpretation(int yearOfMutationAfterInterpretation) {
        this.yearOfMutationAfterInterpretation = yearOfMutationAfterInterpretation;
    }

    public String getDynamicData2() {
        return dynamicData2;
    }

    public void setDynamicData2(String dynamicData2) {
        this.dynamicData2 = dynamicData2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}