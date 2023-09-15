package com.assignment.ras.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String assistantId;
	private BigDecimal asstLocLatitude;
	private BigDecimal asstLocLongitude;
	private String activeInd;

	public String getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(String assistantId) {
		this.assistantId = assistantId;
	}

	public BigDecimal getAsstLocLatitude() {
		return asstLocLatitude;
	}

	public void setAsstLocLatitude(BigDecimal asstLocLatitude) {
		this.asstLocLatitude = asstLocLatitude;
	}

	public BigDecimal getAsstLocLongitude() {
		return asstLocLongitude;
	}

	public void setAsstLocLongitude(BigDecimal asstLocLongitude) {
		this.asstLocLongitude = asstLocLongitude;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	@Override
	public String toString() {
		return "AssistantRequest [assistantId=" + assistantId + ", asstLocLatitude=" + asstLocLatitude
				+ ", asstLocLongitude=" + asstLocLongitude + ", activeInd=" + activeInd + "]";
	}

}
