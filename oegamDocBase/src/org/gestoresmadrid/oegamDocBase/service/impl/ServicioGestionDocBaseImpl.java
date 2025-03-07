package org.gestoresmadrid.oegamDocBase.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.docbase.enumerados.DocBaseEstadoEnum;
import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.docbase.enumerados.TipoTramiteDocBase;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.AcreditacionTrafico;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.TipoCarpeta;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseTipoCarpetaEnum;
import org.gestoresmadrid.oegamDocBase.service.ServicioDocBase;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.service.ServicioImpresionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.DocumentoBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.GestionDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.gestoresmadrid.utilidades.listas.GenericComparator;
import org.gestoresmadrid.utilidades.listas.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGestionDocBaseImpl implements ServicioGestionDocBase {

	private static final long serialVersionUID = -7998519489997243619L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionDocBaseImpl.class);

	@Autowired
	ServicioDocBase servicioDocBase;

	@Autowired
	ServicioImpresionDocBase servicioImpresionDocBase;

	@Autowired
	ServicioComunContrato servicioContrato;

	@Autowired
	ServicioComunCorreo servicioCorreo;

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public List<GestionDocBaseBean> convertirListaEnBeanPantallaConsulta(List<DocumentoBaseVO> listaBBDD) {
		List<GestionDocBaseBean> listaGestionDB = new ArrayList<GestionDocBaseBean>();
		for (DocumentoBaseVO documentoBaseVO : listaBBDD) {
			GestionDocBaseBean gestionDocBaseBean = new GestionDocBaseBean();
			gestionDocBaseBean.setDescContrato(documentoBaseVO.getContrato().getColegiado().getNumColegiado() + " - " + documentoBaseVO.getContrato().getVia());
			gestionDocBaseBean.setDocId(documentoBaseVO.getDocId());
			gestionDocBaseBean.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yyyy", documentoBaseVO.getFechaPresentacion()));
			gestionDocBaseBean.setId(documentoBaseVO.getId());
			gestionDocBaseBean.setEstado(DocBaseEstadoEnum.convertirTexto(documentoBaseVO.getEstado()));
			gestionDocBaseBean.setCarpeta(TipoCarpeta.convertirValor(documentoBaseVO.getCarpeta()));
			listaGestionDB.add(gestionDocBaseBean);
		}
		return listaGestionDB;
	}

	@Override
	public void borrarFichero(File fichero) {
		gestorDocumentos.borradoRecursivo(fichero);
	}

	@Override
	public ResultadoDocBaseBean descargarDocBase(String idDocBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		Boolean esZip = Boolean.FALSE;
		try {
			if (idDocBase != null && !idDocBase.isEmpty()) {
				String[] idsDocBase = idDocBase.split("_");
				if (idsDocBase.length > 1) {
					esZip = Boolean.TRUE;
					url = gestorPropiedades.valorPropertie(RUTA_FICH_TEMP) + "zip" + System.currentTimeMillis();
					out = new FileOutputStream(url);
					zip = new ZipOutputStream(out);
					for (String id : idsDocBase) {
						ResultadoDocBaseBean resultDescgDB = servicioDocBase.descargarDocBase(new Long(id));
						if (!resultDescgDB.getError()) {
							ZipEntry zipEntry = new ZipEntry(resultDescgDB.getNombreFichero());
							zip.putNextEntry(zipEntry);
							zip.write(gestorDocumentos.transformFiletoByte(resultDescgDB.getFichero()));
							zip.closeEntry();
						} else {
							resultado.setMensaje(resultDescgDB.getMensaje());
							resultado.setError(Boolean.TRUE);
							break;
						}
					}
					if (!resultado.getError()) {
						File fichero = new File(url);
						resultado.setEsZip(Boolean.TRUE);
						resultado.setNombreFichero(NOMBRE_ZIP + System.currentTimeMillis() + ConstantesGestorFicheros.EXTENSION_ZIP);
						resultado.setFichero(fichero);
					}
				} else {
					resultado = servicioDocBase.descargarDocBase(new Long(idsDocBase[0]));
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún documento base para su descarga.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar los documento base de la lista: " + idDocBase + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar los documento base.");
		} finally {
			if (esZip) {
				try {
					zip.close();
				} catch (IOException e) {
					log.error("Ha sucedido un error a la hora de cerrar el zip, error: ", e);
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultadoDocBaseBean eliminarDocBase(String idDocBase, Boolean tienePermisoAdmin, Boolean esUsuarioTrafico) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			resultado = validarDatosEliminarDocBase(idDocBase, tienePermisoAdmin, esUsuarioTrafico);
			if (!resultado.getError()) {
				String[] idsDocBase = idDocBase.split("_");
				for (String id : idsDocBase) {
					try {
						ResultadoDocBaseBean resultElimDB = servicioDocBase.eliminar(new Long(id));
						if (!resultElimDB.getError()) {
							resultElimDB = servicioDocBase.borrarPDFDocBase(new Long(id));
							if (!resultElimDB.getError()) {
								resultado.addResumenOK(resultElimDB.getMensaje());
							} else {
								resultado.addResumenError(resultElimDB.getMensaje());
							}
						} else {
							resultado.addResumenError(resultElimDB.getMensaje());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de eliminar el docBase con id: " + id + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de eliminar el docBase con id: " + id);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los documento base de la lista: " + idDocBase + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar los documento base.");
		}
		return resultado;
	}

	private ResultadoDocBaseBean validarDatosEliminarDocBase(String idDocBase, Boolean tienePermisoAdmin, Boolean esUsuarioTrafico) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		if (!tienePermisoAdmin && !esUsuarioTrafico) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene permisos para realizar la accion de eliminar un documento base.");
		} else if (StringUtils.isBlank(idDocBase)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún documento para poder eliminar.");
		}
		return resultado;
	}

	@Override
	public ResultadoDocBaseBean imprimirDocBase(Long idDocBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			resultado = servicioDocBase.getDocBase(idDocBase, Boolean.TRUE);
			if (!resultado.getError()) {
				DocumentoBaseVO docBase = resultado.getDocumentoBase();
				resultado = validarDocBaseImpresion(docBase);
				if (!resultado.getError()) {
					resultado = servicioImpresionDocBase.imprimirDocBase(docBase);
					if (!resultado.getError()) {
						resultado = servicioDocBase.actualizarImpresionDocBase(docBase.getId(), resultado.getDocBaseFinal());
					}
				}
				if (resultado.getError()) {
					docBase.setEstado(DocBaseEstadoEnum.ERROR_IMPRESION.getValorEnum());
					servicioDocBase.actualizar(docBase);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el documeno base con id: " + idDocBase + ", error: ", e);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private ResultadoDocBaseBean validarDocBaseImpresion(DocumentoBaseVO docBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		if (!DocBaseEstadoEnum.CREADO_DOC_LOGICO.getValorEnum().equals(docBase.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El documento base con docId: " + docBase.getDocId() + " no se encuentra en un estado valido para imprimir.");
		} else if (docBase.getTramitesTraficoAsList() == null || docBase.getTramitesTraficoAsList().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El documento base con docId: " + docBase.getDocId() + " no tiene tramites asociados para imprimir.");
		}
		return resultado;
	}

	@Override
	public ResultadoDocBaseBean reimprimirDocBaseErroneo(String idDocBase, boolean tienePermisoAdmin, boolean esUsuarioTrafico) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			resultado = validarDatosReimprimirDocBase(idDocBase, tienePermisoAdmin, esUsuarioTrafico);
			if (!resultado.getError()) {
				String[] idsDocBase = idDocBase.split("_");
				for (String id : idsDocBase) {

					ResultadoDocBaseBean resultadoDocBase = servicioDocBase.getDocBase(Long.parseLong(id), Boolean.TRUE);
					DocumentoBaseVO docBase = resultadoDocBase.getDocumentoBase();
					if (docBase == null) {
						resultado.addResumenError(resultadoDocBase.getMensaje());
					} else if (!docBase.getEstado().equalsIgnoreCase(DocBaseEstadoEnum.ERROR_IMPRESION.getValorEnum())) {
						resultado.addResumenError("No es posible imprimir el documento base con docId: " + docBase.getDocId().trim() + " porque no está en estado Error de impresión del documento.");
					} else if (docBase.getTramitesTraficoAsList() == null || docBase.getTramitesTraficoAsList().isEmpty()) {
						resultado.addResumenError("El documento base con docId: " + docBase.getDocId() + " no tiene trámites asociados para imprimir.");
					} else {

						try {
							ResultadoDocBaseBean resultReimprimirDB = servicioDocBase.reimprimirDocBaseErroneo(docBase, docBase.getContrato().getColegiado().getIdUsuario());
							if (!resultReimprimirDB.getError()) {
								resultado.addResumenOK(resultReimprimirDB.getMensaje());
							} else {
								resultado.addResumenError(resultReimprimirDB.getMensaje());
							}

						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de imprimir el docBase con id: " + id + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de imprimir el docBase con id: " + id);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los documento base de la lista: " + idDocBase + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los documento base.");
		}
		return resultado;
	}
	
	private ResultadoDocBaseBean validarDatosReimprimirDocBase(String idDocBase, boolean tienePermisoAdmin, boolean esUsuarioTrafico) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		if (!tienePermisoAdmin && !esUsuarioTrafico) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene permisos para realizar la acción de reimprimir un documento base.");
		} else if (StringUtils.isBlank(idDocBase)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún documento para poder reimprimir.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoDocBaseBean cambiarEstadoDocBase(String idDocBase, String estadoNuevo, boolean tienePermisoAdmin, boolean esUsuarioTrafico) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			resultado = validarDatosCambiarEstadoDocBase(idDocBase, estadoNuevo, tienePermisoAdmin, esUsuarioTrafico);
			if (!resultado.getError()) {
				String[] idsDocBase = idDocBase.split("_");
				for (String id : idsDocBase) {

					ResultadoDocBaseBean resultadoDocBase = servicioDocBase.getDocBase(Long.parseLong(id), Boolean.FALSE);
					DocumentoBaseVO docBase = resultadoDocBase.getDocumentoBase();
					if (docBase == null) {
						resultado.addResumenError(resultadoDocBase.getMensaje());
					} else if (StringUtils.isNotBlank(docBase.getEstado()) && docBase.getEstado().equalsIgnoreCase(estadoNuevo)) {
						resultado.addResumenError("El estado nuevo del documento base: " + docBase.getDocId().trim() + " es el mismo al actual.");
					} else {
						docBase.setEstado(estadoNuevo);
						servicioDocBase.actualizar(docBase);
						resultado.addResumenOK("El estado del documento base: " + docBase.getDocId().trim() + " se ha cambiado correctamente a " + DocBaseEstadoEnum.convertirTexto(estadoNuevo) + ".");
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los documentos base: " + idDocBase + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado los documentos base.");
		}
		return resultado;
	}
	
	private ResultadoDocBaseBean validarDatosCambiarEstadoDocBase(String idDocBase, String estadoNuevo, boolean tienePermisoAdmin, boolean esUsuarioTrafico) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		if (!tienePermisoAdmin && !esUsuarioTrafico) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene permisos para realizar la acción de cambiar estado a un documento base.");
		} else if (StringUtils.isBlank(estadoNuevo)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún estado a cambiar.");
		} else if (StringUtils.isBlank(idDocBase)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar algún documento para poder cambiar su estado.");
		}
		return resultado;
	}

	@Override
	public ResultadoDocBaseBean gestionDocBaseNocturno() {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			Date fechaPresentacion = null;
			;
			if ("SI".equals(gestorPropiedades.valorPropertie("forzar.docBase")) && (gestorPropiedades.valorPropertie("fecha.forzar.docBase") != null && !gestorPropiedades.valorPropertie(
					"fecha.forzar.docBase").isEmpty())) {
				fechaPresentacion = utilesFecha.formatoFecha("ddMMYYYY", gestorPropiedades.valorPropertie("fecha.forzar.docBase"));
			} else {
				fechaPresentacion = utilesFecha.getPrimerLaborableAnterior().getFecha();
			}
			List<Long> listaIdsContratos = servicioTramiteTrafico.getListaIdsContratosFinalizadosTelematicamenteDocBase(fechaPresentacion);
			if (listaIdsContratos != null && !listaIdsContratos.isEmpty()) {
				for (Long idContrato : listaIdsContratos) {
					ContratoVO contratoVO = servicioContrato.getContrato(idContrato);
					if (contratoVO != null) {
						generarDocBaseNocturnoPorTipoTramite(idContrato, contratoVO.getColegiado().getUsuario().getIdUsuario(), fechaPresentacion, TipoTramiteTrafico.TransmisionElectronica,
								resultado);
						generarDocBaseNocturnoPorTipoTramite(idContrato, contratoVO.getColegiado().getUsuario().getIdUsuario(), fechaPresentacion, TipoTramiteTrafico.Matriculacion, resultado);
						if ("SI".equals(gestorPropiedades.valorPropertie("generar.docBase.btv"))) {
							generarDocBaseNocturnoPorTipoTramite(idContrato, contratoVO.getColegiado().getUsuario().getIdUsuario(), fechaPresentacion, TipoTramiteTrafico.Baja, resultado);
						}
					} else {
						resultado.addResumenError("No existen datos del contrato con id: " + idContrato);
					}
				}
				enviarMailResultadoGestionDocBaseNocturno(resultado, fechaPresentacion);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen contrato con tramites finalizados.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de gestion los documentos base en el proceso nocturno, error: ", e);
			resultado.setExcepcion(new Exception(e));
		}
		return resultado;
	}

	private void enviarMailResultadoGestionDocBaseNocturno(ResultadoDocBaseBean resultado, Date fechaPresentacion) {
		try {
			StringBuffer resultadoHtml = new StringBuffer();
			if (resultado.getResumen().getNumOk() != null && resultado.getResumen().getNumOk() > 0) {
				resultadoHtml.append("<br>El proceso de generacion de documentos base nocturnos con fecha presentacion " + new SimpleDateFormat("dd/MM/yyyy").format(fechaPresentacion)
						+ "  ha tenido las siguientes ejecuciones correctas: <br>");
				for (String mensaje : resultado.getResumen().getListaOk()) {
					resultadoHtml.append("- " + mensaje);
					resultadoHtml.append("<br>");
				}
			}
			if (resultado.getResumen().getNumError() != null && resultado.getResumen().getNumError() > 0) {
				resultadoHtml.append("<br>El proceso de generacion de documentos base nocturnos con fecha presentacion" + new SimpleDateFormat("dd/MM/yyyy").format(fechaPresentacion)
						+ "  ha tenido los siguientes errores: <br>");
				for (String mensaje : resultado.getResumen().getListaError()) {
					resultadoHtml.append("- " + mensaje);
					resultadoHtml.append("<br>");
				}
			}
			String subject = "Ejecucion Diaria Documento Base Nocturno";
			String direcciones = gestorPropiedades.valorPropertie("direcciones.correo.docBaseNocturno");
			ResultadoBean resultEnvio = servicioCorreo.enviarCorreo(resultadoHtml.toString(), Boolean.FALSE, null, subject, direcciones, null, null, null);
			if (resultEnvio.getError()) {
				for (String mensaje : resultEnvio.getListaMensajes()) {
					log.error(mensaje);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de enviar el correo con el resultado de la creacion de colas, error: ", e);
			resultado.addListaMensaje("Ha sucedido un error a la hora de enviar el correo con el resultado de la creacion de colas.");
			resultado.setError(Boolean.TRUE);
		}
	}

	private void generarDocBaseNocturnoPorTipoTramite(Long idContrato, Long idUsuario, Date fechaPresentacion, TipoTramiteTrafico tipoTramite, ResultadoDocBaseBean resultado) {
		try {
			List<TramiteTraficoVO> listaTramitesBBDD = servicioTramiteTrafico.getListaTramitesDocBaseNocturno(idContrato, fechaPresentacion, tipoTramite.getValorEnum());
			if (listaTramitesBBDD != null && !listaTramitesBBDD.isEmpty()) {
				ResultadoDocBaseBean resultDividirTram = dividirTramitesDocBase(listaTramitesBBDD, idContrato);
				if (!resultDividirTram.getError()) {
					String carpeta = obtenerTipoCarpetaNocturnoTipoTramite(tipoTramite);
					generarDocBase(new BigDecimal(idContrato), idUsuario, fechaPresentacion, carpeta, resultDividirTram.getListaDocBaseBean(), resultado);
				} else {
					resultado.addResumenError("Ha sucedido un error a la hora generar los documentos base nocturno para el contrato: " + idContrato + " y el tipo de tramite: " + tipoTramite
							.getNombreEnum() + ", error: " + resultDividirTram.getMensaje());
				}
			} else {
				resultado.addResumenError("No se han tramites validos para poder generar el documento base para el contrato: " + idContrato + ", con fecha de presentacion " + utilesFecha.formatoFecha(
						"dd/MM/yyyy", fechaPresentacion) + " y tipo tramite: " + tipoTramite.getNombreEnum());

			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora generar los documentos base nocturno para el contrato: " + idContrato + " y el tipo de tramite: " + tipoTramite.getNombreEnum() + ", error: ", e);
			resultado.addResumenError("Ha sucedido un error a la hora generar los documentos base nocturno para el contrato: " + idContrato + " y el tipo de tramite: " + tipoTramite.getNombreEnum());
		}
	}

	private String obtenerTipoCarpetaNocturnoTipoTramite(TipoTramiteTrafico tipoTramite) {
		String carpeta = null;
		if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite.getValorEnum())) {
			carpeta = TipoCarpeta.CARPETA_CTIT.getValorEnum();
		} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite.getValorEnum())) {
			carpeta = TipoCarpeta.CARPETA_MATE.getValorEnum();
		} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite.getValorEnum())) {
			carpeta = TipoCarpeta.CARPETA_BTV.getValorEnum();
		}
		return carpeta;
	}

	private void generarDocBase(BigDecimal idContrato, Long idUsuario, Date fechaPresentacion, String carpeta, List<DocumentoBaseBean> listaDocBaseBean, ResultadoDocBaseBean resultado) {
		Boolean existeError = Boolean.FALSE;
		Boolean existeOk = Boolean.FALSE;
		if (listaDocBaseBean != null) {
			for (DocumentoBaseBean docBaseBean : listaDocBaseBean) {
				ResultadoDocBaseBean resultG = servicioDocBase.crearDocBase(carpeta, docBaseBean.getListaTramites(), idContrato.longValue(), idUsuario, fechaPresentacion, null, Boolean.TRUE);
				if (resultG.getError()) {
					existeError = Boolean.TRUE;
				} else {
					existeOk = Boolean.TRUE;
				}
			}
			if (existeError && existeOk) {
				resultado.addResumenError("La ejecucion ha sido parcialmente correcta para el contrato: " + idContrato + " y la carpeta: " + carpeta);
			} else if (existeError) {
				resultado.addResumenError("Ejecucion con errores para el contrato: " + idContrato + " y la carpeta: " + carpeta);
			} else {
				resultado.addResumenOK("Ejecucion correcta para el contrato: " + idContrato + " y la carpeta: " + carpeta);
			}
		}
	}

	@Override
	public ResultadoDocBaseBean procesarPeticionDocBase(Long idImpresion, Long idContrato, String xmlEnviar, Long idUsuario) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		String tipoCarpeta = null;
		ContratoVO contratoVO = null;
		try {
			resultado = servicioImpresionDocBase.obtenerDatosIdImpresionDocBase(idImpresion);
			contratoVO = servicioContrato.getContrato(idContrato);
			if (!resultado.getError()) {
				tipoCarpeta = resultado.getTipoCarpeta();
				resultado = dividirTramitesDocBase(resultado.getListaTramites(), idContrato);
				Boolean existeError = Boolean.FALSE;
				Boolean existeOk = Boolean.FALSE;
				if (!resultado.getError()) {
					for (DocumentoBaseBean docBaseBean : resultado.getListaDocBaseBean()) {
						ResultadoDocBaseBean resultG = servicioDocBase.crearDocBase(tipoCarpeta, docBaseBean.getListaTramites(), idContrato, idUsuario, utilesFecha.formatoFecha("ddMMyyyy", xmlEnviar
								.split("_")[1]), idImpresion, Boolean.FALSE);
						if (resultG.getError()) {
							existeError = Boolean.TRUE;

							String direccionCorreo = contratoVO.getCorreoElectronico();
							if (contratoVO.getCorreosTramites() != null && !contratoVO.getCorreosTramites().isEmpty()) {
								for (CorreoContratoTramiteVO correoContratoTramite : contratoVO.getCorreosTramites()) {
									if (TipoTramiteDocBase.GENERACION.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
										direccionCorreo = correoContratoTramite.getCorreoElectronico();
										break;
									}
								}
							}

							enviarMailErrorGenDocBase(docBaseBean.getListaTramites(), tipoCarpeta, resultG.getMensaje(), direccionCorreo);
						} else {
							existeOk = Boolean.TRUE;
						}
					}
					if (existeError && existeOk) {
						resultado.setError(Boolean.FALSE);
						resultado.setMensaje("La ejecucion ha sido parcialmente correcta para el contrato: " + contratoVO.getIdContrato() + " y la carpeta: " + tipoCarpeta);
					} else if (existeError) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Ejecucion con errores para el contrato: " + contratoVO.getIdContrato() + " y la carpeta: " + tipoCarpeta);
					} else {
						resultado.setError(Boolean.FALSE);
						resultado.setMensaje("Ejecucion correcta para el contrato: " + contratoVO.getIdContrato() + " y la carpeta: " + tipoCarpeta);
					}
				} else {
					enviarMailErrorGenDocBase(null, tipoCarpeta, resultado.getMensaje(), contratoVO.getCorreoElectronico());
				}
			} else {
				enviarMailErrorGenDocBase(null, null, resultado.getMensaje(), contratoVO.getCorreoElectronico());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el documeno base, error: ", e);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private void enviarMailErrorGenDocBase(List<BigDecimal> listaTramites, String tipoCarpeta, String mensajeError, String correoElectronico) {
		try {
			String texto = "";
			if (listaTramites != null && !listaTramites.isEmpty()) {
				texto = "No se han podido generar los documentos base para los siguientes tramites: ";
				String numsExp = "";
				for (BigDecimal numExpediente : listaTramites) {
					if (numsExp.isEmpty()) {
						numsExp = numExpediente.toString();
					} else {
						numsExp += ", " + numExpediente.toString();
					}
				}
				texto += numsExp + ". Por el siguiente motivo: " + mensajeError;

			} else if (tipoCarpeta != null && !tipoCarpeta.isEmpty()) {
				texto = "No se han podido generar el documento base de los expedientes para la carpeta: " + tipoCarpeta + " por el siguiente error: " + mensajeError;
			} else {
				texto = "No se han podido generar el documento base de los expedientes por el siguiente error: " + mensajeError;
			}
			servicioCorreo.enviarCorreo(texto, Boolean.FALSE, null, "Error Generacion Documento Base", correoElectronico, null, null, null);
		} catch (Throwable e) {
			log.error("Ha sucedidio un error a la hora de enviar el correo de aviso de error en la generación del Doc.Base, error: ", e);
		}
	}

	@Override
	public void borrarImpresionDocBase(Long idImpresion, Long idUsuario) {
		try {
			servicioImpresionDocBase.borrarDatosImpresionDocBase(idImpresion, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar la impresion: " + idImpresion + ", error: ");
		}
	}

	@Override
	public void enviarMailError(Long idImpresion, Long idContrato) {
		try {
			ResultadoDocBaseBean resultado = servicioImpresionDocBase.obtenerDatosIdImpresionDocBase(idImpresion);
			ContratoVO contratoVO = servicioContrato.getContrato(idContrato);
			String texto = "";
			if (resultado.getListaTramites() != null && !resultado.getListaTramites().isEmpty()) {
				texto = "No se han podido generar los documentos base para los siguientes tramites: ";
				String numsExp = "";
				for (TramiteTraficoVO tramiteTraficoVO : resultado.getListaTramites()) {
					if (numsExp.isEmpty()) {
						numsExp = tramiteTraficoVO.getNumExpediente().toString();
					} else {
						numsExp += ", " + tramiteTraficoVO.getNumExpediente().toString();
					}
				}
			}

			String direccionCorreo = contratoVO.getCorreoElectronico();
			if (contratoVO.getCorreosTramites() != null && !contratoVO.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contratoVO.getCorreosTramites()) {
					if (TipoTramiteDocBase.GENERACION.getValorEnum().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())) {
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			servicioCorreo.enviarCorreo(texto, Boolean.FALSE, null, "Error Generacion Documento Base", direccionCorreo, null, null, null);
		} catch (Throwable e) {
			log.error("Ha sucedidio un error a la hora de enviar el correo de aviso de error en la generación del Doc.Base, error: ", e);
		}
	}

	private OrdenDocBaseEnum obtenerCriterioOrdenacionTramitesDocBase(Long idContrato) {
		OrdenDocBaseEnum ordenDocBase = servicioContrato.obtenerOrdenDocBase(idContrato);
		return (ordenDocBase != null) ? ordenDocBase : OrdenDocBaseEnum.Matricula;
	}

	private ResultadoDocBaseBean dividirTramitesDocBase(List<TramiteTraficoVO> listaTramites, Long idContrato) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			String tipoTramite = listaTramites.get(0).getTipoTramite();
			OrdenDocBaseEnum ordenDocBase = obtenerCriterioOrdenacionTramitesDocBase(idContrato);
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
				int contTotal = 1;
				List<String> listaMatriculas = new ArrayList<String>();
				List<String> listaMatriculasSinSimul = new ArrayList<String>();
				for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
					listaMatriculas.add(tramiteTraficoVO.getVehiculo().getMatricula());
				}
				Set<String> listaAux = new HashSet<String>();
				listaAux.addAll(listaMatriculas);
				listaMatriculas.clear();
				listaMatriculas.addAll(listaAux);
				listaMatriculasSinSimul.addAll(listaAux);
				DocumentoBaseBean documentoBaseBean = new DocumentoBaseBean();
				int total = 1;
				List<BigDecimal> listaExpedientesSimultaneas = null;
				int totalSinBorrar = listaMatriculas.size();
				for (String matricula : listaMatriculas) {
					listaExpedientesSimultaneas = new ArrayList<BigDecimal>();
					for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
						if (tramiteTraficoVO.getVehiculo().getMatricula().equals(matricula)) {
							listaExpedientesSimultaneas.add(tramiteTraficoVO.getNumExpediente());
						}
					}
					if (listaExpedientesSimultaneas.size() > 1) {
						for (BigDecimal numExpediente : listaExpedientesSimultaneas) {
							documentoBaseBean.addListaTramites(numExpediente);
						}
						listaExpedientesSimultaneas = new ArrayList<BigDecimal>();
						listaMatriculasSinSimul.remove(matricula);
						if (contTotal == 100) {
							resultado.addListaDocumentoBase(documentoBaseBean);
							documentoBaseBean = new DocumentoBaseBean();
							contTotal = 0;
						} else if (total == totalSinBorrar) {
							resultado.addListaDocumentoBase(documentoBaseBean);
						}
						total++;
						contTotal++;
					}
				}
				if (listaMatriculasSinSimul != null && !listaMatriculasSinSimul.isEmpty() && listaMatriculasSinSimul.size() > 0) {
					if (OrdenDocBaseEnum.Matricula.getValorEnum().equals(ordenDocBase.getValorEnum())) {
						ordenarDocBasePorMatricula(listaMatriculasSinSimul, listaTramites, resultado, documentoBaseBean, contTotal);
					} else if (OrdenDocBaseEnum.Numero_Expediente.getValorEnum().equals(ordenDocBase.getValorEnum())) {
						ordenarDocBasePorTipo("numExpediente", resultado, documentoBaseBean, listaMatriculasSinSimul, listaTramites, contTotal);
					} else if (OrdenDocBaseEnum.Referencia_propia.getValorEnum().equals(ordenDocBase.getValorEnum())) {
						ordenarDocBasePorTipo("refPropia", resultado, documentoBaseBean, listaMatriculasSinSimul, listaTramites, contTotal);
					}
				}
			} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
				ordenarTramitesDocBase(listaTramites, resultado, ordenDocBase);
			} else {
				if ("SI".equals(gestorPropiedades.valorPropertie("generar.docBase.matwSuperTelematicos"))) {
					ordenarTramitesDocBase(listaTramites, resultado, ordenDocBase);
				} else {
					int total = 0;
					Boolean esJefOk = Boolean.FALSE;
					String sJefaturasAuto = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.matw");
					String colegiadosImprTodos = gestorPropiedades.valorPropertie("colegiado.impresion.impr.matw");
					List<TramiteTraficoVO> listaAuxTramites = new ArrayList<TramiteTraficoVO>();
					for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
						if (tramiteTraficoVO.getVehiculo().getNive() == null || tramiteTraficoVO.getVehiculo().getNive().isEmpty()) {
							listaAuxTramites.add(total++, tramiteTraficoVO);
						} else {
							esJefOk = Boolean.FALSE;
							if (sJefaturasAuto != null && !sJefaturasAuto.isEmpty()) {
								String[] jefaturasAuto = sJefaturasAuto.split(",");
								for (String jefaturoProv : jefaturasAuto) {
									if (jefaturoProv.equals(tramiteTraficoVO.getContrato().getJefaturaTrafico().getJefaturaProvincial())) {
										esJefOk = Boolean.TRUE;
									}
								}
							}
							if (!esJefOk) {
								if (colegiadosImprTodos == null || colegiadosImprTodos.isEmpty()) {
									listaAuxTramites.add(total++, tramiteTraficoVO);
								} else if (!colegiadosImprTodos.contains(tramiteTraficoVO.getContrato().getColegiado().getNumColegiado())) {
									listaAuxTramites.add(total++, tramiteTraficoVO);
								}
							}
						}
					}
					if (listaAuxTramites != null && !listaAuxTramites.isEmpty() && total > 0) {
						ordenarTramitesDocBase(listaAuxTramites, resultado, ordenDocBase);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de dividir la lista de tramites en los doc.Base, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de dividir la lista de tramites en los doc.Base");
		}
		return resultado;
	}

	private void ordenarTramitesDocBase(List<TramiteTraficoVO> listaTramites, ResultadoDocBaseBean resultado, OrdenDocBaseEnum ordenDocBase) {
		if (OrdenDocBaseEnum.Matricula.getValorEnum().equals(ordenDocBase.getValorEnum())) {
			Collections.sort(listaTramites, new GenericComparator<TramiteTraficoVO>("vehiculo.matricula", OrderType.asc));
		} else if (OrdenDocBaseEnum.Numero_Expediente.getValorEnum().equals(ordenDocBase.getValorEnum())) {
			Collections.sort(listaTramites, new GenericComparator<TramiteTraficoVO>("numExpediente", OrderType.asc));
		} else if (OrdenDocBaseEnum.Referencia_propia.getValorEnum().equals(ordenDocBase.getValorEnum())) {
			Collections.sort(listaTramites, new GenericComparator<TramiteTraficoVO>("refPropia", OrderType.asc));
		}
		final List<List<TramiteTraficoVO>> auxListas = Lists.partition(listaTramites, 100);
		for (List<TramiteTraficoVO> lista : auxListas) {
			DocumentoBaseBean documentoBaseBean = new DocumentoBaseBean();
			for (TramiteTraficoVO tramiteTraficoVO : lista) {
				documentoBaseBean.addListaTramites(tramiteTraficoVO.getNumExpediente());
			}
			resultado.addListaDocumentoBase(documentoBaseBean);
		}
	}

	private void ordenarDocBasePorMatricula(List<String> listaMatriculasSinSimul, List<TramiteTraficoVO> listaTramites, ResultadoDocBaseBean resultado, DocumentoBaseBean documentoBaseBean,
			int contTotal) {
		List<String> listaMatriculasLetras = new ArrayList<String>();
		List<String> listaMatriculasNumeros = new ArrayList<String>();
		for (String matricula : listaMatriculasSinSimul) {
			if (Character.isUpperCase(matricula.charAt(0)) || Character.isLowerCase(matricula.charAt(0))) {
				listaMatriculasLetras.add(matricula);
			} else {
				listaMatriculasNumeros.add(matricula);
			}
		}
		Collections.sort(listaMatriculasLetras);
		Collections.sort(listaMatriculasNumeros);
		int total = 1;
		for (String matriculaLetra : listaMatriculasLetras) {
			for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
				if (tramiteTraficoVO.getVehiculo().getMatricula().equals(matriculaLetra)) {
					documentoBaseBean.addListaTramites(tramiteTraficoVO.getNumExpediente());
					break;
				}
			}
			if (contTotal == 100) {
				resultado.addListaDocumentoBase(documentoBaseBean);
				documentoBaseBean = new DocumentoBaseBean();
				contTotal = 0;
			} else if (total == listaMatriculasSinSimul.size()) {
				resultado.addListaDocumentoBase(documentoBaseBean);
			}
			total++;
			contTotal++;
		}
		for (String matriculaLetra : listaMatriculasNumeros) {
			for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
				if (tramiteTraficoVO.getVehiculo().getMatricula().equals(matriculaLetra)) {
					documentoBaseBean.addListaTramites(tramiteTraficoVO.getNumExpediente());
					break;
				}
			}
			if (contTotal == 100) {
				resultado.addListaDocumentoBase(documentoBaseBean);
				documentoBaseBean = new DocumentoBaseBean();
				contTotal = 0;
			} else if (total == listaMatriculasSinSimul.size()) {
				resultado.addListaDocumentoBase(documentoBaseBean);
			}
			total++;
			contTotal++;
		}
	}

	private void ordenarDocBasePorTipo(String ordenExpedientes, ResultadoDocBaseBean resultado, DocumentoBaseBean documentoBaseBean, List<String> listaMatriculas, List<TramiteTraficoVO> listaTramites,
			int contTotal) {
		List<TramiteTraficoVO> listaTramitesOrden = new ArrayList<>();
		for (String matriculaLetra : listaMatriculas) {
			for (TramiteTraficoVO tramiteTraficoVO : listaTramites) {
				if (tramiteTraficoVO.getVehiculo().getMatricula().equals(matriculaLetra)) {
					listaTramitesOrden.add(tramiteTraficoVO);
					break;
				}
			}
		}
		Collections.sort(listaTramitesOrden, new GenericComparator<TramiteTraficoVO>(ordenExpedientes, OrderType.asc));
		int total = 1;
		for (TramiteTraficoVO traficoVO : listaTramitesOrden) {
			documentoBaseBean.addListaTramites(traficoVO.getNumExpediente());
			if (contTotal == 100) {
				resultado.addListaDocumentoBase(documentoBaseBean);
				documentoBaseBean = new DocumentoBaseBean();
				contTotal = 0;
			} else if (total == listaTramitesOrden.size()) {
				resultado.addListaDocumentoBase(documentoBaseBean);
			}
			total++;
			contTotal++;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public DocBaseCarpetaTramiteBean getDocBaseParaTramite(BigDecimal numExpediente) {
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, false);
			if (tramite != null) {
				ResultadoDocBaseBean resultadoDocBaseBean = servicioDocBase.getDocBase(tramite.getIdDocBase(), Boolean.FALSE);
				if (resultadoDocBaseBean != null && resultadoDocBaseBean.getDocumentoBase() != null) {
					DocBaseCarpetaTramiteBean docBaseCarpetaTramite = new DocBaseCarpetaTramiteBean();
					docBaseCarpetaTramite.setId(resultadoDocBaseBean.getDocumentoBase().getId());
					docBaseCarpetaTramite.setIdDocBase(resultadoDocBaseBean.getDocumentoBase().getDocId());
					docBaseCarpetaTramite.setNumExpediente(numExpediente);
					docBaseCarpetaTramite.setTipoCarpetaDocBase(resultadoDocBaseBean.getDocumentoBase().getCarpeta());
					switch (TipoTramiteTrafico.convertir(tramite.getTipoTramite())) {
						case Matriculacion:
							if (tramite.getEstado() != null && (tramite.getEstado().equals(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) || tramite.getEstado()
									.equals(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())) || tramite.getEstado().equals(new BigDecimal(
											EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum())))) {
								docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_MATE.getNombreEnum());
								docBaseCarpetaTramite.setMotivo("Es un trámite de matriculación finalizado telemáticamente");
							} else {
								if (checkVerdeTipoA(tramite)) {
									docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_MATR_TIPOA.getNombreEnum());
									docBaseCarpetaTramite.setMotivo("Es un trámite de matriculación con tarjeta ITV Tipo A");
								} else {
									docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_MATR_PDF.getNombreEnum());
									docBaseCarpetaTramite.setMotivo("No es un trámite de matriculación finalizado telemáticamente, ni con tarjeta ITV tipo A");
								}
							}
							break;
						case TransmisionElectronica:
							if (tramite.getEstado() != null && (tramite.getEstado().equals(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) || tramite.getEstado()
									.equals(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())) || tramite.getEstado().equals(new BigDecimal(
											EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum())))) {
								docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_CTIT.getNombreEnum());
								docBaseCarpetaTramite.setMotivo("Es un trámite de transmisión finalizado telemáticamente");
							} else {
								if (checkTipoB(tramite)) {
									docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_TIPO_B.getNombreEnum());
									docBaseCarpetaTramite.setMotivo("Es un trámite de transmisión con herencia y/o cotitulares");
								} else if (checkTipoI(tramite)) {
									docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_TIPO_I.getNombreEnum());
									docBaseCarpetaTramite.setMotivo("Es un trámite de transmisión cuyo vehículo es un autobús, sobrepasa los 6000 Kg de peso o tiene un servicio público o B07");
								} else if (checkTipoFusion(tramite)) {
									docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_FUSION.getNombreEnum());
									docBaseCarpetaTramite.setMotivo("Es un trámite de transmisión con tasa tipo 1.6");
								} else {
									docBaseCarpetaTramite.setTipoCarpeta(DocumentoBaseTipoCarpetaEnum.CARPETA_TIPO_A.getNombreEnum());
									docBaseCarpetaTramite.setMotivo("Es un trámite de transmisión que no cumple con los criterios de tipo B, I o Fusión");
								}
							}
							break;
						default:
							docBaseCarpetaTramite.setTipoCarpeta(null);
							break;
					}
					return docBaseCarpetaTramite;
				}
			}
		} catch (Exception e) {
			log.error("Error al obtener la información del documento base del trámite: " + numExpediente, e);
		}
		return null;
	}

	private boolean checkTipoB(TramiteTraficoVO tramite) {
		if (tramite.getIntervinienteTraficos() != null) {
			for (IntervinienteTraficoVO interviniente : tramite.getIntervinienteTraficos()) {
				TipoInterviniente tipoInterviniente = TipoInterviniente.convertir(interviniente.getTipoIntervinienteVO().getTipoInterviniente());
				if (interviniente != null && interviniente.getTipoIntervinienteVO() != null && interviniente.getTipoIntervinienteVO().getTipoInterviniente() != null) {
					if (tipoInterviniente.equals(TipoInterviniente.CotitularTransmision)) {
						return true;
					}
				}
			}
			if (tramite instanceof TramiteTrafTranVO) {
				TramiteTrafTranVO tramiteTrafTran = (TramiteTrafTranVO) tramite;
				if (tramiteTrafTran != null && ((tramiteTrafTran.getModoAdjudicacion() != null && tramiteTrafTran.getModoAdjudicacion().equals(ModoAdjudicacion.tipo2.getValorEnum()))
						|| (tramiteTrafTran.getAcreditaHerenciaDonacion() != null && tramiteTrafTran.getAcreditaHerenciaDonacion().equals(AcreditacionTrafico.Herencia.getValorEnum())))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkTipoI(TramiteTraficoVO tramite) {
		ArrayList<String> listaTiposVehiculoTipoI = getListaTiposVehiculoTipoI();
		ArrayList<String> listaTiposVehiculoSinPesoTipoI = getListaTiposVehiculoSinPesoTipoI();
		ArrayList<String> listaServiciosTipoI = getListaServiciosTipoI();
		Integer limitePesoTipoI = new Integer(6000);
		if (listaTiposVehiculoSinPesoTipoI.contains(tramite.getVehiculo().getTipoVehiculo()) || listaServiciosTipoI.contains(tramite.getVehiculo().getIdServicio()) || (listaTiposVehiculoTipoI
				.contains(tramite.getVehiculo().getTipoVehiculo()) && limitePesoTipoI < new Integer(tramite.getVehiculo().getPesoMma()))) {
			return true;
		}
		return false;

	}

	private boolean checkTipoFusion(TramiteTraficoVO tramite) {
		if (tramite.getTasa() != null && tramite.getTasa().getTipoTasa() != null && tramite.getTasa().getTipoTasa().equals(TipoTasa.UnoSeis.getValorEnum())) {
			return true;
		}
		return false;
	}

	private boolean checkVerdeTipoA(TramiteTraficoVO tramite) {
		if (tramite.getVehiculo().getIdTipoTarjetaItv().equals(TipoTarjetaITV.A.getValorEnum())) {
			return true;
		}
		return false;
	}

	private ArrayList<String> getListaTiposVehiculoSinPesoTipoI() {
		ArrayList<String> listaTiposVehiculoSinPesoTipoI = new ArrayList<String>();
		listaTiposVehiculoSinPesoTipoI.add(TipoVehiculoEnum.AUTOBUS.getValorEnum());
		listaTiposVehiculoSinPesoTipoI.add(TipoVehiculoEnum.AUTOBUS_ARTICULADO.getValorEnum());
		listaTiposVehiculoSinPesoTipoI.add(TipoVehiculoEnum.AUTOBUS_MIXTO.getValorEnum());
		listaTiposVehiculoSinPesoTipoI.add(TipoVehiculoEnum.BIBLIOBUS.getValorEnum());
		listaTiposVehiculoSinPesoTipoI.add(TipoVehiculoEnum.AUTOBUS_SANITARIO.getValorEnum());
		listaTiposVehiculoSinPesoTipoI.add(TipoVehiculoEnum.AUTOBUS_TALLER.getValorEnum());
		return listaTiposVehiculoSinPesoTipoI;
	}

	private ArrayList<String> getListaTiposVehiculoTipoI() {
		ArrayList<String> listaTiposVehiculoTipoI = new ArrayList<String>();
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.VEHICULO_ESPECIAL.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.PALA_CARGADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.PALA_EXCAVADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.CARRETILLA_ELEVADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MOTONIVELADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.COMPACTADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.APISONADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.GIROGRAVILLADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MACHACADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.QUITANIEVES.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.VIVIENDA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.BARREDORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.HORMIGONERA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.VEHICULO_BASCULANTE.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.GRUA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SERVICIO_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.ASPIRADORA_DE_FANGOS.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MOTOCULTOR.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.PALA_CARGADORA_RETROEXCAVADORA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.TREN_HASTA_160_PLAZAS.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.TRACTOR.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.TRACTOCAMION.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.TRACTOCARRO.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_1_EJE.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_2_EJES.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_FURGON.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoTipoI.add(TipoVehiculoEnum.SEMIRREMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());
		return listaTiposVehiculoTipoI;
	}

	private ArrayList<String> getListaServiciosTipoI() {
		ArrayList<String> listaServiciosTipoI = new ArrayList<String>();
		listaServiciosTipoI.add("A00");
		listaServiciosTipoI.add("A01");
		listaServiciosTipoI.add("A02");
		listaServiciosTipoI.add("A03");
		listaServiciosTipoI.add("A04");
		listaServiciosTipoI.add("A05");
		listaServiciosTipoI.add("A07");
		listaServiciosTipoI.add("A08");
		listaServiciosTipoI.add("A09");
		listaServiciosTipoI.add("A10");
		listaServiciosTipoI.add("A11");
		listaServiciosTipoI.add("A12");
		listaServiciosTipoI.add("A13");
		listaServiciosTipoI.add("A14");
		listaServiciosTipoI.add("A15");
		listaServiciosTipoI.add("A16");
		listaServiciosTipoI.add("A18");
		listaServiciosTipoI.add("A20");
		listaServiciosTipoI.add("B07");
		return listaServiciosTipoI;
	}
}
