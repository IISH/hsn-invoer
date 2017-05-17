package org.iish.hsn.invoer.service.scan;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a repository for storing milition scans for data entry.
 */
public class MilitionScanRepository {
    private static final String MILITIE_ORG_PATH = "militie/";

    // [idnr]_[scannummer].[extensie]
    // [idnr]_[scanside]_[scannummer].[extensie]
    // 12345_a_scana.jpg
    private static final Pattern NO_INFO_PATTERN =
            Pattern.compile("^(\\d+)(_([ABab]))?_(\\w+)\\.[a-zA-Z]+$");

    // [idnr] [gemeentenaam] [jaar].[extensie]
    // [idnr] [gemeentenaam] [jaar] [scanside lowercase].[extensie]
    // [idnr] [gemeentenaam] [jaar] [type uppercase].[extensie]
    // [idnr] [gemeentenaam] [jaar] [type uppercase] [scanside lowercase].[extensie]
    // 12345 Den Haag 1820 A a.pdf
    private static final Pattern INFO_PATTERN =
            Pattern.compile("^(\\d+)\\s([\\p{IsAlphabetic}\\s]+)\\s(\\d{4})(\\s([A-Z]))?(\\s([ab]))?\\.[a-zA-Z]+$");

    private Path root;

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

        for (Path path : Files.newDirectoryStream(root, idnr + "[ _]*")) {
            String filename = path.getFileName().toString();

            Matcher matcher = INFO_PATTERN.matcher(filename);
            if (matcher.matches()) {
                setScanWithInfo(scans, idnr, path, matcher);
            }

            matcher = NO_INFO_PATTERN.matcher(filename);
            if (matcher.matches()) {
                setScanWithoutInfo(scans, idnr, path, matcher);
            }
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
     * Set the milition scan info for the current given scan. (Has little info)
     *
     * @param scans   The collected scans.
     * @param idnr    The idnr of the RP.
     * @param path    The scan file.
     * @param matcher The matcher of the information.
     */
    private void setScanWithoutInfo(Map<String, MilitionScan> scans, int idnr, Path path, Matcher matcher) {
        if (Integer.parseInt(matcher.group(1)) == idnr) {
            String scanId = matcher.group(4);
            Path sideA = null, sideB = null;
            if (scans.containsKey(scanId)) {
                Scan scan = scans.get(scanId);
                sideA = scan.getSideA();
                sideB = scan.getSideB();
            }

            if ((matcher.group(3) == null) || matcher.group(3).equalsIgnoreCase("A"))
                sideA = path;
            else
                sideB = path;

            scans.put(scanId, new MilitionScan(sideA, sideB, idnr));
        }
    }

    /**
     * Set the milition scan info for the current given scan. (Has all info)
     *
     * @param scans   The collected scans.
     * @param idnr    The idnr of the RP.
     * @param path    The scan file.
     * @param matcher The matcher of the information.
     */
    private void setScanWithInfo(Map<String, MilitionScan> scans, int idnr, Path path, Matcher matcher) {
        if (Integer.parseInt(matcher.group(1)) == idnr) {
            String municipality = matcher.group(2);
            int year = Integer.parseInt(matcher.group(3));
            String type = matcher.group(5);

            int hashCode = 1;
            if (municipality != null)
                hashCode = 31 * hashCode + municipality.hashCode();
            hashCode = 31 * hashCode + year;
            if (type != null)
                hashCode = 31 * hashCode + type.hashCode();
            String hashCodeStr = String.valueOf(hashCode);

            Path sideA = null, sideB = null;
            if (scans.containsKey(hashCodeStr)) {
                Scan scan = scans.get(hashCodeStr);
                sideA = scan.getSideA();
                sideB = scan.getSideB();
            }

            if ((matcher.group(7) == null) || matcher.group(7).equalsIgnoreCase("A"))
                sideA = path;
            else
                sideB = path;

            scans.put(hashCodeStr, new MilitionScan(sideA, sideB, idnr, municipality, year, type));
        }
    }
}
