package com.assignment.ras.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Builder
public class Geolocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal latitude;
	private BigDecimal longitude;
	private String locGeohashTxt;

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getLocGeohashTxt() {
		return locGeohashTxt;
	}

	public void setLocGeohashTxt(String locGeohashTxt) {
		this.locGeohashTxt = locGeohashTxt;
	}

	@Override
	public String toString() {
		return "Geolocation [latitude=" + latitude + ", longitude=" + longitude + ", locGeohashTxt=" + locGeohashTxt
				+ "]";
	}

}
