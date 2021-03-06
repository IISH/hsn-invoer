package org.iish.hsn.invoer.repository.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.Plaats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlaatsRepository extends Repository<Plaats, Integer> {
    Plaats findByGemnr(int gemnr);

    Plaats findByGemnaamLikeIgnoreCase(String gemnaam);

    @Query("SELECT plts FROM Plaats plts " +
           "WHERE (UPPER(plts.gemnaam) = UPPER(?1)) " +
           "AND (plts.workOrder = ?2 OR plts.workOrder = ?3)")
    List<Plaats> findByGemnaam(String gemnaam, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    @Query("SELECT plts FROM Plaats plts " +
           "WHERE (UPPER(plts.gemnaam) LIKE UPPER(?1)) " +
           "AND (plts.workOrder = ?2 OR plts.workOrder = ?3) " +
           "ORDER BY plts.gemnaam ASC")
    List<Plaats> findByGemnaamLike(String gemnaam, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    Plaats save(Plaats entity);
}
