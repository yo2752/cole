package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CARACTERISTICAS_ELECTRICAS database table.
 */
@Entity
@Table(name = "CARACTERISTICAS_ELECTRICAS")
public class CaracteristicasElectricasVO implements Serializable {

	private static final long serialVersionUID = -1860081307645708091L;

	@Id
	@Column(name = "ID_CARACTERISTICA_ELECTRICA")
	private Long idCaracteristicaElectrica;

	private String bastidor;

	@Column(name = "CODIGO_MARCA")
	private String codigoMarca;

	private String marca;

	private BigDecimal consumo;

	private String modelo;

	@Column(name = "TIPO_ITV")
	private String tipoItv;

	private String variante;

	private String version;

	@Column(name = "CATEGORIA_ELECTRICA")
	private String categoriaElectrica;

	@Column(name = "AUTONOMIA_ELECTRICA")
	private BigDecimal autonomiaElectrica;

	public Long getIdCaracteristicaElectrica() {
		return idCaracteristicaElectrica;
	}

	public void setIdCaracteristicaElectrica(Long idCaracteristicaElectrica) {
		this.idCaracteristicaElectrica = idCaracteristicaElectrica;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public BigDecimal getConsumo() {
		return consumo;
	}

	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipoItv() {
		return tipoItv;
	}

	public void setTipoItv(String tipoItv) {
		this.tipoItv = tipoItv;
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
}