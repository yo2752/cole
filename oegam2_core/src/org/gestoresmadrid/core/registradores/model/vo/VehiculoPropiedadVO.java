package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VEHICULO_PROPIEDAD database table.
 * 
 */
@Entity
@Table(name="VEHICULO_PROPIEDAD")
public class VehiculoPropiedadVO implements Serializable {

	private static final long serialVersionUID = 6663766774097415213L;

	@EmbeddedId
	private VehiculoPropiedadPK id;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	//bi-directional many-to-one association to Propiedad
	@ManyToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	public VehiculoPropiedadVO() {
	}

	public VehiculoPropiedadPK getId() {
		return this.id;
	}

	public void setId(VehiculoPropiedadPK id) {
		this.id = id;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public PropiedadVO getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

}