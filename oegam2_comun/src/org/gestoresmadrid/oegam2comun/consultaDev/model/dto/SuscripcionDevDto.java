package org.gestoresmadrid.oegam2comun.consultaDev.model.dto;

import java.io.Serializable;
import java.util.Date;

public class SuscripcionDevDto implements Serializable{

	private static final long serialVersionUID = -5358572727227031065L;

	private Long idSuscripcionDev;
	
	private Long idConsultaDev;
	
	private String codProcedimiento;
	
	private String descProcedimiento;
	
	private String emisor;
	
	private Date fechaSuscripcion;
	
	private String codEstado;
	
	private String datosPersonales;
	
	private ConsultaDevDto consultaDevDto;

	public Long getIdSuscripcionDev() {
		return idSuscripcionDev;
	}

	public void setIdSuscripcionDev(Long idSuscripcionDev) {
		this.idSuscripcionDev = idSuscripcionDev;
	}

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

	public String getCodProcedimiento() {
		return codProcedimiento;
	}

	public void setCodProcedimiento(String codProcedimiento) {
		this.codProcedimiento = codProcedimiento;
	}

	public String getDescProcedimiento() {
		return descProcedimiento;
	}

	public void setDescProcedimiento(String descProcedimiento) {
		this.descProcedimiento = descProcedimiento;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public Date getFechaSuscripcion() {
		return fechaSuscripcion;
	}

	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public ConsultaDevDto getConsultaDevDto() {
		return consultaDevDto;
	}

	public void setConsultaDevDto(ConsultaDevDto consultaDevDto) {
		this.consultaDevDto = consultaDevDto;
	}

	public String getDatosPersonales() {
		return datosPersonales;
	}

	public void setDatosPersonales(String datosPersonales) {
		this.datosPersonales = datosPersonales;
	}
	
}
