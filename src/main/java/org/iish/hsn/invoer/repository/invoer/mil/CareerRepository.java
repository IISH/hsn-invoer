package org.iish.hsn.invoer.repository.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.mil.Career;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CareerRepository extends Repository<Career, Integer> {
    @Query("SELECT c FROM Career c WHERE c.idnr = ?1 AND c.seq = ?2 AND c.type = ?3 AND c.workOrder = ?4 ORDER BY c.seq2 ASC")
    List<Career> findByIdnrAndSeqAndTypeAndWorkOrder(int idnr, int seq, int type, WorkOrder workOrder);

    Career save(Career entity);

    void delete(Career entity);

    void delete(Iterable<? extends Career> entities);
}
