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
public class KataSloganRequest {

	@NotNull
	@Size(min = 1, max = 1024)
	private String sloganContent;

}

