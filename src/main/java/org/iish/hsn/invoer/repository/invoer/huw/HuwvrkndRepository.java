package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwvrknd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HuwvrkndRepository extends Repository<Huwvrknd, Integer> {
    @Query("SELECT h FROM Huwvrknd h WHERE h.idnr = ?1 AND h.huw = ?2 AND h.workOrder = ?3 ORDER BY h.vlgnrvk ASC")
    public List<Huwvrknd> findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    public Huwvrknd save(Huwvrknd entity);

    void delete(Huwvrknd entity);

    void delete(Iterable<? extends Huwvrknd> entities);
}
