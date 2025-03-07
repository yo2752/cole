package trafico.beans;

import java.io.Serializable;
import java.util.List;

public class ResumenEEFF implements Serializable{

	private static final long serialVersionUID = -849274506585085510L;
	
	private Boolean esLiberacion = Boolean.FALSE;
	private Boolean esConsulta = Boolean.FALSE;
	private Integer numOK = 0;
	private Integer numError = 0;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesError;
	
	public ResumenEEFF() {
		esLiberacion = Boolean.FALSE;
		esConsulta = Boolean.FALSE;
		numOK = 0;
		numError = 0;
	}
	
	public Boolean getEsLiberacion() {
		return esLiberacion;
	}
	public void setEsLiberacion(Boolean esLiberacion) {
		this.esLiberacion = esLiberacion;
	}
	public Boolean getEsConsulta() {
		return esConsulta;
	}
	public void setEsConsulta(Boolean esConsulta) {
		this.esConsulta = esConsulta;
	}
	public Integer getNumOK() {
		return numOK;
	}
	public void setNumOK(Integer numOK) {
		this.numOK = numOK;
	}
	public Integer getNumError() {
		return numError;
	}
	public void setNumError(Integer numError) {
		this.numError = numError;
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