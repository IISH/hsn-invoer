package org.iish.hsn.invoer.domain.invoer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Embeddable
@Inheritance(strategy = InheritanceType.JOINED)
public class StatusWorkOrder extends WorkOrder implements Serializable {
    public static final StatusWorkOrder EMPTY_STATUS_WORKORDER = new StatusWorkOrder("", "", "I");

    @Column(name = "STATUS", nullable = false, length = 1) private String status = "I";

    public StatusWorkOrder() {
    }

    public StatusWorkOrder(String ondrzko, String opdrnri, String status) {
        super(ondrzko, opdrnri);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
