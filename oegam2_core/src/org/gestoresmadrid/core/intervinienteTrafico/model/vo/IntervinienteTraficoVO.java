package org.gestoresmadrid.core.intervinienteTrafico.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TipoIntervinienteVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

/**
 * The persistent class for the INTERVINIENTE_TRAFICO database table.
 */
@Entity
@Table(name = "INTERVINIENTE_TRAFICO")
public class IntervinienteTraficoVO implements Serializable, Cloneable {

	private static final long serialVersionUID = -1549122564742815751L;

	@EmbeddedId
	private IntervinienteTraficoPK id;

	private String autonomo;

	@Column(name = "CAMBIO_DOMICILIO")
	private String cambioDomicilio;

	@Column(name = "CODIGO_IAE")
	private String codigoIae;

	@Column(name = "CONCEPTO_REPRE")
	private String conceptoRepre;

	@Column(name = "DATOS_DOCUMENTO")
	private String datosDocumento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "HORA_FIN")
	private String horaFin;

	@Column(name = "HORA_INICIO")
	private String horaInicio;

	@Column(name = "ID_MOTIVO_TUTELA")
	private String idMotivoTutela;

	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
	private DireccionVO direccion;

	// bi-directional many-to-one association to TipoIntervinienteVO
	@ManyToOne
	@JoinColumn(name = "TIPO_INTERVINIENTE", insertable = false, updatable = false)
	private TipoIntervinienteVO tipoIntervinienteVO;

	@Column(name = "NUM_INTERVINIENTE")
	private Integer numInterviniente;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "NIF", referencedColumnName = "NIF", insertable = false, updatable = false) })
	private PersonaVO persona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	public IntervinienteTraficoPK getId() {
		return id;
	}

	public void setId(IntervinienteTraficoPK id) {
		this.id = id;
	}

	public String getAutonomo() {
		return autonomo;
	}

	public void setAutonomo(String autonomo) {
		this.autonomo = autonomo;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getCodigoIae() {
		return codigoIae;
	}

	public void setCodigoIae(String codigoIae) {
		this.codigoIae = codigoIae;
	}

	public String getConceptoRepre() {
		return conceptoRepre;
	}

	public void setConceptoRepre(String conceptoRepre) {
		this.conceptoRepre = conceptoRepre;
	}

	public String getDatosDocumento() {
		return datosDocumento;
	}

	public void setDatosDocumento(String datosDocumento) {
		this.datosDocumento = datosDocumento;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getIdMotivoTutela() {
		return idMotivoTutela;
	}

	public void setIdMotivoTutela(String idMotivoTutela) {
		this.idMotivoTutela = idMotivoTutela;
	}

	public TipoIntervinienteVO getTipoIntervinienteVO() {
		return tipoIntervinienteVO;
	}

	public void setTipoIntervinienteVO(TipoIntervinienteVO tipoIntervinienteVO) {
		this.tipoIntervinienteVO = tipoIntervinienteVO;
	}

	public Integer getNumInterviniente() {
		return numInterviniente;
	}

	public void setNumInterviniente(Integer numInterviniente) {
		this.numInterviniente = numInterviniente;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public PersonaVO getPersona() {
		return persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}
}