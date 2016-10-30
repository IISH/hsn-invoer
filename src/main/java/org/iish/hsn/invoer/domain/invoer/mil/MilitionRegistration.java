package org.iish.hsn.invoer.domain.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the static attributes of a registration
 */
@Entity
@Table(name = "m1", indexes = {@Index(columnList = "IDNR, VOLG"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class MilitionRegistration extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;
    @Column(name = "VOLG", nullable = false) private int seq;

    @Column(name = "ANM", nullable = false, length = 50) private String familyName = "";
    @Column(name = "VNM", nullable = false, length = 50) private String firstName = "";

    @Column(name = "GBD", nullable = false) private int dayOfBirth;
    @Column(name = "GBM", nullable = false) private int monthOfBirth;
    @Column(name = "GBJ", nullable = false) private int yearOfBirth;

    @Column(name = "GBPLTS", nullable = false, length = 50) private String placeOfBirth = "";
    @Column(name = "WPLTS", nullable = false, length = 50) private String placeOfLiving = "";
    @Column(name = "ODSPLTS", nullable = false, length = 50) private String placeParents = "";
    @Column(name = "VGDPLTS", nullable = false, length = 50) private String placeGuardian = "";

    @Column(name = "INGES", nullable = false, length = 1) private String inGesticht = "";
    @Column(name = "NMGES", nullable = false, length = 50) private String nameGesticht = "";

    @Column(name = "PLGMSD", nullable = false, length = 1) private String commitCrime = "";
    @Column(name = "WELMSD", nullable = false, length = 50) private String whatCrime = "";
    @Column(name = "VERMSD", nullable = false, length = 50) private String conviction = ""; // TODO: ???

    @Column(name = "IVWD", nullable = false, length = 1) private String voluntaryService = "";
    @Column(name = "WVWD", nullable = false, length = 50) private String voluntaryWhere = "";

    @Column(name = "IVRKL", nullable = false, length = 1) private String formerClass = "";
    @Column(name = "HFDLST", nullable = false, length = 1) private String headOfList = "";
    @Column(name = "BTNDST", nullable = false, length = 1) private String outStateService = "";

    @Column(name = "ANMVDR", nullable = false, length = 50) private String familyNameFather = "";
    @Column(name = "VNVDR", nullable = false, length = 50) private String firstNameFather = "";
    @Column(name = "ANMDR", nullable = false, length = 50) private String familyNameMother = "";
    @Column(name = "VNMDR", nullable = false, length = 50) private String firstNameMother = "";
    @Column(name = "ANVGD", nullable = false, length = 50) private String familyNameGuardian = "";
    @Column(name = "VNVGD", nullable = false, length = 50) private String firstNameGuardian = "";

    @Column(name = "BRP", nullable = false, length = 50) private String profession = "";
    @Column(name = "BRPVDR", nullable = false, length = 50) private String professionFather = "";
    @Column(name = "BRPMDR", nullable = false, length = 50) private String professionMother = "";
    @Column(name = "BRPVGD", nullable = false, length = 50) private String professionGuardian = "";

    @Column(name = "LGTME", nullable = false) private int meter;
    @Column(name = "LGTDM", nullable = false) private int decimeter;
    @Column(name = "LGTCM", nullable = false) private int centimeter;
    @Column(name = "LGTMM", nullable = false) private int millimeter;

    @Column(name = "SGMGEZ", nullable = false, length = 50) private String face = "";
    @Column(name = "SGMVHF", nullable = false, length = 50) private String forehead = "";
    @Column(name = "SGMOOG", nullable = false, length = 50) private String eyes = "";
    @Column(name = "SGMNEU", nullable = false, length = 50) private String nose = "";
    @Column(name = "SGMMON", nullable = false, length = 50) private String mouth = "";
    @Column(name = "SGMKIN", nullable = false, length = 50) private String chin = "";
    @Column(name = "SGMHAA", nullable = false, length = 50) private String hair = "";
    @Column(name = "SGMWBR", nullable = false, length = 50) private String eyebrows = "";
    @Column(name = "SGMMTK", nullable = false, length = 50) private String notableSigns = "";

    @Column(name = "RDNVRIJ", nullable = false, length = 50) private String reasonsExcempion = "";
    @Column(name = "RDNOGS", nullable = false, length = 50) private String reasonsInapplicability = "";
    @Column(name = "ADVIES", nullable = false, length = 50) private String advice = "";
    @Column(name = "GRDVRIJ", nullable = false, length = 50) private String motiveExcempion = "";

    @Column(name = "NMRZ", nullable = false) private int numberRegulationIllness;

    @Column(name = "PVNM", nullable = false, length = 1) private String substitute = "";
    @Column(name = "PVNB", nullable = false) private int substituteNumber;
    @Column(name = "ANPV", nullable = false, length = 50) private String familyNameSubstitute = "";
    @Column(name = "VNPV", nullable = false, length = 50) private String firstNameSubstitute = "";

    @Column(name = "ZEEMIL", nullable = false, length = 1) private String inNaval = "";
    @Column(name = "KPINL", nullable = false, length = 50) private String positionAnnexation = "";
    @Column(name = "INLD", nullable = false) private int dayOfAnnexation;
    @Column(name = "INLM", nullable = false) private int monthOfAnnexation;
    @Column(name = "INLJ", nullable = false) private int yearOfAnnexation;

    @Column(name = "AAN", nullable = false, length = 128) private String remarks = "";
    @Column(name = "BYZ", nullable = false, length = 255) private String byz = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPlaceOfLiving() {
        return placeOfLiving;
    }

    public void setPlaceOfLiving(String placeOfLiving) {
        this.placeOfLiving = placeOfLiving;
    }

    public String getPlaceParents() {
        return placeParents;
    }

    public void setPlaceParents(String placeParents) {
        this.placeParents = placeParents;
    }

    public String getPlaceGuardian() {
        return placeGuardian;
    }

    public void setPlaceGuardian(String placeGuardian) {
        this.placeGuardian = placeGuardian;
    }

    public String getInGesticht() {
        return inGesticht;
    }

    public void setInGesticht(String inGesticht) {
        this.inGesticht = inGesticht;
    }

    public String getNameGesticht() {
        return nameGesticht;
    }

    public void setNameGesticht(String nameGesticht) {
        this.nameGesticht = nameGesticht;
    }

    public String getCommitCrime() {
        return commitCrime;
    }

    public void setCommitCrime(String commitCrime) {
        this.commitCrime = commitCrime;
    }

    public String getWhatCrime() {
        return whatCrime;
    }

    public void setWhatCrime(String whatCrime) {
        this.whatCrime = whatCrime;
    }

    public String getConviction() {
        return conviction;
    }

    public void setConviction(String conviction) {
        this.conviction = conviction;
    }

    public String getVoluntaryService() {
        return voluntaryService;
    }

    public void setVoluntaryService(String voluntaryService) {
        this.voluntaryService = voluntaryService;
    }

    public String getVoluntaryWhere() {
        return voluntaryWhere;
    }

    public void setVoluntaryWhere(String voluntaryWhere) {
        this.voluntaryWhere = voluntaryWhere;
    }

    public String getFormerClass() {
        return formerClass;
    }

    public void setFormerClass(String formerClass) {
        this.formerClass = formerClass;
    }

    public String getHeadOfList() {
        return headOfList;
    }

    public void setHeadOfList(String headOfList) {
        this.headOfList = headOfList;
    }

    public String getOutStateService() {
        return outStateService;
    }

    public void setOutStateService(String outStateService) {
        this.outStateService = outStateService;
    }

    public String getFamilyNameFather() {
        return familyNameFather;
    }

    public void setFamilyNameFather(String familyNameFather) {
        this.familyNameFather = familyNameFather;
    }

    public String getFirstNameFather() {
        return firstNameFather;
    }

    public void setFirstNameFather(String firstNameFather) {
        this.firstNameFather = firstNameFather;
    }

    public String getFamilyNameMother() {
        return familyNameMother;
    }

    public void setFamilyNameMother(String familyNameMother) {
        this.familyNameMother = familyNameMother;
    }

    public String getFirstNameMother() {
        return firstNameMother;
    }

    public void setFirstNameMother(String firstNameMother) {
        this.firstNameMother = firstNameMother;
    }

    public String getFamilyNameGuardian() {
        return familyNameGuardian;
    }

    public void setFamilyNameGuardian(String familyNameGuardian) {
        this.familyNameGuardian = familyNameGuardian;
    }

    public String getFirstNameGuardian() {
        return firstNameGuardian;
    }

    public void setFirstNameGuardian(String firstNameGuardian) {
        this.firstNameGuardian = firstNameGuardian;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfessionFather() {
        return professionFather;
    }

    public void setProfessionFather(String professionFather) {
        this.professionFather = professionFather;
    }

    public String getProfessionMother() {
        return professionMother;
    }

    public void setProfessionMother(String professionMother) {
        this.professionMother = professionMother;
    }

    public String getProfessionGuardian() {
        return professionGuardian;
    }

    public void setProfessionGuardian(String professionGuardian) {
        this.professionGuardian = professionGuardian;
    }

    public int getMeter() {
        return meter;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }

    public int getDecimeter() {
        return decimeter;
    }

    public void setDecimeter(int decimeter) {
        this.decimeter = decimeter;
    }

    public int getCentimeter() {
        return centimeter;
    }

    public void setCentimeter(int centimeter) {
        this.centimeter = centimeter;
    }

    public int getMillimeter() {
        return millimeter;
    }

    public void setMillimeter(int millimeter) {
        this.millimeter = millimeter;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getForehead() {
        return forehead;
    }

    public void setForehead(String forehead) {
        this.forehead = forehead;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getNose() {
        return nose;
    }

    public void setNose(String nose) {
        this.nose = nose;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getChin() {
        return chin;
    }

    public void setChin(String chin) {
        this.chin = chin;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getEyebrows() {
        return eyebrows;
    }

    public void setEyebrows(String eyebrows) {
        this.eyebrows = eyebrows;
    }

    public String getNotableSigns() {
        return notableSigns;
    }

    public void setNotableSigns(String notableSigns) {
        this.notableSigns = notableSigns;
    }

    public String getReasonsExcempion() {
        return reasonsExcempion;
    }

    public void setReasonsExcempion(String reasonsExcempion) {
        this.reasonsExcempion = reasonsExcempion;
    }

    public String getReasonsInapplicability() {
        return reasonsInapplicability;
    }

    public void setReasonsInapplicability(String reasonsInapplicability) {
        this.reasonsInapplicability = reasonsInapplicability;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getMotiveExcempion() {
        return motiveExcempion;
    }

    public void setMotiveExcempion(String motiveExcempion) {
        this.motiveExcempion = motiveExcempion;
    }

    public int getNumberRegulationIllness() {
        return numberRegulationIllness;
    }

    public void setNumberRegulationIllness(int numberRegulationIllness) {
        this.numberRegulationIllness = numberRegulationIllness;
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }

    public int getSubstituteNumber() {
        return substituteNumber;
    }

    public void setSubstituteNumber(int substituteNumber) {
        this.substituteNumber = substituteNumber;
    }

    public String getFamilyNameSubstitute() {
        return familyNameSubstitute;
    }

    public void setFamilyNameSubstitute(String familyNameSubstitute) {
        this.familyNameSubstitute = familyNameSubstitute;
    }

    public String getFirstNameSubstitute() {
        return firstNameSubstitute;
    }

    public void setFirstNameSubstitute(String firstNameSubstitute) {
        this.firstNameSubstitute = firstNameSubstitute;
    }

    public String getInNaval() {
        return inNaval;
    }

    public void setInNaval(String inNaval) {
        this.inNaval = inNaval;
    }

    public String getPositionAnnexation() {
        return positionAnnexation;
    }

    public void setPositionAnnexation(String positionAnnexation) {
        this.positionAnnexation = positionAnnexation;
    }

    public int getDayOfAnnexation() {
        return dayOfAnnexation;
    }

    public void setDayOfAnnexation(int dayOfAnnexation) {
        this.dayOfAnnexation = dayOfAnnexation;
    }

    public int getMonthOfAnnexation() {
        return monthOfAnnexation;
    }

    public void setMonthOfAnnexation(int monthOfAnnexation) {
        this.monthOfAnnexation = monthOfAnnexation;
    }

    public int getYearOfAnnexation() {
        return yearOfAnnexation;
    }

    public void setYearOfAnnexation(int yearOfAnnexation) {
        this.yearOfAnnexation = yearOfAnnexation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getByz() {
        return byz;
    }

    public void setByz(String byz) {
        this.byz = byz;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
