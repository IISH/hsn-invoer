package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gebknd",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Gebknd extends Invoer implements Serializable {
    @Column(name = "GEMNR", nullable = false) private                int    gemnr;
    @Column(name = "JAAR", nullable = false) private                 int    jaar;
    @Column(name = "AKTENR", nullable = false) private               int    aktenr;
    @Column(name = "COHORTNR", nullable = false) private             int    cohortnr;
    @Column(name = "IDNR", nullable = false) private                 int    idnr;
    @Column(name = "OVERSAMP", nullable = false, length = 1) private String oversamp;
    @Column(name = "INVBEPER", nullable = false, length = 1) private String invbeper;
    @Column(name = "AKTEUUR", nullable = false) private              int    akteuur;
    @Column(name = "AKTEDAG", nullable = false) private              int    aktedag;
    @Column(name = "AKTEMND", nullable = false) private              int    aktemnd;
    @Column(name = "LENGEB", nullable = false) private               int    lengeb;
    @Column(name = "ANMAG", nullable = false, length = 50) private   String anmag;
    @Column(name = "TUSAG", nullable = false, length = 10) private   String tusag;
    @Column(name = "VRN1AG", nullable = false, length = 20) private  String vrn1ag;
    @Column(name = "VRN2AG", nullable = false, length = 20) private  String vrn2ag;
    @Column(name = "VRN3AG", nullable = false, length = 30) private  String vrn3ag;
    @Column(name = "LFTAG", nullable = false) private                int    lftag;
    @Column(name = "BRPAG", nullable = false, length = 50) private   String brpag;
    @Column(name = "ADRAG", nullable = false, length = 50) private   String adrag;
    @Column(name = "HNDAG", nullable = false, length = 1) private    String hndag;
    @Column(name = "GEBDAG", nullable = false) private               int    gebdag;
    @Column(name = "GEBMND", nullable = false) private               int    gebmnd;
    @Column(name = "GEBJR", nullable = false) private                int    gebjr;
    @Column(name = "GEBUUR", nullable = false) private               int    gebuur;
    @Column(name = "GEBMIN", nullable = false) private               int    gebmin;
    @Column(name = "GEBSEX", nullable = false, length = 1) private   String gebsex;
    @Column(name = "GEBADR", nullable = false, length = 50) private  String gebadr;
    @Column(name = "ANMMR", nullable = false, length = 50) private   String anmmr;
    @Column(name = "TUSMR", nullable = false, length = 10) private   String tusmr;
    @Column(name = "VRN1MR", nullable = false, length = 20) private  String vrn1mr;
    @Column(name = "VRN2MR", nullable = false, length = 20) private  String vrn2mr;
    @Column(name = "VRN3MR", nullable = false, length = 30) private  String vrn3mr;
    @Column(name = "LFTMR", nullable = false) private                int    lftmr;
    @Column(name = "BRGSTMR", nullable = false, length = 1) private  String brgstmr;
    @Column(name = "BRPMR", nullable = false, length = 50) private   String brpmr;
    @Column(name = "ADRMR", nullable = false, length = 50) private   String adrmr;
    @Column(name = "ANMGEB", nullable = false, length = 50) private  String anmgeb;
    @Column(name = "TUSGEB", nullable = false, length = 10) private  String tusgeb;
    @Column(name = "VRN1GEB", nullable = false, length = 20) private String vrn1geb;
    @Column(name = "VRN2GEB", nullable = false, length = 20) private String vrn2geb;
    @Column(name = "VRN3GEB", nullable = false, length = 30) private String vrn3geb;
    @Column(name = "KANT", nullable = false, length = 1) private     String kant;
    @Column(name = "PROBLM", nullable = false, length = 1) private   String problm;

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

    public String getOversamp() {
        return oversamp;
    }

    public void setOversamp(String oversamp) {
        this.oversamp = oversamp;
    }

    public String getInvbeper() {
        return invbeper;
    }

    public void setInvbeper(String invbeper) {
        this.invbeper = invbeper;
    }

    public int getAkteuur() {
        return akteuur;
    }

    public void setAkteuur(int akteuur) {
        this.akteuur = akteuur;
    }

    public int getAktedag() {
        return aktedag;
    }

    public void setAktedag(int aktedag) {
        this.aktedag = aktedag;
    }

    public int getAktemnd() {
        return aktemnd;
    }

    public void setAktemnd(int aktemnd) {
        this.aktemnd = aktemnd;
    }

    public int getLengeb() {
        return lengeb;
    }

    public void setLengeb(int lengeb) {
        this.lengeb = lengeb;
    }

    public String getAnmag() {
        return anmag;
    }

    public void setAnmag(String anmag) {
        this.anmag = anmag;
    }

    public String getTusag() {
        return tusag;
    }

    public void setTusag(String tusag) {
        this.tusag = tusag;
    }

    public String getVrn1ag() {
        return vrn1ag;
    }

    public void setVrn1ag(String vrn1ag) {
        this.vrn1ag = vrn1ag;
    }

    public String getVrn2ag() {
        return vrn2ag;
    }

    public void setVrn2ag(String vrn2ag) {
        this.vrn2ag = vrn2ag;
    }

    public String getVrn3ag() {
        return vrn3ag;
    }

    public void setVrn3ag(String vrn3ag) {
        this.vrn3ag = vrn3ag;
    }

    public int getLftag() {
        return lftag;
    }

    public void setLftag(int lftag) {
        this.lftag = lftag;
    }

    public String getBrpag() {
        return brpag;
    }

    public void setBrpag(String brpag) {
        this.brpag = brpag;
    }

    public String getAdrag() {
        return adrag;
    }

    public void setAdrag(String adrag) {
        this.adrag = adrag;
    }

    public String getHndag() {
        return hndag;
    }

    public void setHndag(String hndag) {
        this.hndag = hndag;
    }

    public int getGebdag() {
        return gebdag;
    }

    public void setGebdag(int gebdag) {
        this.gebdag = gebdag;
    }

    public int getGebmnd() {
        return gebmnd;
    }

    public void setGebmnd(int gebmnd) {
        this.gebmnd = gebmnd;
    }

    public int getGebjr() {
        return gebjr;
    }

    public void setGebjr(int gebjr) {
        this.gebjr = gebjr;
    }

    public int getGebuur() {
        return gebuur;
    }

    public void setGebuur(int gebuur) {
        this.gebuur = gebuur;
    }

    public int getGebmin() {
        return gebmin;
    }

    public void setGebmin(int gebmin) {
        this.gebmin = gebmin;
    }

    public String getGebsex() {
        return gebsex;
    }

    public void setGebsex(String gebsex) {
        this.gebsex = gebsex;
    }

    public String getGebadr() {
        return gebadr;
    }

    public void setGebadr(String gebadr) {
        this.gebadr = gebadr;
    }

    public String getAnmmr() {
        return anmmr;
    }

    public void setAnmmr(String anmmr) {
        this.anmmr = anmmr;
    }

    public String getTusmr() {
        return tusmr;
    }

    public void setTusmr(String tusmr) {
        this.tusmr = tusmr;
    }

    public String getVrn1mr() {
        return vrn1mr;
    }

    public void setVrn1mr(String vrn1mr) {
        this.vrn1mr = vrn1mr;
    }

    public String getVrn2mr() {
        return vrn2mr;
    }

    public void setVrn2mr(String vrn2mr) {
        this.vrn2mr = vrn2mr;
    }

    public String getVrn3mr() {
        return vrn3mr;
    }

    public void setVrn3mr(String vrn3mr) {
        this.vrn3mr = vrn3mr;
    }

    public int getLftmr() {
        return lftmr;
    }

    public void setLftmr(int lftmr) {
        this.lftmr = lftmr;
    }

    public String getBrgstmr() {
        return brgstmr;
    }

    public void setBrgstmr(String brgstmr) {
        this.brgstmr = brgstmr;
    }

    public String getBrpmr() {
        return brpmr;
    }

    public void setBrpmr(String brpmr) {
        this.brpmr = brpmr;
    }

    public String getAdrmr() {
        return adrmr;
    }

    public void setAdrmr(String adrmr) {
        this.adrmr = adrmr;
    }

    public String getAnmgeb() {
        return anmgeb;
    }

    public void setAnmgeb(String anmgeb) {
        this.anmgeb = anmgeb;
    }

    public String getTusgeb() {
        return tusgeb;
    }

    public void setTusgeb(String tusgeb) {
        this.tusgeb = tusgeb;
    }

    public String getVrn1geb() {
        return vrn1geb;
    }

    public void setVrn1geb(String vrn1geb) {
        this.vrn1geb = vrn1geb;
    }

    public String getVrn2geb() {
        return vrn2geb;
    }

    public void setVrn2geb(String vrn2geb) {
        this.vrn2geb = vrn2geb;
    }

    public String getVrn3geb() {
        return vrn3geb;
    }

    public void setVrn3geb(String vrn3geb) {
        this.vrn3geb = vrn3geb;
    }

    public String getKant() {
        return kant;
    }

    public void setKant(String kant) {
        this.kant = kant;
    }

    public String getProblm() {
        return problm;
    }

    public void setProblm(String problm) {
        this.problm = problm;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}