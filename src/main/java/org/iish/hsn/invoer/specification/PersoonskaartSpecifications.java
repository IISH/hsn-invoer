package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.pk.Pkknd;
import org.iish.hsn.invoer.domain.invoer.pk.Pkknd_;

public class PersoonskaartSpecifications extends AkteSpecifications<Pkknd, Integer> {
    public PersoonskaartSpecifications() {
        super(Pkknd_.idnr, Pkknd_.workOrder);
    }
}
