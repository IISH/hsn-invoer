package org.iish.hsn.invoer.flow.state;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeboorteStart implements Serializable {
    private String oversamp;
    private String invbeper;

    public String getOversamp() {
        if ((this.oversamp == null) || this.oversamp.trim().isEmpty()) {
            return "n";
        }
        return this.oversamp;
    }

    public void setOversamp(String oversamp) {
        this.oversamp = oversamp;
    }

    public String getInvbeper() {
        if ((this.invbeper == null) || this.invbeper.trim().isEmpty()) {
            return "n";
        }
        return this.invbeper;
    }

    public void setInvbeper(String invbeper) {
        this.invbeper = invbeper;
    }
}
