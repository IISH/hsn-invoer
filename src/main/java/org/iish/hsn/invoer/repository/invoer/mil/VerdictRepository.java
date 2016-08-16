package org.iish.hsn.invoer.repository.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.mil.MilitionId;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.domain.invoer.pk.Pkbrp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface VerdictRepository extends Repository<Verdict, Integer> {
    @Query("SELECT v FROM Verdict v WHERE v.idnr = ?1 AND v.militionId = ?2 AND v.workOrder = ?3")
    List<Verdict> findByIdnrAndMilitionIdAndWorkOrder(int idnr, MilitionId militionId, WorkOrder workOrder);

    Verdict save(Verdict entity);

    void delete(Verdict entity);

    void delete(Iterable<? extends Verdict> entities);
}
