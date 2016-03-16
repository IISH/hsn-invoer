package org.iish.hsn.invoer.domain.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "kg", indexes = {@Index(columnList = "KERKGENO"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Kg {
    @Embedded private WorkOrder workOrder;

    @Column(name = "KODE", nullable = false, length = 2) private       String kode     = "";
    @Column(name = "KERKGENO", nullable = false, length = 50) private  String kerkgeno = "";
    @Column(name = "AFGEKORT", nullable = false, length = 20) private  String afgekort = "";
    @Column(name = "OMSCHRIJ", nullable = false, length = 100) private String omschrij = "";
    @Column(name = "NWINLST", nullable = false, length = 1) private    String nwinlst  = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKerkgeno() {
        return kerkgeno;
    }

    public void setKerkgeno(String kerkgeno) {
        this.kerkgeno = kerkgeno;
    }

    public String getAfgekort() {
        return afgekort;
    }

    public void setAfgekort(String afgekort) {
        this.afgekort = afgekort;
    }

    public String getOmschrij() {
        return omschrij;
    }

    public void setOmschrij(String omschrij) {
        this.omschrij = omschrij;
    }

    public String getNwinlst() {
        return nwinlst;
    }

    public void setNwinlst(String nwinlst) {
        this.nwinlst = nwinlst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
