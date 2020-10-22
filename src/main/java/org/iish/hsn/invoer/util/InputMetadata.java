package org.iish.hsn.invoer.util;

import org.iish.hsn.invoer.domain.invoer.Invoer;
import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.security.User;
import org.iish.hsn.invoer.domain.invoer.security.UserWorkOrder;
import org.iish.hsn.invoer.repository.invoer.security.UserRepository;
import org.iish.hsn.invoer.repository.invoer.security.UserWorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InputMetadata implements Serializable {
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private String init;
    private String ondrzk;
    private String opdrnr;

    @Value("${application.version}") private String version;

    @Autowired private Environment environment;
    @Autowired private UserRepository userRepository;
    @Autowired private UserWorkOrderRepository userWorkOrderRepository;

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

    public String getVersion() {
        return version;
    }

    public boolean isValid() {
        boolean valid = ((ondrzk != null) && (ondrzk.length() == 3) && (opdrnr != null) && (opdrnr.length() == 3));

        if (valid && !environment.acceptsProfiles(Profiles.of("noCheck"))
                && environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            User user = getLoggedInUser();
            UserWorkOrder userWorkOrder =
                    userWorkOrderRepository.findByTripleAndWorkOrder(user.getTriple(), getWorkOrder());
            valid = (userWorkOrder != null);
        }
        else if (valid && !environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            valid = ((init != null) && (init.length() == 3));
        }

        return valid;
    }

    public boolean isAdmin() {
        if (environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            User user = getLoggedInUser();
            return user.getType().equalsIgnoreCase("ADMIN");
        }

        return false;
    }

    public WorkOrder getWorkOrder() {
        return new WorkOrder(ondrzk, opdrnr);
    }

    public void saveToEntity(Invoer invoer) {
        invoer.setOpdrnr(opdrnr);
        invoer.setInit(getInit());
        invoer.setVersie(version);
        invoer.setDatum(DATE_TIME_FORMAT.format(new Date()));

        // Use the field 'opdrnro' to check if the original values should be set as well
        if ((invoer.getOpdrnro() == null) || invoer.getOpdrnro().trim().isEmpty()) {
            invoer.setWorkOrder(getWorkOrder());
            invoer.setOpdrnro(opdrnr);
            invoer.setInito(getInit());
            invoer.setVersieo(version);
            invoer.setDatumo(DATE_TIME_FORMAT.format(new Date()));
        }
    }

    private String getInit() {
        if (environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            User user = getLoggedInUser();
            return user.getTriple();
        }
        return this.init;
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByInlognaam(authentication.getName());
    }
}
