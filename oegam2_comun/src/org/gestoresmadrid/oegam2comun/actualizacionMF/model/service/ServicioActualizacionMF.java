package org.gestoresmadrid.oegam2comun.actualizacionMF.model.service;

import java.io.File;
import java.math.BigDecimal;

import escrituras.beans.ResultBean;

public interface ServicioActualizacionMF {

	ResultBean encolarActualizacion(File ficheroSubido, BigDecimal idContrato,BigDecimal idUsuario);

	ResultBean realizarActualizacion(BigDecimal idActualizacion,BigDecimal idUsuario);

	ResultBean descargarFicheros(String codigos);

}