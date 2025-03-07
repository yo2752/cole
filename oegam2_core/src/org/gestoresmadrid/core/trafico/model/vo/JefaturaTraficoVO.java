package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.utilidades.listas.ListsOperator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the JEFATURA_TRAFICO database table.
 */
@Entity
@Table(name = "JEFATURA_TRAFICO")
public class JefaturaTraficoVO implements Serializable {

	private static final long serialVersionUID = 6297909258030274346L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "JEFATURA_PROVINCIAL")
	private String jefaturaProvincial;

	private String descripcion;

	private String jefatura;

	private String sucursal;

	// bi-directional many-to-one association to Contrato
	@OneToMany(mappedBy = "jefaturaTrafico", fetch = FetchType.LAZY)
	private Set<ContratoVO> contratos;

	// bi-directional many-to-one association to ProvinciaVO
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROVINCIA")
	private ProvinciaVO provincia;

	public JefaturaTraficoVO() {}

	public String getJefaturaProvincial() {
		return this.jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJefatura() {
		return this.jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
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

	public ProvinciaVO getProvincia() {
		return this.provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

}