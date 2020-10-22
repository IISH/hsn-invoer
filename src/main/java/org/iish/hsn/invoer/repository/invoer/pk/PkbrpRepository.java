package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.Pkbrp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PkbrpRepository extends Repository<Pkbrp, Integer> {
    @Query("SELECT p FROM Pkbrp p WHERE p.idnr = ?1 AND p.workOrder = ?2 ORDER BY p.vgnrbrp ASC")
    List<Pkbrp> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Pkbrp save(Pkbrp entity);

    void delete(Pkbrp entity);

    void deleteAll(Iterable<? extends Pkbrp> entities);
}

