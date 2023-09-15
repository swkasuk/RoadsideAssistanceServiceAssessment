package com.assignment.ras.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.ras.controller.ROAController;
import com.assignment.ras.exception.AssistantNotFoundException;
import com.assignment.ras.exception.RequestNotValidException;
import com.assignment.ras.exception.RoadsideServiceException;
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
import com.assignment.ras.util.GeoHashUtil;
import com.assignment.ras.util.ServiceCodeEnum;
import com.assignment.ras.util.ServiceRequestStatusEnum;

@Component
@Service
public class RoadsideAssistanceServiceImpl implements RoadsideAssistanceService {

	private static Logger logger = LoggerFactory.getLogger(RoadsideAssistanceServiceImpl.class);

	@Autowired
	private AssistantRepository assistantRepo;

	@Autowired
	private AssistantGeoLocationRepository asstGeoLocationRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ServiceRequestRepository serviceReqRepo;

	/**
	 * This method is used to update the location of the roadside assistance service
	 * provider. *
	 * 
	 * @param assistant         represents the roadside assistance service provider
	 * @param assistantLocation represents the location of the roadside assistant
	 */
	@Override
	public void updateAssistantLocation(Assistant assistant, Geolocation assistantLocation) {
		try {
			if (assistant != null && assistantLocation != null) {
				if (assistant.getAssistantId() == null || assistantLocation.getLatitude() == null
						|| assistantLocation.getLongitude() == null) {
					throw new RequestNotValidException("Not a valid Input");
				}
				AssistantEntity assistEntity = null;
				logger.debug("assistant.getId() " + assistant.getAssistantId());
				Optional<AssistantEntity> assistantEntity = assistantRepo
						.findByAssistantID(Integer.valueOf(assistant.getAssistantId()).intValue());

				if (assistantEntity.isPresent()) {
					assistEntity = assistantEntity.get();
					AssistantGeoLocationEntity asstGeoLocationEntity = assistEntity.getAsstGeoLocation();

					if (asstGeoLocationEntity != null) {
						asstGeoLocationEntity.setLatitude(assistantLocation.getLatitude());
						asstGeoLocationEntity.setLongitude(assistantLocation.getLongitude());
						String geoHashtxt = GeoHashUtil.geoHashCalculate(assistantLocation.getLatitude().doubleValue(),
								assistantLocation.getLongitude().doubleValue(), 4);
						asstGeoLocationEntity.setLocGeohashTxt(geoHashtxt);
						if (assistant.getActiveInd() != null) {
							assistEntity.setActiveInd(assistant.getActiveInd());
						}
					}
					assistantRepo.save(assistEntity);
				} else {
					logger.error("updateAssistantLocation : Assistant Not found ");
					throw new AssistantNotFoundException();
				}

			}
		} catch (RequestNotValidException | AssistantNotFoundException exception) {
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in updateAssistantLocation " + e.getMessage());
			throw new RoadsideServiceException("Exception occured when updating Assistant's location");
		}
	}

	@Override
	/**
	 * This method returns a collection of roadside assistants ordered by their
	 * distance from the input geo location. *
	 * 
	 * @param geolocation - geolocation from which to search for assistants
	 * @param limit       - the number of assistants to return
	 * @return a sorted collection of assistants ordered ascending by distance from
	 *         geoLocation
	 */

