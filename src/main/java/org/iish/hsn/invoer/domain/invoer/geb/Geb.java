package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "geb") // Is actually not a table, but a view
public class Geb extends Invoer {
    @Embeddable
    public static class GebId implements Serializable {
        @Column(name = "ID", nullable = false) private       Integer id;
        @Column(name = "GEBKODE", nullable = false) private  long    gebkode; // long, as MySQL turns this into a bigint

        public Integer getId() {
            return id;
        }

        public long getGebkode() {
            return gebkode;
        }
    }

    @Column(name = "GEMNR", nullable = false) private    int    gemnr;
    @Column(name = "GEMNAAM", nullable = false) private  String gemnaam = "";
    @Column(name = "JAAR", nullable = false) private     int    jaar;
    @Column(name = "AKTENR", nullable = false) private   int    aktenr;
    @Column(name = "IDNR", nullable = false) private     int    idnr;
    @Column(name = "OVERSAMP", nullable = false) private String oversamp = "";

    @EmbeddedId private GebId id;

    public int getGemnr() {
        return gemnr;
    }

    public String getGemnaam() {
        return gemnaam;
    }

    public int getJaar() {
        return jaar;
    }

    public int getAktenr() {
        return aktenr;
    }

    public int getIdnr() {
        return idnr;
    }

    public String getOversamp() {
        return oversamp;
    }

    public GebId getId() {
        return id;
    }
}
