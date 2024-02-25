package com.mango.customer.restapi.services.impl;


import com.mango.customer.exceptions.AlreadyExistException;
import com.mango.customer.exceptions.BusinessRuleViolatedException;
import com.mango.customer.exceptions.NotFoundException;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.dto.KataCustomerDTO;
import com.mango.customer.restapi.persistence.entities.KataCustomer;
import com.mango.customer.restapi.persistence.entities.KataSlogan;
import com.mango.customer.restapi.persistence.mappers.KataCustomerDataBaseMapper;
import com.mango.customer.restapi.persistence.repositories.KataCustomerRepository;
import com.mango.customer.restapi.persistence.repositories.KataSloganRepository;
import com.mango.customer.restapi.services.BasicService;
import com.mango.customer.restapi.services.KataCustomerService;
import com.mango.customer.restapi.services.mappers.KataCustomerDomainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author javiruizidneo
 * I put some examples of other kind of call I will not implement anything on other examples
 */
@Service
@Validated
@Transactional
public class KataCustomerServiceImpl extends BasicService implements KataCustomerService {

    private static final String ID_MANDATORY = "Id field is Mandatory";

    private static final String NAME_EMPTY = "Customer name cannot be empty";

	private static final String EMAIL_EMPTY = "Customer email cannot be empty";

	private static final String EMAIL_WRONG_FORMAT = "Customer email has not a valid format";

    private static final String SLOGAN_MANDATORY = "Slogan text is Mandatory";

    private static final String CUSTOMER_MANDATORY = "The customer Object is Mandatory";

	private static final String MAXIMUM_SLOGANS = "The maximum number of slogans allowed per Customer has been Reached";

	private static final long MAXIMUM_SLOGANS_ALLOWED = 3L;

	private static final Pattern EMAIL_REGEX = Pattern.compile(
		"^(?=.{1,40}$)[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]{1,20}@([a-zA-Z0-9-]+[.]{1,1})[a-zA-Z0-9-]+$");

    private final KataCustomerRepository kataCustomerRepository;

    private final KataSloganRepository kataSloganRepository;

	public KataCustomerServiceImpl(KataCustomerRepository kataCustomerRepository,
		KataSloganRepository kataSloganRepository) {
		this.kataCustomerRepository = kataCustomerRepository;
		this.kataSloganRepository = kataSloganRepository;
	}

	@Override
    public List<KataCustomerDomain> getAllCustomers() {
        return KataCustomerDataBaseMapper.INSTANCE.entityToDomain(kataCustomerRepository.findAll());
    }


    @Override
    public KataCustomerDomain findCustomerById(final Long id) {
        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        return KataCustomerDataBaseMapper.INSTANCE.entityToDomain(getEntityById(id));
    }


    private KataCustomer getEntityById(final Long id) {
        return kataCustomerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Not found customer with id %s", id)));
    }

    @Override
    public KataCustomerDomain createCustomer(final KataCustomerDTO customerDTO) {
		KataCustomerDomain customerDO = this.validateCustomerToCreate(customerDTO);

		KataCustomer customerE = KataCustomerDataBaseMapper.INSTANCE.domainToEntity(customerDO);

        kataCustomerRepository.saveAndFlush(customerE);

        return KataCustomerDataBaseMapper.INSTANCE.entityToDomain(customerE);
    }



	@Override
	public KataCustomerDomain updateCustomer(Long id, KataCustomerDTO kataCustomerDTO) {
		KataCustomerDomain customerDO = this.validateCustomerToUpdate(id, kataCustomerDTO);

		KataCustomer customerToUpdate = this.getEntityById(id);

		KataCustomerDataBaseMapper.INSTANCE.copyToEntityIgnoreSlogans(customerDO, customerToUpdate);


		kataCustomerRepository.saveAndFlush(customerToUpdate);


		return KataCustomerDataBaseMapper.INSTANCE.entityToDomain(customerToUpdate);
	}

	@Override
	public KataCustomerDomain addSloganToCustomer(Long id, String sloganText) {
		validateAddSloganToCustomer(id, sloganText);

		KataSlogan sloganToAdd = KataSlogan.builder().customerId(id).sloganContent(sloganText).build();

		kataSloganRepository.saveAndFlush(sloganToAdd);

		return KataCustomerDataBaseMapper.INSTANCE.entityToDomain(this.getEntityById(id));
	}

	private KataCustomerDomain validateCustomerToCreate(final KataCustomerDTO customerDTO) {
        this.validateCustomerData(customerDTO);
        this.checkIfEmailAlreadyExists(customerDTO.getEmail());
        return KataCustomerDomainMapper.INSTANCE.dtoToDomain(customerDTO);
    }

	    private KataCustomerDomain validateCustomerToUpdate(final Long id, final KataCustomerDTO customerDTO) {
			if (id == null) {
				throw new BusinessRuleViolatedException(ID_MANDATORY);
			}
			this.validateCustomerData(customerDTO);
			this.checkIfEmailAlreadyExists(id, customerDTO.getEmail());
        return KataCustomerDomainMapper.INSTANCE.dtoToDomain(customerDTO);
    }


	private void checkIfEmailAlreadyExists(final String email) {
		kataCustomerRepository.findFirstByEmailIgnoreCase(email).ifPresent(this::throwAlreadyExistException);
	}

	private void checkIfEmailAlreadyExists(final Long id, final String email) {
		kataCustomerRepository.findFirstByEmailIgnoreCase(email).ifPresent(h -> {
			if (h.getId() != id.longValue()) {
				throwAlreadyExistException(h);
			}
		});
	}

	private void throwAlreadyExistException(final KataCustomer customerEntity) {
		throw new AlreadyExistException("This customer already exists with same email: " + customerEntity.toString());
	}


	private void validateCustomerData(final KataCustomerDTO customerDTO) {
		if (customerDTO == null) {
			throw new BusinessRuleViolatedException(CUSTOMER_MANDATORY);
		}
		if (!StringUtils.hasText(customerDTO.getName())) {
			throw new BusinessRuleViolatedException(NAME_EMPTY);
		}
		if (!StringUtils.hasText(customerDTO.getEmail())) {
			throw new BusinessRuleViolatedException(EMAIL_EMPTY);
		}
		if (!isValidEmail(customerDTO.getEmail())) {
			throw new BusinessRuleViolatedException(EMAIL_WRONG_FORMAT);
		}


	}

	private boolean isValidEmail(final String email) {
		boolean isValid = false;
		if (StringUtils.hasText(email)) {
			isValid = EMAIL_REGEX.matcher(email).matches();
		}
		return isValid;
	}


    private void validateAddSloganToCustomer(final Long id, final String slogan) {

        if (id == null) {
            throw new BusinessRuleViolatedException(ID_MANDATORY);
        }
        if (!StringUtils.hasText(slogan)) {
            throw new BusinessRuleViolatedException(SLOGAN_MANDATORY);
        }

		this.findCustomerById(id);

		long numberOfSlogans = kataSloganRepository.countByCustomerId(id);

        if (numberOfSlogans >= MAXIMUM_SLOGANS_ALLOWED) {
            throw new BusinessRuleViolatedException(MAXIMUM_SLOGANS);
        }
    }



}
