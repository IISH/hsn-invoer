package org.iish.hsn.invoer.service.scan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.nio.file.Path;

/**
 * Representation of a stored scan.
 */
public class Scan {
    private @JsonIgnore Path sideA;
    private @JsonIgnore Path sideB;

    /**
     * Creates a new link to a stored scan.
     *
     * @param sideA The A side.
     * @param sideB The B side.
     */
    public Scan(Path sideA, Path sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }

    /**
     * Get the path to the A side.
     *
     * @return The path.
     */
    public Path getSideA() {
        return sideA;
    }

    /**
     * Get the path to the B side.
     *
     * @return The path.
     */
    public Path getSideB() {
        return sideB;
    }
}
