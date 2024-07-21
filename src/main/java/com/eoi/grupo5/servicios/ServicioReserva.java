package com.eoi.grupo5.servicios;

import com.eoi.grupo5.modelos.Habitacion;
import com.eoi.grupo5.modelos.HabitacionReservada;
import com.eoi.grupo5.modelos.Reserva;
import com.eoi.grupo5.modelos.Usuario;
import com.eoi.grupo5.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServicioReserva extends AbstractBusinessServiceSoloEnt<Reserva, Integer, RepoReserva>{

    protected ServicioReserva(RepoReserva repoReserva, RepoHabitacion repoHabitacion, RepoHabitacionReservada repoHabitacionReservada, RepoVuelo repoVuelo, RepoActividad repoActividad) {
        super(repoReserva);
        this.repoReserva = repoReserva;
        this.repoHabitacion = repoHabitacion;
        this.repoHabitacionReservada = repoHabitacionReservada;
        this.repoVuelo = repoVuelo;
        this.repoActividad = repoActividad;
    }

    private final RepoReserva repoReserva;
    private final RepoHabitacion repoHabitacion;
    private final RepoHabitacionReservada repoHabitacionReservada;
    private final RepoVuelo repoVuelo;
    private final RepoActividad repoActividad;

    public Reserva crearReserva(Usuario usuario, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Reserva reserva = new Reserva();
        reserva.setUsu(usuario);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        return repoReserva.save(reserva);
    }

    public void addHabitacionToReserva(Reserva reserva, Integer idHabitacion) {
        Optional<Habitacion> optionalHabitacion = repoHabitacion.findById(idHabitacion);
        if (optionalHabitacion.isPresent()) {
            Habitacion habitacion = optionalHabitacion.get();
            HabitacionReservada habitacionReservada = new HabitacionReservada();
            habitacionReservada.setReserva(reserva);
            habitacionReservada.setHabitacion(habitacion);
            reserva.getHabitacionesReservadas().add(habitacionReservada);
            repoReserva.save(reserva);
            repoHabitacionReservada.save(habitacionReservada);
        } else {
            throw new RuntimeException("No se encontró la habitación");
        }


    }
}
