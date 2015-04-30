package org.iish.hsn.invoer.repository.reference;

import org.iish.hsn.invoer.domain.reference.Ref_GBH;
import org.springframework.data.repository.Repository;

public interface Ref_GBHRepository extends Repository<Ref_GBH, Integer> {
    public Ref_GBH findByIdnr(int idnr);
}
