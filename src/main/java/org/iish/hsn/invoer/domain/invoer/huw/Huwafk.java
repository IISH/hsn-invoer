package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huwafk",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "HDAG", "HMAAND", "HJAAR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Huwafk extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private Huw huw = new Huw();

    @Column(name = "HWAKNR", nullable = false) private              int    hwaknr;
    @Column(name = "HWAKDG", nullable = false) private              int    hwakdg;
    @Column(name = "HWAKMD", nullable = false) private              int    hwakmd;
    @Column(name = "HWAKJR", nullable = false) private              int    hwakjr;
    @Column(name = "HWAKGR", nullable = false) private              int    hwakgr;
    @Column(name = "HWAKPL", nullable = false, length = 50) private String hwakpl;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Huwafk() {
    }

    public Huwafk(int hwaknr) {
        this.hwaknr = hwaknr;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public Huw getHuw() {
        return huw;
    }

    public void setHuw(Huw huw) {
        this.huw = huw;
    }

    public int getHwaknr() {
        return hwaknr;
    }

    public void setHwaknr(int hwaknr) {
        this.hwaknr = hwaknr;
    }

    public int getHwakdg() {
        return hwakdg;
    }

    public void setHwakdg(int hwakdg) {
        this.hwakdg = hwakdg;
    }

    public int getHwakmd() {
        return hwakmd;
    }

    public void setHwakmd(int hwakmd) {
        this.hwakmd = hwakmd;
    }

    public int getHwakjr() {
        return hwakjr;
    }

    public void setHwakjr(int hwakjr) {
        this.hwakjr = hwakjr;
    }

    public int getHwakgr() {
        return hwakgr;
    }

    public void setHwakgr(int hwakgr) {
        this.hwakgr = hwakgr;
    }

    public String getHwakpl() {
        return hwakpl;
    }

    public void setHwakpl(String hwakpl) {
        this.hwakpl = hwakpl;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
