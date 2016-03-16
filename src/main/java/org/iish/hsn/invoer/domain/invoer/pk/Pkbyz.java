package org.iish.hsn.invoer.domain.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.Byz;

import javax.persistence.*;

@Entity
@Table(name = "pkbyz")
public class Pkbyz extends Byz {
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
