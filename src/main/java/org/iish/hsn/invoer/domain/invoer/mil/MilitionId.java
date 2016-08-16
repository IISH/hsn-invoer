package org.iish.hsn.invoer.domain.invoer.mil;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MilitionId implements Serializable {
    @Column(name = "JAAR", nullable = false) private int year;
    @Column(name = "VOLG", nullable = false) private int seq = 1;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return year + " (" + seq + ")";
    }
}
