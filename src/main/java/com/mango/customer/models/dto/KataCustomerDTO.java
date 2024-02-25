package com.mango.customer.models.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author javiruizidneo
 */
@Data
@Builder
public class KataCustomerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private String lastName;

	private String address;

	private String city;

	private String email;

	private List<KataSloganDTO> slogans;

}
