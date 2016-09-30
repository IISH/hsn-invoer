package org.iish.hsn.invoer.service.scan;

import java.io.IOException;
import java.nio.file.Path;

public abstract class HsnScan {
    protected Path scansStorage;

    public HsnScan(Path scansStorage) {
        this.scansStorage = scansStorage;
    }

    public Path getScan() throws IOException {
        return findScan(scansStorage);
    }

    protected abstract Path findScan(Path root) throws IOException;
}
