package org.gestoresmadrid.core.presentacion.jpt.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "ESTADISTICAS_JPT")
public class EstadisticasJPTVO {

	@Id
	@Column(name = "ID_ESTADISTICAS_JPT")
	@SequenceGenerator(name = "estadisticas_jpt_secuencia", sequenceName = "ESTADISTICAS_JPT_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "estadisticas_jpt_secuencia")
	private Long idEstadisticas;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuarioVO;

	@Column(name = "CANTIDAD")
	private Long cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_ESTADISTICAS")
	private TipoEstadisticasJPTVO tipoEstadisticasJPTVO;

	@Column(name = "JEFATURA_JPT")
	private String jefaturaJpt;

	public Long getIdEstadisticas() {
		return idEstadisticas;
	}

	public void setIdEstadisticas(Long idEstadisticas) {
		this.idEstadisticas = idEstadisticas;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public TipoEstadisticasJPTVO getTipoEstadisticasJPTVO() {
		return tipoEstadisticasJPTVO;
	}

	public void setTipoEstadisticasJPTVO(TipoEstadisticasJPTVO tipoEstadisticasJPTVO) {
		this.tipoEstadisticasJPTVO = tipoEstadisticasJPTVO;
	}

	public String getJefaturaJpt() {
		return jefaturaJpt;
	}

	public void setJefaturaJpt(String jefaturaJpt) {
		this.jefaturaJpt = jefaturaJpt;
	}
}
