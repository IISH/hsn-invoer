package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;

@Entity
@Table(name = "geb")
public class Geb extends Invoer {
    @Column(name = "GEMNR", nullable = false) private    int    gemnr;
    @Column(name = "GEMNAAM", nullable = false) private  String gemnaam;
    @Column(name = "JAAR", nullable = false) private     int    jaar;
    @Column(name = "AKTENR", nullable = false) private   int    aktenr;
    @Column(name = "IDNR", nullable = false) private     int    idnr;
    @Column(name = "GEBKODE", nullable = false) private  int    gebkode;
    @Column(name = "OVERSAMP", nullable = false) private String oversamp;

    @Id @Column(name = "RecordID") private Integer recordID;

    public int getGemnr() {
        return gemnr;
    }

    public String getGemnaam() {
        return gemnaam;
    }

    public int getJaar() {
        return jaar;
    }

    public int getAktenr() {
        return aktenr;
    }

    public int getIdnr() {
        return idnr;
    }

    public int getGebkode() {
        return gebkode;
    }

    public String getOversamp() {
        return oversamp;
    }

    public Integer getRecordID() {
        return recordID;
    }
}
