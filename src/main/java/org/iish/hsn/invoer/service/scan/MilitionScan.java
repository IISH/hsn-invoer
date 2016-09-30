package org.iish.hsn.invoer.service.scan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Represents a milition scan stored for data entry.
 */
public class MilitionScan extends HsnScan {
    private static final String MILITIE_ORG_PATH = "militie/";

    private int idnr;
    private int year;
    private int seq;

    /**
     * Creates a new representation of a scan.
     *
     * @param storage The path where the scans are stored.
     * @param idnr    The idnr of the person.
     * @param year    The year of the milition scan.
     * @param seq     The sequence number.
     */
    public MilitionScan(Path storage, int idnr, int year, int seq) {
        super(storage.resolve(MILITIE_ORG_PATH));
        this.idnr = idnr;
        this.year = year;
        this.seq = seq;
    }

    /**
     * Find the path to the milition scan.
     *
     * @param root The path to scan.
     * @return The path to the scan.
     * @throws IOException On I/O related errors.
     */
    @Override
    protected Path findScan(Path root) throws IOException {
        Path yearFolder = root.resolve(String.valueOf(year));
        if (Files.exists(yearFolder)) {
            Iterator<Path> files = Files.newDirectoryStream(yearFolder, idnr + "." + seq + ".*").iterator();
            if (files.hasNext()) {
                return files.next();
            }
        }
        return null;
    }
}
