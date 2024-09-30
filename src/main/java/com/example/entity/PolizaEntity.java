package com.example.entity;

import java.security.Timestamp;

public class PolizaEntity {
	private Timestamp dateStart;
	private Timestamp dateEnd;
	private Integer idSeguro;
	
	public PolizaEntity(Timestamp dateStart, Timestamp dateEnd, Integer idSeguro) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.idSeguro = idSeguro;
	}
	
	public Timestamp getDateStart() {
		return dateStart;
	}
	public void setDateStart(Timestamp dateStart) {
		this.dateStart = dateStart;
	}
	public Timestamp getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Integer getIdSeguro() {
		return idSeguro;
	}
	public void setIdSeguro(Integer idSeguro) {
		this.idSeguro = idSeguro;
	}
	
	
	
}
