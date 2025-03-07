package trafico.dto.matriculacion;

import hibernate.entities.trafico.TramiteTrafico;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;

import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import utilidades.estructuras.Fecha;

public class TramiteTrafMatrDto {

	private long numExpediente;

	private BigDecimal aDeducir576;

	private BigDecimal baseImpoReducida576;

	private BigDecimal baseImponible576;

	private String causaHechoImpon576;

	private BigDecimal cuota576;

	private BigDecimal cuotaIngresar576;

	private BigDecimal deduccionLineal576;

	private BigDecimal ejercicio576;

	private String entregaFactMatriculacion;

	private String exento576;

	private Fecha fechaPago576;

	private String idNoSujeccion06;

	private String idReduccion05;

	private BigDecimal importe576;

	private BigDecimal liquidacion576;

	private String nDeclaracionComp576;

	private String nrc576;

	private String observaciones576;

	private String reduccion576;

	private BigDecimal tipoGravamen576;

	private List<TramiteTrafico> tramiteTrafico;

	private TipoTramiteMatriculacion tipoTramiteMatriculacion;

	private EeffLiberacionDTO eeffLiberacionDTO;

	private String justificadoIvtm;

	private String nre;

	private Fecha fechaRegistroNRE;

	// TODO MPC. Cambio IVTM.
	private IvtmMatriculacionDto ivtmMatriculacionDto;

	public long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getaDeducir576() {
		return aDeducir576;
	}

	public void setaDeducir576(BigDecimal aDeducir576) {
		this.aDeducir576 = aDeducir576;
	}

	public BigDecimal getBaseImpoReducida576() {
		return baseImpoReducida576;
	}

	public void setBaseImpoReducida576(BigDecimal baseImpoReducida576) {
		this.baseImpoReducida576 = baseImpoReducida576;
	}

	public BigDecimal getBaseImponible576() {
		return baseImponible576;
	}

	public void setBaseImponible576(BigDecimal baseImponible576) {
		this.baseImponible576 = baseImponible576;
	}

	public String getCausaHechoImpon576() {
		return causaHechoImpon576;
	}

	public void setCausaHechoImpon576(String causaHechoImpon576) {
		this.causaHechoImpon576 = causaHechoImpon576;
	}

	public BigDecimal getCuota576() {
		return cuota576;
	}

	public void setCuota576(BigDecimal cuota576) {
		this.cuota576 = cuota576;
	}

	public BigDecimal getCuotaIngresar576() {
		return cuotaIngresar576;
	}

	public void setCuotaIngresar576(BigDecimal cuotaIngresar576) {
		this.cuotaIngresar576 = cuotaIngresar576;
	}

	public BigDecimal getDeduccionLineal576() {
		return deduccionLineal576;
	}

	public void setDeduccionLineal576(BigDecimal deduccionLineal576) {
		this.deduccionLineal576 = deduccionLineal576;
	}

	public BigDecimal getEjercicio576() {
		return ejercicio576;
	}

	public void setEjercicio576(BigDecimal ejercicio576) {
		this.ejercicio576 = ejercicio576;
	}

	public String getEntregaFactMatriculacion() {
		return entregaFactMatriculacion;
	}

	public void setEntregaFactMatriculacion(String entregaFactMatriculacion) {
		this.entregaFactMatriculacion = entregaFactMatriculacion;
	}

	public String getExento576() {
		return exento576;
	}

	public void setExento576(String exento576) {
		this.exento576 = exento576;
	}

	public Fecha getFechaPago576() {
		return fechaPago576;
	}

	public void setFechaPago576(Fecha fechaPago576) {
		this.fechaPago576 = fechaPago576;
	}

	public String getIdNoSujeccion06() {
		return idNoSujeccion06;
	}

	public void setIdNoSujeccion06(String idNoSujeccion06) {
		this.idNoSujeccion06 = idNoSujeccion06;
	}

	public String getIdReduccion05() {
		return idReduccion05;
	}

	public void setIdReduccion05(String idReduccion05) {
		this.idReduccion05 = idReduccion05;
	}

	public BigDecimal getImporte576() {
		return importe576;
	}

	public void setImporte576(BigDecimal importe576) {
		this.importe576 = importe576;
	}

	public BigDecimal getLiquidacion576() {
		return liquidacion576;
	}

	public void setLiquidacion576(BigDecimal liquidacion576) {
		this.liquidacion576 = liquidacion576;
	}

	public String getnDeclaracionComp576() {
		return nDeclaracionComp576;
	}

	public void setnDeclaracionComp576(String nDeclaracionComp576) {
		this.nDeclaracionComp576 = nDeclaracionComp576;
	}

	public String getNrc576() {
		return nrc576;
	}

	public void setNrc576(String nrc576) {
		this.nrc576 = nrc576;
	}

	public String getObservaciones576() {
		return observaciones576;
	}

	public void setObservaciones576(String observaciones576) {
		this.observaciones576 = observaciones576;
	}

	public String getReduccion576() {
		return reduccion576;
	}

	public void setReduccion576(String reduccion576) {
		this.reduccion576 = reduccion576;
	}

	public BigDecimal getTipoGravamen576() {
		return tipoGravamen576;
	}

	public void setTipoGravamen576(BigDecimal tipoGravamen576) {
		this.tipoGravamen576 = tipoGravamen576;
	}

	public List<TramiteTrafico> getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(List<TramiteTrafico> tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public TipoTramiteMatriculacion getTipoTramiteMatriculacion() {
		return tipoTramiteMatriculacion;
	}

	public void setTipoTramiteMatriculacion(TipoTramiteMatriculacion tipoTramiteMatriculacion) {
		this.tipoTramiteMatriculacion = tipoTramiteMatriculacion;
	}

	public EeffLiberacionDTO getEeffLiberacionDTO() {
		return eeffLiberacionDTO;
	}

	public void setEeffLiberacionDTO(EeffLiberacionDTO eeffLiberacionDTO) {
		this.eeffLiberacionDTO = eeffLiberacionDTO;
	}

	// TODO MPC. Cambio IVTM.
	public IvtmMatriculacionDto getIvtmMatriculacionDto() {
		return ivtmMatriculacionDto;
	}

	public void setIvtmMatriculacionDto(IvtmMatriculacionDto ivtmMatriculacionDto) {
		this.ivtmMatriculacionDto = ivtmMatriculacionDto;
	}

	public String getJustificadoIvtm() {
		return justificadoIvtm;
	}

	public void setJustificadoIvtm(String justificadoIvtm) {
		this.justificadoIvtm = justificadoIvtm;
	}

	public String getNre() {
		return nre;
	}

	public void setNre(String nre) {
		this.nre = nre;
	}

	public Fecha getFechaRegistroNRE() {
		return fechaRegistroNRE;
	}

	public void setFechaRegistroNRE(Fecha fechaRegistroNRE) {
		this.fechaRegistroNRE = fechaRegistroNRE;
	}
}