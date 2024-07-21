package com.eoi.grupo5.servicios.filtros;

import com.eoi.grupo5.modelos.filtros.ActividadDto;
import com.eoi.grupo5.modelos.filtros.PaginaRespuestaActividades;
import com.eoi.grupo5.repos.filtros.FiltroActividades;

public interface ServicioFiltroActividades {

    PaginaRespuestaActividades<ActividadDto> buscarActividades(FiltroActividades filtro, int page, int size);
}
