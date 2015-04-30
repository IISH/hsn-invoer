package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.Pkadres;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PkadresRepository extends Repository<Pkadres, Integer> {
    @Query("SELECT p FROM Pkadres p WHERE p.idnr = ?1 AND p.workOrder = ?2 ORDER BY p.vgnradp ASC")
    public List<Pkadres> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public Pkadres save(Pkadres entity);

    void delete(Iterable<? extends Pkadres> entities);
}

