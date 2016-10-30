package org.iish.hsn.invoer.repository.invoer.mil;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface MilitionRegistrationRepository extends Repository<MilitionRegistration, Integer>,
        JpaSpecificationExecutor<MilitionRegistration> {
    MilitionRegistration findByIdnrAndSeqAndWorkOrder(int idnr, int seq, WorkOrder workOrder);

    MilitionRegistration save(MilitionRegistration entity);

    void delete(MilitionRegistration entity);
}
