package com.assignment.ras.controller;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.ras.api.model.AssistantRequest;
import com.assignment.ras.api.model.CustomerRequest;
import com.assignment.ras.exception.AssistantNotFoundException;
import com.assignment.ras.model.Assistant;
import com.assignment.ras.model.Customer;
import com.assignment.ras.model.Geolocation;
import com.assignment.ras.service.RoadsideAssistanceService;

@RestController
@RequestMapping(value = "/v1")

public class ROAController {

	private static Logger log = LoggerFactory.getLogger(ROAController.class);

	@Autowired
	private RoadsideAssistanceService roaService;

	@PutMapping(value = "/serviceprovider/assistant", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> updateAssistantLoc(@RequestBody AssistantRequest assistantRequest,
			@RequestHeader HttpHeaders headers)

	{
		if (assistantRequest == null) {
			log.error("Error condition.. Invalid input.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {

			Assistant assistant = new Assistant();
			assistant.setAssistantId(assistantRequest.getAssistantId());
			if (assistantRequest.getActiveInd() != null) {
				assistant.setActiveInd(assistantRequest.getActiveInd());
			}

			Geolocation assistantGeoLoc = new Geolocation();
			assistantGeoLoc.setLatitude((assistantRequest.getAsstLocLatitude()));
			assistantGeoLoc.setLongitude(assistantRequest.getAsstLocLongitude());
			roaService.updateAssistantLocation(assistant, assistantGeoLoc);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/customer/{custId}/findNearestAssistants")
	public ResponseEntity<SortedSet<Assistant>> findNearestAssistants(
			@PathVariable(value = "custId", required = true) String custId,
			@RequestParam(value = "custLocLatitude", required = true) BigDecimal latitude,
			@RequestParam(value = "custLocLongitude", required = true) BigDecimal longitude,
			@RequestParam(value = "maxResults", defaultValue = "5", required = false) int limit) {

		Geolocation assistantGeoLoc = new Geolocation();
		assistantGeoLoc.setLatitude(latitude);
		assistantGeoLoc.setLongitude(longitude);

		SortedSet<Assistant> response = roaService.findNearestAssistants(assistantGeoLoc, limit);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new AssistantNotFoundException();
		}

	}

	@PostMapping(value = "/customer/reserveAssistant", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Assistant> assignAssistant(@RequestBody CustomerRequest customerReqObj) {
		Customer customer = new Customer();
		customer.setCustomerId(customerReqObj.getCustId());

		log.debug("Inside reserveAssistant ... ");

		Geolocation assistantGeoLoc = new Geolocation();
		assistantGeoLoc.setLatitude(customerReqObj.getCustLocLat());
		assistantGeoLoc.setLongitude(customerReqObj.getCustLocLong());
		Optional<Assistant> response = roaService.reserveAssistant(customer, assistantGeoLoc);
		if (response != null) {
			return new ResponseEntity<>(response.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/customer/releaseAssistant", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Assistant> releaseAssistant(@RequestBody CustomerRequest customerReqObj) {
		Customer customer = new Customer();
		customer.setCustomerId(customerReqObj.getCustId());
		customer.setSrvcRequestStatus(customerReqObj.getServiceReqStatus());

		Assistant assistant = new Assistant();
		assistant.setAssistantId(customerReqObj.getAssistantId());
		log.debug("Inside releaseAssistant ... ");
		roaService.releaseAssistant(customer, assistant);

		return new ResponseEntity<>(HttpStatus.OK);

	}

}
