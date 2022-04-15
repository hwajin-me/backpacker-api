package com.idus.backpacker.core.kernel.domain.share;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** DDD Concept 의 Value Object 의 Marker Annotation 입니다. */
@Target({TYPE})
@Retention(RUNTIME)
public @interface ValueObject {}
