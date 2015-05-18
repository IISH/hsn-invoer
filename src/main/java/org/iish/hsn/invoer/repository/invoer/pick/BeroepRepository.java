package org.iish.hsn.invoer.repository.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.Beroep;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BeroepRepository extends Repository<Beroep, Integer> {
    @Query("SELECT brp FROM Beroep brp " +
           "WHERE (UPPER(brp.berpnaam) = UPPER(?1)) " +
           "AND (brp.workOrder = ?2 OR brp.workOrder = ?3)")
    Beroep findByBerpnaam(String berpnaam, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    @Query("SELECT brp FROM Beroep brp " +
           "WHERE (UPPER(brp.berpnaam) LIKE UPPER(?1)) " +
           "AND (brp.workOrder = ?2 OR brp.workOrder = ?3) " +
           "ORDER BY brp.berpnaam ASC")
    List<Beroep> findByBerpnaamLike(String berpnaam, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    Beroep save(Beroep entity);
}
