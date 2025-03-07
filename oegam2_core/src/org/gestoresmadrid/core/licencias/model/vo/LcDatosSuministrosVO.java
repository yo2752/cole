package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="LC_DATOS_SUMINISTRO")
public class LcDatosSuministrosVO implements Serializable{

	private static final long serialVersionUID = 8314635017562023902L;

	@Id
	@SequenceGenerator(name = "lc_datos_suministros", sequenceName = "LC_DATOS_SUMINISTROS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_datos_suministros")
	@Column(name = "ID_DATOS_SUMINISTROS")
	private Long idDatosSuministros;
	
	@Column(name = "ACUMULD_CALOR_AGUA_C")
	private String acumuladorCalorAguaC;

	@Column(name = "AGUA_CALIENTE_SANT")
	private String aguaCalienteSanitaria;

	@Column(name = "POTENCIA_ACUMULADOR")
	private BigDecimal potenciaAcumuladorAguaC;

	@Column(name = "POTENCIA_AGUA_CALT")
	private BigDecimal potenciaAguaCaliente;

	@Column(name = "TIPO_COMBTB_AGUA_C")
	private String tipoCombustibleAguaC;
	
	@Column(name = "CAPT_ENERGIA_SOLAR")
	private String captEnergiaSolar;

	@Column(name = "EQUIPO_FOTOVOLTAICO")
	private String equipoFotovoltaico;

	@Column(name = "NUM_EQPS_USO_FTVLTC")
	private BigDecimal numEquiposUsoFotovoltaico;

	@Column(name = "SUPERF_CAPT_EQ_USO_FOTOVOL")
	private BigDecimal superfCaptEqUsoFotovoltaico;

	@Column(name = "EQUIPOS_USOS_TERMICO")
	private String equiposUsosTermico;

	@Column(name = "NUM_EQPS_USO_TERMICO")
	private BigDecimal numEquiposUsoTermico;

	@Column(name = "SUPERF_CAPT_EQ_USO_TERMICO")
	private BigDecimal superfCaptEqUsoTermico;
	
	@Column(name = "CAUDAL_COND_CUBIERTA")
	private BigDecimal caudalCondensadorCubierta;

	@Column(name = "CAUDAL_SALIDA_CUBIERTA")
	private BigDecimal caudalSalidaCubierta;

	@Column(name = "CAUDAL_SALIDA_FACHADA")
	private BigDecimal caudalSalidaFachada;

	@Column(name = "CLIMATIZACION_AC")
	private String climatizacionAc;

	@Column(name = "CONDSC_SALIDA_CHIMENEA")
	private String condensacionSalidaChimenea;

	@Column(name = "CONDSC_SALIDA_FACHADA")
	private String condensacionSalidaFachada;

	@Column(name = "CONDENSADOR_CUBIERTA")
	private String condensadorCubierta;

	@Column(name = "HAY_ENFRD_EVPTVS")
	private String hayEnfriadoresEvaporativos;

	@Column(name = "ENFRDRS_EVAPORATIVOS")
	private BigDecimal enfriadoresEvaporativos;

	@Column(name = "EQUIPO_CENTRL_LOCAL")
	private String equipoCentralizadoLocal;

	@Column(name = "HAY_EQUIPOS_AUTONOMOS")
	private String hayEquiposAutonomos;

	@Column(name = "EQUIPOS_AUTONOMOS")
	private BigDecimal equiposAutonomos;

	@Column(name = "INSTALC_GENRL_EDIFICIO")
	private String instalacionesGeneralEdificio;

	@Column(name = "NUM_TORRES_REFRGRCN")
	private BigDecimal numTorresRefrigeracion;

	@Column(name = "POTNC_CALOR_ENFRDRS")
	private BigDecimal potenciaCalorEnfriadores;

	@Column(name = "POTNC_CALOR_EQ_AUTONMS")
	private BigDecimal potenciaCalorEqAutonomos;

	@Column(name = "POTNC_CALOR_EQ_CENTR")
	private BigDecimal potenciaCalorEqCentr;

	@Column(name = "POTC_CALOR_INST_GEN")
	private BigDecimal potenciaCalorInstGen;

	@Column(name = "POTNC_FRIGO_EQ_CENTR")
	private BigDecimal potenciaFrigoEqCentr;

	@Column(name = "POTNC_FRIGO_ENFRDRS")
	private BigDecimal potenciaFrigoEnfriadores;

