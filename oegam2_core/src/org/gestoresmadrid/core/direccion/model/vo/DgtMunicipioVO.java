package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the DGT_MUNICIPIO database table.
 */
@Entity
@Table(name = "DGT_MUNICIPIO")
public class DgtMunicipioVO implements Serializable {

	private static final long serialVersionUID = -4961004326112764661L;

	@Id
	@Column(name = "ID_DGT_MUNICIPIO")
	private BigDecimal idDgtMunicipio;

	@Column(name = "CODIGO_POSTAL")
	private String codigoPostal;

	@Column(name = "CODIGO_INE")
	private String codigoIne;

	private String municipio;

	@Column(name = "SIN_VALOR")
	private String sinValor;

	@ManyToOne
	@JoinColumn(name = "ID_DGT_PROVINCIA", insertable = false, updatable = false)
	private DgtProvinciaVO dgtProvincia;

	public BigDecimal getIdDgtMunicipio() {
		return idDgtMunicipio;
	}

	public void setIdDgtMunicipio(BigDecimal idDgtMunicipio) {
		this.idDgtMunicipio = idDgtMunicipio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoIne() {
		return codigoIne;
	}

	public void setCodigoIne(String codigoIne) {
		this.codigoIne = codigoIne;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getSinValor() {
		return sinValor;
	}

	public void setSinValor(String sinValor) {
		this.sinValor = sinValor;
	}

	public DgtProvinciaVO getDgtProvincia() {
		return dgtProvincia;
	}

	public void setDgtProvincia(DgtProvinciaVO dgtProvincia) {
		this.dgtProvincia = dgtProvincia;
	}
}