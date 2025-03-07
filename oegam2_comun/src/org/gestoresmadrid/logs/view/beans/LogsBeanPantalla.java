package org.gestoresmadrid.logs.view.beans;

import org.gestoresmadrid.logs.enumerados.TipoLogFrontalEnum;
import org.gestoresmadrid.logs.enumerados.TipoLogProcesoEnum;

import utilidades.estructuras.FechaFraccionada;

public class LogsBeanPantalla {

	FechaFraccionada fechaLog;
	String maquina;
	String tipoLog;
	String ficheros;
	String maquinasEnum;
	TipoLogFrontalEnum tipoLogFrontal;
	TipoLogProcesoEnum tipoLogProceso;

	public FechaFraccionada getFechaLog() {
		return fechaLog;
	}
	public void setFechaLog(FechaFraccionada fechaLog) {
		this.fechaLog = fechaLog;
	}
	public String getMaquina() {
		return maquina;
	}
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}
	public String getTipoLog() {
		return tipoLog;
	}
	public void setTipoLog(String tipoLog) {
		this.tipoLog = tipoLog;
	}
	public TipoLogFrontalEnum getTipoLogFrontal() {
		return tipoLogFrontal;
	}
	public void setTipoLogFrontal(TipoLogFrontalEnum tipoLogFrontal) {
		this.tipoLogFrontal = tipoLogFrontal;
	}
	public TipoLogProcesoEnum getTipoLogProceso() {
		return tipoLogProceso;
	}
	public void setTipoLogProceso(TipoLogProcesoEnum tipoLogProceso) {
		this.tipoLogProceso = tipoLogProceso;
	}
	public String getFicheros() {
		return ficheros;
	}
	public void setFicheros(String ficheros) {
		this.ficheros = ficheros;
	}
	public String getMaquinasEnum() {
		return maquinasEnum;
	}
	public void setMaquinasEnum(String maquinasEnum) {
		this.maquinasEnum = maquinasEnum;
	}

}