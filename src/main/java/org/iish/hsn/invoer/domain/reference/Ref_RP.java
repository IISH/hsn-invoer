package org.iish.hsn.invoer.domain.reference;

import org.iish.hsn.invoer.util.NullSafeUtils;

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
    @Id
    @Column(name = "Id")
    private int id;

    @Column(name = "IDNR") private      Integer idnr;
    @Column(name = "ID_origin") private Integer idOrigin;

    @Column(name = "Project") private String project;

    @Column(name = "Gemnr") private Integer numberMunicipality;

    @Column(name = "Valid_day") private   Integer validDay;
    @Column(name = "Valid_month") private Integer validMonth;
    @Column(name = "Valid_year") private  Integer validYear;

    @Column(name = "RP_family") private    String lastName;
    @Column(name = "RP_prefix") private    String prefixName;
    @Column(name = "RP_firstname") private String firstName;

    @Column(name = "RP_B_DAY") private   Integer dayOfBirth;
    @Column(name = "RP_B_MONTH") private Integer monthOfBirth;
    @Column(name = "RP_B_YEAR") private  Integer yearOfBirth;

    @Column(name = "RP_B_SEX") private   String  sex;
    @Column(name = "RP_B_PLACE") private String  nameMunicipality;
    @Column(name = "RP_B_PROV") private  Integer cohortNumber;

    @Column(name = "MO_family") private    String lastNameMother;
    @Column(name = "MO_prefix") private    String prefixMother;
    @Column(name = "MO_firstname") private String firstNameMother;

    @Column(name = "FA_family") private    String lastNameFather;
    @Column(name = "FA_prefix") private    String prefixFather;
    @Column(name = "FA_firstname") private String firstNameFather;

    public int getId() {
        return id;
    }

    public int getIdnr() {
        return NullSafeUtils.getInteger(idnr);
    }

    public int getIdOrigin() {
        return NullSafeUtils.getInteger(idOrigin);
    }

    public String getProject() {
        return NullSafeUtils.getString(project);
    }

    public int getNumberMunicipality() {
        return NullSafeUtils.getInteger(numberMunicipality);
    }

    public int getValidDay() {
        return NullSafeUtils.getInteger(validDay);
    }

    public int getValidMonth() {
        return NullSafeUtils.getInteger(validMonth);
    }

    public int getValidYear() {
        return NullSafeUtils.getInteger(validYear);
    }

    public String getLastName() {
        return NullSafeUtils.getString(lastName);
    }

    public String getPrefixName() {
        return NullSafeUtils.getString(prefixName);
    }

    public String getPrefixLastName() {
        String prefixLastName = getLastName();
        if (!getPrefixName().isEmpty())
            return prefixLastName + ", " + getPrefixName();
        return prefixLastName;
    }

    public String getFirstName() {
        return NullSafeUtils.getString(firstName);
    }

    public int getDayOfBirth() {
        return NullSafeUtils.getInteger(dayOfBirth);
    }

    public int getMonthOfBirth() {
        return NullSafeUtils.getInteger(monthOfBirth);
    }

    public int getYearOfBirth() {
        return NullSafeUtils.getInteger(yearOfBirth);
    }

    public String getSex() {
        return NullSafeUtils.getString(sex);
    }

    public String getNameMunicipality() {
        return NullSafeUtils.getString(nameMunicipality);
    }

    public int getCohortNumber() {
        return NullSafeUtils.getInteger(cohortNumber);
    }

    public String getLastNameMother() {
        return NullSafeUtils.getString(lastNameMother);
    }

    public String getPrefixMother() {
        return NullSafeUtils.getString(prefixMother);
    }

    public String getPrefixLastNameMother() {
        String prefixLastName = getLastNameMother();
        if (!getPrefixMother().isEmpty())
            return prefixLastName + ", " + getPrefixMother();
        return prefixLastName;
    }

    public String getFirstNameMother() {
        return NullSafeUtils.getString(firstNameMother);
    }

    public String getLastNameFather() {
        return NullSafeUtils.getString(lastNameFather);
    }

    public String getPrefixFather() {
        return NullSafeUtils.getString(prefixFather);
    }

    public String getPrefixLastNameFather() {
        String prefixLastName = getLastNameFather();
        if (!getPrefixFather().isEmpty())
            return prefixLastName + ", " + getPrefixFather();
        return prefixLastName;
    }

    public String getFirstNameFather() {
        return NullSafeUtils.getString(firstNameFather);
    }
}
