package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.EstadosImportacionEstadistica;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.OrigenImportacion;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.ResultadoImportacionEnum;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.TipoImportacion;
import org.gestoresmadrid.core.importacionFichero.model.vo.EstadisticaImportacionFicherosVO;
import org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamConversiones.jaxb.pegatina.ParametrosPegatinaMatriculacion;
import org.gestoresmadrid.oegamImportacion.bean.ImportarTramiteBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;
import org.gestoresmadrid.oegamImportacion.contrato.service.ServicioContratoImportacion;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.service.ServicioEstadisticaImportacion;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacion;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionBaja;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionCet;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionCtit;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionDupDistintivo;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionDuplicado;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionMatw;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionPegatinas;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionSolicitud;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionTasas;
import org.gestoresmadrid.oegamImportacion.tasa.bean.TasaImportacionBean;
import org.gestoresmadrid.oegamImportacion.utiles.PreferenciasEtiquetasImportacion;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionImpl implements ServicioImportacion {

	private static final long serialVersionUID = -7794597319217978043L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionImpl.class);

	@Autowired
	ServicioImportacionBaja servicioImportacionBaja;

	@Autowired
	ServicioImportacionCtit servicioImportacionCtit;

	@Autowired
	ServicioImportacionMatw servicioImportacionMatw;

	@Autowired
	ServicioImportacionDuplicado servicioImportacionDuplicado;

	@Autowired
	ServicioImportacionCet servicioImportacionCet;

	@Autowired
	ServicioImportacionSolicitud servicioImportacionSolicitud;

	@Autowired
	ServicioImportacionPegatinas servicioImportacionPegatinas;

	@Autowired
	ServicioContratoImportacion servicioContrato;

	@Autowired
	PreferenciasEtiquetasImportacion preferenciasEtiquetas;

	@Autowired
	ServicioImportacionDupDistintivo servicioImportacionDupDistintivo;

	@Autowired
	ServicioImportacionTasas servicioImportacionTasas;

	@Autowired
	ServicioEstadisticaImportacion servicioEstadisticaImportacion;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoImportacionBean importarFichero(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, Long idUsuario, Boolean tienePermisoAdmin,
			Boolean tienePermisoLiberarEEFF, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		Boolean esGenEstadistica = Boolean.FALSE;
		EstadisticaImportacionFicherosVO estadisticaImporFich = null;
		try {
			resultado = validarDatosImportacion(fichero, nombreFichero, importarTramiteBean);
			if (!resultado.getError()) {
				estadisticaImporFich = new EstadisticaImportacionFicherosVO();
				if (!comprobarImportacionEjecutandose(importarTramiteBean.getIdContrato(), importarTramiteBean.getTipoImportacion(), idUsuario, estadisticaImporFich, esGenEstadistica, nombreFichero)) {
					ContratoVO contratoBBDD = servicioContrato.getContratoVO(importarTramiteBean.getIdContrato());
					if (TipoImportacion.XML_MATW.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarMatw(fichero, idUsuario, contratoBBDD, tienePermisoAdmin, tienePermisoLiberarEEFF, esSiga);
					} else if (TipoImportacion.XML_CTIT.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarCtit(fichero, idUsuario, contratoBBDD, tienePermisoAdmin, esSiga);
					} else if (TipoImportacion.XML_CET.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarCet(fichero, contratoBBDD);
					} else if (TipoImportacion.XML_DUPLICADO.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarXmlDuplicado(fichero, idUsuario, contratoBBDD, tienePermisoAdmin, esSiga);
					} else if (TipoImportacion.XML_BAJA.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarBaja(fichero, idUsuario, contratoBBDD, tienePermisoAdmin, esSiga);
					} else if (TipoImportacion.XML_SOLICITUDES.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarSolicitudes(fichero, idUsuario, contratoBBDD, tienePermisoAdmin, esSiga);
					} else if (TipoImportacion.DUPLICADOS_DSTV.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarDuplicadosDistintivos(fichero, nombreFichero, importarTramiteBean, idUsuario, Boolean.FALSE);
					} else if (TipoImportacion.XLS_DUPLICADO.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarDuplicado(fichero, idUsuario, contratoBBDD, tienePermisoAdmin, esSiga);
					} else if (TipoImportacion.TXT_TASAS.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
						resultado = importarTasas(fichero, nombreFichero, importarTramiteBean, idUsuario,tienePermisoAdmin, Boolean.FALSE);
					}
					actualizarEstadistica(resultado, nombreFichero, estadisticaImporFich);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Hay una importación ya ejecutándose de ese tipo de trámite.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de importar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de importar los tramites.");
			if (esGenEstadistica) {
				actualizarEstadistica(resultado, nombreFichero, estadisticaImporFich);
			}
		}
		return resultado;
	}

	private ResultadoImportacionBean importarXmlDuplicado(File fichero, Long idUsuario, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionDuplicado.convertirFicheroXmlImportacion(fichero, contratoBBDD, tienePermisoAdmin, idUsuario, esSiga);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs Duplicado " + resultado.getListaTramitesDuplicado().size() + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO : resultado.getListaTramitesDuplicado()) {
					try {
						log.error("INICIO VALIDACION Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionDuplicado.validarTramiteImportacion(tramiteTrafDuplicadoVO);
						log.error("FIN VALIDACION Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							resultado = gestionarResultadoIncorrecto(resultVal, cont, resultado);
						} else {
							log.error("INICIO GUARDADO Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionDuplicado.guardarImportacion(tramiteTrafDuplicadoVO, idUsuario);
							if (resultGuardar.getError()) {
								resultado = gestionarResultadoIncorrecto(resultGuardar, cont, resultado);
							} else {
								resultado = gestionarResultadoCorrecto(resultGuardar, resultVal, cont, resultado);
							}
							log.error("FIN GUARDADO Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
				resultado = anaidirErroresConvertir(resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de ctit.");
		}
		return resultado;
	}

	private ResultadoImportacionBean anaidirErroresConvertir(ResultadoImportacionBean resultadoFinal) {
		if (resultadoFinal.getListaConversionesError() != null && !resultadoFinal.getListaConversionesError().isEmpty()) {
			for (String mensaje : resultadoFinal.getListaConversionesError()) {
				resultadoFinal.addResumenKO(mensaje);
			}
		}
		return resultadoFinal;
	}

	private boolean comprobarImportacionEjecutandose(Long idContrato, String tipoImportacion, Long idUsuario, EstadisticaImportacionFicherosVO estadisticaImporFich, Boolean esGenEstadistica, String nombreFichero) {
		boolean existen = servicioEstadisticaImportacion.hayImportacionesEjecutandose(idContrato, tipoImportacion);
		if (!existen) {
			estadisticaImporFich.setIdContrato(idContrato);
			estadisticaImporFich.setIdUsuario(idUsuario);
			estadisticaImporFich.setFecha(new Date());
			estadisticaImporFich.setOrigen(OrigenImportacion.IMPEX.getValorEnum());
			estadisticaImporFich.setTipo(tipoImportacion);
			estadisticaImporFich.setNombre(nombreFichero);
			estadisticaImporFich.setEstado(EstadosImportacionEstadistica.Ejecutandose.getValorEnum());
			ResultadoImportacionBean resultado = servicioEstadisticaImportacion.guardarEstadistica(estadisticaImporFich);
			estadisticaImporFich.setIdImportacionFich(resultado.getIdImportacionFich());
			esGenEstadistica = Boolean.TRUE;
		}
		return existen;
	}

	private ResultadoImportacionBean actualizarEstadistica(ResultadoImportacionBean resultado, String nombreFichero, EstadisticaImportacionFicherosVO estadisticaImporFich) {
		if (resultado.getError()) {
			estadisticaImporFich.setTipoError(ResultadoImportacionEnum.Error_Catastrofico.getCodigo());
			estadisticaImporFich.setNumError(new Long(0));
		} else if ((resultado.getResumen().getListaErrores() != null
				&& !resultado.getResumen().getListaErrores().isEmpty())
				&& (resultado.getResumen().getListaOk() != null && !resultado.getResumen().getListaOk().isEmpty())) {
			estadisticaImporFich.setTipoError(ResultadoImportacionEnum.OK_PARCIAL.getCodigo());
			estadisticaImporFich.setNumOk(resultado.getResumen().getNumOk().longValue());
			estadisticaImporFich.setNumError(resultado.getResumen().getNumError().longValue());
		} else if (resultado.getResumen().getListaErrores() != null && !resultado.getResumen().getListaErrores().isEmpty()) {
			estadisticaImporFich.setTipoError(ResultadoImportacionEnum.Error_Catastrofico.getCodigo());
			estadisticaImporFich.setNumError(resultado.getResumen().getNumError().longValue());
		} else {
			estadisticaImporFich.setTipoError(ResultadoImportacionEnum.OK.getCodigo());
			estadisticaImporFich.setNumOk(resultado.getResumen().getNumOk().longValue());
		}
		estadisticaImporFich.setNombre(nombreFichero);
		estadisticaImporFich.setEstado(EstadosImportacionEstadistica.Finalizado.getValorEnum());
		return servicioEstadisticaImportacion.actualizarEstadistica(estadisticaImporFich);
	}

	@Override
	public ResultadoImportacionBean importarFicheroPegatinas(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, int numContratosPorColegiado, Long idContratoAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = validarDatosImportacion(fichero, nombreFichero, importarTramiteBean);
			if (!resultado.getError()) {
				ContratoVO contratoBBDD = null;
				contratoBBDD = servicioContrato.getContratoVO(importarTramiteBean.getIdContrato() != null ? importarTramiteBean.getIdContrato() : idContratoAdmin);
				preferenciasEtiquetas.guardarPreferencias(importarTramiteBean.getEtiquetaParametros(), contratoBBDD.getColegiado().getNumColegiado(), contratoBBDD.getIdContrato().toString(),
						numContratosPorColegiado);
				resultado = importarPegatinas(fichero, importarTramiteBean.getEtiquetaParametros());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de importar los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de importar los tramites.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarPegatinas(File fichero, ParametrosPegatinaMatriculacion etiquetaParametros) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionPegatinas.convertirFicheroImportacion(fichero);
			if (!resultado.getError()) {
				try {
					ResultadoImportacionBean resultGuardar = servicioImportacionPegatinas.generarPdfEtiquetas(etiquetaParametros, resultado.getListaPegatinas());
					if (resultGuardar.getError()) {
						resultado.addResumenKO("-Error generando el PDF de Etiquetas.");
					} else {
						resultado.addResumenOK("-Fichero de pegatinas generado.");
						resultado.setPegatinas(resultGuardar.getPegatinas());
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de generar el PDF de Etiquetas ,error: ", e);
					resultado.addResumenKO("Ha sucedido un error a la hora de generar el PDF de Etiquetas.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero de Pegatinas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero de Pegatinas.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarSolicitudes(File fichero, Long idUsuario, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionSolicitud.convertirFicheroImportacion(fichero, contratoBBDD, tienePermisoAdmin, idUsuario, esSiga);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs Solicitud " + resultado.getListaTramitesSolicitudInteve().size() + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (TramiteTraficoInteveVO tramiteInteveVO : resultado.getListaTramitesSolicitudInteve()) {
					try {
						log.error("INICIO VALIDACION Solicitud tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionSolicitud.validarTramiteSolicitud(tramiteInteveVO);
						log.error("FIN VALIDACION Solicitud tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							resultado = gestionarResultadoIncorrecto(resultVal, cont, resultado);
						} else {
							log.error("INICIO GUARDADO Solicitud tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionSolicitud.guardarImportacion(tramiteInteveVO, idUsuario);
							if (resultGuardar.getError()) {
								resultado = gestionarResultadoIncorrecto(resultGuardar, cont, resultado);
							} else {
								resultado = gestionarResultadoCorrecto(resultGuardar, resultVal, cont, resultado);
							}
							log.error("FIN GUARDADO Solicitud tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de baja.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarBaja(File fichero, Long idUsuario, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionBaja.convertirFicheroImportacion(fichero, contratoBBDD, tienePermisoAdmin, idUsuario, esSiga);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs Baja " + resultado.getListaTramitesBaja().size() + " colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (TramiteTrafBajaVO tramiteBajaVO : resultado.getListaTramitesBaja()) {
					try {
						log.error("INICIO VALIDACION Baja tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionBaja.validarTramiteImportacion(tramiteBajaVO);
						log.error("FIN VALIDACION Baja tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							resultado = gestionarResultadoIncorrecto(resultVal, cont, resultado);
						} else {
							log.error("INICIO GUARDADO Baja tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionBaja.guardarImportacion(tramiteBajaVO, idUsuario);
							if (resultGuardar.getError()) {
								resultado = gestionarResultadoIncorrecto(resultGuardar, cont, resultado);
							} else {
								resultado = gestionarResultadoCorrecto(resultGuardar, resultVal, cont, resultado);
							}
							log.error("FIN GUARDADO Baja tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
				resultado = anaidirErroresConvertir(resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de baja.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarDuplicado(File fichero, Long idUsuario, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionDuplicado.obtenerDatosFichero(fichero, contratoBBDD, tienePermisoAdmin, idUsuario, esSiga);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs Duplicado " + resultado.getListaTramitesDuplicado().size() + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO : resultado.getListaTramitesDuplicado()) {
					try {
						log.error("INICIO VALIDACION Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionDuplicado.validarTramiteImportacionExcel(tramiteTrafDuplicadoVO);
						log.error("FIN VALIDACION Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							resultado = gestionarResultadoIncorrecto(resultVal, cont, resultado);
						} else {
							log.error("INICIO GUARDADO Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionDuplicado.guardarImportacion(tramiteTrafDuplicadoVO, idUsuario);
							if (resultGuardar.getError()) {
								resultado = gestionarResultadoIncorrecto(resultGuardar, cont, resultado);
							} else {
								resultado = gestionarResultadoCorrecto(resultGuardar, resultVal, cont, resultado);
							}
							log.error("FIN GUARDADO Duplicado tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de ctit.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarCet(File fichero, ContratoVO contratoBBDD) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionCet.convertirFicheroImportacion(fichero);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs CET " + resultado.getListaCets().size() + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (AutoliquidacionBean autoliquidacion : resultado.getListaCets()) {
					try {
						log.error("INICIO VALIDACION CET tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionCet.validarAutoliquidacion(autoliquidacion);
						log.error("FIN VALIDACION CET tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							resultado.addResumenKO("Importación " + cont + " CET con código " + autoliquidacion.getCodigo() + ": " + resultVal.getMensaje());
						} else {
							log.error("INICIO GUARDADO CET tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionCet.autoliquidacion(autoliquidacion, contratoBBDD);
							if (resultGuardar.getError()) {
								resultado.addResumenKO("Importación " + cont + " CET con código " + autoliquidacion.getCodigo() + ": " + resultGuardar.getMensaje());
							} else {
								resultado.addResumenOK("Importación " + cont + " CET con código " + autoliquidacion.getCodigo());
							}
							log.error("FIN GUARDADO CET tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los CETs, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los CETs.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarCtit(File fichero, Long idUsuario, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionCtit.convertirFicheroImportacion(fichero, contratoBBDD, tienePermisoAdmin, idUsuario, esSiga);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs Ctit " + resultado.getListaTramitesCtit().size() + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (TramiteTrafTranVO tramiteCtitVO : resultado.getListaTramitesCtit()) {
					try {
						log.error("INICIO VALIDACION CTIT tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionCtit.validarTramiteImportacion(tramiteCtitVO);
						log.error("FIN VALIDACION CTIT tramite " + cont + " colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							gestionarResultadoIncorrecto(resultVal, cont, resultado);
						} else {
							log.error("INICIO GUARDADO CTIT tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionCtit.guardarImportacion(tramiteCtitVO, idUsuario);
							if (resultGuardar.getError()) {
								resultado = gestionarResultadoIncorrecto(resultGuardar, cont, resultado);
							} else {
								resultado = gestionarResultadoCorrecto(resultGuardar, resultVal, cont, resultado);
							}
							log.error("FIN GUARDADO CTIT tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
				resultado = anaidirErroresConvertir(resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de ctit.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarMatw(File fichero, Long idUsuario, ContratoVO contratoBBDD, Boolean tienePermisoAdmin,
			Boolean tienePermisoLiberarEEFF, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = servicioImportacionMatw.convertirFicheroImportacion(fichero, contratoBBDD, tienePermisoAdmin, tienePermisoLiberarEEFF, idUsuario, esSiga);
			if (!resultado.getError()) {
				int cont = 1;
				log.error("CONTADOR TRAMITES: lista de VOs Matw " + resultado.getListaTramitesMatw().size() + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
				for (TramiteTrafMatrVO tramiteMatrVO : resultado.getListaTramitesMatw()) {
					try {
						log.error("INICIO VALIDACION MATW tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						ResultadoValidacionImporBean resultVal = servicioImportacionMatw.validarTramiteImportacion(tramiteMatrVO);
						log.error("FIN VALIDACION MATW tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						if (resultVal.getError()) {
							resultado = gestionarResultadoIncorrecto(resultVal, cont, resultado);
						} else {
							log.error("INICIO GUARDADO MATW tramite " + cont + " del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
							ResultadoImportacionBean resultGuardar = servicioImportacionMatw.guardarImportacion(tramiteMatrVO, idUsuario, tienePermisoLiberarEEFF);
							if (resultGuardar.getError()) {
								resultado = gestionarResultadoIncorrecto(resultGuardar, cont, resultado);
							} else {
								resultado = gestionarResultadoCorrecto(resultGuardar, resultVal, cont, resultado);
							}
							log.error("FIN GUARDADO MATW tramite " + cont + " del  colegiado " + contratoBBDD.getColegiado().getNumColegiado());
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de guardar el tramite numero: " + cont + " ,error: ", e);
						resultado.addResumenKO("Ha sucedido un error a la hora de guardar el tramite numero: " + cont);
					}
					cont++;
				}
				resultado = anaidirErroresConvertir(resultado);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de matw, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar de importar el fichero con los tramites de matw.");
		}
		return resultado;
	}

	private ResultadoImportacionBean gestionarResultadoIncorrecto(ResultadoImportacionBean resultGuardar, int cont, ResultadoImportacionBean resultadoFinal) {
		String mensajeErrores = "";
		String mensaje = "";
		if (resultGuardar.getListaMensajes() != null && !resultGuardar.getListaMensajes().isEmpty()) {
			mensajeErrores = "Errores: ";
			for (String aviso : resultGuardar.getListaMensajes()) {
				mensajeErrores += " " + aviso;
			}
		}

		mensaje += cont == 0 ? "Tramite de solicitud no guardado: " : "Tramite " + cont + " no guardado: ";

		if (resultGuardar.getMensaje() != null && !resultGuardar.getMensaje().isEmpty()) {
			mensaje += resultGuardar.getMensaje();
		}

		if (mensajeErrores != null && !mensajeErrores.isEmpty()) {
			mensaje += " " + mensajeErrores;
		}

		resultadoFinal.addResumenKO(mensaje);
		return resultadoFinal;
	}

	private ResultadoImportacionBean gestionarResultadoIncorrecto(ResultadoValidacionImporBean resultVal, int cont, ResultadoImportacionBean resultadoFinal) {
		String mensajeErrores = "";
		String mensaje = "";
		if (resultVal.getListaMensajeError() != null && !resultVal.getListaMensajeError().isEmpty()) {
			mensajeErrores = "Errores: ";
			for (String aviso : resultVal.getListaMensajeError()) {
				mensajeErrores += " " + aviso;
			}
		}

		mensaje += cont == 0 ? "Trámite de solicitud no guardado: " : "Trámite " + cont + " no guardado: ";

		if (resultVal.getMensaje() != null && !resultVal.getMensaje().isEmpty()) {
			mensaje += resultVal.getMensaje();
		}

		if (mensajeErrores != null && !mensajeErrores.isEmpty()) {
			mensaje += " " + mensajeErrores;
		}

		resultadoFinal.addResumenKO(mensaje);
		return resultadoFinal;
	}

	private ResultadoImportacionBean gestionarResultadoCorrecto(ResultadoImportacionBean resultGuardar, ResultadoValidacionImporBean resultVal, int cont, ResultadoImportacionBean resultadoFinal) {
		String mensajeAvisos = "";
		String mensaje = "";
		if (resultVal.getListaMensajesAvisos() != null && !resultVal.getListaMensajesAvisos().isEmpty()) {
			mensajeAvisos = "Avisos: ";
			for (String aviso : resultVal.getListaMensajesAvisos()) {
				mensajeAvisos += " " + aviso;
			}
		}

		if (cont == 0) {
			mensaje += "Tramite de solicitud ";
		} else {
			mensaje += "Tramite " + cont + " ";
		}

		if (resultGuardar.getMensaje() != null && !resultGuardar.getMensaje().isEmpty()) {
			mensaje += "con el numero de expediente: " + resultGuardar.getNumExpediente() + " guardado con las siguientes incidencias: " + resultGuardar.getMensaje();
			if (mensajeAvisos != null && !mensajeAvisos.isEmpty()) {
				mensaje += mensajeAvisos;
			}
		} else {
			mensaje += "guardado correctamente con el numero de expediente: " + resultGuardar.getNumExpediente() + ". ";
			if (mensajeAvisos != null && !mensajeAvisos.isEmpty()) {
				mensaje += mensajeAvisos;
			}
		}

		resultadoFinal.addResumenOK(mensaje);
		return resultadoFinal;
	}

	private ResultadoImportacionBean validarDatosImportacion(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean) {
		ResultadoImportacionBean resultValidar = new ResultadoImportacionBean(Boolean.FALSE);
		if (fichero == null) {
			resultValidar.setError(Boolean.TRUE);
			resultValidar.setMensaje("Debe de seleccionar un fichero para poder importar.");
		} else if (importarTramiteBean == null) {
			resultValidar.setError(Boolean.TRUE);
			resultValidar.setMensaje("Debe de rellenar los datos de pantalla para poder importar el fichero.");
		} else if (importarTramiteBean.getTipoImportacion() == null || importarTramiteBean.getTipoImportacion().isEmpty()) {
			resultValidar.setError(Boolean.TRUE);
			resultValidar.setMensaje("Debe de elegir el tipo de fichero que desea importar.");
		} else if (importarTramiteBean.getIdContrato() == null) {
			if (!TipoImportacion.IMPORT_PEGATINAS.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
				resultValidar.setError(Boolean.TRUE);
				resultValidar.setMensaje("Debe de indicar el Contrato del Gestor.");
			}
		} else {
			String ext = FilenameUtils.getExtension(nombreFichero);
			if (TipoImportacion.XML_MATW.getValorEnum().equals(importarTramiteBean.getTipoImportacion()) || TipoImportacion.XML_CTIT.getValorEnum().equals(importarTramiteBean.getTipoImportacion())
					|| TipoImportacion.XML_CET.getValorEnum().equals(importarTramiteBean.getTipoImportacion()) || TipoImportacion.XML_DUPLICADO.getValorEnum().equals(importarTramiteBean
							.getTipoImportacion()) || TipoImportacion.XML_BAJA.getValorEnum().equals(importarTramiteBean.getTipoImportacion()) || TipoImportacion.XML_SOLICITUDES.getValorEnum().equals(
									importarTramiteBean.getTipoImportacion())) {
				if (!"XML".equalsIgnoreCase(ext)) {
					resultValidar.setError(Boolean.TRUE);
					resultValidar.setMensaje("Solo se pueden importar un fichero con extensión .xml");
				}
			} else if (TipoImportacion.TXT_TASAS.getValorEnum().equals(importarTramiteBean.getTipoImportacion())) {
				if (!"TXT".equalsIgnoreCase(ext)) {
					resultValidar.setError(Boolean.TRUE);
					resultValidar.setMensaje("Para poder importar este tipo de tramite debe de ser un fichero con extensión .txt");
				}
			} else if (TipoImportacion.XLS_DUPLICADO.getValorEnum().equals(importarTramiteBean.getTipoImportacion())
					&& !"XLS".equalsIgnoreCase(ext) && !"XLSX".equalsIgnoreCase(ext)) {
				resultValidar.setError(Boolean.TRUE);
				resultValidar.setMensaje("Para poder importar este tipo de tramite debe de ser un fichero con extensión .xls o .xlsx");
			}
		}
		return resultValidar;
	}

	@Override
	public ResultadoImportacionBean importarDuplicadosDistintivos(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, Long idUsuario, Boolean esGestoria) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			ContratoVO contratoVO = servicioContrato.getContratoVO(importarTramiteBean.getIdContrato());
			if (contratoVO != null) {
				resultado = servicioImportacionDupDistintivo.obtenerDatosFichero(fichero, nombreFichero);
				if (!resultado.getError() && resultado.getListaDuplicadosDstv() != null
						&& !resultado.getListaDuplicadosDstv().isEmpty()) {
					for (VehNoMatOegamVO duplicado : resultado.getListaDuplicadosDstv()) {
						try {
							ResultadoImportacionBean resultImpDup = servicioImportacionDupDistintivo.importarDuplicado(duplicado, contratoVO, idUsuario, esGestoria);
							if (resultImpDup.getError()) {
								resultado.addResumenKO("Ha sucedido un error a la hora de importar un duplicado de distintivo para la matricula: " + duplicado.getMatricula());
							} else {
								resultado.addResumenOK("Matricula: " + duplicado.getMatricula() + " importada correctamente.");
								resultado.addListaIdDupDstv(resultImpDup.getIdDupDistintivo());
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de importar un duplicado de distintivo para la matricula: " + duplicado.getMatricula() + ", error: ", e);
							resultado.addResumenKO("Ha sucedido un error a la hora de importar un duplicado de distintivo para la matricula: " + duplicado.getMatricula());
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos para el contrato indicado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de importar los duplicados de distintivos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de importar los duplicados de distintivos.");
		}
		return resultado;
	}

	private ResultadoImportacionBean importarTasas(File fichero, String nombreFichero,ImportarTramiteBean importarTramiteBean, Long idUsuario, Boolean esGestoria, Boolean esAdmin) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			ContratoVO contratoVO = servicioContrato.getContratoVO(importarTramiteBean.getIdContrato());
			if (contratoVO != null) {
				resultado = servicioImportacionTasas.obtenerDatosFichero(fichero, nombreFichero,importarTramiteBean,contratoVO,idUsuario,esAdmin);
			//	if (!resultado.getError()) {
				if (resultado.getListaTasaBean() != null && !resultado.getListaTasaBean().isEmpty()) {
					for (TasaImportacionBean tasaBean : resultado.getListaTasaBean()) {
						try {
							ResultadoImportacionBean resultImpTasa = null;
							if (FormatoTasa.ELECTRONICO.getCodigo()==(Integer.parseInt(tasaBean.getFormatoTasa()))) {
								resultImpTasa = servicioImportacionTasas.importarTasasElectronica(tasaBean, contratoVO, idUsuario, esGestoria);
							} else {
								resultImpTasa = servicioImportacionTasas.importarTasasPegatinaTasa(tasaBean, contratoVO, idUsuario, esGestoria);
							}
							gestionarResultado(resultImpTasa,resultado,tasaBean);
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de importar la tasa: " + tasaBean.getCodigoTasa() + ", error: ", e);
							resultado.addResumenKO("Ha sucedido un error a la hora de importar la tasa: " + tasaBean.getCodigoTasa());
						}
					}
				}
			}else {
				resultado.setError(Boolean.TRUE);
				resultado.getListaMensajes();
			}
			/*} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se han encontrado datos para el contrato indicado.");
			}*/
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de importar las tasas, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de importar las tasas.");
		}
		return resultado;
	}

	private void gestionarResultado(ResultadoImportacionBean resultImpTasa, ResultadoImportacionBean resultado, TasaImportacionBean tasaBean) {
		if (resultImpTasa.getError()) {
			resultado.addResumenKO("Ha sucedido un error a la hora de importar la tasa : " + tasaBean.getCodigoTasa());
		} else {
			resultado.addResumenOK("Tasa: " + tasaBean.getCodigoTasa() + " importada correctamente.");
		}
	}
}