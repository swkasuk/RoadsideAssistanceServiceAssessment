package com.assignment.ras.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Assistant extends Geolocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String assistantId;
	private String assistantName;
	private String activeInd;
	private double distanceBetweenCustandAsst;

	public double getDistanceBetweenCustandAsst() {
		return distanceBetweenCustandAsst;
	}

	public void setDistanceBetweenCustandAsst(double distanceBetweenCustandSp) {
		this.distanceBetweenCustandAsst = distanceBetweenCustandSp;
	}

	public String getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(String assistantId) {
		this.assistantId = assistantId;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	@Override
	public String toString() {
		return "Assistant [assistantId=" + assistantId + ", assistantName=" + assistantName + ", activeInd=" + activeInd
				+ ", distanceBetweenCustandAsst=" + distanceBetweenCustandAsst + "]";
	}

	

}
