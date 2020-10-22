package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwbyz;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HuwbyzRepository extends Repository<Huwbyz, Integer> {
    List<Huwbyz> findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    Huwbyz save(Huwbyz entity);

    void delete(Huwbyz entity);

    void deleteAll(Iterable<? extends Huwbyz> entities);
}