package org.iish.hsn.invoer.domain.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;

@Entity
@Table(name = "p8",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "P8TPNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class P8 extends Invoer {
    @Column(name = "IDNR", nullable = false) private                int    idnr;         // ID Number
    @Column(name = "IDNRP", nullable = false) private               int    idnrp;        // IDNR partner
    @Column(name = "P8TPNR", nullable = false) private              int    p8tpnr;       // sequence number
    @Column(name = "DGADRP", nullable = false) private              int    dgadrp;       // day address
    @Column(name = "MDADRP", nullable = false) private              int    mdadrp;       // month address
    @Column(name = "JRADRP", nullable = false) private              int    jradrp;       // year address
    @Column(name = "PLADRP", nullable = false, length = 50) private String pladrp = "";       // place (municipality)
    @Column(name = "P8OPPD", nullable = false) private              int    p8oppd;       // day of first address
    @Column(name = "P8OPPM", nullable = false) private              int    p8oppm;       // month of first address
    @Column(name = "P8OPPJ", nullable = false) private              int    p8oppj;       // year of first address
    @Column(name = "P8OPWF", nullable = false) private              String p8opwf = "";       // type of address
    @Column(name = "P8OPWL", nullable = false) private              String p8opwl = "";       // Name of locality
    @Column(name = "P8OPWS", nullable = false) private              String p8opws = "";       // Name of the street
    @Column(name = "P8OPWH", nullable = false) private              String p8opwh = "";       // House number
    @Column(name = "P8OPWR", nullable = false) private              String p8opwr = "";       // Addition character(s) to house number
    @Column(name = "P8OPWT", nullable = false) private              String p8opwt = "";       // Additional number to house number
    @Column(name = "P8OPWP", nullable = false) private              String p8opwp = "";       // Postal code
    @Column(name = "P8OPWB", nullable = false) private              String p8opwb = "";       // Description of location
    @Column(name = "P8OPIL", nullable = false, length = 50) private String p8opil = "";       // Country from which PL-holder originates from
    @Column(name = "P8OPIJ", nullable = false) private              int    p8opij;       // Year of registration of arrival from foreign country
    @Column(name = "P8OPIM", nullable = false) private              int    p8opim;       // Month of registration of arrival from foreign country
    @Column(name = "P8OPID", nullable = false) private              int    p8opid;       // Day of registration of arrival from foreign country
    @Column(name = "P8OPAG", nullable = false) private              String p8opag = "";       // Data under research
    @Column(name = "P8OPZJ", nullable = false) private              int    p8opzj;       // Year of 'data under research'
    @Column(name = "P8OPZM", nullable = false) private              int    p8opzm;       // Month of 'data under research'
    @Column(name = "P8OPZD", nullable = false) private              int    p8opzd;       // Day of 'data under research'
    @Column(name = "P8OPIO", nullable = false) private              String p8opio = "";       // 'indicatie onjuist'; not clear what this variable means (only sporadically content)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public P8() {
    }

    public P8(int p8tpnr) {
        this.p8tpnr = p8tpnr;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getIdnrp() {
        return idnrp;
    }

    public void setIdnrp(int idnrp) {
        this.idnrp = idnrp;
    }

    public int getP8tpnr() {
        return p8tpnr;
    }

    public void setP8tpnr(int p8tpnr) {
        this.p8tpnr = p8tpnr;
    }

    public int getDgadrp() {
        return dgadrp;
    }

    public void setDgadrp(int dgadrp) {
        this.dgadrp = dgadrp;
    }

    public int getMdadrp() {
        return mdadrp;
    }

    public void setMdadrp(int mdadrp) {
        this.mdadrp = mdadrp;
    }

    public int getJradrp() {
        return jradrp;
    }

    public void setJradrp(int jradrp) {
        this.jradrp = jradrp;
    }

    public String getPladrp() {
        return pladrp;
    }

    public void setPladrp(String pladrp) {
        this.pladrp = pladrp;
    }

    public int getP8oppd() {
        return p8oppd;
    }

    public void setP8oppd(int p8oppd) {
        this.p8oppd = p8oppd;
    }

    public int getP8oppm() {
        return p8oppm;
    }

    public void setP8oppm(int p8oppm) {
        this.p8oppm = p8oppm;
    }

    public int getP8oppj() {
        return p8oppj;
    }

    public void setP8oppj(int p8oppj) {
        this.p8oppj = p8oppj;
    }

    public String getP8opwf() {
        return p8opwf;
    }

    public void setP8opwf(String p8opwf) {
        this.p8opwf = p8opwf;
    }

    public String getP8opwl() {
        return p8opwl;
    }

    public void setP8opwl(String p8opwl) {
        this.p8opwl = p8opwl;
    }

    public String getP8opws() {
        return p8opws;
    }

    public void setP8opws(String p8opws) {
        this.p8opws = p8opws;
    }

    public String getP8opwh() {
        return p8opwh;
    }

    public void setP8opwh(String p8opwh) {
        this.p8opwh = p8opwh;
    }

    public String getP8opwr() {
        return p8opwr;
    }

    public void setP8opwr(String p8opwr) {
        this.p8opwr = p8opwr;
    }

    public String getP8opwt() {
        return p8opwt;
    }

    public void setP8opwt(String p8opwt) {
        this.p8opwt = p8opwt;
    }

    public String getP8opwp() {
        return p8opwp;
    }

    public void setP8opwp(String p8opwp) {
        this.p8opwp = p8opwp;
    }

    public String getP8opwb() {
        return p8opwb;
    }

    public void setP8opwb(String p8opwb) {
        this.p8opwb = p8opwb;
    }

    public String getP8opil() {
        return p8opil;
    }

    public void setP8opil(String p8opil) {
        this.p8opil = p8opil;
    }

    public int getP8opij() {
        return p8opij;
    }

    public void setP8opij(int p8opij) {
        this.p8opij = p8opij;
    }

    public int getP8opim() {
        return p8opim;
    }

    public void setP8opim(int p8opim) {
        this.p8opim = p8opim;
    }

    public int getP8opid() {
        return p8opid;
    }

    public void setP8opid(int p8opid) {
        this.p8opid = p8opid;
    }

    public String getP8opag() {
        return p8opag;
    }

    public void setP8opag(String p8opag) {
        this.p8opag = p8opag;
    }

    public int getP8opzj() {
        return p8opzj;
    }

    public void setP8opzj(int p8opzj) {
        this.p8opzj = p8opzj;
    }

    public int getP8opzm() {
        return p8opzm;
    }

    public void setP8opzm(int p8opzm) {
        this.p8opzm = p8opzm;
    }

    public int getP8opzd() {
        return p8opzd;
    }

    public void setP8opzd(int p8opzd) {
        this.p8opzd = p8opzd;
    }

    public String getP8opio() {
        return p8opio;
    }

    public void setP8opio(String p8opio) {
        this.p8opio = p8opio;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
