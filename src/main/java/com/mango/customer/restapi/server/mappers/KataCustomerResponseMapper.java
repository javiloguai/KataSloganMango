package com.mango.customer.restapi.server.mappers;

import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.restapi.server.responses.KataCustomerResponse;
import com.mango.customer.restapi.server.responses.KataSloganResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The KataCustomerResponseMapper
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataCustomerResponseMapper extends ResponseMapper<KataCustomerDomain, KataCustomerResponse> {

	KataCustomerResponseMapper INSTANCE = Mappers.getMapper(KataCustomerResponseMapper.class);

	default KataSloganResponse map(KataSloganDomain slogan) {

		return KataSloganResponseMapper.INSTANCE.toResponse(slogan);
	}

	default List<KataSloganResponse> mapList(List<KataSloganDomain> slogans) {
		return KataSloganResponseMapper.INSTANCE.toResponses(slogans);
	}


}
