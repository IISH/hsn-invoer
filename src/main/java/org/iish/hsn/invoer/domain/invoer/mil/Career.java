package org.iish.hsn.invoer.domain.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class handles the attributes of a career history of a milition registry
 */
@Entity
@Table(name = "m3", indexes = {@Index(columnList = "IDNR, VOLG, TYPE, VOLG2"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Career extends Invoer implements Serializable {
    public enum Type {
        BEROEP(1), ONDERWIJS(2);

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
    @Column(name = "VOLG2", nullable = false) private int seq2;

    @Column(name = "WAARDE", nullable = false, length = 254) private String value = "";

    @Column(name = "DAG", nullable = false) private int day;
    @Column(name = "MAAND", nullable = false) private int month;
    @Column(name = "JAAR", nullable = false) private int year;

    @Column(name = "JAREN", nullable = false) private int numberOfYears;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Career() {
    }

    public Career(int type, int seq2) {
        this.type = type;
        this.seq2 = seq2;
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

    public int getSeq2() {
        return seq2;
    }

    public void setSeq2(int seq2) {
        this.seq2 = seq2;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }
}
