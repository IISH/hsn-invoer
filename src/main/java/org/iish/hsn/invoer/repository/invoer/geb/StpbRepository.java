package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Stpb;
import org.springframework.data.repository.Repository;

public interface StpbRepository extends Repository<Stpb, Integer> {
    Stpb findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);
}
