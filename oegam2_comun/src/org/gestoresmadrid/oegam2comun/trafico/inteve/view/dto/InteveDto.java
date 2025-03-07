package org.gestoresmadrid.oegam2comun.trafico.inteve.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InteveDto implements Serializable {

	private static final long serialVersionUID = 5667035069138535765L;

	private String matricula;

	private String tasa;

	private String fechaMatr;

	private String jefatura;

	private String titular;

	private String bastidor;

	private String marca;

	private String modelo;

	private String tipoVehiculo;

	private String color;

	private String procedencia;

	private String servDest;

	private String plazas;

	private String carburante;

	private String autTrans;

	private BigDecimal potenciaFiscal;

	private String cilindrada;

	private String mma;

	private String tara;

	private String tipoVarVer;

	private String contrHomolog;

	private String mmCarga;

	private String masaCirculacion;

	private BigDecimal potenciaNetaMax;

	private String relPotenciaPeso;

	private String numPlazasPie;

	private String municipio;

	private String pueblo;

	private String provincia;

	private String domicilio;

	private String codigoPostal;

	private List<String> listaDatos;

	private boolean repetir;

	private boolean desactivadaConsulta;

	private Long idContadorUso;

	private Date fechaInforme;

	private Long idContrato;

	// Campos que indican si la presentación ha ido bien o mal
	/**
	 * Indica si se ha producido algún error en el proceso de consulta de la matrícula
	 */
	private Boolean error;

	/**
	 * Mensaje que obtenido en el posible error producido durante la consulta de la matrícula.
	 */
	private String mensaje;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAutTrans() {
		return autTrans;
	}

	public void setAutTrans(String autTrans) {
		this.autTrans = autTrans;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getCarburante() {
		return carburante;
	}

	public void setCarburante(String carburante) {
		this.carburante = carburante;
	}

	public String getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getContrHomolog() {
		return contrHomolog;
	}

	public void setContrHomolog(String contrHomolog) {
		this.contrHomolog = contrHomolog;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getFechaMatr() {
		return fechaMatr;
	}

	public void setFechaMatr(String fechaMatr) {
		this.fechaMatr = fechaMatr;
	}

	public List<String> getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List<String> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMasaCirculacion() {
		return masaCirculacion;
	}

	public void setMasaCirculacion(String masaCirculacion) {
		this.masaCirculacion = masaCirculacion;
	}

	public String getMma() {
		return mma;
	}

	public void setMma(String mma) {
		this.mma = mma;
	}

	public String getMmCarga() {
		return mmCarga;
	}

	public void setMmCarga(String mmCarga) {
		this.mmCarga = mmCarga;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getPlazas() {
		return plazas;
	}

	public void setPlazas(String plazas) {
		this.plazas = plazas;
	}

	public BigDecimal getPotenciaFiscal() {
		return potenciaFiscal;
	}

	public void setPotenciaFiscal(BigDecimal potenciaFiscal) {
		this.potenciaFiscal = potenciaFiscal;
	}

	public BigDecimal getPotenciaNetaMax() {
		return potenciaNetaMax;
	}

	public void setPotenciaNetaMax(BigDecimal potenciaNetaMax) {
		this.potenciaNetaMax = potenciaNetaMax;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getRelPotenciaPeso() {
		return relPotenciaPeso;
	}

	public void setRelPotenciaPeso(String relPotenciaPeso) {
		this.relPotenciaPeso = relPotenciaPeso;
	}

	public String getServDest() {
		return servDest;
	}

	public void setServDest(String servDest) {
		this.servDest = servDest;
	}

	public String getTara() {
		return tara;
	}

	public void setTara(String tara) {
		this.tara = tara;
	}

	public String getTipoVarVer() {
		return tipoVarVer;
	}

	public void setTipoVarVer(String tipoVarVer) {
		this.tipoVarVer = tipoVarVer;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getNumPlazasPie() {
		return numPlazasPie;
	}

	public void setNumPlazasPie(String numPlazasPie) {
		this.numPlazasPie = numPlazasPie;
	}

	public String getTasa() {
		return tasa;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public Long getIdContadorUso() {
		return idContadorUso;
	}

	public void setIdContadorUso(Long idContadorUso) {
		this.idContadorUso = idContadorUso;
	}

	public Date getFechaInforme() {
		return fechaInforme;
	}

	public void setFechaInforme(Date fechaInforme) {
		this.fechaInforme = fechaInforme;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public boolean isRepetir() {
		return repetir;
	}

	public void setRepetir(boolean repetir) {
		this.repetir = repetir;
	}

	public boolean isDesactivadaConsulta() {
		return desactivadaConsulta;
	}

	public void setDesactivadaConsulta(boolean desactivadaConsulta) {
		this.desactivadaConsulta = desactivadaConsulta;
	}
}
