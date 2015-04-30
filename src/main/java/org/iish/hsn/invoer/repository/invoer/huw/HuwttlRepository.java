package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwttl;
import org.springframework.data.repository.Repository;

// TODO: WorkOrder???
public interface HuwttlRepository extends Repository<Huwttl, Integer> {
    public Huwttl findByIdnrAndHuw(int idnr, Huw huw);

    public Huwttl save(Huwttl entity);

    void delete(Huwttl entity);
}
