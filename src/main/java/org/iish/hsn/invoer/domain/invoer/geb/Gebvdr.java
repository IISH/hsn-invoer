package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gebvdr",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Gebvdr extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;
    @Column(name = "GEGVR", nullable = false, length = 1) private   String gegvr  = "";
    @Column(name = "ANMVR", nullable = false, length = 50) private  String anmvr  = "";
    @Column(name = "TUSVR", nullable = false, length = 10) private  String tusvr  = "";
    @Column(name = "VRN1VR", nullable = false, length = 20) private String vrn1vr = "";
    @Column(name = "VRN2VR", nullable = false, length = 20) private String vrn2vr = "";
    @Column(name = "VRN3VR", nullable = false, length = 30) private String vrn3vr = "";
    @Column(name = "LFTVR", nullable = false) private int lftvr;
    @Column(name = "BRPVR", nullable = false, length = 254) private String brpvr = "";
    @Column(name = "ADRVR", nullable = false, length = 50) private  String adrvr = "";
    @Column(name = "G5OOSD", nullable = false) private int g5oosd;
    @Column(name = "G5OOSM", nullable = false) private int g5oosm;
    @Column(name = "G5OOSJ", nullable = false) private int g5oosj;
    @Column(name = "G5OOGS", nullable = false, length = 50) private String g5oogs = "";
    @Column(name = "G5VOOD", nullable = false) private int g5vood;
    @Column(name = "G5VOOM", nullable = false) private int g5voom;
    @Column(name = "G5VOOJ", nullable = false) private int g5vooj;
    @Column(name = "G5VOGO", nullable = false, length = 50) private String g5vogo = "";

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

    public String getGegvr() {
        return gegvr;
    }

    public void setGegvr(String gegvr) {
        this.gegvr = gegvr;
    }

    public String getAnmvr() {
        return anmvr;
    }

    public void setAnmvr(String anmvr) {
        this.anmvr = anmvr;
    }

    public String getTusvr() {
        return tusvr;
    }

    public void setTusvr(String tusvr) {
        this.tusvr = tusvr;
    }

    public String getVrn1vr() {
        return vrn1vr;
    }

    public void setVrn1vr(String vrn1vr) {
        this.vrn1vr = vrn1vr;
    }

    public String getVrn2vr() {
        return vrn2vr;
    }

    public void setVrn2vr(String vrn2vr) {
        this.vrn2vr = vrn2vr;
    }

    public String getVrn3vr() {
        return vrn3vr;
    }

    public void setVrn3vr(String vrn3vr) {
        this.vrn3vr = vrn3vr;
    }

    public int getLftvr() {
        return lftvr;
    }

    public void setLftvr(int lftvr) {
        this.lftvr = lftvr;
    }

    public String getBrpvr() {
        return brpvr;
    }

    public void setBrpvr(String brpvr) {
        this.brpvr = brpvr;
    }

    public String getAdrvr() {
        return adrvr;
    }

    public void setAdrvr(String adrvr) {
        this.adrvr = adrvr;
    }

    public int getG5oosd() {
        return g5oosd;
    }

    public void setG5oosd(int g5oosd) {
        this.g5oosd = g5oosd;
    }

    public int getG5oosm() {
        return g5oosm;
    }

    public void setG5oosm(int g5oosm) {
        this.g5oosm = g5oosm;
    }

    public int getG5oosj() {
        return g5oosj;
    }

    public void setG5oosj(int g5oosj) {
        this.g5oosj = g5oosj;
    }

    public String getG5oogs() {
        return g5oogs;
    }

    public void setG5oogs(String g5oogs) {
        this.g5oogs = g5oogs;
    }

    public int getG5vood() {
        return g5vood;
    }

    public void setG5vood(int g5vood) {
        this.g5vood = g5vood;
    }

    public int getG5voom() {
        return g5voom;
    }

    public void setG5voom(int g5voom) {
        this.g5voom = g5voom;
    }

    public int getG5vooj() {
        return g5vooj;
    }

    public void setG5vooj(int g5vooj) {
        this.g5vooj = g5vooj;
    }

    public String getG5vogo() {
        return g5vogo;
    }

    public void setG5vogo(String g5vogo) {
        this.g5vogo = g5vogo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
