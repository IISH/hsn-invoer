package org.iish.hsn.invoer.param;

import java.util.HashMap;
import java.util.Map;

public class OverviewParams {
    private Integer laagsteIdnr;
    private Integer hoogsteIdnr;
    private Integer idnr;

    public Integer getLaagsteIdnr() {
        return laagsteIdnr;
    }

    public void setLaagsteIdnr(Integer laagsteIdnr) {
        this.laagsteIdnr = laagsteIdnr;
    }

    public Integer getHoogsteIdnr() {
        return hoogsteIdnr;
    }

    public void setHoogsteIdnr(Integer hoogsteIdnr) {
        this.hoogsteIdnr = hoogsteIdnr;
    }

    public Integer getIdnr() {
        return idnr;
    }

    public void setIdnr(Integer idnr) {
        this.idnr = idnr;
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("laagsteIdnr", laagsteIdnr);
        params.put("hoogsteIdnr", hoogsteIdnr);
        params.put("idnr", idnr);
        return params;
    }
}
