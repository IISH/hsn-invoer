package org.iish.hsn.invoer.domain.invoer.security;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "user_workorders", uniqueConstraints = @UniqueConstraint(columnNames = {"triple", "ONDRZKO", "OPDRNRI"}))
public class UserWorkOrder {
    @Column(name = "triple", nullable = false, length = 3) private String triple;

    @Embedded private WorkOrder workOrder = new WorkOrder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public String getTriple() {
        return triple;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public Integer getId() {
        return id;
    }
}