	@Column(name = "POTNC_FRIGO_EQ_AUTONMS")
	private BigDecimal potenciaFrigoEqAutonomos;

	@Column(name = "POTNC_FRIGO_EQ_GEN")
	private BigDecimal potenciaFrigoEqGen;

	@Column(name = "POTC_FRIGO_INST_GEN")
	private BigDecimal potenciaFrigoInstGen;

	@Column(name = "TORRE_REFIRGERACION")
	private String torreRefirgeracion;
	
	@Column(name = "EQUIPOS_AUDIOVISUALES")
	private String equiposAudiovisuales;

	@Column(name = "EQUIPOS_INST_RADIOACTIVAS")
	private String equiposInstRadioactivas;

	@Column(name = "EQUIPOS_RAYOS_LASER")
	private String equiposRayosLaser;

	@Column(name = "EQUIPOS_RAYOS_UVA")
	private String equiposRayosUva;

	@Column(name = "HAY_INSTLCNS_RLVNTS")
	private String hayInstalacionesRelevantes;

	@Column(name = "POTNC_EQPS_RAYOS_LASER")
	private BigDecimal potenciaEquiposRayosLaser;

	@Column(name = "POTNC_CTRO_TRNSFMR")
	private BigDecimal potenciaCentroTransformador;

	@Column(name = "POTENCIA_TOTAL")
	private BigDecimal potenciaTotal;
	
	@Column(name = "EQP_CNTRL_LCL_CALFCC")
	private String equipoCentralizadoLocalCalefcc;

	@Column(name = "INSTAL_CALEFCC")
	private String instalacionesCalefaccion;

	@Column(name = "INSTAL_GNRL_EDF_CALEFCC")
	private String instalacionesGeneralEdificioCalefcc;

	@Column(name = "POTENCIA_CALEFCC")
	private BigDecimal potenciaCalefaccion;

	@Column(name = "POTNC_CLR_EQ_CTR_CFCC")
	private BigDecimal potenciaCalorEqCentrCalefcc;

	@Column(name = "POTC_CLR_INST_GEN_CLFCC")
	private BigDecimal potenciaCalorInstGenCalefcc;

	@Column(name = "TP_COMBUSTIBLE_CLFCC")
	private String tipoCombustibleCalefcc;
	
	@Column(name = "EVACUACION_CUBIERTA")
	private String evacuacionCubierta;

	@Column(name = "EVACUACION_FACHADA")
	private String evacuacionFachada;

	@Column(name = "VENTILACION_FORZADA")
	private String ventilacionForzada;
	
	@Column(name = "CAUDAL")
	private BigDecimal caudal;

	public Long getIdDatosSuministros() {
		return idDatosSuministros;
	}

	public void setIdDatosSuministros(Long idDatosSuministros) {
		this.idDatosSuministros = idDatosSuministros;
	}

	public String getAcumuladorCalorAguaC() {
		return acumuladorCalorAguaC;
	}

	public void setAcumuladorCalorAguaC(String acumuladorCalorAguaC) {
		this.acumuladorCalorAguaC = acumuladorCalorAguaC;
	}

	public String getAguaCalienteSanitaria() {
		return aguaCalienteSanitaria;
	}

	public void setAguaCalienteSanitaria(String aguaCalienteSanitaria) {
		this.aguaCalienteSanitaria = aguaCalienteSanitaria;
	}

	public BigDecimal getPotenciaAcumuladorAguaC() {
		return potenciaAcumuladorAguaC;
	}

	public void setPotenciaAcumuladorAguaC(BigDecimal potenciaAcumuladorAguaC) {
		this.potenciaAcumuladorAguaC = potenciaAcumuladorAguaC;
	}

	public BigDecimal getPotenciaAguaCaliente() {
		return potenciaAguaCaliente;
	}

	public void setPotenciaAguaCaliente(BigDecimal potenciaAguaCaliente) {
		this.potenciaAguaCaliente = potenciaAguaCaliente;
	}

	public String getTipoCombustibleAguaC() {
		return tipoCombustibleAguaC;
	}

	public void setTipoCombustibleAguaC(String tipoCombustibleAguaC) {
		this.tipoCombustibleAguaC = tipoCombustibleAguaC;
	}

	public String getCaptEnergiaSolar() {
		return captEnergiaSolar;
	}

	public void setCaptEnergiaSolar(String captEnergiaSolar) {
		this.captEnergiaSolar = captEnergiaSolar;
	}

