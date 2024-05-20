package com.asimkilic.courier.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@FilterDef(
        name = "activeFilter",
        defaultCondition = "is_deleted = false"
)
public abstract class BaseEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7412577628032965466L;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Instant createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Instant modifiedAt;


    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;


    @ToString.Include
    @EqualsAndHashCode.Include
    public abstract T getId();

}
