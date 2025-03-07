package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the COLEGIO database table.
 * 
 */
@Entity
@NamedQuery(name="Colegio.findAll", query="SELECT c FROM Colegio c")
public class Colegio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String colegio;

	private String cif;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	private String nombre;

	public Colegio() {
	}

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