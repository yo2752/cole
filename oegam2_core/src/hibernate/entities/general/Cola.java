package hibernate.entities.general;

import hibernate.entities.procesos.Proceso;
import hibernate.entities.registradores.TramiteRegistro;
import hibernate.entities.trafico.TramiteTrafico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * The persistent class for the COLA database table.
 * 
 */
@Entity
public class Cola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ENVIO")
	private long idEnvio;

	private String cola;

	private BigDecimal estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

	@Column(name = "ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name = "N_INTENTO")
	private BigDecimal nIntento;

	@Column(name = "NODO")
	private String nodo;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumns({
			@JoinColumn(name = "PROCESO", referencedColumnName = "PROCESO", insertable = false, updatable = false),
			@JoinColumn(name = "NODO", referencedColumnName = "NODO", insertable = false, updatable = false) })
	private Proceso proceso;

	private String respuesta;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "XML_ENVIAR")
	private String xmlEnviar;

	// bi-directional one-to-one association to TramiteTrafico
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_TRAMITE", referencedColumnName = "NUM_EXPEDIENTE")
	@NotFound(action = NotFoundAction.IGNORE)
	private TramiteTrafico tramiteTrafico;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ID_TRAMITE", referencedColumnName = "ID_TRAMITE_REGISTRO", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private TramiteRegistro tramiteRegistro;

	@Column(name = "ID_CONTRATO")
	private BigDecimal idContrato;

	public Cola() {
	}

	public long getIdEnvio() {
		return this.idEnvio;
	}

	public void setIdEnvio(long idEnvio) {
		this.idEnvio = idEnvio;
	}

	public String getCola() {
		return this.cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public BigDecimal getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getNIntento() {
		return this.nIntento;
	}

	public void setNIntento(BigDecimal nIntento) {
		this.nIntento = nIntento;
	}

	// Duplicados el get y el set de nIntento porque el display table
	// no reconoce los generados automáticamente con JPA

	public BigDecimal getnIntento() {
		return nIntento;
	}

	public void setnIntento(BigDecimal nIntento) {
		this.nIntento = nIntento;
	}

	public String getNodo() {
		return this.nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public Proceso getProceso() {
		return this.proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Object getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getXmlEnviar() {
		return this.xmlEnviar;
	}

	public void setXmlEnviar(String xmlEnviar) {
		this.xmlEnviar = xmlEnviar;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public TramiteRegistro getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistro tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public Date getfechaHora() {
		return this.fechaHora;
	}

	public void setfechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}
}