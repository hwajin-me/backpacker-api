package com.idus.backpacker.core.kernel.domain.share;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregateRootRepository<
                A extends BaseAggregateRoot<A, ID>, ID extends Serializable>
        extends JpaRepository<A, ID> {
    @Override
    default void delete(A aggregate) {
        aggregate.delete();
    }
}
