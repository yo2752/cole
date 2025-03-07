package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.EstadoImpr;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.impr.service.ServicioDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvolucionGestionarImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImprFichas;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImprPermisos;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaGestionImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.CarpetaImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ImprExpBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImprImpl implements ServicioImpr{

	private static final long serialVersionUID = 8998112415448261178L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImprImpl.class);

	@Autowired
	ServicioPersistenciaGestionImpr servicioPersistenciaGestionImpr;

	@Autowired
	ServicioComunTramiteTrafico servicioComunTramiteTrafico;

	@Autowired
	ServicioEvolucionGestionarImpr servicioEvolucionGestionarImpr;

	@Autowired
	UtilesVistaTramites utiles;

	@Autowired
	ServicioComunContrato servicioContrato;

	@Autowired
	ServicioImprPermisos servicioImprPermisos;

	@Autowired
	ServicioImprFichas servicioImprFichas;

	@Autowired
	ServicioDocImpr servicioDocImpr;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoImprBean cambiarEstado(Long id, String estadoNuevo, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			ImprVO imprBBDD = servicioPersistenciaGestionImpr.getImprVO(id);
			validarImprCambioEstado(imprBBDD,id,estadoNuevo,resultado);
			if(!resultado.getError()) {
				servicioPersistenciaGestionImpr.actualiarEstado(id,estadoNuevo,idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del IMPR: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado del IMPR: " + id);
		}
		return resultado;
	}

	private void validarImprCambioEstado(ImprVO imprBBDD, Long id, String estadoNuevo, ResultadoImprBean resultado) {
		if(imprBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el IMPR: " + id);
		}else if(EstadoImpr.Tramitado_Telematicamente.getValorEnum().equals(estadoNuevo) || EstadoImpr.Finalizado.getValorEnum().equals(estadoNuevo)){
			TramiteTraficoVO tramiteTraficoVO = servicioComunTramiteTrafico.getTramite(imprBBDD.getNumExpediente(), Boolean.FALSE);
			if(!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equals(tramiteTraficoVO.getEstado().toString())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("El tramite no se encuentra en un estado finalizado por lo que no se podra actualizar el estado del IMPR" + id);
			}
		}
	}

	@Override
	public ResultadoImprBean modificarCarpeta(Long id, String carpetaNueva, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			ImprVO imprBBDD = servicioPersistenciaGestionImpr.getImprVO(id);
			validarImprModificarCarpeta(imprBBDD,id,resultado);
			if(!resultado.getError()) {
				servicioPersistenciaGestionImpr.modificarCarpeta(id,carpetaNueva,idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del IMPR: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado del IMPR: " + id);
		}
		return resultado;
	}

	private void validarImprModificarCarpeta(ImprVO imprBBDD, Long id, ResultadoImprBean resultado) {
		if(imprBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el IMPR: " + id);
		} else if(EstadoImpr.Finalizado.getValorEnum().equals(imprBBDD.getEstadoImpr())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede modificar la carpeta del IMPR: " + id + " porque se encuentra en un estado Finalizado.");
		}
	}

	@Override
	public ResultadoImprBean dividirImprSolicitudesPantalla(String codSeleccionados) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			List<ImprVO> listaImprBBDD = servicioPersistenciaGestionImpr.getListaImprPorIds(utiles.convertirArrayStringToListLong(codSeleccionados.split("_")));
			if(listaImprBBDD != null && !listaImprBBDD.isEmpty()) {
				for(ImprVO imprVO : listaImprBBDD) {
					if(!EstadoImpr.Tramitado_Telematicamente.getValorEnum().equals(imprVO.getEstadoImpr())) {
						resultado.addListaMensajeError("El IMPR: " + imprVO.getId() + " no se puede solicitar porque no se encuentra en estado " + EstadoImpr.Tramitado_Telematicamente.getNombreEnum());
					} else if(EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(imprVO.getEstadoSolicitud())) {
						resultado.addListaMensajeError("El IMPR: " + imprVO.getId() + " no se puede solicitar porque ya se ha obtenido su Documentacion con anterioridad.");
					} else {
						resultado.aniadirCarpeta(imprVO.getCarpeta(), imprVO.getTipoImpr(), imprVO.getTipoTramite(),
								imprVO.getTipoVehiculoImpr(), imprVO.getNumExpediente(), imprVO.getId(),imprVO.getIdContrato());
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen IMPR para los datos seleccionados.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de dividir los IMPR en listas para su solicitud, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de dividir los IMPR en listas para su solicitud.");
		}
		return resultado;
	}

	@Override
	public void solicitarImprEncarpetadosPantalla(Long idUsuario, ResultadoImprBean resultado) {
		try {
			for (CarpetaImprBean carpetaImpr : resultado.getListaEncarpetado()) {
				ContratoVO contrato = servicioContrato.getContrato(carpetaImpr.getIdContrato());
				if("NO".equals(gestorPropiedades.valorPropertie("nueva.gestion.imprDstv"))) {
					generarSolicitudesImprNocturnoEnCarpetasGestionImpr(carpetaImpr.getListaImprCarpeta(), carpetaImpr.getCarpeta(), carpetaImpr.getTipoImpr(), 
						carpetaImpr.getTipoVehiculoImpr(), carpetaImpr.getTipoTramite(), contrato, resultado, Boolean.TRUE);
				} else {
					generarSolicitudesImprNocturnoEnCarpetas(carpetaImpr.getListaImprCarpeta(), carpetaImpr.getCarpeta(), carpetaImpr.getTipoImpr(), 
						carpetaImpr.getTipoVehiculoImpr(), carpetaImpr.getTipoTramite(), contrato, resultado);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los IMPR, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los IMPR.");
		}
	}

	private void generarSolicitudesImprNocturnoEnCarpetas(List<ImprExpBean> listaImprCarpeta, String carpeta, String tipoImpr,
			String tipoVehiculoImpr, String tipoTramite, ContratoVO contrato, ResultadoImprBean resultado) {
		try {
			String refDocumento = tipoImpr + "_";
			if(StringUtils.isNotBlank(tipoVehiculoImpr)) {
				refDocumento += tipoVehiculoImpr + "_" + carpeta;
			} else {
				refDocumento += carpeta;
			}
			if(listaImprCarpeta.size() > 250) {
				List<List<ImprExpBean>> auxListas = Lists.partition(listaImprCarpeta, 250);
				int cont = 1;
				for(List<ImprExpBean> listaExpAux : auxListas) {
					String refDoc = refDocumento + "_" + cont;
					try {
						List<Long> listaExp = new ArrayList<Long>();
						for (ImprExpBean imprExp : listaExpAux) {
							listaExp.add(imprExp.getIdImpr());
						}
						if("2631".equals(contrato.getColegiado().getNumColegiado())){
							generarSolicitudImprNocturnoImpr(listaExp, tipoImpr, tipoTramite, contrato, refDoc, Boolean.TRUE, carpeta, resultado);
						}else {
							generarSolicitudImprNocturnoImpr(listaExp, tipoImpr, tipoTramite, contrato, refDoc, Boolean.FALSE, carpeta, resultado);
						}
					} catch (Throwable e) {
						log.error("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
								+ " para la carpeta: " + carpeta + 
								(StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: " + tipoVehiculoImpr : "") + ", error: ",e);
						resultado.addListaMensajeError("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
								+ " para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
										+ tipoVehiculoImpr : ""));
					}
					cont++;
				}
			} else {
				try {
					List<Long> listaExp = new ArrayList<>();
					for (ImprExpBean imprExp : listaImprCarpeta) {
						listaExp.add(imprExp.getIdImpr());
					}
					if("2631".equals(contrato.getColegiado().getNumColegiado())){
						generarSolicitudImprNocturnoImpr(listaExp, tipoImpr, tipoTramite, contrato, refDocumento, Boolean.TRUE, carpeta, resultado);
					}else {
						generarSolicitudImprNocturnoImpr(listaExp, tipoImpr, tipoTramite, contrato, refDocumento, Boolean.FALSE, carpeta, resultado);
					}
				} catch (Throwable e) {
					log.error("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
							+ " para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
									+ tipoVehiculoImpr : "") + ", error: ",e);
					resultado.addListaMensajeError("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
							+ " para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
									+ tipoVehiculoImpr : ""));
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las solicitudes de IMPR_NOCTURNO para el tipo tramite: " + tipoTramite + ", tipoImpr: " + tipoImpr + 
					" para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
							+ tipoVehiculoImpr : "")+ ", error: ",e);
			resultado.addListaMensajeError("Ha sucedido un error a la hora de generar las solicitudes de IMPR_NOCTURNO para el tipo tramite: " + tipoTramite + ", tipoImpr: " + tipoImpr + 
					" para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
							+ tipoVehiculoImpr : ""));
		}
	}

	private void generarSolicitudImprNocturnoImpr(List<Long> listaImpr, String tipoImpr, String tipoTramite, ContratoVO contrato, 
			String refDocumento, Boolean esEntornoAm, String carpeta, ResultadoImprBean resultado) {
		ResultadoImprBean resultSolImpr = null;
		resultSolImpr = generarColasImpr(listaImpr, tipoImpr, tipoTramite, contrato.getColegiado().getUsuario().getIdUsuario(), 
					contrato.getIdContrato(), esEntornoAm, refDocumento, carpeta);
		if(!resultSolImpr.getError()) {
			if(resultSolImpr.getExisteListaMensajeOk()) {
				resultado.addListaMensajeOk("Solicitud de Impr Nocturno generada correctamente para el tipoTramite" + tipoTramite + " de " + tipoImpr +
						" con referencia: " + refDocumento);
			} else {
				resultado.addListaMensajeError("Ha sucedido el siguiente error para el tipoTramite: " + tipoTramite + " de " + tipoImpr 
						+ " con referencia: " + refDocumento + " , error: " + resultSolImpr.getMensaje());
			}
		} else {
			resultado.addListaMensajeError("Ha sucedido el siguiente error para el tipoTramite" + tipoTramite + " de " + tipoImpr 
					+ " con referencia: " + refDocumento + " , error: " + resultSolImpr.getMensaje());
		}
	}

	private ResultadoImprBean generarColasImpr(List<Long> listaImpr, String tipoImpr, String tipoTramite,
			Long idUsuario, Long idContrato, Boolean esEntornoAm, String referenciaDocumento, String carpeta) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			String jefatura = servicioContrato.getJefaturaContrato(idContrato);
			if(TipoImpr.Permiso_Circulacion.getValorEnum().equals(tipoImpr)) {
				generarColaImprNocturno(listaImpr, idContrato, tipoTramite, idUsuario,
						jefatura, referenciaDocumento, esEntornoAm, TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(),
						TipoImpr.Permiso_Circulacion.getValorEnum(), carpeta, resultado);
			} else if(TipoImpr.Ficha_Tecnica.getValorEnum().equals(tipoImpr)) {
				generarColaImprNocturno(listaImpr, idContrato, tipoTramite, idUsuario,
						jefatura, referenciaDocumento, esEntornoAm, TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(),
						TipoImpr.Ficha_Tecnica.getValorEnum(), carpeta, resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las solicitudes para obtener los IMPR, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las solicitudes para obtener los IMPR.");
		}
		return resultado;
	}

	private void generarColaImprNocturno(List<Long> listaImpr, Long idContrato, String tipoTramite, Long idUsuario,
			String jefatura, String referenciaDocumento, Boolean esEntornoAm, String tipoSolicitudTramite, String tipoImpr,
			String carpeta, ResultadoImprBean resultado) {
		Date fechaImpr = new Date();
		ResultadoDocImprBean resultadoGenDocImpr = servicioDocImpr.generarDocImpr(idContrato, fechaImpr, tipoImpr, jefatura, idUsuario, 
				tipoTramite, referenciaDocumento, carpeta);
		if(!resultadoGenDocImpr.getError()){
			try {
				ResultadoDocImprBean resultadoDocImpr = servicioPersistenciaGestionImpr.generarDocImprNocturno(listaImpr, 
						resultadoGenDocImpr.getDocId(), idUsuario, tipoSolicitudTramite, esEntornoAm, idContrato);
				if(resultadoDocImpr.getError()){
					resultado.addListaMensajeError(resultadoDocImpr.getMensaje());
				} else {
					resultado.addListaMensajeOk("Documento con referencia Propia: " + referenciaDocumento + " solicitando sus " + TipoImpr.convertirTexto(tipoImpr));
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de cambiar los estados y generar la cola para los IMPR_NOCTURNOS del contrato: " + idContrato + " de la fecha: "+ fechaImpr + " del tipo " + tipoImpr + " y tipoTramite: " + tipoTramite + ", error: ",e);
				resultado.addListaMensajeError("Ha sucedido un error a la hora de cambiar los estados y generar la cola para los IMPR_NOCTURNOS del contrato: " + idContrato + " de la fecha: "+ fechaImpr + " del tipo " + tipoImpr + " y tipoTramite: " + tipoTramite);
			}
			if(resultado.getError()){
				servicioDocImpr.borrarDocImpr(resultadoGenDocImpr.getDocId());
			}
		}
	}

	private void generarSolicitudesImprNocturnoEnCarpetasGestionImpr(List<ImprExpBean> listaExpedientes, String carpeta,
			String tipoImpr, String tipoVehiculoImpr, String tipoTramite, ContratoVO contrato, ResultadoImprBean resultado,Boolean actualizarEstadoImpr) {
		try {
			String refDocumento = tipoImpr + "_";
			if(StringUtils.isNotBlank(tipoVehiculoImpr)) {
				refDocumento += tipoVehiculoImpr + "_" + carpeta;
			} else {
				refDocumento += carpeta;
			}
			if(listaExpedientes.size() > 250) {
				List<List<ImprExpBean>> auxListas = Lists.partition(listaExpedientes, 250);
				int cont = 1;
				for(List<ImprExpBean> listaExpAux : auxListas) {
					String refDoc = refDocumento + "_" + cont;
					try {
						List<BigDecimal> listaExp = new ArrayList<>();
						for (ImprExpBean imprExp : listaExpAux) {
							listaExp.add(imprExp.getNumExpediente());
						}
						if("2631".equals(contrato.getColegiado().getNumColegiado())){
							generarSolicitudImprNocturnoGestionImpr(listaExp, tipoImpr, tipoTramite, contrato, refDoc, Boolean.TRUE, resultado);
						}else {
							generarSolicitudImprNocturnoGestionImpr(listaExp, tipoImpr, tipoTramite, contrato, refDoc, Boolean.FALSE, resultado);
						}
						if(actualizarEstadoImpr) {
							actualizarEstadoCarpetaImpr(listaExpAux, contrato, resultado);
						}
					} catch (Throwable e) {
						log.error("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
								+ " para la carpeta: " + carpeta + 
								(StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: " + tipoVehiculoImpr : "") + ", error: ",e);
						resultado.addListaMensajeError("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
								+ " para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
										+ tipoVehiculoImpr : ""));
					}
					cont++;
				}
			} else {
				try {
					List<BigDecimal> listaExp = new ArrayList<>();
					for (ImprExpBean imprExp : listaExpedientes) {
						listaExp.add(imprExp.getNumExpediente());
					}
					if("2631".equals(contrato.getColegiado().getNumColegiado())){
						generarSolicitudImprNocturnoGestionImpr(listaExp, tipoImpr, tipoTramite, contrato, refDocumento, Boolean.TRUE, resultado);
					}else {
						generarSolicitudImprNocturnoGestionImpr(listaExp, tipoImpr, tipoTramite, contrato, refDocumento, Boolean.FALSE, resultado);
					}

					if(actualizarEstadoImpr) {
						actualizarEstadoCarpetaImpr(listaExpedientes, contrato, resultado);
					}
				} catch (Throwable e) {
					log.error("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
							+ " para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
									+ tipoVehiculoImpr : "") + ", error: ",e);
					resultado.addListaMensajeError("Ha sucedido un error a la hora de generar la solicitud de ImprNocturno para el tipo: " + tipoTramite + " de " + tipoImpr 
							+ " para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
									+ tipoVehiculoImpr : ""));
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las solicitudes de IMPR_NOCTURNO para el tipo tramite: " + tipoTramite + ", tipoImpr: " + tipoImpr + 
					" para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
							+ tipoVehiculoImpr : "")+ ", error: ",e);
			resultado.addListaMensajeError("Ha sucedido un error a la hora de generar las solicitudes de IMPR_NOCTURNO para el tipo tramite: " + tipoTramite + ", tipoImpr: " + tipoImpr + 
					" para la carpeta: " + carpeta + (StringUtils.isNotBlank(tipoVehiculoImpr) ? " y el tipo Vehiculo: "
							+ tipoVehiculoImpr : ""));
		}
	}

	private void actualizarEstadoCarpetaImpr(List<ImprExpBean> listaExpAux, ContratoVO contrato, ResultadoImprBean resultado) {
		for (ImprExpBean imprExpBean : listaExpAux) {
			try {
				servicioPersistenciaGestionImpr.actualizarGeneracionImpr(imprExpBean.getIdImpr(), contrato.getColegiado().getUsuario().getIdUsuario());
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de actualizar el estado de la generacion del IMPR: " + imprExpBean.getIdImpr() +
						" del expediente: " + imprExpBean.getNumExpediente() + ", error: ",e);
				resultado.addListaMensajeError("Ha sucedido un error a la hora de actualizar el estado de la generacion del IMPR: " + imprExpBean.getIdImpr() +
						" del expediente: " + imprExpBean.getNumExpediente());
			}
		}
	}

	private void generarSolicitudImprNocturnoGestionImpr(List<BigDecimal> listaExpedientes, String tipoImpr, String tipoTramite, ContratoVO contrato,
			String refDocumento, Boolean esEntornoAm, ResultadoImprBean resultado) {
		ResultadoImprBean resultSolImpr = null;
		if(TipoImpr.Permiso_Circulacion.getValorEnum().equals(tipoImpr)) {
			resultSolImpr = generarColasTramitesImpr(listaExpedientes, null, tipoImpr, tipoTramite, contrato.getColegiado().getUsuario().getIdUsuario(),
					contrato.getIdContrato(), esEntornoAm, refDocumento);
		} else {
			resultSolImpr = generarColasTramitesImpr(null, listaExpedientes, tipoImpr, tipoTramite, contrato.getColegiado().getUsuario().getIdUsuario(),
					contrato.getIdContrato(), esEntornoAm, refDocumento);
		}
		if(!resultSolImpr.getError()) {
			if(resultSolImpr.getExisteListaMensajeOk()) {
				resultado.addListaMensajeOk("Solicitud de Impr Nocturno generada correctamente para el tipoTramite" + tipoTramite + " de " + tipoImpr +
						" con referencia: " + refDocumento);
			} else {
				resultado.addListaMensajeError("Ha sucedido el siguiente error para el tipoTramite" + tipoTramite + " de " + tipoImpr
						+ " con referencia: " + refDocumento + " , error: " + resultSolImpr.getMensaje());
			}
		} else {
			resultado.addListaMensajeError("Ha sucedido el siguiente error para el tipoTramite" + tipoTramite + " de " + tipoImpr
					+ " con referencia: " + refDocumento + " , error: " + resultSolImpr.getMensaje());
		}
	}

	private ResultadoImprBean generarColasTramitesImpr(List<BigDecimal> listaExpedientesImprPC, List<BigDecimal> listaExpedientesImprFCT, String tipoImpr, String tipoTramite, Long idUsuario,
			Long idContrato, Boolean esEntornoAm, String referenciaDocumento) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			String jefatura = servicioContrato.getJefaturaContrato(idContrato);
			if(TipoImpr.Permiso_Circulacion.getValorEnum().equals(tipoImpr)) {
				servicioImprPermisos.generarColaExpedienteImprNocturno(listaExpedientesImprPC, idContrato, tipoTramite, idUsuario,
						jefatura, referenciaDocumento, esEntornoAm, resultado);
			} else if(TipoImpr.Ficha_Tecnica.getValorEnum().equals(tipoImpr)) {
				servicioImprFichas.generarColaExpedienteImprNocturno(listaExpedientesImprFCT, idContrato, tipoTramite, idUsuario,
						jefatura, referenciaDocumento, esEntornoAm, resultado);
			} else if(TipoImpr.Permiso_Y_Fichas.getValorEnum().equals(tipoImpr)) {
				if(listaExpedientesImprPC != null && !listaExpedientesImprPC.isEmpty()) {
					servicioImprPermisos.generarColaExpedienteImprNocturno(listaExpedientesImprPC, idContrato, tipoTramite, idUsuario,
							jefatura, referenciaDocumento, esEntornoAm, resultado);
				}
				if(listaExpedientesImprFCT != null && !listaExpedientesImprFCT.isEmpty()) {
					servicioImprFichas.generarColaExpedienteImprNocturno(listaExpedientesImprFCT, idContrato, tipoTramite, idUsuario,
							jefatura, referenciaDocumento, esEntornoAm, resultado);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar las solicitudes para obtener los IMPR, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las solicitudes para obtener los IMPR.");
		}
		return resultado;
	}

	@Override
	public List<ImprVO> getListaExpedientesPorListNumExp(Long[] ids) {
		try {
			if (ids != null) {
				return servicioPersistenciaGestionImpr.getListaExpedientesPorListNumExp(ids);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la lista de los tramites, error: ", e);
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoImprBean desasignarDocumento(Long id, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			ImprVO imprBBDD = servicioPersistenciaGestionImpr.getImprVO(id);
			validarImprDesasignarDocumento(imprBBDD,id,resultado);
			if(!resultado.getError()) {
				servicioPersistenciaGestionImpr.desasignarDocumento(id,idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar el documento del IMPR: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar el documento del IMPR: " + id);
		}
		return resultado;
	}

	private void validarImprDesasignarDocumento(ImprVO imprBBDD, Long id, ResultadoImprBean resultado) {
		if(imprBBDD == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el IMPR: " + id);
		} else if(imprBBDD.getDocId() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede desasignar el documento del IMPR: " + id + " porque no tiene asociado ninguno docId.");
		}
	}
}