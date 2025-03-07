package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the EVOLUCION_VEHICULO database table.
 */
@Entity
@Table(name = "EVOLUCION_VEHICULO")
public class EvolucionVehiculoVO implements Serializable {

	private static final long serialVersionUID = -1181157325224390928L;

	@EmbeddedId
	private EvolucionVehiculoPK id;

	@Column(name = "BASTIDOR_ANT")
	private String bastidorAnt;

	@Column(name = "BASTIDOR_NUE")
	private String bastidorNue;

	@Column(name = "CODIGO_ITV_ANT")
	private String codigoItvAnt;

	@Column(name = "CODIGO_ITV_NUE")
	private String codigoItvNue;

	@Column(name = "MATRICULA_ANT")
	private String matriculaAnt;

	@Column(name = "MATRICULA_NUE")
	private String matriculaNue;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	private String otros;

	@Column(name = "TIPO_ACTUALIZACION")
	private String tipoActualizacion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@Column(name = "TIPO_VEHICULO_ANT")
	private String tipoVehiculoAnt;

	@Column(name = "TIPO_VEHICULO_NUE")
	private String tipoVehiculoNue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Column(name = "ID_VEHICULO_ORIGEN")
	private Long idVehiculoOrigen;

	public EvolucionVehiculoVO() {}

	public EvolucionVehiculoPK getId() {
		return id;
	}

	public void setId(EvolucionVehiculoPK id) {
		this.id = id;
	}

	public String getBastidorAnt() {
		return bastidorAnt;
	}

	public void setBastidorAnt(String bastidorAnt) {
		this.bastidorAnt = bastidorAnt;
	}

	public String getBastidorNue() {
		return bastidorNue;
	}

	public void setBastidorNue(String bastidorNue) {
		this.bastidorNue = bastidorNue;
	}

	public String getCodigoItvAnt() {
		return codigoItvAnt;
	}

	public void setCodigoItvAnt(String codigoItvAnt) {
		this.codigoItvAnt = codigoItvAnt;
	}

	public String getCodigoItvNue() {
		return codigoItvNue;
	}

	public void setCodigoItvNue(String codigoItvNue) {
		this.codigoItvNue = codigoItvNue;
	}

	public String getMatriculaAnt() {
		return matriculaAnt;
	}

	public void setMatriculaAnt(String matriculaAnt) {
		this.matriculaAnt = matriculaAnt;
	}

	public String getMatriculaNue() {
		return matriculaNue;
	}

	public void setMatriculaNue(String matriculaNue) {
		this.matriculaNue = matriculaNue;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getTipoActualizacion() {
		return tipoActualizacion;
	}

	public void setTipoActualizacion(String tipoActualizacion) {
		this.tipoActualizacion = tipoActualizacion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoVehiculoAnt() {
		return tipoVehiculoAnt;
	}

	public void setTipoVehiculoAnt(String tipoVehiculoAnt) {
		this.tipoVehiculoAnt = tipoVehiculoAnt;
	}

	public String getTipoVehiculoNue() {
		return tipoVehiculoNue;
	}

	public void setTipoVehiculoNue(String tipoVehiculoNue) {
		this.tipoVehiculoNue = tipoVehiculoNue;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Long getIdVehiculoOrigen() {
		return idVehiculoOrigen;
	}

	public void setIdVehiculoOrigen(Long idVehiculoOrigen) {
		this.idVehiculoOrigen = idVehiculoOrigen;
	}
}