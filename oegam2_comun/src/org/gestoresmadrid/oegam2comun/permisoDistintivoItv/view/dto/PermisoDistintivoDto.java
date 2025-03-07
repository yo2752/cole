package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class PermisoDistintivoDto implements Serializable{
	
	private static final long serialVersionUID = 4746950490572840040L;

	private Date fechaAlta;
	
	private BigDecimal numExpediente;
	
	private BigDecimal estado;
	
	private Boolean permiso;
	
	private Boolean distintivo;
	
	private String numSeriePermiso;
	
	private String tipoDistintivo;
	
	private Long docIdImpMasiva;
	
	private Long docId;
	
	private Date fechaImpresionDistintivo;
	
	private TramiteTrafMatrDto tramiteTrafMatr;
	
	private UsuarioDto usuario;

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Boolean getPermiso() {
		return permiso;
	}

	public void setPermiso(Boolean permiso) {
		this.permiso = permiso;
	}

	public String getNumSeriePermiso() {
		return numSeriePermiso;
	}

	public void setNumSeriePermiso(String numSeriePermiso) {
		this.numSeriePermiso = numSeriePermiso;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public TramiteTrafMatrDto getTramiteTrafMatr() {
		return tramiteTrafMatr;
	}

	public void setTramiteTrafMatr(TramiteTrafMatrDto tramiteTrafMatr) {
		this.tramiteTrafMatr = tramiteTrafMatr;
	}

	public Boolean getDistintivo() {
		return distintivo;
	}

	public void setDistintivo(Boolean distintivo) {
		this.distintivo = distintivo;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public Long getDocIdImpMasiva() {
		return docIdImpMasiva;
	}

	public void setDocIdImpMasiva(Long docIdImpMasiva) {
		this.docIdImpMasiva = docIdImpMasiva;
	}

	public Date getFechaImpresionDistintivo() {
		return fechaImpresionDistintivo;
	}

	public void setFechaImpresionDistintivo(Date fechaImpresionDistintivo) {
		this.fechaImpresionDistintivo = fechaImpresionDistintivo;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}


	
	

}
