package org.iish.hsn.invoer.repository.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.Relatie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RelatieRepository extends Repository<Relatie, Integer> {
    Relatie findByRelkode(Integer relkode);

    @Query("SELECT rel FROM Relatie rel " +
           "WHERE (UPPER(rel.relatie) = UPPER(?1)) " +
           "AND (rel.workOrder = ?2 OR rel.workOrder = ?3)")
    List<Relatie> findByRelatie(String relatie, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    @Query("SELECT rel FROM Relatie rel " +
           "WHERE (UPPER(rel.relatie) LIKE UPPER(?1)) " +
           "AND (rel.workOrder = ?2 OR rel.workOrder = ?3) " +
           "ORDER BY rel.relatie ASC")
    List<Relatie> findByRelatieLike(String relatie, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    Relatie save(Relatie entity);
}
