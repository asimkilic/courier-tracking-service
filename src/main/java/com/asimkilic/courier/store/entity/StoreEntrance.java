package com.asimkilic.courier.store.entity;

import com.asimkilic.courier.common.entity.BaseEntity;
import com.asimkilic.courier.courier.entity.Courier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serial;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "store_entrance",
        indexes = {@Index(name = "ix_storeEntrance_courierIdCreatedAt", columnList = "courier_id,created_at")})
public class StoreEntrance extends BaseEntity<Long> {

    @Serial
    private static final long serialVersionUID = 865758627950597383L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = false)
    private Courier courier;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
    private Store store;

    @Column(name = "entrance_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant entranceTime;

    @Column(name = "total_trip", nullable = false)
    private Double totalTrip;
}
