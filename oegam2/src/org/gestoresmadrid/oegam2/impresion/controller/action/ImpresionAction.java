package org.gestoresmadrid.oegam2.impresion.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

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
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ImpresionAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -1742714994286896744L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImpresionAction.class);

	private static final String COLUMDEFECT = "numExpediente";

	@Resource(name = "modeloImprimirPaginated")
	private ModelPagination modeloImprimirPaginated;

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private ServicioImpresionDocumentos servicioImpresionTrafico;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioImpresionBaja servicioImpresionBaja;

	@Autowired
	private ServicioImpresionDuplicados servicioImpresionDuplicado;

	@Autowired
	private ServicioImpresionSolicitud servicioImpresionSolicitud;

	@Autowired
	private ServicioImpresionMatriculacion servicioImpresionMatriculacion;

	@Autowired
	private ServicioImpresionTransmision servicioImpresionTransmision;

	@Autowired
	private ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ParametrosPegatinaMatriculacion etiquetaParametros;
	private ImprimirTramiteTraficoDto imprimirTramiteTraficoDto;
	private ResultValidarTramitesImprimir resultBeanImprimir;
	private Boolean electronica = null;
	private String numsExpediente;
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String listaChecksConsultaTramite;
	private String impreso;

	private String tipoRepresentacion;
	private String tipoRepresentacion1;
	private String tipoRepresentacion2;
	private String tipoRepresentacion3;
	private String tipoRepresentacion4;
	private String tipoRepresentacion5;

	private boolean volverAntiguaConsulta;

	public String inicio() {
		etiquetaParametros = servicioImpresion.getEtiquetasParametros(resultBeanImprimir.getTipoTramite(), utilesColegiado.getNumColegiadoSession());
		electronica = servicioImpresion.esTransmisionElectronica(resultBeanImprimir.getTipoTramite());
		cargarFiltroInicial();
		if (imprimirTramiteTraficoDto != null && imprimirTramiteTraficoDto.getResultBeanImprimir() != null) {
			rellenarCreditos();
			actualizarPaginatedList();
		} else {
			addActionError("Error al obtener los expedientes para imprimir.");
		}
		return SUCCESS;
	}

	private void rellenarCreditos() {
		ResultBean resultCreditos = null;
		if (imprimirTramiteTraficoDto != null && imprimirTramiteTraficoDto.getResultBeanImprimir() != null) {
			resultCreditos = servicioImpresion.generarSituacionCreditos(imprimirTramiteTraficoDto,
					utilesColegiado.getIdContratoSessionBigDecimal());
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

	public String imprimir() {
		ResultadoImpresionBean result = new ResultadoImpresionBean(Boolean.FALSE);
		imprimirTramiteTraficoDto = (ImprimirTramiteTraficoDto) getSession().get(getKeyCriteriosSession());
		result = validarPermisos();
		if (result != null && !result.getError()) {
			result = validarDatosObligatorios();
			if (result != null && !result.getError()) {
				String[] numsExpedientes = result.getNumsExpedientes();
				result = servicioImpresionDocumentos.validarImpresionPantalla(impreso,
						imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite().getValorEnum(),
						numsExpedientes, utilesColegiado.getIdContratoSessionBigDecimal(),
						utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (result != null && !result.getError()) {
					if (mandarAProceso()) {
						imprimirProceso(numsExpedientes, imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite().getValorEnum());
					} else {
						try {
							ResultBean resultadoImpresion = imprimirTramitesNormal(numsExpedientes,
									utilesColegiado.getNumColegiadoSession(),
									utilesColegiado.getIdContratoSessionBigDecimal(),
									utilesColegiado.getIdUsuarioSessionBigDecimal());
							if (resultadoImpresion != null && !resultadoImpresion.getError()) {
								byte[] archivo = (byte[]) resultadoImpresion.getAttachment(ResultBean.TIPO_PDF);
								String nombreArchivo = impreso;
								String nombreFinal = "NRE";
								if ("SolicitudNre06".equals(nombreArchivo)) {
									for (int i = 0; i < numsExpedientes.length; i++) {
										if (resultadoImpresion.getAttachment(ResultBean.NOMBRE_FICHERO) != null) {
											nombreArchivo = (String) resultadoImpresion.getAttachment(ResultBean.NOMBRE_FICHERO);
										}
										fileName = numsExpedientes[i] + "_" + nombreFinal + ConstantesGestorFicheros.EXTENSION_PDF;
										boolean guardado = servicioImpresion.guardarFichero(archivo, getFileName(), impreso);
										if (!guardado) {
											addActionError("Existen problemas al imprimir. Inténtelo más tarde");
										} else {
											addActionMessage("La impresión se realizó correctamente");
										}
									}
								} else {
									if (resultadoImpresion.getAttachment(ResultBean.NOMBRE_FICHERO) != null) {
										nombreArchivo = (String) resultadoImpresion.getAttachment(ResultBean.NOMBRE_FICHERO);
									}
									if ("PermisoTemporalDuplicado.pdf".contentEquals(nombreArchivo) ||
											"JustifRegEntrada.pdf".contentEquals(nombreArchivo) ||
											"JustificanteDuplicado.pdf".contentEquals(nombreArchivo)) {
										for (int i = 0; i < numsExpedientes.length; i++) {
											fileName = numsExpedientes[i] + "_" + nombreArchivo;
										}
									} else if("CertificadoRevisionColegial".contentEquals(nombreArchivo)){
										for (int i = 0; i < numsExpedientes.length; i++) {
											fileName = numsExpedientes[i] + "_" + nombreArchivo;
										}
									} else {
										fileName = new SimpleDateFormat("ddMMyy_HHmmss.S").format(new Date()) + "_" + nombreArchivo;
									}

									boolean guardado = servicioImpresion.guardarFichero(archivo, getFileName(), impreso);
									if (!guardado) {
										addActionError("Existen problemas al imprimir. Inténtelo más tarde");
									} else {
										addActionMessage("La impresión se realizó correctamente");
									}
								}
							} else {
								addActionError(
										resultadoImpresion != null && resultadoImpresion.getListaMensajes() != null
												? resultadoImpresion.getListaMensajes().get(0)
												: "No se pudo recuperar ningun archivo.");
							}
						} catch (Throwable e) {
							log.error("Error a la hora de imprimir", e);
							addActionError("Error a la hora de imprimir.");
						}
					}
				}
			}
		}

		if (result != null && result.getError()) {
			addActionError(result.getMensaje());
		}

		actualizarPaginatedList();
		rellenarCreditos();

		return SUCCESS;
	}

	private ResultadoImpresionBean validarDatosObligatorios() {
		ResultadoImpresionBean result = new ResultadoImpresionBean(Boolean.FALSE);

		if (impreso == null || impreso.equals("")) {
			result.setMensaje("No se ha seleccionado el tipo de documento a imprimir");
			result.setError(Boolean.TRUE);
			return result;
		}

		if (listaChecksConsultaTramite != null && listaChecksConsultaTramite.equals("")) {
			result.setMensaje("No existen expedientes seleccionados.");
			result.setError(Boolean.TRUE);
			return result;
		} else {
			result.setNumsExpedientes(listaChecksConsultaTramite.replace(" ", "").split(","));
		}

		String numeroMaxImpresiones = gestorPropiedades.valorPropertie("maximo.numero.impresiones");
		if (numeroMaxImpresiones != null && result.getNumsExpedientes() != null
				&& result.getNumsExpedientes().length > Integer.parseInt(numeroMaxImpresiones)) {
			result.setMensaje("No puede realizar más de " + numeroMaxImpresiones + " impresiones.");
			result.setError(Boolean.TRUE);
			return result;
		}
		return result;
	}

	private ResultadoImpresionBean validarPermisos() {
		ResultadoImpresionBean result = new ResultadoImpresionBean(Boolean.FALSE);

		String[] numsExpedientes = null;
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			result.setMensaje("No tiene permiso para realizar esta acción.");
			result.setError(Boolean.TRUE);
			return result;
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			if (!servicioPermisos.usuarioTienePermisoSobreTramites(numsExpedientes, utilesColegiado.getIdContratoSession())) {
				result.setMensaje("No tienes permiso para trabajar con los trámites seleccionados");
				result.setError(Boolean.TRUE);
				return result;
			}

			if (impreso != null && "PDF417".equals(impreso)) {
				if (imprimirTramiteTraficoDto.getResultBeanImprimir().getTipoTramite() == TipoTramiteTrafico.Baja) {
					if (!utilesColegiado.tienePermisoImpresionBloqueBajas()) {
						result.setMensaje("No tiene permiso para imprimir trámites de Bajas tipo PDF417 en bloque.");
						result.setError(Boolean.TRUE);
						return result;
					}
				} else if (imprimirTramiteTraficoDto.getResultBeanImprimir()
						.getTipoTramite() == TipoTramiteTrafico.Transmision
						|| imprimirTramiteTraficoDto.getResultBeanImprimir()
								.getTipoTramite() == TipoTramiteTrafico.TransmisionElectronica) {
					if (!utilesColegiado.tienePermisoImpresionBloqueTransmision()) {
						result.setMensaje("No tiene permiso para imprimir trámites de Transmisión tipo PDF417 en bloque.");
						result.setError(Boolean.TRUE);
						return result;
					}
				}
			}
		}
		return result;
	}

	private void imprimirProceso(String[] numExpedientes, String tipoTramite) {
		ResultadoImpresionBean resultado = servicioImpresionTrafico.crearImpresion(numExpedientes,
				utilesColegiado.getIdContratoSession(), utilesColegiado.getIdUsuarioSession(), impreso, tipoTramite,
				tipoRepresentacion);
		if (resultado != null && !resultado.getError()) {
			addActionMessage("Su solicitud de impresión se está procesando. Estará disponible en unos minutos. Recibirá una notificación con nombre del documento " + resultado.getNombreDocumento()
					+ ".");
		} else {
			addActionError("Existen errores en la solicitud de impresión.");
		}
	}

	private ResultBean imprimirTramitesNormal(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario) throws Throwable {
		ResultBean result = new ResultBean(Boolean.FALSE);
		CriteriosImprimirTramiteTraficoBean criteriosImprimir = comprobarTipoImpreso();

		if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion)) {
			result = servicioImpresionMatriculacion.imprimirMatriculacionPorExpedientes(numsExpedientes,
					criteriosImprimir, idUsuario, utilesColegiado.tienePermisoImpresionPdf417());
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.TransmisionElectronica)) {
			result = servicioImpresionTransmision.imprimirTransmisionesPorExpedientes(numsExpedientes,
					criteriosImprimir, numColegiado, idContrato, idUsuario, true,
					utilesColegiado.tienePermisoImpresionPdf417());
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Transmision)) {
			result = servicioImpresionTransmision.imprimirTransmisionesPorExpedientes(numsExpedientes,
					criteriosImprimir, numColegiado, idContrato, idUsuario, false,
					utilesColegiado.tienePermisoImpresionPdf417());
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Solicitud)) {
			result = servicioImpresionSolicitud.imprimirSolicitudPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario);
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Baja)) {
			result = servicioImpresionBaja.imprimirBajaPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario);
		} else if (criteriosImprimir.getTipoTramite().equals(TipoTramiteTrafico.Duplicado)) {
			result = servicioImpresionDuplicado.imprimirDuplicadosPorExpedientes(numsExpedientes, criteriosImprimir, idUsuario);
		}
		return result;
	}

	private CriteriosImprimirTramiteTraficoBean comprobarTipoImpreso() {
		CriteriosImprimirTramiteTraficoBean criterio = new CriteriosImprimirTramiteTraficoBean();
		if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(impreso) || TipoImpreso.MatriculacionMandatoGenerico.toString().equals(impreso)) {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion);
		} else if (TipoImpreso.DeclaracionJuradaExportacionTransito.getValorEnum().equals(impreso)) {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion1);
		} else if (TipoImpreso.DeclaracionJuradaExtravioFicha.getValorEnum().equals(impreso)) {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion2);
		} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.getValorEnum().equals(impreso)) {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion3);
		} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.getValorEnum().equals(impreso)) {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion4);
		} else if (TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.getValorEnum().equals(impreso)) {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion5);
		} else {
			criterio = servicioImpresion.generarCriteriosImprimirTramite(impreso, imprimirTramiteTraficoDto, etiquetaParametros, tipoRepresentacion);
		}
		return criterio;
	}

	private boolean mandarAProceso() {
		boolean nuevaImpresionProceso = false;
		if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(impreso)
				|| TipoImpreso.MatriculacionMandatoGenerico.toString().equals(impreso)) {
			nuevaImpresionProceso = true;
		} else if (TipoImpreso.MatriculacionBorradorPDF417.toString().equals(impreso)
				|| TipoImpreso.MatriculacionPDF417.toString().equals(impreso)
				|| TipoImpreso.MatriculacionPDFPresentacionTelematica.toString().equals(impreso)
				|| TipoImpreso.MatriculacionListadoBastidores_2.toString().equals(impreso)
				|| TipoImpreso.TransmisionModelo430.toString().equals(impreso)) {
			nuevaImpresionProceso = true;
			tipoRepresentacion = "";
		} else if ((TipoImpreso.DeclaracionJuradaExportacionTransito.toString().equals(impreso)
				|| TipoImpreso.DeclaracionJuradaExtravioFicha.toString().equals(impreso)
				|| TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.toString().equals(impreso)
				|| TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.toString().equals(impreso)
				|| TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.toString().equals(impreso))) {
			nuevaImpresionProceso = true;
			if (TipoImpreso.DeclaracionJuradaExportacionTransito.toString().equals(impreso)) {
				tipoRepresentacion = tipoRepresentacion1;
			} else if (TipoImpreso.DeclaracionJuradaExtravioFicha.toString().equals(impreso)) {
				tipoRepresentacion = tipoRepresentacion2;
			} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.toString().equals(impreso)) {
				tipoRepresentacion = tipoRepresentacion3;
			} else if (TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.toString().equals(impreso)) {
				tipoRepresentacion = tipoRepresentacion4;
			} else if (TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.toString().equals(impreso)) {
				tipoRepresentacion = tipoRepresentacion5;
			}
		} else if (TipoImpreso.TxtNre.toString().equals(impreso)) {
			nuevaImpresionProceso = true;
		}
		return nuevaImpresionProceso;
	}

	public String descargarFichero() {
		try {
			File file = servicioImpresion.recuperarFichero(fileName);
			imprimirTramiteTraficoDto = (ImprimirTramiteTraficoDto) getSession().get(getKeyCriteriosSession());
			if (file != null) {
				setInputStream(new FileInputStream(file.getAbsoluteFile()));
				servicioImpresion.borrarDocumentoGuardado(fileName, null);
				return "descargarFichero";
			}
		} catch (FileNotFoundException e) {
			log.error("No se ha podido borrar el fichero, error: " + e.getMessage());
		}

		addActionError("No se ha encontrato el fichero a descargar");
		rellenarCreditos();
		cargarFiltroInicial();
		actualizarPaginatedList();
		fileName = null;
		return SUCCESS;
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
			if (numsExpediente != null) {
				String[] auxCodSeleccionados = numsExpediente.replace(" ", "").split(",");
				BigDecimal[] codSeleccionados = new BigDecimal[auxCodSeleccionados.length];
				for (int i = 0; i < auxCodSeleccionados.length; i++) {
					BigDecimal auxBigDecimal = new BigDecimal(auxCodSeleccionados[i]);
					codSeleccionados[i] = auxBigDecimal;
				}
				imprimirTramiteTraficoDto.setNumExpedientes(codSeleccionados);
				this.setResultadosPorPagina(String.valueOf(codSeleccionados.length));
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

	public ModelPagination getModeloImprimirPaginated() {
		return modeloImprimirPaginated;
	}

	public void setModeloImprimirPaginated(ModelPagination modeloImprimirPaginated) {
		this.modeloImprimirPaginated = modeloImprimirPaginated;
	}

	public ParametrosPegatinaMatriculacion getEtiquetaParametros() {
		return etiquetaParametros;
	}

	public void setEtiquetaParametros(ParametrosPegatinaMatriculacion etiquetaParametros) {
		this.etiquetaParametros = etiquetaParametros;
	}

	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}

	public void setResultBeanImprimir(ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}

	public Boolean getElectronica() {
		return electronica;
	}

	public void setElectronica(Boolean electronica) {
		this.electronica = electronica;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getListaChecksConsultaTramite() {
		return listaChecksConsultaTramite;
	}

	public void setListaChecksConsultaTramite(String listaChecksConsultaTramite) {
		this.listaChecksConsultaTramite = listaChecksConsultaTramite;
	}

	public ImprimirTramiteTraficoDto getImprimirTramiteTraficoDto() {
		return imprimirTramiteTraficoDto;
	}

	public void setImprimirTramiteTraficoDto(ImprimirTramiteTraficoDto imprimirTramiteTraficoDto) {
		this.imprimirTramiteTraficoDto = imprimirTramiteTraficoDto;
	}

	public String getImpreso() {
		return impreso;
	}

	public void setImpreso(String impreso) {
		this.impreso = impreso;
	}

	public String getTipoRepresentacion() {
		return tipoRepresentacion;
	}

	public void setTipoRepresentacion(String tipoRepresentacion) {
		this.tipoRepresentacion = tipoRepresentacion;
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