package org.gestoresmadrid.oegamBajas.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;

import utilidades.web.OegamExcepcion;

public interface ServicioPersistenciaBaja extends Serializable{

	TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, Boolean tramiteCompleto);

	void guardarActualizarTramite(TramiteTrafBajaVO tramiteTrafBajaVO);

	void guardarActualizarTramiteConEvo(TramiteTrafBajaVO tramiteTrafBajaVO, EvolucionTramiteTraficoVO evolucionTramiteTraficoVO);

	ResultadoBajasBean realizarCheckBtv(BigDecimal numExpediente, String proceso, String nombreFichero, BigDecimal idUsuario, String hostProceso, Long idContrato) throws OegamExcepcion;

	ResultadoBajasBean realizarTramitacionBtv(BigDecimal numExpediente, String proceso, String nombreXml, BigDecimal idUsuario, String hostProceso, Long idContrato) throws OegamExcepcion;

	ResultadoBajasBean realizarPdteEnvioExcel(BigDecimal numExpediente, BigDecimal idUsuario, Long idContrato);

	void finalizarSolicitudCheckBtv(BigDecimal numExpediente, BigDecimal estadoNuevo, String respuesta, BigDecimal idUsuario);

	void finalizarSolicitudBtv(BigDecimal numExpediente, BigDecimal nuevoEstado, String respuesta, BigDecimal idUsuario);
}
