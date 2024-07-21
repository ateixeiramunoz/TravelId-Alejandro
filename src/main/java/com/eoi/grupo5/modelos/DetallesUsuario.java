package com.eoi.grupo5.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detallesUsuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallesUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "dni", length = 10)
    private String dni;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name="nombre", length = 45)
    private String nombre;

    @Column(name="apellidos", length = 45)
    private String apellidos;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idUsuario")
    private Usuario usu;

    // Constructores
    public DetallesUsuario(String email) {
        this.email = email;
    }
}
