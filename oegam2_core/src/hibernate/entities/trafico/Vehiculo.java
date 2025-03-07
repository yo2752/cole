package hibernate.entities.trafico;

import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.Persona;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the VEHICULO database table.
 */
@Entity
public class Vehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VehiculoPK id;

	private Short activo;

	@Column(name = "ANIO_FABRICA")
	private String anioFabrica;

	private String bastidor;

	@Column(name = "BASTIDOR_MATRICULADO")
	private Integer bastidorMatriculado;

	private String caracteristicas;

	private String carroceria;

	private String cdmarca;

	private String cdmodveh;

	private String cilindrada;

	@Column(name = "CLASIFICACION_ITV")
	private String clasificacionItv;

	private String co2;

	@Column(name = "CODIGO_ECO")
	private String codigoEco;

	// bi-directional many-to-one association to MarcaDgt
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_MARCA")
	private MarcaDgt marcaDgt;

	private String coditv;

	@Column(name = "CODITV_ORIGINAL")
	private String coditvOriginal;

	@Column(name = "CONCEPTO_ITV")
	private String conceptoItv;

	private BigDecimal consumo;

	private String diplomatico;

	@Column(name = "DISTANCIA_EJES")
	private BigDecimal distanciaEjes;

	@Column(name = "ECO_INNOVACION")
	private String ecoInnovacion;

	@Column(name = "ESTACION_ITV")
	private String estacionItv;

	@Column(name = "EXCESO_PESO")
	private String excesoPeso;

	private String fabricacion;

	private String fabricante;

	@Temporal(TemporalType.DATE)
	private Date fecdesde;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INSPECCION")
	private Date fechaInspeccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ITV")
	private Date fechaItv;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_MATRICULACION")
	private Date fechaMatriculacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_PRIM_MATRI")
	private Date fechaPrimMatri;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ULTM_MODIF")
	private Date fechaUltmModif;

	@Column(name = "HORAS_USO")
	private BigDecimal horasUso;

	@Column(name = "ID_CARBURANTE")
	private String idCarburante;

	@Column(name = "ID_COLOR")
	private String idColor;

	@Column(name = "ID_CRITERIO_CONSTRUCCION")
	private String idCriterioConstruccion;

	@Column(name = "ID_CRITERIO_UTILIZACION")
	private String idCriterioUtilizacion;

	@Column(name = "ID_DIRECTIVA_CEE")
	private String idDirectivaCee;

	@Column(name = "ID_EPIGRAFE")
	private String idEpigrafe;

	@Column(name = "ID_LUGAR_ADQUISICION")
	private String idLugarAdquisicion;

	@Column(name = "ID_LUGAR_MATRICULACION")
	private String idLugarMatriculacion;

	@Column(name = "ID_MOTIVO_ITV")
	private String idMotivoItv;

	@Column(name = "ID_PROCEDENCIA")
	private String idProcedencia;

	@Column(name = "ID_SERVICIO")
	private String idServicio;

	@Column(name = "ID_SERVICIO_ANTERIOR")
	private String idServicioAnterior;

	@Column(name = "ID_TIPO_TARJETA_ITV")
	private String idTipoTarjetaItv;

	@Column(name = "ID_VEHICULO_PREVER")
	private BigDecimal idVehiculoPrever;

	private String importado;

	@Column(name = "KM_USO")
	private BigDecimal kmUso;

	@Temporal(TemporalType.DATE)
	@Column(name = "LIMITE_MATR_TURIS")
	private Date limiteMatrTuris;

	@Column(name = "MATRI_AYUNTAMIENTO")
	private String matriAyuntamiento;

	private String matricula;

	@Column(name = "MATRICULA_ORIGEN")
	private String matriculaOrigen;

	private String modelo;

	private BigDecimal mom;

	@Column(name = "MTMA_ITV")
	private String mtmaItv;

	@Column(name = "N_CILINDROS")
	private BigDecimal nCilindros;

	@Column(name = "N_HOMOLOGACION")
	private String nHomologacion;

	@Column(name = "N_PLAZAS_PIE")
	private BigDecimal nPlazasPie;

	@Column(name = "N_RUEDAS")
	private BigDecimal nRuedas;

	@Column(name = "N_SERIE")
	private String nSerie;

	private String nive;

	@Column(name = "NIVEL_EMISIONES")
	private String nivelEmisiones;

	@Column(name = "PAIS_IMPORTACION")
	private String paisImportacion;

	@Column(name = "PESO_MMA")
	private String pesoMma;

	private BigDecimal plazas;

	@Column(name = "POTENCIA_FISCAL")
	private BigDecimal potenciaFiscal;

	@Column(name = "POTENCIA_NETA")
	private BigDecimal potenciaNeta;

	@Column(name = "POTENCIA_PESO")
	private BigDecimal potenciaPeso;

	@Column(name = "REDUCCION_ECO")
	private BigDecimal reduccionEco;

	private String subastado;

	private String tara;

	@Column(name = "TIPO_ALIMENTACION")
	private String tipoAlimentacion;

	@Column(name = "TIPO_INDUSTRIA")
	private String tipoIndustria;

	@Column(name = "TIPO_ITV")
	private String tipoItv;

	// bi-directional many-to-one association to TipoVehiculo
	@ManyToOne
	@JoinColumn(name = "TIPO_VEHICULO")
	private TipoVehiculo tipoVehiculoBean;

	private String tipvehi;

	private String variante;

	@Column(name = "VEHI_USADO")
	private String vehiUsado;

	@Column(name = "VEHICULO_AGRICOLA")
	private String vehiculoAgricola;

	@Column(name = "VEHICULO_TRANSPORTE")
	private String vehiculoTransporte;

	private String version;

	@Column(name = "VIA_ANTERIOR")
	private BigDecimal viaAnterior;

	@Column(name = "VIA_POSTERIOR")
	private BigDecimal viaPosterior;

	@Column(name = "CHECK_FECHA_CADUCIDAD_ITV")
	private String checkFechaCaducidadItv;

	@Column(name = "PROVINCIA_PRIMERA_MATRICULA")
	private BigDecimal provinciaPrimeraMatricula;

	// bi-directional many-to-one association to Vehiculo
	@OneToMany(mappedBy = "vehiculo", cascade = { CascadeType.PERSIST })
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private List<TramiteTrafico> tramiteTrafico;

	// bi-directional many-to-one association to Direccion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION")
	private Direccion direccion;

	// bi-directional many-to-one association to Persona
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "NIF_INTEGRADOR", referencedColumnName = "NIF", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private Persona persona;

	@Column(name = "PAIS_FABRICACION")
	private String paisFabricacion;

	@Column(name = "PROCEDENCIA")
	private String procedencia;

	@Column(name = "FICHA_TECNICA_RD750")
	private BigDecimal fichaTecnicaRd750;

	/** Nuevos campos para vehículos eléctricos y similar */

	// bi-directional many-to-one association to MarcaDgt
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_MARCA_BASE")
	private MarcaDgt marcaDgtBase;

	@Column(name = "FABRICANTE_BASE")
	private String fabricanteBase;

	@Column(name = "TIPO_BASE")
	private String tipoBase;

	@Column(name = "VARIANTE_BASE")
	private String varianteBase;

	@Column(name = "VERSION_BASE")
	private String versionBase;

	@Column(name = "N_HOMOLOGACION_BASE")
	private String nHomologacionBase;

	@Column(name = "MOM_BASE")
	private BigDecimal momBase;

	@Column(name = "CATEGORIA_ELECTRICA")
	private String categoriaElectrica;

	@Column(name = "AUTONOMIA_ELECTRICA")
	private BigDecimal autonomiaElectrica;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_LECTURA_KM")
	private Date fechaLecturaKm;

	@Column(name = "DOI_RESPONSABLE_KM")
	private String doiResponsableKm;

	// bi-directional many-to-one association to SolicitudPlaca
	@OneToMany(mappedBy = "vehiculo")
	private List<SolicitudPlaca> solicitudPlacas;

	/** Fin de Nuevos campos para vehículos eléctricos y similar */

	@Column(name = "ES_SINIESTRO")
	private Boolean esSiniestro;

	//DVV
