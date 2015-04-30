package org.iish.hsn.invoer.domain.invoer;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Contains all common columns with metadata about the 'invoer'.
 */
@MappedSuperclass
public abstract class Invoer implements Serializable {
    @Embedded private WorkOrder workOrder = new WorkOrder();

    @Column(name = "ARCH", nullable = false) private    String arch; // TODO: Column not found in PkKnd or B* (Rijksarchief / gemeentearchief)
    @Column(name = "OPDRNR", nullable = false) private  String opdrnr;
    @Column(name = "DATUM", nullable = false) private   String datum;
    @Column(name = "INIT", nullable = false) private    String init;
    @Column(name = "VERSIE", nullable = false) private  String versie;
    @Column(name = "ARCHO", nullable = false) private   String archo; // TODO: Column not found in PkKnd or B* (Rijksarchief / gemeentearchief)
    @Column(name = "OPDRNRO", nullable = false) private String opdrnro;
    @Column(name = "DATUMO", nullable = false) private  String datumo;
    @Column(name = "INITO", nullable = false) private   String inito;
    @Column(name = "VERSIEO", nullable = false) private String versieo;

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getOpdrnr() {
        return opdrnr;
    }

    public void setOpdrnr(String opdrnr) {
        this.opdrnr = opdrnr;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getVersie() {
        return versie;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public String getArcho() {
        return archo;
    }

    public void setArcho(String archo) {
        this.archo = archo;
    }

    public String getOpdrnro() {
        return opdrnro;
    }

    public void setOpdrnro(String opdrnro) {
        this.opdrnro = opdrnro;
    }

    public String getDatumo() {
        return datumo;
    }

    public void setDatumo(String datumo) {
        this.datumo = datumo;
    }

    public String getInito() {
        return inito;
    }

    public void setInito(String inito) {
        this.inito = inito;
    }

    public String getVersieo() {
        return versieo;
    }

    public void setVersieo(String versieo) {
        this.versieo = versieo;
    }
}
