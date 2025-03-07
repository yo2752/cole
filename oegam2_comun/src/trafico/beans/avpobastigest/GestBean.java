package trafico.beans.avpobastigest;

import java.util.List;

/**
 * Contiene la información de la respuesta del GEST para obtener la matrícula.
 * @author TB-solutions
 *
 */
public class GestBean {

	//Campos que indican si la consulta ha ido bien o mal
	/**
	 * Indica si se ha producido algún error en el proceso de consulta de cargos
	 */
	private Boolean error;
	
	/**
	 * Mensaje obtenido de la consulta de cargos. 
	 */
	private String mensaje;
	private String matricula;
	
	private List<CargaGestBean> cargas;
	private List<EmbargoGestBean> embargos;
	
	private boolean desactivadaConsulta;
	
	public GestBean() {
		
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
	
	public List<CargaGestBean> getCargas() {
		return cargas;
	}

	public void setCargas(
			List<CargaGestBean> cargas) {
		this.cargas = cargas;
	}

	public List<EmbargoGestBean> getEmbargos() {
		return embargos;
	}

	public void setEmbargos(List<EmbargoGestBean> embargos) {
		this.embargos = embargos;
	}

	@Override
	public String toString() {
		StringBuffer cadena = new StringBuffer();
		
		cadena.append("Respuesta GEST:\n");
		cadena.append("\tError: " + getError() + "\n");
		cadena.append("\tMensaje: " + getMensaje() + "\n");
		
		if(cargas!=null && cargas.size()>0){
			for(CargaGestBean bean: cargas){
				cadena.append("\tCargas:\n");
				cadena.append("\t\tFechas: " + bean.getFecha() + "\n");
				cadena.append("\t\tTipo: " + bean.getTipo() + "\n");
				cadena.append("\t\tNumRegistro: " + bean.getNumRegistro() + "\n");
				cadena.append("\t\tFinanciera y domicilio: " + bean.getFinancieraYDomicilio() + "\n");
			}
		}
		
		if(embargos!=null && embargos.size()>0){
			for(EmbargoGestBean bean: embargos){
				cadena.append("\tEmpargos:\n");
				cadena.append("\t\tConcepto: " + bean.getConcepto() + "\n");
				cadena.append("\t\tExpediente: " + bean.getExpediente() + "\n");
				cadena.append("\t\tFecha: " + bean.getFecha() + "\n");
				cadena.append("\t\tFMateri: " + bean.getFmateri() + "\n");
				cadena.append("\t\tAutoridad: " + bean.getAutoridad() + "\n");
			}
		}
		
		return cadena.toString();
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean isDesactivadaConsulta() {
		return desactivadaConsulta;
	}

	public void setDesactivadaConsulta(boolean desactivadaConsulta) {
		this.desactivadaConsulta = desactivadaConsulta;
	}
	
	
}
