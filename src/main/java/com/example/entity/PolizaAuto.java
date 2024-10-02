package com.example.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "polizasauto")
public class PolizaAuto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza_auto", nullable = false)
    private Integer idPolizaAuto;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_auto")
    private Auto auto;

    @Column(name = "numero_poliza", length = 50, nullable = false)
    private String numeroPoliza;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "monto_asegurado")
    private double montoAsegurado;

    @Column(name = "estado")
    private int estado;
	
    public PolizaAuto() {}

	public Integer getIdPolizaAuto() {
		return idPolizaAuto;
	}

	public void setIdPolizaAuto(Integer idPolizaAuto) {
		this.idPolizaAuto = idPolizaAuto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Auto getAuto() {
		return auto;
	}

	public void setAuto(Auto auto) {
		this.auto = auto;
	}

	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getMontoAsegurado() {
		return montoAsegurado;
	}

	public void setMontoAsegurado(double montoAsegurado) {
		this.montoAsegurado = montoAsegurado;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	};
    
    
    
}
