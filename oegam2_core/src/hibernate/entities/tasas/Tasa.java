package hibernate.entities.tasas;

import hibernate.entities.general.Contrato;
import hibernate.entities.general.Usuario;
import hibernate.entities.trafico.TramiteTrafTran;
import hibernate.entities.trafico.TramiteTrafico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * The persistent class for the TASA database table.
 * 
 */
@Entity
public class Tasa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TASA")
	private String codigoTasa;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_ASIGNACION")
	private Date fechaAsignacion;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_FIN_VIGENCIA")
	private Date fechaFinVigencia;

	//bi-directional many-to-one association to Contrato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO")
	private Contrato contrato;

	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	private BigDecimal precio;

	@Column(name="TIPO_TASA")
	private String tipoTasa;

	@Column(name="NUM_EXPEDIENTE")
	private Long numExpediente;

	//bi-directional many-to-one association to TramiteTrafico
	@OneToMany(mappedBy="tasa")
	private Set<TramiteTrafico> tramiteTraficos;

	//bi-directional many-to-one association to TramiteTrafTran
	@OneToMany(mappedBy="tasa1")
	private Set<TramiteTrafTran> tramiteTrafTrans1;

	//bi-directional many-to-one association to TramiteTrafTran
	@OneToMany(mappedBy="tasa2")
	private Set<TramiteTrafTran> tramiteTrafTrans2;

	@OneToMany(mappedBy="tasa")
	@Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	private List<EvolucionTasa> evolucionTasas;

	@Column(name="COMENTARIOS")
	private String comentarios;

	public Tasa() {
	}

	public String getCodigoTasa() {
		return this.codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaFinVigencia() {
		return this.fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getTipoTasa() {
		return this.tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public Set<TramiteTrafico> getTramiteTraficos() {
		return this.tramiteTraficos;
	}

	public void setTramiteTraficos(Set<TramiteTrafico> tramiteTraficos) {
		this.tramiteTraficos = tramiteTraficos;
	}

	public Set<TramiteTrafTran> getTramiteTrafTrans1() {
		return this.tramiteTrafTrans1;
	}

	public void setTramiteTrafTrans1(Set<TramiteTrafTran> tramiteTrafTrans1) {
		this.tramiteTrafTrans1 = tramiteTrafTrans1;
	}

	public Set<TramiteTrafTran> getTramiteTrafTrans2() {
		return this.tramiteTrafTrans2;
	}

	public void setTramiteTrafTrans2(Set<TramiteTrafTran> tramiteTrafTrans2) {
		this.tramiteTrafTrans2 = tramiteTrafTrans2;
	}

	public List<EvolucionTasa> getEvolucionTasas() {
		return this.evolucionTasas;
	}

	public void setEvolucionTasas(List<EvolucionTasa> evolucionTasas) {
		this.evolucionTasas = evolucionTasas;
	}

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}