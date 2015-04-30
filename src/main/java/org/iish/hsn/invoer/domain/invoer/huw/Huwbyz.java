package org.iish.hsn.invoer.domain.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.Byz;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "huwbyz", indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Huwbyz extends Byz implements Serializable {
    @Embedded private Huw huw = new Huw();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Huw getHuw() {
        return huw;
    }

    public void setHuw(Huw huw) {
        this.huw = huw;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
