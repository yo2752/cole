package trafico.beans.daos;

public class JefaturaTraficoDao {

	private String jefatura_provincial;
	private String jefatura;
	private String sucusal;
	private String descripcion;
	private String id_provincia;

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
	public String getSucusal() {
		return sucusal;
	}
	public void setSucusal(String sucusal) {
		this.sucusal = sucusal;
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