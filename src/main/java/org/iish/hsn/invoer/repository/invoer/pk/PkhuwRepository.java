package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.Pkhuw;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PkhuwRepository extends Repository<Pkhuw, Integer> {
    @Query("SELECT p FROM Pkhuw p WHERE p.idnr = ?1 AND p.workOrder = ?2 ORDER BY p.vnrhuwp ASC")
    List<Pkhuw> findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    Pkhuw save(Pkhuw entity);

    void delete(Pkhuw entity);

    void delete(Iterable<? extends Pkhuw> entities);
}
