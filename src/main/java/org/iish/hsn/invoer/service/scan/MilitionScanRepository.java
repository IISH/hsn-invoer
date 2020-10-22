package org.iish.hsn.invoer.service.scan;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a repository for storing milition scans for data entry.
 */
public class MilitionScanRepository {
    private static final String MILITIE_ORG_PATH = "militie/";

    // Use a space or underscore _ as separator
    // idnr is mandatory
    // [idnr] [municipality] [year] [type uppercase (one of I/A/L/K/N)] [scan side lowercase (one of a/b)] [scan number].[extension]
    // For example: 12345 Den Haag 1820 A a DG77777.pdf
    private static final Pattern SCAN_PATTERN =
            Pattern.compile("^(\\d+)((\\s|_)([\\p{IsAlphabetic}\\s_]+))?((\\s|_)(\\d{4}))?((\\s|_)([IALKN]))?((\\s|_)([ab]))?((\\s|_)([^\\s_\\.]{2,}))?\\.[a-zA-Z]+$");

    private final Path root;

    /**
     * Creates a new representation of a milition scan repository.
     *
     * @param storage The path where the scans are stored.
     */
    public MilitionScanRepository(Path storage) {
        this.root = storage.resolve(MILITIE_ORG_PATH);
    }

    /**
     * Find the milition scans of the given RP.
     *
     * @param idnr The idnr of the person to search for.
     * @return The scans found.
     * @throws IOException On I/O related errors.
     */
    public Set<MilitionScan> findScans(int idnr) throws IOException {
        Map<String, MilitionScan> scans = new HashMap<>();
        Map<String, MilitionScan> scansAB = new HashMap<>();

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(root, idnr + "[ _]*")) {
            for (Path path : dirStream) {
                String filename = path.getFileName().toString();

                Matcher matcher = SCAN_PATTERN.matcher(filename);
                if (matcher.matches()) {
                    setScan(scans, scansAB, idnr, path, matcher);
                }
            }
        }
        catch (NoSuchFileException nsfe) {
            // TODO: Ignore for now
        }

        return new HashSet<>(scans.values());
    }

    /**
     * Determine for the given set of entered militions whether there is a scan available not yet entered.
     *
     * @param idnr             The idnr of the person to search for.
     * @param enteredMilitions The list with entered militions.
     * @return A scan not yet entered, or null if no scan was found.
     * @throws IOException On I/O related errors.
     */
    public MilitionScan findScanNotEntered(int idnr, List<Milition> enteredMilitions) throws IOException {
        for (MilitionScan scan : findScans(idnr)) {
            boolean scanEnteredBefore = false;

            for (Milition milition : enteredMilitions) {
                if (milition.getScanA().equals(scan.getSideA().getFileName().toString())) {
                    scanEnteredBefore = true;
                    break;
                }
            }

            if (!scanEnteredBefore)
                return scan;
        }
        return null;
    }

    /**
     * Set the milition scan info for the current given scan. (Has all info)
     *
     * @param scans   The collected scans.
     * @param scansAB The collected scans which may have a B side as well.
     * @param idnr    The idnr of the RP.
     * @param path    The scan file.
     * @param matcher The matcher of the information.
     */
    private void setScan(Map<String, MilitionScan> scans, Map<String, MilitionScan> scansAB,
                         int idnr, Path path, Matcher matcher) {
        if (Integer.parseInt(matcher.group(1)) == idnr) {
            String municipality = (matcher.groupCount() >= 4) ? matcher.group(4) : null;
            if (municipality != null) municipality = municipality.replace('_', ' ');
            Integer year = (matcher.groupCount() >= 7) ? Integer.parseInt(matcher.group(7)) : null;
            String type = (matcher.groupCount() >= 10) ? matcher.group(10) : null;
            String side = (matcher.groupCount() >= 13) ? matcher.group(13) : null;
            String number = (matcher.groupCount() >= 16) ? matcher.group(16) : null;

            String hashCode = computeHashCode(municipality, year, type, null);
            Path sideA = null, sideB = null;
            if (scansAB.containsKey(hashCode)) {
                Scan scan = scansAB.get(hashCode);
                sideA = scan.getSideA();
                sideB = scan.getSideB();
            }

            if ((side == null) || side.equalsIgnoreCase("a"))
                sideA = path;
            else
                sideB = path;

            MilitionScan scan = new MilitionScan(sideA, sideB, idnr, municipality, year, type, number);
            scansAB.put(hashCode, scan);
            scans.put(computeHashCode(municipality, year, type, number), scan);
        }
    }

    /**
     * Computes the hash code of scan information.
     *
     * @param municipality The municipality of the register.
     * @param year         The year of the register.
     * @param type         The type of the register.
     * @param number       The scan number.
     * @return The computed hash code.
     */
    private static String computeHashCode(String municipality, Integer year, String type, String number) {
        int hashCode = 1;
        if (municipality != null)
            hashCode = 31 * hashCode + municipality.hashCode();
        if (year != null)
            hashCode = 31 * hashCode + year;
        if (type != null)
            hashCode = 31 * hashCode + type.hashCode();
        if (number != null)
            hashCode = 31 * hashCode + number.hashCode();
        return String.valueOf(hashCode);
    }
}
