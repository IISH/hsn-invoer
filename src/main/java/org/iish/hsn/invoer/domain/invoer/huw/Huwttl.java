package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huwttl",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "HDAG", "HMAAND", "HJAAR"})})
public class Huwttl extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;

    @Embedded private Huw huw = new Huw();

    @Column(name = "HGEMNR", nullable = false) private             int    hgemnr;
    @Column(name = "HAKTENR", nullable = false) private            int    haktenr;
    @Column(name = "HPLTS", nullable = false, length = 50) private String hplts = "";
    @Column(name = "HUUR", nullable = false) private               int    huur;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer recordID;

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

    public int getHgemnr() {
        return hgemnr;
    }

    public void setHgemnr(int hgemnr) {
        this.hgemnr = hgemnr;
    }

    public int getHaktenr() {
        return haktenr;
    }

    public void setHaktenr(int haktenr) {
        this.haktenr = haktenr;
    }

    public String getHplts() {
        return hplts;
    }

    public void setHplts(String hplts) {
        this.hplts = hplts;
    }

    public int getHuur() {
        return huur;
    }

    public void setHuur(int huur) {
        this.huur = huur;
    }

    public Integer getId() {
        return recordID;
    }

    public void setId(Integer id) {
        this.recordID = recordID;
    }
}
