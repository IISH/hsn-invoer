package org.iish.hsn.invoer.domain.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the static attributes of a registration
 */
@Entity
@Table(name = "m1", indexes = {@Index(columnList = "IDNR, VOLG"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Milition extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private int idnr;
    @Column(name = "VOLG", nullable = false) private int seq = 1;

    @Column(name = "JAAR", nullable = false) private int year;
    @Column(name = "JAARKZ", nullable = false) private int yearChoice;
    @Column(name = "TYPE", nullable = false, length = 1) private String type = "";
    @Column(name = "GEMNAAM", nullable = false, length = 50) private String municipality = "";
    @Column(name = "INVNR", nullable = false, length = 10) private String invNumber = "";
    @Column(name = "VOLGREG", nullable = false) private int seqRegister;
    @Column(name = "VOLGWIJS", nullable = false) private int seqRefer;
    @Column(name = "LOTNR", nullable = false) private int drawnNumber;

    @Column(name = "SCANA", nullable = false, length = 255) private String scanA = "";
    @Column(name = "SCANB", nullable = false, length = 255) private String scanB = "";
    @Column(name = "SCANNR", nullable = false, length = 255) private String scanNumber = "";

    @Column(name = "ANM", nullable = false, length = 50) private String familyName = "";
    @Column(name = "VNM", nullable = false, length = 50) private String firstName = "";

    @Column(name = "GBD", nullable = false) private int dayOfBirth;
    @Column(name = "GBM", nullable = false) private int monthOfBirth;
    @Column(name = "GBJ", nullable = false) private int yearOfBirth;

    @Column(name = "GBPLTS", nullable = false, length = 50) private String placeOfBirth = "";
    @Column(name = "GBMDIS", nullable = false, length = 50) private String placeOfMilition = "";
    @Column(name = "WPLTS", nullable = false, length = 50) private String placeOfLiving = "";
    @Column(name = "ODSPLTS", nullable = false, length = 50) private String placeParents = "";
    @Column(name = "VGDPLTS", nullable = false, length = 50) private String placeGuardian = "";

    @Column(name = "OUDERS", nullable = false, length = 1) private String livesWithParents = "";
    @Column(name = "ONDRWS", nullable = false, length = 100) private String education = "";

    @Column(name = "ANVDR", nullable = false, length = 50) private String familyNameFather = "";
    @Column(name = "VNVDR", nullable = false, length = 50) private String firstNameFather = "";
    @Column(name = "ANMDR", nullable = false, length = 50) private String familyNameMother = "";
    @Column(name = "VNMDR", nullable = false, length = 50) private String firstNameMother = "";
    @Column(name = "ANVGD", nullable = false, length = 50) private String familyNameGuardian = "";
    @Column(name = "VNVGD", nullable = false, length = 50) private String firstNameGuardian = "";

    @Column(name = "BRP", nullable = false, length = 100) private String profession = "";
    @Column(name = "BRPVDR", nullable = false, length = 100) private String professionFather = "";
    @Column(name = "BRPMDR", nullable = false, length = 100) private String professionMother = "";
    @Column(name = "BRPVGD", nullable = false, length = 100) private String professionGuardian = "";

    @Column(name = "LGTME", nullable = false) private int meter;
    @Column(name = "LGTDM", nullable = false) private int decimeter;
    @Column(name = "LGTCM", nullable = false) private int centimeter;
    @Column(name = "LGTMM", nullable = false) private int millimeter;

    @Column(name = "GEWICHT", nullable = false) private int kg;
    @Column(name = "IDX", nullable = false, length = 10) private String index = "";

    @Column(name = "SGMGEZ", nullable = false, length = 50) private String face = "";
    @Column(name = "SGMVHF", nullable = false, length = 50) private String forehead = "";
    @Column(name = "SGMOOG", nullable = false, length = 50) private String eyes = "";
    @Column(name = "SGMNEU", nullable = false, length = 50) private String nose = "";
    @Column(name = "SGMMON", nullable = false, length = 50) private String mouth = "";
    @Column(name = "SGMKIN", nullable = false, length = 50) private String chin = "";
    @Column(name = "SGMHAA", nullable = false, length = 50) private String hair = "";
    @Column(name = "SGMWBR", nullable = false, length = 50) private String eyebrows = "";
    @Column(name = "SGMMTK", nullable = false, length = 250) private String notableSigns = "";

    @Column(name = "AANGIFTE", nullable = false) private int declaration;

    @Column(name = "IVWD", nullable = false, length = 1) private String voluntaryService = "";
    @Column(name = "NMREG", nullable = false, length = 200) private String voluntaryWhere = "";
    @Column(name = "DRREG", nullable = false, length = 200) private String voluntaryTime = "";

    @Column(name = "IVRKL", nullable = false, length = 50) private String formerClass = "";
    @Column(name = "RDNLTR", nullable = false, length = 200) private String formerClassReason = "";

    @Column(name = "HFDLST", nullable = false, length = 1) private String headOfList = "";
    @Column(name = "BTNDST", nullable = false, length = 1) private String outStateService = "";

    @Column(name = "INGES", nullable = false, length = 1) private String inGesticht = "";
    @Column(name = "NMGES", nullable = false, length = 200) private String nameGesticht = "";
    @Column(name = "INGEV", nullable = false, length = 1) private String inJail = "";
    @Column(name = "DELICT", nullable = false, length = 200) private String whatCrime = "";

    @Column(name = "EXEMP", nullable = false, length = 200) private String reasonsNotIncluded = "";
    @Column(name = "MILCOM", nullable = false, length = 255) private String militionChairImprovements = "";
    @Column(name = "BEPERK", nullable = false) private int yearsConstraint;

    @Column(name = "RDNVRIJ", nullable = false, length = 100) private String reasonsExemption = "";
    @Column(name = "RDNVRIE", nullable = false, length = 100) private String extraReasonsExemption = "";
    @Column(name = "RDNOU", nullable = false, length = 100) private String earlierDecisions = "";
    @Column(name = "RDNOGS", nullable = false, length = 100) private String reasonsInapplicability = "";
    @Column(name = "ADVIES", nullable = false, length = 100) private String advice = "";
    @Column(name = "EXEMPD", nullable = false) private int dayOfExemptionVerdict;
    @Column(name = "EXEMPM", nullable = false) private int monthOfExemptionVerdict;
    @Column(name = "EXEMPJ", nullable = false) private int yearOfExemptionVerdict;
    @Column(name = "EXEMPU", nullable = false, length = 150) private String exemptionVerdict = "";
    @Column(name = "BRPD", nullable = false) private int dayOfAppeal;
    @Column(name = "BRPM", nullable = false) private int monthOfAppeal;
    @Column(name = "BRPJ", nullable = false) private int yearOfAppeal;

    @Column(name = "MEDD1", nullable = false) private int dayOfMedicalVerdict;
    @Column(name = "MEDM1", nullable = false) private int monthOfMedicalVerdict;
    @Column(name = "MEDJ1", nullable = false) private int yearOfMedicalVerdict;
    @Column(name = "MEDGEB1", nullable = false, length = 100) private String medicalDefects = "";
    @Column(name = "MEDADV1", nullable = false, length = 100) private String medicalAdvice = "";
    @Column(name = "MEDU1", nullable = false, length = 50) private String medicalVerdict = "";
    @Column(name = "MEDREG1", nullable = false, length = 10) private String medicalNumber = "";

    @Column(name = "MEDD2", nullable = false) private int dayOfSecondMedicalVerdict;
    @Column(name = "MEDM2", nullable = false) private int monthOfSecondMedicalVerdict;
    @Column(name = "MEDJ2", nullable = false) private int yearOfSecondMedicalVerdict;
    @Column(name = "MEDGEB2", nullable = false, length = 100) private String secondMedicalDefects = "";
    @Column(name = "MEDADV2", nullable = false, length = 100) private String secondMedicalAdvice = "";
    @Column(name = "MEDU2", nullable = false, length = 50) private String secondMedicalVerdict = "";
    @Column(name = "MEDREG2", nullable = false, length = 10) private String secondMedicalNumber = "";

    @Column(name = "UITSTEL", nullable = false, length = 1) private String delayOfService = "";
    @Column(name = "UITSTELD", nullable = false) private int dayOfDelayVerdict;
    @Column(name = "UITSTELM", nullable = false) private int monthOfDelayVerdict;
    @Column(name = "UITSTELJ", nullable = false) private int yearOfDelayVerdict;
    @Column(name = "PERIOD", nullable = false, length = 50) private String delayInformation = "";
    @Column(name = "BESLUIT", nullable = false, length = 50) private String delayReasons = "";

    @Column(name = "PVNM", nullable = false, length = 1) private String substitute = "";
    @Column(name = "ANPV", nullable = false, length = 50) private String familyNameSubstitute = "";
    @Column(name = "VNPV", nullable = false, length = 50) private String firstNameSubstitute = "";
    @Column(name = "PVNB", nullable = false, length = 10) private String substituteNumber = "";

    @Column(name = "AARDDP", nullable = false, length = 1) private String natureOfService = "";
    @Column(name = "ZEEMIL", nullable = false, length = 1) private String inNaval = "";
    @Column(name = "WENS1", nullable = false, length = 50) private String firstWishOfRecruit = "";
    @Column(name = "WENS2", nullable = false, length = 50) private String secondWishOfRecruit = "";
    @Column(name = "GSCHK1", nullable = false, length = 50) private String firstSuitability = "";
    @Column(name = "GSCHK2", nullable = false, length = 50) private String secondSuitability = "";
    @Column(name = "NGSCHK", nullable = false, length = 50) private String notSuitabile = "";

    @Column(name = "AANWD", nullable = false) private int dayOfDesignation;
    @Column(name = "AANWM", nullable = false) private int monthOfDesignation;
    @Column(name = "AANWJ", nullable = false) private int yearOfDesignation;
    @Column(name = "AANW", nullable = false, length = 50) private String designation = "";

    @Column(name = "INLD", nullable = false) private int dayOfAnnexation;
    @Column(name = "INLM", nullable = false) private int monthOfAnnexation;
    @Column(name = "INLJ", nullable = false) private int yearOfAnnexation;
    @Column(name = "KPINL", nullable = false, length = 255) private String positionAnnexation = "";
    @Column(name = "INLG", nullable = false, length = 50) private String garrisonAnnexation = "";
    @Column(name = "INLT", nullable = false, length = 50) private String annexationPeriod = "";

    @Column(name = "BYZAAN", nullable = false, length = 100) private String byzRemarks = "";
    @Column(name = "AANMRK", nullable = false, length = 255) private String remarks = "";
    @Column(name = "BYZ", nullable = false, length = 1028) private String byz = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public boolean is1815() {
        int year = getYear();
        return (year >= 1815) && (year <= 1861);
    }

    public boolean is1862() {
        int year = getYear();
        return (year >= 1862) && (year <= 1912);
    }

    public boolean is1913() {
        int year = getYear();
        return (year >= 1913) && (year <= 1922);
    }

    public boolean is1923() {
        int year = getYear();
        return (year >= 1923) && (year <= 1941);
    }

    public boolean isOtherYear() {
        return !is1815() && !is1862() && !is1913() && !is1923();
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYearChoice() {
        return yearChoice;
    }

    public void setYearChoice(int yearChoice) {
        this.yearChoice = yearChoice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(String invNumber) {
        this.invNumber = invNumber;
    }

    public int getSeqRegister() {
        return seqRegister;
    }

    public void setSeqRegister(int seqRegister) {
        this.seqRegister = seqRegister;
    }

    public int getSeqRefer() {
        return seqRefer;
    }

    public void setSeqRefer(int seqRefer) {
        this.seqRefer = seqRefer;
    }

    public int getDrawnNumber() {
        return drawnNumber;
    }

    public void setDrawnNumber(int drawnNumber) {
        this.drawnNumber = drawnNumber;
    }

    public String getScanA() {
        return scanA;
    }

    public void setScanA(String scanA) {
        this.scanA = scanA;
    }

    public String getScanB() {
        return scanB;
    }

    public void setScanB(String scanB) {
        this.scanB = scanB;
    }

    public String getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(String scanNumber) {
        this.scanNumber = scanNumber;
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

    public String getPlaceOfMilition() {
        return placeOfMilition;
    }

    public void setPlaceOfMilition(String placeOfMilition) {
        this.placeOfMilition = placeOfMilition;
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

    public String getLivesWithParents() {
        return livesWithParents;
    }

    public void setLivesWithParents(String livesWithParents) {
        this.livesWithParents = livesWithParents;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public int getDeclaration() {
        return declaration;
    }

    public void setDeclaration(int declaration) {
        this.declaration = declaration;
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

    public String getVoluntaryTime() {
        return voluntaryTime;
    }

    public void setVoluntaryTime(String voluntaryTime) {
        this.voluntaryTime = voluntaryTime;
    }

    public String getFormerClass() {
        return formerClass;
    }

    public void setFormerClass(String formerClass) {
        this.formerClass = formerClass;
    }

    public String getFormerClassReason() {
        return formerClassReason;
    }

    public void setFormerClassReason(String formerClassReason) {
        this.formerClassReason = formerClassReason;
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

    public String getInJail() {
        return inJail;
    }

    public void setInJail(String inJail) {
        this.inJail = inJail;
    }

    public String getWhatCrime() {
        return whatCrime;
    }

    public void setWhatCrime(String whatCrime) {
        this.whatCrime = whatCrime;
    }

    public String getReasonsNotIncluded() {
        return reasonsNotIncluded;
    }

    public void setReasonsNotIncluded(String reasonsNotIncluded) {
        this.reasonsNotIncluded = reasonsNotIncluded;
    }

    public String getMilitionChairImprovements() {
        return militionChairImprovements;
    }

    public void setMilitionChairImprovements(String militionChairImprovements) {
        this.militionChairImprovements = militionChairImprovements;
    }

    public int getYearsConstraint() {
        return yearsConstraint;
    }

    public void setYearsConstraint(int yearsConstraint) {
        this.yearsConstraint = yearsConstraint;
    }

    public String getReasonsExemption() {
        return reasonsExemption;
    }

    public void setReasonsExemption(String reasonsExemption) {
        this.reasonsExemption = reasonsExemption;
    }

    public String getExtraReasonsExemption() {
        return extraReasonsExemption;
    }

    public void setExtraReasonsExemption(String extraReasonsExemption) {
        this.extraReasonsExemption = extraReasonsExemption;
    }

    public String getEarlierDecisions() {
        return earlierDecisions;
    }

    public void setEarlierDecisions(String earlierDecisions) {
        this.earlierDecisions = earlierDecisions;
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

    public int getDayOfExemptionVerdict() {
        return dayOfExemptionVerdict;
    }

    public void setDayOfExemptionVerdict(int dayOfExemptionVerdict) {
        this.dayOfExemptionVerdict = dayOfExemptionVerdict;
    }

    public int getMonthOfExemptionVerdict() {
        return monthOfExemptionVerdict;
    }

    public void setMonthOfExemptionVerdict(int monthOfExemptionVerdict) {
        this.monthOfExemptionVerdict = monthOfExemptionVerdict;
    }

    public int getYearOfExemptionVerdict() {
        return yearOfExemptionVerdict;
    }

    public void setYearOfExemptionVerdict(int yearOfExemptionVerdict) {
        this.yearOfExemptionVerdict = yearOfExemptionVerdict;
    }

    public String getExemptionVerdict() {
        return exemptionVerdict;
    }

    public void setExemptionVerdict(String exemptionVerdict) {
        this.exemptionVerdict = exemptionVerdict;
    }

    public int getDayOfAppeal() {
        return dayOfAppeal;
    }

    public void setDayOfAppeal(int dayOfAppeal) {
        this.dayOfAppeal = dayOfAppeal;
    }

    public int getMonthOfAppeal() {
        return monthOfAppeal;
    }

    public void setMonthOfAppeal(int monthOfAppeal) {
        this.monthOfAppeal = monthOfAppeal;
    }

    public int getYearOfAppeal() {
        return yearOfAppeal;
    }

    public void setYearOfAppeal(int yearOfAppeal) {
        this.yearOfAppeal = yearOfAppeal;
    }

    public int getDayOfMedicalVerdict() {
        return dayOfMedicalVerdict;
    }

    public void setDayOfMedicalVerdict(int dayOfMedicalVerdict) {
        this.dayOfMedicalVerdict = dayOfMedicalVerdict;
    }

    public int getMonthOfMedicalVerdict() {
        return monthOfMedicalVerdict;
    }

    public void setMonthOfMedicalVerdict(int monthOfMedicalVerdict) {
        this.monthOfMedicalVerdict = monthOfMedicalVerdict;
    }

    public int getYearOfMedicalVerdict() {
        return yearOfMedicalVerdict;
    }

    public void setYearOfMedicalVerdict(int yearOfMedicalVerdict) {
        this.yearOfMedicalVerdict = yearOfMedicalVerdict;
    }

    public String getMedicalDefects() {
        return medicalDefects;
    }

    public void setMedicalDefects(String medicalDefects) {
        this.medicalDefects = medicalDefects;
    }

    public String getMedicalAdvice() {
        return medicalAdvice;
    }

    public void setMedicalAdvice(String medicalAdvice) {
        this.medicalAdvice = medicalAdvice;
    }

    public String getMedicalVerdict() {
        return medicalVerdict;
    }

    public void setMedicalVerdict(String medicalVerdict) {
        this.medicalVerdict = medicalVerdict;
    }

    public String getMedicalNumber() {
        return medicalNumber;
    }

    public void setMedicalNumber(String medicalNumber) {
        this.medicalNumber = medicalNumber;
    }

    public int getDayOfSecondMedicalVerdict() {
        return dayOfSecondMedicalVerdict;
    }

    public void setDayOfSecondMedicalVerdict(int dayOfSecondMedicalVerdict) {
        this.dayOfSecondMedicalVerdict = dayOfSecondMedicalVerdict;
    }

    public int getMonthOfSecondMedicalVerdict() {
        return monthOfSecondMedicalVerdict;
    }

    public void setMonthOfSecondMedicalVerdict(int monthOfSecondMedicalVerdict) {
        this.monthOfSecondMedicalVerdict = monthOfSecondMedicalVerdict;
    }

    public int getYearOfSecondMedicalVerdict() {
        return yearOfSecondMedicalVerdict;
    }

    public void setYearOfSecondMedicalVerdict(int yearOfSecondMedicalVerdict) {
        this.yearOfSecondMedicalVerdict = yearOfSecondMedicalVerdict;
    }

    public String getSecondMedicalDefects() {
        return secondMedicalDefects;
    }

    public void setSecondMedicalDefects(String secondMedicalDefects) {
        this.secondMedicalDefects = secondMedicalDefects;
    }

    public String getSecondMedicalAdvice() {
        return secondMedicalAdvice;
    }

    public void setSecondMedicalAdvice(String secondMedicalAdvice) {
        this.secondMedicalAdvice = secondMedicalAdvice;
    }

    public String getSecondMedicalVerdict() {
        return secondMedicalVerdict;
    }

    public void setSecondMedicalVerdict(String secondMedicalVerdict) {
        this.secondMedicalVerdict = secondMedicalVerdict;
    }

    public String getSecondMedicalNumber() {
        return secondMedicalNumber;
    }

    public void setSecondMedicalNumber(String secondMedicalNumber) {
        this.secondMedicalNumber = secondMedicalNumber;
    }

    public String getDelayOfService() {
        return delayOfService;
    }

    public void setDelayOfService(String delayOfService) {
        this.delayOfService = delayOfService;
    }

    public int getDayOfDelayVerdict() {
        return dayOfDelayVerdict;
    }

    public void setDayOfDelayVerdict(int dayOfDelayVerdict) {
        this.dayOfDelayVerdict = dayOfDelayVerdict;
    }

    public int getMonthOfDelayVerdict() {
        return monthOfDelayVerdict;
    }

    public void setMonthOfDelayVerdict(int monthOfDelayVerdict) {
        this.monthOfDelayVerdict = monthOfDelayVerdict;
    }

    public int getYearOfDelayVerdict() {
        return yearOfDelayVerdict;
    }

    public void setYearOfDelayVerdict(int yearOfDelayVerdict) {
        this.yearOfDelayVerdict = yearOfDelayVerdict;
    }

    public String getDelayInformation() {
        return delayInformation;
    }

    public void setDelayInformation(String delayInformation) {
        this.delayInformation = delayInformation;
    }

    public String getDelayReasons() {
        return delayReasons;
    }

    public void setDelayReasons(String delayReasons) {
        this.delayReasons = delayReasons;
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute;
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

    public String getSubstituteNumber() {
        return substituteNumber;
    }

    public void setSubstituteNumber(String substituteNumber) {
        this.substituteNumber = substituteNumber;
    }

    public String getInNaval() {
        return inNaval;
    }

    public void setInNaval(String inNaval) {
        this.inNaval = inNaval;
    }

    public String getNatureOfService() {
        return natureOfService;
    }

    public void setNatureOfService(String natureOfService) {
        this.natureOfService = natureOfService;
    }

    public String getFirstWishOfRecruit() {
        return firstWishOfRecruit;
    }

    public void setFirstWishOfRecruit(String firstWishOfRecruit) {
        this.firstWishOfRecruit = firstWishOfRecruit;
    }

    public String getSecondWishOfRecruit() {
        return secondWishOfRecruit;
    }

    public void setSecondWishOfRecruit(String secondWishOfRecruit) {
        this.secondWishOfRecruit = secondWishOfRecruit;
    }

    public String getFirstSuitability() {
        return firstSuitability;
    }

    public void setFirstSuitability(String firstSuitability) {
        this.firstSuitability = firstSuitability;
    }

    public String getSecondSuitability() {
        return secondSuitability;
    }

    public void setSecondSuitability(String secondSuitability) {
        this.secondSuitability = secondSuitability;
    }

    public String getNotSuitabile() {
        return notSuitabile;
    }

    public void setNotSuitabile(String notSuitabile) {
        this.notSuitabile = notSuitabile;
    }

    public int getDayOfDesignation() {
        return dayOfDesignation;
    }

    public void setDayOfDesignation(int dayOfDesignation) {
        this.dayOfDesignation = dayOfDesignation;
    }

    public int getMonthOfDesignation() {
        return monthOfDesignation;
    }

    public void setMonthOfDesignation(int monthOfDesignation) {
        this.monthOfDesignation = monthOfDesignation;
    }

    public int getYearOfDesignation() {
        return yearOfDesignation;
    }

    public void setYearOfDesignation(int yearOfDesignation) {
        this.yearOfDesignation = yearOfDesignation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getPositionAnnexation() {
        return positionAnnexation;
    }

    public void setPositionAnnexation(String positionAnnexation) {
        this.positionAnnexation = positionAnnexation;
    }

    public String getGarrisonAnnexation() {
        return garrisonAnnexation;
    }

    public void setGarrisonAnnexation(String garrisonAnnexation) {
        this.garrisonAnnexation = garrisonAnnexation;
    }

    public String getAnnexationPeriod() {
        return annexationPeriod;
    }

    public void setAnnexationPeriod(String annexationPeriod) {
        this.annexationPeriod = annexationPeriod;
    }

    public String getByzRemarks() {
        return byzRemarks;
    }

    public void setByzRemarks(String byzRemarks) {
        this.byzRemarks = byzRemarks;
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
