package escrituras.beans.dao;


/**
 * Bean que almacena datos de la tabla MUNICIPIO.
 *
 */
public class MunicipioDao {

	private String id_Municipio;
	private String id_Provincia;
	private String codigo_postal;
	private String nombre;
	
	public MunicipioDao() {
		
	}

	public String getId_Municipio() {
		return id_Municipio;
	}

	public void setId_Municipio(String idMunicipio) {
		id_Municipio = idMunicipio;
	}

	public String getId_Provincia() {
		return id_Provincia;
	}




	public void setId_Provincia(String idProvincia) {
		id_Provincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigoPostal) {
		codigo_postal = codigoPostal;
	}
	
	

}
