package trafico.beans;

import java.io.Serializable;

public class ServicioTraficoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4992084826334735894L;

	private String descripcion; 
	private TipoTramiteBean tipoTramiteBean; 
	private String idServicio;
	

	public ServicioTraficoBean(boolean inicializar) {
		this();
		this.tipoTramiteBean = new TipoTramiteBean(inicializar); 
		
	}
	public ServicioTraficoBean() {
		super();
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public TipoTramiteBean getTipoTramiteBean() {
		return tipoTramiteBean;
	}
	public void setTipoTramiteBean(TipoTramiteBean tipoTramiteBean) {
		this.tipoTramiteBean = tipoTramiteBean;
	}
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	} 
	
	
	
	
	
	
	}
