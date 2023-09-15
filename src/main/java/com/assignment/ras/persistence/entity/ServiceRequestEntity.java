package com.assignment.ras.persistence.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_request")
@NamedQuery(name = "ServiceRequestEntity.findAll", query = "SELECT f FROM ServiceRequestEntity f")
@NamedQuery(name = "ServiceRequestEntity.findReqByCustIdAndByAsstIdAndBySvcCodeAndByStatus", query = "SELECT f FROM ServiceRequestEntity f where "
		+ " f.customer.customerId = ?1 and f.assistant.assistantID =?2 and f.srvcReqCode =?3 and f.status = ?4")
@NamedQuery(name = "ServiceRequestEntity.findReqByCustIdAndBySvcCodeAndByStatus", query = "SELECT f FROM ServiceRequestEntity f where "
		+ " f.customer.customerId = ?1  and f.srvcReqCode =?2 and f.status = ?3")

public class ServiceRequestEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SRVC_REQ_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer srvcReqId;

	@Column(name = "SRVC_LOC_LAT", precision = 10, scale = 7)
	private BigDecimal srvcLocLatitude;

	@Column(name = "SRVC_LOC_LONG", precision = 10, scale = 7)
	private BigDecimal srvcLocLongitude;

//	@Column(name = "SRVC_LOC_GEOHASH_TXT")
//	private String srvcLocGeohashTxt;

	@ManyToOne
	@JoinColumn(name = "CUST_ID")
	private CustomerEntity customer;

	@ManyToOne
	@JoinColumn(name = "ASSISTANT_ID")
	private AssistantEntity assistant;

	@Column(name = "SRVC_REQ_CODE")
	private String srvcReqCode;

	@Column(name = "SRVC_STATUS")
	// assigned, completed,cancelled
	private String status;

	public Integer getSrvcReqId() {
		return srvcReqId;
	}

	public void setSrvcReqId(Integer srvcReqId) {
		this.srvcReqId = srvcReqId;
	}

	public BigDecimal getSrvcLocLatitude() {
		return srvcLocLatitude;
	}

	public void setSrvcLocLatitude(BigDecimal srvcLocLatitude) {
		this.srvcLocLatitude = srvcLocLatitude;
	}

	public BigDecimal getSrvcLocLongitude() {
		return srvcLocLongitude;
	}

	public void setSrvcLocLongitude(BigDecimal srvcLocLongitude) {
		this.srvcLocLongitude = srvcLocLongitude;
	}

//	public String getSrvcLocGeohashTxt() {
//		return srvcLocGeohashTxt;
//	}
//
//	public void setSrvcLocGeohashTxt(String srvcLocGeohashTxt) {
//		this.srvcLocGeohashTxt = srvcLocGeohashTxt;
//	}

	public String getSrvcReqCode() {
		return srvcReqCode;
	}

	public void setSrvcReqCode(String srvcReqCode) {
		this.srvcReqCode = srvcReqCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public AssistantEntity getAssistant() {
		return assistant;
	}

	public void setAssistant(AssistantEntity assistant) {
		this.assistant = assistant;
	}

}
