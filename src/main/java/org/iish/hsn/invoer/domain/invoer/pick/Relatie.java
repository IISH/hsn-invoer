package org.iish.hsn.invoer.domain.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "relatie", indexes = {@Index(columnList = "RELKODE"), @Index(columnList = "RELATIE"),
                                    @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Relatie {
    @Embedded private WorkOrder workOrder;

    @Column(name = "RELKODE", nullable = false) private              Integer relkode;
    @Column(name = "RELATIE", nullable = false, length = 50) private String  relatie;
    @Column(name = "NWINLST", nullable = false, length = 1) private  String  nwinlst;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "RecordID")
    private Integer recordID;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Integer getRelkode() {
        return relkode;
    }

    public void setRelkode(Integer relkode) {
        this.relkode = relkode;
    }

    public String getRelatie() {
        return relatie;
    }

    public void setRelatie(String relatie) {
        this.relatie = relatie;
    }

    public String getNwinlst() {
        return nwinlst;
    }

    public void setNwinlst(String nwinlst) {
        this.nwinlst = nwinlst;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
