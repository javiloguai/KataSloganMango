package com.mango.customer.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author javiruizidneo
 */
@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class KataCustomerDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String lastName;

	private String address;

	private String city;

	private String email;

    private List<KataSloganDomain> slogans;


}
