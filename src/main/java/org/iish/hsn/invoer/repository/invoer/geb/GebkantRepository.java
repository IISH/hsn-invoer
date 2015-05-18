package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Gebkant;
import org.springframework.data.repository.Repository;

public interface GebkantRepository extends Repository<Gebkant, Integer> {
    Gebkant findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Gebkant save(Gebkant entity);

    void delete(Gebkant entity);
}