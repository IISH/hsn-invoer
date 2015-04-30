package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.huw.Huwknd;
import org.iish.hsn.invoer.domain.invoer.huw.Huwknd_;

public class HuwelijksSpecifications extends AkteSpecifications<Huwknd, Integer> {
    public HuwelijksSpecifications() {
        super(Huwknd_.idnr, Huwknd_.workOrder);
    }
}
