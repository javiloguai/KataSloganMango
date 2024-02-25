package com.mango.customer.restapi.server.controllers;

import com.mango.customer.constants.RequestMappings;
import com.mango.customer.models.domain.KataCustomerDomain;
import com.mango.customer.restapi.server.mappers.KataCustomerRequestMapper;
import com.mango.customer.restapi.server.mappers.KataCustomerResponseMapper;
import com.mango.customer.restapi.server.requests.KataCustomerRequest;
import com.mango.customer.restapi.server.requests.KataSloganRequest;
import com.mango.customer.restapi.server.responses.KataCustomerResponse;
import com.mango.customer.restapi.services.KataCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = false)
@RestController
@RequestMapping(RequestMappings.API + RequestMappings.CUSTOMERS)
public class KataCustomerController extends ExceptionController {

	private static final Logger LOGGER = LogManager.getLogger(KataCustomerController.class);

	private final KataCustomerService kataCustomerService;

	public KataCustomerController(KataCustomerService kataCustomerService) {
		this.kataCustomerService = kataCustomerService;
	}

	// TODO: It is just a simple guide to start, you may create the controllers as you wish. Feel free modifying all stuff implemented here, for instance requested/returned objects.

	/**
	 * Some javadoc here
	 *
	 * @return List
	 */
	@GetMapping
	@Operation(summary = "Gets All the customers", description = "Gets All the customers.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Successful retrieval of customers", content = @Content(array = @ArraySchema(schema = @Schema(implementation = KataCustomerResponse.class)))),
		@ApiResponse(responseCode = "204", description = "EmptyList. Any customer found.", content = {
			@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "Forbidden.", content = {
			@Content(schema = @Schema(), mediaType = "application/json") }) })
	public ResponseEntity<List<KataCustomerResponse>> getAllCustomers() {
		LOGGER.debug("We can log whatever we need...");
		List<KataCustomerDomain> allcustomers = this.kataCustomerService.getAllCustomers();
		LOGGER.debug(
			"If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here.");
		if (allcustomers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(KataCustomerResponseMapper.INSTANCE.toResponses(allcustomers), HttpStatus.OK);

	}

	/**
	 * Some javadoc here
	 *
	 * @return Customer
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Get by id", description = "Gets a customer by his/her id.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Successful retrieval of the customer", content = {
			@Content(schema = @Schema(implementation = KataCustomerResponse.class),mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", description = "Any customer found with the given Id.", content = {
			@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "403", description = "Forbidden.", content = {
			@Content(schema = @Schema()) }) })

	public ResponseEntity<KataCustomerResponse> getCustomerById(@PathVariable(value = "id", required = true) Long id) {
		LOGGER.debug("We can log whatever we need...");
		KataCustomerDomain customer = this.kataCustomerService.findCustomerById(id);
		LOGGER.debug(
			"If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here.");
		return new ResponseEntity<>(KataCustomerResponseMapper.INSTANCE.toResponse(customer), HttpStatus.OK);
	}

	/**
	 * Creates a new Customer
	 *
	 * @param  customerRequest Customer request data
	 * @return the Customer created
	 */
	@Operation(summary = "Create Customer", description = "This api is used to Create a new customer into DB")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Customer successfully added to the DB", content = {
		@Content(schema = @Schema(implementation = KataCustomerResponse.class),mediaType = "application/json") }),
		@ApiResponse(responseCode = "409", description = "Customer Already exist with given name", content = {
			@Content(schema = @Schema()) }) })
	@PostMapping(consumes="application/json")
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseEntity<KataCustomerResponse> createCustomer(@Valid @RequestBody final KataCustomerRequest customerRequest) {

		LOGGER.debug(String.format("Init - Create new Customer %s", customerRequest.toString()));
		KataCustomerDomain customer = this.kataCustomerService.createCustomer(
			KataCustomerRequestMapper.INSTANCE.fromRequestToDto(customerRequest));

		LOGGER.debug(String.format("End - Create new Customer %s", Objects.isNull(customer) ? null : customer.toString()));
		return new ResponseEntity<>(KataCustomerResponseMapper.INSTANCE.toResponse(customer), HttpStatus.CREATED);
	}

	@PutMapping(value =  "/{id}", consumes="application/json")
	@Transactional(propagation = Propagation.REQUIRED)
	@Operation(summary = "Update Customer", description = "Updates an existing customer details by providing the id.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Customer details are successfully updated into the DB", content = {
			@Content(schema = @Schema(implementation = KataCustomerResponse.class),mediaType = "application/json") }),
		@ApiResponse(responseCode = "409", description = "Customer Already exist with given name", content = {
			@Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "404", description = "Customer not found for the given id", content = {
			@Content(schema = @Schema()) }) })

	public ResponseEntity<KataCustomerResponse> updateCustomer(@PathVariable(value = "id", required = true) Long id,
		@RequestBody KataCustomerRequest customerRequest) {
		LOGGER.debug(String.format("Init - Update existing customer %s", customerRequest.toString()));
		KataCustomerDomain customer = this.kataCustomerService.updateCustomer(id,
			KataCustomerRequestMapper.INSTANCE.fromRequestToDto(customerRequest));

		LOGGER.debug(String.format("End - Update existing customer %s", Objects.isNull(customer) ? null : customer.toString()));
		return new ResponseEntity<>(KataCustomerResponseMapper.INSTANCE.toResponse(customer), HttpStatus.OK);
	}

	@PatchMapping(value =  "/{id}", consumes="application/json")
	@Transactional(propagation = Propagation.REQUIRED)
	@Operation(summary = "Add an Slogan", description = "Adds an slogan to an existing customer by providing its id")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Customer slogan is added", content = {
		@Content(schema = @Schema(implementation = KataCustomerResponse.class),mediaType = "application/json") }),
		@ApiResponse(responseCode = "404", description = "Customer not found for the given id", content = {
			@Content(schema = @Schema()) }) })
	public ResponseEntity<KataCustomerResponse> addSloganToCustomer(@PathVariable(value = "id", required = true) Long id,
		@RequestBody KataSloganRequest sloganRequest) {
		LOGGER.debug(String.format("Init - Add a slogan to customer with id %s", id));
		KataCustomerDomain customer = this.kataCustomerService.addSloganToCustomer(id, sloganRequest.getSloganContent());

		LOGGER.debug(String.format("End - Add a slogan to customer %s", Objects.isNull(customer) ? null : customer.toString()));
		return new ResponseEntity<>(KataCustomerResponseMapper.INSTANCE.toResponse(customer), HttpStatus.OK);
	}
}
