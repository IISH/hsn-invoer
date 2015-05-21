package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huweer",
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {"IDNR", "HDAG", "HMAAND", "HJAAR", "VLGNREH", "ONDRZKO", "OPDRNRI"})
       },
       indexes = {@Index(columnList = "IDNR, HDAG, HMAAND, HJAAR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Huweer extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private Huw huw = new Huw();

    @Column(name = "VLGNREH", nullable = false) private             int    vlgnreh;
    @Column(name = "HUWER", nullable = false, length = 1) private   String huwer = "";
    @Column(name = "ANMEH", nullable = false, length = 50) private  String anmeh = "";
    @Column(name = "TUSEH", nullable = false, length = 10) private  String tuseh = "";
    @Column(name = "VRN1EH", nullable = false, length = 20) private String vrn1eh = "";
    @Column(name = "VRN2EH", nullable = false, length = 20) private String vrn2eh = "";
    @Column(name = "VRN3EH", nullable = false, length = 30) private String vrn3eh = "";
    @Column(name = "EINDEH", nullable = false, length = 1) private  String eindeh = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Huweer() {
    }

    public Huweer(int vlgnreh, String huwer) {
        this.vlgnreh = vlgnreh;
        this.huwer = huwer;
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

    public int getVlgnreh() {
        return vlgnreh;
    }

    public void setVlgnreh(int vlgnreh) {
        this.vlgnreh = vlgnreh;
    }

    public String getHuwer() {
        return huwer;
    }

    public void setHuwer(String huwer) {
        this.huwer = huwer;
    }

    public String getAnmeh() {
        return anmeh;
    }

    public void setAnmeh(String anmeh) {
        this.anmeh = anmeh;
    }

    public String getTuseh() {
        return tuseh;
    }

    public void setTuseh(String tuseh) {
        this.tuseh = tuseh;
    }

    public String getVrn1eh() {
        return vrn1eh;
    }

    public void setVrn1eh(String vrn1eh) {
        this.vrn1eh = vrn1eh;
    }

    public String getVrn2eh() {
        return vrn2eh;
    }

    public void setVrn2eh(String vrn2eh) {
        this.vrn2eh = vrn2eh;
    }

    public String getVrn3eh() {
        return vrn3eh;
    }

    public void setVrn3eh(String vrn3eh) {
        this.vrn3eh = vrn3eh;
    }

    public String getEindeh() {
        return eindeh;
    }

    public void setEindeh(String eindeh) {
        this.eindeh = eindeh;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
