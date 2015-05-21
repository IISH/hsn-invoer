package org.iish.hsn.invoer.domain.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the static attributes of a registration
 */
@Entity
@Table(name = "b4", indexes = {
        @Index(columnList = "IDNR, B1IDBG, B2DIBG, B2MIBG, B2JIBG"), @Index(columnList = "ONDRZKO, OPDRNRI"),
        @Index(columnList = "B2FDBG, B2FMBG, B2FJBG")
})
public class Registration extends Invoer implements Serializable {
    @Embedded private RegistrationId registrationId = new RegistrationId();

    @Column(name = "B2FDBG", nullable = false) private int dayEntryRP;
    @Column(name = "B2FMBG", nullable = false) private int monthEntryRP;
    @Column(name = "B2FJBG", nullable = false) private int yearEntryRP;

    @Column(name = "B2PGBG", nullable = false, length = 8) private  String pageNumberOfSource = "";
    @Column(name = "B2VHBG", nullable = false) private              int    numberOfHousehold;
    @Column(name = "B4GKBG", nullable = false, length = 50) private String infoFamilyCardsSystem = "";
    @Column(name = "B4SPBG", nullable = false, length = 50) private String specialDataEntryCodes = "";
    @Column(name = "B4AAN", nullable = false, length = 255) private String remarks = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public RegistrationId getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(RegistrationId registrationId) {
        this.registrationId = registrationId;
    }

    public int getDayEntryRP() {
        return dayEntryRP;
    }

    public void setDayEntryRP(int dayEntryRP) {
        this.dayEntryRP = dayEntryRP;
    }

    public int getMonthEntryRP() {
        return monthEntryRP;
    }

    public void setMonthEntryRP(int monthEntryRP) {
        this.monthEntryRP = monthEntryRP;
    }

    public int getYearEntryRP() {
        return yearEntryRP;
    }

    public void setYearEntryRP(int yearEntryRP) {
        this.yearEntryRP = yearEntryRP;
    }

    public String getPageNumberOfSource() {
        return pageNumberOfSource;
    }

    public void setPageNumberOfSource(String pageNumberOfSource) {
        this.pageNumberOfSource = pageNumberOfSource;
    }

    public int getNumberOfHousehold() {
        return numberOfHousehold;
    }

    public void setNumberOfHousehold(int numberOfHousehold) {
        this.numberOfHousehold = numberOfHousehold;
    }

    public String getInfoFamilyCardsSystem() {
        return infoFamilyCardsSystem;
    }

    public void setInfoFamilyCardsSystem(String infoFamilyCardsSystem) {
        this.infoFamilyCardsSystem = infoFamilyCardsSystem;
    }

    public String getSpecialDataEntryCodes() {
        return specialDataEntryCodes;
    }

    public void setSpecialDataEntryCodes(String specialDataEntryCodes) {
        this.specialDataEntryCodes = specialDataEntryCodes;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}