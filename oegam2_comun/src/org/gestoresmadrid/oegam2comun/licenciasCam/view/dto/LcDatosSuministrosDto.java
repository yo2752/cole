package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcDatosSuministrosDto implements Serializable {

	private static final long serialVersionUID = -2896493161916244784L;

	private Long idDatosSuministros;

	private String acumuladorCalorAguaC;

	private Boolean aguaCalienteSanitaria;

	private BigDecimal potenciaAcumuladorAguaC;

	private BigDecimal potenciaAguaCaliente;

	private String tipoCombustibleAguaC;

	private Boolean captEnergiaSolar;

	private Boolean equipoFotovoltaico;

	private BigDecimal numEquiposUsoFotovoltaico;

	private BigDecimal superfCaptEqUsoFotovoltaico;

	private String equiposUsosTermico;

	private BigDecimal numEquiposUsoTermico;

	private BigDecimal superfCaptEqUsoTermico;

	private BigDecimal caudalCondensadorCubierta;

	private BigDecimal caudalSalidaCubierta;

	private BigDecimal caudalSalidaFachada;

	private Boolean climatizacionAc;

	private Boolean condensacionSalidaChimenea;

	private Boolean condensacionSalidaFachada;

	private Boolean condensadorCubierta;

	private Boolean hayEnfriadoresEvaporativos;

	private BigDecimal enfriadoresEvaporativos;

	private Boolean equipoCentralizadoLocal;

	private Boolean hayEquiposAutonomos;

	private BigDecimal equiposAutonomos;

	private Boolean instalacionesGeneralEdificio;

	private BigDecimal numTorresRefrigeracion;

	private BigDecimal potenciaCalorEnfriadores;

	private BigDecimal potenciaCalorEqAutonomos;

	private BigDecimal potenciaCalorEqCentr;

	private BigDecimal potenciaCalorInstGen;

	private BigDecimal potenciaFrigoEqCentr;

	private BigDecimal potenciaFrigoEnfriadores;

	private BigDecimal potenciaFrigoEqAutonomos;

	private BigDecimal potenciaFrigoEqGen;

	private BigDecimal potenciaFrigoInstGen;

	private Boolean torreRefirgeracion;

	private String equiposAudiovisuales;

	private String equiposInstRadioactivas;

	private String equiposRayosLaser;

	private String equiposRayosUva;

	private Boolean hayInstalacionesRelevantes;

	private BigDecimal potenciaEquiposRayosLaser;

	private BigDecimal potenciaCentroTransformador;

	private BigDecimal potenciaTotal;

	private Boolean equipoCentralizadoLocalCalefcc;

	private Boolean instalacionesCalefaccion;

	private Boolean instalacionesGeneralEdificioCalefcc;

	private BigDecimal potenciaCalefaccion;

	private BigDecimal potenciaCalorEqCentrCalefcc;

	private BigDecimal potenciaCalorInstGenCalefcc;

	private String tipoCombustibleCalefcc;

	private String evacuacionCubierta;

	private String evacuacionFachada;

	private Boolean ventilacionForzada;

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

	public Boolean getAguaCalienteSanitaria() {
		return aguaCalienteSanitaria;
	}

	public void setAguaCalienteSanitaria(Boolean aguaCalienteSanitaria) {
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

	public Boolean getCaptEnergiaSolar() {
		return captEnergiaSolar;
	}

	public void setCaptEnergiaSolar(Boolean captEnergiaSolar) {
		this.captEnergiaSolar = captEnergiaSolar;
	}

	public Boolean getEquipoFotovoltaico() {
		return equipoFotovoltaico;
	}

	public void setEquipoFotovoltaico(Boolean equipoFotovoltaico) {
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

	public Boolean getClimatizacionAc() {
		return climatizacionAc;
	}

	public void setClimatizacionAc(Boolean climatizacionAc) {
		this.climatizacionAc = climatizacionAc;
	}

	public Boolean getCondensacionSalidaChimenea() {
		return condensacionSalidaChimenea;
	}

	public void setCondensacionSalidaChimenea(Boolean condensacionSalidaChimenea) {
		this.condensacionSalidaChimenea = condensacionSalidaChimenea;
	}

	public Boolean getCondensacionSalidaFachada() {
		return condensacionSalidaFachada;
	}

	public void setCondensacionSalidaFachada(Boolean condensacionSalidaFachada) {
		this.condensacionSalidaFachada = condensacionSalidaFachada;
	}

	public Boolean getCondensadorCubierta() {
		return condensadorCubierta;
	}

	public void setCondensadorCubierta(Boolean condensadorCubierta) {
		this.condensadorCubierta = condensadorCubierta;
	}

	public Boolean getHayEnfriadoresEvaporativos() {
		return hayEnfriadoresEvaporativos;
	}

	public void setHayEnfriadoresEvaporativos(Boolean hayEnfriadoresEvaporativos) {
		this.hayEnfriadoresEvaporativos = hayEnfriadoresEvaporativos;
	}

	public BigDecimal getEnfriadoresEvaporativos() {
		return enfriadoresEvaporativos;
	}

	public void setEnfriadoresEvaporativos(BigDecimal enfriadoresEvaporativos) {
		this.enfriadoresEvaporativos = enfriadoresEvaporativos;
	}

	public Boolean getEquipoCentralizadoLocal() {
		return equipoCentralizadoLocal;
	}

	public void setEquipoCentralizadoLocal(Boolean equipoCentralizadoLocal) {
		this.equipoCentralizadoLocal = equipoCentralizadoLocal;
	}

	public Boolean getHayEquiposAutonomos() {
		return hayEquiposAutonomos;
	}

	public void setHayEquiposAutonomos(Boolean hayEquiposAutonomos) {
		this.hayEquiposAutonomos = hayEquiposAutonomos;
	}

	public BigDecimal getEquiposAutonomos() {
		return equiposAutonomos;
	}

	public void setEquiposAutonomos(BigDecimal equiposAutonomos) {
		this.equiposAutonomos = equiposAutonomos;
	}

	public Boolean getInstalacionesGeneralEdificio() {
		return instalacionesGeneralEdificio;
	}

	public void setInstalacionesGeneralEdificio(Boolean instalacionesGeneralEdificio) {
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

	public Boolean getTorreRefirgeracion() {
		return torreRefirgeracion;
	}

	public void setTorreRefirgeracion(Boolean torreRefirgeracion) {
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

	public Boolean getHayInstalacionesRelevantes() {
		return hayInstalacionesRelevantes;
	}

	public void setHayInstalacionesRelevantes(Boolean hayInstalacionesRelevantes) {
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

	public Boolean getEquipoCentralizadoLocalCalefcc() {
		return equipoCentralizadoLocalCalefcc;
	}

	public void setEquipoCentralizadoLocalCalefcc(Boolean equipoCentralizadoLocalCalefcc) {
		this.equipoCentralizadoLocalCalefcc = equipoCentralizadoLocalCalefcc;
	}

	public Boolean getInstalacionesCalefaccion() {
		return instalacionesCalefaccion;
	}

	public void setInstalacionesCalefaccion(Boolean instalacionesCalefaccion) {
		this.instalacionesCalefaccion = instalacionesCalefaccion;
	}

	public Boolean getInstalacionesGeneralEdificioCalefcc() {
		return instalacionesGeneralEdificioCalefcc;
	}

	public void setInstalacionesGeneralEdificioCalefcc(Boolean instalacionesGeneralEdificioCalefcc) {
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

	public Boolean getVentilacionForzada() {
		return ventilacionForzada;
	}

	public void setVentilacionForzada(Boolean ventilacionForzada) {
		this.ventilacionForzada = ventilacionForzada;
	}

	public BigDecimal getCaudal() {
		return caudal;
	}

	public void setCaudal(BigDecimal caudal) {
		this.caudal = caudal;
	}

	public BigDecimal getCaudalCondensadorCubierta() {
		return caudalCondensadorCubierta;
	}

	public void setCaudalCondensadorCubierta(BigDecimal caudalCondensadorCubierta) {
		this.caudalCondensadorCubierta = caudalCondensadorCubierta;
	}

}
