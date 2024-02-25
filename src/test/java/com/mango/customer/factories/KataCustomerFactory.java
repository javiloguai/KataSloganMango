package com.mango.customer.factories;

import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.models.dto.KataCustomerDTO;
import com.mango.customer.models.dto.KataSloganDTO;
import com.mango.customer.restapi.persistence.entities.KataCustomer;
import com.mango.customer.restapi.persistence.entities.KataSlogan;
import com.mango.customer.restapi.server.requests.KataCustomerRequest;
import com.mango.customer.restapi.server.requests.KataSloganRequest;

import java.util.List;

public class KataCustomerFactory {

	public static Long CUSTOMER_ID = 100L;

	public static Long SLOGAN_ID = 288L;

	public static String NAME = "Homer";
	public static String LASTNAME = "Simpson";

	public static String EMAIL = "homer.simpson@mango.es";

	public static String ADDRESS = "Evergreen Terrace 741";

	public static String CITY = "Springfield";

	public static String LOREM = "I just can\\'t do it. I will write some great, great text on your website\\'s Southern border, and I will make Google pay for that text. Lorem Ispum is a choke artist. Sadly, it\\'s no longer a 10. " +
		"I like text that wasn\\'t captured. I\\'m speaking with myself, number one, because I have a very good brain and I\\'ve said a lot of things. It could be Russia, but it could also be China. Mark my words. " +
		"My placeholder text, I think, is going to end up being very good with women. That\\'s to be expected. They\\'re not sending you, they\\'re sending words that have lots of problems and they\\'re bringing those problems with us.";

	public static KataCustomer getEntity() {
		return getEntity(CUSTOMER_ID);
	}
	public static KataCustomer getEntity(Long customerId) {
		List<KataSlogan> slogans = getSlogansEntity(customerId) ;
		KataCustomer myKataCustomer = KataCustomer.builder()
			.id(customerId)
			.name(NAME)
			.lastName(LASTNAME)
			.address(ADDRESS)
			.city(CITY)
			.email(EMAIL)
			.slogans(slogans).build();

		return myKataCustomer;
	}
	public static List<KataSlogan> getSlogansEntity(Long customerId) {
		return List.of(getSloganEntity(customerId));
	}

	public static KataSlogan getSloganEntity(Long customerId) {
		return getSloganEntity(customerId, SLOGAN_ID);
	}

	public static KataSlogan getSloganEntity(Long customerId,Long sloganId) {
		return KataSlogan.builder()
			.id(sloganId).customerId(customerId).sloganContent(LOREM).build();
	}
	public static KataCustomerDTO getDTO() {
		return getDTO(CUSTOMER_ID);
	}
	public static KataCustomerDTO getDTO(Long customerId) {
		List<KataSloganDTO> slogans = getSlogansDTO(customerId) ;
		KataCustomerDTO myKataCustomerDto = KataCustomerDTO.builder()
			.id(customerId)
			.name(NAME)
			.lastName(LASTNAME)
			.address(ADDRESS)
			.city(CITY)
			.email(EMAIL)
			.slogans(slogans).build();
		return myKataCustomerDto;
	}

	public static List<KataSloganDTO> getSlogansDTO(Long customerId) {
		return List.of(getSloganDTO(customerId));
	}

	public static KataSloganDTO getSloganDTO(Long customerId) {
		return getSloganDTO(customerId, SLOGAN_ID);
	}

	public static KataSloganDTO getSloganDTO(Long customerId,Long sloganId) {
		return KataSloganDTO.builder().id(sloganId).customerId(customerId).sloganContent(LOREM).build();
	}

	public static KataCustomerDomain getDO() {
		return getDO(CUSTOMER_ID);
	}

	public static KataCustomerDomain getDO(Long customerId) {
		List<KataSloganDomain> slogans = getSlogansDO(customerId) ;
		KataCustomerDomain myKataCustomerDomain = KataCustomerDomain.builder()
			.id(customerId)
			.name(NAME)
			.lastName(LASTNAME)
			.address(ADDRESS)
			.city(CITY)
			.email(EMAIL)
			.slogans(slogans).build();
		return myKataCustomerDomain;
	}

	public static List<KataSloganDomain> getSlogansDO(Long customerId) {
		return List.of(getSloganDO(customerId));
	}

	public static KataSloganDomain getSloganDO(Long customerId) {
		return getSloganDO(customerId, SLOGAN_ID);
	}

	public static KataSloganDomain getSloganDO(Long customerId,Long sloganId) {
		return KataSloganDomain.builder().id(sloganId).customerId(customerId).sloganContent(LOREM).build();
	}
	public static KataCustomerRequest getCustomerRequest() {
		KataCustomerRequest req = KataCustomerRequest.builder()
			.name(NAME)
			.lastName(LASTNAME)
			.address(ADDRESS)
			.city(CITY)
			.email(EMAIL)
			.build();
		return req;
	}

	public static KataSloganRequest getSloganRequest() {
		KataSloganRequest req = KataSloganRequest.builder()
			.sloganContent(LOREM)
			.build();
		return req;
	}


}
