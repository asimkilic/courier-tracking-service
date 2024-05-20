package com.asimkilic.courier.courier.entity;

import com.asimkilic.courier.common.annotation.ValidIdentityNumber;
import com.asimkilic.courier.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "courier",
        indexes = {
                @Index(name = "ix_courier_identityNumber", columnList = "identity_number")
        },
        uniqueConstraints = {@UniqueConstraint(name = "ix_unique_identityNumber", columnNames = "identity_number")})
@Filters(@Filter(name = "activeFilter"))
public class Courier extends BaseEntity<Long> {

    @Serial
    private static final long serialVersionUID = 8125339494607621408L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "full_name", length = 100, nullable = false)
    @NotBlank
    private String fullName;

    @Column(name = "identity_number", length = 11, nullable = false)
    @NotBlank
    @ValidIdentityNumber
    private String identityNumber;
}
