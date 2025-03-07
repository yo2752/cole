package trafico.beans;

/**
 * Bean que almacena datos de la tabla CRITERIO_UTILIZACION.
 *
 */
public class CriterioUtilizacion {

	private String idCriterioUtilizacion;
	private String descripcion;

	public CriterioUtilizacion() {
	}

	public String getIdCriterioUtilizacion() {
		return idCriterioUtilizacion;
	}

	public void setIdCriterioUtilizacion(String idCriterioUtilizacion) {
		this.idCriterioUtilizacion = idCriterioUtilizacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}