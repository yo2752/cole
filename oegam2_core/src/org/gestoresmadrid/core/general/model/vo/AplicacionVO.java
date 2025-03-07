package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

/**
 * The persistent class for the APLICACION database table.
 */
@Entity
@Table(name = "APLICACION")
public class AplicacionVO implements Serializable {

	private static final long serialVersionUID = -709625101952478109L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CODIGO_APLICACION")
	private String codigoAplicacion;

	@Column(name = "\"ALIAS\"")
	private String alias;

	@Column(name = "DESC_APLICACION")
	private String descAplicacion;

	// bi-directional many-to-many association to Contrato
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CONTRATO_APLICACION", joinColumns = { @JoinColumn(name = "CODIGO_APLICACION") }, inverseJoinColumns = { @JoinColumn(name = "ID_CONTRATO") })
	private Set<ContratoVO> contratos;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="APLICACION_URL",joinColumns = { @JoinColumn(name="CODIGO_APLICACION") }, inverseJoinColumns={ @JoinColumn(name="CODIGO_URL") })
	private List<UrlVO> urls;


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "aplicacion")
	private Set<FuncionVO> funciones;

	public AplicacionVO() {}

	public String getCodigoAplicacion() {
		return this.codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescAplicacion() {
		return this.descAplicacion;
	}

	public void setDescAplicacion(String descAplicacion) {
		this.descAplicacion = descAplicacion;
	}

	public Set<ContratoVO> getContratos() {
		return contratos;
	}

	public List<ContratoVO> getContratosAsList() {
		// Map from Set to List
		List<ContratoVO> lista = new ArrayList<ContratoVO>();
		if (contratos != null) {
			lista.addAll(contratos);
		}
		return lista;
	}

	public ContratoVO getFirstElementContrato() {
		ListsOperator<ContratoVO> listOperator = new ListsOperator<ContratoVO>();
		return listOperator.getFirstElement(getContratos());
	}

	public void setContratos(Set<ContratoVO> contratos) {
		this.contratos = contratos;
	}

	public void setContratos(List<ContratoVO> contratos) {
		if (contratos == null) {
			this.contratos = null;
		} else {
			// Map from List to Set
			Set<ContratoVO> set = new HashSet<ContratoVO>();
			set.addAll(contratos);
			this.contratos = set;
		}
	}

	public Set<FuncionVO> getFunciones() {
		return funciones;
	}

	public List<FuncionVO> getFuncionesAsList() {
		// Map from Set to List
		List<FuncionVO> lista = new ArrayList<FuncionVO>();
		if (funciones != null) {
			lista.addAll(funciones);
		}
		return lista;
	}

	public FuncionVO getFirstElementFuncion() {
		ListsOperator<FuncionVO> listOperator = new ListsOperator<FuncionVO>();
		return listOperator.getFirstElement(getFunciones());
	}

	public void setFunciones(Set<FuncionVO> funciones) {
		this.funciones = funciones;
	}

	public void setFunciones(List<FuncionVO> funciones) {
		if (funciones == null) {
			this.funciones = null;
		} else {
			// Map from List to Set
			Set<FuncionVO> set = new HashSet<FuncionVO>();
			set.addAll(funciones);
			this.funciones = set;
		}
	}

	public List<UrlVO> getUrls() {
		return urls;
	}

	public void setUrls(List<UrlVO> urls) {
		this.urls = urls;
	}
}