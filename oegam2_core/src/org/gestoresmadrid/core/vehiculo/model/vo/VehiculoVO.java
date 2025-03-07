
package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;

/**
 * The persistent class for the VEHICULO database table.
 */
@Entity
@Table(name = "VEHICULO")
public class VehiculoVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 6601784582032318698L;

	@Id
	@SequenceGenerator(name = "vehiculo_secuencia", sequenceName = "ID_VEHICULO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "vehiculo_secuencia")
	@Column(name = "ID_VEHICULO")
	private Long idVehiculo;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

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

	@Column(name = "CODIGO_MARCA")
	private String codigoMarca;

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

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecdesde;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INSPECCION")
	private Date fechaInspeccion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ITV")
	private Date fechaItv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_MATRICULACION")
	private Date fechaMatriculacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRIM_MATRI")
	private Date fechaPrimMatri;

	@Temporal(TemporalType.TIMESTAMP)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VEHICULO_PREVER", referencedColumnName = "ID_VEHICULO", insertable = false, updatable = false)
	private VehiculoVO vehiculoPrever;

	private String importado;

	@Column(name = "KM_USO")
	private BigDecimal kmUso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LIMITE_MATR_TURIS")
	private Date limiteMatrTuris;

	@Column(name = "MATRI_AYUNTAMIENTO")
	private String matriAyuntamiento;

	private String matricula;

	@Column(name = "MATRICULA_ORIGEN")
	private String matriculaOrigen;

	@Column(name = "MATRICULA_ORIG_EXTR")
	private String matriculaOrigExtr;

	private String modelo;

	private BigDecimal mom;

	@Column(name = "MTMA_ITV")
	private String mtmaItv;

	@Column(name = "N_CILINDROS")
	private BigDecimal nCilindros;

	@Column(name = "N_HOMOLOGACION")
	private String nHomologacion;

	@Column(name = "NIF_INTEGRADOR")
	private String nifIntegrador;

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

	@Column(name = "TIPO_VEHICULO")
	private String tipoVehiculo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO_VEHICULO", referencedColumnName = "TIPO_VEHICULO", insertable = false, updatable = false)
	private TipoVehiculoVO tipoVehiculoVO;

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

	// bi-directional many-to-one association to Direccion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION")
	private DireccionVO direccion;

	// bi-directional many-to-one association to Persona
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NIF_INTEGRADOR", referencedColumnName = "NIF", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private PersonaVO persona;

	@Column(name = "PAIS_FABRICACION")
	private String paisFabricacion;

	@Column(name = "PROCEDENCIA")
	private String procedencia;

	@Column(name = "FICHA_TECNICA_RD750")
	private Boolean fichaTecnicaRd750;

	/** Nuevos campos para vehículos eléctricos y similar */

	@Column(name = "CODIGO_MARCA_BASE")
	private String codigoMarcaBase;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_LECTURA_KM")
	private Date fechaLecturaKm;

	@Column(name = "DOI_RESPONSABLE_KM")
	private String doiResponsableKm;

	@Column(name = "ES_SINIESTRO")
	private Boolean esSiniestro = Boolean.FALSE;

	@Column(name = "VELOCIDAD_MAXIMA")
	private BigDecimal velocidadMaxima;
	
	@Column(name = "LONGITUD")
	private BigDecimal longitud;
	
	@Column(name = "ANCHURA")
	private BigDecimal anchura;
	
	@Column(name = "ALTURA")
	private BigDecimal altura;
	
	@Column(name = "NUM_EJES")
	private BigDecimal numEjes;

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public String getAnioFabrica() {
		return anioFabrica;
	}

	public void setAnioFabrica(String anioFabrica) {
		this.anioFabrica = anioFabrica;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Integer getBastidorMatriculado() {
		return bastidorMatriculado;
	}

	public void setBastidorMatriculado(Integer bastidorMatriculado) {
		this.bastidorMatriculado = bastidorMatriculado;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getCdmarca() {
		return cdmarca;
	}

	public void setCdmarca(String cdmarca) {
		this.cdmarca = cdmarca;
	}

	public String getCdmodveh() {
		return cdmodveh;
	}

	public void setCdmodveh(String cdmodveh) {
		this.cdmodveh = cdmodveh;
	}

	public String getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getClasificacionItv() {
		return clasificacionItv;
	}

	public void setClasificacionItv(String clasificacionItv) {
		this.clasificacionItv = clasificacionItv;
	}

	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
	}

	public String getCodigoEco() {
		return codigoEco;
	}

	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}

	public String getCoditv() {
		return coditv;
	}

	public void setCoditv(String coditv) {
		this.coditv = coditv;
	}

	public String getCoditvOriginal() {
		return coditvOriginal;
	}

	public void setCoditvOriginal(String coditvOriginal) {
		this.coditvOriginal = coditvOriginal;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getCodigoMarcaBase() {
		return codigoMarcaBase;
	}

	public void setCodigoMarcaBase(String codigoMarcaBase) {
		this.codigoMarcaBase = codigoMarcaBase;
	}

	public String getConceptoItv() {
		return conceptoItv;
	}

	public void setConceptoItv(String conceptoItv) {
		this.conceptoItv = conceptoItv;
	}

	public BigDecimal getConsumo() {
		return consumo;
	}

	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}

	public String getDiplomatico() {
		return diplomatico;
	}

	public void setDiplomatico(String diplomatico) {
		this.diplomatico = diplomatico;
	}

	public BigDecimal getDistanciaEjes() {
		return distanciaEjes;
	}

	public void setDistanciaEjes(BigDecimal distanciaEjes) {
		this.distanciaEjes = distanciaEjes;
	}

	public String getEcoInnovacion() {
		return ecoInnovacion;
	}

	public void setEcoInnovacion(String ecoInnovacion) {
		this.ecoInnovacion = ecoInnovacion;
	}

	public String getEstacionItv() {
		return estacionItv;
	}

	public void setEstacionItv(String estacionItv) {
		this.estacionItv = estacionItv;
	}

	public String getExcesoPeso() {
		return excesoPeso;
	}

	public void setExcesoPeso(String excesoPeso) {
		this.excesoPeso = excesoPeso;
	}

	public String getFabricacion() {
		return fabricacion;
	}

	public void setFabricacion(String fabricacion) {
		this.fabricacion = fabricacion;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Date getFecdesde() {
		return fecdesde;
	}

	public void setFecdesde(Date fecdesde) {
		this.fecdesde = fecdesde;
	}

	public Date getFechaInspeccion() {
		return fechaInspeccion;
	}

	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}

	public Date getFechaItv() {
		return fechaItv;
	}

	public void setFechaItv(Date fechaItv) {
		this.fechaItv = fechaItv;
	}

	public Date getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(Date fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public Date getFechaPrimMatri() {
		return fechaPrimMatri;
	}

	public void setFechaPrimMatri(Date fechaPrimMatri) {
		this.fechaPrimMatri = fechaPrimMatri;
	}

	public Date getFechaUltmModif() {
		return fechaUltmModif;
	}

	public void setFechaUltmModif(Date fechaUltmModif) {
		this.fechaUltmModif = fechaUltmModif;
	}

	public BigDecimal getHorasUso() {
		return horasUso;
	}

	public void setHorasUso(BigDecimal horasUso) {
		this.horasUso = horasUso;
	}

	public String getIdCarburante() {
		return idCarburante;
	}

	public void setIdCarburante(String idCarburante) {
		this.idCarburante = idCarburante;
	}

	public String getIdColor() {
		return idColor;
	}

	public void setIdColor(String idColor) {
		this.idColor = idColor;
	}

	public String getIdCriterioConstruccion() {
		return idCriterioConstruccion;
	}

	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}

	public String getIdCriterioUtilizacion() {
		return idCriterioUtilizacion;
	}

	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}

	public String getIdDirectivaCee() {
		return idDirectivaCee;
	}

	public void setIdDirectivaCee(String idDirectivaCee) {
		this.idDirectivaCee = idDirectivaCee;
	}

	public String getIdEpigrafe() {
		return idEpigrafe;
	}

	public void setIdEpigrafe(String idEpigrafe) {
		this.idEpigrafe = idEpigrafe;
	}

	public String getIdLugarAdquisicion() {
		return idLugarAdquisicion;
	}

	public void setIdLugarAdquisicion(String idLugarAdquisicion) {
		this.idLugarAdquisicion = idLugarAdquisicion;
	}

	public String getIdLugarMatriculacion() {
		return idLugarMatriculacion;
	}

	public void setIdLugarMatriculacion(String idLugarMatriculacion) {
		this.idLugarMatriculacion = idLugarMatriculacion;
	}

	public String getIdMotivoItv() {
		return idMotivoItv;
	}

	public void setIdMotivoItv(String idMotivoItv) {
		this.idMotivoItv = idMotivoItv;
	}

	public String getIdProcedencia() {
		return idProcedencia;
	}

	public void setIdProcedencia(String idProcedencia) {
		this.idProcedencia = idProcedencia;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getIdServicioAnterior() {
		return idServicioAnterior;
	}

	public void setIdServicioAnterior(String idServicioAnterior) {
		this.idServicioAnterior = idServicioAnterior;
	}

	public String getIdTipoTarjetaItv() {
		return idTipoTarjetaItv;
	}

	public void setIdTipoTarjetaItv(String idTipoTarjetaItv) {
		this.idTipoTarjetaItv = idTipoTarjetaItv;
	}

	public BigDecimal getIdVehiculoPrever() {
		return idVehiculoPrever;
	}

	public void setIdVehiculoPrever(BigDecimal idVehiculoPrever) {
		this.idVehiculoPrever = idVehiculoPrever;
	}

	public String getImportado() {
		return importado;
	}

	public void setImportado(String importado) {
		this.importado = importado;
	}

	public BigDecimal getKmUso() {
		return kmUso;
	}

	public void setKmUso(BigDecimal kmUso) {
		this.kmUso = kmUso;
	}

	public Date getLimiteMatrTuris() {
		return limiteMatrTuris;
	}

	public void setLimiteMatrTuris(Date limiteMatrTuris) {
		this.limiteMatrTuris = limiteMatrTuris;
	}

	public String getMatriAyuntamiento() {
		return matriAyuntamiento;
	}

	public void setMatriAyuntamiento(String matriAyuntamiento) {
		this.matriAyuntamiento = matriAyuntamiento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMatriculaOrigen() {
		return matriculaOrigen;
	}

	public void setMatriculaOrigen(String matriculaOrigen) {
		this.matriculaOrigen = matriculaOrigen;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getMom() {
		return mom;
	}

	public void setMom(BigDecimal mom) {
		this.mom = mom;
	}

	public String getMtmaItv() {
		return mtmaItv;
	}

	public void setMtmaItv(String mtmaItv) {
		this.mtmaItv = mtmaItv;
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

	public String getNifIntegrador() {
		return nifIntegrador;
	}

	public void setNifIntegrador(String nifIntegrador) {
		this.nifIntegrador = nifIntegrador;
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

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getNivelEmisiones() {
		return nivelEmisiones;
	}

	public void setNivelEmisiones(String nivelEmisiones) {
		this.nivelEmisiones = nivelEmisiones;
	}

	public String getPaisImportacion() {
		return paisImportacion;
	}

	public void setPaisImportacion(String paisImportacion) {
		this.paisImportacion = paisImportacion;
	}

	public String getPesoMma() {
		return pesoMma;
	}

	public void setPesoMma(String pesoMma) {
		this.pesoMma = pesoMma;
	}

	public BigDecimal getPlazas() {
		return plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public BigDecimal getPotenciaFiscal() {
		return potenciaFiscal;
	}

	public void setPotenciaFiscal(BigDecimal potenciaFiscal) {
		this.potenciaFiscal = potenciaFiscal;
	}

	public BigDecimal getPotenciaNeta() {
		return potenciaNeta;
	}

	public void setPotenciaNeta(BigDecimal potenciaNeta) {
		this.potenciaNeta = potenciaNeta;
	}

	public BigDecimal getPotenciaPeso() {
		return potenciaPeso;
	}

	public void setPotenciaPeso(BigDecimal potenciaPeso) {
		this.potenciaPeso = potenciaPeso;
	}

	public BigDecimal getReduccionEco() {
		return reduccionEco;
	}

	public void setReduccionEco(BigDecimal reduccionEco) {
		this.reduccionEco = reduccionEco;
	}

	public String getSubastado() {
		return subastado;
	}

	public void setSubastado(String subastado) {
		this.subastado = subastado;
	}

	public String getTara() {
		return tara;
	}

	public void setTara(String tara) {
		this.tara = tara;
	}

	public String getTipoAlimentacion() {
		return tipoAlimentacion;
	}

	public void setTipoAlimentacion(String tipoAlimentacion) {
		this.tipoAlimentacion = tipoAlimentacion;
	}

	public String getTipoIndustria() {
		return tipoIndustria;
	}

	public void setTipoIndustria(String tipoIndustria) {
		this.tipoIndustria = tipoIndustria;
	}

	public String getTipoItv() {
		return tipoItv;
	}

	public void setTipoItv(String tipoItv) {
		this.tipoItv = tipoItv;
	}

	public String getTipvehi() {
		return tipvehi;
	}

	public void setTipvehi(String tipvehi) {
		this.tipvehi = tipvehi;
	}

	public String getVariante() {
		return variante;
	}

	public void setVariante(String variante) {
		this.variante = variante;
	}

	public String getVehiUsado() {
		return vehiUsado;
	}

	public void setVehiUsado(String vehiUsado) {
		this.vehiUsado = vehiUsado;
	}

	public String getVehiculoAgricola() {
		return vehiculoAgricola;
	}

	public void setVehiculoAgricola(String vehiculoAgricola) {
		this.vehiculoAgricola = vehiculoAgricola;
	}

	public String getVehiculoTransporte() {
		return vehiculoTransporte;
	}

	public void setVehiculoTransporte(String vehiculoTransporte) {
		this.vehiculoTransporte = vehiculoTransporte;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BigDecimal getViaAnterior() {
		return viaAnterior;
	}

	public void setViaAnterior(BigDecimal viaAnterior) {
		this.viaAnterior = viaAnterior;
	}

	public BigDecimal getViaPosterior() {
		return viaPosterior;
	}

	public void setViaPosterior(BigDecimal viaPosterior) {
		this.viaPosterior = viaPosterior;
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

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public PersonaVO getPersona() {
		return persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
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

	public Boolean getFichaTecnicaRd750() {
		return fichaTecnicaRd750;
	}

	public void setFichaTecnicaRd750(Boolean fichaTecnicaRd750) {
		this.fichaTecnicaRd750 = fichaTecnicaRd750;
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

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public TipoVehiculoVO getTipoVehiculoVO() {
		return tipoVehiculoVO;
	}

	public void setTipoVehiculoVO(TipoVehiculoVO tipoVehiculoVO) {
		this.tipoVehiculoVO = tipoVehiculoVO;
	}

	public String getMatriculaOrigExtr() {
		return matriculaOrigExtr;
	}

	public void setMatriculaOrigExtr(String matriculaOrigExtr) {
		this.matriculaOrigExtr = matriculaOrigExtr;
	}

	public VehiculoVO getVehiculoPrever() {
		return vehiculoPrever;
	}

	public void setVehiculoPrever(VehiculoVO vehiculoPrever) {
		this.vehiculoPrever = vehiculoPrever;
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

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}

	public Boolean getEsSiniestro() {
		return esSiniestro;
	}

	public void setEsSiniestro(Boolean esSiniestro) {
		this.esSiniestro = esSiniestro == null ? Boolean.FALSE : esSiniestro;
	}

	public BigDecimal getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public void setVelocidadMaxima(BigDecimal velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public BigDecimal getAnchura() {
		return anchura;
	}

	public void setAnchura(BigDecimal anchura) {
		this.anchura = anchura;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getNumEjes() {
		return numEjes;
	}

	public void setNumEjes(BigDecimal numEjes) {
		this.numEjes = numEjes;
	}

}