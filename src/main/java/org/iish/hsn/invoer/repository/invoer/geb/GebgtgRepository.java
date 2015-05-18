package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.geb.Gebgtg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GebgtgRepository extends Repository<Gebgtg, Integer> {
    @Query("SELECT g FROM Gebgtg g WHERE g.idnr = ?1 AND g.workOrder = ?2 ORDER BY g.vlgnrgt ASC")
    List<Gebgtg> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Gebgtg save(Gebgtg entity);

    void delete(Iterable<? extends Gebgtg> entities);
}
