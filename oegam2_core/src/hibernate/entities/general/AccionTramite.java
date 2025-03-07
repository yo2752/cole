package hibernate.entities.general;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the ACCION_TRAMITE database table.
 * 
 */
@Entity
@Table(name="ACCION_TRAMITE")
@NamedQuery(name="AccionTramite.findAll", query="SELECT a FROM AccionTramite a")
public class AccionTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AccionTramitePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_FIN")
	private java.util.Date fechaFin;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	private String respuesta;

	public AccionTramite() {
	}

	public AccionTramitePK getId() {
		return this.id;
	}

	public void setId(AccionTramitePK id) {
		this.id = id;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}