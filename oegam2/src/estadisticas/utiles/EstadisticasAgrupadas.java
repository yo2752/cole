package estadisticas.utiles;

/**
 * Bean que almacena datos de la tabla ESCRITURA
 */

public class EstadisticasAgrupadas {

	private String campo;
	private Integer numRegistros;
	private String fechaMatriculacion;

	public EstadisticasAgrupadas() {
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	public String getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(String FechaMatriculacion) {
		this.fechaMatriculacion = FechaMatriculacion;
	}
}