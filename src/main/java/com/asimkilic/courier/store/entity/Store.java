package com.asimkilic.courier.store.entity;

import com.asimkilic.courier.common.annotation.Crop;
import com.asimkilic.courier.common.entity.BaseEntity;
import com.asimkilic.courier.common.util.FieldUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "store",
        indexes = {
                @Index(name = "ix_store_name", columnList = "name"),
                @Index(name = "ix_store_latLon", columnList = "latitude,longitude"),
                @Index(name = "ix_store_lat", columnList = "latitude"),
                @Index(name = "ix_store_lon", columnList = "longitude")
        },
        uniqueConstraints = {@UniqueConstraint(name = "ix_unique_storeName", columnNames = "name")})
@Filters(
        @Filter(name = "activeFilter")
)
public class Store extends BaseEntity<Long> {

    @Serial
    private static final long serialVersionUID = 3077315492850989990L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", length = 150, nullable = false)
    @NotBlank
    @Crop(150)
    private String name;

    @JsonProperty("lat")
    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @JsonProperty("lng")
    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @PreUpdate
    @PrePersist
    void cropField() {
        FieldUtils.cropValueWithAnnotation(this);
    }
}
