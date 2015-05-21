package org.iish.hsn.invoer.domain.invoer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class WorkOrder implements Serializable {
    public static final WorkOrder EMPTY_WORKORDER = new WorkOrder("", "");

    @Column(name = "ONDRZKO", nullable = false, length = 3) private String ondrzko = "";
    @Column(name = "OPDRNRI", nullable = false, length = 3) private String opdrnri = "";

    public WorkOrder() {
    }

    public WorkOrder(String ondrzko, String opdrnri) {
        this.ondrzko = ondrzko;
        this.opdrnri = opdrnri;
    }

    public String getOndrzko() {
        return ondrzko;
    }

    public void setOndrzko(String ondrzko) {
        this.ondrzko = ondrzko;
    }

    public String getOpdrnri() {
        return opdrnri;
    }

    public void setOpdrnri(String opdrnri) {
        this.opdrnri = opdrnri;
    }
}
