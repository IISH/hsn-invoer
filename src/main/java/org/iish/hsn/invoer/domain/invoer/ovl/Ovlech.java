package org.iish.hsn.invoer.domain.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ovlech",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VLGECH", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Ovlech extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private   int idnr;
    @Column(name = "VLGECH", nullable = false) private int vlgech;
    @Column(name = "ANMEOVL", nullable = false, length = 50) private  String anmeovl  = "";
    @Column(name = "TUSEOVL", nullable = false, length = 10) private  String tuseovl  = "";
    @Column(name = "VRN1EOVL", nullable = false, length = 20) private String vrn1eovl = "";
    @Column(name = "VRN2EOVL", nullable = false, length = 20) private String vrn2eovl = "";
    @Column(name = "VRN3EOVL", nullable = false, length = 30) private String vrn3eovl = "";
    @Column(name = "LEVEOVL", nullable = false, length = 1) private   String leveovl  = "";
    @Column(name = "LFTEOVL", nullable = false) private int lfteovl;
    @Column(name = "BRPEOVL", nullable = false, length = 254) private String brpeovl = "";
    @Column(name = "ADREOVL", nullable = false, length = 50) private  String adreovl = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Ovlech() {
    }

    public Ovlech(int vlgech) {
        this.vlgech = vlgech;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getVlgech() {
        return vlgech;
    }

    public void setVlgech(int vlgech) {
        this.vlgech = vlgech;
    }

    public String getAnmeovl() {
        return anmeovl;
    }

    public void setAnmeovl(String anmeovl) {
        this.anmeovl = anmeovl;
    }

    public String getTuseovl() {
        return tuseovl;
    }

    public void setTuseovl(String tuseovl) {
        this.tuseovl = tuseovl;
    }

    public String getVrn1eovl() {
        return vrn1eovl;
    }

    public void setVrn1eovl(String vrn1eovl) {
        this.vrn1eovl = vrn1eovl;
    }

    public String getVrn2eovl() {
        return vrn2eovl;
    }

    public void setVrn2eovl(String vrn2eovl) {
        this.vrn2eovl = vrn2eovl;
    }

    public String getVrn3eovl() {
        return vrn3eovl;
    }

    public void setVrn3eovl(String vrn3eovl) {
        this.vrn3eovl = vrn3eovl;
    }

    public String getLeveovl() {
        return leveovl;
    }

    public void setLeveovl(String leveovl) {
        this.leveovl = leveovl;
    }

    public int getLfteovl() {
        return lfteovl;
    }

    public void setLfteovl(int lfteovl) {
        this.lfteovl = lfteovl;
    }

    public String getBrpeovl() {
        return brpeovl;
    }

    public void setBrpeovl(String brpeovl) {
        this.brpeovl = brpeovl;
    }

    public String getAdreovl() {
        return adreovl;
    }

    public void setAdreovl(String adreovl) {
        this.adreovl = adreovl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}