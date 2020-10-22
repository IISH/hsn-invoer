package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.flow.state.ViewStateHistory;

import java.util.HashMap;
import java.util.Map;

public class PersoonskaartViewNames implements ViewNames {
    private static final Map<String, String> byzViewNames = new HashMap<>();

    private final ViewStateHistory viewStateHistory;

    static {
        byzViewNames.put("PS0I", "Identificatie nummer PK-houder");
        byzViewNames.put("PS0C", "Identificatie via correctie");
        byzViewNames.put("PS0A", "Algemene gegevens pk");
        byzViewNames.put("PS1", "Gegevens PK-houder");
        byzViewNames.put("PS2", "Ouders");
        byzViewNames.put("PS3", "Beroepen");
        byzViewNames.put("PS4", "Huwelijk");
        byzViewNames.put("PS5", "Adressen");
        byzViewNames.put("PS6", "Kinderen");
    }

    public PersoonskaartViewNames(ViewStateHistory viewStateHistory) {
        this.viewStateHistory = viewStateHistory;
    }

    @Override
    public String getCurByzViewName() {
        return getByzViewName(viewStateHistory.getCurViewStateId());
    }

    @Override
    public String getPrevByzViewName() {
        return getByzViewName(viewStateHistory.getPrevViewStateId());
    }

    private static String getByzViewName(String viewState) {
        if (byzViewNames.containsKey(viewState)) {
            return byzViewNames.get(viewState);
        }
        return "Overige bijzonderheden";
    }
}
