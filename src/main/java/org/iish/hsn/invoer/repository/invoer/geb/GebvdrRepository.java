package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Gebvdr;
import org.springframework.data.repository.Repository;

public interface GebvdrRepository extends Repository<Gebvdr, Integer> {
    Gebvdr findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Gebvdr save(Gebvdr entity);

    void delete(Gebvdr entity);
}