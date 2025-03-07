package org.gestoresmadrid.oegam2comun.evolucionJustifProf.views.beans;



import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

public class EvolucionJustifProfBean {
	//@FilterSimpleExpression(name="id.numExpediente")
	long numExpediente;
	
	@FilterSimpleExpression(name="id.idJustificanteInterno")
	long idJustificanteInterno;
	
	long idJustificante;
	
	Date fecha;
	
	int estadoAnterior;
	
	BigDecimal estadoNuevo;
	
	//@FilterSimpleExpression
	//@FilterRelationship(name="usuario", joinType=Criteria.LEFT_JOIN)
	UsuarioVO usuario;

	public long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public long getIdJustificante() {
		return idJustificante;
	}

	public void setIdJustificante(long idJustificante) {
		this.idJustificante = idJustificante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(int estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
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

	public long getIdJustificanteInterno() {
		return idJustificanteInterno;
	}

	public void setIdJustificanteInterno(long idJustificanteInterno) {
		this.idJustificanteInterno = idJustificanteInterno;
	}
	
}
