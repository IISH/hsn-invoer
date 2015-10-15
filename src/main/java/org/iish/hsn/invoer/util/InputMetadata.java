package org.iish.hsn.invoer.util;

import org.iish.hsn.invoer.domain.invoer.Invoer;
import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InputMetadata implements Serializable {
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private String ondrzk;
    private String opdrnr;
    //private String arch; // TODO: Required?
    private String init;

    @Value("${application.version}") private String version;

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

    /*public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }*/

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getVersion() {
        return version;
    }

    public boolean isValid() {
        return ((ondrzk != null) && (ondrzk.length() == 3) &&
                (opdrnr != null) && (opdrnr.length() == 3) &&
               /* (arch != null) && (arch.length() == 1) &&*/
                (init != null) && (init.length() == 3));
    }

    public WorkOrder getWorkOrder() {
        return new WorkOrder(ondrzk, opdrnr);
    }

    public void saveToEntity(Invoer invoer) {
        invoer.setOpdrnr(opdrnr);
        //invoer.setArch(arch);
        invoer.setInit(init);
        invoer.setVersie(version);
        invoer.setDatum(DATE_TIME_FORMAT.format(new Date()));

        // Use the field 'opdrnro' to check if the original values should be set as well
        if ((invoer.getOpdrnro() == null) || invoer.getOpdrnro().trim().isEmpty()) {
            invoer.setWorkOrder(getWorkOrder());
            invoer.setOpdrnro(opdrnr);
            //invoer.setArcho(arch);
            invoer.setInito(init);
            invoer.setVersieo(version);
            invoer.setDatumo(DATE_TIME_FORMAT.format(new Date()));
        }
    }
}
