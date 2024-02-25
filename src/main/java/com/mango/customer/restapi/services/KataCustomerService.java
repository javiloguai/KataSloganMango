package com.mango.customer.restapi.services;

import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.dto.KataCustomerDTO;

import java.util.List;

/**
 * @author javiruizidneo
 */
public interface KataCustomerService {

    List<KataCustomerDomain> getAllCustomers();


    KataCustomerDomain findCustomerById(final Long id);


	KataCustomerDomain createCustomer(final KataCustomerDTO kataCustomerDTO);

	KataCustomerDomain addSloganToCustomer(final Long id, final String sloganText);

	KataCustomerDomain updateCustomer(final Long id, final KataCustomerDTO kataCustomerDTO);


}
