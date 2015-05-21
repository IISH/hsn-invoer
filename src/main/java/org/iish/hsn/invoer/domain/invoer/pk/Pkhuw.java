package org.iish.hsn.invoer.domain.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;

@Entity
@Table(name = "pkhuw",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VNRHUWP", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Pkhuw extends Invoer {
    @Column(name = "IDNR", nullable = false) private    int idnr;                       // ID Number
    @Column(name = "VNRHUWP", nullable = false) private int vnrhuwp;                    // sequence number spouse

    @Column(name = "ANMHUWP", nullable = false, length = 50) private String anmhuwp = "";    // last name spouse
    @Column(name = "TUSHUWP", nullable = false, length = 10) private String tushuwp = "";    // prefix spouse
    @Column(name = "VN1HUWP", nullable = false, length = 20) private String vn1huwp = "";    // 1st firstname spouse
    @Column(name = "VN2HUWP", nullable = false, length = 20) private String vn2huwp = "";    // 2nd firstname spouse
    @Column(name = "VN3HUWP", nullable = false, length = 30) private String vn3huwp = "";    // 3rd firstname spouse

    @Column(name = "BRPHUWP", nullable = false, length = 50) private String brphuwp = "";    // profession spouse

    @Column(name = "GDGHUWP", nullable = false) private   int gdghuwp;                  // day of birth spouse
    @Column(name = "GMDHUWP", nullable = false) private   int gmdhuwp;                  // month of birth spouse
    @Column(name = "GJRHUWP", nullable = false) private   int gjrhuwp;                  // year of birth spouse
    @Column(name = "GDGHUWPCR", nullable = false) private int gdghuwpcr;                // day of birth spouse after correction
    @Column(name = "GMDHUWPCR", nullable = false) private int gmdhuwpcr;                // month of birth spouse after correction
    @Column(name = "GJRHUWPCR", nullable = false) private int gjrhuwpcr;                // year of birth spouse after correction

    @Column(name = "GPLHUWP", nullable = false, length = 50) private String gplhuwp = "";    // birth place spouse

    @Column(name = "HDGHUWP", nullable = false) private              int    hdghuwp;    // day of marriage spouse
    @Column(name = "HMDHUWP", nullable = false) private              int    hmdhuwp;    // month of marriage spouse
    @Column(name = "HJRHUWP", nullable = false) private              int    hjrhuwp;    // year of marriage spouse
    @Column(name = "HPLHUWP", nullable = false, length = 50) private String hplhuwp = "";    // place of marriage

    @Column(name = "ODGHUWP", nullable = false) private int odghuwp;                    // day of decease spouse
    @Column(name = "OMDHUWP", nullable = false) private int omdhuwp;                    // month of decease spouse
    @Column(name = "OJRHUWP", nullable = false) private int ojrhuwp;                    // year of decease spouse

    @Column(name = "ORDHUWP", nullable = false) private int ordhuwp;                    // reason divorce

    @Column(name = "OPLHUWP", nullable = false, length = 50) private String oplhuwp = "";    // place decease spouse
    @Column(name = "ADGHUWP", nullable = false) private              int    adghuwp;    // day of departure spouse
    @Column(name = "AMDHUWP", nullable = false) private              int    amdhuwp;    // month of departure spouse
    @Column(name = "AJRHUWP", nullable = false) private              int    ajrhuwp;    // year of departure spouse

    @Column(name = "APLHUWP", nullable = false, length = 50) private String aplhuwp = "";    // destination departing spouse
    @Column(name = "SRTHUWP", nullable = false, length = 50) private String srthuwp = "";    // kind of marriage (geregistreerd partnerschap etc.)

    @Column(name = "DDGHUWP", nullable = false) private int ddghuwp;                    // registration day spouse
    @Column(name = "DMDHUWP", nullable = false) private int dmdhuwp;                    // registration month spouse
    @Column(name = "DJRHUWP", nullable = false) private int djrhuwp;                    // registration year spouse

    @Column(name = "OPDGHUWP", nullable = false) private int opdghuwp;                  // day GBA entry
    @Column(name = "OPMDHUWP", nullable = false) private int opmdhuwp;                  // month GBA entry
    @Column(name = "OPJRHUWP", nullable = false) private int opjrhuwp;                  // year GBA entry

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public Pkhuw() {
    }

    public Pkhuw(int vnrhuwp) {
        this.vnrhuwp = vnrhuwp;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getVnrhuwp() {
        return vnrhuwp;
    }

    public void setVnrhuwp(int vnrhuwp) {
        this.vnrhuwp = vnrhuwp;
    }

    public String getAnmhuwp() {
        return anmhuwp;
    }

    public void setAnmhuwp(String anmhuwp) {
        this.anmhuwp = anmhuwp;
    }

    public String getTushuwp() {
        return tushuwp;
    }

    public void setTushuwp(String tushuwp) {
        this.tushuwp = tushuwp;
    }

    public String getVn1huwp() {
        return vn1huwp;
    }

    public void setVn1huwp(String vn1huwp) {
        this.vn1huwp = vn1huwp;
    }

    public String getVn2huwp() {
        return vn2huwp;
    }

    public void setVn2huwp(String vn2huwp) {
        this.vn2huwp = vn2huwp;
    }

    public String getVn3huwp() {
        return vn3huwp;
    }

    public void setVn3huwp(String vn3huwp) {
        this.vn3huwp = vn3huwp;
    }

    public String getBrphuwp() {
        return brphuwp;
    }

    public void setBrphuwp(String brphuwp) {
        this.brphuwp = brphuwp;
    }

    public int getGdghuwp() {
        return gdghuwp;
    }

    public void setGdghuwp(int gdghuwp) {
        this.gdghuwp = gdghuwp;
    }

    public int getGmdhuwp() {
        return gmdhuwp;
    }

    public void setGmdhuwp(int gmdhuwp) {
        this.gmdhuwp = gmdhuwp;
    }

    public int getGjrhuwp() {
        return gjrhuwp;
    }

    public void setGjrhuwp(int gjrhuwp) {
        this.gjrhuwp = gjrhuwp;
    }

    public int getGdghuwpcr() {
        return gdghuwpcr;
    }

    public void setGdghuwpcr(int gdghuwpcr) {
        this.gdghuwpcr = gdghuwpcr;
    }

    public int getGmdhuwpcr() {
        return gmdhuwpcr;
    }

    public void setGmdhuwpcr(int gmdhuwpcr) {
        this.gmdhuwpcr = gmdhuwpcr;
    }

    public int getGjrhuwpcr() {
        return gjrhuwpcr;
    }

    public void setGjrhuwpcr(int gjrhuwpcr) {
        this.gjrhuwpcr = gjrhuwpcr;
    }

    public String getGplhuwp() {
        return gplhuwp;
    }

    public void setGplhuwp(String gplhuwp) {
        this.gplhuwp = gplhuwp;
    }

    public int getHdghuwp() {
        return hdghuwp;
    }

    public void setHdghuwp(int hdghuwp) {
        this.hdghuwp = hdghuwp;
    }

    public int getHmdhuwp() {
        return hmdhuwp;
    }

    public void setHmdhuwp(int hmdhuwp) {
        this.hmdhuwp = hmdhuwp;
    }

    public int getHjrhuwp() {
        return hjrhuwp;
    }

    public void setHjrhuwp(int hjrhuwp) {
        this.hjrhuwp = hjrhuwp;
    }

    public String getHplhuwp() {
        return hplhuwp;
    }

    public void setHplhuwp(String hplhuwp) {
        this.hplhuwp = hplhuwp;
    }

    public int getOdghuwp() {
        return odghuwp;
    }

    public void setOdghuwp(int odghuwp) {
        this.odghuwp = odghuwp;
    }

    public int getOmdhuwp() {
        return omdhuwp;
    }

    public void setOmdhuwp(int omdhuwp) {
        this.omdhuwp = omdhuwp;
    }

    public int getOjrhuwp() {
        return ojrhuwp;
    }

    public void setOjrhuwp(int ojrhuwp) {
        this.ojrhuwp = ojrhuwp;
    }

    public int getOrdhuwp() {
        return ordhuwp;
    }

    public void setOrdhuwp(int ordhuwp) {
        this.ordhuwp = ordhuwp;
    }

    public String getOplhuwp() {
        return oplhuwp;
    }

    public void setOplhuwp(String oplhuwp) {
        this.oplhuwp = oplhuwp;
    }

    public int getAdghuwp() {
        return adghuwp;
    }

    public void setAdghuwp(int adghuwp) {
        this.adghuwp = adghuwp;
    }

    public int getAmdhuwp() {
        return amdhuwp;
    }

    public void setAmdhuwp(int amdhuwp) {
        this.amdhuwp = amdhuwp;
    }

    public int getAjrhuwp() {
        return ajrhuwp;
    }

    public void setAjrhuwp(int ajrhuwp) {
        this.ajrhuwp = ajrhuwp;
    }

    public String getAplhuwp() {
        return aplhuwp;
    }

    public void setAplhuwp(String aplhuwp) {
        this.aplhuwp = aplhuwp;
    }

    public String getSrthuwp() {
        return srthuwp;
    }

    public void setSrthuwp(String srthuwp) {
        this.srthuwp = srthuwp;
    }

    public int getDdghuwp() {
        return ddghuwp;
    }

    public void setDdghuwp(int ddghuwp) {
        this.ddghuwp = ddghuwp;
    }

    public int getDmdhuwp() {
        return dmdhuwp;
    }

    public void setDmdhuwp(int dmdhuwp) {
        this.dmdhuwp = dmdhuwp;
    }

    public int getDjrhuwp() {
        return djrhuwp;
    }

    public void setDjrhuwp(int djrhuwp) {
        this.djrhuwp = djrhuwp;
    }

    public int getOpdghuwp() {
        return opdghuwp;
    }

    public void setOpdghuwp(int opdghuwp) {
        this.opdghuwp = opdghuwp;
    }

    public int getOpmdhuwp() {
        return opmdhuwp;
    }

    public void setOpmdhuwp(int opmdhuwp) {
        this.opmdhuwp = opmdhuwp;
    }

    public int getOpjrhuwp() {
        return opjrhuwp;
    }

    public void setOpjrhuwp(int opjrhuwp) {
        this.opjrhuwp = opjrhuwp;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
