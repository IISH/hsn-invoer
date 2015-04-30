package org.iish.hsn.invoer.repository.invoer.pick;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.Adrestp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AdrestpRepository extends Repository<Adrestp, Integer> {
    public Adrestp findByCode(String code);

    @Query("SELECT adr FROM Adrestp adr " +
           "WHERE (UPPER(adr.code) LIKE UPPER(?1) OR UPPER(adr.type) LIKE UPPER(?1)) " +
           "AND (adr.workOrder = ?2 OR adr.workOrder = ?3)")
    public List<Adrestp> findByCodeOrTypeLike(String type, WorkOrder emptyWorkOrder, WorkOrder workOrder);
}
