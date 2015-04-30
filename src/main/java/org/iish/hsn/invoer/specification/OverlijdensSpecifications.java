package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd_;

public class OverlijdensSpecifications extends AkteSpecifications<Ovlknd, Integer> {
    public OverlijdensSpecifications() {
        super(Ovlknd_.idnr, Ovlknd_.workOrder);
    }
}
