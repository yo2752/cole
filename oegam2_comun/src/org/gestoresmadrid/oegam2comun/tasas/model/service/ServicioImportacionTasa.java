package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.File;
import java.math.BigDecimal;

import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResumenCompraBean;

import escrituras.beans.ResultBean;

public interface ServicioImportacionTasa {

	/**
	 * Servicio de importacion de tasas
	 * @param fichero fichero con las tasas a importar
	 * @param usuario usuario que realiza la importacion
	 * @param contrato contrato al que se asigna la tasa
	 * @param idSession identificador de la session
	 * @param formatoTasa tipo de tasa (pegatina o electronica)
	 * @param numColegiado
	 * @param tienePermisosAdmin
	 * @param tienePermisoColegio
	 * @return
	 */
	ResultBean importarTasas(File fichero, Long idUsuario, BigDecimal contrato, String idSession, FormatoTasa formatoTasa, String numColegiado, Boolean tienePermisosAdmin, Boolean tienePermisoColegio);

	/**
	 * 
	 * @param resumenCompraBean
	 * @param ficheroTasas
	 * @return
	 */
	ResultBean recuperarTasasDesdeFichero(ResumenCompraBean resumenCompraBean, File ficheroTasas);

}