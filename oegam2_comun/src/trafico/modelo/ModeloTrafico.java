package trafico.modelo;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.general.model.vo.OficinaLiquidadora620VO;
import org.gestoresmadrid.core.hibernate.util.HibernateBean;
import org.gestoresmadrid.core.hibernate.util.HibernateUtil;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencion620VO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MotivoItvVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoMotorVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.model.service.ServicioOficinaLiquidadora620;
import org.gestoresmadrid.oegam2comun.model.service.ServicioTipoTramite;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFundamentoExencion620;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioConstruccion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCriterioUtilizacion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMotivoItv;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioServicioTrafico;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoMotor;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoReduccion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioConstruccionDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.CriterioUtilizacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasas;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ColegioBean;
import escrituras.beans.FundamentoExencion;
import escrituras.beans.ResultBean;
import escrituras.beans.dao.OficinaLiquidadoraDao;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import general.utiles.UtilHttpClient;
import general.utiles.enumerados.TipoVehiculoEnum;
import hibernate.entities.trafico.TipoCreacion;
import hibernate.entities.trafico.TramiteTrafMatr;
import hibernate.entities.trafico.TramiteTrafico;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import oegam.constantes.ValoresSchemas;
import trafico.beans.ClasificacionCriterioConstruccionBean;
import trafico.beans.ClasificacionCriterioUtilizacionBean;
import trafico.beans.DescripcionesDireccionBean;
import trafico.beans.DescripcionesTraficoBean;
import trafico.beans.MotivoBean;
import trafico.beans.ServicioTraficoBean;
import trafico.beans.TipoMotorBean;
import trafico.beans.TipoReduccionBean;
import trafico.beans.TipoVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.beans.daos.BeanPQDescripcionesDireccion;
import trafico.beans.daos.BeanPQDescripcionesTrafico;
import trafico.beans.daos.BeanPQDuplicarTramite;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQTramiteBuscarResult;
import trafico.beans.daos.BeanPQTramiteTraficoBuscar;
import trafico.beans.daos.BeanPQTramiteTraficoEliminar;
import trafico.beans.daos.BeanPQValidarTramite;
import trafico.beans.daos.JefaturaTraficoDao;
import trafico.beans.utiles.ColegioBeanPQConversion;
import trafico.beans.utiles.DescripcionBeanPQConversion;
import trafico.beans.utiles.TramiteTraficoBeanPQConversion;
import trafico.beans.utiles.VehiculoBeanPQConversion;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.enumerados.AcreditacionTrafico;
import trafico.utiles.enumerados.ModoAdjudicacion;
import trafico.utiles.enumerados.TipoProceso;
import trafico.utiles.enumerados.TipoTasa;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.FechaFraccionada;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.validaciones.LongitudCampo;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;

public class ModeloTrafico extends ModeloBasePQ {
	private static final int TamanoP_Respuesta = 300;
	private static final String AEAT_DIRECCION_SERVIDOR_VALIDAR_ANAGRAMA = "aeat.server.host.validarAnagrama";
	private static final String KEY_STORE_CLAVE_PRIVADA_URL = "aeat.keystore.url";
	private static final String KEY_STORE_CLAVE_PRIVADA_PASSWORD = "aeat.keystore.password";

