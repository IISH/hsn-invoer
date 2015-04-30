package org.iish.hsn.invoer.domain.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;

@Entity
@Table(name = "pkeigknd",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VGNRKDP", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Pkeigknd extends Invoer {
    @Column(name = "IDNR", nullable = false) private                 int    idnr;       // ID Number
    @Column(name = "VGNRKDP", nullable = false) private              int    vgnrkdp;    // sequence number child
    @Column(name = "ANMKNDP", nullable = false, length = 50) private String anmkndp;    // last name child
    @Column(name = "TUSKNDP", nullable = false, length = 10) private String tuskndp;    // prefix child
    @Column(name = "VN1KNDP", nullable = false, length = 20) private String vn1kndp;    // 1st firstname child
    @Column(name = "VN2KNDP", nullable = false, length = 20) private String vn2kndp;    // 2nd firstname child
    @Column(name = "VN3KNDP", nullable = false, length = 30) private String vn3kndp;    // 3rd firstname child
    @Column(name = "GDGKNDP", nullable = false) private              int    gdgkndp;    // day of birth child
    @Column(name = "GMDKNDP", nullable = false) private              int    gmdkndp;    // month of birth child
    @Column(name = "GJRKNDP", nullable = false) private              int    gjrkndp;    // year of birth child
    @Column(name = "GDGKNDPCR", nullable = false) private            int    gdgkndpcr;  // day of birth child after correction
    @Column(name = "GMDKNDPCR", nullable = false) private            int    gmdkndpcr;  // month of birth child after correction
    @Column(name = "GJRKNDPCR", nullable = false) private            int    gjrkndpcr;  // year of birth child after correction
    @Column(name = "GPLKNDP", nullable = false, length = 50) private String gplkndp;    // birth place child
    @Column(name = "GLNKNDP", nullable = false) private              String glnkndp;    // birth country child
    @Column(name = "RELKNDP", nullable = false, length = 50) private String relkndp;    // relationship to PK-Holder (son, daughter etc)
    @Column(name = "HDGKNDP", nullable = false) private              int    hdgkndp;    // day of marriage child
    @Column(name = "HMDKNDP", nullable = false) private              int    hmdkndp;    // month of marriage child
    @Column(name = "HJRKNDP", nullable = false) private              int    hjrkndp;    // year of marriage child
    @Column(name = "HPLKNDP", nullable = false, length = 50) private String hplkndp;    // place of marriage
    @Column(name = "VNMPTNP", nullable = false, length = 20) private String vnmptnp;    // first name partner child
    @Column(name = "TUSPTNP", nullable = false, length = 10) private String tusptnp;    // prefix partner child
    @Column(name = "ANMPTNP", nullable = false, length = 50) private String anmptnp;    // last name partner child
    @Column(name = "ODGKNDP", nullable = false) private              int    odgkndp;    // day of decease child
    @Column(name = "OMDKNDP", nullable = false) private              int    omdkndp;    // month of decease child
    @Column(name = "OJRKNDP", nullable = false) private              int    ojrkndp;    // year of decease child
    @Column(name = "OPLKNDP", nullable = false, length = 50) private String oplkndp;    // place decease child
    @Column(name = "ADGKNDP", nullable = false) private              int    adgkndp;    // day of departure child
    @Column(name = "AMDKNDP", nullable = false) private              int    amdkndp;    // month of departure child
    @Column(name = "AJRKNDP", nullable = false) private              int    ajrkndp;    // year of departure child
    @Column(name = "APLKNDP", nullable = false, length = 50) private String aplkndp;    // destination departing child
    @Column(name = "AANTEK", nullable = false, length = 60) private  String aantek;     // remark

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Pkeigknd() {
    }

    public Pkeigknd(int vgnrkdp) {
        this.vgnrkdp = vgnrkdp;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getVgnrkdp() {
        return vgnrkdp;
    }

    public void setVgnrkdp(int vgnrkdp) {
        this.vgnrkdp = vgnrkdp;
    }

    public String getAnmkndp() {
        return anmkndp;
    }

    public void setAnmkndp(String anmkndp) {
        this.anmkndp = anmkndp;
    }

    public String getTuskndp() {
        return tuskndp;
    }

    public void setTuskndp(String tuskndp) {
        this.tuskndp = tuskndp;
    }

    public String getVn1kndp() {
        return vn1kndp;
    }

    public void setVn1kndp(String vn1kndp) {
        this.vn1kndp = vn1kndp;
    }

    public String getVn2kndp() {
        return vn2kndp;
    }

    public void setVn2kndp(String vn2kndp) {
        this.vn2kndp = vn2kndp;
    }

    public String getVn3kndp() {
        return vn3kndp;
    }

    public void setVn3kndp(String vn3kndp) {
        this.vn3kndp = vn3kndp;
    }

    public int getGdgkndp() {
        return gdgkndp;
    }

    public void setGdgkndp(int gdgkndp) {
        this.gdgkndp = gdgkndp;
    }

    public int getGmdkndp() {
        return gmdkndp;
    }

    public void setGmdkndp(int gmdkndp) {
        this.gmdkndp = gmdkndp;
    }

    public int getGjrkndp() {
        return gjrkndp;
    }

    public void setGjrkndp(int gjrkndp) {
        this.gjrkndp = gjrkndp;
    }

    public int getGdgkndpcr() {
        return gdgkndpcr;
    }

    public void setGdgkndpcr(int gdgkndpcr) {
        this.gdgkndpcr = gdgkndpcr;
    }

    public int getGmdkndpcr() {
        return gmdkndpcr;
    }

    public void setGmdkndpcr(int gmdkndpcr) {
        this.gmdkndpcr = gmdkndpcr;
    }

    public int getGjrkndpcr() {
        return gjrkndpcr;
    }

    public void setGjrkndpcr(int gjrkndpcr) {
        this.gjrkndpcr = gjrkndpcr;
    }

    public String getGplkndp() {
        return gplkndp;
    }

    public void setGplkndp(String gplkndp) {
        this.gplkndp = gplkndp;
    }

    public String getGlnkndp() {
        return glnkndp;
    }

    public void setGlnkndp(String glnkndp) {
        this.glnkndp = glnkndp;
    }

    public String getRelkndp() {
        return relkndp;
    }

    public void setRelkndp(String relkndp) {
        this.relkndp = relkndp;
    }

    public int getHdgkndp() {
        return hdgkndp;
    }

    public void setHdgkndp(int hdgkndp) {
        this.hdgkndp = hdgkndp;
    }

    public int getHmdkndp() {
        return hmdkndp;
    }

    public void setHmdkndp(int hmdkndp) {
        this.hmdkndp = hmdkndp;
    }

    public int getHjrkndp() {
        return hjrkndp;
    }

    public void setHjrkndp(int hjrkndp) {
        this.hjrkndp = hjrkndp;
    }

    public String getHplkndp() {
        return hplkndp;
    }

    public void setHplkndp(String hplkndp) {
        this.hplkndp = hplkndp;
    }

    public String getVnmptnp() {
        return vnmptnp;
    }

    public void setVnmptnp(String vnmptnp) {
        this.vnmptnp = vnmptnp;
    }

    public String getTusptnp() {
        return tusptnp;
    }

    public void setTusptnp(String tusptnp) {
        this.tusptnp = tusptnp;
    }

    public String getAnmptnp() {
        return anmptnp;
    }

    public void setAnmptnp(String anmptnp) {
        this.anmptnp = anmptnp;
    }

    public int getOdgkndp() {
        return odgkndp;
    }

    public void setOdgkndp(int odgkndp) {
        this.odgkndp = odgkndp;
    }

    public int getOmdkndp() {
        return omdkndp;
    }

    public void setOmdkndp(int omdkndp) {
        this.omdkndp = omdkndp;
    }

    public int getOjrkndp() {
        return ojrkndp;
    }

    public void setOjrkndp(int ojrkndp) {
        this.ojrkndp = ojrkndp;
    }

    public String getOplkndp() {
        return oplkndp;
    }

    public void setOplkndp(String oplkndp) {
        this.oplkndp = oplkndp;
    }

    public int getAdgkndp() {
        return adgkndp;
    }

    public void setAdgkndp(int adgkndp) {
        this.adgkndp = adgkndp;
    }

    public int getAmdkndp() {
        return amdkndp;
    }

    public void setAmdkndp(int amdkndp) {
        this.amdkndp = amdkndp;
    }

    public int getAjrkndp() {
        return ajrkndp;
    }

    public void setAjrkndp(int ajrkndp) {
        this.ajrkndp = ajrkndp;
    }

    public String getAplkndp() {
        return aplkndp;
    }

    public void setAplkndp(String aplkndp) {
        this.aplkndp = aplkndp;
    }

    public String getAantek() {
        return aantek;
    }

    public void setAantek(String aantek) {
        this.aantek = aantek;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
