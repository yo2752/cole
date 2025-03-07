package escrituras.beans;

/**
 * Bean que almacena datos de la tabla MUNICIPIO.
 *
 */
public class MunicipioBean {

	private String idMunicipio;
	 private String idProvincia; 
	private String nombre;
	
	public MunicipioBean() {
		super();
	}
	
	
	

	public String getIdMunicipio() {
		return idMunicipio;
	}



	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
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

}
