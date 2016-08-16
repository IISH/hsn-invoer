package org.iish.hsn.invoer.repository.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.MilitionId;
import org.springframework.data.repository.Repository;

public interface MilitionRepository extends Repository<Milition, Integer> {
    Milition findByIdnrAndMilitionIdAndWorkOrder(int idnr, MilitionId militionId, WorkOrder workOrder);

    Milition save(Milition entity);

    void delete(Milition entity);
}
