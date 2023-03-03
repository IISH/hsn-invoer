package org.iish.hsn.invoer.domain.reference;

import org.iish.hsn.invoer.util.NullSafeUtils;

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

    @Column(name = "CONTROLE") private String  controle;
    @Column(name = "B1BGBG") private   Integer b1bgbg;
    @Column(name = "GEMNAAM") private  String  municipality;
    @Column(name = "PROVNR") private   Integer provinceNumber;
    @Column(name = "B1ABBG") private   String  typeRegister;
    @Column(name = "B1BJBG") private   Integer startYearRegister;
    @Column(name = "B1BJBGCR") private Integer startYearRegisterCorrected;
    @Column(name = "B1EJBG") private   Integer endYearRegister;
    @Column(name = "B1EJBGCR") private Integer endYearRegisterCorrected;
    @Column(name = "B1GWBG") private   String  b1gwbg;
    @Column(name = "B1IVBG") private   String  b1ivbg;

    @Column(name = "DATUM") private  String datum;
    @Column(name = "NWBRON") private String nwbron;
    @Column(name = "INIT") private   String init;
    @Column(name = "VERSIE") private String versie;

    public int getKeyToSourceRegister() {
        return keyToSourceRegister;
    }

    public String getControle() {
        return NullSafeUtils.getString(controle);
    }

    public int getB1bgbg() {
        return NullSafeUtils.getInteger(b1bgbg);
    }

    public String getMunicipality() {
        return NullSafeUtils.getString(municipality);
    }

    public int getProvinceNumber() {
        return NullSafeUtils.getInteger(provinceNumber);
    }

    public String getTypeRegister() {
        return NullSafeUtils.getString(typeRegister);
    }

    public int getStartYearRegister() {
        return NullSafeUtils.getInteger(startYearRegister);
    }

    public int getStartYearRegisterCorrected() {
        return NullSafeUtils.getInteger(startYearRegisterCorrected);
    }

    public int getEndYearRegister() {
        return NullSafeUtils.getInteger(endYearRegister);
    }

    public int getEndYearRegisterCorrected() {
        return NullSafeUtils.getInteger(endYearRegisterCorrected);
    }

    public String getB1gwbg() {
        return NullSafeUtils.getString(b1gwbg);
    }

    public String getB1ivbg() {
        return NullSafeUtils.getString(b1ivbg);
    }

    public String getDatum() {
        return NullSafeUtils.getString(datum);
    }

    public String getNwbron() {
        return NullSafeUtils.getString(nwbron);
    }

    public String getInit() {
        return NullSafeUtils.getString(init);
    }

    public String getVersie() {
        return NullSafeUtils.getString(versie);
    }

    public String getBron() {
        return switch (getTypeRegister().toUpperCase()) {
            case "A" -> "Alleenstaande register 1840-1940";
            case "B" -> "Bevolkingsregister 1860-1940";
            case "C" -> "Bevolkingsregister 1850-1865";
            case "D" -> "Bevolkingsregister 1812-1850";
            case "G" -> "Gezinskaarten-systeem 1880-1940";
            case "I" -> "Individueel kaartsysteem 1880-1940";
            case "V" -> "Volkstellingsregisters 1812-1850";
            case "X" -> "Register niet aanwezig/verloren 1850-1940";
            default -> "";
        };
    }
}
