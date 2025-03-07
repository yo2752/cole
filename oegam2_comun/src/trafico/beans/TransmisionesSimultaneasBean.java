package trafico.beans;

/**
 * Bean que almacena atributos que identifican transmisiones simultáneas para construir colecciones
 * @author santiago.cuenca
 *
 */
public class TransmisionesSimultaneasBean {

	/* Inicio definición de atributos */
	private String numColegiado;
	private String matricula;
	private String numTramites;
	/* Fin definición de atributos */
	
	/* Inicio Getters & Setters */
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNumTramites() {
		return numTramites;
	}
	public void setNumTramites(String numTramites) {
		this.numTramites = numTramites;
	}
	/* Fin Getters & Setters */
	
}
