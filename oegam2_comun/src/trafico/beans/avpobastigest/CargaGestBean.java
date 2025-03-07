package trafico.beans.avpobastigest;


/**
 * Contiene la información de la respuesta del GEST para obtener los cargos.
 * @author TB-solutions
 *
 */
public class CargaGestBean {

	//Campos a rellenar en la presentación
	private String fecha;
	private String tipo;
	private String numRegistro;
	private String financieraYDomicilio;
	
	public CargaGestBean() {
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFinancieraYDomicilio() {
		return financieraYDomicilio;
	}

	public void setFinancieraYDomicilio(String financieraYDomicilio) {
		this.financieraYDomicilio = financieraYDomicilio;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
