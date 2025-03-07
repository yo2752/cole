package trafico.beans;

import java.math.BigDecimal;

public class DatosITV {

	private String codigoItv;
	private String bastidor;
	private String tipoVehiculoIndustria;
	private String tipoVehiculoTrafico;
	private BigDecimal codigoMarca;
	private String marca;
	private String modelo;
	private String tara;
	private String mma;
	private String plazas;
	private String carburante;
	private String cilindrada;
	private BigDecimal potenciaFiscal;
	private BigDecimal potenciaReal;
	private String co2;
	private String nive;
	//Nuevos campos Mate 2.5

	private String fabricante;
	private String tipo; // Corresponde al VehiculoBean.HomologacionBean.clasificacionItv
	private String variante;
	private String version;
	private String catHomologacion; // Se corresponde con VehiculoBean.HomologacionBean.idHomologacion
	private BigDecimal consumo;
	private BigDecimal masaMom;
	private String procedencia; //Se corresponde con VehiculoBean.fabricacionBean.idFabricacion
	private BigDecimal distanciaEjes;
	private BigDecimal viaAnterior;
	private BigDecimal viaPosterior;
	private String tipoAlimentacion;//Se corresponde con VehiculoBean.alimentacionBean.idAlimentacion
	private String contraseniaHomologacion; //Corresponde con VehiculoBean.numHomologacion
	private String nivelEmision;
	private String ecoInnovacion;
	private BigDecimal reduccionEco;
	private String codigoEco;
	private String mmc;
	private String numPlazasPie;
	private String relPotenciaPeso;
	private String carroceria;

	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getVariante() {
		return variante;
	}
	public void setVariante(String variante) {
		this.variante = variante;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCatHomologacion() {
		return catHomologacion;
	}
	public void setCatHomologacion(String catHomologacion) {
		this.catHomologacion = catHomologacion;
	}
	public BigDecimal getConsumo() {
		return consumo;
	}
	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}
	public BigDecimal getMasaMom() {
		return masaMom;
	}
	public void setMasaMom(BigDecimal masaMom) {
		this.masaMom = masaMom;
	}
	public String getProcedencia() {
		return procedencia;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}
	public BigDecimal getDistanciaEjes() {
		return distanciaEjes;
	}
	public void setDistanciaEjes(BigDecimal distanciaEjes) {
		this.distanciaEjes = distanciaEjes;
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
	public String getTipoAlimentacion() {
		return tipoAlimentacion;
	}
	public void setTipoAlimentacion(String tipoAlimentacion) {
		this.tipoAlimentacion = tipoAlimentacion;
	}
	public String getContraseniaHomologacion() {
		return contraseniaHomologacion;
	}
	public void setContraseniaHomologacion(String contraseniaHomologacion) {
		this.contraseniaHomologacion = contraseniaHomologacion;
	}
	public String getNivelEmision() {
		return nivelEmision;
	}
	public void setNivelEmision(String nivelEmision) {
		this.nivelEmision = nivelEmision;
	}
	public String getEcoInnovacion() {
		return ecoInnovacion;
	}
	public void setEcoInnovacion(String ecoInnovacion) {
		this.ecoInnovacion = ecoInnovacion;
	}
	public BigDecimal getReduccionEco() {
		return reduccionEco;
	}
	public void setReduccionEco(BigDecimal reduccionEco) {
		this.reduccionEco = reduccionEco;
	}
	public String getCodigoEco() {
		return codigoEco;
	}
	public void setCodigoEco(String codigoEco) {
		this.codigoEco = codigoEco;
	}
	public String getNive() {
		return nive;
	}
	public void setNive(String nive) {
		this.nive = nive;
	}
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
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getTara() {
		return tara;
	}
	public void setTara(String tara) {
		this.tara = tara;
	}
	public String getMma() {
		return mma;
	}
	public void setMma(String mma) {
		this.mma = mma;
	}

	public String getPlazas() {
		return plazas;
	}
	public void setPlazas(String plazas) {
		this.plazas = plazas;
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
	public String getCo2() {
		return co2;
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
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	public BigDecimal getCodigoMarca() {
		return codigoMarca;
	}
	public void setCodigoMarca(BigDecimal codigoMarca) {
		this.codigoMarca = codigoMarca;
	}
	public String getMmc() {
		return mmc;
	}
	public void setMmc(String mmc) {
		this.mmc = mmc;
	}
	public String getNumPlazasPie() {
		return numPlazasPie;
	}
	public void setNumPlazasPie(String numPlazasPie) {
		this.numPlazasPie = numPlazasPie;
	}
	public String getRelPotenciaPeso() {
		return relPotenciaPeso;
	}
	public void setRelPotenciaPeso(String relPotenciaPeso) {
		this.relPotenciaPeso = relPotenciaPeso;
	}
	public String getCarroceria() {
		return carroceria;
	}
	public void setCarroceria(String carroceria) {
		this.carroceria = carroceria;
	}

}