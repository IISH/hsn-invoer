package org.iish.hsn.invoer.domain.invoer.security;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "inlognaam")})
public class User {
    @Column(name = "inlognaam", nullable = false, length = 30)  private String inlognaam = "";
    @Column(name = "wachtwoord", nullable = false, length = 60) private String wachtwoord = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    public String getInlognaam() {
        return inlognaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }
}