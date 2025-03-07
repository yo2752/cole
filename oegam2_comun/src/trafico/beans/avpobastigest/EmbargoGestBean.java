package trafico.beans.avpobastigest;


/**
 * Contiene la información de la respuesta del GEST para obtener los embargos.
 * @author TB-solutions
 *
 */
public class EmbargoGestBean {

	//Campos a rellenar en la presentación
	private String concepto;
	private String expediente;
	private String fecha;
	private String fmateri;
	private String autoridad;
	
	public EmbargoGestBean() {
	
	}
	
	public String getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(String autoridad) {
		this.autoridad = autoridad;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFmateri() {
		return fmateri;
	}

	public void setFmateri(String fmateri) {
		this.fmateri = fmateri;
	}

}
