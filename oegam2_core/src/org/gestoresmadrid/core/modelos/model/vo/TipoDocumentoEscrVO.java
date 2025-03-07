package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_DOCUMENTO_ESCR")
public class TipoDocumentoEscrVO implements Serializable{

	private static final long serialVersionUID = -2313045958154191741L;
	
	@Id
	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name="DESC_TIPO_DOC_ESC")
	private String descTipoDocEscr;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="MODELO_TIP_DOC_ESCR", joinColumns = {@JoinColumn(name="TIPO_DOCUMENTO")},inverseJoinColumns = {@JoinColumn(name="MODELO"),@JoinColumn(name="VERSION")})
	private Set<ModeloVO> modelo;

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDescTipoDocEscr() {
		return descTipoDocEscr;
	}

	public void setDescTipoDocEscr(String descTipoDocEscr) {
		this.descTipoDocEscr = descTipoDocEscr;
	}

	public Set<ModeloVO> getModelo() {
		return modelo;
	}

	public void setModelo(Set<ModeloVO> modelo) {
		this.modelo = modelo;
	}

	
}