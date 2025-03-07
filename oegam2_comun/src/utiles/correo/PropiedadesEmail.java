package utiles.correo;


public class PropiedadesEmail {
	
	String remitente;
	String para;
	String copia;
	Boolean textoPlano;
	String asunto;
	String cuerpo;

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getPara() {
		return para;
	}
	
	public void setPara(String para) {
		this.para = para;
	}
	
	public String getCopia() {
		return copia;
	}
	
	public void setCopia(String copia) {
		this.copia = copia;
	}
	
	public String getAsunto() {
		return asunto;
	}
	
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Boolean getTextoPlano() {
		return textoPlano;
	}

	public void setTextoPlano(Boolean textoPlano) {
		this.textoPlano = textoPlano;
	}

}
