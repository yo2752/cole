package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoLimitacionDisposicionIEDTM;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

import utilidades.estructuras.Fecha;

public class TramiteTrafMatrDto extends TramiteTrafDto {

	private static final long serialVersionUID = -664814969830074426L;

	private BigDecimal deducir576;

	private BigDecimal baseImpoReducida576;

	private BigDecimal baseImponible576;

	private String causaHechoImpon576;

	private BigDecimal cuota576;

	private BigDecimal cuotaIngresar576;

	private BigDecimal deduccionLineal576;

	private BigDecimal ejercicio576;

	private Boolean entregaFactMatriculacion;

	private Boolean exento576;

	private Fecha fechaPago576;

	private String idNoSujeccion06;

	private String idReduccion05;

	private BigDecimal importe576;

	private BigDecimal liquidacion576;

	private String numDeclaracionComp576;

	private String nrc576;

	private String observaciones576;

	private Boolean reduccion576;

	private BigDecimal tipoGravamen576;

	private String tipoTramiteMatr;

	private Boolean justificadoIvtm;

	private String respuestaEitv;

	private IvtmMatriculacionDto ivtmMatriculacionDto;

	private String respuesta576;

	// Este interviniente está en el padre
	// private IntervinienteTraficoDto titular;

	private IntervinienteTraficoDto representanteTitular;

	private IntervinienteTraficoDto arrendatario;

	private IntervinienteTraficoDto representanteArrendatario;

	private IntervinienteTraficoDto conductorHabitual;

	private TipoLimitacionDisposicionIEDTM tipoLimitacionDisposicionIEDTM;

	private VehiculoDto vehiculoPrever;

	private Boolean carsharing;

	private String permiso;

	private String distintivo;

	private String eitv;

	private String pegatina;

	private LiberacionEEFFDto liberacionEEFF;

	private List<ConsultaEEFFDto> listaConsultaEEFF;

	private Boolean docHomologacion;

	private String estadoPetPermDstv;

	private String respPetPermDstv;

	private String estadoPetEitv;

	private Boolean checkJustifFicheroIvtm;

	private String tipoDistintivo;

	private String estado576;

	private String expedienteRef576;

	private String codigoVerificacion576;

	private String nre;

	private Fecha fechaRegistroNRE;
	
	private String respBloqBastidor;
	
	private String exentoCtr;
	
	private String autorizadoFichaA;
	
	private String autorizadoExentoCtr;

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

	public String getEstadoPetEitv() {
		return estadoPetEitv;
	}

	public void setEstadoPetEitv(String estadoPetEitv) {
		this.estadoPetEitv = estadoPetEitv;
	}

	public String getRespPetEitv() {
		return respPetEitv;
	}

	public void setRespPetEitv(String respPetEitv) {
		this.respPetEitv = respPetEitv;
	}

	private String respPetEitv;

	public Boolean getDocHomologacion() {
		return docHomologacion;
	}

