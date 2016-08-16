package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration;
import org.springframework.stereotype.Component;

@Component
public class MilitieregisterHelper {
    public String getVrijstellingWegensZiekte(MilitionRegistration milReg, boolean isCorrection) {
        if (milReg.getNumberRegulationIllness() > 0) {
            return "j";
        }
        if (isCorrection) {
            return "n";
        }
        return "";
    }
}
