package trafico.modelo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioFabricante;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.viafirma.cliente.exception.InternalException;

import com.thoughtworks.xstream.XStream;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ColegioBean;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloPersona;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import general.utiles.BasicText;
import general.utiles.UtilesCadenaCaracteres;
import hibernate.entities.trafico.Vehiculo;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import procesos.daos.BeanPQCrearSolicitud;
import procesos.daos.BeanPQTomarITV;
import trafico.beans.DatosITV;
import trafico.beans.DescripcionesDireccionBean;
import trafico.beans.DescripcionesTraficoBean;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQAltaMatriculacion;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.beans.daos.BeanPQDetalleMatriculacion;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQMatricular;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarInterviniente;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarMatriculacion;
import trafico.beans.daos.BeanPQValidarWMI;
import trafico.beans.daos.BeanPQVehiculosGuardar;
import trafico.beans.daos.pq_tramite_trafico.BeanPQCAMBIAR_ESTADO_S_V;
import trafico.beans.jaxb.matriculacion.TipoSINO;
import trafico.beans.jaxb.matw.dgt.tipos.TipoCambioDomicilio;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoAsuntoMATG;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoColegio;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoColegio.DocumentoIdentificacion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoConductor;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoDestino;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoDireccion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoGestoria;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoNombre;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDatoTitular;
import trafico.beans.jaxb.matw.dgt.tipos.TipoDocumentoIdentificacion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoInspeccion;
import trafico.beans.jaxb.matw.dgt.tipos.TipoSN;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Arrendatario;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.ConductorHabitual;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.DatosTecnicos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.DatosTecnicos.Caracteristicas;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.DatosVehiculo;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Impuestos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Titular;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosEspecificos.Tutor;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosGenericos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.DatosGenericos.Interesados;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.Documentos;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.SolicitudRegistroEntrada;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.TipoVehiculoDGT;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.beans.utiles.IntervinienteTraficoBeanPQConversion;
import trafico.beans.utiles.MatriculacionTramiteTraficoBeanMatwPQConversion;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.UtilesConversionesMatw;
import trafico.utiles.UtilesConversionesTrafico;
import trafico.utiles.XMLMatwFactory;
import trafico.utiles.XMLPruebaCertificado;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.enumerados.ProvinciasEstacionITV;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTutela;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.GuardarMatriculacionExcepcion;
import viafirma.utilidades.UtilesViafirma;
/**********INTEGRACION MATW */

public class ModeloMatriculacion extends ModeloBasePQ {

	private static String TIPO_EITV_NIVE = "NIVE";
	private static String TIPO_EITV_ITV = "ITV";
	private static final String ERROR_AL_MATRICULAR = "Error al matricular";
	private static final String MATRICULA_INSCRITA_CORRECTAMENTE = "Matricula inscrita Correctamente";
	private static final String VALIDAR_ANAGRAMA_SI_AEAT_CAIDA="validar.anagrama.aeat";
	private static final String OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA = "SI";
	private static final String OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM = "SI";
	private static final String VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM = "validar.anagrama.aeat.selenium";

	@Autowired
	private ServicioEEFF servicioEEFF;

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	private ServicioFabricante servicioFabricante;

	@Autowired
	private ServicioTipoVia servicioTipoVia;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	MatriculacionTramiteTraficoBeanMatwPQConversion matriculacionTramiteTraficoBeanMatwPQConversion;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/*************
	 * INTEGRACIÓN DE MATW
	 * 
	 * private static final String XML_VALIDO = "CORRECTO"; private static final
	 * String XML_ENCODING_UTF8 =UTF_8; //Valor del atributo ID del nodo a
	 * firmar en la petición XML a MATEGE private static final String
	 * MATEGE_NODO_FIRMAR_ID = "D0";
	 * 
	 * private static final String VERSION_REGISTRO_ENTRADA = "3.0"; private
	 * static final String VERSION = "1.1"; private static final String
	 * ORGANISMO = "DGT"; /*JF cambio en el código de la etiqueta Asunto debido
	 * a cambios en el esquema
	 */
	private static final String CODIGO_ASUNTO = "MATW";
	private static final String XML_VALIDO = "CORRECTO";
	private static final String DESCRIPCION_ASUNTO = "Solicitud de Matriculación Online para Gestores con Datos Técnicos";
	private static final String CODIGO_DESTINO = "101001";
	private static final String DESCRIPCION_DESTINO = "DGT - Vehículos";
	private static final String PAIS = "ESP";
	private static int _18 = 18;
	private static int _45 = 45;
	private static final String SIN_CODIG = "SINCODIG";

