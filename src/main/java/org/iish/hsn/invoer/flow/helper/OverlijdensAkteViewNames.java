package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.flow.state.ViewStateHistory;

import java.util.HashMap;
import java.util.Map;

public class OverlijdensAkteViewNames implements ViewNames {
    private static final Map<String, String> byzViewNames = new HashMap<>();

    private ViewStateHistory viewStateHistory;
    private int curAangeverIndex;

    static {
        byzViewNames.put("OS0I", "Controle identificatie OVL akte");
        byzViewNames.put("OS1A", "Identificatie overledene");
        byzViewNames.put("OS1B", "Akte gegevens");
        byzViewNames.put("OS1C", "Overlijdensdatum en tijd");
        byzViewNames.put("OS2A", "Vader");
        byzViewNames.put("OS2B", "Moeder");
        byzViewNames.put("OS3A", "Aantal aangevers");
        byzViewNames.put("OS4", "Overige gegevens OVL akte");
        byzViewNames.put("OS5", "Echtgeno(o)t(e) overledene");
    }

    public OverlijdensAkteViewNames(ViewStateHistory viewStateHistory, int curAangeverIndex) {
        this.viewStateHistory = viewStateHistory;
        this.curAangeverIndex = curAangeverIndex;
    }

    @Override
    public String getCurByzViewName() {
        return getByzViewName(viewStateHistory.getCurViewStateId(), curAangeverIndex + 1);
    }

    @Override
    public String getPrevByzViewName() {
        return getByzViewName(viewStateHistory.getPrevViewStateId(), curAangeverIndex);
    }

    private static String getByzViewName(String viewState, int aangeverNr) {
        if (byzViewNames.containsKey(viewState)) {
            return byzViewNames.get(viewState);
        }
        else if (viewState.equals("OS3B")) {
            return aangeverNr + "e Aangever";
        }
        return "Overige bijzonderheden";
    }
}
