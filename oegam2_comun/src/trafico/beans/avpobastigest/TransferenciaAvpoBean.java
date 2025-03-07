package trafico.beans.avpobastigest;


/**
 * Contiene la información de la respuesta del AVPO con los datos de una transferencia.
 *
 */
public class TransferenciaAvpoBean {

	//Campos a rellenar en la presentación
	private String fecha;
	private String jefatura;
	private String sucursal;
	private String titularAnterior;
	
	public TransferenciaAvpoBean() {
		
	}
	
	public void init(){
		//
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getTitularAnterior() {
		return titularAnterior;
	}

	public void setTitularAnterior(String titularAnterior) {
		this.titularAnterior = titularAnterior;
	}
	
	
	
}
