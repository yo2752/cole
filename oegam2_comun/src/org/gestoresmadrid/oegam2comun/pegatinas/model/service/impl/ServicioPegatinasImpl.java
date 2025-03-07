package org.gestoresmadrid.oegam2comun.pegatinas.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockPeticionesVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaBajaPeticionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaPeticionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaRecepcionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioRestPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.ConstantesPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.EstadoPeticiones;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPegatinasImpl implements ServicioPegatinas{

	private static final long serialVersionUID = -2451647137417959589L;

	@Autowired
	private ServicioCola servicioCola;
	
	@Autowired
	private ServicioRestPegatinas servicioRestPegatinas;
	
	@Autowired
	private ServicioPegatinasTransactional servicioPegatinasTransactional;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioPegatinasImpl.class);
	
	@Override
	@Transactional
	public ResultBean creaSolicitudPeticionStock(int cantidad, String tipo, String jefatura, int idPeticionStock) {
		LOG.info("Crear solicitud de petición de stock");
		ResultBean result = null;
		try {
			String xml = "pedirStock " + cantidad + "," + tipo + "," + jefatura + "," + idPeticionStock;
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.PEGATINAS.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.PEGATINAS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de stock, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al pedir stock");
		}

		return result;
	}
	
	@Override
	@Transactional
	public ResultBean creaSolicitudBajaPeticionStock(String identificadorPeticion, String jefatura) {
		LOG.info("Crear solicitud de petición de stock");
		ResultBean result = null;
		try {
			String xml = "bajaStock " + identificadorPeticion + "," + jefatura;
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.PEGATINAS.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.PEGATINAS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de baja de stock, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al pedir la baja de stock");
		}

		return result;
	}
	
	@Override
	@Transactional
	public ResultBean creaSolicitudRecepcionStock(String identificadorPeticion, String jefatura) {
		LOG.info("Crear solicitud de petición de stock");
		ResultBean result = null;
		try {
			String xml = "recepcionStock " + identificadorPeticion + "," + jefatura;
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.PEGATINAS.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.PEGATINAS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de recepcion de stock, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al solicitar la recepción de stock");
		}

		return result;
	}
	
	public RespuestaPegatinas generaRespuesta(ColaBean solicitud) {
		RespuestaPegatinas respuesta = null;
		String[] datosSolicitud = solicitud.getXmlEnviar().split(" ");
		if (solicitud.getXmlEnviar().contains("pedirStock")){  
			RespuestaPeticionStock respuestaPeticion = servicioRestPegatinas.pedirStockPegatinas(datosSolicitud[0], datosSolicitud[1]);
			String[] campos = datosSolicitud[1].split(",");
			if (respuestaPeticion.getException() != null){
				respuesta = new RespuestaPegatinas();
				respuesta.setException(respuestaPeticion.getException());
				servicioPegatinasTransactional.cambiarEstadoPeticionById(campos[3], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), EstadoPeticiones.FINALIZADO_ERROR.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucion(campos[3], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), "No ha sido posible conectar con el WebService de Distintivos");
				return respuesta;
			}
			if (respuestaPeticion.getResultado() == 0){
				servicioPegatinasTransactional.actualizarPegatinaIdentificador(campos[3], respuestaPeticion.getIdentificadorPeticion(), EstadoPeticiones.PENDIENTE_RECEPCION.getNombreEnum(), EstadoPeticiones.PENDIENTE_RECEPCION.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucion(campos[3], EstadoPeticiones.PENDIENTE_RECEPCION.getNombreEnum(), ConstantesPegatinas.CAMBIO_ESTADO);
				servicioPegatinasTransactional.cambiarEstadoPeticionById(campos[3], EstadoPeticiones.PENDIENTE_RECEPCION.getNombreEnum(), EstadoPeticiones.PENDIENTE_RECEPCION.getValorEnum());

			}else{
				servicioPegatinasTransactional.cambiarEstadoPeticionById(campos[3], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), EstadoPeticiones.FINALIZADO_ERROR.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucion(campos[3], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), respuestaPeticion.getMensaje());
				respuesta = new RespuestaPegatinas();
				respuesta.setError(true);
				respuesta.setMensajeError(respuestaPeticion.getMensaje());
				return respuesta;
			}
		}else if (solicitud.getXmlEnviar().contains("bajaStock")){
			RespuestaBajaPeticionStock respuestaPeticion = servicioRestPegatinas.solicitarBajaStock(datosSolicitud[0], datosSolicitud[1]);
			String campos[] = datosSolicitud[1].split(",");
			if (respuestaPeticion.getException() != null){
				respuesta = new RespuestaPegatinas();
				respuesta.setException(respuestaPeticion.getException());
				servicioPegatinasTransactional.cambiarEstadoPeticionByIdentificadorPeticion(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), EstadoPeticiones.FINALIZADO_ERROR.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucionByIdentificador(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), "No ha sido posible conectar con el WebService de Distintivos");
				return respuesta;
			}
			if (respuestaPeticion.getResultado() == 0){
				servicioPegatinasTransactional.cambiarEstadoPeticionByIdentificadorPeticion(campos[0], EstadoPeticiones.ANULADO.getNombreEnum(), EstadoPeticiones.ANULADO.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucionByIdentificador(campos[0], EstadoPeticiones.ANULADO.getNombreEnum(), ConstantesPegatinas.CAMBIO_ESTADO);

			}else{
				servicioPegatinasTransactional.cambiarEstadoPeticionByIdentificadorPeticion(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), EstadoPeticiones.FINALIZADO_ERROR.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucionByIdentificador(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), respuestaPeticion.getMensaje());
				respuesta = new RespuestaPegatinas();
				respuesta.setError(true);
				respuesta.setMensajeError(respuestaPeticion.getMensaje());
				return respuesta;
			}
		}else if (solicitud.getXmlEnviar().contains("recepcionStock")){
			RespuestaRecepcionStock respuestaPeticion = servicioRestPegatinas.confirmarRecepcionStock(datosSolicitud[0], datosSolicitud[1]);
			String campos[] = datosSolicitud[1].split(",");
			if (respuestaPeticion.getException() != null){
				respuesta = new RespuestaPegatinas();
				respuesta.setException(respuestaPeticion.getException());
				servicioPegatinasTransactional.cambiarEstadoPeticionByIdentificadorPeticion(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), EstadoPeticiones.FINALIZADO_ERROR.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucionByIdentificador(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), "No ha sido posible conectar con el WebService de Distintivos");
				return respuesta;
			}
			if (respuestaPeticion.getResultado() == 0){
				PegatinasStockPeticionesVO peticion = servicioPegatinasTransactional.getIdStockByIdentificador(campos[0]);
				int actual = servicioPegatinasTransactional.actualizarStockByIdStock(peticion.getIdStock(), peticion.getNumPegatinas());
				servicioPegatinasTransactional.insertarHistorico(peticion.getIdStock(), "Peticion Stock", actual, peticion.getTipo(), null);
				servicioPegatinasTransactional.cambiarEstadoPeticionByIdentificadorPeticion(campos[0], EstadoPeticiones.FINALIZADO.getNombreEnum(), EstadoPeticiones.FINALIZADO.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucionByIdentificador(campos[0], EstadoPeticiones.FINALIZADO.getNombreEnum(), ConstantesPegatinas.CAMBIO_ESTADO);
			}else{
				
				servicioPegatinasTransactional.cambiarEstadoPeticionByIdentificadorPeticion(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), EstadoPeticiones.FINALIZADO_ERROR.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucionByIdentificador(campos[0], EstadoPeticiones.FINALIZADO_ERROR.getNombreEnum(), respuestaPeticion.getMensaje());
				respuesta = new RespuestaPegatinas();
				respuesta.setError(true);
				respuesta.setMensajeError(respuestaPeticion.getMensaje());
				return respuesta;
			}
		}
		
		return respuesta;
	}
	
	
	
}
