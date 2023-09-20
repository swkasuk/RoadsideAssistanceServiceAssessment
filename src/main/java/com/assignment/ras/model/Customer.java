package com.assignment.ras.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor

public class Customer extends Geolocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerId;
	private String firstName;
	private String assistantId;
	private String serviceCode;
	private String srvcRequestId;
	private String srvcRequestStatus;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(String assistantId) {
		this.assistantId = assistantId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getSrvcRequestId() {
		return srvcRequestId;
	}

	public void setSrvcRequestId(String srvcRequestId) {
		this.srvcRequestId = srvcRequestId;
	}

	public String getSrvcRequestStatus() {
		return srvcRequestStatus;
	}

	public void setSrvcRequestStatus(String srvcRequestStatus) {
		this.srvcRequestStatus = srvcRequestStatus;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", assistantId=" + assistantId + ", serviceCode=" + serviceCode
				+ ", srvcRequestId=" + srvcRequestId + ", srvcRequestStatus=" + srvcRequestStatus + "]";
	}

}
