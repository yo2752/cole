package org.gestoresmadrid.core.impr.model.vo;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name = "EVOLUCION_IMPR_KO")
public class EvolucionImprKoVO implements Serializable {

	private static final long serialVersionUID = -7099465224511847575L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "id_evo_impr_ko_secuencia", sequenceName = "ID_EVO_IMPR_KO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id_evo_impr_ko_secuencia")
	Long id;

	@Column(name = "FECHA")
	Date fecha;

	@Column(name = "ID_USUARIO")
	Long idUsuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
	UsuarioVO usuario;

	@Column(name = "ID_IMPR")
	Long idImprKo;

	@Column(name = "ESTADO_ANTERIOR")
	String estadoAnterior;

	@Column(name = "ESTADO_NUEVO")
	String estadoNuevo;

	@Column(name = "TIPO_IMPR")
	String tipoImpr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Long getIdImprKo() {
		return idImprKo;
	}

	public void setIdImprKo(Long idImprKo) {
		this.idImprKo = idImprKo;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getTipoImpr() {
		return tipoImpr;
	}

	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}
}
