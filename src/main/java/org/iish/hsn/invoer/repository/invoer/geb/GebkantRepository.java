package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Gebkant;
import org.springframework.data.repository.Repository;

public interface GebkantRepository extends Repository<Gebkant, Integer> {
    public Gebkant findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Gebkant save(Gebkant entity);

    void delete(Gebkant entity);
}