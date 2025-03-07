package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_VIA database table.
 */
@Entity
@Table(name = "TIPO_VIA")
@NamedQuery(name = "TipoViaVO.findAll", query = "SELECT t FROM TipoViaVO t")
public class TipoViaVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1123082083267793638L;

	@Id
	@Column(name = "ID_TIPO_VIA")
	private String idTipoVia;

	@Column(name = "ID_TIPO_DGT")
	private String idTipoDgt;

	@Column(name = "ID_TIPO_VIA_DGT")
	private String idTipoViaDgt;

	@Column(name = "NOMBRE")
	private String nombre;

	private String obsoleto;

	public TipoViaVO() {}

	public String getIdTipoVia() {
		return this.idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getIdTipoDgt() {
		return this.idTipoDgt;
	}

	public void setIdTipoDgt(String idTipoDgt) {
		this.idTipoDgt = idTipoDgt;
	}

	public String getIdTipoViaDgt() {
		return this.idTipoViaDgt;
	}

	public void setIdTipoViaDgt(String idTipoViaDgt) {
		this.idTipoViaDgt = idTipoViaDgt;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObsoleto() {
		return this.obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}

}