package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gebgtg",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI", "VLGNRGT"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Gebgtg extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private                int    idnr;
    @Column(name = "VLGNRGT", nullable = false) private             int    vlgnrgt;
    @Column(name = "ANMGT", nullable = false, length = 50) private  String anmgt = "";
    @Column(name = "TUSGT", nullable = false, length = 10) private  String tusgt = "";
    @Column(name = "VRN1GT", nullable = false, length = 20) private String vrn1gt = "";
    @Column(name = "VRN2GT", nullable = false, length = 20) private String vrn2gt = "";
    @Column(name = "VRN3GT", nullable = false, length = 30) private String vrn3gt = "";
    @Column(name = "LFTGT", nullable = false) private               int    lftgt;
    @Column(name = "BRPGT", nullable = false, length = 50) private  String brpgt = "";
    @Column(name = "ADRGT", nullable = false, length = 50) private  String adrgt = "";
    @Column(name = "HNDGT", nullable = false, length = 1) private   String hndgt = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Gebgtg() {
    }

    public Gebgtg(int vlgnrgt) {
        this.vlgnrgt = vlgnrgt;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
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

    public int getLftgt() {
        return lftgt;
    }

    public void setLftgt(int lftgt) {
        this.lftgt = lftgt;
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
}
