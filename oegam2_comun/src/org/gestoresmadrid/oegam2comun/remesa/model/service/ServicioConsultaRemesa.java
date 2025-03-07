package org.gestoresmadrid.oegam2comun.remesa.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;

import escrituras.beans.ResultBean;

public interface ServicioConsultaRemesa extends Serializable{

	ResultBean validarRemesaPorBloque(String codSeleccionados, BigDecimal idUsuario);

	ResultBean cambiarEstadoBloque(String codSeleccionados,	String estadoRemesaNuevo, BigDecimal idUsuario);

	ResultBean generarModelosRemesasBloque(String codSeleccionados,	BigDecimal idUsuario);

	ResultBean presentarEnBloque(String codSeleccionados, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario);

	ResultBean consultarEstadoRemesa(BigDecimal numExpediente);

	ResultBean anularRemesaPorBloque(String codSeleccionados, BigDecimal idUsuario);

}
