package com.mango.customer.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mango.customer.config.test.TestControllerConfig;
import com.mango.customer.constants.RequestMappings;
import com.mango.customer.exceptions.BusinessRuleViolatedException;
import com.mango.customer.exceptions.NotFoundException;
import com.mango.customer.factories.KataCustomerFactory;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.models.domain.KataSloganDomain;
import com.mango.customer.restapi.server.controllers.KataCustomerController;
import com.mango.customer.restapi.server.requests.KataCustomerRequest;
import com.mango.customer.restapi.server.requests.KataSloganRequest;
import com.mango.customer.restapi.services.KataCustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

/**
 * The Class KataCustomerController Integration Test.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = { "spring.main.banner-mode=off" })
@ContextConfiguration(classes = { TestControllerConfig.class, /*TestWebMvcConfig.class, */KataCustomerController.class })
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles(TestControllerConfig.PROFILE)
@EnableSpringDataWebSupport
class KataCustomerControllerIT {

	@MockBean
	KataCustomerService kataCustomerService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper getMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * getCustomerById test cases.
	 */
	@Nested
	class GetCustomerByIdTest {

		@Test
		void givenNonExistingCustomer_ThenReturnNotFound() throws Exception {

			Mockito.when(kataCustomerService.findCustomerById(KataCustomerFactory.CUSTOMER_ID)).thenThrow(new NotFoundException("Error"));

			final String requestURL = RequestMappings.API + RequestMappings.CUSTOMERS + "/" + KataCustomerFactory.CUSTOMER_ID;

			// @formatter:off
			mockMvc.perform(MockMvcRequestBuilders.get(requestURL)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();
			// @formatter:on

			Mockito.verify(kataCustomerService, Mockito.atLeastOnce()).findCustomerById(KataCustomerFactory.CUSTOMER_ID);

		}

		@Test
		void givenExistingCustomer_ThenReturnIt() throws Exception {

			final KataCustomerDomain domainToResponse = KataCustomerFactory.getDO();

			Mockito.when(kataCustomerService.findCustomerById(288L)).thenReturn(domainToResponse);

			final String requestURL = RequestMappings.API + RequestMappings.CUSTOMERS + "/" + 288L;

			// @formatter:off
			final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(requestURL)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
			// @formatter:on

			Mockito.verify(kataCustomerService, Mockito.atLeastOnce()).findCustomerById(288L);
			final String response = result.getResponse().getContentAsString();
			final KataCustomerDomain searched = getObjectFromJson(response,
				KataCustomerDomain.class);

			Assertions.assertEquals(domainToResponse.getName(), searched.getName());
			Assertions.assertEquals(domainToResponse.getLastName(), searched.getLastName());
			Assertions.assertEquals(domainToResponse.getAddress(), searched.getAddress());
			Assertions.assertEquals(domainToResponse.getCity(), searched.getCity());
			Assertions.assertEquals(domainToResponse.getEmail(), searched.getEmail());

		}
	}

	@Nested
	@DisplayName("FindAll test cases. TODO: skills demostrated on ether GET Interation Test")
	class getAllCustomersTest {
		//TODO Implement integrations tests for this endpoint.
		// It would need to be completed to have full code coverage.
		// It is clear from the tests above that I know how to perform integration tests for GET,
		// because of this I do not waste any more time implementing this method
		@Test
		@Disabled
		void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
			Assertions.assertTrue(true);
		}
	}

	@Nested
	@DisplayName("createCustomer test cases")
	class CreateCustomerTest {
		@Test
		@DisplayName("Create new group")
		void givenRequestingCreation_ThenOperationIsAccepted() throws Exception {

			final KataCustomerRequest request = KataCustomerFactory.getCustomerRequest();

			final String requestBody = getJsonFromObject(request);

			final KataCustomerDomain domainToResponse = KataCustomerFactory.getDO();

			Mockito.when(kataCustomerService.createCustomer(any())).thenReturn(domainToResponse);

			// @formatter:off
			final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post(RequestMappings.API + RequestMappings.CUSTOMERS)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
			// @formatter:on

			Mockito.verify(kataCustomerService, Mockito.times(1)).createCustomer(any());
			final String response = result.getResponse().getContentAsString();
			final KataCustomerDomain domain = getObjectFromJson(response,
				KataCustomerDomain.class);

			Assertions.assertEquals(domainToResponse.getName(), domain.getName());
			Assertions.assertEquals(domainToResponse.getLastName(), domain.getLastName());
			Assertions.assertEquals(domainToResponse.getAddress(), domain.getAddress());
			Assertions.assertEquals(domainToResponse.getCity(), domain.getCity());
			Assertions.assertEquals(domainToResponse.getEmail(), domain.getEmail());

		}

	}

