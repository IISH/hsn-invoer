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
    private String number;

    /**
     * Creates a new link to a stored scan.
     *
     * @param sideA        The A side.
     * @param sideB        The B side.
     * @param idnr         The idnr of the RP.
     * @param municipality The municipality.
     * @param year         The year of the register.
     * @param type         The type of the register.
     * @param number       The number of the register.
     */
    public MilitionScan(Path sideA, Path sideB, int idnr, String municipality, Integer year,
                        String type, String number) {
        super(sideA, sideB);
        this.idnr = idnr;
        this.municipality = municipality;
        this.year = year;
        this.type = type;
        this.number = number;
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

    public String getNumber() {
        return number;
    }
}
