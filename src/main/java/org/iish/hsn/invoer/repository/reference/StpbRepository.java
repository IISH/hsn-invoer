package org.iish.hsn.invoer.repository.reference;

import org.iish.hsn.invoer.domain.reference.Stpb;
import org.springframework.data.repository.Repository;

public interface StpbRepository extends Repository<Stpb, Integer> {
    public Stpb findByIdnr(int idnr);
}
