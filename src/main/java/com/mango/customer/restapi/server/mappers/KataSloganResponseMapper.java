package com.mango.customer.restapi.server.mappers;

import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.restapi.server.responses.KataSloganResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The KataSloganResponseMapper
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataSloganResponseMapper extends ResponseMapper<KataSloganDomain, KataSloganResponse> {

	KataSloganResponseMapper INSTANCE = Mappers.getMapper(KataSloganResponseMapper.class);



}
