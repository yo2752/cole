package org.gestoresmadrid.oegamInterga.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;

public interface ServicioInterga extends Serializable {

	ResultadoIntergaBean generarMandato(BigDecimal numExpediente, Long idContrato, Long idUsuario, String tipoTramite);

	ResultadoIntergaBean eliminarMandato(BigDecimal numExpediente, String tipoTramite);

	String obtenerNombreDocumentoMandato(BigDecimal numExpediente, String tipoTramite);

	ResultadoIntergaBean descargarMandato(BigDecimal numExpediente, Long idUsuario, String tipoTramite);

	ResultadoIntergaBean descargarFichero(String nombreDocumento, Long idUsuario);
}
