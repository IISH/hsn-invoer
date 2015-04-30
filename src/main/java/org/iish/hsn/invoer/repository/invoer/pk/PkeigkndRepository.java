package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.Pkeigknd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PkeigkndRepository extends Repository<Pkeigknd, Integer> {
    @Query("SELECT p FROM Pkeigknd p WHERE p.idnr = ?1 AND p.workOrder = ?2 ORDER BY p.vgnrkdp ASC")
    public List<Pkeigknd> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Pkeigknd save(Pkeigknd entity);

    void delete(Pkeigknd entity);

    void delete(Iterable<? extends Pkeigknd> entities);
}
