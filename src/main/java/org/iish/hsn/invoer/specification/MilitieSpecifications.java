package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration;
import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration_;

public class MilitieSpecifications extends AkteSpecifications<MilitionRegistration, Integer> {
    public MilitieSpecifications() {
        super(MilitionRegistration_.idnr, MilitionRegistration_.workOrder);
    }
}
