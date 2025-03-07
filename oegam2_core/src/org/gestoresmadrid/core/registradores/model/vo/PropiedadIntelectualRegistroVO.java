package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the PROPIEDAD_INTELECTUAL_REGISTRO database table.
 * 
 */
@Entity
@Table(name="PROPIEDAD_INTELECTUAL_REGISTRO")
public class PropiedadIntelectualRegistroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3370790891235250396L;

	@Id
	@SequenceGenerator(name = "propint_registro_secuencia", sequenceName = "PROPINT_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "propint_registro_secuencia")
	@Column(name="ID_PROPIEDAD_INTELECTUAL")
	private long idPropiedadIntelectual;

	private String clase;

	private String descripcion;

	@Column(name="NUM_REG_ADM")
	private String numRegAdm;

	@Column(name = "ID_PROPIEDAD")
	private BigDecimal idPropiedad;
	
	@OneToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	public PropiedadIntelectualRegistroVO() {
	}

	public long getIdPropiedadIntelectual() {
		return this.idPropiedadIntelectual;
	}

	public void setIdPropiedadIntelectual(long idPropiedadIntelectual) {
		this.idPropiedadIntelectual = idPropiedadIntelectual;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNumRegAdm() {
		return this.numRegAdm;
	}

	public void setNumRegAdm(String numRegAdm) {
		this.numRegAdm = numRegAdm;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public PropiedadVO getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

}