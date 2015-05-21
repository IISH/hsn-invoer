package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huwgtg",
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {"IDNR", "HDAG", "HMAAND", "HJAAR", "VLGNRGT", "ONDRZKO", "OPDRNRI"})
       },
       indexes = {@Index(columnList = "IDNR, HDAG, HMAAND, HJAAR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Huwgtg extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private Huw huw = new Huw();

    @Column(name = "VLGNRGT", nullable = false) private             int    vlgnrgt;
    @Column(name = "ANMGT", nullable = false, length = 50) private  String anmgt = "";
    @Column(name = "TUSGT", nullable = false, length = 10) private  String tusgt = "";
    @Column(name = "VRN1GT", nullable = false, length = 20) private String vrn1gt = "";
    @Column(name = "VRN2GT", nullable = false, length = 20) private String vrn2gt = "";
    @Column(name = "VRN3GT", nullable = false, length = 30) private String vrn3gt = "";
    @Column(name = "LFTJGT", nullable = false) private              int    lftjgt;
    @Column(name = "BRPGT", nullable = false, length = 50) private  String brpgt = "";
    @Column(name = "ADRGT", nullable = false, length = 50) private  String adrgt = "";
    @Column(name = "HNDGT", nullable = false, length = 1) private   String hndgt = "";
    @Column(name = "RELWIE", nullable = false, length = 1) private  String relwie = "";
    @Column(name = "RELGT", nullable = false, length = 50) private  String relgt = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Huwgtg() {
    }

    public Huwgtg(int vlgnrgt) {
        this.vlgnrgt = vlgnrgt;
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

    public int getVlgnrgt() {
        return vlgnrgt;
    }

    public void setVlgnrgt(int vlgnrgt) {
        this.vlgnrgt = vlgnrgt;
    }

    public String getAnmgt() {
        return anmgt;
    }

    public void setAnmgt(String anmgt) {
        this.anmgt = anmgt;
    }

    public String getTusgt() {
        return tusgt;
    }

    public void setTusgt(String tusgt) {
        this.tusgt = tusgt;
    }

    public String getVrn1gt() {
        return vrn1gt;
    }

    public void setVrn1gt(String vrn1gt) {
        this.vrn1gt = vrn1gt;
    }

    public String getVrn2gt() {
        return vrn2gt;
    }

    public void setVrn2gt(String vrn2gt) {
        this.vrn2gt = vrn2gt;
    }

    public String getVrn3gt() {
        return vrn3gt;
    }

    public void setVrn3gt(String vrn3gt) {
        this.vrn3gt = vrn3gt;
    }

    public int getLftjgt() {
        return lftjgt;
    }

    public void setLftjgt(int lftjgt) {
        this.lftjgt = lftjgt;
    }

    public String getBrpgt() {
        return brpgt;
    }

    public void setBrpgt(String brpgt) {
        this.brpgt = brpgt;
    }

    public String getAdrgt() {
        return adrgt;
    }

    public void setAdrgt(String adrgt) {
        this.adrgt = adrgt;
    }

    public String getHndgt() {
        return hndgt;
    }

    public void setHndgt(String hndgt) {
        this.hndgt = hndgt;
    }

    public String getRelwie() {
        return relwie;
    }

    public void setRelwie(String relwie) {
        this.relwie = relwie;
    }

    public String getRelgt() {
        return relgt;
    }

    public void setRelgt(String relgt) {
        this.relgt = relgt;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
