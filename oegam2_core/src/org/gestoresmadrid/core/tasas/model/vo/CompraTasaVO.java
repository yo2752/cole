package org.gestoresmadrid.core.tasas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.hibernate.annotations.Cascade;


/**
 * The persistent class for the COMPRA_TASAS database table.
 * 
 */
@NamedQueries({
	@NamedQuery(name = CompraTasaVO.CAMBIO_ESTADO, query = CompraTasaVO.CAMBIO_ESTADO_QUERY),
	@NamedQuery(name = CompraTasaVO.CAMBIO_REFERENCIA_PROPIA, query = CompraTasaVO.CAMBIO_REFERENCIA_PROPIA_QUERY) })
@Entity
@Table(name="COMPRA_TASAS")
public class CompraTasaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3262644163290721082L;

	public static final String CAMBIO_ESTADO = "CompraTasaVO.cambioEstado";
	static final String CAMBIO_ESTADO_QUERY = "UPDATE CompraTasaVO c SET c.estado = :estado, c.respuesta = :respuesta where c.idCompra = :idCompra";

	public static final String CAMBIO_REFERENCIA_PROPIA = "CompraTasaVO.cambioReferenciaPropia";
	static final String CAMBIO_REFERENCIA_PROPIA_QUERY = "UPDATE CompraTasaVO c SET c.refPropia = :refPropia where c.idCompra = :idCompra";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="compra_tasas_seq_gen")
	@SequenceGenerator(name="compra_tasas_seq_gen", sequenceName="SEC_COMPRA_SEQ")
	@Column(name="ID_COMPRA")
	private Long idCompra;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_COMPRA")
	private Date fechaCompra;

	@Column(name="FORMA_PAGO")
	private BigDecimal formaPago;

	@Column(name="IMPORTE_TOTAL_TASAS")
	private BigDecimal importeTotalTasas;

	@Column(name="N_AUTOLIQUIDACION")
	private String nAutoliquidacion;

	@Column(name = "NRC")
	private String nrc;

	@Column(name="NUM_JUSTIFICANTE_791")
	private String numJustificante791;

	//bi-directional many-to-one association to DesgloseCompraTasa
	@OneToMany(mappedBy = "compraTasa", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<DesgloseCompraTasa> desgloseCompraTasas;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="ID_CONTRATO")
	//, updatable=false, insertable=false
	private ContratoVO contrato;

	@Column(name = "DATOS_BANCARIOS")
	private String datosBancarios;

	@Column(name = "RESPUESTA")
	private String respuesta;

	@Column(name = "CSV")
	private String csv;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	public CompraTasaVO() {
	}

	public Long getIdCompra() {
		return this.idCompra;
	}

	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaCompra() {
		return this.fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public BigDecimal getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(BigDecimal formaPago) {
		this.formaPago = formaPago;
	}

	public BigDecimal getImporteTotalTasas() {
		return this.importeTotalTasas;
	}

	public void setImporteTotalTasas(BigDecimal importeTotalTasas) {
		this.importeTotalTasas = importeTotalTasas;
	}

	public String getNAutoliquidacion() {
		return this.nAutoliquidacion;
	}

	public void setNAutoliquidacion(String nAutoliquidacion) {
		this.nAutoliquidacion = nAutoliquidacion;
	}

	public String getNrc() {
		return this.nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNumJustificante791() {
		return this.numJustificante791;
	}

	public void setNumJustificante791(String numJustificante791) {
		this.numJustificante791 = numJustificante791;
	}

	public List<DesgloseCompraTasa> getDesgloseCompraTasas() {
		return this.desgloseCompraTasas;
	}

	public void setDesgloseCompraTasas(List<DesgloseCompraTasa> desgloseCompraTasas) {
		this.desgloseCompraTasas = desgloseCompraTasas;
	}

	public String getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(String datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public DesgloseCompraTasa addDesgloseCompraTasa(DesgloseCompraTasa desgloseCompraTasa) {
		if (getDesgloseCompraTasas()==null) {
			setDesgloseCompraTasas(new ArrayList<DesgloseCompraTasa>());
		}
		getDesgloseCompraTasas().add(desgloseCompraTasa);
		desgloseCompraTasa.setCompraTasa(this);

		return desgloseCompraTasa;
	}

	public DesgloseCompraTasa removeDesgloseCompraTasa(DesgloseCompraTasa desgloseCompraTasa) {
		getDesgloseCompraTasas().remove(desgloseCompraTasa);
		desgloseCompraTasa.setCompraTasa(null);

		return desgloseCompraTasa;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}
	
}