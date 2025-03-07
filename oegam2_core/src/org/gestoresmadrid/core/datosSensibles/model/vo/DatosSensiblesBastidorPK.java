package org.gestoresmadrid.core.datosSensibles.model.vo;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DATOS_SENSIBLES_BASTIDOR database table.
 * 
 */
@Embeddable
public class DatosSensiblesBastidorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Column(name="BASTIDOR")
	private String bastidor;

	@Column(name="ID_GRUPO")
	private String idGrupo;

    public DatosSensiblesBastidorPK() {
    }
	public String getBastidor() {
		return this.bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
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
		if (!(other instanceof DatosSensiblesBastidorPK)) {
			return false;
		}
		DatosSensiblesBastidorPK castOther = (DatosSensiblesBastidorPK)other;
		return 
			this.bastidor.equals(castOther.bastidor)
			&& this.idGrupo.equals(castOther.idGrupo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bastidor.hashCode();
		hash = hash * prime + this.idGrupo.hashCode();
		
		return hash;
    }
}