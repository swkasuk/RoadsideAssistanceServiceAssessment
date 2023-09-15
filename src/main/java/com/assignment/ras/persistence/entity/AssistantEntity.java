package com.assignment.ras.persistence.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assistant")
@NamedQuery(name = "AssistantEntity.findAll", query = "SELECT f FROM AssistantEntity f")

public class AssistantEntity extends BaseEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ASSISTANT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer assistantID;

	@Column(name = "ASST_NAME")
	private String name;

	@Column(name = "RESERVED_IND")
	private String reservedInd;

	@Column(name = "ACTV_IND")
	private String activeInd;

	@OneToOne(mappedBy = "assistant")
	private AssistantGeoLocationEntity asstGeoLocation;

	@OneToMany(mappedBy = "assistant", fetch = FetchType.LAZY)
	private List<ServiceRequestEntity> srvcRequests;

	public void addSrvRequest(ServiceRequestEntity srvReq) {
		srvcRequests.add(srvReq);
		srvReq.setAssistant(this);
	}

	public Integer getAssistantID() {
		return assistantID;
	}

	public void setAssistantID(Integer assistantID) {
		this.assistantID = assistantID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public String getReservedInd() {
		return reservedInd;
	}

	public void setReservedInd(String reservedInd) {
		this.reservedInd = reservedInd;
	}

	public AssistantGeoLocationEntity getAsstGeoLocation() {
		return asstGeoLocation;
	}

	public void setAsstGeoLocation(AssistantGeoLocationEntity asstGeoLocation) {
		this.asstGeoLocation = asstGeoLocation;
	}

	public List<ServiceRequestEntity> getSrvcRequests() {
		return srvcRequests;
	}

	public void setSrvcRequests(List<ServiceRequestEntity> srvcRequests) {
		this.srvcRequests = srvcRequests;
	}

}
