package escrituras.beans.dao;

public class ColegioDao {
	private String colegio;
	private String nombre;
	private String cif;
	private String correo_electronico;
	
	public ColegioDao() {
	}

	public String getColegio() {
		return colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correoElectronico) {
		correo_electronico = correoElectronico;
	}
	
	
}
