package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.colegio.model.service.ServicioColegio;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildXmlMatwDistintivo;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioDistintivoDgtImpl implements ServicioDistintivoDgt {

	private static final long serialVersionUID = -5411546673225494290L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDistintivoDgtImpl.class);

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	DocPermDistItvDao docPermDistItvDao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	BuildXmlMatwDistintivo buildXmlMatwDistintivo;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioColegio servicioColegio;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioConsultaStock servicioConsultaStock;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionDstv;

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	TramiteTraficoMatrDao tramiteTraficoMatrDao;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Override
	public ResultadoDistintivoDgtBean guardarPdfDstv(byte[] distintivoPdf, BigDecimal numExpediente) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.DISTINTIVOS);
			fichero.setSubTipo(null);
			fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			fichero.setFicheroByte(distintivoPdf);
			fichero.setNombreDocumento(TipoImpreso.SolicitudDistintivo.getNombreEnum() + "_" + numExpediente);
			gestorDocumentos.guardarByte(fichero);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el pdf con el distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el pdf con el distintivo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarDistintivo(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			TramiteTrafMatrVO tramiteTrafMatr = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
			resultado = validarTramiteSolicitud(tramiteTrafMatr);
			if (!resultado.getError()) {
				String estadoAnt = tramiteTrafMatr.getEstadoPetPermDstv();
				tramiteTrafMatr.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
				servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatr);
				String ip = getIpConexion();
				ResultadoPermisoDistintivoItvBean result = servicioEvolucionDstv.guardarEvolucion(numExpediente, idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.SOLICITUD,
						new Date(), estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(), null,ip);
				if (!result.getError()) {
					String nombreFichero = TipoImpreso.SolicitudDistintivo.getNombreEnum() + "_" + tramiteTrafMatr.getNumExpediente() + "_" + ServicioDistintivoDgt.SOLICITUD_DSTV;
					ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.DSTV.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_2),
							TipoTramiteTrafico.Solicitud_Distintivo.getValorEnum(), null, idUsuario, null, new BigDecimal(tramiteTrafMatr.getContrato().getIdContrato()));
					if (resultCola.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar el distintivo medioambiental, error: ", e);
			resultado.setError(Boolean.TRUE);
			if (e.getMessage().contains("El trámite ya se encuentra en la cola")) {
				resultado.setMensaje("No se puede solicitar el distintivo porque ya se encuentra actualmente procesando. Por favor espere a la respuesta de la solicitud que estan en proceso.");
			} else {
				resultado.setMensaje("Ha sucedido un error a la hora de solicitar el distintivo medioambiental.");
			}
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarDistintivoMasivos(String matricula, Long idContrato, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> lista = servicioTramiteTraficoMatriculacion.getTramitePorMatriculaContrato(matricula, idContrato);
			if (lista != null && !lista.isEmpty()) {
				if (lista.size() > 1) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
				} else {
					TramiteTrafMatrVO tramiteTrafMatr = lista.get(0);
					resultado = validarTramiteSolicitud(tramiteTrafMatr);
					if (!resultado.getError()) {
						String estadoAnt = tramiteTrafMatr.getEstadoPetPermDstv();
						tramiteTrafMatr.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
						servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatr);
						String ip = getIpConexion();
						ResultadoPermisoDistintivoItvBean result = servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatr.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO,
								OperacionPrmDstvFicha.SOLICITUD_MSV, new Date(), estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(), null,ip);
						if (!result.getError()) {
							String nombreFichero = TipoImpreso.SolicitudDistintivo.getNombreEnum() + "_" + tramiteTrafMatr.getNumExpediente() + "_" + ServicioDistintivoDgt.SOLICITUD_DSTV;
							ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.DSTV.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_2),
									TipoTramiteTrafico.Solicitud_Distintivo.getValorEnum(), null, idUsuario, null, new BigDecimal(tramiteTrafMatr.getContrato().getIdContrato()));
							if (resultCola.getError()) {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje(resultCola.getMensaje());
							}
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han recuperado las siguientes matriculas " + matricula + ", ya que no son trámites de matriculación de OEGAM. ");
			}
		} catch (Exception |

				OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar el distintivo medioambiental, error: ", e);
			resultado.setError(Boolean.TRUE);
			if (e.getMessage().contains("El trámite ya se encuentra en la cola")) {
				resultado.setMensaje("No se puede solicitar el distintivo para la matricula: " + matricula
						+ " porque ya se encuentra actualmente procesando. Por favor espere a la respuesta de la solicitud que estan en proceso.");
			} else {
				resultado.setMensaje("Ha sucedido un error a la hora de solicitar el distintivo medioambiental para la matricula: " + matricula + ".");
			}
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarDistintivoAntiguo(TramiteTrafMatrVO tramiteTrafMatrVO) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			resultado = validarTramiteSolicitud(tramiteTrafMatrVO);
			if (!resultado.getError()) {
				String estadoAnt = tramiteTrafMatrVO.getEstadoPetPermDstv();
				tramiteTrafMatrVO.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
				servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatrVO);
				String ip = getIpConexion();
				ResultadoPermisoDistintivoItvBean result = servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(), new BigDecimal(tramiteTrafMatrVO.getContrato().getColegiado()
						.getIdUsuario()), TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.SOLICITUD, new Date(), estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(),
						null,ip);
				if (!result.getError()) {
					String nombreFichero = TipoImpreso.SolicitudDistintivo.getNombreEnum() + "_" + tramiteTrafMatrVO.getNumExpediente() + "_" + ServicioDistintivoDgt.SOLICITUD_DSTV;
					ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.DSTV.getNombreEnum(), nombreFichero, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_2),
							TipoTramiteTrafico.Solicitud_Distintivo.getValorEnum(), null, new BigDecimal(tramiteTrafMatrVO.getContrato().getColegiado().getIdUsuario()), null, new BigDecimal(
									tramiteTrafMatrVO.getContrato().getIdContrato()));
					if (resultCola.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar el distintivo medioambiental, error: ", e);
			resultado.setError(Boolean.TRUE);
			if (e.getMessage().contains("El trámite ya se encuentra en la cola")) {
				resultado.setMensaje("No se puede solicitar el distintivo porque ya se encuentra actualmente procesando. Por favor espere a la respuesta de la solicitud que estan en proceso.");
			} else {
				resultado.setMensaje("Ha sucedido un error a la hora de solicitar el distintivo medioambiental.");
			}
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarTramiteSolicitud(TramiteTrafMatrVO tramiteTrafMatr) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (tramiteTrafMatr == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para ese expediente en base de datos.");
		} else if (tramiteTrafMatr.getDistintivo() != null && tramiteTrafMatr.getDistintivo().isEmpty() && tramiteTrafMatr.getDistintivo().equals("S")) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede volver a solicitar el distintivo.");
		} else if (tramiteTrafMatr.getEstadoPetPermDstv() != null
				&& !EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(tramiteTrafMatr.getEstadoPetPermDstv())
				&& tramiteTrafMatr.getEstadoPetPermDstv() != null && !EstadoPermisoDistintivoItv.Finalizado_Error
						.getValorEnum().equals(tramiteTrafMatr.getEstadoPetPermDstv())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Para las matriculas" + tramiteTrafMatr.getVehiculo().getMatricula() + "Solo se pueden solicitar los distintivos en Estado Iniciado o Finalizado con Error");
		} else if (tramiteTrafMatr.getVehiculo() == null || tramiteTrafMatr.getVehiculo().getMatricula() == null || tramiteTrafMatr.getVehiculo().getMatricula().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se pueden solicitar los distintivos de aquellos tramites que tiene ya asignada una matricula.");
		} else if (tramiteTrafMatr.getVehiculo() == null || tramiteTrafMatr.getVehiculo().getBastidor() == null || tramiteTrafMatr.getVehiculo().getBastidor().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Solo se pueden solicitar los distintivos de aquellos tramites que tiene relleno el bastidor.");
		} else if (tramiteTrafMatr.getDocDistintivo() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El expediente " + tramiteTrafMatr.getNumExpediente() + " ya se ha impreso su distintivo con anterioridad.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Boolean accionesAsociadas, Boolean esDstv, Date fechaSol, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				TramiteTrafMatrVO tramiteTrafMatr = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
				if (tramiteTrafMatr != null) {
					if (accionesAsociadas) {
						if (EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum().equals(tramiteTrafMatr.getEstadoPetPermDstv())) {
							servicioCola.eliminar(tramiteTrafMatr.getNumExpediente(), ProcesosEnum.DSTV.getNombreEnum());
						}
					}
					String docId = null;
					if (tramiteTrafMatr.getDocDistintivoVO() != null && tramiteTrafMatr.getDocDistintivoVO().getDocIdPerm() != null) {
						docId = tramiteTrafMatr.getDocDistintivoVO().getDocIdPerm();
					}
					BigDecimal estadoAntiguo = new BigDecimal(tramiteTrafMatr.getEstadoPetPermDstv());
					tramiteTrafMatr.setEstadoPetPermDstv(estadoNuevo.toString());

					servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatr);
					String ip = getIpConexion();
					servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatr.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.CAMBIO_ESTADO, fechaSol,
							estadoAntiguo.toString(), estadoNuevo.toString(), docId,ip);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede cambiar el estado a la solicitud: " + numExpediente + " porque no se encuentran datos en BBDD.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede cambiar el estado a la solicitud ya que no ha indicado su numero de expediente.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado a la solicitud.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private String getIpConexion() {
		String ipConexion = "";
		try {
			ipConexion = ServletActionContext.getRequest().getRemoteAddr();
			if (ServletActionContext.getRequest().getHeader("client-ip") != null) {
				ipConexion = ServletActionContext.getRequest().getHeader("client-ip");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la IP de conexion.");
		}
		return ipConexion;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean solicitarImpresionDistintivo(BigDecimal numExpediente, Boolean tienePermisoAdmin, BigDecimal idUsuario) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				TramiteTrafMatrVO tramiteTrafMatrVO = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
				resultado = validarSolicitudImpresionDstv(tramiteTrafMatrVO, numExpediente, tienePermisoAdmin);
				if (!resultado.getError()) {
					Date fechaSolImp = new Date();
					String docId = resultado.getDocId();
					tramiteTrafMatrVO.setEstadoImpDstv(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
					tramiteTrafMatrVO.setFechaSolicitudDistintivo(fechaSolImp);
					servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatrVO);
					String ip = getIpConexion();
					servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.SOL_IMPRESION, fechaSolImp,
							EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), docId,ip);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un expediente para poder solicitar la impresion de su distintivo");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresion del distintivo " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion del distintivo " + numExpediente);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarImpresionDstvMasivo(String matricula, Long idContrato, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> lista = servicioTramiteTraficoMatriculacion.getTramitePorMatriculaContrato(matricula, idContrato);
			if (lista != null && !lista.isEmpty()) {
				if (lista.size() > 1) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
				} else {
					TramiteTrafMatrVO tramiteTrafMatr = lista.get(0);
					resultado = validacionGeneracionDocumentosMasivos(tramiteTrafMatr, matricula, null, Boolean.FALSE);
					if (!resultado.getError()) {
						Date fechaSolImp = new Date();
						String docId = resultado.getDocId();
						tramiteTrafMatr.setEstadoImpDstv(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
						tramiteTrafMatr.setFechaSolicitudDistintivo(fechaSolImp);
						servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatr);
						String ip = getIpConexion();
						servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatr.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.SOL_IMPRESION_MSV,
								fechaSolImp, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), docId,ip);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han recuperado las siguientes matriculas " + matricula + ", ya que no son trámites de matriculación de OEGAM. ");
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresion del distintivo con matricula" + matricula + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion del distintivo con matricula" + matricula);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarSolicitudImpresionDstv(TramiteTrafMatrVO tramiteTrafMatrBBDD, BigDecimal numExpediente, Boolean tienePermisoAdmin) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if (tramiteTrafMatrBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el expediente " + numExpediente + " en Base de Datos.");
		} else {
			if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoTramite())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El expediente " + tramiteTrafMatrBBDD.getNumExpediente() + " no se pueden solicitar su impresión de distintivos porque no es un trámite de Matriculación.");
			} else if (!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafMatrBBDD.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Telematicamente
					.getValorEnum().equals(tramiteTrafMatrBBDD.getEstado().toString()) && (tienePermisoAdmin && !EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafMatrBBDD
							.getEstado().toString()))) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El expediente " + tramiteTrafMatrBBDD.getNumExpediente()
						+ " no se encuentra en estado Finalizado Telematicamente o Finalizado Telematicamente Impreso para poder solicitar la impresión de su distintivo.");
			} else if (tramiteTrafMatrBBDD.getEstadoImpDstv() != null && !EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(tramiteTrafMatrBBDD.getEstadoImpDstv())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El expediente " + tramiteTrafMatrBBDD.getNumExpediente() + " ya se ha solicitado su impresión de distintivo o esta pendiente de imprimirse.");
			} else if (tramiteTrafMatrBBDD.getDocDistintivo() != null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El expediente " + tramiteTrafMatrBBDD.getNumExpediente() + " ya se ha impreso su distintivo con anterioridad.");
			} else if (!EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(tramiteTrafMatrBBDD.getEstadoPetPermDstv())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El expediente " + tramiteTrafMatrBBDD.getNumExpediente()
						+ " no se puede solicitar su impresión ya que no tiene distintivo asociado, por favor solicitele antes de generar su distintivo.");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean solicitarImpresionDemandaMasivo(List<String> matriculas, String nifTitular, Long idContrato, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(idContrato));
			List<TramiteTrafMatrVO> listaCero = new ArrayList<>();
			List<TramiteTrafMatrVO> listaEco = new ArrayList<>();
			List<TramiteTrafMatrVO> listaB = new ArrayList<>();
			List<TramiteTrafMatrVO> listaC = new ArrayList<>();
			List<TramiteTrafMatrVO> listaCeroMotos = new ArrayList<>();
			List<TramiteTrafMatrVO> listaEcoMotos = new ArrayList<>();
			List<TramiteTrafMatrVO> listaBMotos = new ArrayList<>();
			List<TramiteTrafMatrVO> listaCMotos = new ArrayList<>();
			for (String matricula : matriculas) {
				List<TramiteTrafMatrVO> lista = servicioTramiteTraficoMatriculacion.getTramitePorMatriculaContrato(matricula, idContrato);
				if (lista != null && !lista.isEmpty()) {
					if (lista.size() > 1) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Existen dos trámites para la misma matrícula. Por favor, verifique cuál es el correcto");
					} else {
						TramiteTrafMatrVO tramiteTrafMatrVO = lista.get(0);
						resultado = validacionGeneracionDocumentosMasivos(tramiteTrafMatrVO, matricula, nifTitular, Boolean.TRUE);
						if (resultado.getError()) {
							break;
						} else {
							if (TipoDistintivo.CEROMT.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaCeroMotos.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.ECOMT.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaEcoMotos.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.BMT.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaBMotos.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.CMT.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaCMotos.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.CERO.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaCero.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.ECO.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaEco.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.B.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaB.add(tramiteTrafMatrVO);
							} else if (TipoDistintivo.C.getValorEnum().equals(tramiteTrafMatrVO.getTipoDistintivo())) {
								listaC.add(tramiteTrafMatrVO);
							}
						}
					}
				}
			}
			if (!resultado.getError()) {
				generarDocDistintivo(resultado, listaCero, TipoDistintivo.CERO, idUsuario, contrato, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaEco, TipoDistintivo.ECO, idUsuario, contrato, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaB, TipoDistintivo.B, idUsuario, contrato, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaC, TipoDistintivo.C, idUsuario, contrato, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaCeroMotos, TipoDistintivo.CEROMT, idUsuario, contrato, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaEcoMotos, TipoDistintivo.ECOMT, idUsuario, contrato, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaBMotos, TipoDistintivo.BMT, idUsuario, contrato, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
				generarDocDistintivo(resultado, listaCMotos, TipoDistintivo.CMT, idUsuario, contrato, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD_MASIVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los distintivos de forma masiva, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar los distintivos de forma masiva.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} else {
			resultado.setMensaje("Solictud de impresion generada correctamente.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validacionGeneracionDocumentosMasivos(TramiteTrafMatrVO tramiteTrafMatrVO, String matricula, String nifTitular, Boolean esGenMasivoTitular) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (tramiteTrafMatrVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para la matricula " + matricula + " en Base de Datos.");
		} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafMatrVO.getTipoTramite())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La matricula + " + matricula + " no se puede generar porque no es un trámite de Matriculación");
		} else if (!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafMatrVO.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Telematicamente
				.getValorEnum().equals(tramiteTrafMatrVO.getEstado().toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La matricula  " + matricula + " no se puede generar porque el tramite no esta en estado Finalizado Telematicamente o Finalizado Telematicamente Impreso");
		} else if (tramiteTrafMatrVO.getDistintivo() == null || tramiteTrafMatrVO.getDistintivo().isEmpty() || "N".equals(tramiteTrafMatrVO.getDistintivo())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El distintivo para la matricula " + matricula + " no se encuentra disponible en nuestro sistema.");
		} else if (tramiteTrafMatrVO.getTipoDistintivo() == null || tramiteTrafMatrVO.getTipoDistintivo().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La matricula " + matricula + " no se puede generar porque no se ha recibido su tipo de distintivo, por favor vuelva a solicitarlo.");
		} else if ("S".equals(tramiteTrafMatrVO.getDistintivo()) && tramiteTrafMatrVO.getDocDistintivo() != null
				&& tramiteTrafMatrVO.getTipoDistintivo() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El distintivo para la matricula " + matricula + " ya se ha generado. Recibirá un correo electrónico indicando que están impresos y pueden pasar a retirarlos.");
		} else if (esGenMasivoTitular) {
			if (tramiteTrafMatrVO.getIntervinienteTraficos() == null || tramiteTrafMatrVO.getIntervinienteTraficos().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La matricula " + matricula + " no se puede generar porque no se ha podido comprobar si el titular indicado en la petición era el mismo del trámite.");
			} else {
				for (IntervinienteTraficoVO titular : tramiteTrafMatrVO.getIntervinienteTraficos()) {
					if (TipoInterviniente.Titular.getValorEnum().equals(titular.getId().getTipoInterviniente())) {
						if (!nifTitular.equals(titular.getId().getNif().toUpperCase())) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("La matricula " + matricula + " no se puede generar porque el titular del trámite no es el mismo que el indicado en la petición.");
							break;
						}
					}
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean generarDemandaDistintivos(String[] codSeleccionados, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			List<TramiteTrafMatrVO> listaTramitesMatrBBDD = servicioTramiteTraficoMatriculacion.getListaTramitesMatriculacion(utiles.convertirStringArrayToBigDecimal(codSeleccionados), Boolean.TRUE);
			ResultadoPermisoDistintivoItvBean result = validarTramitesGenerarDemanda(listaTramitesMatrBBDD, idUsuario, tienePermisoAdmin);
			if (!result.getError()) {
				List<TramiteTrafMatrVO> listaCero = result.getListaCero();
				List<TramiteTrafMatrVO> listaEco = result.getListaEco();
				List<TramiteTrafMatrVO> listaB = result.getListaB();
				List<TramiteTrafMatrVO> listaC = result.getListaC();
				List<TramiteTrafMatrVO> listaCeroMotos = result.getListaCeroMotos();
				List<TramiteTrafMatrVO> listaEcoMotos = result.getListaEcoMotos();
				List<TramiteTrafMatrVO> listaBMotos = result.getListaBMotos();
				List<TramiteTrafMatrVO> listaCMotos = result.getListaCMotos();
				List<TramiteTrafMatrVO> listaCarsharing = result.getListaCarsharing();
				List<TramiteTrafMatrVO> listaMotosharing = result.getListaMotosharing();
				if ((listaCero == null || listaCero.isEmpty()) && (listaEco == null || listaEco.isEmpty()) && (listaB == null || listaB.isEmpty()) && (listaC == null || listaC.isEmpty())
						&& (listaCeroMotos == null || listaCeroMotos.isEmpty()) && (listaEcoMotos == null || listaEcoMotos.isEmpty()) && (listaBMotos == null || listaBMotos.isEmpty())
						&& (listaCMotos == null || listaCMotos.isEmpty()) && (listaCarsharing == null || listaCarsharing.isEmpty())
						&& (listaMotosharing == null || listaMotosharing.isEmpty())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos de los distintivos para poder generarlos.");
				} else {
					ContratoVO contratoVo = listaTramitesMatrBBDD.get(0).getContrato();
					generarDocDistintivo(resultado, listaCero, TipoDistintivo.CERO, idUsuario, contratoVo, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaEco, TipoDistintivo.ECO, idUsuario, contratoVo, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaB, TipoDistintivo.B, idUsuario, contratoVo, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaC, TipoDistintivo.C, idUsuario, contratoVo, Boolean.FALSE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaCeroMotos, TipoDistintivo.CEROMT, idUsuario, contratoVo, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaEcoMotos, TipoDistintivo.ECOMT, idUsuario, contratoVo, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaBMotos, TipoDistintivo.BMT, idUsuario, contratoVo, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaCMotos, TipoDistintivo.CMT, idUsuario, contratoVo, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaCarsharing, TipoDistintivo.CARSHARING, idUsuario, contratoVo, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
					generarDocDistintivo(resultado, listaMotosharing, TipoDistintivo.MOTOSHARING, idUsuario, contratoVo, Boolean.TRUE, OperacionPrmDstvFicha.SOL_IMPRESION_DMD);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(result.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el distintivo con los expedientes seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el distintivo con los expedientes seleccionados");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public void generarDocDistintivo(ResultadoDistintivoDgtBean resultado, List<TramiteTrafMatrVO> listaTramites, TipoDistintivo tipoDistintivo, BigDecimal idUsuario, ContratoVO contratoVO,
			Boolean esMoto, OperacionPrmDstvFicha tipoOperacion) {
		try {
			if (listaTramites != null && !listaTramites.isEmpty()) {
				ResultadoPermisoDistintivoItvBean resultGenDoc = servicioDocPrmDstvFicha.generarDoc(idUsuario,
						new Date(), TipoDocumentoImprimirEnum.DISTINTIVO, tipoDistintivo,
						contratoVO.getIdContrato().longValue(), contratoVO.getJefaturaTrafico().getJefaturaProvincial(),
						Boolean.TRUE);
				if (!resultGenDoc.getError()) {
					Long idDoc = resultGenDoc.getIdDocPermDstvEitv();
					String docId = resultGenDoc.getDocId();
					for (TramiteTrafMatrVO tramiteTrafMatrVO : listaTramites) {
						Date fecha = new Date();
						tramiteTrafMatrVO.setDocDistintivo(idDoc);
						tramiteTrafMatrVO.setEstadoImpDstv(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
						tramiteTrafMatrVO.setFechaSolicitudDistintivo(fecha);
						servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatrVO);
						String ip = getIpConexion();
						servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO, tipoOperacion, fecha,
								EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(), docId,ip);
					}
					resultado = generarPeticionImpresionDistintivo(idDoc, idUsuario, contratoVO.getIdContrato(), tipoDistintivo.getNombreEnum(), ServicioDistintivoDgt.SOLICITUD_DSTV + "_" + idDoc);
					if (resultado != null && !resultado.getError()) {
						resultado.getResumen().addListaMensajeOk("Peticion de Impresión generada para el tipo de distintivo " + tipoDistintivo.getNombreEnum() + " con docId: " + idDoc);
					}
				} else {
					resultado.getResumen().addNumError();
					resultado.getResumen().addListaMensajeError("No se han podido generar los distintivos del Tipo: " + tipoDistintivo.getNombreEnum());
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error al generar los distintivos del Tipo: " + tipoDistintivo.getNombreEnum() + ", error: ", e);
			resultado.getResumen().addNumError();
			resultado.getResumen().addListaMensajeError("Ha sucedido un error al generar los distintivos del Tipo: " + tipoDistintivo.getNombreEnum());
		}
	}

	private ResultadoDistintivoDgtBean generarPeticionImpresionDistintivo(Long idDoc, BigDecimal idUsuario, Long idContrato, String tipoDistintivo, String datos) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMP_DSTV.getNombreEnum(), datos,
					gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum(),
					idDoc.toString(), idUsuario, null, new BigDecimal(idContrato));
			if (resultBean.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora encolar la peticion de impresion de los distintivos del tipo: " + tipoDistintivo + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion de los distintivos del tipo: " + tipoDistintivo);
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarTramitesGenerarDemanda(List<TramiteTrafMatrVO> listaTramitesMatrBBDD, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (listaTramitesMatrBBDD != null && !listaTramitesMatrBBDD.isEmpty()) {
				Long idContrato = listaTramitesMatrBBDD.get(0).getContrato().getIdContrato();
				for (TramiteTrafMatrVO tramiteTrafMatrBBDD : listaTramitesMatrBBDD) {
					ResultadoPermisoDistintivoItvBean resultVal = validacionGeneracionDocumentos(tramiteTrafMatrBBDD, tienePermisoAdmin, idContrato);
					if (resultVal.getError()) {
						resultado = resultVal;
						break;
					} else {
						if (TipoDistintivo.CEROMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaCeroMotos(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.ECOMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaEcoMotos(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.BMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaBMotos(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.CMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaCMotos(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.CERO.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaCero(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.ECO.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaEco(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.B.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaB(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.C.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaC(tramiteTrafMatrBBDD);
						} else if (TipoDistintivo.CARSHARING.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaCarsharing(tramiteTrafMatrBBDD);
						}else if (TipoDistintivo.MOTOSHARING.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
							resultado.addListaMotosharing(tramiteTrafMatrBBDD);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El tramite " + tramiteTrafMatrBBDD.getNumExpediente() + " no tiene tipo de distintivo asociado.");
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido recuperar datos de los trámites seleccionados.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar a demanda los distintivos seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar a demanda los distintivos seleccionados.");
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validacionGeneracionDocumentos(TramiteTrafMatrVO tramiteTrafMatrBBDD, Boolean tienePermisoAdmin, Long idContrato) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if (tramiteTrafMatrBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para ese expediente en Base de Datos.");
		} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoTramite())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Sólo se pueden solicitar distintivos para los trámites de Matriculación");
		} else if (!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafMatrBBDD.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Telematicamente
				.getValorEnum().equals(tramiteTrafMatrBBDD.getEstado().toString()) && (tienePermisoAdmin && !EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafMatrBBDD.getEstado()
						.toString()))) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Sólo se pueden solicitar distintivos con el estado Finalizado Telematicamente o Finalizado Telematicamente Impreso");
		} else if (tramiteTrafMatrBBDD.getDistintivo() == null || tramiteTrafMatrBBDD.getDistintivo().isEmpty() || "N".equals(tramiteTrafMatrBBDD.getDistintivo())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El distintivos para el expediente" + tramiteTrafMatrBBDD.getNumExpediente() + " no se encuentra disponible en nuestro sistema.");
		} else if ("S".equals(tramiteTrafMatrBBDD.getDistintivo()) && tramiteTrafMatrBBDD.getDocDistintivo() != null
				&& tramiteTrafMatrBBDD.getTipoDistintivo() != null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El distintivos para el expediente" + tramiteTrafMatrBBDD.getNumExpediente()
					+ " ya se ha generado. Recibirá un correo electrónico indicando que están impresos y pueden pasar a retirarlos.");
		} else if(idContrato != tramiteTrafMatrBBDD.getContrato().getIdContrato()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El distintivos para el expediente" + tramiteTrafMatrBBDD.getNumExpediente()
					+ " no se puede generar porque no es del mismo contrato.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TramiteTrafMatrVO> getListaTramitesPorDocId(Long idDocPermDistItv) {
		try {
			return servicioTramiteTraficoMatriculacion.getListaTramitesDistintivosPorDocId(idDocPermDistItv);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de los distintivos del docId: " + idDocPermDistItv + ", error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean revertirDistintivo(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			TramiteTrafMatrVO tramiteTrafMatr = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
			resultado = validarTramRevertir(tramiteTrafMatr, numExpediente);
			if (!resultado.getError()) {
				String estadoAnt = tramiteTrafMatr.getEstadoImpDstv();
				String docId = tramiteTrafMatr.getDocDistintivoVO().getDocIdPerm();
				tramiteTrafMatr.setEstadoImpDstv(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafMatr.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafMatr.setDocDistintivo(null);
				tramiteTrafMatr.setDocDistintivoVO(null);
				servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatr);
				String ip = getIpConexion();
				ResultadoPermisoDistintivoItvBean resultEstado = servicioEvolucionDstv.guardarEvolucion(numExpediente,
						idUsuario, TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.REVERTIR, fecha,
						estadoAnt, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), docId, ip);
				if (resultEstado.getError()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultEstado.getMensaje());
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de revertir el permiso para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir el permiso para el expediente: " + numExpediente);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean validarTramRevertir(TramiteTrafMatrVO tramiteTrafMatr, BigDecimal numExpediente) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if (tramiteTrafMatr == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el expediente" + numExpediente);
		} else if (!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(tramiteTrafMatr.getEstadoImpDstv())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el distintivo para el expediente" + numExpediente + ", porque no se encuentra impreso todavia.");
		} else if (tramiteTrafMatr.getDocDistintivo() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el distintivo para el expediente" + numExpediente + ", porque no se encuentran datos de su documento.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean generarDistintivoNoche(ContratoDto contrato) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		String descContrato = contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia();
		try {
			List<TramiteTrafMatrVO> listaTramites = servicioTramiteTraficoMatriculacion.getListaTramitesContratoDistintivos(contrato.getIdContrato().longValue());
			List<VehNoMatOegamVO> listaDuplicados = servicioDistintivoVehNoMat.getListaVehiculosPdteImpresionPorContrato(contrato.getIdContrato().longValue());
			ResultadoDistintivoDgtBean resultListas = dividirListasEnTiposDistintivos(listaTramites, listaDuplicados);
			if (!resultListas.getError()) {
				Date fecha = new Date();
				if ((resultListas.getListaCero() != null && !resultListas.getListaCero().isEmpty()) || (resultListas.getListaCeroVNMO() != null && !resultListas.getListaCeroVNMO().isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaCero(), resultListas.getListaCeroVNMO(), resultado, TipoDistintivo.CERO, contrato, fecha);
				}
				if ((resultListas.getListaEco() != null && !resultListas.getListaEco().isEmpty()) || (resultListas.getListaEcoVNMO() != null && !resultListas.getListaEcoVNMO().isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaEco(), resultListas.getListaEcoVNMO(), resultado, TipoDistintivo.ECO, contrato, fecha);
				}
				if ((resultListas.getListaC() != null && !resultListas.getListaC().isEmpty()) || (resultListas.getListaCVNMO() != null && !resultListas.getListaCVNMO().isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaC(), resultListas.getListaCVNMO(), resultado, TipoDistintivo.C, contrato, fecha);
				}
				if ((resultListas.getListaB() != null && !resultListas.getListaB().isEmpty()) || (resultListas.getListaBVNMO() != null && !resultListas.getListaBVNMO().isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaB(), resultListas.getListaBVNMO(), resultado, TipoDistintivo.B, contrato, fecha);
				}
				if ((resultListas.getListaCeroMotos() != null && !resultListas.getListaCeroMotos().isEmpty()) || (resultListas.getListaCeroMotosVNMO() != null && !resultListas.getListaCeroMotosVNMO()
						.isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaCeroMotos(), resultListas.getListaCeroMotosVNMO(), resultado, TipoDistintivo.CEROMT, contrato, fecha);
				}
				if ((resultListas.getListaEcoMotos() != null && !resultListas.getListaEcoMotos().isEmpty()) || (resultListas.getListaEcoMotosVNMO() != null && !resultListas.getListaEcoMotosVNMO()
						.isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaEcoMotos(), resultListas.getListaEcoMotosVNMO(), resultado, TipoDistintivo.ECOMT, contrato, fecha);
				}
				if ((resultListas.getListaCMotos() != null && !resultListas.getListaCMotos().isEmpty()) || (resultListas.getListaCMotosVNMO() != null && !resultListas.getListaCMotosVNMO()
						.isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaCMotos(), resultListas.getListaCMotosVNMO(), resultado, TipoDistintivo.CMT, contrato, fecha);
				}
				if ((resultListas.getListaBMotos() != null && !resultListas.getListaBMotos().isEmpty()) || (resultListas.getListaBMotosVNMO() != null && !resultListas.getListaBMotosVNMO()
						.isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaBMotos(), resultListas.getListaBMotosVNMO(), resultado, TipoDistintivo.BMT, contrato, fecha);
				}
				if ((resultListas.getListaCarsharing() != null && !resultListas.getListaCarsharing().isEmpty()) || (resultListas.getListaCarsharingVNMO() != null && !resultListas.getListaCarsharingVNMO()
						.isEmpty())) {
					generarDistintivosConDoc(resultListas.getListaCarsharing(), resultListas.getListaCarsharingVNMO(), resultado, TipoDistintivo.CARSHARING, contrato, fecha);
				}
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar por la noche los documentos de distintivos para el contrato: " + descContrato + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar por la noche los documentos de distintivos para el contrato: " + descContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void generarDistintivosConDoc(List<BigDecimal> listaTramites, List<Long> listaDuplicados, ResultadoDistintivoDgtBean resultado, TipoDistintivo tipoDstv,
			ContratoDto contrato, Date fecha) {
		ResultadoDistintivoDgtBean resultGen = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		String descContrato = contrato.getColegiadoDto().getNumColegiado() + " - " + contrato.getVia();
		try {
			ResultadoPermisoDistintivoItvBean result = servicioDocPrmDstvFicha.generarDoc(contrato.getColegiadoDto().getUsuario().getIdUsuario(), fecha, TipoDocumentoImprimirEnum.DISTINTIVO, tipoDstv,
					contrato.getIdContrato().longValue(), contrato.getJefaturaTraficoDto().getJefaturaProvincial(), Boolean.FALSE);
			if (!result.getError()) {
				DocPermDistItvVO docPermDistItvVO = result.getDocPermDistItv();
				resultGen = actualizarDocIdListasDistintivos(docPermDistItvVO, listaTramites, listaDuplicados, fecha, contrato.getColegiadoDto().getUsuario().getIdUsuario());
				if (!resultGen.getError()) {
					resultGen = generarPeticionImpresionDistintivo(docPermDistItvVO.getIdDocPermDistItv(), contrato.getColegiadoDto().getUsuario().getIdUsuario(), docPermDistItvVO.getIdContrato(),
							TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()), ServicioDistintivoDgt.IMPRESION_PROCESO_DSTV + "_" + docPermDistItvVO.getIdDocPermDistItv());
				}
				if (resultGen.getError()) {
					resultado.getResumen().addNumError();
					resultado.getResumen().addListaMensajeError("No se han podido generar los distintivos del tipo: " + tipoDstv.getNombreEnum() + " para el contrato " + descContrato + " error: "
							+ resultGen.getMensaje());
				} else {
					resultado.getResumen().addNumOk();
					resultado.getResumen().addListaMensajeOk("Distintivos del tipo: " + tipoDstv.getNombreEnum() + " generados para el contrato " + descContrato);
				}
			} else {
				resultado.getResumen().addNumError();
				resultado.getResumen().addListaMensajeError("No se han podido generar los distintivos del tipo: " + tipoDstv.getNombreEnum() + " para el contrato " + descContrato + " error: " + result
						.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedidio un error a la hora de generar los distintivos de tipo " + tipoDstv.getNombreEnum() + " para el contrato: " + descContrato + ", error: ", e);
			resultado.getResumen().addNumError();
			resultado.getResumen().addListaMensajeError("Ha sucedidio un error a la hora de generar los distintivos de tipo " + tipoDstv.getNombreEnum() + " para el contrato: " + descContrato
					+ " para el número de expediente " + listaTramites.get(0));
		}
	}

	private ResultadoDistintivoDgtBean actualizarDocIdListasDistintivos(DocPermDistItvVO docPermDistItvVO, List<BigDecimal> listaTramites, List<Long> listaDuplicados, Date fecha,
			BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (listaTramites != null && !listaTramites.isEmpty()) {
				resultado = servicioTramiteTraficoMatriculacion.indicarDocIdTramites(listaTramites, docPermDistItvVO.getIdDocPermDistItv(), idUsuario, fecha, docPermDistItvVO.getDocIdPerm(), getIpConexion());
			}
			if (listaDuplicados != null && !listaDuplicados.isEmpty() && !resultado.getError()) {
				resultado = servicioDistintivoVehNoMat.indicarDocIdVehiculos(listaDuplicados, docPermDistItvVO.getIdDocPermDistItv(), idUsuario, fecha, docPermDistItvVO.getDocIdPerm());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar las listas con el id del Documento, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar las listas con el id del Documento.");
		}
		return resultado;
	}

	private ResultadoDistintivoDgtBean dividirListasEnTiposDistintivos(List<TramiteTrafMatrVO> listaTramites, List<VehNoMatOegamVO> listaDuplicados) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (listaTramites != null && !listaTramites.isEmpty()) {
				for (TramiteTrafMatrVO tramiteTrafMatrBBDD : listaTramites) {
					if (TipoDistintivo.CERO.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaCero(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.ECO.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaEco(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.B.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaB(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.C.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaC(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.CEROMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaCeroMotos(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.ECOMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaEcoMotos(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.BMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaBMotos(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.CMT.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaCMotos(tramiteTrafMatrBBDD.getNumExpediente());
					} else if (TipoDistintivo.CARSHARING.getValorEnum().equals(tramiteTrafMatrBBDD.getTipoDistintivo())) {
						resultado.addListaCarsharing(tramiteTrafMatrBBDD.getNumExpediente());
					}
				}
			}
			if (listaDuplicados != null && !listaDuplicados.isEmpty()) {
				for (VehNoMatOegamVO vehNoMatOegamBBDD : listaDuplicados) {
					if (TipoDistintivo.CERO.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaCeroVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.ECO.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaEcoVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.B.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaBVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.C.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaCVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.CEROMT.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaCeroMotosVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.ECOMT.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaEcoMotosVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.BMT.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaBMotosVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.CMT.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaCMotosVNMO(vehNoMatOegamBBDD.getId());
					} else if (TipoDistintivo.CARSHARING.getValorEnum().equals(vehNoMatOegamBBDD.getTipoDistintivo())) {
						resultado.addListaCarsharingVNMO(vehNoMatOegamBBDD.getId());
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de dividir las listas por tipo de distintivo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de dividir las listas por tipo de distintivo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean desasignar(BigDecimal numExpediente, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			TramiteTrafMatrVO  tramiteTrafMatr = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(numExpediente, Boolean.FALSE, Boolean.TRUE);
			if (tramiteTrafMatr != null) {
				String estadoAnt = tramiteTrafMatr.getEstadoImpDstv();
				String docId = tramiteTrafMatr.getDocDistintivoVO().getDocIdPerm();
				tramiteTrafMatr.setEstadoImpDstv(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafMatr.setEstadoPetPermDstv(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafMatr.setDocDistintivo(null);
				tramiteTraficoMatrDao.actualizar(tramiteTrafMatr);
				String ip = getIpConexion();
				servicioEvolucionDstv.guardarEvolucion(tramiteTrafMatr.getNumExpediente(), idUsuario,
						TipoDocumentoImprimirEnum.DISTINTIVO, OperacionPrmDstvFicha.DESASIGNAR, new Date(), estadoAnt,
						tramiteTrafMatr.getEstadoImpDstv(), docId, ip);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar los documentos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar los documentos.");
		}
		return resultado;
	}

}