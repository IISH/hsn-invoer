package org.iish.hsn.invoer.domain.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "m0", uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VOLG", "TYPE"})})
public class Milition extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private MilitionId militionId = new MilitionId();

    @Column(name = "TYPE", nullable = false, length = 1) private String type = "";
    @Column(name = "GEMNAAM", nullable = false, length = 50) private String municipality;
    @Column(name = "INVNR", nullable = false) private int invNumber;

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

    public MilitionId getMilitionId() {
        return militionId;
    }

    public void setMilitionId(MilitionId militionId) {
        this.militionId = militionId;
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

    public int getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(int invNumber) {
        this.invNumber = invNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
