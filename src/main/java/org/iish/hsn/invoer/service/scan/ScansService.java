package org.iish.hsn.invoer.service.scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * Service that helps with stored scans.
 */
@Service
public class ScansService {
    @Value("${storage.path}")
    private String storagePath;

    /**
     * Obtain the milition scan from storage.
     *
     * @param idnr The idnr of the person.
     * @param year The year of the scan.
     * @param seq  The sequence number.
     * @return The scan if found.
     */
    public MilitionScan getMilitionScan(int idnr, int year, int seq) {
        return new MilitionScan(Paths.get(storagePath), idnr, year, seq);
    }
}
