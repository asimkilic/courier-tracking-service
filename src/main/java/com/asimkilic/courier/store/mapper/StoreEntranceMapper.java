package com.asimkilic.courier.store.mapper;

import com.asimkilic.courier.common.config.DefaultMapStructConfiguration;
import com.asimkilic.courier.courier.entity.Courier;
import com.asimkilic.courier.store.entity.Store;
import com.asimkilic.courier.store.entity.StoreEntrance;
import java.time.Instant;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapStructConfiguration.class, builder = @Builder(disableBuilder = true))
public interface StoreEntranceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    StoreEntrance convert(Store store, Courier courier, Instant entranceTime, Double totalTrip);
}
