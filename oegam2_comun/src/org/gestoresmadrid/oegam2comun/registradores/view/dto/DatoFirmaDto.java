package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;

import utilidades.estructuras.Fecha;

public class DatoFirmaDto implements Serializable{

	private static final long serialVersionUID = -7513843340548019483L;
	
	private long idDatoFirma;

	private String consumidor;

	private String derechoDes;

	private Timestamp fecCreacion;

	private Date fecFirma;
	
	private Fecha fecFirmaDatFirma;

	private Timestamp fecModificacion;

	private String informado;

	private String lugar;

	private String tipoFirma;

	private String tipoIntervencion;

	private BigDecimal idTramiteRegistro;
	
	private DireccionDto direccion;

	public long getIdDatoFirma() {
		return idDatoFirma;
	}

	public void setIdDatoFirma(long idDatoFirma) {
		this.idDatoFirma = idDatoFirma;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(String consumidor) {
		this.consumidor = consumidor;
	}

	public String getDerechoDes() {
		return derechoDes;
	}

	public void setDerechoDes(String derechoDes) {
		this.derechoDes = derechoDes;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecFirma() {
		return fecFirma;
	}

	public void setFecFirma(Date fecFirma) {
		this.fecFirma = fecFirma;
	}

	public Fecha getFecFirmaDatFirma() {
		return fecFirmaDatFirma;
	}

	public void setFecFirmaDatFirma(Fecha fecFirmaDatFirma) {
		this.fecFirmaDatFirma = fecFirmaDatFirma;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getInformado() {
		return informado;
	}

	public void setInformado(String informado) {
		this.informado = informado;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getTipoIntervencion() {
		return tipoIntervencion;
	}

	public void setTipoIntervencion(String tipoIntervencion) {
		this.tipoIntervencion = tipoIntervencion;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public DireccionDto getDireccion() {
		if (direccion==null)
			direccion= new DireccionDto();
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

}
