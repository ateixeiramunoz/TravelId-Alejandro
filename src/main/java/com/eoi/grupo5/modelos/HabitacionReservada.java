package com.eoi.grupo5.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "habitacionesReservadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionReservada {

    @EmbeddedId
    private HabitacionReservadaId id;

    @ManyToOne
    @MapsId("idHabitacion")
    @JoinColumn(name = "idHabitacion")
    private Habitacion habitacion;

    @ManyToOne
    @MapsId("idReserva")
    @JoinColumn(name = "idReserva")
    private Reserva reserva;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;


}
