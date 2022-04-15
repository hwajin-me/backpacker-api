package com.idus.backpacker.api.kernel.application.query;

import com.idus.backpacker.core.kernel.domain.share.BaseAggregateRoot;
import lombok.NonNull;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class BaseQuerySupport<T extends BaseAggregateRoot<T, ?>>
        extends QuerydslRepositorySupport {
    /**
     * QueryDSL Constructor
     *
     * @param clazz Aggregate root
     */
    public BaseQuerySupport(@NonNull Class<T> clazz) {
        super(clazz);
    }

    /**
     * Get QueryDSL Object (Anti-corruption method)
     *
     * @return ACL QueryDSL Object
     */
    protected Querydsl getSupport() {
        return this.getQuerydsl();
    }
}
