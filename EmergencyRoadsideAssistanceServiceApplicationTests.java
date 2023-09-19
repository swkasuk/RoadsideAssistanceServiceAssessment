package com.assignment.ras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.ras.model.Assistant;
import com.assignment.ras.model.Customer;
import com.assignment.ras.model.Geolocation;
import com.assignment.ras.persistence.entity.AssistantEntity;
import com.assignment.ras.persistence.entity.AssistantGeoLocationEntity;
import com.assignment.ras.persistence.entity.CustomerEntity;
import com.assignment.ras.persistence.entity.ServiceRequestEntity;
import com.assignment.ras.persistence.repositories.AssistantGeoLocationRepository;
import com.assignment.ras.persistence.repositories.AssistantRepository;
import com.assignment.ras.persistence.repositories.CustomerRepository;
import com.assignment.ras.persistence.repositories.ServiceRequestRepository;
import com.assignment.ras.service.RoadsideAssistanceServiceImpl;
import com.assignment.ras.util.ServiceCodeEnum;
import com.assignment.ras.util.ServiceRequestStatusEnum;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest()

class EmergencyRoadsideAssistanceServiceApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(EmergencyRoadsideAssistanceServiceApplicationTests.class);

	@Mock
	private AssistantRepository assistantRepo;

	@Mock
	private AssistantGeoLocationRepository asstGeoLocationRepo;

	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private ServiceRequestRepository serviceReqRepo;

	@InjectMocks
	private RoadsideAssistanceServiceImpl roadsideAssistServiceImpl;

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldUpdateAssistant_IfFound() {
		logger.debug("==============================");
		logger.debug("Test shouldUpdateAssistant_IfFound");

		Assistant assistant = new Assistant();
		assistant.setAssistantId("1");

		Geolocation assistantLocation = new Geolocation();
		assistantLocation.setLatitude(BigDecimal.valueOf(42.462526));
		assistantLocation.setLongitude(BigDecimal.valueOf(-83.376587));

		AssistantEntity asstEntity = new AssistantEntity();

		when(assistantRepo.findByAssistantID(Integer.valueOf(assistant.getAssistantId()).intValue()))
				.thenReturn(Optional.ofNullable(asstEntity));

		when(assistantRepo.save(asstEntity)).thenReturn(asstEntity);

		roadsideAssistServiceImpl.updateAssistantLocation(assistant, assistantLocation);
		logger.debug("Success updateAssistant");
		logger.debug("==============================");

	}

	@Test
	public void reserveAssistantTest() {
		try {
			logger.debug("==============================");
			logger.debug("Test reserveAssistantTest");

			Customer cust = new Customer();
			cust.setCustomerId("123");

			Geolocation customerLocation = new Geolocation();
			customerLocation.setLatitude(BigDecimal.valueOf(42.462526));
			customerLocation.setLongitude(BigDecimal.valueOf(-83.376587));

			CustomerEntity customerEntity = new CustomerEntity();

			AssistantGeoLocationEntity asstGeoLocationEntity = new AssistantGeoLocationEntity();
			ServiceRequestEntity newServiceReq = new ServiceRequestEntity();

			customerEntity.setCustomerId(BigInteger.valueOf(123));

			when(customerRepo.findById(Integer.valueOf(cust.getCustomerId()).intValue()))
					.thenReturn(Optional.ofNullable(customerEntity));

			when(serviceReqRepo.findReqByCustIdAndBySvcCodeAndByStatus(Integer.valueOf(cust.getCustomerId()).intValue(),
					ServiceCodeEnum.RoadsideAssistance.name(), ServiceRequestStatusEnum.Assigned.name()))
							.thenReturn(null);

			List<AssistantGeoLocationEntity> nearByAssistantList = new ArrayList<>();

			AssistantGeoLocationEntity assisGeoLoc1 = new AssistantGeoLocationEntity();
			AssistantEntity assistantEntity = new AssistantEntity();
			assisGeoLoc1.setAssistant(assistantEntity);
			assisGeoLoc1.getAssistant().setName("SP_3");
			assisGeoLoc1.getAssistant().setAssistantID(3);

			assisGeoLoc1.setLatitude(BigDecimal.valueOf(42.48947917637189));
			assisGeoLoc1.setLongitude(BigDecimal.valueOf(-83.50272543783966));

			nearByAssistantList.add(assisGeoLoc1);

			when(asstGeoLocationRepo.findByActvAndAvailAsstAndByGeohash("dps9")).thenReturn(nearByAssistantList);

			when(asstGeoLocationRepo.save(asstGeoLocationEntity)).thenReturn(asstGeoLocationEntity);

			newServiceReq.setSrvcReqId(1);
			newServiceReq.setStatus("Assigned");
			newServiceReq.setCustomer(customerEntity);

			when(serviceReqRepo.save(newServiceReq)).thenReturn(newServiceReq);

			Optional<Assistant> reservedAssistant = roadsideAssistServiceImpl.reserveAssistant(cust, customerLocation);
			assertEquals("SP_3", reservedAssistant.get().getAssistantName());
			logger.debug("Success reserve assistant");
			logger.debug("==============================");

		} catch (Exception e) {
			
		}

	}

	@Test
	public void findNearByAssistantsTest() {
		try {
			logger.debug("==============================");
			logger.debug("Test findNearByAssistantsTest");

			Geolocation customerLocation = new Geolocation();
			customerLocation.setLatitude(BigDecimal.valueOf(42.462526));
			customerLocation.setLongitude(BigDecimal.valueOf(-83.376587));

			List<AssistantGeoLocationEntity> nearByAssistantList = new ArrayList<>();

			AssistantGeoLocationEntity assisGeoLoc1 = new AssistantGeoLocationEntity();
			AssistantEntity assistantEntity = new AssistantEntity();
			assisGeoLoc1.setAssistant(assistantEntity);
			assisGeoLoc1.getAssistant().setName("SP_3");
			assisGeoLoc1.getAssistant().setAssistantID(3);

			assisGeoLoc1.setLatitude(BigDecimal.valueOf(42.48947917637189));
			assisGeoLoc1.setLongitude(BigDecimal.valueOf(-83.50272543783966));

			nearByAssistantList.add(assisGeoLoc1);

			AssistantGeoLocationEntity assisGeoLoc2 = new AssistantGeoLocationEntity();
			AssistantEntity assistantEntity1 = new AssistantEntity();
			assisGeoLoc2.setAssistant(assistantEntity1);
			assisGeoLoc2.getAssistant().setName("SP_4");
			assisGeoLoc2.getAssistant().setAssistantID(4);

			assisGeoLoc2.setLatitude(BigDecimal.valueOf(42.52773000820324));
			assisGeoLoc2.setLongitude(BigDecimal.valueOf(-83.51436493630912));

			nearByAssistantList.add(assisGeoLoc2);

			when(asstGeoLocationRepo.findByActvAndAvailAsstAndByGeohash("dps9")).thenReturn(nearByAssistantList);

			SortedSet<Assistant> result = roadsideAssistServiceImpl.findNearestAssistants(customerLocation, 5);
			assertEquals(result.size(), 2);
			logger.debug("Success findNearByAssistantsTest");
			logger.debug("==============================");
		} catch (Exception e) {
			
		}

	}

	@Test
	public void releaseAssitant() {
		try {
			logger.debug("==============================");
			logger.debug("Test releaseAssitant");

			Customer customer = new Customer();
			customer.setCustomerId("123");
			Assistant assistant = new Assistant();
			assistant.setAssistantId("3");

			ServiceRequestEntity servReqEntity = new ServiceRequestEntity();
			servReqEntity.setSrvcReqId(1);

			when(serviceReqRepo.findReqByCustIdAndByAsstIdAndBySvcCodeAndByStatus(
					Integer.valueOf(customer.getCustomerId()).intValue(),
					Integer.valueOf(assistant.getAssistantId()).intValue(), ServiceCodeEnum.RoadsideAssistance.name(),
					ServiceRequestStatusEnum.Assigned.name())).thenReturn(servReqEntity);

			Optional<AssistantEntity> assistantEntity = Optional.ofNullable(new AssistantEntity());

			when(assistantRepo.findById(Integer.valueOf(assistant.getAssistantId()).intValue()))
					.thenReturn(assistantEntity);

			assistantEntity.get().setReservedInd("N");
			when(assistantRepo.save(assistantEntity.get())).thenReturn(assistantEntity.get());

			servReqEntity.setStatus("Completed");
			when(serviceReqRepo.save(servReqEntity)).thenReturn(servReqEntity);

			roadsideAssistServiceImpl.releaseAssistant(customer, assistant);
			assertEquals("Completed", servReqEntity.getStatus());
			logger.debug("Success release assistant");
			logger.debug("==============================");

		} catch (Exception e) {
			
		}

	}

}
