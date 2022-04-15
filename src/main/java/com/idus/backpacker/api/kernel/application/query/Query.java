package com.idus.backpacker.api.kernel.application.query;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** CQRS - Query */
@Transactional(readOnly = true)
@Repository
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {}
