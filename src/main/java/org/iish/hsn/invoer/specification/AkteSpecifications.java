package org.iish.hsn.invoer.specification;

import org.iish.hsn.invoer.domain.invoer.Invoer;
import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class AkteSpecifications<X extends Invoer, T extends Integer> {
    private final SingularAttribute<X, T> idnrAttr;
    private final SingularAttribute<Invoer, WorkOrder> workOrderAttr;

    public AkteSpecifications(SingularAttribute<X, T> idnrAttr, SingularAttribute<Invoer, WorkOrder> workOrderAttr) {
        this.idnrAttr = idnrAttr;
        this.workOrderAttr = workOrderAttr;
    }

    public Specification<X> getSpec(WorkOrder workOrder) {
        return Specification.where(hasWorkOrder(workOrder));
    }

    public Specification<X> isBetweenLowestAndHighestIdnr(final int lowestIdnr, final int highestIdnr) {
        return (root, query, cb) -> {
            query.orderBy(getOrderBy(root, cb));
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get(this.idnrAttr), lowestIdnr),
                    cb.lessThanOrEqualTo(root.get(this.idnrAttr), highestIdnr)
            );
        };
    }

    public Specification<X> hasIdnr(final int idnr) {
        final SingularAttribute<X, T> idnrAttr = this.idnrAttr;
        return (root, query, cb) -> cb.equal(root.get(idnrAttr), idnr);
    }

    protected List<Order> getOrderBy(Root<X> root, CriteriaBuilder cb) {
        List<Order> orderColumns = new ArrayList<>();
        orderColumns.add(cb.asc(root.get(idnrAttr)));
        return orderColumns;
    }

    private Specification<X> hasWorkOrder(final WorkOrder workOrder) {
        final SingularAttribute<Invoer, WorkOrder> workOrderAttr = this.workOrderAttr;
        return (root, query, cb) -> {
            query.orderBy(getOrderBy(root, cb));
            return cb.equal(root.get(workOrderAttr), workOrder);
        };
    }
}
