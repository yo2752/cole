package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the URL database table.
 */
@Entity
@Table(name = "URL")
public class UrlVO implements Serializable {

	private static final long serialVersionUID = 5681442899329529021L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CODIGO_URL")
	private String codigoUrl;

	@Column(name = "PATRON_URL")
	private String patronUrl;

	@Column(name = "ORDEN")
	private String orden;
	
	@ManyToMany(mappedBy="urls")
	private List<AplicacionVO> aplicaciones;

	public UrlVO() {}

	public String getCodigoUrl() {
		return this.codigoUrl;
	}

	public void setCodigoUrl(String codigoUrl) {
		this.codigoUrl = codigoUrl;
	}

	public String getPatronUrl() {
		return this.patronUrl;
	}

	public void setPatronUrl(String patronUrl) {
		this.patronUrl = patronUrl;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public List<AplicacionVO> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(List<AplicacionVO> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}
}