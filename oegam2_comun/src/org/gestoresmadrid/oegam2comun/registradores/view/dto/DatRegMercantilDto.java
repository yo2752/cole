package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DatRegMercantilDto implements Serializable {

	private static final long serialVersionUID = 3076536116408734978L;
	
	private long idDatRegMercantil;

	private String codRegistroMercantil;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private BigDecimal folio;

	private String hoja;

	private BigDecimal libro;

	private String numInscripcion;

	private BigDecimal tomo;

	public long getIdDatRegMercantil() {
		return idDatRegMercantil;
	}

	public void setIdDatRegMercantil(long idDatRegMercantil) {
		this.idDatRegMercantil = idDatRegMercantil;
	}

	public String getCodRegistroMercantil() {
		return codRegistroMercantil;
	}

	public void setCodRegistroMercantil(String codRegistroMercantil) {
		this.codRegistroMercantil = codRegistroMercantil;
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

	public BigDecimal getFolio() {
		return folio;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

	public String getHoja() {
		return hoja;
	}

	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	public BigDecimal getLibro() {
		return libro;
	}

	public void setLibro(BigDecimal libro) {
		this.libro = libro;
	}

	public String getNumInscripcion() {
		return numInscripcion;
	}

	public void setNumInscripcion(String numInscripcion) {
		this.numInscripcion = numInscripcion;
	}

	public BigDecimal getTomo() {
		return tomo;
	}

	public void setTomo(BigDecimal tomo) {
		this.tomo = tomo;
	}

}
