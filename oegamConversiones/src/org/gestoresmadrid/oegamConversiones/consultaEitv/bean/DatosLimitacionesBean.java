package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosLimitacionesBean implements Serializable {

	private static final long serialVersionUID = 3729750442377784791L;
	
	private String tipolimitacion;
	private String fechalimitacion;
	private String numeroregistro;
	private String financiera;
	private String fechaalta;
	
	public DatosLimitacionesBean() {
		super();
	}

	public String getTipolimitacion() {
		return tipolimitacion;
	}

	public void setTipolimitacion(String tipolimitacion) {
		this.tipolimitacion = tipolimitacion;
	}

	public String getFechalimitacion() {
		return fechalimitacion;
	}

	public void setFechalimitacion(String fechalimitacion) {
		this.fechalimitacion = fechalimitacion;
	}

	public String getNumeroregistro() {
		return numeroregistro;
	}

	public void setNumeroregistro(String numeroregistro) {
		this.numeroregistro = numeroregistro;
	}

	public String getFinanciera() {
		return financiera;
	}

	public void setFinanciera(String financiera) {
		this.financiera = financiera;
	}

	public String getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(String fechaalta) {
		this.fechaalta = fechaalta;
	}

}