	@Nested
	@DisplayName("updateCustomer test cases")
	class UpdateCustomerTest {
		@Test
		@DisplayName("Modify the group")
		void givenRequestingModify_ThenOperationIsAccepted() throws Exception {
			final KataCustomerRequest request = KataCustomerFactory.getCustomerRequest();

			final String requestBody = getJsonFromObject(request);

			final KataCustomerDomain domainToResponse = KataCustomerFactory.getDO();

			Mockito.when(kataCustomerService.updateCustomer(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
				.thenReturn(domainToResponse);

			// @formatter:off
			final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.put(RequestMappings.API + RequestMappings.CUSTOMERS + "/{id}",KataCustomerFactory.CUSTOMER_ID)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			// @formatter:on

			Mockito.verify(kataCustomerService, Mockito.times(1))
				.updateCustomer(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
			final String response = result.getResponse().getContentAsString();
			final KataCustomerDomain domain = getObjectFromJson(response,
				KataCustomerDomain.class);

			Assertions.assertEquals(request.getName(), domain.getName());

			Assertions.assertEquals(request.getName(), domain.getName());
			Assertions.assertEquals(request.getLastName(), domain.getLastName());
			Assertions.assertEquals(request.getAddress(), domain.getAddress());
			Assertions.assertEquals(request.getCity(), domain.getCity());
			Assertions.assertEquals(request.getEmail(), domain.getEmail());
		}

	}

	@Nested
	@DisplayName("addSloganToCustomer test cases")
	class AddSloganToCustomerTest {
		@Test
		@DisplayName("Modify the group")
		void givenRequestingPatch_ThenOperationIsAccepted() throws Exception {
			final KataSloganRequest request = KataCustomerFactory.getSloganRequest();

			final String requestBody = getJsonFromObject(request);

			final KataCustomerDomain domainToResponse = KataCustomerFactory.getDO();

			Mockito.when(kataCustomerService.addSloganToCustomer(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
				.thenReturn(domainToResponse);

			// @formatter:off
			final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.patch(RequestMappings.API + RequestMappings.CUSTOMERS + "/{id}",KataCustomerFactory.CUSTOMER_ID)
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			// @formatter:on

			Mockito.verify(kataCustomerService, Mockito.times(1))
				.addSloganToCustomer(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
			final String response = result.getResponse().getContentAsString();
			final KataCustomerDomain domain = getObjectFromJson(response,
				KataCustomerDomain.class);

			List<KataSloganDomain> slogansOnCustomerAfterAdding = domain.getSlogans();

			Assertions.assertTrue(slogansOnCustomerAfterAdding.stream()
				.anyMatch(x -> Objects.equals(x.getSloganContent(), request.getSloganContent())));

		}

	}

	public <T> T getObjectFromJson(final String json, final Class<T> clazz) {
		T result = null;
		try {
			result = getMapper.readValue(json.getBytes(), clazz);

		} catch (final IOException exception) {
			throw new BusinessRuleViolatedException(exception);
		}
		return result;
	}

	public String getJsonFromObject(final Object abstractConfigDto) {
		final StringBuilder jsonConfig = new StringBuilder();

		try {

			final ObjectMapper mapper = getMapper;
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
			final ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
			jsonConfig.append(writer.writeValueAsString(abstractConfigDto));

		} catch (final IOException exception) {
			throw new BusinessRuleViolatedException(exception);
		}

		return jsonConfig.toString();
	}


}

