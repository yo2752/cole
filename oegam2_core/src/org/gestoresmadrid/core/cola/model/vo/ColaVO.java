package org.gestoresmadrid.core.cola.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the COLA database table.
 */

// @NamedQuery(name = ColaVO.COLA_BUSQUEDA_HILO_ACTIVO, query = ColaVO.COLA_BUSQUEDA_HILO_ACTIVO_QUERY)

@NamedQueries({ @NamedQuery(name = ColaVO.ESTABLECER_MAX_PRIORIDAD, query = ColaVO.ESTABLECER_MAX_PRIORIDAD_QUERY),
		@NamedQuery(name = ColaVO.ESTABLECER_MAX_PRIORIDAD_NUEVA, query = ColaVO.ESTABLECER_MAX_PRIORIDAD_QUERY_NUEVA) })
@Entity(name = "COLA")
@Table(name = "COLA")
public class ColaVO implements Serializable {

	private static final long serialVersionUID = 7153273489101694732L;

	public static final String ESTABLECER_MAX_PRIORIDAD = "ColaVO.establecerMaxPrioridad";
	static final String ESTABLECER_MAX_PRIORIDAD_QUERY = "UPDATE COLA c1 SET c1.fechaHora=(select min(c2.fechaHora)-0.001 from COLA c2 where c1.idEnvio = :idEnvio and c2.cola = c1.cola and c2.nodo = c1.nodo and c2.proceso = c1.proceso and c2.estado = 1)  where c1.idEnvio = :idEnvio and c1.estado = 1";

	public static final String ESTABLECER_MAX_PRIORIDAD_NUEVA = "ColaVO.establecerMaxPrioridadNueva";
	static final String ESTABLECER_MAX_PRIORIDAD_QUERY_NUEVA = "UPDATE COLA c1 SET c1.fechaHora=(select min(c2.fechaHora)-0.001 from COLA c2 where c2.cola = c1.cola and c2.nodo = c1.nodo and c2.proceso = c1.proceso and c2.estado = 1)  where c1.idEnvio = :idEnvio and c1.estado = 1";

	public static final String COLA_BUSQUEDA_HILO_ACTIVO = "ColaVO.ColaBusquedaHilaActivo";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_ID_ENVIO")
	@SequenceGenerator(name = "SEC_ID_ENVIO", sequenceName = "ID_ENVIO_SEQ")
	@Column(name = "ID_ENVIO")
	private Long idEnvio;

	@Column(name = "COLA")
	private String cola;

	@Column(name = "ESTADO")
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

	@Column(name = "PROCESO")
	private String proceso;

	@Column(name = "RESPUESTA")
	private String respuesta;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "XML_ENVIAR")
	private String xmlEnviar;

	@Column(name = "ID_TRAMITE")
	private BigDecimal idTramite;

	@Column(name = "ID_CONTRATO")
	private BigDecimal idContrato;

	public ColaVO() {}

	public Long getIdEnvio() {
		return this.idEnvio;
	}

	public void setIdEnvio(Long idEnvio) {
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

	public BigDecimal getnIntento() {
		return nIntento;
	}

	public void setnIntento(BigDecimal nIntento) {
		this.nIntento = nIntento;
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

	public BigDecimal getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(BigDecimal idTramite) {
		this.idTramite = idTramite;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}
}