	private static final String XML = ".xml";
	private static final String _0 = "0";
	private static final String SOLICITUD_CREADA_CORRECTAMENTE = "Solicitud creada correctamente";
	private static final String TRAMITE_FIRMADO_Y_PENDIENTE_DE_ENVÍO = "Tramite firmado y pendiente de envío.";
	private static final String ERROR_AL_FIRMAR_EL_COLEGIO2 = "Error al firmar el Colegio";
	private static final String ERROR_AL_OBTENER_BYTES_UTF_8 = "Error al obtener bytes UTF-8";
	private static final String HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA = "Ha ocurrido un error recuperando el xml firmado a traves del id de firma";
	private static final String ERROR_AL_FIRMAR_EL_COLEGIO = ERROR_AL_FIRMAR_EL_COLEGIO2;
	private static final String ERROR = "ERROR";
	private static final String VERSION_REGISTRO_ENTRADA = "3.0";
	private static final String VERSION = "1.1";
	private static final String ORGANISMO = "DGT";
	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloMatriculacion.class);

	private ModeloSolicitud modeloSolicitud;
	private ModeloInterviniente modeloInterviniente;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloColegiado modeloColegiado;
	private ModeloTrafico modeloTrafico;
	private ModeloMatriculacionSega modeloMatriculacionSega;

	private void verPCodeSqlErrPInfo(RespuestaGenerica respuestaGenerica) {
		log.debug(ConstantesPQ.LOG_P_CODE + (BigDecimal)respuestaGenerica.getParametro(ConstantesPQ.P_CODE));
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)respuestaGenerica.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)respuestaGenerica.getParametro(ConstantesPQ.P_INFORMACION));
	}

	public HashMap<String, Object> tomarITV(BeanPQTomarITV beanPQTomarSolicitud) {
		ResultBean resultBean = new ResultBean();
		DatosITV datosITV = new DatosITV();

		RespuestaGenerica respuestaSolicitud = ejecutarProc(
				beanPQTomarSolicitud, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_CODITV, "TOMAR", BeanPQGeneral.class);

		log.info(ConstantesPQ.LOG_P_CODE
				+ respuestaSolicitud.getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION
				+ respuestaSolicitud.getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM
				+ respuestaSolicitud.getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaSolicitud
				.getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje("No existe el código ITV");
			resultBean.setError(true);
			resultBean.setMensaje(resultBean.getMensaje() + " ");
		} else {
			resultBean.setMensaje("código ITV Correcto");
			resultBean.setError(false);
		}

		datosITV.setBastidor((String) respuestaSolicitud
				.getParametro("P_BASTIDOR"));
		datosITV.setCarburante((String) respuestaSolicitud
				.getParametro("P_CARBURANTE"));
		datosITV.setCilindrada((String) respuestaSolicitud
				.getParametro("P_CILINDRADA"));
		datosITV.setCo2((String) respuestaSolicitud.getParametro("P_CO2"));
		datosITV.setCodigoItv((String) respuestaSolicitud
				.getParametro("P_CODIGO_ITV"));
		datosITV.setCodigoMarca((BigDecimal) respuestaSolicitud
				.getParametro("P_CODIGO_MARCA"));
		datosITV.setMarca((String) respuestaSolicitud.getParametro("P_MARCA"));
		datosITV.setMma((String) respuestaSolicitud.getParametro("P_MMA"));
		datosITV.setModelo((String) respuestaSolicitud.getParametro("P_MODELO"));
		datosITV.setPlazas((String) respuestaSolicitud.getParametro("P_PLAZAS"));
		datosITV.setPotenciaFiscal((BigDecimal) respuestaSolicitud
				.getParametro("P_POTENCIA_FISCAL"));
		datosITV.setPotenciaReal((BigDecimal) respuestaSolicitud
				.getParametro("P_POTENCIA_REAL"));
		datosITV.setTara((String) respuestaSolicitud.getParametro("P_TARA"));
		datosITV.setTipoVehiculoIndustria(((String) respuestaSolicitud
				.getParametro("P_TIPO_VEHICULO_INDUSTRIA")));
		datosITV.setTipoVehiculoTrafico((String) respuestaSolicitud
				.getParametro("P_TIPO_VEHICULO_TRAFICO"));
		datosITV.setNive((String) respuestaSolicitud.getParametro("P_NIVE"));

		// Nuevos campos Mate 2.5
		datosITV.setFabricante((String) respuestaSolicitud
				.getParametro("P_FABRICANTE"));
		datosITV.setTipo((String) respuestaSolicitud.getParametro("P_TIPO"));
		datosITV.setVariante((String) respuestaSolicitud
				.getParametro("P_VARIANTE"));
		datosITV.setVersion((String) respuestaSolicitud
				.getParametro("P_VERSION_35"));
		datosITV.setCatHomologacion((String) respuestaSolicitud
				.getParametro("P_CAT_HOMOLOGACION"));
		datosITV.setConsumo((BigDecimal) respuestaSolicitud
				.getParametro("P_CONSUMO"));
		datosITV.setMasaMom((BigDecimal) respuestaSolicitud
				.getParametro("P_MASA_MOM"));
		datosITV.setProcedencia((String) respuestaSolicitud
				.getParametro("P_PROCEDENCIA"));
		datosITV.setDistanciaEjes((BigDecimal) respuestaSolicitud
				.getParametro("P_DISTANCIA_EJES"));
		datosITV.setViaAnterior((BigDecimal) respuestaSolicitud
				.getParametro("P_VIA_ANTERIOR"));
		datosITV.setViaPosterior((BigDecimal) respuestaSolicitud
				.getParametro("P_VIA_POSTERIOR"));
		datosITV.setTipoAlimentacion((String) respuestaSolicitud
				.getParametro("P_TIPO_ALIMENTACION"));
		datosITV.setContraseniaHomologacion((String) respuestaSolicitud
				.getParametro("P_CONTRASENIA_HOMOLOGACION"));
		datosITV.setNivelEmision((String) respuestaSolicitud
				.getParametro("P_NIVEL_EMISION"));
		datosITV.setEcoInnovacion((String) respuestaSolicitud
				.getParametro("P_ECO_INNOVACION"));
		datosITV.setReduccionEco((BigDecimal) respuestaSolicitud
				.getParametro("P_REDUCCION_ECO"));
		datosITV.setCodigoEco((String) respuestaSolicitud
				.getParametro("P_CODIGO_ECO"));
		datosITV.setMmc((String) respuestaSolicitud.getParametro("P_MMC"));
		datosITV.setCarroceria((String) respuestaSolicitud
				.getParametro("P_CARROCERIA"));
		datosITV.setNumPlazasPie((String) respuestaSolicitud
				.getParametro("P_PLAZAS_PIE"));
		datosITV.setRelPotenciaPeso((String) respuestaSolicitud
				.getParametro("P_POTENCIA_PESO"));
		// Tomar fechahora

		HashMap<String, Object> resultadoCrearSolicitud = new HashMap<String, Object>();

		resultadoCrearSolicitud.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultadoCrearSolicitud.put(ConstantesPQ.BEANPANTALLA, datosITV);
		resultadoCrearSolicitud.put(ConstantesPQ.P_CODE,
				respuestaSolicitud.getParametro(ConstantesPQ.P_CODE));

		return resultadoCrearSolicitud;
	}

	private ArrayList<Object> listaAuxiliar;
	private ModeloVehiculo modeloVehiculo;

	public ArrayList<Object> getListaAuxiliar() {
		return listaAuxiliar;
	}

	public void setListaAuxiliar(ArrayList<Object> listaAuxiliar) {
		this.listaAuxiliar = listaAuxiliar;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	private boolean comprobarCreditos(BigDecimal idContrato) {
		HashMap<String, Object> resultadoMetodo = getModeloCreditosTrafico().validarCreditosPorNumColegiado(
				idContrato.toString(), new BigDecimal(1), TipoTramiteTrafico.Matriculacion.getValorEnum());
		ResultBean resultBean = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
		return !resultBean.getError();
	}

	/**
	 * 
	 * @param numEXPEDIENTE
	 * @param numColegiado
	 * @param idContrato
	 * @return DETALLE DE UN TRAMITE DE MATRICULACION MATE Y MATW
	 */
	public HashMap<String, Object> obtenerDetalle(BigDecimal numEXPEDIENTE,
			String numColegiado, BigDecimal idContrato) {

		HashMap<String, Object> resultadoMetodo = new HashMap<String, Object>();
		TramiteTraficoMatriculacionBean beanDetalleMatriculacion = new TramiteTraficoMatriculacionBean(
				true);

		BeanPQDetalleMatriculacion beanConsultaMatriculacion = new BeanPQDetalleMatriculacion();
		VehiculoBean vehiculo = new VehiculoBean(true);
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";

		beanConsultaMatriculacion.setP_NUM_EXPEDIENTE(numEXPEDIENTE);

		// Ejecución genérica del acceso a la base de datos (CAMBIAR PARA QUE TENGA LOS CAMBIOS DE MATW)
		RespuestaGenerica resultadoBD = ejecutarProc(beanConsultaMatriculacion,
			valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,"DETALLE_MATRICULACION", BeanPQGeneral.class);

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal) resultadoBD
				.getParametro(ConstantesPQ.P_CODE);
		// Se controla el error y se actualiza para su devolución
		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)) {
			error = true;
			mensaje += ("  - Se ha producido un error al obtener el mensaje. "
					+ (String) resultadoBD.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
		}
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM
				+ (String) resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA
				+ resultadoBD.getParametro("CUENTA"));

		// Convertimos y rellenamos los datos complejos del bean.
		beanDetalleMatriculacion = matriculacionTramiteTraficoBeanMatwPQConversion.convertirPQToBean(resultadoBD);

		// Vehículo (UNIFICAR ESTA LLAMADA MATE Y MATW cargaVehiculoPorId Y cargaVehiculoPorIdMatw)
		// LLAMARA A DETALLE DE MATW con todos los datos del vehiculo.
		vehiculo = getModeloVehiculo().cargaVehiculoPorIdMatw(beanDetalleMatriculacion.getTramiteTraficoBean().getVehiculo()
						.getIdVehiculo());
		beanDetalleMatriculacion.getTramiteTraficoBean().setVehiculo(vehiculo);

		if (null != vehiculo.getVehiculoPreverBean().getIdVehiculoPrever()) {
			BigDecimal idVehiculoPrever = new BigDecimal(vehiculo
					.getVehiculoPreverBean().getIdVehiculoPrever());
			beanDetalleMatriculacion.setVehiculoPreverBean(getModeloVehiculo()
					.cargaVehiculoPorId(idVehiculoPrever));
		}

		// SOLO PARA MATE
		if (beanDetalleMatriculacion.getTramiteTraficoBean().getVehiculo()
				.getNive() != null
				&& !("".equals(beanDetalleMatriculacion.getTramiteTraficoBean()
						.getVehiculo().getNive()))) {
			beanDetalleMatriculacion.setValorTipoMatriculacion(TIPO_EITV_NIVE);
		} else {
			beanDetalleMatriculacion.setValorTipoMatriculacion(TIPO_EITV_ITV);
		}

		// Intervinientes
		List<IntervinienteTrafico> listaIntervinienteTrafico = getModeloInterviniente()
				.obtenerDetalleIntervinientes(numEXPEDIENTE, numColegiado,
						idContrato);

		for (IntervinienteTrafico intervinienteTrafico : listaIntervinienteTrafico) {
			if (intervinienteTrafico.getTipoInterviniente().getValorEnum()
					.equals(TipoInterviniente.Titular.getValorEnum())) {
				beanDetalleMatriculacion.setTitularBean(intervinienteTrafico);
				beanDetalleMatriculacion
						.getTitularBean()
						.getPersona()
						.setFechaNacimientoBean(
								utilesFecha
										.getFechaFracionada(intervinienteTrafico
												.getPersona()
												.getFechaNacimiento()));
				beanDetalleMatriculacion
						.getTitularBean()
						.getPersona()
						.setFechaNacimientoBean(
								utilesFecha
										.getFechaFracionada(intervinienteTrafico
												.getPersona()
												.getFechaNacimiento()));
			} else if (intervinienteTrafico
					.getTipoInterviniente()
					.getValorEnum()
					.equals(TipoInterviniente.RepresentanteTitular
							.getValorEnum())) {
				beanDetalleMatriculacion
						.setRepresentanteTitularBean(intervinienteTrafico);
			} else if (intervinienteTrafico.getTipoInterviniente()
					.getValorEnum()
					.equals(TipoInterviniente.Arrendatario.getValorEnum())) {
				beanDetalleMatriculacion
						.setArrendatarioBean(intervinienteTrafico);
				beanDetalleMatriculacion
						.getArrendatarioBean()
						.getPersona()
						.setFechaNacimientoBean(
								utilesFecha
										.getFechaFracionada(intervinienteTrafico
												.getPersona()
												.getFechaNacimiento()));

				beanDetalleMatriculacion
						.getArrendatarioBean()
						.getPersona()
						.setFechaNacimientoBean(
								utilesFecha
										.getFechaFracionada(intervinienteTrafico
												.getPersona()
												.getFechaNacimiento()));
			} else if (intervinienteTrafico.getTipoInterviniente()
					.getValorEnum()
					.equals(TipoInterviniente.ConductorHabitual.getValorEnum())) {
				beanDetalleMatriculacion
						.setConductorHabitualBean(intervinienteTrafico);

			// SOLO PARA MATW 
			} else if (intervinienteTrafico
					.getTipoInterviniente()
					.getValorEnum()
					.equals(TipoInterviniente.RepresentanteArrendatario
							.getValorEnum())) {
				beanDetalleMatriculacion
						.setRepresentanteArrendatarioBean(intervinienteTrafico);
			}
		}

		// jmbc EEFF
		try {
			beanDetalleMatriculacion
					.setEeffLiberacionDTO(servicioEEFF
							.recuperarDatosLiberacion(beanDetalleMatriculacion
									.getTramiteTraficoBean().getNumExpediente()));
		} catch (Exception e) {
			error = true;
			mensaje += (" - Se ha producido un error al convertir un objeto EntidadFinanciera a EntidadFinancieraDTO. ");
			log.error("Se ha producido un error al convertir un objeto EntidadFinanciera a EntidadFinancieraDTO al obtener el detalle de un trámite MatE." , e, numEXPEDIENTE.toString());
		}

		resultado.setError(error);
		resultado.setMensaje(mensaje);

		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo
				.put(ConstantesPQ.BEANPANTALLA, beanDetalleMatriculacion);
		return resultadoMetodo;
	}

	public HashMap<String, Object> obtenerDetalleConDescripciones(
			BigDecimal numEXPEDIENTE, String numColegiado, BigDecimal idContrato) {

		HashMap<String, Object> resultado = new HashMap<String, Object>();
		HashMap<String, Object> resultadoMetodo = new HashMap<String, Object>();
		ResultBean resultBean = new ResultBean();
		ResultBean resultBeanMetodo = new ResultBean();
		TramiteTraficoMatriculacionBean beanDetalleMatriculacion = new TramiteTraficoMatriculacionBean(
				true);

		resultadoMetodo = obtenerDetalle(numEXPEDIENTE, numColegiado,
				idContrato);

		resultBeanMetodo = (ResultBean) resultadoMetodo
				.get(ConstantesPQ.RESULTBEAN);

		if (resultBeanMetodo.getError()) {
			resultBean.setError(true);
			resultBean.setMensaje(resultBeanMetodo.getMensaje());
		} else {
			beanDetalleMatriculacion = (TramiteTraficoMatriculacionBean) resultadoMetodo
					.get(ConstantesPQ.BEANPANTALLA);
			// Llamada al método de obtener descripciones
			// Se obtienen las descripciones tanto para MATE 2.5 como MATW.
			HashMap<String, Object> resultadoDescripciones = getModeloTrafico()
					.obtenerDescripcionesPorNumExpediente(numEXPEDIENTE);
			ResultBean resultBeanDescripciones = (ResultBean) resultadoDescripciones
					.get(ConstantesPQ.RESULTBEAN);

			if (resultBeanDescripciones.getError()) {
				resultBean.setError(true);
				resultBean.setMensaje(resultBeanDescripciones.getMensaje());
			} else {
				DescripcionesTraficoBean descripciones = (DescripcionesTraficoBean) resultadoDescripciones
						.get(ConstantesPQ.BEANPANTALLA);

				// Rellenamos todos los datos de descripción que necesitamos.
				// Primero la dirección de los intervinientes.Titular
				if (null != beanDetalleMatriculacion.getTitularBean()
						&& null != beanDetalleMatriculacion.getTitularBean()
								.getPersona()
						&& null != beanDetalleMatriculacion.getTitularBean()
								.getPersona().getDireccion()
						&& null != beanDetalleMatriculacion.getTitularBean()
								.getPersona().getDireccion().getIdDireccion()) {

					DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
					resultadoMetodo = getModeloTrafico()
							.obtenerDescripcionesPorIdDireccion(
									new BigDecimal(beanDetalleMatriculacion
											.getTitularBean().getPersona()
											.getDireccion().getIdDireccion()));

					direccion = (DescripcionesDireccionBean) resultadoMetodo
							.get(ConstantesPQ.BEANPANTALLA);
					beanDetalleMatriculacion.getTitularBean().getPersona()
							.getDireccion().getTipoVia()
							.setNombre(direccion.getTipViaNombre());
					beanDetalleMatriculacion.getTitularBean().getPersona()
							.getDireccion().getMunicipio().getProvincia()
							.setNombre(direccion.getProvNombre());
					beanDetalleMatriculacion.getTitularBean().getPersona()
							.getDireccion().getMunicipio()
							.setNombre(direccion.getMuniNombre());
				}
				// Representante del titular
				if (null != beanDetalleMatriculacion
						.getRepresentanteTitularBean()
						&& null != beanDetalleMatriculacion
								.getRepresentanteTitularBean().getPersona()
						&& null != beanDetalleMatriculacion
								.getRepresentanteTitularBean().getPersona()
								.getDireccion()
						&& null != beanDetalleMatriculacion
								.getRepresentanteTitularBean().getPersona()
								.getDireccion().getIdDireccion()) {

					DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
					resultadoMetodo = getModeloTrafico()
							.obtenerDescripcionesPorIdDireccion(
									new BigDecimal(beanDetalleMatriculacion
											.getRepresentanteTitularBean()
											.getPersona().getDireccion()
											.getIdDireccion()));

					direccion = (DescripcionesDireccionBean) resultadoMetodo
							.get(ConstantesPQ.BEANPANTALLA);
					beanDetalleMatriculacion.getRepresentanteTitularBean()
							.getPersona().getDireccion().getTipoVia()
							.setNombre(direccion.getTipViaNombre());
					beanDetalleMatriculacion.getRepresentanteTitularBean()
							.getPersona().getDireccion().getMunicipio()
							.getProvincia()
							.setNombre(direccion.getProvNombre());
					beanDetalleMatriculacion.getRepresentanteTitularBean()
							.getPersona().getDireccion().getMunicipio()
							.setNombre(direccion.getMuniNombre());
				}
				// Arrendatario
				if (null != beanDetalleMatriculacion.getArrendatarioBean()
						&& null != beanDetalleMatriculacion
								.getArrendatarioBean().getPersona()
						&& null != beanDetalleMatriculacion
								.getArrendatarioBean().getPersona()
								.getDireccion()
						&& null != beanDetalleMatriculacion
								.getArrendatarioBean().getPersona()
								.getDireccion().getIdDireccion()) {

					DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
					resultadoMetodo = getModeloTrafico()
							.obtenerDescripcionesPorIdDireccion(
									new BigDecimal(beanDetalleMatriculacion
											.getArrendatarioBean().getPersona()
											.getDireccion().getIdDireccion()));

					direccion = (DescripcionesDireccionBean) resultadoMetodo
							.get(ConstantesPQ.BEANPANTALLA);
					beanDetalleMatriculacion.getArrendatarioBean().getPersona()
							.getDireccion().getTipoVia()
							.setNombre(direccion.getTipViaNombre());
					beanDetalleMatriculacion.getArrendatarioBean().getPersona()
							.getDireccion().getMunicipio().getProvincia()
							.setNombre(direccion.getProvNombre());
					beanDetalleMatriculacion.getArrendatarioBean().getPersona()
							.getDireccion().getMunicipio()
							.setNombre(direccion.getMuniNombre());
				}
				// Conductor Habitual
				if (null != beanDetalleMatriculacion.getConductorHabitualBean()
						&& null != beanDetalleMatriculacion
								.getConductorHabitualBean().getPersona()
						&& null != beanDetalleMatriculacion
								.getConductorHabitualBean().getPersona()
								.getDireccion()
						&& null != beanDetalleMatriculacion
								.getConductorHabitualBean().getPersona()
								.getDireccion().getIdDireccion()) {

					DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
					resultadoMetodo = getModeloTrafico()
							.obtenerDescripcionesPorIdDireccion(
									new BigDecimal(beanDetalleMatriculacion
											.getConductorHabitualBean()
											.getPersona().getDireccion()
											.getIdDireccion()));

					direccion = (DescripcionesDireccionBean) resultadoMetodo
							.get(ConstantesPQ.BEANPANTALLA);
					beanDetalleMatriculacion.getConductorHabitualBean()
							.getPersona().getDireccion().getTipoVia()
							.setNombre(direccion.getTipViaNombre());
					beanDetalleMatriculacion.getConductorHabitualBean()
							.getPersona().getDireccion().getMunicipio()
							.getProvincia()
							.setNombre(direccion.getProvNombre());
					beanDetalleMatriculacion.getConductorHabitualBean()
							.getPersona().getDireccion().getMunicipio()
							.setNombre(direccion.getMuniNombre());
				}

				// Ahora cargamos todos los datos del TramiteTráficoBean
				if (null != beanDetalleMatriculacion.getTramiteTraficoBean()) {

					// Gestoría CIF y RAZÓN SOCIAL
					beanDetalleMatriculacion.getTramiteTraficoBean().setCif(
							descripciones.getCif());
					beanDetalleMatriculacion.getTramiteTraficoBean()
							.setRazonSocial(descripciones.getRazonSocial());

					// Jefatura
					if (null != beanDetalleMatriculacion
							.getTramiteTraficoBean().getJefaturaTrafico()) {

						beanDetalleMatriculacion
								.getTramiteTraficoBean()
								.getJefaturaTrafico()
								.setDescripcion(
										descripciones
												.getDescripcionJefaturaSucursal());
						beanDetalleMatriculacion
								.getTramiteTraficoBean()
								.getJefaturaTrafico()
								.getProvincia()
								.setNombre(
										descripciones
												.getDescripcionJefaturaProvincial());
					}
					// Vehículo
					if (null != beanDetalleMatriculacion
							.getTramiteTraficoBean().getVehiculo()) {

						beanDetalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getMarcaBean()
								.setMarca(descripciones.getMarca());
						beanDetalleMatriculacion
								.getTramiteTraficoBean()
								.getVehiculo()
								.getMarcaBean()
								.setMarcaSinEditar(
										descripciones.getMarcaSinEditar());
						beanDetalleMatriculacion.getTramiteTraficoBean()
								.getVehiculo().getMarcaBaseBean()
								.setMarca(descripciones.getMarcaBase());
						beanDetalleMatriculacion
								.getTramiteTraficoBean()
								.getVehiculo()
								.getMarcaBaseBean()
								.setMarcaSinEditar(
										descripciones.getMarcaSinEditarBase());
						beanDetalleMatriculacion
								.getTramiteTraficoBean()
								.getVehiculo()
								.getTipoVehiculoBean()
								.setDescripcion(
										descripciones
												.getDescripcionTipoVehiculo());
						beanDetalleMatriculacion
								.getTramiteTraficoBean()
								.getVehiculo()
								.getServicioTraficoBean()
								.setDescripcion(
										descripciones.getDescripcionServicio());

						// Dirección del Vehículo
						if (null != beanDetalleMatriculacion
								.getTramiteTraficoBean().getVehiculo()
								.getDireccionBean()
								&& null != beanDetalleMatriculacion
										.getTramiteTraficoBean().getVehiculo()
										.getDireccionBean().getIdDireccion()) {

							DescripcionesDireccionBean direccion = new DescripcionesDireccionBean();
							resultadoMetodo = getModeloTrafico()
									.obtenerDescripcionesPorIdDireccion(
											new BigDecimal(
													beanDetalleMatriculacion
															.getTramiteTraficoBean()
															.getVehiculo()
															.getDireccionBean()
															.getIdDireccion()));

							direccion = (DescripcionesDireccionBean) resultadoMetodo
									.get(ConstantesPQ.BEANPANTALLA);
							beanDetalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getDireccionBean()
									.getTipoVia()
									.setNombre(direccion.getTipViaNombre());
							beanDetalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getDireccionBean()
									.getMunicipio().getProvincia()
									.setNombre(direccion.getProvNombre());
							beanDetalleMatriculacion.getTramiteTraficoBean()
									.getVehiculo().getDireccionBean()
									.getMunicipio()
									.setNombre(direccion.getMuniNombre());
						}
					}
				}

				if (null != beanDetalleMatriculacion
						&& null != descripciones
						&& (null != descripciones.getOtrosSinCodig() || ""
								.equals(descripciones.getOtrosSinCodig()))) {
					beanDetalleMatriculacion.setOtrosSinCodig(descripciones
							.getOtrosSinCodig());
				}
				// Se agrega el tipoTramite para validaciones futuras
				beanDetalleMatriculacion
						.getTramiteTraficoBean()
						.setTipoTramite(
								TipoTramiteTrafico.Matriculacion.getValorEnum());
			}

		}

		resultado.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultado.put(ConstantesPQ.BEANPANTALLA, beanDetalleMatriculacion);
		return resultado;
	}

	public HashMap <String,Object> guardarAltaTramiteMatriculacionMatw(BeanPQAltaMatriculacion traficoMatriculacionBean) throws OegamExcepcion {
		log.info("guardarAltaTramiteMatriculacion");
		HashMap<String,Object> resultadoAlta = new HashMap<String,Object>();
		ResultBean resultado = new ResultBean();
		resultado.setError(false);
		BigDecimal numExpediente = null;
		boolean vehiculoNuevo = false;

		BeanPQTramiteTraficoGuardarMatriculacion beanPQTramiteTraficoGuardarMatriculacion = traficoMatriculacionBean.getBeanGuardarMatriculacion();

		BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentante = traficoMatriculacionBean.getBeanGuardarRepresentante();
		BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarTitular = traficoMatriculacionBean.getBeanGuardarTitular();
		BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarArrendatario = traficoMatriculacionBean.getBeanGuardarArrendatario();
		BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarConductorHabitual =traficoMatriculacionBean.getBeanGuardarConductorHabitual();
		// RRR DESAPARECE EL REPRESENTANTE DEL CONDUCTOR HABITUAL RESPECTO DE LA VERSIÓN INICIAL DE MATW
		// BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentanteConductorHabitual = traficoMatriculacionBean.getBeanGuardarRepresentanteConductorHabitual();
		BeanPQTramiteTraficoGuardarInterviniente beanPQTramiteTraficoGuardarRepresentanteArrendatario = traficoMatriculacionBean.getBeanGuardarRepresentanteArrendatario();

		BeanPQVehiculosGuardar beanPQVehiculosGuardar = traficoMatriculacionBean.getBeanGuardarVehiculo();
		beanPQVehiculosGuardar.setP_TIPO_TRAMITE(TipoTramiteTrafico.Matriculacion.getValorEnum());

		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteArrendatario=null;
		RespuestaGenerica resultadoTramiteTraficoGuardarMatriculacion =null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteTitular = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentante=null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteConductorHabitual=null;
		// RRR DESAPARECE EL REPRESENTANTE DEL CONDUCTOR HABITUAL RESPECTO DE LA VERSIÓN INICIAL DE MATW
		//RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteConductorHabitual = null;
		RespuestaGenerica resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario = null;
		RespuestaGenerica resultadoVehiculo=null;

		BigDecimal pCodeVehiculo = null;
		String sqlErrmVehiculo = null;
		String pInformacionVehiculo=null;

		BigDecimal pCodeTramite= null;
		String sqlErrmTramite= null;
		String pInformacionTramite= null;

		String sqlErrmTitular = null;
		BigDecimal pCodeTitular= null;
		String pInformacionTitular = null;

		BigDecimal pCodeRepresentante= null;
		String sqlErrmRepresentante= null;
		String pInformacionRepresentante= null;

		BigDecimal pCodeArrendatario= null;
		String sqlErrmArrendatario= null;
		String pInformacionArrendatario= null;

		BigDecimal pCodeConductorHabitual= null;
		String sqlErrmConductorHabitual= null;
		String pInformacionConductorHabitual= null;

		// RRR DESAPARECE EL REPRESENTANTE DEL CONDUCTOR HABITUAL RESPECTO DE LA VERSIÓN INICIAL DE MATW

		BigDecimal pCodeRepresentanteArrendatario = null;
		String sqlErrmRepresentanteArrendatario= null;
		String pInformacionRepresentanteArrendatario= null;

		// --------------------------VEHICULO-----------------------------------
		// beanPQVehiculosGuardar = VehiculoBeanPQConversion.beanVehiculoPreverConvertirBeanPQ(traficoMatriculacionBean.getTramiteTraficoBean().getVehiculo(),
		// traficoMatriculacionBean.getVehiculoPreverBean(), traficoMatriculacionBean.getTramiteTraficoBean());

		// Mantis 16626. David Sierra: Mensaje de error en el caso de que no se rellene el Bastidor o el Codigo ITV
		if((beanPQVehiculosGuardar.getP_BASTIDOR()==null || beanPQVehiculosGuardar.getP_BASTIDOR().isEmpty())
			&& (beanPQVehiculosGuardar.getP_CODITV()==null || beanPQVehiculosGuardar.getP_CODITV().isEmpty())) {
			resultado.setMensaje("Los datos del Vehículo no se guardarán si no tienen Bastidor o Código ITV");
		}

		if ((beanPQVehiculosGuardar.getP_BASTIDOR()!=null
				|| beanPQVehiculosGuardar.getP_CODITV()!=null)
				|| beanPQVehiculosGuardar.getP_MATRICULA()!=null) {

			if (beanPQVehiculosGuardar.getP_MATRICULA() == null && beanPQVehiculosGuardar.getP_ID_VEHICULO() == null) {
				vehiculoNuevo = true;
			}

			resultadoVehiculo = ejecutarProc(beanPQVehiculosGuardar,valoresSchemas.getSchema(),ValoresCatalog.PQ_VEHICULOS,"GUARDAR_MATW",BeanPQGeneral.class);

			//Recuperamos información de respuesta
			pCodeVehiculo = (BigDecimal)resultadoVehiculo.getParametro(ConstantesPQ.P_CODE);
			sqlErrmVehiculo = (String)resultadoVehiculo.getParametro(ConstantesPQ.P_SQLERRM);
			pInformacionVehiculo = (String)resultadoVehiculo.getParametro(ConstantesPQ.P_INFORMACION);
			verPCodeSqlErrPInfo(resultadoVehiculo);

			if ((!ConstantesPQ.pCodeOk.equals(pCodeVehiculo))) {
				// ------
				if (sqlErrmVehiculo == null)
					resultado.setMensaje("Error al guardar el vehículo dentro del alta de matriculación.");
				resultado.setError(true);
			}
		} else {
			resultadoVehiculo = resultadoFaltanDatosVehiculo();
		}

		// --------------------------TRAMITE---------------------------------
		//Seteamos el P_ID_VEHICULO
		if (resultadoVehiculo!=null && resultadoVehiculo.getParametro("P_ID_VEHICULO") != null) {
			beanPQTramiteTraficoGuardarMatriculacion.setP_ID_VEHICULO((BigDecimal)resultadoVehiculo.getParametro("P_ID_VEHICULO"));
		} else {
			beanPQTramiteTraficoGuardarMatriculacion.setP_ID_VEHICULO(beanPQVehiculosGuardar.getP_ID_VEHICULO());
		}

		// beanPQTramiteTraficoGuardarMatriculacion = MatriculacionTramiteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean);
		//Ejecutamos el procedimiento de guardar el trámite
		resultadoTramiteTraficoGuardarMatriculacion = ejecutarProc(beanPQTramiteTraficoGuardarMatriculacion,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_MATRICULACION",BeanPQGeneral.class);

		//Recuperamos información de respuesta
		pCodeTramite = (BigDecimal)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_CODE);
		sqlErrmTramite = (String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_SQLERRM);
		pInformacionTramite = (String)resultadoTramiteTraficoGuardarMatriculacion.getParametro(ConstantesPQ.P_INFORMACION);
		verPCodeSqlErrPInfo(resultadoTramiteTraficoGuardarMatriculacion);

		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)) {
			resultado.setError(true);
			//resultado.setMensaje("Error al guardar el trámite: " + sqlErrmTramite);
			//resultado.setMensaje("No se han guardado los datos de los intervinientes.");
		}

		//Si el trámite se ha guardado bien, continuamos guardando los intervinientes
		if ((ConstantesPQ.pCodeOk.equals(pCodeTramite))) {
			numExpediente = (BigDecimal)resultadoTramiteTraficoGuardarMatriculacion.getParametro("P_NUM_EXPEDIENTE");

			// errorIntervinientes = evaluaPCode(resultado, pCodeRepresentante, pSqlErrmRepresentante, pInformacionTitular);
			//------------------------ARRENDATARIO----------------------------
			//	beanPQTramiteTraficoGuardarArrendatario = intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getArrendatarioBean());
			beanPQTramiteTraficoGuardarArrendatario.setP_NUM_EXPEDIENTE(numExpediente);
			//	beanPQTramiteTraficoGuardarArrendatario.setP_TIPO_INTERVINIENTE(TipoInterviniente.Arrendatario.getValorEnum());
			//Ejecutamos el procedimiento de guardar el Arrendatario

			if ((beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING()!=null && "SI".equals(beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING()))
					&& ((beanPQTramiteTraficoGuardarArrendatario.getP_NIF()!=null && !beanPQTramiteTraficoGuardarArrendatario.getP_NIF().equals(""))
							|| (beanPQTramiteTraficoGuardarArrendatario.getP_TIPO_INTERVINIENTE()!=null && !beanPQTramiteTraficoGuardarArrendatario.getP_TIPO_INTERVINIENTE().equals(""))))
			{
				resultadoTramiteTraficoGuardarIntervinienteArrendatario = ejecutarProc(beanPQTramiteTraficoGuardarArrendatario, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "GUARDAR_INTERVINIENTE", BeanPQGeneral.class);

				pCodeArrendatario= (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_CODE);
				sqlErrmArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM);
				pInformacionArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_INFORMACION);
				verPCodeSqlErrPInfo(resultadoTramiteTraficoGuardarIntervinienteArrendatario);

				if ((ConstantesPQ.pCodeOkAlt.equals(pCodeArrendatario))){
					if ((beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING()!=null && "SI".equals(beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING()))
							&& (beanPQTramiteTraficoGuardarArrendatario.getP_APELLIDO1_RAZON_SOCIAL()!=null && !"".equals(beanPQTramiteTraficoGuardarArrendatario.getP_APELLIDO1_RAZON_SOCIAL())
							|| (beanPQTramiteTraficoGuardarArrendatario.getP_NOMBRE()!=null && !"".equals(beanPQTramiteTraficoGuardarArrendatario.getP_NOMBRE())))) {
						resultadoTramiteTraficoGuardarIntervinienteArrendatario = resultadoFaltaDNIInterviniente();
						pCodeArrendatario= (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_CODE);
						sqlErrmArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro(ConstantesPQ.P_SQLERRM);
					} else {
						resultadoTramiteTraficoGuardarIntervinienteArrendatario = null;
						pCodeArrendatario = null;
						sqlErrmArrendatario = null;
						pInformacionArrendatario= null;
					}
				}
				// errorIntervinientes = evaluaPCode(resultado, pCodeArrendatario, sqlErrmArrendatario,pInformacionTitular);
			}

			//------------------------- REPRESENTANTE DEL ARRENDATARIO ------------------------

			beanPQTramiteTraficoGuardarRepresentanteArrendatario.setP_NUM_EXPEDIENTE(numExpediente);
			beanPQTramiteTraficoGuardarRepresentanteArrendatario.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteArrendatario.getValorEnum());

			if((beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING()!=null && "SI".equals(beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING()))
					&& ((beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NIF()!=null && !beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NIF().equals(""))
					|| (beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_TIPO_INTERVINIENTE()!=null && !beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_TIPO_INTERVINIENTE().equals(""))))
			{
				resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario = ejecutarProc(beanPQTramiteTraficoGuardarRepresentanteArrendatario, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "GUARDAR_INTERVINIENTE", BeanPQGeneral.class);

				pCodeRepresentanteArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_CODE);
				sqlErrmRepresentanteArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_SQLERRM);
				pInformacionRepresentanteArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_INFORMACION);
				verPCodeSqlErrPInfo(resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario);
				if ((ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentanteArrendatario)))	{
					if ((beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_APELLIDO1_RAZON_SOCIAL()!=null && !"".equals(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_APELLIDO1_RAZON_SOCIAL())
							|| (beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NOMBRE()!=null && !"".equals(beanPQTramiteTraficoGuardarRepresentanteArrendatario.getP_NOMBRE())))) {
						resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario = resultadoFaltaDNIInterviniente();
						pCodeRepresentanteArrendatario = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_CODE);
						sqlErrmRepresentanteArrendatario = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro(ConstantesPQ.P_SQLERRM);
					} else {
						resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario = null;
						pCodeRepresentanteArrendatario = null;
						sqlErrmRepresentanteArrendatario = null;
						pInformacionRepresentanteArrendatario= null;
					}
				}
			}

			//-------------------------CONDUCTOR HABITUAL------------------------
			//	beanPQTramiteTraficoGuardarConductorHabitual = intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getConductorHabitualBean());
			beanPQTramiteTraficoGuardarConductorHabitual.setP_NUM_EXPEDIENTE(numExpediente);
			beanPQTramiteTraficoGuardarConductorHabitual.setP_TIPO_INTERVINIENTE(TipoInterviniente.ConductorHabitual.getValorEnum());

			if((beanPQTramiteTraficoGuardarConductorHabitual.getP_NIF()!=null && !beanPQTramiteTraficoGuardarConductorHabitual.getP_NIF().equals(""))
					|| ((beanPQTramiteTraficoGuardarConductorHabitual.getP_TIPO_INTERVINIENTE()!=null && !beanPQTramiteTraficoGuardarConductorHabitual.getP_TIPO_INTERVINIENTE().equals("")))) {
				resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = ejecutarProc(beanPQTramiteTraficoGuardarConductorHabitual, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "GUARDAR_INTERVINIENTE", BeanPQGeneral.class);

				pCodeConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_CODE);
				sqlErrmConductorHabitual = (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM);
				pInformacionConductorHabitual = (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_INFORMACION);
				verPCodeSqlErrPInfo(resultadoTramiteTraficoGuardarIntervinienteConductorHabitual);
				if ((ConstantesPQ.pCodeOkAlt.equals(pCodeConductorHabitual)))	{
					if ((beanPQTramiteTraficoGuardarConductorHabitual.getP_APELLIDO1_RAZON_SOCIAL()!=null && !"".equals(beanPQTramiteTraficoGuardarConductorHabitual.getP_APELLIDO1_RAZON_SOCIAL())
							|| (beanPQTramiteTraficoGuardarConductorHabitual.getP_NOMBRE()!=null && !"".equals(beanPQTramiteTraficoGuardarConductorHabitual.getP_NOMBRE())))) {
						resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = resultadoFaltaDNIInterviniente();
						pCodeConductorHabitual = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_CODE);
						sqlErrmConductorHabitual = (String)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro(ConstantesPQ.P_SQLERRM);
					} else {
						resultadoTramiteTraficoGuardarIntervinienteConductorHabitual = null;
						pCodeConductorHabitual = null;
						sqlErrmConductorHabitual = null;
						pInformacionConductorHabitual= null;
					}
				}
			}

			//------------------------TITULAR-------------------------------
			//	beanPQTramiteTraficoGuardarTitular = intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getTitularBean());
			beanPQTramiteTraficoGuardarTitular.setP_NUM_EXPEDIENTE(numExpediente);
			//	beanPQTramiteTraficoGuardarTitular.setP_TIPO_INTERVINIENTE(TipoInterviniente.Titular.getValorEnum());
			//Ejecutamos el procedimiento de guardar el titular

			// Mantis 16626. David Sierra: Mensaje de error si no se rellena el campo NIF/CIF
			if (beanPQTramiteTraficoGuardarTitular.getP_NIF()==null || beanPQTramiteTraficoGuardarTitular.getP_NIF().isEmpty()) {
				resultado.setMensaje("Los datos del Titular no se guardarán si no se rellena el campo NIF/CIF");
			}
			if ((beanPQTramiteTraficoGuardarTitular.getP_NIF()!=null && !beanPQTramiteTraficoGuardarTitular.getP_NIF().equals(""))
					|| (beanPQTramiteTraficoGuardarTitular.getP_TIPO_INTERVINIENTE()!=null && !beanPQTramiteTraficoGuardarTitular.getP_TIPO_INTERVINIENTE().equals(""))) {
				resultadoTramiteTraficoGuardarIntervinienteTitular = ejecutarProc(beanPQTramiteTraficoGuardarTitular,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE",BeanPQGeneral.class);

				//Recuperamos información de respuesta
				pCodeTitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_CODE);
				sqlErrmTitular = (String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_SQLERRM);
				pInformacionTitular = (String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_INFORMACION);
				verPCodeSqlErrPInfo(resultadoTramiteTraficoGuardarIntervinienteTitular);

				if ((ConstantesPQ.pCodeOkAlt.equals(pCodeTitular))) {
					if ((beanPQTramiteTraficoGuardarTitular.getP_APELLIDO1_RAZON_SOCIAL()!=null && !"".equals(beanPQTramiteTraficoGuardarTitular.getP_APELLIDO1_RAZON_SOCIAL())
							|| (beanPQTramiteTraficoGuardarTitular.getP_NOMBRE()!=null && !"".equals(beanPQTramiteTraficoGuardarTitular.getP_NOMBRE())))) {
						resultadoTramiteTraficoGuardarIntervinienteTitular = resultadoFaltaDNIInterviniente();
						pCodeTitular = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_CODE);
						sqlErrmTitular = (String)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro(ConstantesPQ.P_SQLERRM);
					} else {
						resultadoTramiteTraficoGuardarIntervinienteTitular= null;
						pCodeTitular = null;
						sqlErrmTitular = null;
						pInformacionTitular= null;
					}
				} else if ((!ConstantesPQ.pCodeOk.equals(pCodeTitular))) {
					//errorIntervinientes = true;
					//resultado.setMensaje("Error al guardar el titular: "+sqlErrmTitular);
					//resultado.setError(true);
				}
			}

			// -------------------------REPRESENTANTE-------------------------
			//	beanPQTramiteTraficoGuardarRepresentante = intervinienteTraficoBeanPQConversion.convertirBeanToPQ(traficoMatriculacionBean.getRepresentanteTitularBean());
			beanPQTramiteTraficoGuardarRepresentante.setP_NUM_EXPEDIENTE(numExpediente);
			//	beanPQTramiteTraficoGuardarRepresentante.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTitular.getValorEnum());

			if ((beanPQTramiteTraficoGuardarRepresentante.getP_NIF()!=null && !beanPQTramiteTraficoGuardarRepresentante.getP_NIF().equals(""))
					|| (beanPQTramiteTraficoGuardarRepresentante.getP_TIPO_INTERVINIENTE()!=null && !beanPQTramiteTraficoGuardarRepresentante.getP_TIPO_INTERVINIENTE().equals(""))) {
				//Ejecutamos el procedimiento de guardar el representante
				resultadoTramiteTraficoGuardarIntervinienteRepresentante = ejecutarProc(beanPQTramiteTraficoGuardarRepresentante,valoresSchemas.getSchema(),ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE",BeanPQGeneral.class);

				//Recuperamos información de respuesta
				pCodeRepresentante = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_CODE);
				sqlErrmRepresentante = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_SQLERRM);
				pInformacionRepresentante = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_INFORMACION);
				verPCodeSqlErrPInfo(resultadoTramiteTraficoGuardarIntervinienteRepresentante);

				if ((ConstantesPQ.pCodeOkAlt.equals(pCodeRepresentante))){
					if ((beanPQTramiteTraficoGuardarRepresentante.getP_APELLIDO1_RAZON_SOCIAL()!=null && !"".equals(beanPQTramiteTraficoGuardarRepresentante.getP_APELLIDO1_RAZON_SOCIAL())
							|| (beanPQTramiteTraficoGuardarRepresentante.getP_NOMBRE()!=null && !"".equals(beanPQTramiteTraficoGuardarRepresentante.getP_NOMBRE())))) {
						resultadoTramiteTraficoGuardarIntervinienteRepresentante = resultadoFaltaDNIInterviniente();
						pCodeRepresentante = (BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_CODE);
						sqlErrmRepresentante = (String)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro(ConstantesPQ.P_SQLERRM);
					} else {
						resultadoTramiteTraficoGuardarIntervinienteRepresentante = null;
						pCodeRepresentante = null;
						sqlErrmRepresentante = null;
						pInformacionRepresentante= null;
					}
				}
			}

		}
		//fin de guardar el tramite y los intervinientes

		if (sqlErrmVehiculo != null && pCodeVehiculo != null && !ConstantesPQ.pCodeOk.equals(pCodeVehiculo))
			resultado.setMensaje("Error al guardar el Vehículo: " + sqlErrmVehiculo);
		if (pInformacionVehiculo != null) 
			resultado.setMensaje("Vehiculo: " + pInformacionVehiculo);

		if (sqlErrmTramite != null && pCodeTramite != null && !ConstantesPQ.pCodeOk.equals(pCodeTramite))
			resultado.setMensaje("Error al guardar el Trámite: " + sqlErrmTramite);
		if (pInformacionTramite != null) 
			resultado.setMensaje("Tramite: " + pInformacionTramite);

		if (sqlErrmTitular != null && pCodeTitular != null && !ConstantesPQ.pCodeOk.equals(pCodeTitular))
			resultado.setMensaje("Error al guardar el Titular: "+ sqlErrmTitular);
		if (pInformacionTitular != null && ConstantesPQ.pCodeOk.equals(pCodeTitular))
			resultado.setMensaje("Titular: "+ pInformacionTitular);

		if (sqlErrmRepresentante != null && pCodeRepresentante != null && !ConstantesPQ.pCodeOk.equals(pCodeRepresentante))
			resultado.setMensaje("Error al guardar el Representante: "+ sqlErrmRepresentante);
		if (pInformacionRepresentante != null && ConstantesPQ.pCodeOk.equals(pCodeTitular))
			resultado.setMensaje("Representante: "+ pInformacionRepresentante);

		if (sqlErrmArrendatario != null && pCodeArrendatario != null && !ConstantesPQ.pCodeOk.equals(pCodeArrendatario))
			resultado.setMensaje("Error al guardar el Arrendatario: "+ sqlErrmArrendatario);
		if (pInformacionArrendatario != null && ConstantesPQ.pCodeOk.equals(pCodeTitular))
			resultado.setMensaje("Arrendatario: "+ pInformacionArrendatario);

		if (sqlErrmConductorHabitual != null && pCodeConductorHabitual != null && !ConstantesPQ.pCodeOk.equals(pCodeConductorHabitual))
			resultado.setMensaje("Error al guardar el Conductor Habitual: "+ sqlErrmConductorHabitual);
		if (pInformacionConductorHabitual != null && ConstantesPQ.pCodeOk.equals(pCodeConductorHabitual))
			resultado.setMensaje("Conductor Habitual: "+ pInformacionConductorHabitual);

		// RRR DESAPARECE EL REPRESENTANTE DEL CONDUCTOR HABITUAL RESPECTO DE LA VERSIÓN INICIAL DE MATW
		//if (sqlErrmRepresentanteConductorHabitual != null && pCodeRepresentanteConductorHabitual != null && !ConstantesPQ.pCodeOk.equals(pCodeRepresentanteConductorHabitual))
		//	resultado.setMensaje("Error al guardar el representante del conductor habitual: "+ sqlErrmRepresentanteConductorHabitual);
		//if (pInformacionRepresentanteConductorHabitual != null && ConstantesPQ.pCodeOk.equals(pCodeRepresentanteConductorHabitual))
		//	resultado.setMensaje("Representante del Conductor Habitual: "+ pInformacionRepresentanteConductorHabitual);

		if (sqlErrmRepresentanteArrendatario != null && pCodeRepresentanteArrendatario != null && !ConstantesPQ.pCodeOk.equals(pCodeRepresentanteArrendatario))
			resultado.setMensaje("Error al guardar el representante del arrendatario: "+ sqlErrmRepresentanteArrendatario);
		if (pInformacionRepresentanteArrendatario != null && ConstantesPQ.pCodeOk.equals(pCodeRepresentanteArrendatario))
			resultado.setMensaje("Representante del arrendatario: "+ pInformacionRepresentanteArrendatario);

		TramiteTraficoMatriculacionBean tramiteRespuesta = new TramiteTraficoMatriculacionBean(true);
		if (ConstantesPQ.pCodeOk.equals(pCodeTramite)) {
			tramiteRespuesta=matriculacionTramiteTraficoBeanMatwPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarMatriculacion);

			if (resultadoVehiculo!=null && resultadoVehiculo.getParametro("P_CODE") != null &&
					((BigDecimal)resultadoVehiculo.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				tramiteRespuesta.getTramiteTraficoBean().setVehiculo(vehiculoBeanPQConversion.convertirPQToBean(resultadoVehiculo));
				if(tramiteRespuesta.getTramiteTraficoBean().getVehiculo() != null){
					tramiteRespuesta.getTramiteTraficoBean().getVehiculo().setHomologacionBean(servicioDirectivaCee.getHomologacionBean((String)resultadoVehiculo.getParametro("P_ID_DIRECTIVA_CEE")));
				}
				tramiteRespuesta.setVehiculoPreverBean(vehiculoBeanPQConversion.convertirPQToBeanPrever(resultadoVehiculo));
			} else {
				tramiteRespuesta.getTramiteTraficoBean().setVehiculo(vehiculoBeanPQConversion.convertirPQtoBean(beanPQVehiculosGuardar));
				tramiteRespuesta.setVehiculoPreverBean(vehiculoBeanPQConversion.convertirPQToBeanPrever(beanPQVehiculosGuardar));
			}

			if (resultadoTramiteTraficoGuardarIntervinienteTitular!=null && ((BigDecimal)resultadoTramiteTraficoGuardarIntervinienteTitular.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				tramiteRespuesta.setTitularBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteTitular));
				// tramiteRespuesta.getTramiteTraficoBean().setEstado(tramiteRespuesta.getTitularBean().getEstadoTramite() != null ? tramiteRespuesta.getTitularBean().getEstadoTramite().getValorEnum():null);
			} else {
				tramiteRespuesta.setTitularBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(beanPQTramiteTraficoGuardarTitular, tramiteRespuesta.getTramiteTraficoBean().getIdContrato()));
			}

			if (resultadoTramiteTraficoGuardarIntervinienteArrendatario!=null &&
					((BigDecimal)resultadoTramiteTraficoGuardarIntervinienteArrendatario.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				tramiteRespuesta.setArrendatarioBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteArrendatario));
				tramiteRespuesta.getTramiteTraficoBean().setEstado(tramiteRespuesta.getArrendatarioBean().getEstadoTramite() != null ? tramiteRespuesta.getArrendatarioBean().getEstadoTramite().getValorEnum():null);
			} else {
				if (beanPQTramiteTraficoGuardarMatriculacion.getP_RENTING().equals("SI")){
					tramiteRespuesta.setArrendatarioBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(beanPQTramiteTraficoGuardarArrendatario, tramiteRespuesta.getTramiteTraficoBean().getIdContrato()));
				}else{
					tramiteRespuesta.setArrendatarioBean(new IntervinienteTrafico(true));
				}
			}

			if (resultadoTramiteTraficoGuardarIntervinienteRepresentante!=null && ((BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentante.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				tramiteRespuesta.setRepresentanteTitularBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentante));
				// tramiteRespuesta.getTramiteTraficoBean().setEstado(tramiteRespuesta.getRepresentanteTitularBean().getEstadoTramite() != null ? tramiteRespuesta.getRepresentanteTitularBean().getEstadoTramite().getValorEnum():null);
			} else {
				tramiteRespuesta.setRepresentanteTitularBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(beanPQTramiteTraficoGuardarRepresentante, tramiteRespuesta.getTramiteTraficoBean().getIdContrato()));
			}

			if (resultadoTramiteTraficoGuardarIntervinienteConductorHabitual!=null && ((BigDecimal)resultadoTramiteTraficoGuardarIntervinienteConductorHabitual.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				tramiteRespuesta.setConductorHabitualBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteConductorHabitual));
				// tramiteRespuesta.getTramiteTraficoBean().setEstado(tramiteRespuesta.getConductorHabitualBean().getEstadoTramite() != null ? tramiteRespuesta.getConductorHabitualBean().getEstadoTramite().getValorEnum():null);
			} else {
				tramiteRespuesta.setConductorHabitualBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(beanPQTramiteTraficoGuardarConductorHabitual, tramiteRespuesta.getTramiteTraficoBean().getIdContrato()));
			}

			// RRR DESAPARECE EL REPRESENTANTE DEL CONDUCTOR HABITUAL RESPECTO DE LA VERSIÓN INICIAL DE MATW
			//if (resultadoTramiteTraficoGuardarIntervinienteRepresentanteConductorHabitual!=null && ((BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteConductorHabitual.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
			//	tramiteRespuesta.setRepresentanteConductorHabitualBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentanteConductorHabitual));
			//	tramiteRespuesta.getTramiteTraficoBean().setEstado(tramiteRespuesta.getRepresentanteConductorHabitualBean().getEstadoTramite() != null ? tramiteRespuesta.getRepresentanteConductorHabitualBean().getEstadoTramite().getValorEnum():null);
			//} else {
		//		tramiteRespuesta.setRepresentanteConductorHabitualBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(beanPQTramiteTraficoGuardarRepresentanteConductorHabitual, tramiteRespuesta.getTramiteTraficoBean().getIdContrato()));
		//	}

			if (resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario!=null && ((BigDecimal)resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				tramiteRespuesta.setRepresentanteArrendatarioBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarIntervinienteRepresentanteArrendatario));
				// tramiteRespuesta.getTramiteTraficoBean().setEstado(tramiteRespuesta.getRepresentanteArrendatarioBean().getEstadoTramite() != null ? tramiteRespuesta.getRepresentanteArrendatarioBean().getEstadoTramite().getValorEnum():null);
			} else {
				tramiteRespuesta.setRepresentanteArrendatarioBean(intervinienteTraficoBeanPQConversion.convertirPQToBean(beanPQTramiteTraficoGuardarRepresentanteArrendatario, tramiteRespuesta.getTramiteTraficoBean().getIdContrato()));
			}

			//	traficoMatriculacionBean = MatriculacionTramiteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarMatriculacion);
			//	traficoMatriculacionBean.setTramiteTraficoBean(TramiteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarMatriculacion));
			//	traficoMatriculacionBean.setTramiteTraficoBean(TramiteTraficoBeanPQConversion.convertirPQToBean(resultadoTramiteTraficoGuardarMatriculacion));
			//	traficoMatriculacionBean.setTramiteTraficoBean(resultadoTramiteTraficoGuardarMatriculacion);
		} else if (vehiculoNuevo) {
			// Eliminar vehiculo
			if (resultadoVehiculo != null && resultadoVehiculo.getParametro("P_CODE") != null && resultadoVehiculo.getParametro("P_ID_VEHICULO") != null
					&& ((BigDecimal) resultadoVehiculo.getParametro("P_CODE")).equals(ConstantesPQ.pCodeOk)) {
				BigDecimal idVehiculo = (BigDecimal) resultadoVehiculo.getParametro("P_ID_VEHICULO");
				ServicioVehiculo servicioVehiculo = ContextoSpring.getInstance().getBean(ServicioVehiculo.class);
				servicioVehiculo.eliminar(idVehiculo.longValue());
			}
		}

		log.debug(ConstantesPQ.MENSAJE + resultado.getMensaje());
		resultadoAlta.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoAlta.put(ConstantesPQ.BEANPANTALLA, tramiteRespuesta);
		return resultadoAlta;
	}

	/**
	 * 
	 * @param traficoTramiteMatriculacionBean
	 * @param numColegiado
	 * @param idContratoSesion
	 * @param idUsuario
	 * @param idContrato
	 * @return
	 * @throws OegamExcepcion
		Este metodo es el que se llama en matw 
	 */
	public HashMap<String, Object> guardarTramite(
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean,
			String numColegiado, BigDecimal idContratoSesion,
			BigDecimal idUsuario, BigDecimal idContrato) throws OegamExcepcion {
		HashMap<String, Object> resultadoModelo = null;

		resultadoModelo = guardarAltaTramiteMatriculacionMatw(matriculacionTramiteTraficoBeanMatwPQConversion
				.convertirTramiteMatriculacionToPQ(
						traficoTramiteMatriculacionBean, numColegiado,
						idContratoSesion, idUsuario, idContrato));

		return resultadoModelo;
	}

	/**
	 * Simula la respuesta de una llamada al procedimiento de guardar vehículo
	 * sin matrícula, condigo itv o bastidor
	 * 
	 * @return
	 */
	private RespuestaGenerica resultadoFaltanDatosVehiculo() {
		RespuestaGenerica resultadoVeh = new RespuestaGenerica();
		HashMap<String, Object> paramManual = new HashMap<String, Object>();
		paramManual.put(ConstantesPQ.P_CODE, new BigDecimal(1));
		paramManual.put(ConstantesPQ.P_SQLERRM, "Faltan datos obligatorios");
		resultadoVeh.setMapaParametros(paramManual);
		return resultadoVeh;
	}

	/**
	 * Simula la respuesta de una llamada al procedimiento de guardar
	 * interviniente sin dni
	 * 
	 * @return
	 */
	private RespuestaGenerica resultadoFaltaDNIInterviniente() {
		RespuestaGenerica resultadoVeh = new RespuestaGenerica();
		HashMap<String, Object> paramManual = new HashMap<String, Object>();
		paramManual.put(ConstantesPQ.P_CODE, new BigDecimal(1));
		paramManual.put(ConstantesPQ.P_SQLERRM, "Falta el DNI");
		resultadoVeh.setMapaParametros(paramManual);
		return resultadoVeh;
	}

	/* MÉTODO IMPORTADO DE MATW -CAMBIAR EL PQ CUANDO SE CAMBIE EN BB.DD */

	public ResultBean matricular(BeanPQMatricular beanPQMatricular){
		log.info("matricular");

		ResultBean resultBean = new ResultBean();

		RespuestaGenerica respuestaMatricular = ejecutarProc(beanPQMatricular, valoresSchemas.getSchema(), ValoresCatalog.PQ_DGT_WS, "MATRICULAR", BeanPQGeneral.class);

		log.info(ConstantesPQ.LOG_P_CODE + respuestaMatricular .getParametro(ConstantesPQ.P_CODE));
		log.info(ConstantesPQ.LOG_P_INFORMACION + respuestaMatricular .getParametro(ConstantesPQ.P_INFORMACION));
		log.info(ConstantesPQ.LOG_P_SQLERRM + respuestaMatricular .getParametro(ConstantesPQ.P_SQLERRM));

		if ((!ConstantesPQ.pCodeOk.equals(respuestaMatricular.getParametro(ConstantesPQ.P_CODE)))) {
			log.error(ERROR_AL_MATRICULAR);
			resultBean.setError(true);
			resultBean.setMensaje(respuestaMatricular.getParametro(ConstantesPQ.P_SQLERRM) + " ");
		} else{
			resultBean.setMensaje(MATRICULA_INSCRITA_CORRECTAMENTE);
			log.info(MATRICULA_INSCRITA_CORRECTAMENTE);
			resultBean.setError(false);
		}

		log.info("fin matricular");

		return resultBean;
	}

	public ResultBean validarWMI (TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean){
		ResultBean resultado = new ResultBean();

		List<String> errores = validarTramiteWMI(traficoTramiteMatriculacionBean.getTramiteTraficoBean());

		resultado.setListaMensajes(errores);
		resultado.setError(false);
		//if((resultado.getListaMensajes()!=null) && (resultado.getListaMensajes().size()> 0))
		//	resultado.getListaMensajes().remove(resultado.getListaMensajes().size()-1);

		return resultado;
	}

	private List<String> validarTramiteWMI(TramiteTraficoBean traficoTramiteBean) {
		List<String> errores = new ArrayList<String>();
		BeanPQValidarWMI beanPqWMI= new BeanPQValidarWMI();

		beanPqWMI.setP_BASTIDOR(traficoTramiteBean.getVehiculo().getBastidor()!=null && !traficoTramiteBean.getVehiculo().getBastidor().equals("")?traficoTramiteBean.getVehiculo().getBastidor():null);
		beanPqWMI.setP_CAT_EU(traficoTramiteBean.getVehiculo().getHomologacionBean()!=null && traficoTramiteBean.getVehiculo().getHomologacionBean().getIdHomologacion()!=null && !traficoTramiteBean.getVehiculo().getHomologacionBean().getIdHomologacion().equals("")?traficoTramiteBean.getVehiculo().getHomologacionBean().getIdHomologacion():null);
		beanPqWMI.setP_MARCA(traficoTramiteBean.getVehiculo().getMarcaBean()!=null && traficoTramiteBean.getVehiculo().getMarcaBean().getCodigoMarca()!=null && !traficoTramiteBean.getVehiculo().getMarcaBean().getCodigoMarca().equals("")?traficoTramiteBean.getVehiculo().getMarcaBean().getCodigoMarca():null);
		beanPqWMI.setP_FABRICANTE(traficoTramiteBean.getVehiculo().getFabricante()!=null && !traficoTramiteBean.getVehiculo().getFabricante().equals("")?traficoTramiteBean.getVehiculo().getFabricante():null);
		if(traficoTramiteBean.getVehiculo().getRevisado()!=null){
			beanPqWMI.setP_REVISADO(traficoTramiteBean.getVehiculo().getRevisado().toString());
		}else{
			beanPqWMI.setP_REVISADO(Boolean.FALSE.toString());
		}

		RespuestaGenerica resultadoValidacion = ejecutarProc(beanPqWMI,valoresSchemas.getSchema(),ValoresCatalog.PQ_VEHICULOS,"VALIDAR_WMI",BeanPQGeneral.class);

		//Recuperamos información de respuesta
		BigDecimal pCode = (BigDecimal)resultadoValidacion.getParametro(ConstantesPQ.P_CODE);
		String sqlErrm = (String)resultadoValidacion.getParametro(ConstantesPQ.P_SQLERRM);
		String pInformacion = (String)resultadoValidacion.getParametro(ConstantesPQ.P_INFORMACION);

		if(pCode!= null && !pCode.equals(new BigDecimal(0))){ //Ha habido errores...
			String[] erroresAux = sqlErrm.split(" - ");
			for(String error:erroresAux){
				if(!error.trim().equals("")){
					if(error.contains("- ")){
						error = error.replace("- ", "");
					}
					errores.add(error.trim());
				}
			}
		}

		if(pInformacion!=null && !pInformacion.equals("")){
			String[] erroresAux = pInformacion.split(" - ");
			for(String error:erroresAux){
				if(!error.trim().equals("")){
					if(error.contains("- ")){
						error = error.replace("- ", "");
					}
					errores.add(error.trim());
				}
			}
		}
		return errores;
	}

	/**
	 * Método que valida el trámite, el CEM y el Anagrama del titular
	 */
	public ResultBean validar(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		ResultBean resultado = new ResultBean();

//**************************************************************************************************************************
// VALIDACIONES QUE SON BLOQUEANTES Y NO CAMBIAN EL TRAMITE DE ESTADO SI SE PRODUCE ALGUN ERROR EL TRAMITE SEGUIRÁ A INICIADO
//**************************************************************************************************************************

		// Validar territorialidad
		boolean territorialidadValida = validarTerritorialidad(traficoTramiteMatriculacionBean);
		if (!territorialidadValida) {
			String mensajeTerritorialidad = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.mensaje");
			resultado.setError(true);
			resultado.setMensaje(mensajeTerritorialidad);
			return resultado;
		}

		/* Validaciones del IVTM */
		List<String> erroresIVTM = new ArrayList<String>();
		if (traficoTramiteMatriculacionBean!=null && traficoTramiteMatriculacionBean.getTramiteTraficoBean()!=null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()!=null){
			erroresIVTM = new IVTMModeloMatriculacionImpl().validarMatriculacion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		} else {
			erroresIVTM = new ArrayList<String>();
			erroresIVTM.add("Hay errores recuperando datos, y no se puede comprobar la situación del IVTM");
		}
		if (erroresIVTM.size()>0){
			List<String> listaErrorres = new ArrayList<>();
			for (String error : erroresIVTM){
				listaErrorres.add(error);
			}
			resultado.setListaMensajes(listaErrorres);
			resultado.setError(true);
			resultado.setMensaje("Error de IVTM");
			return resultado;
		}
		/* Fin validaciones del IVTM */

		// Mantis 19172 y 21598. David Sierra: Validar fecha caducidad nifs y fecha de nacimiento de los intervinientes
		try {
			ResultBean resultValCadNif = comprobarCaducidadNifs(traficoTramiteMatriculacionBean, true);
			if(resultValCadNif.getError()) {
				return resultValCadNif;
			}else if(!resultValCadNif.getError() && resultValCadNif.getListaMensajes() != null && !resultValCadNif.getListaMensajes().isEmpty()){
				resultado.setListaMensajes(resultValCadNif.getListaMensajes());
			}
			String mensajeValidezFechas = comprobarFormatoFechas(traficoTramiteMatriculacionBean);
			if(!mensajeValidezFechas.isEmpty()) {
				resultado.setError(true);
				resultado.setMensaje(mensajeValidezFechas);
				return resultado;
			}
		} catch (ParseException e) {
			log.error("Al comprobar la fecha de caducidad de un NIF se ha producido el siguiente error: " + e.getMessage());
		}

		// DRC@01-04-2013 Incidencia Mantis: 4044

		// Validación del Fabricante inactivo

		boolean validacionFabricante = validarFabricante(traficoTramiteMatriculacionBean);
		if (!validacionFabricante) {
			String mensajeFabricante = "Fabricante no verificado ";
			resultado.setError(true);
			resultado.setMensaje(mensajeFabricante);
			return resultado;
		}

		if (traficoTramiteMatriculacionBean.getCheckJustifIvtm() != null
				&& traficoTramiteMatriculacionBean.getCheckJustifIvtm().equals("S")
				&& !validarSiPagoIVTM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente(),
						traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado())) {
			resultado.setError(true);
			resultado.addMensajeALista("No se ha encontrado el justificante de pago IVTM");
			return resultado;
		}

		// Validación Matrícula origen y matrícula extranjera.
		String validacionMatriculaOrigen = validarMatriculaOrigen(traficoTramiteMatriculacionBean);
		if (!"OK".equals(validacionMatriculaOrigen)) {
			resultado.setError(true);
			resultado.setMensaje(validacionMatriculaOrigen);
			return resultado;
		}

