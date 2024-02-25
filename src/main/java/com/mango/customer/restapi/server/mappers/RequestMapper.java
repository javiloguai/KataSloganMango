package com.mango.customer.restapi.server.mappers;

/**
 * Mapper from requests to domain dto
 *
 * @param <I> the request dto
 * @param <R> the domain dto
 */
public interface RequestMapper<I, R> {

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
	R fromRequestToDto(I input);

}
