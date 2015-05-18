package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.geb.Gebknd;
import org.springframework.data.repository.Repository;

public interface GebkndRepository extends Repository<Gebknd, Integer> {
    Gebknd findByIdnr(int idnr);

    Gebknd save(Gebknd entity);

    void delete(Gebknd entity);
}
