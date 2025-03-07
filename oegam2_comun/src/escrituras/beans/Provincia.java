package escrituras.beans;

import java.io.Serializable;

public class Provincia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7676106228497805616L;

	private String idProvincia;
	private String nombre;
	private String jefaturaProvincial;
	
	
	public Provincia() {
		super();
	}

	


	public Provincia(String idProvincia, String nombre) {
		
		this.idProvincia = idProvincia;
		this.nombre = nombre;
	}


	
	
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}




	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}




	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public String imprimir() {
		String buffer ="";
		buffer= "idProvincia: "+idProvincia+
		",nombre: "+nombre;		
		return buffer.toString();
	}
}
