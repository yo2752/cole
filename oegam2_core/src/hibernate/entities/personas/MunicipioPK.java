package hibernate.entities.personas;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MUNICIPIO database table.
 * 
 */
@Embeddable
public class MunicipioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_PROVINCIA")
	private String idProvincia;

	@Column(name="ID_MUNICIPIO")
	private String idMunicipio;

    public MunicipioPK() {
    }
	public String getIdProvincia() {
		return this.idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getIdMunicipio() {
		return this.idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MunicipioPK)) {
			return false;
		}
		MunicipioPK castOther = (MunicipioPK)other;
		return 
			this.idProvincia.equals(castOther.idProvincia)
			&& this.idMunicipio.equals(castOther.idMunicipio);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProvincia.hashCode();
		hash = hash * prime + this.idMunicipio.hashCode();
		
		return hash;
    }
}