//	@Column(name = "TIENE_CARGA_FINANCIERA")
//	private Boolean tieneCargaFinanciera;

	public Vehiculo() {}

	public VehiculoPK getId() {
		return this.id;
	}

	public void setId(VehiculoPK id) {
		this.id = id;
	}

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public String getAnioFabrica() {
		return this.anioFabrica;
	}

	public void setAnioFabrica(String anioFabrica) {
		this.anioFabrica = anioFabrica;
	}

	public String getBastidor() {
		return this.bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Integer getBastidorMatriculado() {
		return this.bastidorMatriculado;
	}

	public void setBastidorMatriculado(Integer bastidorMatriculado) {
		this.bastidorMatriculado = bastidorMatriculado;
	}

	public String getCaracteristicas() {
		return this.caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getCarroceria() {
		return this.carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getCdmarca() {
		return this.cdmarca;
	}

	public void setCdmarca(String cdmarca) {
		this.cdmarca = cdmarca;
	}

	public String getCdmodveh() {
		return this.cdmodveh;
	}

	public void setCdmodveh(String cdmodveh) {
		this.cdmodveh = cdmodveh;
	}

	public String getCilindrada() {
		return this.cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getClasificacionItv() {
		return this.clasificacionItv;
	}

	public void setClasificacionItv(String clasificacionItv) {
		this.clasificacionItv = clasificacionItv;
	}

	public String getCo2() {
		return this.co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}

	public String getCodigoEco() {
		return this.codigoEco;
	}

	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}

	public MarcaDgt getMarcaDgt() {
		return this.marcaDgt;
	}

	public void setMarcaDgt(MarcaDgt marcaDgt) {
		this.marcaDgt = marcaDgt;
	}

	public String getCoditv() {
		return this.coditv;
	}

	public void setCoditv(String coditv) {
		this.coditv = coditv;
	}

	public String getCoditvOriginal() {
		return this.coditvOriginal;
	}

	public void setCoditvOriginal(String coditvOriginal) {
		this.coditvOriginal = coditvOriginal;
	}

	public String getConceptoItv() {
		return this.conceptoItv;
	}

	public void setConceptoItv(String conceptoItv) {
		this.conceptoItv = conceptoItv;
	}

	public BigDecimal getConsumo() {
		return this.consumo;
	}

	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}

	public String getDiplomatico() {
		return this.diplomatico;
	}

	public void setDiplomatico(String diplomatico) {
		this.diplomatico = diplomatico;
	}

	public BigDecimal getDistanciaEjes() {
		return this.distanciaEjes;
	}

	public void setDistanciaEjes(BigDecimal distanciaEjes) {
		this.distanciaEjes = distanciaEjes;
	}

	public String getEcoInnovacion() {
		return this.ecoInnovacion;
	}

	public void setEcoInnovacion(String ecoInnovacion) {
		this.ecoInnovacion = ecoInnovacion;
	}

	public String getEstacionItv() {
		return this.estacionItv;
	}

	public void setEstacionItv(String estacionItv) {
		this.estacionItv = estacionItv;
	}

	public String getExcesoPeso() {
		return this.excesoPeso;
	}

	public void setExcesoPeso(String excesoPeso) {
		this.excesoPeso = excesoPeso;
	}

	public String getFabricacion() {
		return this.fabricacion;
	}

	public void setFabricacion(String fabricacion) {
		this.fabricacion = fabricacion;
	}

	public String getFabricante() {
		return this.fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Date getFecdesde() {
		return this.fecdesde;
	}

	public void setFecdesde(Date fecdesde) {
		this.fecdesde = fecdesde;
	}

	public Date getFechaInspeccion() {
		return this.fechaInspeccion;
	}

	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}

	public Date getFechaItv() {
		return this.fechaItv;
	}

	public void setFechaItv(Date fechaItv) {
		this.fechaItv = fechaItv;
	}

	public Date getFechaMatriculacion() {
		return this.fechaMatriculacion;
	}

	public void setFechaMatriculacion(Date fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public Date getFechaPrimMatri() {
		return this.fechaPrimMatri;
	}

	public void setFechaPrimMatri(Date fechaPrimMatri) {
		this.fechaPrimMatri = fechaPrimMatri;
	}

	public Date getFechaUltmModif() {
		return this.fechaUltmModif;
	}

	public void setFechaUltmModif(Date fechaUltmModif) {
		this.fechaUltmModif = fechaUltmModif;
	}

	public BigDecimal getHorasUso() {
		return this.horasUso;
	}

	public void setHorasUso(BigDecimal horasUso) {
		this.horasUso = horasUso;
	}

	public String getIdCarburante() {
		return this.idCarburante;
	}

	public void setIdCarburante(String idCarburante) {
		this.idCarburante = idCarburante;
	}

	public String getIdColor() {
		return this.idColor;
	}

	public void setIdColor(String idColor) {
		this.idColor = idColor;
	}

	public String getIdCriterioConstruccion() {
		return this.idCriterioConstruccion;
	}

	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}

	public String getIdCriterioUtilizacion() {
		return this.idCriterioUtilizacion;
	}

	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}

	public String getIdDirectivaCee() {
		return this.idDirectivaCee;
	}

	public void setIdDirectivaCee(String idDirectivaCee) {
		this.idDirectivaCee = idDirectivaCee;
	}

	public String getIdEpigrafe() {
		return this.idEpigrafe;
	}

	public void setIdEpigrafe(String idEpigrafe) {
		this.idEpigrafe = idEpigrafe;
	}

	public String getIdLugarAdquisicion() {
		return this.idLugarAdquisicion;
	}

	public void setIdLugarAdquisicion(String idLugarAdquisicion) {
		this.idLugarAdquisicion = idLugarAdquisicion;
	}

	public String getIdLugarMatriculacion() {
		return this.idLugarMatriculacion;
	}

	public void setIdLugarMatriculacion(String idLugarMatriculacion) {
		this.idLugarMatriculacion = idLugarMatriculacion;
	}

	public String getIdMotivoItv() {
		return this.idMotivoItv;
	}

	public void setIdMotivoItv(String idMotivoItv) {
		this.idMotivoItv = idMotivoItv;
	}

	public String getIdProcedencia() {
		return this.idProcedencia;
	}

	public void setIdProcedencia(String idProcedencia) {
		this.idProcedencia = idProcedencia;
	}

	public String getIdServicio() {
		return this.idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getIdServicioAnterior() {
		return this.idServicioAnterior;
	}

	public void setIdServicioAnterior(String idServicioAnterior) {
		this.idServicioAnterior = idServicioAnterior;
	}

	public String getIdTipoTarjetaItv() {
		return this.idTipoTarjetaItv;
	}

	public void setIdTipoTarjetaItv(String idTipoTarjetaItv) {
		this.idTipoTarjetaItv = idTipoTarjetaItv;
	}

	public BigDecimal getIdVehiculoPrever() {
		return this.idVehiculoPrever;
	}

	public void setIdVehiculoPrever(BigDecimal idVehiculoPrever) {
		this.idVehiculoPrever = idVehiculoPrever;
	}

	public String getImportado() {
		return this.importado;
	}

	public void setImportado(String importado) {
		this.importado = importado;
	}

	public BigDecimal getKmUso() {
		return this.kmUso;
	}

	public void setKmUso(BigDecimal kmUso) {
		this.kmUso = kmUso;
	}

	public Date getLimiteMatrTuris() {
		return this.limiteMatrTuris;
	}

	public void setLimiteMatrTuris(Date limiteMatrTuris) {
		this.limiteMatrTuris = limiteMatrTuris;
	}

	public String getMatriAyuntamiento() {
		return this.matriAyuntamiento;
	}

	public void setMatriAyuntamiento(String matriAyuntamiento) {
		this.matriAyuntamiento = matriAyuntamiento;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMatriculaOrigen() {
		return this.matriculaOrigen;
	}

	public void setMatriculaOrigen(String matriculaOrigen) {
		this.matriculaOrigen = matriculaOrigen;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getMom() {
		return this.mom;
	}

	public void setMom(BigDecimal mom) {
		this.mom = mom;
	}

	public String getMtmaItv() {
		return this.mtmaItv;
	}

	public void setMtmaItv(String mtmaItv) {
		this.mtmaItv = mtmaItv;
	}

	public BigDecimal getNCilindros() {
		return this.nCilindros;
	}

	public void setNCilindros(BigDecimal nCilindros) {
		this.nCilindros = nCilindros;
	}

	public String getNHomologacion() {
		return this.nHomologacion;
	}

	public void setNHomologacion(String nHomologacion) {
		this.nHomologacion = nHomologacion;
	}

	public BigDecimal getNPlazasPie() {
		return this.nPlazasPie;
	}

	public void setNPlazasPie(BigDecimal nPlazasPie) {
		this.nPlazasPie = nPlazasPie;
	}

	public BigDecimal getNRuedas() {
		return this.nRuedas;
	}

	public void setNRuedas(BigDecimal nRuedas) {
		this.nRuedas = nRuedas;
	}

	public String getNSerie() {
		return this.nSerie;
	}

	public void setNSerie(String nSerie) {
		this.nSerie = nSerie;
	}

	public String getNive() {
		return this.nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getNivelEmisiones() {
		return this.nivelEmisiones;
	}

	public void setNivelEmisiones(String nivelEmisiones) {
		this.nivelEmisiones = nivelEmisiones;
	}

	public String getPaisImportacion() {
		return this.paisImportacion;
	}

	public void setPaisImportacion(String paisImportacion) {
		this.paisImportacion = paisImportacion;
	}

	public String getPesoMma() {
		return this.pesoMma;
	}

	public void setPesoMma(String pesoMma) {
		this.pesoMma = pesoMma;
	}

	public BigDecimal getPlazas() {
		return this.plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public BigDecimal getPotenciaFiscal() {
		return this.potenciaFiscal;
	}

	public void setPotenciaFiscal(BigDecimal potenciaFiscal) {
		this.potenciaFiscal = potenciaFiscal;
	}

	public BigDecimal getPotenciaNeta() {
		return this.potenciaNeta;
	}

	public void setPotenciaNeta(BigDecimal potenciaNeta) {
		this.potenciaNeta = potenciaNeta;
	}

	public BigDecimal getPotenciaPeso() {
		return this.potenciaPeso;
	}

	public void setPotenciaPeso(BigDecimal potenciaPeso) {
		this.potenciaPeso = potenciaPeso;
	}

	public BigDecimal getReduccionEco() {
		return this.reduccionEco;
	}

	public void setReduccionEco(BigDecimal reduccionEco) {
		this.reduccionEco = reduccionEco;
	}

	public String getSubastado() {
		return this.subastado;
	}

	public void setSubastado(String subastado) {
		this.subastado = subastado;
	}

	public String getTara() {
		return this.tara;
	}

	public void setTara(String tara) {
		this.tara = tara;
	}

	public String getTipoAlimentacion() {
		return this.tipoAlimentacion;
	}

	public void setTipoAlimentacion(String tipoAlimentacion) {
		this.tipoAlimentacion = tipoAlimentacion;
	}

	public String getTipoIndustria() {
		return this.tipoIndustria;
	}

	public void setTipoIndustria(String tipoIndustria) {
		this.tipoIndustria = tipoIndustria;
	}

	public String getTipoItv() {
		return this.tipoItv;
	}

	public void setTipoItv(String tipoItv) {
		this.tipoItv = tipoItv;
	}

	public TipoVehiculo getTipoVehiculoBean() {
		return this.tipoVehiculoBean;
	}

	public void setTipoVehiculoBean(TipoVehiculo tipoVehiculoBean) {
		this.tipoVehiculoBean = tipoVehiculoBean;
	}

	public String getTipvehi() {
		return this.tipvehi;
	}

	public void setTipvehi(String tipvehi) {
		this.tipvehi = tipvehi;
	}

	public String getVariante() {
		return this.variante;
	}

	public void setVariante(String variante) {
		this.variante = variante;
	}

	public String getVehiUsado() {
		return this.vehiUsado;
	}

	public void setVehiUsado(String vehiUsado) {
		this.vehiUsado = vehiUsado;
	}

	public String getVehiculoAgricola() {
		return this.vehiculoAgricola;
	}

	public void setVehiculoAgricola(String vehiculoAgricola) {
		this.vehiculoAgricola = vehiculoAgricola;
	}

	public String getVehiculoTransporte() {
		return this.vehiculoTransporte;
	}

	public void setVehiculoTransporte(String vehiculoTransporte) {
		this.vehiculoTransporte = vehiculoTransporte;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BigDecimal getViaAnterior() {
		return this.viaAnterior;
	}

	public void setViaAnterior(BigDecimal viaAnterior) {
		this.viaAnterior = viaAnterior;
	}

	public BigDecimal getViaPosterior() {
		return this.viaPosterior;
	}

	public void setViaPosterior(BigDecimal viaPosterior) {
		this.viaPosterior = viaPosterior;
	}

	public List<TramiteTrafico> getTramiteTraficos() {
		return this.tramiteTrafico;
	}

	public void setTramiteTraficos(List<TramiteTrafico> tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Direccion getDireccion() {
		return this.direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getCheckFechaCaducidadItv() {
		return checkFechaCaducidadItv;
	}

	public void setCheckFechaCaducidadItv(String checkFechaCaducidadItv) {
		this.checkFechaCaducidadItv = checkFechaCaducidadItv;
	}

	public BigDecimal getProvinciaPrimeraMatricula() {
		return provinciaPrimeraMatricula;
	}

	public void setProvinciaPrimeraMatricula(BigDecimal provinciaPrimeraMatricula) {
		this.provinciaPrimeraMatricula = provinciaPrimeraMatricula;
	}

	public String getPaisFabricacion() {
		return paisFabricacion;
	}

	public void setPaisFabricacion(String paisFabricacion) {
		this.paisFabricacion = paisFabricacion;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public BigDecimal getFichaTecnicaRd750() {
		return fichaTecnicaRd750;
	}

	public void setFichaTecnicaRd750(BigDecimal fichaTecnicaRd750) {
		this.fichaTecnicaRd750 = fichaTecnicaRd750;
	}

	public BigDecimal getnCilindros() {
		return nCilindros;
	}

	public void setnCilindros(BigDecimal nCilindros) {
		this.nCilindros = nCilindros;
	}

	public String getnHomologacion() {
		return nHomologacion;
	}

	public void setnHomologacion(String nHomologacion) {
		this.nHomologacion = nHomologacion;
	}

	public BigDecimal getnPlazasPie() {
		return nPlazasPie;
	}

	public void setnPlazasPie(BigDecimal nPlazasPie) {
		this.nPlazasPie = nPlazasPie;
	}

	public BigDecimal getnRuedas() {
		return nRuedas;
	}

	public void setnRuedas(BigDecimal nRuedas) {
		this.nRuedas = nRuedas;
	}

	public String getnSerie() {
		return nSerie;
	}

	public void setnSerie(String nSerie) {
		this.nSerie = nSerie;
	}

	public List<TramiteTrafico> getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(List<TramiteTrafico> tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Date getFechaLecturaKm() {
		return fechaLecturaKm;
	}

	public void setFechaLecturaKm(Date fechaLecturaKm) {
		this.fechaLecturaKm = fechaLecturaKm;
	}

	public String getDoiResponsableKm() {
		return doiResponsableKm;
	}

	public void setDoiResponsableKm(String doiResponsableKm) {
		this.doiResponsableKm = doiResponsableKm;
	}

	/** Getters and setters para nuevos campos para vehículos eléctricos y similar */

	public MarcaDgt getMarcaDgtBase() {
		return marcaDgtBase;
	}

	public void setMarcaDgtBase(MarcaDgt marcaDgtBase) {
		this.marcaDgtBase = marcaDgtBase;
	}

	public String getFabricanteBase() {
		return fabricanteBase;
	}

	public void setFabricanteBase(String fabricanteBase) {
		this.fabricanteBase = fabricanteBase;
	}

	public String getTipoBase() {
		return tipoBase;
	}

	public void setTipoBase(String tipoBase) {
		this.tipoBase = tipoBase;
	}

	public String getVarianteBase() {
		return varianteBase;
	}

	public void setVarianteBase(String varianteBase) {
		this.varianteBase = varianteBase;
	}

	public String getVersionBase() {
		return versionBase;
	}

	public void setVersionBase(String versionBase) {
		this.versionBase = versionBase;
	}

	public String getnHomologacionBase() {
		return nHomologacionBase;
	}

	public void setnHomologacionBase(String nHomologacionBase) {
		this.nHomologacionBase = nHomologacionBase;
	}

	public BigDecimal getMomBase() {
		return momBase;
	}

	public void setMomBase(BigDecimal momBase) {
		this.momBase = momBase;
	}

	public String getCategoriaElectrica() {
		return categoriaElectrica;
	}

	public void setCategoriaElectrica(String categoriaElectrica) {
		this.categoriaElectrica = categoriaElectrica;
	}

	public BigDecimal getAutonomiaElectrica() {
		return autonomiaElectrica;
	}

	public void setAutonomiaElectrica(BigDecimal autonomiaElectrica) {
		this.autonomiaElectrica = autonomiaElectrica;
	}

	public List<SolicitudPlaca> getSolicitudPlacas() {
		return solicitudPlacas;
	}

	public void setSolicitudPlacas(List<SolicitudPlaca> solicitudPlacas) {
		this.solicitudPlacas = solicitudPlacas;
	}
	/** Fin: Getters and setters para nuevos campos para vehículos eléctricos y similar */

	public Boolean getEsSiniestro() {
		return esSiniestro;
	}

	public void setEsSiniestro(Boolean esSiniestro) {
		this.esSiniestro = esSiniestro;
	}

//	public Boolean getTieneCargaFinanciera() {
//		return tieneCargaFinanciera;
//	}

//	public void setTieneCargaFinanciera(Boolean tieneCargaFinanciera) {
//		this.tieneCargaFinanciera = tieneCargaFinanciera;
//	}

}