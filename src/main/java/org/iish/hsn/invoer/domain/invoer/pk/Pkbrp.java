package org.iish.hsn.invoer.domain.invoer.pk;

import javax.persistence.*;

import org.iish.hsn.invoer.domain.invoer.Invoer;

@Entity
@Table(name = "pkbrp",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "VGNRBRP", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Pkbrp extends Invoer {
    @Column(name = "IDNR", nullable = false) private    int idnr;    // ID Number
    @Column(name = "VGNRBRP", nullable = false) private int vgnrbrp; // Sequence number for different profession of person
    @Column(name = "BRPPOSP", nullable = false, length = 1) private   String brpposp = ""; // Position in profession hierarchy
    @Column(name = "BEROEPP", nullable = false, length = 254) private String beroepp = ""; // Profession
    @Column(name = "DHBRPP", nullable = false, length = 1) private    String dhbrppp = ""; // Profession is crossed over (doorgehaald)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Pkbrp() {
    }

    public Pkbrp(int vgnrbrp) {
        this.vgnrbrp = vgnrbrp;
    }

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getVgnrbrp() {
        return vgnrbrp;
    }

    public void setVgnrbrp(int vgnrbrp) {
        this.vgnrbrp = vgnrbrp;
    }

    public String getBrpposp() {
        return brpposp;
    }

    public void setBrpposp(String brpposp) {
        this.brpposp = brpposp;
    }

    public String getBeroepp() {
        return beroepp;
    }

    public void setBeroepp(String beroepp) {
        this.beroepp = beroepp;
    }

    public String getDhbrppp() {
        return dhbrppp;
    }

    public void setDhbrppp(String dhbrppp) {
        this.dhbrppp = dhbrppp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
