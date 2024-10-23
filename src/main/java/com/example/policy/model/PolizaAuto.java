package com.example.policy.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "polizasauto")
public class PolizaAuto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza_auto")
    private Integer idPolizaAuto;

	@OneToOne
	@JoinColumn(name = "id_poliza")
    private Poliza poliza;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private String modelo;
    
    @Column(name = "anio")
    private Integer anio;

    @Column(name = "numero_placa", length = 50)
    private String numeroPlaca;
	
 
    
}
