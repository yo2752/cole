package escrituras.beans;

import java.io.Serializable;

/**
 * Bean que almacena datos de la tabla MUNICIPIO.
 *
 */
public class Municipio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5443344897473637653L;

	private String idMunicipio;
	private Provincia provincia;
	private String nombre;
	
	public Municipio() {
		super();
	}
	
	public Municipio(boolean inicializar) {
		this();
		if (inicializar){
			provincia = new Provincia();
		}
	}
	

	public String getIdMunicipio() {
		return idMunicipio;
	}



	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}


	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String imprimir() {
		String buffer ="";
		
		if (provincia == null){
			setProvincia(new Provincia());
		}
		buffer= "idMunicipio: "+idMunicipio+
		",provincia: ["+provincia.imprimir()+"]"+
		",nombre: "+nombre;		
		return buffer.toString();
	}
}
