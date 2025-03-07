package trafico.beans.avpobastigest;


/**
 * Contiene la información de la respuesta del BSTI para obtener la matrícula.
 * @author TB-solutions
 *
 */
public class BstiBean {

	//Campos a rellenar en la presentación
	/**
	 * Matrícula correspondiente al Número de Bastidor solicitado
	 */
	private String matricula;
	
	//Campos que indican si la presentación ha ido bien o mal
	/**
	 * Indica si se ha producido algún error en el proceso de consulta de la matrícula
	 */
	private Boolean error;
	
	/**
	 * Mensaje que obtenido en el posible error producido durante la consulta de la matrícula. 
	 */
	private String mensaje;
	
	/**
	 * Id del contador de uso por si hay que eliminarlo. 
	 */
	private Long idContadorUso;
	
	/**
	 * Indica si la invocación está desactivada
	 */
	private boolean desactivadaConsulta;
	
	public BstiBean() {

	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Long getIdContadorUso() {
		return idContadorUso;
	}

	public void setIdContadorUso(Long idContadorUso) {
		this.idContadorUso = idContadorUso;
	}

	public boolean isDesactivadaConsulta() {
		return desactivadaConsulta;
	}

	public void setDesactivadaConsulta(boolean desactivadaConsulta) {
		this.desactivadaConsulta = desactivadaConsulta;
	}
	
	

}
