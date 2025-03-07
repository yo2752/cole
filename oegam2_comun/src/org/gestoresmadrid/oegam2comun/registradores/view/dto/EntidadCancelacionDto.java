package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class EntidadCancelacionDto implements Serializable {

	private static final long serialVersionUID = 890999999453750342L;

	private long idEntidad;

	private String codigoIdentificacionFiscal;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private String numeroImpreso;

	private String razonSocial;

	private BigDecimal idContrato;

	private DatRegMercantilDto datRegMercantil;

	public long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(long idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getCodigoIdentificacionFiscal() {
		return codigoIdentificacionFiscal;
	}

	public void setCodigoIdentificacionFiscal(String codigoIdentificacionFiscal) {
		this.codigoIdentificacionFiscal = codigoIdentificacionFiscal;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getNumeroImpreso() {
		return numeroImpreso;
	}

	public void setNumeroImpreso(String numeroImpreso) {
		this.numeroImpreso = numeroImpreso;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public DatRegMercantilDto getDatRegMercantil() {
		return datRegMercantil;
	}

	public void setDatRegMercantil(DatRegMercantilDto datRegMercantil) {
		this.datRegMercantil = datRegMercantil;
	}

	/**
	 * @return the idContrato
	 */
	public BigDecimal getIdContrato() {
		return idContrato;
	}

	/**
	 * @param idContrato the idContrato to set
	 */
	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}
}
