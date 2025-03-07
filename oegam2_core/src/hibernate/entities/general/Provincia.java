package hibernate.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the PROVINCIA database table.
 * 
 */
@Entity
public class Provincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	private String nombre;

	// bi-directional many-to-one association to Contrato
	@OneToMany(mappedBy = "provincia")
	private List<Contrato> contratos;

	// bi-directional many-to-one association to JefaturaTrafico
	@OneToMany(mappedBy = "provincia")
	private List<JefaturaTrafico> jefaturaTraficos;

	// bi-directional many-to-one association to Vehiculo
//	@OneToMany(mappedBy="provincia")
//	private List<Vehiculo> vehiculos;

	public Provincia() {
	}

	public String getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Contrato> getContratos() {
		return this.contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public List<JefaturaTrafico> getJefaturaTraficos() {
		return this.jefaturaTraficos;
	}

	public void setJefaturaTraficos(List<JefaturaTrafico> jefaturaTraficos) {
		this.jefaturaTraficos = jefaturaTraficos;
	}

//	public List<Vehiculo> getVehiculos() {
//		return this.vehiculos;
//	}

//	public void setVehiculos(List<Vehiculo> vehiculos) {
//		this.vehiculos = vehiculos;
//	}

}