package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the FUNCION database table.
 */
@Entity
@Table(name = "FUNCION")
public class FuncionVO implements Serializable {

	private static final long serialVersionUID = -675508004682940998L;

	@EmbeddedId
	private FuncionPK id;

	@Column(name = "DESC_FUNCION")
	private String descFuncion;

	// bi-directional many-to-one association to Aplicacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_APLICACION", insertable = false, updatable = false)
	private AplicacionVO aplicacion;

	@Column(name = "CODIGO_FUNCION_PADRE")
	private String codFuncionPadre;
	
	private Long nivel;

	private Long orden;

	private String tipo;

	private String url;
	
	@Column(name = "ESTADO_FUNCION")
	private Long estadoFuncion;

	public FuncionVO() {}

	public FuncionPK getId() {
		return this.id;
	}

	public void setId(FuncionPK id) {
		this.id = id;
	}

	public String getDescFuncion() {
		return this.descFuncion;
	}

	public void setDescFuncion(String descFuncion) {
		this.descFuncion = descFuncion;
	}

	public AplicacionVO getAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getCodFuncionPadre() {
		return codFuncionPadre;
	}

	public void setCodFuncionPadre(String codFuncionPadre) {
		this.codFuncionPadre = codFuncionPadre;
	}

	public Long getNivel() {
		return nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getEstadoFuncion() {
		return estadoFuncion;
	}

	public void setEstadoFuncion(Long estadoFuncion) {
		this.estadoFuncion = estadoFuncion;
	}
}