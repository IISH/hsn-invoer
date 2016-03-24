package org.iish.hsn.invoer.domain.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This class contains data of one RP row of reference data.
 */
@Entity
@Table(name = "HSNRP")
public class Ref_RP implements Serializable {
    @Id @Column(name = "Id") private int id;

    @Column(name = "IDNR", nullable = false) private      int idnr;
    @Column(name = "ID_origin", nullable = false) private int idOrigin;

    @Column(name = "Project", nullable = false) private String project;

    @Column(name = "Gemnr", nullable = false) private int numberMunicipality;

    @Column(name = "Valid_day", nullable = false) private   int validDay;
    @Column(name = "Valid_month", nullable = false) private int validMonth;
    @Column(name = "Valid_year", nullable = false) private  int validYear;

    @Column(name = "RP_family", nullable = false) private    String lastName;
    @Column(name = "RP_prefix", nullable = false) private    String prefixName;
    @Column(name = "RP_firstname", nullable = false) private String firstName;

    @Column(name = "RP_B_DAY", nullable = false) private   int dayOfBirth;
    @Column(name = "RP_B_MONTH", nullable = false) private int monthOfBirth;
    @Column(name = "RP_B_YEAR", nullable = false) private  int yearOfBirth;

    @Column(name = "RP_B_SEX", nullable = false) private   String sex;
    @Column(name = "RP_B_PLACE", nullable = false) private String nameMunicipality;
    @Column(name = "RP_B_PROV", nullable = false) private  int    cohortNumber;

    @Column(name = "MO_family", nullable = false) private    String lastNameMother;
    @Column(name = "MO_prefix", nullable = false) private    String prefixMother;
    @Column(name = "MO_firstname", nullable = false) private String firstNameMother;

    @Column(name = "FA_family", nullable = false) private    String lastNameFather;
    @Column(name = "FA_prefix", nullable = false) private    String prefixFather;
    @Column(name = "FA_firstname", nullable = false) private String firstNameFather;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getIdOrigin() {
        return idOrigin;
    }

    public void setIdOrigin(int idOrigin) {
        this.idOrigin = idOrigin;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public int getNumberMunicipality() {
        return numberMunicipality;
    }

    public void setNumberMunicipality(int numberMunicipality) {
        this.numberMunicipality = numberMunicipality;
    }

    public int getValidDay() {
        return validDay;
    }

    public void setValidDay(int validDay) {
        this.validDay = validDay;
    }

    public int getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(int validMonth) {
        this.validMonth = validMonth;
    }

    public int getValidYear() {
        return validYear;
    }

    public void setValidYear(int validYear) {
        this.validYear = validYear;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNameMunicipality() {
        return nameMunicipality;
    }

    public void setNameMunicipality(String nameMunicipality) {
        this.nameMunicipality = nameMunicipality;
    }

    public int getCohortNumber() {
        return cohortNumber;
    }

    public void setCohortNumber(int cohortNumber) {
        this.cohortNumber = cohortNumber;
    }

    public String getLastNameMother() {
        return lastNameMother;
    }

    public void setLastNameMother(String lastNameMother) {
        this.lastNameMother = lastNameMother;
    }

    public String getPrefixMother() {
        return prefixMother;
    }

    public void setPrefixMother(String prefixMother) {
        this.prefixMother = prefixMother;
    }

    public String getFirstNameMother() {
        return firstNameMother;
    }

    public void setFirstNameMother(String firstNameMother) {
        this.firstNameMother = firstNameMother;
    }

    public String getLastNameFather() {
        return lastNameFather;
    }

    public void setLastNameFather(String lastNameFather) {
        this.lastNameFather = lastNameFather;
    }

    public String getPrefixFather() {
        return prefixFather;
    }

    public void setPrefixFather(String prefixFather) {
        this.prefixFather = prefixFather;
    }

    public String getFirstNameFather() {
        return firstNameFather;
    }

    public void setFirstNameFather(String firstNameFather) {
        this.firstNameFather = firstNameFather;
    }
}
