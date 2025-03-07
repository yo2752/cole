package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.distintivo.view.dto.VehiculoNoMatriculadoOegamDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class FacturacionDistintivoDto implements Serializable{

	private static final long serialVersionUID = -8634479224351170853L;
	
	private Long idDistintivoFacturado;
	
	private Long idUsuario;
	
	private Long idUsuarioIncidencia;
	
	private Long idContrato;
	
	private String estado;
	
	private String docId;
	
	private Long idDocPermDistItv;
	
	private Long idDuplicadoDstv;
	
	private BigDecimal numExpediente;
	
	private String motivoIncidencia;
	
	private Date fecha;
	
	private UsuarioDto usuario;
	
	private UsuarioDto usuarioIncidencia;
	
	private ContratoDto contrato;
	
	private TramiteTrafMatrDto tramiteTraficoMatr;
	
	private VehiculoNoMatriculadoOegamDto duplicadoDistintivo;

	public Long getIdDistintivoFacturado() {
		return idDistintivoFacturado;
	}

	public void setIdDistintivoFacturado(Long idDistintivoFacturado) {
		this.idDistintivoFacturado = idDistintivoFacturado;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdUsuarioIncidencia() {
		return idUsuarioIncidencia;
	}

	public void setIdUsuarioIncidencia(Long idUsuarioIncidencia) {
		this.idUsuarioIncidencia = idUsuarioIncidencia;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Long getIdDocPermDistItv() {
		return idDocPermDistItv;
	}

	public void setIdDocPermDistItv(Long idDocPermDistItv) {
		this.idDocPermDistItv = idDocPermDistItv;
	}

	public Long getIdDuplicadoDstv() {
		return idDuplicadoDstv;
	}

	public void setIdDuplicadoDstv(Long idDuplicadoDstv) {
		this.idDuplicadoDstv = idDuplicadoDstv;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getMotivoIncidencia() {
		return motivoIncidencia;
	}

	public void setMotivoIncidencia(String motivoIncidencia) {
		this.motivoIncidencia = motivoIncidencia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public UsuarioDto getUsuarioIncidencia() {
		return usuarioIncidencia;
	}

	public void setUsuarioIncidencia(UsuarioDto usuarioIncidencia) {
		this.usuarioIncidencia = usuarioIncidencia;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public TramiteTrafMatrDto getTramiteTraficoMatr() {
		return tramiteTraficoMatr;
	}

	public void setTramiteTraficoMatr(TramiteTrafMatrDto tramiteTraficoMatr) {
		this.tramiteTraficoMatr = tramiteTraficoMatr;
	}

	public VehiculoNoMatriculadoOegamDto getDuplicadoDistintivo() {
		return duplicadoDistintivo;
	}

	public void setDuplicadoDistintivo(VehiculoNoMatriculadoOegamDto duplicadoDistintivo) {
		this.duplicadoDistintivo = duplicadoDistintivo;
	}

}
