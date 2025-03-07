package org.gestoresmadrid.core.facturacionDistintivo.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "DISTINTIVOS_FACT_INCID")
public class FacturacionDstvIncidenciaVO implements Serializable {

	private static final long serialVersionUID = -846886797016646367L;

	@Id
	@SequenceGenerator(name = "secuencia_dstv_fact_inc", sequenceName = "ID_DSTV_FACT_INCD_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_dstv_fact_inc")
	@Column(name = "ID_INCIDENCIA")
	private Long idDistintivoFactInc;

	@Column(name = "ID_USUARIO")
	private Long idUsuarioIncidencia;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuarioIncidencia;

	@Column(name = "MOTIVO")
	private String motivo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INCIDENCIA")
	private Date fechaIncidencia;

	@Column(name = "CANTIDAD")
	private Long cantidad;

	@Column(name = "DUPLICADO")
	private String duplicado;

	@Column(name = "ID_FACTURACION_DSTV")
	private Long idDistintivoFacturado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FACTURACION_DSTV", insertable = false, updatable = false)
	private FacturacionDistintivoVO facturacionDstv;

	public Long getIdDistintivoFacturado() {
		return idDistintivoFacturado;
	}

	public void setIdDistintivoFacturado(Long idDistintivoFacturado) {
		this.idDistintivoFacturado = idDistintivoFacturado;
	}

	public FacturacionDistintivoVO getFacturacionDstv() {
		return facturacionDstv;
	}

	public void setFacturacionDstv(FacturacionDistintivoVO facturacionDstv) {
		this.facturacionDstv = facturacionDstv;
	}

	public Long getIdDistintivoFactInc() {
		return idDistintivoFactInc;
	}

	public void setIdDistintivoFactInc(Long idDistintivoFactInc) {
		this.idDistintivoFactInc = idDistintivoFactInc;
	}

	public Long getIdUsuarioIncidencia() {
		return idUsuarioIncidencia;
	}

	public void setIdUsuarioIncidencia(Long idUsuarioIncidencia) {
		this.idUsuarioIncidencia = idUsuarioIncidencia;
	}

	public UsuarioVO getUsuarioIncidencia() {
		return usuarioIncidencia;
	}

	public void setUsuarioIncidencia(UsuarioVO usuarioIncidencia) {
		this.usuarioIncidencia = usuarioIncidencia;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFechaIncidencia() {
		return fechaIncidencia;
	}

	public void setFechaIncidencia(Date fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getDuplicado() {
		return duplicado;
	}

	public void setDuplicado(String duplicado) {
		this.duplicado = duplicado;
	}

}