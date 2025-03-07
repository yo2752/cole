package org.gestoresmadrid.oegamComun.interviniente.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;

public class ResultadoIntevTraficoBean implements Serializable{
	
	private static final long serialVersionUID = 4467578622180948069L;
	
	Boolean error;
	String mensaje;
	Boolean esModificada;
	IntervinienteTraficoVO intervinienteTrafBorrar;
	IntervinienteTraficoVO intervinienteTrafico;
	
	public ResultadoIntevTraficoBean(Boolean esError) {
		this.error = esError;
		this.esModificada = Boolean.FALSE;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public IntervinienteTraficoVO getIntervinienteTrafBorrar() {
		return intervinienteTrafBorrar;
	}
	public void setIntervinienteTrafBorrar(IntervinienteTraficoVO intervinienteTrafBorrar) {
		this.intervinienteTrafBorrar = intervinienteTrafBorrar;
	}
	public IntervinienteTraficoVO getIntervinienteTrafico() {
		return intervinienteTrafico;
	}
	public void setIntervinienteTrafico(IntervinienteTraficoVO intervinienteTrafico) {
		this.intervinienteTrafico = intervinienteTrafico;
	}
	public Boolean getEsModificada() {
		return esModificada;
	}
	public void setEsModificada(Boolean esModificada) {
		this.esModificada = esModificada;
	}
	
	
}
