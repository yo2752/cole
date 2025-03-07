package org.gestoresmadrid.oegam2comun.licenciasCam.view.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenConsultaLicencias implements Serializable {

	private static final long serialVersionUID = 8592457234640130798L;

	private int numOk;
	private int numFallidos;
	private int totalFallidos;
	private int totalOk;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesErrores;

	public int getNumOk() {
		return numOk;
	}

	public void setNumOk(int numOk) {
		this.numOk = numOk;
	}

	public int getNumFallidos() {
		return numFallidos;
	}

	public void setNumFallidos(int numFallidos) {
		this.numFallidos = numFallidos;
	}

	public int getTotalFallidos() {
		return totalFallidos;
	}

	public void setTotalFallidos(int totalFallidos) {
		this.totalFallidos = totalFallidos;
	}

	public int getTotalOk() {
		return totalOk;
	}

	public void setTotalOk(int totalOk) {
		this.totalOk = totalOk;
	}

	public List<String> getListaMensajesErrores() {
		return listaMensajesErrores;
	}

	public void setListaMensajesErrores(List<String> listaMensajesErrores) {
		this.listaMensajesErrores = listaMensajesErrores;
	}

	public List<String> getListaMensajesOk() {
		return listaMensajesOk;
	}

	public void setListaMensajesOk(List<String> listaMensajesOk) {
		this.listaMensajesOk = listaMensajesOk;
	}

}
