package org.iish.hsn.invoer.domain.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "m0", uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VOLG"})})
public class Milition extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;
    @Column(name = "VOLG", nullable = false) private int seq = 1;

    @Column(name = "JAAR", nullable = false) private int year;
    @Column(name = "TYPE", nullable = false, length = 1) private String type = "";
    @Column(name = "GEMNAAM", nullable = false, length = 50) private String municipality = "";
    @Column(name = "INVNR", nullable = false, length = 10) private String invNumber = "";

    @Column(name = "SCANA", nullable = false, length = 255) private String scanA = "";
    @Column(name = "SCANB", nullable = false, length = 255) private String scanB = "";

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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(String invNumber) {
        this.invNumber = invNumber;
    }

    public String getScanA() {
        return scanA;
    }

    public void setScanA(String scanA) {
        this.scanA = scanA;
    }

    public String getScanB() {
        return scanB;
    }

    public void setScanB(String scanB) {
        this.scanB = scanB;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
