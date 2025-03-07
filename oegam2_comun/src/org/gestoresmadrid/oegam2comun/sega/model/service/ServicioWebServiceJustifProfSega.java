package org.gestoresmadrid.oegam2comun.sega.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoJustificanteProfesionalBean;

public interface ServicioWebServiceJustifProfSega extends Serializable{
	
	public final String UTF_8 = "UTF-8";
	public final String TIMEOUT_PROP_JUSTIFICANTES = "webservice.justificantesPro.timeOut";
	public final String WEBSERVICE_JUSTIFICANTES_PRO = "webService.url.sega.justProfesional";
	public final Integer MAXIMA_LONGITUD_COMENTARIOS_EVOLUCION = 500;

	ResultadoJustificanteProfesionalBean procesarSolicitudJustificanteProf(ColaBean solicitud);

	void cambiarEstadoJustificanteProfSolicitud(BigDecimal idJustificanteInterno, EstadoJustificante estadoNuevo, BigDecimal idUsuario);

	void tratarDevolverCredito(BigDecimal idJustificanteInterno, BigDecimal idUsuario);
	
	void tratarDevolverCreditoSega(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoJustificanteProfesionalBean verificacionJustificanteProf(ColaBean solicitud);

	ResultadoJustificanteProfesionalBean actualizarVerificacionJustProf(BigDecimal idJustifianteInterno, EstadoJustificante estado, Boolean verificado, BigDecimal idUsuario, List<String> listaMensajes);

	void cambiarEstadoJustificanteProfSolicitudSega(BigDecimal numExpediente, EstadoJustificante estadoNuevo,BigDecimal idUsuario);

	void cambiarEstadoJustificanteProfSolicitudSega(BigDecimal numExpediente, EstadoJustificante estadoAnterior,
			EstadoJustificante estadoNuevo, BigDecimal idUsuario);

}
