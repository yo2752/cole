package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConsultaPermisoDistintivoBean implements Serializable{

	private static final long serialVersionUID = 6499006710785116880L;
	
	private BigDecimal numExpediente;
	private String permiso;
	private String nSeriePermiso;
	private String distintivo;
	private String tipoDistintivo;
	private String descContrato;
	private String eitv;
	private String estadoPetImp;
	private String estadoPermDstv; 
	
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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
	public String getDistintivo() {
		return distintivo;
	}
	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}
	public String getTipoDistintivo() {
		return tipoDistintivo;
	}
	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}
	public String getDescContrato() {
		return descContrato;
	}
	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}
	public String getEstadoPermDstv() {
		return estadoPermDstv;
	}
	public void setEstadoPermDstv(String estadoPermDstv) {
		this.estadoPermDstv = estadoPermDstv;
	}
	public String getEitv() {
		return eitv;
	}
	public void setEitv(String eitv) {
		this.eitv = eitv;
	}
	public String getEstadoPetImp() {
		return estadoPetImp;
	}
	public void setEstadoPetImp(String estadoPetImp) {
		this.estadoPetImp = estadoPetImp;
	}
	
}
