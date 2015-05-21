package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gebakte",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Gebakte extends Invoer implements Serializable {
    @Column(name = "GEMNR", nullable = false) private                int    gemnr;
    @Column(name = "JAAR", nullable = false) private                 int    jaar;
    @Column(name = "AKTENR", nullable = false) private               int    aktenr;
    @Column(name = "IDNR", nullable = false) private                 int    idnr;
    @Column(name = "GEBKODE", nullable = false) private              int    gebkode;
    @Column(name = "OVERSAMP", nullable = false, length = 1) private String oversamp = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public int getGemnr() {
        return gemnr;
    }

    public void setGemnr(int gemnr) {
        this.gemnr = gemnr;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public int getAktenr() {
        return aktenr;
    }

    public void setAktenr(int aktenr) {
        this.aktenr = aktenr;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getGebkode() {
        return gebkode;
    }

    public void setGebkode(int gebkode) {
        this.gebkode = gebkode;
    }

    public String getOversamp() {
        return oversamp;
    }

    public void setOversamp(String oversamp) {
        this.oversamp = oversamp;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