//*****************************************************************************************************************************
// FIN VALIDACIONES BLOQUEANTES
//******************************************************************************************************************************

//******************************************************************************************************************************
// VALIDACIONES QUE SON INFORMATIVAS Y CAMBIAN EL TRAMITE DE ESTADO , EL TRAMITE PASARA A VALIDADOPDF O VALIDADOTELEMATICAMENTE
//*******************************************************************************************************************************

		ResultBean resultadoValidarTramite = getModeloTrafico().validarTramite(traficoTramiteMatriculacionBean.getTramiteTraficoBean());

		// Mantis 15845.David Sierra: Validacion Tipo Inspeccion ITV controlando error y aviso
		String tipoInspeccionItvValidacion = validarTipoInspeccionItv(traficoTramiteMatriculacionBean);

		if ("error".equals(tipoInspeccionItvValidacion)) {
			String mensajeTipoInspeccionItvError = ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV_ERROR;
			resultadoValidarTramite.getListaMensajes().add(mensajeTipoInspeccionItvError);
			resultadoValidarTramite.setError(true);
		} else if ("aviso".equals(tipoInspeccionItvValidacion)) {
			String mensajeTipoInspeccionItvAviso = ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV;
			resultadoValidarTramite.getListaMensajes().add(mensajeTipoInspeccionItvAviso);
		} else if ("avisoFecha".equals(tipoInspeccionItvValidacion)) {
			String mensajeTipoInspeccionItvAvisoFecha = ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV_AVISOFECHA;
			resultadoValidarTramite.getListaMensajes().add(mensajeTipoInspeccionItvAvisoFecha);	
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv().setDia("");
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv().setMes("");
			traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv().setAnio("");
		}
		// Fin Mantis
		if (!resultadoValidarTramite.getError() && resultadoValidarTramite.getListaMensajes().size()==0) {
			String cem = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm();
			String nif = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif();
			String apellido1 = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getApellido1RazonSocial();
			String bastidor = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor();
			ResultBean resultBeanCEM = new ResultBean();
			// Si el titular es del País Vasco, no validamos el CEM
			boolean validarCEM = true;
			if(traficoTramiteMatriculacionBean!=null
				&& traficoTramiteMatriculacionBean.getTitularBean()!=null
				&& traficoTramiteMatriculacionBean.getTitularBean().getPersona()!=null
				&& traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion()!=null
				&& traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio()!=null
				&& traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia()!=null
				&& traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia()!=null
				&& (traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equals(ProvinciasEstacionITV.getIdProvincia(ProvinciasEstacionITV.ALAVA.getValorEnum()))
				|| traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equals(ProvinciasEstacionITV.getIdProvincia(ProvinciasEstacionITV.GUIPUZCOA.getValorEnum()))
				|| traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia().equals(ProvinciasEstacionITV.getIdProvincia(ProvinciasEstacionITV.VIZCAYA.getValorEnum())))	
					){
				validarCEM = false;
			}

			//Si hay CEM, lo validamos
			if (!"".equals(cem) && validarCEM) resultBeanCEM = getModeloTrafico().validaCEM(cem, nif, apellido1, bastidor);
			//Si el CEM no ha validado correctamente
			if (resultBeanCEM.getError()) {
				//Guardamos el error para verlo por pantalla
				//errores.add(resultBeanCEM.getMensaje());
				// DRC@01-04-2013 Incidencia Mantis: 4044
				resultadoValidarTramite.getListaMensajes().add(resultBeanCEM.getMensaje());
				resultadoValidarTramite.setError(Boolean.TRUE);

				//Y cambiamos el estado a validado PDF
				BigDecimal num_expediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente();
				String nuevoEstado = EstadoTramiteTrafico.Validado_PDF.getValorEnum();
				BeanPQCambiarEstadoTramite tramiteCambio = new BeanPQCambiarEstadoTramite();
				tramiteCambio.setP_NUM_EXPEDIENTE(num_expediente);
				tramiteCambio.setP_ESTADO(new BigDecimal(nuevoEstado));
				tramiteCambio.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
				tramiteCambio.setP_RESPUESTA("CEM no validable telemáticamente");
				//Como el trámite ha validado, pero el CEM no, cambiamos el estado a Validado_PDF
				HashMap<String,Object> respuestaCambioEstado = getModeloTrafico().cambiarEstadoTramite(tramiteCambio);
				ResultBean resultadoCambio = (ResultBean)respuestaCambioEstado.get(ConstantesPQ.RESULTBEAN);
				if (resultadoCambio.getError()){
					log.error("El tramite " + num_expediente + " ha dado error al cambiar de estado por el fallo de validacion del CEM: " + resultadoCambio.getMensaje());
				} else {
					log.error("El tramite " + num_expediente + " ha cambiado de estado debido al fallo de validacion del CEM");
					tramiteCambio = (BeanPQCambiarEstadoTramite)respuestaCambioEstado.get(ConstantesPQ.BEANPQRESULTADO);
					//seteamos el estado que devuelve el paquete para que se muestre en pantalla
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(((BigDecimal)tramiteCambio.getP_ESTADO()).toString());
					//y la fecha de última modificación
					Timestamp fecha = (Timestamp)tramiteCambio.getP_FECHA_ULT_MODIF();
					Fecha fecha_ult = utilesFecha.getFechaFracionada(fecha);
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().setFechaUltModif(fecha_ult);
				}
			} //Si el cem es válido para nuestro trámite, validamos también el anagrama del titular en caso de que sea una persona física
			else if (traficoTramiteMatriculacionBean.getTitularBean()!=null
					&& traficoTramiteMatriculacionBean.getTitularBean().getPersona()!=null
					&& traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif()!=null
					&& utilesConversiones.isNifNie(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getNif())){
				String anagrama = traficoTramiteMatriculacionBean.getTitularBean().getPersona().getAnagrama();

			// Se va a validar el anagrama dependiendo de la propertie
			String validarAnag = gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA);
			if ( OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA.equalsIgnoreCase(validarAnag) && !EstadoTramiteTrafico.Iniciado.equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado())) {
				ResultBean resultBeanAnagrama;
				if (OPCION_SI_VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM.equalsIgnoreCase(gestorPropiedades.valorPropertie(VALIDAR_ANAGRAMA_SI_AEAT_CAIDA_SELENIUM))){
					resultBeanAnagrama = getModeloTrafico().validarAnagramaSelenium(nif,apellido1,anagrama);
				}else{
					resultBeanAnagrama = getModeloTrafico().validarAnagrama(nif,apellido1,anagrama);
				}

				//Si el anagrama no ha validado correctamente

				if (resultBeanAnagrama.getError()) {
					//Guardamos el error para verlo por pantalla
					//errores.add(resultBeanAnagrama.getMensaje());
					// DRC@01-04-2013 Incidencia Mantis: 4044
					resultadoValidarTramite.getListaMensajes().add(resultBeanAnagrama.getMensaje());
					resultadoValidarTramite.setError(Boolean.TRUE);

					//Y cambiamos el estado a validado PDF
					BigDecimal num_expediente = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente();
					String nuevoEstado = EstadoTramiteTrafico.Validado_PDF.getValorEnum();
					BeanPQCambiarEstadoTramite tramiteCambio = new BeanPQCambiarEstadoTramite();
					tramiteCambio.setP_NUM_EXPEDIENTE(num_expediente);
					tramiteCambio.setP_ESTADO(new BigDecimal(nuevoEstado));
					tramiteCambio.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
					//Como el trámite ha validado, el CEM también, pero el anagrama no, cambiamos el estado a Validado_PDF
					HashMap<String,Object> respuestaCambioEstado = getModeloTrafico().cambiarEstadoTramite(tramiteCambio);
					ResultBean resultadoCambio = (ResultBean)respuestaCambioEstado.get(ConstantesPQ.RESULTBEAN);
					if (resultadoCambio.getError()){
						log.error("El tramite " + num_expediente + " ha dado error al cambiar de estado por el fallo de validacion del anagrama: " + resultadoCambio.getMensaje());
					} else {
						log.error("El tramite " + num_expediente + " ha cambiado de estado debido al fallo de validacion del anagrama");
						tramiteCambio = (BeanPQCambiarEstadoTramite)respuestaCambioEstado.get(ConstantesPQ.BEANPQRESULTADO);
						//seteamos el estado que devuelve el paquete para que se muestre en pantalla
						traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(((BigDecimal)tramiteCambio.getP_ESTADO()).toString());
						//y la fecha de última modificación
						Timestamp fecha = (Timestamp)tramiteCambio.getP_FECHA_ULT_MODIF();
						Fecha fecha_ult = utilesFecha.getFechaFracionada(fecha);
						traficoTramiteMatriculacionBean.getTramiteTraficoBean().setFechaUltModif(fecha_ult);
					}
				}
			}
		}
	}

	ResultBean resultadoWMI = validarWMI(traficoTramiteMatriculacionBean);

