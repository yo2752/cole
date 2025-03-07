package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;

/**
 * The persistent class for the HISTORICO_CREDITOS database table.
 */
@Entity
@Table(name = "HISTORICO_CREDITOS")
public class HistoricoCreditoVO implements Serializable {

	private static final long serialVersionUID = 9186194028605576417L;

	@Id
	@Column(name = "ID_HISTORICO_CREDITO")
	@SequenceGenerator(name = "historico_credito_secuencia", sequenceName = "ID_HISTORICO_CREDITO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "historico_credito_secuencia")
	private Long idHistoricoCredito;

	@Column(name = "CANTIDAD_RESTADA")
	private BigDecimal cantidadRestada;

	@Column(name = "CANTIDAD_SUMADA")
	private BigDecimal cantidadSumada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
	private UsuarioVO usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO", insertable = false, updatable = false),
		@JoinColumn(name = "TIPO_CREDITO", referencedColumnName = "TIPO_CREDITO", insertable = false, updatable = false) })
	private CreditoVO credito;

	@Column(name = "TIPO_CREDITO")
	private String tipoCredito;

	public HistoricoCreditoVO() {}

	public Long getIdHistoricoCredito() {
		return idHistoricoCredito;
	}

	public void setIdHistoricoCredito(Long idHistoricoCredito) {
		this.idHistoricoCredito = idHistoricoCredito;
	}

	public BigDecimal getCantidadRestada() {
		return cantidadRestada;
	}

	public void setCantidadRestada(BigDecimal cantidadRestada) {
		this.cantidadRestada = cantidadRestada;
	}

	public BigDecimal getCantidadSumada() {
		return cantidadSumada;
	}

	public void setCantidadSumada(BigDecimal cantidadSumada) {
		this.cantidadSumada = cantidadSumada;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public CreditoVO getCredito() {
		return credito;
	}

	public void setCredito(CreditoVO credito) {
		this.credito = credito;
	}
}