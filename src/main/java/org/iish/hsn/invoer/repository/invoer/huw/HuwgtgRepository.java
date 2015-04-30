package org.iish.hsn.invoer.repository.invoer.huw;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwgtg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HuwgtgRepository extends Repository<Huwgtg, Integer> {
    @Query("SELECT h FROM Huwgtg h WHERE h.idnr = ?1 AND h.huw = ?2 AND h.workOrder = ?3 ORDER BY h.vlgnrgt ASC")
    public List<Huwgtg> findByIdnrAndHuwAndWorkOrder(int idnr, Huw huw, WorkOrder workOrder);

    public Huwgtg save(Huwgtg entity);

    void delete(Huwgtg entity);

    void delete(Iterable<? extends Huwgtg> entities);
}
