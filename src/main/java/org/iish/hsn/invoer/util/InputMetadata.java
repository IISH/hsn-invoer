package org.iish.hsn.invoer.util;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InputMetadata implements Serializable {
    private String init;
    private String ondrzk;
    private String opdrnr;

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getOndrzk() {
        return ondrzk;
    }

    public void setOndrzk(String ondrzk) {
        this.ondrzk = ondrzk;
    }

    public String getOpdrnr() {
        return opdrnr;
    }

    public void setOpdrnr(String opdrnr) {
        this.opdrnr = opdrnr;
    }

    public WorkOrder getWorkOrder() {
        return new WorkOrder(ondrzk, opdrnr);
    }
}
