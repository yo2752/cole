package trafico.acciones;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.consultasTGate.model.enumerados.OrigenTGate;
import org.gestoresmadrid.core.ficheroAEAT.bean.FicheroAEATBean;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTarjetaITV;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.dao.FabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.gestoresmadrid.oegam2.utiles.UtilesVistaJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.consulta.tramite.model.service.ServicioConsultaTramiteTrafico;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans.ResultadoFicheroSolicitud05Bean;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioFicheroAEAT;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.paises.model.service.ServicioPais;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResumenPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoConsultaJustProfBean;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioCheckCTIT;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoCambioServicio;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultCambioEstadoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
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
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.trafico.view.dto.FormularioAutorizarTramitesDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResumenDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.opensymphony.xwork2.Action;

import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.BorrarFicherosRecursivoThread;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;
import escrituras.beans.ResultadoAccionUsuarioBean;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloPersona;
import exportarXML.ErrorExportacionXML;
import facturacion.comun.DatosClienteBean;
import general.acciones.AbstractFactoryPaginatedList;
import general.acciones.PaginatedListActionSkeleton;
import general.beans.RespuestaGenerica;
import general.utiles.UtilidadIreport;
import hibernate.entities.facturacion.Factura;
import hibernate.entities.general.Colegiado;
import oegam.constantes.ConstantesFirma;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import procesos.daos.BeanPQCrearSolicitud;
import trafico.avpogestbasti.TN3270.Bsti;
import trafico.avpogestbasti.TN3270.Gest;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.ResumenAsignacionMasivaTasas;
import trafico.beans.ResumenCambiosEstado;
import trafico.beans.ResumenCambiosNive;
import trafico.beans.ResumenCambiosRef;
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
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.avpobastigest.BstiBean;
import trafico.beans.avpobastigest.CargaGestBean;
import trafico.beans.avpobastigest.EmbargoGestBean;
import trafico.beans.avpobastigest.GestBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQMatricular;
import trafico.beans.daos.BeanPQTasasDesasignar;
import trafico.beans.daos.BeanPQTasasDetalle;
import trafico.beans.daos.BeanPQTramiteTraficoEliminar;
import trafico.beans.daos.pq_facturacion.TablaFacturacionBean;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCAMBIAR_ESTADO_S_V;
import trafico.beans.ivtm.ResumenAutoliquidacionIVTM;
import trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;
import trafico.beans.paginacion.ConsultaTramiteTraficoBean;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.beans.utiles.XMLBaja;
import trafico.beans.utiles.XMLDuplicado;
import trafico.beans.utiles.XMLMatw;
import trafico.beans.utiles.XMLSolicitud;
import trafico.beans.utiles.XMLTransmision;
import trafico.dto.TramiteTraficoDto;
import trafico.factoria.ConsultaTramiteTraficoFactoria;
import trafico.ivtm.servicio.ServicioIVTMMatriculacionImpl;
import trafico.ivtm.servicio.ServicioIVTMMatriculacionIntf;
import trafico.modelo.ModeloAcciones;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloDuplicado;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.modelo.ModeloTasas;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.servicio.interfaz.ServicioImprimirTraficoInt;
import trafico.utiles.ComprobadorDatosSensibles;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilesConversionesTrafico;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.UtilidadesAccionTrafico;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.Autorizacion;
import trafico.utiles.enumerados.DocumentosJustificante;
import trafico.utiles.enumerados.Estado620;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.MotivoJustificante;
import trafico.utiles.enumerados.TipoAutorizacion;
import trafico.utiles.enumerados.TipoDatoActualizar;
import trafico.utiles.enumerados.TipoDatoBorrar;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.estructuras.Fecha;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.ValidacionJustificantePorFechaExcepcion;
import utilidades.web.excepciones.ValidacionJustificanteRepetidoExcepcion;

public class ConsultaTramiteTraficoAction extends PaginatedListActionSkeleton {

