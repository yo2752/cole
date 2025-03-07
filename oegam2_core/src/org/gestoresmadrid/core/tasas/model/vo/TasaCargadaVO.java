package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

/**
 * The persistent class for the TASA database table.
 */
@Entity
@Table(name = "TASA_CARGADA")
public class TasaCargadaVO implements Serializable {

	private static final long serialVersionUID = 6399529314415305446L;

	@Id
	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ASIGNACION")
	private Date fechaAsignacion;

	// bi-directional many-to-one association to Contrato
	@ManyToOne
	@JoinColumn(name = "ID_CONTRATO")
	private ContratoVO contrato;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	@Column(name = "TIPO_TASA")
	private String tipoTasa;

	@ManyToOne
	@JoinColumn(name = "NUM_EXPEDIENTE")
	private TramiteTraficoVO tramiteTrafico;

	@Column(name = "IMPORTADO_ICOGAM")
	private BigDecimal importadoIcogam;

	private Integer formato;

	@Column(name = "BLOQUEADA")
	private String bloqueada;
	
	@Column(name = "ALMACEN")
	private String tipoAlmacen;

	public TasaCargadaVO() {}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public BigDecimal getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(BigDecimal importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}

	public String getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(String bloqueada) {
		this.bloqueada = bloqueada;
	}

	public String getTipoAlmacen() {
		return tipoAlmacen;
	}

	public void setTipoAlmacen(String tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}

	public Integer getFormato() {
		return formato;
	}

	public void setFormato(Integer formato) {
		this.formato = formato;
	}
}