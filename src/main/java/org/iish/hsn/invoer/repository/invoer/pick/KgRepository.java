package org.iish.hsn.invoer.repository.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.Kg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface KgRepository extends Repository<Kg, Integer> {
    @Query("SELECT kg FROM Kg kg " +
           "WHERE (UPPER(kg.kerkgeno) LIKE UPPER(?1)) " +
           "AND (kg.workOrder = ?2 OR kg.workOrder = ?3)")
    public Kg findByKerkgeno(String kerkgeno, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    @Query("SELECT kg FROM Kg kg " +
           "WHERE (UPPER(kg.kerkgeno) LIKE UPPER(?1)) " +
           "AND (kg.workOrder = ?2 OR kg.workOrder = ?3) " +
           "ORDER BY kg.kerkgeno ASC")
    public List<Kg> findByKerkgenoLike(String kerkgeno, WorkOrder emptyWorkOrder, WorkOrder workOrder);

    public Kg save(Kg entity);
}
