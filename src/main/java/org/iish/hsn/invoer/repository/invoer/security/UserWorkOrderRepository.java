package org.iish.hsn.invoer.repository.invoer.security;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.security.UserWorkOrder;
import org.springframework.data.repository.Repository;

public interface UserWorkOrderRepository extends Repository<UserWorkOrder, Integer> {
    UserWorkOrder findByTripleAndWorkOrder(String triple, WorkOrder workOrder);
}
