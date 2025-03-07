package org.gestoresmadrid.core.legalizacion.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the LEGALIZACION_CITAS database table.
 */
@Entity
@Table(name = "LEGALIZACION_CITAS")
public class LegalizacionCitaVO implements Serializable {

	private static final long serialVersionUID = -7580148570598131230L;

	@Id
	@Column(name = "ID_PETICION")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_LEGALIZACION")
	@SequenceGenerator(name = "SEC_LEGALIZACION", sequenceName = "LEGALIZACION_CITAS_SEQ")
	private Integer idPeticion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_LEGALIZACION")
	private Date fechaLegalizacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_DOCUMENTO")
	private Date fechaDocumento;

	@Column(name = "FICHERO_ADJUNTO")
	private int ficheroAdjunto;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "REFERENCIA")
	private String referencia;

	@Column(name = "SOLICITADO")
	private int solicitado;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "ESTADO")
	private Integer estado;

	@Column(name = "ESTADO_PETICION")
	private Integer estadoPeticion;

	@Column(name = "ORDEN")
	private Integer orden;

	@Column(name = "CLASE_DOCUMENTO")
	private String claseDocumento;

	@Column(name = "PAIS")
	private String pais;

	@ManyToOne
	@JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Column(name = "ID_CONTRATO")
	private Integer idContrato;

	public LegalizacionCitaVO() {}

	public Integer getIdPeticion() {
		return this.idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getFicheroAdjunto() {
		return ficheroAdjunto;
	}

	public void setFicheroAdjunto(int ficheroAdjunto) {
		this.ficheroAdjunto = ficheroAdjunto;
	}

	public int getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(int solicitado) {
		this.solicitado = solicitado;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechaLegalizacion() {
		return fechaLegalizacion;
	}

	public void setFechaLegalizacion(Date fechaLegalizacion) {
		this.fechaLegalizacion = fechaLegalizacion;
	}

	public Integer getEstadoPeticion() {
		return estadoPeticion;
	}

	public void setEstadoPeticion(Integer estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getClaseDocumento() {
		return claseDocumento;
	}

	public void setClaseDocumento(String claseDocumento) {
		this.claseDocumento = claseDocumento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}