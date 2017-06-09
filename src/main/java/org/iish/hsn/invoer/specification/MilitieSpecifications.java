package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Milition_;

public class MilitieSpecifications extends AkteSpecifications<Milition, Integer> {
    public MilitieSpecifications() {
        super(Milition_.idnr, Milition_.workOrder);
    }
}
