package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class DocPrmDstvFichaDto implements Serializable{

	private static final long serialVersionUID = -2891852742656790265L;

	private Long idDocPermDistItv;
	
	private String docIdPerm;
	
	private BigDecimal estado;
	
	private String tipo;
	
	private String tipoDistintivo;
	
	private String jefatura;
	
	private Long numDescarga;
	
	private Date fechaAlta;
	
	private Date fechaImpresion;
	
	private Date fechaModificacion;
	
	private UsuarioDto usuario;

	private ContratoDto contrato;
	
	private List<TramiteTrafDto> listaTramitesPermiso;
	
	private List<TramiteTrafMatrDto> listaTramitesDistintivo;
	
	private List<TramiteTrafDto> listaTramitesFichas;

	public Long getIdDocPermDistItv() {
		return idDocPermDistItv;
	}

	public void setIdDocPermDistItv(Long idDocPermDistItv) {
		this.idDocPermDistItv = idDocPermDistItv;
	}

	public String getDocIdPerm() {
		return docIdPerm;
	}

	public void setDocIdPerm(String docIdPerm) {
		this.docIdPerm = docIdPerm;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Long getNumDescarga() {
		return numDescarga;
	}

	public void setNumDescarga(Long numDescarga) {
		this.numDescarga = numDescarga;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public List<TramiteTrafDto> getListaTramitesPermiso() {
		return listaTramitesPermiso;
	}

	public void setListaTramitesPermiso(List<TramiteTrafDto> listaTramitesPermiso) {
		this.listaTramitesPermiso = listaTramitesPermiso;
	}

	public List<TramiteTrafMatrDto> getListaTramitesDistintivo() {
		return listaTramitesDistintivo;
	}

	public void setListaTramitesDistintivo(List<TramiteTrafMatrDto> listaTramitesDistintivo) {
		this.listaTramitesDistintivo = listaTramitesDistintivo;
	}

	public List<TramiteTrafDto> getListaTramitesFichas() {
		return listaTramitesFichas;
	}

	public void setListaTramitesFichas(List<TramiteTrafDto> listaTramitesFichas) {
		this.listaTramitesFichas = listaTramitesFichas;
	}
}
