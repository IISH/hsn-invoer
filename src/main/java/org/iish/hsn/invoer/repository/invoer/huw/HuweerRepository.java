package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huweer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HuweerRepository extends Repository<Huweer, Integer> {
    @Query("SELECT h FROM Huweer h " +
           "WHERE h.idnr = ?1 AND h.huw = ?2 AND h.huwer = ?3 AND h.workOrder = ?4 " +
           "ORDER BY h.vlgnreh ASC")
    List<Huweer> findByIdnrAndHuwAndHuwerAndWorkOrder(int idnr, Huw huw, String huwer, WorkOrder workOrder);

    Huweer save(Huweer entity);

    void delete(Huweer entity);

    void delete(Iterable<? extends Huweer> entities);
}
