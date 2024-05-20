package com.asimkilic.courier.courier.mapper;

import com.asimkilic.courier.common.config.DefaultMapStructConfiguration;
import com.asimkilic.courier.courier.dto.CourierRequest;
import com.asimkilic.courier.courier.dto.LocationRequest;
import com.asimkilic.courier.courier.entity.Courier;
import com.asimkilic.courier.courier.entity.CourierLocation;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapStructConfiguration.class, builder = @Builder(disableBuilder = true))
public interface CourierMapper {

    Courier convert(CourierRequest courierRequest);


    @Mapping(target = "courier", source = "courier")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CourierLocation convert(LocationRequest locationRequest, Courier courier);
}
