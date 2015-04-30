package org.iish.hsn.invoer.repository.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationAddress;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RegistrationAddressRepository extends Repository<RegistrationAddress, Integer> {
    @Query("SELECT ra FROM RegistrationAddress ra " +
           "WHERE ra.registrationId = ?1 AND ra.workOrder = ?2 " +
           "ORDER BY ra.keyToRegistrationPersons, ra.sequenceNumberToAddresses, " +
           "ra.dayOfAddress, ra.monthOfAddress, ra.yearOfAddress ASC")
    public List<RegistrationAddress> findByRegistrationId(RegistrationId registrationId, WorkOrder workOrder);

    public List<RegistrationAddress> save(Iterable<RegistrationAddress> entities);

    public void delete(RegistrationAddress entity);

    public void delete(Iterable<? extends RegistrationAddress> entities);
}
