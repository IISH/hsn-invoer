package org.iish.hsn.invoer.repository.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MilitionRepository extends Repository<Milition, Integer>, JpaSpecificationExecutor<Milition> {
    @Query("SELECT m FROM Milition m WHERE m.idnr = ?1 AND m.workOrder = ?2 ORDER BY m.seq")
    List<Milition> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Milition findByIdnrAndSeqAndWorkOrder(int idnr, int seq, WorkOrder workOrder);

    Milition save(Milition entity);

    void delete(Milition entity);
}
