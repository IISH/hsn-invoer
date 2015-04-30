package org.iish.hsn.invoer.repository.invoer.pk;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pk.P8;
import org.springframework.data.repository.Repository;

public interface P8Repository extends Repository<P8, Integer> {
    public P8 findByIdnrAndP8tpnrAndWorkOrder(int idnr, int p8tpnr, WorkOrder workOrder);

    public P8 save(P8 entity);

    public void delete(P8 entity);
}
