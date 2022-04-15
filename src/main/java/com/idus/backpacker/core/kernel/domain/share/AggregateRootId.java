package com.idus.backpacker.core.kernel.domain.share;

import java.lang.annotation.*;
import javax.persistence.Embeddable;

@Documented
@Embeddable
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AggregateRootId {}
