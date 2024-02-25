package com.mango.customer.restapi.persistence.mappers;


import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.restapi.persistence.entities.KataCustomer;
import com.mango.customer.restapi.persistence.entities.KataSlogan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * @author javiruizidneo
 */
/**
 * The Interface KataCustomerDataBaseMapper.
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataCustomerDataBaseMapper extends DatabaseMapper<KataCustomerDomain, KataCustomer> {

	/**
	 * Gets the mapper.
	 *
	 * @return the mapper
	 */
	KataCustomerDataBaseMapper INSTANCE = Mappers.getMapper(KataCustomerDataBaseMapper.class);

	default KataSloganDomain map(KataSlogan slogan) {

		return KataSloganDataBaseMapper.INSTANCE.entityToDomain(slogan);
	}

	default List<KataSloganDomain> mapList(List<KataSlogan> slogans) {
		return KataSloganDataBaseMapper.INSTANCE.entityToDomain(slogans);
	}

	@Mapping(target = "id", ignore = true)
	void copyToEntity(KataCustomerDomain domain, @MappingTarget KataCustomer entity);
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "slogans", ignore = true)
	void copyToEntityIgnoreSlogans(KataCustomerDomain domain, @MappingTarget KataCustomer entity);

}
