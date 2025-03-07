package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Proxy;

/**
 * The persistent class for the TASA database table.
 */
@Entity
@Table(name = "TASA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("CASE WHEN ALMACEN = 'ALMACEN_MATW' then 'ALMACEN_MATW'" + 
						"WHEN ALMACEN = 'ALMACEN_CTIT' then 'ALMACEN_CTIT'" +
						"WHEN ALMACEN = 'ALMACEN_BAJA' then 'ALMACEN_BAJA'" +
						"WHEN ALMACEN = 'ALMACEN_DUPLICADO' then 'ALMACEN_DUPLICADO'" +
						"WHEN ALMACEN = 'ALMACEN_INTEVE' then 'ALMACEN_INTEVE'" +
						"WHEN ALMACEN = 'ALMACEN_PERMISO_INT' then 'ALMACEN_PERMISO_INT'" +
						"end")
@DiscriminatorValue("null")
@Proxy(lazy = false)

public class TasaVO implements Serializable {

	private static final long serialVersionUID = 1176351296252368011L;

	@Id
	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ASIGNACION")
	private Date fechaAsignacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN_VIGENCIA")
	private Date fechaFinVigencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO")
	private ContratoVO contrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	private BigDecimal precio;

	@Column(name = "TIPO_TASA")
	private String tipoTasa;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@OneToMany(mappedBy = "tasa")
	private Set<EvolucionTasaVO> evolucionTasas;

	@Column(name = "COMENTARIOS")
	private String comentarios;

	@Column(name = "IMPORTADO_ICOGAM")
	private BigDecimal importadoIcogam;

	@Column(name = "BLOQUEADA")
	private String bloqueada;

	@Column(name = "ALMACEN")
	private String tipoAlmacen;

	/*
	 * @Column(name = "COMBO_TASA_BLOQUEADO") private String comboTasaBloqueado;
	 */

	public TasaVO() {}

	public String getCodigoTasa() {
		return this.codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaFinVigencia() {
		return this.fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public ContratoVO getContrato() {
		return this.contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public UsuarioVO getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getTipoTasa() {
		return this.tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public Set<EvolucionTasaVO> getEvolucionTasas() {
		return this.evolucionTasas;
	}

	public List<EvolucionTasaVO> getEvolucionTasasAsList() {
		// Map from Set to List
		List<EvolucionTasaVO> lista = new ArrayList<EvolucionTasaVO>();
		if (evolucionTasas != null) {
			lista.addAll(evolucionTasas);
		}
		return lista;
	}

	public EvolucionTasaVO getFirstElementEvolucionTasa() {
		ListsOperator<EvolucionTasaVO> listOperator = new ListsOperator<EvolucionTasaVO>();
		return listOperator.getFirstElement(getEvolucionTasas());
	}

	public void setEvolucionTasas(Set<EvolucionTasaVO> evolucionTasas) {
		this.evolucionTasas = evolucionTasas;
	}

	public void setEvolucionTasas(List<EvolucionTasaVO> evolucionTasas) {
		if (evolucionTasas == null) {
			this.evolucionTasas = null;
		} else {
			// Map from List to Set
			Set<EvolucionTasaVO> set = new HashSet<EvolucionTasaVO>();
			set.addAll(evolucionTasas);
			this.evolucionTasas = set;
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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

	/*
	 * public String getComboTasaBloqueado() { return comboTasaBloqueado; }
	 * 
	 * public void setComboTasaBloqueado(String comboTasaBloqueado) {
	 * this.comboTasaBloqueado = comboTasaBloqueado; }
	 */

}