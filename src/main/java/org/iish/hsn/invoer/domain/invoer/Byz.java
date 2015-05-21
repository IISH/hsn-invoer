package org.iish.hsn.invoer.domain.invoer;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Contains the "bijzonderheden", in other words:
 * Information described on the document that does not fit in any of the predefined fields.
 */
@MappedSuperclass
public abstract class Byz extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private             int    idnr;
    @Column(name = "BYZNR", nullable = false) private            int    byznr;
    @Column(name = "BYZ", nullable = false, length = 55) private String byz = "";
    @Column(name = "SCHERM", nullable = false) private           String scherm = "";

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getByznr() {
        return byznr;
    }

    public void setByznr(int byznr) {
        this.byznr = byznr;
    }

    public String getByz() {
        return byz;
    }

    public void setByz(String byz) {
        this.byz = byz;
    }

    public String getScherm() {
        return scherm;
    }

    public void setScherm(String scherm) {
        this.scherm = scherm;
    }
}
