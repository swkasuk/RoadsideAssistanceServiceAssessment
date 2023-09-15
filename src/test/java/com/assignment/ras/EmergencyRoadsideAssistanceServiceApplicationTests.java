package com.assignment.ras;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest()
@RunWith(SpringRunner.class)

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

	@Autowired
	private RoadsideAssistanceServiceImpl roadsideAssistServiceImpl;

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldUpdateAssistant_IfFound() {
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
		logger.debug("Success");

	}

	@Test
	public void reserveAssistantTest() {
		try {
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

			when(asstGeoLocationRepo.save(asstGeoLocationEntity)).thenReturn(asstGeoLocationEntity);

			newServiceReq.setSrvcReqId(1);
			newServiceReq.setStatus("Assigned");
			newServiceReq.setCustomer(customerEntity);

			when(serviceReqRepo.save(newServiceReq)).thenReturn(newServiceReq);

			Optional<Assistant> reservedAssistant = roadsideAssistServiceImpl.reserveAssistant(cust, customerLocation);
			assertEquals("SP_3", reservedAssistant.get().getAssistantName());
			logger.debug("Success");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void findNearByAssistantsTest() {
		try {
			logger.debug("Test findNearByAssistantsTest");

			Geolocation customerLocation = new Geolocation();
			customerLocation.setLatitude(BigDecimal.valueOf(42.462526));
			customerLocation.setLongitude(BigDecimal.valueOf(-83.376587));

			SortedSet<Assistant> result = roadsideAssistServiceImpl.findNearestAssistants(customerLocation, 5);
			assertEquals(result.size(), 5);
			logger.debug("Success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void releaseAssitant() {
		try {

			logger.debug("Test releaseAssitant");

			Customer customer = new Customer();
			customer.setCustomerId("123");
			Assistant assistant = new Assistant();
			assistant.setAssistantId("3");

			ServiceRequestEntity servReqEntity = new ServiceRequestEntity();

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
			logger.debug("Success");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
