package org.gestoresmadrid.core.facturacionDistintivo.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;

@Entity
@Table(name = "DISTINTIVOS_FACTURADOS")
public class FacturacionDistintivoVO implements Serializable {

	private static final long serialVersionUID = -846886797016646367L;

	@Id
	@SequenceGenerator(name = "secuencia_dstv_facturacion", sequenceName = "ID_DSTV_FACTURADO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_dstv_facturacion")
	@Column(name = "ID")
	private Long idDistintivoFacturado;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "ID_DOC_DSTV")
	private Long idDocPermDistItv;

	@Column(name = "TIPO_DSTV")
	private String tipoDistintivo;

	@Column(name = "TOTAL_FACTURADO_DSTV")
	private Long total;

	@Column(name = "TOTAL_FACTURADO_DUP")
	private Long totalDup;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FACTURACION_DSTV", referencedColumnName = "ID", insertable = false, updatable = false)
	private List<FacturacionDstvIncidenciaVO> listadoIncidencias;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO", insertable = false, updatable = false)
	private ContratoVO contrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOC_DSTV", referencedColumnName = "ID", insertable = false, updatable = false)
	private DocPermDistItvVO docDistintivo;

	public Long getTotalDup() {
		return totalDup;
	}

	public void setTotalDup(Long totalDup) {
		this.totalDup = totalDup;
	}

	public Long getIdDistintivoFacturado() {
		return idDistintivoFacturado;
	}

	public void setIdDistintivoFacturado(Long idDistintivoFacturado) {
		this.idDistintivoFacturado = idDistintivoFacturado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdDocPermDistItv() {
		return idDocPermDistItv;
	}

	public void setIdDocPermDistItv(Long idDocPermDistItv) {
		this.idDocPermDistItv = idDocPermDistItv;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public DocPermDistItvVO getDocDistintivo() {
		return docDistintivo;
	}

	public void setDocDistintivo(DocPermDistItvVO docDistintivo) {
		this.docDistintivo = docDistintivo;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<FacturacionDstvIncidenciaVO> getListadoIncidencias() {
		return listadoIncidencias;
	}

	public void setListadoIncidencias(List<FacturacionDstvIncidenciaVO> listadoIncidencias) {
		this.listadoIncidencias = listadoIncidencias;
	}

}