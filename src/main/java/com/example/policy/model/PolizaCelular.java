package com.example.policy.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "polizascelular")
public class PolizaCelular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_celular")
    private Integer idCelular;

    @OneToOne
    @JoinColumn(name = "id_poliza")
    private Poliza poliza;

    private String marca;
    private String modelo;
}
