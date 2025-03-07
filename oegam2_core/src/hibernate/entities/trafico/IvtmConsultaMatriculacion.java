package hibernate.entities.trafico;

//TODO MPC. Cambio IVTM. Clase nueva.
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CONSULTAS_IVTM")
public class IvtmConsultaMatriculacion {

	@Id
	@Column(name = "ID_PETICION")
	private BigDecimal idPeticion;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "MENSAJE")
	private String mensaje;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REQ")
	private Date fechaReq;

	public BigDecimal getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(BigDecimal idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFechaReq() {
		return fechaReq;
	}

	public void setFechaReq(Date fechaReq) {
		this.fechaReq = fechaReq;
	}

}