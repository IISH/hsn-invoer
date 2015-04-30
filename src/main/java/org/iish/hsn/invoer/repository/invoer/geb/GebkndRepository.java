package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.geb.Gebknd;
import org.springframework.data.repository.Repository;

public interface GebkndRepository extends Repository<Gebknd, Integer> {
    public Gebknd findByIdnr(int idnr);

    public Gebknd save(Gebknd entity);

    public void delete(Gebknd entity);
}
