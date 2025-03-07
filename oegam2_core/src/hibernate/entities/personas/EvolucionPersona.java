package hibernate.entities.personas;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EVOLUCION_PERSONA database table.
 * 
 */
@Entity
@Table(name="EVOLUCION_PERSONA")
public class EvolucionPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EvolucionPersonaPK id;

	@Column(name="APELLIDO1_ANT")
	private String apellido1Ant;

	@Column(name="APELLIDO1_NUE")
	private String apellido1Nue;

	@Column(name="APELLIDO2_ANT")
	private String apellido2Ant;

	@Column(name="APELLIDO2_NUE")
	private String apellido2Nue;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO_ANT")
	private Date fechaNacimientoAnt;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO_NUE")
	private Date fechaNacimientoNue;

	@Column(name="NOMBRE_ANT")
	private String nombreAnt;

	@Column(name="NOMBRE_NUE")
	private String nombreNue;

	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	private String otros;

	@Column(name="TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name="TIPO_TRAMITE")
	private String tipoTramite;

	//bi-directional many-to-one association to Persona
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="NIF", referencedColumnName="NIF", insertable=false, updatable=false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO", insertable=false, updatable=false)
		})
	private Persona persona;

    @Column(name="ID_USUARIO")
	private BigDecimal idUsuario;
    public EvolucionPersona() {
    }

	public EvolucionPersonaPK getId() {
		return this.id;
	}

	public void setId(EvolucionPersonaPK id) {
		this.id = id;
	}
	
	public String getApellido1Ant() {
		return this.apellido1Ant;
	}

	public void setApellido1Ant(String apellido1Ant) {
		this.apellido1Ant = apellido1Ant;
	}

	public String getApellido1Nue() {
		return this.apellido1Nue;
	}

	public void setApellido1Nue(String apellido1Nue) {
		this.apellido1Nue = apellido1Nue;
	}

	public Object getApellido2Ant() {
		return this.apellido2Ant;
	}

	public void setApellido2Ant(String apellido2Ant) {
		this.apellido2Ant = apellido2Ant;
	}

	public String getApellido2Nue() {
		return this.apellido2Nue;
	}

	public void setApellido2Nue(String apellido2Nue) {
		this.apellido2Nue = apellido2Nue;
	}

	public Date getFechaNacimientoAnt() {
		return this.fechaNacimientoAnt;
	}

	public void setFechaNacimientoAnt(Date fechaNacimientoAnt) {
		this.fechaNacimientoAnt = fechaNacimientoAnt;
	}

	public Date getFechaNacimientoNue() {
		return this.fechaNacimientoNue;
	}

	public void setFechaNacimientoNue(Date fechaNacimientoNue) {
		this.fechaNacimientoNue = fechaNacimientoNue;
	}

	public Object getNombreAnt() {
		return this.nombreAnt;
	}

	public void setNombreAnt(String nombreAnt) {
		this.nombreAnt = nombreAnt;
	}

	public Object getNombreNue() {
		return this.nombreNue;
	}

	public void setNombreNue(String nombreNue) {
		this.nombreNue = nombreNue;
	}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getOtros() {
		return this.otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getTipoActuacion() {
		return this.tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public BigDecimal getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}