package org.iish.hsn.invoer.flow.state;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OverlijdensStart implements Serializable {
    private String levnloos;

    public String getLevnloos() {
        if ((this.levnloos == null) || this.levnloos.trim().isEmpty()) {
            return "n";
        }
        return this.levnloos;
    }

    public void setLevnloos(String levnloos) {
        this.levnloos = levnloos;
    }
}
