package org.iish.hsn.invoer.util;

/**
 * Simple utility methods to prevent against NULL values.
 */
public class NullSafeUtils {

    /**
     * Prevent against NULL integers.
     *
     * @param input The input integer.
     * @return The output integer.
     */
    public static int getInteger(Integer input) {
        return (input == null) ? 0 : input;
    }

    /**
     * Prevent against NULL strings.
     *
     * @param input The input string.
     * @return The output string.
     */
    public static String getString(String input) {
        return (input == null) ? "" : input;
    }
}
