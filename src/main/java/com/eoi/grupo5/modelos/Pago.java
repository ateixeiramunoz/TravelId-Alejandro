package com.eoi.grupo5.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "fechaPago")
    private LocalDateTime fechaPago;

    @OneToOne(mappedBy = "pago", cascade = CascadeType.ALL)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "idMetodoPago", foreignKey = @ForeignKey(name = "fkPagosMetodos"), nullable = false)
    private MetodosPago metodoPago;
}
