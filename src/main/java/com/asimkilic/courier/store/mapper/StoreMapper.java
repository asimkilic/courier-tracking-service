package com.asimkilic.courier.store.mapper;

import com.asimkilic.courier.common.config.DefaultMapStructConfiguration;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapStructConfiguration.class, builder = @Builder(disableBuilder = true))
public interface StoreMapper {

}
