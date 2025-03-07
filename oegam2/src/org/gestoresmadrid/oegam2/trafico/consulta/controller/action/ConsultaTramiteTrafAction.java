package org.gestoresmadrid.oegam2.trafico.consulta.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.ficheroAEAT.bean.FicheroAEATBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2.utiles.UtilesVistaJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.ServicioConsultaTramiteTrafico;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.ConsultaTramiteTraficoFilterBean;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.ResumenConsultaTramiteTraficoBean;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans.ResultadoFicheroSolicitud05Bean;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFicheroAEAT;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResumenPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioCheckCTIT;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoCambioServicio;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultCambioEstadoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamBajas.service.ServicioTramiteBaja;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResumenDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;
import escrituras.modelo.ModeloPersona;
import exportarXML.ErrorExportacionXML;
import hibernate.entities.general.Colegiado;
import oegam.constantes.ConstantesPQ;
import procesos.daos.BeanPQCrearSolicitud;
import trafico.beans.ResumenAsignacionMasivaTasas;
import trafico.beans.ResumenCambiosEstado;
import trafico.beans.ResumenCambiosNive;
import trafico.beans.ResumenCertificadoTasas;
import trafico.beans.ResumenConsultaTarjetaEitv;
import trafico.beans.ResumenEEFF;
import trafico.beans.ResumenErroresFicheroAEAT;
import trafico.beans.ResumenErroresFicheroMOVE;
import trafico.beans.ResumenFicheroSolicitud05;
import trafico.beans.ResumenOperacionesTramiteBean;
import trafico.beans.ResumenPendienteExcel;
import trafico.beans.ResumenPresentacion576;
import trafico.beans.ResumenSolicitudNRE06;
import trafico.beans.ResumenTramitacionTelematica;
import trafico.beans.ResumenValidacion;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCAMBIAR_ESTADO_S_V;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloDuplicado;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.UtilesConversionesTrafico;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.UtilidadesAccionTrafico;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.ValidacionJustificantePorFechaExcepcion;
import utilidades.web.excepciones.ValidacionJustificanteRepetidoExcepcion;

public class ConsultaTramiteTrafAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 4477720461404163505L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaTramiteTrafAction.class);

	private static final String POP_UP_ESTADOS = "popUpCambioEstado";
	private static final String POP_UP_MATRICULA = "popUpMatriculaManual";
	private static final String POP_UP_BORRAR_DATOS = "popUpBorrarDatos";
	private static final String POP_UP_DUPLICAR_TRAMITE = "popUpDuplicarTramite";
	private static final String DESCARGAR = "descargarFichero";
	private static final String DESCARGAR_ZIP = "descargarZip";
	private static final String IMPRESION_ACTION = "aImpresionAction";
	private static final String IMPRIMIR_ACTION = "aImprimirAction";
	private static final String MOSTRAR_JUSTIFICANTE = "mostrarDocumentoJustificante";
	private static final String CLONAR_ACTION = "aClonarAction";
	private static final String DESCARGAR_XML = "descargarXMLTrafico";
	private static final String DEBE_ESTAR_VALIDADO_TELEMATICAMENTE_Y_LIBERADO = "Debe estar validado telemáticamente y liberado.";

	private ConsultaTramiteTraficoFilterBean consultaTramiteBean;

	private String matricula;
	private String fechaMatriculacion;

	private String numExpediente;
	private String numsExpediente;
	private String propTexto;
	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	private String listaChecksConsultaTramite;
	private String codSeleccionados;
	private String tipoTramiteSeleccionado;
	private String estadoTramiteSeleccionado;
	private String bastidorSeleccionado;
	private String desasignarTasaAlDuplicar;

	private Boolean resumenValidacion = false;
	private Boolean resumenTramitacion = false;
	private Boolean resumenCertificadoTasasFlag = false;
	private Boolean resumenPendienteEnvioExcel = false;
	private Boolean resumenPresentacion576Flag = false;
	private Boolean resumenAsignacionMasivaTasasFlag = false;
	private Boolean resumenConsultaTarjetaEitvFlag = false;

	private Boolean impresoEspera = false;
	private boolean cierraZip = false;

	private boolean volverAntiguaConsulta;

	private ErrorExportacionXML errorExportacionXML;
	private List<ErrorExportacionXML> listaErroresExportacionXML = new ArrayList<>();
	private int totalErroresXML = 0;

	private List<ResumenValidacion> resumen = new ArrayList<>();
	private List<ResumenTramitacionTelematica> resumenTramitacionTelematica = new ArrayList<>();
	private List<ResumenPendienteExcel> resumenPendientes = new ArrayList<>();
	private List<ResumenAsignacionMasivaTasas> resumenAsignacionMasivaTasas = new ArrayList<>();
	private List<ResumenConsultaTarjetaEitv> resumenConsultaTarjetaEitv = new ArrayList<>();
	private List<String> mensajesValidadoPDF = new ArrayList<>();
	private List<String> mensajesValidadoTelematicamente = new ArrayList<>();
	private List<ResumenPresentacion576> resumenPresentacion576 = new ArrayList<>();
	private ResumenCertificadoTasas resumenCertificadoTasas = new ResumenCertificadoTasas();
	private ResumenDocBaseBean resumenDocBase;
	private ResumenAsignacionMasivaTasas resumenAsignacionMasivaTasasT = new ResumenAsignacionMasivaTasas("TOTAL");
	private ResumenPresentacion576 resumenPresentacion576T = new ResumenPresentacion576("TOTAL");
	private ResumenConsultaTarjetaEitv resumenConsultaTarjetaEitvT = new ResumenConsultaTarjetaEitv("TOTAL");

	// EEFF
	private Boolean resumenEEFFFlag = false;
	private ResumenEEFF resumenEEFF = new ResumenEEFF();
	// Dtos
	private TramiteTrafCambioServicioDto tramiteTraficoCambServ;

	// Beans
	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;
	private SolicitudDatosBean solicitud;
	private DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean;

	private ResultValidarTramitesImprimir resultBeanImprimir;

	// Modelos
	private ModeloTransmision modeloTransmision;
	private ModeloSolicitudDatos modeloSolicitudDatos;
	private ModeloTrafico modeloTrafico;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;
	private ModeloDuplicado modeloDuplicado;
	private ModeloMatriculacion modeloMatriculacion;
	private ModeloSolicitud modeloSolicitud;

	private Boolean existeZip = false;
	private String ficheroDescarga;
	private Persona personaFac;
	private PaginatedList listaAcciones;
	private ResumenConsultaTramiteTraficoBean resumenConsultaTramiteTrafico;

	// Cambios Estado
	private Boolean resumenCambiosEstadoFlag = false;
	private List<ResumenCambiosEstado> resumenCambiosEstado = new ArrayList<>();
	private ResumenCambiosEstado resumenCambiosEstadoT = new ResumenCambiosEstado("TOTAL");
	private String estadoNuevo;

	// Liberación Doc Base NIVE
	private Boolean resumenCambiosFlag = false;
	private List<ResumenCambiosNive> resumenCambiosNive = new ArrayList<>();
	private ResumenCambiosNive resumenCambiosNiveT = new ResumenCambiosNive("TOTAL");

	private Boolean resumenDocumentosGeneradosFlag = false;
	private Boolean resumenPermDstv = false;
	private Boolean resumenAutoBTV = false;
	private Boolean resumenFicheroMOVE = false;
	private ResumenOperacionesTramiteBean resumenOpeTram;

	// Ficheros AEAT
	private Boolean resumenLiquidacionNRE06 = false;
	private Boolean resumenFicheroAEAT = false;

	private List<FicheroAEATBean> listaFicherosAEATBean = new ArrayList<>();
	private List<ResumenErroresFicheroAEAT> listaResumenErroresFicheroAEAT = new ArrayList<>();

	private List<ResumenErroresFicheroMOVE> listaResumenErroresFicheroMOVE = new ArrayList<>();

	// Fichero 05
	private Boolean resumenFicheroSolicitud05Flag = false;
	private ResumenFicheroSolicitud05 resumenFicheroSolicitud05 = new ResumenFicheroSolicitud05();

	// SOLICITUD NRE06
	private Boolean resumenSolicitudNRE06Flag = false;
	private ResumenSolicitudNRE06 resumenSolicitudNRE06 = new ResumenSolicitudNRE06();

	// Lista de Expedientes para mostrar en el Fichero de AEAT
	private Boolean imprimirFichero = false;

	private ResumenPermisoDistintivoItvBean resum;

	@Resource
	private ModelPagination modeloConsultaTramiteTrafPaginated;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioConsultaTramiteTrafico servicioConsultaTramiteTrafico;

	@Autowired
	private ServicioTramiteBaja servicioTramiteBaja;

	@Autowired
	private ServicioTramiteTraficoDuplicado servicioTramiteDuplicado;

	@Autowired
	private ServicioTramiteTraficoCambioServicio servicioTramiteTraficoCambioServicio;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioCheckCTIT servicioCheckCtit;

	@Autowired
	private ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	private ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	private ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	private ServicioEEFFNuevo servicioEEFF;

	@Autowired
	private ServicioVehiculo servicioVehiculoImpl;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioFicheroAEAT servicioFicheroAEAT;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private Conversor conversor;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public String inicio() {
		cargarFiltroInicial();
		return SUCCESS;
	}

	/**
	 * Función que buscará el tipo de detalle que es, accederá a base de datos y obtendrá los datos correspondientes dependiendo del tipo de trámite que sea.
	 * @return
	 * @throws ParseException
	 */
	public String detalle() {
		String irADetalle = SUCCESS;
		try {
			propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;

			if (numExpediente != null) {
				String tipoTramite = servicioTramiteTrafico.getTipoTramite(new BigDecimal(numExpediente));
				if (StringUtils.isNotBlank(tipoTramite)) {
					String ocultarSolicitudes = gestorPropiedades.valorPropertie("ocultar.solicitudes.consulta.tramite");

					if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
						irADetalle = "altaTramiteMatriculacion";
					} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
						Map<String, Object> resultadoMetodo = getModeloTransmision().obtenerDetalle(new BigDecimal(numExpediente));
						tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						cargarOtrosDatos();
						irADetalle = "altasTramiteTransmisionActual";
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
						Map<String, Object> resultadoMetodo = getModeloTransmision().obtenerDetalle(new BigDecimal(numExpediente));
						tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						Integer intProvincia = Integer.parseInt(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia());
						if (intProvincia < 10 && intProvincia > 0) {
							tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().setIdProvincia("0" + intProvincia);
						}
						docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(new BigDecimal(numExpediente));
						cargarOtrosDatos();
						irADetalle = "altasTramiteTransmision";
					} else if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
						irADetalle = "altaTramiteBaja";
					} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
						irADetalle = "altaTramiteDuplicado";
					} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tipoTramite) && "NO".equals(ocultarSolicitudes)) {
						solicitud = new SolicitudDatosBean(true);
						Map<String, Object> resultadoMetodo = getModeloSolicitudDatos().obtenerDetalle(new BigDecimal(numExpediente), utilesColegiado.getNumColegiadoSession(), utilesColegiado
								.getIdContratoSessionBigDecimal());
						solicitud = (SolicitudDatosBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						getSession().put("listaSolicitud", solicitud.getSolicitudesVehiculos());
						try {
							existeZip = existeFicheroAVPO(new BigDecimal(numExpediente));
						} catch (OegamExcepcion e) {
							log.error("Error al recuperar el ZIP de AVPO", e);
						}
						cargarOtrosDatos();
						irADetalle = "altaSolicitud";
					} else if (TipoTramiteTrafico.CambioServicio.getValorEnum().equals(tipoTramite)) {
						tramiteTraficoCambServ = new TramiteTrafCambioServicioDto();
						tramiteTraficoCambServ.setNumExpediente(new BigDecimal(numExpediente));
						irADetalle = "altaTramiteCambioServicio";
					}
				} else {
					addActionError("Este trámite no tiene asignado ningún tipo.");
					log.debug("Este trámite no tiene asignado ningún tipo");
				}
			} else {
				addActionError("No se ha seleccionado ningún número de expediente.");
			}
			return irADetalle;
		} catch (Throwable ex) {
			addActionError("Se ha producido un error al obtener el detalle del tramite");
			log.error("Error al obtener el detalle del trámite.");
		}
		return SUCCESS;
	}

	public String validar() throws ParseException {
		ResultBean resultadoValidacion = new ResultBean();
		ResumenValidacion resumenMatriculacion = new ResumenValidacion(TipoTramiteTrafico.Matriculacion.getNombreEnum());
		ResumenValidacion resumenTransmision = new ResumenValidacion(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenValidacion resumenBaja = new ResumenValidacion(TipoTramiteTrafico.Baja.getNombreEnum());
		ResumenValidacion resumenSolicitud = new ResumenValidacion(TipoTramiteTrafico.Solicitud.getNombreEnum());
		ResumenValidacion resumenDuplicado = new ResumenValidacion(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenValidacion resumenCambioServicio = new ResumenValidacion(TipoTramiteTrafico.CambioServicio.getNombreEnum());
		ResumenValidacion resumenTotal = new ResumenValidacion("TOTAL");

		try {
			String[] codSeleccionados = listaChecksConsultaTramite.replaceAll(" ", "").split(",");

			String numeroMaxValidar = gestorPropiedades.valorPropertie("maximo.numero.validar");
			if (numeroMaxValidar != null && codSeleccionados != null && codSeleccionados.length > Integer.parseInt(numeroMaxValidar)) {
				addActionError("No puede validar más de " + numeroMaxValidar + " trámites.");
				return actualizarPaginatedList();
			}

			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return actualizarPaginatedList();
			}

			for (int i = 0; i < codSeleccionados.length; i++) {
				String tipoTramite = servicioTramiteTrafico.getTipoTramite(new BigDecimal(codSeleccionados[i]));
				if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
					ResultadoBajasBean resultado = servicioTramiteBaja.validarTramite(new BigDecimal(codSeleccionados[i]), utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.getError()) {
						addActionError("Trámite " + codSeleccionados[i] + " fallo en la validación: " + resultado.getMensaje());
						resumenBaja.addFallido();
					} else {
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
						if (Long.parseLong(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()) == resultado.getEstadoNuevo()) {
							mensajeValidado += "Telemáticamente.";
							mensajesValidadoTelematicamente.add(mensajeValidado);
							resumenBaja.addValidadoTelematicamente();
						} else {
							resumenBaja.addValidadoPDF();
							mensajesValidadoPDF.add(mensajeValidado);
							mensajesValidadoPDF.add(resultado.getMensaje());
						}
						addActionMessage(mensajeValidado);
					}
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
					TramiteTrafDuplicadoDto tramiteTrafico = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(codSeleccionados[i]), true);
					ResultBean resultado = servicioTramiteTraficoDuplicado.validarTramite(tramiteTrafico);
					if (resultado.getError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
						for (String error : resultado.getListaMensajes()) {
							mensajeError += "- " + error;
						}
						addActionError(mensajeError);
						resumenDuplicado.addFallido();
					} else {
						resumenDuplicado.addValidadoPDF();
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
						mensajesValidadoPDF.add(mensajeValidado);
						addActionMessage(mensajeValidado);
					}
				} else if (TipoTramiteTrafico.CambioServicio.getValorEnum().equals(tipoTramite)) {
					TramiteTrafCambioServicioDto tramiteTrafico = servicioTramiteTraficoCambioServicio.getTramiteCambServ(new BigDecimal(codSeleccionados[i]), true);
					ResultadoCambioServicioBean resultado = servicioTramiteTraficoCambioServicio.validarTramite(tramiteTrafico, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.isError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación:";
						addActionError(mensajeError + resultado.getMensajeError());
						resumenCambioServicio.addFallido();
					} else {
						resumenCambioServicio.addValidadoPDF();
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
						mensajesValidadoPDF.add(mensajeValidado);
						addActionMessage(mensajeValidado);
					}
				} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					TramiteTrafMatrDto tramiteTrafico = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(codSeleccionados[i]), false, true);
					ResultBean resultado = servicioTramiteTraficoMatriculacion.validarTramite(tramiteTrafico);
					if (resultado.getError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
						for (String error : resultado.getListaMensajes()) {
							mensajeError += "- " + error;
						}
						addActionError(mensajeError);
						resumenMatriculacion.addFallido();
					} else {
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";

						Long estado = (Long) resultado.getAttachment(ServicioTramiteTraficoMatriculacion.ESTADO_VALIDAR);

						if (Long.valueOf(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(estado)) {
							mensajeValidado += "Telemáticamente.";
							resumenMatriculacion.addValidadoTelematicamente();
							mensajesValidadoTelematicamente.add(mensajeValidado);
							if (resultado.getListaMensajes() != null) {
								validarCetInformado(resultado.getListaMensajes());
								validarVarios(resultado.getListaMensajes());
							}
						} else {
							mensajeValidado += "PDF. Limitaciones para la validación telemática: ";
							if (resultado.getListaMensajes() != null) {
								for (String error : resultado.getListaMensajes()) {
									if (!error.contains("AVISO") && !error.contains("Aviso")) {
										mensajeValidado += "- " + error;
									}
								}
							}
							resumenMatriculacion.addValidadoPDF();
							validarVarios(resultado.getListaMensajes());
							mensajesValidadoPDF.add(mensajeValidado);
						}
						addActionMessage(mensajeValidado);
					}
				} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
					TramiteTrafTranDto tramite = servicioTramiteTraficoTransmision.getTramiteTransmision(new BigDecimal(codSeleccionados[i]), true);
					ResultBean resultado = servicioTramiteTraficoTransmision.validarTramite(tramite, utilesColegiado.getIdUsuarioSession());
					if (resultado.getError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
						for (String error : resultado.getListaMensajes()) {
							mensajeError += "- " + error;
						}
						addActionError(mensajeError);
						resumenTransmision.addFallido();
					} else {
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";

						Long estado = (Long) resultado.getAttachment(ServicioTramiteTraficoTransmision.ESTADO_VALIDAR);

						if (Long.valueOf(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(estado)) {
							mensajeValidado += "Telemáticamente.";
							resumenTransmision.addValidadoTelematicamente();
							mensajesValidadoTelematicamente.add(mensajeValidado);
							if (resultado.getListaMensajes() != null) {
								validarCetInformado(resultado.getListaMensajes());
								validarVarios(resultado.getListaMensajes());
							}
						} else {
							mensajeValidado += "PDF. Limitaciones para la validación telemática: ";
							if (resultado.getListaMensajes() != null) {
								for (String error : resultado.getListaMensajes()) {
									if (!error.contains("AVISO") && !error.contains("Aviso")) {
										mensajeValidado += "- " + error;
									}
								}
							}
							resumenTransmision.addValidadoPDF();
							validarVarios(resultado.getListaMensajes());
							mensajesValidadoPDF.add(mensajeValidado);
						}
						addActionMessage(mensajeValidado);
					}
				} else {
					TramiteTraficoBean linea = getModeloTrafico().buscarTramitePorNumExpediente(codSeleccionados[i]);
					resultadoValidacion = new ResultBean();

					if (linea == null) {
						if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
							resumenTransmision.addFallido();
							resumen.add(resumenTransmision);
						} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(tipoTramite)) {
							resumenSolicitud.addFallido();
							resumen.add(resumenSolicitud);
						}
						resumenTotal.setNumFallidos(resumenMatriculacion.getNumFallidos() + resumenTransmision.getNumFallidos() + resumenSolicitud.getNumFallidos());
						resumenTotal.setNumValidadosPDF(resumenMatriculacion.getNumValidadosPDF() + resumenTransmision.getNumValidadosPDF() + resumenSolicitud.getNumValidadosPDF());
						resumenTotal.setNumValidadosTele(resumenMatriculacion.getNumValidadosTele() + resumenTransmision.getNumValidadosTele() + resumenSolicitud.getNumValidadosTele());
						resumen.add(resumenTotal);
						addActionError("No se ha encontrado el número de expediente");
						return SUCCESS;
					}

					ResultBean resultadoValidable = UtilesVistaTrafico.getInstance().tipoEstadoValidable(linea.getEstado(), linea.getTipoTramite());
					if (resultadoValidable.getError()) {
						resultadoValidacion.setError(true);
						resultadoValidacion.setMensaje("No validable: " + resultadoValidable.getMensaje());
					} else {
						resultadoValidacion = getModeloTrafico().validaTramiteGenerico(linea, utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
					}

					if (resultadoValidacion.getError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
						for (String error : resultadoValidacion.getListaMensajes()) {
							mensajeError += "- " + error;
						}
						addActionError(mensajeError);
						if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum())) {
							resumenTransmision.addFallido();
						} else if (TipoTramiteTrafico.Solicitud.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum())) {
							resumenSolicitud.addFallido();
						}
					} else {
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
						if (resultadoValidacion.getMensaje().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())) {
							mensajeValidado += "Telemáticamente.";
							if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum())) {
								resumenTransmision.addValidadoTelematicamente();
							} else if (TipoTramiteTrafico.Solicitud.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum())) {
								resumenSolicitud.addValidadoTelematicamente();
							}

							mensajesValidadoTelematicamente.add(mensajeValidado);

							for (int x = 0; x < resultadoValidacion.getListaMensajes().size(); x++) {
								if (resultadoValidacion.getListaMensajes().get(x).equalsIgnoreCase(ConstantesTrafico.LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA)) {
									mensajeValidado = ConstantesTrafico.LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA;
									mensajesValidadoTelematicamente.add(mensajeValidado);
								}
							}

							if (resultadoValidacion.getListaMensajes() != null) {
								for (String mensaje : resultadoValidacion.getListaMensajes()) {
									if (mensaje.contains(ConstantesTrafico.AVISO_MAXIMA_LONGITUD_DIRECCION_VEHICULO)) {
										addActionMessage("- " + mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									} else if (mensaje.contains(ConstantesTrafico.AVISO_VALIDACION_WMI)
											|| mensaje.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
										mensaje = mensaje.replace("--", "- ");
										addActionMessage(mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									} else if (mensaje.contains(ConstantesTrafico.AVISO_DISTINTIVO_MEDIOAMBIENTAL)) {
										mensaje = "Trámite " + codSeleccionados[i] + mensaje.replace("--", "- ");
										addActionMessage(mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									}
								}
							}
						} else { // Estado != Validado Telematicamente
							if (linea.getTipoTramite().getNombreEnum().equals(TipoTramiteTrafico.Transmision.getNombreEnum())) {
								mensajeValidado += "PDF.";
							} else {
								mensajeValidado += "PDF. Limitaciones para la validación telemática: ";
							}

							for (String error : resultadoValidacion.getListaMensajes()) {
								if (!error.contains("AVISO") && !error.contains("Aviso")) {
									mensajeValidado += "- " + error;
								}
							}
							if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum())) {
								resumenTransmision.addValidadoPDF();
							} else if (TipoTramiteTrafico.Solicitud.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum())) {
								resumenSolicitud.addValidadoPDF();
							}
							mensajesValidadoPDF.add(mensajeValidado);
							if (resultadoValidacion.getListaMensajes() != null) {
								for (String mensaje : resultadoValidacion.getListaMensajes()) {
									if (mensaje.contains(ConstantesTrafico.AVISO_MAXIMA_LONGITUD_DIRECCION_VEHICULO)) {
										addActionMessage("- " + mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									} else if (mensaje.contains(ConstantesTrafico.AVISO_VALIDACION_WMI)
											|| mensaje.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
										mensaje = mensaje.replace("--", "- ");
										addActionMessage(mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									}
								}
							}
						}
						addActionMessage(mensajeValidado);
					}
				}
			}

			resumenTotal.setNumFallidos(resumenMatriculacion.getNumFallidos() + resumenTransmision.getNumFallidos() + resumenBaja.getNumFallidos() + resumenSolicitud.getNumFallidos()
					+ resumenDuplicado.getNumFallidos() + resumenCambioServicio.getNumFallidos());
			resumenTotal.setNumValidadosPDF(resumenMatriculacion.getNumValidadosPDF() + resumenTransmision.getNumValidadosPDF() + resumenBaja.getNumValidadosPDF() + resumenSolicitud
					.getNumValidadosPDF() + resumenDuplicado.getNumValidadosPDF() + resumenCambioServicio.getNumValidadosPDF());
			resumenTotal.setNumValidadosTele(resumenMatriculacion.getNumValidadosTele() + resumenTransmision.getNumValidadosTele() + resumenBaja.getNumValidadosTele() + resumenSolicitud
					.getNumValidadosTele() + resumenDuplicado.getNumValidadosTele() + resumenCambioServicio.getNumValidadosTele());

			resumen.add(resumenMatriculacion);
			resumen.add(resumenTransmision);
			resumen.add(resumenBaja);
			resumen.add(resumenSolicitud);
			resumen.add(resumenDuplicado);
			resumen.add(resumenCambioServicio);
			resumen.add(resumenTotal);

			resumenValidacion = true;
		} catch (Throwable th) {
			log.error("Error en a la hora de validar los tramites:", th);
			addActionError("Ha sucedido un error al validar los tramites. ");
		}
		return actualizarPaginatedList();
	}

	public String cambiarEstado() {
		try {
			ResultCambioEstadoBean resultado = servicioConsultaTramiteTrafico.cambiarEstadoBloque(codSeleccionados, estadoNuevo, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				for (String mensajeOK : resultado.getListaOK()) {
					addActionMessage(mensajeOK);
				}
				for (String mensajeKAO : resultado.getListaErrores()) {
					addActionError(mensajeKAO);
				}

				resumenCambiosEstadoFlag = true;
				resumenCambiosEstadoT.setNumFallidos(resultado.getNumError());
				resumenCambiosEstadoT.setNumCambiosEstado(resultado.getNumOk());
				resumenCambiosEstado.add(resumenCambiosEstadoT);
			}
			// Trazeo detallado cambio de estado sin validaciones:
			logarCambioEstado(EstadoTramiteTrafico.convertir(estadoNuevo), resultado.getNumExpedientesOk().toArray());
			return actualizarPaginatedList();
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return SUCCESS;
		}
	}

	@SuppressWarnings("unchecked")
	public String cambiaEstadoIni() {
		ResultBean resultado = servicioConsultaTramiteTrafico.cambiarEstadoFinalizadoErrorBloque(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
		long lResultadoOk = (Long) resultado.getAttachment("lResultadosOk");
		long lResultadoError = (Long) resultado.getAttachment("lResultadosError");
		if (lResultadoError != 0) {
			List<String> listaMensajesError = (List<String>) resultado.getAttachment("listaMensajesError");
			for (String mensaje : listaMensajesError) {
				addActionError(mensaje);
			}
		}
		if (lResultadoOk != 0) {
			List<String> listaMensajesOK = (List<String>) resultado.getAttachment("listaMensajesOK");
			for (String mensaje : listaMensajesOK) {
				addActionMessage(mensaje);
			}
		}
		resumenCambiosEstadoFlag = true;
		resumenCambiosEstadoT.setNumFallidos((int) lResultadoError);
		resumenCambiosEstadoT.setNumCambiosEstado((int) lResultadoOk);
		resumenCambiosEstado.add(resumenCambiosEstadoT);

		return actualizarPaginatedList();
	}

	/**
	 * Trazas para registrar cambios de estado sin validar de forma detallada (por si...)
	 */
	public void logarCambioEstado(EstadoTramiteTrafico estado, Object[] codSeleccionados) {
		// log.error("INICIO - CAMBIO DE ESTADO SIN VALIDACIONES");
		log.error("El usuario: ID_USUARIO: " + utilesColegiado.getIdUsuarioSession() + "; NUM_COLEGIADO: " + utilesColegiado.getNumColegiadoSession() + "; NOMBRE_APELLIDOS: " + utilesColegiado.getApellidosNombreUsuario() + ";");
		String tramitesCambiados = "Ha cambiado los siguientes expedientes: ";
		for (int cont = 0; cont < codSeleccionados.length; cont++) {
			if (cont != codSeleccionados.length - 1) {
				tramitesCambiados += codSeleccionados[cont].toString() + ", ";
			} else {
				tramitesCambiados += codSeleccionados[cont].toString();
			}
		}
		tramitesCambiados += " a estado : " + estado.getNombreEnum();
		log.error(tramitesCambiados);
	}

	public String tramiteRevisado() {
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				String[] numsExp = codSeleccionados.split("_");
				for (String numExpediente : numsExp) {
					ResultBean respuesta = servicioTramiteTrafico.cambiarEstadoRevisado(new BigDecimal(numExpediente), utilesColegiado.getIdUsuarioSessionBigDecimal().longValue());
					if (respuesta != null && respuesta.getError()) {
						addActionError("El estado del expediente: " + numExpediente + " no se ha cambiado. " + respuesta.getMensaje());
						resumenCambiosEstadoT.addFallido();
						log.error("Error al cambiar de estado:" + respuesta.getMensaje());
					} else {
						resumenCambiosEstadoT.addCorrecto();
						addActionMessage("El estado del expediente: " + numExpediente + " se ha cambiado correctamente.");
					}
				}
				resumenCambiosEstadoFlag = true;
				resumenCambiosEstado.add(resumenCambiosEstadoT);
			}
		} catch (Exception e) {
			log.error("No se ha podido realizar los cambios de estado,error:", e);
			addActionError("No se ha podido realizar los cambios de estado.");
		}
		return actualizarPaginatedList();
	}

	@SuppressWarnings("unchecked")
	public String autorInfoBj() {
		try {
			String[] codigosSel = codSeleccionados.split("_");
			if (codigosSel != null) {
				ResultBean resultado = servicioConsultaTramiteTrafico.autorizarImpresionBTV(codigosSel, utilesColegiado.tienePermisoAdmin());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumenAutoBTV = Boolean.TRUE;
					resumenOpeTram = new ResumenOperacionesTramiteBean();
					List<String> listaErrores = (List<String>) resultado.getAttachment("lResultadosError");
					if (listaErrores != null && !listaErrores.isEmpty()) {
						resumenOpeTram.setListaErroneos(listaErrores);
						resumenOpeTram.setNumErroneos(listaErrores.size());
					}
					List<String> listaOks = (List<String>) resultado.getAttachment("lResultadosOk");
					if (listaOks != null && !listaOks.isEmpty()) {
						resumenOpeTram.setListaOks(listaOks);
						resumenOpeTram.setNumOks(listaOks.size());
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autorizar los trámites seleccionados, error: ", e);
			addActionError("Ha sucedido un error a la hora de autorizar los trámites seleccionados.");
		}
		return actualizarPaginatedList();
	}

	public String descargarPresentacion576() {
		try {
			String[] codigosSel = codSeleccionados.split("_");
			ResultadoMatriculacionBean resultado = servicioConsultaTramiteTrafico.descargarFicheros576(codigosSel);
			if (!resultado.getError()) {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getNombreFichero();
				if (resultado.getEsZip()) {
					servicioConsultaTramiteTrafico.borrarFichero(resultado.getFichero());
				}
				return DESCARGAR;
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (FileNotFoundException e) {
			log.error("No se ha podido descargar los ficheros seleccionados, error:", e);
			addActionError("No se ha podido descargar los ficheros seleccionados.");
		}
		return actualizarPaginatedList();
	}

	public String generarDistintivo() {
		try {
			ResultadoPermisoDistintivoItvBean resultado = servicioConsultaTramiteTrafico.generarDistintivos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
					.tienePermisoImpresionDstvGestor(), utilesColegiado.tienePermisoImpresionDstvGestor(), utilesColegiado.tienePermisoAdmin());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resum = resultado.getResumen();
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado, error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		return actualizarPaginatedList();
	}

	public String actualizarMatManual() {
		ResultBean resultBean = new ResultBean();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateMatriculacion = null;
		try {
			dateMatriculacion = formatter.parse(fechaMatriculacion);
			resultBean = servicioVehiculoImpl.actualizarMatricula(new BigDecimal(numExpediente), matricula, dateMatriculacion, servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal()));
		} catch (ParseException e) {
			resultBean.addError("Error en el formato de los datos de entrada");
			resultBean.setError(true);
		}
		if (resultBean != null && resultBean.getError()) {
			addActionError(resultBean.getMensaje());
		} else {
			addActionMessage("Se ha actualizado la matrícula.");
		}
		return actualizarPaginatedList();
	}

	@SuppressWarnings("unchecked")
	public String liberarDocBaseNive() {
		ResultBean resultado = servicioConsultaTramiteTrafico.liberarDocBaseNive(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
		long lResultadoOk = (Long) resultado.getAttachment("lResultadosOk");
		long lResultadoError = (Long) resultado.getAttachment("lResultadosError");
		if (lResultadoError != 0) {
			List<String> listaMensajesError = (List<String>) resultado.getAttachment("listaMensajesError");
			for (String mensaje : listaMensajesError) {
				addActionError(mensaje);
			}
		}
		if (lResultadoOk != 0) {
			List<String> listaMensajesOK = (List<String>) resultado.getAttachment("listaMensajesOK");
			for (String mensaje : listaMensajesOK) {
				addActionMessage(mensaje);
			}
		}
		resumenCambiosFlag = Boolean.TRUE;
		resumenCambiosNiveT.setNumFallidos((int) lResultadoError);
		resumenCambiosNiveT.setNumCambiosNive((int) lResultadoOk);
		resumenCambiosNive.add(resumenCambiosNiveT);
		return actualizarPaginatedList();
	}

	public String generarLiquidacionNRE06() throws IOException {
		String fileName = "LiquidacionNRE06";
		resumenLiquidacionNRE06 = true;
		byte[] bytesSalida = null;
		String mensajeError = "";
		String[] codSeleccionados = listaChecksConsultaTramite.replaceAll(" ", "").split("-");

		TramiteTrafMatrDto tramite = null;
		File tempFile = File.createTempFile(Long.toString(System.currentTimeMillis()), ".zip");
		tempFile.deleteOnExit();

		FileOutputStream out = new FileOutputStream(tempFile);
		ZipOutputStream zip = new ZipOutputStream(out);

		// Varios expedientes
		if (codSeleccionados.length > 1) {

			for (int i = 0; i < codSeleccionados.length; i++) {
				// Dto que contiene los datos del tramite de matriculacion
				BigDecimal numExp = new BigDecimal(codSeleccionados[i]);
				try {
					tramite = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExp, utilesColegiado.tienePermisoLiberarEEFF(), true);

					if (tramite == null) {
						addActionError("Debe seleccionar un trámite de Matriculación");
						return actualizarPaginatedList();
					}
					if (tramite != null && !getModeloMatriculacion().validarEstadoTramite(tramite)) {
						addActionError("El trámite debe estar en Estado Finalizado");
						return actualizarPaginatedList();
					}
					if (tramite != null && tramite.getVehiculoDto().getCodigoMarca() != null) {
						MarcaDgtVO marca = servicioTramiteTrafico.recuperarMarcaConFabricantes(tramite.getVehiculoDto().getCodigoMarca());
						if (marca != null) {
							tramite.getVehiculoDto().setMarcaSinEditar(marca.getMarcaSinEditar());
						}
					}
				} catch (OegamExcepcion e) {
					e.printStackTrace();
				}

				if (tramite != null) {
					ResultBean resultado = getModeloMatriculacion().validarLiquidacionNRE06(tramite);
					if (resultado.getError()) {
						for (String error : resultado.getListaMensajes()) {
							mensajeError += " " + error;
						}
						addActionError(mensajeError);
						return actualizarPaginatedList();
					}
				}
				// Se empaquetan los datos en un zip
				bytesSalida = servicioFicheroAEAT.generarFicheroLiquidacionNRE06(tramite);
				File fileAEAT = File.createTempFile(fileName + "_" + numExp, ".txt");
				FileUtils.writeByteArrayToFile(fileAEAT, bytesSalida);

				FileInputStream fileInput = new FileInputStream(fileAEAT);
				ZipEntry zipEntry = new ZipEntry(fileAEAT.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = fileInput.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				fileInput.close();

			}
			zip.close();
			// Se prepara el zip para su descarga
			FileInputStream fis = new FileInputStream(tempFile);
			setInputStream(fis);
			setFileName("LiquidacionNRE06" + ConstantesPDF.EXTENSION_ZIP);
			imprimirFichero = true;
			return DESCARGAR_ZIP;

		} else {
			// Solo un expediente
			try {
				BigDecimal numExp = new BigDecimal(codSeleccionados[0]);
				tramite = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExp, utilesColegiado.tienePermisoLiberarEEFF(), true);

				if (tramite == null) {
					addActionError("Debe tener seleccionado un trámite de Matriculación");
					return actualizarPaginatedList();
				}
				if (tramite != null && !getModeloMatriculacion().validarEstadoTramite(tramite)) {
					addActionError("El trámite debe estar en Estado Finalizado");
					return actualizarPaginatedList();
				}
				if (tramite != null && tramite.getVehiculoDto().getCodigoMarca() != null) {
					MarcaDgtVO marca = servicioTramiteTrafico.recuperarMarcaConFabricantes(tramite.getVehiculoDto().getCodigoMarca());
					if (marca != null) {
						tramite.getVehiculoDto().setMarcaSinEditar(marca.getMarcaSinEditar());
					}
				}
			} catch (OegamExcepcion e) {
				e.printStackTrace();
			}
			if (tramite != null) {
				ResultBean resultado = getModeloMatriculacion().validarLiquidacionNRE06(tramite);
				if (resultado.getError()) {
					for (String error : resultado.getListaMensajes()) {
						mensajeError += "- " + error;
					}
					addActionError(mensajeError);
					return actualizarPaginatedList();
				}
			} // Se genera el fichero para descargar
			bytesSalida = servicioFicheroAEAT.generarFicheroLiquidacionNRE06(tramite);
			if (null != bytesSalida) {
				imprimirFichero = true;
				getSession().put(ConstantesTrafico.FICHEROAEAT, bytesSalida);
				getSession().put(ConstantesTrafico.NOMBREAEAT, fileName);
			}
		}
		return actualizarPaginatedList();
	}

	public String generarFicheroSolicitud05() throws IOException {
		try {
			ResultadoFicheroSolicitud05Bean resultado = servicioConsultaTramiteTrafico.generarFicheroSolicitud05DesdeConsultaTramite(codSeleccionados, utilesColegiado.getIdContratoSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensajeError());
			} else {
				resumenFicheroSolicitud05.setNumFallidos(resultado.getNumError());
				resumenFicheroSolicitud05.setNumOk(resultado.getNumOk());
				resumenFicheroSolicitud05.setListaMensajesOk(resultado.getListaOk());
				resumenFicheroSolicitud05.setListaMensajesError(resultado.getListaError());
				resumenFicheroSolicitud05Flag = true;
				if (resultado.getNombreFichero() != null && !resultado.getNombreFichero().isEmpty()) {
					resumenFicheroSolicitud05.setNombreFichero(resultado.getNombreFichero());
					resumenFicheroSolicitud05.setExisteFichero(Boolean.TRUE);
				}
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		} catch (Throwable th) {
			log.error("ERROR: ", th);
			addActionError(th.toString());
		}
		return actualizarPaginatedList();
	}

	public String clonar() throws OegamExcepcion {
		if (listaChecksConsultaTramite != null && !listaChecksConsultaTramite.isEmpty()) {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			ResultBean result = servicioConsultaTramiteTrafico.comprobarPrevioAClonarTramites(codSeleccionados);
			if (!result.getError()) {
				return CLONAR_ACTION;
			}
			addActionError(result.getListaMensajes().get(0));
		}
		return actualizarPaginatedList();
	}

	public String generarFicheroAEAT() {
		String fileName = "";
		resumenFicheroAEAT = true;
		byte[] bytesSalida = null;
		String[] codSeleccionados = listaChecksConsultaTramite.replaceAll(" ", "").split(",");
		if (!listaResumenErroresFicheroAEAT.isEmpty()) {
			listaResumenErroresFicheroAEAT.clear();
		}

		listaFicherosAEATBean = servicioFicheroAEAT.obtenerDatos(codSeleccionados, listaResumenErroresFicheroAEAT);

		if (!listaFicherosAEATBean.isEmpty()) {
			if (listaFicherosAEATBean.size() == 1) {
				fileName = listaFicherosAEATBean.get(0).getTramiteTraficoVO().getNumExpediente().toString();
			} else {
				fileName = ConstantesTrafico.FICHEROAEAT;
			}
			bytesSalida = servicioFicheroAEAT.generarFichero(listaFicherosAEATBean);
		}

		if (null != bytesSalida) {
			imprimirFichero = true;
			getSession().put(ConstantesTrafico.FICHEROAEAT, bytesSalida);
			getSession().put(ConstantesTrafico.NOMBREAEAT, fileName);
		}

		return actualizarPaginatedList();
	}

	public String generarFicheroMOVE() {
		String fileName = "";
		resumenFicheroMOVE = true;
		byte[] bytesSalida = null;

		if (!listaResumenErroresFicheroMOVE.isEmpty()) {
			listaResumenErroresFicheroMOVE.clear();
		}

		ResultadoFicheroSolicitud05Bean resultado = servicioConsultaTramiteTrafico.generarFicheroMOVE(listaChecksConsultaTramite, listaResumenErroresFicheroMOVE);
		if(!StringUtils.isBlank(resultado.getsFichero())) {
			fileName = resultado.getNombreFichero();
			bytesSalida = resultado.getsFichero().getBytes();
		}

		if (null != bytesSalida) {
			imprimirFichero = true;
			getSession().put(ConstantesTrafico.FICHEROAEAT, bytesSalida);
			getSession().put(ConstantesTrafico.NOMBREAEAT, fileName);
		}

		return actualizarPaginatedList();
	}

	public String consultaLiberacion() {
		if (utilesColegiado.tienePermisoConsultaEEFF()) {
			ResultadoConsultaEEFF resultado = servicioConsultaTramiteTrafico.consultaEEFFBloque(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				resumenEEFF = new ResumenEEFF();
				resumenEEFF.setEsConsulta(Boolean.TRUE);
				resumenEEFF.setListaMensajesError(resultado.getListaErrores());
				resumenEEFF.setListaMensajesOk(resultado.getListaOK());
				resumenEEFF.setNumError(resultado.getNumError());
				resumenEEFF.setNumOK(resultado.getNumOk());
				resumenEEFFFlag = Boolean.TRUE;
			}
		} else {
			addActionError("No tiene permisos para consultas EEFF");
		}
		return actualizarPaginatedList();
	}

	public String comprobarBloque() throws Throwable {
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenBaja = new ResumenTramitacionTelematica(TipoTramiteTrafico.Baja.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");

		try {
			String[] codigosSel = codSeleccionados.split("_");

			String numeroMaxComprobar = gestorPropiedades.valorPropertie("maximo.numero.comprobar");
			if (numeroMaxComprobar != null && codigosSel != null && codigosSel.length > Integer.parseInt(numeroMaxComprobar)) {
				addActionError("No puede comprobar más de " + numeroMaxComprobar + " trámites.");
				return actualizarPaginatedList();
			}

			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				resumenOtros.addFallido();
				resumenTotal.addFallido();
				resumenTramitacionTelematica.add(resumenOtros);
				resumenTramitacionTelematica.add(resumenTotal);
				resumenTramitacion = true;
				return actualizarPaginatedList();
			}

			List<TramiteTraficoBean> beans = new ArrayList<TramiteTraficoBean>();
			for (int i = 0; i < codigosSel.length; i++) {
				beans.add(getModeloTrafico().buscarTramitePorNumExpediente(codigosSel[i]));
			}

			boolean validadosTodos = true;
			for (TramiteTraficoBean tramiteTraficoBean : beans) {
				if (tramiteTraficoBean == null) {
					validadosTodos = false;
				} else {
					validadosTodos = validadosTodos && (EstadoTramiteTrafico.Iniciado.equals(tramiteTraficoBean.getEstado()) || EstadoTramiteTrafico.LiberadoEEFF.equals(tramiteTraficoBean
							.getEstado())) && (TipoTramiteTrafico.TransmisionElectronica.equals(tramiteTraficoBean.getTipoTramite()) || TipoTramiteTrafico.Baja.equals(tramiteTraficoBean
									.getTipoTramite()) || TipoTramiteTrafico.Duplicado.equals(tramiteTraficoBean.getTipoTramite()));
				}
			}

			if (!validadosTodos) {
				addActionError("Todos los trámites deben estar iniciados y ser de transmisión, duplicado o bajas por traslado a otro país.");
				resumenOtros.addFallido();
				resumenTotal.addFallido();
				resumenTramitacionTelematica.add(resumenOtros);
				resumenTramitacionTelematica.add(resumenTotal);
				resumenTramitacion = true;
				return actualizarPaginatedList();
			}

			ResultBean resultadoDetalle = null;
			boolean esBaja = false;
			boolean esTransmision = false;
			for (int i = 0; i < beans.size(); i++) {
				if (TipoTramiteTrafico.TransmisionElectronica.equals(beans.get(i).getTipoTramite())) {
					esTransmision = true;
					if (esBaja) {
						addActionError("Todos los expedientes seleccionados deben de ser del mismo tipo.");
					}
					Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalleConDescripciones(beans.get(i).getNumExpediente());
					resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
					if (resultadoDetalle.getError()) {
						addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} else {
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
						ResultBean resultadoComprobar = new ResultBean();
						String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
						if("2631".equals(tramite.getTramiteTraficoBean().getNumColegiado()) && "SI".equals(gestionarConAM)) {
							resultadoComprobar = servicioCheckCtit.crearSolicitud(tramite.getTramiteTraficoBean().getNumExpediente(), null,
									utilesColegiado.getIdUsuarioSessionBigDecimal(), tramite.getTramiteTraficoBean().getIdContrato());
						} else {
							if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.checkCtit"))) {
								resultadoComprobar = servicioTramiteTraficoTransmision.comprobarTramitabilidadTramiteTransmisionSega(tramite, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
										.getIdContratoSessionBigDecimal());
							} else {
								resultadoComprobar = servicioTramiteTraficoTransmision.comprobarTramitabilidadTramiteTransmision(tramite, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
										.getIdContratoSessionBigDecimal());
							}
						}
						if (resultadoComprobar.getError() == false) {
							addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + resultadoComprobar.getMensaje());
							if (resultadoComprobar != null && resultadoComprobar.getListaMensajes() != null && !resultadoComprobar.getListaMensajes().isEmpty()) {
								for (String mensajeComprobar : resultadoComprobar.getListaMensajes()) {
									if (mensajeComprobar.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
										addActionMessage(mensajeComprobar);
									}
								}
							}
							resumenTransmision.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						} else {
							if (resultadoComprobar.getListaMensajes() != null) {
								for (String messageError : resultadoComprobar.getListaMensajes()) {
									if (messageError.contains(ConstantesTrafico.AVISO_NIF_PATRON_NO_VALIDO)) {
										addActionError("Tramite " + beans.get(i).getNumExpediente() + ": Error en la tramitacion telemática. " + ConstantesTrafico.MENSAJE_VALIDACION_PATRON_NIF);
									} else {
										addActionError("Tramite " + beans.get(i).getNumExpediente() + ": Error en la tramitacion telemática. " + messageError);
									}
								}
							}
							resumenTransmision.addFallido();
							resumenTotal.addFallido();
						}
					}
				} else if (TipoTramiteTrafico.Baja.equals(beans.get(i).getTipoTramite())) {
					ResultadoBajasBean resultado = servicioTramiteBaja.comprobarBtv(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.getError()) {
						resumenBaja.addFallido();
						resumenTotal.addFallido();
						addActionError(resultado.getMensaje());
					} else {
						resumenBaja.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
						addActionMessage("Expediente: " + beans.get(i).getNumExpediente() + ", pendiente Consulta Btv.");
					}
				} else if (TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
					ResultBean resultado = servicioTramiteDuplicado.comprobarDuplicado(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.getError()) {
						resumenBaja.addFallido();
						resumenTotal.addFallido();
						addActionError(resultado.getMensaje());
					} else {
						resumenBaja.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
						addActionMessage("Expediente: " + beans.get(i).getNumExpediente() + ", pendiente Check Duplicado.");
					}
				}
			}
			resumenTramitacion = true;
			if (esBaja) {
				resumenTramitacionTelematica.add(resumenBaja);
			} else if (esTransmision) {
				resumenTramitacionTelematica.add(resumenTransmision);
			}
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
		} catch (Throwable th) {
			log.error("Error en a la hora de comprobar los tramites:", th);
			addActionError("Ha sucedido un error al comprobar los tramites. ");
		}
		return actualizarPaginatedList();
	}

	public String duplicar() throws Throwable {
		try {
			String[] codigosSel = codSeleccionados.split("_");
			if (codigosSel != null) {
				ResultBean resultado = servicioConsultaTramiteTrafico.duplicarTramite(utiles.convertirStringArrayToBigDecimal(codigosSel), utilesColegiado.getIdUsuarioSessionBigDecimal(), getDesasignarTasaAlDuplicar());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage("El expediente: " + codigosSel[0] + " se ha iniciado correctamente a petición del colegiado");
					if (StringUtils.isNotBlank(resultado.getMensaje())) {
						addActionMessage(resultado.getMensaje() + " del expediente: " + codigosSel[0]);
					}
				}
			}
		} catch (Throwable th) {
			log.error("Error en a la hora de duplicar los tramites:", th);
			addActionError("Ha sucedido un error al duplicar los tramites. ");
		}
		return actualizarPaginatedList();
	}

	public String eliminar() throws ParseException {
		try {
			ResultCambioEstadoBean resultado = servicioConsultaTramiteTrafico.eliminarTramites(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				for (String mensajeOK : resultado.getListaOK()) {
					addActionMessage(mensajeOK);
				}
				for (String mensajeKAO : resultado.getListaErrores()) {
					addActionError(mensajeKAO);
				}
				resumenCambiosEstadoFlag = true;
				resumenCambiosEstadoT.setNumFallidos(resultado.getNumError());
				resumenCambiosEstadoT.setNumCambiosEstado(resultado.getNumOk());
				resumenCambiosEstado.add(resumenCambiosEstadoT);
			}
		} catch (Throwable th) {
			log.error("Error en a la hora de anular los tramites:", th);
			addActionError("Ha sucedido un error al anular los tramites. ");
		}
		return actualizarPaginatedList();
	}

	public String tramitarBloqueTelematicamente() throws Throwable {
		ResumenTramitacionTelematica resumenMatriculacion = new ResumenTramitacionTelematica(TipoTramiteTrafico.Matriculacion.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenBaja = new ResumenTramitacionTelematica(TipoTramiteTrafico.Baja.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");

		try {
			String[] codSeleccionados = listaChecksConsultaTramite.replaceAll(" ", "").split(",");
			String numeroMaxTramitar = gestorPropiedades.valorPropertie("maximo.numero.tramitar");
			if (numeroMaxTramitar != null && codSeleccionados != null && codSeleccionados.length > Integer.parseInt(numeroMaxTramitar)) {
				addActionError("No puede tramitar más de " + numeroMaxTramitar + " trámites.");
				return actualizarPaginatedList();
			}

			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				resumenOtros.addFallido();
				resumenTotal.addFallido();
				resumenTramitacionTelematica.add(resumenOtros);
				resumenTramitacionTelematica.add(resumenTotal);
				resumenTramitacion = true;
				return actualizarPaginatedList();
			}

			List<TramiteTraficoBean> beans = new ArrayList<>();
			for (int i = 0; i < codSeleccionados.length; i++) {
				TramiteTraficoBean tramite = getModeloTrafico().buscarTramitePorNumExpediente(codSeleccionados[i]);
				beans.add(tramite);
			}

			String tipoCreditoDescontar = validarTiposCreditosTransferencia(beans);
			if (tipoCreditoDescontar == null) {
				addActionError("Los trámites de transmision seleccionados no cobran el mismo tipo de Créditos. Deben serlo para realizar impresiones en bloque.");
				return actualizarPaginatedList();
			}

			for (TramiteTraficoBean tramiteTraficoBean : beans) {
				if (TipoTramiteTrafico.Baja.equals(tramiteTraficoBean.getTipoTramite())) {
					try {
						if (!validarEstados(tramiteTraficoBean)) {
							addActionError(DEBE_ESTAR_VALIDADO_TELEMATICAMENTE_Y_LIBERADO);
							resumenBaja.addFallido();
							resumenTotal.addFallido();
						} else {
							ResultadoBajasBean resultado = servicioTramiteBaja.tramitarBtv(tramiteTraficoBean.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
							if (resultado.getError()) {
								addActionError(resultado.getMensaje());
								resumenBaja.addFallido();
								resumenTotal.addFallido();
							} else {
								addActionMessage("Tramite " + tramiteTraficoBean.getNumExpediente() + ": " + "pendiente de respuesta de DGT.");
								resumenBaja.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							}
						}
					} catch (Throwable th) {
						log.error("Error en a la hora de tramitar los tramites:", th);
						addActionError("Ha sucedido un error al tramitar. ");
						resumenBaja.addFallido();
						resumenTotal.addFallido();
					}
				} else if (TipoTramiteTrafico.Matriculacion.equals(tramiteTraficoBean.getTipoTramite())) {
					try {
						if (!getModeloTrafico().isMatw(Long.valueOf(tramiteTraficoBean.getNumExpediente().toString()))) {
							addActionError("Sólo es posible tramitar telemáticamente un trámite de MATW.");
							resumenMatriculacion.addFallido();
							resumenTotal.addFallido();
						} else if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MATRICULACION_MATW_ALTAS)) {
							addActionError("No tiene permiso para tramitar este tipo de expediente.");
							resumenMatriculacion.addFallido();
							resumenTotal.addFallido();
						} else if (!validarEstados(tramiteTraficoBean)) {
							addActionError(DEBE_ESTAR_VALIDADO_TELEMATICAMENTE_Y_LIBERADO);
							resumenMatriculacion.addFallido();
							resumenTotal.addFallido();
						} else {
							ResultBean resultadoTramitar = servicioTramiteTraficoMatriculacion.tramitacion(tramiteTraficoBean.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
									utilesColegiado.getAlias(), utilesColegiado.getColegioDelContrato(), utilesColegiado.tienePermisoLiberarEEFF(), utilesColegiado.getIdContratoSessionBigDecimal());
							if (!resultadoTramitar.getError()) {
								addActionMessage("Tramite " + tramiteTraficoBean.getNumExpediente() + ": " + resultadoTramitar.getMensaje());
								resumenMatriculacion.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} else {
								addActionError("Tramite " + tramiteTraficoBean.getNumExpediente() + ": Error en la tramitacion telemática. ");
								if (resultadoTramitar.getListaMensajes() != null && !resultadoTramitar.getListaMensajes().isEmpty()) {
									for (String mensaje : resultadoTramitar.getListaMensajes()) {
										addActionError(mensaje);
									}
								}
								resumenMatriculacion.addFallido();
								resumenTotal.addFallido();
							}
						}
					} catch (Throwable th) {
						log.error("Error en a la hora de tramitar los tramites:", th);
						addActionError("Ha sucedido un error al tramitar. ");
						resumenMatriculacion.addFallido();
						resumenTotal.addFallido();
					}
				} else if (TipoTramiteTrafico.TransmisionElectronica.equals(tramiteTraficoBean.getTipoTramite())) {
					if (!validarEstados(tramiteTraficoBean)) {
						addActionError(DEBE_ESTAR_VALIDADO_TELEMATICAMENTE_Y_LIBERADO);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} else {
						try {
							Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(tramiteTraficoBean.getNumExpediente());
							ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
							TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
							if (resultadoDetalle.getError()) {
								addActionError("Error al obtener el trámite " + tramiteTraficoBean.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
								resumenTransmision.addFallido();
								resumenTotal.addFallido();
							} else {
								try {
									String[] numsExpedientes = new String[] { tramiteTraficoBean.getNumExpediente().toString() };
									List<String> listaExpedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(utiles.convertirStringArrayToBigDecimal(numsExpedientes),
											utilesColegiado.getIdUsuarioSessionBigDecimal());
									if (listaExpedientesSensibles != null && listaExpedientesSensibles.size() > 0) {
										for (int j = 0; j < listaExpedientesSensibles.size(); j++) {
											addActionError("Se ha recibido un error técnico. No intenten presentar el tramite " + listaExpedientesSensibles.get(j)
													+ ". Les rogamos comuniquen con el Colegio.");
											log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Datos sensibles en el trámite " + listaExpedientesSensibles.get(j));
										}
										resumenOtros.addFallido();
										resumenTotal.addFallido();
										continue;
									}
								} catch (Exception e) {
									log.error("Error al analizar los datos sensibles de transmision al tramitar", e);
								}
								ResultBean resultadoTramitar = getModeloTransmision().transmisionTelematicaMetodo(tramite, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
								if (resultadoTramitar.getError() == false) {
									addActionMessage("Tramite " + tramiteTraficoBean.getNumExpediente() + ": " + resultadoTramitar.getMensaje());
									resumenTransmision.addTramitadosTelematicamente();
									resumenTotal.addTramitadosTelematicamente();
								} else {
									String error = "Tramite " + tramiteTraficoBean.getNumExpediente() + ": Error en la tramitacion telemática. ";
									for (String mensaje : resultadoTramitar.getListaMensajes()) {
										error += "- " + mensaje;
									}
									addActionError(error);
									resumenTransmision.addFallido();
									resumenTotal.addFallido();
								}
							}
						} catch (Throwable th) {
							log.error("Error en a la hora de tramitar los tramites:", th);
							addActionError("Ha sucedido un error al tramitar. ");
							resumenTransmision.addFallido();
							resumenTotal.addFallido();
						}
					}
				} else if (TipoTramiteTrafico.Duplicado.equals(tramiteTraficoBean.getTipoTramite())) {
					try {
						if (!validarEstados(tramiteTraficoBean)) {
							addActionError(DEBE_ESTAR_VALIDADO_TELEMATICAMENTE_Y_LIBERADO);
							resumenBaja.addFallido();
							resumenTotal.addFallido();
						} else {
							ResultBean resultado = servicioTramiteDuplicado.tramitarDuplicado(tramiteTraficoBean.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
							if (resultado.getError()) {
								addActionError(resultado.getMensaje());
								resumenBaja.addFallido();
								resumenTotal.addFallido();
							} else {
								addActionMessage("Tramite " + tramiteTraficoBean.getNumExpediente() + ": " + "pendiente de respuesta de DGT.");
								resumenBaja.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							}
						}
					} catch (Throwable th) {
						log.error("Error en a la hora de tramitar los tramites:", th);
						addActionError("Ha sucedido un error al tramitar. ");
						resumenBaja.addFallido();
						resumenTotal.addFallido();
					}
				}else {
					addActionError("Para tramitar telemáticamente, el trámite debe ser de tipo Baja, Matriculación, o Transmisión Electrónica.");
					resumenOtros.addFallido();
					resumenTotal.addFallido();
				}
			}
		} catch (Throwable th) {
			log.error("Error en a la hora de tramitar los tramites:", th);
			addActionError("Ha sucedido un error al tramitar los trámites.");
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenMatriculacion);
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenBaja);

		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return actualizarPaginatedList();
	}

	public String generarJustificanteEnBloque() {
		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmisionTelematica = new ResumenTramitacionTelematica(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());

		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");
		try {
			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				resumenOtros.addFallido();
				resumenTotal.addFallido();
				resumenTramitacionTelematica.add(resumenOtros);
				resumenTramitacionTelematica.add(resumenTotal);
				resumenTramitacion = true;
				return actualizarPaginatedList();
			}

			if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES)) {
				addActionError("No tiene permiso para realizar un justificante.");
				return actualizarPaginatedList();
			}

			String[] codigosSel = codSeleccionados.split("_");
			if (codigosSel.length != 1) {
				addActionError("Sólamente se puede generar el justificante de 1 en 1.");
				return actualizarPaginatedList();
			}

			numExpediente = codigosSel[0];
			TramiteTraficoBean tramiteBean = getModeloTrafico().buscarTramitePorNumExpediente(numExpediente);
			TramiteTrafDto tramiteTrafico = conversor.transform(tramiteBean, TramiteTrafDto.class);
			if (!UtilesVistaJustificanteProfesional.getInstance().esGenerableJustificante(tramiteTrafico)) {
				addActionError("No puede obtener mas de un justificante a la vez por trámite.");
				return actualizarPaginatedList();
			}

			if (TipoTramiteTrafico.TransmisionElectronica.equals(tramiteBean.getTipoTramite())) {
				Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(tramiteBean.getNumExpediente());
				ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + tramiteBean.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmisionTelematica.addFallido();
					resumenTotal.addFallido();
				} else {
					try {
						getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionElectronica(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
								utilesColegiado.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
								ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
						addActionMessage("Tramite " + tramiteBean.getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
						resumenTransmisionTelematica.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
					} catch (ValidacionJustificanteRepetidoExcepcion e) {
						servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
						addActionError("Tramite " + tramiteBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
						resumenTransmision.addFallido();
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (ValidacionJustificantePorFechaExcepcion e) {
						servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
						addActionError("Tramite " + tramiteBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_POR_FECHA_EXCEPTION);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} catch (OegamExcepcion e) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generacion de Justificante. ";
						error += "- " + e.getMensajeError1();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} catch (SAXParseException spe) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generacion de Justificante. - " + getModeloJustificanteProfesional().parseSAXParseException(spe);
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						log.error("Error al generar el justificante", spe);
					} catch (Throwable e) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generacion de Justificante. ";
						error += "- " + e.getMessage();
						addActionError(error);
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					}
				}
			} else if (TipoTramiteTrafico.Transmision.equals(tramiteBean.getTipoTramite())) {
				Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(tramiteBean.getNumExpediente());
				ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + tramiteBean.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + tramiteBean.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmision.addFallido();
					resumenTotal.addFallido();
				} else {
					try {
						getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionActual(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
								.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
								ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
						addActionMessage("Tramite " + tramiteBean.getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
						resumenTransmision.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
					} catch (ValidacionJustificanteRepetidoExcepcion e) {
						servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
						addActionError("Tramite " + tramiteBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();

					} catch (ValidacionJustificantePorFechaExcepcion e) {
						servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
						addActionError("Tramite " + tramiteBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_POR_FECHA_EXCEPTION);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
						log.error("", e);
					} catch (OegamExcepcion e) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generacion de Justificante. ";
						error += "- " + e.getMensajeError1();
						addActionError(error);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
						log.error("", e);
					} catch (Throwable e) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generación de Justificante. ";
						error += "- " + e.getMessage();
						addActionError(error);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
						log.error("", e);
					}
				}
			} else if (TipoTramiteTrafico.Duplicado.equals(tramiteBean.getTipoTramite())) {
				HashMap<String, Object> tramiteDetalle = getModeloDuplicado().obtenerDetalle(tramiteBean.getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
				ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				TramiteTraficoDuplicadoBean tramite = (TramiteTraficoDuplicadoBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + tramiteBean.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + tramiteBean.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmision.addFallido();
					resumenTotal.addFallido();
				} else {
					try {
						getModeloJustificanteProfesional().validarYGenerarJustificanteTramiteDuplicados(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
								.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
								ConstantesTrafico.MOTIVO_DUPLICADO_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
						addActionMessage("Tramite " + tramiteBean.getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
						resumenTransmision.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
					} catch (ValidacionJustificanteRepetidoExcepcion e) {
						servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
						addActionError("Tramite " + tramiteBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
						resumenDuplicado.addFallido();
						resumenTotal.addFallido();
					} catch (ValidacionJustificantePorFechaExcepcion e) {
						servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteBean.getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
						addActionError("Tramite " + tramiteBean.getNumExpediente() + "; " + Constantes.JUSTIFICANTE_POR_FECHA_EXCEPTION);
						resumenDuplicado.addFallido();
						resumenTotal.addFallido();
					} catch (OegamExcepcion e) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generación de Justificante. ";
						error += "- " + e.getMensajeError1();
						addActionError(error);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} catch (Throwable e) {
						String error = "Tramite " + tramiteBean.getNumExpediente() + ": Error en la generación de Justificante. ";
						error += "- " + e.getMessage();
						addActionError(error);
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					}
				}
			} else if (!TipoTramiteTrafico.Baja.equals(tramiteBean.getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.equals(tramiteBean.getTipoTramite())
					&& !TipoTramiteTrafico.Transmision.equals(tramiteBean.getTipoTramite()) && !TipoTramiteTrafico.Duplicado.equals(tramiteBean.getTipoTramite())) {
				addActionError("Trámite de tipo " + tramiteBean.getTipoTramite().getNombreEnum() + " no tramitable telemáticamente actualmente");
				resumenOtros.addFallido();
				resumenTotal.addFallido();
			}
		} catch (Throwable th) {
			log.error("Error en a la hora de generar los justificantes profesionales:", th);
			addActionError("Ha sucedido un error al generar los justificantes profesionales. ");
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenDuplicado);
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenTransmisionTelematica);
		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return actualizarPaginatedList();
	}

	public String generarJustificanteProfesionalForzado() {
		ResumenTramitacionTelematica resumenTransmisionTelematica = new ResumenTramitacionTelematica(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");

		try {
			String[] codigosSel = codSeleccionados.split("_");

			List<TramiteTraficoBean> beans = new ArrayList<>();
			for (int i = 0; i < codigosSel.length; i++) {
				if (!servicioJustificanteProfesional.hayJustificante(null, new BigDecimal(codigosSel[i]), EstadoJustificante.Pendiente_autorizacion_colegio)) {
					addActionError("Error al forzar la generación del Justificante Profesional para el expediente: " + codigosSel[i] + ". "
							+ "Para forzar la generación del Justificante profesional este debe estar en estado " + "Pendiente autorización del colegio");
					log.error("Error al forzar la generación del Justificante Profesional: " + codigosSel[i] + ". "
							+ "Para forzar la generación del Justificante profesional este debe estar en estado " + "Pendiente autorización del colegio");
					resumenTransmisionTelematica.addFallido();
					resumenTotal.addFallido();
					continue;
				} else {
					beans.add(getModeloTrafico().buscarTramitePorNumExpediente(codigosSel[i]));
				}
			}

			for (TramiteTraficoBean tramiteTraf : beans) {
				BigDecimal idUsuario = null;
				BigDecimal idContrato = null;
				String numColegiado = null;
				String alias = null;
				String colegioDelContrato = null;

				Colegiado colegiado = utilesColegiado.getColegiado(tramiteTraf.getNumColegiado());
				idUsuario = new BigDecimal(colegiado.getUsuario().getIdUsuario());
				alias = colegiado.getAlias();
				idContrato = tramiteTraf.getIdContrato();
				numColegiado = tramiteTraf.getNumColegiado();

				ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
				if (contratoDto != null && contratoDto.getColegioDto() != null && StringUtils.isNotBlank(contratoDto.getColegioDto().getColegio())) {
					colegioDelContrato = contratoDto.getColegioDto().getColegio();
				} else {
					addActionError("Error al obtener el colegio del contrato que intentó crear el Justificante Profesional");
					log.error("Error al forzar la generación del Justificante Profesional para el expediente: "
							+ tramiteTraf.getNumExpediente());
					resumenTransmisionTelematica.addFallido();
					resumenTotal.addFallido();
					continue;
				}

				if (TipoTramiteTrafico.TransmisionElectronica.equals(tramiteTraf.getTipoTramite())) {
					Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(tramiteTraf.getNumExpediente());
					ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
					if (resultadoDetalle.getError()) {
						addActionError("Error al obtener el trámite " + tramiteTraf.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + tramiteTraf.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} else {
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
						try {
							getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.TransmisionElectronica, alias, tramite.getTramiteTraficoBean(), tramite
									.getAdquirienteBean().getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
									ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

							addActionMessage("Tramite " + tramiteTraf.getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
							resumenTransmisionTelematica.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						} catch (OegamExcepcion e) {
							String error = "Tramite " + tramiteTraf.getNumExpediente() + ": Error en la generación de Justificante. ";
							error += "- " + e.getMensajeError1();
							addActionError(error);
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						} catch (Throwable e) {
							String error = "Tramite " + tramiteTraf.getNumExpediente() + ": Error en la generación de Justificante. ";
							error += "- " + e.getMessage();
							addActionError(error);
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						}
					}
				} else if (TipoTramiteTrafico.Transmision.equals(tramiteTraf.getTipoTramite())) {
					Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(tramiteTraf.getNumExpediente());
					ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
					if (resultadoDetalle.getError()) {
						addActionError("Error al obtener el trámite " + tramiteTraf.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + tramiteTraf.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} else {
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
						try {
							getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.Transmision, alias, tramite.getTramiteTraficoBean(), tramite.getAdquirienteBean()
									.getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
									ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

							addActionMessage("Tramite " + tramiteTraf.getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
							resumenTransmisionTelematica.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						} catch (OegamExcepcion e) {
							String error = "Tramite " + tramiteTraf.getNumExpediente() + ": Error en la generación de Justificante. ";
							error += "- " + e.getMensajeError1();
							addActionError(error);
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						} catch (Throwable e) {
							String error = "Tramite " + tramiteTraf.getNumExpediente() + ": Error en la generación de Justificante. ";
							error += "- " + e.getMessage();
							addActionError(error);
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						}
					}
				} else if (TipoTramiteTrafico.Duplicado.equals(tramiteTraf.getTipoTramite())) {
					HashMap<String, Object> tramiteDetalle = getModeloDuplicado().obtenerDetalle(tramiteTraf.getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
					ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
					if (resultadoDetalle.getError()) {
						addActionError("Error al obtener el trámite " + tramiteTraf.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + tramiteTraf.getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} else {
						TramiteTraficoDuplicadoBean tramite = (TramiteTraficoDuplicadoBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
						try {
							getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.Duplicado, alias, tramite.getTramiteTrafico(), tramite.getTitular().getPersona(),
									idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado, ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO,
									ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

							addActionMessage("Tramite " + tramiteTraf.getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
							resumenTransmision.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						} catch (OegamExcepcion e) {
							String error = "Tramite " + tramiteTraf.getNumExpediente() + ": Error en la generacion de Justificante. ";
							error += " - " + e.getMensajeError1();
							addActionError(error);
							resumenDuplicado.addFallido();
							resumenTotal.addFallido();
						} catch (Throwable e) {
							String error = "Tramite " + tramiteTraf.getNumExpediente() + ": Error en la generacion de Justificante. ";
							error += " - " + e.getMessage();
							addActionError(error);
							resumenDuplicado.addFallido();
							resumenTotal.addFallido();
						}
					}
				} else if (!TipoTramiteTrafico.Baja.equals(tramiteTraf.getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.equals(tramiteTraf.getTipoTramite())
						&& !TipoTramiteTrafico.Transmision.equals(tramiteTraf.getTipoTramite()) && !TipoTramiteTrafico.Duplicado.equals(tramiteTraf.getTipoTramite())) {
					addActionError("Trámite de tipo " + tramiteTraf.getTipoTramite().getNombreEnum() + " no tramitable telemáticamente actualmente");
					resumenOtros.addFallido();
					resumenTotal.addFallido();
				}
			}
		} catch (Throwable th) {
			log.error("Error en a la hora de generar los justificantes profesionales forzados:", th);
			addActionError("Ha sucedido un error al generar los justificantes profesionales forzados. ");
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenDuplicado);
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenTransmisionTelematica);
		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return actualizarPaginatedList();
	}

	public String presentacion576() {
		int presentaciones = 0;
		try {
			String[] codigosSel = codSeleccionados.split("_");

			for (int cont = 0; cont < codigosSel.length; cont++) {
				TramiteTraficoBean linea = getModeloTrafico().buscarTramitePorNumExpediente(codigosSel[cont]);
				if (linea == null) {
					addActionError("No se ha encontrado el expediente de número: " + codigosSel[cont]);
					resumenPresentacion576T.addFallido();
					continue;
				}

				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(linea.getTipoTramite().getValorEnum())) {
					if (linea.getEstado() != EstadoTramiteTrafico.Iniciado) {
						addActionError("El expediente de nº " + linea.getNumExpediente() + " no se encuentra en estado 'Iniciado'");
						resumenPresentacion576T.addFallido();
						break;
					}
					ResultBean resultBean = crearSolicitud576(linea.getNumExpediente().toString(), EstadoTramiteTrafico.Iniciado.getValorEnum());
					if (!resultBean.getError()) {
						presentaciones++;
						String correcto = "En cola la presentación en la AEAT. El trámite " + linea.getNumExpediente().toString()
								+ " se actualizará con el CEM y pasará a 'Iniciado' si se realiza correctamente la presentación. "
								+ "Podrá descargar el PDF con la información de la presentación desde la página de impresión de documentos";
						addActionMessage(correcto);
					} else {
						addActionError("No se ha podido crear la solicitud de presentación del 576 para el expediente " + linea.getNumExpediente() + " " + "debido al siguiente error: " + resultBean
								.getMensaje());
						resumenPresentacion576T.addFallido();
					}
				} else {
					addActionError("El expediente " + linea.getNumExpediente() + " no es de matriculación");
					resumenPresentacion576T.addFallido();
				}
			}

		} catch (Throwable th) {
			log.error("Error en a la hora de presentar el 576:", th);
			addActionError("Error en a la hora de presentar el 576. ");
		}
		resumenPresentacion576Flag = true;

		resumenPresentacion576T.setNumFallidos(resumenPresentacion576T.getNumFallidos());
		resumenPresentacion576T.setNumTasasCertificado(presentaciones);

		resumenPresentacion576.add(resumenPresentacion576T);

		return actualizarPaginatedList();
	}

	public String imprimirJustificante() throws Throwable {
		String[] codigosSel = codSeleccionados.split("_");

		List<String> listaNumExpedientesSinJustificantes = servicioJustificanteProfesional.obtenerExpedientesSinJustificantesEnEstadoPorNumExpediente(codigosSel,
				org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante.Ok);
		if (!listaNumExpedientesSinJustificantes.isEmpty()) {
			addActionError("No se pueden imprimir los justificantes de los expedientes seleccionados. Los siguientes expedientes no tienen ningún justificante en estado 'recibido': " + utiles
					.transformListaToString(listaNumExpedientesSinJustificantes, "", "", ""));
			return actualizarPaginatedList();
		}
		ByteArrayInputStreamBean byteArrayInputStreamBean = servicioJustificanteProfesional.imprimirJustificantesPorNumExpediente(codigosSel);
		if (byteArrayInputStreamBean != null) {
			if (FileResultStatus.FILE_NOT_FOUND.equals(byteArrayInputStreamBean.getStatus())) {
				addActionError("No se han encontrado los ficheros para ninguno de los justificantes seleccionados.");
				return actualizarPaginatedList();
			}
			setInputStream(byteArrayInputStreamBean.getByteArrayInputStream());
			setFileName(byteArrayInputStreamBean.getFileName());
		}
		return MOSTRAR_JUSTIFICANTE;
	}

	public String impresion() throws Throwable {
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return actualizarPaginatedList();
		}

		String[] codigosSel = codSeleccionados.split("_");
		ResultValidarTramitesImprimir resultValidar = servicioImpresion.validarImpresion(codigosSel, utilesColegiado.tienePermisoAdmin());
		if (resultValidar.getError()) {
			List<String> errores = resultValidar.getListaMensajes();
			if (errores != null) {
				for (int i = 0; i < errores.size(); i++) {
					addActionError(errores.get(i));
				}
			}
			return actualizarPaginatedList();
		}
		setNumsExpediente(listaChecksConsultaTramite);
		setResultBeanImprimir(resultValidar);
		setVolverAntiguaConsulta(Boolean.FALSE);
		return IMPRESION_ACTION;
	}

	public String imprimir() throws Throwable {
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return actualizarPaginatedList();
		}
		String[] codigosSel = codSeleccionados.split("_");

		ResultValidarTramitesImprimir resultValidar = servicioImpresion.validarTramitesPrevioAPermitirImprimir(codigosSel, utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
		if (resultValidar.getError()) {
			List<String> errores = resultValidar.getListaMensajes();
			if (errores != null) {
				for (int i = 0; i < errores.size(); i++) {
					addActionError(errores.get(i));
				}
			}
			return actualizarPaginatedList();
		}
		setNumsExpediente(listaChecksConsultaTramite);
		setResultBeanImprimir(resultValidar);
		setVolverAntiguaConsulta(Boolean.FALSE);
		return IMPRIMIR_ACTION;
	}

	public String generarCertificados() {
		try {
			String[] codigosSel = codSeleccionados.split("_");
			ResultadoCertificadoTasasBean resultado = servicioConsultaTramiteTrafico.generarCertificadoDesdeConsultaTramite(codigosSel, utilesColegiado.getIdContratoSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensajeError());
			} else {
				resumenCertificadoTasas.setNumFallidos(resultado.getNumError());
				resumenCertificadoTasas.setNumTasasCertificado(resultado.getNumOk());
				resumenCertificadoTasas.setListaMensajesOk(resultado.getListaOk());
				resumenCertificadoTasas.setListaMensajesError(resultado.getListaError());
				resumenCertificadoTasasFlag = true;
				ficheroDescarga = resultado.getNombreFichero();
			}
		} catch (Throwable th) {
			log.error("ERROR al generar el certificado de tasas: ", th);
			addActionError("Error al generar el certificado de tasas");
		}
		return actualizarPaginatedList();
	}

	public String pendientesEnvioExcel() {
		ResumenPendienteExcel resumenBaja = new ResumenPendienteExcel(TipoTramiteTrafico.Baja.getNombreEnum());
		ResumenPendienteExcel resumenDuplicado = new ResumenPendienteExcel(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenPendienteExcel resumenCambioServicio = new ResumenPendienteExcel(TipoTramiteTrafico.CambioServicio.getNombreEnum());
		ResumenPendienteExcel resumenTotal = new ResumenPendienteExcel("TOTAL");

		try {
			String[] codSeleccionados = listaChecksConsultaTramite.replaceAll(" ", "").split(",");

			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return actualizarPaginatedList();
			}

			if (!utilesColegiado.esColegiadoEnvioExcel()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return actualizarPaginatedList();
			}

			for (String numExpediente : codSeleccionados) {
				String tipoTramite = servicioTramiteTrafico.getTipoTramite(new BigDecimal(numExpediente));
				if (TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite)) {
					ResultadoBajasBean resultado = servicioTramiteBaja.pendienteEnvioExcel(new BigDecimal(numExpediente), utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultado.getError()) {
						addActionError(resultado.getMensaje());
						resumenBaja.addFallido();
					} else {
						addActionMessage("Baja pendiente Excels: " + numExpediente);
						resumenBaja.addPendientesExcel();
					}
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
					TramiteTrafDuplicadoDto tramiteDto = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(numExpediente), true);
					ResultBean resultadoDuplicado = servicioTramiteTraficoDuplicado.pendientesEnvioExcel(tramiteDto, utilesColegiado.getIdUsuarioSessionBigDecimal());

					if (resultadoDuplicado.getError()) {
						resumenDuplicado.addFallido();
						addActionError("Ha fallado el duplicado con numero expediente: " + numExpediente);
						for (String mensaje : resultadoDuplicado.getListaMensajes()) {
							addActionError(mensaje);
						}
					} else {
						addActionMessage("Duplicado pendiente Excels: " + numExpediente);
						resumenDuplicado.addPendientesExcel();
					}
				} else if (TipoTramiteTrafico.CambioServicio.getValorEnum().equals(tipoTramite)) {
					TramiteTrafCambioServicioDto tramiteDto = servicioTramiteTraficoCambioServicio.getTramiteCambServ(new BigDecimal(numExpediente), true);
					ResultBean resultadoCambioServicio = servicioTramiteTraficoCambioServicio.pendientesEnvioExcel(tramiteDto, utilesColegiado.getIdUsuarioSessionBigDecimal());

					if (resultadoCambioServicio.getError()) {
						resumenCambioServicio.addFallido();
						addActionError("Ha fallado el cambio de servicio con numero expediente: " + numExpediente);
						for (String mensaje : resultadoCambioServicio.getListaMensajes()) {
							addActionError(mensaje);
						}
					} else {
						addActionMessage("Cambio de Servicio pendiente Excels: " + numExpediente);
						resumenCambioServicio.addPendientesExcel();
					}
				} else {
					addActionError("El expediente: " + numExpediente + ", no es del tipo adecuado para el envio excel.");
					resumenTotal.addFallido();
				}
			}

			resumenTotal.setNumFallidos(resumenTotal.getNumFallidos() + resumenBaja.getNumFallidos() + resumenDuplicado.getNumFallidos());
			resumenTotal.setNumPendientesExcel(resumenBaja.getNumPendientesExcel() + resumenDuplicado.getNumPendientesExcel());

			resumenPendientes.add(resumenBaja);
			resumenPendientes.add(resumenDuplicado);
			resumenPendientes.add(resumenCambioServicio);
			resumenPendientes.add(resumenTotal);
			resumenPendienteEnvioExcel = true;

		} catch (Throwable th) {
			log.error("ERROR al enviar a excel. ", th);
			addActionError("Error al cambiar el estado pendiente de envío excel.");
		}
		return actualizarPaginatedList();
	}

	public String generarXML620() throws Throwable {
		byte[] bytesSalida = null;
		try {
			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return actualizarPaginatedList();
			}

			String[] codigosSel = codSeleccionados.split("_");

			String tipoTramite = "";
			List<TramiteTraficoBean> tramites = new ArrayList<>();
			for (int j = 0; j < codigosSel.length; j++) {
				TramiteTraficoBean linea = getModeloTrafico().buscarTramitePorNumExpediente(codigosSel[j]);
				if (linea == null) {
					addActionError("El trámite " + codigosSel[j] + " no existe. No se puede continuar con la exportación;");
					return actualizarPaginatedList();
				} else {
					tramites.add(linea);
					if (StringUtils.isNotBlank(tipoTramite) && !tipoTramite.equals(linea.getTipoTramite().getValorEnum())) {
						addActionError("Todos los trámites deben ser del mismo tipo");
						return actualizarPaginatedList();
					} else {
						tipoTramite = linea.getTipoTramite().getValorEnum();
					}
				}
			}

			fileName = "modelo620gataTrans";
			if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
				List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<TramiteTraficoTransmisionBean>();
				for (TramiteTraficoBean tramiteTraf : tramites) {
					Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(tramiteTraf.getNumExpediente());
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);
					List<String> erroresValidar = getModeloTransmision().validarCamposXML(tramite);
					if (erroresValidar.isEmpty()) {
						listaTramitesTransmision.add(tramite);
					} else {
						addActionError("El trámite " + tramite.getTramiteTraficoBean().getNumExpediente()
								+ " no ha validado su modelo 620. No se ha exportado. Inténtelo desde el propio trámite para poder ver los errores.");
					}
				}

				if (listaTramitesTransmision.isEmpty()) {
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + ". No se ha podido exportar ningun 620");
					return actualizarPaginatedList();
				}

				String idSession = ServletActionContext.getRequest().getSession().getId();
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(listaTramitesTransmision, idSession);

				if (respuestaGenerarXMLJaxb.get("errores") != null && ((List<String>) respuestaGenerarXMLJaxb.get("errores")).size() > 0) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de transmión.");
					return actualizarPaginatedList();
				}

				String nombreFichero = (String) respuestaGenerarXMLJaxb.get("fichero");
				try {
					String validado = validarXML620(new File(nombreFichero));
					if (!ConstantesTrafico.XML_VALIDO.equals(validado)) {
						addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
						return actualizarPaginatedList();
					}
				} catch (Exception e) {
					addActionError("Error al generar el archivo XML");
					return actualizarPaginatedList();
				}

				try {
					bytesSalida = utiles.getBytesFromFile(new File(nombreFichero));
				} catch (Exception e) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
				}
			} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
				List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<>();
				for (TramiteTraficoBean tramiteTraf : tramites) {
					Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(tramiteTraf.getNumExpediente());
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

					List<String> erroresValidar = getModeloTransmision().validarCamposXML(tramite);
					if (erroresValidar.isEmpty()) {
						listaTramitesTransmision.add(tramite);
					} else {
						addActionError("El trámite " + tramite.getTramiteTraficoBean().getNumExpediente()
								+ " no ha validado su modelo 620. No se ha exportado. Inténtelo desde el propio trámite para poder ver los errores.");
						for (int p = 0; p < erroresValidar.size(); p++) {
							addActionError(erroresValidar.get(p).toString());
						}
					}
				}

				if (listaTramitesTransmision.isEmpty()) {
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + ". No se ha podido exportar ningun 620");
					return actualizarPaginatedList();
				}

				String idSession = ServletActionContext.getRequest().getSession().getId();
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(listaTramitesTransmision, idSession);

				if (respuestaGenerarXMLJaxb.get("errores") != null && !((List<String>) respuestaGenerarXMLJaxb.get("errores")).isEmpty()) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de transmión.");
					return actualizarPaginatedList();
				}

				String nombreFichero = (String) respuestaGenerarXMLJaxb.get("fichero");
				try {
					String validado = validarXML620(new File(nombreFichero));
					if (!ConstantesTrafico.XML_VALIDO.equals(validado)) {
						addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
						return actualizarPaginatedList();
					}
				} catch (Exception e) {
					addActionError("Error al generar el archivo XML");
					return actualizarPaginatedList();
				}

				try {
					bytesSalida = utiles.getBytesFromFile(new File(nombreFichero));
				} catch (Exception e) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
				}
			} else {
				addActionError("El modelo 620 es solo para trámites de transmisión");
			}
			if (null != bytesSalida) {
				impresoEspera = true;
				getSession().put(ConstantesTrafico.FICHEROXML, bytesSalida);
				getSession().put(ConstantesTrafico.NOMBREXML, fileName);
				addActionMessage("Fichero XML generado correctamente.");
			}
		} catch (Throwable th) {
			log.error("ERROR al generar el xml 620: ", th);
			addActionError("Error al generar el xml 620.");
		}
		return actualizarPaginatedList();
	}

	public String generarDocBase() {
		try {
			if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
				ResultadoDocBaseBean resultado = servicioConsultaTramiteTrafico.generarDocBase(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumenDocBase = resultado.getResumen();
				}
			} else {
				addActionError("Debe de seleccionar algún expediente para poder generar los docBase.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar los documento base, error: ", e);
		}
		return actualizarPaginatedList();
	}

	public String asignacionMasivaTasas() throws Throwable {
		try {
			String[] codigosSel = codSeleccionados.split("_");
			if (codigosSel != null && codigosSel.length > 0) {
				String idUsuarioTasaColegio = gestorPropiedades.valorPropertie("trafico.id.usuario.tasas.colegio");
				if (idUsuarioTasaColegio == null || idUsuarioTasaColegio.equals("")) {
					log.error(ConstantesTrafico.NO_SE_HA_RECUPERADO_EL_IDENTIFICADOR_DE_LAS_TASAS_IMPORTADAS_DESDE_EL_ICOGAM);
					addActionError(ConstantesTrafico.NO_SE_HA_RECUPERADO_EL_IDENTIFICADOR_DE_LAS_TASAS_IMPORTADAS_DESDE_EL_ICOGAM);
				} else {
					for (String numExpediente : codigosSel) {
						ResultBean resultado = servicioTramiteTrafico.asignarTasaLibre(new BigDecimal(numExpediente), utilesColegiado.getIdUsuarioSession());
						if (resultado != null && !resultado.getError()) {
							addActionMessage("Expediente " + numExpediente + " se asiganado la tasa correctamente");
							resumenAsignacionMasivaTasasT.addCorrecto();
						} else {
							addActionError("Expediente " + numExpediente + ": " + resultado.getMensaje());
							resumenAsignacionMasivaTasasT.addFallido();
						}
					}
					resumenAsignacionMasivaTasasFlag = true;
					resumenAsignacionMasivaTasas.add(resumenAsignacionMasivaTasasT);
				}
			} else {
				addActionError("Debe seleccionar algún trámite.");
			}
		} catch (Exception ex) {
			log.error("No se ha podido asignar las tasas libre a los trámites,error:", ex);
			addActionError("No se ha podido asignar las tasas libre a los trámites.");
		}
		return actualizarPaginatedList();
	}

	public String descargaXML() {
		try {
			String[] codigosSel = codSeleccionados.split("_");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream out = new ZipOutputStream(baos);
			for (int count = 0; count < codigosSel.length; count++) {
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.NOMBRE_CTIT + codigosSel[count], ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.NOMBRE_CHECKCTIT + codigosSel[count], ConstantesGestorFicheros.CHECKCTIT, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.NOMBRE_DUPLICADO + codigosSel[count], ConstantesGestorFicheros.FULLDUPLICADO, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.NOMBRE_CHECKDUPLICADO + codigosSel[count], ConstantesGestorFicheros.CHECKDUPLICADO, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.NOMBRE_MATW + codigosSel[count], ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codigosSel[count], codigosSel[count], ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.CONSULTA_BTV + "_" + codigosSel[count], ConstantesGestorFicheros.CONSULTA_BTV, ConstantesGestorFicheros.XML_ENVIO);
				buscarFicheros(out, codigosSel[count], ConstantesGestorFicheros.TRAMITAR_BTV + "_" + codigosSel[count], ConstantesGestorFicheros.TRAMITAR_BTV, ConstantesGestorFicheros.XML_ENVIO);
			}
			if (cierraZip) {
				out.close();
				ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
				setInputStream(stream);
				setFileName("XMLenviadoDGT" + ConstantesPDF.EXTENSION_ZIP);
				return DESCARGAR_XML;
			} else {
				addActionError("No existe ningún XML para ese trámite");
			}
		} catch (FileNotFoundException e) {
			log.error("Se ha producido un error al descargar el fichero del tramite. ", e);
		} catch (IOException e) {
			log.error("Se ha producido un error al descargar el fichero del tramite. ", e);
		}
		return actualizarPaginatedList();
	}

	public String consultaTarjetaEitv() {
		try {
			String[] codigosSel = codSeleccionados.split("_");
			if (codigosSel == null) {
				addActionError("Debe de seleccionar algun expediente para consultar.");
			} else {
				ResultBean resultado = servicioConsultaTramiteTrafico.consultaEitv(codigosSel, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
				long lResultadoOk = (Long) resultado.getAttachment("lResultadosOk");
				long lResultadosError = (Long) resultado.getAttachment("lResultadosError");
				if (lResultadosError != 0) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
				if (lResultadoOk != 0) {
					List<String> listaMensajesOK = (List<String>) resultado.getAttachment("listaMensajesOK");
					for (String mensaje : listaMensajesOK) {
						addActionMessage(mensaje);
					}
				}
				resumenConsultaTarjetaEitvFlag = true;
				resumenConsultaTarjetaEitvT.setNumFallidos((int) lResultadosError);
				resumenConsultaTarjetaEitvT.setConsultasEitvEnCola((int) lResultadoOk);
				resumenConsultaTarjetaEitv.add(resumenConsultaTarjetaEitvT);
			}
		} catch (Exception ex) {
			log.error("No se ha podido realizar la consulta EITV,error:", ex);
			addActionError("No se ha podido realizar la consulta EITV.");
		}
		return actualizarPaginatedList();
	}

	public String liberarMasivoEeff() {
		String[] codigosSel = codSeleccionados.split("_");
		if (codigosSel == null) {
			addActionError("Debe de seleccionar algun expediente para consultar.");
		} else {
			if (utilesColegiado.tienePermisoConsultaEEFF()) {
				ResultadoConsultaEEFF resultado = servicioConsultaTramiteTrafico.liberacionEEFFBloque(codigosSel, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
					if (resultado.getListaErrores() != null && !resultado.getListaErrores().isEmpty()) {
						for (String error : resultado.getListaErrores()) {
							addActionError(error);
						}
					}
				} else {
					resumenEEFF = new ResumenEEFF();
					resumenEEFF.setEsLiberacion(Boolean.TRUE);
					resumenEEFF.setListaMensajesError(resultado.getListaErrores());
					resumenEEFF.setListaMensajesOk(resultado.getListaOK());
					resumenEEFF.setNumError(resultado.getNumError());
					resumenEEFF.setNumOK(resultado.getNumOk());
					resumenEEFFFlag = Boolean.TRUE;
				}
			} else {
				addActionError("No tiene permisos para consultas EEFF");
			}
		}
		return actualizarPaginatedList();
	}

	private void cargarOtrosDatos() {
		try {
			String numColegiadoExpediente = numExpediente.substring(0, 4);
			String nifFacturacion = servicioTramiteTrafico.getNifFacturacionPorNumExp(numExpediente);
			if (StringUtils.isNotBlank(nifFacturacion)) {
				personaFac = ModeloPersona.obtenerDetallePersonaCompleto(nifFacturacion, numColegiadoExpediente);
			}
			listaAcciones = UtilidadesAccionTrafico.getInstance().generarListaAcciones(numExpediente);
		} catch (Throwable e) {
			log.error("Error al recuperar datos previos al detalle del trámite.");
		}
	}

	private void validarCetInformado(List<String> listaMensajes) {
		if (listaMensajes != null) {
			for (int x = 0; x < listaMensajes.size(); x++) {
				if (listaMensajes.get(x).equalsIgnoreCase(ConstantesTrafico.LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA)) {
					String mensajeValidado = ConstantesTrafico.LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA;
					mensajesValidadoPDF.add(mensajeValidado);
				}
			}
		}
	}

	private void validarVarios(List<String> listaMensajes) {
		if (listaMensajes != null) {
			for (String mensaje : listaMensajes) {
				if (mensaje.contains(ConstantesTrafico.AVISO_MAXIMA_LONGITUD_DIRECCION_VEHICULO)) {
					addActionMessage("- " + mensaje);
					if (mensajesValidadoTelematicamente != null) {
						mensajesValidadoTelematicamente.add(mensaje);
					}
				} else if (mensaje.contains(ConstantesTrafico.AVISO_VALIDACION_WMI)
						|| mensaje.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
					mensaje = mensaje.replace("--", "- ");
					addActionMessage(mensaje);
					if (mensajesValidadoTelematicamente != null) {
						mensajesValidadoTelematicamente.add(mensaje);
					}
				}
			}
		}
	}

	private String validarTiposCreditosTransferencia(List<TramiteTraficoBean> beans) throws OegamExcepcion {
		boolean notificacion = false;
		boolean noNotificacion = false;
		for (TramiteTraficoBean tramite : beans) {
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramite.getTipoTramite().getValorEnum())
					|| TipoTramiteTrafico.Transmision.getValorEnum().equals(tramite.getTipoTramite().getValorEnum())) {
				String tipoTransferencia = servicioTramiteTraficoTransmision.getTipoTransferencia(tramite.getNumExpediente());
				if (StringUtils.isNotBlank(tipoTransferencia)) {
					if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramite.getTipoTramite().getValorEnum())) {
						if (TipoTransferencia.tipo4.getValorEnum().equals(tipoTransferencia)
								|| TipoTransferencia.tipo5.getValorEnum().equals(tipoTransferencia)) {
							notificacion = true;
						} else {
							noNotificacion = true;
						}
					} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tramite.getTipoTramite().getValorEnum())) {
						if (TipoTransferencia.tipo2.getValorEnum().equals(tipoTransferencia)) {
							notificacion = true;
						} else {
							noNotificacion = true;
						}
					}
				}
			}
			if (notificacion && noNotificacion) {
				return null;
			}
		}
		if (notificacion) {
			return "Baja";
		}
		return "Transmisión";
	}

	private boolean validarEstados(TramiteTraficoBean tramiteTraficoBean) {
		boolean validar = true;
		if (EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()
				.equals(tramiteTraficoBean.getEstado().getValorEnum())
				|| EstadoTramiteTrafico.LiberadoEEFF.getValorEnum()
						.equals(tramiteTraficoBean.getEstado().getValorEnum())) {
			if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoLiberarEEFF()
					&& tramiteTraficoBean.getTipoTramite() == TipoTramiteTrafico.Matriculacion
					&& EstadoTramiteTrafico.LiberadoEEFF.getValorEnum()
							.equals(tramiteTraficoBean.getEstado().getValorEnum())) {
				LiberacionEEFFDto liberadoEEFF = servicioEEFF
						.getLiberacionEEFFDto(tramiteTraficoBean.getNumExpediente());
				if (liberadoEEFF == null) {
					if (tramiteTraficoBean.getVehiculo() == null || (tramiteTraficoBean.getVehiculo().getNive() != null
							&& !tramiteTraficoBean.getVehiculo().getNive().isEmpty())) {
						validar = false;
					}
				} else {
					if (!liberadoEEFF.getExento() && !liberadoEEFF.getRealizado()) {
						validar = false;
					}
				}
			}
		} else {
			validar = false;
		}
		return validar;
	}

	private String validarXML620(File fichero) {
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		URL esquema = this.getClass().getResource("/trafico/schemas/620_V1.4.xsd");
		final String MY_SCHEMA = esquema.getFile();
		try {
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
			Schema schema = factory.newSchema(new StreamSource(MY_SCHEMA));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(fichero));
		} catch (SAXParseException spe) {
			String error = "Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + ": Error en la generacion del XML 620. " + getModeloTransmision()
					.parseSAXParseException(spe);
			addActionError(error);
			log.error("Error al validar el XML contra el xsd del 620 (620_V1.4.xsd)", spe);
			return spe.toString();
		} catch (SAXException saxEx) {
			log.error(saxEx);
			return saxEx.toString();
		} catch (Exception ex) {
			log.error(ex);
			return ex.toString();
		}
		return ConstantesTrafico.XML_VALIDO;
	}

	private void buscarFicheros(ZipOutputStream out, String numExpediente, String nombre, String tipoExportar, String subTipo) {
		try {
			FileResultBean resultFileBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipoExportar, subTipo, Utilidades.transformExpedienteFecha(numExpediente), nombre,
					ConstantesGestorFicheros.EXTENSION_XML);
			if (FileResultStatus.ON_DEMAND_FILE.equals(resultFileBean.getStatus())) {
				addActionError(numExpediente + ": " + resultFileBean.getMessage());
			} else if (resultFileBean.getFile() != null) {
				FileInputStream fis = new FileInputStream(resultFileBean.getFile());
				cierraZip = true;
				ZipEntry entrada = new ZipEntry(nombre + ConstantesPDF.EXTENSION_XML);
				out.putNextEntry(entrada);

				byte[] buffer = new byte[1024];
				int leido = 0;
				while (0 < (leido = fis.read(buffer))) {
					out.write(buffer, 0, leido);
				}
				fis.close();
				out.closeEntry();
			}
		} catch (Exception e) {
			log.error("Error al intentar obtener el xml del expediente: " + nombre);
			totalErroresXML++;
			errorExportacionXML = new ErrorExportacionXML();
			errorExportacionXML.addFallido();
			listaErroresExportacionXML.add(errorExportacionXML);
		} catch (OegamExcepcion e) {
			log.error("Error al intentar obtener el xml del expediente: " + nombre);
		}
	}

	private ResultBean crearSolicitud576(String numExpediente, String estadoAnterior) {
		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud();
		beanPQCrearSolicitud.setP_ID_TRAMITE(new BigDecimal(numExpediente));
		beanPQCrearSolicitud.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(TipoTramiteTrafico.Matriculacion.getValorEnum());
		beanPQCrearSolicitud.setP_PROCESO(ProcesosEnum.PROCESO_576.getNombreEnum());

		BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
		beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Respuesta_AEAT.getValorEnum()));
		beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(new BigDecimal(numExpediente));
		Map<String, Object> resultadoCambiarEstado = getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite);
		ResultBean resultBeanCambiarEstado = (ResultBean) resultadoCambiarEstado.get(ConstantesPQ.RESULTBEAN);

		if (!resultBeanCambiarEstado.getError()) {
			resultBeanCambiarEstado.setMensaje("OK");
			Map<String, Object> resultadoSolicitud = getModeloSolicitud().crearSolicitud(beanPQCrearSolicitud);
			ResultBean resultBean = (ResultBean) resultadoSolicitud.get(ConstantesPQ.RESULTBEAN);

			if (!resultBean.getError()) {
				log.info("Se agrego correctamente en la cola la solicitud de presentacion del 576 para el expediente:" + numExpediente + " para el Usuario:" + utilesColegiado.getIdUsuarioSessionBigDecimal());
				resultBeanCambiarEstado.setMensaje("OK");
			} else {
				try {
					TramiteTraficoBean tramiteTrafico = new TramiteTraficoBean();
					tramiteTrafico.setEstado(estadoAnterior);
					tramiteTrafico.setNumExpediente(new BigDecimal(numExpediente));
					BeanPQCAMBIAR_ESTADO_S_V beanCambEstado = UtilesConversionesTrafico.convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(tramiteTrafico);
					beanCambEstado.setP_ESTADO(new BigDecimal(estadoAnterior));
					beanCambEstado.execute();
					if (!beanCambEstado.getP_CODE().toString().equals("0")) {
						log.error("Se ha producido un error al actualizar al estado anterior el tramite." + beanCambEstado.getP_SQLERRM());
					}
				} catch (Exception e) {
					log.error("Se ha producido un error al actualizar al estado anterior el tramite, ya que no se ha podido crear la solicitud. Error: " + e);
				}
				resultBeanCambiarEstado.setError(true);
				resultBeanCambiarEstado.setMensaje(resultBean.getMensaje());
			}
		} else {
			resultBeanCambiarEstado.setError(true);
			resultBeanCambiarEstado.setMensaje(resultBeanCambiarEstado.getMensaje());
		}
		return resultBeanCambiarEstado;
	}

	public String tramitarNRE06() throws OegamExcepcion {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoSolicitudNRE06Bean resultado = servicioConsultaTramiteTrafico.tramitarNRE06(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resumenSolicitudNRE06.setNumFallidos(resultado.getNumError());
					resumenSolicitudNRE06.setNumOk(resultado.getNumOk());
					resumenSolicitudNRE06.setListaMensajesOk(resultado.getListaOk());
					resumenSolicitudNRE06.setListaMensajesError(resultado.getListaError());
					resumenSolicitudNRE06Flag = true;
				}
			}
		} catch (Exception e) {
			log.error("No se ha podido tramitar el NRE,error:", e);
			addActionError("No se ha podido tramitar el NRE.");
		}
		return actualizarPaginatedList();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaTramiteTrafPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaTramiteBean == null) {
			consultaTramiteBean = new ConsultaTramiteTraficoFilterBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTramiteBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaTramiteBean == null) {
			consultaTramiteBean = new ConsultaTramiteTraficoFilterBean();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			consultaTramiteBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}

		consultaTramiteBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	public Boolean validarFechas(Object object) {
		ConsultaTramiteTraficoFilterBean objectCriterios = (ConsultaTramiteTraficoFilterBean) object;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		Boolean esRangoValido = Boolean.FALSE;
		String mensaje = "";
		if (StringUtils.isBlank(objectCriterios.getRefPropia()) && objectCriterios.getNumExpediente() == null && StringUtils.isBlank(objectCriterios.getNifTitular()) && StringUtils.isBlank(
				objectCriterios.getNifFacturacion()) && StringUtils.isBlank(objectCriterios.getBastidor()) && StringUtils.isBlank(objectCriterios.getMatricula())) {
			if (objectCriterios.getFechaAlta() == null && objectCriterios.getFechaPresentacion() == null) {
				addActionError("Para poder realizar una busqueda de tramites debe de indicar alguna fecha.");
			} else {
				if (StringUtils.isNotBlank(valorRangoFechas)) {
					if (objectCriterios.getFechaAlta() != null && !objectCriterios.getFechaAlta().isfechaNula()) {
						if (objectCriterios.getFechaAlta().getFechaInicio() == null || objectCriterios.getFechaAlta().getFechaFin() == null) {
							mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " dias para poder obtener los tramites.";
						} else {
							esRangoValido = utilesFecha.comprobarRangoFechas(objectCriterios.getFechaAlta().getFechaInicio(), objectCriterios.getFechaAlta().getFechaFin(), Integer.parseInt(
									valorRangoFechas));
							if (!esRangoValido) {
								mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " dias para poder obtener los tramites.";
							}
						}
					}
					if (objectCriterios.getFechaPresentacion() != null && !objectCriterios.getFechaPresentacion().isfechaNula()) {
						if (objectCriterios.getFechaPresentacion().getFechaInicio() == null || objectCriterios.getFechaPresentacion().getFechaFin() == null) {
							if (StringUtils.isBlank(mensaje)) {
								mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
							} else {
								mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
							}
						} else {
							esRangoValido = utilesFecha.comprobarRangoFechas(objectCriterios.getFechaPresentacion().getFechaInicio(), objectCriterios.getFechaPresentacion().getFechaFin(), Integer
									.parseInt(valorRangoFechas));
							if (!esRangoValido) {
								if (StringUtils.isBlank(mensaje)) {
									mensaje = "Debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
								} else {
									mensaje = ", debe indicar un rango de fechas de presentación no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
								}
							}
						}
					}
				}
				if (!esRangoValido) {
					if (StringUtils.isNotBlank(mensaje)) {
						addActionError(mensaje);
					} else {
						addActionError("Para poder realizar una búsqueda de trámites debe de indicar alguna fecha.");
					}
				}
			}
		} else {
			esRangoValido = Boolean.TRUE;
		}
		return esRangoValido;
	}

	private String[] prepararExpedientesSeleccionados() {
		String[] codSeleccionados = null;
		if (getListaChecksConsultaTramite() != null && !getListaChecksConsultaTramite().isEmpty()) {
			codSeleccionados = getListaChecksConsultaTramite().replaceAll(" ", "").split(",");
			setNumsExpediente(getListaChecksConsultaTramite());
		} else if (getNumsExpediente() != null && !getNumsExpediente().isEmpty()) {
			codSeleccionados = getNumsExpediente().replaceAll(" ", "").split(",");
		} else {
			codSeleccionados = new String[1];
			codSeleccionados[0] = getNumExpediente();
			setNumsExpediente(getNumExpediente());
		}
		return codSeleccionados;
	}

	@Override
	protected Object getBeanCriterios() {
		return consultaTramiteBean;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.trafico.consulta.view.decorator.DecoradorConsTramiteTrafico";
	}

	public String cargarPopUpCambioEstado() {
		return POP_UP_ESTADOS;
	}

	public String abrirPopMatriculacion() {
		return POP_UP_MATRICULA;
	}
	
	public String abrirPopBorrarDatos() {
		return POP_UP_BORRAR_DATOS;
	}

	public String abrirDuplicarDesasignarTasa() {
		return POP_UP_DUPLICAR_TRAMITE;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		consultaTramiteBean = (ConsultaTramiteTraficoFilterBean) object;
	}

	private Boolean existeFicheroAVPO(BigDecimal numExp) throws OegamExcepcion {
		if (numExp == null) {
			return false;
		}
		FileResultBean result = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.SOLICITUD_INFORMACION, null, Utilidades.transformExpedienteFecha(numExp), numExp.toString());
		return result != null && result.getFiles() != null && !result.getFiles().isEmpty();
	}

	public ConsultaTramiteTraficoFilterBean getConsultaTramiteBean() {
		return consultaTramiteBean;
	}

	public void setConsultaTramiteBean(ConsultaTramiteTraficoFilterBean consultaTramiteBean) {
		this.consultaTramiteBean = consultaTramiteBean;
	}

	public String getPropTexto() {
		if (propTexto == null || propTexto.isEmpty()) {
			propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		}
		return propTexto;
	}

	public void setPropTexto(String propTexto) {
		this.propTexto = propTexto;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
	}

	public void setTramiteTraficoTransmisionBean(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		this.tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBean;
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public SolicitudDatosBean getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDatosBean solicitud) {
		this.solicitud = solicitud;
	}

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	public void setModeloSolicitudDatos(ModeloSolicitudDatos modeloSolicitudDatos) {
		this.modeloSolicitudDatos = modeloSolicitudDatos;
	}

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
	}

	public ModeloDuplicado getModeloDuplicado() {
		if (modeloDuplicado == null) {
			modeloDuplicado = new ModeloDuplicado();
		}
		return modeloDuplicado;
	}

	public void setModeloDuplicado(ModeloDuplicado modeloDuplicado) {
		this.modeloDuplicado = modeloDuplicado;
	}

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public Boolean getExisteZip() {
		return existeZip;
	}

	public void setExisteZip(Boolean existeZip) {
		this.existeZip = existeZip;
	}

	public TramiteTrafCambioServicioDto getTramiteTraficoCambServ() {
		return tramiteTraficoCambServ;
	}

	public void setTramiteTraficoCambServ(TramiteTrafCambioServicioDto tramiteTraficoCambServ) {
		this.tramiteTraficoCambServ = tramiteTraficoCambServ;
	}

	public DocBaseCarpetaTramiteBean getDocBaseCarpetaTramiteBean() {
		return docBaseCarpetaTramiteBean;
	}

	public void setDocBaseCarpetaTramiteBean(DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean) {
		this.docBaseCarpetaTramiteBean = docBaseCarpetaTramiteBean;
	}

	public Persona getPersonaFac() {
		return personaFac;
	}

	public void setPersonaFac(Persona personaFac) {
		this.personaFac = personaFac;
	}

	public PaginatedList getListaAcciones() {
		return listaAcciones;
	}

	public void setListaAcciones(PaginatedList listaAcciones) {
		this.listaAcciones = listaAcciones;
	}

	public String getListaChecksConsultaTramite() {
		return listaChecksConsultaTramite;
	}

	public void setListaChecksConsultaTramite(String listaChecksConsultaTramite) {
		this.listaChecksConsultaTramite = listaChecksConsultaTramite;
	}

	public List<ResumenValidacion> getResumen() {
		return resumen;
	}

	public void setResumen(List<ResumenValidacion> resumen) {
		this.resumen = resumen;
	}

	public List<String> getMensajesValidadoPDF() {
		return mensajesValidadoPDF;
	}

	public void setMensajesValidadoPDF(List<String> mensajesValidadoPDF) {
		this.mensajesValidadoPDF = mensajesValidadoPDF;
	}

	public List<String> getMensajesValidadoTelematicamente() {
		return mensajesValidadoTelematicamente;
	}

	public void setMensajesValidadoTelematicamente(List<String> mensajesValidadoTelematicamente) {
		this.mensajesValidadoTelematicamente = mensajesValidadoTelematicamente;
	}

	public Boolean getResumenValidacion() {
		return resumenValidacion;
	}

	public void setResumenValidacion(Boolean resumenValidacion) {
		this.resumenValidacion = resumenValidacion;
	}

	public Boolean getResumenTramitacion() {
		return resumenTramitacion;
	}

	public void setResumenTramitacion(Boolean resumenTramitacion) {
		this.resumenTramitacion = resumenTramitacion;
	}

	public List<ResumenTramitacionTelematica> getResumenTramitacionTelematica() {
		return resumenTramitacionTelematica;
	}

	public void setResumenTramitacionTelematica(List<ResumenTramitacionTelematica> resumenTramitacionTelematica) {
		this.resumenTramitacionTelematica = resumenTramitacionTelematica;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public ResumenConsultaTramiteTraficoBean getResumenConsultaTramiteTrafico() {
		return resumenConsultaTramiteTrafico;
	}

	public void setResumenConsultaTramiteTrafico(ResumenConsultaTramiteTraficoBean resumenConsultaTramiteTrafico) {
		this.resumenConsultaTramiteTrafico = resumenConsultaTramiteTrafico;
	}

	public Boolean getResumenCambiosEstadoFlag() {
		return resumenCambiosEstadoFlag;
	}

	public void setResumenCambiosEstadoFlag(Boolean resumenCambiosEstadoFlag) {
		this.resumenCambiosEstadoFlag = resumenCambiosEstadoFlag;
	}

	public List<ResumenCambiosEstado> getResumenCambiosEstado() {
		return resumenCambiosEstado;
	}

	public void setResumenCambiosEstado(List<ResumenCambiosEstado> resumenCambiosEstado) {
		this.resumenCambiosEstado = resumenCambiosEstado;
	}

	public ResumenCambiosEstado getResumenCambiosEstadoT() {
		return resumenCambiosEstadoT;
	}

	public void setResumenCambiosEstadoT(ResumenCambiosEstado resumenCambiosEstadoT) {
		this.resumenCambiosEstadoT = resumenCambiosEstadoT;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public Boolean getResumenDocumentosGeneradosFlag() {
		return resumenDocumentosGeneradosFlag;
	}

	public void setResumenDocumentosGeneradosFlag(Boolean resumenDocumentosGeneradosFlag) {
		this.resumenDocumentosGeneradosFlag = resumenDocumentosGeneradosFlag;
	}

	public Boolean getResumenAutoBTV() {
		return resumenAutoBTV;
	}

	public void setResumenAutoBTV(Boolean resumenAutoBTV) {
		this.resumenAutoBTV = resumenAutoBTV;
	}

	public ResumenOperacionesTramiteBean getResumenOpeTram() {
		return resumenOpeTram;
	}

	public void setResumenOpeTram(ResumenOperacionesTramiteBean resumenOpeTram) {
		this.resumenOpeTram = resumenOpeTram;
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

	public ResumenPermisoDistintivoItvBean getResum() {
		return resum;
	}

	public void setResum(ResumenPermisoDistintivoItvBean resum) {
		this.resum = resum;
	}

	public Boolean getResumenPermDstv() {
		return resumenPermDstv;
	}

	public void setResumenPermDstv(Boolean resumenPermDstv) {
		this.resumenPermDstv = resumenPermDstv;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(String fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}

	public void setResultBeanImprimir(ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}

	public Boolean getResumenLiquidacionNRE06() {
		return resumenLiquidacionNRE06;
	}

	public void setResumenLiquidacionNRE06(Boolean resumenLiquidacionNRE06) {
		this.resumenLiquidacionNRE06 = resumenLiquidacionNRE06;
	}

	public Boolean getImprimirFichero() {
		return imprimirFichero;
	}

	public void setImprimirFichero(Boolean imprimirFichero) {
		this.imprimirFichero = imprimirFichero;
	}

	public String getFicheroDescarga() {
		return ficheroDescarga;
	}

	public void setFicheroDescarga(String ficheroDescarga) {
		this.ficheroDescarga = ficheroDescarga;
	}

	public Boolean getResumenCertificadoTasasFlag() {
		return resumenCertificadoTasasFlag;
	}

	public void setResumenCertificadoTasasFlag(Boolean resumenCertificadoTasasFlag) {
		this.resumenCertificadoTasasFlag = resumenCertificadoTasasFlag;
	}

	public ResumenCertificadoTasas getResumenCertificadoTasas() {
		return resumenCertificadoTasas;
	}

	public void setResumenCertificadoTasas(ResumenCertificadoTasas resumenCertificadoTasas) {
		this.resumenCertificadoTasas = resumenCertificadoTasas;
	}

	public Boolean getResumenCambiosFlag() {
		return resumenCambiosFlag;
	}

	public void setResumenCambiosFlag(Boolean resumenCambiosFlag) {
		this.resumenCambiosFlag = resumenCambiosFlag;
	}

	public List<ResumenCambiosNive> getResumenCambiosNive() {
		return resumenCambiosNive;
	}

	public void setResumenCambiosNive(List<ResumenCambiosNive> resumenCambiosNive) {
		this.resumenCambiosNive = resumenCambiosNive;
	}

	public ResumenCambiosNive getResumenCambiosNiveT() {
		return resumenCambiosNiveT;
	}

	public void setResumenCambiosNiveT(ResumenCambiosNive resumenCambiosNiveT) {
		this.resumenCambiosNiveT = resumenCambiosNiveT;
	}

	public ServicioConsultaTramiteTrafico getServicioConsultaTramiteTrafico() {
		return servicioConsultaTramiteTrafico;
	}

	public void setServicioConsultaTramiteTrafico(ServicioConsultaTramiteTrafico servicioConsultaTramiteTrafico) {
		this.servicioConsultaTramiteTrafico = servicioConsultaTramiteTrafico;
	}

	public Boolean getResumenFicheroSolicitud05Flag() {
		return resumenFicheroSolicitud05Flag;
	}

	public void setResumenFicheroSolicitud05Flag(Boolean resumenFicheroSolicitud05Flag) {
		this.resumenFicheroSolicitud05Flag = resumenFicheroSolicitud05Flag;
	}

	public ResumenFicheroSolicitud05 getResumenFicheroSolicitud05() {
		return resumenFicheroSolicitud05;
	}

	public void setResumenFicheroSolicitud05(ResumenFicheroSolicitud05 resumenFicheroSolicitud05) {
		this.resumenFicheroSolicitud05 = resumenFicheroSolicitud05;
	}

	public Boolean getResumenPendienteEnvioExcel() {
		return resumenPendienteEnvioExcel;
	}

	public void setResumenPendienteEnvioExcel(Boolean resumenPendienteEnvioExcel) {
		this.resumenPendienteEnvioExcel = resumenPendienteEnvioExcel;
	}

	public List<ResumenPendienteExcel> getResumenPendientes() {
		return resumenPendientes;
	}

	public void setResumenPendientes(List<ResumenPendienteExcel> resumenPendientes) {
		this.resumenPendientes = resumenPendientes;
	}

	public String getTipoTramiteSeleccionado() {
		return tipoTramiteSeleccionado;
	}

	public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
		this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
	}

	public String getEstadoTramiteSeleccionado() {
		return estadoTramiteSeleccionado;
	}

	public void setEstadoTramiteSeleccionado(String estadoTramiteSeleccionado) {
		this.estadoTramiteSeleccionado = estadoTramiteSeleccionado;
	}

	public String getBastidorSeleccionado() {
		return bastidorSeleccionado;
	}

	public void setBastidorSeleccionado(String bastidorSeleccionado) {
		this.bastidorSeleccionado = bastidorSeleccionado;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public Boolean getImpresoEspera() {
		return impresoEspera;
	}

	public void setImpresoEspera(Boolean impresoEspera) {
		this.impresoEspera = impresoEspera;
	}

	public ResumenDocBaseBean getResumenDocBase() {
		return resumenDocBase;
	}

	public void setResumenDocBase(ResumenDocBaseBean resumenDocBase) {
		this.resumenDocBase = resumenDocBase;
	}

	public Boolean getResumenAsignacionMasivaTasasFlag() {
		return resumenAsignacionMasivaTasasFlag;
	}

	public void setResumenAsignacionMasivaTasasFlag(Boolean resumenAsignacionMasivaTasasFlag) {
		this.resumenAsignacionMasivaTasasFlag = resumenAsignacionMasivaTasasFlag;
	}

	public ResumenAsignacionMasivaTasas getResumenAsignacionMasivaTasasT() {
		return resumenAsignacionMasivaTasasT;
	}

	public void setResumenAsignacionMasivaTasasT(ResumenAsignacionMasivaTasas resumenAsignacionMasivaTasasT) {
		this.resumenAsignacionMasivaTasasT = resumenAsignacionMasivaTasasT;
	}

	public List<ResumenAsignacionMasivaTasas> getResumenAsignacionMasivaTasas() {
		return resumenAsignacionMasivaTasas;
	}

	public void setResumenAsignacionMasivaTasas(List<ResumenAsignacionMasivaTasas> resumenAsignacionMasivaTasas) {
		this.resumenAsignacionMasivaTasas = resumenAsignacionMasivaTasas;
	}

	public boolean isCierraZip() {
		return cierraZip;
	}

	public void setCierraZip(boolean cierraZip) {
		this.cierraZip = cierraZip;
	}

	public ErrorExportacionXML getErrorExportacionXML() {
		return errorExportacionXML;
	}

	public void setErrorExportacionXML(ErrorExportacionXML errorExportacionXML) {
		this.errorExportacionXML = errorExportacionXML;
	}

	public List<ErrorExportacionXML> getListaErroresExportacionXML() {
		return listaErroresExportacionXML;
	}

	public void setListaErroresExportacionXML(List<ErrorExportacionXML> listaErroresExportacionXML) {
		this.listaErroresExportacionXML = listaErroresExportacionXML;
	}

	public int getTotalErroresXML() {
		return totalErroresXML;
	}

	public void setTotalErroresXML(int totalErroresXML) {
		this.totalErroresXML = totalErroresXML;
	}

	public Boolean getResumenPresentacion576Flag() {
		return resumenPresentacion576Flag;
	}

	public void setResumenPresentacion576Flag(Boolean resumenPresentacion576Flag) {
		this.resumenPresentacion576Flag = resumenPresentacion576Flag;
	}

	public List<ResumenPresentacion576> getResumenPresentacion576() {
		return resumenPresentacion576;
	}

	public void setResumenPresentacion576(List<ResumenPresentacion576> resumenPresentacion576) {
		this.resumenPresentacion576 = resumenPresentacion576;
	}

	public ResumenPresentacion576 getResumenPresentacion576T() {
		return resumenPresentacion576T;
	}

	public void setResumenPresentacion576T(ResumenPresentacion576 resumenPresentacion576T) {
		this.resumenPresentacion576T = resumenPresentacion576T;
	}

	public Boolean getResumenFicheroAEAT() {
		return resumenFicheroAEAT;
	}

	public void setResumenFicheroAEAT(Boolean resumenFicheroAEAT) {
		this.resumenFicheroAEAT = resumenFicheroAEAT;
	}

	public List<FicheroAEATBean> getListaFicherosAEATBean() {
		return listaFicherosAEATBean;
	}

	public void setListaFicherosAEATBean(List<FicheroAEATBean> listaFicherosAEATBean) {
		this.listaFicherosAEATBean = listaFicherosAEATBean;
	}

	public List<ResumenErroresFicheroAEAT> getListaResumenErroresFicheroAEAT() {
		return listaResumenErroresFicheroAEAT;
	}

	public void setListaResumenErroresFicheroAEAT(List<ResumenErroresFicheroAEAT> listaResumenErroresFicheroAEAT) {
		this.listaResumenErroresFicheroAEAT = listaResumenErroresFicheroAEAT;
	}

	public Boolean getResumenEEFFFlag() {
		return resumenEEFFFlag;
	}

	public void setResumenEEFFFlag(Boolean resumenEEFFFlag) {
		this.resumenEEFFFlag = resumenEEFFFlag;
	}

	public ResumenEEFF getResumenEEFF() {
		return resumenEEFF;
	}

	public void setResumenEEFF(ResumenEEFF resumenEEFF) {
		this.resumenEEFF = resumenEEFF;
	}

	public Boolean getResumenConsultaTarjetaEitvFlag() {
		return resumenConsultaTarjetaEitvFlag;
	}

	public void setResumenConsultaTarjetaEitvFlag(Boolean resumenConsultaTarjetaEitvFlag) {
		this.resumenConsultaTarjetaEitvFlag = resumenConsultaTarjetaEitvFlag;
	}

	public List<ResumenConsultaTarjetaEitv> getResumenConsultaTarjetaEitv() {
		return resumenConsultaTarjetaEitv;
	}

	public void setResumenConsultaTarjetaEitv(List<ResumenConsultaTarjetaEitv> resumenConsultaTarjetaEitv) {
		this.resumenConsultaTarjetaEitv = resumenConsultaTarjetaEitv;
	}

	public ResumenConsultaTarjetaEitv getResumenConsultaTarjetaEitvT() {
		return resumenConsultaTarjetaEitvT;
	}

	public void setResumenConsultaTarjetaEitvT(ResumenConsultaTarjetaEitv resumenConsultaTarjetaEitvT) {
		this.resumenConsultaTarjetaEitvT = resumenConsultaTarjetaEitvT;
	}

	public String getDesasignarTasaAlDuplicar() {
		return desasignarTasaAlDuplicar;
	}

	public void setDesasignarTasaAlDuplicar(String desasignarTasaAlDuplicar) {
		this.desasignarTasaAlDuplicar = desasignarTasaAlDuplicar;
	}

	public boolean isVolverAntiguaConsulta() {
		return volverAntiguaConsulta;
	}

	public void setVolverAntiguaConsulta(boolean volverAntiguaConsulta) {
		this.volverAntiguaConsulta = volverAntiguaConsulta;
	}

	public Boolean getResumenSolicitudNRE06Flag() {
		return resumenSolicitudNRE06Flag;
	}

	public void setResumenSolicitudNRE06Flag(Boolean resumenSolicitudNRE06Flag) {
		this.resumenSolicitudNRE06Flag = resumenSolicitudNRE06Flag;
	}

	public ResumenSolicitudNRE06 getResumenSolicitudNRE06() {
		return resumenSolicitudNRE06;
	}

	public void setResumenSolicitudNRE06(ResumenSolicitudNRE06 resumenSolicitudNRE06) {
		this.resumenSolicitudNRE06 = resumenSolicitudNRE06;
	}

	public Boolean getResumenFicheroMOVE() {
		return resumenFicheroMOVE;
	}

	public void setResumenFicheroMOVE(Boolean resumenFicheroMOVE) {
		this.resumenFicheroMOVE = resumenFicheroMOVE;
	}

	public List<ResumenErroresFicheroMOVE> getListaResumenErroresFicheroMOVE() {
		return listaResumenErroresFicheroMOVE;
	}

	public void setListaResumenErroresFicheroMOVE(List<ResumenErroresFicheroMOVE> listaResumenErroresFicheroMOVE) {
		this.listaResumenErroresFicheroMOVE = listaResumenErroresFicheroMOVE;
	}
}