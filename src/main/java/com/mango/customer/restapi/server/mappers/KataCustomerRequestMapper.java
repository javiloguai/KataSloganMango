package com.mango.customer.restapi.server.mappers;


import com.mango.customer.constants.MapperConstants;
import com.mango.customer.models.dto.KataCustomerDTO;
import com.mango.customer.restapi.server.requests.KataCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The KataCustomerRequestMapper
 */
@Mapper(componentModel = MapperConstants.COMPONENT_MODEL)
public interface KataCustomerRequestMapper extends RequestMapper<KataCustomerRequest, KataCustomerDTO> {

	KataCustomerRequestMapper INSTANCE = Mappers.getMapper(KataCustomerRequestMapper.class);

    /**
     * From requests to dtos.
     *
     * @param requests the requests
     * @return the mapped result
     */
    List<KataCustomerDTO> fromRequestsToDtos(List<KataCustomerDTO> requests);


}
