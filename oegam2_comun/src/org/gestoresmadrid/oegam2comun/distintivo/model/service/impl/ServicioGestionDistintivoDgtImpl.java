package org.gestoresmadrid.oegam2comun.distintivo.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioGestionDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ConsultaDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.GestionDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGestionDistintivoDgtImpl implements ServicioGestionDistintivoDgt {

	private static final long serialVersionUID = 4268079834684525155L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionDistintivoDgtImpl.class);

	private String texto;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioDistintivoDgt servicioDistintivoDgt;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<GestionDistintivoDgtBean> convertirListaEnBeanPantalla(List<DocPermDistItvVO> listaBBDD) {
		if (listaBBDD != null && !listaBBDD.isEmpty()) {
			List<GestionDistintivoDgtBean> listaPrmDstv = new ArrayList<>();
			for (DocPermDistItvVO docPermDistItvVO : listaBBDD) {
				GestionDistintivoDgtBean gestionPermisoDistintivoItvBean = conversor.transform(docPermDistItvVO, GestionDistintivoDgtBean.class);
				gestionPermisoDistintivoItvBean.setDocId(docPermDistItvVO.getDocIdPerm());
				gestionPermisoDistintivoItvBean.setContrato(docPermDistItvVO.getContrato().getColegiado().getNumColegiado() + "-" + docPermDistItvVO.getContrato().getVia());
				gestionPermisoDistintivoItvBean.setFechaAlta(new SimpleDateFormat("dd/MM/yyyy").format(docPermDistItvVO.getFechaAlta()));
				gestionPermisoDistintivoItvBean.setTipoDocumento(TipoDocumentoImprimirEnum.convertirTexto(docPermDistItvVO.getTipo()));
				gestionPermisoDistintivoItvBean.setEstado(EstadoPermisoDistintivoItv.convertirTexto(docPermDistItvVO.getEstado().toString()));
				if (docPermDistItvVO.getTipoDistintivo() != null && !docPermDistItvVO.getTipoDistintivo().isEmpty()) {
					gestionPermisoDistintivoItvBean.setTipoDistintivo(TipoDistintivo.convertirValor(docPermDistItvVO.getTipoDistintivo()));
				}
				if (docPermDistItvVO.getJefatura() != null && !docPermDistItvVO.getJefatura().isEmpty()) {
					JefaturaTraficoDto jefatura = servicioJefaturaTrafico.getJefatura(docPermDistItvVO.getJefatura());
					if (jefatura != null) {
						gestionPermisoDistintivoItvBean.setJefaturaTrafico(jefatura.getDescripcion());
					}
				}
				if (docPermDistItvVO.getFechaImpresion() != null) {
					gestionPermisoDistintivoItvBean.setFechaImpresion(utilesFecha.formatoFecha("dd/MM/yyyy", docPermDistItvVO.getFechaImpresion()));
				}
				listaPrmDstv.add(gestionPermisoDistintivoItvBean);
			}
			return listaPrmDstv;
		}
		return Collections.emptyList();
	}

	@Override
	public void borrarFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	@Override
	public ResultadoDistintivoDgtBean solicitarDstvBloque(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numsExp = codSeleccionados.split("-");
				for (String numExp : numsExp) {
					try {
						ResultadoDistintivoDgtBean resultSolicitar = servicioDistintivoDgt.solicitarDistintivo(new BigDecimal(numExp), idUsuario);
						if (resultSolicitar.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("La Solicitud para el expediente " + numExp + " se ha generado correctamente.");
						}
					} catch (Throwable e) {
						log.error("No se ha podido solicitar el permiso o distintivo del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido solicitar el permiso o distintivo del tramite: "  + numExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder solicitar su permiso o distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los permisos o distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los permisos o distintivos.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean cambiarEstadoDstvBloque(String codSeleccionados, BigDecimal estadoNuevo, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numsExp = codSeleccionados.split("-");
				Date fechaSol = new Date();
				for(String numExp : numsExp){
					try {
						ResultadoDistintivoDgtBean resultCambEstado = servicioDistintivoDgt.cambiarEstado(
								new BigDecimal(numExp), estadoNuevo, Boolean.TRUE, Boolean.TRUE, fechaSol, idUsuario);
						if (resultCambEstado.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultCambEstado.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("La Solicitud con expediente " + numExp + " se ha cambiado de estado correctamente.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de cambiar el estado a la peticion de distintivos para el expediente: " + numExp + ", error: ", e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de cambiar el estado a la peticion de distintivos para el expediente: " + numExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder cambiar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la solicitud de los permisos o distintivos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la solicitud de los permisos o distintivos.");
		}
		return resultado;
	}

	@Override
	public List<ConsultaDistintivoDgtBean> convertirListaEnBeanPantallaConsulta(List<TramiteTrafMatrVO> listaBBDD) {
		List<ConsultaDistintivoDgtBean> listaPrmDstv = new ArrayList<>();
		for (TramiteTrafMatrVO tramiteTrafMatrVO : listaBBDD) {
			ConsultaDistintivoDgtBean consultaPermisoDistintivoBean = new ConsultaDistintivoDgtBean();
			consultaPermisoDistintivoBean.setNumExpediente(tramiteTrafMatrVO.getNumExpediente());
			consultaPermisoDistintivoBean.setMatricula(tramiteTrafMatrVO.getVehiculo().getMatricula());
			consultaPermisoDistintivoBean.setDescContrato(tramiteTrafMatrVO.getNumColegiado() + " - " + tramiteTrafMatrVO.getContrato().getVia());
			consultaPermisoDistintivoBean.setTipoDistintivo(TipoDistintivo.convertirValor(tramiteTrafMatrVO.getTipoDistintivo()));
			consultaPermisoDistintivoBean.setEstadoDstv(tramiteTrafMatrVO.getEstadoPetPermDstv() != null && !tramiteTrafMatrVO.getEstadoPetPermDstv().isEmpty() ?
					EstadoPermisoDistintivoItv.convertirTexto(tramiteTrafMatrVO.getEstadoPetPermDstv()) : EstadoPermisoDistintivoItv.Iniciado.getNombreEnum());
			consultaPermisoDistintivoBean.setDistintivo(tramiteTrafMatrVO.getDistintivo() != null && tramiteTrafMatrVO.getDistintivo().equals("S") ? "SI" : "NO");
			if (tramiteTrafMatrVO.getEstadoImpDstv() != null && !tramiteTrafMatrVO.getEstadoImpDstv().isEmpty()) {
				consultaPermisoDistintivoBean.setEstadoPetImp(EstadoPermisoDistintivoItv.convertirTexto(tramiteTrafMatrVO.getEstadoImpDstv()));
			}

			if (tramiteTrafMatrVO.getRespPetPermDstv() != null && !tramiteTrafMatrVO.getRespPetPermDstv().isEmpty()) {
				consultaPermisoDistintivoBean.setRespPetPermDstv(tramiteTrafMatrVO.getRespPetPermDstv());
			}
			if (tramiteTrafMatrVO.getRefPropia() != null && !tramiteTrafMatrVO.getRefPropia().isEmpty()) {
				consultaPermisoDistintivoBean.setRefPropia(tramiteTrafMatrVO.getRefPropia());
			}
			listaPrmDstv.add(consultaPermisoDistintivoBean);
		}
		return listaPrmDstv;
	}

	@Override
	public ResultadoDistintivoDgtBean generarDemandaDistintivos(String[] codSeleccionados, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (codSeleccionados != null) {
				resultado = servicioDistintivoDgt.generarDemandaDistintivos(codSeleccionados, idUsuario, tienePermisoAdmin);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen numero de expedientes para realizar la acción");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el distintivo con los expedientes seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el distintivo con los expedientes seleccionados");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean generarDistintivos(String[] numExpedientes, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (numExpedientes != null && numExpedientes.length > 0) {
				for (String numExpediente : numExpedientes) {
					try {
						ResultadoPermisoDistintivoItvBean resultSol = servicioDistintivoDgt.solicitarImpresionDistintivo(new BigDecimal(numExpediente), tienePermisoAdmin, idUsuario);
						if (resultSol.getError()) {
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSol.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("Solicitud de impresión generada para el expediente: " + numExpediente);
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de solicitar el distintivo, error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de solicitar el distintivo.");
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expedienete para generar su distintivo.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los distintivos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los distintivos seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean revertir(String[] numExpedientes, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			if (tienePermisoAdmin) {
				if (numExpedientes != null && numExpedientes.length > 0) {
					Date fechaSol = new Date();
					for (String numExp : numExpedientes) {
						try {
							ResultadoDistintivoDgtBean resultSolicitar = servicioDistintivoDgt.revertirDistintivo(new BigDecimal(numExp), idUsuario, fechaSol);
							if (resultSolicitar.getError()) {
								resultado.getResumen().addNumError();
								resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
							} else {
								resultado.getResumen().addNumOk();
								resultado.getResumen().addListaMensajeOk("El distintivo del expediente " + numExp + " se ha revertido correctamente.");
							}
						} catch (Throwable e) {
							log.error("No se ha podido revertir el distintivo del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e);
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError("No se ha podido revertir el distintivo del tramite: "  + numExp);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de seleccionar algún trámite para poder revertir su distintivo.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene permisos suficientes para realizar la accion de revertir los distintivos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir los distintivos de los trámites en bloque, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir los distintivos de los trámites.");
		}
		return resultado;
	}

	@Override
	public ResultadoDistintivoDgtBean desasignar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		if(codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] numExp = codSeleccionados.split(";");
			for(String numExpediente : numExp) {
				try {
					ResultadoDistintivoDgtBean resultDesasignar = servicioDistintivoDgt.desasignar(new BigDecimal(numExpediente), idUsuario);
					if (resultDesasignar.getError()) {
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultDesasignar.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("Documento: " + numExpediente + " se desasignó correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de desasignar los ids de los documentos, error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de desasignar los ids de los documentos.");
				}
			}
		}
		return resultado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}