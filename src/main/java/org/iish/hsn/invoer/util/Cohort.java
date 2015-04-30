package org.iish.hsn.invoer.util;

/**
 * Represents a cohort.
 */
public class Cohort {
    private int cohortNumber;
    private int startYear;
    private int endYear;

    public int getCohortNumber() {
        return cohortNumber;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    /**
     * Returns the cohort information for the given year.
     *
     * @param year The year in question.
     * @return The cohort for that year.
     */
    public static Cohort getCohortByYear(int year) {
        if (year > 1811) {
            return Cohort.getCohortByNumber(((year - 1813) / 10) + 1);
        }
        return new Cohort();
    }

    /**
     * Returns the cohort information for the given number.
     *
     * @param number The number in question.
     * @return The cohort for that number.
     */
    public static Cohort getCohortByNumber(int number) {
        Cohort cohort = new Cohort();
        if (number == 1) {
            cohort.cohortNumber = 1;
            cohort.startYear = 1812;
            cohort.endYear = 1822;
        }
        else if (number > 0) {
            cohort.cohortNumber = number;
            cohort.startYear = (((number - 1) * 10) + 1813);
            cohort.endYear = cohort.startYear + 9;
        }
        return cohort;
    }
}
