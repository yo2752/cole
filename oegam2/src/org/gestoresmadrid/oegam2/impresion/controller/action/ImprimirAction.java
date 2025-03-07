package org.gestoresmadrid.oegam2.impresion.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionBaja;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionDuplicados;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionMatriculacion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionSolicitud;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionTransmision;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.servicio.interfaz.ServicioImprimirTraficoInt;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImprimirAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 8374623091845605936L;

	@Resource(name = "modeloImprimirPaginated")
	private ModelPagination modeloImprimirPaginated;

	@Autowired
	private ServicioImpresion servicioImpresionImpl;

	@Autowired
	private ServicioPermisos servicioPermisosImpl;

	@Autowired
	private ServicioImprimirTraficoInt servicioImprimirTraficoImpl;

	@Autowired
	private ServicioImpresionBaja servicioImpresionBajaImpl;

	@Autowired
	private ServicioImpresionDuplicados servicioImpresionDuplicados;

	@Autowired
	private ServicioImpresionSolicitud servicioImpresionSolicitud;

	@Autowired
	private ServicioImpresionMatriculacion servicioImpresionMatriculacion;

	@Autowired
	private ServicioImpresionTransmision servicioImpresionTransmision;

	@Autowired
	private ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ModeloTramiteTrafImpl modeloTramiteTrafImpl;

	private ParametrosPegatinaMatriculacion etiquetaParametros;
	private ImprimirTramiteTraficoDto imprimirTramiteTraficoDto;
	private ResultValidarTramitesImprimir resultBeanImprimir;
	private Boolean electronica = null;
	private String numsExpediente;
	private String numCreditosDisponibles;
	private String numCreditosTotales;
	private String numCreditosBloqueados;
	private String tipoRepresentacion;
	private String tipoRepresentacion1;
	private String tipoRepresentacion2;
	private String tipoRepresentacion3;
	private String tipoRepresentacion4;
	private String tipoRepresentacion5;
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String listaChecksConsultaTramite;
	private Boolean generadoNRE06;
	private String impreso;
	private boolean volverAntiguaConsulta;

	private static final String COLUMDEFECT = "numExpediente";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirAction.class);

	public String inicio() {
		etiquetaParametros = servicioImpresionImpl.getEtiquetasParametros(resultBeanImprimir.getTipoTramite(), utilesColegiado.getNumColegiadoSession());
		electronica = servicioImpresionImpl.esTransmisionElectronica(resultBeanImprimir.getTipoTramite());
		cargarFiltroInicial();
		if (imprimirTramiteTraficoDto != null && imprimirTramiteTraficoDto.getResultBeanImprimir() != null) {
			generarSituacionCreditos(utilesColegiado.getIdContratoSessionBigDecimal());
			actualizarPaginatedList();
		} else {
			addActionError("Error al obtener los expedientes para imprimir.");
		}
		return getResultadoPorDefecto();
	}

	private void generarSituacionCreditos(BigDecimal idContrato) {
		ResultBean resultCreditos = null;
		if (imprimirTramiteTraficoDto != null && imprimirTramiteTraficoDto.getResultBeanImprimir() != null) {
			resultCreditos = servicioImpresionImpl.generarSituacionCreditos(imprimirTramiteTraficoDto, idContrato);
		}
		if (resultCreditos != null && !resultCreditos.getError()) {
			setCreditosPantalla(resultCreditos);
		} else {
			addActionError("Error al obtener los créditos del tipo de trámite. Debe iniciar los créditos del usuario para poder realizar operaciones que tengan coste.");
		}
	}

	private void setCreditosPantalla(ResultBean resultCreditos) {
		imprimirTramiteTraficoDto.setNumCreditosBloqueados(resultCreditos.getAttachment("creditosBlock").toString());
		imprimirTramiteTraficoDto.setNumCreditosDisponibles(resultCreditos.getAttachment("creditosDisponibles").toString());
		imprimirTramiteTraficoDto.setNumCreditosTotales(resultCreditos.getAttachment("creditosTotales").toString());
	}

	public String obtenerFichero() throws Throwable {
		String[] numsExpedientes = null;
		ResultBean result = null;
		// Se obtienen todos los datos necesarios de la session al inicio para que no falle por la perdida de session al acceder a utilesColegiado
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		String idSession = ServletActionContext.getRequest().getSession().getId();
		boolean tienePermisosAdmin = utilesColegiado.tienePermisoAdmin();
		boolean tienePermisosImpresionBlqBajas = utilesColegiado.tienePermisoImpresionBloqueBajas();
		boolean tienePermisosImpresionBlqTransm = utilesColegiado.tienePermisoImpresionBloqueTransmision();

		imprimirTramiteTraficoDto = (ImprimirTramiteTraficoDto) getSession().get(getKeyCriteriosSession());

		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tiene permiso para realizar esta acción.");
			return SUCCESS;
		}

		result = servicioImpresionImpl.comprobarDatosObligatorios(listaChecksConsultaTramite, impreso);
		if (result == null) {
			numsExpedientes = listaChecksConsultaTramite.replace(" ", "").split(",");
		} else {
			addActionError(result.getListaMensajes().get(0));
			actualizarPaginatedList();
			generarSituacionCreditos(idContrato);
			return SUCCESS;
		}

		String numeroMaxImpresiones = gestorPropiedades.valorPropertie("maximo.numero.impresiones");
		if (numeroMaxImpresiones != null && numsExpedientes != null && numsExpedientes.length > Integer.parseInt(numeroMaxImpresiones)) {
			addActionError("No puede realizar más de " + numeroMaxImpresiones + " impresiones.");
			return SUCCESS;
		}

		if (!tienePermisosAdmin) {
			if (!servicioPermisosImpl.usuarioTienePermisoSobreTramites(numsExpedientes, idContrato.longValue())) {
				// if (!servicioPermisosImpl.usuarioTienePermisoSobreTramites(numsExpedientes, idContrato.toString())) {
				addActionError("No tienes permiso para trabajar con los trámites seleccionados");
				actualizarPaginatedList();
				generarSituacionCreditos(idContrato);
				return SUCCESS;
			}

			if (impreso.equals("PDF417") && numsExpedientes.length > 1) {
				if (imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.Baja) {
					if (!tienePermisosImpresionBlqBajas) {
						addActionError("No tiene permiso para imprimir trámites de Bajas tipo PDF417 en bloque.");
					}
				} else if (imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.Transmision || imprimirTramiteTraficoDto.getResultBeanImprimir()
						.getTipoTramite() == TipoTramiteTrafico.TransmisionElectronica) {
					if (!tienePermisosImpresionBlqTransm) {
						addActionError("No tiene permiso para imprimir trámites de Transmisión tipo PDF417 en bloque.");
					}
				}
			}

		}
		result = servicioImpresionImpl.comprobarImpresion(impreso, imprimirTramiteTraficoDto, numsExpedientes, idContrato, idUsuario);
		if (result != null) {
			addActionError(result.getListaMensajes().get(0));
			actualizarPaginatedList();
			generarSituacionCreditos(idContrato);
			return SUCCESS;
		}
		// Generamos los parametros para llamar al servicio de impresion.
		CriteriosImprimirTramiteTraficoBean criteriosImprimir = comprobarTipoImpreso();

		result = imprimirTramites(numsExpedientes, criteriosImprimir, numColegiado, idContrato, idUsuario);

		// Variable para controlar si la impresión se ha realizado correctamente
		boolean terminadoCorrectamente = false;
		// Con el documento generado, realizamos el resto de acciones asociadas.
		byte[] archivo = null;
		if (result != null && !result.getError()) {
			try {
				archivo = (byte[]) result.getAttachment(ResultBean.TIPO_PDF);
				String nombreArchivo = impreso;
				if (result.getAttachment(ResultBean.NOMBRE_FICHERO) != null) {
					nombreArchivo = (String) result.getAttachment(ResultBean.NOMBRE_FICHERO);
				}
				setFileName(new SimpleDateFormat("ddMMyy_HHmmss.S").format(new Date()) + "_" + nombreArchivo);
				boolean guardado = servicioImpresionImpl.guardarFichero(archivo, getFileName(), criteriosImprimir.getTipoImpreso());
				boolean borradoDoc = false;
				if (!guardado) {
					addActionError("Existen problemas al imprimir. Inténtelo más tarde");
				} else if (impreso.equals("PDF417")) {
					result = servicioImpresionImpl.impresionPDF417(numsExpedientes, imprimirTramiteTraficoDto, idContrato, idUsuario, impreso);
					if (result == null || result.getError()) {
						borradoDoc = servicioImpresionImpl.borrarDocumentoGuardado(fileName, idSession);
						for (String mensaje : result.getListaMensajes()) {
							addActionError(mensaje);
						}
					} else {
						terminadoCorrectamente = true;
						addActionMessage(result.getListaMensajes().get(0));
					}
				} else if (impreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())) {
					result = servicioImprimirTraficoImpl.cambiarEstadoTramiteImprimir(imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite().getValorEnum(), numsExpedientes, impreso,
							idUsuario);
					if (result == null) {
						addActionError("No se ha podido generar el documento. Inténtelo más tarde");
						log.error("No se ha podido cambiar el estado al trámite de transmisión");
						borradoDoc = servicioImpresionImpl.borrarDocumentoGuardado(fileName, idSession);
					} else if (result.getError()) {
						log.error("No se ha podido cambiar el estado al trámite de transmisión");
						addActionError("No se ha podido cambiar el estado al trámite de transmisión");
						for (String mensaje : result.getListaMensajes()) {
							addActionError(mensaje);
							log.error(mensaje);
						}
						borradoDoc = servicioImpresionImpl.borrarDocumentoGuardado(fileName, idSession);
					} else {
						addActionMessage("La impresión se realizó correctamente");
						log.info("Se ha generado correctamente los impreso de tipo " + impreso + " para los trámites " + numsExpediente + " solicitados por " + idUsuario);
						terminadoCorrectamente = true;
					}
				} else if (TipoImpreso.BajasTelematicas.toString().equals(impreso)) {
					result = servicioTramiteTraficoBaja.cambiarEstadoTramitesImprimir(numsExpedientes, idUsuario);
					if (result != null && result.getError()) {
						for (String mensaje : result.getListaMensajes()) {
							addActionError(mensaje);
						}
						borradoDoc = servicioImpresionImpl.borrarDocumentoGuardado(fileName, idSession);
					} else {
						addActionMessage("La impresión se realizó correctamente");
						log.info("Se ha generado correctamente los impreso de tipo " + impreso + " para los trámites " + numsExpediente + " solicitados por " + idUsuario);
						terminadoCorrectamente = true;
					}
				} else {
					addActionMessage("La impresión se realizó correctamente");
					log.info("Se ha generado correctamente los impreso de tipo " + impreso + " para los trámites " + numsExpediente + " solicitados por " + idUsuario);
					terminadoCorrectamente = true;
				}
				if (borradoDoc) {
					log.info("Se ha producido un error a la hora de borrar el documento imprimido guardado");
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de imprimir, error: " + e.getMessage());
				addActionError("Ha sucedido un error a la hora de imprimir.");
				actualizarPaginatedList();
				generarSituacionCreditos(idContrato);
				return SUCCESS;
			} catch (OegamExcepcion e) {
				log.error("Ha sucedido un error a la hora de imprimir, error: " + e.getMessage());
				addActionError("Ha sucedido un error a la hora de imprimir.");
				actualizarPaginatedList();
				generarSituacionCreditos(idContrato);
				return SUCCESS;
			}
		} else {
			if (result != null) {
				addActionError(result.getListaMensajes().get(0));
			} else {
				addActionError("No se pudo recuperar ningún archivo.");
			}
		}

		servicioImpresionImpl.crearAccionSiNecesaria(terminadoCorrectamente, numsExpedientes, impreso, idUsuario);
		actualizarPaginatedList();
		generarSituacionCreditos(idContrato);

		return SUCCESS;
	}

	private CriteriosImprimirTramiteTraficoBean comprobarTipoImpreso() {
		CriteriosImprimirTramiteTraficoBean criterio = new CriteriosImprimirTramiteTraficoBean();
		if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(impreso) || TipoImpreso.MatriculacionMandatoGenerico.toString().equals(impreso)) {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion);
		} else if (TipoImpreso.DeclaracionJuradaExportacionTransito.getValorEnum().equals(impreso)) {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion1);
		} else if (TipoImpreso.DeclaracionJuradaExtravioFicha.getValorEnum().equals(impreso)) {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion2);
		} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.getValorEnum().equals(impreso)) {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion3);
		} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.getValorEnum().equals(impreso)) {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion4);
		} else if (TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.getValorEnum().equals(impreso)) {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion5);
		} else {
			criterio = servicioImpresionImpl.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion);
		}
		return criterio;
	}

	private ResultBean imprimirTramites(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario)
			throws Throwable {
		ResultBean result = null;
		if (criteriosImprimir == null || criteriosImprimir.getTipoImpreso() == null || criteriosImprimir.getTipoTramite() == null) {
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
			return result;
		}
		// Imprimimos en función del tipo de trámite.
		if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion)) {
			result = servicioImpresionMatriculacion.imprimirMatriculacionPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario, utilesColegiado.tienePermisoImpresionPdf417());
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.TransmisionElectronica)) {
			result = servicioImpresionTransmision.imprimirTransmisionesPorExpedientes(numsExpedientes, criteriosImprimir, numColegiado, idContrato, idUsuario, true, utilesColegiado
					.tienePermisoImpresionPdf417());
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Transmision)) {
			result = servicioImpresionTransmision.imprimirTransmisionesPorExpedientes(numsExpedientes, criteriosImprimir, numColegiado, idContrato, idUsuario, false, utilesColegiado
					.tienePermisoImpresionPdf417());
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Solicitud)) {
			result = servicioImpresionSolicitud.imprimirSolicitudPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario);
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Duplicado)) {
			result = servicioImpresionDuplicados.imprimirDuplicadosPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario);
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Baja)) {
			result = servicioImpresionBajaImpl.imprimirBajaPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario);
		}
		return result;
	}

	public String descargarFichero() {
		try {
			File file = servicioImpresionImpl.recuperarFichero(fileName);
			imprimirTramiteTraficoDto = (ImprimirTramiteTraficoDto) getSession().get(getKeyCriteriosSession());
			if (file != null) {
				setInputStream(new FileInputStream(file.getAbsoluteFile()));
				servicioImpresionImpl.borrarDocumentoGuardado(fileName, null);
			} else {
				generarSituacionCreditos(utilesColegiado.getIdContratoSessionBigDecimal());
				fileName = null;
				cargarFiltroInicial();
				actualizarPaginatedList();
				return SUCCESS;
			}
		} catch (FileNotFoundException e) {
			log.error("No se ha podido borrar el fichero, error: " + e.getMessage());
			addActionError("No se ha podido borrar el fichero");
			generarSituacionCreditos(utilesColegiado.getIdContratoSessionBigDecimal());
			cargarFiltroInicial();
			actualizarPaginatedList();
			fileName = null;
			return SUCCESS;
		}
		return "descargarFichero";
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImprimirPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void cargarFiltroInicial() {
		if (imprimirTramiteTraficoDto == null) {
			imprimirTramiteTraficoDto = new ImprimirTramiteTraficoDto();
			if (getNumsExpediente() != null) {
				String[] auxCodSeleccionados = getNumsExpediente().replace(" ", "").split(",");
				BigDecimal[] codSeleccionados = new BigDecimal[auxCodSeleccionados.length];
				for (int i = 0; i < auxCodSeleccionados.length; i++) {
					BigDecimal auxBigDecimal = new BigDecimal(auxCodSeleccionados[i]);
					codSeleccionados[i] = auxBigDecimal;
				}
				imprimirTramiteTraficoDto.setNumExpedientes(codSeleccionados);
			}
			if (resultBeanImprimir != null) {
				imprimirTramiteTraficoDto.setResultBeanImprimir(resultBeanImprimir);
			}
			if (electronica != null) {
				imprimirTramiteTraficoDto.setElectronica(electronica);
			}
		}
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	protected Object getBeanCriterios() {
		return imprimirTramiteTraficoDto;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.imprimirTramiteTraficoDto = (ImprimirTramiteTraficoDto) object;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorImprimirTramitesTrafico";
	}

	/**
	 * @return the modeloImprimirPaginated
	 */
	public ModelPagination getModeloImprimirPaginated() {
		return modeloImprimirPaginated;
	}

	/**
	 * @param modeloImprimirPaginated the modeloImprimirPaginated to set
	 */
	public void setModeloImprimirPaginated(ModelPagination modeloImprimirPaginated) {
		this.modeloImprimirPaginated = modeloImprimirPaginated;
	}

	/**
	 * @return the etiquetaParametros
	 */
	public ParametrosPegatinaMatriculacion getEtiquetaParametros() {
		return etiquetaParametros;
	}

	/**
	 * @param etiquetaParametros the etiquetaParametros to set
	 */
	public void setEtiquetaParametros(ParametrosPegatinaMatriculacion etiquetaParametros) {
		this.etiquetaParametros = etiquetaParametros;
	}

	/**
	 * @return the resultBeanImprimir
	 */
	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}

	/**
	 * @param resultBeanImprimir the resultBeanImprimir to set
	 */
	public void setResultBeanImprimir(ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}

	/**
	 * @return the electronica
	 */
	public Boolean getElectronica() {
		return electronica;
	}

	/**
	 * @param electronica the electronica to set
	 */
	public void setElectronica(Boolean electronica) {
		this.electronica = electronica;
	}

	/**
	 * @return the numsExpediente
	 */
	public String getNumsExpediente() {
		return numsExpediente;
	}

	/**
	 * @param numsExpediente the numsExpediente to set
	 */
	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	/**
	 * @return the numCreditosDisponibles
	 */
	public String getNumCreditosDisponibles() {
		return numCreditosDisponibles;
	}

	/**
	 * @param numCreditosDisponibles the numCreditosDisponibles to set
	 */
	public void setNumCreditosDisponibles(String numCreditosDisponibles) {
		this.numCreditosDisponibles = numCreditosDisponibles;
	}

	/**
	 * @return the numCreditosTotales
	 */
	public String getNumCreditosTotales() {
		return numCreditosTotales;
	}

	/**
	 * @param numCreditosTotales the numCreditosTotales to set
	 */
	public void setNumCreditosTotales(String numCreditosTotales) {
		this.numCreditosTotales = numCreditosTotales;
	}

	/**
	 * @return the numCreditosBloqueados
	 */
	public String getNumCreditosBloqueados() {
		return numCreditosBloqueados;
	}

	/**
	 * @param numCreditosBloqueados the numCreditosBloqueados to set
	 */
	public void setNumCreditosBloqueados(String numCreditosBloqueados) {
		this.numCreditosBloqueados = numCreditosBloqueados;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the listaChecksConsultaTramite
	 */
	public String getListaChecksConsultaTramite() {
		return listaChecksConsultaTramite;
	}

	/**
	 * @param listaChecksConsultaTramite the listaChecksConsultaTramite to set
	 */
	public void setListaChecksConsultaTramite(String listaChecksConsultaTramite) {
		this.listaChecksConsultaTramite = listaChecksConsultaTramite;
	}

	/**
	 * @return the generadoNRE06
	 */
	public Boolean getGeneradoNRE06() {
		return generadoNRE06;
	}

	/**
	 * @param generadoNRE06 the generadoNRE06 to set
	 */
	public void setGeneradoNRE06(Boolean generadoNRE06) {
		this.generadoNRE06 = generadoNRE06;
	}

	/**
	 * @return the imprimirTramiteTraficoDto
	 */
	public ImprimirTramiteTraficoDto getImprimirTramiteTraficoDto() {
		return imprimirTramiteTraficoDto;
	}

	/**
	 * @param imprimirTramiteTraficoDto the imprimirTramiteTraficoDto to set
	 */
	public void setImprimirTramiteTraficoDto(ImprimirTramiteTraficoDto imprimirTramiteTraficoDto) {
		this.imprimirTramiteTraficoDto = imprimirTramiteTraficoDto;
	}

	/**
	 * @return the impreso
	 */
	public String getImpreso() {
		return impreso;
	}

	/**
	 * @param impreso the impreso to set
	 */
	public void setImpreso(String impreso) {
		this.impreso = impreso;
	}

	/**
	 * @return the tipoRepresentacion
	 */
	public String getTipoRepresentacion() {
		return tipoRepresentacion;
	}

	/**
	 * @param tipoRepresentacion the tipoRepresentacion to set
	 */
	public void setTipoRepresentacion(String tipoRepresentacion) {
		this.tipoRepresentacion = tipoRepresentacion;
	}

	public ModeloTramiteTrafImpl getModeloTramiteTrafImpl() {
		if (modeloTramiteTrafImpl == null) {
			modeloTramiteTrafImpl = new ModeloTramiteTrafImpl();
		}
		return modeloTramiteTrafImpl;
	}

	public void setModeloTramiteTrafImpl(ModeloTramiteTrafImpl modeloTramiteTrafImpl) {
		this.modeloTramiteTrafImpl = modeloTramiteTrafImpl;
	}

	public String getTipoRepresentacion1() {
		return tipoRepresentacion1;
	}

	public void setTipoRepresentacion1(String tipoRepresentacion1) {
		this.tipoRepresentacion1 = tipoRepresentacion1;
	}

	public String getTipoRepresentacion2() {
		return tipoRepresentacion2;
	}

	public void setTipoRepresentacion2(String tipoRepresentacion2) {
		this.tipoRepresentacion2 = tipoRepresentacion2;
	}

	public String getTipoRepresentacion3() {
		return tipoRepresentacion3;
	}

	public void setTipoRepresentacion3(String tipoRepresentacion3) {
		this.tipoRepresentacion3 = tipoRepresentacion3;
	}

	public String getTipoRepresentacion4() {
		return tipoRepresentacion4;
	}

	public void setTipoRepresentacion4(String tipoRepresentacion4) {
		this.tipoRepresentacion4 = tipoRepresentacion4;
	}

	public String getTipoRepresentacion5() {
		return tipoRepresentacion5;
	}

	public void setTipoRepresentacion5(String tipoRepresentacion5) {
		this.tipoRepresentacion5 = tipoRepresentacion5;
	}

	public boolean isVolverAntiguaConsulta() {
		return volverAntiguaConsulta;
	}

	public void setVolverAntiguaConsulta(boolean volverAntiguaConsulta) {
		this.volverAntiguaConsulta = volverAntiguaConsulta;
	}
}