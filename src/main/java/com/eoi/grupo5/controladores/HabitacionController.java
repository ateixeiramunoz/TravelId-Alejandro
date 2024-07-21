package com.eoi.grupo5.controladores;

import com.eoi.grupo5.modelos.Habitacion;
import com.eoi.grupo5.modelos.Precio;
import com.eoi.grupo5.servicios.ServicioHabitacion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final ServicioHabitacion servicio;

    public HabitacionController(ServicioHabitacion servicioHabitacion) {
        this.servicio = servicioHabitacion;
    }

    @GetMapping("/{id}")
    public String detallesHabitacion(@PathVariable Integer id, Model modelo) {
        Optional<Habitacion> habitacion = servicio.encuentraPorId(id);
        // Si no encontramos el hotel no hemos encontrado el hotel
        if (habitacion.isPresent()) {

            modelo.addAttribute("habitacion", habitacion.get());
            Precio precioActual = servicio.getPrecioActual(habitacion.get(), LocalDateTime.now());
            modelo.addAttribute("precioActual", precioActual.getPrecio());

            if(!habitacion.get().getImagenesHabitacion().isEmpty()) {
                String habitacionImagen = habitacion.get().getImagenesHabitacion().stream().findFirst().get().getUrl();
                modelo.addAttribute("imagenHabitacion", habitacionImagen);
            }

            return "detallesHabitacion";
        } else {
            // Hotel no encontrado - htlm
            return "habitacionNoEncontrado";
        }
    }


    @GetMapping("/{id}/reservar")
    public String reservarHabitacion(@PathVariable Integer id, Model modelo) {

        Optional<Habitacion> habitacion = servicio.encuentraPorId(id);

        // Si no encontramos el hotel no hemos encontrado el hotel
        if (habitacion.isPresent()) {
            modelo.addAttribute("habitacion", habitacion.get());
            Precio precioActual = servicio.getPrecioActual(habitacion.get(), LocalDateTime.now());
            modelo.addAttribute("precioActual", precioActual.getPrecio());

            return "reservaHabitacion";
        } else {
            // Hotel no encontrado - htlm
            return "habitacionNoEncontrado";
        }


    }




}

