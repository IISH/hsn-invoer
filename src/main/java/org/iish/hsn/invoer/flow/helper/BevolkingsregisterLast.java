package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * Keeps the last bevolkingsregister registration entered in the user's session.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BevolkingsregisterLast implements Serializable {
    private Registration lastRegistration;

    public Registration getLastRegistration() {
        return lastRegistration;
    }

    public void setLastRegistration(Registration lastRegistration) {
        this.lastRegistration = lastRegistration;
    }
}
