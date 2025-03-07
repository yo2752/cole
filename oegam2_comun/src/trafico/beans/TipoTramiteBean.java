package trafico.beans;

import java.io.Serializable;

public class TipoTramiteBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6129169486667573185L;

	private String descripcion;
	private String tipoTramite;
	private Integer activo; 
	private String codigoAplicacion;
	
	
	public TipoTramiteBean(boolean inicializar) {
		
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}
	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	} 
	
	
	
	
	
}
