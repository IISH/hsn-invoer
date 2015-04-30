package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.Pkknd;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface PkkndRepository extends Repository<Pkknd, Integer>, JpaSpecificationExecutor<Pkknd> {
    public Pkknd findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Pkknd save(Pkknd entity);

    void delete(Pkknd entity);
}
