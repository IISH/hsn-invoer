package org.iish.hsn.invoer.repository.invoer.geb;

import org.iish.hsn.invoer.domain.invoer.geb.Geb;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface GebRepository extends Repository<Geb, Integer>, JpaSpecificationExecutor<Geb> {
}

