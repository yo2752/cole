package org.gestoresmadrid.core.impresion.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the IMPRESION database table.
 */
@Entity
@Table(name = "IMPRESION")
public class ImpresionVO implements Serializable {

	private static final long serialVersionUID = 2964633901272458671L;

	@Id
	@Column(name = "ID_IMPRESION")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEC_IMPRESION")
	@SequenceGenerator(name = "SEC_IMPRESION", sequenceName = "IMPRESION_SEQ")
	private Long idImpresion;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "FECHA_CREACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(name = "NOMBRE_DOCUMENTO")
	private String nombreDocumento;

	@Column(name = "TIPO_INTERVINIENTE")
	private String tipoInterviniente;

	@Column(name = "TIPO_CARPETA")
	private String tipoCarpeta;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "FECHA_MODIFICACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	@Column(name = "FECHA_GENERADO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaGenerado;

	@Column(name = "MENSAJE")
	private String mensaje;

	@OneToMany(mappedBy = "impresion")
	private Set<ImpresionTramiteTraficoVO> impresionTramites;

	public Long getIdImpresion() {
		return idImpresion;
	}

	public void setIdImpresion(Long idImpresion) {
		this.idImpresion = idImpresion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaGenerado() {
		return fechaGenerado;
	}

	public void setFechaGenerado(Date fechaGenerado) {
		this.fechaGenerado = fechaGenerado;
	}

	public String getTipoCarpeta() {
		return tipoCarpeta;
	}

	public void setTipoCarpeta(String tipoCarpeta) {
		this.tipoCarpeta = tipoCarpeta;
	}

	public Set<ImpresionTramiteTraficoVO> getImpresionTramites() {
		return impresionTramites;
	}

	public void setImpresionTramites(Set<ImpresionTramiteTraficoVO> impresionTramites) {
		this.impresionTramites = impresionTramites;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}