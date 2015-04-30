package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.P7;
import org.springframework.data.repository.Repository;

public interface P7Repository extends Repository<P7, Integer> {
    public P7 findByIdnrAndWorkOrder(int idnr, WorkOrder workOrder);

    public P7 save(P7 entity);

    public void delete(P7 entity);
}
