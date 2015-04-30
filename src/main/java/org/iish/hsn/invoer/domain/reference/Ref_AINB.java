package org.iish.hsn.invoer.domain.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This class contains data of one AINB row
 */
@Entity
@Table(name = "ref_ainb")
public class Ref_AINB implements Serializable {
    @Id @Column(name = "B1IDBG", nullable = false) private int keyToSourceRegister;

    @Column(name = "CONTROLE", nullable = false) private String controle;
    @Column(name = "B1BGBG", nullable = false) private   int    b1bgbg;
    @Column(name = "GEMNAAM", nullable = false) private  String municipality;
    @Column(name = "PROVNR", nullable = false) private   int    provinceNumber;
    @Column(name = "B1ABBG", nullable = false) private   String typeRegister;
    @Column(name = "B1BJBG", nullable = false) private   int    startYearRegister;
    @Column(name = "B1BJBGCR", nullable = false) private int    startYearRegisterCorrected;
    @Column(name = "B1EJBG", nullable = false) private   int    endYearRegister;
    @Column(name = "B1EJBGCR", nullable = false) private int    endYearRegisterCorrected;
    @Column(name = "B1GWBG", nullable = false) private   String b1gwbg;
    @Column(name = "B1IVBG", nullable = false) private   String b1ivbg;

    @Column(name = "DATUM", nullable = false) private  String datum;
    @Column(name = "NWBRON", nullable = false) private String nwbron;
    @Column(name = "INIT", nullable = false) private   String init;
    @Column(name = "VERSIE", nullable = false) private String versie;

    public int getKeyToSourceRegister() {
        return keyToSourceRegister;
    }

    public void setKeyToSourceRegister(int keyToSourceRegister) {
        this.keyToSourceRegister = keyToSourceRegister;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public int getB1bgbg() {
        return b1bgbg;
    }

    public void setB1bgbg(int b1bgbg) {
        this.b1bgbg = b1bgbg;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public int getProvinceNumber() {
        return provinceNumber;
    }

    public void setProvinceNumber(int provinceNumber) {
        this.provinceNumber = provinceNumber;
    }

    public String getTypeRegister() {
        return typeRegister;
    }

    public void setTypeRegister(String typeRegister) {
        this.typeRegister = typeRegister;
    }

    public int getStartYearRegister() {
        return startYearRegister;
    }

    public void setStartYearRegister(int startYearRegister) {
        this.startYearRegister = startYearRegister;
    }

    public int getStartYearRegisterCorrected() {
        return startYearRegisterCorrected;
    }

    public void setStartYearRegisterCorrected(int startYearRegisterCorrected) {
        this.startYearRegisterCorrected = startYearRegisterCorrected;
    }

    public int getEndYearRegister() {
        return endYearRegister;
    }

    public void setEndYearRegister(int endYearRegister) {
        this.endYearRegister = endYearRegister;
    }

    public int getEndYearRegisterCorrected() {
        return endYearRegisterCorrected;
    }

    public void setEndYearRegisterCorrected(int endYearRegisterCorrected) {
        this.endYearRegisterCorrected = endYearRegisterCorrected;
    }

    public String getB1gwbg() {
        return b1gwbg;
    }

    public void setB1gwbg(String b1gwbg) {
        this.b1gwbg = b1gwbg;
    }

    public String getB1ivbg() {
        return b1ivbg;
    }

    public void setB1ivbg(String b1ivbg) {
        this.b1ivbg = b1ivbg;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNwbron() {
        return nwbron;
    }

    public void setNwbron(String nwbron) {
        this.nwbron = nwbron;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getVersie() {
        return versie;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public String getBron() {
        switch (typeRegister.toUpperCase()) {
            case "A":
                return "Alleenstaande register 1840-1940";
            case "B":
                return "Bevolkingsregister 1860-1940";
            case "C":
                return "Bevolkingsregister 1850-1865";
            case "D":
                return "Bevolkingsregister 1812-1850";
            case "G":
                return "Gezinskaarten-systeem 1880-1940";
            case "I":
                return "Individueel kaartsysteem 1880-1940";
            case "V":
                return "Volkstellingsregisters 1812-1850";
            case "X":
                return "Register niet aanwezig/verloren 1850-1940";
        }
        return "";
    }
}
