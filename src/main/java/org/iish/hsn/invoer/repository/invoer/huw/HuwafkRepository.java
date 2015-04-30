package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwafk;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HuwafkRepository extends Repository<Huwafk, Integer> {
    @Query("SELECT h FROM Huwafk h WHERE h.idnr = ?1 AND h.huw = ?2 AND h.workOrder = ?3 ORDER BY h.hwaknr ASC")
    public List<Huwafk> findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    public Huwafk save(Huwafk entity);

    void delete(Iterable<? extends Huwafk> entities);
}

