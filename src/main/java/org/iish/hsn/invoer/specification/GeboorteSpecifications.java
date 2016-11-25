package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.geb.Geb;
import org.iish.hsn.invoer.domain.invoer.geb.Geb_;
import org.iish.hsn.invoer.util.Cohort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class GeboorteSpecifications extends AkteSpecifications<Geb, Integer> {
    public GeboorteSpecifications() {
        super(Geb_.idnr, Geb_.workOrder);
    }

    public Specification<Geb> isInGemeente(final int gemnr) {
        return new Specification<Geb>() {
            public Predicate toPredicate(Root<Geb> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.orderBy(getOrderBy(root, cb));
                return cb.equal(root.get(Geb_.gemnr), gemnr);
            }
        };
    }

    public Specification<Geb> isInCohort(final int cohortNumber) {
        final Cohort cohort = Cohort.getCohortByNumber(cohortNumber);
        return new Specification<Geb>() {
            public Predicate toPredicate(Root<Geb> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.orderBy(getOrderBy(root, cb));
                return cb.and(cb.greaterThanOrEqualTo(root.get(Geb_.jaar), cohort.getStartYear()),
                        cb.lessThanOrEqualTo(root.get(Geb_.jaar), cohort.getEndYear()));
            }
        };
    }

    @Override
    protected List<Order> getOrderBy(Root<Geb> root, CriteriaBuilder cb) {
        List<Order> orderColumns = new ArrayList<>();
        orderColumns.add(cb.asc(root.get(Geb_.gemnaam)));
        orderColumns.add(cb.asc(root.get(Geb_.jaar)));
        orderColumns.add(cb.asc(root.get(Geb_.aktenr)));
        orderColumns.add(cb.asc(root.get(Geb_.idnr)));
        return orderColumns;
    }
}
