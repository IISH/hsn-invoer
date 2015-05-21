package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stpb")
public class Stpb extends Invoer implements Serializable {
    @Column(name = "DOELNR", nullable = false) private                int    doelnr;
    @Column(name = "GEMEENTE", nullable = false, length = 50) private String gemeente = "";
    @Column(name = "GEMNR", nullable = false) private                 int    gemnr;
    @Column(name = "JAAR", nullable = false) private                  int    jaar;
    @Column(name = "AKTENR", nullable = false) private                int    aktenr;
    @Column(name = "COHORTNR", nullable = false) private              int    cohortnr;
    @Column(name = "IDNR", nullable = false) private                  int    idnr;
    @Column(name = "PROVNR", nullable = false) private                int    provnr;
    @Column(name = "REGNR", nullable = false) private                 int    regnr;
    @Column(name = "SUBCOHNR", nullable = false) private              int    subcohnr;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public int getDoelnr() {
        return doelnr;
    }

    public void setDoelnr(int doelnr) {
        this.doelnr = doelnr;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

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

    public int getCohortnr() {
        return cohortnr;
    }

    public void setCohortnr(int cohortnr) {
        this.cohortnr = cohortnr;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getProvnr() {
        return provnr;
    }

    public void setProvnr(int provnr) {
        this.provnr = provnr;
    }

    public int getRegnr() {
        return regnr;
    }

    public void setRegnr(int regnr) {
        this.regnr = regnr;
    }

    public int getSubcohnr() {
        return subcohnr;
    }

    public void setSubcohnr(int subcohnr) {
        this.subcohnr = subcohnr;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
