package org.iish.hsn.invoer.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility methods.
 */
public class Utils {

    /**
     * Removes extra information from the last name.
     *
     * @param lastName The last name.
     * @return A cleaned version of the last name.
     */
    public static String getCleanedLastName(String lastName) {
        String[] lastNameSplit = lastName.split("\\$");
        lastNameSplit = lastNameSplit[0].split("!");
        return lastNameSplit[0].trim();
    }

    /**
     * Combines the last name and the 'tussenvoegsel'.
     *
     * @param lastName The last name.
     * @param tusvgl   The 'tussenvoegsel'.
     * @return The complete last name.
     */
    public static String getCompleteLastName(String lastName, String tusvgl) {
        String achternaam = lastName.trim();
        if ((tusvgl != null) && !tusvgl.trim().isEmpty()) {
            achternaam += ", " + tusvgl.trim();
        }
        return achternaam;
    }

    /**
     * Combines all of the first names.
     *
     * @param firstName1 First name part 1.
     * @param firstName2 First name part 2.
     * @param firstName3 First name part 3.
     * @return All first names together.
     */
    public static String getFirstNames(String firstName1, String firstName2, String firstName3) {
        String voornamen = firstName1.trim();
        if ((firstName2 != null) && !firstName2.trim().isEmpty()) {
            voornamen += " " + firstName2.trim();
        }
        if ((firstName3 != null) && !firstName3.trim().isEmpty()) {
            voornamen += " " + firstName3.trim();
        }
        return voornamen;
    }

    /**
     * Divides the first names up into three parts.
     *
     * @param firstName First name.
     * @return First names seperated in three parts.
     */
    public static String[] getFirstNames(String firstName) {
        String[] firstNames = firstName.split("\\s", 3);
        firstNames = Arrays.copyOf(firstNames, 3);
        for (int i = 0; i < 3; i++) {
            if (firstNames[i] == null) {
                firstNames[i] = "";
            }
        }
        return firstNames;
    }

    /**
     * Returns the complete name.
     *
     * @param lastName   The last name.
     * @param tusvgl     The 'tussenvoegsel'.
     * @param firstName1 First name part 1.
     * @param firstName2 First name part 2.
     * @param firstName3 First name part 3.
     * @return The complete name.
     */
    public static String getCompleteName(String lastName, String tusvgl, String firstName1, String firstName2,
                                         String firstName3) {
        String completeName = tusvgl.trim() + " " + lastName.trim() + ", " +
                              getFirstNames(firstName1, firstName2, firstName3);
        return completeName.trim();
    }

    /**
     * Returns the age as a string.
     *
     * @param days   The number of days.
     * @param weeks  The number of weeks.
     * @param months The number of months.
     * @param years  The number of years.
     * @return The age as a string.
     */
    public static String getAgeAsString(int days, int weeks, int months, int years) {
        List<String> parts = new ArrayList<>();
        if (days > 0) {
            parts.add(days + " dagen");
        }
        if (weeks > 0) {
            parts.add(weeks + " weken");
        }
        if (months > 0) {
            parts.add(months + " mndn");
        }
        if (years > 0) {
            parts.add(years + " jaren");
        }
        return StringUtils.collectionToDelimitedString(parts, " ");
    }

    /**
     * Returns the date as a string.
     *
     * @param day   The day.
     * @param month The month.
     * @param year  The year.
     * @return The date as a string.
     */
    public static String getDateAsString(int day, int month, int year) {
        return day + "-" + month + "-" + year;
    }
}
