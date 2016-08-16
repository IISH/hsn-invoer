package org.iish.hsn.invoer.domain.invoer.security;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;

import javax.persistence.*;

@Entity
@Table(name = "user_workorders", uniqueConstraints = @UniqueConstraint(columnNames = {"USERID", "ONDRZKO", "OPDRNRI"}))
public class UserWorkOrder {
    @Column(name = "USERID", nullable = false) private int userId;

    @Embedded private WorkOrder workOrder = new WorkOrder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    public int getUserId() {
        return userId;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public Integer getId() {
        return id;
    }
}
