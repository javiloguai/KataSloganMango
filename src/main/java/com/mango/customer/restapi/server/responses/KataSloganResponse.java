package com.mango.customer.restapi.server.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
public class KataSloganResponse /*extends RepresentationModel<KataSloganResponse>*/ {


	private long id;

	private Long customerId;

	private String sloganContent;

}

