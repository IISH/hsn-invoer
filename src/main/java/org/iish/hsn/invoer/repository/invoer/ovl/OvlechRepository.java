package org.iish.hsn.invoer.repository.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlech;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OvlechRepository extends Repository<Ovlech, Integer> {
    @Query("SELECT o FROM Ovlech o WHERE o.idnr = ?1 AND o.workOrder = ?2 ORDER BY o.vlgech ASC")
    List<Ovlech> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Ovlech save(Ovlech entity);

    void delete(Ovlech entity);

    void delete(Iterable<? extends Ovlech> entities);
}
