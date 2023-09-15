package com.assignment.ras.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.ras.persistence.entity.ServiceRequestEntity;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequestEntity, Integer> {

	ServiceRequestEntity findReqByCustIdAndByAsstIdAndBySvcCodeAndByStatus(int custId, int assistanceId,
			String serviceCode, String status);

	ServiceRequestEntity findReqByCustIdAndBySvcCodeAndByStatus(int custId, String serviceCode, String status);

}
