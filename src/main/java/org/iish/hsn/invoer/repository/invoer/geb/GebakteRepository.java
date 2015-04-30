package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Gebakte;
import org.springframework.data.repository.Repository;

public interface GebakteRepository extends Repository<Gebakte, Integer> {
    public Gebakte findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Gebakte save(Gebakte entity);

    public void delete(Gebakte entity);
}
