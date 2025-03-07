package hibernate.entities.trafico;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TRAMITE_TRAF_MATR database table.
 * 
 */
@Entity
@Table(name="TRAMITE_TRAF_MATR")
public class TramiteTrafMatr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NUM_EXPEDIENTE")
	private long numExpediente;

	@Column(name="A_DEDUCIR_576")
	private BigDecimal aDeducir576;

	@Column(name="BASE_IMPO_REDUCIDA_576")
	private BigDecimal baseImpoReducida576;

	@Column(name="BASE_IMPONIBLE_576")
	private BigDecimal baseImponible576;

	@Column(name="CAUSA_HECHO_IMPON_576")
	private String causaHechoImpon576;

	@Column(name="CUOTA_576")
	private BigDecimal cuota576;

	@Column(name="CUOTA_INGRESAR_576")
	private BigDecimal cuotaIngresar576;

	@Column(name="DEDUCCION_LINEAL_576")
	private BigDecimal deduccionLineal576;

	@Column(name="EJERCICIO_576")
	private BigDecimal ejercicio576;

	@Column(name="ENTREGA_FACT_MATRICULACION")
	private String entregaFactMatriculacion;

	@Column(name="EXENTO_576")
	private String exento576;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_PAGO_576")
	private Date fechaPago576;

	@Column(name="ID_NO_SUJECCION_06")
	private String idNoSujeccion06;

	@Column(name="ID_REDUCCION_05")
	private String idReduccion05;

	@Column(name="IMPORTE_576")
	private BigDecimal importe576;

	@Column(name="JUSTIFICADO_IVTM")
	private String justificadoIvtm;

	@Column(name="RESPUESTA_EITV")
	private String respuestaEitv;

	@Column(name="LIQUIDACION_576")
	private BigDecimal liquidacion576;

	@Column(name="N_DECLARACION_COMP_576")
	private String nDeclaracionComp576;

	@Column(name="NRC_576")
	private String nrc576;

	@Column(name="OBSERVACIONES_576")
	private String observaciones576;

	@Column(name="REDUCCION_576")
	private String reduccion576;

	@Column(name="TIPO_GRAVAMEN_576")
	private BigDecimal tipoGravamen576;

	@Column(name="TIPO_TRAMITE_MATR")
	private String tipoTramiteMatr;

	//bi-directional one-to-one association to TramiteTrafico
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tramiteTrafMatr")
	//@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private List<TramiteTrafico> tramiteTrafico;

	//TODO MPC. Cambio IVTM. Añadida la relación con IVTM_MATRICULACION.
	//bi-directional one-to-one association to ivtmMatriculacion
	@ManyToOne (fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
	@JoinColumn(name="NUM_EXPEDIENTE", insertable=false, updatable=false)
	private IvtmMatriculacion ivtmMatriculacion;

	public TramiteTrafMatr() {
	}

	public long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getADeducir576() {
		return this.aDeducir576;
	}

	public void setADeducir576(BigDecimal aDeducir576) {
		this.aDeducir576 = aDeducir576;
	}

	public BigDecimal getBaseImpoReducida576() {
		return this.baseImpoReducida576;
	}

	public void setBaseImpoReducida576(BigDecimal baseImpoReducida576) {
		this.baseImpoReducida576 = baseImpoReducida576;
	}

	public BigDecimal getBaseImponible576() {
		return this.baseImponible576;
	}

	public void setBaseImponible576(BigDecimal baseImponible576) {
		this.baseImponible576 = baseImponible576;
	}

	public String getCausaHechoImpon576() {
		return this.causaHechoImpon576;
	}

	public void setCausaHechoImpon576(String causaHechoImpon576) {
		this.causaHechoImpon576 = causaHechoImpon576;
	}

	public BigDecimal getCuota576() {
		return this.cuota576;
	}

	public void setCuota576(BigDecimal cuota576) {
		this.cuota576 = cuota576;
	}

	public BigDecimal getCuotaIngresar576() {
		return this.cuotaIngresar576;
	}

	public void setCuotaIngresar576(BigDecimal cuotaIngresar576) {
		this.cuotaIngresar576 = cuotaIngresar576;
	}

	public BigDecimal getDeduccionLineal576() {
		return this.deduccionLineal576;
	}

	public void setDeduccionLineal576(BigDecimal deduccionLineal576) {
		this.deduccionLineal576 = deduccionLineal576;
	}

	public BigDecimal getEjercicio576() {
		return this.ejercicio576;
	}

	public void setEjercicio576(BigDecimal ejercicio576) {
		this.ejercicio576 = ejercicio576;
	}

	public String getEntregaFactMatriculacion() {
		return this.entregaFactMatriculacion;
	}

	public void setEntregaFactMatriculacion(String entregaFactMatriculacion) {
		this.entregaFactMatriculacion = entregaFactMatriculacion;
	}

	public String getExento576() {
		return this.exento576;
	}

	public void setExento576(String exento576) {
		this.exento576 = exento576;
	}

	public Date getFechaPago576() {
		return this.fechaPago576;
	}

	public void setFechaPago576(Date fechaPago576) {
		this.fechaPago576 = fechaPago576;
	}

	public String getIdNoSujeccion06() {
		return this.idNoSujeccion06;
	}

	public void setIdNoSujeccion06(String idNoSujeccion06) {
		this.idNoSujeccion06 = idNoSujeccion06;
	}

	public String getIdReduccion05() {
		return this.idReduccion05;
	}

	public void setIdReduccion05(String idReduccion05) {
		this.idReduccion05 = idReduccion05;
	}

	public BigDecimal getImporte576() {
		return this.importe576;
	}

	public void setImporte576(BigDecimal importe576) {
		this.importe576 = importe576;
	}
	
	public String getJustificadoIvtm() {
		return this.justificadoIvtm;
	}

	public void setJustificadoIvtm(String justificadoIvtm) {
		this.justificadoIvtm = justificadoIvtm;
	}

	public BigDecimal getLiquidacion576() {
		return this.liquidacion576;
	}

	public void setLiquidacion576(BigDecimal liquidacion576) {
		this.liquidacion576 = liquidacion576;
	}

	public String getNDeclaracionComp576() {
		return this.nDeclaracionComp576;
	}

	public void setNDeclaracionComp576(String nDeclaracionComp576) {
		this.nDeclaracionComp576 = nDeclaracionComp576;
	}

	public String getNrc576() {
		return this.nrc576;
	}

	public void setNrc576(String nrc576) {
		this.nrc576 = nrc576;
	}

	public String getObservaciones576() {
		return this.observaciones576;
	}

	public void setObservaciones576(String observaciones576) {
		this.observaciones576 = observaciones576;
	}

	public String getReduccion576() {
		return this.reduccion576;
	}

	public void setReduccion576(String reduccion576) {
		this.reduccion576 = reduccion576;
	}

	public BigDecimal getTipoGravamen576() {
		return this.tipoGravamen576;
	}

	public void setTipoGravamen576(BigDecimal tipoGravamen576) {
		this.tipoGravamen576 = tipoGravamen576;
	}

	public String getTipoTramiteMatr() {
		return this.tipoTramiteMatr;
	}

	public void setTipoTramiteMatr(String tipoTramiteMatr) {
		this.tipoTramiteMatr = tipoTramiteMatr;
	}

	public List<TramiteTrafico> getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(List<TramiteTrafico> tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	//TODO MPC. Cambio IVTM. Añadido el get y set del ivtmMatriculacion	
	public void setIvtmMatriculacion(IvtmMatriculacion ivtmMatriculacion) {
		this.ivtmMatriculacion= ivtmMatriculacion;
	}

	public IvtmMatriculacion getIvtmMatriculacion() {
		return ivtmMatriculacion;
	}

	public String getRespuestaEitv() {
		return respuestaEitv;
	}

	public void setRespuestaEitv(String respuestaEitv) {
		this.respuestaEitv = respuestaEitv;
	}

}