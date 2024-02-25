package com.mango.customer.restapi.services.impl;

import com.mango.customer.exceptions.AlreadyExistException;
import com.mango.customer.exceptions.BusinessRuleViolatedException;
import com.mango.customer.exceptions.NotFoundException;
import com.mango.customer.factories.KataCustomerFactory;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.dto.KataCustomerDTO;
import com.mango.customer.restapi.persistence.entities.KataCustomer;
import com.mango.customer.restapi.persistence.entities.KataSlogan;
import com.mango.customer.restapi.persistence.repositories.KataCustomerRepository;
import com.mango.customer.restapi.persistence.repositories.KataSloganRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author javiruizidneo
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@ContextConfiguration
public class KataCustomerServiceImplTest {

	private static final String ID_MANDATORY = "Id field is Mandatory";

	private static final String NAME_EMPTY = "Customer name cannot be empty";

	private static final String EMAIL_EMPTY = "Customer email cannot be empty";

	private static final String EMAIL_WRONG_FORMAT = "Customer email has not a valid format";

	private static final String SLOGAN_MANDATORY = "Slogan text is Mandatory";

	private static final String CUSTOMER_MANDATORY = "The customer Object is Mandatory";

	private static final String MAXIMUM_SLOGANS = "The maximum number of slogans allowed per Customer has been Reached";

	private static final long MAXIMUM_SLOGANS_ALLOWED = 3L;

	@InjectMocks
	private KataCustomerServiceImpl kataCustomerService;

	@MockBean
	private KataCustomerRepository kataCustomerRepository;

