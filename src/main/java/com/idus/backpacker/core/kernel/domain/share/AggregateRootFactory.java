package com.idus.backpacker.core.kernel.domain.share;

import java.io.Serializable;

public interface AggregateRootFactory<T extends BaseAggregateRoot<T, ? extends Serializable>> {
    T create();
}
