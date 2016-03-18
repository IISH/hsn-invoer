package org.iish.hsn.invoer.domain.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.Byz;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ovlbyz", indexes = {@Index(columnList = "IDNR"), @Index(columnList = "ONDRZKO, OPDRNRI")})
public class Ovlbyz extends Byz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}