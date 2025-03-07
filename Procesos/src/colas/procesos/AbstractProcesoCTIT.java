package colas.procesos;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;

public abstract class AbstractProcesoCTIT extends ProcesoBase {
	private static ILoggerOegam log = LoggerOegam.getLogger(AbstractProcesoCTIT.class);

	private ModeloCreditosTrafico modeloCreditosTrafico;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	protected void finalizarTransaccionCorrectoTransmision(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta)
	throws CambiarEstadoTramiteTraficoExcepcion, DescontarCreditosExcepcion, BorrarSolicitudExcepcion {
		// Hacer la actualizacion de créditos incrementales

		String tipoTranferencia;
		ConceptoCreditoFacturado conceptroCreditoFacturado;

		if (solicitud.getProceso().equals("NOTIFICATIONCTIT") || solicitud.getProceso().equals("TRADECTIT")) {
			tipoTranferencia = TipoTramiteTrafico.Baja.getValorEnum();
		} else {
			tipoTranferencia = TipoTramiteTrafico.Transmision.getValorEnum();
		}
		getModeloCreditosTrafico().descontarCreditosExcep(getModeloTrafico().obtenerIdContratoTramite(solicitud.getIdTramite()),
				utiles.convertirIntegerABigDecimal(1), tipoTranferencia,solicitud.getIdUsuario());

		getModeloTrafico().cambiarEstadoTramite(respuesta, EstadoTramiteTrafico.Finalizado_Telematicamente, solicitud.getIdTramite(), solicitud.getIdUsuario());
		solicitud.setRespuesta("CORRECTO");
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),ConstantesProcesos.OK);

		//anotarGasto.
		if (solicitud.getProceso().equals("NOTIFICATIONCTIT") || solicitud.getProceso().equals("TRADECTIT")) {
			conceptroCreditoFacturado = ConceptoCreditoFacturado.TBT;
		} else {
			conceptroCreditoFacturado = ConceptoCreditoFacturado.TTT;
		}

		try {
			ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
			if (servicioCreditoFacturado != null ){
				ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(solicitud.getIdUsuario().toString());
				servicioCreditoFacturado.anotarGasto(new Integer(1), conceptroCreditoFacturado, contrato.getId().getIdContrato().longValue(), TipoTramiteTrafico.TransmisionElectronica.getValorEnum(), solicitud.getIdTramite().toString());
			}
		} catch (Exception e) {
			log.error("Error anotando el gasto de credito (historico)");
		}
	}

	protected void finalizarTransaccionNoTramitable(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuestaError) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		getModeloTrafico().cambiarEstadoTramite(respuestaError, EstadoTramiteTrafico.No_Tramitable, solicitud.getIdTramite(), solicitud.getIdUsuario()); 
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(),respuestaError);
	}

	protected void finalizarTransaccionConIncidencias(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) throws CambiarEstadoTramiteTraficoExcepcion, BorrarSolicitudExcepcion {
		// Mantis 4149: TRÁMITES CON EMBARGO/PRECINTO, ahora si la respuesta contiene  "PRECINTO" o "EMBARGO" lo pasa a tramitable con
		// incidencias y anula la incidencia 1723
		getModeloTrafico().cambiarEstadoTramite(respuesta, EstadoTramiteTrafico.Tramitable_Incidencias, solicitud.getIdTramite(), solicitud.getIdUsuario());

		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), respuesta);
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}
}