package trafico.beans;

/**
 * Bean que almacena datos de la tabla CRITERIO_CONSTRUCCION.
 *
 */
public class CriterioConstruccion {

	private String idCriterioConstruccion;
	private String descripcion;

	public CriterioConstruccion() {
	}

	public String getIdCriterioConstruccion() {
		return idCriterioConstruccion;
	}

	public void setIdCriterioConstruccion(String idCriterioConstruccion) {
		this.idCriterioConstruccion = idCriterioConstruccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}