package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PactoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8404933715419654529L;

	private long idPacto;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private String pactado;

	private String tipoPacto;

	private BigDecimal idTramiteRegistro;

	public long getIdPacto() {
		return idPacto;
	}

	public void setIdPacto(long idPacto) {
		this.idPacto = idPacto;
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

	public String getPactado() {
		return pactado;
	}

	public void setPactado(String pactado) {
		this.pactado = pactado;
	}

	public String getTipoPacto() {
		return tipoPacto;
	}

	public void setTipoPacto(String tipoPacto) {
		this.tipoPacto = tipoPacto;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}
	
}
