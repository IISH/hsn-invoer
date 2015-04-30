package org.iish.hsn.invoer.domain.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.Byz;

import javax.persistence.*;

@Entity
@Table(name = "pkbyz")
public class Pkbyz extends Byz {
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
