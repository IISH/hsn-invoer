package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huwvrknd",
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {"IDNR", "HDAG", "HMAAND", "HJAAR", "VLGNRVK", "ONDRZKO", "OPDRNRI"})
       },
       indexes = {@Index(columnList = "IDNR, HDAG, HMAAND, HJAAR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Huwvrknd extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private Huw huw = new Huw();

    @Column(name = "VLGNRVK", nullable = false) private              int    vlgnrvk;
    @Column(name = "ANMVK", nullable = false, length = 50) private   String anmvk;
    @Column(name = "TUSVK", nullable = false, length = 10) private   String tusvk;
    @Column(name = "VRN1VK", nullable = false, length = 20) private  String vrn1vk;
    @Column(name = "VRN2VK", nullable = false, length = 20) private  String vrn2vk;
    @Column(name = "VRN3VK", nullable = false, length = 30) private  String vrn3vk;
    @Column(name = "GBDGVK", nullable = false) private               int    gbdgvk;
    @Column(name = "GBMDVK", nullable = false) private               int    gbmdvk;
    @Column(name = "GBJRVK", nullable = false) private               int    gbjrvk;
    @Column(name = "GESLVK", nullable = false, length = 1) private   String geslvk;
    @Column(name = "GBPLVK", nullable = false, length = 50) private  String gbplvk;
    @Column(name = "ERVK", nullable = false, length = 1) private     String ervk;
    @Column(name = "ERVKWIE", nullable = false, length = 1) private  String ervkwie;
    @Column(name = "MEKDGVK", nullable = false) private              int    mekdgvk;
    @Column(name = "MEKMDVK", nullable = false) private              int    mekmdvk;
    @Column(name = "MEKJRVK", nullable = false) private              int    mekjrvk;
    @Column(name = "MEKPLVK", nullable = false, length = 50) private String mekplvk;
    @Column(name = "VEKDGVK", nullable = false) private              int    vekdgvk;
    @Column(name = "VEKMDVK", nullable = false) private              int    vekmdvk;
    @Column(name = "VEKJRVK", nullable = false) private              int    vekjrvk;
    @Column(name = "VEKPLVK", nullable = false, length = 50) private String vekplvk;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Huwvrknd() {
    }

    public Huwvrknd(int vlgnrvk) {
        this.vlgnrvk = vlgnrvk;
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

    public int getVlgnrvk() {
        return vlgnrvk;
    }

    public void setVlgnrvk(int vlgnrvk) {
        this.vlgnrvk = vlgnrvk;
    }

    public String getAnmvk() {
        return anmvk;
    }

    public void setAnmvk(String anmvk) {
        this.anmvk = anmvk;
    }

    public String getTusvk() {
        return tusvk;
    }

    public void setTusvk(String tusvk) {
        this.tusvk = tusvk;
    }

    public String getVrn1vk() {
        return vrn1vk;
    }

    public void setVrn1vk(String vrn1vk) {
        this.vrn1vk = vrn1vk;
    }

    public String getVrn2vk() {
        return vrn2vk;
    }

    public void setVrn2vk(String vrn2vk) {
        this.vrn2vk = vrn2vk;
    }

    public String getVrn3vk() {
        return vrn3vk;
    }

    public void setVrn3vk(String vrn3vk) {
        this.vrn3vk = vrn3vk;
    }

    public int getGbdgvk() {
        return gbdgvk;
    }

    public void setGbdgvk(int gbdgvk) {
        this.gbdgvk = gbdgvk;
    }

    public int getGbmdvk() {
        return gbmdvk;
    }

    public void setGbmdvk(int gbmdvk) {
        this.gbmdvk = gbmdvk;
    }

    public int getGbjrvk() {
        return gbjrvk;
    }

    public void setGbjrvk(int gbjrvk) {
        this.gbjrvk = gbjrvk;
    }

    public String getGeslvk() {
        return geslvk;
    }

    public void setGeslvk(String geslvk) {
        this.geslvk = geslvk;
    }

    public String getGbplvk() {
        return gbplvk;
    }

    public void setGbplvk(String gbplvk) {
        this.gbplvk = gbplvk;
    }

    public String getErvk() {
        return ervk;
    }

    public void setErvk(String ervk) {
        this.ervk = ervk;
    }

    public String getErvkwie() {
        return ervkwie;
    }

    public void setErvkwie(String ervkwie) {
        this.ervkwie = ervkwie;
    }

    public int getMekdgvk() {
        return mekdgvk;
    }

    public void setMekdgvk(int mekdgvk) {
        this.mekdgvk = mekdgvk;
    }

    public int getMekmdvk() {
        return mekmdvk;
    }

    public void setMekmdvk(int mekmdvk) {
        this.mekmdvk = mekmdvk;
    }

    public int getMekjrvk() {
        return mekjrvk;
    }

    public void setMekjrvk(int mekjrvk) {
        this.mekjrvk = mekjrvk;
    }

    public String getMekplvk() {
        return mekplvk;
    }

    public void setMekplvk(String mekplvk) {
        this.mekplvk = mekplvk;
    }

    public int getVekdgvk() {
        return vekdgvk;
    }

    public void setVekdgvk(int vekdgvk) {
        this.vekdgvk = vekdgvk;
    }

    public int getVekmdvk() {
        return vekmdvk;
    }

    public void setVekmdvk(int vekmdvk) {
        this.vekmdvk = vekmdvk;
    }

    public int getVekjrvk() {
        return vekjrvk;
    }

    public void setVekjrvk(int vekjrvk) {
        this.vekjrvk = vekjrvk;
    }

    public String getVekplvk() {
        return vekplvk;
    }

    public void setVekplvk(String vekplvk) {
        this.vekplvk = vekplvk;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
