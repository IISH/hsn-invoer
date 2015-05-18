package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.Pkbyz;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PkbyzRepository extends Repository<Pkbyz, Integer> {
    List<Pkbyz> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Pkbyz save(Pkbyz entity);

    void delete(Pkbyz entity);

    void delete(Iterable<? extends Pkbyz> entities);
}