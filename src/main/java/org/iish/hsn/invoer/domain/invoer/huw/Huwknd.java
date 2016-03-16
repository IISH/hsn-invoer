package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huwknd",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "HDAG", "HMAAND", "HJAAR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Huwknd extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private Huw huw = new Huw();

    @Column(name = "HGEMNR", nullable = false) private                int    hgemnr;
    @Column(name = "HAKTENR", nullable = false) private               int    haktenr;
    @Column(name = "HPLTS", nullable = false, length = 50) private    String hplts = "";
    @Column(name = "HUUR", nullable = false) private                  int    huur;
    @Column(name = "SCHEIDNG", nullable = false, length = 1) private  String scheidng = "";
    @Column(name = "SDAG", nullable = false) private                  int    sdag;
    @Column(name = "SMAAND", nullable = false) private                int    smaand;
    @Column(name = "SJAAR", nullable = false) private                 int    sjaar;
    @Column(name = "SPLTS", nullable = false, length = 50) private    String splts = "";
    @Column(name = "IDAG", nullable = false) private                  int    idag;
    @Column(name = "IMAAND", nullable = false) private                int    imaand;
    @Column(name = "IJAAR", nullable = false) private                 int    ijaar;
    @Column(name = "IPLTS", nullable = false, length = 50) private    String iplts = "";
    @Column(name = "LFTJHM", nullable = false) private                int    lftjhm;
    @Column(name = "LFTJHV", nullable = false) private                int    lftjhv;
    @Column(name = "GEBSEX", nullable = false, length = 1) private    String gebsex = "";
    @Column(name = "ANMHM", nullable = false, length = 50) private    String anmhm = "";
    @Column(name = "TUSHM", nullable = false, length = 10) private    String tushm = "";
    @Column(name = "VRN1HM", nullable = false, length = 20) private   String vrn1hm = "";
    @Column(name = "VRN2HM", nullable = false, length = 20) private   String vrn2hm = "";
    @Column(name = "VRN3HM", nullable = false, length = 30) private   String vrn3hm = "";
    @Column(name = "BRPHM", nullable = false, length = 50) private    String brphm = "";
    @Column(name = "GEBPLNHM", nullable = false) private              int    gebplnhm;
    @Column(name = "GEBPLHM", nullable = false, length = 50) private  String gebplhm = "";
    @Column(name = "ADRHM", nullable = false, length = 50) private    String adrhm = "";
    @Column(name = "OADRHM", nullable = false, length = 50) private   String oadrhm = "";
    @Column(name = "OADREHM", nullable = false, length = 50) private  String oadrehm = "";
    @Column(name = "BSTHM", nullable = false, length = 1) private     String bsthm = "";
    @Column(name = "HNDHM", nullable = false, length = 1) private     String hndhm = "";
    @Column(name = "ANMHV", nullable = false, length = 50) private    String anmhv = "";
    @Column(name = "TUSHV", nullable = false, length = 10) private    String tushv = "";
    @Column(name = "VRN1HV", nullable = false, length = 20) private   String vrn1hv = "";
    @Column(name = "VRN2HV", nullable = false, length = 20) private   String vrn2hv = "";
    @Column(name = "VRN3HV", nullable = false, length = 30) private   String vrn3hv = "";
    @Column(name = "BRPHV", nullable = false, length = 50) private    String brphv = "";
    @Column(name = "GEBPLNHV", nullable = false) private              int    gebplnhv;
    @Column(name = "GEBPLHV", nullable = false, length = 50) private  String gebplhv = "";
    @Column(name = "ADRHV", nullable = false, length = 50) private    String adrhv = "";
    @Column(name = "OADRHV", nullable = false, length = 50) private   String oadrhv = "";
    @Column(name = "OADREHV", nullable = false, length = 50) private  String oadrehv = "";
    @Column(name = "BSTHV", nullable = false, length = 1) private     String bsthv = "";
    @Column(name = "HNDHV", nullable = false, length = 1) private     String hndhv = "";
    @Column(name = "LEVVRHM", nullable = false, length = 1) private   String levvrhm = "";
    @Column(name = "TOEVRHM", nullable = false, length = 1) private   String toevrhm = "";
    @Column(name = "ANMVRHM", nullable = false, length = 50) private  String anmvrhm = "";
    @Column(name = "TUSVRHM", nullable = false, length = 10) private  String tusvrhm = "";
    @Column(name = "VRN1VRHM", nullable = false, length = 20) private String vrn1vrhm = "";
    @Column(name = "VRN2VRHM", nullable = false, length = 20) private String vrn2vrhm = "";
    @Column(name = "VRN3VRHM", nullable = false, length = 30) private String vrn3vrhm = "";
    @Column(name = "BRPVRHM", nullable = false, length = 50) private  String brpvrhm = "";
    @Column(name = "ADRVRHM", nullable = false, length = 50) private  String adrvrhm = "";
    @Column(name = "PLOVVRHM", nullable = false, length = 50) private String plovvrhm = "";
    @Column(name = "HNDVRHM", nullable = false, length = 1) private   String hndvrhm = "";
    @Column(name = "LFTJVRHM", nullable = false) private              int    lftjvrhm;
    @Column(name = "LEVMRHM", nullable = false, length = 1) private   String levmrhm = "";
    @Column(name = "TOEMRHM", nullable = false, length = 1) private   String toemrhm = "";
    @Column(name = "ANMMRHM", nullable = false, length = 50) private  String anmmrhm = "";
    @Column(name = "TUSMRHM", nullable = false, length = 10) private  String tusmrhm = "";
    @Column(name = "VRN1MRHM", nullable = false, length = 20) private String vrn1mrhm = "";
    @Column(name = "VRN2MRHM", nullable = false, length = 20) private String vrn2mrhm = "";
    @Column(name = "VRN3MRHM", nullable = false, length = 30) private String vrn3mrhm = "";
    @Column(name = "BRPMRHM", nullable = false, length = 50) private  String brpmrhm = "";
    @Column(name = "ADRMRHM", nullable = false, length = 50) private  String adrmrhm = "";
    @Column(name = "PLOVMRHM", nullable = false, length = 50) private String plovmrhm = "";
    @Column(name = "HNDMRHM", nullable = false, length = 1) private   String hndmrhm = "";
    @Column(name = "LFTJMRHM", nullable = false) private              int    lftjmrhm;
    @Column(name = "LEVVRHV", nullable = false, length = 1) private   String levvrhv = "";
    @Column(name = "TOEVRHV", nullable = false, length = 1) private   String toevrhv = "";
    @Column(name = "ANMVRHV", nullable = false, length = 50) private  String anmvrhv = "";
    @Column(name = "TUSVRHV", nullable = false, length = 10) private  String tusvrhv = "";
    @Column(name = "VRN1VRHV", nullable = false, length = 20) private String vrn1vrhv = "";
    @Column(name = "VRN2VRHV", nullable = false, length = 20) private String vrn2vrhv = "";
    @Column(name = "VRN3VRHV", nullable = false, length = 30) private String vrn3vrhv = "";
    @Column(name = "BRPVRHV", nullable = false, length = 50) private  String brpvrhv = "";
    @Column(name = "ADRVRHV", nullable = false, length = 50) private  String adrvrhv = "";
    @Column(name = "PLOVVRHV", nullable = false, length = 50) private String plovvrhv = "";
    @Column(name = "HNDVRHV", nullable = false, length = 1) private   String hndvrhv = "";
    @Column(name = "LFTJVRHV", nullable = false) private              int    lftjvrhv;
    @Column(name = "LEVMRHV", nullable = false, length = 1) private   String levmrhv = "";
    @Column(name = "TOEMRHV", nullable = false, length = 1) private   String toemrhv = "";
    @Column(name = "ANMMRHV", nullable = false, length = 50) private  String anmmrhv = "";
    @Column(name = "TUSMRHV", nullable = false, length = 10) private  String tusmrhv = "";
    @Column(name = "VRN1MRHV", nullable = false, length = 20) private String vrn1mrhv = "";
    @Column(name = "VRN2MRHV", nullable = false, length = 20) private String vrn2mrhv = "";
    @Column(name = "VRN3MRHV", nullable = false, length = 30) private String vrn3mrhv = "";
    @Column(name = "BRPMRHV", nullable = false, length = 50) private  String brpmrhv = "";
    @Column(name = "ADRMRHV", nullable = false, length = 50) private  String adrmrhv = "";
    @Column(name = "PLOVMRHV", nullable = false, length = 50) private String plovmrhv = "";
    @Column(name = "HNDMRHV", nullable = false, length = 1) private   String hndmrhv = "";
    @Column(name = "LFTJMRHV", nullable = false) private              int    lftjmrhv;
    @Column(name = "UGEBHUW", nullable = false, length = 1) private   String ugebhuw = "";
    @Column(name = "UOVLOUD", nullable = false, length = 1) private   String uovloud = "";
    @Column(name = "UOVLECH", nullable = false, length = 1) private   String uovlech = "";
    @Column(name = "CERTNATM", nullable = false, length = 1) private  String certnatm = "";
    @Column(name = "TOESTNOT", nullable = false, length = 1) private  String toestnot = "";
    @Column(name = "AKTEBEK", nullable = false, length = 1) private   String aktebek = "";
    @Column(name = "ONVERMGN", nullable = false, length = 1) private  String onvermgn = "";
    @Column(name = "COMMAND", nullable = false, length = 1) private   String command = "";
    @Column(name = "TOESTVGD", nullable = false, length = 1) private  String toestvgd = "";
    @Column(name = "GEGHUW", nullable = false, length = 1) private    String geghuw = "";
    @Column(name = "GEGVR", nullable = false, length = 1) private     String gegvr = "";
    @Column(name = "GEGMR", nullable = false, length = 1) private     String gegmr = "";
    @Column(name = "PROBLM", nullable = false, length = 1) private    String problm = "";
    @Column(name = "NGTG", nullable = false) private                  int    ngtg;
    @Column(name = "ERKEN", nullable = false, length = 1) private     String erken = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

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

    public int getHgemnr() {
        return hgemnr;
    }

    public void setHgemnr(int hgemnr) {
        this.hgemnr = hgemnr;
    }

    public int getHaktenr() {
        return haktenr;
    }

    public void setHaktenr(int haktenr) {
        this.haktenr = haktenr;
    }

    public String getHplts() {
        return hplts;
    }

    public void setHplts(String hplts) {
        this.hplts = hplts;
    }

    public int getHuur() {
        return huur;
    }

    public void setHuur(int huur) {
        this.huur = huur;
    }

    public String getScheidng() {
        return scheidng;
    }

    public void setScheidng(String scheidng) {
        this.scheidng = scheidng;
    }

    public int getSdag() {
        return sdag;
    }

    public void setSdag(int sdag) {
        this.sdag = sdag;
    }

    public int getSmaand() {
        return smaand;
    }

    public void setSmaand(int smaand) {
        this.smaand = smaand;
    }

    public int getSjaar() {
        return sjaar;
    }

    public void setSjaar(int sjaar) {
        this.sjaar = sjaar;
    }

    public String getSplts() {
        return splts;
    }

    public void setSplts(String splts) {
        this.splts = splts;
    }

    public int getIdag() {
        return idag;
    }

    public void setIdag(int idag) {
        this.idag = idag;
    }

    public int getImaand() {
        return imaand;
    }

    public void setImaand(int imaand) {
        this.imaand = imaand;
    }

    public int getIjaar() {
        return ijaar;
    }

    public void setIjaar(int ijaar) {
        this.ijaar = ijaar;
    }

    public String getIplts() {
        return iplts;
    }

    public void setIplts(String iplts) {
        this.iplts = iplts;
    }

    public int getLftjhm() {
        return lftjhm;
    }

    public void setLftjhm(int lftjhm) {
        this.lftjhm = lftjhm;
    }

    public int getLftjhv() {
        return lftjhv;
    }

    public void setLftjhv(int lftjhv) {
        this.lftjhv = lftjhv;
    }

    public String getGebsex() {
        return gebsex;
    }

    public void setGebsex(String gebsex) {
        this.gebsex = gebsex;
    }

    public String getAnmhm() {
        return anmhm;
    }

    public void setAnmhm(String anmhm) {
        this.anmhm = anmhm;
    }

    public String getTushm() {
        return tushm;
    }

    public void setTushm(String tushm) {
        this.tushm = tushm;
    }

    public String getVrn1hm() {
        return vrn1hm;
    }

    public void setVrn1hm(String vrn1hm) {
        this.vrn1hm = vrn1hm;
    }

    public String getVrn2hm() {
        return vrn2hm;
    }

    public void setVrn2hm(String vrn2hm) {
        this.vrn2hm = vrn2hm;
    }

    public String getVrn3hm() {
        return vrn3hm;
    }

    public void setVrn3hm(String vrn3hm) {
        this.vrn3hm = vrn3hm;
    }

    public String getBrphm() {
        return brphm;
    }

    public void setBrphm(String brphm) {
        this.brphm = brphm;
    }

    public int getGebplnhm() {
        return gebplnhm;
    }

    public void setGebplnhm(int gebplnhm) {
        this.gebplnhm = gebplnhm;
    }

    public String getGebplhm() {
        return gebplhm;
    }

    public void setGebplhm(String gebplhm) {
        this.gebplhm = gebplhm;
    }

    public String getAdrhm() {
        return adrhm;
    }

    public void setAdrhm(String adrhm) {
        this.adrhm = adrhm;
    }

    public String getOadrhm() {
        return oadrhm;
    }

    public void setOadrhm(String oadrhm) {
        this.oadrhm = oadrhm;
    }

    public String getOadrehm() {
        return oadrehm;
    }

    public void setOadrehm(String oadrehm) {
        this.oadrehm = oadrehm;
    }

    public String getBsthm() {
        return bsthm;
    }

    public void setBsthm(String bsthm) {
        this.bsthm = bsthm;
    }

    public String getHndhm() {
        return hndhm;
    }

    public void setHndhm(String hndhm) {
        this.hndhm = hndhm;
    }

    public String getAnmhv() {
        return anmhv;
    }

    public void setAnmhv(String anmhv) {
        this.anmhv = anmhv;
    }

    public String getTushv() {
        return tushv;
    }

    public void setTushv(String tushv) {
        this.tushv = tushv;
    }

    public String getVrn1hv() {
        return vrn1hv;
    }

    public void setVrn1hv(String vrn1hv) {
        this.vrn1hv = vrn1hv;
    }

    public String getVrn2hv() {
        return vrn2hv;
    }

    public void setVrn2hv(String vrn2hv) {
        this.vrn2hv = vrn2hv;
    }

    public String getVrn3hv() {
        return vrn3hv;
    }

    public void setVrn3hv(String vrn3hv) {
        this.vrn3hv = vrn3hv;
    }

    public String getBrphv() {
        return brphv;
    }

    public void setBrphv(String brphv) {
        this.brphv = brphv;
    }

    public int getGebplnhv() {
        return gebplnhv;
    }

    public void setGebplnhv(int gebplnhv) {
        this.gebplnhv = gebplnhv;
    }

    public String getGebplhv() {
        return gebplhv;
    }

    public void setGebplhv(String gebplhv) {
        this.gebplhv = gebplhv;
    }

    public String getAdrhv() {
        return adrhv;
    }

    public void setAdrhv(String adrhv) {
        this.adrhv = adrhv;
    }

    public String getOadrhv() {
        return oadrhv;
    }

    public void setOadrhv(String oadrhv) {
        this.oadrhv = oadrhv;
    }

    public String getOadrehv() {
        return oadrehv;
    }

    public void setOadrehv(String oadrehv) {
        this.oadrehv = oadrehv;
    }

    public String getBsthv() {
        return bsthv;
    }

    public void setBsthv(String bsthv) {
        this.bsthv = bsthv;
    }

    public String getHndhv() {
        return hndhv;
    }

    public void setHndhv(String hndhv) {
        this.hndhv = hndhv;
    }

    public String getLevvrhm() {
        return levvrhm;
    }

    public void setLevvrhm(String levvrhm) {
        this.levvrhm = levvrhm;
    }

    public String getToevrhm() {
        return toevrhm;
    }

    public void setToevrhm(String toevrhm) {
        this.toevrhm = toevrhm;
    }

    public String getAnmvrhm() {
        return anmvrhm;
    }

    public void setAnmvrhm(String anmvrhm) {
        this.anmvrhm = anmvrhm;
    }

    public String getTusvrhm() {
        return tusvrhm;
    }

    public void setTusvrhm(String tusvrhm) {
        this.tusvrhm = tusvrhm;
    }

    public String getVrn1vrhm() {
        return vrn1vrhm;
    }

    public void setVrn1vrhm(String vrn1vrhm) {
        this.vrn1vrhm = vrn1vrhm;
    }

    public String getVrn2vrhm() {
        return vrn2vrhm;
    }

    public void setVrn2vrhm(String vrn2vrhm) {
        this.vrn2vrhm = vrn2vrhm;
    }

    public String getVrn3vrhm() {
        return vrn3vrhm;
    }

    public void setVrn3vrhm(String vrn3vrhm) {
        this.vrn3vrhm = vrn3vrhm;
    }

    public String getBrpvrhm() {
        return brpvrhm;
    }

    public void setBrpvrhm(String brpvrhm) {
        this.brpvrhm = brpvrhm;
    }

    public String getAdrvrhm() {
        return adrvrhm;
    }

    public void setAdrvrhm(String adrvrhm) {
        this.adrvrhm = adrvrhm;
    }

    public String getPlovvrhm() {
        return plovvrhm;
    }

    public void setPlovvrhm(String plovvrhm) {
        this.plovvrhm = plovvrhm;
    }

    public String getHndvrhm() {
        return hndvrhm;
    }

    public void setHndvrhm(String hndvrhm) {
        this.hndvrhm = hndvrhm;
    }

    public int getLftjvrhm() {
        return lftjvrhm;
    }

    public void setLftjvrhm(int lftjvrhm) {
        this.lftjvrhm = lftjvrhm;
    }

    public String getLevmrhm() {
        return levmrhm;
    }

    public void setLevmrhm(String levmrhm) {
        this.levmrhm = levmrhm;
    }

    public String getToemrhm() {
        return toemrhm;
    }

    public void setToemrhm(String toemrhm) {
        this.toemrhm = toemrhm;
    }

    public String getAnmmrhm() {
        return anmmrhm;
    }

    public void setAnmmrhm(String anmmrhm) {
        this.anmmrhm = anmmrhm;
    }

    public String getTusmrhm() {
        return tusmrhm;
    }

    public void setTusmrhm(String tusmrhm) {
        this.tusmrhm = tusmrhm;
    }

    public String getVrn1mrhm() {
        return vrn1mrhm;
    }

    public void setVrn1mrhm(String vrn1mrhm) {
        this.vrn1mrhm = vrn1mrhm;
    }

    public String getVrn2mrhm() {
        return vrn2mrhm;
    }

    public void setVrn2mrhm(String vrn2mrhm) {
        this.vrn2mrhm = vrn2mrhm;
    }

    public String getVrn3mrhm() {
        return vrn3mrhm;
    }

    public void setVrn3mrhm(String vrn3mrhm) {
        this.vrn3mrhm = vrn3mrhm;
    }

    public String getBrpmrhm() {
        return brpmrhm;
    }

    public void setBrpmrhm(String brpmrhm) {
        this.brpmrhm = brpmrhm;
    }

    public String getAdrmrhm() {
        return adrmrhm;
    }

    public void setAdrmrhm(String adrmrhm) {
        this.adrmrhm = adrmrhm;
    }

    public String getPlovmrhm() {
        return plovmrhm;
    }

    public void setPlovmrhm(String plovmrhm) {
        this.plovmrhm = plovmrhm;
    }

    public String getHndmrhm() {
        return hndmrhm;
    }

    public void setHndmrhm(String hndmrhm) {
        this.hndmrhm = hndmrhm;
    }

    public int getLftjmrhm() {
        return lftjmrhm;
    }

    public void setLftjmrhm(int lftjmrhm) {
        this.lftjmrhm = lftjmrhm;
    }

    public String getLevvrhv() {
        return levvrhv;
    }

    public void setLevvrhv(String levvrhv) {
        this.levvrhv = levvrhv;
    }

    public String getToevrhv() {
        return toevrhv;
    }

    public void setToevrhv(String toevrhv) {
        this.toevrhv = toevrhv;
    }

    public String getAnmvrhv() {
        return anmvrhv;
    }

    public void setAnmvrhv(String anmvrhv) {
        this.anmvrhv = anmvrhv;
    }

    public String getTusvrhv() {
        return tusvrhv;
    }

    public void setTusvrhv(String tusvrhv) {
        this.tusvrhv = tusvrhv;
    }

    public String getVrn1vrhv() {
        return vrn1vrhv;
    }

    public void setVrn1vrhv(String vrn1vrhv) {
        this.vrn1vrhv = vrn1vrhv;
    }

    public String getVrn2vrhv() {
        return vrn2vrhv;
    }

    public void setVrn2vrhv(String vrn2vrhv) {
        this.vrn2vrhv = vrn2vrhv;
    }

    public String getVrn3vrhv() {
        return vrn3vrhv;
    }

    public void setVrn3vrhv(String vrn3vrhv) {
        this.vrn3vrhv = vrn3vrhv;
    }

    public String getBrpvrhv() {
        return brpvrhv;
    }

    public void setBrpvrhv(String brpvrhv) {
        this.brpvrhv = brpvrhv;
    }

    public String getAdrvrhv() {
        return adrvrhv;
    }

    public void setAdrvrhv(String adrvrhv) {
        this.adrvrhv = adrvrhv;
    }

    public String getPlovvrhv() {
        return plovvrhv;
    }

    public void setPlovvrhv(String plovvrhv) {
        this.plovvrhv = plovvrhv;
    }

    public String getHndvrhv() {
        return hndvrhv;
    }

    public void setHndvrhv(String hndvrhv) {
        this.hndvrhv = hndvrhv;
    }

    public int getLftjvrhv() {
        return lftjvrhv;
    }

    public void setLftjvrhv(int lftjvrhv) {
        this.lftjvrhv = lftjvrhv;
    }

    public String getLevmrhv() {
        return levmrhv;
    }

    public void setLevmrhv(String levmrhv) {
        this.levmrhv = levmrhv;
    }

    public String getToemrhv() {
        return toemrhv;
    }

    public void setToemrhv(String toemrhv) {
        this.toemrhv = toemrhv;
    }

    public String getAnmmrhv() {
        return anmmrhv;
    }

    public void setAnmmrhv(String anmmrhv) {
        this.anmmrhv = anmmrhv;
    }

    public String getTusmrhv() {
        return tusmrhv;
    }

    public void setTusmrhv(String tusmrhv) {
        this.tusmrhv = tusmrhv;
    }

    public String getVrn1mrhv() {
        return vrn1mrhv;
    }

    public void setVrn1mrhv(String vrn1mrhv) {
        this.vrn1mrhv = vrn1mrhv;
    }

    public String getVrn2mrhv() {
        return vrn2mrhv;
    }

    public void setVrn2mrhv(String vrn2mrhv) {
        this.vrn2mrhv = vrn2mrhv;
    }

    public String getVrn3mrhv() {
        return vrn3mrhv;
    }

    public void setVrn3mrhv(String vrn3mrhv) {
        this.vrn3mrhv = vrn3mrhv;
    }

    public String getBrpmrhv() {
        return brpmrhv;
    }

    public void setBrpmrhv(String brpmrhv) {
        this.brpmrhv = brpmrhv;
    }

    public String getAdrmrhv() {
        return adrmrhv;
    }

    public void setAdrmrhv(String adrmrhv) {
        this.adrmrhv = adrmrhv;
    }

    public String getPlovmrhv() {
        return plovmrhv;
    }

    public void setPlovmrhv(String plovmrhv) {
        this.plovmrhv = plovmrhv;
    }

    public String getHndmrhv() {
        return hndmrhv;
    }

    public void setHndmrhv(String hndmrhv) {
        this.hndmrhv = hndmrhv;
    }

    public int getLftjmrhv() {
        return lftjmrhv;
    }

    public void setLftjmrhv(int lftjmrhv) {
        this.lftjmrhv = lftjmrhv;
    }

    public String getUgebhuw() {
        return ugebhuw;
    }

    public void setUgebhuw(String ugebhuw) {
        this.ugebhuw = ugebhuw;
    }

    public String getUovloud() {
        return uovloud;
    }

    public void setUovloud(String uovloud) {
        this.uovloud = uovloud;
    }

    public String getUovlech() {
        return uovlech;
    }

    public void setUovlech(String uovlech) {
        this.uovlech = uovlech;
    }

    public String getCertnatm() {
        return certnatm;
    }

    public void setCertnatm(String certnatm) {
        this.certnatm = certnatm;
    }

    public String getToestnot() {
        return toestnot;
    }

    public void setToestnot(String toestnot) {
        this.toestnot = toestnot;
    }

    public String getAktebek() {
        return aktebek;
    }

    public void setAktebek(String aktebek) {
        this.aktebek = aktebek;
    }

    public String getOnvermgn() {
        return onvermgn;
    }

    public void setOnvermgn(String onvermgn) {
        this.onvermgn = onvermgn;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getToestvgd() {
        return toestvgd;
    }

    public void setToestvgd(String toestvgd) {
        this.toestvgd = toestvgd;
    }

    public String getGeghuw() {
        return geghuw;
    }

    public void setGeghuw(String geghuw) {
        this.geghuw = geghuw;
    }

    public String getGegvr() {
        return gegvr;
    }

    public void setGegvr(String gegvr) {
        this.gegvr = gegvr;
    }

    public String getGegmr() {
        return gegmr;
    }

    public void setGegmr(String gegmr) {
        this.gegmr = gegmr;
    }

    public String getProblm() {
        return problm;
    }

    public void setProblm(String problm) {
        this.problm = problm;
    }

    public String getErken() {
        return erken;
    }

    public void setErken(String erken) {
        this.erken = erken;
    }

    public int getNgtg() {
        return ngtg;
    }

    public void setNgtg(int ngtg) {
        this.ngtg = ngtg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
