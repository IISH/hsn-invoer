package org.iish.hsn.invoer.service.scan;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Represents a scan stored for data entry.
 */
public abstract class HsnScan {
    private Path scansStorage;

    /**
     * Creates a new representation of a scan.
     *
     * @param scansStorage The path where the scans are stored.
     */
    public HsnScan(Path scansStorage) {
        this.scansStorage = scansStorage;
    }

    /**
     * Obtain the path to the scan.
     *
     * @return The path to the scan.
     * @throws IOException On I/O related errors.
     */
    public Path getScan() throws IOException {
        return findScan(scansStorage);
    }

    /**
     * Find the path to the scan.
     *
     * @param root The path to scan.
     * @return The path to the scan.
     * @throws IOException On I/O related errors.
     */
    protected abstract Path findScan(Path root) throws IOException;
}
