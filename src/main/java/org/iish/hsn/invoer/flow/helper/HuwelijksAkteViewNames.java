package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.flow.state.ViewStateHistory;

import java.util.HashMap;
import java.util.Map;

public class HuwelijksAkteViewNames implements ViewNames {
    private static final Map<String, String> byzViewNames = new HashMap<>();

    private final ViewStateHistory viewStateHistory;
    private final int curGebgtgIndex;

    static {
        byzViewNames.put("HS0I", "Identificatie");
        byzViewNames.put("HS0C", "Identificatie");
        byzViewNames.put("HS0A1", "Identificatie");
        byzViewNames.put("HS0A2", "Echtscheiding");
        byzViewNames.put("HS1M", "Gegevens bruidegom");
        byzViewNames.put("HS1V", "Gegevens bruid");
        byzViewNames.put("HS2Mv", "Gegevens vader bruidegom");
        byzViewNames.put("HS2Mm", "Gegevens moeder bruidegom");
        byzViewNames.put("HS2Vv", "Gegevens vader bruid");
        byzViewNames.put("HS2Vm", "Gegevens moeder bruid");
        byzViewNames.put("HS3A", "Overige gegevens huwelijksakte");
        byzViewNames.put("HS3B", "Huwelijksafkondigingen");
        byzViewNames.put("HS4A", "Gegevens eerdere huwelijken");
        byzViewNames.put("HS4B", "Voorkinderen");
        byzViewNames.put("HS6", "Bijlagen");
    }

    public HuwelijksAkteViewNames(ViewStateHistory viewStateHistory, int curGebgtgIndex) {
        this.viewStateHistory = viewStateHistory;
        this.curGebgtgIndex = curGebgtgIndex;
    }

    @Override
    public String getCurByzViewName() {
        return getByzViewName(viewStateHistory.getCurViewStateId(), curGebgtgIndex + 1);
    }

    @Override
    public String getPrevByzViewName() {
        return getByzViewName(viewStateHistory.getPrevViewStateId(), curGebgtgIndex);
    }

    private static String getByzViewName(String viewState, int getuigeIdx) {
        if (byzViewNames.containsKey(viewState)) {
            return byzViewNames.get(viewState);
        }
        else if ((viewState != null) && viewState.equals("HS5")) {
            return (getuigeIdx + 1) + "e getuige";
        }
        return "Overige bijzonderheden";
    }
}
