package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.Invoer;
import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class AkteSpecifications<X extends Invoer, T extends Integer> {
    private SingularAttribute<X, T> idnrAttr;
    private SingularAttribute<Invoer, WorkOrder> workOrderAttr;

    public AkteSpecifications(SingularAttribute<X, T> idnrAttr, SingularAttribute<Invoer, WorkOrder> workOrderAttr) {
        this.idnrAttr = idnrAttr;
        this.workOrderAttr = workOrderAttr;
    }

    public Specifications<X> getSpec(WorkOrder workOrder) {
        return Specifications.where(hasWorkOrder(workOrder));
    }

    public Specification<X> isBetweenLowestAndHighestIdnr(final int lowestIdnr, final int highestIdnr) {
        final SingularAttribute<X, T> idnrAttr = this.idnrAttr;
        return new Specification<X>() {
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.orderBy(getOrderBy(root, cb));
                return cb.and(
                        cb.greaterThanOrEqualTo(root.get(idnrAttr), lowestIdnr),
                        cb.lessThanOrEqualTo(root.get(idnrAttr), highestIdnr)
                );
            }
        };
    }

    public Specification<X> hasIdnr(final int idnr) {
        final SingularAttribute<X, T> idnrAttr = this.idnrAttr;
        return new Specification<X>() {
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(idnrAttr), idnr);
            }
        };
    }

    protected List<Order> getOrderBy(Root<X> root, CriteriaBuilder cb) {
        List<Order> orderColumns = new ArrayList<>();
        orderColumns.add(cb.asc(root.get(idnrAttr)));
        return orderColumns;
    }

    private Specification<X> hasWorkOrder(final WorkOrder workOrder) {
        final SingularAttribute<Invoer, WorkOrder> workOrderAttr = this.workOrderAttr;
        return new Specification<X>() {
            public Predicate toPredicate(Root<X> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.orderBy(getOrderBy(root, cb));
                return cb.equal(root.get(workOrderAttr), workOrder);
            }
        };
    }
}
