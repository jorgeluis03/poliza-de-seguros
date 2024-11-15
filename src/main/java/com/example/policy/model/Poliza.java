package com.example.policy.model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.example.policy.enums.EstadoPoliza;
import com.example.user.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "polizas")
public class Poliza implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza")
	private Integer idPoliza;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "numero_poliza")
    private String numeroPoliza;
	
	@Column(name = "tipo_poliza")
    private Integer tipoPoliza;
	
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

	@Positive(message = "{campo.invalido}")
    @Column(name = "monto_asegurado")
    private Double montoAsegurado;

	@Enumerated(EnumType.STRING)
    @Column(name = "estado" )
    private EstadoPoliza estado;

	@OneToOne(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
	private PolizaAuto polizaAuto;

	@OneToOne(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
	private PolizaCelular polizaCelular;

	@OneToOne(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
	private PolizaInmueble polizaInmueble;

}
