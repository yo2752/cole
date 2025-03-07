package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ClausulaDto implements Serializable {

	private static final long serialVersionUID = -5855050914960926656L;

	private long idClausula;

	private String descripcion;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private String nombre;

	private BigDecimal numero;

	private String tipoClausula;

	private BigDecimal idTramiteRegistro;

	public long getIdClausula() {
		return idClausula;
	}

	public void setIdClausula(long idClausula) {
		this.idClausula = idClausula;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getNumero() {
		return numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public String getTipoClausula() {
		return tipoClausula;
	}

	public void setTipoClausula(String tipoClausula) {
		this.tipoClausula = tipoClausula;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}
}
