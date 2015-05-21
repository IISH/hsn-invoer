package org.iish.hsn.invoer.domain.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "adrestp", indexes = {@Index(columnList = "CODE"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Adrestp {
    @Embedded private WorkOrder workOrder;

    @Column(name = "CODE", nullable = false, length = 2) private  String code = "";
    @Column(name = "TYPE", nullable = false, length = 40) private String type = "";

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
