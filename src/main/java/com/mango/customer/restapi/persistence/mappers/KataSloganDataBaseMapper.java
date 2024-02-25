package com.mango.customer.restapi.persistence.mappers;


import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.restapi.persistence.entities.KataSlogan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author javiruizidneo
 */

/**
 * The Interface KataSloganDataBaseMapper.
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataSloganDataBaseMapper extends DatabaseMapper<KataSloganDomain, KataSlogan> {

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
	KataSloganDataBaseMapper INSTANCE = Mappers.getMapper(KataSloganDataBaseMapper.class);

}
