package org.iish.hsn.invoer.repository.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.bev.PersonDynamic;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonDynamicRepository extends Repository<PersonDynamic, Integer> {
    @Query("SELECT pd FROM PersonDynamic pd " +
            "WHERE pd.registrationId = ?1 AND pd.keyToRegistrationPersons = ?2 " +
            "AND pd.dynamicDataType = ?3 AND pd.workOrder = ?4 " +
            "ORDER BY pd.dynamicDataSequenceNumber ASC")
    List<PersonDynamic> findAllPersonDynamicForPerson(RegistrationId registrationId, int keyToRegistrationPersons,
                                                      int dynamicDataType, WorkOrder workOrder);

    @Query("SELECT pd FROM PersonDynamic pd " +
            "WHERE pd.registrationId = ?1 AND pd.keyToRegistrationPersons = ?2 AND pd.workOrder = ?3 " +
            "ORDER BY pd.dynamicDataType, pd.dynamicDataSequenceNumber ASC")
    List<PersonDynamic> findAllPersonDynamicForPerson(RegistrationId registrationId,
                                                      int keyToRegistrationPersons, WorkOrder workOrder);

    List<PersonDynamic> saveAll(Iterable<PersonDynamic> entities);

    void delete(PersonDynamic entity);

    void deleteAll(Iterable<? extends PersonDynamic> entities);

    @Modifying
    @Transactional
    @Query("DELETE FROM PersonDynamic pd WHERE pd.registrationId = ?1 AND pd.workOrder = ?2")
    void delete(RegistrationId registrationId, WorkOrder workOrder);
}
