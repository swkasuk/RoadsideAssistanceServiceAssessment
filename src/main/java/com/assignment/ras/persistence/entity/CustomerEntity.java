package com.assignment.ras.persistence.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "customer")
@NamedQuery(name = "CustomerEntity.findAll", query = "SELECT f FROM CustomerEntity f")
@Builder
public class CustomerEntity extends ContactInfoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUST_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger customerId;

	@Column(name = "ROA_FETR_ENABLED")
	private String roaFeatureEnabled;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<ServiceRequestEntity> custSrvcRequests = new ArrayList<ServiceRequestEntity>();

	public void addSrvRequest(ServiceRequestEntity srvReq) {
		custSrvcRequests.add(srvReq);
		srvReq.setCustomer(this);
	}

	public BigInteger getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}

	public String getRoaFeatureEnabled() {
		return roaFeatureEnabled;
	}

	public void setRoaFeatureEnabled(String roaFeatureEnabled) {
		this.roaFeatureEnabled = roaFeatureEnabled;
	}

	public List<ServiceRequestEntity> getCustSrvcRequests() {
		return custSrvcRequests;
	}

	public void setCustSrvcRequests(List<ServiceRequestEntity> custSrvcRequests) {
		this.custSrvcRequests = custSrvcRequests;
	}

}
