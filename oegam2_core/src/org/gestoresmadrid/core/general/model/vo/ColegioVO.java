package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the COLEGIO database table.
 */
@Entity
@Table(name = "COLEGIO")
public class ColegioVO implements Serializable {

	private static final long serialVersionUID = 2898996042366264320L;

	@Id
	private String colegio;

	private String cif;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	private String nombre;

	public ColegioVO() {}

	public String getColegio() {
		return this.colegio;
	}

	public void setColegio(String colegio) {
		this.colegio = colegio;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}