package org.gestoresmadrid.core.transporte.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="DATOS_CERT_TRANSPORTES")
public class NotificacionTransporteVO implements Serializable{

	private static final long serialVersionUID = 7696372072974230511L;

	@Id
	@Column(name="ID")
	private Long idNotificacionTransporte;

	@Column(name="ESTADO")
	private String estado;

	@Column(name="FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Column(name="FECHA_MODIFICACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	@Column(name="NIF_EMPRESA")
	private String nifEmpresa;

	@Column(name="EMPRESA")
	private String nombreEmpresa;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO", insertable=false, updatable=false)
	private UsuarioVO usuario;

	@Column(name = "NOMBRE_PDF")
	private String nombrePdf;

	public Long getIdNotificacionTransporte() {
		return idNotificacionTransporte;
	}

	public void setIdNotificacionTransporte(Long idNotificacionTransporte) {
		this.idNotificacionTransporte = idNotificacionTransporte;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNifEmpresa() {
		return nifEmpresa;
	}

	public void setNifEmpresa(String nifEmpresa) {
		this.nifEmpresa = nifEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getNombrePdf() {
		return nombrePdf;
	}

	public void setNombrePdf(String nombrePdf) {
		this.nombrePdf = nombrePdf;
	}

}