	public SortedSet<Assistant> findNearestAssistants(Geolocation geolocation, int limit) {

//			if (geolocation != null) {
//				BigDecimal inputLocLat = geolocation.getLatitude();
//				BigDecimal inputLocLong = geolocation.getLongitude();
//				
//				String inputLocationGeoHash = GeoHashUtil.geoHashCalculate(inputLocLat.doubleValue(),
//						inputLocLong.doubleValue(), 4);
//				logger.debug("Begin findNearestAssistants : Latitude:{} ,Longitude :{} , GeoHash:{} ",inputLocLat,inputLocLong,inputLocationGeoHash);
//				
//				List<AssistantGeoLocationEntity> assistantNearByList = asstGeoLocationRepo
//						.findByActvAndAvailAsstAndByGeohash(inputLocationGeoHash);
//				
//				if (assistantNearByList != null && !assistantNearByList.isEmpty()) {
//					logger.debug("Found Nearby Assistants :: " + assistantNearByList.size());
//					
//					for (AssistantGeoLocationEntity nearByAssistant : assistantNearByList) {
//
//						double distanceBetweenCustAndAssistant = GeoHashUtil.distanceBetweenTwoCoordinates(
//								inputLocLat.doubleValue(), inputLocLong.doubleValue(),
//								nearByAssistant.getLatitude().doubleValue(),
//								nearByAssistant.getLongitude().doubleValue());
//						logger.debug("nearByAssistant name :: " + nearByAssistant.getAssistant().getName());
//
//						logger.debug("distanceBetweenCustAndAssistant :: "
//								+ distanceBetweenCustAndAssistant * 0.000621371);
//
//					}
//			
//					SortedSet<AssistantGeoLocationEntity> nearByAssistantList = assistantNearByList.stream()
//							.peek(sp -> sp.setDistanceBetweenAsstToCust(GeoHashUtil.distanceBetweenTwoCoordinates(
//									inputLocLat.doubleValue(), inputLocLong.doubleValue(),
//									sp.getLatitude().doubleValue(), sp.getLongitude().doubleValue())))
//							.collect(Collectors.toCollection(() -> new TreeSet<AssistantGeoLocationEntity>(Comparator
//									.comparingDouble(AssistantGeoLocationEntity::getDistanceBetweenAsstToCust))));
//
//					logger.debug(
//							"before limit nearByAssistantList size :: " + nearByAssistantList.size());
//
//					nearByAssistantList = nearByAssistantList.stream().limit(limit)
//							.collect(Collectors.toCollection(() -> new TreeSet<AssistantGeoLocationEntity>(Comparator
//									.comparingDouble(AssistantGeoLocationEntity::getDistanceBetweenAsstToCust))));
//					logger.debug(
//							"after limit assistantFilteredNearByList size :: " + nearByAssistantList.size());
//
//					System.out.println("nearByAssistantList size :: " + nearByAssistantList.size());
//
//					SortedSet<Assistant> response = mapAssistantListToResponse(
//							Collections.synchronizedSortedSet(nearByAssistantList));
//					return response;
//				}
//
//			}

		try {
			List<AssistantGeoLocationEntity> nearByAssistantList = getNearbyAssistantsSortedAndFiltered(geolocation,
					limit);

			if (nearByAssistantList != null && !nearByAssistantList.isEmpty()) {

				SortedSet<AssistantGeoLocationEntity> nearByAssistantSet = nearByAssistantList.stream()
						.collect(Collectors.toCollection(() -> new TreeSet<AssistantGeoLocationEntity>(
								Comparator.comparingDouble(AssistantGeoLocationEntity::getDistanceBetweenAsstToCust))));

				SortedSet<Assistant> response = mapAssistantListToResponse(
						Collections.synchronizedSortedSet(nearByAssistantSet));
				return response;
			} else {
				logger.error("No Assistants found nearby ");
				throw new AssistantNotFoundException();
			}

		}catch (RequestNotValidException | AssistantNotFoundException exception) {
			throw exception;
		}
		catch (Exception e) {
			logger.error("Exception occured in findNearestAssistants " + e.getMessage());
			throw new RoadsideServiceException("Exception occured when finding near by Assistants");
		}

	}

