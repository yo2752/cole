package trafico.beans;

import escrituras.beans.Provincia;

public class JefaturaTrafico {
	
	
	private String jefaturaProvincial; //jefatura provincial de matriculacion incluida la sucursal 
	private String jefatura; 
	private String sucursal; 
	private String descripcion; 
	private Provincia provincia;
	
	//CONSTRUCTORES
	public JefaturaTrafico() {
		
	}
	//ESTE CONSTRUCTOR TIENE QUE ESTAR PRESENTE PARA QUE FUNCIONE BIEN STRUTS
	// siempre y cuando esta clase (JefaturaTrafico) contenga algún atributo complejo (Provincia).
	public JefaturaTrafico(boolean inicio) {
		provincia = new Provincia();
	}
	
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}
	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	
	
	
	
	
	
	
}
