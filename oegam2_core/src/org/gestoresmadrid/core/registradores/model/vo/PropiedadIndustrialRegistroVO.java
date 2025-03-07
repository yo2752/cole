package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the PROPIEDAD_INDUSTRIAL_REGISTRO database table.
 * 
 */
@Entity
@Table(name="PROPIEDAD_INDUSTRIAL_REGISTRO")
public class PropiedadIndustrialRegistroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2915090598614327075L;

	@Id
	@SequenceGenerator(name = "propind_registro_secuencia", sequenceName = "PROPIND_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "propind_registro_secuencia")
	@Column(name="ID_PROPIEDAD_INDUSTRIAL")
	private long idPropiedadIndustrial;

	@Column(name="CLASE_MARCA")
	private String claseMarca;

	private String descripcion;

	@Column(name="NOMBRE_MARCA")
	private String nombreMarca;

	@Column(name="NUM_REG_ADM")
	private String numRegAdm;

	@Column(name="SUB_CLASE_MARCA")
	private String subClaseMarca;

	@Column(name="TIPO_MARCA")
	private String tipoMarca;

	@Column(name = "ID_PROPIEDAD")
	private BigDecimal idPropiedad;
	
	@OneToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	public PropiedadIndustrialRegistroVO() {
	}

	public long getIdPropiedadIndustrial() {
		return this.idPropiedadIndustrial;
	}

	public void setIdPropiedadIndustrial(long idPropiedadIndustrial) {
		this.idPropiedadIndustrial = idPropiedadIndustrial;
	}

	public String getClaseMarca() {
		return this.claseMarca;
	}

	public void setClaseMarca(String claseMarca) {
		this.claseMarca = claseMarca;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreMarca() {
		return this.nombreMarca;
	}

	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}

	public String getNumRegAdm() {
		return this.numRegAdm;
	}

	public void setNumRegAdm(String numRegAdm) {
		this.numRegAdm = numRegAdm;
	}

	public String getSubClaseMarca() {
		return this.subClaseMarca;
	}

	public void setSubClaseMarca(String subClaseMarca) {
		this.subClaseMarca = subClaseMarca;
	}

	public String getTipoMarca() {
		return this.tipoMarca;
	}

	public void setTipoMarca(String tipoMarca) {
		this.tipoMarca = tipoMarca;
	}

	public PropiedadVO getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

}