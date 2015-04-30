package org.iish.hsn.invoer.domain.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.Byz;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ovlbyz", indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Ovlbyz extends Byz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}