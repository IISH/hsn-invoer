package org.iish.hsn.invoer.repository.reference;

import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.springframework.data.repository.Repository;

public interface Ref_RPRepository extends Repository<Ref_RP, Integer> {
    Ref_RP findByIdnr(int idnr);
}
