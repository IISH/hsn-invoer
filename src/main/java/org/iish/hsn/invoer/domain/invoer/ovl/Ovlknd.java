package org.iish.hsn.invoer.domain.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ovlknd",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Ovlknd extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private                  int    idnr;
    @Column(name = "OACGEMNR", nullable = false) private              int    oacgemnr;
    @Column(name = "OACGEMNM", nullable = false, length = 50) private String oacgemnm;
    @Column(name = "OAKTENR", nullable = false) private               int    oaktenr;
    @Column(name = "OAKTEUUR", nullable = false) private              int    oakteuur;
    @Column(name = "OAKTEMIN", nullable = false) private              int    oaktemin;
    @Column(name = "OAKTEDAG", nullable = false) private              int    oaktedag;
    @Column(name = "OAKTEMND", nullable = false) private              int    oaktemnd;
    @Column(name = "OAKTEJR", nullable = false) private               int    oaktejr;
    @Column(name = "LENGEB", nullable = false) private                int    lengeb;
    @Column(name = "AGVVADR", nullable = false, length = 1) private   String agvvadr;
    @Column(name = "NAGVR", nullable = false) private                 int    nagvr;
    @Column(name = "HNDTKV", nullable = false, length = 1) private    String hndtkv;
    @Column(name = "GEGOVL", nullable = false, length = 1) private    String gegovl;
    @Column(name = "GEGVAD", nullable = false, length = 1) private    String gegvad;
    @Column(name = "GEGMOE", nullable = false, length = 1) private    String gegmoe;
    @Column(name = "ANMOVL", nullable = false, length = 50) private   String anmovl;
    @Column(name = "TUSOVL", nullable = false, length = 10) private   String tusovl;
    @Column(name = "VRN1OVL", nullable = false, length = 20) private  String vrn1ovl;
    @Column(name = "VRN2OVL", nullable = false, length = 20) private  String vrn2ovl;
    @Column(name = "VRN3OVL", nullable = false, length = 30) private  String vrn3ovl;
    @Column(name = "LAAUG", nullable = false) private                 int    laaug;
    @Column(name = "BRPOVL", nullable = false, length = 50) private   String brpovl;
    @Column(name = "GBPOVL", nullable = false) private                int    gbpovl;
    @Column(name = "GGMOVL", nullable = false, length = 50) private   String ggmovl;
    @Column(name = "ADROVL", nullable = false, length = 50) private   String adrovl;
    @Column(name = "BRGOVL", nullable = false, length = 1) private    String brgovl;
    @Column(name = "OVLSEX", nullable = false, length = 1) private    String ovlsex;
    @Column(name = "OVLDAG", nullable = false) private                int    ovldag;
    @Column(name = "OVLMND", nullable = false) private                int    ovlmnd;
    @Column(name = "OVLJR", nullable = false) private                 int    ovljr;
    @Column(name = "OVLUUR", nullable = false) private                int    ovluur;
    @Column(name = "OVLMIN", nullable = false) private                int    ovlmin;
    @Column(name = "PLOOVL", nullable = false, length = 50) private   String ploovl;
    @Column(name = "LFTUOVL", nullable = false) private               int    lftuovl;
    @Column(name = "LFTDOVL", nullable = false) private               int    lftdovl;
    @Column(name = "LFTWOVL", nullable = false) private               int    lftwovl;
    @Column(name = "LFTMOVL", nullable = false) private               int    lftmovl;
    @Column(name = "LFTJOVL", nullable = false) private               int    lftjovl;
    @Column(name = "MREOVL", nullable = false) private                int    mreovl;
    @Column(name = "ANMVOVL", nullable = false, length = 50) private  String anmvovl;
    @Column(name = "TUSVOVL", nullable = false, length = 10) private  String tusvovl;
    @Column(name = "VRN1VOVL", nullable = false, length = 20) private String vrn1vovl;
    @Column(name = "VRN2VOVL", nullable = false, length = 20) private String vrn2vovl;
    @Column(name = "VRN3VOVL", nullable = false, length = 30) private String vrn3vovl;
    @Column(name = "LEVVOVL", nullable = false, length = 1) private   String levvovl;
    @Column(name = "BRPVOVL", nullable = false, length = 50) private  String brpvovl;
    @Column(name = "LFVROVL", nullable = false) private               int    lfvrovl;
    @Column(name = "ADRVOVL", nullable = false, length = 50) private  String adrvovl;
    @Column(name = "ANMMOVL", nullable = false, length = 50) private  String anmmovl;
    @Column(name = "TUSMOVL", nullable = false, length = 10) private  String tusmovl;
    @Column(name = "VRN1MOVL", nullable = false, length = 20) private String vrn1movl;
    @Column(name = "VRN2MOVL", nullable = false, length = 20) private String vrn2movl;
    @Column(name = "VRN3MOVL", nullable = false, length = 30) private String vrn3movl;
    @Column(name = "LEVMOVL", nullable = false, length = 1) private   String levmovl;
    @Column(name = "BRPMOVL", nullable = false, length = 50) private  String brpmovl;
    @Column(name = "LFMROVL", nullable = false) private               int    lfmrovl;
    @Column(name = "ADRMOVL", nullable = false, length = 50) private  String adrmovl;
    @Column(name = "EXTRACT", nullable = false, length = 1) private   String extract;
    @Column(name = "PROBLM", nullable = false, length = 1) private    String problm;

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

    public String getOacgemnm() {
        return oacgemnm;
    }

    public void setOacgemnm(String oacgemnm) {
        this.oacgemnm = oacgemnm;
    }

    public int getOaktenr() {
        return oaktenr;
    }

    public void setOaktenr(int oaktenr) {
        this.oaktenr = oaktenr;
    }

    public int getOakteuur() {
        return oakteuur;
    }

    public void setOakteuur(int oakteuur) {
        this.oakteuur = oakteuur;
    }

    public int getOaktemin() {
        return oaktemin;
    }

    public void setOaktemin(int oaktemin) {
        this.oaktemin = oaktemin;
    }

    public int getOaktedag() {
        return oaktedag;
    }

    public void setOaktedag(int oaktedag) {
        this.oaktedag = oaktedag;
    }

    public int getOaktemnd() {
        return oaktemnd;
    }

    public void setOaktemnd(int oaktemnd) {
        this.oaktemnd = oaktemnd;
    }

    public int getOaktejr() {
        return oaktejr;
    }

    public void setOaktejr(int oaktejr) {
        this.oaktejr = oaktejr;
    }

    public String getAgvvadr() {
        return agvvadr;
    }

    public void setAgvvadr(String agvvadr) {
        this.agvvadr = agvvadr;
    }

    public int getNagvr() {
        return nagvr;
    }

    public void setNagvr(int nagvr) {
        this.nagvr = nagvr;
    }

    public String getHndtkv() {
        return hndtkv;
    }

    public void setHndtkv(String hndtkv) {
        this.hndtkv = hndtkv;
    }

    public String getGegovl() {
        return gegovl;
    }

    public void setGegovl(String gegovl) {
        this.gegovl = gegovl;
    }

    public String getGegvad() {
        return gegvad;
    }

    public void setGegvad(String gegvad) {
        this.gegvad = gegvad;
    }

    public String getGegmoe() {
        return gegmoe;
    }

    public void setGegmoe(String gegmoe) {
        this.gegmoe = gegmoe;
    }

    public String getAnmovl() {
        return anmovl;
    }

    public void setAnmovl(String anmovl) {
        this.anmovl = anmovl;
    }

    public String getTusovl() {
        return tusovl;
    }

    public void setTusovl(String tusovl) {
        this.tusovl = tusovl;
    }

    public String getVrn1ovl() {
        return vrn1ovl;
    }

    public void setVrn1ovl(String vrn1ovl) {
        this.vrn1ovl = vrn1ovl;
    }

    public String getVrn2ovl() {
        return vrn2ovl;
    }

    public void setVrn2ovl(String vrn2ovl) {
        this.vrn2ovl = vrn2ovl;
    }

    public String getVrn3ovl() {
        return vrn3ovl;
    }

    public void setVrn3ovl(String vrn3ovl) {
        this.vrn3ovl = vrn3ovl;
    }

    public int getLaaug() {
        return laaug;
    }

    public void setLaaug(int laaug) {
        this.laaug = laaug;
    }

    public String getBrpovl() {
        return brpovl;
    }

    public void setBrpovl(String brpovl) {
        this.brpovl = brpovl;
    }

    public int getGbpovl() {
        return gbpovl;
    }

    public void setGbpovl(int gbpovl) {
        this.gbpovl = gbpovl;
    }

    public String getGgmovl() {
        return ggmovl;
    }

    public void setGgmovl(String ggmovl) {
        this.ggmovl = ggmovl;
    }

    public String getAdrovl() {
        return adrovl;
    }

    public void setAdrovl(String adrovl) {
        this.adrovl = adrovl;
    }

    public String getBrgovl() {
        return brgovl;
    }

    public void setBrgovl(String brgovl) {
        this.brgovl = brgovl;
    }

    public String getOvlsex() {
        return ovlsex;
    }

    public void setOvlsex(String ovlsex) {
        this.ovlsex = ovlsex;
    }

    public int getOvldag() {
        return ovldag;
    }

    public void setOvldag(int ovldag) {
        this.ovldag = ovldag;
    }

    public int getOvlmnd() {
        return ovlmnd;
    }

    public void setOvlmnd(int ovlmnd) {
        this.ovlmnd = ovlmnd;
    }

    public int getOvljr() {
        return ovljr;
    }

    public void setOvljr(int ovljr) {
        this.ovljr = ovljr;
    }

    public int getOvluur() {
        return ovluur;
    }

    public void setOvluur(int ovluur) {
        this.ovluur = ovluur;
    }

    public int getOvlmin() {
        return ovlmin;
    }

    public void setOvlmin(int ovlmin) {
        this.ovlmin = ovlmin;
    }

    public String getPloovl() {
        return ploovl;
    }

    public void setPloovl(String ploovl) {
        this.ploovl = ploovl;
    }

    public int getLftuovl() {
        return lftuovl;
    }

    public void setLftuovl(int lftuovl) {
        this.lftuovl = lftuovl;
    }

    public int getLftdovl() {
        return lftdovl;
    }

    public void setLftdovl(int lftdovl) {
        this.lftdovl = lftdovl;
    }

    public int getLftwovl() {
        return lftwovl;
    }

    public void setLftwovl(int lftwovl) {
        this.lftwovl = lftwovl;
    }

    public int getLftmovl() {
        return lftmovl;
    }

    public void setLftmovl(int lftmovl) {
        this.lftmovl = lftmovl;
    }

    public int getLftjovl() {
        return lftjovl;
    }

    public void setLftjovl(int lftjovl) {
        this.lftjovl = lftjovl;
    }

    public int getMreovl() {
        return mreovl;
    }

    public void setMreovl(int mreovl) {
        this.mreovl = mreovl;
    }

    public String getAnmvovl() {
        return anmvovl;
    }

    public void setAnmvovl(String anmvovl) {
        this.anmvovl = anmvovl;
    }

    public String getTusvovl() {
        return tusvovl;
    }

    public void setTusvovl(String tusvovl) {
        this.tusvovl = tusvovl;
    }

    public String getVrn1vovl() {
        return vrn1vovl;
    }

    public void setVrn1vovl(String vrn1vovl) {
        this.vrn1vovl = vrn1vovl;
    }

    public String getVrn2vovl() {
        return vrn2vovl;
    }

    public void setVrn2vovl(String vrn2vovl) {
        this.vrn2vovl = vrn2vovl;
    }

    public String getVrn3vovl() {
        return vrn3vovl;
    }

    public void setVrn3vovl(String vrn3vovl) {
        this.vrn3vovl = vrn3vovl;
    }

    public String getLevvovl() {
        return levvovl;
    }

    public void setLevvovl(String levvovl) {
        this.levvovl = levvovl;
    }

    public String getBrpvovl() {
        return brpvovl;
    }

    public void setBrpvovl(String brpvovl) {
        this.brpvovl = brpvovl;
    }

    public int getLfvrovl() {
        return lfvrovl;
    }

    public void setLfvrovl(int lfvrovl) {
        this.lfvrovl = lfvrovl;
    }

    public String getAdrvovl() {
        return adrvovl;
    }

    public void setAdrvovl(String adrvovl) {
        this.adrvovl = adrvovl;
    }

    public String getAnmmovl() {
        return anmmovl;
    }

    public void setAnmmovl(String anmmovl) {
        this.anmmovl = anmmovl;
    }

    public String getTusmovl() {
        return tusmovl;
    }

    public void setTusmovl(String tusmovl) {
        this.tusmovl = tusmovl;
    }

    public String getVrn1movl() {
        return vrn1movl;
    }

    public void setVrn1movl(String vrn1movl) {
        this.vrn1movl = vrn1movl;
    }

    public String getVrn2movl() {
        return vrn2movl;
    }

    public void setVrn2movl(String vrn2movl) {
        this.vrn2movl = vrn2movl;
    }

    public String getVrn3movl() {
        return vrn3movl;
    }

    public void setVrn3movl(String vrn3movl) {
        this.vrn3movl = vrn3movl;
    }

    public String getLevmovl() {
        return levmovl;
    }

    public void setLevmovl(String levmovl) {
        this.levmovl = levmovl;
    }

    public String getBrpmovl() {
        return brpmovl;
    }

    public void setBrpmovl(String brpmovl) {
        this.brpmovl = brpmovl;
    }

    public int getLfmrovl() {
        return lfmrovl;
    }

    public void setLfmrovl(int lfmrovl) {
        this.lfmrovl = lfmrovl;
    }

    public String getAdrmovl() {
        return adrmovl;
    }

    public void setAdrmovl(String adrmovl) {
        this.adrmovl = adrmovl;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public String getProblm() {
        return problm;
    }

    public void setProblm(String problm) {
        this.problm = problm;
    }

    public int getOacgemnr() {
        return oacgemnr;
    }

    public void setOacgemnr(int oacgemnr) {
        this.oacgemnr = oacgemnr;
    }

    public int getLengeb() {
        return lengeb;
    }

    public void setLengeb(int lengeb) {
        this.lengeb = lengeb;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}