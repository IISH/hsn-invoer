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
     * Obtain the milition scan repository.
     *
     * @return The milition scan repository.
     */
    public MilitionScanRepository getMilitionScanRepository() {
        return new MilitionScanRepository(Paths.get(storagePath));
    }
}