	private static final String AEAT_DIRECCION_SERVIDOR_VALIDAR_CEM = "aeat.server.host.validarCEM";
	private static final String AEAT_VALIDAR_CEM_ACTIVO = "aeat.validarCEM.activo";
	// log de errores
	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloTrafico.class);

	public static final String ERROR_AEAT_VALIDACION_CEM_SERVICIO = "CEM no validable telemáticamente, error en el servicio";
	public static final String ERROR_AEAT_VALIDACION_CEM_BASTIDOR = "El número de bastidor no coincide con el presentado en la AEAT";
	public static final String ERROR_AEAT_VALIDACION_CEM_NO_VALIDABLE = "El valor del CEM no ha sido validado por la A.E.A.T.. Si se trata del modelo 05, no es posible la validación telemática. En otro caso el CEM no es válido.";

	public static final String NIF = "NIF";
	public static final String APELLIDO1 = "APELLIDO1";
	public static final String CODIGO = "CODIGO";

	private static final String KEYSTORE_LOCATION_SELENIUM = "aeat.selenium.keystore.url";
	private static final String KEYSTORE_PASS_SELENIUM = "aeat.selenium.keystore.password";
	private static final String AEAT_SELENIUM_DIRECCION_SERVIDOR_VALIDAR_ANAGRAMA = "aeat.selenium.server.host.validarAnagrama";
	private static final String AEAT_SELENIUM_INPUT_NIF = "aeat.selenium.input.nif";
	private static final String AEAT_SELENIUM_INPUT_APE = "aeat.selenium.input.ape";
	private static final String AEAT_SELENIUM_CHECKBOX_ETI2 = "aeat.selenium.checkbox.eti2";
	private static final String AEAT_SELENIUM_CHECBOX_FIL1 = "aeat.selenium.checkbox.fil1";
	private static final String AEAT_SELENIUM_SELECT_LOG = "aeat.selenium.select.log";
	private static final String AEAT_SELENIUM_FORM_SOLICITUD = "aeat.selenium.form.solicitud";

	private ModeloMatriculacion modeloMatriculacion;
	private ModeloTransmision modeloTransmision;

	@Autowired
	private ServicioEEFF servicioEEFF;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	private ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioProcesos servicioProcesos;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioTipoTramite servicioTipoTramite;

	@Autowired
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Autowired
	ServicioTipoMotor servicioTipoMotor;

	@Autowired
	ServicioTipoReduccion servicioTipoReduccion;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioMotivoItv servicioMotivoItv;

	@Autowired
	ServicioPersistenciaTasas servicioPersistenciaTasas;

	@Autowired
	ServicioCriterioUtilizacion servicioCriterioUtilizacion;

	@Autowired
	ServicioServicioTrafico servicioServicioTrafico;

	@Autowired
	ServicioFundamentoExencion620 servicioFundamentoExencion620;

	@Autowired
	ServicioOficinaLiquidadora620 servicioOficinaLiquidadora620;

	@Autowired
	ServicioCriterioConstruccion servicioCriterioConstruccion;

	@Autowired
	TramiteTraficoBeanPQConversion tramiteTraficoBeanPQConversion;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	private UtilHttpClient utilHttpClient;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**
	 * Solo devuelve un bean de pantalla con el numero de expediente.
	 * 
	 * @param traficoTramiteBean
	 * @return
	 */

	public HashMap<String, Object> duplicarTramite(TramiteTraficoBean traficoTramiteBean) {

		RespuestaGenerica respuestaGenerica = null;

		ResultBean resultado = new ResultBean();

		BeanPQDuplicarTramite beanPQDuplicarTramite = new BeanPQDuplicarTramite();
		// Guardamos el numero de expediente previo, para recuperar luego los datos del
		// EEFF
		BigDecimal numExpedientePrevio = null;
		if (traficoTramiteBean != null && traficoTramiteBean.getNumExpediente() != null) {
			numExpedientePrevio = traficoTramiteBean.getNumExpediente();
		}

		// BeanConversion deberia coger los valores del bean de pantalla
		beanPQDuplicarTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQDuplicarTramite.setP_ID_CONTRATO(utilesColegiado.getIdContratoSessionBigDecimal());
		beanPQDuplicarTramite.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		if (traficoTramiteBean != null)
			if (traficoTramiteBean.getNumExpediente() != null) {
				beanPQDuplicarTramite.setP_NUM_EXPEDIENTE_ORI(traficoTramiteBean.getNumExpediente());
			}
		beanPQDuplicarTramite.setP_NUM_EXPEDIENTE_NUE(null);

		respuestaGenerica = ejecutarProc(beanPQDuplicarTramite, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_TRAMITE_TRAFICO, "DUPLICAR", BeanPQGeneral.class);

		BigDecimal P_CODE = (BigDecimal) respuestaGenerica.getParametro(ConstantesPQ.P_CODE);
		String P_SQLERRM = (String) respuestaGenerica.getParametro(ConstantesPQ.P_SQLERRM);

		log.debug(ConstantesPQ.LOG_P_CODE + P_CODE);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + P_SQLERRM);

		TramiteTraficoBean tramiteTraficoBean = new TramiteTraficoBean();

		if (!ConstantesPQ.pCodeOk.equals(P_CODE)) {
			log.debug("El tramite no se ha duplicado. ");
			resultado.setMensaje(" " + P_SQLERRM);
			resultado.setError(true);
		} else {
			BigDecimal numExpedienteNuevo = (BigDecimal) respuestaGenerica.getParametro("P_NUM_EXPEDIENTE_NUE");
			if (numExpedienteNuevo != null) {
				tramiteTraficoBean.setNumExpediente(numExpedienteNuevo);
				// Duplicamos los datos de EEFF Liberación.
				if (numExpedientePrevio != null
						&& traficoTramiteBean.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion)) {
					servicioEEFF.duplicarLiberacion(numExpedientePrevio, numExpedienteNuevo);
					// MPC. Cambio IVTM
				}
				// Duplicamos los datos del IVTM.
				if (numExpedientePrevio != null
						&& traficoTramiteBean.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion)) {
					IVTMModeloMatriculacionInterface ivtmModelo = new IVTMModeloMatriculacionImpl();
					ivtmModelo.duplicar(numExpedientePrevio, numExpedienteNuevo);
				}
			} else {
				log.debug(numExpedienteNuevo);
				resultado.setMensaje(resultado.getMensaje() + " " + "no se ha generado un numero de expediente nuevo");
			}
		}

		// Guardamos para devolver respuesta
		HashMap<String, Object> resultadoAlta = new HashMap<String, Object>();

		resultadoAlta.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoAlta.put(ConstantesPQ.BEANPANTALLA, tramiteTraficoBean);

		return resultadoAlta;
	}

	public List<ClasificacionCriterioUtilizacionBean> obtenerClasificacionesCriterioUtilizacion() {
		List<ClasificacionCriterioUtilizacionBean> clasificacionesCriterioUtilizacion = new ArrayList<>();

		List<CriterioUtilizacionDto> criteriosDto = servicioCriterioUtilizacion.getListadoCriterioUtilizacion(null);

		if (null != criteriosDto && !criteriosDto.isEmpty()) {
			for (CriterioUtilizacionDto criterioDto : criteriosDto) {
				ClasificacionCriterioUtilizacionBean clasificacionUtilizacion = new ClasificacionCriterioUtilizacionBean();
				clasificacionUtilizacion.setIdCriterioUtilizacion(criterioDto.getIdCriterioUtilizacion());
				clasificacionUtilizacion
						.setDescripcion(criterioDto.getIdCriterioUtilizacion() + " - " + criterioDto.getDescripcion());
				clasificacionesCriterioUtilizacion.add(clasificacionUtilizacion);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtener ClasificacionesCriterioUtilizacion.");
		return clasificacionesCriterioUtilizacion;
	}

	public List<ServicioTraficoBean> obtenerServiciosTrafico(String tipoTramite) {
		log.info("inicio obtenerServiciosTrafico. tipo Tramite: " + tipoTramite);
		List<ServicioTraficoDto> servicios = servicioServicioTrafico.getServicioTraficoPorTipoTramite(tipoTramite);

		List<ServicioTraficoBean> serviciosTrafico = new ArrayList<ServicioTraficoBean>();

		if (null != servicios && !servicios.isEmpty()) {
			for (ServicioTraficoDto servicio : servicios) {
				ServicioTraficoBean servicioTraficoBean = new ServicioTraficoBean();
				servicioTraficoBean.setIdServicio(servicio.getIdServicio());
				servicioTraficoBean.setDescripcion(servicio.getIdServicio() + " - " + servicio.getDescripcion());
				serviciosTrafico.add(servicioTraficoBean);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtener obtenerServiciosTrafico.");
		return serviciosTrafico;
	}

	public List<String> tiposNumeroIVTM() {
		List<String> listaVehiculos = new ArrayList<String>();
		listaVehiculos.add("NUM");
		listaVehiculos.add("S/N");
		listaVehiculos.add("KM");
		return listaVehiculos;
	}

	public List<TipoVehiculoBean> obtenerTiposVehiculo(String tipoTramite) {
		log.info("inicio obtener tipos vehiculo");
		List<TipoVehiculoVO> tiposVO = servicioTipoVehiculo.getTipoVehiculosPorTipoTramite(tipoTramite);
		List<TipoVehiculoBean> tiposVehiculo = new ArrayList<>();
		if (null != tiposVO && !tiposVO.isEmpty()) {
			for (TipoVehiculoVO tipo : tiposVO) {
				TipoVehiculoBean tipoVehiculo = new TipoVehiculoBean();
				tipoVehiculo.setTipoVehiculo(tipo.getTipoVehiculo());
				tipoVehiculo.setDescripcion(tipo.getTipoVehiculo() + " - " + tipo.getDescripcion());
				tipoVehiculo.setTipoTramite(tipo.getTipoTramite());
				tiposVehiculo.add(tipoVehiculo);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerTipoVehiculos.");
		return tiposVehiculo;
	}

	// Obtenemos los tipos de motor
	public List<TipoMotorBean> obtenerTiposMotor() {
		log.info("inicio obtener tipos motor");
		List<TipoMotorVO> tiposVO = servicioTipoMotor.getListaTiposMotores();
		List<TipoMotorBean> tiposMotor = new ArrayList<>();
		if (null != tiposVO && !tiposVO.isEmpty()) {
			for (TipoMotorVO tipo : tiposVO) {
				TipoMotorBean tipoMotor = new TipoMotorBean();
				tipoMotor.setIdTipoMotor(tipo.getTipoMotor());
				tipoMotor.setDescripcion(tipo.getTipoMotorDesc());
				tiposMotor.add(tipoMotor);
			}
		}
		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerTiposMotor.");
		return tiposMotor;
	}

	// Obtenemos los tipos de reducción
	public List<TipoReduccionBean> obtenerTiposReduccion() {
		log.info("Inicio obtener tipos reducción");
		List<TipoReduccionVO> tiposVO = servicioTipoReduccion.getListaTiposReducciones();
		List<TipoReduccionBean> tiposReduccion = new ArrayList<>();
		if (null != tiposVO && !tiposVO.isEmpty()) {
			for (TipoReduccionVO tipo : tiposVO) {
				TipoReduccionBean tipoReduccion = new TipoReduccionBean();
				tipoReduccion.setIdTipoReduccion(tipo.getTipoReduccion());
				tipoReduccion.setDescripcion(tipo.getDescTipoReduccion());
				tipoReduccion.setPorcentaje(tipo.getPorcentajeReduccion());
				tiposReduccion.add(tipoReduccion);
			}
		}
		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerTiposReduccion.");
		return tiposReduccion;
	}

	/**
	 * Obtenemos las marcas de la tabla MARCA_DGT
	 * 
	 * @param
	 * @return MarcaBean. En caso de error devuelve null.
	 */
	public MarcaDgtVO obtenerDescMarca(BigDecimal codigoMarca) {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerDescMarca");
		try {
			return servicioMarcaDgt.getMarcaDgtCodigo(codigoMarca.longValue());
		} catch (Throwable e) {
			log.error("Error al obtener Descripcion de marca: " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Obtenemos las jefaturas provinciales de la tabla JEFATURA
	 * 
	 * @param
	 * @return JefaturaTrafico. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public JefaturaTraficoVO obtenerJefaturaCompleta(String jefaturaProvincial) {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerJefatura");
		try {
			return servicioJefaturaTrafico.getJefaturaTrafico(jefaturaProvincial);
		} catch (Throwable e) {
			log.error("Error al obtener la jefatura: " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Obtenemos las jefaturas provinciales de la tabla JEFATURA
	 * 
	 * @param
	 * @return List<JefaturaTraficoDao>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<JefaturaTraficoDao> obtenerJefaturasProvinciales() {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerProvincias.");
		List<JefaturaTraficoDao> jefaturas = new ArrayList<JefaturaTraficoDao>();

		List<JefaturaTraficoVO> jefaturasVO = servicioJefaturaTrafico
				.getJefaturasPorContrato(utilesColegiado.getIdContratoSession());

		if (null != jefaturasVO && !jefaturasVO.isEmpty()) {
			for (JefaturaTraficoVO jefaturaVO : jefaturasVO) {
				JefaturaTraficoDao jefatura = new JefaturaTraficoDao();
				jefatura.setDescripcion(jefaturaVO.getDescripcion());
				jefatura.setId_provincia(jefaturaVO.getProvincia().getIdProvincia());
				jefatura.setJefatura(jefaturaVO.getJefatura());
				jefatura.setJefatura_provincial(jefaturaVO.getJefaturaProvincial());
				jefatura.setSucusal(jefaturaVO.getSucursal());
				jefaturas.add(jefatura);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerJefaturasProvinciales.");
		return jefaturas;
	}

	/**
	 * Obtenemos todas las jefaturas provinciales de la tabla JEFATURA
	 * 
	 * @param
	 * @return List<JefaturaTraficoDao>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<JefaturaTraficoDao> obtenerJefaturasProvincialesTodas() {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerProvincias.");
		List<JefaturaTraficoDao> jefaturas = new ArrayList<JefaturaTraficoDao>();

		List<JefaturaTraficoDto> jefaturasDto = servicioJefaturaTrafico.listadoJefaturas(null);

		if (null != jefaturasDto && !jefaturasDto.isEmpty()) {
			for (JefaturaTraficoDto JefaturaDto : jefaturasDto) {
				JefaturaTraficoDao jefatura = new JefaturaTraficoDao();
				jefatura.setDescripcion(JefaturaDto.getDescripcion());
				jefatura.setId_provincia(JefaturaDto.getProvinciaDto().getIdProvincia());
				jefatura.setJefatura(JefaturaDto.getJefatura());
				jefatura.setJefatura_provincial(JefaturaDto.getJefaturaProvincial());
				jefatura.setSucusal(JefaturaDto.getSucursal());
				jefaturas.add(jefatura);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerJefaturasProvinciales.");
		return jefaturas;
	}

	/**
	 * Obtenemos las oficinas liquidadoras de la tabla OFICINA_LIQUIDADORA_620
	 * 
	 * @param
	 * @return List<OficinaLiquidadoraDao>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<OficinaLiquidadoraDao> obtenerOficinasLiquidadoras() {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerOficinasLiquidadoras.");
		List<OficinaLiquidadoraDao> oficinas = new ArrayList<OficinaLiquidadoraDao>();

		List<OficinaLiquidadora620VO> oficinaLiquidadoras = servicioOficinaLiquidadora620.getOficinasLiquidadoras(null);

		if (null != oficinaLiquidadoras && !oficinaLiquidadoras.isEmpty()) {
			for (OficinaLiquidadora620VO oficinaVO : oficinaLiquidadoras) {
				OficinaLiquidadoraDao oficina = new OficinaLiquidadoraDao();
				oficina.setIdProvincia(oficinaVO.getId().getIdProvincia());
				oficina.setNombreOficinaLiq(oficinaVO.getNombreOficinaLiq());
				oficina.setOficinaLiquidadora(oficinaVO.getId().getOficinaLiquidadora());
				oficinas.add(oficina);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerOficinasLiquidadoras.");
		return oficinas;
	}

	/**
	 * Obtenemos los fundamentos de exención de la tabla FUNDAMENTO_EXENCION_620
	 * 
	 * @param
	 * @return List<FundamentoExencion>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<FundamentoExencion> obtenerFundamentosExencion() {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerFundamentosExencion.");
		List<FundamentoExencion> fundamentosExencion = new ArrayList<FundamentoExencion>();

		List<FundamentoExencion620VO> fundamentos620VO = servicioFundamentoExencion620.getlistaFundamentoExencion620();
		try {
			if (null != fundamentos620VO && !fundamentos620VO.isEmpty()) {
				for (FundamentoExencion620VO fundamento620 : fundamentos620VO) {
					FundamentoExencion fundamentoExencion = new FundamentoExencion();
					fundamentoExencion.setFundamentoExencion(fundamento620.getFundamentoExcencion());
					fundamentoExencion.setDescFundamentoExe(fundamento620.getDescripcion());
					fundamentosExencion.add(fundamentoExencion);
				}
			}
		} catch (Throwable e) {
			log.error("Error al obtenerFundamentosExencion" + e.getMessage(), e);
			return null;
		}
		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerFundamentosExencion.");
		return fundamentosExencion;
	}

	/**
	 * Obtenemos los fundamentos de no sujección de la tabla FUNDAMENTO_EXENCION_620
	 * 
	 * @param
	 * @return List<FundamentoExencion>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<FundamentoExencion> obtenerFundamentosNoSujeto() {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerFundamentosNoSujeccion.");
		List<FundamentoExencion> fundamentosExencion = new ArrayList<FundamentoExencion>();

		List<FundamentoExencion620VO> fundamentos620VO = servicioFundamentoExencion620.getlistaFundamentoNoSujeto620();
		try {
			if (null != fundamentos620VO && !fundamentos620VO.isEmpty()) {
				for (FundamentoExencion620VO fundamento620 : fundamentos620VO) {
					FundamentoExencion fundamentoExencion = new FundamentoExencion();
					fundamentoExencion.setFundamentoExencion(fundamento620.getFundamentoExcencion());
					fundamentoExencion.setDescFundamentoExe(fundamento620.getDescripcion());
					fundamentosExencion.add(fundamentoExencion);
				}
			}
		} catch (Throwable e) {
			log.error("Error al obtenerFundamentosNoSujeccion" + e.getMessage(), e);
			return null;
		}
		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerFundamentosNoSujeccion.");
		return fundamentosExencion;
	}

	public List<ClasificacionCriterioConstruccionBean> obtenerClasificacionesCriteriosConstruccionVehiculo() {

		log.info("obtenerClasificacionesCriteriosConstruccionVehiculo");
		List<ClasificacionCriterioConstruccionBean> clasificaciones = new ArrayList<ClasificacionCriterioConstruccionBean>();

		List<CriterioConstruccionDto> criteriosConstruccionDto = servicioCriterioConstruccion
				.getListadoCriterioConstruccion(null);

		if (null != criteriosConstruccionDto && !criteriosConstruccionDto.isEmpty()) {
			for (CriterioConstruccionDto criterioDto : criteriosConstruccionDto) {
				ClasificacionCriterioConstruccionBean clasificacionCriterioConstruccionBean = new ClasificacionCriterioConstruccionBean();

				clasificacionCriterioConstruccionBean
						.setIdCriterioConstruccion(criterioDto.getIdCriterioConstruccion());
				clasificacionCriterioConstruccionBean
						.setDescripcion(criterioDto.getIdCriterioConstruccion() + " - " + criterioDto.getDescripcion());
				clasificaciones.add(clasificacionCriterioConstruccionBean);
			}
		}

		log.info("fin de obtener clasificacion de criterios de construccion");
		return clasificaciones;
	}

	/**
	 * Obtenemos las tasas de la tabla TASA
	 * 
	 * @param
	 * @return List<Tasa>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<TasaDto> obtenerTasasBajas(BigDecimal idContrato) {
		log.info("ModeloTrafico: --- INICIO --- obtenerTasasBajas.");

		try {
			List<TasaVO> tasasVO = servicioPersistenciaTasas.getTasasLibres(idContrato.longValue(),
					TipoTasa.CuatroUno.getValorEnum());

			return conversor.transform(tasasVO, TasaDto.class);

		} catch (Throwable e) {
			log.error("Error al obtenerTasasBajas" + e.getMessage(), e);
			return null;
		}
	}

	public List<TasaDto> obtenerTasasDuplicados(BigDecimal idContrato) {
		log.info("ModeloTrafico: --- INICIO --- obtenerTasasDuplicados.");

		try {
			List<TasaVO> tasasVO = servicioPersistenciaTasas.getTasasLibres(idContrato.longValue(),
					TipoTasa.CuatroCuatro.getValorEnum());

			return conversor.transform(tasasVO, TasaDto.class);

		} catch (Throwable e) {
			log.error("Error al obtenerTasasDuplicados" + e.getMessage(), e);
			return null;
		}
	}

	public boolean hayEnvio(TipoProceso tipoEnvioData) throws Exception, OegamExcepcion {
		log.info("ModeloTrafico: --- INICIO --- obtener si hay envio de: " + tipoEnvioData.getNombreEnum());

		List<EnvioDataVO> envios = servicioProcesos.ejecucionesUltimasProceso(tipoEnvioData.getValorEnum(), ConstantesProcesos.EJECUCION_CORRECTA);

		if (null != envios && !envios.isEmpty()) {

			for (EnvioDataVO envio : envios) {
				if ((utilesFecha.formatoFecha(envio.getFechaEnvio())).equals(utilesFecha.formatoFecha(new Date()))) {
					return true;
				}
			}
		}

		if (tipoEnvioData == TipoProceso.Primera_Matriculacion) {
			VehiculoDto vehiculoDto = servicioVehiculo.primeraMatricula(new Date());
			if (StringUtils.isNotBlank(vehiculoDto.getMatricula())) {
				return false;
			} else {
				throw new Exception("Cancelado el envío. Aún no se ha procesado la primera matriculación.");
			}
		} else {
			return false;
		}
	}

	public List<TasaDto> obtenerTasasMatriculacion(BigDecimal idContratoTramite, String tipoTasa) {
		log.info("ModeloTrafico: --- INICIO --- obtenerTasasMatriculacion.");

		try {
			List<TasaVO> tasasVO = servicioPersistenciaTasas.getTasasLibres(idContratoTramite.longValue(), tipoTasa);

			return conversor.transform(tasasVO, TasaDto.class);

		} catch (Throwable e) {
			log.error("Error al obtenerTasasMatriculacion" + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<TasaDto> getTasasLibresMatriculacion(BigDecimal idContratoTramite) {
		log.info("ModeloTrafico: --- INICIO --- obtenerTasasMatriculacion.");

		try {
			List<TasaVO> tasasVO = servicioPersistenciaTasas.getTasasLibresMatriculacion(idContratoTramite.longValue());

			return conversor.transform(tasasVO, TasaDto.class);

		} catch (Throwable e) {
			log.error("Error al obtenerTasasMatriculacion" + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<TasaDto> obtenerCodigosTasaLibres(BigDecimal idContrato) {
		log.info("ModeloTrafico: --- INICIO --- obtenerCodigosTasaLibres.");

		try {
			List<TasaVO> tasasVO = servicioPersistenciaTasas.getTasasLibres(idContrato.longValue(), null);

			return conversor.transform(tasasVO, TasaDto.class);

		} catch (Throwable e) {
			log.error("Error al obtenerTasasLibres" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Obtenemos los motivos de ITV
	 * 
	 * @param
	 * @return List<MotivoBean>. En caso de error devuelve null.
	 * @throws OegamExcepcion
	 */
	public List<MotivoBean> obtenerMotivosITV() {
		log.info(Claves.CLAVE_LOG_ESCRITURA600_INICIO + "obtenerMotivosITV.");
		List<MotivoBean> motivos = new ArrayList<MotivoBean>();

		List<MotivoItvVO> motivosVO = servicioMotivoItv.getListaMotivoItv();

		if (null != motivosVO && !motivosVO.isEmpty()) {
			for (MotivoItvVO motivoVO : motivosVO) {
				MotivoBean motivo = new MotivoBean();
				motivo.setIdMotivo(motivoVO.getIdMotivoItv());
				motivo.setDescripcion(motivoVO.getDescripcion());
				motivos.add(motivo);
			}
		}

		log.info(Claves.CLAVE_LOG_ESCRITURA600_FIN + "obtenerMotivosITV.");
		return motivos;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {

		ListaRegistros listaRegistros = new ListaRegistros();

		// Datos de la búsqueda.
		// Datos que sacamos de la sesión para la búsqueda
		BeanPQTramiteTraficoBuscar beanConsultaTramite = new BeanPQTramiteTraficoBuscar();
		beanConsultaTramite.setP_ID_CONTRATO(null);
		beanConsultaTramite.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanConsultaTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanConsultaTramite.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());

		// Datos de paginación, que pasamos por defecto
		beanConsultaTramite.setPAGINA(new BigDecimal(pagina));
		beanConsultaTramite.setNUM_REG(new BigDecimal(numeroElementosPagina));
		beanConsultaTramite.setCOLUMNA_ORDEN(columnaOrden);
		beanConsultaTramite.setORDEN(orden.toString().toUpperCase());

		// Datos de búsqueda del formulario.
		beanConsultaTramite.setP_NIF((String) getParametrosBusqueda().get(ConstantesSession.NIF_TITULAR_CONSULTA));
		Integer estado = (Integer) getParametrosBusqueda().get(ConstantesSession.ESTADO_TRAMITE_CONSULTA);
		beanConsultaTramite.setP_ESTADO(utiles.convertirIntegerABigDecimal(estado));
		beanConsultaTramite
				.setP_TIPO_TRAMITE((String) getParametrosBusqueda().get(ConstantesSession.TIPO_TRAMITE_CONSULTA));
		beanConsultaTramite
				.setP_NUM_COLEGIADO((String) getParametrosBusqueda().get(ConstantesSession.NUM_COLEGIADO_CONCULTA));
		beanConsultaTramite
				.setP_REF_PROPIA((String) getParametrosBusqueda().get(ConstantesSession.REFERENCIA_PROPIA_CONSULTA));
		beanConsultaTramite.setP_NUM_EXPEDIENTE(
				(BigDecimal) getParametrosBusqueda().get(ConstantesSession.NUM_EXPEDIENTE_CONSULTA));
		String matricula = (String) getParametrosBusqueda().get(ConstantesSession.MATRICULA_CONSULTA);
		if (matricula != null) {
			matricula = matricula.toUpperCase();
		}
		beanConsultaTramite.setP_MATRICULA(matricula);
		String bastidor = (String) getParametrosBusqueda().get(ConstantesSession.NUM_BASTIDOR_CONSULTA);
		if (bastidor != null) {
			bastidor = bastidor.toUpperCase();
		}
		beanConsultaTramite.setP_BASTIDOR(bastidor);
		beanConsultaTramite.setP_NIVE((String) getParametrosBusqueda().get(ConstantesSession.NIVE_CONSULTA));
		beanConsultaTramite
				.setP_NIF_FACTURACION((String) getParametrosBusqueda().get(ConstantesSession.NIF_FACTURACION_CONSULTA));

		beanConsultaTramite.setP_TIPO_TASA((String) getParametrosBusqueda().get(ConstantesSession.TIPO_TASA_CONSULTA));

		FechaFraccionada fechaAltaTramite = (FechaFraccionada) getParametrosBusqueda().get("fechaAltaTramite");

		Timestamp fechaAltaTramiteDesde = null;
		Timestamp fechaAltaTramiteHasta = null;

		if (fechaAltaTramite != null) {
			Date fechaInicio = null;
			if ((fechaInicio = fechaAltaTramite.getFechaInicio()) != null) {
				fechaAltaTramiteDesde = utilesFecha.getTimestamp(fechaInicio);
			}

			Date fechaFin = null;
			if ((fechaFin = fechaAltaTramite.getFechaFin()) != null) {
				fechaAltaTramiteHasta = utilesFecha.getTimestamp(fechaFin);
			}
		}

		beanConsultaTramite.setP_FECHA_ALTA_DESDE(fechaAltaTramiteDesde);
		beanConsultaTramite.setP_FECHA_ALTA_HASTA(fechaAltaTramiteHasta);

		FechaFraccionada fechaPresentacionTramite = (FechaFraccionada) getParametrosBusqueda()
				.get("fechaPresentacionTramite");

		Timestamp fechaPresentacionTramiteDesde = null;
		Timestamp fechaPresentacionTramiteHasta = null;

		if (fechaPresentacionTramite != null) {
			Date fechaInicio = null;
			if ((fechaInicio = fechaPresentacionTramite.getFechaInicio()) != null) {
				fechaPresentacionTramiteDesde = utilesFecha.getTimestamp(fechaInicio);
			}

			Date fechaFin = null;
			if ((fechaFin = fechaPresentacionTramite.getFechaFin()) != null) {
				fechaPresentacionTramiteHasta = utilesFecha.getTimestamp(fechaFin);
			}
		}

		beanConsultaTramite.setP_FECHA_PRESENTACION_DESDE(fechaPresentacionTramiteDesde);
		beanConsultaTramite.setP_FECHA_PRESENTACION_HASTA(fechaPresentacionTramiteHasta);

		// Realiza la llamada genérica para la consulta
		RespuestaGenerica resultadoBusquedaTramiteTrafico = ejecutarProc(beanConsultaTramite,
				valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "BUSCAR",
				BeanPQTramiteBuscarResult.class);

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal) resultadoBusquedaTramiteTrafico.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM
				+ (String) resultadoBusquedaTramiteTrafico.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultadoBusquedaTramiteTrafico.getParametro("CUENTA"));

		List<Object> listaCursor = resultadoBusquedaTramiteTrafico.getListaCursor();
		List<Object> listaBeanVista = new ArrayList<Object>();
		TramiteTraficoBean linea;

		for (Object object : listaCursor) {
			linea = new TramiteTraficoBean(true);
			BeanPQTramiteBuscarResult deCursor = (BeanPQTramiteBuscarResult) object;
			linea = tramiteTraficoBeanPQConversion.convertirCursorToBean(deCursor);
			listaBeanVista.add(linea);
		}

		BigDecimal tamRegs = (BigDecimal) resultadoBusquedaTramiteTrafico.getParametro("CUENTA");// listaCursor.size();
		Integer tamanio = utiles.convertirBigDecimalAInteger(tamRegs);
		listaRegistros.setTamano(tamanio != null ? tamanio: 0);

		listaRegistros.setLista(listaBeanVista);

		return listaRegistros;
	}

	/**
	 * Método que permite buscar un trámite pasándole el número de expediente.
	 * 
	 * @param numExpediente
	 */
	public TramiteTraficoBean buscarTramitePorNumExpediente(String numExpediente) {
		BeanPQTramiteTraficoBuscar beanConsultaTramite = new BeanPQTramiteTraficoBuscar();

		beanConsultaTramite.setP_NUM_EXPEDIENTE(new BigDecimal(numExpediente));
		beanConsultaTramite.setP_ID_CONTRATO(null);
		beanConsultaTramite.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanConsultaTramite.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (utilesColegiado.tienePermisoAdmin() || utilesColegiado.tienePermisoEspecial()
				|| utilesColegiado.tienePermisoColegio()) {
			beanConsultaTramite.setP_NUM_COLEGIADO("");
		} else {
			beanConsultaTramite.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
		}

		// Realiza la llamada genérica para la consulta
		RespuestaGenerica resultadoBusquedaTramiteTrafico = ejecutarProc(beanConsultaTramite,
				valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO, "BUSCAR",
				BeanPQTramiteBuscarResult.class);

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal) resultadoBusquedaTramiteTrafico.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM
				+ (String) resultadoBusquedaTramiteTrafico.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultadoBusquedaTramiteTrafico.getParametro("CUENTA"));

		List<Object> listaCursor = resultadoBusquedaTramiteTrafico.getListaCursor();
		TramiteTraficoBean linea = null;

		for (Object object : listaCursor) {
			linea = new TramiteTraficoBean(true);
			BeanPQTramiteBuscarResult deCursor = (BeanPQTramiteBuscarResult) object;
			linea = tramiteTraficoBeanPQConversion.convertirCursorToBean(deCursor);
		}
		return linea;
	}

	/**
	 * Método que eliminará el registro que le pasemos como parámetro mediante el
	 * bean.
	 * 
	 * @param beanEliminar
	 * @return
	 */

	public RespuestaGenerica eliminarTramiteTrafico(BeanPQTramiteTraficoEliminar beanEliminar) {
		beanEliminar.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Anulado.getValorEnum()));

		RespuestaGenerica resultadoEliminar = ejecutarProc(beanEliminar, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_TRAMITE_TRAFICO, "CAMB_ESTADO", BeanPQGeneral.class);

		BigDecimal pCodeTramite = (BigDecimal) resultadoEliminar.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoEliminar.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + resultadoEliminar.getParametro("P_INFORMACION"));

		return resultadoEliminar;
	}

	public HashMap<String, Object> cambiarEstadoTramite(BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite) {

		ResultBean resultBean = new ResultBean();
		BeanPQCambiarEstadoTramite beanPQ = new BeanPQCambiarEstadoTramite();

		beanPQCambiarEstadoTramite.setP_RESPUESTA(
				LongitudCampo.truncarString(beanPQCambiarEstadoTramite.getP_RESPUESTA(), TamanoP_Respuesta));

		RespuestaGenerica respuestaCambioEstado = ejecutarProc(beanPQCambiarEstadoTramite, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_TRAMITE_TRAFICO, "CAMB_ESTADO", BeanPQGeneral.class);

		// 05/02/2014 Mantis : 7128 Ricardo Rodriguez
		if (respuestaCambioEstado == null || respuestaCambioEstado.getParametro(ConstantesPQ.P_CODE) == null) {
			resultBean.setMensaje("Error desconocido al cambiar el estado del tramite");
			resultBean.setError(true);
			beanPQ = beanPQCambiarEstadoTramite;
			// Si entra aquí, hay un error en PQ_TRAMITE_TRAFICO.CAMB_ESTADO. Trazas para
			// depurar el pq en desarrollo
			log.error("ERROR EN PQ_TRAMITE_TRAFICO.CAMB_ESTADO CON LOS PARAMETROS:");
			log.error("P_INFORMACION : " + beanPQCambiarEstadoTramite.getP_INFORMACION());
			log.error("P_SQLERRM : " + beanPQCambiarEstadoTramite.getP_SQLERRM());
			log.error("P_CODE : " + beanPQCambiarEstadoTramite.getP_CODE());
			log.error("P_ESTADO : " + beanPQCambiarEstadoTramite.getP_ESTADO());
			log.error("P_FECHA_ULT_MODIF : " + beanPQCambiarEstadoTramite.getP_FECHA_ULT_MODIF());
			log.error("" + beanPQCambiarEstadoTramite.getP_ID_USUARIO());
			log.error("" + beanPQCambiarEstadoTramite.getP_NUM_EXPEDIENTE());
			log.error("" + beanPQCambiarEstadoTramite.getP_RESPUESTA());
		}
		// Fin Mantis : 7128 (para restaurar quitar tambien el else de la línea de abajo
		// y dejar el if)
		else if ((!ConstantesPQ.pCodeOk.equals(respuestaCambioEstado.getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje("Error al cambiar el estado del tramite: "
					+ respuestaCambioEstado.getParametro(ConstantesPQ.P_SQLERRM));
			resultBean.setError(true);
			beanPQ = beanPQCambiarEstadoTramite;
		} else {
			resultBean.setMensaje("Estado cambiado correctamente a: " + respuestaCambioEstado.getParametro("P_ESTADO"));
			resultBean.setError(false);
			beanPQ.setP_ESTADO(respuestaCambioEstado.getParametro("P_ESTADO"));
			beanPQ.setP_NUM_EXPEDIENTE(respuestaCambioEstado.getParametro("P_NUM_EXPEDIENTE"));
			beanPQ.setP_ID_USUARIO(respuestaCambioEstado.getParametro("P_ID_USUARIO"));
			beanPQ.setP_FECHA_ULT_MODIF(respuestaCambioEstado.getParametro("P_FECHA_ULT_MODIF"));
			// beanPQ.setROWID_TRAMITE(respuestaCambioEstado.getParametro("ROWID_TRAMITE"));
			beanPQ.setP_RESPUESTA(respuestaCambioEstado.getParametro("P_RESPUESTA"));
		}

		HashMap<String, Object> resultadoCambioEstadoTramite = new HashMap<String, Object>();

		resultadoCambioEstadoTramite.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultadoCambioEstadoTramite.put(ConstantesPQ.BEANPQRESULTADO, beanPQ);

		return resultadoCambioEstadoTramite;
	}

	public BeanPQCambiarEstadoTramite cambiarEstadoTramite(String pRespuesta, EstadoTramiteTrafico estado,
			BigDecimal idExpediente, BigDecimal idUsuario) throws CambiarEstadoTramiteTraficoExcepcion {

		ResultBean resultBean = new ResultBean();
		BeanPQCambiarEstadoTramite beanPQ = new BeanPQCambiarEstadoTramite();
		beanPQ.setP_ESTADO(new BigDecimal(estado.getValorEnum()));
		beanPQ.setP_RESPUESTA(LongitudCampo.truncarString(pRespuesta, TamanoP_Respuesta));
		beanPQ.setP_NUM_EXPEDIENTE(idExpediente);
		if (idUsuario != null) {
			beanPQ.setP_ID_USUARIO(idUsuario);
		}

		RespuestaGenerica respuestaCambioEstado = ejecutarProc(beanPQ, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_TRAMITE_TRAFICO, "CAMB_ESTADO", BeanPQGeneral.class);

		if ((!ConstantesPQ.pCodeOk.equals(respuestaCambioEstado.getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje("Error al cambiar el estado del tramite: "
					+ respuestaCambioEstado.getParametro(ConstantesPQ.P_SQLERRM));
			throw new CambiarEstadoTramiteTraficoExcepcion("Error al cambiar el estado del tramite:"
					+ (String) respuestaCambioEstado.getParametro(ConstantesPQ.P_SQLERRM));
		} else {
			resultBean.setMensaje("Estado cambiado correctamente a: " + respuestaCambioEstado.getParametro("P_ESTADO"));
			resultBean.setError(false);
			beanPQ.setP_ESTADO(respuestaCambioEstado.getParametro("P_ESTADO"));
			beanPQ.setP_NUM_EXPEDIENTE(respuestaCambioEstado.getParametro("P_NUM_EXPEDIENTE"));
			beanPQ.setP_ID_USUARIO(respuestaCambioEstado.getParametro("P_ID_USUARIO"));
			beanPQ.setP_FECHA_ULT_MODIF(respuestaCambioEstado.getParametro("P_FECHA_ULT_MODIF"));
			// beanPQ.setROWID_TRAMITE(respuestaCambioEstado.getParametro("ROWID_TRAMITE"));
			beanPQ.setP_RESPUESTA(respuestaCambioEstado.getParametro("P_RESPUESTA"));
		}

		return beanPQ;
	}

	public HashMap<String, Object> obtenerDatosColegio(BeanPQDatosColegio beanPQDatosColegio) {

		ResultBean resultBean = new ResultBean();
		beanPQDatosColegio.setP_COLEGIO("ICOGAM");
		ColegioBean colegioBean = new ColegioBean();
		RespuestaGenerica resultadoDatosColegio = ejecutarProc(beanPQDatosColegio, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_CONTRATOS, "DATOS_COLEGIO", BeanPQGeneral.class);

		if ((!ConstantesPQ.pCodeOk.equals(resultadoDatosColegio.getParametro(ConstantesPQ.P_CODE)))) {
			resultBean.setMensaje("Error al recuperar informacion del colegio");
			resultBean.setError(true);
			resultBean.setMensaje(resultBean.getMensaje() + " ");
		} else {
			resultBean.setMensaje("Info del colegio recuperada Correctamente");
		}

		HashMap<String, Object> resultadoColegio = new HashMap<String, Object>();
		colegioBean = ColegioBeanPQConversion.convertirPQToColegio(resultadoDatosColegio);

		resultadoColegio.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultadoColegio.put(ConstantesPQ.BEANPANTALLA, colegioBean);

		return resultadoColegio;
	}

	/**
	 * Método de validación de un trámite
	 * 
	 * @param traficoTramiteBean a validar
	 * @return ResultBean con el resultado de la validación
	 */
	public ResultBean validarTramite(TramiteTraficoBean traficoTramiteBean) {

		List<String> errores = new ArrayList<String>();
		BeanPQValidarTramite beanPQ = new BeanPQValidarTramite();
		ResultBean resultadoValidarTramite = new ResultBean();

//		beanPQ.setROWID_TRAMITE(traficoTramiteBean.getRowIdTraficoTramite()!=null && !traficoTramiteBean.getRowIdTraficoTramite().equals("")?traficoTramiteBean.getRowIdTraficoTramite():null);
		beanPQ.setP_NUM_EXPEDIENTE(
				traficoTramiteBean.getNumExpediente() != null && !traficoTramiteBean.getNumExpediente().equals("")
						? traficoTramiteBean.getNumExpediente()
						: null);
		beanPQ.setP_ESTADO(traficoTramiteBean.getEstado() != null && !traficoTramiteBean.getEstado().equals("-1")
				? new BigDecimal(traficoTramiteBean.getEstado().getValorEnum())
				: null);
		beanPQ.setP_ID_USUARIO(traficoTramiteBean.getIdUsuario() != null ? traficoTramiteBean.getIdUsuario()
				: utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQ.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		try {
			beanPQ.setP_FECHA_ULT_MODIF(
					traficoTramiteBean.getFechaUltModif() != null ? traficoTramiteBean.getFechaUltModif().getTimestamp()
							: null);
		} catch (ParseException e) {
			beanPQ.setP_FECHA_ULT_MODIF(null);
		}
		if (traficoTramiteBean.getVehiculo().getRevisado() != null
				&& traficoTramiteBean.getVehiculo().getRevisado() == true) {
			beanPQ.setP_REVISADO(traficoTramiteBean.getVehiculo().getRevisado().toString());
		} else {
			beanPQ.setP_REVISADO(Boolean.FALSE.toString());
		}

		// Ejecutamos validar
		RespuestaGenerica resultadoValidacion = null;
		if (!isMatw(Long.valueOf(traficoTramiteBean.getNumExpediente().toString()))) {
			resultadoValidacion = ejecutarProc(beanPQ, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,
					"VALIDAR_TRAMITE", BeanPQGeneral.class);
		} else {
			resultadoValidacion = ejecutarProc(beanPQ, valoresSchemas.getSchema(), ValoresCatalog.PQ_TRAMITE_TRAFICO,
					"VALIDAR_TRAMITE_MATW", BeanPQGeneral.class);
		}

		// Recuperamos información de respuesta
		BigDecimal pCode = (BigDecimal) resultadoValidacion.getParametro(ConstantesPQ.P_CODE);
		String sqlErrm = (String) resultadoValidacion.getParametro(ConstantesPQ.P_SQLERRM);
		String pInformacion = (String) resultadoValidacion.getParametro(ConstantesPQ.P_INFORMACION);

		// Ha habido errores ...
		if (!pCode.equals(new BigDecimal(0))) {
			String[] erroresAux = sqlErrm.split(" - ");
			for (String error : erroresAux) {
				if (!error.trim().equals("")) {
					errores.add(error.trim());
				}
			}
			// DRC@01-04-2013 Incidencia Mantis: 4044
			resultadoValidarTramite.setError(Boolean.TRUE);
			resultadoValidarTramite.setListaMensajes(errores);
		}

		// DRC@02-04-2013 Incidencia Mantis: 4044 - Los mensajes informativos SÓLO se
		// muestran cuando no hay errores.
		if (errores.size() == 0 && pInformacion != null && !pInformacion.equals("")) {
			// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
			String[] erroresAux = pInformacion.split(" - ");
			for (String error : erroresAux) {
				if (!error.trim().equals("")) {
					errores.add(error.trim());
				}
			}
			// DRC@01-04-2013 Incidencia Mantis: 4044
			resultadoValidarTramite.setError(Boolean.FALSE);
			resultadoValidarTramite.setListaMensajes(errores);
			// errores.add(pInformacion);
		}

		// Refrescamos el estado del trámite...
		traficoTramiteBean.setEstado(((BigDecimal) resultadoValidacion.getParametro("P_ESTADO")).toString());

		return resultadoValidarTramite;
	}

	/**
	 * Método que valida un tramite y devuelve un ResultBean con los mensajes de
	 * esta validación.
	 * 
	 * @param traficoTramiteBean
	 * @return ResultBean
	 */
	public ResultBean validaTramite(TramiteTraficoBean traficoTramiteBean) {
		List<String> errores = new ArrayList<String>();
		BeanPQValidarTramite beanPQ = new BeanPQValidarTramite();
		ResultBean resultadoMetodo = new ResultBean();

		TramiteTrafTranDto trafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(traficoTramiteBean.getNumExpediente(), Boolean.FALSE);
//		beanPQ.setROWID_TRAMITE(traficoTramiteBean.getRowIdTraficoTramite()!=null && !traficoTramiteBean.getRowIdTraficoTramite().equals("")?traficoTramiteBean.getRowIdTraficoTramite():null);
		beanPQ.setP_NUM_EXPEDIENTE(
				traficoTramiteBean.getNumExpediente() != null && !traficoTramiteBean.getNumExpediente().equals("")
						? traficoTramiteBean.getNumExpediente()
						: null);
		beanPQ.setP_ESTADO(traficoTramiteBean.getEstado() != null && !traficoTramiteBean.getEstado().equals("-1")
				? new BigDecimal(traficoTramiteBean.getEstado().getValorEnum())
				: null);
		beanPQ.setP_ID_USUARIO(traficoTramiteBean.getIdUsuario() != null ? traficoTramiteBean.getIdUsuario()
				: utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQ.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		try {
			beanPQ.setP_FECHA_ULT_MODIF(
					traficoTramiteBean.getFechaUltModif() != null ? traficoTramiteBean.getFechaUltModif().getTimestamp()
							: null);
		} catch (ParseException e) {
			beanPQ.setP_FECHA_ULT_MODIF(null);
		}

		// Ejecutamos validar
		RespuestaGenerica resultadoValidacion = ejecutarProc(beanPQ, valoresSchemas.getSchema(),
				ValoresCatalog.PQ_TRAMITE_TRAFICO, "VALIDAR_TRAMITE", BeanPQGeneral.class);

		// Recuperamos información de respuesta
		BigDecimal pCode = (BigDecimal) resultadoValidacion.getParametro(ConstantesPQ.P_CODE);
		String sqlErrm = (String) resultadoValidacion.getParametro(ConstantesPQ.P_SQLERRM);
		String pInformacion = (String) resultadoValidacion.getParametro(ConstantesPQ.P_INFORMACION);

		if (!pCode.equals(new BigDecimal(0))) { // Ha habido errores...
			resultadoMetodo.setError(true);
			String[] erroresAux = sqlErrm.split(" - ");
			for (String error : erroresAux) {
				if (!error.trim().equals("")) {
					errores.add(error.trim());
				}
			}
		} else {
			resultadoMetodo.setError(false);
		}

		if (pInformacion != null && !pInformacion.equals("")) {
			String[] erroresAux = pInformacion.split(" - ");
			for (String error : erroresAux) {
				if (!error.trim().equals("")) {
					errores.add(error.trim());
				}
			}
		}

		// Refrescamos el estado del trámite...
		traficoTramiteBean.setEstado(((BigDecimal) resultadoValidacion.getParametro("P_ESTADO")).toString());
		Long estadoNuevo = null;
		estadoNuevo = new Long(traficoTramiteBean.getEstado().toString());
		if ("Correcto".equals(sqlErrm)) {
			if(trafTranDto != null && utiles.esUsuarioIvtm(trafTranDto.getNumColegiado())) {
				if((EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum().equalsIgnoreCase(trafTranDto.getEstado().toString()) && "SI".equalsIgnoreCase(trafTranDto.getAcreditacionPago())
						&& !TipoTransferencia.tipo5.getValorEnum().equalsIgnoreCase(trafTranDto.getTipoTransferencia()))) {
					estadoNuevo = new Long(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum());
					traficoTramiteBean.setEstado(estadoNuevo.toString());
					// Si existe en la evolución una autorización colegial se valida telemáticamente
					List<EvolucionTramiteTraficoVO> evolucion = servicioEvolucionTramite.getTramiteFinalizadoErrorAutorizado(traficoTramiteBean.getNumExpediente());
					if (evolucion != null && !evolucion.isEmpty()) {
						estadoNuevo = new Long(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
						traficoTramiteBean.setEstado(estadoNuevo.toString());
					}
				}else if(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum().equalsIgnoreCase(trafTranDto.getEstado().toString()) && "SI".equalsIgnoreCase(trafTranDto.getAcreditacionPago())
						&& TipoTransferencia.tipo5.getValorEnum().equalsIgnoreCase(trafTranDto.getTipoTransferencia())) {
					estadoNuevo = new Long(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
					traficoTramiteBean.setEstado(estadoNuevo.toString());
				}
				servicioTramiteTrafico.cambiarEstadoConEvolucion(traficoTramiteBean.getNumExpediente(),EstadoTramiteTrafico.convertir(trafTranDto.getEstado()), EstadoTramiteTrafico.convertir(estadoNuevo.toString()), false, null, (BigDecimal)resultadoValidacion.getParametro("P_ID_USUARIO"));
			}
		}
		resultadoMetodo.addListaMensajes(errores);
		return resultadoMetodo;
	}

	/**
	 * Método que valida un tramite y devuelve un ResultBean con los mensajes de
	 * esta validación.
	 * 
	 * @param traficoTramiteBean
	 * @param numColegiado
	 * @param idContrato
	 * @return ResultBean
	 */
	@SuppressWarnings("unchecked")
	public ResultBean validaTramiteGenerico(TramiteTraficoBean traficoTramiteBean, String numColegiado,
			BigDecimal idContrato) throws Throwable {
		ResultBean resultadoMetodo = new ResultBean();
		ResultBean resultBeanDetalle = new ResultBean();

		// Obtenemos el tipo del trámite para hacer una u otra validación.
		TipoTramiteTrafico tipo = traficoTramiteBean.getTipoTramite();

		// Matriculación

		if (tipo.getValorEnum().equals(TipoTramiteTrafico.Matriculacion.getValorEnum())) {
			TramiteTraficoMatriculacionBean beanMatriculacion = new TramiteTraficoMatriculacionBean(true);

			if (!isMatw((traficoTramiteBean.getNumExpediente().longValue()))) {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("Sólo se puede validar un trámite de MATW");
				return resultadoMetodo;
			} else {
				Map<String, Object> resultadoDetalle = getModeloMatriculacion()
						.obtenerDetalle(traficoTramiteBean.getNumExpediente(), numColegiado, idContrato);
				resultBeanDetalle = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);

				if (null != resultBeanDetalle && !resultBeanDetalle.getError()) {
					beanMatriculacion = (TramiteTraficoMatriculacionBean) resultadoDetalle
							.get(ConstantesPQ.BEANPANTALLA);
					resultadoMetodo = getModeloMatriculacion().validar(beanMatriculacion);
					if (!resultadoMetodo.getError()) {
						if (beanMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum().equals(
								resultadoMetodo.getMensaje().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())))
							resultadoMetodo.setMensaje(EstadoTramiteTrafico.Validado_PDF.getValorEnum());
						else if (beanMatriculacion.getTramiteTraficoBean().getEstado().getValorEnum()
								.equals(resultadoMetodo.getMensaje()
										.equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())))
							resultadoMetodo.setMensaje(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
					}
					if (resultadoMetodo.getError()
							&& (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoMetodo.getMensaje())
									|| EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()
											.equals(resultadoMetodo.getMensaje()))) {
						resultadoMetodo.setError(false);
					}
					if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoMetodo.getMensaje())) {
						ResultBean resultadoJaxb = getModeloMatriculacion()
								.generaXMLMatriculacionNuevo(beanMatriculacion);
						// Mensajes de error en la validacion JAXB
						if (resultadoJaxb.getError()) {
							if (resultadoJaxb.getListaMensajes() != null) {
								for (String mensaje : resultadoJaxb.getListaMensajes()) {
									resultadoMetodo.getListaMensajes().add(mensaje);
								}
							}
						}
					}
				} else {
					resultadoMetodo.setError(true);
					resultadoMetodo.setMensaje("No se ha podido recuperar el Bean de Matriculación");
					return resultadoMetodo;
				}
			}
		}

		// Transmisión y transmisión electrónica
		if (TipoTramiteTrafico.Transmision.equals(tipo) || TipoTramiteTrafico.TransmisionElectronica.equals(tipo)) {
			TramiteTraficoTransmisionBean beanTransmision = new TramiteTraficoTransmisionBean();

			Map<String, Object> resultadoDetalle = getModeloTransmision()
					.obtenerDetalle(traficoTramiteBean.getNumExpediente());
			resultBeanDetalle = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);

			if (null != resultBeanDetalle && !resultBeanDetalle.getError()) {
				beanTransmision = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);

				if ((TipoTransferencia.tipo4.equals(beanTransmision.getTipoTransferencia())
						|| TipoTransferencia.tipo5.equals(beanTransmision.getTipoTransferencia()))
						&& "SI".equals(beanTransmision.getImpresionPermiso())) {
					resultadoMetodo.setError(true);
					resultadoMetodo.setMensaje("Debe desmarcar el check de Impresión de Permisos.");
				} else {
					resultadoMetodo = getModeloTransmision().validar(beanTransmision);
					if (resultadoMetodo.getError()
							&& (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoMetodo.getMensaje())
									|| EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()
											.equals(resultadoMetodo.getMensaje()))) {
						resultadoMetodo.setError(false);
					}

					if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(resultadoMetodo.getMensaje())
							&& TipoTramiteTrafico.TransmisionElectronica.equals(tipo)) {

						// Generamos XML con firma, y se custodia en servidor de documentos
						Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLTransmisionNoTelematica(beanTransmision);

						if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha habido errores, los mostramos...
							List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
							resultadoMetodo.addListaMensajes(errores);
						}
					} else if (TipoTramiteTrafico.Transmision.equals(tipo) && EstadoTramiteTrafico.Validado_PDF
							.equals(beanTransmision.getTramiteTraficoBean().getEstado())) {
						// Generamos XML con firma, y se custodia en servidor de documentos
						Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision()
								.generarXMLTransmisionNoTelematica(beanTransmision);

						if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha habido errores, los mostramos...
							List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
							resultadoMetodo.addListaMensajes(errores);
						}
					}
				}
			} else {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("No se ha podido recuperar el Bean de Transmisión");
				return resultadoMetodo;
			}

		}

		// Solicitud
		if (TipoTramiteTrafico.Solicitud.equals(tipo)) {
			resultadoMetodo = validaTramite(traficoTramiteBean);
			resultadoMetodo.setMensaje("Tramite Solicitud.");
		}

		return resultadoMetodo;
	}

	public HashMap<String, Object> obtenerDescripcionesPorNumExpediente(BigDecimal numExpediente) {
		HashMap<String, Object> resultado = new HashMap<String, Object>();
		ResultBean resultBean = new ResultBean();
		DescripcionesTraficoBean descripcionesBean = new DescripcionesTraficoBean();
		BeanPQDescripcionesTrafico beanConsultaTramite = new BeanPQDescripcionesTrafico();
		RespuestaGenerica resultadoDescripcionesTrafico;

		beanConsultaTramite.setP_NUM_EXPEDIENTE(numExpediente);

		// Distinguir las descripciones de MATE 2.5 y MATW
		if (!isMatw(Long.valueOf(numExpediente.toString()))) {
			resultadoDescripcionesTrafico = ejecutarProc(beanConsultaTramite, valoresSchemas.getSchema(),
					ValoresCatalog.PQ_TRAMITE_TRAFICO, "DESCRIPCIONES", BeanPQGeneral.class);
		} else {
			resultadoDescripcionesTrafico = ejecutarProc(beanConsultaTramite, valoresSchemas.getSchema(),
					ValoresCatalog.PQ_TRAMITE_TRAFICO, "DESCRIPCIONES_MATW", BeanPQGeneral.class);
		}

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal) resultadoDescripcionesTrafico.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM
				+ (String) resultadoDescripcionesTrafico.getParametro(ConstantesPQ.P_SQLERRM));

		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)) {
			resultBean.setMensaje("Error al recuperar información de descripciones");
			resultBean.setError(true);
			resultBean.setMensaje(resultBean.getMensaje() + " ");
		} else {
			resultBean.setMensaje("Descripciones recuperadas Correctamente");
		}

		// Recuperamos los datos de las descripciones.

		descripcionesBean = DescripcionBeanPQConversion.convertirPQToBean(resultadoDescripcionesTrafico);

		resultado.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultado.put(ConstantesPQ.BEANPANTALLA, descripcionesBean);

		return resultado;
	}

	public HashMap<String, Object> obtenerDescripcionesPorIdDireccion(BigDecimal idDireccion) {
		HashMap<String, Object> resultadoMetodo = new HashMap<>();
		DescripcionesDireccionBean beanPantalla = new DescripcionesDireccionBean();
		BeanPQDescripcionesDireccion beanPQ = new BeanPQDescripcionesDireccion();
		ResultBean resultado = new ResultBean();
		Boolean error = false;
		String mensaje = "";

		beanPQ.setP_ID_DIRECCION(idDireccion);

		// Ejecución genérica del acceso a la base de datos
		RespuestaGenerica resultadoBD = ejecutarProc(beanPQ, valoresSchemas.getSchema(), ValoresCatalog.PQ_DIRECCION,
				"DESCRIPCIONES", BeanPQGeneral.class);

		// Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal) resultadoBD.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String) resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_CUENTA + resultadoBD.getParametro("CUENTA"));

		// Se controla el error y se actualiza para su devolucion
		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)) {
			error = true;
			mensaje += ("Error al obtener las descripciones de las direcciones: "
					+ (String) resultadoBD.getParametro(ConstantesPQ.P_SQLERRM));
		}

		// Convertimos y rellenamos los datos del bean
		beanPantalla.setIdDireccion(idDireccion);
		beanPantalla.setIdMunicipio((String) resultadoBD.getParametro("P_ID_MUNICIPIO"));
		beanPantalla.setIdProvincia((String) resultadoBD.getParametro("P_ID_PROVINCIA"));
		beanPantalla.setIdTipoVia((String) resultadoBD.getParametro("P_ID_TIPO_VIA"));
		beanPantalla.setMuniNombre((String) resultadoBD.getParametro("P_MUNI_NOMBRE"));
		beanPantalla.setProvNombre((String) resultadoBD.getParametro("P_PROV_NOMBRE"));
		beanPantalla.setTipViaNombre((String) resultadoBD.getParametro("P_TIP_VIA_NOMBRE"));

		resultado.setError(error);
		resultado.setMensaje(mensaje);

		resultadoMetodo.put(ConstantesPQ.RESULTBEAN, resultado);
		resultadoMetodo.put(ConstantesPQ.BEANPANTALLA, beanPantalla);

		return resultadoMetodo;
	}

	public ResultBean validarAnagramaSelenium(String nif, String primerApellido, String anagramaValidar) {
		ResultBean resultado = new ResultBean();
		WebDriver driver;
		try {
			driver = iniciarNavegador();
		} catch (Exception e) {
			log.error("Ocurrio un error iniciando el navegador HtmlUnitDriver", e);
			resultado.setMensaje(e.getMessage());
			resultado.setError(true);
			return resultado;
		}

		try {
			driver.findElement(By.id(gestorPropiedades.valorPropertie(AEAT_SELENIUM_INPUT_NIF))).sendKeys(nif);
			driver.findElement(By.id(gestorPropiedades.valorPropertie(AEAT_SELENIUM_INPUT_APE)))
					.sendKeys(primerApellido);
			driver.findElement(By.id(gestorPropiedades.valorPropertie(AEAT_SELENIUM_CHECKBOX_ETI2))).click();
			driver.findElement(By.id(gestorPropiedades.valorPropertie(AEAT_SELENIUM_CHECBOX_FIL1))).click();

			Select select = new Select(
					driver.findElement(By.name(gestorPropiedades.valorPropertie(AEAT_SELENIUM_SELECT_LOG))));
			for (WebElement options : select.getOptions()) {
				if ("NO".equals(options.getAttribute("value"))) {
					options.click();
				}
			}
			driver.findElement(By.name(gestorPropiedades.valorPropertie(AEAT_SELENIUM_FORM_SOLICITUD))).submit();
			resultado = gestionaRespuestaValidacionAnagrama(driver.getPageSource(), anagramaValidar);

		} catch (Throwable e) {
			// Si se ha producido un error, ok = false;
			log.error("Error al validar ANAGRAMA en la AEAT - OK false");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido verificar el ANAGRAMA en la AEAT");
			log.error("Salir de validarAnagrama - KO");
		}

		if (driver != null) {
			driver.close();
		}

		return resultado;
	}

	private WebDriver iniciarNavegador() throws SecurityException, NoSuchFieldException, MalformedURLException,
			IllegalArgumentException, IllegalAccessException {
		// Iniciamos el navegador
		WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		// Por reflexion, recuperamos el atributo webClient del objeto HtmlUnitDriver
		// para obtener su webClientOptions y poder establecer el SSLClientCertificate
		Field webClient = driver.getClass().getDeclaredField("webClient");
		webClient.setAccessible(true);
		WebClientOptions webClientOptions = ((WebClient) webClient.get(driver)).getOptions();
		webClientOptions.setSSLClientCertificate(new URL(gestorPropiedades.valorPropertie(KEYSTORE_LOCATION_SELENIUM)),
				gestorPropiedades.valorPropertie(KEYSTORE_PASS_SELENIUM), "jks");
		webClientOptions.setUseInsecureSSL(true);
		webClientOptions.setThrowExceptionOnScriptError(false);
		webClientOptions.setJavaScriptEnabled(true);
		driver.get(gestorPropiedades.valorPropertie(AEAT_SELENIUM_DIRECCION_SERVIDOR_VALIDAR_ANAGRAMA));
		return driver;
	}

	public ResultBean validarAnagrama(String nif, String primerApellido, String anagramaValidar) {
		// log.error("Entro en validarAnagrama");
		// log.error("Parametro de entrada nif " + nif);
		// log.error("Parametro de entrada primerApellido " + primerApellido);
		// log.error("Parametro de entrada anagramaValidar " + anagramaValidar);
		ResultBean resultado = new ResultBean();
		// Se prepara el objeto request para hacer la petición a la AEAT
		LinkedHashMap<String, String> request = new LinkedHashMap<String, String>();
		request.put("HID", "ETICERTI");
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("aeat.anagrama.nueva.url"))) {
			request.put("NIF", nif);
			String apellidoCorto = primerApellido != null && primerApellido.length() > 20
					? primerApellido.substring(0, 20)
					: primerApellido;
			request.put("APE", apellidoCorto);
			request.put("LOG", "NO");
			request.put("ETQ", "2");
			request.put("FIL", "1");
			request.put("FIN", "");

		} else {
			request.put("nif", nif);
			request.put("ape", primerApellido);
			request.put("log", "NO");
			request.put("etiquetas", "2");

		}
		request.put("ENV", "Enviar");
		String userAgent = gestorPropiedades.valorPropertie("aeat.anagrama.user.agent");
		if (userAgent != null && !userAgent.isEmpty()) {
			request.put(HttpMethodParams.USER_AGENT, userAgent);
		}

		try {
			// Se realiza la invocación a la AEAT
			ResultBean result = utilHttpClient.executeMethod(
					gestorPropiedades.valorPropertie(AEAT_DIRECCION_SERVIDOR_VALIDAR_ANAGRAMA),
					gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_URL),
					gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_PASSWORD), request);

			// log.error("Respuesta de validar Anagrama Error "+ result.getError());
			// log.error("Respuesta de validar Anagrama Mensaje "+ result.getMensaje());

			if (!result.getError().booleanValue()) {
				// Si no se ha producido error, se gestiona la respuesta
				// log.error("No hay error en validarAnagrama - OK");
				resultado = gestionaRespuestaValidacionAnagrama(result.getMensaje(), anagramaValidar);
			} else {
				// Si se ha producido un error, ok = false;
				log.error("Error al validar ANAGRAMA en la AEAT - OK false");
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido verificar el ANAGRAMA en la AEAT");
				log.error("Salir de validarAnagrama - KO");
			}
		} catch (Throwable e) {
			log.error("Error al validar ANAGRAMA en la AEAT", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se ha podido verificar el ANAGRAMA en la AEAT");
			log.error("Salir de validarAnagrama - KO");
		}
		// log.error("Salir de validarAnagrama - OK");
		return resultado;
	}

	private ResultBean gestionaRespuestaValidacionAnagrama(String mensajeRespuesta, String anagramaValidar) {
		ResultBean result = new ResultBean();
		boolean ok = respuestaCorrectaValidacionAnagrama(mensajeRespuesta);

		if (ok) {
			String refDatosRespuestaIni = "<font size=2 face=Arial color=BLACK><b>";
			int indexIni = mensajeRespuesta.indexOf(refDatosRespuestaIni);
			int indexFin = mensajeRespuesta.indexOf("</b></font>", indexIni + refDatosRespuestaIni.length());

			if (indexIni != -1 && indexFin != -1) {
				String anagrama = mensajeRespuesta.substring(indexIni + refDatosRespuestaIni.length(), indexFin);
				anagrama = anagrama.replaceAll(" ", "");

				log.debug("ANAGRAMA de la AEAT: " + anagrama);

				if (anagrama != null && anagrama.trim().equals(anagramaValidar)) {
					result.setError(Boolean.FALSE);
				} else {
					result.setError(Boolean.TRUE);
					result.setMensaje("El anagrama no coincide con el devuelto por la AEAT (" + anagrama + ")");
				}
			}
		} else {
			// Intentar recuperar algún mensaje de error desde la página devuelta por la
			// AEAT.
			String mensajeError = getMensajeErrorAnagrama(mensajeRespuesta);
			if (mensajeError == null) {
				mensajeError = "No se ha podido obtener el ANAGRAMA en la AEAT para los datos aportados";
			}

			result.setError(Boolean.TRUE);
			result.setMensaje(mensajeError);
		}
		return result;
	}

	private boolean respuestaCorrectaValidacionAnagrama(String mensajeRespuesta) {
		String refRespuestaCorrecta = "<font size=2 face=Arial color=BLACK><b>";
		return mensajeRespuesta.contains(refRespuestaCorrecta);
	}

	/**
	 * Obtiene el mensaje de error en la respuesta de la AEAT
	 * 
	 * @param respuesta
	 * @return
	 */
	private String getMensajeErrorAnagrama(String respuesta) {
		String mensaje = "";
		String inicio = "<strong>";
		String fin = "</strong>";

		int indexInicio = respuesta.indexOf(inicio) + inicio.length();
		int indexFin = respuesta.indexOf(fin, indexInicio);

		try {
			if (indexInicio != -1 && indexFin != -1) {
				mensaje = "Respuesta de la AEAT: \"" + respuesta.substring(indexInicio, indexFin) + "\"";
				log.error("Validacion fallida de ANAGRAMA: Error obtenido en la " + mensaje);
			} else {
				mensaje = "Validacion fallida de ANAGRAMA: Error interno al conectar con la AEAT";
				log.error(mensaje);
			}
		} catch (Exception e) {
			log.error("Error al obtener el mensaje de respuesta", e);
		}

		return mensaje;
	}

	public ResultBean validaCEM(String cem, String nif, String apellido1, String bastidor) {
		ResultBean resultado = new ResultBean();
		if (!"".equals(cem) && null != cem && !"".equals(nif) && null != nif && !"".equals(apellido1)
				&& null != apellido1) {
			// Validación del CEM
			if ("00000000".equals(cem)) {
				resultado.setError(true);
				resultado.setMensaje("El CEM no puede estar cubierto por ceros");
			} else {
				ResultBean result = validarCEM(cem, nif, apellido1, bastidor);
				if (result.getError()) {
					resultado.setError(true);
					resultado.setMensaje(result.getMensaje());
				}
			}
		}
		return resultado;
	}

	public ResultBean validarCEM(String cem, String nif, String apellido1, String bastidor) {

		ResultBean resultado = new ResultBean();

		// Si no esta activa la validacion del CEM, se devuleve un resultado con error a
		// FALSE
		if ("NO".equalsIgnoreCase(gestorPropiedades.valorPropertie(AEAT_VALIDAR_CEM_ACTIVO))) {
			return resultado;
		}

		// Se prepara el objeto request para hacer la petición a la AEAT
		LinkedHashMap<String, String> request = new LinkedHashMap<String, String>();
		request.put(NIF, nif);
		request.put(APELLIDO1, apellido1);
		request.put(CODIGO, cem);

		try {
			// Se realiza la invocación a la AEAT
			ResultBean result = utilHttpClient.executeMethod(
					gestorPropiedades.valorPropertie(AEAT_DIRECCION_SERVIDOR_VALIDAR_CEM),
					gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_URL),
					gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_PASSWORD), request);

			if (!result.getError().booleanValue()) {
				// Si no se ha producido error, se gestiona la respuesta
				resultado = gestionaRespuestaValidacionCEM(result.getMensaje(), bastidor);
			} else {
				// Si se ha producido un error, ok = false;
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(ERROR_AEAT_VALIDACION_CEM_SERVICIO);
				log.error(ERROR_AEAT_VALIDACION_CEM_SERVICIO + " Detalle: " + result.getMensaje());
			}
		} catch (Throwable e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(ERROR_AEAT_VALIDACION_CEM_SERVICIO);
			log.error(ERROR_AEAT_VALIDACION_CEM_SERVICIO + " Detalle Error: " + e.getMessage(), e);
		}
		return resultado;

	}

	private ResultBean gestionaRespuestaValidacionCEM(String mensajeRespuesta, String bastidor) {
		log.debug(mensajeRespuesta);
		ResultBean result = new ResultBean();
		boolean ok = respuestaCorrectaValidacionCEM(mensajeRespuesta);
		if (ok) {
			log.debug("Respuesta de validación de CEM: VALIDO");
			String refDatosRespuestaIni = "var valores=new Array([";
			int indexIni = mensajeRespuesta.indexOf(refDatosRespuestaIni);
			int indexCorchete = indexIni;
			for (int i = 0; i < 10; i++) {
				indexCorchete = mensajeRespuesta.indexOf("[", indexCorchete) + 1;
			}
			int indexDatos = indexCorchete + 1;

			int i = 0;
			boolean fin = false;
			boolean coincide = false;
			// Hay capacidad para que vengan hasta 150 líneas (bastidores)
			while (i < 150 && !fin) {
				String numBastidor = mensajeRespuesta.substring(indexDatos + 150, indexDatos + 167);
				indexDatos += 185;
				log.debug("Datos de la respuesta del CEM:");
				if (numBastidor != null && !"".equals(numBastidor.trim()) && numBastidor.trim().equals(bastidor)) {
					// El número de bastidor coincide con uno de los de la respuesta
					coincide = true;
					fin = true;
					log.debug("Coincide el bastidor: " + numBastidor);
				} else if (numBastidor != null && !"".equals(numBastidor.trim())
						&& !numBastidor.trim().equals(bastidor)) {
					/* Mostrar trazas con datos expediente */
					log.error("-El número de bastidor devuelto no coincide con el del trámite: " + numBastidor);
					/* ******************************** */
				} else {
					// Ya no hay más bastidores
					fin = true;
				}
				i++;
			}

			if (coincide) {
				/* Mostrar trazas con datos expediente */
				log.debug("-Respuesta de valicación de CEM: VÁLIDO. NumBastidor: " + bastidor);
				/* ******************************** */
				result.setError(Boolean.FALSE);
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje(ERROR_AEAT_VALIDACION_CEM_BASTIDOR);
			}
		} else {
			/* Mostrar trazas con datos expediente */
			log.debug("-Respuesta de validación de CEM: NO VÁLIDO");
			/* ******************************** */
			result.setError(Boolean.TRUE);
			result.setMensaje(ERROR_AEAT_VALIDACION_CEM_NO_VALIDABLE
					+ " Recuerde que el CEM va asociado al NIF y el primer apellido del titular.");
		}
		return result;
	}

	private boolean respuestaCorrectaValidacionCEM(String mensajeRespuesta) {
		String refRespuestaCorrecta = "GCMTW02H";
		return mensajeRespuesta.contains(refRespuestaCorrecta);
	}

	public String obtenerDescripcionTipoVehiculo(String tipoVehiculo) { // NO_UCD (use default)

		try {
			TipoVehiculoVO tipoVehiculoVO = servicioTipoVehiculo.getTipoVehiculo(tipoVehiculo);

			if (tipoVehiculoVO != null) {
				return tipoVehiculoVO.getDescripcion();
			}
		} catch (Throwable e) {
			log.error("Error al obtener descripcion del tipo de vehiculo: " + e.getMessage(), e);
			return null;
		}
		return null;
	}

	public String obtenerDescripcionMunicipio(String idMunicipio, String idProvincia) {
		log.info("obtenerIdMunicipio");
		try {
			MunicipioVO municipio = servicioMunicipio.getMunicipio(idMunicipio, idProvincia);

			return municipio.getNombre();
		} catch (Throwable e) {
			log.error("Error al obtener descripcion del municipio: " + e.getMessage(), e);
			return null;
		}
	}

	public String obtenerDescripcionProvincia(String idProvincia) {
		log.info("obtenerDeescripcion provincia");
		try {
			ProvinciaVO provincia = servicioProvincia.getProvincia(idProvincia);
			return provincia.getNombre();
		} catch (Throwable e) {
			log.error("Error al obtener descripcion de la provincia: " + e.getMessage(), e);
			return null;
		}
	}

	public BigDecimal obtenerIdContratoTramite(BigDecimal numExpediente) {
		log.info("obtenerIdContratoTramite");
		try {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(numExpediente, Boolean.FALSE);
			return new BigDecimal(tramite.getContrato().getIdContrato());
		} catch (Throwable e) {
			log.error("Error al obtener contrato: " + e.getMessage(), e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerTotalesTramitesCTIT(boolean bTelematico, Timestamp fechaDesde,
			Timestamp fechaHasta) {

		log.info("Estadísticas CTIT: --- INICIO --- obtener lista resumen trámites por colegiado.");
		List<TramiteTrafico> listaExpedientes = null;

		Date fechaInicio = utilesFecha.getDate(fechaDesde);
		Date fechaFin = utilesFecha.getDate(fechaHasta);

		try {
			HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

			hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
			hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.count("numExpediente"));
			projectionList.add(Projections.groupProperty("ttt.tipoTransferencia"));
			projectionList.add(Projections.groupProperty("tipoTramite"));
			hb.getCriteria().setProjection(projectionList);

			hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
			hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
			hb.getCriteria().add(Restrictions.isNotNull("ttt.tipoTransferencia"));

			hb.getCriteria()
					.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

			if (bTelematico) {
				hb.getCriteria()
						.add(Restrictions.disjunction()
								.add(Restrictions.eq("estado",
										new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
								.add(Restrictions.eq("estado", new BigDecimal(
										EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
			} else {
				hb.getCriteria().add(
						Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
			}

			listaExpedientes = hb.getCriteria().list();

		} catch (HibernateException e) {
			log.error("Error al obtener estadísticas CTIT - Totales : " + e.getMessage());
			return null;
		} catch (Throwable e) {
			log.error("Error al obtener estadísticas CTIT - Totales : " + e.getMessage(), e);
			return null;
		}

		log.info("Estadísticas CTIT: --- FIN --- obtenerTotalesTramites");

		return listaExpedientes;
	}

	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerTotalesTramitesCTITPorJefatura(boolean bTelematico, String provincia,
			Timestamp fechaDesde, Timestamp fechaHasta) {

		log.info("Estadísticas CTIT: --- INICIO --- obtener lista resumen trámites por colegiado.");
		List<TramiteTrafico> listaExpedientes = null;

		Date fechaInicio = utilesFecha.getDate(fechaDesde);
		Date fechaFin = utilesFecha.getDate(fechaHasta);

		try {
			HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

			hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
			hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.count("numExpediente"));
			projectionList.add(Projections.groupProperty("ttt.tipoTransferencia"));
			projectionList.add(Projections.groupProperty("tipoTramite"));
			hb.getCriteria().setProjection(projectionList);

			hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
			hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));

			hb.getCriteria().createAlias("contrato", "contrato");
			hb.getCriteria().createAlias("contrato.provincia", "prov");
			hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

			hb.getCriteria()
					.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

			if (bTelematico) {
				hb.getCriteria()
						.add(Restrictions.disjunction()
								.add(Restrictions.eq("estado",
										new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
								.add(Restrictions.eq("estado", new BigDecimal(
										EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
			} else {
				hb.getCriteria().add(
						Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
			}

			listaExpedientes = hb.getCriteria().list();

		} catch (HibernateException e) {
			log.error("Error al obtener estadísticas CTIT - Totales : " + e.getMessage());
			return null;

		} catch (Throwable e) {
			log.error("Error al obtener estadísticas CTIT - Totales : " + e.getMessage(), e);
			return null;
		}

		log.info("Estadísticas CTIT: --- FIN --- obtenerTotalesTramites");

		return listaExpedientes;
	}

	@SuppressWarnings("unchecked")
	public List<Long> obtenerListadoNumerosExpedienteCTIT(boolean esTelematico, Date fechaInicio, Date fechaFin) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que obtiene el conteo de trámites que tienen anotaciones en el GEST
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param listadoPosibles
	 * @return
	 */
	public int obtenerDetalleTramitesCTITAnotacionesGest(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		int cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITAnotacionesGest(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;
	}

	/**
	 * Método para obtener el listado total de números de expediente de CTIT
	 * finalizados con anotaciones en el GEST
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param listadoPosibles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITAnotacionesGest(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITAnotacionesGest(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método para obtener los criterios asociados a los trámites de CTIT
	 * finalizados con anotaciones en el GEST
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param listadoPosibles
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITAnotacionesGest(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);

		hb.getCriteria().add(Restrictions.ne("respuestaGest", "OK"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTITVehiculosAgricolas(boolean esTelematico, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<TramiteTrafico> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITVehiculosAgricolas(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("v.tipoVehiculoBean"));
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<TramiteTrafico>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método igual que el anterior, pero que en vez de devolver el resultado con
	 * group y/o count, lo devuelve como una List<TramiteTrafico> completa de
	 * expedientes
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITVehiculosAgricolas(boolean esTelematico, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITVehiculosAgricolas(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		// Projections específicas para obtener el listado de expedientes
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que forma todo el criteria de los dos métodos anteriores para
	 * vehículos agrícolas.
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITVehiculosAgricolas(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("vehiculo", "v");
		hb.getCriteria().createAlias("vehiculo.tipoVehiculoBean", "tv");
		hb.getCriteria().setFetchMode("tv", FetchMode.JOIN);

		hb.getCriteria().add(Restrictions.isNotNull("cema"));
		hb.getCriteria().add(Restrictions.eq("v.vehiculoAgricola", "SI"));
		hb.getCriteria().add(Restrictions.eq("v.idServicio", "B06"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTITCarpetaFusion(boolean esTelematico, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<TramiteTrafico> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaFusion(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("v.tipoVehiculoBean"));
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<TramiteTrafico>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve una lista completa de expedientes de tipo I finalizados
	 * (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITCarpetaFusion(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaFusion(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve los criterios asociados a los dos métodos anteriores de
	 * expedientes de tipo I finalizados (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITCarpetaFusion(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("vehiculo", "v");
		hb.getCriteria().createAlias("tasa", "tasa");
		hb.getCriteria().createAlias("vehiculo.tipoVehiculoBean", "tv");
		hb.getCriteria().setFetchMode("tv", FetchMode.JOIN);

		hb.getCriteria().add(Restrictions.eq("tasa.tipoTasa", "1.6"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTITCarpetaI(boolean esTelematico, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<TramiteTrafico> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaI(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("v.tipoVehiculoBean"));
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<TramiteTrafico>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve una lista completa de expedientes de tipo I finalizados
	 * (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITCarpetaI(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaI(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve los criterios asociados a los dos métodos anteriores de
	 * expedientes de tipo I finalizados (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITCarpetaI(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		List<String> listaTiposVehiculo = new ArrayList<String>();
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_ARTICULADO.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_MIXTO.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.BIBLIOBUS.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_TALLER.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_SANITARIO.getValorEnum());

		List<String> listaServicios = new ArrayList<String>();
		listaServicios.add("A00");
		listaServicios.add("A01");
		listaServicios.add("A02");
		listaServicios.add("A03");
		listaServicios.add("A04");
		listaServicios.add("A05");
		listaServicios.add("A07");
		listaServicios.add("A08");
		listaServicios.add("A09");
		listaServicios.add("A10");
		listaServicios.add("A11");
		listaServicios.add("A12");
		listaServicios.add("A13");
		listaServicios.add("A14");
		listaServicios.add("A15");
		listaServicios.add("A16");
		listaServicios.add("A18");
		listaServicios.add("A20");
		listaServicios.add("B07");

		List<String> listaTiposVehiculoExcluidosPeso = new ArrayList<String>();
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VEHICULO_ESPECIAL.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_CARGADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_EXCAVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.CARRETILLA_ELEVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MOTONIVELADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.COMPACTADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.APISONADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.GIROGRAVILLADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MACHACADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.QUITANIEVES.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VIVIENDA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.BARREDORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VEHICULO_BASCULANTE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SERVICIO_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.ASPIRADORA_DE_FANGOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MOTOCULTOR.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_CARGADORA_RETROEXCAVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TREN_HASTA_160_PLAZAS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOR.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOCAMION.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOCARRO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_1_EJE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_2_EJES.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_FURGON.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("tramiteTrafBaja", "baja", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("vehiculo", "v");
		hb.getCriteria().createAlias("vehiculo.tipoVehiculoBean", "tv");
		hb.getCriteria().setFetchMode("tv", FetchMode.JOIN);

		hb.getCriteria().add(Restrictions.disjunction().add(Restrictions.in("tv.tipoVehiculo", listaTiposVehiculo))
				.add(Restrictions.conjunction()
						.add(Restrictions.sqlRestriction(
								"(CASE peso_mma WHEN 'ND' THEN 0 ELSE to_number(peso_mma) END) > (?)", 6000,
								Hibernate.INTEGER))
						.add(Restrictions.not(Restrictions.in("tv.tipoVehiculo", listaTiposVehiculoExcluidosPeso))))
				.add(Restrictions.in("v.idServicio", listaServicios)));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	public int obtenerDetalleTramitesCTITCarpetaB(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaB(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;
	}

	/**
	 * Método que devuelve la lista completa de expedientes de carpeta tipo B
	 * finalizados
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITCarpetaB(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaB(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve los criterios asociados a los expedientes finalizados
	 * tipo B de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITCarpetaB(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("intervinienteTraficos", "it");

		hb.getCriteria()
				.add(Restrictions.disjunction()
						.add(Restrictions.eq("it.tipoIntervinienteBean.tipoInterviniente",
								TipoInterviniente.CotitularTransmision.getValorEnum()))
						.add(Restrictions.conjunction()
								.add(Restrictions.eq("ttt.modoAdjudicacion", ModoAdjudicacion.tipo2.getValorEnum()))
								.add(Restrictions.eq("it.tipoIntervinienteBean.tipoInterviniente",
										TipoInterviniente.TransmitenteTrafico.getValorEnum())))
						.add(Restrictions.conjunction()
								.add(Restrictions.eq("ttt.acreditaHerenciaDonacion",
										AcreditacionTrafico.Herencia.getValorEnum()))
								.add(Restrictions.eq("it.tipoIntervinienteBean.tipoInterviniente",
										TipoInterviniente.TransmitenteTrafico.getValorEnum()))));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	public int obtenerDetalleTramitesCTITJudicialSubasta(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITJudicialSubasta(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		// projectionList.add(Projections.groupProperty("ttt.modoAdjudicacion"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;
	}

	/**
	 * Método que devuelve la lista completa de trámites de CTIT Finalizados con
	 * Adjudicación Judicial o Subasta
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITJudicialSubasta(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITJudicialSubasta(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		// projectionList.add(Projections.groupProperty("ttt.modoAdjudicacion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que obtiene los criterios para los trámites de CTIT con Adjudicación
	 * Judicial o Subasta de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	private HibernateBean obtenerCriteriosCTITJudicialSubasta(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

		hb.getCriteria().add(Restrictions.eq("ttt.modoAdjudicacion", ModoAdjudicacion.tipo2.getValorEnum()));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	public int obtenerDetalleTramitesCTITSinCETNiFactura(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		int cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		hb = obtenerCriteriosCTITSinCETNiFactura(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;

	}

	/**
	 * Método que devuelve la lista completa de expedientes CTIT finalizados que no
	 * tienen CET, ni factura
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITSinCETNiFactura(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		hb = obtenerCriteriosCTITSinCETNiFactura(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que devuelve los criterios asociados a los trámites finalizados de
	 * CTIT sin CET, ni factura, para los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	private HibernateBean obtenerCriteriosCTITSinCETNiFactura(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

		hb.getCriteria().add(Restrictions.disjunction().add(Restrictions.eq("ttt.cetItp", ""))
				.add(Restrictions.isNull("ttt.cetItp")).add(Restrictions.eq("ttt.cetItp", "00000000")));

		hb.getCriteria().add(Restrictions.disjunction().add(Restrictions.eq("ttt.factura", ""))
				.add(Restrictions.isNull("ttt.factura")));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	public int obtenerDetalleTramitesCTITEmbargoPrecinto(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEmbargoPrecinto(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;

	}

	/**
	 * Método que obtiene la lista completa de expedientes CTIT finalizados que
	 * tienen embargo o precinto en la respuesta de Check CTIT
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITEmbargoPrecinto(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEmbargoPrecinto(esTelematico, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que obtiene los criterios asociados a los trámites CTIT finalizados
	 * con Embargo o Precinto de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	private HibernateBean obtenerCriteriosCTITEmbargoPrecinto(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles, HibernateBean hb) {
		// Mantis 21103: David Sierra
		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().add(Restrictions.like("ttt.resCheckCtit", "TRAMITABLE EN JEFATURA"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	public List<Object> obtenerListaExpedientesCTITErrorJefatura(Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) throws OegamExcepcion {

		Session session = HibernateUtil.getSessionFactory().openSession();

		String stringSQL = "SELECT" + " distinct(it.num_expediente)"
				+ " FROM tramite_trafico t, tramite_traf_tran tr, interviniente_trafico it, direccion d"
				+ " WHERE t.ESTADO=13" + "   AND t.FECHA_PRESENTACION >= :fechaInicio"
				+ "   AND t.FECHA_PRESENTACION < :fechaFin" + " AND t.TIPO_TRAMITE='T7'"
				+ " AND tr.num_expediente = t.num_expediente" + " AND t.num_expediente = it.num_expediente"
				+ " AND it.id_direccion = d.id_direccion" + " AND (" + "   ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   )" + "	)";
		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			stringSQL += " AND t.num_expediente in (:expedientesPosibles)";
		}

		Query query = session.createSQLQuery(stringSQL);

		query.setDate("fechaInicio", fechaInicio);
		query.setDate("fechaFin", fechaFin);
		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			query.setParameterList("expedientesPosibles", listadoPosibles);
		}

		@SuppressWarnings("unchecked")
		List<Object> listaExpedientes = (List<Object>) query.list();

		return listaExpedientes;

	}

	@SuppressWarnings("unchecked")
	public List<Object> obtenerDetalleTramitesCTITErrorJefatura(Date fechaInicio, Date fechaFin,
			List<Long> listadoPosibles) throws OegamExcepcion {

		Session session = HibernateUtil.getSessionFactory().openSession();

		String stringSQL = "select count(caso) as numero, caso from ( " + "SELECT" + " distinct(it.num_expediente),"
				+ " CASE"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1) THEN 'Entrega Compraventa cuyo Poseedor es de Madrid y el Transmitente de fuera de Madrid'"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1) THEN 'Interviene Compraventa cuyo Poseedor es de Madrid y el Adquiriente de fuera de Madrid'"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1) THEN 'Notificación entre Compraventas donde uno es de Madrid y el otro no'"
				+ "   WHEN ((select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1) THEN 'Notificación entre Compraventas donde uno es de Madrid y el otro no'"
				+ " END as caso"
				+ " FROM tramite_trafico t, tramite_traf_tran tr, interviniente_trafico it, direccion d"
				+ " WHERE t.ESTADO=13" + "   AND t.FECHA_PRESENTACION >= :fechaInicio"
				+ "   AND t.FECHA_PRESENTACION < :fechaFin" + " AND t.TIPO_TRAMITE='T7'"
				+ " AND tr.num_expediente = t.num_expediente" + " AND t.num_expediente = it.num_expediente"
				+ " AND it.id_direccion = d.id_direccion" + " AND (" + "   ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='5' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='018' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='3' and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia<>'28')) = 1"
				+ "   ) or ("
				+ "     (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='006' and d2.id_provincia='28')) = 1"
				+ "     and (select count(*) from interviniente_trafico it2, direccion d2 where tr.tipo_transferencia='4' and it.codigo_iae is not null and it2.num_expediente=t.num_expediente and it2.id_direccion=d2.id_direccion and (it2.tipo_interviniente='005' and d2.id_provincia<>'28')) = 1"
				+ "   )" + "	)";
		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			stringSQL += " AND t.num_expediente in (:expedientesPosibles)";
		}
		stringSQL += ") group by caso";

		Query query = session.createSQLQuery(stringSQL);

		query.setDate("fechaInicio", fechaInicio);
		query.setDate("fechaFin", fechaFin);
		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			query.setParameterList("expedientesPosibles", listadoPosibles);
		}

		List<Object> resultado = (List<Object>) query.list();

		return resultado;

	}

	/**
	 * Método que obtiene el conteo de trámites CTIT Finalizados PDF según su
	 * evolución anterior
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estadoAnterior
	 * @param listadoPosibles
	 * @return
	 */
	public int obtenerDetalleTramitesCTITEvolucion(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<EstadoTramiteTrafico> transicionEstados, List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEvolucion(esTelematico, fechaInicio, fechaFin, transicionEstados, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;

	}

	/**
	 * Método que devuelve la lista completa de expedientes de CTIT finalizados PDF
	 * según su evolución anterior
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estadoAnterior
	 * @param listadoPosibles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITEvolucion(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<EstadoTramiteTrafico> transicionEstados, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEvolucion(esTelematico, fechaInicio, fechaFin, transicionEstados, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que devuelve los criterios asociados a los expedientes finalizados PDF
	 * de CTIT según evolución de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estadoAnterior
	 * @param listadoPosibles
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITEvolucion(boolean esTelematico, Date fechaInicio, Date fechaFin,
			List<EstadoTramiteTrafico> transicionEstados, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("evolucionTramiteTraficos", "evol");

		String estadoAnterior = null;
		String estadoPosterior = null;

		if (transicionEstados.size() >= 2) {

			for (int i = 1; i < transicionEstados.size(); i++) {

				estadoAnterior = transicionEstados.get(i - 1).getValorEnum();
				estadoPosterior = transicionEstados.get(i).getValorEnum();

				// Si ha llegado a finalizado con error, puede pasar a iniciado o el estado que
				// sea, por lo que sólo tengo en cuenta que terminará en estado Finalizado PDF.
				if (estadoAnterior.equals(EstadoTramiteTrafico.Finalizado_Con_Error)) {
					hb.getCriteria().add(Restrictions.sqlRestriction(
							"{alias}.num_expediente in (select num_expediente from evolucion_tramite_trafico where estado_nuevo='"
									+ estadoPosterior + "')"));
					hb.getCriteria().add(Restrictions.sqlRestriction(
							"{alias}.num_expediente not in (select num_expediente from evolucion_tramite_trafico where estado_nuevo='"
									+ EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum() + "')"));
				} else {
					hb.getCriteria().add(Restrictions.sqlRestriction(
							"{alias}.num_expediente in (select num_expediente from evolucion_tramite_trafico where estado_anterior='"
									+ estadoAnterior + "' and estado_nuevo='" + estadoPosterior + "')"));
				}

			}
		}

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	@SuppressWarnings("unchecked")
	public List<Object> obtenerListaTramitesGestorSobreExp(List<Long> listadoPosibles) {

		List<Object> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		projectionList.add(Projections.groupProperty("numColegiado"));
		hb.getCriteria().setProjection(projectionList);

		hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));

		listaExpedientes = (List<Object>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Metodo que devuelve el sumario de trámites por tipo de transferencia.
	 * Utilizado para el envío de estadísticas de CTIT
	 * 
	 * @param bTelematico Indica si la información se obtiene para trámites
	 *                    telemáticos (true), o no telemáticos (false)
	 * @param delegacion  Indica la delegacion de la que se van a obtener los
	 *                    tramites
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public int obtenerTotalesTramitesMATE(boolean bTelematico, String delegacion, Timestamp fechaDesde,
			Timestamp fechaHasta) {

		log.info("obtenerTotalesTramitesMATE: --- INICIO ---");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(TramiteTrafico.class);

		criteria.add(Restrictions.eq("tipoTramite", "T1"));

		if (bTelematico) {

			List<BigDecimal> listaEstados = new ArrayList<BigDecimal>();
			listaEstados.add(
					BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())));
			listaEstados.add(BigDecimal
					.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())));
			criteria.add(Restrictions.in("estado", listaEstados));
		} else {
			criteria.add(Restrictions.eq("estado",
					BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))));
		}

		if ("TOTALES".equals(delegacion)) {
			// Quiero los totales de Oegam, por lo que no aniado restriccion por provincia
		} else if ("TODASDELEGACIONES".equals(delegacion)) {
			criteria.createAlias("contrato", "contrato");
			criteria.createAlias("contrato.provincia", "prov");
			List<String> listaDelegaciones = new ArrayList<String>();
			listaDelegaciones.add("40");
			listaDelegaciones.add("05");
			listaDelegaciones.add("16");
			listaDelegaciones.add("13");
			listaDelegaciones.add("19");
			criteria.add(Restrictions.in("prov.idProvincia", listaDelegaciones));
		} else if (!"".equals(delegacion)) {
			criteria.createAlias("contrato", "contrato");
			criteria.createAlias("contrato.provincia", "prov");
			criteria.add(Restrictions.eq("prov.idProvincia", delegacion));
		}

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		Number numeroExpedientes = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();

		log.info("obtenerTotalesTramitesMATE: --- FIN ---");

		session.close();

		if (numeroExpedientes == null || numeroExpedientes.intValue() == 0)
			return 0;
		else
			return numeroExpedientes.intValue();

	}

	/**
	 * Método que devuelve Matriculaciones No Telemáticas con Ficha Técnica A o C
	 * para una provincia y una Jefaturaprovincia de la que se obtendrán los
	 * trámites
	 * 
	 * @param provincia  provincia de la que se obtendrán los trámites
	 * @param jefatura   jefatura de la que se obtendrán los trámites
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public String obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(String provincia, String jefatura,
			Timestamp fechaDesde, Timestamp fechaHasta) {

		log.info("obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica: --- INICIO ---");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(TramiteTrafico.class);

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.provincia", "prov");
		criteria.add(Restrictions.eq("prov.idProvincia", provincia));

		criteria.add(Restrictions.eq("tipoTramite", "T1"));

		List<String> listaFichasTecnicas = new ArrayList<>();

		listaFichasTecnicas.add("A");
		listaFichasTecnicas.add("C");
		criteria.add(Restrictions.in("vehiculo.idTipoTarjetaItv", listaFichasTecnicas));

		criteria.add(Restrictions.eq("estado",
				BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))));

		if (jefatura != null) {
			criteria.createAlias("contrato.jefaturaTrafico", "Jefatura");
			criteria.add(Restrictions.eq("Jefatura.jefaturaProvincial", jefatura));
		}

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		Number numeroTramitesMate = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();

		log.info("obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica: --- FIN ---");

		// session.close();
		return String.valueOf(numeroTramitesMate);

	}

	/**
	 * Metodo que devuelve el total de Matriculaciones No Telematicas que no son ni
	 * ficha Tecnica A ni C para una provincia y una Jefatura
	 * 
	 * @param provincia  provincia de la que se obtendran los tramites
	 * @param jefatura   jefatura de la que se obtendran los tramites
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public String obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(String provincia, String jefatura,
			Timestamp fechaDesde, Timestamp fechaHasta) {

		log.info("obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica: --- INICIO ---");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(TramiteTrafico.class);

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.provincia", "prov");
		criteria.add(Restrictions.eq("prov.idProvincia", provincia));

		criteria.add(Restrictions.eq("tipoTramite", "T1"));

		List<String> listaFichasTecnicas = new ArrayList<>();

		listaFichasTecnicas.add("A");
		listaFichasTecnicas.add("C");
		criteria.add(Restrictions.not(Restrictions.in("vehiculo.idTipoTarjetaItv", listaFichasTecnicas)));

		criteria.add(Restrictions.eq("estado",
				BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))));

		if (jefatura != null) {
			criteria.createAlias("contrato.jefaturaTrafico", "Jefatura");
			criteria.add(Restrictions.eq("Jefatura.jefaturaProvincial", jefatura));
		}

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		Number numeroTramitesMate = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();

		log.info("obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica: --- FIN ---");

		// session.close();
		return String.valueOf(numeroTramitesMate);

	}

	/**
	 * Metodo que devuelve agrupados por tipo de Vehiculos las de Matriculaciones No
	 * Telematicas que no son ni ficha Tecnica A ni C para una provincia y una
	 * Jefatura
	 * 
	 * @param provincia  provincia de la que se obtendran los tramites
	 * @param jefatura   jefatura de la que se obtendran los tramites
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(String provincia, String jefatura,
			Timestamp fechaDesde, Timestamp fechaHasta) {

		log.info("obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos: --- INICIO ---");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(TramiteTrafico.class);

		List<TramiteTrafico> listaTramites = null;

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.INNER_JOIN);

		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.provincia", "prov");
		criteria.add(Restrictions.eq("prov.idProvincia", provincia));

		criteria.add(Restrictions.eq("tipoTramite", "T1"));

		criteria.add(Restrictions.eq("estado",
				BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))));

		if (jefatura != null) {
			criteria.createAlias("contrato.jefaturaTrafico", "Jefatura");
			criteria.add(Restrictions.eq("Jefatura.jefaturaProvincial", jefatura));
		}

		List<String> listaFichasTecnicas = new ArrayList<>();
		listaFichasTecnicas.add("A");
		listaFichasTecnicas.add("C");
		criteria.add(Restrictions.not(Restrictions.in("vehiculo.idTipoTarjetaItv", listaFichasTecnicas)));

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		ProjectionList projectionList = Projections.projectionList();

		criteria.createAlias("vehiculo.tipoVehiculoBean", "tipoVehic");
		projectionList.add(Projections.groupProperty("tipoVehic.descripcion"), "tipoVehic.descripcion");
		projectionList.add(Projections.rowCount(), "tipoVehic.descripcion");
		criteria.setProjection(projectionList).addOrder(Order.asc("tipoVehic.descripcion"));

		listaTramites = criteria.list();

		log.info("obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos: --- FIN ---");

		session.close();
		return listaTramites;

	}

	/**
	 * Metodo que agrupa tramites Telematicos por Vehiculo, Carburante o Jefatura
	 * 
	 * @param provincia  provincia de la que se va a agrupar
	 * @param agrupacion tipo de agrupacion (Vehiculo, Carburante o Jefatura)
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerTramitesMATETelVehiculos(String provincia, String agrupacion, Timestamp fechaDesde,
			Timestamp fechaHasta, int totales) {

		log.info("obtenerTramitesMATETelVehiculos: --- INICIO ---");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(TramiteTrafico.class);

		List<TramiteTrafico> listaTramites = null;

		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.INNER_JOIN);

		criteria.createAlias("contrato", "contrato");
		criteria.createAlias("contrato.provincia", "prov");
		criteria.add(Restrictions.eq("prov.idProvincia", provincia));

		criteria.add(Restrictions.eq("tipoTramite", "T1"));

		List<BigDecimal> listaEstados = new ArrayList<>();
		listaEstados.add(
				BigDecimal.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())));
		listaEstados.add(BigDecimal
				.valueOf(Long.parseLong(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())));
		criteria.add(Restrictions.in("estado", listaEstados));

		criteria.add(Restrictions.ge("fechaPresentacion", fechaDesde));
		criteria.add(Restrictions.lt("fechaPresentacion", fechaHasta));

		ProjectionList projectionList = Projections.projectionList();

		if ("TIPOVEHICULO".equals(agrupacion)) {
			criteria.createAlias("vehiculo.tipoVehiculoBean", "tipoVehic");
			projectionList.add(Projections.groupProperty("tipoVehic.descripcion"), "tipoVehic.descripcion");
			projectionList.add(Projections.rowCount(), "tipoVehic.descripcion");
			criteria.setProjection(projectionList).addOrder(Order.asc("tipoVehic.descripcion"));
		} else if ("CARBURANTE".equals(agrupacion)) {
			projectionList.add(Projections.groupProperty("vehiculo.idCarburante"), "vehiculo.idCarburante");
			projectionList.add(Projections.rowCount(), "vehiculo.idCarburante");
			criteria.setProjection(projectionList).addOrder(Order.asc("vehiculo.idCarburante"));
		} else if ("JEFATURA".equals(agrupacion)) {
			criteria.createAlias("contrato.jefaturaTrafico", "jefaturaTrafico");
			projectionList.add(Projections.groupProperty("jefaturaTrafico.descripcion"), "jefaturaTrafico.descripcion");
			projectionList.add(Projections.rowCount(), "jefaturaTrafico.descripcion");
			criteria.setProjection(projectionList).addOrder(Order.asc("jefaturaTrafico.descripcion"));
		}

		listaTramites = criteria.list();
		List<Object[]> listaObjects = new ArrayList<Object[]>();

		Iterator<?> iter = listaTramites.iterator();
		int totalSinEspecificar = 0;
		int totalesQuery = 0;

		while (iter.hasNext()) {

			Object[] temp = (Object[]) iter.next();
			if (temp[0] == null || ((String) temp[0]).equals("-1")) {
				totalSinEspecificar = totalSinEspecificar + Integer.parseInt(temp[1].toString());
			} else {
				totalesQuery = totalesQuery + Integer.parseInt(temp[1].toString());
				listaObjects.add(temp);
			}
		}
		if (totalSinEspecificar > 0) {
			Object[] object = new Object[2];
			object[0] = "SIN ESPECIFICAR";
			object[1] = totalSinEspecificar;
			listaObjects.add(object);
		} else if ("TIPOVEHICULO".equals(agrupacion)) {
			totalSinEspecificar = totales - totalesQuery;
			Object[] object = new Object[2];
			object[0] = "SIN ESPECIFICAR";
			object[1] = totalSinEspecificar;
			listaObjects.add(object);
		}

		log.info("obtenerTramitesMATETelVehiculos: --- FIN ---");

		session.close();
		return listaObjects;
	}

	/**
	 * Devuelve el listado de trámites por tipo de transferencia. Utilizado para el
	 * envío de estadísticas de CTIT
	 * 
	 * @param bTelematico Indica si la información se obtiene para trámites
	 *                    telemáticos (true), o no telemáticos (false)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTIT(boolean bTelematico, Timestamp fechaDesde,
			Timestamp fechaHasta) {

		log.info("Estadísticas CTIT: --- INICIO --- obtener lista resumen trámites por colegiado.");
		List<TramiteTrafico> listaExpedientes = null;

		Date fechaInicio = utilesFecha.getDate(fechaDesde);
		Date fechaFin = utilesFecha.getDate(fechaHasta);

		try {

			HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

			hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.count("numColegiado"));
			projectionList.add(Projections.groupProperty("numColegiado"));
			projectionList.add(Projections.groupProperty("estado"));
			hb.getCriteria().setProjection(projectionList);

			hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
			hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));

			hb.getCriteria()
					.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

			if (bTelematico) {
				hb.getCriteria()
						.add(Restrictions.disjunction()
								.add(Restrictions.eq("estado",
										new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
								.add(Restrictions.eq("estado", new BigDecimal(
										EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
			} else {
				hb.getCriteria().add(
						Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
			}

			listaExpedientes = hb.getCriteria().list();

		} catch (HibernateException e) {
			log.error("Error al obtener estadísticas CTIT - Detalle : ", e);
			return null;
		} catch (Throwable e) {
			log.error("Error al obtener estadísticas CTIT - Detalle : ", e);
			return null;
		}

		log.info("Estadísticas CTIT: --- FIN --- obtenerDetalleTramites");

		return listaExpedientes;
	}

	@SuppressWarnings("unchecked")
	public List<TipoCreacion> obtenerTipoCreaciones() {
		List<TipoCreacion> tiposCreaciones = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(TipoCreacion.class);
		criteria.addOrder(Order.asc("idTipoCreacion"));
		tiposCreaciones = criteria.list();

		session.close();

		return tiposCreaciones;
	}

	/*
	 * Metodos nuevos para el correo por delegaciones
	 */

	@SuppressWarnings("unchecked")
	public List<Long> obtenerListadoNumerosExpedienteCTIT(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));
		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que obtiene el conteo de trámites que tienen anotaciones en el GEST
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param listadoPosibles
	 * @return
	 */
	public int obtenerDetalleTramitesCTITAnotacionesGest(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		int cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITAnotacionesGest(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;
	}

	/**
	 * Método para obtener el listado total de números de expediente de CTIT
	 * finalizados con anotaciones en el GEST
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param listadoPosibles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITAnotacionesGest(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITAnotacionesGest(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método para obtener los criterios asociados a los trámites de CTIT
	 * finalizados con anotaciones en el GEST
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param listadoPosibles
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITAnotacionesGest(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria().add(Restrictions.ne("respuestaGest", "OK"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	/**
	 * Método que obtiene el detalle agrupado de vehículos agrícolas (Ej: 5
	 * tractores, 2 remolques, 3 grúas, etc...) para trámites de CTIT finalizados
	 * 
	 * @query SELECT count(v.tipo_vehiculo) numero, tv.descripcion FROM
	 *        tramite_trafico t, tramite_traf_tran tr, vehiculo v, tipo_vehiculo tv
	 *        WHERE t.ESTADO=13 AND TRUNC(t.FECHA_PRESENTACION) >=
	 *        TRUNC(TO_DATE('04/04/13')) AND TRUNC(t.FECHA_PRESENTACION) <
	 *        TRUNC(TO_DATE('05/04/13')) AND t.TIPO_TRAMITE='T7' AND
	 *        tr.num_expediente = t.num_expediente AND t.id_vehiculo = v.id_vehiculo
	 *        AND v.tipo_vehiculo = tv.tipo_vehiculo AND t.cema is not null AND
	 *        v.vehiculo_agricola='SI' AND v.id_servicio='B06' GROUP BY
	 *        v.tipo_vehiculo, tv.descripcion;
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTITVehiculosAgricolas(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<TramiteTrafico> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITVehiculosAgricolas(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles,
				hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("v.tipoVehiculoBean"));
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<TramiteTrafico>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método igual que el anterior, pero que en vez de devolver el resultado con
	 * group y/o count, lo devuelve como una List<TramiteTrafico> completa de
	 * expedientes
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITVehiculosAgricolas(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITVehiculosAgricolas(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles,
				hb);

		// Projections específicas para obtener el listado de expedientes
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que forma todo el criteria de los dos métodos anteriores para
	 * vehículos agrícolas.
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITVehiculosAgricolas(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("vehiculo", "v");
		hb.getCriteria().createAlias("vehiculo.tipoVehiculoBean", "tv");
		hb.getCriteria().setFetchMode("tv", FetchMode.JOIN);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria().add(Restrictions.isNotNull("cema"));
		hb.getCriteria().add(Restrictions.eq("v.vehiculoAgricola", "SI"));
		hb.getCriteria().add(Restrictions.eq("v.idServicio", "B06"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	/**
	 * Método que obtiene la cuenta agrupada de los trámites de carpeta I
	 * (certificado de transporte de mercancías) finalizados
	 * 
	 * @query SELECT count(v.tipo_vehiculo) numero, tv.descripcion FROM
	 *        tramite_trafico t, tramite_traf_tran tr, vehiculo v, tipo_vehiculo tv,
	 *        tasa WHERE t.ESTADO=13 AND TRUNC(t.FECHA_PRESENTACION) >=
	 *        TRUNC(TO_DATE('04/04/13')) AND TRUNC(t.FECHA_PRESENTACION) <
	 *        TRUNC(TO_DATE('05/04/13')) AND t.TIPO_TRAMITE='T7' AND
	 *        tr.num_expediente = t.num_expediente AND t.id_vehiculo = v.id_vehiculo
	 *        AND v.tipo_vehiculo = tv.tipo_vehiculo AND tasa.tipo_tasa = '1.6'
	 *        GROUP BY v.tipo_vehiculo, tv.descripcion;
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTITCarpetaFusion(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<TramiteTrafico> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaFusion(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("v.tipoVehiculoBean"));
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<TramiteTrafico>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que devuelve una lista completa de expedientes de tipo I finalizados
	 * (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITCarpetaFusion(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaFusion(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que devuelve los criterios asociados a los dos métodos anteriores de
	 * expedientes de tipo I finalizados (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITCarpetaFusion(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("vehiculo", "v");
		hb.getCriteria().createAlias("tasa", "tasa");
		hb.getCriteria().createAlias("vehiculo.tipoVehiculoBean", "tv");
		hb.getCriteria().setFetchMode("tv", FetchMode.JOIN);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria().add(Restrictions.eq("tasa.tipoTasa", "1.6"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTITCarpetaI(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<TramiteTrafico> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaI(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("v.tipoVehiculoBean"));
		projectionList.add(Projections.groupProperty("tv.descripcion"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<TramiteTrafico>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve una lista completa de expedientes de tipo I finalizados
	 * (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITCarpetaI(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaI(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve los criterios asociados a los dos métodos anteriores de
	 * expedientes de tipo I finalizados (certificado de transporte de mercancías)
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITCarpetaI(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		List<String> listaTiposVehiculo = new ArrayList<String>();
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_ARTICULADO.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_MIXTO.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.BIBLIOBUS.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_TALLER.getValorEnum());
		listaTiposVehiculo.add(TipoVehiculoEnum.AUTOBUS_SANITARIO.getValorEnum());

		List<String> listaServicios = new ArrayList<>();
		listaServicios.add("A00");
		listaServicios.add("A01");
		listaServicios.add("A02");
		listaServicios.add("A03");
		listaServicios.add("A04");
		listaServicios.add("A05");
		listaServicios.add("A07");
		listaServicios.add("A08");
		listaServicios.add("A09");
		listaServicios.add("A10");
		listaServicios.add("A11");
		listaServicios.add("A12");
		listaServicios.add("A13");
		listaServicios.add("A14");
		listaServicios.add("A15");
		listaServicios.add("A16");
		listaServicios.add("A18");
		listaServicios.add("A20");
		listaServicios.add("B07");

		List<String> listaTiposVehiculoExcluidosPeso = new ArrayList<String>();
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VEHICULO_ESPECIAL.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_CARGADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_EXCAVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.CARRETILLA_ELEVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MOTONIVELADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.COMPACTADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.APISONADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.GIROGRAVILLADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MACHACADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.QUITANIEVES.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VIVIENDA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.BARREDORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.VEHICULO_BASCULANTE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SERVICIO_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.ASPIRADORA_DE_FANGOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MOTOCULTOR.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.PALA_CARGADORA_RETROEXCAVADORA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TREN_HASTA_160_PLAZAS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOR.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOCAMION.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.TRACTOCARRO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.REMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_1_EJE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_ARRASTRADA_DE_2_EJES.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.MAQUINARIA_AGRICOLA_AUTOMOTRIZ.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_BOTELLERO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CAJA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CISTERNA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_CONTRA_INCENDIOS.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_DE_GRUA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_FRIGORIFICO.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_FURGON.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_HORMIGONERA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_JAULA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_PLATAFORMA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_TALLER.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_VIVIENDA_O_CARAVANA.getValorEnum());
		listaTiposVehiculoExcluidosPeso.add(TipoVehiculoEnum.SEMIRREMOLQUE_VOLQUETE_DE_CANTERA.getValorEnum());

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("vehiculo", "v");
		hb.getCriteria().createAlias("vehiculo.tipoVehiculoBean", "tv");
		hb.getCriteria().setFetchMode("tv", FetchMode.JOIN);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria().add(Restrictions.disjunction().add(Restrictions.in("tv.tipoVehiculo", listaTiposVehiculo))
				.add(Restrictions.conjunction()
						.add(Restrictions.sqlRestriction(
								"(CASE peso_mma WHEN 'ND' THEN 0 ELSE to_number(peso_mma) END) > (?)", 6000,
								Hibernate.INTEGER))
						.add(Restrictions.not(Restrictions.in("tv.tipoVehiculo", listaTiposVehiculoExcluidosPeso))))
				.add(Restrictions.in("v.idServicio", listaServicios)));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	public int obtenerDetalleTramitesCTITCarpetaB(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaB(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;
	}

	/**
	 * Método que devuelve la lista completa de expedientes de carpeta tipo B
	 * finalizados
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITCarpetaB(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITCarpetaB(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que devuelve los criterios asociados a los expedientes finalizados
	 * tipo B de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITCarpetaB(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("intervinienteTraficos", "it");

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria()
				.add(Restrictions.disjunction()
						.add(Restrictions.eq("it.tipoIntervinienteBean.tipoInterviniente",
								TipoInterviniente.CotitularTransmision.getValorEnum()))
						.add(Restrictions.conjunction()
								.add(Restrictions.eq("ttt.modoAdjudicacion", ModoAdjudicacion.tipo2.getValorEnum()))
								.add(Restrictions.eq("it.tipoIntervinienteBean.tipoInterviniente",
										TipoInterviniente.TransmitenteTrafico.getValorEnum())))
						.add(Restrictions.conjunction()
								.add(Restrictions.eq("ttt.acreditaHerenciaDonacion",
										AcreditacionTrafico.Herencia.getValorEnum()))
								.add(Restrictions.eq("it.tipoIntervinienteBean.tipoInterviniente",
										TipoInterviniente.TransmitenteTrafico.getValorEnum()))));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	public int obtenerDetalleTramitesCTITJudicialSubasta(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITJudicialSubasta(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;
	}

	/**
	 * Método que devuelve la lista completa de trámites de CTIT Finalizados con
	 * Adjudicación Judicial o Subasta
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITJudicialSubasta(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITJudicialSubasta(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;
	}

	/**
	 * Método que obtiene los criterios para los trámites de CTIT con Adjudicación
	 * Judicial o Subasta de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	private HibernateBean obtenerCriteriosCTITJudicialSubasta(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria().add(Restrictions.eq("ttt.modoAdjudicacion", ModoAdjudicacion.tipo2.getValorEnum()));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	public int obtenerDetalleTramitesCTITSinCETNiFactura(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		int cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		hb = obtenerCriteriosCTITSinCETNiFactura(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;

	}

	/**
	 * Método que devuelve la lista completa de expedientes CTIT finalizados que no
	 * tienen CET, ni factura
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITSinCETNiFactura(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		hb = obtenerCriteriosCTITSinCETNiFactura(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que devuelve los criterios asociados a los trámites finalizados de
	 * CTIT sin CET, ni factura, para los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	private HibernateBean obtenerCriteriosCTITSinCETNiFactura(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		hb.getCriteria().add(Restrictions.disjunction().add(Restrictions.eq("ttt.cetItp", ""))
				.add(Restrictions.isNull("ttt.cetItp")).add(Restrictions.eq("ttt.cetItp", "00000000")));

		hb.getCriteria().add(Restrictions.disjunction().add(Restrictions.eq("ttt.factura", ""))
				.add(Restrictions.isNull("ttt.factura")));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	public int obtenerDetalleTramitesCTITEmbargoPrecinto(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEmbargoPrecinto(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.count("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;

	}

	/**
	 * Método que obtiene la lista completa de expedientes CTIT finalizados que
	 * tienen embargo o precinto en la respuesta de Check CTIT
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITEmbargoPrecinto(boolean esTelematico, String provincia,
			Date fechaInicio, Date fechaFin, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEmbargoPrecinto(esTelematico, provincia, fechaInicio, fechaFin, listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que obtiene los criterios asociados a los trámites CTIT finalizados
	 * con Embargo o Precinto de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param hb
	 * @return
	 */
	private HibernateBean obtenerCriteriosCTITEmbargoPrecinto(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<Long> listadoPosibles, HibernateBean hb) {
		// Mantis 21103: David Sierra
		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().add(Restrictions.like("ttt.resCheckCtit", "TRAMITABLE EN JEFATURA"));

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;
	}

	/**
	 * Método que obtiene el conteo de trámites CTIT Finalizados PDF según su
	 * evolución anterior
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estadoAnterior
	 * @param listadoPosibles
	 * @return
	 */
	public int obtenerDetalleTramitesCTITEvolucion(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<EstadoTramiteTrafico> transicionEstados, List<Long> listadoPosibles) {

		Integer cuenta = 0;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEvolucion(esTelematico, provincia, fechaInicio, fechaFin, transicionEstados,
				listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.countDistinct("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		cuenta = (Integer) hb.getCriteria().uniqueResult();

		return cuenta;

	}

	/**
	 * Método que devuelve la lista completa de expedientes de CTIT finalizados PDF
	 * según su evolución anterior
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estadoAnterior
	 * @param listadoPosibles
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerListaExpedientesCTITEvolucion(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<EstadoTramiteTrafico> transicionEstados, List<Long> listadoPosibles) {

		List<Long> listaExpedientes = null;

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

		hb = obtenerCriteriosCTITEvolucion(esTelematico, provincia, fechaInicio, fechaFin, transicionEstados,
				listadoPosibles, hb);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("numExpediente"));
		hb.getCriteria().setProjection(projectionList);

		listaExpedientes = (List<Long>) hb.getCriteria().list();

		return listaExpedientes;

	}

	/**
	 * Método que devuelve los criterios asociados a los expedientes finalizados PDF
	 * de CTIT según evolución de los dos métodos anteriores
	 * 
	 * @param esTelematico
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estadoAnterior
	 * @param listadoPosibles
	 * @param hb
	 * @return
	 */
	public HibernateBean obtenerCriteriosCTITEvolucion(boolean esTelematico, String provincia, Date fechaInicio,
			Date fechaFin, List<EstadoTramiteTrafico> transicionEstados, List<Long> listadoPosibles, HibernateBean hb) {

		hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);
		hb.getCriteria().createAlias("evolucionTramiteTraficos", "evol");

		hb.getCriteria().createAlias("contrato", "contrato");
		hb.getCriteria().createAlias("contrato.provincia", "prov");
		hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

		String estadoAnterior = null;
		String estadoPosterior = null;

		if (transicionEstados.size() >= 2) {

			for (int i = 1; i < transicionEstados.size(); i++) {

				estadoAnterior = transicionEstados.get(i - 1).getValorEnum();
				estadoPosterior = transicionEstados.get(i).getValorEnum();

				// Si ha llegado a finalizado con error, puede pasar a iniciado o el estado que
				// sea, por lo que sólo tengo en cuenta que terminará en estado Finalizado PDF.
				if (estadoAnterior.equals(EstadoTramiteTrafico.Finalizado_Con_Error)) {
					hb.getCriteria().add(Restrictions.sqlRestriction(
							"{alias}.num_expediente in (select num_expediente from evolucion_tramite_trafico where estado_nuevo='"
									+ estadoPosterior + "')"));
					hb.getCriteria().add(Restrictions.sqlRestriction(
							"{alias}.num_expediente not in (select num_expediente from evolucion_tramite_trafico where estado_nuevo='"
									+ EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum() + "')"));
				} else {
					hb.getCriteria().add(Restrictions.sqlRestriction(
							"{alias}.num_expediente in (select num_expediente from evolucion_tramite_trafico where estado_anterior='"
									+ estadoAnterior + "' and estado_nuevo='" + estadoPosterior + "')"));
				}

			}
		}

		hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
		hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));
		hb.getCriteria().add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

		if (listadoPosibles != null && !listadoPosibles.isEmpty()) {
			hb.getCriteria().add(Restrictions.in("numExpediente", listadoPosibles));
		}

		if (esTelematico) {
			hb.getCriteria().add(Restrictions.disjunction()
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
					.add(Restrictions.eq("estado",
							new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
		} else {
			hb.getCriteria()
					.add(Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
		}

		return hb;

	}

	/**
	 * Devuelve el listado de trámites por tipo de transferencia. Utilizado para el
	 * envío de estadísticas de CTIT
	 * 
	 * @param bTelematico Indica si la información se obtiene para trámites
	 *                    telemáticos (true), o no telemáticos (false)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TramiteTrafico> obtenerDetalleTramitesCTIT(boolean bTelematico, String provincia, Timestamp fechaDesde,
			Timestamp fechaHasta) {

		log.info("Estadísticas CTIT: --- INICIO --- obtener lista resumen trámites por colegiado.");
		List<TramiteTrafico> listaExpedientes = null;

		Date fechaInicio = utilesFecha.getDate(fechaDesde);
		Date fechaFin = utilesFecha.getDate(fechaHasta);

		try {

			HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafico.class);

			hb.getCriteria().createAlias("tramiteTrafTran", "ttt", CriteriaSpecification.LEFT_JOIN);

			hb.getCriteria().createAlias("contrato", "contrato");
			hb.getCriteria().createAlias("contrato.provincia", "prov");
			hb.getCriteria().add(Restrictions.eq("prov.idProvincia", provincia));

			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.count("numColegiado"));
			projectionList.add(Projections.groupProperty("numColegiado"));
			projectionList.add(Projections.groupProperty("estado"));
			hb.getCriteria().setProjection(projectionList);

			hb.getCriteria().add(Restrictions.ge("fechaPresentacion", fechaInicio));
			hb.getCriteria().add(Restrictions.lt("fechaPresentacion", fechaFin));

			hb.getCriteria()
					.add(Restrictions.eq("tipoTramite", TipoTramiteTrafico.TransmisionElectronica.getValorEnum()));

			if (bTelematico) {
				hb.getCriteria()
						.add(Restrictions.disjunction()
								.add(Restrictions.eq("estado",
										new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())))
								.add(Restrictions.eq("estado", new BigDecimal(
										EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))));
			} else {
				hb.getCriteria().add(
						Restrictions.eq("estado", new BigDecimal(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())));
			}

			listaExpedientes = hb.getCriteria().list();

		} catch (HibernateException e) {
			log.error("Error al obtener estadísticas CTIT - Detalle : ", e);
			return null;
		} catch (Throwable e) {
			log.error("Error al obtener estadísticas CTIT - Detalle : ", e);
			return null;
		}

		log.info("Estadísticas CTIT: --- FIN --- obtenerDetalleTramites");

		return listaExpedientes;
	}

	public boolean isMatw(Long numExpediente) {

		TramiteTrafMatr tramite = new TramiteTrafMatr();

		HibernateBean hb = HibernateUtil.createCriteria(TramiteTrafMatr.class);
		hb.getCriteria().add(Restrictions.eq("numExpediente", numExpediente));

		tramite = (TramiteTrafMatr) hb.getCriteria().uniqueResult();

		if (tramite == null || tramite.getTipoTramiteMatr() == null) {
			return false;
		} else {
			return true;
		}

	}

	// Mantis 17570. David Sierra. Llamada al servicio
	public void actualizarRespuesta(String respuestaErrror, BigDecimal numExpediente) {
		servicioTramiteTrafico.actualizarRespuestaMateEitv(respuestaErrror, numExpediente);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
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

}