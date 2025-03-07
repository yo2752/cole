package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the TIPO_VEHICULO database table.
 * 
 */
@Entity
@Table(name = "TIPO_VEHICULO")
public class TipoVehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TIPO_VEHICULO")
	private String tipoVehiculo;

	private String descripcion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	public TipoVehiculo() {
	}

	public String getTipoVehiculo() {
		return this.tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}