package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Gebbyz;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GebbyzRepository extends Repository<Gebbyz, Integer> {
    List<Gebbyz> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Gebbyz save(Gebbyz entity);

    void delete(Gebbyz entity);

    void deleteAll(Iterable<? extends Gebbyz> entities);
}