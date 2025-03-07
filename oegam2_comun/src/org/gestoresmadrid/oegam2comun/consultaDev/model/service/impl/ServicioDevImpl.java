package org.gestoresmadrid.oegam2comun.consultaDev.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.consultaDev.model.dao.ConsultaDevDao;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoCif;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.consultaDev.model.dto.ConsultaDevDto;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioSuscripcionDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioWebServiceConsultaDev;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.evolucionConsultaDev.model.service.ServicioEvolucionConsultaDev;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.dgt.www.nostra.esquemas.consultaDEV.respuesta.Respuesta;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioDevImpl implements ServicioDev{

	private static final long serialVersionUID = 2597684887243212424L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDevImpl.class);
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	ConsultaDevDao consultaDevDao;
	
	@Autowired
	ServicioContrato servicioContrato;
	
	@Autowired
	ServicioCredito servicioCredito;
	
	@Autowired
	ServicioCola servicioCola;
	
	@Autowired
	ServicioSuscripcionDev servicioSuscripcionDev;
	
	@Autowired
	ServicioEvolucionConsultaDev servicioEvolucionConsultaDev;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true)
	public ResultBean getConsultaDev(Long idConsultaDev) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(idConsultaDev != null){
				ConsultaDevVO consultaDevVO = getConsultaDevVO(idConsultaDev,true);
				if(consultaDevVO != null){
					resultado.addAttachment(ServicioDev.descConsultaDevDto, conversor.transform(consultaDevVO, ConsultaDevDto.class));
				}else{
					resultado.setError(true);
					resultado.addMensajeALista("No existen datos para esa consulta Dev.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de indicar la consulta dev que desea obtener sus datos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta dev con id: " + idConsultaDev + " , error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de obtener la consulta dev con id: " + idConsultaDev);
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ConsultaDevVO getConsultaDevVO(Long idConsultaDev, Boolean completo) {
		try {
			if(idConsultaDev != null){
				return consultaDevDao.getConsultaDevPorId(idConsultaDev, completo);
			}else{
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las consultas dev.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta dev con id: " + idConsultaDev + " , error: ",e);
		}
		return null;
	}
	
	@Override
	@Transactional
	public ResultBean consultar(ConsultaDevDto consultaDev, BigDecimal idUsuario, Boolean esAccionCons) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultValidar = validarConsultaDev(consultaDev);
			if(!resultValidar.getError()){
				ConsultaDevVO consultaDevVO = conversor.transform(consultaDev, ConsultaDevVO.class);
				if(!esAccionCons){
					consultaDevDao.guardarOActualizar(consultaDevVO);
				}
				BigDecimal estadoAnt = consultaDevVO.getEstado();
				consultaDevVO.setEstado(new BigDecimal(EstadoConsultaDev.Pdte_Consulta_Dev.getValorEnum()));
				ResultBean resultConsultar = realizarConsultar(consultaDevVO,idUsuario);
				if(resultConsultar.getError()){
					resultado = resultConsultar;
				}else{
					servicioEvolucionConsultaDev.guardarEvolucion(consultaDevVO.getIdConsultaDev(),idUsuario.longValue(), new Date(),estadoAnt,consultaDevVO.getEstado(),TipoActualizacion.MOD.getValorEnum());
				}
			}else{
				return resultValidar;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la consulta Dev, error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la consulta Dev.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de realizar la consulta Dev, error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la consulta Dev.");
		}
		resultado.addAttachment(ServicioDev.idConsultaDevDto, consultaDev.getIdConsultaDev());
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private ResultBean realizarConsultar(ConsultaDevVO consultaDevVO, BigDecimal idUsuario) throws OegamExcepcion{
		ResultBean resultado = new ResultBean(false);
		consultaDevDao.guardarOActualizar(consultaDevVO);
		ResultBean resultCreditos = tratarCobrarCreditos(consultaDevVO,new BigDecimal(consultaDevVO.getIdContrato()),idUsuario);
		if(!resultCreditos.getError()){
			ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.CONSULTA_DEV.getNombreEnum(), null, gestorPropiedades.valorPropertie(ServicioDev.NOMBRE_HOST_SOLICITUD_NODO_2), 
				TipoTramiteTrafico.Consulta_Dev.getValorEnum(), consultaDevVO.getIdConsultaDev().toString(), idUsuario, null, new BigDecimal(consultaDevVO.getIdContrato()));
			if(resultCola.getError()){
				resultado = resultCola;
			}else{
				resultado.addAttachment(ServicioDev.idConsultaDevDto, consultaDevVO.getIdConsultaDev());
			}
		}else{
			resultado = resultCreditos;
		}
		return resultado;
	}


	private ResultBean tratarCobrarCreditos(ConsultaDevVO consultaDevVO, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			if("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioDev.cobrarCreditos))){
				resultado = servicioCredito.validarCreditos(TipoTramiteTrafico.Consulta_Dev.getValorEnum(), 
						idContrato, BigDecimal.ONE);
				if (!resultado.getError()) {
					// Descontar creditos
					resultado = servicioCredito.descontarCreditos(TipoTramiteTrafico.Consulta_Dev.getValorEnum(), idContrato, 
						BigDecimal.ONE, idUsuario, ConceptoCreditoFacturado.CDEV, consultaDevVO.getIdConsultaDev().toString());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los creditos para realizar la consulta dev, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de cobrar los creditos.");
		}
		return resultado;
	}

	private ResultBean validarConsultaDev(ConsultaDevDto consultaDev) {
		ResultBean resultado = new ResultBean(false);
		if(consultaDev == null){
			resultado.setError(true);
			resultado.setMensaje("No existen datos que consultar.");
			return resultado;
		}
		if(consultaDev.getCif() == null || consultaDev.getCif().isEmpty()){
			resultado.setError(true);
			resultado.setMensaje("Debe de indicar un CIF para realizar la consulta.");
			return resultado;
		}
		if(consultaDev.getEstado() != null && !consultaDev.getEstado().isEmpty()){
			if(!EstadoConsultaDev.Iniciado.getValorEnum().equals(consultaDev.getEstado())){
				resultado.setError(true);
				resultado.setMensaje("No se puede volver a consultar el CIF mientras se encuentre en estado " + EstadoConsultaDev.convertirTexto(consultaDev.getEstado())+ ".");
				return resultado;
			}else if(EstadoConsultaDev.Anulada.getValorEnum().equals(consultaDev.getEstado())){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La consulta dev con CIF: " + consultaDev.getCif() + " se encuentra en estado anulada y no se podran realizar acciones sobre ella.");
				return resultado;
			}{
				
			}
		}
		if(consultaDev.getContrato() == null || consultaDev.getContrato().getIdContrato() == null){
			resultado.setError(true);
			resultado.setMensaje("Debe seleccionar el contrato.");
			return resultado;
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean cambiarEstado(Long idConsultaDev, BigDecimal idUsuario, BigDecimal estadoNuevo, Boolean accionesAsociadas) {
		ResultBean resultado = new ResultBean();
		try {
			if(idConsultaDev != null){
				ConsultaDevVO consultaDevVO = getConsultaDevVO(idConsultaDev, false);
				if(consultaDevVO != null){
					if(EstadoConsultaDev.Anulada.getValorEnum().equals(consultaDevVO.getEstado().toString())){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La consulta dev con CIF: " + consultaDevVO.getCif() + " se encuentra en estado anulada y no se podran realizar acciones sobre ella.");
						return resultado;
					}
					BigDecimal estadoAnt = consultaDevVO.getEstado();
					consultaDevVO.setEstado(estadoNuevo);
					consultaDevDao.actualizar(consultaDevVO);
					servicioEvolucionConsultaDev.guardarEvolucion(consultaDevVO.getIdConsultaDev(),idUsuario.longValue(), new Date(),estadoAnt,consultaDevVO.getEstado(),TipoActualizacion.MOD.getValorEnum());
					if(accionesAsociadas){
						resultado = accionesCambiarEstadoSinValidacion(consultaDevVO,estadoAnt,estadoNuevo,idUsuario);
					}
					if(!resultado.getError()){
						resultado.setMensaje("La consulta Dev con CIF: " + consultaDevVO.getCif() + ", se ha actualizado.");
						resultado.addAttachment(ServicioDev.cifConsultaDevDto, consultaDevVO.getCif());
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta dev: " + idConsultaDev + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la consulta dev: " + idConsultaDev);
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ResultBean accionesCambiarEstadoSinValidacion(ConsultaDevVO consultaDevVO, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		try {
			if(EstadoConsultaDev.Pdte_Consulta_Dev.getValorEnum().equals(estadoAnt.toString())){
				servicioCola.eliminar(new BigDecimal(consultaDevVO.getIdConsultaDev()),ProcesosEnum.CONSULTA_DEV.getNombreEnum());
				if("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioDev.cobrarCreditos))){
					resultado = servicioCredito.devolverCreditos(TipoTramiteTrafico.Consulta_Dev.getValorEnum(), new BigDecimal(consultaDevVO.getIdContrato()), 
						1, idUsuario, ConceptoCreditoFacturado.DDEV, consultaDevVO.getIdConsultaDev().toString());
				}
			}else if(EstadoConsultaDev.Anulada.getValorEnum().equals(estadoAnt.toString())){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La consulta Dev con CIF: " + consultaDevVO.getCif() + ", se encuentra en estado anulado y no se podran realizar acciones sobre ella.");
			}
			if(EstadoConsultaDev.Pdte_Consulta_Dev.getValorEnum().equals(estadoNuevo.toString())){
				return realizarConsultar(consultaDevVO,idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta Dev con CIF: " + consultaDevVO.getCif() + ", error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta Dev con CIF: " + consultaDevVO.getCif() + ".");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta Dev con CIF: " + consultaDevVO.getCif() + ", error: ",e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta Dev con CIF: " + consultaDevVO.getCif() + ".");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarConsultaDevVO(ConsultaDevVO consultaDevVO) {
		ResultBean resultBean = new ResultBean();
		try {
			if(consultaDevVO != null && consultaDevVO.getIdConsultaDev() != null){
				consultaDevDao.actualizar(consultaDevVO);
			}else{
				resultBean.setError(Boolean.TRUE);
				resultBean.setMensaje("No existen datos de la consulta dev para poder actualizarlos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar la consulta dev, error: ",e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de actualizar la consulta dev.");
		}
		if(resultBean.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultBean;
	}
	
	@Override
	@Transactional
	public ResultBean guardarDatosRespuesta(ConsultaDevVO consultaDevVO, Respuesta respuesta) {
		ResultBean resultado = new ResultBean();
		try {
			if(consultaDevVO != null && respuesta != null){
				if(respuesta.getTransmisiones() != null 
					&& respuesta.getTransmisiones().getTransmisionDatos() != null
					&& respuesta.getTransmisiones().getTransmisionDatos().getDatosEspecificos() != null
					&& respuesta.getTransmisiones().getTransmisionDatos().getDatosEspecificos().getSuscripciones() != null
					&& respuesta.getTransmisiones().getTransmisionDatos().getDatosEspecificos().getSuscripciones().getSuscripcion() != null
					&& respuesta.getTransmisiones().getTransmisionDatos().getDatosEspecificos().getSuscripciones().getSuscripcion().length >= 1){
					ResultBean resultSuscripciones = servicioSuscripcionDev.guardarSuscripcionesWSConsultaDev(respuesta.getTransmisiones().getTransmisionDatos().getDatosEspecificos().getSuscripciones().getSuscripcion(), consultaDevVO.getIdConsultaDev());
					if(resultSuscripciones.getError()){
						resultado = resultSuscripciones;
					} else {
						consultaDevVO.setEstadoCif((BigDecimal) resultSuscripciones.getAttachment(ServicioWebServiceConsultaDev.estadoSuscripcion));
					}
				}else{
					consultaDevVO.setEstadoCif(new BigDecimal(EstadoCif.No_Existe.getValorEnum()));
				}
				consultaDevVO.setRespuesta(respuesta.getAtributos().getEstado().getLiteralError());
				consultaDevVO.setCodRespuesta(respuesta.getAtributos().getEstado().getCodigoEstado());
				actualizarConsultaDevVO(consultaDevVO);
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de la consulta dev o de la respuesta para actualizar su contenido.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de respuesta del WS de consulta dev, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar los datos de respuesta del WS de consulta dev.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultBean guardar(ConsultaDevDto consultaDev, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			ResultBean resultDatosMinimos = datosMinimosConsultaDev(consultaDev);
			if(!resultDatosMinimos.getError()){
				Date fecha = new Date();
				BigDecimal estadoAnt = null;
				TipoActualizacion tipoActualizacion = null;
				ConsultaDevVO consultaDevVO = conversor.transform(consultaDev, ConsultaDevVO.class);
				if(consultaDevVO.getFechaAlta() == null){
					consultaDevVO.setFechaAlta(fecha);
				}
				if(consultaDevVO.getEstado() != null && !consultaDev.getEstado().isEmpty()){
					estadoAnt = new BigDecimal(consultaDev.getEstado());
				}
				if(consultaDev.getIdConsultaDev() != null){
					tipoActualizacion = TipoActualizacion.MOD;
				}else{
					tipoActualizacion = TipoActualizacion.CRE;
				}
				consultaDevVO.setEstado(new BigDecimal(EstadoConsultaDev.Iniciado.getValorEnum()));
				consultaDevDao.guardarOActualizar(consultaDevVO);
				resultado.addAttachment(ServicioDev.idConsultaDevDto, consultaDevVO.getIdConsultaDev());
				servicioEvolucionConsultaDev.guardarEvolucion(consultaDevVO.getIdConsultaDev(),idUsuario.longValue(), fecha,estadoAnt,consultaDevVO.getEstado(),tipoActualizacion.getValorEnum());
			}else{
				return resultDatosMinimos;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la consulta Dev, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de guardar la consulta Dev.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultBean datosMinimosConsultaDev(ConsultaDevDto consultaDev) {
		ResultBean resultado = new ResultBean(false);
		if(consultaDev == null){
			resultado.setError(true);
			resultado.setMensaje("No existen datos que guardar.");
			return resultado;
		}
		if(consultaDev.getCif() == null || consultaDev.getCif().isEmpty()){
			resultado.setError(true);
			resultado.setMensaje("Debe de indicar un CIF para poder guardar la consulta.");
			return resultado;
		}
		if(consultaDev.getContrato() == null || consultaDev.getContrato().getIdContrato() == null){
			resultado.setError(true);
			resultado.setMensaje("Debe seleccionar el contrato.");
			return resultado;
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public void guardarFicheroAltaMasiva(File fichero, BigDecimal idContrato) {
		try {
			Date fecha = new Date();
			ContratoDto contrato = servicioContrato.getContratoDto(idContrato);
			String nombreFichero = ServicioDev.NOMBRE_MASIVAS_TXT + contrato.getColegiadoDto().getNumColegiado() + "_" +  utilesFecha.formatoFecha("yyyyMMdd HHmmss", fecha).replace(" ", "");
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setFichero(fichero);
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CONSULTA_DEV);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.ALTA_MASIVA_DEV);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
			ficheroBean.setFecha(new Fecha(fecha));
			ficheroBean.setNombreDocumento(nombreFichero);
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el txt de las altas de consultas dev masivas, error: ",e);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el txt de las altas de consultas dev masivas, error: ",e);
		}
	}
}
