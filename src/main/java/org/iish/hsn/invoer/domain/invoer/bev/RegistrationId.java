package org.iish.hsn.invoer.domain.invoer.bev;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RegistrationId implements Serializable {
    @Column(name = "B1IDBG", nullable = false) private int keyToSourceRegister;
    @Column(name = "B2DIBG", nullable = false) private int dayEntryHead;
    @Column(name = "B2MIBG", nullable = false) private int monthEntryHead;
    @Column(name = "B2JIBG", nullable = false) private int yearEntryHead;
    @Column(name = "IDNR", nullable = false) private   int keyToRP;

    public int getKeyToRP() {
        return keyToRP;
    }

    public void setKeyToRP(int keyToRP) {
        this.keyToRP = keyToRP;
    }

    public int getKeyToSourceRegister() {
        return keyToSourceRegister;
    }

    public void setKeyToSourceRegister(int keyToSourceRegister) {
        this.keyToSourceRegister = keyToSourceRegister;
    }

    public int getDayEntryHead() {
        return dayEntryHead;
    }

    public void setDayEntryHead(int dayEntryHead) {
        this.dayEntryHead = dayEntryHead;
    }

    public int getMonthEntryHead() {
        return monthEntryHead;
    }

    public void setMonthEntryHead(int monthEntryHead) {
        this.monthEntryHead = monthEntryHead;
    }

    public int getYearEntryHead() {
        return yearEntryHead;
    }

    public void setYearEntryHead(int yearEntryHead) {
        this.yearEntryHead = yearEntryHead;
    }

    @Override
    public String toString() {
        return keyToSourceRegister + ", " + dayEntryHead + "-" + monthEntryHead + "-" + yearEntryHead + ", " + keyToRP;
    }
}
