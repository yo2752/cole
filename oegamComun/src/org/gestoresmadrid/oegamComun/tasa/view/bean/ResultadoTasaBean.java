package org.gestoresmadrid.oegamComun.tasa.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

public class ResultadoTasaBean implements Serializable{

	private static final long serialVersionUID = 9129287979303016252L;

	public ResultadoTasaBean(Boolean error) {
		super();
		this.error = error;
	}
	
	Boolean error;
	String mensaje;
	TasaVO tasaDesasignar;
	TasaVO tasa;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesError;
	
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
	public TasaVO getTasaDesasignar() {
		return tasaDesasignar;
	}
	public void setTasaDesasignar(TasaVO tasaDesasignar) {
		this.tasaDesasignar = tasaDesasignar;
	}
	public TasaVO getTasa() {
		return tasa;
	}
	public void setTasa(TasaVO tasa) {
		this.tasa = tasa;
	}
	public void addListaMensajeOk(String mensaje) {
		if (listaMensajesOk == null) {
			listaMensajesOk = new ArrayList<>();
		}
		listaMensajesOk.add(mensaje);
	}
	public void addListaMensajeError(String mensaje) {
		if (listaMensajesError == null) {
			listaMensajesError = new ArrayList<>();
		}
		listaMensajesError.add(mensaje);
	}
	public List<String> getListaMensajesOk() {
		return listaMensajesOk;
	}
	public void setListaMensajesOk(List<String> listaMensajesOk) {
		this.listaMensajesOk = listaMensajesOk;
	}
	public List<String> getListaMensajesError() {
		return listaMensajesError;
	}
	public void setListaMensajesError(List<String> listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}
	
}
