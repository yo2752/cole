package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


public class BuqueRegistroDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6725109362293787786L;

	private long idBuque;

	private String anioConstruccion;

	private String anioInscripcion;

	private String astillero;

	private String bruto;

	private String caladoMaximo;

	private String capitaniaMaritima;

	private String cargaMaxima;

	private String desplazamiento;

	private String distritoMaritimo;

	private String eslora;

	private String estado;

	private String folioInscripcion;

	private String manga;

	private String marca;

	private String material;

	private String matriculaFluvial;

	private String modelo;

	private String nib;

	private String nombreBuque;

	private String numLista;

	private String numSerie;

	private String omi;

	private String otrosDatos;

	private String pabellon;

	private String pais;

	private String pesoMuerto;

	private String provinciaMaritima;

	private String puntal;

	private String registradoBruto;

	private String registradoNeto;

	private String subTipoEmbarcacion;

	private String tipoEmbarcacion;

	private String neto;
	
	private BigDecimal idPropiedad;

	private List<MotorBuqueDto> motorBuques;
	
	private  MotorBuqueDto motor;

	public BuqueRegistroDto() {
	}

	public long getIdBuque() {
		return this.idBuque;
	}

	public void setIdBuque(long idBuque) {
		this.idBuque = idBuque;
	}

	public String getAnioConstruccion() {
		return this.anioConstruccion;
	}

	public void setAnioConstruccion(String anioConstruccion) {
		this.anioConstruccion = anioConstruccion;
	}

	public String getAnioInscripcion() {
		return this.anioInscripcion;
	}

	public void setAnioInscripcion(String anioInscripcion) {
		this.anioInscripcion = anioInscripcion;
	}

	public String getAstillero() {
		return this.astillero;
	}

	public void setAstillero(String astillero) {
		this.astillero = astillero;
	}

	public String getBruto() {
		return this.bruto;
	}

	public void setBruto(String bruto) {
		this.bruto = bruto;
	}

	public String getCaladoMaximo() {
		return this.caladoMaximo;
	}

	public void setCaladoMaximo(String caladoMaximo) {
		this.caladoMaximo = caladoMaximo;
	}

	public String getCapitaniaMaritima() {
		return this.capitaniaMaritima;
	}

	public void setCapitaniaMaritima(String capitaniaMaritima) {
		this.capitaniaMaritima = capitaniaMaritima;
	}

	public String getCargaMaxima() {
		return this.cargaMaxima;
	}

	public void setCargaMaxima(String cargaMaxima) {
		this.cargaMaxima = cargaMaxima;
	}

	public String getDesplazamiento() {
		return this.desplazamiento;
	}

	public void setDesplazamiento(String desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	public String getDistritoMaritimo() {
		return this.distritoMaritimo;
	}

	public void setDistritoMaritimo(String distritoMaritimo) {
		this.distritoMaritimo = distritoMaritimo;
	}

	public String getEslora() {
		return this.eslora;
	}

	public void setEslora(String eslora) {
		this.eslora = eslora;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFolioInscripcion() {
		return this.folioInscripcion;
	}

	public void setFolioInscripcion(String folioInscripcion) {
		this.folioInscripcion = folioInscripcion;
	}

	public String getManga() {
		return this.manga;
	}

	public void setManga(String manga) {
		this.manga = manga;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMatriculaFluvial() {
		return this.matriculaFluvial;
	}

	public void setMatriculaFluvial(String matriculaFluvial) {
		this.matriculaFluvial = matriculaFluvial;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNib() {
		return this.nib;
	}

	public void setNib(String nib) {
		this.nib = nib;
	}

	public String getNombreBuque() {
		return this.nombreBuque;
	}

	public void setNombreBuque(String nombreBuque) {
		this.nombreBuque = nombreBuque;
	}

	public String getNumLista() {
		return this.numLista;
	}

	public void setNumLista(String numLista) {
		this.numLista = numLista;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getOmi() {
		return this.omi;
	}

	public void setOmi(String omi) {
		this.omi = omi;
	}

	public String getOtrosDatos() {
		return this.otrosDatos;
	}

	public void setOtrosDatos(String otrosDatos) {
		this.otrosDatos = otrosDatos;
	}

	public String getPabellon() {
		return this.pabellon;
	}

	public void setPabellon(String pabellon) {
		this.pabellon = pabellon;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPesoMuerto() {
		return this.pesoMuerto;
	}

	public void setPesoMuerto(String pesoMuerto) {
		this.pesoMuerto = pesoMuerto;
	}

	public String getProvinciaMaritima() {
		return this.provinciaMaritima;
	}

	public void setProvinciaMaritima(String provinciaMaritima) {
		this.provinciaMaritima = provinciaMaritima;
	}

	public String getPuntal() {
		return this.puntal;
	}

	public void setPuntal(String puntal) {
		this.puntal = puntal;
	}

	public String getRegistradoBruto() {
		return this.registradoBruto;
	}

	public void setRegistradoBruto(String registradoBruto) {
		this.registradoBruto = registradoBruto;
	}

	public String getRegistradoNeto() {
		return this.registradoNeto;
	}

	public void setRegistradoNeto(String registradoNeto) {
		this.registradoNeto = registradoNeto;
	}

	public String getSubTipoEmbarcacion() {
		return this.subTipoEmbarcacion;
	}

	public void setSubTipoEmbarcacion(String subTipoEmbarcacion) {
		this.subTipoEmbarcacion = subTipoEmbarcacion;
	}

	public String getTipoEmbarcacion() {
		return this.tipoEmbarcacion;
	}

	public void setTipoEmbarcacion(String tipoEmbarcacion) {
		this.tipoEmbarcacion = tipoEmbarcacion;
	}

	public List<MotorBuqueDto> getMotorBuques() {
		return this.motorBuques;
	}

	public void setMotorBuques(List<MotorBuqueDto> motorBuques) {
		this.motorBuques = motorBuques;
	}

	public MotorBuqueDto getMotor() {
		return motor;
	}

	public void setMotor(MotorBuqueDto motor) {
		this.motor = motor;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public String getNeto() {
		return neto;
	}

	public void setNeto(String neto) {
		this.neto = neto;
	}

}