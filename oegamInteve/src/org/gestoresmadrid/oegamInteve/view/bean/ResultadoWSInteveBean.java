package org.gestoresmadrid.oegamInteve.view.bean;

import java.io.Serializable;

public class ResultadoWSInteveBean implements Serializable {

	private static final long serialVersionUID = -5766470959327734325L;

	Long idTramiteInteve;
	String estado;
	String resultado;

	public Long getIdTramiteInteve() {
		return idTramiteInteve;
	}

	public void setIdTramiteInteve(Long idTramiteInteve) {
		this.idTramiteInteve = idTramiteInteve;
	}

	public String getEstado() {
		return estado;
	}

	public ResultadoWSInteveBean(Long idTramiteInteve, String estado, String resultado) {
		super();
		this.idTramiteInteve = idTramiteInteve;
		this.estado = estado;
		this.resultado = resultado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}