package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.gestoresmadrid.utilidades.listas.ListsOperator;

/**
 * The persistent class for the TIPO_CREDITO database table.
 */
@Entity
@Table(name = "TIPO_CREDITO")
public class TipoCreditoVO implements Serializable {

	private static final long serialVersionUID = -5644583407134767398L;

	@Id
	@Column(name = "TIPO_CREDITO")
	private String tipoCredito;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "IMPORTE")
	private BigDecimal importe;

	@Column(name = "INCRE_DECRE")
	private String increDecre;

	@Column(name = "ORDEN_LISTADO")
	private BigDecimal ordenListado;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoCredito")
	@JoinTable(name = "TIPO_CREDITO")
	private Set<HistoricoCreditoVO> historicoCredito;

	public TipoCreditoVO() {}

	public String getTipoCredito() {
		return this.tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getIncreDecre() {
		return this.increDecre;
	}

	public void setIncreDecre(String increDecre) {
		this.increDecre = increDecre;
	}

	public BigDecimal getOrdenListado() {
		return this.ordenListado;
	}

	public void setOrdenListado(BigDecimal ordenListado) {
		this.ordenListado = ordenListado;
	}

	public Set<HistoricoCreditoVO> getHistoricoCredito() {
		return historicoCredito;
	}

	public List<HistoricoCreditoVO> getHistoricoCreditoAsList() {
		// Map from Set to List
		List<HistoricoCreditoVO> lista = new ArrayList<HistoricoCreditoVO>();
		if (historicoCredito != null) {
			lista.addAll(historicoCredito);
		}
		return lista;
	}

	public HistoricoCreditoVO getFirstElementHistoricoCredito() {
		ListsOperator<HistoricoCreditoVO> listOperator = new ListsOperator<HistoricoCreditoVO>();
		return listOperator.getFirstElement(getHistoricoCredito());
	}

	public void setHistoricoCredito(Set<HistoricoCreditoVO> historicoCredito) {
		this.historicoCredito = historicoCredito;
	}

	public void setHistoricoCredito(List<HistoricoCreditoVO> historicoCredito) {
		if (historicoCredito == null) {
			this.historicoCredito = null;
		} else {
			// Map from List to Set
			Set<HistoricoCreditoVO> set = new HashSet<HistoricoCreditoVO>();
			set.addAll(historicoCredito);
			this.historicoCredito = set;
		}
	}

}