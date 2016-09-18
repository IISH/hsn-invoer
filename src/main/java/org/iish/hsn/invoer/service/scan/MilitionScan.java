package org.iish.hsn.invoer.service.scan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class MilitionScan extends HsnScan {
    private static final String MILITIE_ORG_PATH = "militie/";
    private static final String MILITIE_CROP_PATH = "militie/geknipt/";

    private int idnr;
    private int year;
    private int seq;

    public MilitionScan(Path storage, int idnr, int year, int seq) {
        super(storage.resolve(MILITIE_ORG_PATH), storage.resolve(MILITIE_CROP_PATH));
        this.idnr = idnr;
        this.year = year;
        this.seq = seq;
    }

    @Override
    protected String getCroppedScanPath() {
        return year + "/" + idnr + "." + seq + ".jpg";
    }

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
