package org.iish.hsn.invoer.service.scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class ScansService {
    @Value("${storage.path}")
    private String storagePath;

    public MilitionScan getMilitionScan(int idnr, int year, int seq) {
        return new MilitionScan(Paths.get(storagePath), idnr, year, seq);
    }
}
