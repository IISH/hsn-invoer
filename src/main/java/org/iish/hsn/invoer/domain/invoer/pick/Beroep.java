package org.iish.hsn.invoer.domain.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "beroep", indexes = {@Index(columnList = "BERPNAAM"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Beroep {
    @Embedded private WorkOrder workOrder;

    @Column(name = "BERPNAAM", nullable = false, length = 50) private String berpnaam = "";
    @Column(name = "NWINLST", nullable = false, length = 1) private   String nwinlst = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public String getBerpnaam() {
        return berpnaam;
    }

    public void setBerpnaam(String berpnaam) {
        this.berpnaam = berpnaam;
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
