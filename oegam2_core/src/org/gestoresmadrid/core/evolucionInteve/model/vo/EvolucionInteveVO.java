package org.gestoresmadrid.core.evolucionInteve.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;


@Entity
@Table(name="EVOLUCION_INTEVE")
public class EvolucionInteveVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8502359442840539597L;

	@EmbeddedId
	private EvolucionIntevePK id;
	
	@Column(name="TIPO_ACTUACION")
	private String tipoActuacion;
	
	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnt;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public EvolucionIntevePK getId() {
		return id;
	}

	public void setId(EvolucionIntevePK id) {
		this.id = id;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public BigDecimal getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

}
