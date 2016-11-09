package org.iish.hsn.invoer.repository.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.KindRelatie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface KindRelatieRepository extends Repository<KindRelatie, Integer> {
    @Query("SELECT rel FROM KindRelatie rel " +
           "WHERE (UPPER(rel.relatie) LIKE UPPER(?1)) " +
           "AND (rel.workOrder = ?2 OR rel.workOrder = ?3)")
    KindRelatie findByRelatie(String relatie, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    @Query("SELECT rel FROM KindRelatie rel " +
           "WHERE (UPPER(rel.relatie) LIKE UPPER(?1)) " +
           "AND (rel.workOrder = ?2 OR rel.workOrder = ?3) " +
           "ORDER BY rel.relatie ASC")
    List<KindRelatie> findByRelatieLike(String relatie, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    KindRelatie save(KindRelatie entity);
}
