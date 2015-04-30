package org.iish.hsn.invoer.repository.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RegistrationRepository extends Repository<Registration, Integer> {
    public Registration findByRegistrationIdAndWorkOrder(RegistrationId registrationId, WorkOrder workOrder);

    @Query("SELECT r FROM Registration r WHERE r.registrationId.keyToRP = ?1 AND r.workOrder = ?2 " +
           "ORDER BY r.yearEntryRP, r.monthEntryRP, r.dayEntryRP ASC")
    public List<Registration> findByKeyToRPAndWorkOrder(int keyToRP, WorkOrder workOrder);

    @Query("SELECT r FROM Registration r WHERE r.workOrder = ?1 " +
           "ORDER BY r.registrationId.keyToRP, r.yearEntryRP, r.monthEntryRP, r.dayEntryRP ASC")
    public List<Registration> findByWorkOrder(WorkOrder workOrder);

    @Query("SELECT r FROM Registration r " +
           "WHERE r.registrationId.keyToRP = ?1 AND r.dayEntryRP = ?2 " +
           "AND r.monthEntryRP = ?3 AND r.yearEntryRP = ?4 AND r.workOrder = ?5")
    public Registration findByOPDatum(int idnr, int day, int month, int year, WorkOrder workOrder);

    @Query("SELECT r FROM Registration r WHERE r.registrationId.keyToRP = ?1 AND r.workOrder = ?2 " +
           "ORDER BY r.yearEntryRP, r.monthEntryRP, r.dayEntryRP")
    public List<Registration> findByOP(int idnr, WorkOrder workOrder);

    public Registration save(Registration entity);

    public void delete(Registration entity);
}
