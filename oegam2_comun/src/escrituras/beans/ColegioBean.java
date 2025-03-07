package escrituras.beans;

/**
 * Bean que almacena datos de la tabla COLEGIO.
 *
 */
public class ColegioBean {

	private String colegio;
	private String nombre;
	private String cif; 
	private String correoElectronico;
	
	public ColegioBean() {
		super();
	}
  
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getColegio() {
		return colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public String getCorreoElectronico() {
		return correoElectronico;
	}


	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	
	

	
}
