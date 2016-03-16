package org.iish.hsn.invoer.domain.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "plaats", indexes = {@Index(columnList = "GEMNR"), @Index(columnList = "GEMNAAM"),
                                   @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Plaats {
    @Embedded private WorkOrder workOrder;

    @Column(name = "GEMNR", nullable = false) private                Integer gemnr;
    @Column(name = "PROVNR", nullable = false) private               Integer provnr;
    @Column(name = "REGNR", nullable = false) private                Integer regnr;
    @Column(name = "GEMNAAM", nullable = false, length = 50) private String  gemnaam;
    @Column(name = "NWINLST", nullable = false, length = 1) private  String  nwinlst;
    @Column(name = "REGIO", nullable = false, length = 20) private   String  regio;
    @Column(name = "WAGENINGEN", nullable = false) private           Integer wageningen;
    @Column(name = "EUCLIDMIDX", nullable = false) private           Integer euclidmidx;
    @Column(name = "EUCLIDMIDY", nullable = false) private           Integer euclidmidy;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID")
    private Integer id;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Integer getGemnr() {
        return gemnr;
    }

    public void setGemnr(Integer gemnr) {
        this.gemnr = gemnr;
    }

    public Integer getProvnr() {
        return provnr;
    }

    public void setProvnr(Integer provnr) {
        this.provnr = provnr;
    }

    public Integer getRegnr() {
        return regnr;
    }

    public void setRegnr(Integer regnr) {
        this.regnr = regnr;
    }

    public String getGemnaam() {
        return gemnaam;
    }

    public void setGemnaam(String gemnaam) {
        this.gemnaam = gemnaam;
    }

    public String getNwinlst() {
        return nwinlst;
    }

    public void setNwinlst(String nwinlst) {
        this.nwinlst = nwinlst;
    }

    public String getRegio() {
        return regio;
    }

    public void setRegio(String regio) {
        this.regio = regio;
    }

    public Integer getWageningen() {
        return wageningen;
    }

    public void setWageningen(Integer wageningen) {
        this.wageningen = wageningen;
    }

    public Integer getEuclidmidx() {
        return euclidmidx;
    }

    public void setEuclidmidx(Integer euclidmidx) {
        this.euclidmidx = euclidmidx;
    }

    public Integer getEuclidmidy() {
        return euclidmidy;
    }

    public void setEuclidmidy(Integer euclidmidy) {
        this.euclidmidy = euclidmidy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
