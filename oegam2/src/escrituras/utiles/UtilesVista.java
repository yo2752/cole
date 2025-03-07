package escrituras.utiles;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.gestoresmadrid.core.imputaciones.model.vo.TipoImputacionVO;
import org.gestoresmadrid.core.legalizacion.model.enumerados.EstadoPeticion;
import org.gestoresmadrid.core.legalizacion.model.enumerados.TipoDocumento;
import org.gestoresmadrid.core.legalizacion.model.enumerados.TipoNoSi;
import org.gestoresmadrid.core.legalizacion.model.enumerados.TipoSiNo;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.EstadoCivil;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.semaforo.model.enumerados.EstadoSemaforo;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.imputaciones.model.service.ServicioImputacionHorasInt;
import org.gestoresmadrid.oegam2comun.imputaciones.views.bean.TipoImputacionBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.placas.utilities.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.icogam.legalizacion.utiles.ClaseDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.utiles.enumerados.Aplicacion;
import escrituras.utiles.enumerados.DecisionTrafico;
import escrituras.utiles.enumerados.EstadoTramiteCredito;
import escrituras.utiles.enumerados.Modelos;
import escrituras.utiles.enumerados.SexoPersona;
import escrituras.utiles.enumerados.SubPersona;
import escrituras.utiles.enumerados.TipoDocumentoAlternativo;
import facturacion.utiles.enumerados.TipoSINO;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.ModeloIEDTM;
import trafico.utiles.enumerados.ModeloISD;
import trafico.utiles.enumerados.ModeloITP;
import trafico.utiles.enumerados.ModoAdjudicacion;
import trafico.utiles.enumerados.OegamStatusEnum;
import trafico.utiles.enumerados.TipoTramiteTraficoJustificante;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVista {

	private static UtilesVista utilesVista;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public UtilesVista() {
		super();
	}

	public static UtilesVista getInstance() {
		if (utilesVista == null) {
			utilesVista = new UtilesVista();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVista);
		}
		return utilesVista;
	}

	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO = "5";
	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADOS = "10";
	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO_ESTADISTICAS = "10";
	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO_STOCK_PEGATINAS = "10";
	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO_DIRECCIONES = "10";
	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO_CREDITOS = "50";
	private static final String[] resultadosPorPagina = new String[] { "5", "10", "15", "20", "50", "100", "200", "500" };
	private static final String TOPE_RESULTADOS_POR_PAGINA = "999";
	private static List<String> comboResultadosPorPagina = null;
	private static List<String> comboResultadosPorPaginaTope = null;

	private List<ProvinciaDto> provinciasSujetoPasivo;
	private List<TipoImputacionVO> listaTipoImputacionVO;
	private List<TipoImputacionBean> tipoImputacion;
	private List<ProcesoDto> listaProcesos;

	private static boolean _iniciados = false;

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVista.class);

	@Autowired
	private ServicioImputacionHorasInt servicioImputacionHoras;

	@Autowired
	private ServicioProvincia servicioProvincia;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private ServicioProcesos servicioProcesos;

	public JefaturasJPTEnum[] getJefaturasJPTEnums() {
		return JefaturasJPTEnum.values();
	}

	public String getJefaturaProvincial(String idJefaturaProvincial) {
		JefaturasJPTEnum[] jefaturas = getJefaturasJPTEnums();

		for (JefaturasJPTEnum jefaturaJpt : jefaturas) {
			if (jefaturaJpt.getJefatura().equals(idJefaturaProvincial)) {
				return jefaturaJpt.getDescripcion();
			}
		}
		return null;
	}

	public JefaturasJPTEnum[] getJefaturaProvincialPegatinas() {
		JefaturasJPTEnum[] jefaturas = getJefaturasJPTEnums();
			for (int i = 0; i < jefaturas.length; i++) {
				// Eliminamos Alcalá de Henares ya que GESTOCK no contempla que Alcalá de Henares sea una sucursal de Madrid
				if ("ALCALA DE HENARES".equals(jefaturas[i].getDescripcion())) {
					jefaturas = (JefaturasJPTEnum[]) ArrayUtils.remove(jefaturas, i);
				}
			}

		return jefaturas;
	}

	public List<String> getComboResultadosPorPagina() {
		if (comboResultadosPorPagina == null) {
			comboResultadosPorPagina = new ArrayList<>();
			for (int i = 0; i < resultadosPorPagina.length; i++) {
				comboResultadosPorPagina.add(resultadosPorPagina[i]);
			}
		}
		if (comboResultadosPorPaginaTope == null) {
			comboResultadosPorPaginaTope = new ArrayList<>();
			for (int i = 0; i < resultadosPorPagina.length; i++) {
				comboResultadosPorPaginaTope.add(resultadosPorPagina[i]);
			}
			comboResultadosPorPaginaTope.add(TOPE_RESULTADOS_POR_PAGINA);
		}
		String colegiadosSinCorreo = gestorPropiedades.valorPropertie("resultadosPagina.tope.permisos");
		if (utilesColegiado.tienePermisoAdmin() || (colegiadosSinCorreo != null && colegiadosSinCorreo.contains(utilesColegiado.getNumColegiadoSession()))) {
			return comboResultadosPorPaginaTope;
		} else {
			return comboResultadosPorPagina;
		}
	}

	public Estado[] getComboEstados() {
		return Estado.values();
	}

	public SexoPersona[] getComboSexo() {
		return SexoPersona.values();
	}

	public EstadoTramiteCredito[] getComboIncreDecre() {
		return EstadoTramiteCredito.values();
	}

	public Aplicacion[] getComboAplicacion() {
		return Aplicacion.values();
	}

	public EstadoCivil[] getComboEstadosCivil() {
		return EstadoCivil.values();
	}

	public TipoPersona[] getComboTipoPersonas() {
		return TipoPersona.values();
	}

	public TipoInterviniente[] getComboTipoIntervinientes() {
		return TipoInterviniente.values();
	}

	public SubPersona[] getComboTipoSubtipoPersonas() {
		return SubPersona.values();
	}

	public Modelos[] getComboModelos() {
		return Modelos.values();
	}

	public DecisionTrafico[] getComboDecisionTrafico() {
		return DecisionTrafico.values();
	}

	public TipoTransferencia[] getComboTipoTransferencia() {
		return TipoTransferencia.values();
	}

	public TipoTransferenciaActual[] getComboTipoTransferenciaActual() {
		return TipoTransferenciaActual.values();
	}

	public ModoAdjudicacion[] getComboModoAdjudicacion() {
		return ModoAdjudicacion.values();
	}

	public TipoTramiteTraficoJustificante[] getComboTipoTramitesJustificantes() {
		TipoTramiteTraficoJustificante[] motivos = new TipoTramiteTraficoJustificante[1];
		motivos[0]=TipoTramiteTraficoJustificante.TRANSFERENCIA;
		return motivos;
//		String habilitar = gestorPropiedades.valorPropertie("habilitar.justificantes.temporal");
//		if ("SI".equals(habilitar)) {
//			TipoTramiteTraficoJustificante[] motivos = new TipoTramiteTraficoJustificante[4];
//			motivos[0]=TipoTramiteTraficoJustificante.BAJA_TEMPORAL;
//			motivos[1]=TipoTramiteTraficoJustificante.CAMBIO_TITULAR;
//			motivos[2]=TipoTramiteTraficoJustificante.DUPLICADO;
//			motivos[3]=TipoTramiteTraficoJustificante.NOTIFICACION_VENTA;
//			return motivos;
//		} else {
//			TipoTramiteTraficoJustificante[] motivos = new TipoTramiteTraficoJustificante[3];
//			motivos[0]=TipoTramiteTraficoJustificante.TransmisionElectronica;
//			motivos[1]=TipoTramiteTraficoJustificante.Duplicado;
//			motivos[2]=TipoTramiteTraficoJustificante.Baja;
//			return motivos;
//		}
	}

	public EstadoJustificante[] getComboEstadoJustificante() {
		return EstadoJustificante.values();
	}

	public int[] getComboNum0A50() {
		int[] numeros = new int[51];
		for (int i = 0; i < numeros.length; i++) {
			numeros[i] = i;
		}
		return numeros;
	}

	public TipoDocumentoAlternativo[] getComboTipoDocumentoAlternativo() {
		return TipoDocumentoAlternativo.values();
	}

	private void iniciarCombos() {
		log.debug("iniciarCombos()");
		try {
			if (provinciasSujetoPasivo == null) {
				provinciasSujetoPasivo = servicioProvincia.getListaProvincias();
			}
			if (listaTipoImputacionVO == null) {
				listaTipoImputacionVO = servicioImputacionHoras.getListaTipoImputacion();
				if (listaTipoImputacionVO == null) {
					listaTipoImputacionVO = Collections.emptyList();
				}
			}

			if (tipoImputacion == null) {
				tipoImputacion = new ArrayList<>();
				for (TipoImputacionVO tipoImputacionVO : listaTipoImputacionVO) {
					TipoImputacionBean tipoImpBean = new TipoImputacionBean();
					tipoImpBean.setIdTipoImputacion(tipoImputacionVO.getIdTipoImputacion());
					tipoImpBean.setDescCorta(tipoImputacionVO.getDescCorta());
					tipoImpBean.setDescripcion(tipoImputacionVO.getDescripcion());
					tipoImputacion.add(tipoImpBean);
				}
			}

			if (listaProcesos == null) {
				listaProcesos = servicioProcesos.getListadoProcesos();
			}
		} catch (Throwable e) {
			log.error("Error al obtener la conexión");
		}
		_iniciados = true;
	}

	public List<TipoImputacionBean> getTipoImputacion() {
		if (tipoImputacion == null) {
			iniciarCombos();
		}
		return tipoImputacion;
	}

	/**
	 * Recibe una cadena en formato de casilla (p.e. 4530) y devuelve una cadena en formato decimal (45,3)
	 * @param cadena
	 * @return
	 */
	public String pasaCadenaToDecimal(String cadena) {
		DecimalFormat DF_DECIMAL_DOS = new DecimalFormat("0.00");
		if (null == cadena || "".equals(cadena)) {
			return null;
		}
		try {
			double d = Double.parseDouble(cadena);
			d = d / 100;
			return DF_DECIMAL_DOS.format(d);
		} catch (Throwable e) {
			log.error(e);
			return null;
		}
	}

	public List<ProvinciaDto> getProvinciasSujetoPasivo() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasSujetoPasivo;
	}

	public OegamStatusEnum[] getListaProcesos() {
		return OegamStatusEnum.values();
	}

	public ModeloITP[] getComboModeloITP() {
		return ModeloITP.values();
	}

	public ModeloISD[] getComboModeloISD() {
		return ModeloISD.values();
	}

	public ModeloIEDTM[] getComboModeloIEDTM() {
		return ModeloIEDTM.values();
	}

	public TipoSINO[] getComboTransferenciaSimultanea() {
		return TipoSINO.values();
	}

	public static TipoDocumento[] getTiposDocumentos() {
		return TipoDocumento.values();
	}

	public static ClaseDocumento[] getClaseDocumento() {
		return ClaseDocumento.values();
	}

	public static TipoSiNo[] getTipoSiNo() {
		return TipoSiNo.values();
	}

	public static TipoNoSi[] getTipoNoSi() {
		return TipoNoSi.values();
	}

	public static EstadoPeticion[] getEstadoPeticion(){
		return EstadoPeticion.values();
	}

	public EstadoSemaforo[] getComboEstadosSemaf() {
		return EstadoSemaforo.values();
	}

	public EstadoVehiculo[] getEstadoVehiculo() {
		return EstadoVehiculo.values();
	}

	public static String getEstadoIvtm(BigDecimal estadoIvtm) {
		return EstadoIVTM.convertirTexto(estadoIvtm.toString());
	}

	public boolean validarBotonIvtm(String nrc, BigDecimal estadoIvtm) {
		if (nrc != null && !nrc.equals("") && !getEstadoIvtm(estadoIvtm).equals(EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum())
				&& !getEstadoIvtm(estadoIvtm).equals(EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto.getValorEnum())) {
			return true;
		}
		return false;
	}

	public Boolean estadoPendienteIvtm(BigDecimal estadoIvtm) {
		if ((estadoIvtm != null) &&
			(EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum().equals(estadoIvtm.toString()) ||
			EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto.getValorEnum().equals(estadoIvtm.toString()))) {
			return true;
		}
		return false;
	}

	public Boolean estadoFinIvtm(BigDecimal estadoIvtm) {
		if(estadoIvtm != null){
			if(EstadoIVTM.Ivtm_Ok.getValorEnum().equals(estadoIvtm.toString()) ||
				EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum().equals(estadoIvtm.toString()) ||
				EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum().equals(estadoIvtm.toString()))
			{
				return true;
			}
		}
		return false;
	}

	public Boolean botonAutoliquidar(BigDecimal estadoIvtm, BigDecimal estadoTramite) {
		if(estadoIvtm != null && estadoTramite !=null){
			if(estadoTramite.toString().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())){
				if(EstadoIVTM.Ivtm_Ok.getValorEnum().equals(estadoIvtm.toString()) ||
					EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum().equals(estadoIvtm.toString()) ||
					EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum().equals(estadoIvtm.toString()) ||
					EstadoIVTM.Pendiente_Respuesta_Modificacion_Ayto.getValorEnum().equals(estadoIvtm.toString()) ||
					EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum().equals(estadoIvtm.toString()))
				{
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}

		return false;
	}

	public static Boolean pagoIvtm(BigDecimal estadoIvtm) {
		if ((estadoIvtm != null) && ((EstadoIVTM.Ivtm_Ok.getValorEnum().equals(estadoIvtm.toString())) || (EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum().equals(estadoIvtm.toString())))) {
			return true;
		}
		return false;
	}

	public static Boolean tieneNRC(String nrc) {
		return (nrc!=null && !"".equals(nrc));
	}

	public static Boolean pagoIvtm2(IvtmMatriculacionDto ivtm) {
		if (ivtm != null && ivtm.getEstadoIvtm() != null
				 && ((EstadoIVTM.Ivtm_Ok.getValorEnum().equals(ivtm.getEstadoIvtm().toString())) || (EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum().equals(ivtm.getEstadoIvtm().toString())))) {
			return true;
		}
		return false;
	}

	public ServicioImputacionHorasInt getServicioImputacionHoras() {
		return servicioImputacionHoras;
	}

	public void setServicioImputacionHoras(ServicioImputacionHorasInt servicioImputacionHoras) {
		this.servicioImputacionHoras = servicioImputacionHoras;
	}

	public BigDecimal stringToBigDecimal(String cadena){
		if(cadena==null || cadena.trim().isEmpty()){
			return new BigDecimal("0");
		}
		DecimalFormat formateador = new DecimalFormat("#,##0.00");
		try {
			return new BigDecimal(formateador.parse(cadena).toString());
		} catch (ParseException e) {
			return null;
		}
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public Map<String, String> getEstadoSolicPlacas(){
		Map<String,String> valoresEstSolPlacas = new HashMap<>();
		EstadoSolicitudPlacasEnum[] valoresEnum = EstadoSolicitudPlacasEnum.values();
		for (EstadoSolicitudPlacasEnum estEnum : valoresEnum) {
			if ("0".equals(estEnum.getValorEnum())) {
				continue;
			}
			valoresEstSolPlacas.put(estEnum.getValorEnum(), estEnum.getNombreEnum());
		}
		return valoresEstSolPlacas;
	}
}