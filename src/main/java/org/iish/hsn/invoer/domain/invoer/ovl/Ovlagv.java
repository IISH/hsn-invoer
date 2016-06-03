package org.iish.hsn.invoer.domain.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ovlagv",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VLGNRAG", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Ovlagv extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private    int idnr;
    @Column(name = "VLGNRAG", nullable = false) private int vlgnrag;
    @Column(name = "ANMAGV", nullable = false, length = 50) private  String anmagv  = "";
    @Column(name = "TUSAGV", nullable = false, length = 10) private  String tusagv  = "";
    @Column(name = "VRN1AGV", nullable = false, length = 20) private String vrn1agv = "";
    @Column(name = "VRN2AGV", nullable = false, length = 20) private String vrn2agv = "";
    @Column(name = "VRN3AGV", nullable = false, length = 30) private String vrn3agv = "";
    @Column(name = "RAGVOVL", nullable = false, length = 50) private String ragvovl = "";
    @Column(name = "LFTAGV", nullable = false) private int lftagv;
    @Column(name = "BRPAGV", nullable = false, length = 254) private String brpagv = "";
    @Column(name = "ADRAGV", nullable = false, length = 50) private  String adragv = "";
    @Column(name = "HNDAGV", nullable = false, length = 1) private   String hndagv = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Ovlagv() {
    }

    public Ovlagv(int vlgnrag) {
        this.vlgnrag = vlgnrag;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getVlgnrag() {
        return vlgnrag;
    }

    public void setVlgnrag(int vlgnrag) {
        this.vlgnrag = vlgnrag;
    }

    public String getAnmagv() {
        return anmagv;
    }

    public void setAnmagv(String anmagv) {
        this.anmagv = anmagv;
    }

    public String getTusagv() {
        return tusagv;
    }

    public void setTusagv(String tusagv) {
        this.tusagv = tusagv;
    }

    public String getVrn1agv() {
        return vrn1agv;
    }

    public void setVrn1agv(String vrn1agv) {
        this.vrn1agv = vrn1agv;
    }

    public String getVrn2agv() {
        return vrn2agv;
    }

    public void setVrn2agv(String vrn2agv) {
        this.vrn2agv = vrn2agv;
    }

    public String getVrn3agv() {
        return vrn3agv;
    }

    public void setVrn3agv(String vrn3agv) {
        this.vrn3agv = vrn3agv;
    }

    public String getRagvovl() {
        return ragvovl;
    }

    public void setRagvovl(String ragvovl) {
        this.ragvovl = ragvovl;
    }

    public int getLftagv() {
        return lftagv;
    }

    public void setLftagv(int lftagv) {
        this.lftagv = lftagv;
    }

    public String getBrpagv() {
        return brpagv;
    }

    public void setBrpagv(String brpagv) {
        this.brpagv = brpagv;
    }

    public String getAdragv() {
        return adragv;
    }

    public void setAdragv(String adragv) {
        this.adragv = adragv;
    }

    public String getHndagv() {
        return hndagv;
    }

    public void setHndagv(String hndagv) {
        this.hndagv = hndagv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
