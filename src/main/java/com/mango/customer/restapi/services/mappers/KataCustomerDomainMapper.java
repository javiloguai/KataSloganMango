package com.mango.customer.restapi.services.mappers;


import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.models.dto.KataCustomerDTO;
import com.mango.customer.models.dto.KataSloganDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataCustomerDomainMapper extends DomainMapper<KataCustomerDTO, KataCustomerDomain> {

	KataCustomerDomainMapper INSTANCE = Mappers.getMapper(KataCustomerDomainMapper.class);

	default KataSloganDomain map(KataSloganDTO slogan) {

		return KataSloganDomainMapper.INSTANCE.dtoToDomain(slogan);
	}

	default List<KataSloganDomain> mapDtoList(List<KataSloganDTO> slogans) {
		return KataSloganDomainMapper.INSTANCE.dtoToDomain(slogans);
	}

	default KataSloganDTO map(KataSloganDomain slogan) {

		return KataSloganDomainMapper.INSTANCE.domainToDto(slogan);
	}

	default List<KataSloganDTO> mapList(List<KataSloganDomain> slogans) {
		return KataSloganDomainMapper.INSTANCE.domainToDto(slogans);
	}

}
