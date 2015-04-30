package org.iish.hsn.invoer.repository.invoer.bev;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PersonRepository extends Repository<Person, Integer> {
    @Query("SELECT p FROM Person p " +
           "WHERE p.registrationId = ?1 AND p.workOrder = ?2 " +
           "ORDER BY p.keyToRegistrationPersons ASC")
    public List<Person> findByRegistrationIdAndWorkOrder(RegistrationId registrationId, WorkOrder workOrder);

    public Person save(Person entity);

    void delete(Person entity);

    void delete(Iterable<? extends Person> entities);
}
