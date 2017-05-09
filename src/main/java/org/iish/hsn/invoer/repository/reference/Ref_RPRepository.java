package org.iish.hsn.invoer.repository.reference;

import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface Ref_RPRepository extends Repository<Ref_RP, Integer> {
    @Query("SELECT rp FROM Ref_RP rp WHERE rp.idnr = ?1 AND rp.idOrigin = 10")
    Ref_RP findByIdnr(int idnr);
}
