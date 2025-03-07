package org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto;

import java.util.Date;

public class FicherosTemporalesDto {
	
	private Long idFicheroTemporal;
	
	private String nombre;
	
	private String extension;
	
	private Date fecha;
	
	private String tipoDocumento;
	
	private String subTipoDocumento;
	
	private String numColegiado;
	
	private Long idUsuario;
	
	private Long idContrato;
	
	private Integer Estado;

	public Long getIdFicheroTemporal() {
		return idFicheroTemporal;
	}

	public void setIdFicheroTemporal(Long idFicheroTemporal) {
		this.idFicheroTemporal = idFicheroTemporal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getSubTipoDocumento() {
		return subTipoDocumento;
	}

	public void setSubTipoDocumento(String subTipoDocumento) {
		this.subTipoDocumento = subTipoDocumento;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getEstado() {
		return Estado;
	}

	public void setEstado(Integer estado) {
		Estado = estado;
	}
	
}
