package org.iish.hsn.invoer.domain.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gebkant",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class Gebkant extends Invoer implements Serializable {
    @Column(name = "IDNR", nullable = false) private                  int    idnr;
    @Column(name = "KANTTYPE", nullable = false) private              int    kanttype;
    @Column(name = "KANTDAG", nullable = false) private               int    kantdag;
    @Column(name = "KANTMND", nullable = false) private               int    kantmnd;
    @Column(name = "KANTJR", nullable = false) private                int    kantjr;
    @Column(name = "KHUWDAG", nullable = false) private               int    khuwdag;
    @Column(name = "KHUWMND", nullable = false) private               int    khuwmnd;
    @Column(name = "KHUWJR", nullable = false) private                int    khuwjr;
    @Column(name = "KHUWGEM", nullable = false, length = 50) private  String khuwgem = "";
    @Column(name = "KHUWANR", nullable = false, length = 50) private  String khuwanr = "";
    @Column(name = "KANMVAD", nullable = false, length = 50) private  String kanmvad = "";
    @Column(name = "KTUSVAD", nullable = false, length = 10) private  String ktusvad = "";
    @Column(name = "KVRN1VAD", nullable = false, length = 20) private String kvrn1vad = "";
    @Column(name = "KVRN2VAD", nullable = false, length = 20) private String kvrn2vad = "";
    @Column(name = "KVRN3VAD", nullable = false, length = 30) private String kvrn3vad = "";
    @Column(name = "KWYZDAG", nullable = false) private               int    kwyzdag;
    @Column(name = "KWYZMND", nullable = false) private               int    kwyzmnd;
    @Column(name = "KWYZJR", nullable = false) private                int    kwyzjr;
    @Column(name = "KWYZKB", nullable = false) private                int    kwyzkb;
    @Column(name = "KWYZSTBL", nullable = false) private              int    kwyzstbl;
    @Column(name = "KGMRB", nullable = false, length = 50) private    String kgmrb = "";
    @Column(name = "KGMERK", nullable = false, length = 50) private   String kgmerk = "";
    @Column(name = "KWGMMR", nullable = false, length = 50) private   String kwgmmr = "";
    @Column(name = "KBRPMR", nullable = false, length = 50) private   String kbrpmr = "";
    @Column(name = "KANMGEB", nullable = false, length = 50) private  String kanmgeb = "";
    @Column(name = "KVRN1GEB", nullable = false, length = 20) private String kvrn1geb = "";
    @Column(name = "KVRN2GEB", nullable = false, length = 20) private String kvrn2geb = "";
    @Column(name = "KVRN3GEB", nullable = false, length = 30) private String kvrn3geb = "";
    @Column(name = "KTUSGEB", nullable = false, length = 10) private  String ktusgeb = "";
    @Column(name = "KSEXGEB", nullable = false, length = 1) private   String ksexgeb = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getKanttype() {
        return kanttype;
    }

    public void setKanttype(int kanttype) {
        this.kanttype = kanttype;
    }

    public int getKantdag() {
        return kantdag;
    }

    public void setKantdag(int kantdag) {
        this.kantdag = kantdag;
    }

    public int getKantmnd() {
        return kantmnd;
    }

    public void setKantmnd(int kantmnd) {
        this.kantmnd = kantmnd;
    }

    public int getKantjr() {
        return kantjr;
    }

    public void setKantjr(int kantjr) {
        this.kantjr = kantjr;
    }

    public int getKhuwdag() {
        return khuwdag;
    }

    public void setKhuwdag(int khuwdag) {
        this.khuwdag = khuwdag;
    }

    public int getKhuwmnd() {
        return khuwmnd;
    }

    public void setKhuwmnd(int khuwmnd) {
        this.khuwmnd = khuwmnd;
    }

    public int getKhuwjr() {
        return khuwjr;
    }

    public void setKhuwjr(int khuwjr) {
        this.khuwjr = khuwjr;
    }

    public String getKhuwgem() {
        return khuwgem;
    }

    public void setKhuwgem(String khuwgem) {
        this.khuwgem = khuwgem;
    }

    public String getKhuwanr() {
        return khuwanr;
    }

    public void setKhuwanr(String khuwanr) {
        this.khuwanr = khuwanr;
    }

    public String getKanmvad() {
        return kanmvad;
    }

    public void setKanmvad(String kanmvad) {
        this.kanmvad = kanmvad;
    }

    public String getKtusvad() {
        return ktusvad;
    }

    public void setKtusvad(String ktusvad) {
        this.ktusvad = ktusvad;
    }

    public String getKvrn1vad() {
        return kvrn1vad;
    }

    public void setKvrn1vad(String kvrn1vad) {
        this.kvrn1vad = kvrn1vad;
    }

    public String getKvrn2vad() {
        return kvrn2vad;
    }

    public void setKvrn2vad(String kvrn2vad) {
        this.kvrn2vad = kvrn2vad;
    }

    public String getKvrn3vad() {
        return kvrn3vad;
    }

    public void setKvrn3vad(String kvrn3vad) {
        this.kvrn3vad = kvrn3vad;
    }

    public int getKwyzdag() {
        return kwyzdag;
    }

    public void setKwyzdag(int kwyzdag) {
        this.kwyzdag = kwyzdag;
    }

    public int getKwyzmnd() {
        return kwyzmnd;
    }

    public void setKwyzmnd(int kwyzmnd) {
        this.kwyzmnd = kwyzmnd;
    }

    public int getKwyzjr() {
        return kwyzjr;
    }

    public void setKwyzjr(int kwyzjr) {
        this.kwyzjr = kwyzjr;
    }

    public int getKwyzkb() {
        return kwyzkb;
    }

    public void setKwyzkb(int kwyzkb) {
        this.kwyzkb = kwyzkb;
    }

    public int getKwyzstbl() {
        return kwyzstbl;
    }

    public void setKwyzstbl(int kwyzstbl) {
        this.kwyzstbl = kwyzstbl;
    }

    public String getKgmrb() {
        return kgmrb;
    }

    public void setKgmrb(String kgmrb) {
        this.kgmrb = kgmrb;
    }

    public String getKgmerk() {
        return kgmerk;
    }

    public void setKgmerk(String kgmerk) {
        this.kgmerk = kgmerk;
    }

    public String getKwgmmr() {
        return kwgmmr;
    }

    public void setKwgmmr(String kwgmmr) {
        this.kwgmmr = kwgmmr;
    }

    public String getKbrpmr() {
        return kbrpmr;
    }

    public void setKbrpmr(String kbrpmr) {
        this.kbrpmr = kbrpmr;
    }

    public String getKanmgeb() {
        return kanmgeb;
    }

    public void setKanmgeb(String kanmgeb) {
        this.kanmgeb = kanmgeb;
    }

    public String getKvrn1geb() {
        return kvrn1geb;
    }

    public void setKvrn1geb(String kvrn1geb) {
        this.kvrn1geb = kvrn1geb;
    }

    public String getKvrn2geb() {
        return kvrn2geb;
    }

    public void setKvrn2geb(String kvrn2geb) {
        this.kvrn2geb = kvrn2geb;
    }

    public String getKvrn3geb() {
        return kvrn3geb;
    }

    public void setKvrn3geb(String kvrn3geb) {
        this.kvrn3geb = kvrn3geb;
    }

    public String getKtusgeb() {
        return ktusgeb;
    }

    public void setKtusgeb(String ktusgeb) {
        this.ktusgeb = ktusgeb;
    }

    public String getKsexgeb() {
        return ksexgeb;
    }

    public void setKsexgeb(String ksexgeb) {
        this.ksexgeb = ksexgeb;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }
}