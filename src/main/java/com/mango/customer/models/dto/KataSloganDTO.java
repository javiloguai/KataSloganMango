package com.mango.customer.models.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author javiruizidneo
 */
@Data
@Builder
public class KataSloganDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private long id;

	private Long customerId;

	private String sloganContent;

}
