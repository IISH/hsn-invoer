package org.iish.hsn.invoer.repository.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationAddress;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RegistrationAddressRepository extends Repository<RegistrationAddress, Integer> {
    @Query("SELECT ra FROM RegistrationAddress ra " +
           "WHERE ra.registrationId = ?1 AND ra.workOrder = ?2 " +
           "ORDER BY ra.keyToRegistrationPersons, ra.sequenceNumberToAddresses, " +
           "ra.dayOfAddress, ra.monthOfAddress, ra.yearOfAddress ASC")
    List<RegistrationAddress> findByRegistrationId(RegistrationId registrationId, WorkOrder workOrder);

    List<RegistrationAddress> save(Iterable<RegistrationAddress> entities);

    void delete(RegistrationAddress entity);

    void delete(Iterable<? extends RegistrationAddress> entities);

    @Modifying
    @Transactional
    @Query("DELETE FROM RegistrationAddress ra WHERE ra.registrationId = ?1 AND ra.workOrder = ?2")
    void delete(RegistrationId registrationId, WorkOrder workOrder);
}
