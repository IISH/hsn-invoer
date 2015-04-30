package org.iish.hsn.invoer.flow.helper;

import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.springframework.stereotype.Component;

@Component
public class OverlijdensAkteHelper {
    public boolean isFatherAangeverButDeath(Ovlknd ovlknd) {
        return ((ovlknd.getLevvovl().equals("n") || ovlknd.getLevvovl().equals("o")) && ovlknd.getAgvvadr().equals("j"));
    }

    public boolean isFatherNotAangeverButAlive(Ovlknd ovlknd) {
        return (ovlknd.getLevvovl().equals("j") && ovlknd.getAgvvadr().equals("n"));
    }
}