	/**
	 * This method determines and returns the nearby Assistants by computing the
	 * geohash of the customer's location and then comparing it with the assistants
	 * geohash based on their recent location. The list of near by assistants are
	 * sorted in an ascending order of their distance from the customer' location.
	 * 
	 * @param geolocation
	 * @param limit
	 * @return
	 */
	private List<AssistantGeoLocationEntity> getNearbyAssistantsSortedAndFiltered(Geolocation geolocation, int limit) {
		try {
			if (geolocation != null) {
				BigDecimal inputLocLat = geolocation.getLatitude();
				BigDecimal inputLocLong = geolocation.getLongitude();
				String inputLocationGeoHash = GeoHashUtil.geoHashCalculate(inputLocLat.doubleValue(),
						inputLocLong.doubleValue(), 4);

				logger.debug("Begin getNearbyAssistantsSortedAndFiltered : Latitude:{} ,Longitude :{} , GeoHash:{} ",
						inputLocLat, inputLocLong, inputLocationGeoHash);

				List<AssistantGeoLocationEntity> nearByAssistantList = asstGeoLocationRepo
						.findByActvAndAvailAsstAndByGeohash(inputLocationGeoHash);

				if (nearByAssistantList != null && !nearByAssistantList.isEmpty()) {
					logger.debug("Found Nearby Assistants :: " + nearByAssistantList.size());

					for (AssistantGeoLocationEntity nearByAssistant : nearByAssistantList) {

						double distanceBetweenCustAndAssistant = GeoHashUtil.distanceBetweenTwoCoordinates(
								inputLocLat.doubleValue(), inputLocLong.doubleValue(),
								nearByAssistant.getLatitude().doubleValue(),
								nearByAssistant.getLongitude().doubleValue());
						logger.debug("nearByAssistant name :: " + nearByAssistant.getAssistant().getName());

						logger.debug(
								"distanceBetweenCustAndAssistant :: " + distanceBetweenCustAndAssistant * 0.000621371);
					}
					nearByAssistantList.stream()
							.peek(sp -> sp.setDistanceBetweenAsstToCust(GeoHashUtil.distanceBetweenTwoCoordinates(
									inputLocLat.doubleValue(), inputLocLong.doubleValue(),
									sp.getLatitude().doubleValue(), sp.getLongitude().doubleValue())))
							.min(Comparator.comparingDouble(AssistantGeoLocationEntity::getDistanceBetweenAsstToCust));

					logger.debug("before limit nearByAssistantList size :: " + nearByAssistantList.size());

					nearByAssistantList = nearByAssistantList.stream().limit(limit).toList();

					logger.debug("after limit assistantFilteredNearByList size :: " + nearByAssistantList.size());

					return nearByAssistantList;
				}

			}
		} catch (RequestNotValidException | AssistantNotFoundException exception) {
			throw exception;
		}catch (Exception e) {
			throw new RoadsideServiceException("Exception occured in getNearbyAssistantsSortedAndFiltered");
		}
		return null;
	}

	/**
	 * This method reserves an assistant for a customer that is stranded on the
	 * roadside due to a disabled vehicle. A service request is created in Service
	 * Request table with status Assigned.
	 *
	 * @param customer         - Represents a customer
	 * @param customerLocation - Location of the customer
	 * @return The Assistant that is on their way to help
	 */

	@Override
	@Transactional
	public Optional<Assistant> reserveAssistant(Customer customer, Geolocation customerLocation) {
		try {
			if (customerLocation != null) {
				logger.debug("Begin reserveAssistant in the serviceImpl ");

				BigDecimal custLocLat = customerLocation.getLatitude();
				BigDecimal custLocLong = customerLocation.getLongitude();

				Optional<CustomerEntity> customerEntity = customerRepo
						.findById(Integer.valueOf(customer.getCustomerId()).intValue());

				if (customerEntity.isPresent()) {

					ServiceRequestEntity servReqEntity = serviceReqRepo.findReqByCustIdAndBySvcCodeAndByStatus(
							Integer.valueOf(customer.getCustomerId()).intValue(),
							ServiceCodeEnum.RoadsideAssistance.name(), ServiceRequestStatusEnum.Assigned.name());

					if (servReqEntity != null) {
						logger.error("Assistant already reserved for the customer");
						throw new RequestNotValidException("Assistant already reserved for the customer");

					} else {
						logger.debug("Noservice req found");
						List<AssistantGeoLocationEntity> nearByAssistantList = getNearbyAssistantsSortedAndFiltered(
								customerLocation, 1);
						if (nearByAssistantList != null && !nearByAssistantList.isEmpty()) {

							AssistantGeoLocationEntity asstGeoLocationEntity = nearByAssistantList.get(0);
							logger.debug(
									"Assistant being reserved .... " + asstGeoLocationEntity.getAssistant().getName());

							asstGeoLocationEntity.getAssistant().setReservedInd("Y");
							int assistantId = asstGeoLocationEntity.getAssistant().getAssistantID();

							AssistantEntity assistant = asstGeoLocationEntity.getAssistant();
							asstGeoLocationRepo.save(asstGeoLocationEntity);

							ServiceRequestEntity newServiceReq = new ServiceRequestEntity();
							newServiceReq.setSrvcLocLatitude(custLocLat);
							newServiceReq.setSrvcLocLongitude(custLocLong);
							newServiceReq.setAssistant(assistant);
							newServiceReq.setCustomer(customerEntity.get());
							newServiceReq.setStatus(ServiceRequestStatusEnum.Assigned.name());
							newServiceReq.setSrvcReqCode(ServiceCodeEnum.RoadsideAssistance.name());
							serviceReqRepo.save(newServiceReq);

							Assistant assistantResponse = new Assistant();
							assistantResponse.setAssistantId(assistant.getAssistantID().toString());
							assistantResponse.setAssistantName(assistant.getName());
							assistantResponse.setDistanceBetweenCustandAsst(
									asstGeoLocationEntity.getDistanceBetweenAsstToCust());

							Optional<Assistant> response = Optional.ofNullable(assistantResponse);

							return response;

						}
					}
				} else {
					logger.error("customer Not found");
					throw new RequestNotValidException("customer not found");
				}
			} else {
				logger.error("Customer location is not valid");
			}
		} catch (RequestNotValidException | AssistantNotFoundException exception) {
			throw exception;
		}catch (Exception e) {
			throw new RoadsideServiceException("Exception occured in reserveAssistant");
		}
		return null;
	}

