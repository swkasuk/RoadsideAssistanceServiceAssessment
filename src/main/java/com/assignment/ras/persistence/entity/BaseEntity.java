package com.assignment.ras.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ADD_BY_USER")
	private String addByUser = "system";

	@Column(name = "REC_ADD_DTTM")
	@CreationTimestamp
	private Timestamp recAddDttm;

	@Column(name = "REC_UPD_DTTM")
	@UpdateTimestamp
	private Timestamp recUpdDttm;

	@Column(name = "UPD_BY_USER")
	private String updByUser = "system";

}
