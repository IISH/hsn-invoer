package org.iish.hsn.invoer.service.scan;

import java.nio.file.Path;

/**
 * Representation of a stored milition scan.
 */
public class MilitionScan extends Scan {
    private int idnr;
    private String municipality;
    private Integer year;
    private String type;

    /**
     * Creates a new link to a stored scan.
     *
     * @param sideA The A side.
     * @param sideB The B side.
     * @param idnr  The idnr of the RP.
     */
    public MilitionScan(Path sideA, Path sideB, int idnr) {
        super(sideA, sideB);
        this.idnr = idnr;
    }

    /**
     * Creates a new link to a stored scan.
     *
     * @param sideA        The A side.
     * @param sideB        The B side.
     * @param idnr         The idnr of the RP.
     * @param municipality The municipality.
     * @param year         The year of the register.
     * @param type         The type of the register.
     */
    public MilitionScan(Path sideA, Path sideB, int idnr, String municipality, Integer year, String type) {
        super(sideA, sideB);
        this.idnr = idnr;
        this.municipality = municipality;
        this.year = year;
        this.type = type;
    }

    public int getIdnr() {
        return idnr;
    }

    public String getMunicipality() {
        return municipality;
    }

    public Integer getYear() {
        return year;
    }

    public String getType() {
        return type;
    }
}
