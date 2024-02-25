package com.mango.customer.restapi.server.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
public class KataCustomerResponse/*extends RepresentationModel<KataCustomerResponse>*/ {


	private long id;

	private String name;

	private String lastName;

	private String address;

	private String city;

	private String email;

	private List<KataSloganResponse> slogans;

}