	public String getEquipoFotovoltaico() {
		return equipoFotovoltaico;
	}

	public void setEquipoFotovoltaico(String equipoFotovoltaico) {
		this.equipoFotovoltaico = equipoFotovoltaico;
	}

	public BigDecimal getNumEquiposUsoFotovoltaico() {
		return numEquiposUsoFotovoltaico;
	}

	public void setNumEquiposUsoFotovoltaico(BigDecimal numEquiposUsoFotovoltaico) {
		this.numEquiposUsoFotovoltaico = numEquiposUsoFotovoltaico;
	}

	public BigDecimal getSuperfCaptEqUsoFotovoltaico() {
		return superfCaptEqUsoFotovoltaico;
	}

	public void setSuperfCaptEqUsoFotovoltaico(BigDecimal superfCaptEqUsoFotovoltaico) {
		this.superfCaptEqUsoFotovoltaico = superfCaptEqUsoFotovoltaico;
	}

	public String getEquiposUsosTermico() {
		return equiposUsosTermico;
	}

	public void setEquiposUsosTermico(String equiposUsosTermico) {
		this.equiposUsosTermico = equiposUsosTermico;
	}

	public BigDecimal getNumEquiposUsoTermico() {
		return numEquiposUsoTermico;
	}

	public void setNumEquiposUsoTermico(BigDecimal numEquiposUsoTermico) {
		this.numEquiposUsoTermico = numEquiposUsoTermico;
	}

	public BigDecimal getSuperfCaptEqUsoTermico() {
		return superfCaptEqUsoTermico;
	}

	public void setSuperfCaptEqUsoTermico(BigDecimal superfCaptEqUsoTermico) {
		this.superfCaptEqUsoTermico = superfCaptEqUsoTermico;
	}

	public BigDecimal getCaudalSalidaCubierta() {
		return caudalSalidaCubierta;
	}

	public void setCaudalSalidaCubierta(BigDecimal caudalSalidaCubierta) {
		this.caudalSalidaCubierta = caudalSalidaCubierta;
	}

	public BigDecimal getCaudalSalidaFachada() {
		return caudalSalidaFachada;
	}

	public void setCaudalSalidaFachada(BigDecimal caudalSalidaFachada) {
		this.caudalSalidaFachada = caudalSalidaFachada;
	}

	public String getCondensacionSalidaChimenea() {
		return condensacionSalidaChimenea;
	}

	public void setCondensacionSalidaChimenea(String condensacionSalidaChimenea) {
		this.condensacionSalidaChimenea = condensacionSalidaChimenea;
	}

	public String getCondensacionSalidaFachada() {
		return condensacionSalidaFachada;
	}

	public void setCondensacionSalidaFachada(String condensacionSalidaFachada) {
		this.condensacionSalidaFachada = condensacionSalidaFachada;
	}

	public String getCondensadorCubierta() {
		return condensadorCubierta;
	}

	public void setCondensadorCubierta(String condensadorCubierta) {
		this.condensadorCubierta = condensadorCubierta;
	}

	public String getHayEnfriadoresEvaporativos() {
		return hayEnfriadoresEvaporativos;
	}

	public void setHayEnfriadoresEvaporativos(String hayEnfriadoresEvaporativos) {
		this.hayEnfriadoresEvaporativos = hayEnfriadoresEvaporativos;
	}

	public BigDecimal getEnfriadoresEvaporativos() {
		return enfriadoresEvaporativos;
	}

	public void setEnfriadoresEvaporativos(BigDecimal enfriadoresEvaporativos) {
		this.enfriadoresEvaporativos = enfriadoresEvaporativos;
	}

	public String getEquipoCentralizadoLocal() {
		return equipoCentralizadoLocal;
	}

	public void setEquipoCentralizadoLocal(String equipoCentralizadoLocal) {
		this.equipoCentralizadoLocal = equipoCentralizadoLocal;
	}

	public String getHayEquiposAutonomos() {
		return hayEquiposAutonomos;
	}

	public void setHayEquiposAutonomos(String hayEquiposAutonomos) {
		this.hayEquiposAutonomos = hayEquiposAutonomos;
	}

	public BigDecimal getEquiposAutonomos() {
		return equiposAutonomos;
	}

	public void setEquiposAutonomos(BigDecimal equiposAutonomos) {
		this.equiposAutonomos = equiposAutonomos;
	}

	public String getInstalacionesGeneralEdificio() {
		return instalacionesGeneralEdificio;
	}

