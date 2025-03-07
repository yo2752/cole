package org.gestoresmadrid.oegamDocBase.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.docbase.enumerados.DocBaseEstadoEnum;
import org.gestoresmadrid.core.docbase.enumerados.TipoTramiteDocBase;
import org.gestoresmadrid.core.docbase.model.dao.DocumentoBaseDao;
import org.gestoresmadrid.core.docbase.model.vo.DocumentoBaseVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.enumerados.ServicioTraficoEnum;
import org.gestoresmadrid.core.enumerados.TipoDocumentoDuplicado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.MotivoDuplicado;
import org.gestoresmadrid.core.model.enumerados.TipoCarpeta;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoVehiculoEnum;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.service.ServicioDocBase;
import org.gestoresmadrid.oegamDocBase.service.ServicioImpresionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.TramiteBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDocBaseImpl implements ServicioDocBase{

	private static final long serialVersionUID = 6466047879507541300L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioDocBaseImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioImpresionDocBase servicioImpresionDocBase;

	@Autowired
	ServicioComunCola servicioCola;

	@Autowired
	DocumentoBaseDao documentoBaseDao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultadoDocBaseBean eliminar(Long idDocBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		DocumentoBaseVO docBase = null;
		try {
			docBase = getDocBaseBBDD(idDocBase, Boolean.TRUE);
			resultado = validarEliminarDocBase(docBase, idDocBase);
			if (!resultado.getError()) {
				for (TramiteTraficoVO tramiteTrafico : docBase.getTramitesTraficoAsList()) {
					tramiteTrafico.setIdDocBase(null);
					servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				}
				docBase.setEstado(DocBaseEstadoEnum.DOC_REVERTIDO.getValorEnum());
				documentoBaseDao.actualizar(docBase);
			}
		} catch (Exception e) {
			if (docBase != null) {
				log.error("Ha sucedido un error a la hora de eliminar el docBase con docId: " + docBase.getDocId() + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de eliminar el docBase con docId: " + docBase.getDocId());
			} else {
				log.error("Ha sucedido un error a la hora de eliminar el docBase con id: " + idDocBase + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de eliminar el docBase con id: " + idDocBase);
			}
			resultado.setError(Boolean.TRUE);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDocBaseBean descargarDocBase(Long idDocBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		DocumentoBaseVO docBase = null;
		try {
			docBase = getDocBaseBBDD(idDocBase, Boolean.FALSE);
			if (docBase == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para poder descargar el PDF del documento base con id: " + idDocBase);
			} else if (!DocBaseEstadoEnum.IMPRESO_DOC.getValorEnum().equals(docBase.getEstado())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No esta en un estado valido para descargar el pdf del documento base con docId: " + docBase.getDocId());
			} else if (docBase.getFechaPresentacion().before((new GregorianCalendar(2014, 3, 26).getTime()))) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La fecha de presentación del documento base con docId: " + docBase.getDocId() + " es demasiado antigua");
			} else {
				Fecha fechaPresentacion = utilesFecha.getFechaConDate(docBase.getFechaPresentacion());
				String subTipo = servicioImpresionDocBase.obtenerSubtipoDocumentoFromTipoCarpeta(docBase.getCarpeta());
				FileResultBean ficheroResult = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TIPO_DOC_GESTION_DOCUMENTAL, subTipo, fechaPresentacion, docBase.getDocId(),
						ConstantesGestorFicheros.EXTENSION_PDF);
				if (ficheroResult != null && ficheroResult.getFile() != null) {
					resultado.setFichero(ficheroResult.getFile());
					resultado.setNombreFichero(docBase.getDocId() + ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe el PDF para el documento base con docId: " + docBase.getDocId());
				}
			}
		} catch (Throwable e) {
			if (docBase != null) {
				log.error("Ha sucedido un error a la hora de descargar el PDF del docBase con docId: " + docBase.getDocId() + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de descargar el PDF del docBase con docId: " + docBase.getDocId());
			} else {
				log.error("Ha sucedido un error a la hora de descargar el PDF del docBase con id: " + idDocBase + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de descargar el PDF del docBase con id: " + idDocBase);
			}
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDocBaseBean borrarPDFDocBase(Long idDocBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		DocumentoBaseVO docBase = null;
		try {
			docBase = getDocBaseBBDD(idDocBase, Boolean.FALSE);
			if (docBase == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para poder eliminar el PDF del documento base con id: " + idDocBase);
			} else if (!DocBaseEstadoEnum.DOC_REVERTIDO.getValorEnum().equals(docBase.getEstado())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No esta en un estado valido para eliminar el pdf del documento base con docId: " + docBase.getDocId());
			} else {
				Fecha fechaPresentacion = utilesFecha.getFechaConDate(docBase.getFechaPresentacion());
				String subTipo = servicioImpresionDocBase.obtenerSubtipoDocumentoFromTipoCarpeta(docBase.getCarpeta());
				gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.TIPO_DOC_GESTION_DOCUMENTAL, subTipo, fechaPresentacion, docBase.getDocId() + ConstantesGestorFicheros.EXTENSION_PDF);
				resultado.setMensaje("Documento Base: " + docBase.getDocId() + " eliminado correctamente.");
			}
		} catch (Throwable e) {
			if (docBase != null) {
				log.error("Ha sucedido un error a la hora de eliminar el PDF del docBase con docId: " + docBase.getDocId() + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de eliminar el PDF del docBase con docId: " + docBase.getDocId());
			} else {
				log.error("Ha sucedido un error a la hora de eliminar el PDF del docBase con id: " + idDocBase + ", error: ", e);
				resultado.setMensaje("Ha sucedido un error a la hora de eliminar el PDF del docBase con id: " + idDocBase);
			}
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private ResultadoDocBaseBean validarEliminarDocBase(DocumentoBaseVO docBase, Long idDocBase) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		if (docBase == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos en BBDD para el documento base con id: " + idDocBase);
		} else if (!DocBaseEstadoEnum.IMPRESO_DOC.getValorEnum().equals(docBase.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede eliminar el documento base con docID: " + docBase.getDocId() + " porque no se encuentra en un estado valido para su eliminación.");
		} else if (docBase.getTramitesTraficoAsList() == null || docBase.getTramitesTraficoAsList().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede eliminar el documento base con docID: " + docBase.getDocId() + " porque no se encuentra datos asociados a los expedientes del documento.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDocBaseBean actualizarImpresionDocBase(Long idDocBase, DocBaseBean docBaseFinal) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			int cont = 1;
			for (TramiteBaseBean tramiteBaseBean : docBaseFinal.getTramites()) {
				if (tramiteBaseBean.getListaTramitesSimultaneos() != null && !tramiteBaseBean.getListaTramitesSimultaneos().isEmpty()) {
					for (BigDecimal numExpedienteAux : tramiteBaseBean.getListaTramitesSimultaneos()) {
						TramiteTraficoVO tramiteVO = servicioTramiteTrafico.getTramite(numExpedienteAux, Boolean.FALSE);
						tramiteVO.setOrdenDocBase(new BigDecimal(cont));
						servicioTramiteTrafico.actualizarTramite(tramiteVO);
					}
				} else {
					TramiteTraficoVO tramiteVO = servicioTramiteTrafico.getTramite(new BigDecimal(tramiteBaseBean.getNumExpediente()), Boolean.FALSE);
					tramiteVO.setOrdenDocBase(new BigDecimal(cont));
					servicioTramiteTrafico.actualizarTramite(tramiteVO);
				}
				cont++;
			}
			DocumentoBaseVO docBase = getDocBaseBBDD(idDocBase, Boolean.FALSE);
			docBase.setEstado(DocBaseEstadoEnum.IMPRESO_DOC.getValorEnum());
			actualizar(docBase);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar los datos de la impresion para el docId: " + idDocBase + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de actualizar los datos de la impresion para el docId: " + idDocBase);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private DocumentoBaseVO getDocBaseBBDD(Long idDocBase, Boolean docBaseCompleto) {
		return documentoBaseDao.getDocBase(idDocBase, docBaseCompleto);
	}

	@Override
	@Transactional
	public void actualizar(DocumentoBaseVO docBase) {
		documentoBaseDao.actualizar(docBase);
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoDocBaseBean getDocBase(Long idDocBase, Boolean docBaseCompleto) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			if (idDocBase != null) {
				DocumentoBaseVO docBase = getDocBaseBBDD(idDocBase, docBaseCompleto);
				if (docBase != null) {
					resultado.setDocumentoBase(docBase);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos del documento base en BBDD para el id: " + idDocBase);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de indicar un id para obtener los datos del docBase.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del docBase con id: " + idDocBase + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del docBase con id: " + idDocBase);
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDocBaseBean crearDocBase(String tipoCarpeta, List<BigDecimal> listaTramites, Long idContrato, Long idUsuario, Date fechaPresentacion, Long idImpresion, Boolean esNocturno) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			Date fecha = new Date();
			List<TramiteTraficoVO> listaTramitesBBDD = servicioTramiteTrafico.getListaTramitesTraficoVO(utiles.convertirBigDecimalListToBigDecimalArray(listaTramites), Boolean.TRUE);
			if (listaTramitesBBDD != null && !listaTramites.isEmpty()) {
				DocumentoBaseVO docBase = generarDocBase(tipoCarpeta, idContrato, idUsuario, fecha, listaTramitesBBDD, fechaPresentacion);
				for (TramiteTraficoVO tramiteTraficoVO : listaTramitesBBDD) {
					tramiteTraficoVO.setIdDocBase(docBase.getId());
					servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
				}
				if (!resultado.getError()) {
					ResultadoBean resultCola = servicioCola.crearSolicitud(docBase.getId(), ProcesosEnum.IMPRIMIR_DOC_BASE.getNombreEnum(), gestorPropiedades.valorPropertie("nombreHostProceso"),
							TipoTramiteDocBase.IMPRESION.getValorEnum(), new BigDecimal(idUsuario), new BigDecimal(idContrato), null);
					if (resultCola.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen tramites para poder generar el documento base.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de crear el documento base para la carpeta: " + tipoCarpeta + " y el idContrato: " + idContrato + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear el documento base para la carpeta: " + tipoCarpeta);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDocBaseBean reimprimirDocBaseErroneo(DocumentoBaseVO docBase, Long idUsuario) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			docBase.setEstado(DocBaseEstadoEnum.CREADO_DOC_LOGICO.getValorEnum());
			documentoBaseDao.guardarOActualizar(docBase);

			ResultadoBean resultCola = servicioCola.crearSolicitud(docBase.getId(), ProcesosEnum.IMPRIMIR_DOC_BASE.getNombreEnum(), gestorPropiedades.valorPropertie("nombreHostProceso"),
					TipoTramiteDocBase.IMPRESION.getValorEnum(), new BigDecimal(idUsuario), new BigDecimal(docBase.getIdContrato()), null);
			if (resultCola.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultCola.getMensaje());
			} else {
				resultado.setMensaje("El documento base: " + docBase.getDocId().trim() + " se ha encolado correctamente para su impresión.");
			}

		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de crear el documento base para la carpeta: " + docBase.getCarpeta() + " y el idContrato: " + docBase.getIdContrato() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear el documento base para la carpeta: " + docBase.getCarpeta());
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private DocumentoBaseVO generarDocBase(String tipoCarpeta, Long idContrato, Long idUsuario, Date fecha, List<TramiteTraficoVO> listaTramites, Date fechaPresentacion) {
		DocumentoBaseVO docBase = new DocumentoBaseVO();
		docBase.setCarpeta(tipoCarpeta);
		docBase.setDocId(generarDocId(fecha));
		docBase.setEstado(DocBaseEstadoEnum.CREADO_DOC_LOGICO.getValorEnum());
		docBase.setFechaCreacion(fecha);
		docBase.setFechaPresentacion(fechaPresentacion);
		docBase.setIdContrato(idContrato);
		docBase.setQrCode(generarCodigoQr(docBase.getDocId(), docBase.getFechaPresentacion(), listaTramites.get(0).getNumExpediente(), listaTramites.get(0).getVehiculo().getMatricula()));
		documentoBaseDao.guardar(docBase);
		return docBase;
	}

	private String generarCodigoQr(String docId, Date fechaPresentacion, BigDecimal numExpediente, String matricula) {
		Fecha fPresentacion = utilesFecha.getFechaConDate(fechaPresentacion);
		String codigoQr = docId.substring(5);
		codigoQr += "-000-";
		codigoQr += fPresentacion.getDia() + "/" + fPresentacion.getMes() + "/" + fPresentacion.getAnio();
		codigoQr += "-";
		codigoQr += numExpediente;
		codigoQr += "-";
		if (matricula != null) {
			codigoQr += matricula;
		} else {
			codigoQr += "NO_MATR";
		}

		return codigoQr;
	}

	private String generarDocId(Date fecha) {
		List<String> listaDocId = documentoBaseDao.obtenerDocIdMax(fecha);
		String nextDocId = null;
		if (listaDocId != null && !listaDocId.isEmpty()) {
			nextDocId = String.valueOf(Integer.valueOf(listaDocId.get(0).substring(listaDocId.get(0).length() - longitudSecuencialDocBase, listaDocId.get(0).length())) + 1);
		} else {
			nextDocId = "1";
		}
		String secuencial = utiles.rellenarCeros(nextDocId, longitudSecuencialDocBase);
		return utilesFecha.getFechaConDate(fecha).getAnio() + "-" + secuencial;
	}

	@Override
	public ResultadoDocBaseBean generarDocBase(List<TramiteTraficoVO> listaTramites, BigDecimal idUsuario, Boolean tienePermisoAdmin) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		try {
			resultado = validarTramitesDocBaseDemanda(listaTramites, tienePermisoAdmin);
			if (!resultado.getExisteErroresVal()) {
				if (resultado.getListaMensaje() != null && !resultado.getListaMensaje().isEmpty()) {
					for (String mensaje : resultado.getListaMensaje()) {
						resultado.addResumenError(mensaje);
					}
				}
				if (resultado.getListaCarpetaCtit() != null && !resultado.getListaCarpetaCtit().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaCtit(), TipoCarpeta.CARPETA_CTIT, idUsuario, resultado, resultado.getListaCarpetaCtit().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaFusion() != null && !resultado.getListaCarpetaFusion().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaFusion(), TipoCarpeta.CARPETA_FUSION, idUsuario, resultado, resultado.getListaCarpetaFusion().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaMate() != null && !resultado.getListaCarpetaMate().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaMate(), TipoCarpeta.CARPETA_MATE, idUsuario, resultado, resultado.getListaCarpetaMate().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaBtv() != null && !resultado.getListaCarpetaBtv().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaBtv(), TipoCarpeta.CARPETA_BTV, idUsuario, resultado, resultado.getListaCarpetaBtv().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaMateTipoA() != null && !resultado.getListaCarpetaMateTipoA().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaMateTipoA(), TipoCarpeta.CARPETA_MATR_TIPOA, idUsuario, resultado, resultado.getListaCarpetaMateTipoA().get(0)
							.getFechaPresentacion());
				}
				if (resultado.getListaCarpetaMatrPdf() != null && !resultado.getListaCarpetaMatrPdf().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaMatrPdf(), TipoCarpeta.CARPETA_MATR_PDF, idUsuario, resultado, resultado.getListaCarpetaMatrPdf().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaTipoA() != null && !resultado.getListaCarpetaTipoA().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaTipoA(), TipoCarpeta.CARPETA_TIPO_A, idUsuario, resultado, resultado.getListaCarpetaTipoA().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaTipoB() != null && !resultado.getListaCarpetaTipoB().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaTipoB(), TipoCarpeta.CARPETA_TIPO_B, idUsuario, resultado, resultado.getListaCarpetaTipoB().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaTipoI() != null && !resultado.getListaCarpetaTipoI().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaTipoI(), TipoCarpeta.CARPETA_TIPO_I, idUsuario, resultado, resultado.getListaCarpetaTipoI().get(0).getFechaPresentacion());
				}
				if (resultado.getListaCarpetaDuplicado() != null && !resultado.getListaCarpetaDuplicado().isEmpty()) {
					generarImpresionDocBase(resultado.getListaCarpetaDuplicado(), TipoCarpeta.CARPETA_DUPLICADO, idUsuario, resultado, resultado.getListaCarpetaDuplicado().get(0).getFechaPresentacion());
				}
			} else {
				if (resultado.getListaMensaje() != null && !resultado.getListaMensaje().isEmpty()) {
					for (String mensaje : resultado.getListaMensaje()) {
						resultado.addResumenError(mensaje);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el documento base con el listado de expedientes, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el documento base con el listado de expedientes.");
		}
		return resultado;
	}

	private void generarImpresionDocBase(List<TramiteTraficoVO> listaExpedientes, TipoCarpeta tipoCarpeta, BigDecimal idUsuario, ResultadoDocBaseBean resultado, Date fechaPresentacion) {
		try {
			ResultadoDocBaseBean resultGenSolicitud = servicioImpresionDocBase.generarSolicitudImpresionDocBase(listaExpedientes, tipoCarpeta, idUsuario, resultado.getIdContrato(), fechaPresentacion);
			if (!resultGenSolicitud.getError()) {
				resultado.addResumenOK("Solicitud de generacion de documento Base para la carpeta: " + tipoCarpeta.getValorEnum() + " creada correctamente.");
			} else {
				resultado.addResumenError(resultGenSolicitud.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la peticion de impresion para los expedientes de la carpeta: " + tipoCarpeta.getNombreEnum() + ", error: ", e);
			resultado.addResumenError("Ha sucedido un error a la hora de generar la peticion de impresion para los expedientes de la carpeta: " + tipoCarpeta.getNombreEnum() + getExpedientesError(
					listaExpedientes));
		}
	}

	private String getExpedientesError(List<TramiteTraficoVO> listaTramites) {
		String mensaje = "";
		for (TramiteTraficoVO tramite : listaTramites) {
			if (mensaje.isEmpty()) {
				mensaje = " para los expedientes: " + tramite.getNumExpediente().toString();
			} else {
				mensaje += ", " + tramite.getNumExpediente().toString();
			}
		}
		return mensaje;
	}

	@SuppressWarnings("unused")
	private ResultadoDocBaseBean validarTramitesDocBaseDemanda(List<TramiteTraficoVO> listaTramites, Boolean tienePermisoAdmin) {
		ResultadoDocBaseBean resultado = new ResultadoDocBaseBean(Boolean.FALSE);
		if (listaTramites != null && !listaTramites.isEmpty()) {
			Date fechaPresentacion = null;
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			for (TramiteTraficoVO tramiteTrafico : listaTramites) {
				try {
					if (tramiteTrafico.getFechaPresentacion() != null && fechaPresentacion == null) {
						fechaPresentacion = tramiteTrafico.getFechaPresentacion();
					}
					if (tramiteTrafico.getContrato() != null && tramiteTrafico.getContrato().getIdContrato() != null && resultado.getIdContrato() == null) {
						resultado.setIdContrato(tramiteTrafico.getContrato().getIdContrato());
					}
					if (tramiteTrafico.getIdDocBase() != null) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " ya tiene asociado un documento base.");
					} else if (tramiteTrafico.getFechaPresentacion() == null) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no tiene fecha de presentación asociada.");
					} else if (tramiteTrafico.getContrato() == null && tramiteTrafico.getContrato().getIdContrato() == null) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no es del mismo contrato.");
					} else if (!utilesFecha.formatoFecha(fechaPresentacion).equals(utilesFecha.formatoFecha(tramiteTrafico.getFechaPresentacion()))) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente()
						+ " no se puede generar su documento base porque solo se pueden generar documentos de una unica fecha de presentacion.");
					} else if (resultado.getIdContrato() != tramiteTrafico.getContrato().getIdContrato()) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no es del mismo contrato.");
					} else if (tramiteTrafico.getEstado() == null) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no tiene un estado valido para poder generarlo.");
					} else if ((EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
							.getValorEnum().equals(tramiteTrafico.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equals(tramiteTrafico.getEstado().toString())) && calendar.getTime().before(tramiteTrafico.getFechaPresentacion()) && !tienePermisoAdmin) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " tiene la fecha de presentación posterior a la fecha actual para un tramite telematico.");
						/*
						 * } else if(Integer.valueOf(utilesFecha.getFechaConDate(tramiteTrafico.getFechaPresentacion()).getAnio()).intValue() > Integer.valueOf(utilesFecha.getFechaActual().getAnio()).intValue()){ resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() +
						 * " la fecha de presentación del trámite no puede ser posterior al año actual.");
						 */
					} else if (!EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equals(tramiteTrafico.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
							.getValorEnum().equals(tramiteTrafico.getEstado().toString()) && !EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafico.getEstado().toString()) &&  !EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equals(tramiteTrafico.getEstado().toString())) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " debe de estar finalizado para poder obtener su documento base.");
					} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite()) 
							&& !TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico.getTipoTramite()) 
							&& !TipoTramiteTrafico.Transmision.getValorEnum().equals(tramiteTrafico.getTipoTramite()) 
							&& !TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())
							&& !TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no es de un tipo de tramite apto para poder obtener su documento base.");
					} else if (servicioImpresionDocBase.existeTramiteDocBasePdtImprimir(tramiteTrafico.getNumExpediente())) {
						resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " ya esta en la cola para generar su documento base.");
					} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						if (tramiteTrafico.getVehiculo() != null && tramiteTrafico.getVehiculo().getNive() != null && !tramiteTrafico.getVehiculo().getNive().isEmpty()) {
							resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no se puede obtener su documento base porque es un vehiculo con nive.");
						} else if (!EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTrafico.getEstado().toString())) {
							if ("SI".equals(gestorPropiedades.valorPropertie("generar.docBase.matwSuperTelematicos"))) {
								resultado.addListaCarpetaMate(tramiteTrafico);
							} else if (tramiteTrafico.getVehiculo().getNive() == null || tramiteTrafico.getVehiculo().getNive().isEmpty()) {
								resultado.addListaCarpetaMate(tramiteTrafico);
							} else {
								Boolean esJefOk = Boolean.FALSE;
								String sJefaturasAuto = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.matw");
								if (sJefaturasAuto != null && !sJefaturasAuto.isEmpty()) {
									String[] jefaturasAuto = sJefaturasAuto.split(",");
									for (String jefaturoProv : jefaturasAuto) {
										if (jefaturoProv.equals(tramiteTrafico.getContrato().getJefaturaTrafico().getJefaturaProvincial())) {
											esJefOk = Boolean.TRUE;
										}
									}
								}
								if (!esJefOk) {
									String colegiadosImprTodos = gestorPropiedades.valorPropertie("colegiado.impresion.impr.matw");
									if (colegiadosImprTodos == null || colegiadosImprTodos.isEmpty()) {
										resultado.addListaCarpetaMate(tramiteTrafico);
									} else if (!colegiadosImprTodos.contains(tramiteTrafico.getContrato().getColegiado().getNumColegiado())) {
										resultado.addListaCarpetaMate(tramiteTrafico);
									} else {
										resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " es supertelematico, no se incluira en el documento base.");
									}
								} else {
									resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " es supertelematico, no se incluira en el documento base.");
								}
							}
						} else {
							if (tramiteTrafico.getVehiculo() != null && tramiteTrafico.getVehiculo().getIdTipoTarjetaItv() != null && !tramiteTrafico.getVehiculo().getIdTipoTarjetaItv().isEmpty()
									&& (TipoTarjetaITV.A.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdTipoTarjetaItv()) || TipoTarjetaITV.AL.getValorEnum().equals(tramiteTrafico
											.getVehiculo().getIdTipoTarjetaItv()) || TipoTarjetaITV.AR.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdTipoTarjetaItv()) || TipoTarjetaITV.AT
											.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdTipoTarjetaItv()))) {
								resultado.addListaCarpetaMateTipoA(tramiteTrafico);
							} else {
								resultado.addListaCarpetaMatrPdf(tramiteTrafico);
							}
						}
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
						if ("SI".equals(gestorPropiedades.valorPropertie("generar.docBase.btv"))) {
							if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
									.getValorEnum().equals(tramiteTrafico.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado
									.getValorEnum().equals(tramiteTrafico.getEstado().toString())) {
								resultado.addListaCarpetaBtv(tramiteTrafico);
							} else {
								TramiteTrafBajaVO tramiteBaja = (TramiteTrafBajaVO) tramiteTrafico;
								if (tramiteBaja != null) {
									if (MotivoBaja.TempT.getValorEnum().equals(tramiteBaja.getMotivoBaja())) {
										resultado.addListaCarpetaTipoA(tramiteBaja);
									} else {
										resultado.addListaMensaje("El expediente: " + tramiteBaja.getNumExpediente() + " no cumple los requisitos para obtener su documento base.");
									}
								} else {
									resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no se encuentran datos para el tramite en BBDD.");
								}
							}
						} else {
							resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no se puede obtener su documento base al ser un tramite de Baja.");
						}
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite())) {
							TramiteTrafDuplicadoVO tramiteDuplicado = (TramiteTrafDuplicadoVO) tramiteTrafico;
							if (tramiteDuplicado != null) {
								if (MotivoDuplicado.Deter.getValorEnum().equals(tramiteDuplicado.getMotivoDuplicado())
										|| MotivoDuplicado.Extrv.getValorEnum().equals(tramiteDuplicado.getMotivoDuplicado())
										|| MotivoDuplicado.Sustr.getValorEnum().equals(tramiteDuplicado.getMotivoDuplicado())) {
									resultado.addListaCarpetaDuplicado(tramiteDuplicado);
								} else {
									resultado.addListaMensaje("El expediente: " + tramiteDuplicado.getNumExpediente() + " no tiene un motivo autorizado para obtener su documento base.");
								}
							} else {
								resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no se encuentran datos para el tramite en BBDD.");
							}
					} else {
						if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
								.getValorEnum().equals(tramiteTrafico.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado
								.getValorEnum().equals(tramiteTrafico.getEstado().toString())) {
							resultado.addListaCarpetaCtit(tramiteTrafico);
						} else if (tramiteTrafico.getTasa() == null || tramiteTrafico.getTasa().getCodigoTasa() == null || tramiteTrafico.getTasa().getCodigoTasa().isEmpty()) {
							resultado.addListaMensaje("El expediente: " + tramiteTrafico.getNumExpediente() + " no tiene tasa por lo que no se incluira en el documento base.");
						} else if (tramiteTrafico.getTasa() != null && TipoTasa.UnoSeis.getValorEnum().equals(tramiteTrafico.getTasa().getTipoTasa())) {
							resultado.addListaCarpetaFusion(tramiteTrafico);
						} else if (esAutoBusTipoI(tramiteTrafico) || noEsVehiculoEspecial(tramiteTrafico)) {
							resultado.addListaCarpetaTipoI(tramiteTrafico);
						} else {
							TramiteTrafTranVO tramiteTrafTranVO = (TramiteTrafTranVO) tramiteTrafico;
							if (tramiteTrafTranVO != null) {
								if (ModoAdjudicacion.tipo2.getValorEnum().equals(tramiteTrafTranVO.getModoAdjudicacion())) {
									String jefaturas = gestorPropiedades.valorPropertie("jefatura.finalizacion.tras.notificacion");
									if (StringUtils.isNotBlank(jefaturas) && ("TODOS".equals(jefaturas) || jefaturasTipoA(jefaturas, tramiteTrafTranVO.getJefaturaTrafico()))) {
										resultado.addListaCarpetaTipoA(tramiteTrafico);
									} else {
										resultado.addListaCarpetaTipoB(tramiteTrafico);
									}
								} else if (ModoAdjudicacion.tipo3.getValorEnum().equals(tramiteTrafTranVO.getModoAdjudicacion()) && "HERENCIA".equals(tramiteTrafTranVO
										.getAcreditaHerenciaDonacion())) {
									resultado.addListaCarpetaTipoB(tramiteTrafico);
								} else {
									Boolean esCarpetaB = Boolean.FALSE;
									for (IntervinienteTraficoVO interv : tramiteTrafico.getIntervinienteTraficosAsList()) {
										if (TipoInterviniente.CotitularTransmision.getValorEnum().equals(interv.getId().getTipoInterviniente())) {
											esCarpetaB = Boolean.TRUE;
											break;
										}
									}
									if (esCarpetaB) {
										resultado.addListaCarpetaTipoB(tramiteTrafico);
									} else {
										resultado.addListaCarpetaTipoA(tramiteTrafico);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de validar el tramite: " + tramiteTrafico.getNumExpediente() + " para la generación de su documento base, error: ", e);
					resultado.addListaMensaje("Ha sucedido un error a la hora de validar el tramite: " + tramiteTrafico.getNumExpediente() + " para la generación de su documento base.");
				}
			}
			if ((resultado.getListaCarpetaBtv() == null || resultado.getListaCarpetaBtv().isEmpty()) && (resultado.getListaCarpetaCtit() == null || resultado.getListaCarpetaCtit().isEmpty())
					&& (resultado.getListaCarpetaFusion() == null || resultado.getListaCarpetaFusion().isEmpty()) && (resultado.getListaCarpetaMate() == null || resultado.getListaCarpetaMate()
					.isEmpty()) && (resultado.getListaCarpetaMateTipoA() == null || resultado.getListaCarpetaMateTipoA().isEmpty()) && (resultado.getListaCarpetaMatrPdf() == null || resultado
					.getListaCarpetaMatrPdf().isEmpty()) && (resultado.getListaCarpetaTipoA() == null || resultado.getListaCarpetaTipoA().isEmpty()) && (resultado
							.getListaCarpetaTipoB() == null || resultado.getListaCarpetaTipoB().isEmpty()) && (resultado.getListaCarpetaTipoI() == null || resultado
							.getListaCarpetaTipoI().isEmpty()) && (resultado.getListaCarpetaDuplicado() == null || resultado.getListaCarpetaDuplicado().isEmpty())) {
				resultado.setExisteErroresVal(Boolean.TRUE);
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de indicar una lista de expedientes para generar sus documentos base.");
		}
		return resultado;
	}

	private boolean jefaturasTipoA(String jefaturas, JefaturaTraficoVO jefaturaTrafico) {
		if (jefaturaTrafico != null) {
			String[] listaJefaturas = jefaturas.split(",");
			if (listaJefaturas != null && listaJefaturas.length > 0) {
				for (String jef : listaJefaturas) {
					if (jef != null && jef.equals(jefaturaTrafico.getJefaturaProvincial())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Boolean noEsVehiculoEspecial(TramiteTraficoVO tramiteTrafico) {
		if (tramiteTrafico.getVehiculo().getPesoMma() != null && !tramiteTrafico.getVehiculo().getPesoMma().isEmpty() && Integer.parseInt(tramiteTrafico.getVehiculo().getPesoMma()) > 6000
				&& (!TipoVehiculoEnum.VEHICULO_ESPECIAL.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.PALA_CARGADORA.getValorEnum().equals(tramiteTrafico
						.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.PALA_EXCAVADORA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.CARRETILLA_ELEVADORA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.MOTONIVELADORA.getValorEnum().equals(
								tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.COMPACTADORA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.APISONADORA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.GIROGRAVILLADORA.getValorEnum().equals(
								tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.MACHACADORA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.QUITANIEVES.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.VIVIENDA.getValorEnum().equals(tramiteTrafico
								.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.BARREDORA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.HORMIGONERA
						.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.VEHICULO_BASCULANTE.getValorEnum().equals(tramiteTrafico
								.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.GRUA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.SERVICIO_CONTRA_INCENDIOS.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.ASPIRADORA_DE_FANGOS.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.MOTOCULTOR.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.PALA_CARGADORA_RETROEXCAVADORA
						.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.TREN_HASTA_160_PLAZAS.getValorEnum().equals(tramiteTrafico.getVehiculo()
								.getTipoVehiculo()) && !TipoVehiculoEnum.TRACTOR.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.TRACTOCAMION
						.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.TRACTOCARRO.getValorEnum().equals(tramiteTrafico
								.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.REMOLQUE_BOTELLERO.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_CAJA.getValorEnum().equals(
								tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_CISTERNA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.REMOLQUE_CONTRA_INCENDIOS.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_DE_GRUA.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_FRIGORIFICO.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.REMOLQUE_HORMIGONERA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_JAULA.getValorEnum().equals(
								tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_PLATAFORMA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.REMOLQUE_TALLER.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.REMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum().equals(tramiteTrafico.getVehiculo()
								.getTipoVehiculo()) && !TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_1_EJE.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_2_EJES.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_BOTELLERO.getValorEnum().equals(tramiteTrafico.getVehiculo()
								.getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_CAJA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.SEMIRREMOLQUE_CISTERNA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_CONTRA_INCENDIOS
						.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_DE_GRUA.getValorEnum().equals(tramiteTrafico.getVehiculo()
								.getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_FRIGORIFICO.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.SEMIRREMOLQUE_FURGON.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_HORMIGONERA.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_JAULA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
						&& !TipoVehiculoEnum.SEMIRREMOLQUE_PLATAFORMA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_TALLER.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum().equals(tramiteTrafico.getVehiculo()
								.getTipoVehiculo()) && !TipoVehiculoEnum.SEMIRREMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private Boolean esAutoBusTipoI(TramiteTraficoVO tramiteTrafico) {
		if ((TipoVehiculoEnum.AUTOBUS.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) || TipoVehiculoEnum.AUTOBUS_ARTICULADO.getValorEnum().equals(tramiteTrafico.getVehiculo()
				.getTipoVehiculo()) || TipoVehiculoEnum.AUTOBUS_MIXTO.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo()) || TipoVehiculoEnum.BIBLIOBUS.getValorEnum().equals(
						tramiteTrafico.getVehiculo().getTipoVehiculo()) || TipoVehiculoEnum.AUTOBUS_SANITARIO.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())
				|| TipoVehiculoEnum.AUTOBUS_TALLER.getValorEnum().equals(tramiteTrafico.getVehiculo().getTipoVehiculo())) && (ServicioTraficoEnum.PUBL_SIN_ESPECIFICAR.getValorEnum().equals(
						tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_SIN_ESPECIFICAR.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio())
						|| ServicioTraficoEnum.PUBL_ALQUILER_SIN_CONDUCTOR.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_ALQUILER_CON_CONDUCTOR
						.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_APRENDIZAJE_DE_CONDUCCION.getValorEnum().equals(tramiteTrafico
								.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_TAXI.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio())
						|| ServicioTraficoEnum.PUBL_AUXILIO_EN_CARRETERA.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_AMBULANCIA.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_FUNERARIO.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio())
						|| ServicioTraficoEnum.PUBL_OBRAS.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_MERCANCIAS_PELIGROSAS.getValorEnum().equals(
								tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_BASURERO.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio())
						|| ServicioTraficoEnum.PUBL_TRANSPORTE_ESCOLAR.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_POLICIA.getValorEnum().equals(
								tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_BOMBEROS.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio())
						|| ServicioTraficoEnum.PUBL_PROTECCION_CIVIL_Y_SALVAMENTO.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_DEFENSA.getValorEnum()
						.equals(tramiteTrafico.getVehiculo().getIdServicio()) || ServicioTraficoEnum.PUBL_ACTIVIDAD_ECONOMICA.getValorEnum().equals(tramiteTrafico.getVehiculo()
								.getIdServicio()) || ServicioTraficoEnum.PUBL_MERCANCIAS_PERECEDERAS.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio())
						|| ServicioTraficoEnum.PART_AMBULANCIAS.getValorEnum().equals(tramiteTrafico.getVehiculo().getIdServicio()))) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}
}
