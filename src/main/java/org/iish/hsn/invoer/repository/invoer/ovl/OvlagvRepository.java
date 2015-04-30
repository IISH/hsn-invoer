package org.iish.hsn.invoer.repository.invoer.ovl;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlagv;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OvlagvRepository extends Repository<Ovlagv, Integer> {
    @Query("SELECT o FROM Ovlagv o WHERE o.idnr = ?1 AND o.workOrder = ?2 ORDER BY o.vlgnrag ASC")
    public List<Ovlagv> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Ovlagv save(Ovlagv entity);

    void delete(Ovlagv entity);

    void delete(Iterable<? extends Ovlagv> entities);
}
