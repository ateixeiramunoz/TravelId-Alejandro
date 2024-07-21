package com.eoi.grupo5.controladores;

import com.eoi.grupo5.controladores.criteria.BusquedaCriteriaActividades;
import com.eoi.grupo5.mapper.ActividadesMapper;
import com.eoi.grupo5.modelos.Actividad;
import com.eoi.grupo5.modelos.Precio;

import com.eoi.grupo5.modelos.filtros.ActividadDto;
import com.eoi.grupo5.modelos.filtros.PaginaRespuestaActividades;
import com.eoi.grupo5.servicios.ServicioActividad;
import com.eoi.grupo5.servicios.ServicioTipoActividad;
import com.eoi.grupo5.servicios.filtros.ServicioFiltroActividades;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ActividadController {

    private final ServicioActividad servicioActividad;
    private final ServicioTipoActividad servicioTipoActividad;

    // Filtro
    private final ServicioFiltroActividades servicioFiltroActividades;
    private final ActividadesMapper actividadesMapper;

    public ActividadController(ServicioActividad servicioActividad, ServicioTipoActividad servicioTipoActividad, ServicioFiltroActividades servicioFiltroActividades, ActividadesMapper actividadesMapper) {
        this.servicioActividad = servicioActividad;
        this.servicioTipoActividad = servicioTipoActividad;
        this.servicioFiltroActividades = servicioFiltroActividades;
        this.actividadesMapper = actividadesMapper;
    }

    @GetMapping("/actividades/lista")
    public String listaActividades(Model modelo) {
        List<Actividad> actividades = servicioActividad.buscarEntidades();
        modelo.addAttribute("lista", actividades);
        modelo.addAttribute("preciosActuales", servicioActividad.obtenerPreciosActualesActividades(actividades));
        modelo.addAttribute("tiposActividad", servicioTipoActividad.buscarEntidades());
        return "actividades";
    }

    @GetMapping("/filtrar-actividades")
    public String filtrarActividades(Model modelo, BusquedaCriteriaActividades criteria) {

        if(criteria.getFechaInicio() != null && criteria.getFechaFin() == null || criteria.getFechaInicio() == null && criteria.getFechaFin() !=null) {
            modelo.addAttribute("error", "Debe seleccionar ambas fechas: Fecha de inicio y Fecha de fin.");
        } else {
            if(criteria.getTipoId() == null) {
                List<Actividad> actividades;
                if(criteria.getFechaInicio() != null) {
                    actividades = servicioActividad.buscarPorFechas(criteria.getFechaInicio(), criteria.getFechaFin());
                } else {
                    actividades = servicioActividad.buscarEntidades();
                }
                modelo.addAttribute("lista", actividades);
                modelo.addAttribute("preciosActuales", servicioActividad.obtenerPreciosActualesActividades(actividades));
                modelo.addAttribute("tiposActividad", servicioTipoActividad.buscarEntidades());
                return "actividades";
            } else {
                PaginaRespuestaActividades<ActividadDto> actividades = servicioFiltroActividades
                        .buscarActividades(actividadesMapper.filtrar(criteria), criteria.getPage(), criteria.getSize());
                modelo.addAttribute("lista",actividades);

                // Obtener los precios actuales de las habitaciones del hotel
                LocalDateTime fechaActual = LocalDateTime.now();
                Map<Integer, Double> preciosActuales = new HashMap<>();

                 actividades.forEach(actividad -> {
                    Precio precioActual = actividad.obtenerPrecioActual(actividad, fechaActual);
                    if (precioActual != null) {
                        preciosActuales.put(actividad.getId(), precioActual.getPrecio());
                    } else {
                        preciosActuales.put(actividad.getId(), null);
                    }
                });

                modelo.addAttribute("preciosActuales", preciosActuales);
            }
        }

        modelo.addAttribute("tiposActividad", servicioTipoActividad.buscarEntidades());
        return "actividades";
    }

}
