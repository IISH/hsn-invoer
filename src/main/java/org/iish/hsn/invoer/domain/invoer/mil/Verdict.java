package org.iish.hsn.invoer.domain.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the static attributes of a verdict
 */
@Entity
@Table(name = "m2", indexes = {@Index(columnList = "IDNR, VOLG, TYPE"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Verdict extends Invoer implements Serializable {
    public enum Type {
        UITSTEL(1), TWEEDE_UITSTEL(2), BEZWAREN(3), WET(4), KONING(5);

        private int type;

        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static Type getType(int type) {
            for (Type t : Type.values()) {
                if (t.getType() == type) {
                    return t;
                }
            }
            return null;
        }
    }

    @Column(name = "IDNR", nullable = false) private int idnr;
    @Column(name = "VOLG", nullable = false) private int seq;

    @Column(name = "TYPE", nullable = false) private int type;
    @Column(name = "AARD", nullable = false, length = 50) private String nature = "";

    @Column(name = "USPRD", nullable = false) private int dayOfVerdict;
    @Column(name = "USPRM", nullable = false) private int monthOfVerdict;
    @Column(name = "USPRJ", nullable = false) private int yearOfVerdict;

    @Column(name = "NRUSPR", nullable = false, length = 10) private String numberVerdict = "";
    @Column(name = "NRREG", nullable = false, length = 10) private String numberRegulation = "";

    @Column(name = "APPLD", nullable = false) private int dayOfAppeal;
    @Column(name = "APPLM", nullable = false) private int monthOfAppeal;
    @Column(name = "APPLJ", nullable = false) private int yearOfAppeal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Verdict() {
    }

    public Verdict(int type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public int getDayOfVerdict() {
        return dayOfVerdict;
    }

    public void setDayOfVerdict(int dayOfVerdict) {
        this.dayOfVerdict = dayOfVerdict;
    }

    public int getMonthOfVerdict() {
        return monthOfVerdict;
    }

    public void setMonthOfVerdict(int monthOfVerdict) {
        this.monthOfVerdict = monthOfVerdict;
    }

    public int getYearOfVerdict() {
        return yearOfVerdict;
    }

    public void setYearOfVerdict(int yearOfVerdict) {
        this.yearOfVerdict = yearOfVerdict;
    }

    public String getNumberVerdict() {
        return numberVerdict;
    }

    public void setNumberVerdict(String numberVerdict) {
        this.numberVerdict = numberVerdict;
    }

    public String getNumberRegulation() {
        return numberRegulation;
    }

    public void setNumberRegulation(String numberRegulation) {
        this.numberRegulation = numberRegulation;
    }

    public int getDayOfAppeal() {
        return dayOfAppeal;
    }

    public void setDayOfAppeal(int dayOfAppeal) {
        this.dayOfAppeal = dayOfAppeal;
    }

    public int getMonthOfAppeal() {
        return monthOfAppeal;
    }

    public void setMonthOfAppeal(int monthOfAppeal) {
        this.monthOfAppeal = monthOfAppeal;
    }

    public int getYearOfAppeal() {
        return yearOfAppeal;
    }

    public void setYearOfAppeal(int yearOfAppeal) {
        this.yearOfAppeal = yearOfAppeal;
    }
}
