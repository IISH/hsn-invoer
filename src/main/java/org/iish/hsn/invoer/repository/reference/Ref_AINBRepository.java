package org.iish.hsn.invoer.repository.reference;

import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.springframework.data.repository.Repository;

public interface Ref_AINBRepository extends Repository<Ref_AINB, Integer> {
    Ref_AINB findByKeyToSourceRegister(int keyToSourceRegister);
}