//***************************************************************************************************************
// FIN VALIDACIONES INFORMATIVAS
//***************************************************************************************************************
		if (!resultadoValidarTramite.getError() && resultadoValidarTramite.getListaMensajes().size() == 0) {
			resultado.setMensaje(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum());
			resultado.getListaMensajes().remove(resultado.getListaMensajes().size()-1);
			if(resultadoWMI!=null){
				if(resultadoWMI.getListaMensajes()!=null && resultadoWMI.getListaMensajes().size()>0){
					resultado.addListaMensajes(resultadoWMI.getListaMensajes());
				}
			}
			return resultado;
		} else {
			if(resultadoWMI!=null){
				if(resultadoWMI.getListaMensajes()!=null && resultadoWMI.getListaMensajes().size()>0){
					resultadoValidarTramite.getListaMensajes().add(resultadoWMI.getListaMensajes().get(0));
				}
			}
			if (!resultadoValidarTramite.getError() && resultadoValidarTramite.getListaMensajes().size() > 0) {
				resultado.setListaMensajes(resultadoValidarTramite.getListaMensajes());
				resultado.setError(false);
				resultado.setMensaje(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				resultado.getListaMensajes().remove(resultado.getListaMensajes().size()-1);
			} else {
				resultado.setListaMensajes(resultadoValidarTramite.getListaMensajes());
				resultado.setError(true);
				resultado.setMensaje(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum());
				resultado.getListaMensajes().remove(resultado.getListaMensajes().size()-1);
			}
			return resultado;
		}
	}

	/**
	 * Valida si el titular y el vehículo son válidos por la territorialidad de su dirección
	 * @param tramiteTraficoTransmisionBean
	 * @return
	 */
	private boolean validarTerritorialidad(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		boolean valido = true;
		try {
			String activo = gestorPropiedades.valorPropertie("territorialidad.matriculacion.exclusiones.activar");

			if ("S".equals(activo) || "SI".equals(activo)) {
				Persona titular = null;
				Direccion direccionTitular = null;
				String provinciaTitular = null;

				String[] arrayExcluidas = gestorPropiedades.valorPropertie("territorialidad.ctit.exclusiones.provincias").split(",");

				if (tramiteTraficoMatriculacionBean.getTitularBean() != null && tramiteTraficoMatriculacionBean.getTitularBean().getPersona() != null) {
					titular = tramiteTraficoMatriculacionBean.getTitularBean().getPersona();
				}

				if (titular != null) {
					direccionTitular = ModeloPersona.dameDireccionPersona(titular);
					provinciaTitular = ModeloPersona.dameProvinciaPersona(direccionTitular);
				}

				for (String provinciaExcluida : arrayExcluidas) {
					if (provinciaTitular != null && provinciaExcluida.equals(provinciaTitular)) {
						valido = false;
					}
				}
			}
		} catch (Throwable e) {
			valido = true;
			log.error("Error al validar la territorialidad del trámite: " + tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		}
		return valido;
	}

	/**
	 * Valida si el Fabricante del vehículo no esta verificado por que actualmente no está vigente y es un fabricante antiguo
	 * @param tramiteTraficoTransmisionBean
	 * @return
	 */
	private boolean validarFabricante(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		boolean valido = true;
		String cod_fab;

		try {
			String[] arrayFabricantesInactivos = gestorPropiedades.valorPropertie("Fabricantes.inactivos").split(",");

			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricante()!=null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricante().isEmpty()) {

				FabricanteVO fabricanteVO = servicioFabricante.getFabricante(tramiteTraficoMatriculacionBean
								.getTramiteTraficoBean().getVehiculo().getFabricante());

				cod_fab=fabricanteVO.getCodFabricante().toString();

				for (int i = 0; i < arrayFabricantesInactivos.length; i++)
					if (arrayFabricantesInactivos[i].equals(cod_fab))
						return false;
			}
		} catch (Throwable e) {
			valido = true;
			log.error("Error al validar el Fabricante del vehiculo (Fabricante no verificado): " + tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		}
		return valido;
	}

	private boolean validarSiPagoIVTM(BigDecimal numExpediente, String numColegiado) {
		boolean valido = true;
		try {
			ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(numExpediente.toString());
			if (resultado.getError()) {
				String numColegiados = gestorPropiedades.valorPropertie("colegiados.sin.pago.justificante.IVTM");
				if (numColegiados != null && numColegiados.contains(numColegiado)) {
					return true;
				}
				return false;
			}
		} catch (Throwable e) {
			valido = true;
			log.error("Error al validar el pago IVTM del trámite: " + numExpediente, e, numExpediente.toString());
		}
		return valido;
	}

	/**
	 * Mantis 15845. David Sierra
	 * Valida el Tipo Inspeccion ITV en funcion del elemento seleccionado en tipoVehiculo y servicio
	 * @param tramiteTraficoTransmisionBean
	 * @return valido
	 */
	public String validarTipoInspeccionItv(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		String servicio = null;
		String tipoVehiculo = null;
		String motivo = null;
		String tipo = null;
		String validacion = null;
		Fecha fecha = null;
		boolean fechaCompleta = true;
		try {
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() !=null
				&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio().isEmpty()) {
				servicio = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio();
			}
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo() !=null
				&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().isEmpty()) {
				tipoVehiculo = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
			}
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo() !=null
				&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo().isEmpty()) {
				motivo = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo();
			}
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv() !=null) {
				fecha = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv();
			}
			if((fecha.getDia()==null || fecha.getDia().isEmpty()) && (fecha.getMes()==null || fecha.getMes().isEmpty())
				&& (fecha.getAnio()==null || fecha.getAnio().isEmpty())) {
				fechaCompleta = false;
			}
			if (tipoVehiculo!=null && !tipoVehiculo.isEmpty()) {
				tipo = tipoVehiculo.substring(0, 1);
				// A09: PUBL-Obras, B09: PART-Obras, 7: Vehiculo especial, E: Exento ITV
				// No se ha seleccionado Exento ITV cuando corresponde
				if (("A09".equals(servicio) || "B09".equals(servicio)) && ("7".equals(tipo) && !"E".equals(motivo))) {
						validacion = "aviso";
				}
				// Aviso fecha para validación con exento y fecha ITV rellena
				else if (("A09".equals(servicio) || "B09".equals(servicio)) && ("7".equals(tipo) && "E".equals(motivo)
					&& fechaCompleta)) {
					validacion = "avisoFecha";
				}
				// Se ha seleccionado Exento ITV cuando no corresponde
				else if ((!"A09".equals(servicio) || !"B09".equals(servicio)) && (!"7".equals(tipo) && ("E".equals(motivo)))) {
					validacion = "error";
				} else {
					validacion = "correcta";
				}
			}
		} catch (Throwable e) {
			log.error("Error al validar el Tipo Inspeccion ITV del tramite: " + tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		}
		return validacion;
	}

	/**
	 * Mantis 19262. David Sierra
	 * Validacion para comprobar que las fechas de pantalla tienen un formato correcto
	 * @param tramiteTraficoTransmisionBean
	 * @return resultadoValidacion
	 */
	public String validarFechasMatriculacion(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		String resultadoValidacion = "OK";
		do {
			if(!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getFechaPresentacion().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getFechaPresentacion())) {
				resultadoValidacion = "La fecha de presentación del trámite que ha introducido no es válida";
				break;
			}
			if(tramiteTraficoMatriculacionBean.getTitularBean().getPersona().getFechaCaducidadNif() != null && !tramiteTraficoMatriculacionBean.getTitularBean().getPersona().getFechaCaducidadNif().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoMatriculacionBean.getTitularBean().getPersona().getFechaCaducidadNif())) {
				resultadoValidacion = "La fecha de caducidad del NIF que ha introducido para el titular no es válida";
				break;
			}
			if(tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona().getFechaCaducidadNif() != null && !tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona().getFechaCaducidadNif().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona().getFechaCaducidadNif())) {
				resultadoValidacion = "La fecha de caducidad del NIF que ha introducido para el representante del titular no es válida";
				break;
			}
			if(!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv())) {
					resultadoValidacion = "La fecha de caducidad ITV del vehículo no es válida";
					break;
			}
			if(!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getFechaIedtm().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getFechaIedtm())) {
				resultadoValidacion = "La fecha de inicio de la exención no es válida";
				break;
			}
			if (tramiteTraficoMatriculacionBean.getFechaPago576() != null
					&& !utilesFecha.validarFormatoFecha(tramiteTraficoMatriculacionBean.getFechaPago576())) {
				resultadoValidacion = "La fecha de pago no es válida";
				break;
			}
			break;
		} while(true);

		return resultadoValidacion;
	}

	public void crearSolicitudEnColaEItv(BigDecimal numExpediente,BigDecimal usuario) throws CrearSolicitudExcepcion {
		getModeloSolicitud().crearSolicitudExcep(numExpediente, usuario, TipoTramiteTrafico.Matriculacion, ConstantesProcesos.PROCESO_CONSULTAEITV);
	}

	public void crearSolicitudEnColaEItvPrematriculados(BigDecimal id,BigDecimal usuario) throws CrearSolicitudExcepcion {
		getModeloSolicitud().crearSolicitudExcep(id, usuario, TipoTramiteTrafico.Matriculacion, ConstantesProcesos.VEHICULO_PREMATICULADO_DATOS_EITV);
	}

	public void crearSolicitudEnColaWsInfoEitv(BigDecimal numExpediente,BigDecimal usuario) throws CrearSolicitudExcepcion {
		getModeloSolicitud().crearSolicitud(
				numExpediente, usuario,TipoTramiteTrafico.Matriculacion, ConstantesProcesos.PROCESO_INFO_WS,ConstantesProcesos.PROCESO_CONSULTAEITV);
	}

	private ResultBean firmarHsm(String cadenaXML,String alias) {
		UtilesViafirma utilesViafirma=new UtilesViafirma();
		ResultBean resultBean = new ResultBean();

		String xmlFirmado= utilesViafirma.firmarMensajeMatriculacionServidor_MATW(cadenaXML,alias);

		if(xmlFirmado.equals(ERROR)) {
			// Ha fallado el proceso de firma en servidor
			log.error(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
			resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
			resultBean.setError(true);
		} else{//firma correctamente
			resultBean.setError(false);
			resultBean.setMensaje(xmlFirmado);
		}
		return resultBean;
	}

	public ResultBean generaXMLMatriculacionNuevo(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) throws InternalException, UnsupportedEncodingException {
		ResultBean resultBeanExportar = new ResultBean();
		XStream xstream = new XStream();
		try {
		String xml = xstream.toXML(traficoTramiteMatriculacionBean);

		byte[] xmlByte = firmarXml(xml, traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumColegiado());

		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(xmlByte);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.NO_TELEMATICO);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.MATE);
		ficheroBean.setNombreDocumento(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente()));

		gestorDocumentos.guardarFichero(ficheroBean);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero de custodia para una matriculacion.",e, traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
			resultBeanExportar.setMensaje("Error al guardar el fichero de custodia para una matriculacion");
			resultBeanExportar.setError(true);
		} catch (Exception e) {
			log.error("Error al guardar el fichero de custodia para una matriculacion.",e, traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
			resultBeanExportar.setMensaje("Se ha producido un error al comprobar la caducidad del certificado");
			resultBeanExportar.setError(true);
		}
		return resultBeanExportar;
	}

	private byte[] firmarXml(String xml, String numColegiado) throws InternalException, UnsupportedEncodingException,Exception {
		byte[] Afirmar = new String (xml.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");

		String aliasColegiado= utilesColegiado.getAlias(numColegiado);
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		if(utilesViafirma.firmarPruebaCertificadoCaducidad(xml, aliasColegiado)==null){
			throw new Exception("Se ha producido un error al comprobar la caducidad del certificado");
		}

		return utilesViafirma.firmarXMLTrafico(Afirmar, aliasColegiado);
	}

	/**
	 * 
	 * @param expediente
	 * @param matricula
	 * @throws GuardarMatriculacionExcepcion
	 */

	public void guardarMatriculacion(BigDecimal expediente,String matricula) throws GuardarMatriculacionExcepcion{
		BeanPQMatricular beanPQMatricular = new BeanPQMatricular();
		//beanPQMatricular.setP_NUM_EXPEDIENTE(new BigDecimal(respuestaMatriculacion.getNumeroExpedienteColegio()));
		beanPQMatricular.setP_NUM_EXPEDIENTE(expediente);
		beanPQMatricular.setP_MATRICULA(matricula);
		beanPQMatricular.setP_FECHA_MATRICULACION(utilesFecha.getTimestampActual());
		beanPQMatricular.setP_FECHA_PRESENTACION(utilesFecha.getTimestampActual());
		beanPQMatricular.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()));
		ResultBean resultBeanMatricular = (ResultBean) matricular(beanPQMatricular);
		if (resultBeanMatricular.getError())
			throw new GuardarMatriculacionExcepcion(resultBeanMatricular.getMensaje());
	}

	private ResultBean realizarFirmaDatosFirmadosHsm(SolicitudRegistroEntrada solicitudRegistroEntrada,String alias){
		XMLMatwFactory xmlMatwFactory = new XMLMatwFactory();
		String xml = xmlMatwFactory.toXML(solicitudRegistroEntrada);

		return firmarHsm(xml, alias);
	}

	private boolean comprobarCaducidadCertificado(String idUsuario) {
		boolean esOk = false;
		String numColegiado = utilesColegiado.getNumColegiadoSession();
		String aliasColegiado = utilesColegiado.getAlias(numColegiado);
		SolicitudPruebaCertificado solicitudPruebaCertificado = obtenerSolicitudPruebaCertificado(aliasColegiado);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);

		UtilesViafirma utilesViafirma = new UtilesViafirma();
		String idFirma= utilesViafirma.firmarPruebaCertificadoCaducidad(xml,aliasColegiado);
		if(idFirma != null){
			esOk = true;
		}
		return esOk;
	}

	private ResultBean anhadirFirmasHsm(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		ResultBean resultFirmasBean=new ResultBean();

		/**
		 * Firma del XML por el Colegio
		 */

		String aliasColegio= gestorPropiedades.valorPropertie("trafico.claves.colegiado.aliasHsm");

		ResultBean resultFirmaColegioHsm = realizarFirmaDatosFirmadosHsm(solicitudRegistroEntrada, aliasColegio);
		String xmlFirmadoColegio = resultFirmaColegioHsm.getMensaje();

		log.debug("XML firmado en servidor:" + xmlFirmadoColegio);

		if(!resultFirmaColegioHsm.getError()) {
			try{
				resultFirmasBean.setError(false);
				resultFirmasBean.setMensaje(xmlFirmadoColegio);
			}catch(Exception e){
				log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
			}
		}else{
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_EL_COLEGIO);
		}
		return resultFirmasBean;
	}

	public ResultBean tramitacion(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, BigDecimal idUsuario, BigDecimal idContrato) throws Throwable {
		if (!comprobarCreditos(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getIdContrato())){
			ResultBean resultBean = new ResultBean();
			resultBean.setMensaje("No tiene suficientes créditos");
			resultBean.setError(true);
			return resultBean;
		}

		boolean esCertColegiadoCorrecto = comprobarCaducidadCertificado(utilesColegiado.getIdUsuarioSessionBigDecimal().toString());
		ResultBean resultBeanExportar = new ResultBean();
		if(esCertColegiadoCorrecto){
			if("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.matw"))){
				resultBeanExportar = getModeloMatriculacionSega().tramitarSega(traficoTramiteMatriculacionBean,idUsuario,idContrato);
				if(!resultBeanExportar.getError()) {
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum());
				}
			} else {
				//Obtenemos el elemento raíz del xml a través del trámite
				SolicitudRegistroEntrada solicitudRegistroEntrada = obtenerSolicitudRegistroEntrada(traficoTramiteMatriculacionBean);
				//almacenará el xml firmado o el mensaje de error
				ResultBean resultadoFirmasBean = anhadirFirmasHsm(solicitudRegistroEntrada);

				/**
				 * Se obtiene la petición completa que se envía a MATW
				 */
				if (!resultadoFirmasBean.getError()){
					resultBeanExportar= completarSolicitud(solicitudRegistroEntrada, resultadoFirmasBean.getMensaje());
					if (!resultBeanExportar.getError()){
						ResultBean resSolcicitud = crearSolicitud(solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getNumeroExpedienteColegio(), traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum());
						traficoTramiteMatriculacionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum());
						resultBeanExportar.setMensaje(resSolcicitud.getMensaje());
						resultBeanExportar.setError(resSolcicitud.getError());
					}
				}else{
					resultBeanExportar.setMensaje(resultadoFirmasBean.getMensaje());
					resultBeanExportar.setError(true);
				}
			}
		}else{
			resultBeanExportar.setMensaje("El certificado del colegiado de session contiene un error o esta caducado.");
			resultBeanExportar.setError(true);
		}

		return resultBeanExportar;
	}

	public ResultBean completarSolicitud(SolicitudRegistroEntrada solicitudRegistroEntrada, String firmado) throws Throwable {
		ResultBean resultSolicitudCompleta = new ResultBean();

		FicheroBean ficheroBean = new FicheroBean();

		try{
			String nombre = ConstantesProcesos.PROCESO_MATW+solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.ENVIO);
			ficheroBean.setSobreescribir(true);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setNombreDocumento(nombre);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente()));

			gestorDocumentos.crearFicheroXml(ficheroBean, XMLMatwFactory.NAME_CONTEXT, solicitudRegistroEntrada, firmado, null);
		}catch (OegamExcepcion oe){
			resultSolicitudCompleta.setError(true);
			resultSolicitudCompleta.setMensaje(oe.getMessage());
			return resultSolicitudCompleta;
		}

		String xmlResultadoValidar = new XMLMatwFactory().validarXMLMATW(ficheroBean.getFichero());

		if (xmlResultadoValidar.equals(XML_VALIDO)) {
			resultSolicitudCompleta.setError(false);
			resultSolicitudCompleta.setMensaje(solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente());
		}else {
			resultSolicitudCompleta.setError(true);
			resultSolicitudCompleta.setMensaje(" La petición no es válida debido al siguiente error: " + xmlResultadoValidar);
			try{
				log.error("Error validación XML:" + xmlResultadoValidar +" del expediente: " + solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente());
			}catch (Exception e) {
				log.error("Excepcion al sacar el numExpediente del xml por error de validación. Detalle: " + e.getMessage());
				log.error("Error validación XML:" + xmlResultadoValidar);
			}
		}

		return resultSolicitudCompleta;
	}

	public ResultBean crearSolicitud(String numExpediente, String estadoAnterior) {
		log.info("Se va a agregar en la cola el trámite de MATW:" + numExpediente + " para el Usuario:" +utilesColegiado.getIdUsuarioSessionBigDecimal());
		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud();
		beanPQCrearSolicitud.setP_ID_TRAMITE(new BigDecimal(numExpediente));
		beanPQCrearSolicitud.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(TipoTramiteTrafico.Matriculacion.getValorEnum());
		beanPQCrearSolicitud.setP_XML_ENVIAR(ProcesosEnum.MATW.getNombreEnum()+numExpediente+ XML);
		beanPQCrearSolicitud.setP_PROCESO(ProcesosEnum.MATW.getNombreEnum());

		// DRC@26-03-2013 Incidencia Mantis: 3853
		BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
		beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
		beanPQCambiarEstadoTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(new BigDecimal(numExpediente));
		HashMap<String,Object> resultadoCambiarEstado = getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite);
		ResultBean resultBeanCambiarEstado = (ResultBean) resultadoCambiarEstado.get(ConstantesPQ.RESULTBEAN);

		if (resultBeanCambiarEstado.getError()==false) {
			resultBeanCambiarEstado.setMensaje(TRAMITE_FIRMADO_Y_PENDIENTE_DE_ENVÍO);
			HashMap<String,Object> resultadoSolicitud = getModeloSolicitud().crearSolicitud(beanPQCrearSolicitud);
			ResultBean resultBean = (ResultBean) resultadoSolicitud.get(ConstantesPQ.RESULTBEAN);

			if (resultBean.getError()==false) {
				log.info("Se agregó correctamente en la cola el trámite de MATW:" + numExpediente + " para el Usuario:" +utilesColegiado.getIdUsuarioSessionBigDecimal());
				//resultBean.setMensaje(SOLICITUD_CREADA_CORRECTAMENTE);
				resultBeanCambiarEstado.setMensaje(SOLICITUD_CREADA_CORRECTAMENTE);
			} else { // Si la solicitud no se ha creado correctamente.
				try {
					TramiteTraficoBean tramiteTrafico = new TramiteTraficoBean();
					tramiteTrafico.setEstado(estadoAnterior);
					tramiteTrafico.setNumExpediente(new BigDecimal(numExpediente));
					BeanPQCAMBIAR_ESTADO_S_V beanCambEstado = UtilesConversionesTrafico.convertirTramiteTraficoABeanPQCAMB_ESTADO_S_V(tramiteTrafico);
					beanCambEstado.setP_ESTADO(new BigDecimal(estadoAnterior));
					beanCambEstado.execute();
					if(!beanCambEstado.getP_CODE().toString().equals("0")){
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
	// Comprobar caducidades nifs intervinientes
	public ResultBean comprobarCaducidadNifs(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean, boolean validacion) throws ParseException{
		ResultBean resultado = new ResultBean(false);
		Fecha fechaActual = utilesFecha.getFechaActual();
		java.util.Date fechaAdvertencia = utilesFecha.getDate((utilesFecha.sumaDias(fechaActual, "-30")));
		List<TipoInterviniente> listaIntervinientes = new ArrayList<>();
		listaIntervinientes.add(TipoInterviniente.Titular);
		listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
		listaIntervinientes.add(TipoInterviniente.RepresentanteTitular);
		Persona persona = null;

		for (TipoInterviniente interviniente : listaIntervinientes) {

			switch(interviniente){

				case Titular:
					if(tramiteTraficoMatriculacionBean!=null
					&& tramiteTraficoMatriculacionBean.getTitularBean()!=null
					&& tramiteTraficoMatriculacionBean.getTitularBean().getPersona()!=null){
						persona = tramiteTraficoMatriculacionBean.getTitularBean().getPersona();
					}
					break;
				case ConductorHabitual:
					if(tramiteTraficoMatriculacionBean!=null
					&& tramiteTraficoMatriculacionBean.getConductorHabitualBean()!=null
					&& tramiteTraficoMatriculacionBean.getConductorHabitualBean().getPersona()!=null){
						persona = tramiteTraficoMatriculacionBean.getConductorHabitualBean().getPersona();
					}
					break;
				case RepresentanteTitular:
					if(tramiteTraficoMatriculacionBean!=null
					&& tramiteTraficoMatriculacionBean.getRepresentanteTitularBean()!=null
					&& tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona()!=null){
						persona = tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona();
					}
					break;
				default:
					persona = null;
					break;
			}
			ResultBean resultValNif = validarCaducidadNif(persona,
					validacion, interviniente,fechaActual,fechaAdvertencia);
			if(!resultado.getError() && resultValNif.getError()){
				resultado.setError(true);
			}
			if(resultValNif.getListaMensajes() != null && !resultValNif.getListaMensajes().isEmpty()){
				for(String mensaje : resultValNif.getListaMensajes()){
					resultado.addMensajeALista(mensaje);
				}
			}
		}
		return resultado;
	}

	private ResultBean validarCaducidadNif(Persona persona, boolean validacion, TipoInterviniente interviniente, Fecha fechaActual, Date fechaAdvertencia) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(persona != null && persona.getFechaCaducidadNif()!=null && persona.getFechaCaducidadNif().getDate()!=null) {
				if(!validacion && persona.getFechaCaducidadNif().getDate().after(fechaActual.getDate()) &&
						persona.getFechaCaducidadNif().getDate().before(fechaAdvertencia)){
					resultado.addMensajeALista("NIF del " + interviniente.getNombreEnum() + " próximo a caducar");
					if(persona.getCodigoMandato()!=null){
						resultado.addMensajeALista("Recuerde que esta persona tiene asociado el código de mandato: " + persona.getCodigoMandato());
					}
				} else if (persona.getFechaCaducidadNif().getDate().before(fechaActual.getDate())) {
					resultado.addMensajeALista("NIF del " + interviniente.getNombreEnum() + " caducado");
					resultado.setError(true);
				}
				if(resultado.getError()){
					if(persona.isOtroDocumentoIdentidad()){
						if(persona.getFechaCaducidadAlternativo() == null || persona.getFechaCaducidadAlternativo().isfechaNula()){
							resultado.setError(true);
						} else {
							resultado.setError(false);
						}
					}else{
						resultado.setError(true);
					}
				}
			}
		} catch (ParseException e) {
			log.error("Ha sucedido un error a la hora de validar la caducidad del NIF, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar la caducidad del NIF del " + interviniente.getNombreEnum());
		}
		return resultado;
	}

		// Mantis 19172 y 21598. David Sierra: Validar fecha nacimiento de los intervinientes
		public String comprobarFormatoFechas(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) throws ParseException{
			String resultado = "";
			String resultadoValidacionFecha;
			List<TipoInterviniente> listaIntervinientes = new ArrayList<TipoInterviniente>();
			listaIntervinientes.add(TipoInterviniente.Titular);
			listaIntervinientes.add(TipoInterviniente.ConductorHabitual);
			listaIntervinientes.add(TipoInterviniente.RepresentanteTitular);
			listaIntervinientes.add(TipoInterviniente.Arrendatario);
			Persona persona = null;
			for (TipoInterviniente interviniente : listaIntervinientes) {
				switch(interviniente){
					case Titular:
						if(tramiteTraficoMatriculacionBean!=null
						&& tramiteTraficoMatriculacionBean.getTitularBean()!=null
						&& tramiteTraficoMatriculacionBean.getTitularBean().getPersona()!=null){
							persona = tramiteTraficoMatriculacionBean.getTitularBean().getPersona();
						}
						break;
					case ConductorHabitual:
						if(tramiteTraficoMatriculacionBean!=null
						&& tramiteTraficoMatriculacionBean.getConductorHabitualBean()!=null
						&& tramiteTraficoMatriculacionBean.getConductorHabitualBean().getPersona()!=null){
							persona = tramiteTraficoMatriculacionBean.getConductorHabitualBean().getPersona();
						}
						break;
					case RepresentanteTitular:
						if(tramiteTraficoMatriculacionBean!=null
						&& tramiteTraficoMatriculacionBean.getRepresentanteTitularBean()!=null
						&& tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona()!=null){
							persona = tramiteTraficoMatriculacionBean.getRepresentanteTitularBean().getPersona();
						}
						break;
					case Arrendatario:
						if(tramiteTraficoMatriculacionBean!=null
						&& tramiteTraficoMatriculacionBean.getArrendatarioBean()!=null
						&& tramiteTraficoMatriculacionBean.getArrendatarioBean().getPersona()!=null){
							persona = tramiteTraficoMatriculacionBean.getArrendatarioBean().getPersona();
						}
						break;
					default:
						persona = null;
						break;
				}
				resultadoValidacionFecha = "";
				if (persona != null && persona.getFechaNacimientoBean() != null && persona.getFechaNacimientoBean().getDia() != null
					&& persona.getFechaNacimientoBean().getMes() != null && persona.getFechaNacimientoBean().getAnio() != null) {
					String dia = persona.getFechaNacimientoBean().getDia();
					String mes = persona.getFechaNacimientoBean().getMes();
					String anio = persona.getFechaNacimientoBean().getAnio();
					int diaNum = 0;
					int mesNum = 0;
					if(!dia.isEmpty()) {
						diaNum = Integer.parseInt(dia);
						if (diaNum < 0 || diaNum > 31) {
							resultadoValidacionFecha = "El formato de la fecha de nacimiento del " + interviniente.getNombreEnum()
							+ " no es válida. El día debe estar comprendido entre el 1 y el 31";
						}
					}
					if(!mes.isEmpty()) {
						mesNum = Integer.parseInt(mes);
						if (mesNum < 1 || mesNum > 12) {
							resultadoValidacionFecha = "El formato de la fecha de nacimiento del " + interviniente.getNombreEnum()
							+ " no es válida. El mes debe estar comprendido entre el 1 y el 12";
						}
					}
					if(!anio.isEmpty() && anio.length() != 4) {
						resultadoValidacionFecha = "El formato de la fecha de nacimiento del " + interviniente.getNombreEnum()
						+ " no es válida. El año debe tener 4 dígitos.";
					}
				}
				if (resultadoValidacionFecha != null && !resultadoValidacionFecha.equals("")) {
					resultado += "\n" + resultadoValidacionFecha;
				}
			}
			return resultado;
		}

	private SolicitudPruebaCertificado obtenerSolicitudPruebaCertificado(String alias){
		SolicitudPruebaCertificado solicitudPruebaCertificado = null;
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();

		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();

		DatosFirmados datosFirmados= objectFactory.createDatosFirmados();
		datosFirmados.setAlias(alias);

		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);

		return solicitudPruebaCertificado;
	}

	private SolicitudRegistroEntrada obtenerSolicitudRegistroEntrada(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		SolicitudRegistroEntrada solicitudRegistroEntrada=null;

		trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory objFactory = new trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory();

		Persona colegiadoCompleto = getModeloColegiado().obtenerColegiadoCompleto(utilesColegiado.getIdContratoSessionBigDecimal());
		// Obtenemos datos del colegio
		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio.setP_COLEGIO(utilesColegiado.getColegioDelContrato());
		ColegioBean colegioBean = (ColegioBean) getModeloTrafico().obtenerDatosColegio(beanPQDatosColegio).get(ConstantesPQ.BEANPANTALLA);

		//-----------------ELEMENTO RAIZ----------------------
		solicitudRegistroEntrada= (SolicitudRegistroEntrada) objFactory.createSolicitudRegistroEntrada();

		solicitudRegistroEntrada.setDatosFirmados(objFactory.createDatosFirmados());
		solicitudRegistroEntrada.setVersion(VERSION_REGISTRO_ENTRADA);
		//--------------------------------------------------DATOS FIRMADOS----------------------------------------------------------------------------

		DatosEspecificos datosEspecificos = objFactory.createDatosEspecificos();
		Documentos documentos = objFactory.createDocumentos();
		DatosGenericos datosGenericos = objFactory.createDatosGenericos();

		solicitudRegistroEntrada.getDatosFirmados().setDocumentos(documentos);
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(datosGenericos);
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(datosEspecificos);

		//	----------------------------------------------------DOCUMENTOS------------------------------------------

		//--------------------------------------------------DATOS GENERICOS---------------------------------------------------------------------------

		//TIPO DATO ASUNTO:
		TipoDatoAsuntoMATG tipoDatoAsunto = new TipoDatoAsuntoMATG();
		tipoDatoAsunto.setCodigo(CODIGO_ASUNTO);
		tipoDatoAsunto.setDescripcion(DESCRIPCION_ASUNTO);
		//TIPO DATO DESTINO
		TipoDatoDestino tipoDatoDestino = new TipoDatoDestino();
		tipoDatoDestino.setCodigo(CODIGO_DESTINO);
		tipoDatoDestino.setDescripcion(DESCRIPCION_DESTINO);
		//TIPO DATO COLEGIO
		TipoDatoColegio tipoDatoColegioRemitente = new TipoDatoColegio();
		tipoDatoColegioRemitente.setApellidos("");
		/* INICIO Mantis 0011046: (ihgl) se incluye el correo electrónico del colegio para alinear el XML con el de CTIT */
		String correoElectronico = (colegioBean.getCorreoElectronico() != null) ? colegioBean.getCorreoElectronico() : "";
		tipoDatoColegioRemitente.setCorreoElectronico(correoElectronico);
		/* FIN Mantis 0011046 (ihgl) */
		tipoDatoColegioRemitente.setNombre(colegioBean.getNombre());
		DocumentoIdentificacion documentoIdentificacion = new DocumentoIdentificacion();
		documentoIdentificacion.setNumero(colegioBean.getCif());
		tipoDatoColegioRemitente.setDocumentoIdentificacion(documentoIdentificacion);

		// Datos genéricos
		datosGenericos.setAsunto(tipoDatoAsunto);
		datosGenericos.setDestino(tipoDatoDestino);

		//INTERESADOS
		Interesados interesados = objFactory.createDatosGenericosInteresados();
		datosGenericos.setInteresados(interesados);
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion();
		tipoDocumentoIdentificacion.setNumero(colegiadoCompleto.getNif());

		//TIPODATOGESTORIA -> para interesados
		TipoDatoGestoria tipoDatoGestoria = new TipoDatoGestoria();
		tipoDatoGestoria.setCorreoElectronico(colegiadoCompleto.getCorreoElectronico());
		tipoDatoGestoria.setDocumentoIdentificacion(tipoDocumentoIdentificacion);

		String nombreUsuario = colegiadoCompleto.getNombre();

		if(nombreUsuario.length() > _18){
			nombreUsuario = nombreUsuario.substring(0, _18);
		}
		tipoDatoGestoria.setNombre(nombreUsuario);

		String apellidosUsuario = colegiadoCompleto.getApellido1RazonSocial();
		if(null != apellidosUsuario && !apellidosUsuario.equals("")){
			apellidosUsuario += " " + colegiadoCompleto.getApellido2();
			if(nombreUsuario.length() > _45 ){
				apellidosUsuario = apellidosUsuario.substring(0, _45);
			}
			tipoDatoGestoria.setApellidos(apellidosUsuario);
		}

		interesados.setInteresado(tipoDatoGestoria);

		//COMPLETAMOS DATOS GENÉRICOS
		datosGenericos.setOrganismo(ORGANISMO);
		datosGenericos.setInteresados(interesados);
		datosGenericos.setNumeroExpediente(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
		datosGenericos.setRemitente(tipoDatoColegioRemitente);

		//--------------------------------------------------DATOS ESPECÍFICOS--------------------------------------------------------------------------

		Titular titular = objFactory.createDatosEspecificosTitular();
		Arrendatario arrendatario = objFactory.createDatosEspecificosArrendatario();
		ConductorHabitual conductorHabitual = objFactory.createDatosEspecificosConductorHabitual();
		DatosVehiculo datosVehiculo = objFactory.createDatosEspecificosDatosVehiculo();
		Tutor tutor = objFactory.createDatosEspecificosTutor();

		TipoDatoDireccion tipoDatoDireccionVehiculo = new TipoDatoDireccion();
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean() != null
				&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean().getIdDireccion() != null){
			tipoDatoDireccionVehiculo = beanToXmldireccion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean());
			datosEspecificos.setDomicilioVehiculo(tipoDatoDireccionVehiculo);
		}else{
			// Rellena la dirección del vehículo con la dirección del titular si no hay datos específicos de dirección del vehículo:
			tipoDatoDireccionVehiculo = beanToXmldireccion(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion());
			datosEspecificos.setDomicilioVehiculo(tipoDatoDireccionVehiculo);
		}

		//	datosEspecificos.setDatosTecnicos(completaDatosTecnicos(traficoTramiteMatriculacionBean));

		//----------------NUM EXPEDIENTE COLEGIO
		datosEspecificos.setNumeroExpedienteColegio(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());

		//----- IMPUESTOS

		//IMPUESTOS
		Impuestos impuestos = objFactory.createDatosEspecificosImpuestos();
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm() != null &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm().equals("")){
			impuestos.setCEM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm());
		}
		impuestos.setCEMA(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCema());
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm() != null && 
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum() != null &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum().equals("")){
			impuestos.setExencionCEM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum());
		}

		//JMC CEMU desaparece de la Primera Version de MATW

		//impuestos.setCEMU(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemu());

		if(traficoTramiteMatriculacionBean.getJustificado_IVTM() != null && traficoTramiteMatriculacionBean.getJustificado_IVTM().equalsIgnoreCase("true")){
			impuestos.setJustificadoIVTM(TipoSN.S);
		}else if(traficoTramiteMatriculacionBean.getJustificado_IVTM() != null && traficoTramiteMatriculacionBean.getJustificado_IVTM().equalsIgnoreCase("false")){
			impuestos.setJustificadoIVTM(TipoSN.N);
		}

		datosEspecificos.setImpuestos(impuestos);

		//--------------VERSION-------------------
		datosEspecificos.setVersion(VERSION);

		//-----------------------------------------

		datosEspecificos.setTasa(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getTasa().getCodigoTasa());

		JefaturaTraficoVO jefatura = getModeloTrafico().obtenerJefaturaCompleta(traficoTramiteMatriculacionBean.getTramiteTraficoBean().
				getJefaturaTrafico().getJefaturaProvincial());

		String sucursal = jefatura.getSucursal();
		if (!"1".equals(sucursal)) {
			String activarSucursal = gestorPropiedades.valorPropertie("activar.sucursal.alcala.matw.xml");
			if ("2".equals(sucursal) && "SI".equals(activarSucursal)) {
				datosEspecificos.setSucursal(sucursal);
			} else {
				datosEspecificos.setSucursal("");
			}
		} else {
			datosEspecificos.setSucursal(sucursal);
		}

		datosEspecificos.setJefatura(jefatura.getJefatura());

		//JMC id_matricula_anotador Desaparece la Primera version de Matw. No estaba en MATE 2.5
		//datosEspecificos.setIdMatriculaAnotador(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());

		datosEspecificos.setTipoTramite(traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion().getValorEnum());

		//---------------TITULAR---------------------

		//DOMICILIO -> tipoCambioDomicilio
		TipoCambioDomicilio tipoCambioDomicilioTitular = new TipoCambioDomicilio();

		if (traficoTramiteMatriculacionBean.getTitularBean().getCambioDomicilio()!=null &&
				!("").equals(traficoTramiteMatriculacionBean.getTitularBean().getCambioDomicilio())) {
			if (traficoTramiteMatriculacionBean.getTitularBean().getCambioDomicilio().equalsIgnoreCase("true")){
				tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.S.value());
			}else{ 
				tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.N.value());
			}
			tipoCambioDomicilioTitular.setDatosDomicilio(beanToXmldireccion(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion()));
		}
		else tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.N.value());

		titular.setDomicilioTitular(tipoCambioDomicilioTitular);

		//TIPO DATO TITULAR -> Identificacion
		TipoDatoTitular tipoDatoTitular = beanToXmlTipoDatoTitular(traficoTramiteMatriculacionBean.getTitularBean());
		if(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif() != null &&
				!"".equals(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif())){
			// Setea el representante si no se trata de tutor. Si lo fuera irán sus datos en el elemento <Tutor>
			if(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela() == null){
				TipoDocumentoIdentificacion tipoDocumentoIdentificacionRepresentante = new TipoDocumentoIdentificacion();
				tipoDocumentoIdentificacionRepresentante.setNumero(
					traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif());
				tipoDatoTitular.setDocumentoIdentidadRepresentante(tipoDocumentoIdentificacionRepresentante);
			}
		}
		titular.setIdentificacion(tipoDatoTitular);
		//SERVICIO AUTONOMO
		DatosEspecificos.Titular.ServicioAutonomo servicioAutonomo = objFactory.createDatosEspecificosTitularServicioAutonomo();

		if (null != traficoTramiteMatriculacionBean.getTitularBean().getCodigoIAE()
				&& !"".equals(traficoTramiteMatriculacionBean.getTitularBean().getCodigoIAE())) {
			servicioAutonomo.setAutonomo(TipoSN.S.value());
			servicioAutonomo.setCodigoIAE(new BigInteger(traficoTramiteMatriculacionBean.getTitularBean().getCodigoIAE()));
		} else {
			servicioAutonomo.setAutonomo(TipoSN.N.value());
			servicioAutonomo.setCodigoIAE(null);
		}
		titular.setServicioAutonomo(servicioAutonomo);

		// Rellenamos titular
		solicitudRegistroEntrada.
		getDatosFirmados().
		getDatosEspecificos().
		setTitular(titular);

		//------------------ARRENDATARIO-----------------

		if (traficoTramiteMatriculacionBean.getArrendatarioBean()!=null)
			if (traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif()!=null &&
			!traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif().equals("")) {

				//DOMICILIO -> tipoCambioDomicilio
				TipoCambioDomicilio tipoCambioDomicilioArrendatario = new TipoCambioDomicilio();

				if (traficoTramiteMatriculacionBean.getArrendatarioBean().getCambioDomicilio()!=null &&
						!("".equals(traficoTramiteMatriculacionBean.getArrendatarioBean().getCambioDomicilio()))) {
					if (traficoTramiteMatriculacionBean.getArrendatarioBean().getCambioDomicilio().equalsIgnoreCase("true")){
						tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.S.value());
					}else{ 
						tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.N.value());
					}
					tipoCambioDomicilioArrendatario.setDatosDomicilio(beanToXmldireccion(traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getDireccion()));
				} else tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.N.value());

				arrendatario.setDomicilioArrendatario(tipoCambioDomicilioArrendatario);

				Fecha fechaInicioArrendatario = traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaInicio();
				if(fechaInicioArrendatario != null && !fechaInicioArrendatario.isfechaNula()){
					arrendatario.setFechaInicio(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaInicio()));
				}

				Fecha fechaFinArrendatario = traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaFin();
				if (fechaFinArrendatario != null && !fechaFinArrendatario.isfechaNula()){
					arrendatario.setFechaFin(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaFin()));
				}

				if(traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraInicio()!=null) {
					if(!traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraInicio().equals("")) {
						String hora = (traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraInicio());
						hora = hora.replace(":", "");
						arrendatario.setHoraInicio(new BigInteger(hora));
					}
				} else {
					arrendatario.setHoraInicio(null);
				}

				if(traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraFin()!=null) {
					if(!traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraFin().equals("")) {
						String hora = traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraFin();
						hora = hora.replace(":", "");
						arrendatario.setHoraFin(new BigInteger(hora));
					}
				} else {
					arrendatario.setHoraFin(null);
				}

				//TIPO DATO ARRENDATARIO -> Identificacion
				TipoDatoTitular tipoDatoArrendatario = beanToXmlTipoDatoTitular(traficoTramiteMatriculacionBean.getArrendatarioBean());
				if(traficoTramiteMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getNif() != null &&
						!traficoTramiteMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getNif().equals("")){
					TipoDocumentoIdentificacion tipoDocumentoIdentificacionRepresentante = new TipoDocumentoIdentificacion();
					tipoDocumentoIdentificacionRepresentante.setNumero(
							traficoTramiteMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getNif());
					tipoDatoArrendatario.setDocumentoIdentidadRepresentante(tipoDocumentoIdentificacionRepresentante);
				}
				arrendatario.setIdentificacion(tipoDatoArrendatario);

				//SERVICIO AUTÓNOMO
				// No tiene

				// Rellenamos arrendatario

				if (traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif()==null ||
						!traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif().equals(""))
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setArrendatario(arrendatario);
			}else {
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setArrendatario(null);
			}

		//--------------------REPRESENTANTE-TUTOR----------------------

		if (traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela()!=null)
			tutor = completaDatosTutor(tutor,traficoTramiteMatriculacionBean);
		else tutor = null;

		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTutor(tutor);

		//------------------CONDUCTOR HABITUAL----------

		if (traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif()!=null &&
				!traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif().equals("")) {
			TipoDatoConductor tipoDatoConductor = new TipoDatoConductor();
			TipoDocumentoIdentificacion tipoDocumentoIdentificacionCH = new TipoDocumentoIdentificacion();
			tipoDocumentoIdentificacionCH.setNumero(traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif());
			tipoDatoConductor.setDocumentoIdentidad(tipoDocumentoIdentificacionCH);
			conductorHabitual.setIdentificacion(tipoDatoConductor);

			//DOMICILIO -> tipoCambioDomicilio
			TipoCambioDomicilio tipoCambioDomicilioCH = new TipoCambioDomicilio();

			if (traficoTramiteMatriculacionBean.getConductorHabitualBean().getCambioDomicilio()!=null &&
					!("".equals(traficoTramiteMatriculacionBean.getConductorHabitualBean().getCambioDomicilio()))) {
				if (traficoTramiteMatriculacionBean.getConductorHabitualBean().getCambioDomicilio().equalsIgnoreCase("true")){
					tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.S.value());
				}else{
					tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.N.value());
				}
				tipoCambioDomicilioCH.setDatosDomicilio(beanToXmldireccion(traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getDireccion()));
			} else tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.N.value());

			conductorHabitual.setDomicilioConductor(tipoCambioDomicilioCH);

			if(traficoTramiteMatriculacionBean.getConductorHabitualBean().getHoraFin()!=null){
				if(!traficoTramiteMatriculacionBean.getConductorHabitualBean().getHoraFin().equals("")){
					String hora = traficoTramiteMatriculacionBean.getConductorHabitualBean().getHoraFin();
					hora = hora.replace(":", "");
					conductorHabitual.setHoraFin(new BigInteger(hora));
				}
			}else {
				conductorHabitual.setHoraFin(null);
			}

			if(traficoTramiteMatriculacionBean.getConductorHabitualBean().getFechaFin() != null &&
					!traficoTramiteMatriculacionBean.getConductorHabitualBean().getFechaFin().isfechaNula()){
				conductorHabitual.setFechaFin(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getConductorHabitualBean().getFechaFin()));
			}
		} else conductorHabitual = null;

		// Rellenamos conductor habitual
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setConductorHabitual(conductorHabitual);

		//----------------DATOS VEHÍCULO---------------

		datosVehiculo.setBastidor(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor());
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getSubasta() !=null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getSubasta()){
			datosVehiculo.setSubasta(TipoSN.S.value());
		}else{
			datosVehiculo.setSubasta(TipoSN.N.value());
		}

		//JMB - Cambios MATW
		//datosVehiculo.setCuentaHoras(null);

		//JMC - Desaparece Diplomatica en la última versión de Matw
		//datosVehiculo.setDiplomatica("");
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean() != null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo() != null){

			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo().equalsIgnoreCase("M")){
				datosVehiculo.setTipoInspeccionItv(TipoInspeccion.M.value());
			}
			// Mantis 15845. David Sierra: Se ha agregado a tipoInspeccionItv E: Exento ITV.
			else if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo().equalsIgnoreCase("E")){
				datosVehiculo.setTipoInspeccionItv(TipoInspeccion.E.value());
			}
		}

		Fecha fechaValidezItv = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv();
		if(fechaValidezItv != null && !fechaValidezItv.isfechaNula()){
			datosVehiculo.setFechaValidezITV(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv()));
		}
		// Mantis 15845. Mantis 15845. David Sierra: Si tipoInspeccionItv es Exento se vacía fechaValidezItv
		if(fechaValidezItv != null && !fechaValidezItv.isfechaNula() && "E".equals(datosVehiculo.getTipoInspeccionItv())) {
			datosVehiculo.setFechaValidezITV(null);
		}
		// RRR LA ESTACION ITV DESAPARECE DE LA VERSION INICIAL DE MATW
		//datosVehiculo.setEstacionITV(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEstacionItv());

		// DATOS DE VEHÍCULO USADO
		// MATW. Seteo de los datos de vehículo usado solo si en la jsp se marca el check de 'usado'
		// USADO
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado().equalsIgnoreCase("true")){
			datosVehiculo.setUsado(TipoSN.S.value());
		}else{
			datosVehiculo.setUsado(TipoSN.N.value());
		}
		if(datosVehiculo.getUsado() != null && datosVehiculo.getUsado().equals(TipoSN.S.value())){
			// FECHA DE LA PRIMERA MATRICULACIÓN
			Fecha fechaPrimeraMatriculacion = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri();
			if(fechaPrimeraMatriculacion != null && !fechaPrimeraMatriculacion.isfechaNula()){
				datosVehiculo.setFechaPrimeraMatriculacion(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().
						getFechaPrimMatri()));
			}
			// MATRÍCULA ORIGEN
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen()!=null &&
					!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen().equals("")
					&& TipoTramiteMatriculacion.REMATRICULAR.equals(traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion())){
				datosVehiculo.setMatriculaOrigen(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen());
			}
			// KILÓMETROS DE USO
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toString().length()<=6){
				datosVehiculo.setKilometraje(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toBigInteger());
			} else if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toString().length()>6){
				datosVehiculo.setKilometraje(new BigDecimal(999999).toBigInteger());
			}
			// HORAS DE USO
			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso().toString().length()<=6){
				datosVehiculo.setCuentaHoras(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso().toBigInteger());
			} else if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso().toString().length()>6){
				datosVehiculo.setCuentaHoras(new BigDecimal(999999).toBigInteger());
			}
			// IMPORTADO
			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado() != null &&
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado()){
				datosVehiculo.setImportado(TipoSN.S.value());
				// Matrícula Extranjera

				if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null &&
							traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr() != null &&
							!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr().equals("") &&
							 TipoTramiteMatriculacion.REMATRICULAR.equals(traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion())){
					datosVehiculo.setMatriculaExtranjera(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr());
				}
			}else{
				datosVehiculo.setImportado(TipoSN.N.value());
			}
			// PROCEDENCIA. Está dentro de los datos técnicos. ver método 'completaDatosTecnicos('
		}
		// FIN DE DATOS DE VEHÍCULO USADO

		if (null != traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio().equals("") &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio().equals("-1"))
		{
			datosVehiculo.setServicio(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio());
		}

		//Campo nuevo Carsharing, Mantis 22806
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCarsharing() != null
				&& !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCarsharing().isEmpty()){
			if("true".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCarsharing())){
				datosVehiculo.setCarsharing("S");
			}else{
				datosVehiculo.setCarsharing("N");
			}
		}

		//Nuevos Campos Matw

		// Rellenamos vehiculo
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosVehiculo(datosVehiculo);
		// Nuevos Matw NIVE jbc
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive()!=null &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive().equals("")){
			datosVehiculo.setNIVE(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive());
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosTecnicos(new DatosTecnicos());
			//Tipo tarjeta ITV
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null &&
					!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().isEmpty()){
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getDatosTecnicos().setTipoTarjetaItv(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv());
			}
			//Tipo vehículo
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo()!=null &&
					!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().isEmpty()){
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getDatosTecnicos().setTipoVehiculoDGT(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo());
			}
		} else{
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosTecnicos(completaDatosTecnicos(traficoTramiteMatriculacionBean));
		}

		return solicitudRegistroEntrada;
	}

	// Método que setea los datos técnicos del vehículo para el xml de matriculación MATW
	private DatosTecnicos completaDatosTecnicos(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean){

		String valor = null;
		ObjectFactory objFactory = new ObjectFactory();
		DatosTecnicos datosTecnicos = objFactory.createDatosEspecificosDatosTecnicos();

		// Prueba blanco a número homologación si valen null:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacion()==null){
			datosTecnicos.setNumeroHomologacion("");
		}else{
			datosTecnicos.setNumeroHomologacion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacion());
		}

		String codITV= tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv();

		if (!SIN_CODIG.equals(codITV)) {
			datosTecnicos.setCodigoITV(codITV);
		}
		valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion() +
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().getIdCriterioUtilizacion();
		datosTecnicos.setTipoVehiculoIndustria(valor);

		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean()!=null)
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
		else valor = null;
		if(null != valor && !"".equals(valor.trim()) && !"-1".equals(valor.trim()) && TipoVehiculoDGT.contains(valor)){
			datosTecnicos.setTipoVehiculoDGT(valor);
		}

		MarcaDgtVO marca = getModeloTrafico().obtenerDescMarca(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean().getCodigoMarca());
		if (marca!=null){
			datosTecnicos.setMarca(marca.getMarca());
		}
		datosTecnicos.setModelo(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getModelo());

		valor = (null!=tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getColorBean().getValorEnum() ?
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getColorBean().getValorEnum() : "");

		if(null != valor && !"".equals(valor.trim())){
			if ("-".equals(valor))
				datosTecnicos.setColor("");
			else {
				datosTecnicos.setColor(valor);
			}
		}
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getTipoItv() != null && !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getTipoItv().equals("")){
			datosTecnicos.setTipo(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getTipoItv());
		}

		// Variante:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getVariante() != null && !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getVariante().equals("")){
			datosTecnicos.setVariante(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getVariante());
		}

		// Versión:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getVersion() != null && !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getVersion().equals("")){
			datosTecnicos.setVersion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getVersion());
		}

		// MATE 2.5

		// Procedencia si se trata de vehículo usado e importado.
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia().equals("") &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia().equals("-1")&&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado().equals("true") &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado()){
			datosTecnicos.setProcedencia(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia()));
		}

		// Matrícula origen Extranjero , Sólo se envia si el tipo de matriculación es 8,Rematricular.

	// Si está habilitada la matriculación extrajera.
	/* if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("habilitar.matw.matriculacionExtranjera"))) {
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr().equals("") &&
				TipoTramiteMatriculacion.REMATRICULAR.equals(tramiteTraficoMatriculacionBean.getTipoTramiteMatriculacion())){

			datosTecnicos.setMatriculaOrigenExtranjero(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr());
		}
	}*/

		// Fabricante:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getFabricante() != null && 
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getFabricante().equals("")){

			datosTecnicos.setFabricante(tramiteTraficoMatriculacionBean.getTramiteTraficoBean()
					.getVehiculo().getFabricante());
		}

		//Tipo tarjeta ITV
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().isEmpty()){
			datosTecnicos.setTipoTarjetaItv(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv());
		}

		// FIN MATE 2.5

		datosTecnicos.setCaracteristicas(completaCaracteristicasVehiculo(tramiteTraficoMatriculacionBean));

		// MATW. País de fabricación
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPaisFabricacionBean() != null){
			datosTecnicos.setPaisFabricacion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPaisFabricacionBean().getIdPaisFabricacion());
		}

		// VEHÍCULOS MULTIFÁSICOS
		if (tramiteTraficoMatriculacionBean != null 
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null) {

			// Marca base
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean() != null
					&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean().getCodigoMarca() != null) {
				MarcaDgtVO marcaBase = getModeloTrafico().obtenerDescMarca(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean().getCodigoMarca());
				datosTecnicos.setMarcaBase(marcaBase != null ? marcaBase.getMarca() : null);
			}

			// Fabricante base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricanteBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricanteBase().isEmpty()){
				datosTecnicos.setFabricanteBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricanteBase());
			}

			// Tipo base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoItvBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoItvBase().isEmpty()){
				datosTecnicos.setTipoBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoItvBase());
			}

			// Variante base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVarianteBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVarianteBase().isEmpty()){
				datosTecnicos.setVarianteBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVarianteBase());
			}

			// Versión base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVersionBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVersionBase().isEmpty()){
				datosTecnicos.setVersionBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVersionBase());
			}

			// Homologación base; prueba blanco a número homologación si valen null:
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase()!=null &&
					!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase().isEmpty()){
				datosTecnicos.setNumeroHomologacionBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase());
			}

		}

		return datosTecnicos;
	}

	/**
	 * Obtiene el objeto {@link Caracteristicas} relleno a partir de las casillas que forman el trámite
	 * que se quiere presentar.
	 * 
	 * @param tramite {@link TramitesBean} que contiene todas las casillas correspondientes al trámite que se quiere presentar
	 * @return Devuelve el objeto {@link Caracteristicas} relleno
	 */
	private Caracteristicas completaCaracteristicasVehiculo(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean){
		String valor = null;
		BigDecimal valorBigDec = new BigDecimal(0);
		ObjectFactory objFactory = new ObjectFactory();

		Caracteristicas vehiculo = objFactory.createDatosEspecificosDatosTecnicosCaracteristicas();

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal().toString().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal().toString();
			vehiculo.setPotenciaFiscal(new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setPotenciaFiscal(null);
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada();
			vehiculo.setCilindrada(new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN));
		} else if(null==tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada() || "".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().trim())){
			vehiculo.setCilindrada(null);
		} else {
			vehiculo.setCilindrada(new BigDecimal(0));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara()
				&& !equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara().isEmpty())
				&& Integer.parseInt(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara()) > 0){
			vehiculo.setTara(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara()));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPesoMma() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPesoMma().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPesoMma();
			vehiculo.setMasaMaximaAutorizada(new BigInteger(valor));
		} else {
			vehiculo.setMasaMaximaAutorizada(new BigInteger(_0));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPlazas() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPlazas().toString().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPlazas().toString();
			vehiculo.setNumeroMaximoPlazas(new BigInteger(valor));
		} else {
			vehiculo.setNumeroMaximoPlazas(new BigInteger(_0));
		}

		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null){
			vehiculo.setTipoCarburante(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante());
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaPeso() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaPeso())){
			valorBigDec = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaPeso();
			vehiculo.setRelacionPotenciaPeso(valorBigDec.setScale(4, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setRelacionPotenciaPeso(new BigDecimal(_0).setScale(4, BigDecimal.ROUND_DOWN));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumPlazasPie() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumPlazasPie())){
			valorBigDec = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumPlazasPie();
			vehiculo.setNumeroPlazasDePie(valorBigDec.toBigInteger());
		} else {
			vehiculo.setNumeroPlazasDePie(new BigInteger(_0));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2() &&
				!"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2()) &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null &&
				!"E".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante())){

			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2();
			// MATW. Por si se recuperan valores anteriores a matw con más de tres decimales, setea el CO2 construyendo
			// el big decimal indicando como 3 el máximo número de decimales. No redondea.
			vehiculo.setCo2(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
			/**
			 * Julio Molina
			 * Incidencia 2056. No enviamos la etiqueta del CO2 en el XML que se envia si el vehiculo es electrico.
			 */

		} else if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null &&
				"E".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante())){
			//No se establece el parametro ya que no debe de ir en el XML
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2();
			if (valor != null) {
				vehiculo.setCo2(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
			}
			//vehiculo.setCo2(BigDecimal.ZERO);
		} else {
			vehiculo.setCo2(null);
		}

		// Masa en servicio (mom):
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMom() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMom().equals("")){
			BigInteger intMasaServicio = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMom().toString());
			vehiculo.setMasaEnServicio(intMasaServicio);
		}

		// MASA TÉCNICA ADMISIBLE
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMtmaItv() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMtmaItv().equals("")){
			BigInteger intMasaTecnicaAdmisible = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMtmaItv());
			vehiculo.setMasaTecnicaAdmisible(intMasaTecnicaAdmisible);
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaNeta() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaNeta().toString().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaNeta().toString();
			vehiculo.setPotenciaNetaMaxima(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setPotenciaNetaMaxima(new BigDecimal(0));
		}

		// MATE 2.5

		// Código eco:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodigoEco() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodigoEco().equals("")){
			vehiculo.setCodigoECO(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodigoEco());
		}

		// Distancia entre ejes:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDistanciaEntreEjes() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDistanciaEntreEjes().equals("")){
			BigInteger intDistEjes = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getDistanciaEntreEjes().toString());
			vehiculo.setDistanciaEjes(intDistEjes);
		}

		// Eco innovación:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEcoInnovacion() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEcoInnovacion().equals("")){
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEcoInnovacion().equals(TipoSINO.SI)){
				vehiculo.setEcoInnovacion(TipoSN.S.value());
			}else{
				vehiculo.setEcoInnovacion(TipoSN.N.value());
			}
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones()
				&& !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones())
				&& !"1".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones().toUpperCase();
			vehiculo.setNivelEmisiones(valor);
		} else if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones()
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones().equals("1")
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean()!=null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("D")) {
			valor = BasicText.changeSize("1", 6, '0', true);
			vehiculo.setNivelEmisiones(valor);
		} else {
			vehiculo.setNivelEmisiones("0");
		}

		// Reduccion eco:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getReduccionEco() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getReduccionEco().equals("")){
			vehiculo.setReduccionEco(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getReduccionEco().toString()));
		}

		// Vía anterior:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaAnterior() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaAnterior().equals("")){
			BigInteger intViaAnt = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaAnterior().toString());
			vehiculo.setViaAnterior(intViaAnt);
		}

		// Vía posterior:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaPosterior() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaPosterior().equals("")){
			BigInteger intViaPos = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaPosterior().toString());
			vehiculo.setViaPosterior(intViaPos);
		}

		// Consumo:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo().equals("")){
			//String consumo = Utiles.rellenarCeros(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo().toString(),4);
			vehiculo.setConsumo(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo().toString()));
		}

		// Carroceria:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().getIdCarroceria() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().getIdCarroceria().equals("") &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().getIdCarroceria().equals("-1")){

			vehiculo.setCarroceria(tramiteTraficoMatriculacionBean.getTramiteTraficoBean()
					.getVehiculo().getCarroceriaBean().getIdCarroceria());
		}

		// Categoria de homologación:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion().equals("") &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion().equals("-1")){

			vehiculo.setCategoriaEu(tramiteTraficoMatriculacionBean.getTramiteTraficoBean()
					.getVehiculo().getHomologacionBean().getIdHomologacion());
		}

		// Tipo de alimentación
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean().getIdAlimentacion() != null){
			vehiculo.setTipoAlimentacion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean().getIdAlimentacion());
		}

		// VEHÍCULOS MULTIFÁSICOS
		// Masa en servicio base (mom base):
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMomBase() != null){
			vehiculo.setMOMBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMomBase().toBigInteger());
		}

		// Categoría eléctrica
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean() != null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica()!=null
				&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica().isEmpty()) {
			vehiculo.setCategoriaElectrica(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica());
		}

		// Autonomía eléctrica
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAutonomiaElectrica()!=null) {
			vehiculo.setAutonomiaElectrica(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAutonomiaElectrica().toBigInteger());
		}

		return vehiculo;
	}

	private TipoDatoDireccion beanToXmldireccion(Direccion direccion){
		//TIPO DATO DIRECCIÓN
		TipoDatoDireccion tipoDatoDireccion = new TipoDatoDireccion();

		UtilesConversionesMatw utils = new UtilesConversionesMatw();

		if (direccion.getBloque()!=null && !"".equals(direccion.getPortal())) {
			tipoDatoDireccion.setBloque(direccion.getBloque());
		} else {
			tipoDatoDireccion.setBloque("");
		}

		if(direccion.getPuerta()!= null && !"".equals(direccion.getPuerta())){
			tipoDatoDireccion.setPortal(direccion.getPuerta());
		} else {
			tipoDatoDireccion.setPortal("");
		}

		if (direccion.getCodPostalCorreos() != null && !"".equals(direccion.getCodPostalCorreos())) {
			tipoDatoDireccion.setCodigoPostal(direccion.getCodPostalCorreos());
		} else {
			tipoDatoDireccion.setCodigoPostal("");
		}

		if (direccion.getEscalera()!=null && !"".equals(direccion.getEscalera()))
			tipoDatoDireccion.setEscalera(direccion.getEscalera());
		else
			tipoDatoDireccion.setEscalera("");

		if (direccion.getHm()!=null && !"".equals(direccion.getHm()))
			tipoDatoDireccion.setHectometro(new BigInteger(direccion.getHm()));

		if (direccion.getPuntoKilometrico()!=null && !"".equals(direccion.getPuntoKilometrico()))
			tipoDatoDireccion.setKilometro(new BigInteger(direccion.getPuntoKilometrico()));

		if (direccion.getPuebloCorreos()!=null && !"".equals(direccion.getPuebloCorreos()))
			tipoDatoDireccion.setLocalidad(direccion.getPuebloCorreos());
		else tipoDatoDireccion.setLocalidad("");

		if (direccion.getMunicipio()!=null && !"".equals(direccion.getMunicipio())) {
			if (direccion.getMunicipio().getIdMunicipio()!=null) {
				tipoDatoDireccion.setMunicipio(direccion.getMunicipio().getProvincia().getIdProvincia()+direccion.getMunicipio().getIdMunicipio());
			} else {
				tipoDatoDireccion.setMunicipio("");
			}
		}

		if (direccion.getNombreVia()!=null && !"".equals(direccion.getNombreVia())) {
			String nombreVia = utilesConversiones.ajustarCamposIne(direccion.getNombreVia());
			tipoDatoDireccion.setNombreVia(UtilesCadenaCaracteres.quitarCaracteresExtranios(nombreVia));
			// tipoDatoDireccion.setNombreVia(nombreVia);
		} else tipoDatoDireccion.setNombreVia("");

		if (direccion.getNumero()!=null && !"".equals(direccion.getNumero()))
			tipoDatoDireccion.setNumero(direccion.getNumero());
		else tipoDatoDireccion.setNumero("");

		if (direccion.getPlanta()!=null && !"".equals(direccion.getPlanta()))
			tipoDatoDireccion.setPlanta(direccion.getPlanta());
		else tipoDatoDireccion.setPlanta("");

		if (direccion!=null && direccion.getLetra()!=null) {
			tipoDatoDireccion.setPuerta(direccion.getLetra());
		} else tipoDatoDireccion.setPuerta("");

		tipoDatoDireccion.setPais(PAIS);

		if (direccion!=null&&direccion.getMunicipio()!=null &&
				direccion.getMunicipio().getProvincia()!=null &&
				direccion.getMunicipio().getProvincia().getIdProvincia()!=null) {

			if (direccion.getMunicipio().getProvincia().getIdProvincia().equals("-1"))
				tipoDatoDireccion.setProvincia("");
			else {
				//jbc cambio matw para enviar en la provincia la sigla
				tipoDatoDireccion.setProvincia(utils.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()));
			}
		} else
			tipoDatoDireccion.setProvincia("");

		if (direccion!=null&&direccion.getTipoVia()!=null&&direccion.getTipoVia().getIdTipoVia()!=null) {
			if (direccion.getTipoVia().getIdTipoVia().equals("-1")) {
				tipoDatoDireccion.setTipoVia("");
			} else {
				// Comprobamos si el tipo de vía tiene una traducción entre las aceptadas por DGT (que para MATW sólo los tiene en castellano)
				String idTipoVia = null;
				TipoViaVO tipoViaVO = servicioTipoVia.getTipoVia(direccion.getTipoVia().getIdTipoVia());
				idTipoVia = tipoViaVO.getIdTipoViaDgt();
				if(idTipoVia!=null){
					tipoDatoDireccion.setTipoVia(idTipoVia);
				} else {
					tipoDatoDireccion.setTipoVia(direccion.getTipoVia().getIdTipoVia());
				}
			}
		} else
			tipoDatoDireccion.setTipoVia("");

		return tipoDatoDireccion;
	}

	private TipoDatoTitular beanToXmlTipoDatoTitular(IntervinienteTrafico interviniente) {

		trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory objectFactory = new trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory();

		TipoDatoTitular tipoDatoTitular = new TipoDatoTitular();

		//DOCUMENTO DE IDENTIDAD
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = beanToXmlTipoDocumentoIdentificacion(interviniente, objectFactory);
		tipoDatoTitular.setDocumentoIdentidad(tipoDocumentoIdentificacion);

		//TIPODATONOMBRE
		TipoDatoNombre tipoDatoNombre = beanToXmlTipoDatoNombre(interviniente, objectFactory, tipoDatoTitular);
		tipoDatoTitular.setDatosNombre(tipoDatoNombre);

		//FECHA NACIMIENTO

		if (!interviniente.getPersona().getTipoPersona().equals(TipoPersona.Juridica)) {
			if(interviniente.getPersona().getFechaNacimientoBean()!=null && !interviniente.getPersona().getFechaNacimientoBean().getAnio().equals("") &&
					!interviniente.getPersona().getFechaNacimientoBean().getMes().equals("") && !interviniente.getPersona().getFechaNacimientoBean().getDia().equals(""))
				tipoDatoTitular.setFechaNacimiento(utilesFecha.invertirCadenaFechaSinSeparador(interviniente.getPersona().getFechaNacimientoBean().toString()));
		} else {
			tipoDatoTitular.setFechaNacimiento(null);
		}
		//SEXO
		tipoDatoTitular.setSexo(interviniente.getPersona().getSexo());

		// JMC NACIONALIDAD DESAPARECE DE LA VERSION INICIAL DE MATW
		//tipoDatoTitular.setNacionalidad(interviniente.getPersona().getNacionalidad());

		return tipoDatoTitular;
	}

	private TipoDatoNombre beanToXmlTipoDatoNombre(IntervinienteTrafico titular,
			trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory objectFactory,
			TipoDatoTitular tipoDatoTitular) {
		TipoDatoNombre tipoDatoNombre = new TipoDatoNombre();

		if(null == titular.getPersona().getNombre() || "".equals(titular.getPersona().getNombre())){
			if (titular.getPersona().getApellido1RazonSocial().length()>=70)
				tipoDatoNombre.setRazonSocial(titular.getPersona().getApellido1RazonSocial().substring(0, 70));
			else tipoDatoNombre.setRazonSocial(titular.getPersona().getApellido1RazonSocial());
			tipoDatoNombre.setNombre("");
			tipoDatoNombre.setPrimerApellido("");
			tipoDatoNombre.setSegundoApellido("");
		}else{
			tipoDatoNombre.setRazonSocial("");
			tipoDatoNombre.setNombre(titular.getPersona().getNombre());
			if (titular.getPersona().getApellido1RazonSocial().length()>=26)
				tipoDatoNombre.setPrimerApellido(titular.getPersona().getApellido1RazonSocial().substring(0, 26));
			else tipoDatoNombre.setPrimerApellido(titular.getPersona().getApellido1RazonSocial());
			tipoDatoNombre.setSegundoApellido(titular.getPersona().getApellido2());
		}

		tipoDatoTitular.setDatosNombre(tipoDatoNombre);

		return tipoDatoNombre;
	}

	private TipoDocumentoIdentificacion beanToXmlTipoDocumentoIdentificacion(IntervinienteTrafico interviniente, trafico.beans.jaxb.matw.dgt.tipos.complejos.ObjectFactory objectFactory) {
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion();

		if (interviniente.getPersona().getNif()!=null && !interviniente.getPersona().getNif().equals(""))
			tipoDocumentoIdentificacion.setNumero(interviniente.getPersona().getNif());
		else tipoDocumentoIdentificacion.setNumero(null);
		return tipoDocumentoIdentificacion;
	}

	private Tutor completaDatosTutor(Tutor tutor,TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {

		TipoDocumentoIdentificacion tipoDocumentoIdentificacionTutor = new TipoDocumentoIdentificacion();

		if (traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif()!=null
				&&!traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif().equals("")) {
			tipoDocumentoIdentificacionTutor.setNumero(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif());
			tutor.setDocumentoIdentidad(tipoDocumentoIdentificacionTutor);

			TipoTutela tipoTutela = null;

			if(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela() != null){
				tipoTutela = traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela();
				/**
				 * Inicio
				 * 20/12/13 JF, se incluye el valor de Menor Emancipado "E" como un nuevo
				 * valor posible del motivo de la tutela, se agrega el valor Menor Emancipado
				 */
					if(tipoTutela.equals(TipoTutela.MinoriaEdad)){
						tutor.setTipoTutela(TipoTutela.MinoriaEdad.toString());
					}else if (tipoTutela.equals(TipoTutela.MenorEmancipado)){
						tutor.setTipoTutela(TipoTutela.MenorEmancipado.toString());
					}else if (tipoTutela.equals(TipoTutela.Otros)){
						tutor.setTipoTutela(TipoTutela.Otros.toString());
					}
				/**
				 * Fin
				 */
			}

		}
		return tutor;
	}

	/**
	 * Punto en el que se unifican los caminos de MATE y MATG en MATW
	 * @param traficoTramiteMatriculacionBean
	 * @param idUsuario
	 * @param idContrato
	 * @return
	 * @throws Throwable
	 * @throws OegamExcepcion
	 * @throws ClassNotFoundException
	 */
	public ResultBean matriculacionTelematicaHsm( TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, BigDecimal idUsuario, BigDecimal idContrato) throws Throwable {
		ResultBean resultBean = new ResultBean();
		log.info("NIVE: " + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive());
		log.info("Expediente: " + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
		resultBean = tramitacion(traficoTramiteMatriculacionBean,idUsuario,idContrato);

		return resultBean;
	}

	public boolean validarEstadoTramite(TramiteTrafMatrDto tramite) {
		boolean validacionEstado = false;
		if(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramite.getEstado())) {
			validacionEstado = true;
		} else if(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramite.getEstado())) {
			validacionEstado = true;
		} else if(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramite.getEstado())) {
			validacionEstado = true;
		}
		return validacionEstado;
	}
	public ResultBean validarSolicitud05(TramiteTrafMatrDto tramite) {
		ResultBean resultado = new ResultBean();

		List<String> errores = validarTramiteSolicitud05(tramite);

		if(errores.size() > 0) {
			resultado.setListaMensajes(errores);
			resultado.setError(true);
		}
		return resultado;
	}
	private List<String> validarTramiteSolicitud05(TramiteTrafMatrDto tramite) {
		List<String> errores = new ArrayList<>();

		if(tramite.getTitular() != null) {
			if(tramite.getTitular().getPersona() != null) {
				if(tramite.getTitular().getPersona().getNif() == null) {
					errores.add("Se debe rellenar el campo Nif del Titular");
				}
				if ("JURIDICA".equals(tramite.getTitular().getPersona().getTipoPersona())
					|| "FISICA".equals(tramite.getTitular().getPersona().getTipoPersona())) {
					if(tramite.getTitular().getPersona().getApellido1RazonSocial() == null) {
						errores.add("Se debe rellenar el campo Apellido/Razón Social");
					}
				}
				if("FISICA".equals(tramite.getTitular().getPersona().getTipoPersona())) {
					if(tramite.getTitular().getPersona().getApellido2() == null) {
						errores.add("Se debe rellenar el campo Segundo Apellido");
					}
					if(tramite.getTitular().getPersona().getNombre() == null) {
						errores.add("Se debe rellenar el campo Nombre");
					}
				}
			}
		}

		if(tramite.getVehiculoDto() != null) {
			if(tramite.getVehiculoDto().getCodigoMarca() == null) {
				errores.add("Se debe rellenar el campo Marca");
			}
			if(tramite.getVehiculoDto().getTipoVehiculo() == null) {
				errores.add("Se debe rellenar el campo Tipo");
			}
			if(tramite.getVehiculoDto().getModelo() == null) {
				errores.add("Se debe rellenar el campo Modelo");
			}
			if(tramite.getVehiculoDto().getBastidor() == null) {
				errores.add("Se debe rellenar el campo Bastidor");
			}
		}
		return errores;
	}

	public ResultBean validarLiquidacionNRE06(TramiteTrafMatrDto tramite) {
		ResultBean resultado = new ResultBean();

		List<String> errores = validarTramiteLiquidacionNRE06(tramite);

		if(errores.size() > 0) {
			resultado.setListaMensajes(errores);
			resultado.setError(true);
		}
		return resultado;
	}

	private List<String> validarTramiteLiquidacionNRE06(TramiteTrafMatrDto tramite) {
		List<String> errores = new ArrayList<>();

		if (tramite.getTitular() != null && tramite.getTitular().getPersona() != null) {
			if(tramite.getTitular().getPersona().getNif() == null) {
				errores.add("Se debe rellenar el campo NIF del Titular.");
			}
			if ("JURIDICA".equals(tramite.getTitular().getPersona().getTipoPersona()) 
				|| "FISICA".equals(tramite.getTitular().getPersona().getTipoPersona())) {
				if(tramite.getTitular().getPersona().getApellido1RazonSocial() == null) {
					errores.add("Se debe rellenar el campo Apellido/Razón Social.");
				}
			}
			if("FISICA".equals(tramite.getTitular().getPersona().getTipoPersona())) {
				if(tramite.getTitular().getPersona().getApellido2() == null) {
					errores.add("Se debe rellenar el campo Segundo Apellido.");
				}
				if(tramite.getTitular().getPersona().getNombre() == null) {
					errores.add("Se debe rellenar el campo Nombre.");
				}
			}
		}

		if(tramite.getVehiculoDto() != null) {
			if(tramite.getVehiculoDto().getMarcaSinEditar() == null) {
				errores.add("Se debe rellenar el campo Marca.");
			}
			if(tramite.getVehiculoDto().getModelo() == null) {
				errores.add("Se debe rellenar el campo Modelo.");
			}
			if(tramite.getVehiculoDto().getBastidor() == null) {
				errores.add("Se debe rellenar el campo Bastidor.");
			}
			if(tramite.getVehiculoDto().getClasificacionItv() == null) {
				errores.add("Se debe rellenar el campo Clasificación.");
			}
			if(tramite.getVehiculoDto().getCriterioConstruccion() == null) {
				errores.add("Se debe rellenar el campo Criterio de Construcción.");
			}
			if(tramite.getVehiculoDto().getCriterioUtilizacion() == null) {
				errores.add("Se debe rellenar el campo Criterio de Utilización.");
			}
			if(tramite.getVehiculoDto().getIdDirectivaCee() == null) {
				errores.add("Se debe rellenar el campo Categoría EU.");
			}
			if(tramite.getVehiculoDto().getCo2() == null) {
				errores.add("Se debe rellenar el campo CO2.");
			}
			if(tramite.getVehiculoDto().getEpigrafe() == null) {
				errores.add("Se debe rellenar el campo Epígrafe");
			}
			if(tramite.getVehiculoDto().getNumSerie() == null) {
				errores.add("Se debe rellenar el campo número de serie");
			}
			if(tramite.getVehiculoDto().getTipoItv() == null) {
				errores.add("Se debe rellenar el campo Tipo tarjeta ITV");
			}
			if(tramite.getVehiculoDto().getCarburante() == null) {
				errores.add("Se debe rellenar el campo Tipo de Carburante.");
			}
			if(tramite.getVehiculoDto().getCilindrada() == null) {
				errores.add("Se debe rellenar el campo Cilindrada.");
			}
			if(tramite.getVehiculoDto().getTipoVehiculo() == null) {
				errores.add("Se debe rellenar el campo Tipo de Vehículo.");
			}
			if(tramite.getVehiculoDto().getTipoVehiculo() != null) {
				String tipoVehiculo = tramite.getVehiculoDto().getTipoVehiculo().substring(0, 1);
				if ("5".equals(tipoVehiculo) && tramite.getVehiculoDto().getPotenciaPeso() == null) {
					errores.add("Para este Tipo de Vehículo se debe rellenar el campo Potencia/Peso.");
				}
			}
		}
		if(tramite.getBaseImponible576() == null) {
			errores.add("Se debe rellenar el campo Base imponible");
		}
		if(tramite.getTipoGravamen576() == null) {
			errores.add("Se debe rellenar el campo Tipo de Gravamen.");
		}
		if(tramite.getCuota576() == null) {
			errores.add("Se debe rellenar el campo Tipo de Cuota.");
		}
		if(tramite.getCuotaIngresar576() == null) {
			errores.add("Se debe rellenar el campo Cuota a ingresar.");
		}
		if(tramite.getLiquidacion576() == null) {
			errores.add("Se debe rellenar el campo Resultado de la liquidación.");
		}
		if(tramite.getCausaHechoImpon576() == null) {
			errores.add("Se debe rellenar el campo Causa del Hecho Imponible.");
		}
		if ("A".equals(tramite.getVehiculoDto().getTipoItv()) &&
			tramite.getVehiculoDto().getNifIntegrador().getNif() == null) {
			errores.add("Se debe rellenar el campo NIF de la persona que ha introducido el vehículo en España.");
		}
		if ("A".equals(tramite.getVehiculoDto().getTipoItv())) {
			if ("FISICA".equals(tramite.getVehiculoDto().getNifIntegrador().getTipoPersona())) {
				if(tramite.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial() == null) {
					errores.add("Se debe introducir el primer apellido del Introductor del vehículo en España.");
				}
				if(tramite.getVehiculoDto().getNifIntegrador().getApellido2() == null) {
					errores.add(" Se debe introducir el segundo apellido del Introductor del vehículo en España.");
				}
				if(tramite.getVehiculoDto().getNifIntegrador().getNombre() == null) {
					errores.add(" Se debe introducir el Nombre del Introductor del vehículo en España.");
				}
			} else if ("JURIDICA".equals(tramite.getVehiculoDto().getNifIntegrador().getTipoPersona())) {
				if(tramite.getVehiculoDto().getNifIntegrador().getApellido1RazonSocial() == null) {
					errores.add(" Se debe introducir Razón Social del Introductor del vehículo en España.");
				}
			}
		}
		if(tramite.getVehiculoDto().getVehiUsado()) {
			if(tramite.getVehiculoDto().getFechaPrimMatri() == null) {
				errores.add("En el caso de un vehículo usado es obligatoria la fecha de primera matriculación.");
			}
			if(tramite.getVehiculoDto().getKmUso() == null) {
				errores.add("En el caso de un vehículo usado son obligatorios los kms de uso.");
			}
		}
		return errores;
	}

	public String validarMatriculaOrigen(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		String resultadoValidacion = "OK";
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean() != null && tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null){
			if("true".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado())){
				if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado()){
					if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia() == null
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia().isEmpty()
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia() == "" 
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia() == "-1"){
						return "Para los vehículos importados es obligatorio indicar la procedencia.";
					}

					if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr() == null
							|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr().isEmpty()){
						return "Para los vehículos importados es obligatorio indicar la matrícula extranjera.";
					}

					if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen() != null
							&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen().isEmpty()){
						return "Para los vehículos importados no se debe de indicar la matrícula origen.";
					}
				}else{
					if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen() == null
							|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen().isEmpty()){
						return "Para los vehículos usados es obligatorio indicar la matrícula origen.";
					}
				}

				if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri() == null
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri().isfechaNula()){
					return "Para los vehículos usados es obligatorio indicar la fecha de primera matriculación.";
				}
			}
		}
		return resultadoValidacion;
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

	public ModeloInterviniente getModeloInterviniente() {
		if (modeloInterviniente == null) {
			modeloInterviniente = new ModeloInterviniente();
		}
		return modeloInterviniente;
	}

	public void setModeloInterviniente(ModeloInterviniente modeloInterviniente) {
		this.modeloInterviniente = modeloInterviniente;
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
 
	public ModeloVehiculo getModeloVehiculo() {
		if (modeloVehiculo == null) {
			modeloVehiculo = new ModeloVehiculo();
		}
		return modeloVehiculo;
	}

	public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
		this.modeloVehiculo = modeloVehiculo;
	}

	public ModeloColegiado getModeloColegiado() {
		if (modeloColegiado == null) {
			modeloColegiado = new ModeloColegiado();
		}
		return modeloColegiado;
	}

	public void setModeloColegiado(ModeloColegiado modeloColegiado) {
		this.modeloColegiado = modeloColegiado;
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

	public ServicioEEFF getServicioEEFF() {
		return servicioEEFF;
	}

	public void setServicioEEFF(ServicioEEFF servicioEEFF) {
		this.servicioEEFF = servicioEEFF;
	}

	public void ajustarTara(TramiteTraficoMatriculacionBean tramite) {
		if (tramite!=null && tramite.getTramiteTraficoBean()!=null
				|| tramite.getTramiteTraficoBean().getVehiculo()!=null){
			ajustarTara(tramite.getTramiteTraficoBean().getVehiculo()); //Se genera la TARA, en caso de no existir, a partir del MOM.
		}
	}

	public void ajustarTara(VehiculoBean vehiculo) {
		if (vehiculo != null && (vehiculo.getTara()==null || vehiculo.getTara().isEmpty()) && vehiculo.getMom()!=null && !vehiculo.getMom().equals("")){
			vehiculo.setTara(vehiculo.getMom().subtract(new BigDecimal(75)).toString());
		}
	}

	public void ajustarTaraDto(Vehiculo vehiculo) {
		if (vehiculo != null && (vehiculo.getTara()==null || vehiculo.getTara().isEmpty()) && vehiculo.getMom()!=null && !vehiculo.getMom().equals("")){
			vehiculo.setTara(vehiculo.getMom().subtract(new BigDecimal(75)).toString());
		}
	}
	public String validarDevengo576(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		String resultadoValidacion = "OK";
		if(tramiteTraficoMatriculacionBean.getEjercicioDevengo576().toString().equals("-1")){
			resultadoValidacion = "Debe elegir un año para el ejercicio de devengo ";
		}
		return resultadoValidacion;
	}

	public ModeloMatriculacionSega getModeloMatriculacionSega() {
		if(modeloMatriculacionSega == null){
			modeloMatriculacionSega = new ModeloMatriculacionSega();
		}
		return modeloMatriculacionSega;
	}

	public void setModeloMatriculacionSega(ModeloMatriculacionSega modeloMatriculacionSega) {
		this.modeloMatriculacionSega = modeloMatriculacionSega;
	}

}