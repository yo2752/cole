package hibernate.entities.datosSensibles;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hibernate.entities.general.Grupo;

/**
 * The persistent class for the DATOS_SENSIBLES_MATRICULA database table.
 * 
 */
@Entity
@Table(name="DATOS_SENSIBLES_MATRICULA")
public class DatosSensiblesMatricula implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DatosSensiblesMatriculaPK id;

	@Column(name="APELLIDOS_NOMBRE")
	private String apellidosNombre;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="ESTADO")
	private BigDecimal estado;

	@Column(name="FECHA_BAJA")
	private Date fechaBaja;

	//bi-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name="ID_GRUPO",insertable=false, updatable=false)
	private Grupo grupo;

	/** Autor: Julio Molina
	 *  Fecha: 26/12/2012
	 * Metodo De momento comentamos esto ya que los Usuarios no todos tendran grupo
	 */
//	bi-directional many-to-one association to Usuario
//  @ManyToOne
//	@JoinColumn(name="ID_USUARIO")
//	private Usuario usuario;

	@Column(name="ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name="TIEMPO_RESTAURACION")
	private BigDecimal tiempoRestauracion;

	@Column(name="FECHA_OPERACION")
	private Date fechaOperacion;

	@Column(name="USUARIO_OPERACION")
	private BigDecimal usuarioOperacion;

	public DatosSensiblesMatricula() {
	}

	public DatosSensiblesMatriculaPK getId() {
		return this.id;
	}

	public void setId(DatosSensiblesMatriculaPK id) {
		this.id = id;
	}

	public String getApellidosNombre() {
		return this.apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	public BigDecimal getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public BigDecimal getTiempoRestauracion() {
		return tiempoRestauracion;
	}

	public void setTiempoRestauracion(BigDecimal tiempoRestauracion) {
		this.tiempoRestauracion = tiempoRestauracion;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public BigDecimal getUsuarioOperacion() {
		return usuarioOperacion;
	}

	public void setUsuarioOperacion(BigDecimal usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}

}