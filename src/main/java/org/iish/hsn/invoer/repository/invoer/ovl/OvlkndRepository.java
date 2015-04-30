package org.iish.hsn.invoer.repository.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface OvlkndRepository extends Repository<Ovlknd, Integer>, JpaSpecificationExecutor<Ovlknd> {
    public Ovlknd findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Ovlknd save(Ovlknd entity);

    void delete(Ovlknd entity);
}
