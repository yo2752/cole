package org.gestoresmadrid.oegam2comun.modelo600_601.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;

import escrituras.beans.ResultBean;

public interface ServicioConsultaModelo600_601 extends Serializable{

	ResultBean presentarEnBloque(String codSeleccionados, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuarioDeSesion, File fichero);

	ResultBean autoLiquidarModelosEnBloque(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultBean cambiarEstadoBloque(String codSeleccionados, String estadoModeloNuevo, BigDecimal idUsuarioDeSesion);

	ResultBean validarModeloEnBloque(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultBean consultarEstadoAnuladoModelo(BigDecimal numExpediente);

	ResultBean anularModelosEnBloque(String codSeleccionados, BigDecimal idUsuario);

}