	public void setDocHomologacion(Boolean docHomologacion) {
		this.docHomologacion = docHomologacion;
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

	public Boolean getEntregaFactMatriculacion() {
		return entregaFactMatriculacion;
	}

	public void setEntregaFactMatriculacion(Boolean entregaFactMatriculacion) {
		this.entregaFactMatriculacion = entregaFactMatriculacion;
	}

	public Boolean getExento576() {
		return exento576;
	}

	public void setExento576(Boolean exento576) {
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

	public Boolean getReduccion576() {
		return reduccion576;
	}

	public void setReduccion576(Boolean reduccion576) {
		this.reduccion576 = reduccion576;
	}

	public BigDecimal getTipoGravamen576() {
		return tipoGravamen576;
	}

	public void setTipoGravamen576(BigDecimal tipoGravamen576) {
		this.tipoGravamen576 = tipoGravamen576;
	}

	public String getTipoTramiteMatr() {
		return tipoTramiteMatr;
	}

	public void setTipoTramiteMatr(String tipoTramiteMatr) {
		this.tipoTramiteMatr = tipoTramiteMatr;
	}

	// TODO MPC. Cambio IVTM.
	public IvtmMatriculacionDto getIvtmMatriculacionDto() {
		return ivtmMatriculacionDto;
	}

	public void setIvtmMatriculacionDto(IvtmMatriculacionDto ivtmMatriculacionDto) {
		this.ivtmMatriculacionDto = ivtmMatriculacionDto;
	}

	public Boolean getJustificadoIvtm() {
		return justificadoIvtm;
	}

	public void setJustificadoIvtm(Boolean justificadoIvtm) {
		this.justificadoIvtm = justificadoIvtm;
	}

	public String getRespuestaEitv() {
		return respuestaEitv;
	}

	public void setRespuestaEitv(String respuestaEitv) {
		this.respuestaEitv = respuestaEitv;
	}

	public IntervinienteTraficoDto getRepresentanteTitular() {
		return representanteTitular;
	}

	public void setRepresentanteTitular(IntervinienteTraficoDto representanteTitular) {
		this.representanteTitular = representanteTitular;
	}

	public IntervinienteTraficoDto getArrendatario() {
		return arrendatario;
	}

	public void setArrendatario(IntervinienteTraficoDto arrendatario) {
		this.arrendatario = arrendatario;
	}

	public IntervinienteTraficoDto getRepresentanteArrendatario() {
		return representanteArrendatario;
	}

	public void setRepresentanteArrendatario(IntervinienteTraficoDto representanteArrendatario) {
		this.representanteArrendatario = representanteArrendatario;
	}

	public IntervinienteTraficoDto getConductorHabitual() {
		return conductorHabitual;
	}

	public void setConductorHabitual(IntervinienteTraficoDto conductorHabitual) {
		this.conductorHabitual = conductorHabitual;
	}

	public TipoLimitacionDisposicionIEDTM getTipoLimitacionDisposicionIEDTM() {
		return tipoLimitacionDisposicionIEDTM;
	}

	public void setTipoLimitacionDisposicionIEDTM(TipoLimitacionDisposicionIEDTM tipoLimitacionDisposicionIEDTM) {
		this.tipoLimitacionDisposicionIEDTM = tipoLimitacionDisposicionIEDTM;
	}

	public VehiculoDto getVehiculoPrever() {
		return vehiculoPrever;
	}

	public void setVehiculoPrever(VehiculoDto vehiculoPrever) {
		this.vehiculoPrever = vehiculoPrever;
	}

	public Boolean getCarsharing() {
		return carsharing;
	}

	public void setCarsharing(Boolean carsharing) {
		this.carsharing = carsharing;
	}

	public String getRespuesta576() {
		return respuesta576;
	}

	public void setRespuesta576(String respuesta576) {
		this.respuesta576 = respuesta576;
	}

	public String getPegatina() {
		return pegatina;
	}

	public void setPegatina(String pegatina) {
		this.pegatina = pegatina;
	}

	public BigDecimal getDeducir576() {
		return deducir576;
	}

	public void setDeducir576(BigDecimal deducir576) {
		this.deducir576 = deducir576;
	}

	public String getNumDeclaracionComp576() {
		return numDeclaracionComp576;
	}

	public void setNumDeclaracionComp576(String numDeclaracionComp576) {
		this.numDeclaracionComp576 = numDeclaracionComp576;
	}

	public LiberacionEEFFDto getLiberacionEEFF() {
		return liberacionEEFF;
	}

	public void setLiberacionEEFF(LiberacionEEFFDto liberacionEEFF) {
		this.liberacionEEFF = liberacionEEFF;
	}

	public List<ConsultaEEFFDto> getListaConsultaEEFF() {
		return listaConsultaEEFF;
	}

	public void setListaConsultaEEFF(List<ConsultaEEFFDto> listaConsultaEEFF) {
		this.listaConsultaEEFF = listaConsultaEEFF;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getDistintivo() {
		return distintivo;
	}

	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}

	public String getEitv() {
		return eitv;
	}

	public void setEitv(String eitv) {
		this.eitv = eitv;
	}

	public Boolean getCheckJustifFicheroIvtm() {
		return checkJustifFicheroIvtm;
	}

	public void setCheckJustifFicheroIvtm(Boolean checkJustifFicheroIvtm) {
		this.checkJustifFicheroIvtm = checkJustifFicheroIvtm;
	}

	public String getTipoDistintivo() {
		return tipoDistintivo;
	}

	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
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