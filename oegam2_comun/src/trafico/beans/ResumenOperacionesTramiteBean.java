package trafico.beans;

import java.io.Serializable;
import java.util.List;

public class ResumenOperacionesTramiteBean implements Serializable{

	private static final long serialVersionUID = 1394512382458426305L;
	
	private Integer numOks;
	private Integer numErroneos;
	private List<String> listaOks;
	private List<String> listaErroneos;
	
	public int getNumOks() {
		return numOks;
	}
	public void setNumOks(int numOks) {
		this.numOks = numOks;
	}
	public int getNumErroneos() {
		return numErroneos;
	}
	public void setNumErroneos(int numErroneos) {
		this.numErroneos = numErroneos;
	}
	public List<String> getListaOks() {
		return listaOks;
	}
	public void setListaOks(List<String> listaOks) {
		this.listaOks = listaOks;
	}
	public List<String> getListaErroneos() {
		return listaErroneos;
	}
	public void setListaErroneos(List<String> listaErroneos) {
		this.listaErroneos = listaErroneos;
	}
	
}
