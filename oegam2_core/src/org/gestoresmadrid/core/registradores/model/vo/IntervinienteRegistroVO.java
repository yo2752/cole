package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;


/**
 * The persistent class for the INTERVINIENTE_REGISTRO database table.
 * 
 */
@Entity
@Table(name="INTERVINIENTE_REGISTRO")
public class IntervinienteRegistroVO implements Serializable {

	private static final long serialVersionUID = 6347362859823115957L;

	@Id
	@SequenceGenerator(name = "interviniente_registro_secuencia", sequenceName = "INTERVINIENTE_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "interviniente_registro_secuencia")
	@Column(name="ID_INTERVINIENTE")
	private long idInterviniente;

	@Column(name="ID_ESCRITURA")
	private BigDecimal idEscritura;

	@Column(name="NIF")
	private String nif;

	@Column(name="ID_REPRESENTADO")
	private long idRepresentado;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@Column(name="TIPO_INTERVINIENTE")
	private String tipoInterviniente;
	
	@Column(name="CARGO")
	private String cargo;
	
	@Column(name="TIPO_PERSONA")
	private String tipoPersona;

	//bi-directional many-to-one association to DatRegMercantil
	@OneToOne
	@JoinColumn(name="ID_DAT_REG_MERCANTIL")
	private DatRegMercantilVO datRegMercantil;

	//bi-directional many-to-one association to Telefono
	@OneToMany(mappedBy="intervinienteRegistro")
	private Set<TelefonoVO> telefonos;
	
	//bi-directional many-to-one association to Notario
	@ManyToOne
	@JoinColumn(name="CODIGO_NOTARIO")
	private NotarioRegistroVO notario;
	
	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="NIF", referencedColumnName="NIF", insertable = false, updatable = false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO", insertable = false, updatable = false)
	})
	private PersonaVO persona;
	
	//bi-directional many-to-many association to tramiteRegRbm
	@ManyToMany	
	@JoinTable(
		name="INTERVINIENTE_TRAMITE_REG_RBM"
		, joinColumns={
			@JoinColumn(name="ID_INTERVINIENTE")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_TRAMITE_REGISTRO")
			}
		)
	private Set<TramiteRegRbmVO> tramites;
	
	@Column(name = "ID_DIRECCION")
	private Long idDireccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
	private DireccionVO direccion;

	public IntervinienteRegistroVO() {
	}

	public long getIdInterviniente() {
		return this.idInterviniente;
	}

	public void setIdInterviniente(long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public BigDecimal getIdEscritura() {
		return this.idEscritura;
	}

	public void setIdEscritura(BigDecimal idEscritura) {
		this.idEscritura = idEscritura;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public long getIdRepresentado() {
		return this.idRepresentado;
	}

	public void setIdRepresentado(long idRepresentado) {
		this.idRepresentado = idRepresentado;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTipoInterviniente() {
		return this.tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public DatRegMercantilVO getDatRegMercantil() {
		return this.datRegMercantil;
	}

	public void setDatRegMercantil(DatRegMercantilVO datRegMercantil) {
		this.datRegMercantil = datRegMercantil;
	}

	
	public PersonaVO getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public NotarioRegistroVO getNotario() {
		return notario;
	}

	public void setNotario(NotarioRegistroVO notario) {
		this.notario = notario;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public Set<TelefonoVO> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Set<TelefonoVO> telefonos) {
		this.telefonos = telefonos;
	}

	public Set<TramiteRegRbmVO> getTramites() {
		return tramites;
	}

	public void setTramites(Set<TramiteRegRbmVO> tramites) {
		this.tramites = tramites;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}


}