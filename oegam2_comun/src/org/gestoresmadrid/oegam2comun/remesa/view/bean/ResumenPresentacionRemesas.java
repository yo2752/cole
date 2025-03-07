package org.gestoresmadrid.oegam2comun.remesa.view.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenPresentacionRemesas implements Serializable{

	private static final long serialVersionUID = -2457700073965337642L;
	
	private int numOk;
	private int numErrores;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesErrores;
	
	public int getNumOk() {
		return numOk;
	}
	public void setNumOk(int numOk) {
		this.numOk = numOk;
	}
	public int getNumErrores() {
		return numErrores;
	}
	public void setNumErrores(int numErrores) {
		this.numErrores = numErrores;
	}
	public List<String> getListaMensajesOk() {
		return listaMensajesOk;
	}
	public void setListaMensajesOk(List<String> listaMensajesOk) {
		this.listaMensajesOk = listaMensajesOk;
	}
	public List<String> getListaMensajesErrores() {
		return listaMensajesErrores;
	}
	public void setListaMensajesErrores(List<String> listaMensajesErrores) {
		this.listaMensajesErrores = listaMensajesErrores;
	}
	
}