	public void setInstalacionesGeneralEdificio(String instalacionesGeneralEdificio) {
		this.instalacionesGeneralEdificio = instalacionesGeneralEdificio;
	}

	public BigDecimal getNumTorresRefrigeracion() {
		return numTorresRefrigeracion;
	}

	public void setNumTorresRefrigeracion(BigDecimal numTorresRefrigeracion) {
		this.numTorresRefrigeracion = numTorresRefrigeracion;
	}

	public BigDecimal getPotenciaCalorEnfriadores() {
		return potenciaCalorEnfriadores;
	}

	public void setPotenciaCalorEnfriadores(BigDecimal potenciaCalorEnfriadores) {
		this.potenciaCalorEnfriadores = potenciaCalorEnfriadores;
	}

	public BigDecimal getPotenciaCalorEqAutonomos() {
		return potenciaCalorEqAutonomos;
	}

	public void setPotenciaCalorEqAutonomos(BigDecimal potenciaCalorEqAutonomos) {
		this.potenciaCalorEqAutonomos = potenciaCalorEqAutonomos;
	}

	public BigDecimal getPotenciaCalorEqCentr() {
		return potenciaCalorEqCentr;
	}

	public void setPotenciaCalorEqCentr(BigDecimal potenciaCalorEqCentr) {
		this.potenciaCalorEqCentr = potenciaCalorEqCentr;
	}

	public BigDecimal getPotenciaCalorInstGen() {
		return potenciaCalorInstGen;
	}

	public void setPotenciaCalorInstGen(BigDecimal potenciaCalorInstGen) {
		this.potenciaCalorInstGen = potenciaCalorInstGen;
	}

	public BigDecimal getPotenciaFrigoEqCentr() {
		return potenciaFrigoEqCentr;
	}

	public void setPotenciaFrigoEqCentr(BigDecimal potenciaFrigoEqCentr) {
		this.potenciaFrigoEqCentr = potenciaFrigoEqCentr;
	}

	public BigDecimal getPotenciaFrigoEnfriadores() {
		return potenciaFrigoEnfriadores;
	}

	public void setPotenciaFrigoEnfriadores(BigDecimal potenciaFrigoEnfriadores) {
		this.potenciaFrigoEnfriadores = potenciaFrigoEnfriadores;
	}

	public BigDecimal getPotenciaFrigoEqAutonomos() {
		return potenciaFrigoEqAutonomos;
	}

	public void setPotenciaFrigoEqAutonomos(BigDecimal potenciaFrigoEqAutonomos) {
		this.potenciaFrigoEqAutonomos = potenciaFrigoEqAutonomos;
	}

	public BigDecimal getPotenciaFrigoEqGen() {
		return potenciaFrigoEqGen;
	}

	public void setPotenciaFrigoEqGen(BigDecimal potenciaFrigoEqGen) {
		this.potenciaFrigoEqGen = potenciaFrigoEqGen;
	}

	public BigDecimal getPotenciaFrigoInstGen() {
		return potenciaFrigoInstGen;
	}

	public void setPotenciaFrigoInstGen(BigDecimal potenciaFrigoInstGen) {
		this.potenciaFrigoInstGen = potenciaFrigoInstGen;
	}

	public String getTorreRefirgeracion() {
		return torreRefirgeracion;
	}

	public void setTorreRefirgeracion(String torreRefirgeracion) {
		this.torreRefirgeracion = torreRefirgeracion;
	}

	public String getEquiposAudiovisuales() {
		return equiposAudiovisuales;
	}

	public void setEquiposAudiovisuales(String equiposAudiovisuales) {
		this.equiposAudiovisuales = equiposAudiovisuales;
	}

	public String getEquiposInstRadioactivas() {
		return equiposInstRadioactivas;
	}

	public void setEquiposInstRadioactivas(String equiposInstRadioactivas) {
		this.equiposInstRadioactivas = equiposInstRadioactivas;
	}

	public String getEquiposRayosLaser() {
		return equiposRayosLaser;
	}

	public void setEquiposRayosLaser(String equiposRayosLaser) {
		this.equiposRayosLaser = equiposRayosLaser;
	}

	public String getEquiposRayosUva() {
		return equiposRayosUva;
	}

	public void setEquiposRayosUva(String equiposRayosUva) {
		this.equiposRayosUva = equiposRayosUva;
	}

	public String getHayInstalacionesRelevantes() {
		return hayInstalacionesRelevantes;
	}

