package org.iish.hsn.invoer.repository.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlbyz;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OvlbyzRepository extends Repository<Ovlbyz, Integer> {
    List<Ovlbyz> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Ovlbyz save(Ovlbyz entity);

    void delete(Ovlbyz entity);

    void deleteAll(Iterable<? extends Ovlbyz> entities);
}