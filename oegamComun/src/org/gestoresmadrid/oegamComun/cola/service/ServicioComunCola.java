package org.gestoresmadrid.oegamComun.cola.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

import utilidades.web.OegamExcepcion;

public interface ServicioComunCola extends Serializable {

	ResultadoBean crearSolicitud(Long idTramite, String proceso, String nodo, String tipoTramite, BigDecimal idUsuario, BigDecimal idContrato, String xmlEnviar) throws OegamExcepcion;

	void borrarPeticion(Long idCola);

	ResultadoBean eliminarCola(BigDecimal idTramite, String proceso, String nodo);

	int getNumColasProcesoPorContrato(String proceso, Long idContrato);

	Boolean existeColaProceso(String proceso, String nodo);

	Boolean existeColaTramiteProceso(String proceso, BigDecimal idTramite, String nodo);

	ResultadoBean crearSolicitud(String proceso, String xmlEnviar, String tipoTramite, BigDecimal idTramite, Long idUsuario, BigDecimal idContrato);
}
