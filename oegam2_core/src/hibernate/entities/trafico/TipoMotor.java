package hibernate.entities.trafico;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the TIPO_MOTOR database table.
 * 
 */
@Entity
@Table(name="TIPO_MOTOR")
public class TipoMotor implements Serializable {

	private static final long serialVersionUID = 47000204188304061L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TIPO_MOTOR")
	private String tipoMotor;

	@Column(name="TIPO_MOTOR_DESC")
	private String descripcion;

	public TipoMotor() {
	}

	public String getTipoMotor() {
		return this.tipoMotor;
	}

	public void setTipoMotor(String tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}