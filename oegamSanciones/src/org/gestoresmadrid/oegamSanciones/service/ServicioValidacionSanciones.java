package org.gestoresmadrid.oegamSanciones.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamSanciones.view.beans.ResultadoSancionesBean;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionDto;

public interface ServicioValidacionSanciones extends Serializable {
	ResultadoSancionesBean validacionImprimirListado(String[] codSeleccionados);

	ResultadoSancionesBean validacionGuardarSancion(SancionDto sancionDto);
}