	@MockBean
	private KataSloganRepository kataSloganRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		kataCustomerService = new KataCustomerServiceImpl(
			kataCustomerRepository, kataSloganRepository);
	}

	/**
	 * Tests for getAllCustomers method
	 */
	@Nested
	class GetAllCustomersTest {

		@Test
		void givenNonExistingCustomers_thenReturnEmptyList() {
			// given
			final List<KataCustomer> customerlist = List.of();

			Mockito.when(kataCustomerRepository.findAll()).thenReturn(customerlist);

			// when
			final List<KataCustomerDomain> listResultDomain = kataCustomerService.getAllCustomers();

			// then
			Assertions.assertNotNull(listResultDomain);
			Assertions.assertTrue(listResultDomain.isEmpty());
			Assertions.assertEquals(customerlist.size(), listResultDomain.size());
		}


		@Test
		void givenExistingCustomers_thenReturnAllCustomers() {
			// given
			final KataCustomer customer1 = KataCustomerFactory.getEntity(1L);
			final KataCustomer customer2 = KataCustomerFactory.getEntity(2L);

			final List<KataCustomer> customerlist = List.of(customer1, customer2);

			Mockito.when(kataCustomerRepository.findAll()).thenReturn(customerlist);

			// when
			final List<KataCustomerDomain> listResultDomain = kataCustomerService.getAllCustomers();

			// then
			Assertions.assertEquals(customerlist.size(), listResultDomain.size());
			Assertions.assertEquals(customerlist.get(0).getId(), listResultDomain.get(0).getId());
			Assertions.assertEquals(customerlist.get(1).getId(), listResultDomain.get(1).getId());
		}

	}

	/**
	 * Tests for findCustomerById method
	 */
	@Nested
	class FindCustomerByIdTest {

		@Test
		void givenNullId_thenThrowException() {

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.findCustomerById(null));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

		}

		@Test
		void givenNotMatchingId_thenThrowNotFoundException() {

			Mockito.when(kataCustomerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

			final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
				() -> kataCustomerService.findCustomerById(KataCustomerFactory.CUSTOMER_ID));
			Assertions.assertEquals("Not found customer with id " + KataCustomerFactory.CUSTOMER_ID, ex.getMessage());

		}

		@Test
		void givenMatchingId_thenReturnsTheCustomer() {
			// given
			final KataCustomer customer = KataCustomerFactory.getEntity();

			Mockito.when(kataCustomerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(customer));

			// when
			final KataCustomerDomain resultDomain = kataCustomerService.findCustomerById(KataCustomerFactory.CUSTOMER_ID);

			// then
			Assertions.assertNotNull(resultDomain);
			Assertions.assertEquals(customer.getId(), resultDomain.getId());
			Assertions.assertEquals(customer.getName(), resultDomain.getName());
			Assertions.assertEquals(customer.getLastName(), resultDomain.getLastName());
			Assertions.assertEquals(customer.getAddress(), resultDomain.getAddress());
			Assertions.assertEquals(customer.getCity(), resultDomain.getCity());
			Assertions.assertEquals(customer.getEmail(), resultDomain.getEmail());

			Assertions.assertEquals(customer.getSlogans().size(), resultDomain.getSlogans().size());
			Assertions.assertEquals(customer.getSlogans().get(0).getSloganContent(),
				resultDomain.getSlogans().get(0).getSloganContent());

		}

	}

	@Nested
	class CreateCustomerTest {

		@Captor
		private ArgumentCaptor<KataCustomer> createCustomerEntityCaptor;


		@Test
		void givenNullParameters_ThenThrowsException() {
			//given
			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.createCustomer(null));
			Assertions.assertEquals(CUSTOMER_MANDATORY, ex.getMessage());

		}

		@Test
		void givenNotNullDto_WhenDtoIsNotValid_ThenThrowsException() {
			//given
			KataCustomerDTO noName = KataCustomerFactory.getDTO();
			noName.setName(null);

			KataCustomerDTO alreadyExisting = KataCustomerFactory.getDTO();
			KataCustomer alreadyExistingE = KataCustomerFactory.getEntity();
			alreadyExisting.setEmail("alreadyexisting@aaaa.com");
			alreadyExistingE.setEmail("alreadyexisting@aaaa.com");

			KataCustomerDTO wrongEmail = KataCustomerFactory.getDTO();
			wrongEmail.setEmail("aaaaaaaa");

			KataCustomerDTO noEmail = KataCustomerFactory.getDTO();
			noEmail.setEmail(null);

			Mockito.when(kataCustomerRepository.findFirstByEmailIgnoreCase("alreadyexisting@aaaa.com"))
				.thenReturn(Optional.of(alreadyExistingE));

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.createCustomer(noName));
			Assertions.assertEquals(NAME_EMPTY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.createCustomer(wrongEmail));
			Assertions.assertEquals(EMAIL_WRONG_FORMAT, ex2.getMessage());

			final AlreadyExistException ex3 = Assertions.assertThrows(AlreadyExistException.class,
				() -> kataCustomerService.createCustomer(alreadyExisting));
			Assertions.assertTrue(ex3.getMessage().contains("This customer already exists with same email"));

			final BusinessRuleViolatedException ex4 = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.createCustomer(noEmail));
			Assertions.assertEquals(EMAIL_EMPTY, ex4.getMessage());

		}

		@Test
		void givenValidatedDto_ThenCreateCustomer() {
			//given
			KataCustomerDTO validDto = KataCustomerFactory.getDTO();

			Mockito.when(kataCustomerRepository.findFirstByEmailIgnoreCase(ArgumentMatchers.anyString()))
				.thenReturn(Optional.empty());

			//when
			kataCustomerService.createCustomer(validDto);

			//then
			Mockito.verify(kataCustomerRepository, Mockito.atLeast(1)).saveAndFlush(createCustomerEntityCaptor.capture());

			Assertions.assertEquals(validDto.getId(), createCustomerEntityCaptor.getValue().getId());
			Assertions.assertEquals(validDto.getName(), createCustomerEntityCaptor.getValue().getName());
			Assertions.assertEquals(validDto.getLastName(), createCustomerEntityCaptor.getValue().getLastName());
			Assertions.assertEquals(validDto.getAddress(), createCustomerEntityCaptor.getValue().getAddress());
			Assertions.assertEquals(validDto.getCity(), createCustomerEntityCaptor.getValue().getCity());
			Assertions.assertEquals(validDto.getEmail(), createCustomerEntityCaptor.getValue().getEmail());
		}

	}

	@Nested
	class UpdateCustomerTest {

		@Captor
		private ArgumentCaptor<KataCustomer> updateCustomerEntityCaptor;


		@Test
		void givenNullParameters_ThenThrowsException() {
			//given
			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.updateCustomer(null, KataCustomerFactory.getDTO()));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.updateCustomer(KataCustomerFactory.CUSTOMER_ID, null));
			Assertions.assertEquals(CUSTOMER_MANDATORY, ex2.getMessage());

		}

		@Test
		void givenNotNullDto_WhenDtoIsNotValid_ThenThrowsException() {
			//given
			KataCustomerDTO noName = KataCustomerFactory.getDTO();
			noName.setName(null);

			KataCustomerDTO noEmail = KataCustomerFactory.getDTO();
			noEmail.setEmail(null);

			KataCustomerDTO alreadyExisting = KataCustomerFactory.getDTO();
			KataCustomer alreadyExistingE = KataCustomerFactory.getEntity();
			alreadyExistingE.setId(288L);
			alreadyExisting.setEmail("alreadyexisting@aaaa.com");
			alreadyExistingE.setEmail("alreadyexisting@aaaa.com");

			KataCustomerDTO wrongEmail = KataCustomerFactory.getDTO();
			wrongEmail.setEmail("aaaaaaaa");

			Mockito.when(kataCustomerRepository.findFirstByEmailIgnoreCase("alreadyexisting@aaaa.com"))
				.thenReturn(Optional.of(alreadyExistingE));

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.updateCustomer(null, KataCustomerFactory.getDTO()));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.updateCustomer(KataCustomerFactory.CUSTOMER_ID, noName));
			Assertions.assertEquals(NAME_EMPTY, ex2.getMessage());

			final BusinessRuleViolatedException ex3 = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.updateCustomer(KataCustomerFactory.CUSTOMER_ID, noEmail));
			Assertions.assertEquals(EMAIL_EMPTY, ex3.getMessage());

			final AlreadyExistException ex4 = Assertions.assertThrows(AlreadyExistException.class,
					() -> kataCustomerService.updateCustomer(KataCustomerFactory.CUSTOMER_ID,alreadyExisting));
			Assertions.assertTrue(ex4.getMessage().contains("This customer already exists with same email"));

		}

		@Test
		void givenValidatedDto_ThenUpdatesCustomer() {
			//given

			KataCustomerDTO validDto = KataCustomerFactory.getDTO();
			validDto.setId(288L);
			KataCustomer validEntity = KataCustomerFactory.getEntity();
			validEntity.setId(288L);

			Mockito.when(kataCustomerRepository.findFirstByEmailIgnoreCase(ArgumentMatchers.anyString()))
				.thenReturn(Optional.empty());
			Mockito.when(kataCustomerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(validEntity));
			//when
			kataCustomerService.updateCustomer(KataCustomerFactory.CUSTOMER_ID, validDto);

			//then
			Mockito.verify(kataCustomerRepository, Mockito.atLeast(1)).saveAndFlush(updateCustomerEntityCaptor.capture());

			Assertions.assertEquals(validDto.getId(), updateCustomerEntityCaptor.getValue().getId());
			Assertions.assertEquals(validDto.getName(), updateCustomerEntityCaptor.getValue().getName());
			Assertions.assertEquals(validDto.getLastName(), updateCustomerEntityCaptor.getValue().getLastName());
			Assertions.assertEquals(validDto.getAddress(), updateCustomerEntityCaptor.getValue().getAddress());
			Assertions.assertEquals(validDto.getCity(), updateCustomerEntityCaptor.getValue().getCity());
			Assertions.assertEquals(validDto.getEmail(), updateCustomerEntityCaptor.getValue().getEmail());
		}

	}

	@Nested
	class AddSloganToCustomerTest {

		@Captor
		private ArgumentCaptor<KataSlogan> sloganEntityCaptor;

		@Test
		void givenNullParameters_ThenThrowsException() {
			//given

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.addSloganToCustomer(null, KataCustomerFactory.LOREM));
			Assertions.assertEquals(ID_MANDATORY, ex.getMessage());

			final BusinessRuleViolatedException ex2 = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.addSloganToCustomer(KataCustomerFactory.CUSTOMER_ID, null));
			Assertions.assertEquals(SLOGAN_MANDATORY, ex2.getMessage());

		}

		@Test
		void givenNonExistingCustomer_ThenThrowsNotFoundException() {
			//given

			Mockito.when(kataCustomerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

			final NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
				() -> kataCustomerService.addSloganToCustomer(KataCustomerFactory.CUSTOMER_ID, KataCustomerFactory.LOREM));
			Assertions.assertEquals("Not found customer with id " + KataCustomerFactory.CUSTOMER_ID, ex.getMessage());

		}

		@Test
		void givenExistingCustomer_WhenHasReachedMaxSlogans_ThenThrowsException() {
			//given

			final KataCustomer c1 = KataCustomerFactory.getEntity();

			Mockito.when(kataCustomerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(c1));

			Mockito.when(kataSloganRepository.countByCustomerId(ArgumentMatchers.anyLong())).thenReturn(MAXIMUM_SLOGANS_ALLOWED);

			final BusinessRuleViolatedException ex = Assertions.assertThrows(BusinessRuleViolatedException.class,
				() -> kataCustomerService.addSloganToCustomer(KataCustomerFactory.CUSTOMER_ID, KataCustomerFactory.LOREM));
			Assertions.assertEquals(MAXIMUM_SLOGANS, ex.getMessage());

		}

		@Test
		void givenExistingCustomer_WhenHasNotReachedMaxSlogans_ThenAddSlogan() {
			//given
			final KataCustomer c1 = KataCustomerFactory.getEntity();

			Mockito.when(kataCustomerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(c1));
			Mockito.when(kataSloganRepository.countByCustomerId(ArgumentMatchers.anyLong())).thenReturn(2L);

			//when
			KataCustomerDomain result = kataCustomerService.addSloganToCustomer(KataCustomerFactory.CUSTOMER_ID,
				KataCustomerFactory.LOREM);

			//then
			Mockito.verify(kataSloganRepository, Mockito.times(1))
				.saveAndFlush(sloganEntityCaptor.capture());
			Assertions.assertEquals(KataCustomerFactory.LOREM,
				sloganEntityCaptor.getValue().getSloganContent());
			Assertions.assertNotNull(result);
			List<String> slogans = result.getSlogans().stream().map(p -> p.getSloganContent()).distinct()
				.collect(Collectors.toList());
			//TODO
			// Has to Mock customerRepository.findById the second time to return an updated customer
			Assertions.assertTrue(slogans.contains(KataCustomerFactory.LOREM));
		}

	}

}
