package org.iish.hsn.invoer.domain.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "plaats", indexes = {@Index(columnList = "GEMNR"), @Index(columnList = "GEMNAAM"),
                                   @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Plaats {
    @Embedded private WorkOrder workOrder;

    @Column(name = "GEMNR", nullable = false) private                int gemnr;
    @Column(name = "PROVNR", nullable = false) private               int provnr;
    @Column(name = "REGNR", nullable = false) private                int regnr;
    @Column(name = "GEMNAAM", nullable = false, length = 50) private String  gemnaam;
    @Column(name = "NWINLST", nullable = false, length = 1) private  String  nwinlst;
    @Column(name = "REGIO", nullable = false, length = 20) private   String  regio;
    @Column(name = "WAGENINGEN", nullable = false) private           int wageningen;
    @Column(name = "EUCLIDMIDX", nullable = false) private           int euclidmidx;
    @Column(name = "EUCLIDMIDY", nullable = false) private           int euclidmidy;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID")
    private Integer id;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public int getGemnr() {
        return gemnr;
    }

    public void setGemnr(int gemnr) {
        this.gemnr = gemnr;
    }

    public int getProvnr() {
        return provnr;
    }

    public void setProvnr(int provnr) {
        this.provnr = provnr;
    }

    public int getRegnr() {
        return regnr;
    }

    public void setRegnr(int regnr) {
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

    public int getWageningen() {
        return wageningen;
    }

    public void setWageningen(int wageningen) {
        this.wageningen = wageningen;
    }

    public int getEuclidmidx() {
        return euclidmidx;
    }

    public void setEuclidmidx(int euclidmidx) {
        this.euclidmidx = euclidmidx;
    }

    public int getEuclidmidy() {
        return euclidmidy;
    }

    public void setEuclidmidy(int euclidmidy) {
        this.euclidmidy = euclidmidy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
