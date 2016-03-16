package org.iish.hsn.invoer.domain.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.Invoer;

import javax.persistence.*;

@Entity
@Table(name = "p7",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"IDNR", "ONDRZKO", "OPDRNRI"})},
       indexes = {@Index(columnList = "ONDRZKO, OPDRNRI")})
public class P7 extends Invoer {
    @Column(name = "IDNR", nullable = false) private                int    idnr;   // ID Number
    @Column(name = "IDNRP", nullable = false) private               int    idnrp;  // IDNR partner
    @Column(name = "P7IDBG", nullable = false) private              int    p7idbg; // Original identifier for the Personal List from CBG itself (only kept for checks on consistency)
    @Column(name = "P7OPOG", nullable = false) private              String p7opog = ""; // Place of death on PL-lists (municipality and country distinguished)
    @Column(name = "P7OPOL", nullable = false) private              String p7opol = ""; // Place of death on PL-lists (municipality and country distinguished)
    @Column(name = "P7OPOR", nullable = false) private              String p7opor = ""; // Municipality of certificate; rarely used only in case of foreign places of death
    @Column(name = "P7OPOB", nullable = false) private              String p7opob = ""; // Administrative code of the death certificate or other indication; comparable with field oakperp (to be checked); only in combination with p7opor
    @Column(name = "P7OPIO", nullable = false) private              String p7opio = ""; // indicatie onjuist; Not clear what this variable means (only sporadically content)
    @Column(name = "P7OPPG", nullable = false, length = 60) private String p7oppg = ""; // Municipality where the Personal List was put up
    @Column(name = "P7OPPC", nullable = false, length = 60) private  String p7oppc = ""; // Affirmation of the Personal Card was converted

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public int getIdnr() {
        return idnr;
    }

    public void setIdnr(int idnr) {
        this.idnr = idnr;
    }

    public int getIdnrp() {
        return idnrp;
    }

    public void setIdnrp(int idnrp) {
        this.idnrp = idnrp;
    }

    public int getP7idbg() {
        return p7idbg;
    }

    public void setP7idbg(int p7idbg) {
        this.p7idbg = p7idbg;
    }

    public String getP7opog() {
        return p7opog;
    }

    public void setP7opog(String p7opog) {
        this.p7opog = p7opog;
    }

    public String getP7opol() {
        return p7opol;
    }

    public void setP7opol(String p7opol) {
        this.p7opol = p7opol;
    }

    public String getP7opor() {
        return p7opor;
    }

    public void setP7opor(String p7opor) {
        this.p7opor = p7opor;
    }

    public String getP7opob() {
        return p7opob;
    }

    public void setP7opob(String p7opob) {
        this.p7opob = p7opob;
    }

    public String getP7opio() {
        return p7opio;
    }

    public void setP7opio(String p7opio) {
        this.p7opio = p7opio;
    }

    public String getP7oppg() {
        return p7oppg;
    }

    public void setP7oppg(String p7oppg) {
        this.p7oppg = p7oppg;
    }

    public String getP7oppc() {
        return p7oppc;
    }

    public void setP7oppc(String p7oppc) {
        this.p7oppc = p7oppc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
