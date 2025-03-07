package trafico.beans.avpobastigest;


/**
 * Contiene la informaci�n de la respuesta del BSTI para obtener la matr�cula.
 * @author TB-solutions
 *
 */
public class BstiBean {

	//Campos a rellenar en la presentaci�n
	/**
	 * Matr�cula correspondiente al N�mero de Bastidor solicitado
	 */
	private String matricula;
	
	//Campos que indican si la presentaci�n ha ido bien o mal
	/**
	 * Indica si se ha producido alg�n error en el proceso de consulta de la matr�cula
	 */
	private Boolean error;
	
	/**
	 * Mensaje que obtenido en el posible error producido durante la consulta de la matr�cula. 
	 */
	private String mensaje;
	
	/**
	 * Id del contador de uso por si hay que eliminarlo. 
	 */
	private Long idContadorUso;
	
	/**
	 * Indica si la invocaci�n est� desactivada
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