	private static final long serialVersionUID = 952474274362305743L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaTramiteTraficoAction.class);

	public static final int T1 = 1;
	public static final int T2 = 2;
	public static final int T3 = 3;
	public static final int T4 = 4;
	public static final int T7 = 7;
	public static final int T8 = 8;
	public static final int T11 = 11;
	public static final int T27 = 27;
	private static final String SI = "SI";
	private static final String POSEEDOR = "poseedor";
	private static final String REPRESENTANTE_POSEEDOR = "representante_poseedor";
	private static final String PRIMER_COTITULAR = "primer cotitular";
	private static final String SEGUNDO_COTITULAR = "segundo cotitular";
	private HttpServletRequest request;
	// Resultado cuando se puede imprimir los trámites
	private static final String IMPRIMIR_ACTION = "aImprimirAction";
	private static final String IMPRESION_ACTION = "aImpresionAction";
	private static final String CLONAR_ACTION = "aClonarAction";
	private static final String ALTA_TRAMITE_TRAFICO_JUSTIFICANTE = "altaTramiteTraficoJustificante";
	private static final String POP_UP_JUSTIFICANTE_PRO = "popUpJustificanteProf";
	private static final String POP_UP_MOTIVO = "popUpMotivo";
	private static final String POP_UP_DUPLICAR_TRAMITE = "popUpDuplicarTramite";
	private static final String POP_UP_BORRAR_DATOS = "popUpBorrarDatos";
	private static final String POP_UP_ACTUALIZAR_DATOS = "popUpActualizarDatos";
	private static final String POP_UP_AUTORIZAR_TRAMITES = "popUpAutorizarTramites";
	private static final String POP_UP_ACTUALIZAR_PUEBLO = "popUpActualizarPueblo";
	// Resultado del imprimir, para pasar los parámetros al action de imprimir
	private ResultValidarTramitesImprimir resultBeanImprimir;

	private static final String PAGO_IVTM = "pagoIVTM";

	private static final String DEBE_SELECIONAR_EXPEDIENTE = "Debe de seleccionar algun expediente para consultar.";
	
	private static final String AUTORIZAR_MATW_FICHAS_A = "autorizarFichasA";
	private static final String AUTORIZAR_CTIT = "autorizarCtit";
	private static final String AUTORIZAR_MATW_EXENTO_CTR = "autorizarMatw";

	// Parámetros de entrada de parámetros
	private String tipoTramiteGenerar;
	private String numsExpediente;
	private String numExpediente;
	// Utilizado solo para imprimir. Debe eliminarse cuando se unifiquen los checks.
	private String listaExpedientes;
	private String codSeleccionados;
	private Boolean duplicado = false;
	private Boolean linkeado = false;
	private Boolean imprimirDesdeAlta = false;
	private Boolean excelDesdeAlta = false;
	private Boolean exportarDesdeAlta = false;
	private Boolean exportarXmlSesion = false;
	private Boolean existeZip = false;
	private Boolean existeDocJstfPermiso = Boolean.FALSE;
	private String motivoJustificantes;
	private String documentoJustificantes;
	private String diasValidezJustificantes;
	private String desasignarTasaAlDuplicar;
	private String nombreFicheroJustifIvtm;
	
	private String datoBorrar;
	private String datoActualizar;
	private String datoNuevo;
	private String puebloNuevo;

	private boolean volverAntiguaConsulta;

	private ResumenPermisoDistintivoItvBean resum;

	private String nombreJustfPerm;
	// Persona para recuperar el titular de facturación
	private Persona personaFac;

	private Persona persona;
	private Direccion direccion;
	private String planta;

	// Variables para el control de la validación
	private Boolean resumenValidacion = false;
	// Resumen
	private List<ResumenValidacion> resumen = new ArrayList<>();
	private List<ResumenPendienteExcel> resumenPendientes = new ArrayList<>();
	private Boolean resumenPendienteEnvioExcel = false;

	// CERTIFICADO DE TASAS
	private Boolean resumenCertificadoTasasFlag = false;
	private ResumenCertificadoTasas resumenCertificadoTasas = new ResumenCertificadoTasas();
	private ArrayList<TablaFacturacionBean> tablaFacturacionBeanLista = new ArrayList<>();
	private ResumenCertificadoTasas resumenCertificadoTasasT = new ResumenCertificadoTasas();
	int tasas = 0;
	Date inicio = null;
	Date fin = null;

	// FICHERO SOLICITUD 05
	private Boolean resumenFicheroSolicitud05Flag = false;
	private ResumenFicheroSolicitud05 resumenFicheroSolicitud05 = new ResumenFicheroSolicitud05();

	//SOLICITUD NRE06
	private Boolean resumenSolicitudNRE06Flag = false;
	private ResumenSolicitudNRE06 resumenSolicitudNRE06 = new ResumenSolicitudNRE06();

	// ASIGNACION MASIVA DE TASAS
	private ResumenAsignacionMasivaTasas resumenAsignacionMasivaTasasT = new ResumenAsignacionMasivaTasas("TOTAL");
	private Boolean resumenAsignacionMasivaTasasFlag = false;
	private List<ResumenAsignacionMasivaTasas> resumenAsignacionMasivaTasas = new ArrayList<>();

	// CAMBIOS DE ESTADO DE TRAMITES
	private Boolean resumenCambiosEstadoFlag = false;
	private List<ResumenCambiosEstado> resumenCambiosEstado = new ArrayList<>();
	private ResumenCambiosEstado resumenCambiosEstadoT = new ResumenCambiosEstado("TOTAL");
	private String valorEstadoCambio;
	// CAMBIOS DE ESTADO DE TRÁMITES

	// CAMBIOS REFERENCIA PROPIA
	private Boolean resumenCambiosRefFlag = false;
	private List<ResumenCambiosRef> resumenCambiosRef = new ArrayList<>();
	private ResumenCambiosRef resumenCambiosRefT = new ResumenCambiosRef("TOTAL");
	// CAMBIOS REFERENCIA PROPIA

	// CAMBIOS DE ESTADO LIBERACIÓN DOCUMENTO BASE NIVE
	int correcto = 0;
	private Boolean resumenCambiosFlag = false;
	private List<ResumenCambiosNive> resumenCambiosNive = new ArrayList<>();
	private ResumenCambiosNive resumenCambiosNiveT = new ResumenCambiosNive("TOTAL");
	// CAMBIOS DE ESTADO LIBERACION DOCUMENTO BASE NIVE

	// PRESENTACIÓN 576
	private Boolean resumenPresentacion576Flag = false;
	private List<ResumenPresentacion576> resumenPresentacion576 = new ArrayList<>();
	private ResumenPresentacion576 resumenPresentacion576T = new ResumenPresentacion576("TOTAL");
	int presentaciones = 0;

	// Consulta tarjeta eITV
	private Boolean resumenConsultaTarjetaEitvFlag = false;
	private List<ResumenConsultaTarjetaEitv> resumenConsultaTarjetaEitv = new ArrayList<>();
	private ResumenConsultaTarjetaEitv resumenConsultaTarjetaEitvT = new ResumenConsultaTarjetaEitv("TOTAL");
	int consultasEitvEnCola = 0;

	// EEFF
	private Boolean resumenEEFFFlag = false;
	private ResumenEEFF resumenEEFF = new ResumenEEFF();

	private List<String> mensajesValidadoPDF = new ArrayList<>();
	private List<String> mensajesValidadoTelematicamente = new ArrayList<>();

	// Variables para el control de la tramitación
	private Boolean resumenTramitacion = false;
	// Resumen
	private List<ResumenTramitacionTelematica> resumenTramitacionTelematica = new ArrayList<>();

	private String tipoTramite = null;
	private String idContratoTramites = null;
	private String idContratoSession = null;

	// Generar Permisos
	private Boolean resumenDocumentosGeneradosFlag = false;
	private Boolean resumenPermDstv = false;
	private Boolean resumenAutoBTV = false;
	private ResumenOperacionesTramiteBean resumenOpeTram;
	private ResumenDocBaseBean resumenDocBase;

	private static final String antiguoDatosSensibles = "datosSensibles.antiguo";

	private static final String cambioEstadoCreditosNuevo = "cambio.estado.creditos.nuevo";

	private TramiteTrafCambioServicioDto tramiteTraficoCambServ;

	private String nuevaReferenciaPropia;
	
	private String campo;
	
	private String interviniente;
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioEEFF servicioAntEEFF;

	@Autowired
	ServicioEEFFNuevo servicioEEFF;

	@Autowired
	ServicioImprimirTraficoInt servicioImprimir;

	@Autowired
	ServicioImpresion servicioImprimirImpl;

	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	ServicioConsultaTramiteTrafico servicioConsultaTramiteTrafico;

	@Autowired
	FabricanteDao fabricanteDao;

	@Autowired
	ServicioFicheroAEAT servicioFicheroAEAT;

	@Autowired
	ServicioCreditoFacturado servicioCreditoFacturado;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	Conversor conversor;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	ServicioTramiteBaja servicioTramiteBaja;

	@Autowired
	ServicioTramiteTraficoDuplicado servicioTramiteDuplicado;

	@Autowired
	ServicioEvolucionTramite servicioEvolucionTramite;
	
	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	boolean cierraZip = false;
	ErrorExportacionXML errorExportacionXML;
	List<ErrorExportacionXML> listaErroresExportacionXML = new ArrayList<>();
	int totalErroresXML = 0;

	private IvtmMatriculacionDto ivtmMatriculacionDto;
	private String ibanTitular = null;
	private IVTMModeloMatriculacionInterface ivtmModelo = null;
	private ServicioIVTMMatriculacionIntf servicioIVTM;
	private boolean resumenAutoliquidacionIVTMFlag = false;
	private ResumenAutoliquidacionIVTM resumenTotalIVTM = new ResumenAutoliquidacionIVTM("TOTAL");

	private BeanPQTasasDetalle resultadoCodigoTasa;
	// Persona para recuperar el titular de facturación
	private Factura factura;

	// Sirve para recuperar datos de facturación
	private DatosClienteBean datosCliente;

	private Boolean resumenFicheroAEAT = false;
	private Boolean resumenLiquidacionNRE06 = false;
	private Boolean resumenFicheroMOVE = false;
	// Lista de Expedientes para mostrar en el Fichero de AEAT
	private List<FicheroAEATBean> listaFicherosAEATBean = new ArrayList<>();
	private List<ResumenErroresFicheroAEAT> listaResumenErroresFicheroAEAT = new ArrayList<>();
	private List<ResumenErroresFicheroMOVE> listaResumenErroresFicheroMOVE = new ArrayList<>();
	private Boolean imprimirFichero = false;
	private TramiteTrafTranDto tramiteTrafTranDto;
	private TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto;
	private TramiteTrafDto tramiteTrafico;
	private int numPeticiones;
	private String tipoTramiteJustificante;

	private DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean;
	/*
	 * Elementos comunes a todos los action de listas, tales como la página actual (para la paginación), si esta ordenado por una columna, si el ordenamiento es ascendente o descendente, la cantidad de elemntos a mostrar por página, entre otros
	 */
	private static final String COLUMDEFECT = "numExpediente";

	private static final String CONSULTA_TRAMITE_TRAFICO_RESULT = "consultaTramiteTrafico";

	private static final String EXPORTAR_TRAMITE = "exportarTramite";

	// Parámetros generales de los listar
	TramiteTraficoDto tramiteTraficoDto = new TramiteTraficoDto();
	ModeloTramiteTrafInterface modeloTramiteTraf = new ModeloTramiteTrafImpl();
	private BigDecimal numCreditosTotales;
	private BigDecimal numCreditosDisponibles;
	private BigDecimal numCreditosBloqueados;
	private Boolean impresoEspera = false; // Booleano que nos permite controlar si hay una impresión que realizar
	private ParametrosPegatinaMatriculacion etiquetaParametros; // Para los parámetros por defecto de las etiquetas o si tienen alguno guardado en la configuración.

	Boolean electronica = false; // variable para controlar las transmisiones electronicas al imprimir.

	private PaginatedList listaAcciones;// PaginatedList con las acciones que se han realizado sobre un trámite

	// Trámite Tráfico
	private TramiteTraficoBean tramiteTraficoBean;
	// T1-Trámite Tráfico Matriculación
	private TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean;
	private IntervinienteTrafico presentadorSoloAdmin;
	// T2-Trámite Tráfico Transmisión
	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;
	// T4-Trámite Tráfico Solicitud de datos
	private SolicitudDatosBean solicitud;
	// T8-Trámite Duplicado
	private TramiteTraficoDuplicadoBean tramiteTraficoDuplicado;

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir

	private String propTexto;

	// Para indicar al struts xml el nombre del fichero de forma dinámica:
	private String ficheroDescarga;

	private ModeloTrafico modeloTrafico;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloAcciones modeloAcciones;
	private ModeloDuplicado modeloDuplicado;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;
	private ModeloMatriculacion modeloMatriculacion;
	private ModeloSolicitudDatos modeloSolicitudDatos;
	private ModeloTasas modeloTasas;
	private ModeloTransmision modeloTransmision;
	private ModeloSolicitud modeloSolicitud;
	private UtilidadIreport utilidadIreport;

	@Autowired
	private ServicioTramiteTraficoBaja servicioTramiteTraficoBaja;

	@Autowired
	private ServicioTramiteTraficoCambioServicio servicioTramiteTraficoCambioServicio;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculoImpl;

	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired 
	ServicioCheckCTIT servicioCheckCtit;
	
	@Autowired
	ServicioPais servicioPais;

	@Autowired
	UtilesColegiado utilesColegiado;

	private FormularioAutorizarTramitesDto formularioAutorizarTramitesDto;

	private boolean generadoNRE06;

	private String url;
	private boolean errorPagoIVTM;
	private String respuestaPagoIVTM;

	private String tipoTramiteSeleccionado;
	private String estadoTramiteSeleccionado;
	private String bastidorSeleccionado;
	private String comboTasaBloqueado;
	private String codigoTasaTransmisionSeleccionadoId;

	private boolean generado05;
	private boolean mostrarBtnGest;
	
	private boolean esTransporteBasura;
	private boolean esTransporteDinero;
	private boolean esVelMaxAutorizada;
	private boolean esVehUnidoMaquina;
	private boolean esEmpresaASC;
	private boolean esMinistCCEntLocal;
	private boolean esAutoescuela;
	private boolean esCompraventaVeh;
	private boolean esVivienda;
	
	private List<Autorizacion> listaAutorizados;
	private String valorSeleccionado;
	private String valorAdicional;
	
	// MÉTODOS

	/**
	 * Método que se ejecuta al principio de la consulta, que inicializará los parámetros de búsqueda.
	 */

	public String inicio() {
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		return super.inicio();
	}

	public TramiteTraficoDuplicadoBean getTramiteTraficoDuplicado() {
		return tramiteTraficoDuplicado;
	}

	public void setTramiteTraficoDuplicado(TramiteTraficoDuplicadoBean tramiteTraficoDuplicado) {
		this.tramiteTraficoDuplicado = tramiteTraficoDuplicado;
	}

	/**
	 * Método para validar trámites en bloque independientemente del tipo que sean.
	 * @return
	 * @throws ParseException
	 */
	public String validar() throws ParseException {
		try {
			recargarPaginatedList();

			// -- Cambios del check
			String[] codSeleccionados = null;
			if (getListaExpedientes() != null && !getListaExpedientes().isEmpty()) {
				codSeleccionados = getListaExpedientes().replaceAll(" ", "").split(",");
				setNumsExpediente(getListaExpedientes());
			} else if (getNumsExpediente() != null && !getNumsExpediente().isEmpty()) {
				codSeleccionados = getNumsExpediente().replaceAll(" ", "").split(",");
			} else {
				codSeleccionados = new String[1];
				codSeleccionados[0] = getNumExpediente();
				setNumsExpediente(getNumExpediente());
			}

			String numeroMaxValidar = gestorPropiedades.valorPropertie("maximo.numero.validar");
			if (numeroMaxValidar != null && codSeleccionados != null && codSeleccionados.length > Integer.parseInt(numeroMaxValidar)) {
				addActionError("No puede validar más de " + numeroMaxValidar + " trámites.");
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			}

			TramiteTraficoBean linea = null;
			BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();

			ResultBean resultadoValidacion = new ResultBean();
			ResumenValidacion resumenMatriculacion = new ResumenValidacion(TipoTramiteTrafico.Matriculacion.getNombreEnum());
			ResumenValidacion resumenTransmision = new ResumenValidacion(TipoTramiteTrafico.Transmision.getNombreEnum());
			ResumenValidacion resumenBaja = new ResumenValidacion(TipoTramiteTrafico.Baja.getNombreEnum());
			ResumenValidacion resumenSolicitud = new ResumenValidacion(TipoTramiteTrafico.Solicitud.getNombreEnum());
			ResumenValidacion resumenDuplicado = new ResumenValidacion(TipoTramiteTrafico.Duplicado.getNombreEnum());
			ResumenValidacion resumenCambioServicio = new ResumenValidacion(TipoTramiteTrafico.CambioServicio.getNombreEnum());
			ResumenValidacion resumenTotal = new ResumenValidacion("TOTAL");

			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Validar en bloque.");
			resumenValidacion = true;

			// Comprobamos que tenga algún tipo de permiso de manteniemiento
			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			}
			ResultBean rTipoTramites = servicioTramiteTrafico.getTipoTramite(codSeleccionados);

			String habilitarMatriculacion = gestorPropiedades.valorPropertie("habilitar.matriculacion.nueva");
			String habilitarPQCtit = gestorPropiedades.valorPropertie("habilitar.pq.validar.ctit");

			for (int i = 0; i < codSeleccionados.length; i++) {
				if (TipoTramiteTrafico.Baja.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
					String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
					if (SI.equals(nuevasBajas)) {
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
					} else {
						ResultBean resultado = servicioTramiteTraficoBaja.validarTramite(new BigDecimal(codSeleccionados[i]), utilesColegiado.tienePermisoBTV());
						if (resultado.getError()) {
							String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
							for (String error : resultado.getListaMensajes()) {
								mensajeError += "- " + error;
							}
							addActionError(mensajeError);
							resumenBaja.addFallido();
						} else {
							String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
							String motivoBaja = (String) resultado.getAttachment(ServicioTramiteTraficoBaja.MOTIVO_BAJA);
							if (MotivoBaja.DefE.getValorEnum().equals(motivoBaja) || MotivoBaja.DefTC.getValorEnum().equals(motivoBaja)) {
								Long estado = (Long) resultado.getAttachment(ServicioTramiteTraficoBaja.ESTADO_VALIDAR);
								if (Long.valueOf(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(estado)) {
									resumenBaja.addValidadoTelematicamente();
									mensajeValidado += "Telemáticamente.";
									mensajesValidadoTelematicamente.add(mensajeValidado);
								} else {
									resumenBaja.addValidadoPDF();
									mensajesValidadoPDF.add(mensajeValidado);
								}
							} else {
								resumenBaja.addValidadoPDF();
								mensajesValidadoPDF.add(mensajeValidado);
								validarCetInformado(resultado.getListaMensajes());
							}
							addActionMessage(mensajeValidado);
						}
					}
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
					TramiteTrafDuplicadoDto tramiteTrafico = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(codSeleccionados[i]), true);
					ResultBean resultado = servicioTramiteTraficoDuplicado.validarTramite(tramiteTrafico);
					if (resultado.getError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
						for (String error : resultado.getListaMensajes()) {
							mensajeError += "- " + error;
						}
						addActionError(mensajeError);
						resumenDuplicado.addFallido();
					} else  {
							Long estado = (Long) resultado.getAttachment(ServicioTramiteTraficoDuplicado.ESTADO_VALIDAR);
							String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
							if(Long.valueOf(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(estado)){
								resumenDuplicado.addValidadoTelematicamente();
								mensajesValidadoTelematicamente.add(mensajeValidado);
							}else {
								resumenDuplicado.addValidadoPDF();
								mensajesValidadoPDF.add(mensajeValidado);
						}
							addActionMessage(mensajeValidado);
					}
				} else if (TipoTramiteTrafico.CambioServicio.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
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
				} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i])) && SI.equals(habilitarMatriculacion)) {
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
						} else if(Long.valueOf(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()).equals(estado)){
							mensajeValidado += "Pendiente Autorización.";
							resumenMatriculacion.addValidadoTelematicamente();
							mensajesValidadoTelematicamente.add(mensajeValidado);
							validarVarios(resultado.getListaMensajes());
							
						}else {
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
				} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i])) && SI.equals(habilitarPQCtit)) {
					TramiteTrafTranDto tramite = servicioTramiteTraficoTransmision.getTramiteTransmision(new BigDecimal(codSeleccionados[i]), true);
					ResultBean resultado = servicioTramiteTraficoTransmision.validarTramite(tramite, idUsuario.longValue());
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
						
						TramiteTrafTranVO trafTranVOBBDD = servicioTramiteTraficoTransmision.getTramite(tramite.getNumExpediente(),Boolean.FALSE);
						if(trafTranVOBBDD != null) {
							servicioTramiteTraficoTransmision.actualizarTramiteCtit(trafTranVOBBDD,tramite, estado,idUsuario);
						}
						
						if (Long.valueOf(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(estado)) {
							mensajeValidado += "Telemáticamente.";
							resumenTransmision.addValidadoTelematicamente();
							mensajesValidadoTelematicamente.add(mensajeValidado);
							if (resultado.getListaMensajes() != null) {
								validarCetInformado(resultado.getListaMensajes());
								validarVarios(resultado.getListaMensajes());
							}
						} else if (Long.valueOf(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum()).equals(estado)) {
							mensajeValidado += "Pendiente Autorización.";
							resumenTransmision.addValidadoTelematicamente();
							mensajesValidadoTelematicamente.add(mensajeValidado);
							if (resultado.getListaMensajes() != null) {
								validarCetInformado(resultado.getListaMensajes());
								validarVarios(resultado.getListaMensajes());
							}
						}else {
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
					linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));
					resultadoValidacion = new ResultBean();

					if (linea == null) {
						if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
							resumenMatriculacion.addFallido();
							resumen.add(resumenMatriculacion);
						} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
							resumenTransmision.addFallido();
							resumen.add(resumenTransmision);
						} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
							resumenTransmision.addFallido();
							resumen.add(resumenTransmision);
						} else if (TipoTramiteTrafico.Solicitud.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
							resumenSolicitud.addFallido();
							resumen.add(resumenSolicitud);
						}
						// Resumen con todos los mensajes a mostrar
						resumenTotal.setNumFallidos(resumenMatriculacion.getNumFallidos() + resumenTransmision.getNumFallidos() + resumenSolicitud.getNumFallidos());
						resumenTotal.setNumValidadosPDF(resumenMatriculacion.getNumValidadosPDF() + resumenTransmision.getNumValidadosPDF() + resumenSolicitud.getNumValidadosPDF());
						resumenTotal.setNumValidadosTele(resumenMatriculacion.getNumValidadosTele() + resumenTransmision.getNumValidadosTele() + resumenSolicitud.getNumValidadosTele());
						resumen.add(resumenTotal);
						addActionError("No se ha encontrado el número de expediente");
						return CONSULTA_TRAMITE_TRAFICO_RESULT;
					}

					ResultBean resultadoValidable = UtilesVistaTrafico.getInstance().tipoEstadoValidable(linea.getEstado(), linea.getTipoTramite());
					if (resultadoValidable.getError()) {
						resultadoValidacion.setError(true);
						resultadoValidacion.setMensaje("No validable: " + resultadoValidable.getMensaje());
					} else {
						// Aquí va a venir toda la lógica de las validaciones del trámite.
						resultadoValidacion = getModeloTrafico().validaTramiteGenerico(linea, utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
					}

					if (resultadoValidacion.getError()) {
						String mensajeError = "Trámite " + codSeleccionados[i] + " fallo en la validación: ";
						for (String error : resultadoValidacion.getListaMensajes()) {
							mensajeError += "- " + error;
						}
						addActionError(mensajeError);
						if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()))
							resumenMatriculacion.addFallido();
						if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()) || TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(linea
								.getTipoTramite().getNombreEnum()))
							resumenTransmision.addFallido();
						if (TipoTramiteTrafico.Solicitud.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()))
							resumenSolicitud.addFallido();
					} else {
						String mensajeValidado = "Trámite " + codSeleccionados[i] + " validado ";
						if (resultadoValidacion.getMensaje().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())) {
							mensajeValidado += "Telemáticamente.";
							if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()))
								resumenMatriculacion.addValidadoTelematicamente();
							if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()) || TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(linea
									.getTipoTramite().getNombreEnum()))
								resumenTransmision.addValidadoTelematicamente();
							if (TipoTramiteTrafico.Solicitud.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()))
								resumenSolicitud.addValidadoTelematicamente();

							mensajesValidadoTelematicamente.add(mensajeValidado);

							/*
							 * @author: Santiago Cuenca Mantis: 1632. Añadir mensaje informativo (no restrictivo) cuando no se indique la provincia del CET
							 */
							for (int x = 0; x < resultadoValidacion.getListaMensajes().size(); x++) {
								if (resultadoValidacion.getListaMensajes().get(x).equalsIgnoreCase(ConstantesTrafico.LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA)) {
									mensajeValidado = ConstantesTrafico.LA_PROVINCIA_DEL_CET_DEBERIA_SER_INFORMADA;
									mensajesValidadoTelematicamente.add(mensajeValidado);
								}
							}
							// MANTIS.- PARA AGREGAR EL MENSAJE INFORMATIVO DE LOS 26 CARACTERES
							if (resultadoValidacion.getListaMensajes() != null) {
								for (String mensaje : resultadoValidacion.getListaMensajes()) {
									if (mensaje.contains(ConstantesTrafico.AVISO_MAXIMA_LONGITUD_DIRECCION_VEHICULO)) {
										addActionMessage("- " + mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									} else if (mensaje.contains(ConstantesTrafico.AVISO_VALIDACION_WMI)) {
										mensaje = mensaje.replace("--", "- ");
										addActionMessage(mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									} // 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
									else if (mensaje.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
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
									} // FIN 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
								}
							}
						} else { // Estado != Validado Telematicamente
							if (linea.getTipoTramite().getNombreEnum().equals(TipoTramiteTrafico.Transmision.getNombreEnum())) {
								mensajeValidado += "PDF.";
							} else {
								mensajeValidado += "PDF. Limitaciones para la validación telemática: ";
							}

							for (String error : resultadoValidacion.getListaMensajes()) {
								// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
								if (!error.contains("AVISO") && !error.contains("Aviso")) {
									mensajeValidado += "- " + error;
								}
								// mensajeValidado += "- " + error;
								// FIN 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
							}
							if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()))
								resumenMatriculacion.addValidadoPDF();
							if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()) || TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(linea
									.getTipoTramite().getNombreEnum()))
								resumenTransmision.addValidadoPDF();
							if (TipoTramiteTrafico.Solicitud.getNombreEnum().equals(linea.getTipoTramite().getNombreEnum()))
								resumenSolicitud.addValidadoPDF();

							mensajesValidadoPDF.add(mensajeValidado);
							// MANTIS.- PARA AGREGAR EL MENSAJE INFORMATIVO DE LOS 26 CARACTERES
							if (resultadoValidacion.getListaMensajes() != null) {
								for (String mensaje : resultadoValidacion.getListaMensajes()) {
									if (mensaje.contains(ConstantesTrafico.AVISO_MAXIMA_LONGITUD_DIRECCION_VEHICULO)) {
										addActionMessage("- " + mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									} else if (mensaje.contains(ConstantesTrafico.AVISO_VALIDACION_WMI)) {
										mensaje = mensaje.replace("--", "- ");
										addActionMessage(mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									}
									// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
									else if (mensaje.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
										mensaje = mensaje.replace("--", "- ");
										addActionMessage(mensaje);
										if (mensajesValidadoTelematicamente != null) {
											mensajesValidadoTelematicamente.add(mensaje);
										}
									}
									// FIN 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
								}
							}
						}
						addActionMessage(mensajeValidado);
					}
				}
			}

			// Resumen con todos los mensajes a mostrar
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

			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		} catch (Throwable th) {
			log.error("ERROR: ", th);
			addActionError(th.toString());
			return Action.ERROR;
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
				} else if (mensaje.contains(ConstantesTrafico.AVISO_VALIDACION_WMI)) {
					mensaje = mensaje.replace("--", "- ");
					addActionMessage(mensaje);
					if (mensajesValidadoTelematicamente != null) {
						mensajesValidadoTelematicamente.add(mensaje);
					}
				} else if (mensaje.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
					mensaje = mensaje.replace("--", "- ");
					addActionMessage(mensaje);
					if (mensajesValidadoTelematicamente != null) {
						mensajesValidadoTelematicamente.add(mensaje);
					}
				}
			}
		}
	}

	/**
	 * Método del action para eliminar los trámites que se hayan seleccionado de la pantalla de consultar tramites.
	 * @return
	 * @throws ParseException
	 */
	public String eliminar() throws ParseException {
		recargarPaginatedList();
		TramiteTraficoBean linea = null;
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		RespuestaGenerica resultado = null;
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "eliminar.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		}

		for (int i = 0; i < codSeleccionados.length; i++) {
			linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

			if (linea == null) {
				addActionError("No se ha encontrado el número de expediente");
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			}

			BeanPQTramiteTraficoEliminar beanEliminar = new BeanPQTramiteTraficoEliminar();
			beanEliminar.setP_NUM_EXPEDIENTE(linea.getNumExpediente());
			beanEliminar.setP_ESTADO(new BigDecimal(linea.getEstado().getValorEnum()));

			beanEliminar.setP_ID_USUARIO(new BigDecimal(utilesColegiado.getIdUsuarioSession()));
			beanEliminar.setP_FECHA_ULT_MODIF(utilesFecha.getFechaActual().getTimestamp());

			resultado = getModeloTrafico().eliminarTramiteTrafico(beanEliminar);

			BigDecimal pCodeTramite = (BigDecimal) resultado.getParametro(ConstantesPQ.P_CODE);
			if (resultado == null || utiles.convertirBigDecimalAInteger(pCodeTramite) != 0) {
				addActionError("Error al eliminar trámite " + beanEliminar.getP_NUM_EXPEDIENTE() + ": " + (String) resultado.getParametro(ConstantesPQ.P_SQLERRM));
			} else {
				addActionMessage("Se ha eliminado el trámite " + beanEliminar.getP_NUM_EXPEDIENTE());
			}

		}
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	/**
	 * Función que buscará el tipo de detalle que es, accederá a base de datos y obtendrá los datos correspondientes dependiendo del tipo de trámite que sea.
	 * @return
	 * @throws ParseException
	 */
	public String detalle() {
		try {
			propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;

			String[] arrayExpediente = { numExpediente };
			ResultBean resultado = servicioTramiteTrafico.getTipoTramite(arrayExpediente);

			tipoTramite = (String) resultado.getAttachment(numExpediente);

			Map<String, Object> resultadoMetodo = new HashMap<String, Object>();
			String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
			TramiteTraficoBean linea = null;

			String habilitarMatriculacion = gestorPropiedades.valorPropertie("habilitar.matriculacion.nueva");
			String habilitarSolicitud = gestorPropiedades.valorPropertie(PropertiesConstantes.SERVICIO_SOL_INFORMACION_NUEVO);

			if (!TipoTramiteTrafico.Baja.getValorEnum().equals(tipoTramite) && !(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite) && SI.equals(habilitarMatriculacion))
					&& !(TipoTramiteTrafico.Solicitud.getValorEnum().equals(tipoTramite) && SI.equalsIgnoreCase(habilitarSolicitud)) && !(TipoTramiteTrafico.Duplicado.getValorEnum().equals(
							tipoTramite))) {
				linea = new TramiteTraficoBean(true);
				// Incorporamos la obtención de acciones por trámite
				listaAcciones = UtilidadesAccionTrafico.getInstance().generarListaAcciones(numExpediente);

				// Recupera si tiene titular facturación los detalles:
				String nifFacturacion = getNifFacturacion_NUM_EXPEDIENTE(numExpediente);
				String numColegiadoExpediente = numExpediente.substring(0, 4);
				personaFac = ModeloPersona.obtenerDetallePersonaCompleto(nifFacturacion, numColegiadoExpediente);

				// Fin Acciones
				tramiteTraficoBean = new TramiteTraficoBean(true);
				tramiteTraficoBean.setNumExpediente(new BigDecimal(numExpediente));
				log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "detalle.");

				if (!duplicado && !linkeado) {
					linea = obtenerBeanConNumExpediente(tramiteTraficoBean.getNumExpediente());
					if (linea == null) {
						// Mantis 11754. David Sierra
						addActionError("El número de expediente introducido se encuentra Anulado. Para visualizar un expediente, " + "este debe estar al menos en estado Iniciado.");
						// Fin Mantis
						return donde;
					}
					tipoTramite = linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null;
				}
				if (duplicado || linkeado) {
					linea.setNumExpediente(new BigDecimal(numExpediente));
				}

			}
			// Deberemos llamar dependiendo del tipo de trámite que tenga.
			switch (Integer.parseInt(tipoTramite.substring(1))) {
				// Matriculación
				case T1: {
					if (habilitarMatriculacion != null && SI.equals(habilitarMatriculacion)) {
						donde = "altaTramiteMatriculacion";
					} else {
						traficoTramiteMatriculacionBean = new TramiteTraficoMatriculacionBean();
						presentadorSoloAdmin = new IntervinienteTrafico();

						if (getModeloTrafico().isMatw(Long.valueOf(linea.getNumExpediente().toString()))) {
							resultadoMetodo = getModeloMatriculacion().obtenerDetalle(linea.getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
							traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
							// TODO MPC. Cambio IVTM.
							// Cargamos ivtm
							ivtmMatriculacionDto = getIvtmModelo().buscarIvtmNumExp(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
							// TODO En un futuro se cambiará por el del titular.
							ibanTitular = getIvtmModelo().getIbanTitular(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
							donde = "altasMatriculacionMatw";
						} else {
							resultadoMetodo = getModeloMatriculacion().obtenerDetalle(linea.getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
							traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
							// TODO MPC. Cambio IVTM.
							// Cargamos ivtm
							ivtmMatriculacionDto = getIvtmModelo().buscarIvtmNumExp(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
							donde = "altasMatriculacion";
						}
						ModeloColegiado modeloColegiado = new ModeloColegiado();
						presentadorSoloAdmin.setPersona(modeloColegiado.obtenerColegiadoCompleto(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato() != null
								? traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal()));
						presentadorSoloAdmin.setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());
						if (traficoTramiteMatriculacionBean.getCheckJustifIvtm() != null && traficoTramiteMatriculacionBean.getCheckJustifIvtm().equals("S")) {
							ResultadoMatriculacionBean salida = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()
									.toString());
							if (salida.getFichero() != null) {
								setNombreFicheroJustifIvtm(ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString()
										+ ConstantesGestorFicheros.EXTENSION_PDF);
							}
						}
					}
				}
					break;
				// Transmisión
				case T2: {
					tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
					resultadoMetodo = getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
					tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					donde = "altasTramiteTransmisionActual";
				}
					break;
				// Transmisión Electrónica
				case T7: {
					tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
					resultadoMetodo = getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
					// JRG: Necesito hacer esto para que el valor de idProvincia sean 2 dígitos.
					// La solución buena es cambiar el tipo de dato de la base de datos a String (ahora es BigDecimal).
					TramiteTraficoTransmisionBean tramiteTraficoTransmisionBeanTemp = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					Integer intProvincia = Integer.parseInt(tramiteTraficoTransmisionBeanTemp.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().getIdProvincia());
					if (intProvincia < 10 && intProvincia > 0) {
						tramiteTraficoTransmisionBeanTemp.getTramiteTraficoBean().getVehiculo().getProvinciaPrimeraMatricula().setIdProvincia("0" + intProvincia);
					}
					//
					tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBeanTemp;
					donde = "altasTramiteTransmision";
				}
					break;
				// Bajas
				case T3: {
					donde = "altaTramiteBaja";
				}
					break;
				// Duplicados
				case T8: {
					donde = "altaTramiteDuplicado";
				}
					break;
				// Cambio de servicio
				case T27: {
					tramiteTraficoCambServ = new TramiteTrafCambioServicioDto();
					tramiteTraficoCambServ.setNumExpediente(new BigDecimal(numExpediente));
					donde = "altaTramiteCambioServicio";
				}
					break;
				// Solicitudes
				case T4: {
					if (SI.equalsIgnoreCase(gestorPropiedades.valorPropertie(PropertiesConstantes.SERVICIO_SOL_INFORMACION_NUEVO))) {
						donde = "detalleSolicitudInformacion";
						break;
					}
					solicitud = new SolicitudDatosBean(true);
					resultadoMetodo = getModeloSolicitudDatos().obtenerDetalle(linea.getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
					solicitud = (SolicitudDatosBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
					// Método de Rocio de conversión de "resultado" en "solicitud"
					getSession().put("listaSolicitud", solicitud.getSolicitudesVehiculos());
					log.debug("altasSolicitud" + linea.getNumExpediente() + "Y se meterá por el tipo de trámite T4");
					try {
						existeZip = existeFicheroAVPO(linea.getNumExpediente());
					} catch (OegamExcepcion e) {
						log.error(e);
					}
					donde = "altaSolicitud";
				}
					break;
				// No tiene tipo, se define el error y se redirecciona a la pagina de consulta
				default: {
					addActionError("Este trámite no tiene asignado ningún tipo");
					log.debug("Este trámite no tiene asignado ningún tipo");
				}
			}

			docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(new BigDecimal(numExpediente));
			return donde;
		} catch (Throwable ex) {
			addActionError(ex.toString());
			log.error(ex);
			return Action.ERROR;
		}
	}

	private Boolean existeFicheroAVPO(BigDecimal numExp) throws OegamExcepcion {
		if (numExp == null) {
			return false;
		}
		FileResultBean result = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.SOLICITUD_INFORMACION, null, Utilidades.transformExpedienteFecha(numExp), numExp.toString());
		return result != null && result.getFiles() != null && !result.getFiles().isEmpty();
	}

	// TODO MPC. Cambio IVTM.
	public String detalleVueltaPago() {
		if (respuestaPagoIVTM != null && !respuestaPagoIVTM.isEmpty()) {
			if (errorPagoIVTM) {
				addActionError(respuestaPagoIVTM);
			} else {
				addActionMessage(respuestaPagoIVTM);
			}
		}
		return detalle();
	}

	/**
	 * Método del action que duplicará un action que le pasaremos como parámetro el número de expediente.
	 * @return
	 * @throws Throwable
	 */
	public String duplicar() throws Throwable {
		String duplicarNuevo = gestorPropiedades.valorPropertie("duplicar.tramites");
		String resultadoDesasignarTasa;
		if (SI.equals(duplicarNuevo)) {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultBean resultado = servicioConsultaTramiteTrafico.duplicarTramite(utiles.convertirStringArrayToBigDecimal(codSeleccionados), utilesColegiado.getIdUsuarioSessionBigDecimal(), getDesasignarTasaAlDuplicar());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage(resultado.getMensaje() + " del expediente:  " + codSeleccionados[0]);
				}
			}
		} else {
			recargarPaginatedList();
			try {
				log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " duplicar.");
				log.info("duplicar tramite");

				String[] codSeleccionados = prepararExpedientesSeleccionados();
				if (codSeleccionados.length != 1) {
					addActionError("Los trámites se deben duplicar de 1 en 1.");
					return CONSULTA_TRAMITE_TRAFICO_RESULT;
				}
				numExpediente = codSeleccionados[0];

				// Comprobamos que tenga permiso de mantenimiento.
				/*
				 * if (!utilesColegiado.tienePermisoMantenimientoTrafico()) { addActionError("No tienes permiso para realizar esta acción."); return CONSULTA_TRAMITE_TRAFICO_RESULT; }
				 */

				TramiteTraficoBean linea = obtenerBeanConNumExpediente(new BigDecimal(numExpediente));

				if (linea == null) {
					addActionError("No se ha encontrado el número de expediente");
					return CONSULTA_TRAMITE_TRAFICO_RESULT;
				}

				// Si el trámite no es de matriculación, baja o de transmisión electrónica, o no está en estado FINALIZADO_PDF --> no puede duplicar.
				if (!(linea.getTipoTramite() == TipoTramiteTrafico.TransmisionElectronica || linea.getTipoTramite() == TipoTramiteTrafico.Matriculacion || linea
						.getTipoTramite() == TipoTramiteTrafico.Baja) || linea.getEstado() != EstadoTramiteTrafico.Finalizado_PDF) {
					addActionError("No se permite duplicar un trámite que no sea de transmisión electrónica, baja o matriculación, y que no esté en estado Finalizado PDF");
					return CONSULTA_TRAMITE_TRAFICO_RESULT;
				}
				// if (!utilesColegiado.tienePermisoAdmin() && linea.getTipoTramite() != TipoTramiteTrafico.Matriculacion || linea.getTipoTramite() != TipoTramiteTrafico.TransmisionElectronica) {
				// addActionError("No tiene permiso para modificar este trámite");
				// return CONSULTA_TRAMITE_TRAFICO_RESULT;
				// }
				if (linea.getTipoTramite() == TipoTramiteTrafico.Matriculacion && !getModeloTrafico().isMatw(new Long(numExpediente))) {
					addActionError("No se puede modificar una matriculación que no sea de MATW");
					return CONSULTA_TRAMITE_TRAFICO_RESULT;
				}

				TramiteTraficoBean tramiteTraficoBean = new TramiteTraficoBean(true);
				tramiteTraficoBean.setNumExpediente(new BigDecimal(numExpediente));
				BeanPQCAMBIAR_ESTADO_S_V beanCambEstado = UtilesConversionesTrafico.convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(tramiteTraficoBean);
				beanCambEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Modificado_Peticion.getValorEnum()));
				beanCambEstado.execute();
				if (!beanCambEstado.getP_CODE().toString().equals("0")) {
					addActionError("El expediente: " + beanCambEstado.getP_NUM_EXPEDIENTE() + " no se ha podido modificar. " + beanCambEstado.getP_SQLERRM());
					log.error(beanCambEstado.getP_SQLERRM());
					return CONSULTA_TRAMITE_TRAFICO_RESULT;
				}
				beanCambEstado.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
				beanCambEstado.execute();
				if (!beanCambEstado.getP_CODE().toString().equals("0")) {
					addActionError("El expediente: " + beanCambEstado.getP_NUM_EXPEDIENTE()
					+ " se ha cambiado, pero se ha quedado en un estado no modificable. Contacte con el CAU para resolver este problema. " + beanCambEstado.getP_SQLERRM());
					log.error(beanCambEstado.getP_SQLERRM());
					return CONSULTA_TRAMITE_TRAFICO_RESULT;
				}
				addActionMessage("El expediente: " + beanCambEstado.getP_NUM_EXPEDIENTE() + " se ha iniciado correctamente a petición del colegiado");

				if (SI.equalsIgnoreCase(getDesasignarTasaAlDuplicar())) {
					if (linea.getTasa() != null && linea.getTasa().getCodigoTasa() != null && !linea.getTasa().getCodigoTasa().isEmpty()) {
						resultadoDesasignarTasa = desasignarTasa(linea.getTasa().getCodigoTasa());
						if (resultadoDesasignarTasa.equalsIgnoreCase("OK")) {
							addActionMessage("Se ha desasignado la tasa " + linea.getTasa().getCodigoTasa() + " del expediente " + numExpediente);
						} else {
							addActionError("Ha existido un error al desasignar la tasa " + linea.getTasa().getCodigoTasa() + " en el expediente " + numExpediente + ": " + resultadoDesasignarTasa);
						}
					}
					if (linea.getTipoTramite() == TipoTramiteTrafico.TransmisionElectronica) {
						Map<String, Object> resultadoMetodo = getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
						TramiteTraficoTransmisionBean tramiteTraficoTransmisionBeanTemp = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						if (tramiteTraficoTransmisionBeanTemp != null && tramiteTraficoTransmisionBeanTemp.getCodigoTasaCambioServicio() != null && !tramiteTraficoTransmisionBeanTemp
								.getCodigoTasaCambioServicio().isEmpty()) {
							resultadoDesasignarTasa = desasignarTasa(tramiteTraficoTransmisionBeanTemp.getCodigoTasaCambioServicio());
							if (resultadoDesasignarTasa.equalsIgnoreCase("OK")) {
								addActionMessage("Se ha desasignado la tasa de cambio de servicio " + tramiteTraficoTransmisionBeanTemp.getCodigoTasaCambioServicio() + " del expediente " + numExpediente + ": " + resultadoDesasignarTasa);
							} else {
								addActionError("Ha habido un error desasignando la tasa de cambio de servicio " + tramiteTraficoTransmisionBeanTemp.getCodigoTasaCambioServicio() + " del expediente "
										+ numExpediente);
							}
						}
						if (tramiteTraficoTransmisionBeanTemp != null && tramiteTraficoTransmisionBeanTemp.getCodigoTasaInforme() != null && !tramiteTraficoTransmisionBeanTemp.getCodigoTasaInforme()
								.isEmpty()) {
							resultadoDesasignarTasa =  desasignarTasa(tramiteTraficoTransmisionBeanTemp.getCodigoTasaInforme());
							if (resultadoDesasignarTasa.equalsIgnoreCase("OK")) {
								addActionMessage("Se ha desasignado la tasa de informe " + tramiteTraficoTransmisionBeanTemp.getCodigoTasaInforme() + " del expediente " + numExpediente);
							} else {
								addActionError("Ha habido un error desasignando la tasa del informe " + tramiteTraficoTransmisionBeanTemp.getCodigoTasaInforme() + " del expediente " + numExpediente + ": " + resultadoDesasignarTasa);
							}
						}
					}
				}
			} catch (Exception e) {
				addActionError("Ha ocurrido un error. Vuelva a intentarlo más tarde.");
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			}
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String abrirDuplicarDesasignarTasa() {
		return POP_UP_DUPLICAR_TRAMITE;
	}

	private String desasignarTasa(String codigoTasa) {
		BeanPQTasasDesasignar beanDesasignar = new BeanPQTasasDesasignar();
		beanDesasignar.setP_CODIGO_TASA(codigoTasa);
		beanDesasignar.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
		beanDesasignar.setP_ID_CONTRATO(utilesColegiado.getIdContratoSessionBigDecimal());
		beanDesasignar.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanDesasignar.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		return getModeloTasas().desasignarTasa(beanDesasignar);
	}

	/**
	 * Función del action para imprimir un trámite, para el que se pase el número de expediente. Redirigirá a la página de impresión seleccionando el trámite indicado.
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	public String imprimir() throws Throwable {
		recargarPaginatedList();
		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		}
		String pagina = null;
		// Ponemos los números de expediente para que el otro action los recoga. Cuando se unifique, se debe cambiar.
		String[] codSeleccionados = prepararExpedientesSeleccionados();

		ResultValidarTramitesImprimir resultValidar = null;
		resultValidar = servicioImprimirImpl.validarTramitesPrevioAPermitirImprimir(codSeleccionados, utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
		if (resultValidar.getError()) {
			List<String> errores = resultValidar.getListaMensajes();
			if (errores != null) {
				for (int i = 0; i < errores.size(); i++) {
					addActionError(errores.get(i));
				}
			}
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		}
		pagina = IMPRIMIR_ACTION;
		setResultBeanImprimir(resultValidar);
		setVolverAntiguaConsulta(Boolean.TRUE);
		return pagina;
	}

	// Nuevo método para imprimir trámites desde Consulta de trámites
	public String impresion() throws Throwable {
		recargarPaginatedList();
		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			recargarPaginatedList();
			addActionError("No tienes permiso para realizar esta acción.");
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		}
		// Ponemos los números de expediente para que el otro action los recoga. Cuando se unifique, se debe cambiar.
		String[] codSeleccionados = prepararExpedientesSeleccionados();

		ResultValidarTramitesImprimir resultValidar = servicioImprimirImpl.validarImpresion(codSeleccionados, utilesColegiado.tienePermisoAdmin());
		if (resultValidar.getError()) {
			List<String> errores = resultValidar.getListaMensajes();
			if (errores != null) {
				for (int i = 0; i < errores.size(); i++) {
					addActionError(errores.get(i));
				}
			}
			recargarPaginatedList();
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		}
		setResultBeanImprimir(resultValidar);
		setVolverAntiguaConsulta(Boolean.TRUE);
		return IMPRESION_ACTION;
	}

	/**
	 * Funcion del action para clonar un expediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public String clonar() throws OegamExcepcion {
		if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			ResultBean result = servicioConsultaTramiteTrafico.comprobarPrevioAClonarTramites(codSeleccionados);
			if (!result.getError()) {
				return CLONAR_ACTION;
			}
			addActionError(result.getListaMensajes().get(0));
		}
		actualizarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	// Gilber-Mantis:2360
	// Recupera el Tipo de Transferencia del Tramite con el Numero de Expediente
	private boolean recuperaTipoTransferencia(String numExp) throws OegamExcepcion {
		boolean notificacion = false;
		String tipoTransferencia = servicioTramiteTraficoTransmision.getTipoTransferencia(new BigDecimal(numExp));

		if (StringUtils.isNotBlank(tipoTransferencia)) {
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
				// tipoTransmision 4-Notificación y 5-Entrega compraventa-Créditos Baja para el
				// resto créditos de transmisión
				if (TipoTransferencia.tipo4.getValorEnum().equals(tipoTransferencia)
						|| TipoTransferencia.tipo5.getValorEnum()
								.equals(tipoTransferencia)) {
					notificacion = true;
				}

			} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
				// tipoTransmisión 2-Notificación gasta Créditos Baja,
				if (TipoTransferencia.tipo2.getValorEnum().equals(tipoTransferencia)) {
					notificacion = true;
				}
			}
		}
		return notificacion;
	}

	public String imprimirJustifProfNuevo() {
		String[] codSeleccionados = null;
		if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
			codSeleccionados = listaExpedientes.replace(" ", "").split(",");
		}
		try {
			ResultadoConsultaJustProfBean resultado = servicioConsultaTramiteTrafico.imprimirJustificantesProf(codSeleccionados);
			if (!resultado.getError()) {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getNombreFichero();
			} else {
				addActionError(resultado.getMensaje());
				return recargarPaginatedList();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora imprimir los justificantes seleccionados, error: ", e);
			addActionError("Ha sucedido un error a la hora imprimir los justificantes seleccionados");
			return recargarPaginatedList();
		}

		return "mostrarDocumentoJustificante";
	}

	/**
	 * Función del action para imprimir un trámite, para el que se pase el número de expediente. Redirigirá a la página de impresión seleccionando el trámite indicado. Mantis 0004830. SCL. Modificación general para gestionar los expedientes con varios justificantes asociados, respetando la forma
	 * antigua de almacenarlos
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	public String imprimirJustificante() throws Throwable {
		recargarPaginatedList();
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		String[] expedientes = utiles.quitarDuplicados(codSeleccionados);

		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;

		List<String> listaNumExpedientesSinJustificantes = servicioJustificanteProfesional.obtenerExpedientesSinJustificantesEnEstadoPorNumExpediente(expedientes,
				org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante.Ok);
		if (listaNumExpedientesSinJustificantes.size() > 0) {
			addActionError("No se pueden imprimir los justificantes de los expedientes seleccionados. Los siguientes expedientes no tienen ningún justificante en estado 'recibido': " + utiles
					.transformListaToString(listaNumExpedientesSinJustificantes, "", "", ""));
			return donde;
		}
		ByteArrayInputStreamBean byteArrayInputStreamBean = servicioJustificanteProfesional.imprimirJustificantesPorNumExpediente(expedientes);
		if (byteArrayInputStreamBean != null) {
			if (FileResultStatus.FILE_NOT_FOUND.equals(byteArrayInputStreamBean.getStatus())) {
				addActionError("No se han encontrado los ficheros para ninguno de los justificantes seleccionados.");
				return donde;
			}
			setInputStream(byteArrayInputStreamBean.getByteArrayInputStream());
			setFileName(byteArrayInputStreamBean.getFileName());
		}
		return "mostrarDocumentoJustificante";
	}

	/**
	 * Función del action para exportar los trámites en un fichero XML. Sólo aceptará trámites del mismo tipo y del mismo número de colegiado.
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	public String exportar() throws Throwable {
		recargarPaginatedList();
		try {
			// Definición e inicialización de variables.

			// -- Cambios del check
			String[] codSeleccionados = prepararExpedientesSeleccionados();

			String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
			fileName = "exportarXML";
			byte[] bytesSalida = null;
			TramiteTraficoBean linea = new TramiteTraficoBean(true);
			int i = 0;

			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Exportar.");

			// Comprobamos que tenga algún tipo de permiso de manteniemiento
			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return donde;
			}

			if (!exportarDesdeAlta) {
				// Comprobamos que todos los trámites son del mismo tipo
				boolean iguales = true;
				String tipo = "";
				for (int j = 0; j < codSeleccionados.length; j++) {
					linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[j]));
					if (linea == null) {
						addActionError("El trámite " + codSeleccionados[j] + " no existe. No se puede continuar con la exportación;");
						return donde;
					} else {
						if ("".equals(tipo))
							tipo = linea.getTipoTramite().getValorEnum();
						iguales = iguales && (tipo.equals(linea.getTipoTramite().getValorEnum()));
					}
				}
				// si no son todos iguales
				if (!iguales) {
					addActionError("Todos los trámites deben ser del mismo tipo");
					return donde;
				}

				linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[0]));

				if (linea == null) {
					addActionError("No se ha encontrado el número de expediente");
					return donde;
				}

			} else {
				linea = getModeloTrafico().buscarTramitePorNumExpediente(codSeleccionados[0]);
				if (linea == null) {
					addActionError("No se ha encontrado el número de expediente");
					return donde;
				}
			}

			// Nuevo contrato que utilizamos para gestionar en el caso de que el contrato de sesión sea diferente al
			// de los trámites.
			idContratoSession = null != utilesColegiado.getIdContratoSessionBigDecimal() ? utilesColegiado.getIdContratoSessionBigDecimal().toString() : null;

			tipoTramite = null != linea.getTipoTramite() ? linea.getTipoTramite().getValorEnum() : null;
			idContratoTramites = null != linea.getIdContrato() ? linea.getIdContrato().toString() : null;

			// Miramos el tipo del que es, y según esto lo mandamos por un lado o por otro para generar el XML.

			// Separaremos dependiendo del tipo.
			switch (Integer.parseInt(tipoTramite.substring(1))) {
				// Matriculación
				case T1: {
					fileName += "Matr";
					ResultBean resultadoMatriculacion = new ResultBean();
					resultadoMatriculacion = exportarMatriculacion(codSeleccionados);
					bytesSalida = (byte[]) resultadoMatriculacion.getAttachment(ConstantesTrafico.BYTESXML);
				}
					break;
				// Transmisión
				case T2: {
					// Para transmision
					fileName += "Trans";
					ResultBean resultadoTransmision = new ResultBean();
					List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<TramiteTraficoTransmisionBean>();
					for (i = 0; i < codSeleccionados.length; i++) {
						// Seleccionamos el registro indicado desde el jsp

						Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]));

						// Recogemos los valores del modelo
						ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultado.getError()) {
							addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
							return donde;
						}

						if (!exportarDesdeAlta) {
							linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

							if (linea == null) {
								addActionError("No se ha encontrado el número de expediente");
								return donde;
							}

							if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
								addActionError("Los trámites seleccionado no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
								return donde;
							}

							if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
								addActionError("Los trámites seleccionado no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
								return donde;
							}
						}

						// Sentencias para controlar que en la exportación XML de Transmisión el valor del campo de ventana Piso, si es igual a "BAJO" se exporte como "BJ"
						String controlPlanta = "BAJO";
						String planta = "BJ";

						if (controlPlanta.equals(tramite.getAdquirienteBean().getPersona().getDireccion().getPlanta())) {
							tramite.getAdquirienteBean().getPersona().getDireccion().setPlanta(planta);
						}

						if (controlPlanta.equals(tramite.getTransmitenteBean().getPersona().getDireccion().getPlanta())) {
							tramite.getTransmitenteBean().getPersona().getDireccion().setPlanta(planta);
						}

						if (controlPlanta.equals(tramite.getPresentadorBean().getPersona().getDireccion().getPlanta())) {
							tramite.getPresentadorBean().getPersona().getDireccion().setPlanta(planta);
						}

						listaTramitesTransmision.add(tramite);

					}

					XMLTransmision xMLTransmision = new XMLTransmision();

					trafico.beans.jaxb.ga_trans_exportar.FORMATOGA formatoTransmision = xMLTransmision.convertirAFORMATOGA(listaTramitesTransmision, exportarXmlSesion);

					if (formatoTransmision == null) {
						addActionError("Error al convertir a FORMATOGA de transmión.");
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de transmión.");
						return donde;
					}

					String idSession = ServletActionContext.getRequest().getSession().getId();
					resultadoTransmision = xMLTransmision.FORMATOGAtoXML(formatoTransmision, idSession);

					if (null == resultadoTransmision || resultadoTransmision.getError()) {
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, Generando el XML de Transmisión.");
						addActionError("Error al generar XML: " + (null != resultadoTransmision ? resultadoTransmision.getMensaje() : "Devuelve Null"));
						return donde;
					}

					bytesSalida = (byte[]) resultadoTransmision.getAttachment(ConstantesTrafico.BYTESXML);
				}
					break;
				// Transmisión Electrónica
				case T7: {
					// Para transmision Electrónica
					fileName += "Trans";
					ResultBean resultadoTransmision = new ResultBean();
					List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<TramiteTraficoTransmisionBean>();
					for (i = 0; i < codSeleccionados.length; i++) {
						// Seleccionamos el registro indicado desde el jsp

						Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]));

						// Recogemos los valores del modelo
						ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultado.getError()) {
							addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
							return donde;
						}

						if (!exportarDesdeAlta) {
							linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

							if (linea == null) {
								addActionError("No se ha encontrado el número de expediente");
								return donde;
							}

							if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
								addActionError("Los trámites seleccionado no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
								return donde;
							}

							if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
								addActionError("Los trámites seleccionado no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
								return donde;
							}
						}

						// Sentencias para controlar que en la exportación XML de Transmisión Electrónica el valor del campo de ventana Piso, si es igual a "BAJO" se exporte como "BJ"
						String controlPlanta = "BAJO";
						String planta = "BJ";

						if (controlPlanta.equals(tramite.getAdquirienteBean().getPersona().getDireccion().getPlanta())) {
							tramite.getAdquirienteBean().getPersona().getDireccion().setPlanta(planta);
						}

						if (controlPlanta.equals(tramite.getTransmitenteBean().getPersona().getDireccion().getPlanta())) {
							tramite.getTransmitenteBean().getPersona().getDireccion().setPlanta(planta);
						}

						if (controlPlanta.equals(tramite.getPresentadorBean().getPersona().getDireccion().getPlanta())) {
							tramite.getPresentadorBean().getPersona().getDireccion().setPlanta(planta);
						}

						listaTramitesTransmision.add(tramite);
					}
					XMLTransmision xMLTransmision = new XMLTransmision();
					trafico.beans.jaxb.ga_trans_exportar.FORMATOGA formatoTransmision = xMLTransmision.convertirAFORMATOGA(listaTramitesTransmision, exportarXmlSesion);

					if (formatoTransmision == null) {
						addActionError("Error al convertir a FORMATOGA de transmión.");
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de transmión.");
						return donde;
					}

					String idSession = ServletActionContext.getRequest().getSession().getId();
					resultadoTransmision = xMLTransmision.FORMATOGAtoXML(formatoTransmision, idSession);

					if (null == resultadoTransmision || resultadoTransmision.getError()) {
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, Generando el XML de Transmisión.");
						addActionError("Error al generar XML: " + (null != resultadoTransmision ? resultadoTransmision.getMensaje() : "Devuelve Null"));
						return donde;
					}

					bytesSalida = (byte[]) resultadoTransmision.getAttachment(ConstantesTrafico.BYTESXML);
				}
					break;
				// Bajas
				case T3: {
					fileName += "Baja";
					ResultBean resultadoBaja = exportarBaja(codSeleccionados);
					if (resultadoBaja.getError()) {
						addActionError(resultadoBaja.getMensaje());
						return donde;
					} else {
						bytesSalida = (byte[]) resultadoBaja.getAttachment(ConstantesTrafico.BYTESXML);
					}
				}
					break;
				// Solicitudes
				case T4: {
					// Mantis 14125. David Sierra: Exportacion de solicitudes a XML
					fileName += "Solic";
					ResultBean resultadoSolicitud = new ResultBean();
					List<SolicitudDatosBean> listaTramitesSolicitados = new ArrayList<SolicitudDatosBean>();
					for (i = 0; i < codSeleccionados.length; i++) {

						Map<String, Object> resultadoModelo = getModeloSolicitudDatos().obtenerDetalle(new BigDecimal(codSeleccionados[i]), utilesColegiado.getNumColegiadoSession(), utilesColegiado
								.getIdContratoSessionBigDecimal());
						ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
						SolicitudDatosBean solicitud = new SolicitudDatosBean(true);
						solicitud = (SolicitudDatosBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizo bien
						if (resultado.getError()) {
							addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " al obtener tramites.");
							return donde;
						}

						if (!exportarDesdeAlta) {
							linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

							if (linea == null) {
								addActionError("No se ha encontrado el número de expediente");
								return donde;
							}

							if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
								addActionError("Los trámites seleccionados no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
								return donde;
							}

							if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
								addActionError("Los trámites seleccionadso no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
								return donde;
							}

						}
						listaTramitesSolicitados.add(solicitud);
					}

					XMLSolicitud xmlSolicitud = new XMLSolicitud();
					trafico.beans.jaxb.solicitud.FORMATOOEGAM2SOLICITUD formatoSolicitud = xmlSolicitud.convertirAFORMATOGA(listaTramitesSolicitados, exportarXmlSesion);

					if (formatoSolicitud == null) {
						addActionError("Error al convertir a FORMATOGA de solicitudes.");
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de solicitudes.");
						return donde;
					}

					String idSession = ServletActionContext.getRequest().getSession().getId();
					resultadoSolicitud = xmlSolicitud.FORMATOGAtoXML(formatoSolicitud, idSession);

					if (null == resultadoSolicitud || resultadoSolicitud.getError()) {
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, Generando el XML de Solicitudes.");
						addActionError("Error al generar XML: " + (null != resultadoSolicitud ? resultadoSolicitud.getMensaje() : "Devuelve Null"));
						return donde;
					}

					bytesSalida = (byte[]) resultadoSolicitud.getAttachment(ConstantesTrafico.BYTESXML);

				}
					break;
				case T8: {
					// Para duplicados
					fileName += "Dupl";
					ResultBean resultadoDuplicado = exportarDuplicado(codSeleccionados);
					if (resultadoDuplicado.getError()) {
						addActionError(resultadoDuplicado.getMensaje());
						return donde;
					} else {
						bytesSalida = (byte[]) resultadoDuplicado.getAttachment(ConstantesTrafico.BYTESXML);
					}
				}
					break;
				// No tiene tipo, se define el error y se redirecciona a la pagina de consulta
				default: {
					if (!exportarDesdeAlta) {
						addActionError("Este trámite no tiene asignado ningún tipo");
						log.debug("Este trámite no tiene asignado ningún tipo");
					}
				}
			}

			fileName = fileName + ConstantesGestorFicheros.EXTENSION_XML;
			inputStream = new ByteArrayInputStream(bytesSalida);

			addActionMessage("Fichero XML generado correctamente.");
			return EXPORTAR_TRAMITE;

		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	private ResultBean exportarMatriculacion(String[] codSeleccionados) throws Exception, OegamExcepcion {
		ResultBean resultadoMatriculacion = new ResultBean();
		List<TramiteTrafMatrDto> listaTramitesMatriculacion = new ArrayList<>();
		for (int i = 0; i < codSeleccionados.length; i++) {
			TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(codSeleccionados[i]), utilesColegiado.tienePermisoLiberarEEFF(), true);
			if (tramiteTrafMatrDto != null) {
				listaTramitesMatriculacion.add(tramiteTrafMatrDto);
			}
		}
		XMLMatw xMLMatw = new XMLMatw();
		trafico.beans.jaxb.matw.FORMATOGA formatoMatriculacion = xMLMatw.convertirAFORMATOGANuevo(listaTramitesMatriculacion, exportarXmlSesion);

		if (formatoMatriculacion == null) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de Matriculacion.");
			resultadoMatriculacion.setError(true);
			resultadoMatriculacion.setMensaje("Error al convertir a FORMATOGA de matriculacion.");
		}

		String idSession = ServletActionContext.getRequest().getSession().getId();
		resultadoMatriculacion = xMLMatw.FORMATOGAtoXML(formatoMatriculacion, idSession);

		if (null == resultadoMatriculacion || resultadoMatriculacion.getError()) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, Generando el XML de Matriculacion.");
			resultadoMatriculacion.setMensaje("Error al generar XML: " + (null != resultadoMatriculacion ? resultadoMatriculacion.getMensaje() : "Devuelve Null"));
		}
		return resultadoMatriculacion;
	}

	private ResultBean exportarBaja(String[] codSeleccionados) {
		ResultBean resultadoBaja = new ResultBean();
		List<TramiteTrafBajaDto> listaTramitesBaja = new ArrayList<>();
		for (int i = 0; i < codSeleccionados.length; i++) {
			TramiteTrafBajaDto tramiteBajaDto = servicioTramiteTraficoBaja.getTramiteBaja(new BigDecimal(codSeleccionados[i]), true);
			if (tramiteBajaDto != null) {
				listaTramitesBaja.add(tramiteBajaDto);
			}
		}
		XMLBaja xMLBaja = new XMLBaja();
		FORMATOOEGAM2BAJA formatoBaja = xMLBaja.convertirAFORMATOGA(listaTramitesBaja, exportarXmlSesion, utilesColegiado.tienePermisoBTV());

		if (formatoBaja == null) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de baja.");
			resultadoBaja.setError(true);
			resultadoBaja.setMensaje("Error al convertir a FORMATOGA de baja.");
		}

		String idSession = ServletActionContext.getRequest().getSession().getId();
		resultadoBaja = xMLBaja.FORMATOGAtoXML(formatoBaja, idSession);

		if (null == resultadoBaja || resultadoBaja.getError()) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, Generando el XML de Baja.");
			resultadoBaja.setMensaje("Error al generar XML: " + (null != resultadoBaja ? resultadoBaja.getMensaje() : "Devuelve Null"));
		}
		return resultadoBaja;
	}

	private ResultBean exportarDuplicado(String[] codSeleccionados) {
		ResultBean resultadoDuplicado = new ResultBean();
		List<TramiteTrafDuplicadoDto> listaTramitesDuplicado = new ArrayList<>();
		for (int i = 0; i < codSeleccionados.length; i++) {
			TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(codSeleccionados[i]), true);
			if (tramiteTrafDuplicadoDto != null) {
				listaTramitesDuplicado.add(tramiteTrafDuplicadoDto);
			}
		}
		XMLDuplicado xMLDuplicado = new XMLDuplicado();
		FORMATOOEGAM2DUPLICADO formatoDuplciado = xMLDuplicado.convertirAFORMATOGA(listaTramitesDuplicado, exportarXmlSesion);

		if (formatoDuplciado == null) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de duplicado.");
			resultadoDuplicado.setError(true);
			resultadoDuplicado.setMensaje("Error al convertir a FORMATOGA de duplicado.");
		}

		String idSession = ServletActionContext.getRequest().getSession().getId();
		resultadoDuplicado = xMLDuplicado.FORMATOGAtoXML(formatoDuplciado, idSession);

		if (null == resultadoDuplicado || resultadoDuplicado.getError()) {
			log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, Generando el XML de Duplicado.");
			resultadoDuplicado.setMensaje("Error al generar XML: " + (null != resultadoDuplicado ? resultadoDuplicado.getMensaje() : "Devuelve Null"));
		}
		return resultadoDuplicado;
	}

	/**
	 * Función del action para exportar los trámites en un fichero XML. Sólo aceptará trámites del mismo tipo y del mismo número de colegiado.
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	public String exportarBM() throws Throwable {
		recargarPaginatedList();
		// Definición e inicialización de variables.

		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();

		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
		String fileName = "exportarBM";
		byte[] bytesSalida = null;
		TramiteTraficoBean linea = new TramiteTraficoBean(true);

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Exportar BM.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return donde;
		}

		List<String> lineasExport = new ArrayList<String>();
		String lineaExport = "";
		// Vamos a recoger todos los trámites, y los que sean de matriculación, los pasamos al fichero.
		for (int j = 0; j < codSeleccionados.length; j++) {
			lineaExport = "";
			linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[j]));

			// Si no existe el expediente
			if (linea == null) {
				addActionError("No se ha encontrado el trámite para el expediente " + codSeleccionados[j]);
			}
			// Si no son de matriculación
			else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(linea.getTipoTramite().getValorEnum())) {
				addActionError("La exportación BM sólo es para matriculación. El trámite " + codSeleccionados[j] + " no se ha exportado");
			}
			// Si existe, y es de matriculación
			else {
				lineaExport += null != linea.getVehiculo().getBastidor() ? linea.getVehiculo().getBastidor() : "";
				lineaExport += ";";
				lineaExport += null != linea.getVehiculo().getMatricula() ? linea.getVehiculo().getMatricula() : "";
				lineaExport += ";";
				lineaExport += null != linea.getReferenciaPropia() ? linea.getReferenciaPropia() : "";
				lineaExport += ";";
				lineaExport += null != linea.getNumExpediente() ? linea.getNumExpediente() : "";
				// añadimos la linea a la lista de lineas
				lineasExport.add(lineaExport);
			}
		}

		if (lineasExport.size() > 0) {
			try {
				String idSession = ServletActionContext.getRequest().getSession().getId();
				// creamos el fichero con los erroresç
				// Gestor de ficheros nueva historificación
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.EXPORTAR);
				fichero.setSubTipo(ConstantesGestorFicheros.BM);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_BM);
				fichero.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_EXPORTAR + idSession);
				fichero.setSobreescribir(true);
				fichero.setFecha(utilesFecha.getFechaActual());
				File file = gestorDocumentos.guardaFicheroEnviandoStrings(fichero, lineasExport);

				// y a los 5 minutos, lo borraremos
				BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(file.getAbsolutePath());
				hiloBorrar.start();
				log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
				bytesSalida = utiles.getBytesFromFile(file);
			} catch (Exception e) {
				addActionError("Error al crear el fichero");
			}
		}

		// Aquí irá lo que se hace cuando se recupere el fichero si no ha habido errores..
		// ponemos el esperaImpreso a true para que lo recupere desde la jsp llamando al siguiente action,
		// o incluso lo metemos en sesion con el nombre que en imprimir y lo recuperamos y mostramos con
		// el mostrar documento de traficoAction que ya está implementado.

		if (null != bytesSalida) {
			impresoEspera = true;

			if (getSession().get(ConstantesTrafico.FICHEROXML) != null || getSession().get(ConstantesTrafico.NOMBREXML) != null) {
				getSession().put(ConstantesTrafico.FICHEROXML, null);
				getSession().put(ConstantesTrafico.NOMBREXML, null);
			}

			getSession().put(ConstantesTrafico.FICHEROBM, bytesSalida);
			getSession().put(ConstantesTrafico.NOMBREBM, fileName);
			addActionMessage("Fichero BM generado correctamente con los trámites de matriculación seleccionados.");
		}
		return donde;
	}

	/**
	 * Función del action para obtener los XML del 620 de transmision
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public String exportarXML620() throws Throwable {
		recargarPaginatedList();
		// Definición e inicialización de variables.
		String indices = getNumsExpediente();
		String[] codSeleccionados = indices.split("-");
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
		String fileName = "";
		byte[] bytesSalida = null;
		TramiteTraficoBean linea = new TramiteTraficoBean(true);
		int i = 0;

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Extraer XML 620.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return donde;
		}

		linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[0]));

		if (linea == null) {
			addActionError("No se ha encontrado el número de expediente");
			return donde;
		}

		// Nuevo contrato que utilizamos para gestionar en el caso de que el contrato de sesión sea diferente al
		// de los trámites.
		idContratoSession = null != utilesColegiado.getIdContratoSessionBigDecimal() ? utilesColegiado.getIdContratoSessionBigDecimal().toString() : null;

		tipoTramite = null != linea.getTipoTramite() ? linea.getTipoTramite().getValorEnum() : null;
		idContratoTramites = null != linea.getIdContrato() ? linea.getIdContrato().toString() : null;

		// Miramos el tipo del que es, y según esto lo mandamos por un lado o por otro para generar el XML.

		// Separaremos dependiendo del tipo.
		switch (Integer.parseInt(tipoTramite.substring(1))) {
			// Matriculación
			case T1: {
				addActionError("Sólo se puede generar XML del modelo 620 para trámites de transmisión.");
				// En matriculación no existe este tipo de XML
			}
				break;
			// Transmisión
			case T2: {
				// Para transmision
				List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<TramiteTraficoTransmisionBean>();
				for (i = 0; i < codSeleccionados.length; i++) {
					// Seleccionamos el registro indicado desde el jsp

					Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]));

					// Recogemos los valores del modelo
					ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

					// Comprobamos que se realizó bien
					if (resultado.getError()) {
						addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
						return donde;
					}

					linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

					if (linea == null) {
						addActionError("No se ha encontrado el número de expediente");
						return donde;
					}

					if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
						addActionError("Los trámites seleccionado no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
						return donde;
					}

					if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
						addActionError("Los trámites seleccionado no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
						return donde;
					}

					if (tramite.getEstado620().getValorEnum().equals(Estado620.Validado.getValorEnum())) {
						addActionMessage("Trámite con número de expediente " + linea.getNumExpediente().toString() + " correcto.");
						listaTramitesTransmision.add(tramite);
					} else {
						addActionError("Trámite con número de expediente " + linea.getNumExpediente().toString() + " no estaba en estado validado");
					}
				}

				String idSession = ServletActionContext.getRequest().getSession().getId();
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(listaTramitesTransmision, idSession);

				if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha habido errores, los mostramos...
					List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
					for (String error : errores) {
						addActionError(error);
					}
				} else { // Si todo ha ido bien...
					// Lo validamos contra el XSD
					File fichero = new File((String) respuestaGenerarXMLJaxb.get("fichero"));
					String resultadoValidar = validarXML620(fichero);
					if (!ConstantesTrafico.XML_VALIDO.equals(resultadoValidar)) {
						addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
						return donde;
					}

					bytesSalida = utiles.getBytesFromFile(fichero);
					fileName = "oegamGata620";
				}
			}
				break;
			// Transmisión Electrónica
			case T7: {
				// Para transmision
				List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<>();
				for (i = 0; i < codSeleccionados.length; i++) {
					// Seleccionamos el registro indicado desde el jsp

					Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]));

					// Recogemos los valores del modelo
					ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

					// Comprobamos que se realizó bien
					if (resultado.getError()) {
						addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
						return donde;
					}

					linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

					if (linea == null) {
						addActionError("No se ha encontrado el número de expediente");
						return donde;
					}

					if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
						addActionError("Los trámites seleccionado no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
						return donde;
					}

					if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
						addActionError("Los trámites seleccionado no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
						return donde;
					}

					if (tramite.getEstado620().getValorEnum().equals(Estado620.Validado.getValorEnum())) {
						addActionMessage("Trámite con número de expediente " + linea.getNumExpediente().toString() + " correcto.");
						listaTramitesTransmision.add(tramite);
					} else {
						addActionError("Trámite con número de expediente " + linea.getNumExpediente().toString() + " no estaba en estado validado");
					}
				}

				String idSession = ServletActionContext.getRequest().getSession().getId();
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(listaTramitesTransmision, idSession);

				if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha habido errores, los mostramos...
					List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
					for (String error : errores) {
						addActionError(error);
					}
				} else { // Si todo ha ido bien...
					// Lo validamos contra el XSD
					File fichero = new File((String) respuestaGenerarXMLJaxb.get("fichero"));
					String resultadoValidar = validarXML620(fichero);
					if (!ConstantesTrafico.XML_VALIDO.equals(resultadoValidar)) {
						addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
						return donde;
					}

					bytesSalida = utiles.getBytesFromFile(fichero);
					fileName = "oegamGata620";
				}
			}
				break;
			// Bajas
			case T3: {
				addActionError("Sólo se puede generar XML del modelo 620 para trámites de transmisión.");
				// Para Bajas en el caso de que haya que hacerlo.

			}
				break;
			// Solicitudes
			case T4: {
				addActionError("Sólo se puede generar XML del modelo 620 para trámites de transmisión.");
				// Para solicitudes en el caso de que haya que hacerlo.
			}
				break;
			// No tiene tipo, se define el error y se redirecciona a la pagina de consulta
			default: {
				addActionError("Este trámite no tiene asignado ningún tipo");
				log.debug("Este trámite no tiene asignado ningún tipo");
			}
		}
		// Aquí irá lo que se hace cuando se recupere el fichero si no ha habido errores..
		// ponemos el esperaImpreso a true para que lo recupere desde la jsp llamando al siguiente action,
		// o incluso lo metemos en sesion con el nombre que en imprimir y lo recuperamos y mostramos con
		// el mostrar documento de traficoAction que ya está implementado.

		if (null != bytesSalida) {
			impresoEspera = true;
			getSession().put(ConstantesTrafico.FICHEROXML, bytesSalida);
			getSession().put(ConstantesTrafico.NOMBREXML, fileName);
			addActionMessage("Fichero XML generado correctamente.");
		}
		return donde;
	}

	/**
	 * Clase del action para la tramitación telemática en bloque.
	 * @return
	 * @throws Throwable
	 */
	public String tramitarBloqueTelematicamente() throws Throwable {
		recargarPaginatedList();
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;

		String numeroMaxTramitar = gestorPropiedades.valorPropertie("maximo.numero.tramitar");
		if (numeroMaxTramitar != null && codSeleccionados != null && codSeleccionados.length > Integer.parseInt(numeroMaxTramitar)) {
			addActionError("No puede tramitar más de " + numeroMaxTramitar + " trámites.");
			return donde;
		}

		List<TramiteTraficoBean> beans = new ArrayList<TramiteTraficoBean>();

		ResumenTramitacionTelematica resumenMatriculacion = new ResumenTramitacionTelematica(TipoTramiteTrafico.Matriculacion.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenBaja = new ResumenTramitacionTelematica(TipoTramiteTrafico.Baja.getNombreEnum());
		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		BigDecimal idContrato = utilesColegiado.getIdContratoSessionBigDecimal();

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Tramitar en bloque telemáticamente:" + codSeleccionados.length + " trámites seleccionados.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			resumenOtros.addFallido();
			resumenTotal.addFallido();
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
			resumenTramitacion = true;
			return donde;
		}

		// Obtenemos los trámites, para ver si existen y de qué tipo son, más adelante obtendremos el detalle dependiendo del tipo
		for (int i = 0; i < codSeleccionados.length; i++) {
			beans.add(obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i])));
		}

		// Recorremos los beans, para ver si están todos en validados telemáticamente
		boolean validadosTodos = true;
		for (TramiteTraficoBean tramiteTraficoBean : beans) {
			if (tramiteTraficoBean == null) {
				validadosTodos = false;
			} else {

				validadosTodos = validadosTodos && (EstadoTramiteTrafico.Validado_Telematicamente.equals(tramiteTraficoBean.getEstado()) || 
						EstadoTramiteTrafico.Autorizado.equals(tramiteTraficoBean.getEstado()) || 
						EstadoTramiteTrafico.LiberadoEEFF.equals(tramiteTraficoBean.getEstado()));

				List<EvolucionTramiteTraficoVO> evolucion = servicioEvolucionTramite.getTramiteFinalizadoErrorAutorizado(tramiteTraficoBean.getNumExpediente());
				if(evolucion != null && !evolucion.isEmpty()) {
					if (EstadoTramiteTrafico.Validado_Telematicamente.equals(tramiteTraficoBean.getEstado())
							|| EstadoTramiteTrafico.Autorizado.equals(tramiteTraficoBean.getEstado())
							|| EstadoTramiteTrafico.LiberadoEEFF.equals(tramiteTraficoBean.getEstado())
							|| EstadoTramiteTrafico.Finalizado_Con_Error.equals(tramiteTraficoBean.getEstado())) {
						validadosTodos = true;
					}
				}
				if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoLiberarEEFF()) {
					if (tramiteTraficoBean.getTipoTramite() == TipoTramiteTrafico.Matriculacion && tramiteTraficoBean.getEstado() != EstadoTramiteTrafico.LiberadoEEFF) {
						LiberacionEEFFDto liberadoEEFF = servicioEEFF.getLiberacionEEFFDto(tramiteTraficoBean.getNumExpediente());
						if (liberadoEEFF == null) {
							if (tramiteTraficoBean.getVehiculo() == null || (tramiteTraficoBean.getVehiculo().getNive() != null && !tramiteTraficoBean.getVehiculo().getNive().isEmpty())) {
								validadosTodos = false;
							}
						} else if (!liberadoEEFF.getExento() && !liberadoEEFF.getRealizado()) {
							validadosTodos = false;
						}
					}
				}
			}
			if (!validadosTodos) {
				break;
			}
		}

		// Si no están todos validados, volvemos a la pantalla y mostramos el mensaje de error
		if (!validadosTodos) {
			addActionError("Todos los trámites deben estar validados telemáticamente y liberados");
			resumenOtros.addFallido();
			resumenTotal.addFallido();
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
			resumenTramitacion = true;
			return donde;
		}

		/**
		 * Incidencia Mantis 0004650: verificar descuento créditos de transmisión Se cuentan los Créditos de Baja para los tramites de Transmision Telematica pero con el tipo de Transferencia Entrega Compraventa, Notificación de cambio de titularidad porque cobran ese tipo de Créditos.
		 */
		// Comprobamos que todos los trámites cobran el mismo tipo de Crédito,sino no se puede Imprimir
		boolean notificacion = false;
		tipoTramite = null != beans.get(0).getTipoTramite() ? beans.get(0).getTipoTramite().getValorEnum() : null;
		notificacion = recuperaTipoTransferencia(codSeleccionados[0]);

		for (int j = 0; j < beans.size(); j++) {
			try {
				tipoTramite = null != beans.get(j).getTipoTramite() ? beans.get(j).getTipoTramite().getValorEnum() : null;
				if (notificacion != recuperaTipoTransferencia(codSeleccionados[j])) {
					if ((!beans.get(j).getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())) && (!beans.get(j).getEstado().getValorEnum().equals(
							EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))) {
						addActionError("Los trámites de transmision seleccionados no cobran el mismo tipo de Créditos. Deben serlo para realizar impresiones en bloque.");
						return donde;
					}
				}
			} catch (OegamExcepcion e1) {
				log.error("Se ha producido un error al tramitar un bloque telematicamente. ", e1);
			}
		}

		// Contamos los trámites de cada tipo
		int matriculacion = 0;
		int transmision = 0;
		String tipoCreditoDescontar;
		String tipoTramiteDescontar;
		for (int i = 0; i < beans.size(); i++) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(beans.get(i).getTipoTramite().getValorEnum()))
				matriculacion++;
			if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(beans.get(i).getTipoTramite().getValorEnum()))
				transmision++;
			// AÑADIR CONTADORES PARA OTROS TIPOS DE TRÁMITE
		}

		// Ahora controlamos el número de créditos disponibles para cada tipo
		boolean tieneCreditos = true;
		if (matriculacion > 0 && !"2631".equals(beans.get(0).getNumColegiado())){
			HashMap<String, Object> resultado = getModeloCreditosTrafico().validarCreditosPorNumColegiado(beans.get(0).getIdContrato().toString(), new BigDecimal(matriculacion),
					TipoTramiteTrafico.Matriculacion.getValorEnum());
			ResultBean result = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

			if (result.getError()) {
				tieneCreditos = false;
				addActionError("No tiene suficientes créditos de Matriculación, no se tramitarán estos expedientes");
				resumenMatriculacion.addFallido();
				resumenTotal.addFallido();
			}
		}
		// Controlamos si tiene permisos de mantenimiento de matriculación
		boolean tienePermisoMATW;
		tienePermisoMATW = utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MATRICULACION_MATW_ALTAS);

		// Vemos si el tipo de trasnferencia es NOTIFICATION o TRADE
		if (notificacion) {
			tipoTramiteDescontar = TipoTramiteTrafico.Baja.getValorEnum();
			tipoCreditoDescontar = "Baja";
		} else {
			tipoTramiteDescontar = TipoTramiteTrafico.TransmisionElectronica.getValorEnum();
			tipoCreditoDescontar = "Transmisión";
		}
		boolean puedeTrans = true;
		if (transmision > 0) {
			if(!"2631".equals(beans.get(0).getNumColegiado())){
				HashMap<String, Object> resultado = getModeloCreditosTrafico().validarCreditosPorNumColegiado(beans.get(0).getIdContrato().toString(), new BigDecimal(transmision), tipoTramiteDescontar);
				ResultBean result = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);
				if (result.getError()) {
					puedeTrans = false;
					addActionError("No tiene suficientes créditos de " + tipoCreditoDescontar + ", no se tramitarán estos expedientes");
					resumenTransmision.addFallido();
					resumenTotal.addFallido();
				}
			}
		}

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Se recuperaron " + beans.size() + " Trámites, se pueden Matricular: " + tieneCreditos + " ,se pueden Transmitir: " + puedeTrans);

		// Si están validados telemáticamente, los tramitamos dependiendo del tipo
		if (getTipoFirma().equals(ConstantesFirma.TIPO_FIRMA_HSM)) {
			// Instanciar comprobador de datos sensibles
			// ComprobadorDatosSensibles comprobadorDatosSensibles = new ComprobadorDatosSensibles();
			// recorremos los tramites
			for (int i = 0; i < beans.size(); i++) {
				ResultBean resultadoDetalle = null;
				if (TipoTramiteTrafico.Baja.equals(beans.get(i).getTipoTramite())) {
					String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
					if (SI.equals(nuevasBajas)) {
						ResultadoBajasBean resultado = servicioTramiteBaja.tramitarBtv(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
						if (resultado.getError()) {
							addActionError(resultado.getMensaje());
							resumenBaja.addFallido();
							resumenTotal.addFallido();
						} else {
							addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "pendiente de respuesta de DGT.");
							resumenBaja.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						}
					} else {
						resultadoDetalle = servicioTramiteTraficoBaja.tramitarBajaTelematicamente(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
								.getColegiado().getNumColegiado());
						if (resultadoDetalle != null && resultadoDetalle.getError()) {
							addActionError(resultadoDetalle.getListaMensajes().get(0));
							resumenBaja.addFallido();
							resumenTotal.addFallido();
						} else {
							addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "pendiente de respuesta de DGT.");
							resumenBaja.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						}
					}
				}
				// MATRICULACION
				if (TipoTramiteTrafico.Matriculacion.equals(beans.get(i).getTipoTramite()) && tieneCreditos) {
					if (!getModeloTrafico().isMatw(Long.valueOf(beans.get(i).getNumExpediente().toString()))) {
						addActionError("Sólo es posible tramitar telemáticamente un trámite de MATW");
						return donde;
					} else {
						if (!tienePermisoMATW) {
							addActionError("No tiene permiso para tramitar este tipo de expediente. Contacte con el administrador del sistema.");
							return donde;
						}

						String habilitarMatriculacion = gestorPropiedades.valorPropertie("habilitar.matriculacion.nueva");
						if (habilitarMatriculacion != null && SI.equals(habilitarMatriculacion)) {
							log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Se va a mandar a matricular al expediente: " + beans.get(i).getNumExpediente());
							ResultBean resultadoTramitar = servicioTramiteTraficoMatriculacion.tramitacion(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
									.getAlias(), utilesColegiado.getColegioDelContrato(), utilesColegiado.tienePermisoLiberarEEFF(), utilesColegiado.getIdContratoSessionBigDecimal());
							if (!resultadoTramitar.getError()) {
								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + resultadoTramitar.getMensaje());
								resumenMatriculacion.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} else {
								addActionError("Tramite " + beans.get(i).getNumExpediente() + ": Error en la tramitacion telemática. ");
								if (resultadoTramitar.getListaMensajes() != null && !resultadoTramitar.getListaMensajes().isEmpty()) {
									for (String mensaje : resultadoTramitar.getListaMensajes()) {
										addActionError(mensaje);
									}
								}
								resumenMatriculacion.addFallido();
								resumenTotal.addFallido();
							}
						} else {
							HashMap<String, Object> tramiteDetalle = getModeloMatriculacion().obtenerDetalleConDescripciones(beans.get(i).getNumExpediente(), utilesColegiado.getNumColegiadoSession(),
									utilesColegiado.getIdContratoSessionBigDecimal());
							// Recogemos los valores del modelo
							resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
							TramiteTraficoMatriculacionBean tramite = (TramiteTraficoMatriculacionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

							// Comprobamos que se realizó bien
							if (resultadoDetalle.getError()) {
								addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
								log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
								resumenMatriculacion.addFallido();
								resumenTotal.addFallido();
							} else {
								// -- Inicio comprobación de datos sensibles
								try {
									// TODO MPC. Esta comprobación debería hacer al principio para todos los trámites juntos.
									String[] numsExpedientes = new String[] { beans.get(i).getNumExpediente().toString() };
									List<String> listaExpedientesSensibles = null;
									String datosSensiblesAnt = gestorPropiedades.valorPropertie(antiguoDatosSensibles);
									if ("true".equals(datosSensiblesAnt)) {
										listaExpedientesSensibles = new ComprobadorDatosSensibles().isSensibleData(numsExpedientes);
									} else {
										listaExpedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(utiles.convertirStringArrayToBigDecimal(numsExpedientes), utilesColegiado
												.getIdUsuarioSessionBigDecimal());
									}
									if (listaExpedientesSensibles != null && !listaExpedientesSensibles.isEmpty()) {
										for (int j = 0; j < listaExpedientesSensibles.size(); j++) {
											addActionError("Se ha recibido un error técnico. No intenten presentar el tramite " + listaExpedientesSensibles.get(j)
													+ ". Les rogamos comuniquen con el Colegio.");
											log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Datos sensibles en el trámite " + listaExpedientesSensibles.get(j));
										}
										resumenOtros.addFallido();
										resumenTotal.addFallido();
										continue;
									}
								} catch (Exception e) {}
								// -- Fin de comprobación de datos sensibles
								log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Se va a mandar a matricular al expediente: " + beans.get(i).getNumExpediente());
								ResultBean resultadoTramitar = getModeloMatriculacion().matriculacionTelematicaHsm(tramite, idUsuario, idContrato);
								if (resultadoTramitar.getError() == false) {
									addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + resultadoTramitar.getMensaje());
									resumenMatriculacion.addTramitadosTelematicamente();
									resumenTotal.addTramitadosTelematicamente();
								} else {
									addActionError("Tramite " + beans.get(i).getNumExpediente() + ": Error en la tramitacion telemática. " + resultadoTramitar.getMensaje());
									resumenMatriculacion.addFallido();
									resumenTotal.addFallido();
								}
							}
						}
					}
				}
				// TRANSMISION
				if (TipoTramiteTrafico.TransmisionElectronica.equals(beans.get(i).getTipoTramite()) && puedeTrans) {
					Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
					// Recogemos los valores del modelo
					resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

					// Comprobamos que se realizó bien
					if (resultadoDetalle.getError()) {
						addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} else {
						// -- Inicio comprobación de datos sensibles
						try {
							// TODO MPC. Esta comprobación debería hacer al principio para todos los trámites juntos.
							String[] numsExpedientes = new String[] { beans.get(i).getNumExpediente().toString() };
							List<String> listaExpedientesSensibles = null;
							String datosSensiblesAnt = gestorPropiedades.valorPropertie(antiguoDatosSensibles);
							if ("true".equals(datosSensiblesAnt)) {
								listaExpedientesSensibles = new ComprobadorDatosSensibles().isSensibleData(numsExpedientes);
							} else {
								listaExpedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(utiles.convertirStringArrayToBigDecimal(numsExpedientes), utilesColegiado
										.getIdUsuarioSessionBigDecimal());
							}
							if (listaExpedientesSensibles != null && !listaExpedientesSensibles.isEmpty()) {
								for (int j = 0; j < listaExpedientesSensibles.size(); j++) {
									addActionError("Se ha recibido un error técnico. No intenten presentar el tramite " + listaExpedientesSensibles.get(j)
											+ ". Les rogamos comuniquen con el Colegio.");
									log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Datos sensibles en el trámite " + listaExpedientesSensibles.get(j));
								}
								resumenOtros.addFallido();
								resumenTotal.addFallido();
								continue;
							}
						} catch (Exception e) {}
						// -- Fin de comprobación de datos sensibles
						ResultBean resultadoTramitar = getModeloTransmision().transmisionTelematicaMetodo(tramite, idUsuario, idContrato);
						if (resultadoTramitar.getError() == false) {
							addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + resultadoTramitar.getMensaje());
							resumenTransmision.addTramitadosTelematicamente();
							resumenTotal.addTramitadosTelematicamente();
						} else {
							String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la tramitacion telemática. ";
							for (String mensaje : resultadoTramitar.getListaMensajes()) {
								error += "- " + mensaje;
							}
							addActionError(error);
							resumenTransmision.addFallido();
							resumenTotal.addFallido();
						}
					}
				}
				if (TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
					try {
						if (!validarEstados(beans.get(i))) {
							addActionError("Debe estar validado telemáticamente y liberado.");
							resumenDuplicado.addFallido();
							resumenTotal.addFallido();
						} else {
							ResultBean resultado = servicioTramiteDuplicado.tramitarDuplicado(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
							if (resultado.getError()) {
								addActionError(resultado.getMensaje());
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							} else {
								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "pendiente de respuesta de DGT.");
								resumenDuplicado.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							}
						}
					} catch (Throwable th) {
						log.error("Error en a la hora de tramitar los tramites:", th);
						addActionError("Ha sucedido un error al tramitar. ");
						resumenDuplicado.addFallido();
						resumenTotal.addFallido();
					}
				}
			}
		} else {
			addActionError("El tipo de firma no es válido para tramitar en bloque");
			resumenOtros.addFallido();
			resumenTotal.addFallido();
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
			resumenTramitacion = true;
			return donde;
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenMatriculacion);
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenBaja);
		resumenTramitacionTelematica.add(resumenDuplicado);
		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return donde;
	}

	/**
	 * Clase del action para la tramitación telemática en bloque.
	 * @return
	 * @throws Throwable
	 */
	public String comprobarBloque() throws Throwable {
		recargarPaginatedList();
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;

		String numeroMaxComprobar = gestorPropiedades.valorPropertie("maximo.numero.comprobar");
		if (numeroMaxComprobar != null && codSeleccionados != null && codSeleccionados.length > Integer.parseInt(numeroMaxComprobar)) {
			addActionError("No puede comprobar más de " + numeroMaxComprobar + " trámites.");
			return donde;
		}

		List<TramiteTraficoBean> beans = new ArrayList<TramiteTraficoBean>();

		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenBaja = new ResumenTramitacionTelematica(TipoTramiteTrafico.Baja.getNombreEnum());
		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " comprobar en bloque.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			resumenOtros.addFallido();
			resumenTotal.addFallido();
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
			resumenTramitacion = true;
			return donde;
		}

		// obtenemos los trámites, para ver si existen y de qué tipo son, más adelante obtendremos el detalle dependiendo del tipo
		for (int i = 0; i < codSeleccionados.length; i++) {
			beans.add(obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i])));
		}

		// recorremos los beans, para ver si están todos en validados telemáticamente
		boolean validadosTodos = true;
		for (TramiteTraficoBean tramiteTraficoBean : beans) {
			if (tramiteTraficoBean == null) {
				validadosTodos = false;
			} else {
				if(utiles.esUsuarioSive(tramiteTraficoBean.getNumColegiado()) && TipoTramiteTrafico.Duplicado.getValorEnum().equalsIgnoreCase(tramiteTraficoBean.getTipoTramite().toString())) {
					validadosTodos = validadosTodos && (EstadoTramiteTrafico.Iniciado.equals(tramiteTraficoBean.getEstado()) || EstadoTramiteTrafico.TramitadoNRE06.equals(tramiteTraficoBean.getEstado()) || EstadoTramiteTrafico.LiberadoEEFF.equals(tramiteTraficoBean
							.getEstado())) && (TipoTramiteTrafico.TransmisionElectronica.equals(tramiteTraficoBean.getTipoTramite()) || TipoTramiteTrafico.Baja.equals(tramiteTraficoBean
									.getTipoTramite()) || TipoTramiteTrafico.Duplicado.equals(tramiteTraficoBean.getTipoTramite()));

				}else {
					validadosTodos = validadosTodos && (EstadoTramiteTrafico.Iniciado.equals(tramiteTraficoBean.getEstado()) || EstadoTramiteTrafico.TramitadoNRE06.equals(tramiteTraficoBean.getEstado()) || EstadoTramiteTrafico.LiberadoEEFF.equals(tramiteTraficoBean
							.getEstado())) && (TipoTramiteTrafico.TransmisionElectronica.equals(tramiteTraficoBean.getTipoTramite()) || TipoTramiteTrafico.Baja.equals(tramiteTraficoBean
									.getTipoTramite()));
				}
			}
		}

		// Si no están todos validados, volvemos a la pantalla y mostramos el mensaje de error
		if (!validadosTodos) {
			addActionError("Todos los trámites deben estar iniciados y ser de transmisión o duplicados SIVE o bajas por traslado a otro país.");

			resumenOtros.addFallido();
			resumenTotal.addFallido();
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
			resumenTramitacion = true;
			return donde;
		}

		// Si están validados telemáticamente, los tramitamos dependiendo del tipo
		// if (getTipoFirma().equals(ConstantesFirma.TIPO_FIRMA_HSM)) {
		// recorremos los tramites
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
				// Recogemos los valores del modelo
				resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
				// Comprobamos que se ha recuperado bien
				if (resultadoDetalle.getError()) {
					addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
					resumenTransmision.addFallido();
					resumenTotal.addFallido();
				} else {
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);
					ResultBean resultadoComprobar;
					if (gestorPropiedades.valorPropertie("XmlYFirmaEnNodoProceso").equals("si")) {
						resultadoComprobar = getModeloTransmision().validarTramiteTransmisionEnServidor(tramite, utilesColegiado.getIdUsuarioSessionBigDecimal());
					} else {
						// La validación comentada acontinuación, es la antigua
						if (SI.equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.checkCtit"))) {
							resultadoComprobar = servicioTramiteTraficoTransmision.comprobarTramitabilidadTramiteTransmisionSega(tramite, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
									.getIdContratoSessionBigDecimal());
						} else {
							String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
							if("2631".equals(tramite.getTramiteTraficoBean().getNumColegiado()) && SI.equals(gestionarConAM)) {
								resultadoComprobar = servicioCheckCtit.crearSolicitud(tramite.getTramiteTraficoBean().getNumExpediente(), null,
										utilesColegiado.getIdUsuarioSessionBigDecimal(), tramite.getTramiteTraficoBean().getIdContrato());
							} else {
								resultadoComprobar = servicioTramiteTraficoTransmision.comprobarTramitabilidadTramiteTransmision(tramite, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
									.getIdContratoSessionBigDecimal());
							}
						}
					}
					if (resultadoComprobar.getError() == false) {
						addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + resultadoComprobar.getMensaje());
						// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
						if (resultadoComprobar != null && resultadoComprobar.getListaMensajes() != null && !resultadoComprobar.getListaMensajes().isEmpty()) {
							for (String mensajeComprobar : resultadoComprobar.getListaMensajes()) {
								if (mensajeComprobar.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
									addActionMessage(mensajeComprobar);
								}
							}
						}
						// FIN 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
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
				String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
				if (SI.equals(nuevasBajas)) {
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
				} else {
					esBaja = true;
					if (esTransmision) {
						addActionError("Todos los expedientes seleccionados deben de ser del mismo tipo.");
					}
					TramiteTrafBajaDto tramiteTrafBajaDto = servicioTramiteTraficoBaja.getTramiteBaja(beans.get(i).getNumExpediente(), true);
					resultadoDetalle = servicioTramiteTraficoBaja.comprobarConsultaBTV(tramiteTrafBajaDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resultadoDetalle != null && resultadoDetalle.getError()) {
						for (String mensaje : resultadoDetalle.getListaMensajes()) {
							addActionError(mensaje);
						}
						resumenBaja.addFallido();
						resumenTotal.addFallido();
					} else {
						resumenBaja.addTramitadosTelematicamente();
						resumenTotal.addTramitadosTelematicamente();
						addActionMessage("Expediente: " + tramiteTrafBajaDto.getNumExpediente() + ", pendiente Consulta Btv.");
					}
				}
			} else if (TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
				ResultBean resultado = servicioTramiteDuplicado.comprobarDuplicado(beans.get(i).getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					resumenDuplicado.addFallido();
					resumenTotal.addFallido();
					addActionError(resultado.getMensaje());
				} else {
					resumenDuplicado.addTramitadosTelematicamente();
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
		return donde;
	}

	/**
	 * Método para obtener el número de matrículas para trámites que ya estén matriculados.
	 * @return
	 */
	public String obtenerMatriculasBloque() throws OegamExcepcion {
		recargarPaginatedList();
		// Definición e inicialización de variables.
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;

		TramiteTraficoBean linea = new TramiteTraficoBean(true);
		int i = 0;

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Obtener Matrículas en Bloque.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return donde;
		}

		linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[0]));

		if (linea == null) {
			addActionError("No se ha encontrado el número de expediente");
			return donde;
		}

		// Nuevo contrato que utilizamos para gestionar en el caso de que el contrato de sesión sea diferente al
		// de los trámites.
		idContratoSession = null != utilesColegiado.getIdContratoSessionBigDecimal() ? utilesColegiado.getIdContratoSessionBigDecimal().toString() : null;

		tipoTramite = null != linea.getTipoTramite() ? linea.getTipoTramite().getValorEnum() : null;
		idContratoTramites = null != linea.getIdContrato() ? linea.getIdContrato().toString() : null;

		// Miramos el tipo del que es, y según esto lo mandamos por un lado o por otro para generar el XML.

		// Separaremos dependiendo del tipo.
		switch (Integer.parseInt(tipoTramite.substring(1))) {
			// Matriculación
			case T1: {
				for (i = 0; i < codSeleccionados.length; i++) {
					// Seleccionamos el registro indicado desde el jsp
					Boolean error = false;
					HashMap<String, Object> resultadoModelo = new HashMap<String, Object>();
					resultadoModelo = getModeloMatriculacion().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());

					// Recogemos los valores del modelo
					ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
					TramiteTraficoMatriculacionBean tramite = (TramiteTraficoMatriculacionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

					// Comprobamos que se realizó bien
					if (resultado.getError()) {
						addActionError("El trámite " + codSeleccionados[i] + " no es de matriculación, o no se encontró.");
						log.trace(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
						error = true;
					}

					if (!error) {
						if (tramite.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()) || utilesColegiado.tienePermiso("OAB01")) {

							ResultBean resultadoMatriculacion = obtieneMatricula(tramite);

							if (!resultadoMatriculacion.getError()) {
								addActionMessage("Trámite con número de expediente " + codSeleccionados[i] + " correcto: " + resultadoMatriculacion.getMensaje());
							} else {
								addActionError("Trámite con número de expediente " + codSeleccionados[i] + " erróneo: " + resultadoMatriculacion.getMensaje());
							}

						} else {
							addActionError("Trámite con número de expediente " + linea.getNumExpediente().toString() + " no está en estado Finalizado PDF y no tiene permiso especial");
						}
					}
				}
			}
				break;
			// Transmisión
			case T2: {
				// Para transmision
				addActionError("Sólo se pueden obtener matrículas para Trámites de Matriculación.");
			}
				break;
			// Transmisión
			case T7: {
				// Para transmision
				addActionError("Sólo se pueden obtener matrículas para Trámites de Matriculación.");
			}
				break;
			// Bajas
			case T3: {
				addActionError("Sólo se pueden obtener matrículas para Trámites de Matriculación.");
				// Para Bajas en el caso de que haya que hacerlo.

			}
				break;
			// Solicitudes
			case T4: {
				addActionError("Sólo se pueden obtener matrículas para Trámites de Matriculación.");
				// Para solicitudes en el caso de que haya que hacerlo.
			}
				break;
			// No tiene tipo, se define el error y se redirecciona a la pagina de consulta
			default: {
				addActionError("Este trámite no tiene asignado ningún tipo");
				log.debug("Este trámite no tiene asignado ningún tipo");
			}
		}
		return donde;
	}

	/**
	 * Construye el xml, lo valida el tramite contra su esquema, lo firma e incluye una entrada en la tabla de procesos
	 * @return
	 */

	private String getTipoFirma() {
		return gestorPropiedades.valorPropertie(ConstantesFirma.TIPO_FIRMA_MATRICULACION);
	}

	public String cargarPopUpJustificantesProf() {
		// validar todos mismo tipo tramite
		ResultBean resultado = servicioConsultaTramiteTrafico.validarMismoTipoTramiteGenJustificante(listaExpedientes);
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			return recargarPaginatedList();
		}
		tipoTramiteGenerar = (String) resultado.getAttachment("tipoTramite");
		return POP_UP_JUSTIFICANTE_PRO;
	}

	public String generarJustifProfNuevo() {

		// -- Cambios del check
		String[] codSeleccionados = null;
		if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
			codSeleccionados = listaExpedientes.replace(" ", "").split(",");
		}

		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmisionTelematica = new ResumenTramitacionTelematica(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());

		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Generar justificantes en bloque telemáticamente.");

		ResultadoConsultaJustProfBean resultadoJustifBean = servicioConsultaTramiteTrafico.generarJustificanteProfDesdeConsultaTramite(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(),
				motivoJustificantes, documentoJustificantes, diasValidezJustificantes);
		if (!resultadoJustifBean.getError()) {
			rellenarResumenJustificantes(resultadoJustifBean, resumenDuplicado, resumenTransmisionTelematica, resumenOtros, resumenTotal);
		} else {
			addActionError(resultadoJustifBean.getMensaje());
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenDuplicado);
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenTransmisionTelematica);
		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return recargarPaginatedList();
	}
	
	public String cargarPopUp() {
		return POP_UP_MOTIVO;
	}
	public String generarJustificanteEnBloque() throws Throwable {
		recargarPaginatedList();
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
		List<TramiteTraficoBean> beans = new ArrayList<TramiteTraficoBean>();

		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenTransmisionTelematica = new ResumenTramitacionTelematica(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());

		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Generar justificantes en bloque telemáticamente.");

		String[] exped_seleccionados = prepararExpedientesSeleccionados();
		if (codSeleccionados.length != 1) {
			addActionError("Sólamente se puede generar el justificante  de 1 en 1.");
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		}
		numExpediente = exped_seleccionados[0];
		tramiteTrafico = conversor.transform(obtenerBeanConNumExpediente(new BigDecimal(numExpediente)), TramiteTrafDto.class);

		if (!UtilesVistaJustificanteProfesional.getInstance().esGenerableJustificante(tramiteTrafico)) {
			addActionError("No puede obtener mas de un justificante a la vez por tramite.");
			return donde;

		}

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			resumenOtros.addFallido();
			resumenTotal.addFallido();
			resumenTramitacionTelematica.add(resumenOtros);
			resumenTramitacionTelematica.add(resumenTotal);
			resumenTramitacion = true;
			return donde;
		}

		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES)) {
			addActionError("No tiene permiso para realizar un justificante.");
			return donde;
		}
		// obtenemos los trámites, para ver si existen y de qué tipo son, más adelante obtendremos el detalle dependiendo del tipo
		for (int i = 0; i < codSeleccionados.length; i++) {
			beans.add(obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i])));
		}

		// Ahora controlamos el número de créditos disponibles para cada tipo
		boolean puedeTransTelematica = true;
		boolean puedeTrans = true;
		ResultBean rTipoTramites = servicioTramiteTrafico.getTipoTramite(codSeleccionados);

		// Si están validados telemáticamente, los tramitamos dependiendo del tipo
		if (getTipoFirma().equals(ConstantesFirma.TIPO_FIRMA_HSM)) {
			// recorremos los tramites
			for (int i = 0; i < beans.size(); i++) {
				if (beans.get(i) == null) {
					if (TipoTramiteTrafico.Transmision.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
						resumenTransmision.addFallido();
						resumenTotal.addFallido();
					} else if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(rTipoTramites.getAttachment(codSeleccionados[i]))) {
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
					} else {
						resumenOtros.addFallido();
						resumenTotal.addFallido();
					}
					addActionError("No existen datos para ese expediente");
				} else {
					// TRANSMISION ELECTRONICA
					if (TipoTramiteTrafico.TransmisionElectronica.equals(beans.get(i).getTipoTramite()) && puedeTransTelematica) {
						Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
						// Recogemos los valores del modelo
						ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultadoDetalle.getError()) {
							addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						} else {
							try {
								getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionElectronica(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
										utilesColegiado.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
										MotivoJustificante.convertir(motivoJustificantes), DocumentosJustificante.convertir(documentoJustificantes), false, false);
								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
								resumenTransmisionTelematica.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} catch (ValidacionJustificanteRepetidoExcepcion e) {
								/*
								 * Si no se permite crear un justificante (ni siquiera forzándolo) para un adquiriente y matrícula que previamente ya se había generado
								 */
								if (SI.equals(Constantes.PERMITE_SER_FORZADO)) {
									servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(beans.get(i).getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
									addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
								} else {
									addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
								}
								resumenTransmision.addFallido();
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}

							catch (ValidacionJustificantePorFechaExcepcion e) {
								servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(beans.get(i).getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
								addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_POR_FECHA_EXCEPTION);

								resumenTransmision.addFallido();
								resumenTotal.addFallido();
							}

							catch (OegamExcepcion e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMensajeError1();
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}

							catch (SAXParseException spe) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. - " + getModeloJustificanteProfesional()
										.parseSAXParseException(spe);
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
								log.error("Error al generar el justificante", spe);
							}

							catch (Throwable e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMessage();
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}
						}
					}

					// TRANSMISION ACTUAL
					if (TipoTramiteTrafico.Transmision.equals(beans.get(i).getTipoTramite()) && puedeTrans) {
						Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
						// Recogemos los valores del modelo
						ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultadoDetalle.getError()) {
							addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							resumenTransmision.addFallido();
							resumenTotal.addFallido();
						} else {
							try {
								getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionActual(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
										utilesColegiado.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
										ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
								resumenTransmision.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							}catch (ValidacionJustificanteRepetidoExcepcion e) {
								/*
								 * Si no se permite crear un justificante (ni siquiera forzandolo) para un adquiriente y matricula que previamente ya se había generado
								 */
								if (SI.equals(Constantes.PERMITE_SER_FORZADO)) {
									servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(beans.get(i).getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
									addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
								} else {
									addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
								}
								resumenTransmision.addFallido();
								resumenTotal.addFallido();
							} catch (ValidacionJustificantePorFechaExcepcion e) {
								servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(beans.get(i).getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
								addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_POR_FECHA_EXCEPTION);
								resumenTransmision.addFallido();
								resumenTotal.addFallido();
								log.error("", e);
							} catch (OegamExcepcion e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMensajeError1();
								addActionError(error);
								resumenTransmision.addFallido();
								resumenTotal.addFallido();
								log.error("", e);
							}
						}
					}
					if (TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
						HashMap<String, Object> tramiteDetalle = getModeloDuplicado().obtenerDetalle(beans.get(i).getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
						ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoDuplicadoBean tramite = (TramiteTraficoDuplicadoBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

						if (resultadoDetalle.getError()) {
							addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							resumenDuplicado.addFallido();
							resumenTotal.addFallido();
						} else {
							try {
								getModeloJustificanteProfesional().validarYGenerarJustificanteTramiteDuplicados(tramite, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
										.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
										ConstantesTrafico.MOTIVO_DUPLICADO_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
								resumenDuplicado.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} catch (ValidacionJustificanteRepetidoExcepcion e) {
								servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(beans.get(i).getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
								addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							} catch (ValidacionJustificantePorFechaExcepcion e) {
								servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(beans.get(i).getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
								addActionError("Tramite " + beans.get(i).getNumExpediente() + "; " + Constantes.JUSTIFICANTE_POR_FECHA_EXCEPTION);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							} catch (OegamExcepcion e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMensajeError1();
								addActionError(error);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							} catch (Throwable e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMessage();
								addActionError(error);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							}
						}
					} // OTROS TIPOS
					else if (!TipoTramiteTrafico.TransmisionElectronica.equals(beans.get(i).getTipoTramite())
							&& !TipoTramiteTrafico.Transmision.equals(beans.get(i).getTipoTramite())
							&& !TipoTramiteTrafico.Baja.equals(beans.get(i).getTipoTramite())
							&& !TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
						addActionError("Tramite de tipo " + beans.get(i).getTipoTramite() + " no puede generar justificante profesional");
						resumenOtros.addFallido();
						resumenTotal.addFallido();
					}
				}
			}
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenTransmisionTelematica);
		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return donde;
	}

	private boolean validarEstados(TramiteTraficoBean tramiteTraficoBean) {
		boolean validar = true;
		if (EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTraficoBean.getEstado().getValorEnum()) || EstadoTramiteTrafico.LiberadoEEFF.getValorEnum().equals(
				tramiteTraficoBean.getEstado().getValorEnum())) {
			if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoLiberarEEFF()) {
				if (tramiteTraficoBean.getTipoTramite() == TipoTramiteTrafico.Matriculacion && EstadoTramiteTrafico.LiberadoEEFF.getValorEnum().equals(tramiteTraficoBean.getEstado().getValorEnum())) {
					LiberacionEEFFDto liberadoEEFF = servicioEEFF.getLiberacionEEFFDto(tramiteTraficoBean.getNumExpediente());
					if (liberadoEEFF == null) {
						if (tramiteTraficoBean.getVehiculo() == null || (tramiteTraficoBean.getVehiculo().getNive() != null && !tramiteTraficoBean.getVehiculo().getNive().isEmpty())) {
							validar = false;
						}
					} else if (!liberadoEEFF.getExento() && !liberadoEEFF.getRealizado()) {
						validar = false;
					}
				}
			}
		} else {
			validar = false;
		}
		return validar;
	}

	public void rellenarResumenJustificantes(ResultadoConsultaJustProfBean resultado, ResumenTramitacionTelematica resumenDuplicado, ResumenTramitacionTelematica resumenTransmisionTelematica,
			ResumenTramitacionTelematica resumenOtros, ResumenTramitacionTelematica resumenTotal) {

		if (resultado.getListaOKDuplicados() != null && !resultado.getListaOKDuplicados().isEmpty()) {
			for (String okDuplicado : resultado.getListaOKDuplicados()) {
				resumenDuplicado.addTramitadosTelematicamente();
				resumenTotal.addTramitadosTelematicamente();
				addActionMessage(okDuplicado);
			}
		}
		if (resultado.getListaErroresDuplicados() != null && !resultado.getListaErroresDuplicados().isEmpty()) {
			for (String errorDuplicado : resultado.getListaErroresDuplicados()) {
				resumenDuplicado.addFallido();
				resumenTotal.addFallido();
				addActionError(errorDuplicado);
			}
		}
		if (resultado.getListaOKTransmisiones() != null && !resultado.getListaOKTransmisiones().isEmpty()) {
			for (String okTransmisiones : resultado.getListaOKTransmisiones()) {
				resumenTransmisionTelematica.addTramitadosTelematicamente();
				resumenTotal.addTramitadosTelematicamente();
				addActionMessage(okTransmisiones);
			}
		}
		if (resultado.getListaErroresTransmisiones() != null && !resultado.getListaErroresTransmisiones().isEmpty()) {
			for (String errorTransmisiones : resultado.getListaErroresTransmisiones()) {
				resumenTransmisionTelematica.addFallido();
				resumenTotal.addFallido();
				addActionError(errorTransmisiones);
			}
		}
		if (resultado.getListaErrores() != null && !resultado.getListaErrores().isEmpty()) {
			for (String errorOtros : resultado.getListaErrores()) {
				resumenOtros.addFallido();
				resumenTotal.addFallido();
				addActionError(errorOtros);
			}
		}
	}

	private boolean generarJustificanteEnBloqueNuevo(String[] codSeleccionados, ResumenTramitacionTelematica resumenDuplicado, ResumenTramitacionTelematica resumenTransmision,
			ResumenTramitacionTelematica resumenTransmisionTelematica, ResumenTramitacionTelematica resumenOtros, ResumenTramitacionTelematica resumenTotal) {
		ResultBean resultB = servicioTramiteTrafico.getTipoTramite(codSeleccionados);

		for (String numExpediente : codSeleccionados) {
			numPeticiones = 0;
			try {
				String tipoTramite = (String) resultB.getAttachment(numExpediente);

				BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
				JustificanteProfDto justificanteProfDto = new JustificanteProfDto();

				if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
					tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(new BigDecimal(numExpediente), true);
					if (tramiteTrafTranDto != null) {
						if (tramiteTrafTranDto.getUsuarioDto() != null && tramiteTrafTranDto.getUsuarioDto().getIdUsuario() != null) {
							idUsuario = tramiteTrafTranDto.getUsuarioDto().getIdUsuario();
						}

						ResultBean result = servicioJustificanteProfesional.generarJustificanteTransmision(tramiteTrafTranDto, justificanteProfDto, idUsuario, utilesColegiado.tienePermisoAdmin());
						if (result != null) {
							String mensajes = "";
							for (String mensaje : result.getListaMensajes()) {
								mensajes = mensajes + mensaje + " ";
							}
							if (!result.getError()) {
								addActionMessage(mensajes + ": " + numExpediente);
								if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
									resumenTransmisionTelematica.addTramitadosTelematicamente();
								} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
									resumenTransmision.addTramitadosTelematicamente();
								}
								resumenTotal.addTramitadosTelematicamente();
							} else {
								if (result.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES) != null) {
									numPeticiones = (Integer) result.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES);
									if (numPeticiones == 1 || numPeticiones == 2) {
										if (codSeleccionados.length == 1) {
											tipoTramiteJustificante = TipoTramiteTrafico.TransmisionElectronica.getValorEnum();
											return true;
										} else {
											mensajes = "Ya ha solicitado más de un Justificante, no se puede generar en bloque";
										}
									}
								}
								addActionError("Error al generar el justificante para el trámite " + numExpediente + ": " + mensajes);
								if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
									resumenTransmisionTelematica.addFallido();
								} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
									resumenTransmision.addFallido();
								}
								resumenTotal.addFallido();
							}
						}
					} else {
						addActionError("Error al obtener el trámite: " + numExpediente);
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
							resumenTransmisionTelematica.addFallido();
						} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
							resumenTransmision.addFallido();
						}
						resumenTotal.addFallido();
					}
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
					tramiteTrafDuplicadoDto = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(numExpediente), true);
					if (tramiteTrafDuplicadoDto != null) {
						if (tramiteTrafDuplicadoDto.getUsuarioDto() != null && tramiteTrafDuplicadoDto.getUsuarioDto().getIdUsuario() != null) {
							idUsuario = tramiteTrafDuplicadoDto.getUsuarioDto().getIdUsuario();
						}

						ResultBean result = servicioJustificanteProfesional.generarJustificanteDuplicado(tramiteTrafDuplicadoDto, justificanteProfDto, idUsuario, utilesColegiado.tienePermisoAdmin());
						if (result != null) {
							String mensajes = "";
							for (String mensaje : result.getListaMensajes()) {
								mensajes = mensajes + mensaje + " ";
							}

							if (!result.getError()) {
								addActionMessage(mensajes + ": " + numExpediente);
								resumenDuplicado.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} else {
								if (result.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES) != null) {
									numPeticiones = (Integer) result.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES);
									if (numPeticiones == 1 || numPeticiones == 2) {
										if (codSeleccionados.length == 1) {
											tipoTramiteJustificante = TipoTramiteTrafico.Duplicado.getValorEnum();
											return true;
										} else {
											mensajes = "Ya ha solicitado más de un Justificante, no se puede generar en bloque";
										}
									}
								}
								addActionError("Error al generar el justificante para el trámite " + numExpediente + ": " + mensajes);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							}
						}
					} else {
						addActionError("Error al obtener el trámite: " + numExpediente);
						resumenDuplicado.addFallido();
						resumenTotal.addFallido();
					}
				} else {
					addActionError("El trámite " + numExpediente + " de tipo " + tipoTramite + " no tramitable telemáticamente actualmente");
					resumenOtros.addFallido();
					resumenTotal.addFallido();
				}
			} catch (OegamExcepcion e) {
				String error = "Tramite " + numExpediente + ": Error en la generacion de Justificante. ";
				error += "- " + e.getMensajeError1();
				addActionError(error);
				resumenTransmision.addFallido();
				resumenTotal.addFallido();
				log.error("", e);
			}
		}
		return false;
	}

	private void generarJustificanteProfesionalForzadoNuevo(String[] codSeleccionados, ResumenTramitacionTelematica resumenDuplicado, ResumenTramitacionTelematica resumenTransmision,
			ResumenTramitacionTelematica resumenTransmisionTelematica, ResumenTramitacionTelematica resumenOtros, ResumenTramitacionTelematica resumenTotal) {

		ResultBean resultB = servicioTramiteTrafico.getTipoTramite(codSeleccionados);

		for (String numExpediente : codSeleccionados) {
			String tipoTramite = (String) resultB.getAttachment(numExpediente);
			try {
				if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite) || TipoTramiteTrafico.Duplicado
						.getValorEnum().equals(tipoTramite)) {
					JustificanteProfDto justificanteProfDto = servicioJustificanteProfesional.getJustificanteProfesionalPorNumExpediente(new BigDecimal(numExpediente), new BigDecimal(
							EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum()));
					if (justificanteProfDto != null) {
						if (!justificanteProfDto.getEstado().toString().equals(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum())) {
							addActionError("El justificante del expediente " + justificanteProfDto.getNumExpediente() + " debe estar en Pendiente de Autorización del Colegio");
							if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
								resumenTransmisionTelematica.addFallido();
							} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
								resumenTransmision.addFallido();
							} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
								resumenDuplicado.addFallido();
							}
							resumenTotal.addFallido();
						} else {
							ResultBean result = servicioJustificanteProfesional.crearSolicitud(ProcesosEnum.EMISIONJPROF.toString(), justificanteProfDto.getIdJustificanteInterno(), utilesColegiado
									.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), null);
							if (!result.getError()) {
								addActionMessage("Solicitud de justificante enviada: " + numExpediente);
								if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
									resumenTransmisionTelematica.addTramitadosTelematicamente();
								} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
									resumenTransmision.addTramitadosTelematicamente();
								} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
									resumenDuplicado.addTramitadosTelematicamente();
								}
								resumenTotal.addTramitadosTelematicamente();
							} else {
								addActionError("Error al generar el justificante para el trámite " + numExpediente + ": " + result.getMensaje());
								if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
									resumenTransmisionTelematica.addFallido();
								} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
									resumenTransmision.addFallido();
								} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
									resumenDuplicado.addFallido();
								}
								resumenTotal.addFallido();
							}
						}
					} else {
						addActionError("No existe justificante para forzar: " + numExpediente);
						if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tipoTramite)) {
							resumenTransmisionTelematica.addFallido();
						} else if (TipoTramiteTrafico.Transmision.getValorEnum().equals(tipoTramite)) {
							resumenTransmision.addFallido();
						} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
							resumenDuplicado.addFallido();
						}
						resumenTotal.addFallido();
					}
				} else {
					addActionError("El trámite " + numExpediente + " de tipo " + tipoTramite + " no tramitable telemáticamente actualmente");
					resumenOtros.addFallido();
					resumenTotal.addFallido();
				}
			} catch (OegamExcepcion e) {
				String error = "Tramite " + numExpediente + ": Error en la generacion de Justificante. ";
				error += "- " + e.getMensajeError1();
				addActionError(error);
				resumenTransmision.addFallido();
				resumenTotal.addFallido();
				log.error("", e);
			}
		}
	}

	/**
	 * Método que mostrará el contenido de la evolucion de un trámite en un popup de la página de consulta.
	 * @return
	 */
	public String evolucion() {
		TramiteTraficoBean linea = new TramiteTraficoBean(true);

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Evolución.");

		linea = obtenerBeanConNumExpediente(new BigDecimal(numExpediente));

		if (linea == null) {
			addActionError("No se ha encontrado el número de expediente");
			return "evolucionTramite";
		}
		tipoTramite = linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null;
		return "evolucionTramite";
	}

	// MÉTODOS AUXILIARES

	/**
	 * Método auxiliar que obtendrá de la lista en sesión una lista por su número de expediente.
	 * @param expediente
	 * @return
	 */
	private TramiteTraficoBean obtenerBeanConNumExpediente(BigDecimal expediente) {
		return getModeloTrafico().buscarTramitePorNumExpediente(expediente.toString());
	}

	/**
	 * Método que valida un XML contra el xsd del 620 (620_V1.3.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXML620(File fichero) {
		// Constantes para validacion de Schemas
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		URL esquema = this.getClass().getResource("/trafico/schemas/620_V1.4.xsd");
		final String MY_SCHEMA = esquema.getFile();
		try {
			// get validation driver:
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
			// creamos el schema leyéndolo desde el XSD
			Schema schema = factory.newSchema(new StreamSource(MY_SCHEMA));
			Validator validator = schema.newValidator();
			// validamos el XML, si no lo valida devolverá el string de la excepción, si lo valida devolverá 'CORRECTO'
			validator.validate(new StreamSource(fichero));
			// Mantis 11823.David Sierra: Controla la excepcion org.xml.sax.SAXParseException y muestra un mensaje legible al usuario
			// indicandole el error producido. Tambien se muestra un mensaje mas especifico en los logs
		} catch (SAXParseException spe) {
			String error = "Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + ": Error en la generacion del XML 620. " + getModeloTransmision()
					.parseSAXParseException(spe);
			addActionError(error);
			log.error("Error al validar el XML contra el xsd del 620 (620_V1.4.xsd)", spe);
			return spe.toString();
			// Fin Mantis
		} catch (SAXException saxEx) {
			log.error(saxEx);
			return saxEx.toString();
		} catch (Exception ex) {
			log.error(ex);
			return ex.toString();
		}
		return ConstantesTrafico.XML_VALIDO;
	}

	public ResultBean obtieneMatricula(TramiteTraficoMatriculacionBean tramite) {

		ResultBean resultadoMetodo = new ResultBean();
		resultadoMetodo.setError(false);
		Bsti bsti = new Bsti();
		BstiBean bean = null;

		if (tramite.getTramiteTraficoBean().getVehiculo().getBastidor() != null && !"".equals(tramite.getTramiteTraficoBean().getVehiculo().getBastidor())) {
			bean = bsti.obtenerMatricula(tramite.getTramiteTraficoBean().getVehiculo().getBastidor(), tramite.getTramiteTraficoBean().getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
					utilesColegiado.getNumColegiadoSession(), tramite.getTramiteTraficoBean().getVehiculo().getIdVehiculo().longValue(), OrigenTGate.ObtenerMatriculaConsulta.getValorEnum());
			// Ricardo Rodriguez. Incidencia 3061. 04/12/2012
			if (bean.isDesactivadaConsulta()) {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje(bean.getMensaje());
				return resultadoMetodo;
			}
			// Fin. Ricardo Rodriguez. Incidencia 3061. 04/12/2012

			if (!bean.getError()) {
				String matricula = bean.getMatricula();

				// Realizamos la llamada al paquete matricular para añadir la matrícula al detalle del vehículob
				BeanPQMatricular beanPQMatricular = new BeanPQMatricular();
				beanPQMatricular.setP_NUM_EXPEDIENTE(tramite.getTramiteTraficoBean().getNumExpediente());
				beanPQMatricular.setP_MATRICULA(matricula);
				try {
					beanPQMatricular.setP_FECHA_MATRICULACION(tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion() != null && !"".equals(tramite.getTramiteTraficoBean().getVehiculo()
							.getFechaMatriculacion()) ? tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion().getTimestamp() : tramite.getTramiteTraficoBean().getFechaPresentacion()
									.getTimestamp());
					beanPQMatricular.setP_FECHA_PRESENTACION(tramite.getTramiteTraficoBean().getFechaPresentacion().getTimestamp());
				} catch (ParseException e) {
					resultadoMetodo.setMensaje("Error al añadir la matrícula al vehículo. No se le ha descontado ningún credito.");
					resultadoMetodo.setError(true);
					return resultadoMetodo;
				}
				beanPQMatricular.setP_ESTADO(new BigDecimal(tramite.getTramiteTraficoBean().getEstado().getValorEnum()));

				ResultBean resultBean = getModeloMatriculacion().matricular(beanPQMatricular);

				if (!resultBean.getError()) {
					tramite.getTramiteTraficoBean().getVehiculo().setMatricula(matricula);
					tramite.getTramiteTraficoBean().getVehiculo().setFechaMatriculacion(tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion() != null && !"".equals(tramite
							.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion()) ? tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion() : tramite.getTramiteTraficoBean()
									.getFechaPresentacion());
					resultadoMetodo.setMensaje("Matrícula obtenida con éxito y actualizada en el detalle del vehículo.");
				} else {
					resultadoMetodo.setError(true);
					resultadoMetodo.setMensaje(resultBean.getMensaje());
				}
			} else {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje(bean.getMensaje());
			}

			if (bean != null && bean.getError().booleanValue() && !(bean.getMensaje().equals("Error en la invocación") || bean.getMensaje().equals(
					"Se ha producido un error en la invocación al servicio de la D.G.T.") || bean.getMensaje().equals("Error en los parámetros."))) {

				// Hacer la actualizacion de créditos incrementales
				HashMap<String, Object> resultado = getModeloCreditosTrafico().descontarCreditos(utilesColegiado.getIdContratoSessionBigDecimal().toString(), utiles.convertirIntegerABigDecimal(1), "T5",
						utilesColegiado.getIdUsuarioSessionBigDecimal());

				ResultBean resultadoProcedimiento = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

				if (resultadoProcedimiento.getError()) {
					resultadoMetodo.setError(true);
					resultadoMetodo.setMensaje("Error al descontar créditos de la operación");
				} else {
					try {
						servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.GOM, utilesColegiado.getIdContratoSession(), "T5", tramite.getTramiteTraficoBean().getNumExpediente()
								.toString());
					} catch (Exception e) {
						log.error("No se pudo trazar el gasto de creditos", e);
					}
				}
			}
		} else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Debe rellenar el bastidor del vehículo para realizar la consulta BSTI. No se le ha descontado ningún credito.");
		}

		return resultadoMetodo;
	}

	// GETTERS AND SETTERS

	public Boolean getExportarDesdeAlta() {
		return exportarDesdeAlta;
	}

	public void setExportarDesdeAlta(Boolean exportarDesdeAlta) {
		this.exportarDesdeAlta = exportarDesdeAlta;
	}

	// Exportar xml de sesion
	public Boolean getExportarXmlSesion() {
		return exportarXmlSesion;
	}

	public void setExportarXmlSesion(Boolean exportarXmlSesion) {
		this.exportarXmlSesion = exportarXmlSesion;
	}

	public Boolean getImpresoEspera() {
		return impresoEspera;
	}

	public void setImpresoEspera(Boolean impresoEspera) {
		this.impresoEspera = impresoEspera;
	}

	public Boolean getElectronica() {
		return electronica;
	}

	public void setElectronica(Boolean electronica) {
		this.electronica = electronica;
	}

	public Boolean getDuplicado() {
		return duplicado;
	}

	public void setDuplicado(Boolean duplicado) {
		this.duplicado = duplicado;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public SolicitudDatosBean getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDatosBean solicitud) {
		this.solicitud = solicitud;
	}

	public TramiteTraficoMatriculacionBean getTraficoTramiteMatriculacionBean() {
		return traficoTramiteMatriculacionBean;
	}

	public void setTraficoTramiteMatriculacionBean(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		this.traficoTramiteMatriculacionBean = traficoTramiteMatriculacionBean;
	}

	public IntervinienteTrafico getPresentadorSoloAdmin() {
		return presentadorSoloAdmin;
	}

	public void setPresentadorSoloAdmin(IntervinienteTrafico presentadorSoloAdmin) {
		this.presentadorSoloAdmin = presentadorSoloAdmin;
	}

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
	}

	public void setTramiteTraficoTransmisionBean(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		this.tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBean;
	}

	public TramiteTraficoBean getTramiteTraficoBean() {
		return tramiteTraficoBean;
	}

	public void setTramiteTraficoBean(TramiteTraficoBean tramiteTraficoBean) {
		this.tramiteTraficoBean = tramiteTraficoBean;
	}

	public BigDecimal getNumCreditosTotales() {
		return numCreditosTotales;
	}

	public void setNumCreditosTotales(BigDecimal numCreditosTotales) {
		this.numCreditosTotales = numCreditosTotales;
	}

	public BigDecimal getNumCreditosDisponibles() {
		return numCreditosDisponibles;
	}

	public void setNumCreditosDisponibles(BigDecimal numCreditosDisponibles) {
		this.numCreditosDisponibles = numCreditosDisponibles;
	}

	public BigDecimal getNumCreditosBloqueados() {
		return numCreditosBloqueados;
	}

	public void setNumCreditosBloqueados(BigDecimal numCreditosBloqueados) {
		this.numCreditosBloqueados = numCreditosBloqueados;
	}

	public String getIdContratoTramites() {
		return idContratoTramites;
	}

	public void setIdContratoTramites(String idContratoTramites) {
		this.idContratoTramites = idContratoTramites;
	}

	public Boolean getLinkeado() {
		return linkeado;
	}

	public void setLinkeado(Boolean linkeado) {
		this.linkeado = linkeado;
	}

	public Boolean getImprimirDesdeAlta() {
		return imprimirDesdeAlta;
	}

	public void setImprimirDesdeAlta(Boolean imprimirDesdeAlta) {
		this.imprimirDesdeAlta = imprimirDesdeAlta;
	}

	public Boolean getResumenValidacion() {
		return resumenValidacion;
	}

	public void setResumenValidacion(Boolean resumenValidacion) {
		this.resumenValidacion = resumenValidacion;
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

	public String getIdContratoSession() {
		return idContratoSession;
	}

	public void setIdContratoSession(String idContratoSession) {
		this.idContratoSession = idContratoSession;
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

	public ParametrosPegatinaMatriculacion getEtiquetaParametros() {
		return etiquetaParametros;
	}

	public void setEtiquetaParametros(ParametrosPegatinaMatriculacion etiquetaParametros) {
		this.etiquetaParametros = etiquetaParametros;
	}

	public Boolean getExisteZip() {
		return existeZip;
	}

	public void setExisteZip(Boolean existeZip) {
		this.existeZip = existeZip;
	}

	public Boolean getImprimirFichero() {
		return imprimirFichero;
	}

	public void setImprimirFichero(Boolean imprimirFichero) {
		this.imprimirFichero = imprimirFichero;
	}

	/**
	 * Funcion para exportar los datos de los lotes de AEAT
	 * @return
	 */

	public String generarFicheroAEAT() {
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;

		recargarPaginatedList();
		String fileName = "";
		resumenFicheroAEAT = true;
		byte[] bytesSalida = null;
		String[] codSeleccionados = getListaExpedientes().replaceAll(" ", "").split(",");

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

		return donde;
	}

	public String generarFicheroMOVE() {
		String fileName = "";
		resumenFicheroMOVE = true;
		byte[] bytesSalida = null;
		
		if (!listaResumenErroresFicheroMOVE.isEmpty()) {
			listaResumenErroresFicheroMOVE.clear();
		}
		
		ResultadoFicheroSolicitud05Bean resultado = servicioConsultaTramiteTrafico.generarFicheroMOVE(getListaExpedientes(),listaResumenErroresFicheroMOVE);
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
	
	public String generarFicheroSolicitud05() throws IOException {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();

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
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String descargarFichero05() {
		ResultadoFicheroSolicitud05Bean resultado = servicioConsultaTramiteTrafico.descargarFichero05(resumenFicheroSolicitud05.getNombreFichero());
		if (resultado.getError()) {
			addActionError(resultado.getMensajeError());
		} else {
			try {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getNombreFichero();
				return "descargarFichero";
			} catch (FileNotFoundException e) {
				log.error("No se ha podido descargar el fichero,error:", e);
				addActionError("No se ha podido descargar el fichero");
			}
		}
		return actualizarPaginatedList();
	}

	/**
	 * Función para exportar los datos del modelo620 para los trámites de transmisión
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public String generarXML620() throws Throwable {
		recargarPaginatedList();
		// Definición e inicialización de variables.
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
		String fileName = "modelo620gata";
		byte[] bytesSalida = null;
		TramiteTraficoBean linea = new TramiteTraficoBean(true);
		int i = 0;

		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Exportar 620.");

		// Comprobamos que tenga algún tipo de permiso de manteniemiento
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
			return donde;
		}

		if (!exportarDesdeAlta) {

			// Comprobamos que todos los trámites son del mismo tipo
			boolean iguales = true;
			String tipo = "";
			for (int j = 0; j < codSeleccionados.length; j++) {
				linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[j]));
				if (linea == null) {
					addActionError("El trámite " + codSeleccionados[j] + " no existe. No se puede continuar con la exportación;");
					return donde;
				} else {
					if ("".equals(tipo))
						tipo = linea.getTipoTramite().getValorEnum();
					iguales = iguales && (tipo.equals(linea.getTipoTramite().getValorEnum()));
				}
			}
			// si no son todos iguales
			if (!iguales) {
				addActionError("Todos los trámites deben ser del mismo tipo");
				return donde;
			}

			linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[0]));

			if (linea == null) {
				addActionError("No se ha encontrado el número de expediente");
				return donde;
			}

		} else {
			linea = getModeloTrafico().buscarTramitePorNumExpediente(codSeleccionados[0]);

			if (linea == null) {
				addActionError("No se ha encontrado el número de expediente");
				return donde;
			}
		}

		// Nuevo contrato que utilizamos para gestionar en el caso de que el contrato de sesión sea diferente al
		// de los trámites.
		idContratoSession = null != utilesColegiado.getIdContratoSessionBigDecimal() ? utilesColegiado.getIdContratoSessionBigDecimal().toString() : null;

		tipoTramite = null != linea.getTipoTramite() ? linea.getTipoTramite().getValorEnum() : null;
		idContratoTramites = null != linea.getIdContrato() ? linea.getIdContrato().toString() : null;

		// Miramos el tipo del que es, y según esto lo mandamos por un lado o por otro para generar el XML.

		// Separaremos dependiendo del tipo.
		switch (Integer.parseInt(tipoTramite.substring(1))) {
			// Matriculación
			case T1: {
				addActionError("El modelo 620 es solo para trámites de transmisión");
			}
				break;
			// Transmisión
			case T2: {
				// Para transmision
				fileName += "Trans";
				List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<TramiteTraficoTransmisionBean>();
				for (i = 0; i < codSeleccionados.length; i++) {
					// Seleccionamos el registro indicado desde el jsp

					Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]));

					// Recogemos los valores del modelo
					ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

					// Comprobamos que se realizó bien
					if (resultado.getError()) {
						addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
						return donde;
					}

					if (!exportarDesdeAlta) {
						linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

						if (linea == null) {
							addActionError("No se ha encontrado el número de expediente");
							return donde;
						}

						if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
							clearErrorsAndMessages();
							addActionError("Los trámites seleccionados no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
							return donde;
						}

						if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
							clearErrorsAndMessages();
							addActionError("Los trámites seleccionados no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
							return donde;
						}
					}
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
					return donde;
				}

				String idSession = ServletActionContext.getRequest().getSession().getId();
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(listaTramitesTransmision, idSession);

				if (respuestaGenerarXMLJaxb.get("errores") != null && ((List<String>) respuestaGenerarXMLJaxb.get("errores")).size() > 0) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de transmión.");
					return donde;
				}

				String nombreFichero = (String) respuestaGenerarXMLJaxb.get("fichero");

				try {
					String validado = validarXML620(new File(nombreFichero));
					if (!ConstantesTrafico.XML_VALIDO.equals(validado)) {
						addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
						return donde;
					}
				} catch (Exception e) {
					addActionError("Error al generar el archivo XML");
					return donde;
				}

				try {
					bytesSalida = utiles.getBytesFromFile(new File(nombreFichero));
				} catch (Exception e) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
				}
			}
				break;
			// Transmisión
			case T7: {
				// Para transmision
				fileName += "Trans";
				List<TramiteTraficoTransmisionBean> listaTramitesTransmision = new ArrayList<>();
				for (i = 0; i < codSeleccionados.length; i++) {
					// Seleccionamos el registro indicado desde el jsp

					Map<String, Object> resultadoModelo = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(codSeleccionados[i]));

					// Recogemos los valores del modelo
					ResultBean resultado = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
					TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA);

					// Comprobamos que se realizó bien
					if (resultado.getError()) {
						addActionError("Error obteniendo un trámite: " + resultado.getMensaje());
						log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "al obtener tramites.");
						return donde;
					}

					if (!exportarDesdeAlta) {
						linea = obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i]));

						if (linea == null) {
							addActionError("No se ha encontrado el número de expediente");
							return donde;
						}

						if (!tipoTramite.equals(linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null)) {
							clearErrorsAndMessages();
							addActionError("Los trámites seleccionados no son del mismo tipo. Deben serlo para realizar exportaciones en bloque.");
							return donde;
						}

						if (!idContratoTramites.equals(null != linea.getIdContrato() ? linea.getIdContrato().toString() : null)) {
							clearErrorsAndMessages();
							addActionError("Los trámites seleccionados no tienen el mismo Contrato. Deben serlo para realizar exportaciones en bloque.");
							return donde;
						}
					}
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
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + ". No se ha podido exportar ningún 620");
					return donde;
				}

				String idSession = ServletActionContext.getRequest().getSession().getId();
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(listaTramitesTransmision, idSession);

				if (respuestaGenerarXMLJaxb.get("errores") != null && ((List<String>) respuestaGenerarXMLJaxb.get("errores")).size() > 0) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
					log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Al exportar, obteniendo FORMATOGA de transmión.");
					return donde;
				}

				String nombreFichero = (String) respuestaGenerarXMLJaxb.get("fichero");

				try {
					String validado = validarXML620(new File(nombreFichero));
					if (!ConstantesTrafico.XML_VALIDO.equals(validado)) {
						addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
						return donde;
					}
				} catch (Exception e) {
					addActionError("Error al generar el archivo XML");
					return donde;
				}

				try {
					bytesSalida = utiles.getBytesFromFile(new File(nombreFichero));
				} catch (Exception e) {
					addActionError("Error al generar el modelo 620 en bloque. Genérelo desde cada trámite para poder ver la descripción errores.");
				}
			}
				break;
			// Bajas
			case T3: {
				// Para transmisión
				addActionError("El modelo 620 es solo para trámites de transmisión");
			}
				break;
			// Solicitudes
			case T4: {
				addActionError("El modelo 620 es solo para trámites de transmisión");
				// Para solicitudes en el caso de que haya que hacerlo.
			}
				break;
			// No tiene tipo, se define el error y se redirecciona a la página de consulta
			default: {
				if (!exportarDesdeAlta) {
					addActionError("Este trámite no tiene asignado ningún tipo");
					log.debug("Este trámite no tiene asignado ningún tipo");
				}
			}
		}
		// Aquí irá lo que se hace cuando se recupere el fichero si no ha habido errores..
		// ponemos el esperaImpreso a true para que lo recupere desde la jsp llamando al siguiente action,
		// o incluso lo metemos en sesion con el nombre que en imprimir y lo recuperamos y mostramos con
		// el mostrar documento de traficoAction que ya está implementado.

		if (null != bytesSalida) {
			impresoEspera = true;
			getSession().put(ConstantesTrafico.FICHEROXML, bytesSalida);
			getSession().put(ConstantesTrafico.NOMBREXML, fileName);
			addActionMessage("Fichero XML generado correctamente.");
		}
		return donde;
	}

	public String pendientesEnvioExcel() {
		recargarPaginatedList();
		try {
			// -- Cambios del check
			String[] codSeleccionados = prepararExpedientesSeleccionados();

			if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			}

			if (!utilesColegiado.esColegiadoEnvioExcel()) {
				addActionError("No tienes permiso para realizar esta acción.");
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			}

			ResultBean resultB = servicioTramiteTrafico.getTipoTramite(codSeleccionados);

			// Para mostrar el resultado de la operación:
			ResumenPendienteExcel resumenBaja = new ResumenPendienteExcel(TipoTramiteTrafico.Baja.getNombreEnum());
			ResumenPendienteExcel resumenDuplicado = new ResumenPendienteExcel(TipoTramiteTrafico.Duplicado.getNombreEnum());
			ResumenPendienteExcel resumenCambioServicio = new ResumenPendienteExcel(TipoTramiteTrafico.CambioServicio.getNombreEnum());
			ResumenPendienteExcel resumenTotal = new ResumenPendienteExcel("TOTAL");
			for (String numExpediente : codSeleccionados) {
				if (TipoTramiteTrafico.Baja.getValorEnum().equals(resultB.getAttachment(numExpediente))) {
					String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
					if (SI.equals(nuevasBajas)) {
						ResultadoBajasBean resultado = servicioTramiteBaja.pendienteEnvioExcel(new BigDecimal(numExpediente), utilesColegiado.getIdUsuarioSessionBigDecimal());
						if (resultado.getError()) {
							addActionError(resultado.getMensaje());
							resumenBaja.addFallido();
						} else {
							addActionMessage("Baja pendiente Excels: " + numExpediente);
							resumenBaja.addPendientesExcel();
						}
					} else {
						TramiteTrafBajaDto tramiteDto = servicioTramiteTraficoBaja.getTramiteBaja(new BigDecimal(numExpediente), true);
						ResultBean resultadoBaja = servicioTramiteTraficoBaja.pendientesEnvioExcel(tramiteDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
						if (resultadoBaja.getError()) {
							resumenBaja.addFallido();
							addActionError("Ha fallado la baja con número expediente: " + numExpediente);
							for (String mensaje : resultadoBaja.getListaMensajes()) {
								addActionError(mensaje);
							}
						} else {
							addActionMessage("Baja pendiente Excels: " + numExpediente);
							resumenBaja.addPendientesExcel();
						}
					}
				} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(resultB.getAttachment(numExpediente))) {
					TramiteTrafDuplicadoDto tramiteDto = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(numExpediente), true);
					ResultBean resultadoDuplicado = servicioTramiteTraficoDuplicado.pendientesEnvioExcel(tramiteDto, utilesColegiado.getIdUsuarioSessionBigDecimal());

					if (resultadoDuplicado.getError()) {
						resumenDuplicado.addFallido();
						addActionError("Ha fallado el duplicado con número expediente: " + numExpediente);
						for (String mensaje : resultadoDuplicado.getListaMensajes()) {
							addActionError(mensaje);
						}
					} else {
						addActionMessage("Duplicado pendiente Excels: " + numExpediente);
						resumenDuplicado.addPendientesExcel();
					}
				} else if (TipoTramiteTrafico.CambioServicio.getValorEnum().equals(resultB.getAttachment(numExpediente))) {
					TramiteTrafCambioServicioDto tramiteDto = servicioTramiteTraficoCambioServicio.getTramiteCambServ(new BigDecimal(numExpediente), true);
					ResultBean resultadoCambioServicio = servicioTramiteTraficoCambioServicio.pendientesEnvioExcel(tramiteDto, utilesColegiado.getIdUsuarioSessionBigDecimal());

					if (resultadoCambioServicio.getError()) {
						resumenCambioServicio.addFallido();
						addActionError("Ha fallado el cambio de servicio con número expediente: " + numExpediente);
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
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		} catch (Throwable th) {
			log.error("ERROR: ", th);
			addActionError(th.toString());
			return Action.ERROR;
		}
	}

	public void actualizarFechaPresentacion(String numExpediente) throws ParseException, OegamExcepcion, Throwable {
		Date fechaPresentacion = null;

		Timestamp timestamp = utilesFecha.getTimestampActual();

		if (utilesFecha.esFechaLaborableConsiderandoFestivos(utilesFecha.getFechaFracionada(timestamp)) == false) { // Igualar a false tras prueba
			// Considerar día siguiente laborable
			Fecha fecha = utilesFecha.getPrimerLaborablePosterior(utilesFecha.getFechaFracionada(timestamp));
			fechaPresentacion = fecha.getDate();
		} else {
			fechaPresentacion = utilesFecha.getDate(timestamp);

		}
		ResultBean resultado = servicioTramiteTrafico.actualizarFechaPresentacion(new BigDecimal(numExpediente), fechaPresentacion);

		if(resultado.getError()){
			System.err.println("Error: " + resultado.getMensaje());
			log.error("Error al actualizar la fecha de presentación");
		}
	}

	public static void ejecutarTransacciones(Transaction tx) throws Exception {
		try {
			tx.commit();
		} catch (RuntimeException re) {
			try {
				tx.rollback();
				log.error("Se ha ejecutado el rollback: " + re);
				System.err.println("Se ha ejecutado el rollback: " + re);
			} catch (RuntimeException rbe) {
				log.error("No se ha podido ejecutar el rollback: " + rbe);
			}
		}
	}

	/**
	 * Genera el certificado de facturación para la lista de números de expediente que llegan desde la página de la consulta de trámites de tráfico.
	 */
	public String generarCertificados() {
		recargarPaginatedList();
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			ResultadoCertificadoTasasBean resultado = servicioConsultaTramiteTrafico.generarCertificadoDesdeConsultaTramite(codSeleccionados, utilesColegiado.getIdContratoSessionBigDecimal());
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
			return CONSULTA_TRAMITE_TRAFICO_RESULT;
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (Throwable th) {
			log.error("ERROR: ", th);
			addActionError(th.toString());
			return Action.ERROR;
		}
	}

	private String[] prepararExpedientesSeleccionados() {
		String[] codSeleccionados = null;
		if (getListaExpedientes() != null && !getListaExpedientes().isEmpty()) {
			codSeleccionados = getListaExpedientes().replaceAll(" ", "").split(",");
			setNumsExpediente(getListaExpedientes());
		} else if (getNumsExpediente() != null && !getNumsExpediente().isEmpty()) {
			codSeleccionados = getNumsExpediente().replaceAll(" ", "").split(",");
		} else {
			codSeleccionados = new String[1];
			codSeleccionados[0] = getNumExpediente();
			setNumsExpediente(getNumExpediente());
		}
		return codSeleccionados;
	}

	public String descargarCertificado() {
		recargarPaginatedList();
		try {
			File fichero = servicioConsultaTramiteTrafico.getFicheroCertificadosTasa(ficheroDescarga);
			if (fichero == null) {
				addActionError("Ha sucedido un error a la hora de imprimir el certificado de tasas.");
			}
			inputStream = new FileInputStream(fichero);
			ficheroDescarga = fichero.getName();
			return "descargarCertificadoTasas";
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return ERROR;
		}
	}

	/**
	 * Devuelve el nif facturación pasándole el número de expediente o null si el
	 * expediente no tiene titular facturación
	 */
	private String getNifFacturacion_NUM_EXPEDIENTE(String numeroExpediente) throws Exception {
		return servicioTramiteTrafico.getNifFacturacionPorNumExp(numeroExpediente);
	}

	/**
	 * Encapsula la lógica del cambio de estado de los trámites seleccionados en la página de la consulta de trámites de tráfico
	 * @return string para struts
	 */
	public String cambiarEstados() {
		recargarPaginatedList();
		// Mantis 28985: Descontar - Devolver creditos
		if ((SI).equals(gestorPropiedades.valorPropertie(cambioEstadoCreditosNuevo))) {
			try {
				String[] codSeleccionados = prepararExpedientesSeleccionados();
				ResultCambioEstadoBean resultado = servicioConsultaTramiteTrafico.cambiarEstadoBloque(codSeleccionados, valorEstadoCambio, utilesColegiado.getIdUsuarioSessionBigDecimal());
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
				// Traceo detallado cambio de estado sin validaciones:
				logarCambioEstado(EstadoTramiteTrafico.convertir(valorEstadoCambio), resultado.getNumExpedientesOk().toArray());

				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			} catch (Exception ex) {
				log.error(ex);
				addActionError(ex.toString());
				return Action.ERROR;
			}

		} else {
			try {
				ArrayList<BigDecimal> numExpedientesOk = new ArrayList<>();
				String[] codSeleccionados = prepararExpedientesSeleccionados();
				for (int cont = 0; cont < codSeleccionados.length; cont++) {
					String strExpediente = codSeleccionados[cont];
					BigDecimal expediente = new BigDecimal(strExpediente);
					TramiteTrafDto tramiteDto = servicioTramiteTrafico.getTramiteDto(expediente, false);

					ResultBean respuesta = servicioTramiteTrafico.cambiarEstadoSinValidacion(tramiteDto.getNumExpediente(), org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(
							tramiteDto.getEstado()), org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(valorEstadoCambio), utilesColegiado.getIdUsuarioSessionBigDecimal(), false, null);

					if (respuesta.getError()) {
						addActionError("El estado del expediente: " + tramiteDto.getNumExpediente() + " no se ha cambiado." + respuesta.getMensaje());
						resumenCambiosEstadoT.addFallido();
						log.error("Error al cambiar de estado:" + respuesta.getMensaje());
						continue;
					} else {
						resumenCambiosEstadoT.addCorrecto();
						numExpedientesOk.add(tramiteDto.getNumExpediente());
						addActionMessage("El estado del expediente: " + tramiteDto.getNumExpediente() + " se ha cambiado correctamente a '" + EstadoTramiteTrafico.convertir(valorEstadoCambio)
								.getNombreEnum() + "'");
					}
				}
				resumenCambiosEstadoFlag = true;
				resumenCambiosEstado.add(resumenCambiosEstadoT);

				// Traceo detallado cambio de estado sin validaciones:
				logarCambioEstado(EstadoTramiteTrafico.convertir(valorEstadoCambio), numExpedientesOk.toArray());
				return CONSULTA_TRAMITE_TRAFICO_RESULT;
			} catch (Exception ex) {
				log.error(ex);
				addActionError(ex.toString());
				return Action.ERROR;
			}
		}
	}

	@Override
	public Boolean validarFechas(Object object) {
		ConsultaTramiteTraficoBean objectCriterios = (ConsultaTramiteTraficoBean) object;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		Boolean esRangoValido = Boolean.FALSE;
		String mensaje = "";
		if (StringUtils.isBlank(objectCriterios.getReferenciaPropia()) && objectCriterios.getNumExpediente() == null && (objectCriterios.getTitular() != null && StringUtils.isBlank(objectCriterios
				.getTitular().getNif())) && StringUtils.isBlank(objectCriterios.getNifFacturacion()) && StringUtils.isBlank(objectCriterios.getNumBastidor()) && StringUtils.isBlank(objectCriterios
						.getMatricula())) {
			if (objectCriterios.getFechaAlta() == null && objectCriterios.getFechaPresentacion() == null) {
				addActionError("Para poder realizar una búsqueda de trámites debe de indicar alguna fecha.");
			} else {
				if (StringUtils.isNotBlank(valorRangoFechas)) {
					if (objectCriterios.getFechaAlta() != null && !objectCriterios.getFechaAlta().isfechaNula()) {
						if (objectCriterios.getFechaAlta().getFechaInicio() == null || objectCriterios.getFechaAlta().getFechaFin() == null) {
							mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
						} else {
							esRangoValido = utilesFecha.comprobarRangoFechas(objectCriterios.getFechaAlta().getFechaInicio(), objectCriterios.getFechaAlta().getFechaFin(), Integer.parseInt(
									valorRangoFechas));
							if (!esRangoValido) {
								mensaje = "Debe indicar un rango de fechas de alta no mayor a " + valorRangoFechas + " días para poder obtener los trámites.";
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
		// log.error("FIN - CAMBIO DE ESTADO SIN VALIDACIONES");
	}

	public String descargaXML() {
		recargarPaginatedList();
		String donde = "";
		try {
			// -- Cambios del check
			String[] codSeleccionados = prepararExpedientesSeleccionados();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream out = new ZipOutputStream(baos);

			for (int count = 0; count < codSeleccionados.length; count++) {
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.NOMBRE_CTIT + codSeleccionados[count], ConstantesGestorFicheros.CTIT, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.NOMBRE_CHECKCTIT + codSeleccionados[count], ConstantesGestorFicheros.CHECKCTIT, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.NOMBRE_DUPLICADO + codSeleccionados[count], ConstantesGestorFicheros.FULLDUPLICADO, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.NOMBRE_CHECKDUPLICADO + codSeleccionados[count], ConstantesGestorFicheros.CHECKDUPLICADO, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.NOMBRE_MATW + codSeleccionados[count], ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codSeleccionados[count], codSeleccionados[count], ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO);
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.CONSULTA_BTV + "_" + codSeleccionados[count], ConstantesGestorFicheros.CONSULTA_BTV,
						ConstantesGestorFicheros.XML_ENVIO);
				buscarFicheros(out, codSeleccionados[count], ConstantesGestorFicheros.TRAMITAR_BTV + "_" + codSeleccionados[count], ConstantesGestorFicheros.TRAMITAR_BTV,
						ConstantesGestorFicheros.XML_ENVIO);
			}
			if (cierraZip) {
				out.close();
				ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
				setInputStream(stream);
				setFileName("XMLenviadoDGT" + ConstantesPDF.EXTENSION_ZIP);
				donde = "descargarXMLTrafico";
			} else {
				addActionError("No existe ningún xml para ese trámite");
				donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
			}

		} catch (FileNotFoundException e) {
			log.error("Se ha producido un error al descargar el fichero del tramite. ", e);
		} catch (IOException e) {
			log.error("Se ha producido un error al descargar el fichero del tramite. ", e);
		}
		return donde;
	}

	private void buscarFicheros(ZipOutputStream out, String numExpediente, String nombre, String TipoExportar, String subTipo) {
		try {
			FileResultBean resultFileBean = gestorDocumentos.buscarFicheroPorNombreTipo(TipoExportar, subTipo, Utilidades.transformExpedienteFecha(numExpediente), nombre,
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

	public List<String> compruebafechaValidezNifSiRellena(TramiteTraficoTransmisionBean tramite) {
		List<String> adquirientesFallidos = new ArrayList<>();
		if (tramite != null && tramite.getAdquirienteBean() != null &&
			compruebaInterviniente(tramite.getAdquirienteBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.Adquiriente.getNombreEnum());
		}

		if (tramite != null && tramite.getConductorHabitualBean() != null &&
			compruebaInterviniente(tramite.getConductorHabitualBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.ConductorHabitual.getNombreEnum());
		}

		if (tramite != null && tramite.getRepresentanteAdquirienteBean() != null &&
			compruebaInterviniente(tramite.getRepresentanteAdquirienteBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.RepresentanteAdquiriente.getNombreEnum());
		}

		if (tramite != null && tramite.getTransmitenteBean() != null &&
			compruebaInterviniente(tramite.getTransmitenteBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.Transmitente.getNombreEnum());
		}

		if (tramite != null && tramite.getRepresentanteTransmitenteBean() != null &&
			compruebaInterviniente(tramite.getRepresentanteTransmitenteBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.RepresentanteTransmitente.getNombreEnum());
		}

		if (tramite != null && tramite.getPrimerCotitularTransmitenteBean() != null &&
			compruebaInterviniente(tramite.getPrimerCotitularTransmitenteBean()) == false) {
				adquirientesFallidos.add(PRIMER_COTITULAR);
		}

		if (tramite != null && tramite.getSegundoCotitularTransmitenteBean() != null &&
			compruebaInterviniente(tramite.getSegundoCotitularTransmitenteBean()) == false) {
				adquirientesFallidos.add(SEGUNDO_COTITULAR);
		}

		if (tramite != null && tramite.getPoseedorBean() != null &&
			compruebaInterviniente(tramite.getPoseedorBean()) == false) {
				adquirientesFallidos.add(POSEEDOR);
		}

		if (tramite != null && tramite.getRepresentantePoseedorBean() != null &&
			compruebaInterviniente(tramite.getRepresentantePoseedorBean()) == false) {
				adquirientesFallidos.add(REPRESENTANTE_POSEEDOR);
		}

		if (tramite != null && tramite.getArrendatarioBean() != null &&
			compruebaInterviniente(tramite.getArrendatarioBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.Arrendatario.getNombreEnum());
		}

		if (tramite != null && tramite.getRepresentanteArrendatarioBean() != null &&
			compruebaInterviniente(tramite.getRepresentanteArrendatarioBean()) == false) {
				adquirientesFallidos.add(TipoInterviniente.RepresentanteArrendatario.getNombreEnum());
		}

		return adquirientesFallidos;
	}

	public boolean compruebaInterviniente(trafico.beans.IntervinienteTrafico interviniente) {
		if (interviniente.getPersona() != null && interviniente.getPersona().getNif() != null && interviniente.getPersona().getIndefinido() != null && !interviniente.getPersona().getIndefinido()
				.equals(true)) {
			if (interviniente.getPersona().getTipoDocumentoAlternativo() == null || interviniente.getPersona().getTipoDocumentoAlternativo().equals("")) {
				if (interviniente.getPersona().getFechaCaducidadNif() == null) {
					return false;
				}
			} else {
				if (interviniente.getPersona().getFechaCaducidadAlternativo() == null) {
					return false;
				}
			}
		}
		return true;
	}

	public String generarJustificanteProfesionalForzado() {
		recargarPaginatedList();
		// No se utiliza
		// ModeloJustificanteProfesional modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		List<TramiteTraficoBean> beans = new ArrayList<TramiteTraficoBean>();
		// -- Cambios del check
		String[] codSeleccionados = prepararExpedientesSeleccionados();

		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;

		ResumenTramitacionTelematica resumenTransmisionTelematica = new ResumenTramitacionTelematica(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());
		ResumenTramitacionTelematica resumenTotal = new ResumenTramitacionTelematica("TOTAL");
		ResumenTramitacionTelematica resumenTransmision = new ResumenTramitacionTelematica(TipoTramiteTrafico.Transmision.getNombreEnum());
		ResumenTramitacionTelematica resumenDuplicado = new ResumenTramitacionTelematica(TipoTramiteTrafico.Duplicado.getNombreEnum());
		ResumenTramitacionTelematica resumenOtros = new ResumenTramitacionTelematica("Otros");

		String justificantesProfNuevos = gestorPropiedades.valorPropertie("justificantes.profesional.nuevos");
		if (SI.equalsIgnoreCase(justificantesProfNuevos)) {
			ResultadoConsultaJustProfBean resultadoJustifBean = servicioConsultaTramiteTrafico.forzarJustificantesProf(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!resultadoJustifBean.getError()) {
				rellenarResumenJustificantes(resultadoJustifBean, resumenDuplicado, resumenTransmisionTelematica, resumenOtros, resumenTotal);
			} else {
				addActionError(resultadoJustifBean.getMensaje());
			}
		} else {
			String habilitarJustificantesNuevo = gestorPropiedades.valorPropertie("habilitar.justificante.nuevo");
			if (habilitarJustificantesNuevo != null && SI.equals(habilitarJustificantesNuevo)) {
				generarJustificanteProfesionalForzadoNuevo(codSeleccionados, resumenDuplicado, resumenTransmision, resumenTransmisionTelematica, resumenOtros, resumenTotal);
			} else {
				// obtenemos los trámites, para ver si existen y de qué tipo son, más adelante obtendremos el detalle dependiendo del tipo
				for (int i = 0; i < codSeleccionados.length; i++) {
					// Comprueba que los tramites tienen el JP en el nuevo estado "Pendiente autorizacion colegio - 5
				
					if (!servicioJustificanteProfesional.hayJustificante(null, new BigDecimal(codSeleccionados[i]), EstadoJustificante.Pendiente_autorizacion_colegio)) {
						addActionError("Error al forzar la generación del Justificante Profesional para el expediente: " + codSeleccionados[i] + ". "
								+ "Para forzar la generación del Justificante profesional este debe estar en estado " + "Pendiente autorización del colegio");
						log.error("Error al forzar la generación del Justificante Profesional: " + codSeleccionados[i] + ". "
								+ "Para forzar la generación del Justificante profesional este debe estar en estado " + "Pendiente autorización del colegio");
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						continue;
					} else {
						beans.add(obtenerBeanConNumExpediente(new BigDecimal(codSeleccionados[i])));
					}
				}

				// Hay que comprobar si al menos hay 1 trámite que se encuentre
				for (int i = 0; i < beans.size(); i++) {
					// Hay que recoger la información del colegiado que generó el JP originalmente.
					BigDecimal idUsuario = null; // new BigDecimal(0);
					BigDecimal idContrato = null; // new BigDecimal(0);
					String numColegiado;
					String alias;
					String colegioDelContrato;

					if (beans.get(i).getNumColegiado() == null || beans.get(i).getNumColegiado().equals("")) {
						addActionError("No se ha podido obtener el número de colegiado para el expediente: " + beans.get(i).getNumExpediente());
						log.error("No se ha podido obtener el número de colegiado para el expediente: " + beans.get(i).getNumExpediente());
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						continue;
					}
					Colegiado colegiado = utilesColegiado.getColegiado(beans.get(i).getNumColegiado());
					idUsuario = new BigDecimal(colegiado.getUsuario().getIdUsuario());
					alias = colegiado.getAlias();

					idContrato = beans.get(i).getIdContrato();
					numColegiado = beans.get(i).getNumColegiado();
					
					ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
					if (contratoDto != null && contratoDto.getColegioDto() != null && StringUtils.isNotBlank(contratoDto.getColegioDto().getColegio())) {
						colegioDelContrato = contratoDto.getColegioDto().getColegio();
					} else {
						addActionError("Error al obtener el colegio del contrato que intentó crear el Justificante Profesional");
						log.error("Error al forzar la generación del Justificante Profesional para el expediente: " + beans.get(i).getNumExpediente());
						resumenTransmisionTelematica.addFallido();
						resumenTotal.addFallido();
						continue;
					}

					// TRANSMISION ELECTRONICA
					if (TipoTramiteTrafico.TransmisionElectronica.equals(beans.get(i).getTipoTramite())) {
						Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
						// Recogemos los valores del modelo
						ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultadoDetalle.getError()) {
							addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						} else {
							try {
								getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.TransmisionElectronica, alias, tramite.getTramiteTraficoBean(), tramite
										.getAdquirienteBean().getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
										ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
								resumenTransmisionTelematica.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} catch (OegamExcepcion e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMensajeError1();
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}

							catch (Throwable e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMessage();
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}
						}
					}
					// TRANSMISION
					if (TipoTramiteTrafico.Transmision.equals(beans.get(i).getTipoTramite())) {
						Map<String, Object> tramiteDetalle = getModeloTransmision().obtenerDetalle(beans.get(i).getNumExpediente());
						// Recogemos los valores del modelo
						ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoTransmisionBean tramite = (TramiteTraficoTransmisionBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultadoDetalle.getError()) {
							addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							resumenTransmisionTelematica.addFallido();
							resumenTotal.addFallido();
						} else {
							try {
								getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.Transmision, alias, tramite.getTramiteTraficoBean(), tramite
										.getAdquirienteBean().getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
										ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
								resumenTransmisionTelematica.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							} catch (OegamExcepcion e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMensajeError1();
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}

							catch (Throwable e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMessage();
								addActionError(error);
								resumenTransmisionTelematica.addFallido();
								resumenTotal.addFallido();
							}
						}
					}
					// DUPLICADOS
					if (TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
						HashMap<String, Object> tramiteDetalle = getModeloDuplicado().obtenerDetalle(beans.get(i).getNumExpediente(), utilesColegiado.getNumColegiadoSession(), utilesColegiado
								.getIdContratoSessionBigDecimal());
						// Recogemos los valores del modelo
						ResultBean resultadoDetalle = (ResultBean) tramiteDetalle.get(ConstantesPQ.RESULTBEAN);
						TramiteTraficoDuplicadoBean tramite = (TramiteTraficoDuplicadoBean) tramiteDetalle.get(ConstantesPQ.BEANPANTALLA);

						// Comprobamos que se realizó bien
						if (resultadoDetalle.getError()) {
							addActionError("Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Error al obtener el trámite " + beans.get(i).getNumExpediente() + ": " + resultadoDetalle.getMensaje());
							resumenTransmision.addFallido();
							resumenTotal.addFallido();
						} else {
							try {
								getModeloJustificanteProfesional().generarIssueProfesionalProofForzado(TipoTramiteTrafico.Duplicado, alias, tramite.getTramiteTrafico(), tramite.getTitular()
										.getPersona(), idUsuario, idContrato, ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, colegioDelContrato, numColegiado,
										ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, true);

								addActionMessage("Tramite " + beans.get(i).getNumExpediente() + ": " + "Solicitud de Justificante firmada y enviada.");
								resumenTransmision.addTramitadosTelematicamente();
								resumenTotal.addTramitadosTelematicamente();
							}
							catch (OegamExcepcion e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMensajeError1();
								addActionError(error);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							}
							catch (Throwable e) {
								String error = "Tramite " + beans.get(i).getNumExpediente() + ": Error en la generacion de Justificante. ";
								error += "- " + e.getMessage();
								addActionError(error);
								resumenDuplicado.addFallido();
								resumenTotal.addFallido();
							}
						}
					} else if (!TipoTramiteTrafico.Baja.equals(beans.get(i).getTipoTramite()) && !TipoTramiteTrafico.TransmisionElectronica.equals(beans.get(i).getTipoTramite())
							&& !TipoTramiteTrafico.Transmision.equals(beans.get(i).getTipoTramite()) && !TipoTramiteTrafico.Duplicado.equals(beans.get(i).getTipoTramite())) {
						addActionError("Tramite de tipo " + beans.get(i).getTipoTramite() + " no tramitable telemáticamente actualmente");
						resumenOtros.addFallido();
						resumenTotal.addFallido();
					}
				}
			}
		}

		resumenTramitacion = true;
		resumenTramitacionTelematica.add(resumenDuplicado);
		resumenTramitacionTelematica.add(resumenTransmision);
		resumenTramitacionTelematica.add(resumenTransmisionTelematica);
		resumenTramitacionTelematica.add(resumenOtros);
		resumenTramitacionTelematica.add(resumenTotal);

		return donde;
	}

	/**
	 * Realiza una solicitud de presentación del 576 para el proceso 576 por lotes
	 */
	public String presentacion576() {
		recargarPaginatedList();
		try {
			// -- Cambios del check
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			TramiteTraficoBean linea = new TramiteTraficoBean(true);

			for (int cont = 0; cont < codSeleccionados.length; cont++) {
				linea = getModeloTrafico().buscarTramitePorNumExpediente(codSeleccionados[cont]);
				if (linea == null) {
					addActionError("No se ha encontrado el expediente de número: " + codSeleccionados[cont]);
					resumenPresentacion576T.addFallido();
					continue;
				}

				// Recupera el detalle dependiendo del tipo:
				switch (Integer.parseInt(linea.getTipoTramite().getValorEnum().substring(1))) {
					// Matriculación
					case 1: {
						// Verifica el estado:
						if (linea.getEstado() != EstadoTramiteTrafico.Iniciado) {
							addActionError("El expediente de nº " + linea.getNumExpediente() + " no se encuentra en estado 'Iniciado'");
							resumenPresentacion576T.addFallido();
							break;
						}
						// Comprueba si ya se ha tramitado correctamente la presentación del modelo 576 para el trámite
						TramiteTrafMatrDto tramiteMatriculacionDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(linea.getNumExpediente(), false, false);
						if (existePresentacionPrevia576Correcta(tramiteMatriculacionDto)) {
							addActionError("El expediente de nº " + linea.getNumExpediente() + " ya ha obtenido una respuesta correcta para la Presentación del Modelo 576");
							resumenPresentacion576T.addFallido();
							break;
						}
						ResultBean resultBean = crearSolicitud576(linea.getNumExpediente().toString(), EstadoTramiteTrafico.Iniciado.getValorEnum().toString());
						if (resultBean.getError() == false) {
							presentaciones++;
							String correcto = "En cola la presentación en la AEAT. El trámite " + linea.getNumExpediente().toString()
									+ " se actualizará con el CEM y pasará a 'Iniciado' si se realiza correctamente la presentación. "
									+ "Podrá descargar el PDF con la información de la presentación desde la página de impresión de documentos";
							addActionMessage(correcto);
							// Se resetea el campo RESPUESTA_576 al igual que se hace con el campo RESPUESTA en la lógica de crearSolicitud576()
							try {
								servicioTramiteTraficoMatriculacion.actualizarRespuesta576Matriculacion(linea.getNumExpediente(), null);
							} catch (Exception e) {
								log.error("Error al actualizar la respuesta 576 del trámite de matriculación: " + numExpediente, e);
							}
							break;
						} else {
							addActionError("No se ha podido crear la solicitud de presentación del 576 para el expediente " + linea.getNumExpediente() + " " + "debido al siguiente error: "
									+ resultBean.getMensaje());
							resumenPresentacion576T.addFallido();
							break;
						}
					}
					default: {
						addActionError("El expediente " + linea.getNumExpediente() + " no es de matriculación");
						resumenPresentacion576T.addFallido();
						break;
					}
				}
			}

			resumenPresentacion576Flag = true;

			resumenPresentacion576T.setNumFallidos(resumenPresentacion576T.getNumFallidos());
			resumenPresentacion576T.setNumTasasCertificado(presentaciones);

			resumenPresentacion576.add(resumenPresentacion576T);
			return CONSULTA_TRAMITE_TRAFICO_RESULT;

		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (Throwable th) {
			log.error("ERROR: ", th);
			addActionError(th.toString());
			return Action.ERROR;
		}

	}

	private boolean existePresentacionPrevia576Correcta (TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (StringUtils.isBlank(tramiteTrafMatrDto.getRespuesta576())) {
			return false;
		}
		return tramiteTrafMatrDto.getRespuesta576().toLowerCase().contains("correcta");
	}

	private ResultBean crearSolicitud576(String numExpediente, String estadoAnterior) {

		log.info("Se va a agregar en la cola la solicitud de presentacion del 576 para el expediente:" + numExpediente + " para el Usuario:" + utilesColegiado.getIdUsuarioSessionBigDecimal());
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
			} else { // si la solicitud no se ha creado correctamente.
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

	/**
	 * Consulta GEST.
	 */
	public String consultaGest() throws Throwable {
		recargarPaginatedList();
		log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Consulta GEST");
		String errorTipoTramiteNoAdmitido = "Sólo se puede realizar la consulta GEST sobre vehículos contenidos en trámites de transmisión electrónica o en trámites de baja";
		boolean hayError = false;
		String matricula = null;
		String nif = null;
		BigDecimal numExpedienteTGate = null;
		Long idVehiculoTGate = null;
		// -- Cambios del check
		String[] valor = null;
		if (getListaExpedientes() != null && !getListaExpedientes().isEmpty()) {
			valor = getListaExpedientes().replaceAll(" ", "").split(",");
			setNumsExpediente(getListaExpedientes());
		} else if (getNumsExpediente() != null && !getNumsExpediente().isEmpty()) {
			valor = getNumsExpediente().replaceAll(" ", "").split(",");
		} else {
			valor = new String[1];
			valor[0] = getNumExpediente();
			setNumsExpediente(getNumExpediente());
		}
		try {
			if (!utilesColegiado.tienePermisoAdmin()) {
				addActionError("Carece de permisos para realizar la consulta GEST");
				hayError = true;
			}

			for (int i = 0; i < valor.length; i++) {
				Map<String, Object> resultadoMetodo = new HashMap<String, Object>();
				TramiteTraficoBean linea = new TramiteTraficoBean(true);
				linea = obtenerBeanConNumExpediente(new BigDecimal(valor[i]));

				if (linea == null) {
					addActionError("No se encuentra el expediente");
					return ConstantesSession.CONSULTA_TRAMITE_TRAFICO;
				}
				tipoTramite = linea.getTipoTramite() != null ? linea.getTipoTramite().getValorEnum() : null;

				switch (Integer.parseInt(tipoTramite.substring(1))) {
					// Matriculación
					case T1: {
						addActionError(errorTipoTramiteNoAdmitido);
						hayError = true;
						break;
					}
					// Transmisión
					case T2: {
						addActionError(errorTipoTramiteNoAdmitido);
						hayError = true;
						break;
					}
					// Transmisión Electrónica
					case T7: {
						tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean();
						resultadoMetodo = getModeloTransmision().obtenerDetalle(linea.getNumExpediente());
						tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);
						try {
							matricula = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getMatricula();
							if (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getIdVehiculo() != null) {
								idVehiculoTGate = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getIdVehiculo().longValue();
							}
							if (matricula == null)
								throw new Exception();
						} catch (Exception ex) {
							addActionError("No se ha podido realizar la consulta GEST. No se ha podido recuperar la matrícula del vehículo asociado al expediente: " + linea.getNumExpediente());
							hayError = true;
						}
						try {
							nif = tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif();
							if (nif == null)
								throw new Exception();
						} catch (Exception ex) {
							addActionError("No se ha podido realizar la consulta GEST. No se ha podido recuperar el nif del transmitente del vehículo asociado al expediente: " + linea
									.getNumExpediente());
							hayError = true;
						}
						numExpedienteTGate = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente();
						break;
					}
					// Bajas
					case T3: {
						TramiteTrafBajaDto tramiteTrafico = servicioTramiteTraficoBaja.getTramiteBaja(linea.getNumExpediente(), true);

						try {
							if (tramiteTrafico.getVehiculoDto() != null) {
								matricula = tramiteTrafico.getVehiculoDto().getMatricula();
								if (tramiteTrafico.getVehiculoDto().getIdVehiculo() != null) {
									idVehiculoTGate = tramiteTrafico.getVehiculoDto().getIdVehiculo().longValue();
								}
							}
							if (matricula == null)
								throw new Exception();
						} catch (Exception ex) {
							addActionError("No se ha podido realizar la consulta GEST. No se ha podido recuperar la matrícula del vehículo asociado al expediente: " + linea.getNumExpediente());
							hayError = true;
						}
						try {
							if (tramiteTrafico.getTitular() != null && tramiteTrafico.getTitular().getPersona() != null) {
								nif = tramiteTrafico.getTitular().getPersona().getNif();
							}
							if (nif == null)
								throw new Exception();
						} catch (Exception ex) {
							addActionError("No se ha podido realizar la consulta GEST. No se ha podido recuperar el nif del titular del vehículo asociado al expediente: " + linea.getNumExpediente());
							hayError = true;
						}
						numExpedienteTGate = tramiteTrafico.getNumExpediente();
						break;
					}
					// Duplicados
					case T8: {
						addActionError(errorTipoTramiteNoAdmitido);
						hayError = true;
						break;
					}
					// Solicitudes
					case T4: {
						addActionError(errorTipoTramiteNoAdmitido);
						hayError = true;
						break;
					}
					// Anuntis
					case T11: {
						addActionError(errorTipoTramiteNoAdmitido);
						hayError = true;
						break;
					}
					// No tiene tipo, se define el error y se redirecciona a la pagina de consulta
					default: {
						addActionError("Este trámite no tiene asignado ningún tipo");
						log.debug("Este trámite no tiene asignado ningún tipo");
						hayError = true;
					}
				}

				if (!hayError) {
					// CONSULTA GEST
					Gest gest = new Gest();
					GestBean gestBean = gest.obtenerCargas(matricula, nif, numExpedienteTGate, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getNumColegiadoSession(), idVehiculoTGate,
							OrigenTGate.ConsultaGest.getValorEnum());
					if (gestBean.isDesactivadaConsulta()) {
						addActionError(gestBean.getMensaje());
					} else if (gestBean != null && gestBean.getError().booleanValue()) {
						addActionError(gestBean.getMensaje());
					} else if (gestBean != null && ((gestBean.getCargas() != null && gestBean.getCargas().size() > 0) || (gestBean.getEmbargos() != null && gestBean.getEmbargos().size() > 0))) {
						if (gestBean.getCargas() != null && gestBean.getCargas().size() > 0) {
							addActionError("CARGAS: ");
							ArrayList<CargaGestBean> listaCargas = (ArrayList<CargaGestBean>) gestBean.getCargas();
							for (CargaGestBean cargaGestBean : listaCargas) {
								StringBuilder stringBuilder = new StringBuilder();
								if (cargaGestBean.getTipo() != null && !cargaGestBean.getTipo().equals("")) {
									stringBuilder.append(" TIPO : " + cargaGestBean.getTipo());
								}
								if (cargaGestBean.getFinancieraYDomicilio() != null && !cargaGestBean.getFinancieraYDomicilio().equals("")) {
									stringBuilder.append(" FINANCIERA : " + cargaGestBean.getFinancieraYDomicilio());
								}
								if (cargaGestBean.getFecha() != null && !cargaGestBean.getFecha().equals("")) {
									stringBuilder.append(" FECHA : " + cargaGestBean.getFecha());
								}
								if (cargaGestBean.getNumRegistro() != null && !cargaGestBean.getNumRegistro().equals("")) {
									stringBuilder.append(" NUM. REGISTRO : " + cargaGestBean.getNumRegistro());
								}
								addActionError(stringBuilder.toString());
							}
						}
						if (gestBean.getEmbargos() != null && gestBean.getEmbargos().size() > 0) {
							ArrayList<EmbargoGestBean> listaEmbargos = (ArrayList<EmbargoGestBean>) gestBean.getEmbargos();
							for (EmbargoGestBean embargoGestBean : listaEmbargos) {
								StringBuilder stringBuilder = new StringBuilder();
								if (embargoGestBean.getConcepto() != null && !embargoGestBean.getConcepto().equals("")) {
									stringBuilder.append(" CONCEPTO : " + embargoGestBean.getConcepto());
								}
								if (embargoGestBean.getAutoridad() != null && !embargoGestBean.getAutoridad().equals("")) {
									stringBuilder.append(" AUTORIDAD : " + embargoGestBean.getAutoridad());
								}
								if (embargoGestBean.getFmateri() != null && !embargoGestBean.getFmateri().equals("")) {
									stringBuilder.append(" FMATERI : " + embargoGestBean.getFmateri());
								}
								if (embargoGestBean.getFecha() != null && !embargoGestBean.getFecha().equals("")) {
									stringBuilder.append(" FECHA : " + embargoGestBean.getFecha());
								}
								if (embargoGestBean.getExpediente() != null && !embargoGestBean.getExpediente().equals("")) {
									stringBuilder.append(" EXPEDIENTE : " + embargoGestBean.getExpediente());
								}
								addActionError(stringBuilder.toString());
							}
						}
					} else {
						addActionMessage(gestBean.getMensaje());
					}
					// FIN DE CONSULTA GEST
				}
			}
			log.info(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + " Consulta GEST.");
			return ConstantesSession.CONSULTA_TRAMITE_TRAFICO;

		} catch (Throwable ex) {
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		}
	}

	public String liberarMasivoEeff() {
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		if (codSeleccionados == null) {
			addActionError(DEBE_SELECIONAR_EXPEDIENTE);
		} else {
			if (utilesColegiado.tienePermisoConsultaEEFF()) {
				String habilitarMatriculacion = gestorPropiedades.valorPropertie("habilitar.matriculacion.nueva");
				if (SI.equalsIgnoreCase(habilitarMatriculacion)) {
					ResultadoConsultaEEFF resultado = servicioConsultaTramiteTrafico.liberacionEEFFBloque(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
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
					List<ResultBean> result = servicioAntEEFF.liberarMasivo(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (result != null) {
						for (int i = 0; i < result.size(); i++) {
							if (result.get(i).getError()) {
								for (String errorMensaje : result.get(i).getListaMensajes())
									addActionError(errorMensaje);
							} else {
								for (String errorMensaje : result.get(i).getListaMensajes())
									addActionMessage(errorMensaje);
							}
						}
					}
				}
			} else {
				addActionError("No tiene permisos para consultas EEFF");
			}
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String consultaLiberacion() {
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		if (codSeleccionados == null) {
			addActionError(DEBE_SELECIONAR_EXPEDIENTE);
		} else {
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
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	@SuppressWarnings("unchecked")
	public String liberarDocBaseNive() {
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		if (codSeleccionados == null) {
			addActionError(DEBE_SELECIONAR_EXPEDIENTE);
		} else {
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
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	@SuppressWarnings("unchecked")
	public String cambiaEstadoIni() {
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		if (codSeleccionados == null) {
			addActionError("Selecciona algun trámite");
		} else {
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
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	@SuppressWarnings("unchecked")
	public String consultaTarjetaEitv() {
		recargarPaginatedList();
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados == null) {
				addActionError(DEBE_SELECIONAR_EXPEDIENTE);
			} else {
				ResultBean resultado = servicioConsultaTramiteTrafico.consultaEitv(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
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
			log.error(ex);
			addActionError(ex.toString());
			return Action.ERROR;
		} catch (Throwable th) {
			log.error("ERROR: ", th);
			addActionError(th.toString());
			return Action.ERROR;
		}
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	// Mantis 13236 - ASG
	public String generarSolicitudNRE06() {
		log.info("Generando solicitud NRE06");
		recargarPaginatedList();
		ResultBean result = new ResultBean();

		String[] codSeleccionados = prepararExpedientesSeleccionados();

		/* Genero el documento */
		result = servicioImprimir.imprimirNRE06(codSeleccionados);

		// Con el documento generado, realizamos el resto de acciones asociadas.
		byte[] archivo = null;
		if (result != null && !result.getError()) {
			archivo = (byte[]) result.getAttachment(ResultBean.TIPO_PDF);
			String nombreArchivo = "NRE06";
			if (result.getAttachment(ResultBean.NOMBRE_FICHERO) != null) {
				nombreArchivo = (String) result.getAttachment(ResultBean.NOMBRE_FICHERO);
			}
			for (int i = 0; i < codSeleccionados.length; i++) {
				setFileName(codSeleccionados[i] + "_" + nombreArchivo);
			}
			//setFileName(new SimpleDateFormat("ddMMyy_HHmmss.S").format(new Date()) + "_" + nombreArchivo);
			boolean guardado = false;
			if (archivo != null) {
				guardado = guardarFichero(archivo, getFileName());
				if (guardado) {
					generadoNRE06 = true;
				} else {
					addActionError("Ha sucedido un error a la hora de guardar el pdf de NRE06.");
				}
			}
			if (archivo == null || !guardado) {
				addActionError("Existen problemas al imprimir. Inténtelo más tarde");
				log.error("Problemas al imprimir solicitud NRE06");
			} else {
				addActionMessage("La impresión se realizó correctamente");
				log.info("Se ha generado correctamente los impreso de tipo " + nombreArchivo + " para los trámites " + numsExpediente + " solicitados por " + utilesColegiado.getApellidosNombreUsuario());
			}
		}

		// Muestro los errores si hay
		if (result != null && result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
			for (String mensaje : result.getListaMensajes())
				addActionError(mensaje);
		}
		return "consultaTramiteTrafico";
	}

	public String descargarFichero() {
		try {
			File f = recuperarFichero(fileName);
			if (f != null) {
				setInputStream(new FileInputStream(f.getAbsoluteFile()));
				try {
					gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.IMPRESION, null, null, fileName);
				} catch (OegamExcepcion e) {
					log.error("No se ha podido borrar el fichero");
					log.error(e);
				}
			} else {
				recargarPaginatedList();
				fileName = null;
				return SUCCESS;
			}
		} catch (FileNotFoundException e) {
			log.error("No se ha podido leer el fichero");
			log.error(e);
			addActionError("No se ha podido recuperar el documento");
			recargarPaginatedList();
			fileName = null;
			return SUCCESS;
		}
		return "descargarFichero";
	}

	private File recuperarFichero(String nombre) {
		File f = null;
		if (nombre != null && !nombre.isEmpty()) {
			try {
				f = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.IMPRESION, null, null, nombre, "").getFile();

				if (f != null && f.exists()) {
					BorrarFicherosRecursivoThread hiloBorrar = new BorrarFicherosRecursivoThread(f.getAbsolutePath(), 150000);
					hiloBorrar.start();
				}

			} catch (OegamExcepcion e) {
				log.error("Error recuperando el fichero.");
				log.error(e);
			}
		}
		return f;
	}

	private boolean guardarFichero(byte[] contenido, String nombre) {
		if (contenido == null) {
			return false;
		}
		FicheroBean fichero = new FicheroBean();
		fichero.setFicheroByte(contenido);
		fichero.setTipoDocumento(ConstantesGestorFicheros.IMPRESION);
		fichero.setNombreDocumento(nombre);
		fichero.setExtension("");
		// fichero.setSubTipo(ServletActionContext.getRequest().getSession().getId());
		File f = null;
		try {
			f = gestorDocumentos.guardarByte(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error guardando el fichero. No se podrá recuperar");
			log.error(e);
		}
		return f != null;
	}

	// TODO MPC. Cambio IVTM. Método Añadido
	// TODO Gran parte de su contenido debe ir al servicio de matriculación.
	public String autoliquidarIVTMMasivo() throws OegamExcepcion {
		recargarPaginatedList();
		if (!utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return ERROR;
		}
		String indices = getNumsExpediente();
		String[] codSeleccionados = indices.split("/");

		for (int i = 0; i < codSeleccionados.length; i++) {
			BigDecimal numExp = new BigDecimal(codSeleccionados[i]);
			if (null == numExp || numExp.equals(new BigDecimal("0"))) {
				addActionError("El expediente: " + codSeleccionados[i] + " " + ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_USO_AUTOLIQUIDACION);
				llenarResumenAutoliquidacionError(resumenTotalIVTM);
				continue;
			}
			// Se obtiene el trámite completo
			// Dto que contiene los datos del trámite
			TramiteTraficoDto tramite = modeloTramiteTraf.recuperarDtoPorNumExp(numExp);
			IvtmMatriculacionDto ivtmGuardado = null;
			ResultBean rs;
			// Se verifica que el trámite no sea nulo
			if (tramite != null && tramite.getTramiteTrafMatr() != null && tramite.getTramiteTrafMatr().getNumExpediente() != 0) {
				if (!UtilesVistaTrafico.getInstance().esConsultableOGuardableMATW(tramite)) {
					addActionError("El expediente: " + codSeleccionados[i] + " " + ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
					llenarResumenAutoliquidacionError(resumenTotalIVTM);
					continue;
				}
				// Se obtienen los datos correspondientes al IVTM
				ivtmGuardado = getServicioIVTM().recuperarPorNumExp((numExp));
				if (ivtmGuardado == null) {
					ivtmGuardado = new IvtmMatriculacionDto();
					ivtmGuardado.setNumExpediente(numExp);
					getServicioIVTM().guardarIVTM(ivtmGuardado);
				}
				rs = getServicioIVTM().puedeGenerarAutoliquidacion(numExp);
				if (rs.getError()) {
					for (String error : rs.getListaMensajes()) {
						addActionError(error);
					}
					llenarResumenAutoliquidacionError(resumenTotalIVTM);
					continue;
				}
				if (tramite.getVehiculo() != null) {
					getModeloMatriculacion().ajustarTaraDto(tramite.getVehiculo());
				}
				// Se valida
				rs = getServicioIVTM().validarTramite(numExp);
				if (rs.getError()) {// Hay errores de validación
					ivtmGuardado.setEstadoIvtm((new BigDecimal(EstadoIVTM.Iniciado.getValorEnum())));
					try {
						ivtmGuardado = getServicioIVTM().guardarIVTM(ivtmGuardado);
						if (ivtmGuardado == null) {
							addActionError("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " " + ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
							llenarResumenAutoliquidacionError(resumenTotalIVTM);
							continue;
						}
					} catch (Exception e) {
						addActionError("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " " + ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDAR_IVTM);
						llenarResumenAutoliquidacionError(resumenTotalIVTM);
						continue;
					}
					String errores = "";
					for (String mensajeError : rs.getListaMensajes()) {
						errores += mensajeError + ". ";
					}
					addActionError("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " contiene los siguientes errores: " + errores);
					resumenTotalIVTM.addFallido();
					setResumenAutoliquidacionIVTMFlag(true);
					continue;
				} else {
					addActionMessage("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " " + ConstantesIVTM.TEXTO_IVTM_VALIDACION_CORRECTA);
					// Se actualizan los datos IVTM
					ivtmGuardado.setFechaReq(new Fecha(new SimpleDateFormat(ConstantesIVTM.FORMATO_FECHA_GUARDAR_IVTM).format(new Date())));
					ivtmGuardado.setEstadoIvtm(new BigDecimal(EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum()));
					if (tramite.getNumColegiado() != null && !tramite.getNumColegiado().equals("")) {
						ivtmGuardado.setIdPeticion(getServicioIVTM().generarIdPeticion(tramite.getNumColegiado()));
					} else {
						ivtmGuardado.setIdPeticion(getServicioIVTM().generarIdPeticion(utilesColegiado.getNumColegiadoSession()));
					}
					ivtmGuardado = getServicioIVTM().guardarIVTM(ivtmGuardado);
					if (ivtmGuardado == null) {
						addActionError("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " " + ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
						llenarResumenAutoliquidacionError(resumenTotalIVTM);
						continue;
					}
					if (modeloTramiteTraf.cambiarEstado(numExp, EstadoTramiteTrafico.Pendiente_Respuesta_IVTM)) {
						// Hacemos la solicitud de la autoliquidación
						if (getServicioIVTM().solicitarIVTM(numExp)) {
							// Se pudo hacer la solicitud de autoliquidacion autoliquidación del trámite
							addActionMessage("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " " + ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_CORRECTO);
							llenarResumenAutoliquidacionCorrecto(resumenTotalIVTM);
						} else {
							addActionError("El expediente: " + numExp + " " + ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
							modeloTramiteTraf.cambiarEstado(new BigDecimal(tramite.getNumExpediente()), EstadoTramiteTrafico.Iniciado);
							ivtmGuardado.setEstadoIvtm((new BigDecimal(EstadoIVTM.Iniciado.getValorEnum())));
							ivtmGuardado = getServicioIVTM().guardarIVTM(ivtmGuardado);
							llenarResumenAutoliquidacionError(resumenTotalIVTM);
							continue;
						}
					} else {
						addActionError("El expediente: " + tramite.getTramiteTrafMatr().getNumExpediente() + " " + ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
						ivtmGuardado.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
						llenarResumenAutoliquidacionError(resumenTotalIVTM);
						continue;
					}
				}
			} else {
				addActionError(ConstantesIVTM.TEXTO_IVTM_DATOS_TRAMITE_ERROR);
				llenarResumenAutoliquidacionError(resumenTotalIVTM);
				continue;
			}
		}
		return getResultadoPorDefecto();
	}

	// TODO MPC. Cambio IVTM.
	public String pagoIVTM() throws ParseException {
		if (!utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return ERROR;
		}

		if (numExpediente != null) {
			log.info("inicio pagoIVTM del expediente" + numExpediente);
			log.info("inicio pagoIVTM del expediente en BigDecimal " + new BigDecimal(numExpediente));
		} else {
			log.info("inicio pagoIVTM - numExpediente es nulo");
		}

		Map<String, Object> resultadoMetodo = getModeloMatriculacion().obtenerDetalle(new BigDecimal(numExpediente), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
		traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) resultadoMetodo.get(ConstantesPQ.BEANPANTALLA);

		ResultBean rs = null;
		IvtmMatriculacionDto ivtmGuardado = null;

		if (traficoTramiteMatriculacionBean != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean()
				.getNumExpediente() != null) {
			ivtmGuardado = getServicioIVTM().recuperarPorNumExp(new BigDecimal(numExpediente));
			if (ivtmGuardado == null) {
				addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_PAGO);
			} else {
				rs = getServicioIVTM().validarTramitePago(new BigDecimal(numExpediente));
			}
			if (rs.getError()) {
				for (String error : rs.getListaMensajes()) {
					addActionError(error);
				}
				addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_PAGO);
				return getResultadoPorDefecto();
			} else {
				url = getServicioIVTM().crearUrl(true, ivtmGuardado, traficoTramiteMatriculacionBean);
			}
		}
		return PAGO_IVTM;
	}

	// Mantis 19591. David Sierra: Liquidacion 06 NRE
	public String generarLiquidacionNRE06() throws IOException {
		String donde = CONSULTA_TRAMITE_TRAFICO_RESULT;
		recargarPaginatedList();

		String fileName = "LiquidacionNRE06";
		resumenLiquidacionNRE06 = true;
		byte[] bytesSalida = null;
		String mensajeError = "";
		String[] codSeleccionados = getListaExpedientes().replaceAll(" ", "").split(",");

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
						return donde;
					}
					if (tramite != null) {
						if (!getModeloMatriculacion().validarEstadoTramite(tramite)) {
							addActionError("El trámite debe estar en Estado Finalizado");
							return donde;
						}
					}
					if (tramite != null && tramite.getVehiculoDto().getCodigoMarca() != null) {
						MarcaDgtVO marca = fabricanteDao.recuperarMarcaConFabricantes(tramite.getVehiculoDto().getCodigoMarca());
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
						return donde;
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
			donde = "descargarZip";
		} else {
			// Solo un expediente
			try {
				BigDecimal numExp = new BigDecimal(codSeleccionados[0]);
				tramite = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExp, utilesColegiado.tienePermisoLiberarEEFF(), true);

				if (tramite == null) {
					addActionError("Debe tener seleccionado un trámite de Matriculación");
					return donde;
				}
				if (tramite != null) {
					if (!getModeloMatriculacion().validarEstadoTramite(tramite)) {
						addActionError("El trámite debe estar en Estado Finalizado");
						return donde;
					}
				}
				if (tramite != null && tramite.getVehiculoDto().getCodigoMarca() != null) {
					MarcaDgtVO marca = fabricanteDao.recuperarMarcaConFabricantes(tramite.getVehiculoDto().getCodigoMarca());
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
					return donde;
				}
			} // Se genera el fichero para descargar
			bytesSalida = servicioFicheroAEAT.generarFicheroLiquidacionNRE06(tramite);
			if (null != bytesSalida) {
				imprimirFichero = true;
				getSession().put(ConstantesTrafico.FICHEROAEAT, bytesSalida);
				getSession().put(ConstantesTrafico.NOMBREAEAT, fileName);
			}
		}
		return donde;
	}

	// Actualizar matricula manualmente
	public String actualizarMatManual() {
		ResultBean resultBean = new ResultBean();
		HttpServletRequest request = ServletActionContext.getRequest();

		String numExpediente = request.getParameter("numExpediente");
		String matricula = request.getParameter("matricula");
		String fechaMatriculacion = request.getParameter("fechaMatriculacion");

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
		return super.inicio();
	}

	public String descargarPresentacion576() {
		String pagina = "";
		Boolean error = Boolean.FALSE;
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			ResultadoMatriculacionBean resultado = servicioConsultaTramiteTrafico.descargarFicheros576(codSeleccionados);
			if (!resultado.getError()) {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getNombreFichero();
				if (resultado.getEsZip()) {
					servicioConsultaTramiteTrafico.borrarFichero(resultado.getFichero());
				}
				pagina = "descargarFichero";
			} else {
				addActionError(resultado.getMensaje());
				error = Boolean.TRUE;
			}
		} catch (FileNotFoundException e) {
			log.error("No se ha podido descargar los ficheros seleccionados,error:", e);
			addActionError("No se ha podido descargar los ficheros seleccionados.");
			error = Boolean.TRUE;
		}
		if (error) {
			return recargarPaginatedList();
		}
		return pagina;
	}

	public String abrirPopMatriculacion() {
		return "popUpMatriculaManual";
	}

	@SuppressWarnings("unchecked")
	public String autorInfoBj() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultBean resultado = servicioConsultaTramiteTrafico.autorizarImpresionBTV(codSeleccionados, utilesColegiado.tienePermisoAdmin());
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
			} else {
				addActionError("Debe de seleccionar algún trámite para su autorización.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de autorizar los trámites seleccionados, error: ", e);
			addActionError("Ha sucedido un error a la hora de autorizar los trámites seleccionados.");
		}

		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String generarDistintivo() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoPermisoDistintivoItvBean resultado = servicioConsultaTramiteTrafico.generarDistintivos(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado
						.tienePermisoImpresionDstvB(), utilesColegiado.tienePermisoImpresionDstvC(), utilesColegiado.tienePermisoAdmin());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					resum = resultado.getResumen();
				}
			}
		} catch (Exception e) {
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
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

	public String tramiteRevisado() {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null && codSeleccionados.length > 0) {
				for (String numExpediente : codSeleccionados) {
					ResultBean respuesta = servicioTramiteTrafico.cambiarEstadoRevisado(new BigDecimal(numExpediente), utilesColegiado.getIdUsuarioSession());
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
			} else {
				addActionError("Debe seleccionar algún trámite.");
			}
		} catch (Exception e) {
			log.error("No se ha podido realizar los cambios de estado,error:", e);
			addActionError("No se ha podido realizar los cambios de estado.");
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String asignacionMasivaTasas() throws Throwable {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null && codSeleccionados.length > 0) {
				String idUsuarioTasaColegio = gestorPropiedades.valorPropertie("trafico.id.usuario.tasas.colegio");
				if (idUsuarioTasaColegio == null || idUsuarioTasaColegio.equals("")) {
					log.error(ConstantesTrafico.NO_SE_HA_RECUPERADO_EL_IDENTIFICADOR_DE_LAS_TASAS_IMPORTADAS_DESDE_EL_ICOGAM);
					addActionError(ConstantesTrafico.NO_SE_HA_RECUPERADO_EL_IDENTIFICADOR_DE_LAS_TASAS_IMPORTADAS_DESDE_EL_ICOGAM);
				} else {
					for (String numExpediente : codSeleccionados) {
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
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String tramitarNRE06() throws OegamExcepcion {
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoSolicitudNRE06Bean resultado = servicioConsultaTramiteTrafico.tramitarNRE06(codSeleccionados, getUtilesColegiado().getIdUsuarioSessionBigDecimal());
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
			log.error("No se ha podido generar el distintivo solicitado,error:", e);
			addActionError("No se ha podido generar el distintivo solicitado.");
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String cambiarFechaPresentacion() throws OegamExcepcion{
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoBean resultado = servicioConsultaTramiteTrafico.cambiarFechaPresentacion(codSeleccionados, new BigDecimal(utilesColegiado.getIdUsuarioSession()), utilesColegiado.getIdContratoSession());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("No se ha podido cambiar la fecha de presentación,error:", e);
			addActionError("No se ha podido cambiar la fecha de presentación.");
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	@SuppressWarnings("unchecked")
	public String modificarReferenciaPropia() {
		String[] codSeleccionados = prepararExpedientesSeleccionados();
		if (codSeleccionados == null) {
			addActionError("Selecciona algun trámite"); 
		} else {
			ResultBean resultado = servicioConsultaTramiteTrafico.modificarReferenciaPropia(codSeleccionados, utilesColegiado.getIdUsuarioSessionBigDecimal(),nuevaReferenciaPropia);
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
			resumenCambiosRefFlag = true;
			resumenCambiosRefT.setNumFallidos((int) lResultadoError);
			resumenCambiosRefT.setNumCambiosRef((int) lResultadoOk);
			resumenCambiosRef.add(resumenCambiosRefT);
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String autorizarTramitesIcogam() throws OegamExcepcion {
		ResultBean resultado = new ResultBean();
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null && codSeleccionados.length < 2) {
				formularioAutorizarTramitesDto = new FormularioAutorizarTramitesDto();
				for (String numExp : codSeleccionados) {
					TramiteTrafDto tramite = servicioTramiteTrafico.getTramiteDto(new BigDecimal(numExp), Boolean.TRUE);
					TramiteTrafMatrDto trafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(tramite.getNumExpediente(), Boolean.FALSE, Boolean.TRUE);
					TramiteTrafTranDto trafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramite.getNumExpediente(), Boolean.FALSE);
					if(tramite!= null && TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tramite.getTipoTramite()) && EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equals(tramite.getEstado())){
						if(esUnaFichaA(tramite.getVehiculoDto().getTipoTarjetaITV()) && TipoAutorizacion.FICHA_A.getNombreEnum().equalsIgnoreCase(valorSeleccionado)) {
							formularioAutorizarTramitesDto.setNumExpediente(tramite.getNumExpediente().toString());
							for (IntervinienteTraficoDto interviniente : tramite.getIntervinienteTraficos()) {
								if(TipoInterviniente.Titular.getValorEnum().equals(interviniente.getTipoInterviniente())){
									formularioAutorizarTramitesDto.setDoiTitular(interviniente.getNifInterviniente());
								}
							}
							formularioAutorizarTramitesDto.setJefatura(tramite.getJefaturaTraficoDto().getDescripcion());
							formularioAutorizarTramitesDto.setBastiMatri(tramite.getVehiculoDto().getBastidor());
							formularioAutorizarTramitesDto.setNumColegiado(tramite.getNumColegiado());
							formularioAutorizarTramitesDto.setEstacionItv(tramite.getVehiculoDto().getEstacionItv());
							if(tramite.getVehiculoDto().getKmUso()!= null){
								formularioAutorizarTramitesDto.setKilometraje(tramite.getVehiculoDto().getKmUso().toString());
							}
							if(StringUtils.isNotBlank(tramite.getVehiculoDto().getProcedencia())){
								PaisDto paisDto = servicioPais.getIdPaisPorSigla(tramite.getVehiculoDto().getProcedencia());
								if (paisDto != null) {
									formularioAutorizarTramitesDto.setPaisPreviaMatri(paisDto.getNombre());
								}
							}
							formularioAutorizarTramitesDto.setValorSeleccionado(valorSeleccionado);
							formularioAutorizarTramitesDto.setValorAdicional(valorAdicional);
							return AUTORIZAR_MATW_FICHAS_A;
						}else if("SI".equalsIgnoreCase(trafMatrDto.getExentoCtr()) && TipoAutorizacion.EXENTO_CTR.getNombreEnum().equalsIgnoreCase(valorSeleccionado)) {
							formularioAutorizarTramitesDto.setNumExpediente(tramite.getNumExpediente().toString());
							for (IntervinienteTraficoDto interviniente : tramite.getIntervinienteTraficos()) {
								if(TipoInterviniente.Titular.getValorEnum().equals(interviniente.getTipoInterviniente())){
									formularioAutorizarTramitesDto.setDoiTitular(interviniente.getNifInterviniente());
								}
							}
							formularioAutorizarTramitesDto.setJefatura(tramite.getJefaturaTraficoDto().getDescripcion());
							formularioAutorizarTramitesDto.setBastiMatri(tramite.getVehiculoDto().getBastidor());
							formularioAutorizarTramitesDto.setNumColegiado(tramite.getNumColegiado());
							
							TramiteTrafMatrVO trafMatrVO = servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(trafMatrDto.getNumExpediente(), Boolean.FALSE, Boolean.TRUE);
							
							if(trafMatrVO.getExentoCtr() != null) {
								formularioAutorizarTramitesDto.setExentoCtr(trafMatrVO.getExentoCtr());
							}
							if(servicioTramiteTraficoMatriculacion.esTransporteBasura(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsTransporteBasura(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esTransporteDinero(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsTransporteDinero(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esVelMaxAutorizada(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsVelMaxAutorizada(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esVehUnidoMaquina(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsVehUnidoMaquina(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esEmpresaASC(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsEmpresaASC(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esMinistCCEntLocal(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsMinistCCEntLocal(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esAutoescuela(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsAutoescuela(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esCompraventaVeh(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsCompraventaVeh(Boolean.TRUE);
							}
							if(servicioTramiteTraficoMatriculacion.esVivienda(trafMatrVO,resultado)) {
								formularioAutorizarTramitesDto.setEsVivienda(Boolean.TRUE);
							}
							formularioAutorizarTramitesDto.setValorSeleccionado(valorSeleccionado);
							formularioAutorizarTramitesDto.setValorAdicional(valorAdicional);
							return AUTORIZAR_MATW_EXENTO_CTR;
						}
					}else if(tramite!= null && TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(tramite.getTipoTramite()) && EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equals(tramite.getEstado())) {
						formularioAutorizarTramitesDto.setNumExpediente(tramite.getNumExpediente().toString());
						for (IntervinienteTraficoDto interviniente : tramite.getIntervinienteTraficos()) {
							if(TipoInterviniente.Adquiriente.getValorEnum().equals(interviniente.getTipoInterviniente())){
								formularioAutorizarTramitesDto.setDoiAdquiriente(interviniente.getNifInterviniente());
							}
							if(TipoInterviniente.TransmitenteTrafico.getValorEnum().equals(interviniente.getTipoInterviniente())){
								formularioAutorizarTramitesDto.setDoiTransmitente(interviniente.getNifInterviniente());
							}
						}
						formularioAutorizarTramitesDto.setJefatura(tramite.getJefaturaTraficoDto().getDescripcion());
						formularioAutorizarTramitesDto.setBastiMatri(tramite.getVehiculoDto().getMatricula());
						formularioAutorizarTramitesDto.setNumColegiado(tramite.getNumColegiado());
					
						if(servicioTramiteTraficoTransmision.comprobarNuevosTramites(trafTranDto)) {
							if(trafTranDto.getAcreditacionPago() != null) {
								formularioAutorizarTramitesDto.setAcreditacionPago(trafTranDto.getAcreditacionPago());
							}
							if(trafTranDto.getAcreditaHerenciaDonacion() != null) {
								formularioAutorizarTramitesDto.setAcreditacionHerenciaDonacion(trafTranDto.getAcreditaHerenciaDonacion());
							}
							/*if(trafTranDto.getActaSubasta() != null && "SI".equalsIgnoreCase(trafTranDto.getActaSubasta())) {
								formularioAutorizarTramitesDto.setAcreditacionSubastaJudicial(trafTranDto.getActaSubasta());
							}
							if(trafTranDto.getSentenciaJudicial() != null && "SI".equalsIgnoreCase(trafTranDto.getSentenciaJudicial())) {
								formularioAutorizarTramitesDto.setAcreditacionSubastaJudicial(trafTranDto.getSentenciaJudicial());
							}*/

							TramiteTrafTranVO trafTranTrafVO = servicioTramiteTraficoTransmision.getTramite(trafTranDto.getNumExpediente(), Boolean.TRUE);
							
							if(trafTranTrafVO.getExentoCtr() != null) {
								formularioAutorizarTramitesDto.setExentoCtr(trafTranTrafVO.getExentoCtr());
							}
							if("SI".equalsIgnoreCase(formularioAutorizarTramitesDto.getExentoCtr())) {
								if(servicioTramiteTraficoTransmision.esTransporteBasura(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsTransporteBasura(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esTransporteDinero(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsTransporteDinero(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esVelMaxAutorizada(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsVelMaxAutorizada(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esVehUnidoMaquina(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsVehUnidoMaquina(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esEmpresaASC(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsEmpresaASC(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esMinistCCEntLocal(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsMinistCCEntLocal(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esAutoescuela(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsAutoescuela(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esCompraventaVeh(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsCompraventaVeh(Boolean.TRUE);
								}
								if(servicioTramiteTraficoTransmision.esVivienda(trafTranTrafVO)) {
									formularioAutorizarTramitesDto.setEsVivienda(Boolean.TRUE);
								}
							}
							
							formularioAutorizarTramitesDto.setValorSeleccionado(valorSeleccionado);
							formularioAutorizarTramitesDto.setValorAdicional(valorAdicional);
						}
						return AUTORIZAR_CTIT;
					}else {
						addActionError("El trámite con numero de expediente " +tramite.getNumExpediente()+ " no está en estado Pendiente de Autorización Colegio.");
					}
				}
			}else {
				addActionError("No se puede seleccionar mas de un tramite para esta operacion.");
			}
		} catch (Exception e) {
			log.error("No se ha podido autorizar,error:", e);
			addActionError("Ha existido un error y no se ha podido autorizar.");
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String autorizarCertificacion() throws NoSuchAlgorithmException, IOException, OegamExcepcion {
		ResultBean result = null;
		String opcionSeleccionada = request.getParameter("opciones");
		String autorizacionCertificacion = gestorPropiedades.valorPropertie("autorizacion.consejo");
		String firmarAutorizacion = gestorPropiedades.valorPropertie("firma.autorizacion");
		TramiteTrafMatrDto trafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()), Boolean.FALSE, Boolean.TRUE);
		TramiteTrafTranDto trafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()), Boolean.TRUE);
		if (trafMatrDto != null && TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(trafMatrDto.getTipoTramite())) {
			if (esUnaFichaA(trafMatrDto.getVehiculoDto().getTipoTarjetaITV())) {
				if (SI.equals(autorizacionCertificacion)) {
					result = servicioTramiteTrafico.autorizarCertificacion(formularioAutorizarTramitesDto);
				} else {
					result = servicioTramiteTrafico.autorizacionInicialCertificacion(formularioAutorizarTramitesDto, opcionSeleccionada);
				}
				if (result == null || result.getError()) {
					addActionError(formularioAutorizarTramitesDto.getNumExpediente() + ": " + result.getMensaje());
				} else {
					if (SI.equals(firmarAutorizacion)) {
						result = servicioTramiteTrafico.firmarAutorizacion(formularioAutorizarTramitesDto.getNumExpediente(), utilesColegiado.getIdContratoSessionBigDecimal());
					}
					if (!result.getError()) {
						result = servicioTramiteTrafico.aceptarAutorizacionMatw(formularioAutorizarTramitesDto, trafMatrDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
						ResultBean resulCambioEstado = servicioTramiteTrafico.cambiarEstadoAutorizacion(trafTranDto,trafMatrDto,formularioAutorizarTramitesDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
						if (resulCambioEstado.getListaMensajes() != null) {
							for (String mensaje : resulCambioEstado.getListaMensajes()) {
								if (!resulCambioEstado.getError()) {
									addActionMessage(mensaje);
										result = servicioTramiteTrafico.enviarMailAutorizarColegiado(trafTranDto, trafMatrDto, formularioAutorizarTramitesDto, trafMatrDto.getTipoTramite());
								}else {
									addActionError(mensaje);
								}
							}
						}
					} else {
						addActionError(result.getMensaje());
					}
				}
			} else if ("SI".equalsIgnoreCase(trafMatrDto.getExentoCtr())) {
				result = servicioTramiteTrafico.aceptarAutorizacionMatw(formularioAutorizarTramitesDto, trafMatrDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (!result.getError()) {
					ResultBean resulCambioEstado = servicioTramiteTrafico.cambiarEstadoAutorizacion(trafTranDto,trafMatrDto,formularioAutorizarTramitesDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
					if (resulCambioEstado.getListaMensajes() != null) {
						for (String mensaje : resulCambioEstado.getListaMensajes()) {
							if (!resulCambioEstado.getError()) {
								addActionMessage(mensaje);
									result = servicioTramiteTrafico.enviarMailAutorizarColegiado(trafTranDto, trafMatrDto, formularioAutorizarTramitesDto, trafMatrDto.getTipoTramite());
							}else {
								addActionError(mensaje);
							}
						}
					}
				}else {
					for (String mensaje : result.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
				
			}
		} else if (trafTranDto != null && TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(trafTranDto.getTipoTramite())) {
			result = servicioTramiteTrafico.aceptarAutorizacionTransmision(trafTranDto,formularioAutorizarTramitesDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!result.getError()) {
				ResultBean resulCambioEstado = servicioTramiteTrafico.cambiarEstadoAutorizacion(trafTranDto,trafMatrDto,formularioAutorizarTramitesDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if(!resulCambioEstado.getError()) {
					result = servicioTramiteTrafico.enviarMailAutorizarColegiado(trafTranDto, trafMatrDto, formularioAutorizarTramitesDto, trafTranDto.getTipoTramite());
					addActionMessage("El expediente: " + formularioAutorizarTramitesDto.getNumExpediente() + " se ha autorizado para tramitar telemáticamente.");
				}else {
					if (resulCambioEstado.getListaMensajes() != null) {
						for (String error : resulCambioEstado.getListaMensajes()) {
							addActionError(error);
						}
					}
				}
			} else {
				if (result.getListaMensajes() != null) {
					for (String error : result.getListaMensajes()) {
						addActionError(error);
					}
				}
			}
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String denegarAutorizacionCertificacion() throws OegamExcepcion{
		TramiteTrafDto trafDto = servicioConsultaTramiteTrafico.getTramiteDto(new BigDecimal(formularioAutorizarTramitesDto.getNumExpediente()), Boolean.TRUE);
		TramiteTrafMatrDto trafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(trafDto.getNumExpediente(), Boolean.FALSE, Boolean.TRUE);
		TramiteTrafTranDto trafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(trafDto.getNumExpediente(), Boolean.TRUE);
		ResultBean result = servicioTramiteTrafico.denegarAutorizacion(formularioAutorizarTramitesDto,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(result == null || result.getError()) {
			addActionError(formularioAutorizarTramitesDto.getNumExpediente() + ": " + result.getMensaje());
		}else {
			ResultBean resultCorreo = servicioTramiteTrafico.enviarMailDenegarColegiado(trafTranDto,trafMatrDto,formularioAutorizarTramitesDto,trafDto.getTipoTramite());
			if(!resultCorreo.getError()) {
				addActionError(result.getMensaje() + " por los siguientes motivos: " + formularioAutorizarTramitesDto.getObservaciones());
			}
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	public String desasignarTasaCau() throws OegamExcepcion{
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultadoBean resultado = servicioConsultaTramiteTrafico.desasignarTasaCau(codSeleccionados, new BigDecimal(utilesColegiado.getIdUsuarioSession()));
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			log.error("No se ha podido desasignar la tasa,error:", e);
			addActionError("No se ha podido desasignar la tasa.");
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}
	
	public String borrarDatos() {
		ResultadoAccionUsuarioBean resultado = servicioTramiteTrafico.borrarDatos(codSeleccionados,datoBorrar,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(!resultado.getError()) {
			addActionMessage("Se ha borrado el dato " + datoBorrar + " que pertenece al número de expediente " + codSeleccionados);
			guardarActionUsuario(codSeleccionados,datoBorrar,resultado);
		}else {
			addActionError("Ha ocurrido un problema al borrar el " + datoBorrar + " que pertenece al número de expediente " + codSeleccionados);
			addActionError(resultado.getMensaje());
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
		
	}
	
	public String actualizarDatos() {
		ResultadoAccionUsuarioBean resultado = servicioTramiteTrafico.actualizarDatos(codSeleccionados,datoActualizar,datoNuevo,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(!resultado.getError()) {
			addActionMessage("Se ha actualizado el dato " + datoNuevo + " que pertenece al número de expediente " + codSeleccionados);
			guardarActionUsuario(codSeleccionados,datoNuevo,resultado);
		}else {
			addActionError("Ha ocurrido un problema al actualizar el " + datoNuevo + " que pertenece al número de expediente " + codSeleccionados);
			addActionError(resultado.getMensaje());
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
		
	}
	
	public String actualizarPueblo() {
		ResultadoAccionUsuarioBean resultado = servicioTramiteTrafico.actualizarPueblo(codSeleccionados,interviniente,puebloNuevo,utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(!resultado.getError()) {
			addActionMessage("Se ha modificado el pueblo " + puebloNuevo + " que pertenece al número de expediente " + codSeleccionados);
			guardarActionUsuario(codSeleccionados,puebloNuevo,resultado);
		}else {
			addActionError("Ha ocurrido un problema al borrar el " + datoBorrar + " que pertenece al número de expediente " + codSeleccionados);
			addActionError(resultado.getMensaje());
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
		
	}
	
	public String asignarTasaXml() throws OegamExcepcion{
		try {
			String[] codSeleccionados = prepararExpedientesSeleccionados();
			if (codSeleccionados != null) {
				ResultBean resultado = servicioConsultaTramiteTrafico.asignarTasaXml(codSeleccionados, new BigDecimal(utilesColegiado.getIdUsuarioSession()), utilesColegiado.getIdContratoSession());
				if (resultado.getError()) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				} else {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionMessage(mensaje);
					}
				}
			}
		} catch (Exception e) {
			log.error("No se ha podido asignar la tasa del Xml,error:", e);
			addActionError("No se ha podido asignar la tasa del Xml");
		
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}
	
	private void guardarActionUsuario(String numExpediente, String accion, ResultadoAccionUsuarioBean resultado) {
		String ipAccesso = "";
		try {
			ipAccesso = ServletActionContext.getRequest().getRemoteAddr();
			if (ServletActionContext.getRequest().getHeader("client-ip") != null) {
				ipAccesso = ServletActionContext.getRequest().getHeader("client-ip"); 
			}
			servicioTramiteTrafico.guardarActionBorrarDatos(numExpediente,accion,resultado,ipAccesso);
		} catch (Exception e) {
			log.error("Error UnknownHostException: ", e);
		}
		
	}

	public void buscarRespuesta() {
		ServletActionContext.getResponse().setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(new BigDecimal(numExpediente),Boolean.TRUE);
			if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getTipoTramite())) {
				TramiteTrafTranVO trafTranVO = servicioTramiteTrafico.getTramiteTransmision(new BigDecimal(numExpediente),Boolean.FALSE);
				if(TipoDatoBorrar.Respuesta_Check.getValorEnum().equalsIgnoreCase(campo) && trafTranVO.getResCheckCtit() != null) {
					out.print(trafTranVO.getResCheckCtit());
				}else {
					out.print("");
				}
				if(TipoDatoBorrar.Respuesta_Tramitación.getValorEnum().equalsIgnoreCase(campo) && tramiteTraficoVO.getRespuesta() != null) {
					out.print(tramiteTraficoVO.getRespuesta());
				}else {
					out.print("");
				}
				if(TipoDatoBorrar.Kilometraje.getValorEnum().equalsIgnoreCase(campo)) {
					VehiculoTramiteTraficoVO vehiculoTramiteTraficoVO =  servicioVehiculoImpl.getVehiculoTramite(new BigDecimal(numExpediente), tramiteTraficoVO.getVehiculo().getIdVehiculo());
					if(vehiculoTramiteTraficoVO != null && vehiculoTramiteTraficoVO.getKilometros() != null) {
						out.print(vehiculoTramiteTraficoVO.getKilometros());
					}else {
						out.print("");
					}
				}
				
			} else if(TipoTramiteTrafico.Matriculacion.getValorEnum().equalsIgnoreCase(tramiteTraficoVO.getTipoTramite()) && 
				TipoDatoBorrar.Respuesta_Matriculacion.getValorEnum().equalsIgnoreCase(campo)) {
				if(tramiteTraficoVO.getRespuesta() != null) {
					out.print(tramiteTraficoVO.getRespuesta());
				}else {
					out.print("");
				}
			}else {
				out.print("");
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los datos, error: ",e);
		}
		
	}
	
	public void buscarDatoActualizar() {
		ServletActionContext.getResponse().setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(new BigDecimal(numExpediente),Boolean.TRUE);
			if(tramiteTraficoVO != null){
				if(TipoDatoActualizar.Id_Vehiculo.getValorEnum().equalsIgnoreCase(campo)) {
					if(tramiteTraficoVO.getVehiculo() != null && tramiteTraficoVO.getVehiculo().getIdVehiculo() != null) {
						out.print(tramiteTraficoVO.getVehiculo().getIdVehiculo());
					}else {
						out.print("");
					}
				}else {
					out.print("");
				}
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los datos, error: ",e);
		}
		
	}

	 public String obtenerValorAdicional(String valorEnum, TramiteTrafTranVO trafTranVO, TramiteTrafMatrVO trafMatrVO) {
		 String valorRetorno = null;
				if(trafTranVO != null) {
					if(TipoAutorizacion.IVTM.getValorEnum().equalsIgnoreCase(valorEnum)) {
					    	valorRetorno = trafTranVO.getAutorizadoIvtm();
				     }else if(TipoAutorizacion.HERENCIA.getValorEnum().equalsIgnoreCase(valorEnum)) {
				    		valorRetorno = trafTranVO.getAutorizadoHerenciaDonacion();
				     }else if(TipoAutorizacion.DONACION.getValorEnum().equalsIgnoreCase(valorEnum)) {
				    		valorRetorno = trafTranVO.getAutorizadoHerenciaDonacion();
				     }else if(TipoAutorizacion.EXENTO_CTR.getValorEnum().equalsIgnoreCase(valorEnum)) {
				    		valorRetorno = trafTranVO.getAutorizadoHerenciaDonacion();
				     }else if(TipoAutorizacion.SUBASTA.getValorEnum().equalsIgnoreCase(valorEnum)) {
				    		//valorRetorno = trafTranVO.getAutorizadoSubastaJudicial();
				     }else if(TipoAutorizacion.JUDICIAL.getValorEnum().equalsIgnoreCase(valorEnum)) {
				    		//valorRetorno = trafTranVO.getAutorizadoSubastaJudicial();
				     }
				}else if(trafMatrVO != null) {
					if(TipoAutorizacion.FICHA_A.getValorEnum().equalsIgnoreCase(valorEnum)) {
				    	valorRetorno = trafMatrVO.getAutorizadoFichaA();
					}else if(TipoAutorizacion.EXENTO_CTR.getValorEnum().equalsIgnoreCase(valorEnum)) {
			    		valorRetorno = trafMatrVO.getAutorizadoExentoCtr();
					}
				}
				 
			return valorRetorno;
	    }
	
	public void buscarInterviniente() {
		ServletActionContext.getResponse().setContentType("text/html; charset=iso-8859-1");
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(new BigDecimal(numExpediente),Boolean.TRUE);
			IntervinienteTraficoVO titular = buscarInterviniente(tramiteTraficoVO, TipoInterviniente.Titular.getValorEnum());
			IntervinienteTraficoVO adquiriente = buscarInterviniente(tramiteTraficoVO, TipoInterviniente.Adquiriente.getValorEnum());
			IntervinienteTraficoVO transmitente = buscarInterviniente(tramiteTraficoVO, TipoInterviniente.TransmitenteTrafico.getValorEnum());
			if(TipoInterviniente.Titular.getValorEnum().equalsIgnoreCase(campo)) {
				if(titular.getDireccion() != null && titular.getDireccion().getPueblo() != null) {
					out.print(titular.getDireccion().getPueblo());
				}else {
					out.print("");
				}
			} else if(TipoInterviniente.Adquiriente.getValorEnum().equalsIgnoreCase(campo)) {
				if(adquiriente.getDireccion() != null && adquiriente.getDireccion().getPueblo() != null) {
					out.print(adquiriente.getDireccion().getPueblo());
				}else {
					out.print("");
				}
			} else if(TipoInterviniente.TransmitenteTrafico.getValorEnum().equalsIgnoreCase(campo)) {
				if(transmitente.getDireccion() != null && transmitente.getDireccion().getPueblo() != null) {
					out.print(transmitente.getDireccion().getPueblo());
				}else {
					out.print("");
				}
			}else {
				out.print("");
			}
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de cargar los pueblos, error: ",e);
		}
		
	}
	
	private IntervinienteTraficoVO buscarInterviniente(TramiteTraficoVO tramite, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (tramite.getIntervinienteTraficosAsList() != null && !tramite.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO inter : tramite.getIntervinienteTraficosAsList()) {
				if (tipoInterviniente.equals(inter.getId().getTipoInterviniente())) {
					interviniente = inter;
					break;
				}
			}
		}
		return interviniente;
	}
	
	private boolean esUnaFichaA(String tipoFicha) {
		if (tipoFicha == null) {
			return false;
		}
 
		List<String> fichasA = new ArrayList<>();
		fichasA.add(TipoTarjetaITV.A.getValorEnum());
		fichasA.add(TipoTarjetaITV.AT.getValorEnum());
		fichasA.add(TipoTarjetaITV.AR.getValorEnum());
		fichasA.add(TipoTarjetaITV.AL.getValorEnum());
 
		return fichasA.contains(tipoFicha);
	}
	
	public String descargarManualAcreditacionCtr(){
		
		try{
			File fichero = null;
			// Muestra el manual con la parte de administración dependiendo de si el usuario en sesión tiene
			// o no los permisos de un administrador:
			fichero = new File(gestorPropiedades.valorPropertie("manual.acreditacion.ctr"));
			inputStream = new FileInputStream(fichero);
			return "descargarManualAcreditacionCtr";
		}catch(FileNotFoundException ex){
			addActionError("No se ha podido recuperar el manual");
			log.error(ex);
			return Action.ERROR;
		}catch(Exception ex){
			addActionError("No se ha podido recuperar el manual");
			return Action.ERROR;
		}
	}
	
	public String descargarAnexo1(){
		
		try{
			File fichero = null;
			// Muestra el manual con la parte de administración dependiendo de si el usuario en sesión tiene
			// o no los permisos de un administrador:
				fichero = new File("C:/datos/descargas/ANEXO1.pdf");
			inputStream = new FileInputStream(fichero);
			return "descargarAnexo1";
		}catch(FileNotFoundException ex){
			addActionError("No se ha podido recuperar el manual");
			log.error(ex);
			return Action.ERROR;
		}catch(Exception ex){
			addActionError("No se ha podido recuperar el manual");
			return Action.ERROR;
		}
	}
	
	public String abrirPopUpBorrarDatos() {
		return POP_UP_BORRAR_DATOS;
	}
	
	public String abrirPopUpActualizarDatos() {
		return POP_UP_ACTUALIZAR_DATOS;
	}
	
	public String abrirPopUpActualizarPueblo() {
		return POP_UP_ACTUALIZAR_PUEBLO;
	}
	
	public String abrirPopUpAutorizarTramites() {
		TramiteTrafDto tramite = servicioTramiteTrafico.getTramiteDto(new BigDecimal(listaExpedientes), Boolean.FALSE);
		if(!EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equals(tramite.getEstado())) {
			addActionError("El trámite con numero de expediente " + tramite.getNumExpediente() +  " no está en estado Pendiente de Autorización Colegio.");
		}else {
			return POP_UP_AUTORIZAR_TRAMITES;
		}
		recargarPaginatedList();
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	private Object encriptarPDF(byte[] byte1) {
		return PdfMaker.encriptarPdf(byte1, "", "", true, false, false, false, false);
	}

	private UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}

	// TODO MPC. 6 Métodos nuevos para IVTM.
	private void llenarResumenAutoliquidacionError(ResumenAutoliquidacionIVTM resumenTotal) {
		resumenTotal.addFallido();
		setResumenAutoliquidacionIVTMFlag(true);
	}

	private void llenarResumenAutoliquidacionCorrecto(ResumenAutoliquidacionIVTM resumenTotal) {
		resumenTotal.addLiquidadosIVTM();
		setResumenAutoliquidacionIVTMFlag(true);
	}

	public boolean isResumenAutoliquidacionIVTMFlag() {
		return resumenAutoliquidacionIVTMFlag;
	}

	public void setResumenAutoliquidacionIVTMFlag(boolean resumenAutoliquidacionIVTMFlag) {
		this.resumenAutoliquidacionIVTMFlag = resumenAutoliquidacionIVTMFlag;
	}

	public ResumenAutoliquidacionIVTM getResumenTotalIVTM() {
		return resumenTotalIVTM;
	}

	public void setResumenTotalIVTM(ResumenAutoliquidacionIVTM resumenTotalIVTM) {
		this.resumenTotalIVTM = resumenTotalIVTM;
	}

	public String getFicheroDescarga() {
		return ficheroDescarga;
	}

	public void setFicheroDescarga(String ficheroDescarga) {
		this.ficheroDescarga = ficheroDescarga;
	}

	public List<ResumenPendienteExcel> getResumenPendientes() {
		return resumenPendientes;
	}

	public void setResumenPendientes(List<ResumenPendienteExcel> resumenPendientes) {
		this.resumenPendientes = resumenPendientes;
	}

	public Boolean getResumenPendienteEnvioExcel() {
		return resumenPendienteEnvioExcel;
	}

	public void setResumenPendienteEnvioExcel(Boolean resumenPendienteEnvioExcel) {
		this.resumenPendienteEnvioExcel = resumenPendienteEnvioExcel;
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

	public Boolean getExcelDesdeAlta() {
		return excelDesdeAlta;
	}

	public void setExcelDesdeAlta(Boolean excelDesdeAlta) {
		this.excelDesdeAlta = excelDesdeAlta;
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

	public Persona getPersonaFac() {
		return personaFac;
	}

	public void setPersonaFac(Persona personaFac) {
		this.personaFac = personaFac;
	}

	public ArrayList<TablaFacturacionBean> getTablaFacturacionBeanLista() {
		return tablaFacturacionBeanLista;
	}

	public void setTablaFacturacionBeanLista(ArrayList<TablaFacturacionBean> tablaFacturacionBeanLista) {
		this.tablaFacturacionBeanLista = tablaFacturacionBeanLista;
	}

	public ResumenCertificadoTasas getResumenCertificadoTasasT() {
		return resumenCertificadoTasasT;
	}

	public void setResumenCertificadoTasasT(ResumenCertificadoTasas resumenCertificadoTasasT) {
		this.resumenCertificadoTasasT = resumenCertificadoTasasT;
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

	public String getValorEstadoCambio() {
		return valorEstadoCambio;
	}

	public void setValorEstadoCambio(String valorEstadoCambio) {
		this.valorEstadoCambio = valorEstadoCambio;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public BeanPQTasasDetalle getResultadoCodigoTasa() {
		return resultadoCodigoTasa;
	}

	public void setResultadoCodigoTasa(BeanPQTasasDetalle resultadoCodigoTasa) {
		this.resultadoCodigoTasa = resultadoCodigoTasa;
	}

	/**
	 * @return the propTexto
	 */
	public String getPropTexto() {
		if (propTexto == null || propTexto.isEmpty()) {
			propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		}
		return propTexto;
	}

	/**
	 * @param propTexto the propTexto to set
	 */
	public void setPropTexto(String propTexto) {
		this.propTexto = propTexto;
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

	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}

	public void setResultBeanImprimir(ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
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

	public ModeloAcciones getModeloAcciones() {
		if (modeloAcciones == null) {
			modeloAcciones = new ModeloAcciones();
		}
		return modeloAcciones;
	}

	public void setModeloAcciones(ModeloAcciones modeloAcciones) {
		this.modeloAcciones = modeloAcciones;
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

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
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

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	public void setModeloSolicitudDatos(ModeloSolicitudDatos modeloSolicitudDatos) {
		this.modeloSolicitudDatos = modeloSolicitudDatos;
	}

	public ModeloTasas getModeloTasas() {
		if (modeloTasas == null) {
			modeloTasas = new ModeloTasas();
		}
		return modeloTasas;
	}

	public void setModeloTasas(ModeloTasas modeloTasas) {
		this.modeloTasas = modeloTasas;
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

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public UtilidadIreport getUtilidadIreport() {
		if (utilidadIreport == null) {
			utilidadIreport = new UtilidadIreport();
		}
		return utilidadIreport;
	}

	public void setUtilidadIreport(UtilidadIreport utilidadIreport) {
		this.utilidadIreport = utilidadIreport;
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

	public int getConsultasEitvEnCola() {
		return consultasEitvEnCola;
	}

	public void setConsultasEitvEnCola(int consultasEitvEnCola) {
		this.consultasEitvEnCola = consultasEitvEnCola;
	}

	public PaginatedList getListaAcciones() {
		return listaAcciones;
	}

	public void setListaAcciones(PaginatedList listaAcciones) {
		this.listaAcciones = listaAcciones;
	}

	public String getListaExpedientes() {
		return listaExpedientes;
	}

	public void setListaExpedientes(String listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}

	@Override
	public AbstractFactoryPaginatedList getFactory() {
		return new ConsultaTramiteTraficoFactoria();
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	public String getResultadoPorDefecto() {
		return CONSULTA_TRAMITE_TRAFICO_RESULT;
	}

	@Override
	public String getCriterioPaginatedList() {
		return "criterioPaginatedListConsultaTramiteTrafico";
	}

	@Override
	public String getCriteriosSession() {
		return "consultaTramiteTraficoBeanSesion";
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaConsultaTramite";
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoEspecial()) {
			((ConsultaTramiteTraficoBean) beanCriterios).setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	public boolean isBuscarInicio() {
		return false;
	}

	public Boolean getResumenFicheroAEAT() {
		return resumenFicheroAEAT;
	}

	public void setResumenFicheroAEAT(Boolean resumenFicheroAEAT) {
		this.resumenFicheroAEAT = resumenFicheroAEAT;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	@Override
	public String getSort() {
		// Incidencia 11061: Se sobreescribe este método, para que si se busca por bastidor, no se pueda ordenar.
		if (beanCriterios != null && beanCriterios instanceof ConsultaTramiteTraficoBean && ((ConsultaTramiteTraficoBean) beanCriterios).getNumBastidor() != null
				&& !((ConsultaTramiteTraficoBean) beanCriterios).getNumBastidor().isEmpty()) {
			return null;
		}
		return super.getSort();
	}

	public List<ResumenErroresFicheroAEAT> getListaResumenErroresFicheroAEAT() {
		return listaResumenErroresFicheroAEAT;
	}

	public void setListaResumenErroresFicheroAEAT(List<ResumenErroresFicheroAEAT> listaResumenErroresFicheroAEAT) {
		this.listaResumenErroresFicheroAEAT = listaResumenErroresFicheroAEAT;
	}

	public List<FicheroAEATBean> getListaFicherosAEATBean() {
		return listaFicherosAEATBean;
	}

	public void setListaFicherosAEATBean(List<FicheroAEATBean> listaFicherosAEATBean) {
		this.listaFicherosAEATBean = listaFicherosAEATBean;
	}

	public boolean isGeneradoNRE06() {
		return generadoNRE06;
	}

	public void setGeneradoNRE06(boolean generadoNRE06) {
		this.generadoNRE06 = generadoNRE06;
	}

	public IvtmMatriculacionDto getIvtmMatriculacionDto() {
		return ivtmMatriculacionDto;
	}

	public void setIvtmMatriculacionDto(IvtmMatriculacionDto ivtmMatriculacionDto) {
		this.ivtmMatriculacionDto = ivtmMatriculacionDto;
	}

	public String getIbanTitular() {
		return ibanTitular;
	}

	public void setIbanTitular(String ibanTitular) {
		this.ibanTitular = ibanTitular;
	}

	public ServicioIVTMMatriculacionIntf getServicioIVTM() {
		if (servicioIVTM == null) {
			servicioIVTM = new ServicioIVTMMatriculacionImpl();
		}
		return servicioIVTM;
	}

	public IVTMModeloMatriculacionInterface getIvtmModelo() {
		if (ivtmModelo == null) {
			ivtmModelo = new IVTMModeloMatriculacionImpl();
		}
		return ivtmModelo;
	}

	public void setIvtmModelo(IVTMModeloMatriculacionInterface ivtmModelo) {
		this.ivtmModelo = ivtmModelo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isErrorPagoIVTM() {
		return errorPagoIVTM;
	}

	public void setErrorPagoIVTM(boolean errorPagoIVTM) {
		this.errorPagoIVTM = errorPagoIVTM;
	}

	public String getRespuestaPagoIVTM() {
		return respuestaPagoIVTM;
	}

	public void setRespuestaPagoIVTM(String respuestaPagoIVTM) {
		this.respuestaPagoIVTM = respuestaPagoIVTM;
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

	public TramiteTrafTranDto getTramiteTrafTranDto() {
		return tramiteTrafTranDto;
	}

	public void setTramiteTrafTranDto(TramiteTrafTranDto tramiteTrafTranDto) {
		this.tramiteTrafTranDto = tramiteTrafTranDto;
	}

	public int getNumPeticiones() {
		return numPeticiones;
	}

	public void setNumPeticiones(int numPeticiones) {
		this.numPeticiones = numPeticiones;
	}

	public TramiteTrafDuplicadoDto getTramiteTrafDuplicadoDto() {
		return tramiteTrafDuplicadoDto;
	}

	public void setTramiteTrafDuplicadoDto(TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto) {
		this.tramiteTrafDuplicadoDto = tramiteTrafDuplicadoDto;
	}

	public String getTipoTramiteJustificante() {
		return tipoTramiteJustificante;
	}

	public void setTipoTramiteJustificante(String tipoTramiteJustificante) {
		this.tipoTramiteJustificante = tipoTramiteJustificante;
	}

	public Boolean getResumenLiquidacionNRE06() {
		return resumenLiquidacionNRE06;
	}

	public void setResumenLiquidacionNRE06(Boolean resumenLiquidacionNRE06) {
		this.resumenLiquidacionNRE06 = resumenLiquidacionNRE06;
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

	public boolean isGenerado05() {
		return generado05;
	}

	public void setGenerado05(boolean generado05) {
		this.generado05 = generado05;
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

	public boolean isMostrarBtnGest() {
		return mostrarBtnGest;
	}

	public void setMostrarBtnGest(boolean mostrarBtnGest) {
		this.mostrarBtnGest = mostrarBtnGest;
	}

	public Boolean getExisteDocJstfPermiso() {
		return existeDocJstfPermiso;
	}

	public void setExisteDocJstfPermiso(Boolean existeDocJstfPermiso) {
		this.existeDocJstfPermiso = existeDocJstfPermiso;
	}

	public String getNombreJustfPerm() {
		return nombreJustfPerm;
	}

	public void setNombreJustfPerm(String nombreJustfPerm) {
		this.nombreJustfPerm = nombreJustfPerm;
	}

	public Boolean getResumenDocumentosGeneradosFlag() {
		return resumenDocumentosGeneradosFlag;
	}

	public Boolean getResumenPermDstv() {
		return resumenPermDstv;
	}

	public void setResumenPermDstv(Boolean resumenPermDstv) {
		this.resumenPermDstv = resumenPermDstv;
	}

	public void setResumenDocumentosGeneradosFlag(Boolean resumenDocumentosGeneradosFlag) {
		this.resumenDocumentosGeneradosFlag = resumenDocumentosGeneradosFlag;
	}

	public String getMotivoJustificantes() {
		return motivoJustificantes;
	}

	public void setMotivoJustificantes(String motivoJustificantes) {
		this.motivoJustificantes = motivoJustificantes;
	}

	public String getDocumentoJustificantes() {
		return documentoJustificantes;
	}

	public void setDocumentoJustificantes(String documentoJustificantes) {
		this.documentoJustificantes = documentoJustificantes;
	}

	public String getDiasValidezJustificantes() {
		return diasValidezJustificantes;
	}

	public void setDiasValidezJustificantes(String diasValidezJustificantes) {
		this.diasValidezJustificantes = diasValidezJustificantes;
	}

	public String getTipoTramiteGenerar() {
		return tipoTramiteGenerar;
	}

	public void setTipoTramiteGenerar(String tipoTramiteGenerar) {
		this.tipoTramiteGenerar = tipoTramiteGenerar;
	}

	public String getNombreFicheroJustifIvtm() {
		return nombreFicheroJustifIvtm;
	}

	public void setNombreFicheroJustifIvtm(String nombreFicheroJustifIvtm) {
		this.nombreFicheroJustifIvtm = nombreFicheroJustifIvtm;
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

	public ResumenPermisoDistintivoItvBean getResum() {
		return resum;
	}

	public void setResum(ResumenPermisoDistintivoItvBean resum) {
		this.resum = resum;
	}

	public TramiteTrafCambioServicioDto getTramiteTraficoCambServ() {
		return tramiteTraficoCambServ;
	}

	public void setTramiteTraficoCambServ(TramiteTrafCambioServicioDto tramiteTraficoCambServ) {
		this.tramiteTraficoCambServ = tramiteTraficoCambServ;
	}

	public ResumenDocBaseBean getResumenDocBase() {
		return resumenDocBase;
	}

	public void setResumenDocBase(ResumenDocBaseBean resumenDocBase) {
		this.resumenDocBase = resumenDocBase;
	}

	public String getCodSeleccionados() {
		return codSeleccionados;
	}

	public void setCodSeleccionados(String codSeleccionados) {
		this.codSeleccionados = codSeleccionados;
	}

	public DocBaseCarpetaTramiteBean getDocBaseCarpetaTramiteBean() {
		return docBaseCarpetaTramiteBean;
	}

	public void setDocBaseCarpetaTramiteBean(DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean) {
		this.docBaseCarpetaTramiteBean = docBaseCarpetaTramiteBean;
	}

	public DatosClienteBean getDatosCliente() {
		return datosCliente;
	}

	public void setDatosCliente(DatosClienteBean datosCliente) {
		this.datosCliente = datosCliente;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public ResumenAsignacionMasivaTasas getResumenAsignacionMasivaTasasT() {
		return resumenAsignacionMasivaTasasT;
	}

	public void setResumenAsignacionMasivaTasasT(ResumenAsignacionMasivaTasas resumenAsignacionMasivaTasasT) {
		this.resumenAsignacionMasivaTasasT = resumenAsignacionMasivaTasasT;
	}

	public Boolean getResumenAsignacionMasivaTasasFlag() {
		return resumenAsignacionMasivaTasasFlag;
	}

	public void setResumenAsignacionMasivaTasasFlag(Boolean resumenAsignacionMasivaTasasFlag) {
		this.resumenAsignacionMasivaTasasFlag = resumenAsignacionMasivaTasasFlag;
	}

	public List<ResumenAsignacionMasivaTasas> getResumenAsignacionMasivaTasas() {
		return resumenAsignacionMasivaTasas;
	}

	public void setResumenAsignacionMasivaTasas(List<ResumenAsignacionMasivaTasas> resumenAsignacionMasivaTasas) {
		this.resumenAsignacionMasivaTasas = resumenAsignacionMasivaTasas;
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

	public String getDesasignarTasaAlDuplicar() {
		return desasignarTasaAlDuplicar;
	}

	public void setDesasignarTasaAlDuplicar(String desasignarTasaAlDuplicar) {
		this.desasignarTasaAlDuplicar = desasignarTasaAlDuplicar;
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

	public boolean isVolverAntiguaConsulta() {
		return volverAntiguaConsulta;
	}

	public void setVolverAntiguaConsulta(boolean volverAntiguaConsulta) {
		this.volverAntiguaConsulta = volverAntiguaConsulta;
	}

	public String getComboTasaBloqueado() {
		return comboTasaBloqueado;
	}

	public void setComboTasaBloqueado(String comboTasaBloqueado) {
		this.comboTasaBloqueado = comboTasaBloqueado;
	}

	public String getCodigoTasaTransmisionSeleccionadoId() {
		return codigoTasaTransmisionSeleccionadoId;
	}

	public void setCodigoTasaTransmisionSeleccionadoId(String codigoTasaTransmisionSeleccionadoId) {
		this.codigoTasaTransmisionSeleccionadoId = codigoTasaTransmisionSeleccionadoId;
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

	public String getNuevaReferenciaPropia() {
		return nuevaReferenciaPropia;
	}

	public void setNuevaReferenciaPropia(String nuevaReferenciaPropia) {
		this.nuevaReferenciaPropia = nuevaReferenciaPropia;
	}

	public String getDatoBorrar() {
		return datoBorrar;
	}

	public void setDatoBorrar(String datoBorrar) {
		this.datoBorrar = datoBorrar;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getPuebloNuevo() {
		return puebloNuevo;
	}

	public void setPuebloNuevo(String puebloNuevo) {
		this.puebloNuevo = puebloNuevo;
	}

	public String getInterviniente() {
		return interviniente;
	}

	public void setInterviniente(String interviniente) {
		this.interviniente = interviniente;
	}

	public FormularioAutorizarTramitesDto getFormularioAutorizarTramitesDto() {
		return formularioAutorizarTramitesDto;
	}

	public void setFormularioAutorizarTramitesDto(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto) {
		this.formularioAutorizarTramitesDto = formularioAutorizarTramitesDto;
	}

	public boolean isEsTransporteBasura() {
		return esTransporteBasura;
	}

	public void setEsTransporteBasura(boolean esTransporteBasura) {
		this.esTransporteBasura = esTransporteBasura;
	}

	public boolean isEsTransporteDinero() {
		return esTransporteDinero;
	}

	public void setEsTransporteDinero(boolean esTransporteDinero) {
		this.esTransporteDinero = esTransporteDinero;
	}

	public boolean isEsVelMaxAutorizada() {
		return esVelMaxAutorizada;
	}

	public void setEsVelMaxAutorizada(boolean esVelMaxAutorizada) {
		this.esVelMaxAutorizada = esVelMaxAutorizada;
	}

	public boolean isEsVehUnidoMaquina() {
		return esVehUnidoMaquina;
	}

	public void setEsVehUnidoMaquina(boolean esVehUnidoMaquina) {
		this.esVehUnidoMaquina = esVehUnidoMaquina;
	}

	public boolean isEsEmpresaASC() {
		return esEmpresaASC;
	}

	public void setEsEmpresaASC(boolean esEmpresaASC) {
		this.esEmpresaASC = esEmpresaASC;
	}

	public boolean isEsMinistCCEntLocal() {
		return esMinistCCEntLocal;
	}

	public void setEsMinistCCEntLocal(boolean esMinistCCEntLocal) {
		this.esMinistCCEntLocal = esMinistCCEntLocal;
	}

	public boolean isEsAutoescuela() {
		return esAutoescuela;
	}

	public void setEsAutoescuela(boolean esAutoescuela) {
		this.esAutoescuela = esAutoescuela;
	}

	public boolean isEsCompraventaVeh() {
		return esCompraventaVeh;
	}

	public void setEsCompraventaVeh(boolean esCompraventaVeh) {
		this.esCompraventaVeh = esCompraventaVeh;
	}

	public boolean isEsVivienda() {
		return esVivienda;
	}

	public void setEsVivienda(boolean esVivienda) {
		this.esVivienda = esVivienda;
	}

	public List<Autorizacion> getListaAutorizados() {
		listaAutorizados = new ArrayList<>();
		 String[] codSeleccionados = null;
			if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
				codSeleccionados = listaExpedientes.replace(" ", "").split(",");
			}
			for (String numExp : codSeleccionados) {
				TramiteTrafTranVO trafTranVO = servicioTramiteTraficoTransmision.getTramite(new BigDecimal(numExp), Boolean.FALSE);
				TramiteTrafMatrVO trafMatrVO= servicioTramiteTraficoMatriculacion.getTramiteMatriculacionVO(new BigDecimal(numExp),Boolean.FALSE, Boolean.TRUE);
				if(trafTranVO != null) {
					if("SI".equalsIgnoreCase(trafTranVO.getAcreditacionPago())) {
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.IVTM.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
					if("HERENCIA".equalsIgnoreCase(trafTranVO.getAcreditaHerenciaDonacion())) {
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.HERENCIA.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
					if("DONACION".equalsIgnoreCase(trafTranVO.getAcreditaHerenciaDonacion())) {
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.DONACION.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
					if("SI".equalsIgnoreCase(trafTranVO.getExentoCtr())){
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.EXENTO_CTR.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
					if("SI".equalsIgnoreCase(trafTranVO.getActaSubasta())) {
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.SUBASTA.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
					if("SI".equalsIgnoreCase(trafTranVO.getSentenciaJudicial())) {
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.JUDICIAL.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
				}else if(trafMatrVO!= null) {
					if(esUnaFichaA(trafMatrVO.getVehiculo().getIdTipoTarjetaItv())){
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.FICHA_A.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
					if("SI".equalsIgnoreCase(trafMatrVO.getExentoCtr())){
						Autorizacion auto = new Autorizacion();
						auto.setTipo(TipoAutorizacion.EXENTO_CTR.getNombreEnum());
						auto.setValor(obtenerValorAdicional(auto.getTipo(),trafTranVO,trafMatrVO));
						listaAutorizados.add(auto);
					}
				}
			}
			
		return listaAutorizados;
	}

	public void setListaAutorizados(List<Autorizacion> listaAutorizados) {
		this.listaAutorizados = listaAutorizados;
	}

	public String getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(String valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	public String getValorAdicional() {
		return valorAdicional;
	}

	public void setValorAdicional(String valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	public String getDatoActualizar() {
		return datoActualizar;
	}

	public void setDatoActualizar(String datoActualizar) {
		this.datoActualizar = datoActualizar;
	}

	public String getDatoNuevo() {
		return datoNuevo;
	}

	public void setDatoNuevo(String datoNuevo) {
		this.datoNuevo = datoNuevo;
	}


}