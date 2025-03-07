package org.gestoresmadrid.core.notificacion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IdNumColegiadoPK implements Serializable{

	private static final long serialVersionUID = 5191655068588081309L;
	
	@Column(name="CODIGO")
	private int codigo;
	
	@Column(name="NUM_COLEGIADO")
	private java.lang.String numColegiado;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public java.lang.String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(java.lang.String numColegiado) {
		this.numColegiado = numColegiado;
	}

	
}