	/**
	 * This method releases an assistant either after they have completed work, or
	 * the customer no longer needs help. Assigned assistant is released by updating
	 * the reserved indicator of Assistant table to 'N' The service request is
	 * updated either with completed status or cancelled status.
	 *
	 * @param customer  - Represents a customer
	 * @param assistant - An assistant that was previously reserved by the customer
	 */
	@Transactional
	@Override
	public void releaseAssistant(Customer customer, Assistant assistant) {

		try {
			logger.debug("==== Begin releaseAssistant in the serviceImpl ====");
			ServiceRequestEntity servReqEntity = serviceReqRepo.findReqByCustIdAndByAsstIdAndBySvcCodeAndByStatus(
					Integer.valueOf(customer.getCustomerId()).intValue(),
					Integer.valueOf(assistant.getAssistantId()).intValue(), ServiceCodeEnum.RoadsideAssistance.name(),
					ServiceRequestStatusEnum.Assigned.name());

			if (servReqEntity != null) {
				String serviceRequestStatus = customer.getSrvcRequestStatus();

				Optional<AssistantEntity> assistantEntity = Optional
						.ofNullable(assistantRepo.findById(Integer.valueOf(assistant.getAssistantId()).intValue())
								.orElseThrow(() -> new AssistantNotFoundException()));
				if (assistantEntity.isPresent()) {
					logger.debug("setting reserved indicator to N");
					assistantEntity.get().setReservedInd("N");
					assistantRepo.save(assistantEntity.get());
				}
				if (ServiceRequestStatusEnum.Completed.name().equalsIgnoreCase(serviceRequestStatus)) {
					logger.debug("setting status to completed");
					servReqEntity.setStatus(ServiceRequestStatusEnum.Completed.name());
				} else if (ServiceRequestStatusEnum.Cancelled.name().equalsIgnoreCase(serviceRequestStatus)) {
					logger.debug("setting status to Cancelled");
					servReqEntity.setStatus(ServiceRequestStatusEnum.Cancelled.name());
				}
				serviceReqRepo.save(servReqEntity);
			} else {
				logger.error("No service request found to release the assistant for the given customer and assistant");
				throw new RequestNotValidException("Invalid input: No service request found to release the assistant for the given customer and assistant ");
			}

		} catch (RequestNotValidException | AssistantNotFoundException exception) {
			throw exception;
			
		} catch (Exception e) {
			throw new RoadsideServiceException("Exception occured in releaseAssistant");
		}
		
	}

	

	/**
	 * This method maps the sorted and filtered assistant list to the response object.
	 * @param assitantListFromDB
	 * @return
	 */
	private SortedSet<Assistant> mapAssistantListToResponse(SortedSet<AssistantGeoLocationEntity> assitantListFromDB) {
		logger.debug("Begin mapAssistantListToResponse :: ");

		SortedSet<Assistant> response = null;
		if (assitantListFromDB != null && !assitantListFromDB.isEmpty()) {
			response = new TreeSet<Assistant>(Comparator.comparingDouble(Assistant::getDistanceBetweenCustandAsst));
			for (AssistantGeoLocationEntity assistantEntity : assitantListFromDB) {
				Assistant assistant = new Assistant();
				assistant.setDistanceBetweenCustandAsst(assistantEntity.getDistanceBetweenAsstToCust());
				assistant.setLatitude(assistantEntity.getLatitude());
				assistant.setLongitude(assistantEntity.getLongitude());
				assistant.setAssistantId(String.valueOf(assistantEntity.getAssistant().getAssistantID()));
				assistant.setAssistantName(assistantEntity.getAssistant().getName());
				response.add(assistant);

			}
		}
		return response;
	}

}
