package escrituras.beans.dao;

public class JefaturaDao {
	
	private String jefatura_provincial;
	private String jefatura;
	private String sucursal;
	private String descripcion;
	private String id_provincia;

	public JefaturaDao() {

	}

	public String getJefatura_provincial() {
		return jefatura_provincial;
	}

	public void setJefatura_provincial(String jefaturaProvincial) {
		jefatura_provincial = jefaturaProvincial;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(String idProvincia) {
		id_provincia = idProvincia;
	}

	
}
