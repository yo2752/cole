package trafico.beans;

import java.io.Serializable;
import java.util.List;

public class ResumenFicheroSolicitud05 implements Serializable{
	
	private static final long serialVersionUID = 5419327013233083542L;
	
	private int numOk = 0;
	private int numFallidos = 0;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesError;
	private Boolean existeFichero;
	private String nombreFichero;
	
	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public ResumenFicheroSolicitud05() {

	}
	
	public Boolean getExisteFichero() {
		return existeFichero;
	}

	public void setExisteFichero(Boolean existeFichero) {
		this.existeFichero = existeFichero;
	}

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

	
	public int addFallido() {
		this.numFallidos++;
		return this.numFallidos;
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