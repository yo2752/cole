package trafico.utiles;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.consultasTGate.model.enumerados.TipoServicioTGate;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.enumerados.TipoDocumentoDuplicado;
import org.gestoresmadrid.core.general.model.vo.TipoCreditoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosImprimir;
import org.gestoresmadrid.core.impresion.model.enumerados.TipoDocumentoImpresiones;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.EstadoCombosConsTram;
import org.gestoresmadrid.core.model.enumerados.EstadoImpresion;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Color;
import org.gestoresmadrid.logs.enumerados.TipoLogFrontalEnum;
import org.gestoresmadrid.logs.enumerados.TipoLogProcesoEnum;
import org.gestoresmadrid.logs.view.beans.ComboMaquinas;
import org.gestoresmadrid.oegam2comun.codigoIAE.model.service.ServicioCodigoIae;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.estacionITV.model.service.ServicioEstacionITV;
import org.gestoresmadrid.oegam2comun.model.service.ServicioTipoTramite;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTNuevoEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoPlacasMatriculasConTipoVehiculoEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoPlacasMatriculasEnum;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.DirectivaCeeDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.FundamentoExencion;
import escrituras.beans.ResultBean;
import escrituras.beans.dao.OficinaLiquidadoraDao;
import escrituras.modelo.ModeloReconocimiento;
import estadisticas.utiles.enumerados.Agrupacion;
import estadisticas.utiles.enumerados.AgrupacionVehiculos;
import general.beans.ComboGenerico;
import hibernate.entities.general.TipoTramiteRenovacion;
import hibernate.entities.trafico.TipoCreacion;
import trafico.beans.CarroceriaBean;
import trafico.beans.ClasificacionCriterioConstruccionBean;
import trafico.beans.ClasificacionCriterioUtilizacionBean;
import trafico.beans.EstacionITVBean;
import trafico.beans.IAEBean;
import trafico.beans.MotivoBean;
import trafico.beans.ServicioTraficoBean;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.TipoMotorBean;
import trafico.beans.TipoReduccionBean;
import trafico.beans.TipoVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.JefaturaTraficoDao;
//TODO MPC. Cambio IVTM. Añadido.
import trafico.dto.TramiteTraficoDto;
import trafico.inteve.ConstantesInteve;
import trafico.inteve.MotivoConsultaInteve;
import trafico.inteve.TipoInformeInteve;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloTrafico;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.AcreditacionTrafico;
import trafico.utiles.enumerados.Alimentacion;
import trafico.utiles.enumerados.Carroceria;
import trafico.utiles.enumerados.CarroceriaSubtipos;
import trafico.utiles.enumerados.CategoriaElectrica;
import trafico.utiles.enumerados.CausaHechoImponible;
import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.ConceptoTutela;
import trafico.utiles.enumerados.DocumentosJustificante;
import trafico.utiles.enumerados.Epigrafe;
import trafico.utiles.enumerados.Estado620;
import trafico.utiles.enumerados.EstadoFactura;
import trafico.utiles.enumerados.EstadoTramiteAnuntis;
import trafico.utiles.enumerados.Fabricacion;
import trafico.utiles.enumerados.LugarPrimeraMatriculacion;
import trafico.utiles.enumerados.MotivoDuplicado;
import trafico.utiles.enumerados.MotivoJustificante;
import trafico.utiles.enumerados.NoSujeccionOExencion;
import trafico.utiles.enumerados.Observaciones;
import trafico.utiles.enumerados.PaisFabricacion;
import trafico.utiles.enumerados.PaisImportacion;
import trafico.utiles.enumerados.Procedencia;
import trafico.utiles.enumerados.ProvinciasEstacionITV;
import trafico.utiles.enumerados.ReduccionNoSujeccionOExencion05;
import trafico.utiles.enumerados.ResultadoITV;
import trafico.utiles.enumerados.TipoAutorizacion;
import trafico.utiles.enumerados.TipoDatoActualizar;
import trafico.utiles.enumerados.TipoDatoBorrar;
import trafico.utiles.enumerados.TipoDocumentoImpresionesMasivas;
import trafico.utiles.enumerados.TipoFactura;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.enumerados.TipoLimitacionDisposicionIEDTM;
import trafico.utiles.enumerados.TipoMotivoExencion;
import trafico.utiles.enumerados.TipoProcedencia;
import trafico.utiles.enumerados.TipoTarjetaITV;
import trafico.utiles.enumerados.TipoTasa;
import trafico.utiles.enumerados.TipoTasaMatriculacion;
import trafico.utiles.enumerados.TipoTasaTipoB;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTutela;
import trafico.utiles.enumerados.TipoVehiculoCam;
import trafico.utiles.enumerados.TipoVehiculoIvtm;
import trafico.utiles.enumerados.TiposInspeccionITV;
import trafico.utiles.enumerados.TiposReduccion576;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class UtilesVistaTrafico {

	private static UtilesVistaTrafico utilesVistaTrafico;

	public static final String RESULTADOS_POR_PAGINA_POR_DEFECTO = "5";
	private static final String[] resultadosPorPagina = new String[] { "5", "10", "15", "20", "50", "100" };
	private static List<String> comboResultadosPorPagina = null;
	private static List<ComboGenerico> comboNumeroTitulares = null;
	private static String COMBO_MAQUINAS = "combo.maquinas.produccion";

	private List<ProvinciaDto> provinciasPresentador;
	private List<ProvinciaDto> provinciasTitular;
	private List<ProvinciaDto> provinciasConductorHabitual;
	private List<ProvinciaDto> provinciasRepresentante;
	private List<ProvinciaDto> provinciasArrendatario;
	private List<ProvinciaDto> provinciasAdquiriente;
	private List<ProvinciaDto> provinciasTransmitente;
	private List<ProvinciaDto> provinciasPoseedor;
	private List<ProvinciaDto> provinciasVehiculo;
	private List<ProvinciaDto> provinciasCet;
	private List<ProvinciaDto> provinciasCem;
	private List<ProvinciaDto> provincias;
	private List<TipoVehiculoBean> tiposVehiculo;

	private List<TipoVehiculoBean> tiposVehiculoMatriculacion;
	private List<TipoVehiculoBean> tiposVehiculoTransmision;
	private List<String> tiposNumeroIVTM;
	private List<ClasificacionCriterioConstruccionBean> clasificacionesCriteriosConstruccionVehiculo;
	private List<ClasificacionCriterioUtilizacionBean> clasificacionesCriteriosUtilizacionVehiculo;
	private List<ServicioTraficoBean> serviciosTrafico;
	private List<ServicioTraficoBean> serviciosTraficoMatriculacion;
	private List<ServicioTraficoBean> serviciosTraficoTransmision;
	private List<JefaturaTraficoDao> jefaturasTrafico;
	private List<JefaturaTraficoDao> jefaturasTraficoTodas;
	private List<OficinaLiquidadoraDao> oficinaLiquidadora;
	private List<FundamentoExencion> fundamentosExencion;
	private List<FundamentoExencion> fundamentosNoSujeto;
	private List<MotivoBean> motivosITV;
	private List<EstacionITVBean> estacionesITV;
	private List<DatoMaestroBean> comboContratos;
	private List<IAEBean> codigosIAE;
	private List<String> tipoMatriculacion; // Modificación
	private List<TipoCreacion> tipoCreacion;
	private TipoTramiteTrafico[] listaTipoTramiteTrafico = null;
	private String[] listaTipoTramiteTraficoValorEnum = null;
	private List<TipoTramiteRenovacion> tipoTramiteRenovacion = null;
	private EstadoTramiteTrafico[] listaEstadosTramiteTrafico;
	private List<CarroceriaBean> listaCarrocerias;
	private Map<String, String> mapaTipoCreditoTramite;

	private ModeloTrafico modeloTrafico;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;

	@Autowired
	private ServicioDirectivaCee servicioDirectivaCee;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioProvincia servicioProvincia;

	@Autowired
	private ServicioCodigoIae servicioCodigoIae;

	@Autowired
	private ServicioTipoTramite servicioTipoTramite;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	ServicioEstacionITV servicioEstacionITV;

	@Autowired
	ServicioTasa servicioTasa;
	
	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;
	
	@Autowired
	Utiles utiles;

	private static boolean _iniciados = false;

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaTrafico.class);

	public static UtilesVistaTrafico getInstance() {
		if (utilesVistaTrafico == null) {
			utilesVistaTrafico = new UtilesVistaTrafico();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaTrafico);
		}
		return utilesVistaTrafico;
	}

	public TipoFactura[] getComboTipoFactura() {
		return TipoFactura.values();
	}

	public EstadoFactura[] getComboEstadoFactura() {
		return EstadoFactura.values();
	}

	public List<String> getTipoMatriculacion() {
		if (tipoMatriculacion == null) {
			iniciarCombos();
		}
		return tipoMatriculacion;
	}

	public void setTipoMatriculacion(List<String> tipoMatriculacion) {
		UtilesVistaTrafico.getInstance().tipoMatriculacion = tipoMatriculacion;
	}

	public static List<String> getComboResultadosPorPagina() {
		if (comboResultadosPorPagina == null) {
			comboResultadosPorPagina = new ArrayList<>();
			for (int i = 0; i < resultadosPorPagina.length; i++) {
				comboResultadosPorPagina.add(resultadosPorPagina[i]);
			}
		}
		return comboResultadosPorPagina;
	}

	public ReduccionNoSujeccionOExencion05[] getReduccionesNoSujeccionOExencion05() {
		return ReduccionNoSujeccionOExencion05.values();
	}

	public NoSujeccionOExencion[] getNoSujeccionesOExencion06() {
		return NoSujeccionOExencion.values();
	}

	public TipoTarjetaITV[] getTiposTarjetaITV() {
		return TipoTarjetaITV.values();
	}

	public TiposReduccion576[] getTiposReduccion576() {
		return TiposReduccion576.values();
	}

	public Combustible[] getCarburantes() {
		return Combustible.values();
	}

	public CategoriaElectrica[] getCategoriaElectrica() {
		return CategoriaElectrica.values();
	}

	public LugarPrimeraMatriculacion[] getLugaresPrimeraMatriculacion() {
		return LugarPrimeraMatriculacion.values();
	}

	public PaisFabricacion[] getPaisesFabricacion() {
		return PaisFabricacion.values();
	}

	public PaisImportacion[] getPaisesImportacion() {
		return PaisImportacion.values();
	}

	public Color[] getColores() {
		return Color.values();
	}

	public Color[] getColores_MATW() {
		return Color.values_MATW();
	}

	public MotivoJustificante[] getMotivosJustificante() {
		return MotivoJustificante.values();
	}

	public DocumentosJustificante[] getDocumentosJustificante() {
		return DocumentosJustificante.values();
	}

	public Integer[] getEjerciciosDevengo() {
		int anyoActual = Calendar.getInstance().get(Calendar.YEAR);
		int primerAnyo = ConstantesTrafico.PRIMER_ANYO_EJERCICIO_DEVENGO;
		int totalAnyos = anyoActual - primerAnyo + 1;
		Integer[] resultado = new Integer[totalAnyos];
		for (int i = 0; i < totalAnyos; i++) {
			resultado[i] = primerAnyo + i;
		}
		return resultado;
	}

	public Observaciones[] getObservaciones() {
		return Observaciones.values();
	}

	public TipoLimitacionDisposicionIEDTM[] getTiposLimitacionDisposicionIEDTM() {
		return TipoLimitacionDisposicionIEDTM.values();
	}

	public TipoMotivoExencion[] getTipoMotivoExencion() {
		return TipoMotivoExencion.values();
	}

	public CausaHechoImponible[] getCausasHechoImponible() {
		return CausaHechoImponible.values();
	}

	public ConceptoTutela[] getConceptoTutela() {
		return ConceptoTutela.values();
	}

	public TipoTutela[] getTiposTutela() {
		return TipoTutela.values();
	}

	public ResultadoITV[] getResultadoITV() {
		return ResultadoITV.values();
	}

	public Epigrafe[] getEpigrafes() {
		return Epigrafe.values();
	}

	public TipoTramiteTrafico[] getComboTipoTramite() {
		if (listaTipoTramiteTrafico == null) {
			TipoTramiteTrafico[] listaTipoTramiteTraficoAux = null;
			int x = 0;
			String[] arrayComboTipoTramiteDesechar = null;
			String comboTipoTramiteDesechar = "";
			boolean permitido = true;
			comboTipoTramiteDesechar = gestorPropiedades.valorPropertie("combo.tipoTramite.desechar");
			arrayComboTipoTramiteDesechar = comboTipoTramiteDesechar.split(",");
			listaTipoTramiteTrafico = new TipoTramiteTrafico[TipoTramiteTrafico.values().length
					- arrayComboTipoTramiteDesechar.length];
			listaTipoTramiteTraficoAux = new TipoTramiteTrafico[listaTipoTramiteTrafico.length];
			listaTipoTramiteTrafico = TipoTramiteTrafico.values();
			for (TipoTramiteTrafico tipoTramiteTrafico : listaTipoTramiteTrafico) {
				for (String cadenaDesechar : arrayComboTipoTramiteDesechar) {
					if (tipoTramiteTrafico.getNombreEnum().equals(cadenaDesechar)) {
						permitido = false;
					}
				}
				if (permitido) {
					listaTipoTramiteTraficoAux[x] = tipoTramiteTrafico;
					x++;
				}
				permitido = true;
			}
			listaTipoTramiteTrafico = listaTipoTramiteTraficoAux;
		}
		return listaTipoTramiteTrafico;
	}

	public TipoTramiteTrafico[] getComboTipoTramiteImpresion() {
		TipoTramiteTrafico[] listaTipoTramiteTrafico = new TipoTramiteTrafico[4];
		listaTipoTramiteTrafico[0] = TipoTramiteTrafico.Baja;
		listaTipoTramiteTrafico[1] = TipoTramiteTrafico.Matriculacion;
		listaTipoTramiteTrafico[2] = TipoTramiteTrafico.TransmisionElectronica;
		listaTipoTramiteTrafico[3] = TipoTramiteTrafico.Duplicado;
		return listaTipoTramiteTrafico;
	}

	public String[] getComboTipoTramiteValorEnum() {
		if (listaTipoTramiteTraficoValorEnum == null) {
			TipoTramiteTrafico[] listaTT = getComboTipoTramite();
			listaTipoTramiteTraficoValorEnum = new String[listaTT.length];
			int i = 0;
			for (TipoTramiteTrafico tipoTramite : listaTT) {
				listaTipoTramiteTraficoValorEnum[i] = tipoTramite.getValorEnum();
				i++;
			}
		}
		return listaTipoTramiteTraficoValorEnum;
	}

	public List<EstadoTramiteTrafico> getComboEstadosFinal() {
		List<EstadoTramiteTrafico> listaEstadoTramiteFinalizados = new ArrayList<EstadoTramiteTrafico>();
		listaEstadoTramiteFinalizados.add(EstadoTramiteTrafico.Finalizado_PDF);
		listaEstadoTramiteFinalizados.add(EstadoTramiteTrafico.Finalizado_Telematicamente);
		listaEstadoTramiteFinalizados.add(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso);
		return listaEstadoTramiteFinalizados;
	}

	public List<TipoTramiteTrafico> getComboTipoTramiteTraficoTransmision() {
		List<TipoTramiteTrafico> listaTipoTramiteTransmision = new ArrayList<TipoTramiteTrafico>();
		listaTipoTramiteTransmision.add(TipoTramiteTrafico.TransmisionElectronica);
		listaTipoTramiteTransmision.add(TipoTramiteTrafico.Transmision);
		return listaTipoTramiteTransmision;
	}

	public EstadoTramiteAnuntis[] getComboEstadosAnuntis() {
		return EstadoTramiteAnuntis.values();
	}

	public Agrupacion[] getComboAgrupacion() {
		return Agrupacion.values();
	}

	public AgrupacionVehiculos[] getComboAgrupacionVehiculos() {
		return AgrupacionVehiculos.values();
	}

	public TipoTramiteMatriculacion[] getComboTiposTramiteMatriculacion() {
		return TipoTramiteMatriculacion.values();
	}

	public List<CarroceriaBean> getCarrocerias() {
		if (listaCarrocerias == null) {

			String idCarroceria = "", descripcion = "";
			listaCarrocerias = new ArrayList<CarroceriaBean>();
			Carroceria[] carroceriaArray = Carroceria.values();
			for (Carroceria carroceria : carroceriaArray) {

				if (carroceria.equals(Carroceria.CAMION) || carroceria.equals(Carroceria.BASTIDOR_CABINA_O_CUBIERTA)
						|| carroceria.equals(Carroceria.SEMIRREMOLQUE)
						|| carroceria.equals(Carroceria.REMOLQUE_BARRAS_TRACCION)
						|| carroceria.equals(Carroceria.REMOLQUE_EJE_CENTRAL)
						|| carroceria.equals(Carroceria.REMOLQUE_BARRA_TRACCION_RIGIDA)
						|| carroceria.equals(Carroceria.FURGONETA) || carroceria.equals(Carroceria.PICK_UP)) {
					addCarroceriaToList(carroceria.getValorEnum(),
							"+++ Carrocerías " + carroceria.getValorEnum() + " (" + carroceria.getNombreEnum() + ")");

					for (CarroceriaSubtipos subtipo : CarroceriaSubtipos.values()) {
						if (carroceria.equals(Carroceria.FURGONETA)) {
							if (subtipo.getValorEnum().equals("03") || subtipo.getValorEnum().equals("27") || subtipo.getValorEnum().equals("99")) {
								idCarroceria = carroceria.getValorEnum() + " " + subtipo.getValorEnum();
								descripcion = carroceria.getValorEnum() + " " + subtipo.getValorEnum() + " - "
										+ subtipo.getNombreEnum();
								addCarroceriaToList(idCarroceria, descripcion);
							}
						} else if (carroceria.equals(Carroceria.PICK_UP)) {
							if (subtipo.getValorEnum().equals("24")) {
								idCarroceria = carroceria.getValorEnum() + " " + subtipo.getValorEnum();
								descripcion = carroceria.getValorEnum() + " " + subtipo.getValorEnum() + " - "
										+ subtipo.getNombreEnum();
								addCarroceriaToList(idCarroceria, descripcion);
							}
						} else {
							if (subtipo.equals(CarroceriaSubtipos.LONA_LATERAL_ABATIBLE)) {
								if (carroceria.equals(Carroceria.CAMION)) {
									idCarroceria = carroceria.getValorEnum() + " " + subtipo.getValorEnum();
									descripcion = carroceria.getValorEnum() + " " + subtipo.getValorEnum() + " - "
											+ subtipo.getNombreEnum();
									addCarroceriaToList(idCarroceria, descripcion);
								}
							} else {
								idCarroceria = carroceria.getValorEnum() + " " + subtipo.getValorEnum();
								descripcion = carroceria.getValorEnum() + " " + subtipo.getValorEnum() + " - "
										+ subtipo.getNombreEnum();
								addCarroceriaToList(idCarroceria, descripcion);
							}
						}
					}
				} else {
					addCarroceriaToList(carroceria.getValorEnum(),
							carroceria.getValorEnum() + " - " + carroceria.getNombreEnum());
				}
			}
		}
		return listaCarrocerias;
	}

	private void addCarroceriaToList(String idCarroceria, String descripcion) {
		CarroceriaBean carBean = new CarroceriaBean();
		carBean.setIdCarroceria(idCarroceria);
		carBean.setDescripcion(descripcion);
		listaCarrocerias.add(carBean);
	}

	public Alimentacion[] getTiposAlimentacion() {
		return Alimentacion.values();
	}

	public List<DirectivaCeeDto> getListaDirectivasCEE(TramiteTraficoBean tramiteTraficoBean) {
		if (tramiteTraficoBean != null && tramiteTraficoBean.getVehiculo() != null
				&& tramiteTraficoBean.getVehiculo().getCriterioConstruccionBean() != null) {
			List<DirectivaCeeDto> lista = servicioDirectivaCee.listadoDirectivaCee(
					tramiteTraficoBean.getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion());
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		}
		return Collections.emptyList();
	}

	public List<DirectivaCeeDto> getListaDirectivasCEEVehiculo(String idDirectivaCee) {
		if (idDirectivaCee != null && !idDirectivaCee.isEmpty()) {
			List<DirectivaCeeDto> lista = servicioDirectivaCee.listadoDirectivaCee(idDirectivaCee);
			if (lista != null && !lista.isEmpty()) {
				return lista;
			}
		}
		return Collections.emptyList();
	}

	public EstadoTramiteTrafico[] getEstadosTramiteTrafico() {
		EstadoTramiteTrafico[] listaEstadosTramiteTraficoAux = null;
		if (listaEstadosTramiteTrafico == null) {
			listaEstadosTramiteTraficoAux = EstadoTramiteTrafico.values();
			String[] nombres = new String[listaEstadosTramiteTraficoAux.length];
			for (int i = 0; i < listaEstadosTramiteTraficoAux.length; i++) {
				nombres[i] = listaEstadosTramiteTraficoAux[i].getNombreEnum();
			}
			Arrays.sort(nombres);
			listaEstadosTramiteTrafico = new EstadoTramiteTrafico[listaEstadosTramiteTraficoAux.length];
			for (int i = 0; i < nombres.length; i++) {
				for (int j = 0; j < listaEstadosTramiteTraficoAux.length; j++) {
					if (nombres[i].equals(listaEstadosTramiteTraficoAux[j].getNombreEnum())) {
						listaEstadosTramiteTrafico[i] = listaEstadosTramiteTraficoAux[j];
					}
				}
			}
		}
		return listaEstadosTramiteTrafico;
	}

	public EstadoJustificante[] getEstadoJustificante() {
		return EstadoJustificante.values();
	}

	public Estado620[] getEstados620() {
		return Estado620.values();
	}

	public AcreditacionTrafico[] getAcreditacionesTrafico() {
		return AcreditacionTrafico.values();
	}

	public TipoVehiculoCam[] getTiposVehiculoCam() {
		return TipoVehiculoCam.values();
	}

	public ProvinciasEstacionITV[] getProvinciasEstacionItv() {
		return ProvinciasEstacionITV.values();
	}

	public TipoProcedencia[] getTipoProcedencia620() {
		return TipoProcedencia.values();
	}

	/**
	 * Se encarga de inicializar los combos al arrancar cada instancia de la
	 * aplicación (en cada frontal). Si aparecen nuevos combos aquí, que se quiera
	 * que se recargen, incluirlos también en el método "refrescarCombos" de esta
	 * misma clase.
	 */
	private void iniciarCombos() {
		log.info("UtilesVistaTrafico -> iniciarCombos()");
		try {
			if (provincias == null) {
				provincias = servicioProvincia.getListaProvincias();
				if (provincias == null) {
					provincias = new ArrayList<>();
				}
			}
			if (provinciasPresentador == null) {
				provinciasPresentador = provincias;
				if (provinciasPresentador == null) {
					provinciasPresentador = new ArrayList<>();
				}
			}
			if (provinciasTitular == null) {
				provinciasTitular = provincias;
				if (provinciasTitular == null) {
					provinciasTitular = new ArrayList<>();
				}
			}
			if (provinciasConductorHabitual == null) {
				provinciasConductorHabitual = provincias;
				if (provinciasConductorHabitual == null) {
					provinciasConductorHabitual = new ArrayList<>();
				}
			}
			if (provinciasArrendatario == null) {
				provinciasArrendatario = provincias;
				if (provinciasArrendatario == null) {
					provinciasArrendatario = new ArrayList<>();
				}
			}
			if (provinciasAdquiriente == null) {
				provinciasAdquiriente = provincias;
				if (provinciasAdquiriente == null) {
					provinciasAdquiriente = new ArrayList<>();
				}
			}
			if (provinciasTransmitente == null) {
				provinciasTransmitente = provincias;
				if (provinciasTransmitente == null) {
					provinciasTransmitente = new ArrayList<>();
				}
			}
			if (provinciasPoseedor == null) {
				provinciasPoseedor = provincias;
				if (provinciasPoseedor == null) {
					provinciasPoseedor = new ArrayList<>();
				}
			}
			if (provinciasRepresentante == null) {
				provinciasRepresentante = provincias;
				if (provinciasRepresentante == null) {
					provinciasRepresentante = new ArrayList<>();
				}
			}
			if (provinciasVehiculo == null) {
				provinciasVehiculo = provincias;
				if (provinciasVehiculo == null) {
					provinciasVehiculo = new ArrayList<>();
				}
			}
			if (provinciasCet == null) {
				provinciasCet = provincias;
				if (provinciasCet == null) {
					provinciasCet = new ArrayList<>();
				}
			}
			if (provinciasCem == null) {
				provinciasCem = provincias;
				if (provinciasCem == null) {
					provinciasCem = new ArrayList<>();
				}
			}
			if (clasificacionesCriteriosConstruccionVehiculo == null) {
				clasificacionesCriteriosConstruccionVehiculo = getModeloTrafico()
						.obtenerClasificacionesCriteriosConstruccionVehiculo();
				if (clasificacionesCriteriosConstruccionVehiculo == null) {
					clasificacionesCriteriosConstruccionVehiculo = new ArrayList<>();
				}
			}
			if (clasificacionesCriteriosUtilizacionVehiculo == null) {
				clasificacionesCriteriosUtilizacionVehiculo = getModeloTrafico()
						.obtenerClasificacionesCriterioUtilizacion();
				if (clasificacionesCriteriosUtilizacionVehiculo == null) {
					clasificacionesCriteriosUtilizacionVehiculo = new ArrayList<>();
				}
			}
			// En las oficinaLiquidadora me interesa la oficina_liquidadora y el
			// nombre_oficina_liq
			if (oficinaLiquidadora == null) {
				oficinaLiquidadora = getModeloTrafico().obtenerOficinasLiquidadoras();
				if (oficinaLiquidadora == null) {
					oficinaLiquidadora = new ArrayList<>();
				}
			}

			if (fundamentosExencion == null) {
				fundamentosExencion = getModeloTrafico().obtenerFundamentosExencion();
				if (fundamentosExencion == null) {
					fundamentosExencion = new ArrayList<>();
				}
			}
			if (fundamentosNoSujeto == null) {
				fundamentosNoSujeto = getModeloTrafico().obtenerFundamentosNoSujeto();
				if (fundamentosNoSujeto == null) {
					fundamentosNoSujeto = new ArrayList<>();
				}
			}
			if (motivosITV == null) {
				motivosITV = getModeloTrafico().obtenerMotivosITV();
				if (motivosITV == null) {
					motivosITV = new ArrayList<>();
				}
			}
			if (estacionesITV == null) {
				estacionesITV = servicioEstacionITV.getEstacionesItv(null);
			}
			if (comboContratos == null) {
				comboContratos = servicioContrato.getComboContratosHabilitados();
				if (comboContratos == null) {
					comboContratos = Collections.emptyList();
				}
			}
			if (codigosIAE == null) {
				codigosIAE = servicioCodigoIae.obtenerCodigosIAE();
				if (codigosIAE == null) {
					codigosIAE = new ArrayList<>();
				}
			}
			if (tipoMatriculacion == null) {
				tipoMatriculacion = new ArrayList<>();
				tipoMatriculacion.add("ITV");
				tipoMatriculacion.add("NIVE");
			}

			if (tipoCreacion == null) {
				tipoCreacion = getModeloTrafico().obtenerTipoCreaciones();
			}

			if (tipoTramiteRenovacion == null) {
				tipoTramiteRenovacion = new ModeloReconocimiento().listTipoTramiteRenovacion();
			}

			if (listaCarrocerias == null) {
				listaCarrocerias = getCarrocerias();
				if (listaCarrocerias == null) {
					listaCarrocerias = new ArrayList<>();
				}
			}

			if (mapaTipoCreditoTramite == null) {
				mapaTipoCreditoTramite = new HashMap<>();
				List<TipoCreditoTramiteVO> listaTipoCreditosTramites = servicioCredito
						.getListaTiposCreditosTramite(null);
				for (TipoCreditoTramiteVO tipoCreditoTramite : listaTipoCreditosTramites) {
					mapaTipoCreditoTramite.put(tipoCreditoTramite.getTipoCredito(),
							tipoCreditoTramite.getTipoTramite());
				}
			}

		} catch (Throwable e) {
			log.error("Error al obtener la conexión");
		}
		_iniciados = true;
	}

	public List<ClasificacionCriterioConstruccionBean> getClasificacionesCriterioConstruccion() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return clasificacionesCriteriosConstruccionVehiculo;
	}

	/**
	 * Para obtener el combo de códigos IAE de autónomo
	 * 
	 * @return
	 */
	public List<IAEBean> getCodigosIAE() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return codigosIAE;
	}

	public List<ClasificacionCriterioUtilizacionBean> getClasificacionesCriterioUtilizacion() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return clasificacionesCriteriosUtilizacionVehiculo;
	}

	public List<ProvinciaDto> getProvincias() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provincias;
	}

	public List<TipoCreacion> getTipoCreaciones() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return tipoCreacion;
	}

	public List<ServicioTraficoBean> getServiciosTrafico(String tipoTramite) {
		if (!_iniciados) {
			iniciarCombos();
		}
		log.info("inicio getServiciosTrafico");
		if (tipoTramite != null && !tipoTramite.equals("")) {
			if (tipoTramite.equals(TipoTramiteTrafico.Matriculacion.getValorEnum())) {
				if (serviciosTraficoMatriculacion == null)
					serviciosTraficoMatriculacion = getModeloTrafico().obtenerServiciosTrafico(tipoTramite);
				if (serviciosTraficoMatriculacion == null)
					serviciosTraficoMatriculacion = new ArrayList<>();
				log.info("devuelve servicios trafico matriculacion");
				return serviciosTraficoMatriculacion;
			} else if (tipoTramite.equals(TipoTramiteTrafico.Transmision.getValorEnum())) {
				if (serviciosTraficoTransmision == null)
					serviciosTraficoTransmision = getModeloTrafico().obtenerServiciosTrafico(tipoTramite);
				if (serviciosTraficoTransmision == null)
					serviciosTraficoTransmision = new ArrayList<>();
				log.info("Devuelve servicios tráfico transmisión");
				return serviciosTraficoTransmision;
			} else {
				serviciosTrafico = getModeloTrafico().obtenerServiciosTrafico(tipoTramite);
				if (serviciosTrafico == null)
					serviciosTrafico = new ArrayList<>();
				log.info("Devuelve servicios tráfico del tipo de trámite:" + tipoTramite);
			}
		}
		return serviciosTrafico;
	}

	public TipoVehiculoIvtm[] getTiposVehiculoIVTM() {
		return TipoVehiculoIvtm.values();
	}

	public List<String> getTiposNumeroIVTM() {
		tiposNumeroIVTM = getModeloTrafico().tiposNumeroIVTM();
		return tiposNumeroIVTM;
	}

	public List<TipoVehiculoBean> getTiposVehiculo(String tipoTramite) {
		if (!_iniciados) {
			iniciarCombos();
		}

		if (tipoTramite != null && !tipoTramite.equals("")) {
			if (tipoTramite.equals(TipoTramiteTrafico.Matriculacion.getValorEnum())) {
				if (tiposVehiculoMatriculacion == null) {
					tiposVehiculoMatriculacion = getModeloTrafico().obtenerTiposVehiculo(tipoTramite);
				}
				if (tiposVehiculoMatriculacion == null)
					tiposVehiculoMatriculacion = new ArrayList<>();
				log.info("Devuelve tipos vehículo matriculación");
				return tiposVehiculoMatriculacion;
			} else if (tipoTramite.equals(TipoTramiteTrafico.Transmision.getValorEnum())) {
				if (tiposVehiculoTransmision == null) {
					tiposVehiculoTransmision = getModeloTrafico().obtenerTiposVehiculo(tipoTramite);
				}
				if (tiposVehiculoTransmision == null)
					tiposVehiculoTransmision = new ArrayList<>();
				log.info("Devuelve tipos vehículo matriculación");
				return tiposVehiculoTransmision;
			} else {
				tiposVehiculo = getModeloTrafico().obtenerTiposVehiculo(tipoTramite);
				if (tiposVehiculo == null)
					tiposVehiculo = new ArrayList<>();
				log.info("Devuelve tipos vehículo del tipo de trámite:" + tipoTramite);
			}
		}
		return tiposVehiculo;
	}

	// Obtiene lista de tipos de motor
	public List<TipoMotorBean> getTiposMotor() {
		if (!_iniciados) {
			iniciarCombos();
		}

		List<TipoMotorBean> tiposMotor = getModeloTrafico().obtenerTiposMotor();
		if (tiposMotor == null)
			tiposMotor = new ArrayList<>();
		log.info("Devuelve tipos de motor");

		return tiposMotor;
	}

	// Obtiene lista de tipos de reducción
	public List<TipoReduccionBean> getTiposReduccion() {
		if (!_iniciados) {
			iniciarCombos();
		}

		List<TipoReduccionBean> tiposReduccion = getModeloTrafico().obtenerTiposReduccion();
		if (tiposReduccion == null)
			tiposReduccion = new ArrayList<>();
		log.info("Devuelve tipos de reducción");

		return tiposReduccion;
	}

	public List<ProvinciaDto> getProvinciasTitular() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasTitular;
	}

	public List<ProvinciaDto> getProvinciasConductorHabitual() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasConductorHabitual;
	}

	public List<ProvinciaDto> getProvinciasArrendatario() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasArrendatario;
	}

	public List<ProvinciaDto> getProvinciasAdquiriente() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasAdquiriente;
	}

	public List<ProvinciaDto> getProvinciasTransmitente() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasTransmitente;
	}

	public List<ProvinciaDto> getProvinciasPoseedor() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasPoseedor;
	}

	public List<ProvinciaDto> getProvinciasPresentador() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasPresentador;
	}

	public List<ProvinciaDto> getProvinciasRepresentante() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasRepresentante;
	}

	public List<ProvinciaDto> getProvinciasCliente() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasRepresentante;
	}

	public List<ProvinciaDto> getProvinciasVehiculo() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasVehiculo;
	}

	public List<ProvinciaDto> getProvinciasCet() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasCet;
	}

	public List<MotivoBaja> getMotivosBaja(TramiteTrafBajaDto tramiteTraficoBaja) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaAnterior = sdf.parse("21/05/2018");
			if (tramiteTraficoBaja != null && tramiteTraficoBaja.getFechaPresentacion() != null
					&& !tramiteTraficoBaja.getFechaPresentacion().isfechaNula()) {
				if (fechaAnterior.before(tramiteTraficoBaja.getFechaPresentacion().getFecha())) {
					return MotivoBaja.getMotivosSinPosteriorCtit();
				} else {
					return MotivoBaja.getMotivosSinDefinitivaVoluntaria();
				}
			} else {
				return MotivoBaja.getMotivosSinPosteriorCtit();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public MotivoDuplicado[] getMotivosDuplicado() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return MotivoDuplicado.values();
	}

	public List<JefaturaTraficoDao> getJefaturasTrafico() {
		jefaturasTrafico = getModeloTrafico().obtenerJefaturasProvinciales();
		return jefaturasTrafico;
	}

	public List<JefaturaTraficoDao> getJefaturasTraficoTodas() {
		jefaturasTraficoTodas = getModeloTrafico().obtenerJefaturasProvincialesTodas();
		return jefaturasTraficoTodas;
	}

	public List<OficinaLiquidadoraDao> getOficinasLiquidadoras() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return oficinaLiquidadora;
	}

	public List<FundamentoExencion> getFundamentosExencion() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return fundamentosExencion;
	}
	
	public List<FundamentoExencion> getFundamentosNoSujeto() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return fundamentosNoSujeto;
	}

	public Procedencia[] getProcedencias() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return Procedencia.values();
	}

	public Fabricacion[] getFabricacion() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return Fabricacion.values();
	}

	public List<TasaDto> getCodigosTasaMatriculacion(BigDecimal idContratoTramite) {
		if (null == idContratoTramite)
			idContratoTramite = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasa = null;
		codigosTasa = getModeloTrafico().getTasasLibresMatriculacion(idContratoTramite);
		if (codigosTasa == null) {
			codigosTasa = new ArrayList<TasaDto>();
		}
		return codigosTasa;
	}

	public List<TasaDto> getCodigosTasaMatriculacion(BigDecimal idContratoTramite, String tipoTasa) {
		if (null == idContratoTramite)
			idContratoTramite = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasa = null;
		codigosTasa = getModeloTrafico().obtenerTasasMatriculacion(idContratoTramite, tipoTasa);
		if (codigosTasa == null) {
			codigosTasa = new ArrayList<TasaDto>();
		}
		return codigosTasa;
	}

	public List<TasaDto> getCodigosTasaMatriculacion(String tasaSeleccionada, BigDecimal idContratoTramite,
			String tipoTasa) {
		List<TasaDto> codigosTasa = new ArrayList<TasaDto>();
		if ((tipoTasa == null || "-1".equals(tipoTasa) || "".equals(tipoTasa)) && idContratoTramite == null) {
			return new ArrayList<TasaDto>();
		} else if (tipoTasa == null || "-1".equals(tipoTasa) || "".equals(tipoTasa)) {
			codigosTasa = getCodigosTasaMatriculacion(idContratoTramite);
		} else {
			codigosTasa = getCodigosTasaMatriculacion(idContratoTramite, tipoTasa);
		}
		if (!"".equals(tasaSeleccionada) && null != tasaSeleccionada && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasa;
			if (!codigosTasa.contains(tasa))
				respuestaConTasaSeleccionada.add(0, tasa);
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasa;
	}

	public List<TasaDto> getCodigosTasaBajas(BigDecimal idContrato) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasaBajas = getModeloTrafico().obtenerTasasBajas(idContrato);
		if (codigosTasaBajas == null) {
			codigosTasaBajas = new ArrayList<TasaDto>();
		}
		return codigosTasaBajas;
	}

	public List<TasaDto> getCodigosTasaDuplicados(BigDecimal idContrato) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasaDuplicados = getModeloTrafico().obtenerTasasDuplicados(idContrato);
		if (codigosTasaDuplicados == null) {
			codigosTasaDuplicados = new ArrayList<TasaDto>();
		}
		return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaCambServicio(BigDecimal idContrato) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasaDuplicados = getModeloTrafico().obtenerTasasDuplicados(idContrato);
		if (codigosTasaDuplicados == null) {
			codigosTasaDuplicados = new ArrayList<TasaDto>();
		}
		return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaBajas(String tasaSeleccionada, BigDecimal idContrato) {
		List<TasaDto> codigosTasaBajas = getCodigosTasaBajas(idContrato);
		if (!"".equals(tasaSeleccionada) && null != tasaSeleccionada && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaBajas;
			if (!codigosTasaBajas.contains(tasa))
				respuestaConTasaSeleccionada.add(0, tasa);
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasaBajas;
	}

	public List<TasaDto> getCodigosTasaDuplicados(String tasaSeleccionada, BigDecimal idContrato) {
		List<TasaDto> codigosTasaDuplicados = getCodigosTasaDuplicados(idContrato);
		if (!"".equals(tasaSeleccionada) && null != tasaSeleccionada && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaDuplicados;
			if (!codigosTasaDuplicados.contains(tasa))
				respuestaConTasaSeleccionada.add(0, tasa);
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaLibres(BigDecimal idContrato) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasaLibres = getModeloTrafico().obtenerCodigosTasaLibres(idContrato);
		if (codigosTasaLibres == null) {
			codigosTasaLibres = new ArrayList<TasaDto>();
		}
		return codigosTasaLibres;
	}

	public List<TasaDto> getCodigosTasaLibres(String tasaSeleccionada, BigDecimal idContrato) {
		List<TasaDto> codigosTasaLibres = getCodigosTasaLibres(idContrato);
		if (!"".equals(tasaSeleccionada) && null != tasaSeleccionada && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaLibres;
			if (!codigosTasaLibres.contains(tasa)) {
				respuestaConTasaSeleccionada.add(0, tasa);
			}
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasaLibres;
	}

	/*
	 * @author: Santiago Cuenca Fecha: 05/07/2012 Incidencia: 0002016. Añado métodos
	 * para obtener tasas libres partiendo de un tipo de tasa seleccionada.
	 * Necesario para que al volver a entrar a un trámite no muestre todas las
	 * tasas, sino solo las del tipo previamente seleccionado.
	 */
	public List<TasaDto> getCodigosTasaLibresConTipoTasaSeleccionada(String tipoTasaSeleccionada,
			BigDecimal idContrato) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSessionBigDecimal();
		List<TasaDto> codigosTasaLibres = servicioTasa.getTasasLibres(idContrato.longValue(), tipoTasaSeleccionada);
		if (codigosTasaLibres == null) {
			codigosTasaLibres = new ArrayList<TasaDto>();
		}
		return codigosTasaLibres;
	}

	public List<TasaDto> getCodigosTasaLibresConTipoTasaSeleccionada(String tasaSeleccionada,
			String tipoTasaSeleccionada, BigDecimal idContrato) {
		List<TasaDto> codigosTasaLibres = getCodigosTasaLibresConTipoTasaSeleccionada(tipoTasaSeleccionada, idContrato);
		if (tasaSeleccionada != null && !tasaSeleccionada.isEmpty() && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			if (codigosTasaLibres == null || codigosTasaLibres.isEmpty()) {
				codigosTasaLibres = new ArrayList<TasaDto>();
				codigosTasaLibres.add(tasa);
			} else if (!codigosTasaLibres.contains(tasa)) {
				codigosTasaLibres.add(0, tasa);
			}
		}
		return codigosTasaLibres;
	}

	public List<MotivoBean> getMotivosITV() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return motivosITV;
	}

	public TiposInspeccionITV[] getTiposInspeccionITVMatriculacion() {
		return TiposInspeccionITV.values();
	}

	public List<EstacionITVBean> getEstacionesITV() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return estacionesITV;
	}

	public List<DatoMaestroBean> getComboContratos() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return comboContratos;
	}

	public TipoTasaMatriculacion[] getComboTipoTasaMatriculacion() {
		return TipoTasaMatriculacion.values();
	}
	
	public TipoDocumentoDuplicado[] getComboTipoDocumentoDuplicado() {
		return TipoDocumentoDuplicado.values();
	}

	public TipoTasa[] getComboTipoTasa() {
		return TipoTasa.values();
	}

	public TipoPlacasMatriculasEnum[] getTiposPlacasMatriculacion(String location) {

		TipoPlacasMatriculasEnum[] tipoPlacasMatriculasEnum = TipoPlacasMatriculasEnum.values();

		if (null == location) {
			return tipoPlacasMatriculasEnum;
		}

		List<TipoPlacasMatriculasEnum> listTipoPlacasMatriculas = new ArrayList<TipoPlacasMatriculasEnum>(
				Arrays.asList(tipoPlacasMatriculasEnum));

		List<TipoPlacasMatriculasEnum> listTipoPlacasMatriculaswork = new ArrayList<TipoPlacasMatriculasEnum>();
		for (TipoPlacasMatriculasEnum item : listTipoPlacasMatriculas) {
			if ("ambos".equals(item.getLocation()) || location.equals(item.getLocation())) {
				listTipoPlacasMatriculaswork.add(item);
			}
		}

		TipoPlacasMatriculasEnum[] devuelve = listTipoPlacasMatriculaswork
				.toArray(new TipoPlacasMatriculasEnum[listTipoPlacasMatriculaswork.size()]);
		return devuelve;
	}

	public TipoPlacasMatriculasConTipoVehiculoEnum[] getTiposPlacasMatriculacion(String location,
			String tipoVehiculoExterno) {

		String tipoVehiculoInterno = null;

		if (tipoVehiculoExterno != null) {
			tipoVehiculoInterno = TipoPlacasMatriculasConTipoVehiculoEnum.tranlacion(tipoVehiculoExterno);
		} else {
			tipoVehiculoInterno = null;
		}

		TipoPlacasMatriculasConTipoVehiculoEnum[] tipoPlacasMatriculasConTipoVehiculoEnum = TipoPlacasMatriculasConTipoVehiculoEnum
				.values();

		if (null == location && null == tipoVehiculoInterno) {
			return tipoPlacasMatriculasConTipoVehiculoEnum;
		}

		List<TipoPlacasMatriculasConTipoVehiculoEnum> listTipoPlacasMatriculas = new ArrayList<TipoPlacasMatriculasConTipoVehiculoEnum>(
				Arrays.asList(tipoPlacasMatriculasConTipoVehiculoEnum));

		List<TipoPlacasMatriculasConTipoVehiculoEnum> listTipoPlacasMatriculaswork = new ArrayList<TipoPlacasMatriculasConTipoVehiculoEnum>();

		if (null == location && null != tipoVehiculoInterno) {
			for (TipoPlacasMatriculasConTipoVehiculoEnum item : listTipoPlacasMatriculas) {
				if (tipoVehiculoInterno.equals(item.getTipoVehiclo())) {
					listTipoPlacasMatriculaswork.add(item);
				}
			}
		} else {
			for (TipoPlacasMatriculasConTipoVehiculoEnum item : listTipoPlacasMatriculas) {
				if (("ambos".equals(item.getLocation()) || location.equals(item.getLocation()))
						&& (tipoVehiculoInterno.equals(item.getTipoVehiclo()) || tipoVehiculoInterno == null)) {
					listTipoPlacasMatriculaswork.add(item);
				}
			}
		}

		TipoPlacasMatriculasConTipoVehiculoEnum[] devuelve = listTipoPlacasMatriculaswork
				.toArray(new TipoPlacasMatriculasConTipoVehiculoEnum[listTipoPlacasMatriculaswork.size()]);
		return devuelve;
	}

	public TipoTasaTipoB[] getComboTipoTasaTipoB() {
		return TipoTasaTipoB.values();
	}

	/**
	 * @deprecated Mate se encuenta ya obsoleto
	 */
	@Deprecated
	public boolean esImprimibleMATEPdfPresentacionTelematica(TramiteTraficoBean tramiteTrafico) {
		return esImprimibleMatriculacionPdfPresentacionTelematica(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION,
				tramiteTrafico);
	}

	public boolean esImprimibleMATWPdfPresentacionTelematica(TramiteTraficoBean tramiteTrafico) {
		return esImprimibleMatriculacionPdfPresentacionTelematica(
				UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW, tramiteTrafico);
	}

	public boolean esClonable(TramiteTraficoBean tramiteTraficoBean) {
		boolean esOk = false;
		if (tramiteTraficoBean != null) {
			if (tramiteTraficoBean.getEstado() != null
					&& org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.Iniciado.getValorEnum()
							.equals(tramiteTraficoBean.getEstado().getValorEnum())) {
				esOk = true;
			}
		}
		return esOk;
	}

	private boolean esImprimibleMatriculacionPdfPresentacionTelematica(String permisoMantenimiento,
			TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
				if (tramiteTrafico.getEstado() != null)
					if (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.LiberadoEEFF)) {
						return true;
					}
			}
			return false;
		}
		return false;
	}

	public boolean esValidableMATW(TramiteTraficoBean tramiteTrafico) {
		return esValidableMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW, tramiteTrafico);
	}

	/**
	 * @deprecated Mate se encuentra ya obsoleto
	 */
	@Deprecated
	public boolean esValidableMATE(TramiteTraficoBean tramiteTrafico) {
		return esValidableMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION, tramiteTrafico);
	}

	private boolean esValidableMatriculacion(String permisoMantenimiento, TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
				if (tramiteTrafico.getEstado() != null) {
					if (EstadoTramiteTrafico.Iniciado.equals(tramiteTrafico.getEstado())
							|| EstadoTramiteTrafico.TramitadoNRE06.equals(tramiteTrafico.getEstado())
							|| EstadoTramiteTrafico.Consultado_EITV.equals(tramiteTrafico.getEstado())) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	public boolean esMatriculableTelematicamente(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MATRICULACION_TELEMATICA)) {
				if (tramiteTrafico.getEstado() != null) {
					if (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.LiberadoEEFF)) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	public boolean esConsultableOGuardableMATW(TramiteTraficoBean tramiteTrafico) {
		return esConsultableOGuardableMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW,
				tramiteTrafico);
	}

	public boolean esFinalizadoTelematicamenteImpreso(TramiteTraficoBean tramiteTrafico) {
		return esFinalizadoTelemImpreso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW, tramiteTrafico);
	}

	private boolean esFinalizadoTelemImpreso(String permisoMantenimiento, TramiteTraficoBean tramiteTrafico) {
		return esFinalizadoTelemImpreso(permisoMantenimiento, tramiteTrafico.getEstado());
	}

	private boolean esFinalizadoTelemImpreso(String permisoMantenimiento, EstadoTramiteTrafico estado) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
				if ((estado.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso))) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public Boolean esConsultableEitv(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)) {
				if (tramiteTrafico != null && tramiteTrafico.getEstado() != null) {
					if (EstadoTramiteTrafico.Iniciado.equals(tramiteTrafico.getEstado())
							|| EstadoTramiteTrafico.Error_Consulta_EITV.equals(tramiteTrafico.getEstado())) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	/**
	 * @deprecated Mate se encuentra ya obsoleto
	 */
	@Deprecated
	public boolean esConsultableOGuardableMATE(TramiteTraficoBean tramiteTrafico) {
		return esConsultableOGuardableMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION,
				tramiteTrafico);
	}

	// TODO MPC. Cambio IVTM. Método nuevo
	public boolean esConsultableOGuardableMATW(TramiteTraficoDto tramite) {
		if (tramite == null || tramite.getEstado() == null) {
			return true;
		}
		return esConsultableOGuardableMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW,
				EstadoTramiteTrafico.convertir(tramite.getEstado().toString()));
	}

	// TODO MPC. Cambio IVTM. Método nuevo
	private boolean esConsultableOGuardableMatriculacion(String permisoMantenimiento, EstadoTramiteTrafico estado) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
				if (estado == null || (estado.equals(EstadoTramiteTrafico.Iniciado))
						|| (estado.equals(EstadoTramiteTrafico.Validado_PDF))
						|| (estado.equals(EstadoTramiteTrafico.Finalizado_Con_Error))
						|| (estado.equals(EstadoTramiteTrafico.Validado_Telematicamente))
						|| estado.equals(EstadoTramiteTrafico.LiberadoEEFF)
						|| EstadoTramiteTrafico.Consultado_EITV.equals(estado)
						|| EstadoTramiteTrafico.Error_Consulta_EITV.equals(estado)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	// TODO MPC. Cambio IVTM. Hay que revisar si este método se usa.
	public boolean esConsultableOGuardableMatriculacion(TramiteTraficoBean tramiteTrafico,
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		if (traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion() != null) {
			return esConsultableOGuardableMATW(tramiteTrafico);
		} else {
			return esConsultableOGuardableMATE(tramiteTrafico);
		}
	}

	// TODO MPC. Cambio IVTM. Método Cambiado.
	private boolean esConsultableOGuardableMatriculacion(String permisoMantenimiento,
			TramiteTraficoBean tramiteTrafico) {
		return esConsultableOGuardableMatriculacion(permisoMantenimiento, tramiteTrafico.getEstado());
	}

	public boolean esLiberableEEFF(TramiteTraficoBean tramiteTrafico, EeffLiberacionDTO liberacion) {
		if (liberacion != null && (liberacion.isRealizado() || liberacion.isExento())) {
			return false;
		}
		if (utilesColegiado.tienePermisoEspecial()
				|| !utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MATRICULACION_TELEMATICA)
				|| !utilesColegiado.tienePermisoLiberarEEFF()) {
			return false;
		} else if (tramiteTrafico.getEstado() != null
				&& tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
				&& tramiteTrafico.getVehiculo() != null && tramiteTrafico.getVehiculo().getNive() != null) {
			return true;
		}
		return false;
	}

	public boolean sePuedeObtenerMatriculaMATW(TramiteTraficoBean tramiteTrafico) {
		return sePuedeObtenerMatricula(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW, tramiteTrafico);
	}

	/**
	 * @deprecated Mate se encuenta ya obsoleto
	 */
	@Deprecated
	public boolean sePuedeObtenerMatriculaMATE(TramiteTraficoBean tramiteTrafico) {
		return sePuedeObtenerMatricula(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION, tramiteTrafico);
	}

	private boolean sePuedeObtenerMatricula(String permisoMantenimiento, TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
				if (tramiteTrafico.getEstado() != null
						&& (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF))) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esConsultableOGuardableTransmisionActual(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES)) {
				if (tramiteTrafico.getEstado() == null
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.TramitadoNRE06)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.No_Tramitable)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Jefatura)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esConsultableOGuardableTransmision(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)) {
				if (tramiteTrafico.getEstado() == null
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.TramitadoNRE06)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.No_Tramitable)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Jefatura)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esConsultableOGuardableDuplicado(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
				if (tramiteTrafico.getEstado() == null
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.No_Tramitable)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esComprobableTransmision(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)) {
				if (tramiteTrafico.getEstado() == null
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado)
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.TramitadoNRE06)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public String esNoTramitable(TramiteTraficoTransmisionBean tramiteTrafico) {
		String texto = "";
		if (tramiteTrafico != null && tramiteTrafico.getResCheckCtit() != null
				&& !tramiteTrafico.getResCheckCtit().isEmpty()
				&& "NO TRAMITABLE".equals(tramiteTrafico.getResCheckCtit())) {
			if ("NO".equalsIgnoreCase(gestorPropiedades.valorPropertie("consulta.gest"))) {
				texto = "Es recomendable sacar un informe del vehiculo";
			}
		}
		return texto;
	}

	public boolean esValidableTransmision(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)) {
				if (tramiteTrafico.getEstado() != null) {
					if (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.No_Tramitable)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Tramitable_Jefatura)) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	public boolean esValidableTransmisionActual(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES)) {
				if (tramiteTrafico.getEstado() == null
						|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esTramitableTelematicamenteTransmision(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_TRANSMISIONES_TELEMATICA)) {
				if (tramiteTrafico.getEstado() != null
						&& tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean esImprimibleTransmision(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)) {
				if (tramiteTrafico.getEstado() != null) {
					if (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF)) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	public boolean esImprimibleTransmisionActual(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES)) {
				if (tramiteTrafico.getEstado() != null) {
					if (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)
							|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF)) {
						return true;
					}
				}
			}
			return false;
		}
		return false;
	}

	// Visibilidad de la pestaña facturación trámites de duplicados
	public boolean esFacturableLaTasaDuplicado(TramiteTraficoDuplicadoBean tramiteDuplicado) {
		if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
			if (tramiteDuplicado.getTramiteTrafico().getEstado() != null) {
				if (tramiteDuplicado.getTramiteTrafico().getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel)
						|| tramiteDuplicado.getTramiteTrafico().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Excel_Impreso)
						|| tramiteDuplicado.getTramiteTrafico().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_PDF)) {
					return true;
				}
			}
		}
		return false;
	}

	// Visibilidad de la pestaña facturación para trámites AVPO
	public boolean esFacturableLaTasaSolicitud(SolicitudDatosBean solicitud) {
		if (utilesColegiado.tienePermisoMantenimientoSolicitudes()) {
			if (solicitud.getTramiteTrafico().getEstado() != null) {
				if (solicitud.getTramiteTrafico().getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF)) {
					return true;
				}
			}
		}
		return false;
	}

	// Visibilidad de la pestaña facturación trámites de transmisión:
	public boolean esFacturableLaTasaTransmision(TramiteTraficoTransmisionBean tramiteTransmision) {
		if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES)) {
			if (tramiteTransmision.getTramiteTraficoBean().getEstado() != null) {
				if (tramiteTransmision.getTramiteTraficoBean().getEstado()
						.equals(EstadoTramiteTrafico.Finalizado_Telematicamente)
						|| tramiteTransmision.getTramiteTraficoBean().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_PDF)
						|| tramiteTransmision.getTramiteTraficoBean().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso)
						|| tramiteTransmision.getTramiteTraficoBean().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado)) {
					return true;
				}
			}
		}
		return false;
	}

	// Visibilidad de la pestaña facturacion trámites de matriculación MATW:
	public boolean esFacturableLaTasaMatriculacionMATW(
			TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		return esFacturableLaTasaMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW,
				tramiteTraficoMatriculacionBean);
	}

	// Visibilidad de la pestaña facturacion tramites de matriculacion:
	/**
	 * @deprecated Mate se encuenta ya obsoleto
	 */
	@Deprecated
	public boolean esFacturableLaTasaMatriculacionMATE(
			TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		return esFacturableLaTasaMatriculacion(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION,
				tramiteTraficoMatriculacionBean);
	}

	private boolean esFacturableLaTasaMatriculacion(String permisoMantenimiento,
			TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		if (utilesColegiado.tienePermiso(permisoMantenimiento)) {
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado() != null) {
				if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado()
						.equals(EstadoTramiteTrafico.Finalizado_Telematicamente)
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_PDF)
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso)
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado)) {
					return true;
				}
			}
		}
		return false;
	}

	// Inicio - 0006872: Bajas definitivas voluntarias de vehículos de más de 15
	// años que guardan como tasa -1
	public boolean esJefaturaMadrid(String jefatura) {
		if ("M".equalsIgnoreCase(jefatura) || "M1".equalsIgnoreCase(jefatura) || "M2".equalsIgnoreCase(jefatura)) {
			return true;
		}
		return false;
	}
	// Fin - 0006872: Bajas definitivas voluntarias de vehículos de más de 15 años
	// que guardan como tasa -1

	public boolean esGenerableJustificante(TramiteTraficoBean tramiteTrafico) throws SQLException {
		if (tramiteTrafico == null) {
			return true;
		}
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (tienePermisoJustificantes(tramiteTrafico)) {
				if (tramiteTrafico.getNumExpediente() != null && !"".equals(tramiteTrafico.getNumExpediente())) {
					try {
						return !getModeloJustificanteProfesional()
								.hayJustificanteIniciado(tramiteTrafico.getNumExpediente());
						// return
						// !ModeloJustificanteProfesional.enCola(tramiteTrafico.getNumExpediente(),ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF);
					} catch (OegamExcepcion e) {
						log.error(e);
						return false;
					}
				} else {
					return true;
				}
			}

		}
		return false;
	}

	public boolean esEnvioExcelDuplicados(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
				if (tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Validado_PDF)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public String queTipoVehiculo(String tipoVehiculo) {
		String tipo = null;

		if ("R".equals(tipoVehiculo.substring(0, 1)) || "S".equals(tipoVehiculo.substring(0, 1))) {
			tipo = ConstantesPDF.TIPO_MATRICULA_REMOLQUE;
		} else {
			if ("7".equals(tipoVehiculo.substring(0, 1)) && !"A".equals(tipoVehiculo.substring(1, 2))) {
				tipo = ConstantesPDF.TIPO_MATRICULA_ESPECIAL;
			} else {
				if ("9".equals(tipoVehiculo.substring(0, 1))) {
					tipo = ConstantesPDF.TIPO_MATRICULA_CICLOMOTOR;
				} else {
					tipo = ConstantesPDF.TIPO_MATRICULA_ORDINARIA;
				}
			}
		}
		return tipo;
	}

	public String queTipoMatricula(String tipoIndustria) {
		String tipo = ConstantesPDF.TIPO_MATRICULA_ORDINARIA;

		if (tipoIndustria.substring(0, 2).equals("03")) {
			tipo = ConstantesPDF.TIPO_MATRICULA_CICLOMOTOR;
		} else {
			if (tipoIndustria.substring(0, 1).equals("4")) {
				tipo = ConstantesPDF.TIPO_MATRICULA_REMOLQUE;
			} else {
				if (tipoIndustria.substring(0, 1).equals("5") || tipoIndustria.substring(0, 1).equals("6")
						|| tipoIndustria.substring(0, 1).equals("7") || tipoIndustria.substring(0, 1).equals("8")) {
					tipo = ConstantesPDF.TIPO_MATRICULA_ESPECIAL;
				}
			}
		}
		return tipo;
	}

	// Metodo que habrá que ir aumentando según se vaya agregando tipos de impresos
	public Boolean estadoImprimible(EstadoTramiteTrafico estado, String impreso) {
		if (impreso.equals(ConstantesPDF.BORRADOR_PDF_417) || impreso.equals(ConstantesPDF.MODELO_430)
				|| impreso.equals(ConstantesPDF.MODELO_AEAT) || impreso.equals(ConstantesPDF.MANDATO_GENERICO)
				|| impreso.equals(ConstantesPDF.MANDATO_ESPECIFICO) || impreso.equals(ConstantesPDF.CARTA_PAGO_IVTM)
				|| impreso.equals(ConstantesPDF.PEGATINAS_ETIQUETA_MATRICULA)
				|| impreso.equals(ConstantesPDF.LISTADO_BASTIDORES)) {
			return true;
		}

		if (impreso.equals(ConstantesPDF.PDF_417)
				&& (estado.getValorEnum().equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())
						|| estado.getValorEnum().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
						|| estado.getValorEnum().equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()))) {
			return true;
		}

		if (impreso.equals(ConstantesPDF.PDF_PRESENTACION_TELEMATICA)
				&& (estado.getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())
						|| estado.getValorEnum()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))) {
			return true;
		}

		if (impreso.equals(ConstantesPDF.PERMISO_TEMPORAL_CIRCULACION)
				&& (estado.getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())
						|| estado.getValorEnum()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))) {
			return true;
		}

		if (impreso.equals(ConstantesPDF.FICHA_TECNICA)
				&& (estado.getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())
						|| estado.getValorEnum()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()))) {
			return true;
		}

		if (impreso.equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())
				&& (estado.getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())
						|| estado.getValorEnum()
								.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())
						|| estado.getValorEnum().equals(EstadoTramiteTrafico.Informe_Telematico.getValorEnum()))) {
			return true;
		}
		return false;
	}

	/**
	 * Método que controlando el tipo y el estado de un trámite decidirá si es
	 * validable o no, en caso negativo devolviendo un mensaje que indique porque no
	 * es validable.
	 * 
	 * @return
	 */
	public ResultBean tipoEstadoValidable(EstadoTramiteTrafico estado, TipoTramiteTrafico tipoTramite) {
		ResultBean resultado = new ResultBean();

		if (EstadoTramiteTrafico.Finalizado_Telematicamente.getNombreEnum().equals(estado.getNombreEnum())
				|| EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum()
						.equals(estado.getNombreEnum())
				|| EstadoTramiteTrafico.Finalizado_Excel.getNombreEnum().equals(estado.getNombreEnum())
				|| EstadoTramiteTrafico.Finalizado_Excel_Impreso.getNombreEnum().equals(estado.getNombreEnum())
				|| EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum().equals(estado.getNombreEnum())) {
			resultado.setError(true);
			resultado.setMensaje("La validación no tiene efecto para trámites ya finalizados");
			return resultado;
		}

		// INICIO - incicedencia 6474 - Validar un tramite mientras esta en estado
		// "Pendiente Respuesta ..."
		// Si esta en estado Pendiente respuesta Jefatura, endiente respuesta de la AEAT
		// o IVTM no permitimos validar
		if (EstadoTramiteTrafico.Pendiente_Respuesta_AEAT.getNombreEnum().equals(estado.getNombreEnum())
				|| EstadoTramiteTrafico.Pendiente_Respuesta_IVTM.getNombreEnum().equals(estado.getNombreEnum())
				|| EstadoTramiteTrafico.Pendiente_Respuesta_Jefatura.getNombreEnum().equals(estado.getNombreEnum())) {
			resultado.setError(true);
			resultado.setMensaje("No se puede validar un trámite de " + tipoTramite.getNombreEnum().toLowerCase()
					+ " en ese estado.");
			return resultado;
		}
		// FIN - incicedencia 6474 - Validar un tramite mientras esta en estado
		// "Pendiente Respuesta ..."

		if (TipoTramiteTrafico.Transmision.getNombreEnum().equals(tipoTramite.getNombreEnum())
				&& !(EstadoTramiteTrafico.Iniciado.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Validado_PDF.getNombreEnum().equals(estado.getNombreEnum()))) {
			resultado.setError(true);
			resultado.setMensaje("No se puede validar un trámite de transmisión en ese estado.");
			return resultado;
		}

		if (TipoTramiteTrafico.TransmisionElectronica.getNombreEnum().equals(tipoTramite.getNombreEnum())
				&& !(EstadoTramiteTrafico.No_Tramitable.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Tramitable_Incidencias.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Tramitable.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Validado_Telematicamente.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Validado_PDF.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Tramitable_Jefatura.getNombreEnum().equals(estado.getNombreEnum())
						|| EstadoTramiteTrafico.Finalizado_Con_Error.getNombreEnum().equals(estado.getNombreEnum()))) {
			resultado.setError(true);
			resultado.setMensaje("No se puede validar un trámite de transmisión electrónica en ese estado.");
			return resultado;
		}

		if (TipoTramiteTrafico.Matriculacion.getNombreEnum().equals(tipoTramite.getNombreEnum())
				&& EstadoTramiteTrafico.Pendiente_Consulta_EITV.getNombreEnum().equals(estado.getNombreEnum())) {
			resultado.setError(true);
			resultado.setMensaje("No se puede validar un trámite de matriculación en estado "
					+ EstadoTramiteTrafico.Error_Consulta_EITV.getNombreEnum() + ".");
			return resultado;
		}
		resultado.setError(false);
		return resultado;
	}

	public boolean tienePermisoJustificantes(TramiteTraficoBean tramiteTraficoBean) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			if (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES))
				return true;
			else
				return false;
		}
		return false;
	}

	public boolean isSeridorInformesTecnicosAVPO() {
		return servidorInformesTecnicos() != null && servidorInformesTecnicos().contains("AVPO");
	}

	public boolean isSeridorInformesTecnicosINTEVE() {
		return servidorInformesTecnicos() != null && servidorInformesTecnicos().contains("INTEVE");
	}

	public boolean isSeridorInformesTecnicosATEM() {
		return servidorInformesTecnicos() != null && servidorInformesTecnicos().contains("ATEM");
	}

	public String servidorInformesTecnicos() {
		return gestorPropiedades.valorPropertie(ConstantesTrafico.REF_PROP_NOMBRE_SERVIDOR_INFORME);
	}

	public String sesionUsuarioPruebasInteve() {
		return "SI";
	}

	public String sesionUsuarioPruebasServidorAtem() {
		return sesionUsuarioPruebasServidor(ConstantesInteve.REF_PROP_ID_USUARIO_PRUEBAS_ATEM);
	}

	public String sesionUsuarioPruebasInteve3() {
		return sesionUsuarioPruebasServidor(ConstantesInteve.REF_PROP_ID_USUARIO_PRUEBAS_INTEVE3);
	}

	private String sesionUsuarioPruebasServidor(String propiedad) {
		String lecturaPropiedad = gestorPropiedades.valorPropertie(propiedad);
		// Ignora la lectura de la propiedad si no se ha establecido o si está vacía
		if (lecturaPropiedad == null || lecturaPropiedad.isEmpty()) {
			return "NO";
		}
		// Quita los espacios delante y detrás:
		lecturaPropiedad = lecturaPropiedad.trim();
		if (lecturaPropiedad.indexOf(",") == -1) {
			// Sin separador. Lo toma por un solo id
			try {
				if (utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() == new BigDecimal(lecturaPropiedad)
						.intValue()) {
					return "SI";
				} else {
					return "NO";
				}
			} catch (NumberFormatException ex) {
				// Id de usuario no válido para comparar
				return "NO";
			}
		}
		// Trocea la lectura de la propiedad en array de cadenas:
		String[] idsUsuariosPruebas = lecturaPropiedad.split(",");
		// Recorre los ids buscando coincidencia con el usuario actual de la sesión:
		for (String idUsuarioPruebas : idsUsuariosPruebas) {
			try {
				// Quita los espacios entre valores:
				idUsuarioPruebas = idUsuarioPruebas.trim();
				if (utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() == new BigDecimal(idUsuarioPruebas)
						.intValue()) {
					return "SI";
				}
			} catch (NumberFormatException ex) {
				// Id de usuario no válido para comparar
				continue;
			}
		}
		// Sin coincidencia:
		return "NO";
	}

	public static void main(String[] args) {
		new BigDecimal("zeta");
	}

	public List<ProvinciaDto> getProvinciasCem() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return provinciasCem;
	}

	public List<ComboGenerico> getComboNumeroTitulares() {
		if (comboNumeroTitulares == null) {
			comboNumeroTitulares = new ArrayList<ComboGenerico>();
			for (int i = 1; i < 10; i++) {
				ComboGenerico cg = new ComboGenerico();
				cg.setIndice(i + "");
				cg.setDescripcion(i + "");
				comboNumeroTitulares.add(cg);
			}
			ComboGenerico cg = new ComboGenerico();
			cg.setIndice("99");
			cg.setDescripcion("10 o más");
			comboNumeroTitulares.add(cg);
		}
		return comboNumeroTitulares;
	}

	public HashMap<String, String> getComboFrontal() {
		// Recoger la properties que tiene el numero de frontales e iterar en un bucle
		// que recoje del properties todos los frontales.
		int contadorFrontales = 1;
		HashMap<String, String> mapa = new HashMap<String, String>();
		HashMap<String, String> treeMap = new HashMap<String, String>();
		int numeroFrontales = Integer.parseInt(gestorPropiedades.valorPropertie("numero.frontales"));
		while (contadorFrontales <= numeroFrontales) {
			String nombreFrontal = gestorPropiedades.valorPropertie("frontal." + contadorFrontales);
			mapa.put(nombreFrontal, "frontal " + nombreFrontal);
			contadorFrontales++;
		}
		treeMap = new HashMap<String, String>(mapa);
		return treeMap;
	}

	public List<TipoTramiteRenovacion> getTipoTramiteRenovacion() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return tipoTramiteRenovacion;
	}

	public boolean esUsuarioColegio(Long idUsuario) {
		boolean resultado = Boolean.FALSE;
		UsuarioVO usuario = servicioUsuario.getUsuario(new BigDecimal(idUsuario));
		if (usuario != null && usuario.getNumColegiado().equals(getColegiadoAdmin())
				&& usuario.getEstadoUsuario().toString().equals(EstadoUsuario.Habilitado.getValorEnum())) {
			resultado = Boolean.TRUE;
		}
		return resultado;
	}

	public String getColegiadoAdmin() {
		return gestorPropiedades.valorPropertie("trafico.numcolegiado.administrador");
	}

	/**
	 * Se encarga de recargar los combos que se inicializan al arrancar la
	 * aplicación. Si no es capaz de recargar algún combo, lo deja como estaba y
	 * continúa con el siguiente. Hay que tener en cuenta que habría que ejecutar
	 * este método por cada instancia (frontal) donde se encuentre desplegada la
	 * aplicación.
	 * 
	 * @return ResultBean, si algún combo no ha podido ser recargado devuelve error
	 *         y en la lista de mensajes, los combos no refrescados.
	 */
	public synchronized ResultBean refrescarCombos() {
		ResultBean resultBean = new ResultBean();

		if (!_iniciados) {
			iniciarCombos();
			resultBean.setMensaje("Los combos aún no habían sido cargados, se han inicializado normalmente.");
		} else {
			StringBuffer sb = new StringBuffer();
			// Combos de provincias
			List<ProvinciaDto> provinciasAux = null;
			try {
				provinciasAux = servicioProvincia.getListaProvincias();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerProvincias()");
			}
			if (provinciasAux != null) {
				sb.append(", provincias");
				provincias = provinciasAux;
				provinciasPresentador = provinciasAux;
				provinciasTitular = provinciasAux;
				provinciasArrendatario = provinciasAux;
				provinciasAdquiriente = provinciasAux;
				provinciasTransmitente = provinciasAux;
				provinciasPoseedor = provinciasAux;
				provinciasRepresentante = provinciasAux;
				provinciasVehiculo = provinciasAux;
				provinciasCet = provinciasAux;
				provinciasCem = provinciasAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar las provincias");
			}

			// Combo ClasificacionesCriteriosConstruccionVehiculo
			List<ClasificacionCriterioConstruccionBean> clasificacionesCriteriosConstruccionVehiculoAux = null;
			try {
				clasificacionesCriteriosConstruccionVehiculoAux = getModeloTrafico()
						.obtenerClasificacionesCriteriosConstruccionVehiculo();
			} catch (Throwable e) {
				log.error(
						"Error al obtener la conexión en  getModeloTrafico().obtenerClasificacionesCriteriosConstruccionVehiculo()");
			}
			if (clasificacionesCriteriosConstruccionVehiculoAux != null) {
				sb.append(", criterios de construcción de vehículos");
				clasificacionesCriteriosConstruccionVehiculo = clasificacionesCriteriosConstruccionVehiculoAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes()
						.add("No se han podido recargar las clasificaciones de críterios de construcción de vehículos");
			}

			// Combo clasificacionesCriteriosUtilizacionVehiculo
			List<ClasificacionCriterioUtilizacionBean> clasificacionesCriteriosUtilizacionVehiculoAux = null;
			try {
				clasificacionesCriteriosUtilizacionVehiculoAux = getModeloTrafico()
						.obtenerClasificacionesCriterioUtilizacion();
			} catch (Throwable e) {
				log.error(
						"Error al obtener la conexión en  getModeloTrafico().obtenerClasificacionesCriterioUtilizacion()");
			}
			if (clasificacionesCriteriosUtilizacionVehiculoAux != null) {
				sb.append(", criterios de utilización de vehículos");

				clasificacionesCriteriosUtilizacionVehiculo = clasificacionesCriteriosUtilizacionVehiculoAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes()
						.add("No se han podido recargar las clasificaciones de críterios de utilización de vehículos");
			}

			// Combo OficinasLiquidadoras
			List<OficinaLiquidadoraDao> oficinaLiquidadoraAux = null;
			try {
				// En las oficinaLiquidadora me interesa la oficina_liquidadora y el
				// nombre_oficina_liq
				oficinaLiquidadoraAux = getModeloTrafico().obtenerOficinasLiquidadoras();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerOficinasLiquidadoras()");
			}
			if (oficinaLiquidadoraAux != null) {
				sb.append(", oficinas liquidadoras");

				oficinaLiquidadora = oficinaLiquidadoraAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar las oficinas liquidadoras");
			}
			// Combo fundamentos exención
			List<FundamentoExencion> fundamentosExencionAux = null;
			try {
				fundamentosExencionAux = getModeloTrafico().obtenerFundamentosExencion();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerFundamentosExencion()");
			}
			if (fundamentosExencionAux != null) {
				sb.append(", fundamentos de exención");
				fundamentosExencion = fundamentosExencionAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar los fundamentos de exención");
			}
			// Combo fundamentos no sujeto
			List<FundamentoExencion> fundamentosNoSujetoAux = null;
			try {
				fundamentosNoSujetoAux = getModeloTrafico().obtenerFundamentosNoSujeto();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerFundamentosNoSujeto()");
			}
			if (fundamentosNoSujetoAux != null) {
				sb.append(", fundamentos no sujeto");
				fundamentosNoSujeto = fundamentosNoSujetoAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar los fundamentos no sujeto");
			}
			// Combo motivos ITV
			List<MotivoBean> motivosITVAux = null;
			try {
				motivosITVAux = getModeloTrafico().obtenerMotivosITV();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerMotivosITV()");
			}
			if (motivosITVAux != null) {
				sb.append(", motivos ITV");
				motivosITV = motivosITVAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar los motivos ITV");
			}
			// Combo estaciones ITV
			try {
				estacionesITV = servicioEstacionITV.getEstacionesItv(null);
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerEstacionesITV()");
			}
			if (estacionesITV != null && !estacionesITV.isEmpty()) {
				sb.append(", estaciones ITV");
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar las estaciones ITV");
			}
			// Combo Contratos habilitados
			List<DatoMaestroBean> comboContratosAux = null;
			try {
				comboContratosAux = servicioContrato.getComboContratosHabilitados();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en servicioContrato.getComboContratosHabilitados()");
			}
			if (comboContratosAux != null) {
				sb.append(", combo de contratos habilitados");
				comboContratos = comboContratosAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar el combo de contratos habilitados");
			}
			// Combo códigos IAE
			List<IAEBean> codigosIAEAux = null;
			try {
				codigosIAEAux = servicioCodigoIae.obtenerCodigosIAE();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en  ModeloPersona.obtenerCodigosIAE()");
			}
			if (codigosIAEAux != null) {
				sb.append(", códigos IAE");
				codigosIAE = codigosIAEAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar los códigos IAE");
			}
			// Combo tipo creaciones
			List<TipoCreacion> tipoCreacionAux = null;
			try {
				tipoCreacionAux = getModeloTrafico().obtenerTipoCreaciones();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en getModeloTrafico().obtenerTipoCreaciones()");
			}
			if (tipoCreacionAux != null) {
				sb.append(", tipos de creación");
				tipoCreacion = tipoCreacionAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar los tipos de creación");
			}
			// Combo tipo trámite renovación
			List<TipoTramiteRenovacion> tipoTramiteRenovacionAux = null;
			try {
				tipoTramiteRenovacionAux = new ModeloReconocimiento().listTipoTramiteRenovacion();
			} catch (Throwable e) {
				log.error("Error al obtener la conexión en ModeloReconocimiento().listTipoTramiteRenovacion()");
			}
			if (tipoTramiteRenovacionAux != null) {
				sb.append(", tipos de trámites de renovación");
				tipoTramiteRenovacion = tipoTramiteRenovacionAux;
			} else {
				resultBean.setError(true);
				resultBean.getListaMensajes().add("No se han podido recargar los tipos de trámites de renovación");
			}
		}
		return resultBean;
	}

	public Boolean esValidoIntroMatriculaManual(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		if (tramiteTraficoMatriculacionBean != null && tramiteTraficoMatriculacionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null
				&& (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatricula() == null
						|| tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatricula()
								.isEmpty())
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado() != null
				&& EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()
						.equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public TipoDocumentoImpresionesMasivas[] getTipoDocumentoImpresionesMasivas() {
		return TipoDocumentoImpresionesMasivas.values();
	}

	public TipoDocumentoImpresiones[] getTipoDocumentoImpresiones() {
		TipoDocumentoImpresiones[] tipos = new TipoDocumentoImpresiones[TipoDocumentoImpresiones.values().length - 11];
		int i = 0;
		for (TipoDocumentoImpresiones tipoDoc : TipoDocumentoImpresiones.values()) {
			if (!TipoDocumentoImpresiones.DocumentoBase.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiado.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.DeclaracionResponsabilidadColegiadoConducir.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.OtroDocumentoDPC.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.DeclaracionResponsabilidadTitularConducir.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.SolicitudDuplicadoPermisoConducir.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.CarnetConducir.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.CarnetIdentidad.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.PermisoInternacional.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.OtroDocumentoPI.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.CarnetIdentidad.equals(tipoDoc)
					&& !TipoDocumentoImpresiones.SolicitudPermisoInter.equals(tipoDoc)) {
				tipos[i] = tipoDoc;
				i++;
			}
		}
		return tipos;
	}

	public MotivoConsultaInteve[] getMotivoInteve() {
		return MotivoConsultaInteve.values();
	}

	public List<TipoInformeInteve> getTipoInformeInteve() {
		List<TipoInformeInteve> lista = new ArrayList<>();
		lista.add(TipoInformeInteve.CARGAS);
		lista.add(TipoInformeInteve.COMPLETO);
		return lista;
	}

	public EstadoImpresion[] getEstadoImpresion() {
		return EstadoImpresion.values();
	}

	public EstadosImprimir[] getEstadoImprimir() {
		EstadosImprimir[] estados = new EstadosImprimir[EstadosImprimir.values().length - 1];
		int i = 0;
		for (EstadosImprimir estadoDoc : EstadosImprimir.values()) {
			if (!EstadosImprimir.Eliminado.equals(estadoDoc)) {
				estados[i] = estadoDoc;
				i++;
			}
		}
		return estados;
	}

	public List<CarroceriaBean> getListaCarrocerias() {
		return listaCarrocerias;
	}

	public void setListaCarrocerias(List<CarroceriaBean> listaCarrocerias) {
		this.listaCarrocerias = listaCarrocerias;
	}

	public List<ComboMaquinas> getComboMaquinas() {
		ComboMaquinas comboMaquinas;
		String propiedadMaquinas = gestorPropiedades.valorPropertie(COMBO_MAQUINAS);
		List<ComboMaquinas> listaMaquinas = new ArrayList<ComboMaquinas>();
		String[] maquinas = propiedadMaquinas.split(",");

		for (String maquina : maquinas) {
			comboMaquinas = new ComboMaquinas();
			if (maquina.substring(maquina.lastIndexOf(".") + 1).equals("46")) {
				comboMaquinas.setIp(maquina);
				comboMaquinas.setMaquina("Procesos");
			} else {
				comboMaquinas.setIp(maquina);
				comboMaquinas.setMaquina("Frontal " + maquina.substring(maquina.lastIndexOf(".") + 1));
			}
			listaMaquinas.add(comboMaquinas);
		}
		return listaMaquinas;
	}

	public TipoLogFrontalEnum[] getComboTipoLogFrontal() {
		return TipoLogFrontalEnum.values();
	}

	public TipoLogProcesoEnum[] getComboTipoLogProceso() {
		return TipoLogProcesoEnum.values();
	}

	public ConceptoCreditoFacturado[] getComboConceptoCreditoFacturado() {
		return ConceptoCreditoFacturado.values();
	}

	public TipoBastidor[] getComboTipoControlDatoSensible() {
		return TipoBastidor.values();
	}

	public Map<String, String> getTipoCreditoTramite() {
		if (!_iniciados) {
			iniciarCombos();
		}
		return mapaTipoCreditoTramite;
	}

	public TipoServicioTGate[] getTipoServicioTGate() {
		return TipoServicioTGate.values();
	}

	public OrdenDocBaseEnum[] getComboOrdenDocBase() {
		return OrdenDocBaseEnum.values();
	}

	public EstadoPresentacionJPT[] getEstadoPresentacionJPT() {
		EstadoPresentacionJPT[] estados = new EstadoPresentacionJPT[2];
		estados[0] = EstadoPresentacionJPT.Presentado;
		estados[1] = EstadoPresentacionJPT.No_Presentado;
		return estados;
	}

	public EstadoCombosConsTram[] getEstadosCombosConsTram() {
		return EstadoCombosConsTram.values();
	}

	public Boolean esConsultaTarjetaEitvAntigua() {
		String habilitarConsultaEitvNuevo = gestorPropiedades.valorPropertie("nuevo.mateEitv");
		boolean esAntigua = true;
		if (habilitarConsultaEitvNuevo != null && habilitarConsultaEitvNuevo.equals("SI")) {
			esAntigua = false;
		}
		return esAntigua;
	}

	// Mantis 25415
	public String transformarImporte(BigDecimal importe) {
		String salida = importe.toString();
		return salida.replace(".", ",");

	}

	public Boolean nuevoInteve() {
		return "SI".equals(gestorPropiedades.valorPropertie("dgt.aplicacion.inteve.nuevo.habilitar"));
	}

	public Boolean gestionarUsuariosContrato() {
		return "SI".equals(gestorPropiedades.valorPropertie("gestionar.usuarios.contrato"));
	}

	public Boolean esDescargableXmlApp(SolicitudDatosBean solicitud) {
		if (solicitud != null && solicitud.getTramiteTrafico() != null && EstadoTramiteTrafico.Pendiente_Respuesta_APP
				.getValorEnum().equals(solicitud.getTramiteTrafico().getEstado().getValorEnum())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esJustificantesNuevos(TramiteTraficoBean tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial() &&
				tramiteTrafico.getNumExpediente() != null && !"".equals(tramiteTrafico.getNumExpediente())) {
			String justificantesProfNuevos = gestorPropiedades.valorPropertie("justificantes.profesional.nuevos");
			if ("SI".equalsIgnoreCase(justificantesProfNuevos)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	public String getUrlOegam3() {
		String urlOegam3=gestorPropiedades.valorPropertie("url.oegam3");
		return urlOegam3;
	}
	/* ************************************************** */
	/* MODELOS ****************************************** */
	/* ************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
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

	public Boolean esColegiadoImportExcel() {
		String habilitarEnvioExcel = gestorPropiedades.valorPropertie("habilitar.envio.excel");
		if (utilesColegiado.tienePermisoAdmin()) {
			return Boolean.TRUE;
		} else if (habilitarEnvioExcel != null && !habilitarEnvioExcel.isEmpty()) {
			String[] usuariosHabilitados = habilitarEnvioExcel.split(",");
			String usuarioSesion = utilesColegiado.getIdUsuarioSessionBigDecimal().toString();
			for (String usuarioHabilitado : usuariosHabilitados) {
				if (usuarioSesion.equals(usuarioHabilitado)) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	public List<TipoTramiteDto> obtenerTipoTramite() {// NO_UCD (use default)
		try {
			return servicioTipoTramite.getTipoTramitePorAplicacion(UtilesColegiado.APLICACION_OEGAM_TRAF);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}
	public TipoDatoBorrar[] getDatosBorrados() {
		return TipoDatoBorrar.values();
	}
	
	public TipoDatoActualizar[] getDatosActualizados() {
		return TipoDatoActualizar.values();
	}
	
	public TipoInterviniente[] getTipoIntervinientePueblo() {
		TipoInterviniente[] tipo = new TipoInterviniente[3];
		tipo[0] = TipoInterviniente.Adquiriente;
		tipo[1] = TipoInterviniente.TransmitenteTrafico;
		tipo[2] = TipoInterviniente.Titular;
		return tipo;
	}
	
	public boolean esAcreditacionPagoIvtm(TramiteTraficoTransmisionBean tramiteTraficoTranBean) {
		boolean esOk = false;
		if (tramiteTraficoTranBean != null && "SI".equalsIgnoreCase(tramiteTraficoTranBean.getAcreditacionPago())) {
				esOk = true;
			}
		return esOk;
	}

	public boolean estaIvtmActivo() {
		boolean activo = false;
		String habilitarIVTM = gestorPropiedades.valorPropertie("nuevo.ivtm.ctit");
		if ("SI".equalsIgnoreCase(habilitarIVTM)) {
			activo = true;
		}
		return activo;
	}
	
	public JefaturasJPTNuevoEnum[] getJefaturasJPTNuevoEnum() {
		return JefaturasJPTNuevoEnum.values();
	}
	
	public boolean esPdfIvtm(BigDecimal numExp) {
		TramiteTrafTranDto trafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(numExp, Boolean.FALSE);
		if (EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equalsIgnoreCase(trafTranDto.getEstado())) {
			if("SI".equalsIgnoreCase(trafTranDto.getAcreditacionPago())
					&& utiles.esUsuarioIvtm(trafTranDto.getNumColegiado())) {
				return true;
			}
			if("HERENCIA".equalsIgnoreCase(trafTranDto.getAcreditaHerenciaDonacion()) || "DONACION".equalsIgnoreCase(trafTranDto.getAcreditaHerenciaDonacion())) {
				return true;
			}
			
		}
		return false;
	}

}
