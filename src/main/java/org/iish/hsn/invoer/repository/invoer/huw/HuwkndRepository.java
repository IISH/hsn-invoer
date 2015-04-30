package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwknd;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface HuwkndRepository extends Repository<Huwknd, Integer>, JpaSpecificationExecutor<Huwknd> {
    public Huwknd findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    public Huwknd save(Huwknd entity);

    void delete(Huwknd entity);
}
