package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoJustificanteProfesional;

public interface ServicioWebServiceJustificanteProf extends Serializable {

	public final String UTF_8 = "UTF-8";
	public final String TIMEOUT_PROP_JUSTIFICANTES = "webservice.justificantesPro.timeOut";
	public final String WEBSERVICE_JUSTIFICANTES_PRO = "justificante.profesional.ws.url";

	public static final String SERVICIO_JUSTIFICANTE_PROFESIONAL_DOI_PLATAFORMA = "doi.plataforma";

	public static final String ERROR_GENERICO = "Error no controlado en la ejecucion del servicio";
	
	public static final String NOTIFICACION = "PROCESO VERIFIC JP FINALIZADO";
	public static final String TIPO_TRAMITE_NOTIFICACION = "N/A";

	ResultadoJustificanteProfesional procesarEmisionJustificanteProf(ColaDto solicitud);

	ResultadoJustificanteProfesional procesarVerificacionJustificanteProf(ColaDto solicitud);

	void cambiarEstadoJustificanteProfSolicitud(BigDecimal idJustificanteInterno, EstadoJustificante estadoNuevo, BigDecimal idUsuario);

	void tratarDevolverCredito(BigDecimal idJustificanteInterno, BigDecimal idUsuario);
}
