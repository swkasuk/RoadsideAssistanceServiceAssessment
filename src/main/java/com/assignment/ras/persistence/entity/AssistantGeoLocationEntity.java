package com.assignment.ras.persistence.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "assistant_geo_location")
@NamedQuery(name = "AssistantGeoLocationEntity.findAll", query = "SELECT f FROM AssistantGeoLocationEntity f")

@NamedQuery(name = "AssistantGeoLocationEntity.findByActvAndAvailAsstAndByGeohash", query = "SELECT f FROM AssistantGeoLocationEntity f where "
		+ "f.assistant.activeInd='Y' and f.assistant.reservedInd!='Y' and f.locGeohashTxt = ?1")
@NoArgsConstructor

public class AssistantGeoLocationEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ASSISTANT_LOC_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger asstLocId;

	@OneToOne
	@JoinColumn(name = "ASSISTANT_ID")
	private AssistantEntity assistant;

	@Column(name = "LOC_LAT", precision = 10, scale = 7)
	private BigDecimal latitude;

	@Column(name = "LOC_LONG", precision = 10, scale = 7)
	private BigDecimal longitude;

	@Column(name = "LOC_GEOHASH_TXT")
	private String locGeohashTxt;

	@Transient
	private double distanceBetweenAsstToCust;

	public double getDistanceBetweenAsstToCust() {
		return distanceBetweenAsstToCust;
	}

	public void setDistanceBetweenAsstToCust(double distanceBetweenAsstToCust) {
		this.distanceBetweenAsstToCust = distanceBetweenAsstToCust;
	}

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

	public BigInteger getAsstLocId() {
		return asstLocId;
	}

	public void setAsstLocId(BigInteger asstLocId) {
		this.asstLocId = asstLocId;
	}

	public AssistantEntity getAssistant() {
		return assistant;
	}

	public void setAssistant(AssistantEntity assistant) {
		this.assistant = assistant;
	}

//  @Column(name = "CUST_ID",nullable=true)
//  private BigInteger custId;

//  @OneToOne
//  @JoinColumn(name = "SRVC_REQ_ID")
//  private ServiceRequestEntity serviceRequest;
//  
//  @OneToOne
//  @JoinColumn(name = "CUST_ID")
//  private CustomerEntity customerInSvc;

//	public String getAssistantId() {
//		return assistantId;
//	}
//
//	public void setAssistantId(String assistantId) {
//		this.assistantId = assistantId;
//	}

//	public ServiceRequestEntity getServiceRequest() {
//		return serviceRequest;
//	}
//
//	public void setServiceRequest(ServiceRequestEntity serviceRequest) {
//		this.serviceRequest = serviceRequest;
//	}

//	public BigInteger getCustId() {
//		return custId;
//	}
//
//	public void setCustId(BigInteger custId) {
//		this.custId = custId;
//	}
//   
//    @OneToOne
//    @JoinColumn(name = "cust_id", referencedColumnName = "id")
//    private CustomerEntity customer;

//    @Column(name = "ADDR_LINE_1")
//    private String address1;
//    @Column(name = "ADDR_LINE_2")
//    private String address2;
//    @Column(name = "CITY_NME")
//    private String city;
//    @Column(name = "ZIP_POSTAL_CDE")
//    private String zip;
//    @Column(name = "STATE_CDE")
//    private String state;

}
