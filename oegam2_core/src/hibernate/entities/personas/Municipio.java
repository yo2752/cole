package hibernate.entities.personas;

import hibernate.entities.general.Provincia;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MUNICIPIO database table.
 * 
 */
@Entity
public class Municipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MunicipioPK id;

	@Column(name="CODIGO_POSTAL")
	private String codigoPostal;

	private String nombre;

	@Column(name="OFICINA_LIQUIDADORA")
	private String oficinaLiquidadora;

	//bi-directional many-to-one association to Direccion
	@OneToMany(mappedBy="municipio")
	private List<Direccion> direccions;

	//bi-directional many-to-one association to Provincia
    @ManyToOne
	@JoinColumn(name="ID_PROVINCIA",insertable=false, updatable=false)
	private Provincia provincia;

    public Municipio() {
    }

	public MunicipioPK getId() {
		return this.id;
	}

	public void setId(MunicipioPK id) {
		this.id = id;
	}
	
	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOficinaLiquidadora() {
		return this.oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public List<Direccion> getDireccions() {
		return this.direccions;
	}

	public void setDireccions(List<Direccion> direccions) {
		this.direccions = direccions;
	}
	
	public Provincia getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Municipio guest = (Municipio) obj;
        return this.getId()!=null && this.getId().equals(guest.getId()) || this.getId()==null && guest.getId()==null;
	}

}