package org.iish.hsn.invoer.domain.invoer.pk;

import javax.persistence.*;

import org.iish.hsn.invoer.domain.invoer.Invoer;

@Entity
@Table(name = "pkadres",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VGNRADP", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Pkadres extends Invoer {
    @Column(name = "IDNR", nullable = false) private                 int    idnr;         // ID Number
    @Column(name = "VGNRADP", nullable = false) private              int    vgnradp;      // Sequence number address
    @Column(name = "DGADRP", nullable = false) private               int    dgadrp;       // day address
    @Column(name = "MDADRP", nullable = false) private               int    mdadrp;       // month address
    @Column(name = "JRADRP", nullable = false) private               int    jradrp;       // year address
    @Column(name = "VERNUM", nullable = false, length = 1) private   String vernum = "";       // renumbering (vernummering)
    @Column(name = "STRADRP", nullable = false, length = 50) private String stradrp = "";      // address (street)
    @Column(name = "PLADRP", nullable = false, length = 50) private  String pladrp = "";       // place (municipality)
    @Column(name = "LNDADRP", nullable = false, length = 50) private String lndadrp = "";      // land (country)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Pkadres() {
    }

    public Pkadres(int vgnradp) {
        this.vgnradp = vgnradp;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getVgnradp() {
        return vgnradp;
    }

    public void setVgnradp(int vgnradp) {
        this.vgnradp = vgnradp;
    }

    public int getDgadrp() {
        return dgadrp;
    }

    public void setDgadrp(int dgadrp) {
        this.dgadrp = dgadrp;
    }

    public int getMdadrp() {
        return mdadrp;
    }

    public void setMdadrp(int mdadrp) {
        this.mdadrp = mdadrp;
    }

    public int getJradrp() {
        return jradrp;
    }

    public void setJradrp(int jradrp) {
        this.jradrp = jradrp;
    }

    public String getVernum() {
        return vernum;
    }

    public void setVernum(String vernum) {
        this.vernum = vernum;
    }

    public String getStradrp() {
        return stradrp;
    }

    public void setStradrp(String stradrp) {
        this.stradrp = stradrp;
    }

    public String getPladrp() {
        return pladrp;
    }

    public void setPladrp(String pladrp) {
        this.pladrp = pladrp;
    }

    public String getLndadrp() {
        return lndadrp;
    }

    public void setLndadrp(String lndarp) {
        this.lndadrp = lndarp;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}