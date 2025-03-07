package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class GestionPermisosFichasDgtBean implements Serializable{

	private static final long serialVersionUID = 3338423703042262521L;
	
	private BigDecimal numExpediente;
	private String matricula;
	private String permiso;
	private String nSeriePermiso;
	private String descContrato;
	private String fichaTecnica;
	private String estadoPetImp;
	private String estadoSolicitud;
	private String estadoTramite;
	private String numImpresiones;
	private String tipoTramite;
	private String docId;
	private String nif;
	private String docPermiso;
	private String docFicha;
	private String tipoTransferencia;
	private String jefaturaTrafico;
	private String nive;
	private Short conNive;
	private String bastidor;
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getPermiso() {
		return permiso;
	}
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
	public String getnSeriePermiso() {
		return nSeriePermiso;
	}
	public void setnSeriePermiso(String nSeriePermiso) {
		this.nSeriePermiso = nSeriePermiso;
	}
	public String getDescContrato() {
		return descContrato;
	}
	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}
	public String getFichaTecnica() {
		return fichaTecnica;
	}
	public void setFichaTecnica(String fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}
	public String getEstadoPetImp() {
		return estadoPetImp;
	}
	public void setEstadoPetImp(String estadoPetImp) {
		this.estadoPetImp = estadoPetImp;
	}
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}
	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getNumImpresiones() {
		return numImpresiones;
	}
	public void setNumImpresiones(String numImpresiones) {
		this.numImpresiones = numImpresiones;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getDocPermiso() {
		return docPermiso;
	}
	public void setDocPermiso(String docPermiso) {
		this.docPermiso = docPermiso;
	}
	public String getDocFicha() {
		return docFicha;
	}
	public void setDocFicha(String docFicha) {
		this.docFicha = docFicha;
	}
	public String getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	public String getTipoTransferencia() {
		return tipoTransferencia;
	}
	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}
	public String getJefaturaTrafico() {
		return jefaturaTrafico;
	}
	public void setJefaturaTrafico(String jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}
	public Short getConNive() {
		return conNive;
	}
	public void setConNive(Short conNive) {
		this.conNive = conNive;
	}
	public String getNive() {
		return nive;
	}
	public void setNive(String nive) {
		this.nive = nive;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	
	
}
