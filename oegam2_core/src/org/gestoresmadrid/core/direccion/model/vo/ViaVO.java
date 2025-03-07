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
 * The persistent class for the VIA database table.
 */
@Entity
@Table(name = "VIA")
public class ViaVO implements Serializable {

	private static final long serialVersionUID = 2388880118192876060L;

	@Id
	@Column(name = "ID_VIA")
	private BigDecimal idVia;

	@Column(name = "ID_MUNICIPIO")
	private String idMunicipio;

	private String via;

	@ManyToOne
	@JoinColumn(name = "ID_PROVINCIA", insertable = false, updatable = false)
	private ProvinciaVO provincia;

	@ManyToOne
	@JoinColumn(name = "TIPO_VIA_INE", insertable = false, updatable = false)
	private TipoViaVO tipoViaIne;

	public BigDecimal getIdVia() {
		return idVia;
	}

	public void setIdVia(BigDecimal idVia) {
		this.idVia = idVia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public TipoViaVO getTipoViaIne() {
		return tipoViaIne;
	}

	public void setTipoViaIne(TipoViaVO tipoViaIne) {
		this.tipoViaIne = tipoViaIne;
	}
}