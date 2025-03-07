package org.gestoresmadrid.oegam2comun.trafico.canjes.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.canjes.view.dto.CanjesDto;
import org.gestoresmadrid.oegam2comun.trafico.canjes.view.beans.ResultadoCanjesBean;

import utilidades.web.OegamExcepcion;

public interface ServicioGestionCanjes extends Serializable{

	public static final int MAX_REGISTROS = 20;

	ResultadoCanjesBean imprimirCanje(List<CanjesDto> listado) throws Exception;

	ResultadoCanjesBean anadirCanje(CanjesDto canjesDto, List<CanjesDto> listado);

	ResultadoCanjesBean descontarCreditos(List<CanjesDto> listadoCanjes);

	ResultadoCanjesBean recuperarFichero(String fileName, List<CanjesDto> listadoCanjes) throws OegamExcepcion;

	ResultadoCanjesBean modificarImpresion(List<CanjesDto> listadoCanjes);

}
