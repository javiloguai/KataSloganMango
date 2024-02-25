package com.mango.customer.restapi.services.mappers;

import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.models.dto.KataSloganDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataSloganDomainMapper extends DomainMapper<KataSloganDTO, KataSloganDomain> {

	KataSloganDomainMapper INSTANCE = Mappers.getMapper(KataSloganDomainMapper.class);

}
