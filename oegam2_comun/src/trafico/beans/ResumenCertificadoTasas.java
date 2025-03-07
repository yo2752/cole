package trafico.beans;

import java.util.List;

public class ResumenCertificadoTasas {
	
	private int numTasasCertificado = 0;
	private int numFallidos = 0;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesError;
	
	public ResumenCertificadoTasas() {

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

	public int getNumTasasCertificado() {
		return numTasasCertificado;
	}

	public void setNumTasasCertificado(int numTasasCertificado) {
		this.numTasasCertificado = numTasasCertificado;
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