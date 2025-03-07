package org.gestoresmadrid.oegam2comun.transporte.view.dto;

import java.io.Serializable;
import java.util.Date;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class NotificacionTransporteDto implements Serializable{

	private static final long serialVersionUID = 5431437472892010973L;
	
	private Long idNotificacionTransporte;
	
	private String estado;
	
	private Date fechaAlta;
	
	private Date fechaModificacion;
	
	private String nifEmpresa;
	
	private String nombreEmpresa;
	
	private ContratoDto contrato;
	
	private UsuarioDto usuario;
	
	private MunicipioDto municipioContrato;
	
	private String nombrePdf;

	public Long getIdNotificacionTransporte() {
		return idNotificacionTransporte;
	}

	public void setIdNotificacionTransporte(Long idNotificacionTransporte) {
		this.idNotificacionTransporte = idNotificacionTransporte;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNifEmpresa() {
		return nifEmpresa;
	}

	public void setNifEmpresa(String nifEmpresa) {
		this.nifEmpresa = nifEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public MunicipioDto getMunicipioContrato() {
		return municipioContrato;
	}

	public void setMunicipioContrato(MunicipioDto municipioContrato) {
		this.municipioContrato = municipioContrato;
	}

	public String getNombrePdf() {
		return nombrePdf;
	}

	public void setNombrePdf(String nombrePdf) {
		this.nombrePdf = nombrePdf;
	}

}
