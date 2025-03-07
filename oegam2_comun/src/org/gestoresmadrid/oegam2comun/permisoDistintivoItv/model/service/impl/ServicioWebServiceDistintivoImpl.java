package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioWebServiceDistintivo;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import net.gestores.siga.dvl.CriteriosConsultaVehiculo;
import net.gestores.siga.dvl.DefinitiveVehicleLicenseServiceLocator;
import net.gestores.siga.dvl.DefinitiveVehicleLicenseServiceWSBindingStub;
import net.gestores.siga.dvl.DefinitiveVehicleLicenseWS;
import net.gestores.siga.dvl.DvlEDRequestCarSharing;
import net.gestores.siga.dvl.DvlError;
import net.gestores.siga.dvl.DvlReturn;
import net.gestores.siga.dvl.DvlReturnIsLightVehicle;
import net.gestores.siga.dvl.ws.SoapDistintivoWSHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceDistintivoImpl implements ServicioWebServiceDistintivo {

	private static final long serialVersionUID = -2898811354743584129L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceDistintivoImpl.class);
	
	@Autowired
	ServicioImpresionPermisoDistintivoItv servicioImpresionPermisoDistintivoItv;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	ServicioDistintivoDgt servicioDistintivoDgt;
	
	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	ServicioCorreo servicioCorreo;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional
	public void actualizarEstado(BigDecimal idDoc, EstadoPermisoDistintivoItv estado, String respuesta, BigDecimal idUsuario) {
		Boolean error = Boolean.FALSE;
		try {
			if(idDoc != null){
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(idDoc.longValue(),Boolean.FALSE);
				if(docPermDistItvVO != null){
					docPermDistItvVO.setEstado(new BigDecimal(estado.getValorEnum()));
					servicioDocPrmDstvFicha.actualizarEstado(docPermDistItvVO, estado, idUsuario, OperacionPrmDstvFicha.GENERADO);
				}else{
					log.error("No se encuentran datos del docPermDstv para actualizar");
					error = Boolean.TRUE;
				}
			}else{
				log.error("Debe de indicar un id del DocPermDstv para actualizar");
				error = Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado del docPermDstv, error: ", e);
			error = Boolean.TRUE;
		}
		if(error){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	@Override
	public void actualizarEstadosDistintivos(Long idDoc, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, String ipConexion) {
		try {
			Date fecha = new Date();
			DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(idDoc, Boolean.FALSE);
			List<TramiteTrafMatrVO> listaTramites = servicioDistintivoDgt.getListaTramitesPorDocId(idDoc);
			if(listaTramites != null && !listaTramites.isEmpty()){
				for(TramiteTrafMatrVO tramiteTrafMatrVO : listaTramites){
					try {
						servicioTramiteTraficoMatriculacion.actualizarEstadoImpresionDistintivo(tramiteTrafMatrVO.getNumExpediente(), estadoAnt,
								estadoNuevo,idUsuario, fecha, docPermDistItvVO.getDocIdPerm(), ipConexion);
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambiar el estado del distintivo del tramite: " + tramiteTrafMatrVO.getNumExpediente() + ", error: ",e);
					}
				}
			}
			List<VehNoMatOegamVO> listaDuplicados = servicioDistintivoVehNoMat.getListaVehiculoDistintivosPorDocId(idDoc);
			if(listaDuplicados != null && !listaDuplicados.isEmpty()){
				for(VehNoMatOegamVO vehNoMatOegamVO : listaDuplicados){
					try {
						servicioDistintivoVehNoMat.actualizarEstadoImpresionDstv(vehNoMatOegamVO.getId(), estadoAnt, estadoNuevo, idUsuario, fecha, docPermDistItvVO.getDocIdPerm());
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambiar el estado del duplicado: " + vehNoMatOegamVO.getId() + ", error: ",e);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de los expediente, error: ",e);
		}
	}
	
	@Override
	public void actualizarEstadoDstv(BigDecimal numExpediente, EstadoPermisoDistintivoItv estado, String respuestaError) {
		try {
			if(numExpediente != null){
				TramiteTrafMatrVO trafMatrVO = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
				if(trafMatrVO != null){
					trafMatrVO.setEstadoPetPermDstv(estado.getValorEnum());
					trafMatrVO.setRespPetPermDstv(respuestaError);
					servicioTramiteTraficoMatriculacion.guardarOActualizar(trafMatrVO);
				}else{
					log.error("No se ha encontrado ningún expediente para el número: " + numExpediente);
				}
			}else{
				log.error("Debe de indicar un numExpediente para poder actualizar el estado de la solicitud de distintivo");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de la solicitud de distintivos, error: ", e, numExpediente.toString());
		}
	}
	
	@Override
	public void actualizarEstadoDstvVNMO(Long idVehNotMatOegam, EstadoPermisoDistintivoItv estadoNuevo, String respuesta, BigDecimal idUsuario) {
		try {
			servicioDistintivoVehNoMat.actualizarEstadoProceso(idVehNotMatOegam,estadoNuevo,respuesta,idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de la solicitud de distintivos, error: ", e);
		}
		
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean impresionDistintivo(ColaDto solicitud, String tipoSolicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(solicitud != null && solicitud.getIdTramite() != null){
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(),Boolean.FALSE);
				if(docPermDistItvVO != null){
					 if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
						List<TramiteTrafMatrVO> listaTramites = servicioDocPrmDstvFicha.getListaTramitesDistintivosVO(docPermDistItvVO.getIdDocPermDistItv());
						List<VehNoMatOegamVO> listaDuplicados = servicioDistintivoVehNoMat.getListaVehiculoDistintivosPorDocId(docPermDistItvVO.getIdDocPermDistItv());
						if((listaTramites != null && !listaTramites.isEmpty()) || 
								(listaDuplicados != null && !listaDuplicados.isEmpty())){
							resultado = servicioImpresionPermisoDistintivoItv.imprimirDstv(listaTramites,listaDuplicados,docPermDistItvVO.getFechaAlta(),docPermDistItvVO);
							if(!resultado.getError()){
								ResultadoDistintivoDgtBean resultEnvioDoc = servicioDocPrmDstvFicha.generarDocGestoriaDstvProceso(listaTramites,listaDuplicados, docPermDistItvVO, tipoSolicitud);
								if(resultEnvioDoc.getError()){
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje(resultEnvioDoc.getMensaje());
								}
							}
						}else{
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se han obtenido datos de distintivos para imprimir.");
						}
					}else{
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No existe tipo de documento para imprimir.");
					}
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos para poder realizar la petición.");
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La cola o el numero del expediente de la solicitud esta vacio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de solicitud de permisos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la peticion de solicitud de permisos.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean impresionDstvDuplicado(ColaDto solicitud) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(solicitud != null && solicitud.getIdTramite() != null){
				DocPermDistItvVO docPermDistItvVO = servicioDocPrmDstvFicha.getDocPorId(solicitud.getIdTramite().longValue(),Boolean.FALSE);
				if(docPermDistItvVO != null){
					List<VehNoMatOegamVO> listaVehiculos = servicioDocPrmDstvFicha.getListaTramitesDuplicadosDstv(docPermDistItvVO.getIdDocPermDistItv());
					if(listaVehiculos != null){
						resultado = servicioImpresionPermisoDistintivoItv.imprimirDuplicadosDstv(listaVehiculos,docPermDistItvVO);
						if(!resultado.getError()){
							ResultadoDistintivoDgtBean resultEnvioDoc = servicioDocPrmDstvFicha.generarDocGestoriaDistintivoDuplicado(listaVehiculos, docPermDistItvVO);
							if(resultEnvioDoc.getError()){
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje(resultEnvioDoc.getMensaje());
							}
						}
					}else{
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha podido recuperar la lista de vehiculos por id.");
					}
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos para poder realizar la petición.");
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La cola o el numero del documento de la solicitud esta vacio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la impresion de duplicados de distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la impresion de duplicados de distintivos.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean llamadaWS(TramiteTrafMatrVO tramiteTrafico, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			DefinitiveVehicleLicenseServiceWSBindingStub stub = getStubPermisoDistintivo(tramiteTrafico.getNumExpediente());
			CriteriosConsultaVehiculo criterios = generarCriterios(tramiteTrafico.getVehiculo().getMatricula(), tramiteTrafico.getVehiculo().getBastidor(), tramiteTrafico.getVehiculo().getNive());
			
			DvlEDRequestCarSharing carsharing = obtenerCarsharing(tramiteTrafico.getCarsharing());
			
			DvlReturn permdistSoapRespuesta = stub.issueEnvironmentalDistinctive(tramiteTrafico.getContrato().getColegio().getCif(), 
					tramiteTrafico.getContrato().getColegio().getCif(), tramiteTrafico.getContrato().getColegiado().getUsuario().getNif(), 
					null, criterios, carsharing, new Integer(0), new Integer(0));
			if(permdistSoapRespuesta != null){
				resultado = gestionarResultadoDstvWS(permdistSoapRespuesta,tramiteTrafico, idUsuario);
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora realizar la peticion para solicitar el distintivos.");
			}
		} catch (Exception e) {
			log.error("Error en al solicitar el distintivo, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	private DvlEDRequestCarSharing obtenerCarsharing (String carsharing) {
		if (StringUtils.isNotBlank(carsharing)) {
			if ("S".equals(carsharing)) {
				return DvlEDRequestCarSharing.fromString(DvlEDRequestCarSharing.Y.toString());
			} else {
				return DvlEDRequestCarSharing.fromString(DvlEDRequestCarSharing.N.toString());
			}
		}
		return null;
	}
	
	private ResultadoDistintivoDgtBean llamadaWSVNMO(VehNoMatOegamVO vehNoMatOegamVO, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			DefinitiveVehicleLicenseServiceWSBindingStub stub = getStubDistintivoVNMO(vehNoMatOegamVO.getId(), vehNoMatOegamVO.getMatricula());
			CriteriosConsultaVehiculo criterios = generarCriterios(vehNoMatOegamVO.getMatricula(), vehNoMatOegamVO.getBastidor(), vehNoMatOegamVO.getNive());
			DvlReturn permdistSoapRespuesta = stub.issueEnvironmentalDistinctive(vehNoMatOegamVO.getContrato().getColegio().getCif(), 
					vehNoMatOegamVO.getContrato().getColegio().getCif(), vehNoMatOegamVO.getContrato().getColegiado().getUsuario().getNif(), 
					null, criterios, null, new Integer(0), new Integer(0));
			if(permdistSoapRespuesta != null){
				resultado = gestionarResultadoDstvVNMOWS(permdistSoapRespuesta,vehNoMatOegamVO, idUsuario);
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora realizar la peticion para solicitar permisos y distintivos.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada la WS de duplicados de distintivos, error: ",e, vehNoMatOegamVO.getId().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private CriteriosConsultaVehiculo generarCriterios(String matricula, String bastidor, String nive) {
		CriteriosConsultaVehiculo criterios = new CriteriosConsultaVehiculo();
		criterios.setBastidor(bastidor);
		criterios.setMatricula(matricula);
		if(nive != null && !nive.isEmpty()){
			criterios.setNive(nive);
		}
		
		return criterios;
	}

	private ResultadoDistintivoDgtBean gestionarResultadoDstvVNMOWS(DvlReturn permdistSoapRespuesta, VehNoMatOegamVO vehNoMatOegamVO, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		Boolean tieneDstv = Boolean.FALSE;
		if(permdistSoapRespuesta == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha ocurrido un fallo en la petición.");
		} else if(permdistSoapRespuesta.getErrors() != null && permdistSoapRespuesta.getErrors().length > 0){
			String mensaje = "";
			for(DvlError error : permdistSoapRespuesta.getErrors()){
				if(mensaje.isEmpty()){
					mensaje = error.getMessage();
				} else{
					mensaje += "- " + error.getMessage();
				}
			}
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensaje);
		} else {
			
			if(permdistSoapRespuesta.getEnvironmentDistinctiveType() != null 
					&& permdistSoapRespuesta.getEnvironmentDistinctiveType().getValue() != null 
					&& !permdistSoapRespuesta.getEnvironmentDistinctiveType().getValue().isEmpty()){
				
				byte[] distintivoPdf = Base64.decodeBase64(permdistSoapRespuesta.getEnvironmentDistinctivePdf());
				
				String tipoDistintivo = convertirTipoDistintivo(permdistSoapRespuesta.getIsLightVehicle(),permdistSoapRespuesta.getEnvironmentDistinctiveType().getValue(), null);
				ResultadoDistintivoDgtBean resultDistintivo = servicioDistintivoVehNoMat.guardarPdfDstv(distintivoPdf, vehNoMatOegamVO);
				if(resultDistintivo.isError()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultDistintivo.getMensaje());
				} else {
					tieneDstv = Boolean.TRUE;
				}
				resultado = servicioDistintivoVehNoMat.actualizarRecepcionDstv(vehNoMatOegamVO,tieneDstv, tipoDistintivo, idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha obtenido tipo distintivo de DGT.");
			}
		}
		return resultado;
	}
	
	private ResultadoDistintivoDgtBean gestionarResultadoDstvWS(DvlReturn permdistSoapRespuesta,TramiteTrafMatrVO tramiteTrafMatrVO, BigDecimal idUsuario) throws UnsupportedEncodingException {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		Boolean tieneDist = Boolean.FALSE;
		if(permdistSoapRespuesta == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha ocurrido un fallo en la petición.");
		} else if(permdistSoapRespuesta.getErrors() != null && permdistSoapRespuesta.getErrors().length > 0){
			String mensaje = "";
			for(DvlError error : permdistSoapRespuesta.getErrors()){
				if(mensaje.isEmpty()){
					mensaje = error.getMessage();
				} else{
					mensaje += "- " + error.getMessage();
				}
			}
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensaje);
		} else {
			String tipoDistintivo = null;
			if(permdistSoapRespuesta.getEnvironmentDistinctiveType() != null 
				&& permdistSoapRespuesta.getEnvironmentDistinctiveType().getValue() != null 
				&& !permdistSoapRespuesta.getEnvironmentDistinctiveType().getValue().isEmpty()){
				byte[] distintivoPdf = Base64.decodeBase64(permdistSoapRespuesta.getEnvironmentDistinctivePdf());
				tipoDistintivo = convertirTipoDistintivo(permdistSoapRespuesta.getIsLightVehicle(),permdistSoapRespuesta.getEnvironmentDistinctiveType().getValue(), tramiteTrafMatrVO);
				resultado = servicioDistintivoDgt.guardarPdfDstv(distintivoPdf, tramiteTrafMatrVO.getNumExpediente());
				if(!resultado.getError()){
					tieneDist = Boolean.TRUE;
				}
				resultado = servicioTramiteTraficoMatriculacion.actualizarTramiteMatwDstv(tramiteTrafMatrVO, tieneDist, tipoDistintivo, idUsuario, "proceso");
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha obtenido tipo distintivo de DGT.");
			}
		}
		return resultado;
	}

	private String convertirTipoDistintivo(DvlReturnIsLightVehicle esDistintivoMoto, String tipoDistintivo, TramiteTrafMatrVO tramiteTrafMatrVO) {
		String distintivo = null;
		if("Y".equals(esDistintivoMoto.getValue())){
			if(TipoDistintivo.CEROMT.toString().equals(tipoDistintivo)){
				distintivo = TipoDistintivo.CEROMT.getValorEnum();
			} else if(TipoDistintivo.BMT.toString().equals(tipoDistintivo)){
				distintivo = TipoDistintivo.BMT.getValorEnum();
			} else if(TipoDistintivo.ECOMT.toString().equals(tipoDistintivo)){
				distintivo = TipoDistintivo.ECOMT.getValorEnum();
			} else if(TipoDistintivo.CMT.toString().equals(tipoDistintivo)){
				distintivo = TipoDistintivo.CMT.getValorEnum();
			}
		} else{
			if (tramiteTrafMatrVO != null && StringUtils.isNotBlank(tramiteTrafMatrVO.getCarsharing())) {
				if ("S".equals(tramiteTrafMatrVO.getCarsharing())) {
					tipoDistintivo = TipoDistintivo.CARSHARING.getValorEnum();
				}
			}
			distintivo = tipoDistintivo;
		}
		return distintivo;
	}

	private DefinitiveVehicleLicenseServiceWSBindingStub getStubPermisoDistintivo(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		DefinitiveVehicleLicenseServiceWSBindingStub stubPD = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceDistintivo.TIMEOUT_PROP_PERMISO_DISTINTIVO_ITV);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceDistintivo.WEBSERVICE_PERMISO_DISTINTIVO_ITV));
		DefinitiveVehicleLicenseWS tramitarPermisoDistintivo = null;
		if(miUrl != null){
			DefinitiveVehicleLicenseServiceLocator permisoDistintivoTramitarLocator = new DefinitiveVehicleLicenseServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),permisoDistintivoTramitarLocator.getDefinitiveVehicleLicenseServiceWSDDServiceName());
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = permisoDistintivoTramitarLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapDistintivoWSHandler.class);
			
			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapDistintivoWSHandler.PROPERTY_KEY_NUMEXPEDIENTE, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);
			
			list.add(logHandlerInfo);
			
			tramitarPermisoDistintivo = permisoDistintivoTramitarLocator.getDefinitiveVehicleLicenseService(miUrl);
			stubPD = (DefinitiveVehicleLicenseServiceWSBindingStub) tramitarPermisoDistintivo;
			stubPD.setTimeout(Integer.parseInt(timeOut));
		}
		return stubPD;
	}
	
	private DefinitiveVehicleLicenseServiceWSBindingStub getStubDistintivoVNMO(Long id, String matricula) throws MalformedURLException, ServiceException {
		DefinitiveVehicleLicenseServiceWSBindingStub stubPD = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceDistintivo.TIMEOUT_PROP_PERMISO_DISTINTIVO_ITV);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceDistintivo.WEBSERVICE_PERMISO_DISTINTIVO_ITV));
		DefinitiveVehicleLicenseWS tramitarPermisoDistintivo = null;
		if(miUrl != null){
			DefinitiveVehicleLicenseServiceLocator permisoDistintivoTramitarLocator = new DefinitiveVehicleLicenseServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),permisoDistintivoTramitarLocator.getDefinitiveVehicleLicenseServiceWSDDServiceName());
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = permisoDistintivoTramitarLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapDistintivoWSHandler.class);
			
			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapDistintivoWSHandler.PROPERTY_KEY_ID, String.valueOf(id));
			filesConfig.put(SoapDistintivoWSHandler.PROPERTY_KEY_MATRICULA, matricula);
			logHandlerInfo.setHandlerConfig(filesConfig);
			
			list.add(logHandlerInfo);
			
			tramitarPermisoDistintivo = permisoDistintivoTramitarLocator.getDefinitiveVehicleLicenseService(miUrl);
			stubPD = (DefinitiveVehicleLicenseServiceWSBindingStub) tramitarPermisoDistintivo;
			stubPD.setTimeout(Integer.parseInt(timeOut));
		}
		return stubPD;
	}
	
	@Override
	public ResultadoDistintivoDgtBean procesarSolicitudDstv(ColaDto solicitud) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			String[] datos = solicitud.getXmlEnviar().split("_");
			TramiteTrafMatrVO tramite = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(new BigDecimal(datos[1]),
					Boolean.FALSE, Boolean.TRUE);
			if(tramite != null){
				resultado = llamadaWS(tramite, solicitud.getIdUsuario());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos para el expediente: " + solicitud.getIdTramite());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la solicitud distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	@Override
	public ResultadoDistintivoDgtBean procesarSolicitudDuplicadoDstv(ColaDto solicitud) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			VehNoMatOegamVO vehNoMatOegamVO = servicioDistintivoVehNoMat.getVehNoMatOegamVO(solicitud.getIdTramite().longValue());
			if(vehNoMatOegamVO != null){
				resultado = llamadaWSVNMO(vehNoMatOegamVO, solicitud.getIdUsuario());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se encuentran datos para el duplicado de distintivo con id: " + solicitud.getIdTramite());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la solicitud de duplicado de distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}
	
	@Override
	public ResultadoDistintivoDgtBean solicitarDstvAntiguos() {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			Date fechaPresentacion = new Date();
			List<TramiteTrafMatrVO> listaTramites = servicioTramiteTraficoMatriculacion.getListaTramitesPendientesImprimirDstvConFechaAnterior(fechaPresentacion);
			if(listaTramites != null && !listaTramites.isEmpty()){
				for(TramiteTrafMatrVO tramiteTrafMatrVO: listaTramites){
					ResultadoDistintivoDgtBean resultSol = servicioDistintivoDgt.solicitarDistintivoAntiguo(tramiteTrafMatrVO);
					if(resultSol.getError()){
						resultado.addError();
						resultado.addListaError("Ha sucedido un error para el expediente: " + tramiteTrafMatrVO.getNumExpediente() + ", error: " + resultSol.getMensaje());
					}
				}
				if(resultado.getListaError() != null && !resultado.getListaError().isEmpty()){
					enviarMailResumenErrores(resultado.getListaError());
				}
			} else {
				resultado.setMensaje("No existen peticiones pendientes de imprimir con fecha anterior a la fecha de presentación de hoy.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos anteriores a la fecha de hoy de presentación, error: ",e);
			resultado.setExcepcion(new Exception(e));
		}
		return resultado;
	}
	
	private void enviarMailResumenErrores(List<String> listaError) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try{
			String subject = "Resumen Proceso Solicitud Distintivos Anteriores";
			String direcciones = gestorPropiedades.valorPropertie("direcciones.excel.errores.distintivos");
			StringBuffer texto = new StringBuffer();
			texto.append("<br>");
			texto.append("Estos son los errores que se han producido a la hora de solicitar distintivos con fecha de presentación anterior a la del dia actual: ");
			texto.append("<br>");
			for(String error : listaError){
				texto.append("  - ").append(error);
				texto.append("<br>");
			}
			ResultBean resultEnvio = servicioCorreo.enviarCorreo(texto.toString(),Boolean.FALSE, null, subject, 
					direcciones, null, null, null, null);
			if(resultEnvio.getError()){
				for(String mensaje : resultEnvio.getListaMensajes()){
					log.error(mensaje);
				}
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo a la jefatura para la solicitud de impresion de los permisos de circulación, error:",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de enviar el correo a la jefatura para la solicitud de impresion de los permisos de circulación.");
		}
	}

}