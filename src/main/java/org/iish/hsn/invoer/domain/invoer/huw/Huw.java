package org.iish.hsn.invoer.domain.invoer.huw;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Contains all common columns with metadata about a marriage.
 */
@Embeddable
public class Huw implements Serializable {
    @Column(name = "HDAG", nullable = false) private   int hdag;
    @Column(name = "HMAAND", nullable = false) private int hmaand;
    @Column(name = "HJAAR", nullable = false) private  int hjaar;
    // TODO: @Column(name = "HVLGNR", nullable = false) private int hvlgnr;

    public int getHdag() {
        return hdag;
    }

    public void setHdag(int hdag) {
        this.hdag = hdag;
    }

    public int getHmaand() {
        return hmaand;
    }

    public void setHmaand(int hmaand) {
        this.hmaand = hmaand;
    }

    public int getHjaar() {
        return hjaar;
    }

    public void setHjaar(int hjaar) {
        this.hjaar = hjaar;
    }
}
