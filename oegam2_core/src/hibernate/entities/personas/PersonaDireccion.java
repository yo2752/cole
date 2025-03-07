package hibernate.entities.personas;

import hibernate.entities.trafico.IntervinienteTrafico;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PERSONA_DIRECCION database table.
 * 
 */
@Entity
@Table(name="PERSONA_DIRECCION")
public class PersonaDireccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonaDireccionPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

	//bi-directional many-to-one association to IntervinienteTrafico
	@OneToMany(mappedBy="personaDireccion")
	private List<IntervinienteTrafico> intervinienteTraficos;

	//bi-directional many-to-one association to Direccion
    @ManyToOne
	@JoinColumn(name="ID_DIRECCION",insertable=false, updatable=false)
	private Direccion direccion;

	//bi-directional many-to-one association to Persona
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="NIF", referencedColumnName="NIF",insertable=false, updatable=false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO",insertable=false, updatable=false)
		})
	private Persona persona;

    public PersonaDireccion() {
    }

	public PersonaDireccionPK getId() {
		return this.id;
	}

	public void setId(PersonaDireccionPK id) {
		this.id = id;
	}
	
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public List<IntervinienteTrafico> getIntervinienteTraficos() {
		return this.intervinienteTraficos;
	}

	public void setIntervinienteTraficos(List<IntervinienteTrafico> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}
	
	public Direccion getDireccion() {
		return this.direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
}