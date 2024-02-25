package com.mango.customer.restapi.server.mappers;

import java.util.List;

/**
 * Mapper from Domain entity to response DTO
 *
 * @param <I> input
 * @param <R> Response
 */
public interface ResponseMapper<I, R> {

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
	R toResponse(I input);

	/**
	 * Some javadoc here
	 *
	 * @param
	 * @return
	 */
	List<R> toResponses(List<I> inputs);

}
