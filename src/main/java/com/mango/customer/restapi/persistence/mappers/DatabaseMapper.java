package com.mango.customer.restapi.persistence.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * @author javiruizidneo
 */

/**
 * Some javadoc here
 */
public interface DatabaseMapper<DOMAIN, ENTITY> {

    /**
	 * Some javadoc here
     *
     * @param
     * @return
     */
    default Optional<ENTITY> mapToOptionalEntity(Optional<DOMAIN> optionalA) {
        return optionalA.map(this::domainToEntity);
    }

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    default Optional<DOMAIN> entityToDomain(Optional<ENTITY> optionalB) {
        return optionalB.map(this::entityToDomain);
    }

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    ENTITY domainToEntity(DOMAIN domain);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    DOMAIN entityToDomain(ENTITY entity);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    default List<ENTITY> domainToEntity(List<DOMAIN> domainList) {
        if (domainList == null || domainList.isEmpty()) {
            return List.of();
        } else {
            return domainList.stream().map(this::domainToEntity).collect(Collectors.toList());
        }
    }

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    default List<DOMAIN> entityToDomain(List<ENTITY> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return List.of();
        } else {
            return entityList.stream().map(this::entityToDomain).collect(Collectors.toList());
        }
    }

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    Iterable<ENTITY> domainToEntity(Iterable<DOMAIN> domainList);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    List<DOMAIN> entityToDomain(Iterable<ENTITY> entityList);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDomainWithEntity(ENTITY entitySource, @MappingTarget DOMAIN domainTarget);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityWithDomain(DOMAIN domainSource, @MappingTarget ENTITY entityTarget);

}
