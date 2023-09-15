package com.assignment.ras.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String custId;
	private String assistantId;
	private String serviceCode;
	private String srvcRequestId;
	private String serviceReqStatus;

	private BigDecimal custLocLat;
	private BigDecimal custLocLong;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public BigDecimal getCustLocLat() {
		return custLocLat;
	}

	public void setCustLocLat(BigDecimal custLocLat) {
		this.custLocLat = custLocLat;
	}

	public BigDecimal getCustLocLong() {
		return custLocLong;
	}

	public void setCustLocLong(BigDecimal custLocLong) {
		this.custLocLong = custLocLong;
	}

	public String getServiceReqStatus() {
		return serviceReqStatus;
	}

	public void setServiceReqStatus(String serviceReqStatus) {
		this.serviceReqStatus = serviceReqStatus;
	}

	@Override
	public String toString() {
		return "CustomerRequest [custId=" + custId + ", assistantId=" + assistantId + ", serviceCode=" + serviceCode
				+ ", srvcRequestId=" + srvcRequestId + ", serviceReqStatus=" + serviceReqStatus + ", custLocLat="
				+ custLocLat + ", custLocLong=" + custLocLong + "]";
	}

}
