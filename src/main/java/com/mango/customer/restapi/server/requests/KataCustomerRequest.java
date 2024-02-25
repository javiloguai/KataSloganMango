package com.mango.customer.restapi.server.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KataCustomerRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

	@Size(min = 1, max = 100)
	private String lastName;

	@Size(min = 1, max = 100)
	private String address;

	@Size(min = 1, max = 100)
	private String city;

	@NotNull
	@Size(min = 1, max = 100)
	private String email;

}

