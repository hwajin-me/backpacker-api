package com.idus.backpacker.core.kernel.domain.share;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAggregateRootSubDomain<T extends BaseAggregateRoot<?, ?>> {
    @Id @GeneratedValue @EqualsAndHashCode.Exclude @ToString.Exclude
    protected UUID id = UUID.randomUUID();

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    protected LocalDateTime updatedAt;
}
