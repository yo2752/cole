package org.gestoresmadrid.oegam2comun.remesa.view.bean;

import java.io.Serializable;
import java.util.List;

public class ResumenRemesas implements Serializable{
	
	private static final long serialVersionUID = 8592457234640130798L;
	
	private int numOk600;
	private int numOk601;
	private int numFallidos600;
	private int numFallidos601;
	private int totalFallidos;
	private int totalOk;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesErrores;
	
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
	public int getNumFallidos600() {
		return numFallidos600;
	}
	public void setNumFallidos600(int numFallidos600) {
		this.numFallidos600 = numFallidos600;
	}
	public int getNumFallidos601() {
		return numFallidos601;
	}
	public void setNumFallidos601(int numFallidos601) {
		this.numFallidos601 = numFallidos601;
	}
	public List<String> getListaMensajesErrores() {
		return listaMensajesErrores;
	}
	public void setListaMensajesErrores(List<String> listaMensajesErrores) {
		this.listaMensajesErrores = listaMensajesErrores;
	}
	public int getNumOk600() {
		return numOk600;
	}
	public void setNumOk600(int numOk600) {
		this.numOk600 = numOk600;
	}
	public int getNumOk601() {
		return numOk601;
	}
	public void setNumOk601(int numOk601) {
		this.numOk601 = numOk601;
	}
	public List<String> getListaMensajesOk() {
		return listaMensajesOk;
	}
	public void setListaMensajesOk(List<String> listaMensajesOk) {
		this.listaMensajesOk = listaMensajesOk;
	}
	
}
