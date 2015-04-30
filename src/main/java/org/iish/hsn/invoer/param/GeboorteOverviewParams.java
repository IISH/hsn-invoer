package org.iish.hsn.invoer.param;

import java.util.Map;

public class GeboorteOverviewParams extends OverviewParams {
    private String gemeente;
    private Integer cohort;

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public Integer getCohort() {
        return cohort;
    }

    public void setCohort(Integer cohort) {
        this.cohort = cohort;
    }

    @Override
    public Map<String, Object> getParamMap() {
        Map<String, Object> params = super.getParamMap();
        params.put("gemeente", gemeente);
        params.put("cohort", cohort);
        return params;
    }
}
