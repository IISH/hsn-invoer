package org.iish.hsn.invoer.domain.invoer.security;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "inlognaam")})
public class User {
    @Column(name = "inlognaam", nullable = false, length = 30) private String inlognaam = "";
    @Column(name = "wachtwoord", nullable = true, length = 60) private String wachtwoord;
    @Column(name = "triple", nullable = false, length = 3) private String triple = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public String getInlognaam() {
        return inlognaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public String getTriple() {
        return triple;
    }
}
