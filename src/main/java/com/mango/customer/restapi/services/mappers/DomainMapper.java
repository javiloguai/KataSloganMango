package com.mango.customer.restapi.services.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base Mapper for Domain services.
 */
public interface DomainMapper<DTO, DOMAIN> {
	/**
	 * Map data transfer object on a domain model. Both contained in Optional container.
	 *
	 * @param optionalA Optional container containing data transfer object
	 * @return Optional container containing domain model object.
	 */
	default Optional<DOMAIN> dtoToDomain(Optional<DTO> optionalA) {
		return optionalA.map(this::dtoToDomain);
	}

	/**
	 * Map domain model object on a data transfer object. Both contained in Optional container.
	 *
	 * @param optionalB Optional container containing domain model object
	 * @return Optional container containing data transfer object.
	 */
	default Optional<DTO> domainToDto(Optional<DOMAIN> optionalB) {
		return optionalB.map(this::domainToDto);
	}

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
	DOMAIN dtoToDomain(DTO dto);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
	DTO domainToDto(DOMAIN domain);

	/**
	 * Some javadoc here
	 *
	 * @param domainList
	 * @return List
	 */
	default List<DTO> domainToDto(List<DOMAIN> domainList) {
		if (domainList == null || domainList.isEmpty()) {
			return List.of();
		} else {
			return domainList.stream().map(this::domainToDto).collect(Collectors.toList());
		}
	}

	/**
	 * Some javadoc here
	 *
	 * @param dtoList List
	 * @return List
	 */
	default List<DOMAIN> dtoToDomain(List<DTO> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return List.of();
		} else {
			return dtoList.stream().map(this::dtoToDomain).collect(Collectors.toList());
		}
	}

	/**
	 * Some javadoc here
	 *
	 * @param domainList
	 * @return List
	 */
	Iterable<DTO> domainToDto(Iterable<DOMAIN> domainList);

	/**
	 * Some javadoc here
	 *
	 * @param dtoList
	 * @return List
	 */
	List<DOMAIN> dtoToDomain(Iterable<DTO> dtoList);

	/**
	 * Some javadoc here
	 *
	 * @param dtoSource
	 * @param domainTarget
	 */
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateDomainWithDto(DTO dtoSource, @MappingTarget DOMAIN domainTarget);

	/**
	 * Some javadoc here
	 *
	 * @param domainSource
	 * @param dtoTarget
	 */
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateDtoWithDomain(DOMAIN domainSource, @MappingTarget DTO dtoTarget);

}
