package org.gestoresmadrid.core.datosBancariosFavoritos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DATOS_BANCARIOS_FAVORITOS")
public class DatosBancariosFavoritosVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 6870424840735780461L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_DATOS_BANCARIOS")
	@SequenceGenerator(name="SEC_ID_DATOS_BANCARIOS", sequenceName="ID_DATOS_BANCARIOS_FAV_SEQ")
	@Column(name = "ID_DATOS_BANCARIOS")
	private Long idDatosBancarios;
	
	@Column(name="FORMA_PAGO")
	private BigDecimal formaPago;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@Column(name="DATOS_BANCARIOS")
	private String datosBancarios;
	
	@Column(name="NIF_PAGADOR")
	private String nifPagador;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="ESTADO")
	private BigDecimal estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_ULTIMA_MODIFICACION")
	private Date fechaUltimaModificacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_BAJA")
	private Date fechaBaja;
	
	@Column(name="NOMBRE_TITULAR_CUENTA")
	private String nombreTitular;
	
	@Column(name="ID_MUNICIPIO_CUENTA")
	private String idMunicipio;
	
	@Column(name="ID_PROVINCIA_CUENTA")
	private String idProvincia;
	
	public Long getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(Long idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	public BigDecimal getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(BigDecimal formaPago) {
		this.formaPago = formaPago;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(String datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public String getNifPagador() {
		return nifPagador;
	}

	public void setNifPagador(String nifPagador) {
		this.nifPagador = nifPagador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
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

	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
}
