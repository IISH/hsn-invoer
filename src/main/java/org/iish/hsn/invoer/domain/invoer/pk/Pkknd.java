package org.iish.hsn.invoer.domain.invoer.pk;

import javax.persistence.*;

import org.iish.hsn.invoer.domain.invoer.Invoer;

@Entity
@Table(name = "pkknd",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Pkknd extends Invoer {
    @Column(name = "IDNR", nullable = false) private  int idnr;                         // ID Number
    @Column(name = "IDNRP", nullable = false) private int idnrp;                        // IDNR partner

    @Column(name = "GAKTNRP", nullable = false, length = 8) private String gaktnrp;     // Number or code of the birth certificate on which the Personal Card has been based
    @Column(name = "PKTYPE", nullable = false) private              int    pktype;      // Type of PK

    @Column(name = "EINDAGPK", nullable = false) private int eindagpk;                  // Day of end of the PK, when the observation of the PK-holder does not end with his or her death
    @Column(name = "EINMNDPK", nullable = false) private int einmndpk;                  // Month
    @Column(name = "EINJARPK", nullable = false) private int einjarpk;                  // Year

    @Column(name = "CTRDGP", nullable = false) private int ctrdgp;                      // Day of the check of the personal card with the information on the PK and check sign of the civil servant.
    @Column(name = "CTRMDP", nullable = false) private int ctrmdp;                      // Month of the check of the personal card with the information on the PK and check sign of the civil servant.
    @Column(name = "CTRJRP", nullable = false) private int ctrjrp;                      // Year of the check of the personal card with the information on the PK and check sign of the civil servant.

    @Column(name = "CTRPARP", nullable = false, length = 1) private  String ctrparp;    // check sign of the civil servant (j/n)
    @Column(name = "GZNVRMP", nullable = false, length = 50) private String gznvrmp;    // Structure of the family, indicated by who is the head of the family

    // Information about the PK-Holder

    @Column(name = "ANMPERP", nullable = false, length = 50) private  String anmperp;   // Last name PK-Holder
    @Column(name = "TUSPERP", nullable = false, length = 10) private  String tusperp;   // Prefix Last Name PK-Holder
    @Column(name = "VNM1PERP", nullable = false, length = 20) private String vnm1perp;  // First first name of PK-Holder
    @Column(name = "VNM2PERP", nullable = false, length = 20) private String vnm2perp;  // Second first name of PK-Holder
    @Column(name = "VNM3PERP", nullable = false, length = 30) private String vnm3perp;  // Third (and fourth, fifth etc.) first name of PK-Holder

    @Column(name = "GDGPERP", nullable = false) private int gdgperp;                    // Birth day of PK-Holder
    @Column(name = "GMDPERP", nullable = false) private int gmdperp;                    // Birth month of PK-Holder
    @Column(name = "GJRPERP", nullable = false) private int gjrperp;                    // Birth year of PK-Holder

    @Column(name = "GDGPERPCR", nullable = false) private int gdgperpcr;                // Birth day of PK-Holder after correction
    @Column(name = "GMDPERPCR", nullable = false) private int gmdperpcr;                // Birth month of PK-Holder after correction
    @Column(name = "GJRPERPCR", nullable = false) private int gjrperpcr;                // Birth year of PK-Holder after correction

    @Column(name = "GPLPERP", nullable = false, length = 50) private String gplperp;    // Birth place of PK-Holder
    @Column(name = "NATPERP", nullable = false, length = 40) private String natperp;    // Nationality of PK-Holder
    @Column(name = "GDSPERP", nullable = false, length = 20) private String gdsperp;    // Religion of PK-Holder
    @Column(name = "GSLPERP", nullable = false, length = 1) private  String gslperp;    // Gender of PK-Holder

    // Information about the Father of the PK-Holder

    @Column(name = "ANMVDRP", nullable = false, length = 50) private  String anmvdrp;   // Last name Father PK-Holder
    @Column(name = "TUSVDRP", nullable = false, length = 10) private  String tusvdrp;   // Prefix Last name Father PK-Holder
    @Column(name = "VNM1VDRP", nullable = false, length = 20) private String vnm1vdrp;  // First first name Father PK-Holder
    @Column(name = "VNM2VDRP", nullable = false, length = 20) private String vnm2vdrp;  // Second firstname Father PK-Holder
    @Column(name = "VNM3VDRP", nullable = false, length = 30) private String vnm3vdrp;  // Third (and fourth, fifth etc.) first name Father of PK-Holder

    @Column(name = "GDGVDRP", nullable = false) private int gdgvdrp;                    // Birth day of Father PK-Holder
    @Column(name = "GMDVDRP", nullable = false) private int gmdvdrp;                    // Birth month of Father PK-Holder
    @Column(name = "GJRVDRP", nullable = false) private int gjrvdrp;                    // Birth year of Father PK-Holder

    @Column(name = "GDGVDRPCR", nullable = false) private int gdgvdrpcr;                // Birth day of Father PK-Holder after correction
    @Column(name = "GMDVDRPCR", nullable = false) private int gmdvdrpcr;                // Birth month of Father PK-Holder after correction
    @Column(name = "GJRVDRPCR", nullable = false) private int gjrvdrpcr;                // Birth year of Father PK-Holder after correction

    @Column(name = "GPLVDRP", nullable = false, length = 50) private String gplvdrp;    // Birth place of Father PK-Holder

    // Information about the Mother of the PK-Holder

    @Column(name = "ANMMDRP", nullable = false, length = 50) private  String anmmdrp;   // Last name Mother PK-Holder
    @Column(name = "TUSMDRP", nullable = false, length = 10) private  String tusmdrp;   // Prefix Last name Mother PK-Holder
    @Column(name = "VNM1MDRP", nullable = false, length = 20) private String vnm1mdrp;  // First first name Mother PK-Holder
    @Column(name = "VNM2MDRP", nullable = false, length = 20) private String vnm2mdrp;  // Second firstname Mother PK-Holder
    @Column(name = "VNM3MDRP", nullable = false, length = 30) private String vnm3mdrp;  // Third (and fourth, fifth etc.) first name Mother of PK-Holder

    @Column(name = "GDGMDRP", nullable = false) private int gdgmdrp;                    // Birth day of Mother PK-Holder
    @Column(name = "GMDMDRP", nullable = false) private int gmdmdrp;                    // Birth month of Mother PK-Holder
    @Column(name = "GJRMDRP", nullable = false) private int gjrmdrp;                    // Birth year of Mother PK-Holder

    @Column(name = "GDGMDRPCR", nullable = false) private int gdgmdrpcr;                // Birth day of Mother PK-Holder after correction
    @Column(name = "GMDMDRPCR", nullable = false) private int gmdmdrpcr;                // Birth month of Mother PK-Holder after correction
    @Column(name = "GJRMDRPCR", nullable = false) private int gjrmdrpcr;                // Birth year of Mother PK-Holder after correction

    @Column(name = "GPLMDRP", nullable = false, length = 50) private String gplmdrp;    // Birth place of Mother PK-Holder

    // Information about the PK-Holder

    @Column(name = "ODGPERP", nullable = false) private int odgperp;                    // Decease day of PK-Holder
    @Column(name = "OMDPERP", nullable = false) private int omdperp;                    // Decease month of PK-Holder
    @Column(name = "OJRPERP", nullable = false) private int ojrperp;                    // Decease year of PK-Holder

    @Column(name = "OPLPERP", nullable = false, length = 50) private String oplperp;    // Decease place of PK-Holder
    @Column(name = "OAKPERP", nullable = false, length = 10) private String oakperp;    // Code of death certificate of PK-holder
    @Column(name = "ODOPERP", nullable = false, length = 50) private String odoperp;    // Cause of death of the PK-holder

    @Column(name = "GEGPERP", nullable = false, length = 1) private String gegperp;     // Fields to record distinctions between data of the original birth certificate and the Personal Card; not usable and not necessary in the further process
    @Column(name = "GEGVDRP", nullable = false, length = 1) private String gegvdrp;     // Fields to record distinctions between data of the original birth certificate and the Personal Card; not usable and not necessary in the further process
    @Column(name = "GEGMDRP", nullable = false, length = 1) private String gegmdrp;     // Fields to record distinctions between data of the original birth certificate and the Personal Card; not usable and not necessary in the further process

    @Column(name = "PROBLMP", nullable = false, length = 1) private String problmp;     // Indicator if there are records in the file PKBYZ or not; not usable anymore

    @Column(name = "PSBDGP", nullable = false) private              int    psbdgp;      // Day    of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.
    @Column(name = "PSBMDP", nullable = false) private              int    psbmdp;      // Month  of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.
    @Column(name = "PSBJRP", nullable = false) private              int    psbjrp;      // Year   of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.
    @Column(name = "PSBNRP", nullable = false, length = 12) private String psbnrp;      // Number of the "Persoonsbewijs" (Ausweis), date should be between 01-01-1941 and 5-05-1945.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

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

    public String getGaktnrp() {
        return gaktnrp;
    }

    public void setGaktnrp(String gaktnrp) {
        this.gaktnrp = gaktnrp;
    }

    public int getPktype() {
        return pktype;
    }

    public void setPktype(int pktype) {
        this.pktype = pktype;
    }

    public int getEindagpk() {
        return eindagpk;
    }

    public void setEindagpk(int eindagpk) {
        this.eindagpk = eindagpk;
    }

    public int getEinmndpk() {
        return einmndpk;
    }

    public void setEinmndpk(int einmndpk) {
        this.einmndpk = einmndpk;
    }

    public int getEinjarpk() {
        return einjarpk;
    }

    public void setEinjarpk(int einjarpk) {
        this.einjarpk = einjarpk;
    }

    public int getCtrdgp() {
        return ctrdgp;
    }

    public void setCtrdgp(int ctrdgp) {
        this.ctrdgp = ctrdgp;
    }

    public int getCtrmdp() {
        return ctrmdp;
    }

    public void setCtrmdp(int ctrmdp) {
        this.ctrmdp = ctrmdp;
    }

    public int getCtrjrp() {
        return ctrjrp;
    }

    public void setCtrjrp(int ctrjrp) {
        this.ctrjrp = ctrjrp;
    }

    public String getCtrparp() {
        return ctrparp;
    }

    public void setCtrparp(String ctrparp) {
        this.ctrparp = ctrparp;
    }

    public String getGznvrmp() {
        return gznvrmp;
    }

    public void setGznvrmp(String gznvrmp) {
        this.gznvrmp = gznvrmp;
    }

    public String getAnmperp() {
        return anmperp;
    }

    public void setAnmperp(String anmperp) {
        this.anmperp = anmperp;
    }

    public String getTusperp() {
        return tusperp;
    }

    public void setTusperp(String tusperp) {
        this.tusperp = tusperp;
    }

    public String getVnm1perp() {
        return vnm1perp;
    }

    public void setVnm1perp(String vnm1perp) {
        this.vnm1perp = vnm1perp;
    }

    public String getVnm2perp() {
        return vnm2perp;
    }

    public void setVnm2perp(String vnm2perp) {
        this.vnm2perp = vnm2perp;
    }

    public String getVnm3perp() {
        return vnm3perp;
    }

    public void setVnm3perp(String vnm3perp) {
        this.vnm3perp = vnm3perp;
    }

    public int getGdgperp() {
        return gdgperp;
    }

    public void setGdgperp(int gdgperp) {
        this.gdgperp = gdgperp;
    }

    public int getGmdperp() {
        return gmdperp;
    }

    public void setGmdperp(int gmdperp) {
        this.gmdperp = gmdperp;
    }

    public int getGjrperp() {
        return gjrperp;
    }

    public void setGjrperp(int gjrperp) {
        this.gjrperp = gjrperp;
    }

    public int getGdgperpcr() {
        return gdgperpcr;
    }

    public void setGdgperpcr(int gdgperpcr) {
        this.gdgperpcr = gdgperpcr;
    }

    public int getGmdperpcr() {
        return gmdperpcr;
    }

    public void setGmdperpcr(int gmdperpcr) {
        this.gmdperpcr = gmdperpcr;
    }

    public int getGjrperpcr() {
        return gjrperpcr;
    }

    public void setGjrperpcr(int gjrperpcr) {
        this.gjrperpcr = gjrperpcr;
    }

    public String getGplperp() {
        return gplperp;
    }

    public void setGplperp(String gplperp) {
        this.gplperp = gplperp;
    }

    public String getNatperp() {
        return natperp;
    }

    public void setNatperp(String natperp) {
        this.natperp = natperp;
    }

    public String getGdsperp() {
        return gdsperp;
    }

    public void setGdsperp(String gdsperp) {
        this.gdsperp = gdsperp;
    }

    public String getGslperp() {
        return gslperp;
    }

    public void setGslperp(String gslperp) {
        this.gslperp = gslperp;
    }

    public String getAnmvdrp() {
        return anmvdrp;
    }

    public void setAnmvdrp(String anmvdrp) {
        this.anmvdrp = anmvdrp;
    }

    public String getTusvdrp() {
        return tusvdrp;
    }

    public void setTusvdrp(String tusvdrp) {
        this.tusvdrp = tusvdrp;
    }

    public String getVnm1vdrp() {
        return vnm1vdrp;
    }

    public void setVnm1vdrp(String vnm1vdrp) {
        this.vnm1vdrp = vnm1vdrp;
    }

    public String getVnm2vdrp() {
        return vnm2vdrp;
    }

    public void setVnm2vdrp(String vnm2vdrp) {
        this.vnm2vdrp = vnm2vdrp;
    }

    public String getVnm3vdrp() {
        return vnm3vdrp;
    }

    public void setVnm3vdrp(String vnm3vdrp) {
        this.vnm3vdrp = vnm3vdrp;
    }

    public int getGdgvdrp() {
        return gdgvdrp;
    }

    public void setGdgvdrp(int gdgvdrp) {
        this.gdgvdrp = gdgvdrp;
    }

    public int getGmdvdrp() {
        return gmdvdrp;
    }

    public void setGmdvdrp(int gmdvdrp) {
        this.gmdvdrp = gmdvdrp;
    }

    public int getGjrvdrp() {
        return gjrvdrp;
    }

    public void setGjrvdrp(int gjrvdrp) {
        this.gjrvdrp = gjrvdrp;
    }

    public int getGdgvdrpcr() {
        return gdgvdrpcr;
    }

    public void setGdgvdrpcr(int gdgvdrpcr) {
        this.gdgvdrpcr = gdgvdrpcr;
    }

    public int getGmdvdrpcr() {
        return gmdvdrpcr;
    }

    public void setGmdvdrpcr(int gmdvdrpcr) {
        this.gmdvdrpcr = gmdvdrpcr;
    }

    public int getGjrvdrpcr() {
        return gjrvdrpcr;
    }

    public void setGjrvdrpcr(int gjrvdrpcr) {
        this.gjrvdrpcr = gjrvdrpcr;
    }

    public String getGplvdrp() {
        return gplvdrp;
    }

    public void setGplvdrp(String gplvdrp) {
        this.gplvdrp = gplvdrp;
    }

    public String getAnmmdrp() {
        return anmmdrp;
    }

    public void setAnmmdrp(String anmmdrp) {
        this.anmmdrp = anmmdrp;
    }

    public String getTusmdrp() {
        return tusmdrp;
    }

    public void setTusmdrp(String tusmdrp) {
        this.tusmdrp = tusmdrp;
    }

    public String getVnm1mdrp() {
        return vnm1mdrp;
    }

    public void setVnm1mdrp(String vnm1mdrp) {
        this.vnm1mdrp = vnm1mdrp;
    }

    public String getVnm2mdrp() {
        return vnm2mdrp;
    }

    public void setVnm2mdrp(String vnm2mdrp) {
        this.vnm2mdrp = vnm2mdrp;
    }

    public String getVnm3mdrp() {
        return vnm3mdrp;
    }

    public void setVnm3mdrp(String vnm3mdrp) {
        this.vnm3mdrp = vnm3mdrp;
    }

    public int getGdgmdrp() {
        return gdgmdrp;
    }

    public void setGdgmdrp(int gdgmdrp) {
        this.gdgmdrp = gdgmdrp;
    }

    public int getGmdmdrp() {
        return gmdmdrp;
    }

    public void setGmdmdrp(int gmdmdrp) {
        this.gmdmdrp = gmdmdrp;
    }

    public int getGjrmdrp() {
        return gjrmdrp;
    }

    public void setGjrmdrp(int gjrmdrp) {
        this.gjrmdrp = gjrmdrp;
    }

    public int getGdgmdrpcr() {
        return gdgmdrpcr;
    }

    public void setGdgmdrpcr(int gdgmdrpcr) {
        this.gdgmdrpcr = gdgmdrpcr;
    }

    public int getGmdmdrpcr() {
        return gmdmdrpcr;
    }

    public void setGmdmdrpcr(int gmdmdrpcr) {
        this.gmdmdrpcr = gmdmdrpcr;
    }

    public int getGjrmdrpcr() {
        return gjrmdrpcr;
    }

    public void setGjrmdrpcr(int gjrmdrpcr) {
        this.gjrmdrpcr = gjrmdrpcr;
    }

    public String getGplmdrp() {
        return gplmdrp;
    }

    public void setGplmdrp(String gplmdrp) {
        this.gplmdrp = gplmdrp;
    }

    public int getOdgperp() {
        return odgperp;
    }

    public void setOdgperp(int odgperp) {
        this.odgperp = odgperp;
    }

    public int getOmdperp() {
        return omdperp;
    }

    public void setOmdperp(int omdperp) {
        this.omdperp = omdperp;
    }

    public int getOjrperp() {
        return ojrperp;
    }

    public void setOjrperp(int ojrperp) {
        this.ojrperp = ojrperp;
    }

    public String getOplperp() {
        return oplperp;
    }

    public void setOplperp(String oplperp) {
        this.oplperp = oplperp;
    }

    public String getOakperp() {
        return oakperp;
    }

    public void setOakperp(String oakperp) {
        this.oakperp = oakperp;
    }

    public String getOdoperp() {
        return odoperp;
    }

    public void setOdoperp(String odoperp) {
        this.odoperp = odoperp;
    }

    public String getGegperp() {
        return gegperp;
    }

    public void setGegperp(String gegperp) {
        this.gegperp = gegperp;
    }

    public String getGegvdrp() {
        return gegvdrp;
    }

    public void setGegvdrp(String gegvdrp) {
        this.gegvdrp = gegvdrp;
    }

    public String getGegmdrp() {
        return gegmdrp;
    }

    public void setGegmdrp(String gegmdrp) {
        this.gegmdrp = gegmdrp;
    }

    public String getProblmp() {
        return problmp;
    }

    public void setProblmp(String problmp) {
        this.problmp = problmp;
    }

    public int getPsbdgp() {
        return psbdgp;
    }

    public void setPsbdgp(int psbdgp) {
        this.psbdgp = psbdgp;
    }

    public int getPsbmdp() {
        return psbmdp;
    }

    public void setPsbmdp(int psbmdp) {
        this.psbmdp = psbmdp;
    }

    public int getPsbjrp() {
        return psbjrp;
    }

    public void setPsbjrp(int psbjrp) {
        this.psbjrp = psbjrp;
    }

    public String getPsbnrp() {
        return psbnrp;
    }

    public void setPsbnrp(String psbnrp) {
        this.psbnrp = psbnrp;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}
