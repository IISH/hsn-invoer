package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwbyz;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HuwbyzRepository extends Repository<Huwbyz, Integer> {
    public List<Huwbyz> findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    public Huwbyz save(Huwbyz entity);

    void delete(Huwbyz entity);

    void delete(Iterable<? extends Huwbyz> entities);
}