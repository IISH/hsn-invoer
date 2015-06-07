package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.flow.state.ViewStateHistory;

import java.util.HashMap;
import java.util.Map;

public class GeboorteAkteViewNames implements ViewNames {
    private static final Map<String, String> byzViewNames = new HashMap<>();

    private ViewStateHistory viewStateHistory;
    private int curGebgtgIndex;

    static {
        byzViewNames.put("GS0I", "Akte identificatie");
        byzViewNames.put("GS1", "Aangever");
        byzViewNames.put("GS2", "Geboorte en moeder");
        byzViewNames.put("GS3", "Moeder en kind");
        byzViewNames.put("GS4", "Vader");
        byzViewNames.put("GS6", "Overige kantmeldingen");
        byzViewNames.put("GS7a", "Naamsverandering door erkenning bij huwelijk");
        byzViewNames.put("GS7b", "Wijziging door K.B.");
        byzViewNames.put("GS7c", "Wijziging door rechtbankbesluit");
        byzViewNames.put("GS7d", "Erkenning door moeder");
    }

    public GeboorteAkteViewNames(ViewStateHistory viewStateHistory, int curGebgtgIndex) {
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

    private static String getByzViewName(String viewState, int getuigeIndex) {
        if (byzViewNames.containsKey(viewState)) {
            return byzViewNames.get(viewState);
        }
        else if (viewState.equals("GS5")) {
            return (getuigeIndex + 1) + "e getuige";
        }
        return "Overige bijzonderheden";
    }
}