	public void setHayInstalacionesRelevantes(String hayInstalacionesRelevantes) {
		this.hayInstalacionesRelevantes = hayInstalacionesRelevantes;
	}

	public BigDecimal getPotenciaEquiposRayosLaser() {
		return potenciaEquiposRayosLaser;
	}

	public void setPotenciaEquiposRayosLaser(BigDecimal potenciaEquiposRayosLaser) {
		this.potenciaEquiposRayosLaser = potenciaEquiposRayosLaser;
	}

	public BigDecimal getPotenciaCentroTransformador() {
		return potenciaCentroTransformador;
	}

	public void setPotenciaCentroTransformador(BigDecimal potenciaCentroTransformador) {
		this.potenciaCentroTransformador = potenciaCentroTransformador;
	}

	public BigDecimal getPotenciaTotal() {
		return potenciaTotal;
	}

	public void setPotenciaTotal(BigDecimal potenciaTotal) {
		this.potenciaTotal = potenciaTotal;
	}

	public String getEquipoCentralizadoLocalCalefcc() {
		return equipoCentralizadoLocalCalefcc;
	}

	public void setEquipoCentralizadoLocalCalefcc(String equipoCentralizadoLocalCalefcc) {
		this.equipoCentralizadoLocalCalefcc = equipoCentralizadoLocalCalefcc;
	}

	public String getInstalacionesCalefaccion() {
		return instalacionesCalefaccion;
	}

	public void setInstalacionesCalefaccion(String instalacionesCalefaccion) {
		this.instalacionesCalefaccion = instalacionesCalefaccion;
	}

	public String getInstalacionesGeneralEdificioCalefcc() {
		return instalacionesGeneralEdificioCalefcc;
	}

	public void setInstalacionesGeneralEdificioCalefcc(String instalacionesGeneralEdificioCalefcc) {
		this.instalacionesGeneralEdificioCalefcc = instalacionesGeneralEdificioCalefcc;
	}

	public BigDecimal getPotenciaCalefaccion() {
		return potenciaCalefaccion;
	}

	public void setPotenciaCalefaccion(BigDecimal potenciaCalefaccion) {
		this.potenciaCalefaccion = potenciaCalefaccion;
	}

	public BigDecimal getPotenciaCalorEqCentrCalefcc() {
		return potenciaCalorEqCentrCalefcc;
	}

	public void setPotenciaCalorEqCentrCalefcc(BigDecimal potenciaCalorEqCentrCalefcc) {
		this.potenciaCalorEqCentrCalefcc = potenciaCalorEqCentrCalefcc;
	}

	public BigDecimal getPotenciaCalorInstGenCalefcc() {
		return potenciaCalorInstGenCalefcc;
	}

	public void setPotenciaCalorInstGenCalefcc(BigDecimal potenciaCalorInstGenCalefcc) {
		this.potenciaCalorInstGenCalefcc = potenciaCalorInstGenCalefcc;
	}

	public String getTipoCombustibleCalefcc() {
		return tipoCombustibleCalefcc;
	}

	public void setTipoCombustibleCalefcc(String tipoCombustibleCalefcc) {
		this.tipoCombustibleCalefcc = tipoCombustibleCalefcc;
	}

	public String getEvacuacionCubierta() {
		return evacuacionCubierta;
	}

	public void setEvacuacionCubierta(String evacuacionCubierta) {
		this.evacuacionCubierta = evacuacionCubierta;
	}

	public String getEvacuacionFachada() {
		return evacuacionFachada;
	}

	public void setEvacuacionFachada(String evacuacionFachada) {
		this.evacuacionFachada = evacuacionFachada;
	}

	public String getVentilacionForzada() {
		return ventilacionForzada;
	}

	public void setVentilacionForzada(String ventilacionForzada) {
		this.ventilacionForzada = ventilacionForzada;
	}

	public BigDecimal getCaudal() {
		return caudal;
	}

	public void setCaudal(BigDecimal caudal) {
		this.caudal = caudal;
	}

	public String getClimatizacionAc() {
		return climatizacionAc;
	}

	public void setClimatizacionAc(String climatizacionAc) {
		this.climatizacionAc = climatizacionAc;
	}

	public BigDecimal getCaudalCondensadorCubierta() {
		return caudalCondensadorCubierta;
	}

	public void setCaudalCondensadorCubierta(BigDecimal caudalCondensadorCubierta) {
		this.caudalCondensadorCubierta = caudalCondensadorCubierta;
	}
}
