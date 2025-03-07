package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the DAT_REG_MERCANTIL database table.
 * 
 */
@Entity
@Table(name="DAT_REG_MERCANTIL")
public class DatRegMercantilVO implements Serializable {

	private static final long serialVersionUID = 5444119369626599757L;

	@Id
	@SequenceGenerator(name = "dat_reg_mercantil_secuencia", sequenceName = "DAT_REG_MERCANTIL_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dat_reg_mercantil_secuencia")
	@Column(name="ID_DAT_REG_MERCANTIL")
	private long idDatRegMercantil;

	@Column(name="COD_REGISTRO_MERCANTIL")
	private String codRegistroMercantil;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	private BigDecimal folio;

	private String hoja;

	private BigDecimal libro;
	
	@Column(name="NUM_INSCRIPCION")
	private String numInscripcion;

	private BigDecimal tomo;

	public DatRegMercantilVO() {
	}

	public long getIdDatRegMercantil() {
		return this.idDatRegMercantil;
	}

	public void setIdDatRegMercantil(long idDatRegMercantil) {
		this.idDatRegMercantil = idDatRegMercantil;
	}

	public String getCodRegistroMercantil() {
		return this.codRegistroMercantil;
	}

	public void setCodRegistroMercantil(String codRegistroMercantil) {
		this.codRegistroMercantil = codRegistroMercantil;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getFolio() {
		return this.folio;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

	public String getHoja() {
		return this.hoja;
	}

	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	public BigDecimal getLibro() {
		return this.libro;
	}

	public void setLibro(BigDecimal libro) {
		this.libro = libro;
	}

	public String getNumInscripcion() {
		return this.numInscripcion;
	}

	public void setNumInscripcion(String numInscripcion) {
		this.numInscripcion = numInscripcion;
	}

	public BigDecimal getTomo() {
		return this.tomo;
	}

	public void setTomo(BigDecimal tomo) {
		this.tomo = tomo;
	}
}