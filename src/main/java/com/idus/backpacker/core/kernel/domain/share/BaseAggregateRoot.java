package com.idus.backpacker.core.kernel.domain.share;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at is null")
public abstract class BaseAggregateRoot<T extends BaseAggregateRoot<T, ID>, ID extends Serializable>
        extends AbstractAggregateRoot<T> {
    @EmbeddedId @NonNull protected ID id;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    protected LocalDateTime deletedAt;

    protected BaseAggregateRoot(ID id) {
        this.id = id;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;

        var that = (BaseAggregateRoot<?, ?>) o;

        return Objects.equals(that.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
