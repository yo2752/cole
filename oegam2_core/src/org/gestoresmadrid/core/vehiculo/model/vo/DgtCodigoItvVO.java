package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DGT_CODIGO_ITV database table.
 */
@Entity
@Table(name = "DGT_CODIGO_ITV")
public class DgtCodigoItvVO implements Serializable {

	private static final long serialVersionUID = -3158309159539565426L;

	@Id
	@Column(name = "CODIGO_ITV")
	private String codigoItv;

	private String bastidor;

	private String carburante;

	private String carroceria;

	@Column(name = "CAT_HOMOLOGACION")
	private String catHomologacion;

	private String cilindrada;

	@Column(name = "CODIGO_ECO")
	private String codigoEco;

	private BigDecimal consumo;

	@Column(name = "CONTRASENIA_HOMOLOGACION")
	private String contraseniaHomologacion;

	private String co2;

	@Column(name = "DISTANCIA_EJES")
	private BigDecimal distanciaEjes;

	@Column(name = "ECO_INNOVACION")
	private String ecoInnovacion;

	private String fabricante;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ACTUALIZACION")
	private Date fechaActualizacion;

	private String marca;

	@Column(name = "MASA_MOM")
	private BigDecimal masaMom;

	private String mma;

	private String mmc;

	private String modelo;

	@Column(name = "NIVEL_EMISION")
	private String nivelEmision;

	private String plazas;

	@Column(name = "PLAZAS_PIE")
	private String plazasPie;

	@Column(name = "POTENCIA_FISCAL")
	private BigDecimal potenciaFiscal;

	@Column(name = "POTENCIA_REAL")
	private BigDecimal potenciaReal;

	@Column(name = "POTENCIA_PESO")
	private String potenciaPeso;

	private String procedencia;

	@Column(name = "REDUCCION_ECO")
	private BigDecimal reduccionEco;

	private String tara;

	private String tipo;

	@Column(name = "TIPO_ALIMENTACION")
	private String tipoAlimentacion;

	@Column(name = "TIPO_VEHICULO_INDUSTRIA")
	private String tipoVehiculoIndustria;

	@Column(name = "TIPO_VEHICULO_TRAFICO")
	private String tipoVehiculoTrafico;

	private String variante;

	@Column(name = "VERSION_35")
	private String version35;

	@Column(name = "VIA_ANTERIOR")
	private BigDecimal viaAnterior;

	@Column(name = "VIA_POSTERIOR")
	private BigDecimal viaPosterior;

	public String getCodigoItv() {
		return codigoItv;
	}

	public void setCodigoItv(String codigoItv) {
		this.codigoItv = codigoItv;
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

	public String getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

	public String getCatHomologacion() {
		return catHomologacion;
	}

	public void setCatHomologacion(String catHomologacion) {
		this.catHomologacion = catHomologacion;
	}

	public String getCilindrada() {
		return cilindrada;
	}

	public void setCilindrada(String cilindrada) {
		this.cilindrada = cilindrada;
	}

	public String getCodigoEco() {
		return codigoEco;
	}

	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}

	public BigDecimal getConsumo() {
		return consumo;
	}

	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}

	public String getContraseniaHomologacion() {
		return contraseniaHomologacion;
	}

	public void setContraseniaHomologacion(String contraseniaHomologacion) {
		this.contraseniaHomologacion = contraseniaHomologacion;
	}

	public String getCo2() {
		return co2;
	}

	public void setCo2(String co2) {
		this.co2 = co2;
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

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public BigDecimal getMasaMom() {
		return masaMom;
	}

	public void setMasaMom(BigDecimal masaMom) {
		this.masaMom = masaMom;
	}

	public String getMma() {
		return mma;
	}

	public void setMma(String mma) {
		this.mma = mma;
	}

	public String getMmc() {
		return mmc;
	}

	public void setMmc(String mmc) {
		this.mmc = mmc;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNivelEmision() {
		return nivelEmision;
	}

	public void setNivelEmision(String nivelEmision) {
		this.nivelEmision = nivelEmision;
	}

	public String getPlazas() {
		return plazas;
	}

	public void setPlazas(String plazas) {
		this.plazas = plazas;
	}

	public String getPlazasPie() {
		return plazasPie;
	}

	public void setPlazasPie(String plazasPie) {
		this.plazasPie = plazasPie;
	}

	public BigDecimal getPotenciaFiscal() {
		return potenciaFiscal;
	}

	public void setPotenciaFiscal(BigDecimal potenciaFiscal) {
		this.potenciaFiscal = potenciaFiscal;
	}

	public BigDecimal getPotenciaReal() {
		return potenciaReal;
	}

	public void setPotenciaReal(BigDecimal potenciaReal) {
		this.potenciaReal = potenciaReal;
	}

	public String getPotenciaPeso() {
		return potenciaPeso;
	}

	public void setPotenciaPeso(String potenciaPeso) {
		this.potenciaPeso = potenciaPeso;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public BigDecimal getReduccionEco() {
		return reduccionEco;
	}

	public void setReduccionEco(BigDecimal reduccionEco) {
		this.reduccionEco = reduccionEco;
	}

	public String getTara() {
		return tara;
	}

	public void setTara(String tara) {
		this.tara = tara;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoAlimentacion() {
		return tipoAlimentacion;
	}

	public void setTipoAlimentacion(String tipoAlimentacion) {
		this.tipoAlimentacion = tipoAlimentacion;
	}

	public String getTipoVehiculoIndustria() {
		return tipoVehiculoIndustria;
	}

	public void setTipoVehiculoIndustria(String tipoVehiculoIndustria) {
		this.tipoVehiculoIndustria = tipoVehiculoIndustria;
	}

	public String getTipoVehiculoTrafico() {
		return tipoVehiculoTrafico;
	}

	public void setTipoVehiculoTrafico(String tipoVehiculoTrafico) {
		this.tipoVehiculoTrafico = tipoVehiculoTrafico;
	}

	public String getVariante() {
		return variante;
	}

	public void setVariante(String variante) {
		this.variante = variante;
	}

	public String getVersion35() {
		return version35;
	}

	public void setVersion35(String version35) {
		this.version35 = version35;
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
}