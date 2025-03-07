package org.gestoresmadrid.core.trafico.model.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.hibernate.annotations.FetchMode;

@Entity
@SecondaryTable(name = "TRAMITE_TRAF_MATR")
@org.hibernate.annotations.Table(appliesTo = "TRAMITE_TRAF_MATR", fetch = FetchMode.SELECT)
@DiscriminatorValue("T1")
public class TramiteTrafMatrVO extends TramiteTraficoVO {

	private static final long serialVersionUID = 6402061428065329079L;

	@Column(table = "TRAMITE_TRAF_MATR", name = "BASE_IMPONIBLE_576")
	private BigDecimal baseImponible576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "REDUCCION_576")
	private String reduccion576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "BASE_IMPO_REDUCIDA_576")
	private BigDecimal baseImpoReducida576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "TIPO_GRAVAMEN_576")
	private BigDecimal tipoGravamen576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "CUOTA_576")
	private BigDecimal cuota576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "DEDUCCION_LINEAL_576")
	private BigDecimal deduccionLineal576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "CUOTA_INGRESAR_576")
	private BigDecimal cuotaIngresar576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "A_DEDUCIR_576")
	private BigDecimal aDeducir576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "LIQUIDACION_576")
	private BigDecimal liquidacion576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "N_DECLARACION_COMP_576")
	private String nDeclaracionComp576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "EJERCICIO_576")
	private BigDecimal ejercicio576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "CAUSA_HECHO_IMPON_576")
	private String causaHechoImpon576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "OBSERVACIONES_576")
	private String observaciones576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "EXENTO_576")
	private String exento576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "NRC_576")
	private String nrc576;

	@Temporal(TemporalType.DATE)
	@Column(table = "TRAMITE_TRAF_MATR", name = "FECHA_PAGO_576")
	private Date fechaPago576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "ID_REDUCCION_05")
	private String idReduccion05;

	@Column(table = "TRAMITE_TRAF_MATR", name = "ID_NO_SUJECCION_06")
	private String idNoSujeccion06;

	@Column(table = "TRAMITE_TRAF_MATR", name = "IMPORTE_576")
	private BigDecimal importe576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "ENTREGA_FACT_MATRICULACION")
	private String entregaFactMatriculacion;

	@Column(table = "TRAMITE_TRAF_MATR", name = "TIPO_TRAMITE_MATR")
	private String tipoTramiteMatr;

	@Column(table = "TRAMITE_TRAF_MATR", name = "JUSTIFICADO_IVTM")
	private String justificadoIvtm;

	@Column(table = "TRAMITE_TRAF_MATR", name = "RESPUESTA_EITV")
	private String respuestaEitv;

	@Column(table = "TRAMITE_TRAF_MATR", name = "PEGATINA")
	private String pegatina;

	@Column(table = "TRAMITE_TRAF_MATR", name = "RESPUESTA_576")
	private String respuesta576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "CARSHARING")
	private String carsharing;

	@Column(table = "TRAMITE_TRAF_MATR", name = "DOC_DISTINTIVO")
	private Long docDistintivo;

	@Column(table = "TRAMITE_TRAF_MATR", name = "DISTINTIVO")
	private String distintivo;

	@Column(table = "TRAMITE_TRAF_MATR", name = "TIPO_DISTINTIVO")
	private String tipoDistintivo;

	@Column(table = "TRAMITE_TRAF_MATR", name = "NUM_SERIE_DSTV")
	private String numSerieDistintivo;

	@Column(table = "TRAMITE_TRAF_MATR", name = "ESTADO_PET_PERM_DSTV")
	private String estadoPetPermDstv;

	@Column(table = "TRAMITE_TRAF_MATR", name = "RESP_PET_PERM_DSTV")
	private String respPetPermDstv;

	@Column(table = "TRAMITE_TRAF_MATR", name = "ESTADO_IMP_DSTV")
	private String estadoImpDstv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(table = "TRAMITE_TRAF_MATR", name = "FECHA_HORA")
	private Date fechaSolicitudDistintivo;

	@Column(table = "TRAMITE_TRAF_MATR", name = "CHECK_JUSTIF_FICHERO_IVTM")
	private String checkJustifFicheroIvtm;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private IvtmMatriculacionVO ivtmMatriculacionVO;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private LiberacionEEFFVO liberacionEEFF;

	@OneToMany(mappedBy = "tramiteTraficoMatriculacion")
	private Set<ConsultaEEFFVO> listaConsultaEEFF;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(table = "TRAMITE_TRAF_MATR", name = "DOC_DISTINTIVO", referencedColumnName = "ID", insertable = false, updatable = false)
	private DocPermDistItvVO docDistintivoVO;

	@Column(table = "TRAMITE_TRAF_MATR", name = "ESTADO_576")
	private String estado576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "EXPEDIENTE_REF_576")
	private String expedienteRef576;

	@Column(table = "TRAMITE_TRAF_MATR", name = "CODIGO_VERIFICACION_576")
	private String codigoVerificacion576;
	
	@Column(table = "TRAMITE_TRAF_MATR", name = "RESP_BLOQUEO_BASTIDOR")
	private String respBloqBastidor;
	
	@Column(table="TRAMITE_TRAF_MATR", name = "EXENCION_CTR")
	private String exentoCtr;
	
	@Column(table="TRAMITE_TRAF_MATR", name = "AUTORIZADO_FICHA_A")
	private String autorizadoFichaA;
	
	@Column(table="TRAMITE_TRAF_MATR", name = "AUTORIZADO_EXENTO_CTR")
	private String autorizadoExentoCtr;
	   

	/**
	 * @return the baseImponible576
	 */
	public BigDecimal getBaseImponible576() {
		return baseImponible576;
	}

	/**
	 * @param baseImponible576 the baseImponible576 to set
	 */
	public void setBaseImponible576(BigDecimal baseImponible576) {
		this.baseImponible576 = baseImponible576;
	}

	/**
	 * @return the reduccion576
	 */
	public String getReduccion576() {
		return reduccion576;
	}

	/**
	 * @param reduccion576 the reduccion576 to set
	 */
	public void setReduccion576(String reduccion576) {
		this.reduccion576 = reduccion576;
	}

	/**
	 * @return the baseImpoReducida576
	 */
	public BigDecimal getBaseImpoReducida576() {
		return baseImpoReducida576;
	}

	/**
	 * @param baseImpoReducida576 the baseImpoReducida576 to set
	 */
	public void setBaseImpoReducida576(BigDecimal baseImpoReducida576) {
		this.baseImpoReducida576 = baseImpoReducida576;
	}

	/**
	 * @return the tipoGravamen576
	 */
	public BigDecimal getTipoGravamen576() {
		return tipoGravamen576;
	}

	/**
	 * @param tipoGravamen576 the tipoGravamen576 to set
	 */
	public void setTipoGravamen576(BigDecimal tipoGravamen576) {
		this.tipoGravamen576 = tipoGravamen576;
	}

	/**
	 * @return the cuota576
	 */
	public BigDecimal getCuota576() {
		return cuota576;
	}

	/**
	 * @param cuota576 the cuota576 to set
	 */
	public void setCuota576(BigDecimal cuota576) {
		this.cuota576 = cuota576;
	}

	/**
	 * @return the deduccionLineal576
	 */
	public BigDecimal getDeduccionLineal576() {
		return deduccionLineal576;
	}

	/**
	 * @param deduccionLineal576 the deduccionLineal576 to set
	 */
	public void setDeduccionLineal576(BigDecimal deduccionLineal576) {
		this.deduccionLineal576 = deduccionLineal576;
	}

	/**
	 * @return the cuotaIngresar576
	 */
	public BigDecimal getCuotaIngresar576() {
		return cuotaIngresar576;
	}

	/**
	 * @param cuotaIngresar576 the cuotaIngresar576 to set
	 */
	public void setCuotaIngresar576(BigDecimal cuotaIngresar576) {
		this.cuotaIngresar576 = cuotaIngresar576;
	}

	/**
	 * @return the aDeducir576
	 */
	public BigDecimal getaDeducir576() {
		return aDeducir576;
	}

	/**
	 * @param aDeducir576 the aDeducir576 to set
	 */
	public void setaDeducir576(BigDecimal aDeducir576) {
		this.aDeducir576 = aDeducir576;
	}

	/**
	 * @return the liquidacion576
	 */
	public BigDecimal getLiquidacion576() {
		return liquidacion576;
	}

	/**
	 * @param liquidacion576 the liquidacion576 to set
	 */
	public void setLiquidacion576(BigDecimal liquidacion576) {
		this.liquidacion576 = liquidacion576;
	}

	/**
	 * @return the nDeclaracionComp576
	 */
	public String getnDeclaracionComp576() {
		return nDeclaracionComp576;
	}

	/**
	 * @param nDeclaracionComp576 the nDeclaracionComp576 to set
	 */
	public void setnDeclaracionComp576(String nDeclaracionComp576) {
		this.nDeclaracionComp576 = nDeclaracionComp576;
	}

	/**
	 * @return the ejercicio576
	 */
	public BigDecimal getEjercicio576() {
		return ejercicio576;
	}

	/**
	 * @param ejercicio576 the ejercicio576 to set
	 */
	public void setEjercicio576(BigDecimal ejercicio576) {
		this.ejercicio576 = ejercicio576;
	}

	/**
	 * @return the causaHechoImpon576
	 */
	public String getCausaHechoImpon576() {
		return causaHechoImpon576;
	}

	/**
	 * @param causaHechoImpon576 the causaHechoImpon576 to set
	 */
	public void setCausaHechoImpon576(String causaHechoImpon576) {
		this.causaHechoImpon576 = causaHechoImpon576;
	}

	/**
	 * @return the observaciones576
	 */
	public String getObservaciones576() {
		return observaciones576;
	}

	/**
	 * @param observaciones576 the observaciones576 to set
	 */
	public void setObservaciones576(String observaciones576) {
		this.observaciones576 = observaciones576;
	}

	/**
	 * @return the exento576
	 */
	public String getExento576() {
		return exento576;
	}

	/**
	 * @param exento576 the exento576 to set
	 */
	public void setExento576(String exento576) {
		this.exento576 = exento576;
	}

	/**
	 * @return the nrc576
	 */
	public String getNrc576() {
		return nrc576;
	}

	/**
	 * @param nrc576 the nrc576 to set
	 */
	public void setNrc576(String nrc576) {
		this.nrc576 = nrc576;
	}

	/**
	 * @return the fechaPago576
	 */
	public Date getFechaPago576() {
		return fechaPago576;
	}

	/**
	 * @param fechaPago576 the fechaPago576 to set
	 */
	public void setFechaPago576(Date fechaPago576) {
		this.fechaPago576 = fechaPago576;
	}

	/**
	 * @return the idReduccion05
	 */
	public String getIdReduccion05() {
		return idReduccion05;
	}

	/**
	 * @param idReduccion05 the idReduccion05 to set
	 */
	public void setIdReduccion05(String idReduccion05) {
		this.idReduccion05 = idReduccion05;
	}

	/**
	 * @return the idNoSujeccion06
	 */
	public String getIdNoSujeccion06() {
		return idNoSujeccion06;
	}

	/**
	 * @param idNoSujeccion06 the idNoSujeccion06 to set
	 */
	public void setIdNoSujeccion06(String idNoSujeccion06) {
		this.idNoSujeccion06 = idNoSujeccion06;
	}

	/**
	 * @return the importe576
	 */
	public BigDecimal getImporte576() {
		return importe576;
	}

	/**
	 * @param importe576 the importe576 to set
	 */
	public void setImporte576(BigDecimal importe576) {
		this.importe576 = importe576;
	}

	/**
	 * @return the entregaFactMatriculacion
	 */
	public String getEntregaFactMatriculacion() {
		return entregaFactMatriculacion;
	}

	/**
	 * @param entregaFactMatriculacion the entregaFactMatriculacion to set
	 */
	public void setEntregaFactMatriculacion(String entregaFactMatriculacion) {
		this.entregaFactMatriculacion = entregaFactMatriculacion;
	}

	/**
	 * @return the tipoTramiteMatr
	 */
	public String getTipoTramiteMatr() {
		return tipoTramiteMatr;
	}

	/**
	 * @param tipoTramiteMatr the tipoTramiteMatr to set
	 */
	public void setTipoTramiteMatr(String tipoTramiteMatr) {
		this.tipoTramiteMatr = tipoTramiteMatr;
	}

	/**
	 * @return the justificadoIvtm
	 */
	public String getJustificadoIvtm() {
		return justificadoIvtm;
	}

	/**
	 * @param justificadoIvtm the justificadoIvtm to set
	 */
	public void setJustificadoIvtm(String justificadoIvtm) {
		this.justificadoIvtm = justificadoIvtm;
	}

	/**
	 * @return respuetasEitv
	 */
	public String getRespuestaEitv() {
		return respuestaEitv;
	}

	/**
	 * @param respuetasEitv the respuetasEitv to set
	 */
	public void setRespuestaEitv(String respuestaEitv) {
		this.respuestaEitv = respuestaEitv;
	}

	/**
	 * @return the ivtmMatriculacionVO
	 */
	public IvtmMatriculacionVO getIvtmMatriculacionVO() {
		return ivtmMatriculacionVO;
	}

	/**
	 * @param ivtmMatriculacionVO the ivtmMatriculacionVO to set
	 */
	public void setIvtmMatriculacionVO(IvtmMatriculacionVO ivtmMatriculacionVO) {
		this.ivtmMatriculacionVO = ivtmMatriculacionVO;
	}

	public LiberacionEEFFVO getLiberacionEEFF() {
		return liberacionEEFF;
	}

	public void setLiberacionEEFF(LiberacionEEFFVO liberacionEEFF) {
		this.liberacionEEFF = liberacionEEFF;
	}

	/**
	 * @return respuetasEitv
	 */
	public String getPegatina() {
		return pegatina;
	}

	/**
	 * @param pegatina the pegatina to set
	 */
	public void setPegatina(String pegatina) {
		this.pegatina = pegatina;
	}

	public String getRespuesta576() {
		return respuesta576;
	}

	public void setRespuesta576(String respuesta576) {
		this.respuesta576 = respuesta576;
	}

	public String getCarsharing() {
		return carsharing;
	}

	public void setCarsharing(String carsharing) {
		this.carsharing = carsharing;
	}

	public Set<ConsultaEEFFVO> getListaConsultaEEFF() {
		return listaConsultaEEFF;
	}

	public void setListaConsultaEEFF(Set<ConsultaEEFFVO> listaConsultaEEFF) {
		this.listaConsultaEEFF = listaConsultaEEFF;
	}

	public List<ConsultaEEFFVO> getConsultasEEFFAsList() {
		List<ConsultaEEFFVO> lista;
		if (listaConsultaEEFF != null) {
			lista = new ArrayList<ConsultaEEFFVO>(listaConsultaEEFF);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public void setListaConsultaEEFF(List<ConsultaEEFFVO> listaConsultaEEFF) {
		if (listaConsultaEEFF == null) {
			this.listaConsultaEEFF = null;
		} else {
			// Map from List to Set
			Set<ConsultaEEFFVO> set = new HashSet<ConsultaEEFFVO>();
			set.addAll(listaConsultaEEFF);
			this.listaConsultaEEFF = set;
		}
	}

	public Long getDocDistintivo() {
		return docDistintivo;
	}

	public void setDocDistintivo(Long docDistintivo) {
		this.docDistintivo = docDistintivo;
	}

	public DocPermDistItvVO getDocDistintivoVO() {
		return docDistintivoVO;
	}

	public void setDocDistintivoVO(DocPermDistItvVO docDistintivoVO) {
		this.docDistintivoVO = docDistintivoVO;
	}

	public String getDistintivo() {
		return distintivo;
	}

	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}

	public String getEstadoPetPermDstv() {
		return estadoPetPermDstv;
	}

	public void setEstadoPetPermDstv(String estadoPetPermDstv) {
		this.estadoPetPermDstv = estadoPetPermDstv;
	}

	public String getRespPetPermDstv() {
		return respPetPermDstv;
	}

	public void setRespPetPermDstv(String respPetPermDstv) {
		this.respPetPermDstv = respPetPermDstv;
	}

	public String getEstadoImpDstv() {
		return estadoImpDstv;
	}

	public void setEstadoImpDstv(String estadoImpDstv) {
		this.estadoImpDstv = estadoImpDstv;
	}

	public String getNumSerieDistintivo() {
		return numSerieDistintivo;
	}

	public void setNumSerieDistintivo(String numSerieDistintivo) {
		this.numSerieDistintivo = numSerieDistintivo;
	}

	public Date getFechaSolicitudDistintivo() {
		return fechaSolicitudDistintivo;
	}

	public void setFechaSolicitudDistintivo(Date fechaSolicitudDistintivo) {
		this.fechaSolicitudDistintivo = fechaSolicitudDistintivo;
	}

	public String getCheckJustifFicheroIvtm() {
		return checkJustifFicheroIvtm;
	}

	public void setCheckJustifFicheroIvtm(String checkJustifFicheroIvtm) {
		this.checkJustifFicheroIvtm = checkJustifFicheroIvtm;
	}

	public String getEstado576() {
		return estado576;
	}

	public void setEstado576(String estado576) {
		this.estado576 = estado576;
	}

	public String getExpedienteRef576() {
		return expedienteRef576;
	}

	public void setExpedienteRef576(String expedienteRef576) {
		this.expedienteRef576 = expedienteRef576;
	}

	public String getCodigoVerificacion576() {
		return codigoVerificacion576;
	}

	public void setCodigoVerificacion576(String codigoVerificacion576) {
		this.codigoVerificacion576 = codigoVerificacion576;
	}

	public String getRespBloqBastidor() {
		return respBloqBastidor;
	}

	public void setRespBloqBastidor(String respBloqBastidor) {
		this.respBloqBastidor = respBloqBastidor;
	}

	public String getExentoCtr() {
		return exentoCtr;
	}

	public void setExentoCtr(String exentoCtr) {
		this.exentoCtr = exentoCtr;
	}

	public String getAutorizadoFichaA() {
		return autorizadoFichaA;
	}

	public void setAutorizadoFichaA(String autorizadoFichaA) {
		this.autorizadoFichaA = autorizadoFichaA;
	}

	public String getAutorizadoExentoCtr() {
		return autorizadoExentoCtr;
	}

	public void setAutorizadoExentoCtr(String autorizadoExentoCtr) {
		this.autorizadoExentoCtr = autorizadoExentoCtr;
	}

}
