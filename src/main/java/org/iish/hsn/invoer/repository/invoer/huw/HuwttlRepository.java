package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwttl;
import org.springframework.data.repository.Repository;

public interface HuwttlRepository extends Repository<Huwttl, Integer> {
    Huwttl findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    Huwttl save(Huwttl entity);

    void delete(Huwttl entity);
}
