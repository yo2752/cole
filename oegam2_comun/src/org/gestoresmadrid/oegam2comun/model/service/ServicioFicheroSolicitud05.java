package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans.ResultadoFicheroSolicitud05Bean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;

import utilidades.web.OegamExcepcion;

public interface ServicioFicheroSolicitud05 extends Serializable {

	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public final String NOMBRE_FICHERO = "SOLICITUD_05_";
	public final String NOMBRE_ZIP = "Solicitudes_05";

	ResultadoFicheroSolicitud05Bean generarDatosSolicitud05(TramiteTrafDto tramiteTraficoDto, MarcaDgtDto marcaDgtDto) throws OegamExcepcion;

	ResultadoFicheroSolicitud05Bean validarDatosObligatorios(TramiteTrafDto tramiteTraficoDto, MarcaDgtDto marcaDgtDto) throws OegamExcepcion;

	ResultadoFicheroSolicitud05Bean descargarFichero(String nombreFichero);

	ResultadoFicheroSolicitud05Bean guardarFichero(byte[] bytesFichero, String nombreFichero);

}