package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.impl.ServicioPegatinasImpl;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCrearSolicitudesPedido;
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
public class ServicioCrearSolicitudesPedidoImpl implements ServicioCrearSolicitudesPedido {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3438943028521814834L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioPegatinasImpl.class);

	@Autowired	private ServicioCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	@Transactional
	public ResultBean crearSolicitudCrearPedido(Long pedidoId) {
		LOG.info("Crear solicitud de pedido");
		ResultBean result = null;
		
		try {
			String xml = "pedidoCrear " + pedidoId;
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);

		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la Crear solicitud de pedido, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al Crear solicitud de pedido");			
		}

			
		return result;
	}

	@Override
	public ResultBean crearSolicitudActualizarPedido(Long pedidoId) {
		LOG.info("Actualiza la información del detalle de un pedido");
		ResultBean result = null;
		
		try {
			String xml = "pedidoDetalle " + pedidoId;
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);

		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de actualizar la peticion de pedido, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar pedido");			
		}

			
		return result;
	}

	@Override
	public ResultBean crearSolicitudAddMaterialPedido(Long pedidoId, Long MaterialId) {
		LOG.info("Crea la solicitud de un pedido");
		ResultBean result = null;
		
		try {
			String xml = "addItemPedido " + pedidoId + "," + MaterialId; 
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
				
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de añadir material, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al añadir material");			
		}

			
		return result;
	}

	@Override
	public ResultBean crearSolicitudRemoveMaterialPedido(Long pedidoId, Long MaterialId) {
		LOG.info("Elimina material a un pedido");
		ResultBean result = null;
		
		try {
			String xml = "deleteItemPedido " + pedidoId + "," + MaterialId; 
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de eliminar material, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al eliminar material");			
		}

		
		return result;
	}

	@Override
	public ResultBean crearSolicitudIncidencia(Long incidenciaId) {
		LOG.info("Crea la incidencia en el sistema");
		ResultBean result = null;

		try {
			String xml = "incidenciaCrear " + incidenciaId; 
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de crear incidencia, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al crear incidencia");			
		}

		return result;
	}

	@Override
	public ResultBean crearSolicitudActualizarIncidencia(Long incidenciaId) {
		LOG.info("Actualiza la incidencia en el sistema");
		ResultBean result = null;
		
		try {
			String xml = "incidenciaUpdate " + incidenciaId; 
			BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
					nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de actualizar incidencia, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar incidencia");			
		}

		
		return result;
	}

	@Override
	public ResultBean crearSolicitudInformacionPedido(List<Long> pedidosId) {
		// TODO Auto-generated method stub
		LOG.info("Obtiene información de un pedido");
		ResultBean result = null;
		
		try {
			for(Long pedidoId: pedidosId) {
				String xml = "infoPedido " + pedidoId; 
				BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
				BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
				String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);

				result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
						nodo, null, null, idUsuario, null, idContrato);
				
				LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
						"En el nodo " + nodo);
				
			}
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de actualizar incidencia, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar incidencia");			
		}
		
		return result;
	}

	@Override
	public ResultBean crearSolicitudInformacionStock(List<Long> stocksId, ColaBean colaBean) {
		// TODO Auto-generated method stub
		LOG.info("Obtiene información de un stock");
		ResultBean result = null;
		
		try {
			for(Long stockId: stocksId) {
				String xml = "infoStock " + stockId;
				
				BigDecimal idContrato = null;
				BigDecimal idUsuario =  null;
				if ( null == colaBean ) {
					idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
					idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
				} else {
					idContrato = colaBean.getIdContrato();
					idUsuario  = colaBean.getIdUsuario();
				}

				String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
				result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
						nodo, null, null, idUsuario, null, idContrato);
				
				LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
						"En el nodo " + nodo);
			}
		
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de actualizar incidencia, eror: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar incidencia");			
		}
		
		return result;
	}

	@Override
	public ResultBean crearSolicitudActualizarStock(Long stockId) {
		// TODO Auto-generated method stub
		LOG.info("Actualiza Stock en el consejo");
		ResultBean result = null;
		
		try {
			String xml = "stockUpdate " + stockId;
			BigDecimal idContrato = null; //utilesColegiado.getIdContrato();;
			BigDecimal idUsuario =  null; //utilesColegiado.getIdUsuarioDeSesion();
			
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.MATERIALES.getNombreEnum(), xml,
												 nodo, null, null, idUsuario, null, idContrato);
			
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.MATERIALES.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la peticion de actualizar stock, error: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar stock");			
		}
		
		return result;
	}

	
}
