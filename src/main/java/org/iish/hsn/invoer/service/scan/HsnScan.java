package org.iish.hsn.invoer.service.scan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class HsnScan {
    protected Path mainScansStorage;
    protected Path croppedScansStorage;

    public HsnScan(Path mainScansStorage, Path croppedScansStorage) {
        this.mainScansStorage = mainScansStorage;
        this.croppedScansStorage = croppedScansStorage;
    }

    public Path getScan() throws IOException {
        Path croppedImage = getCroppedScan();
        return (croppedImage == null) ? getMainScan() : croppedImage;
    }

    public Path getMainScan() throws IOException {
        return findScan(mainScansStorage);
    }

    public Path getCroppedScan() throws IOException {
        return findScan(croppedScansStorage);
    }

    public void saveCroppedScan(byte[] croppedScan) throws IOException {
        Path croppedScanPath = croppedScansStorage.resolve(getCroppedScanPath());
        Files.createDirectories(croppedScanPath.getParent());
        Files.write(croppedScanPath, croppedScan);
    }

    protected abstract String getCroppedScanPath();

    protected abstract Path findScan(Path root) throws IOException;
}
