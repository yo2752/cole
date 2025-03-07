package org.gestoresmadrid.core.datosSensibles.model.vo;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DATOS_SENSIBLES_MATRICULA database table.
 * 
 */
@Embeddable
public class DatosSensiblesMatriculaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="MATRICULA")
	private String matricula;

	@Column(name="ID_GRUPO")
	private String idGrupo;

    public DatosSensiblesMatriculaPK() {
    }
	public String getMatricula() {
		return this.matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getIdGrupo() {
		return this.idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DatosSensiblesMatriculaPK)) {
			return false;
		}
		DatosSensiblesMatriculaPK castOther = (DatosSensiblesMatriculaPK)other;
		return 
			this.matricula.equals(castOther.matricula)
			&& this.idGrupo.equals(castOther.idGrupo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.matricula.hashCode();
		hash = hash * prime + this.idGrupo.hashCode();
		
		return hash;
    }
}