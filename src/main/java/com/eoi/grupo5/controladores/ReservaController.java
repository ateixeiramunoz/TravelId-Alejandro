package com.eoi.grupo5.controladores;

import com.eoi.grupo5.modelos.Reserva;
import com.eoi.grupo5.modelos.Usuario;
import com.eoi.grupo5.repos.RepoUsuario;
import com.eoi.grupo5.servicios.ServicioReserva;
import com.eoi.grupo5.servicios.ServicioUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ServicioReserva servicioReserva;
    private final RepoUsuario repoUsuario;

    public ReservaController(ServicioReserva servicioReserva, RepoUsuario repoUsuario) {
        this.servicioReserva = servicioReserva;
        this.repoUsuario = repoUsuario;
    }
    

    @PostMapping("/reservar")
    public String crearReservaHabitacion(@RequestParam Integer idHabitacion,
                                         @RequestParam LocalDate fechaInicio,
                                         @RequestParam LocalDate fechaFin,
                                         Principal principal) {
        Optional<Usuario> optionalUsuario = repoUsuario.findByNombreUsuario(principal.getName());
        if (optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            Reserva reserva = servicioReserva.crearReserva(usuario, fechaInicio.atStartOfDay(),fechaFin.atStartOfDay());
            servicioReserva.addHabitacionToReserva(reserva, idHabitacion);
        }
        return "redirect:/reservas";
    }
}
