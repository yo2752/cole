package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import org.gestoresmadrid.oegam2comun.docbase.view.dto.DocBaseDto;
import org.gestoresmadrid.oegam2comun.view.dto.YbpdfDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

import utilidades.estructuras.Fecha;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Tramite_trafico")
public class TramiteTrafDto implements Serializable {

	private static final long serialVersionUID = 853569772169319552L;

	private BigDecimal numExpediente;

	private String anotaciones;

	private Boolean cambioDomicilio;

	private String cem;

	private String cema;

	private String estado;

	private Boolean exentoCem;

	private String exentoIedtm;

	private Fecha fechaAlta;

	private Fecha fechaDigitalizacion;

	private Fecha fechaIedtm;

	private Fecha fechaImpresion;

	private Fecha fechaPresentacion;

	private Fecha fechaUltModif;

	private String financieraIedtm;

	@XmlElement(name = "Datos_Firmados", required = false)
	private UsuarioDto usuarioDto;

	private String iedtm;

	private JefaturaTraficoDto jefaturaTraficoDto;

	private String modeloIedtm;

	private String regIedtm;

	private String noSujecionIedtm;

	private String npasos;

	private String refPropia;

	private Boolean renting;

	private String respuesta;

	private String tipoTramite;

	private BigDecimal ybestado;

	@XStreamOmitField
	private List<EvolucionTramiteTraficoDto> evolucionTramiteTraficos;

	private BigDecimal idTipoCreacion;

	private TasaDto tasa;
	
	private YbpdfDto ybpdfDto;

	private String idYbpdf;

	private String numColegiado;

	private VehiculoDto vehiculoDto;

	private BigDecimal idContrato;

	private BigDecimal simultanea;

	private String respuestaGest;

	private String respuestaDigitalizacionGdoc;

	@XStreamOmitField
	private TramiteTrafFacturacionDto tramiteFacturacion;

	private IntervinienteTraficoDto titular;

	private ContratoDto contrato;

	private Set<IntervinienteTraficoDto> intervinienteTraficos;

	private DocBaseDto documentoBase;

	private Long numImpresionesPerm;

	private Long numImpresionesFicha;

	private String permiso;

	private String fichaTecnica;

	private String estadoSolPerm;

	private String estadoSolFicha;

	private String nSeriePermiso;

	private String estadoImpFicha;

	private String estadoImpPerm;

	private String idProvinciaCem;

	// Mantis 25415
	private BigDecimal valorReal;

	public TramiteTrafDto() {
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public Boolean getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(Boolean cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getCem() {
		return cem;
	}

	public void setCem(String cem) {
		this.cem = cem;
	}

	public String getCema() {
		return cema;
	}

	public void setCema(String cema) {
		this.cema = cema;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getExentoCem() {
		return exentoCem;
	}

	public void setExentoCem(Boolean exentoCem) {
		this.exentoCem = exentoCem;
	}

	public String getExentoIedtm() {
		return exentoIedtm;
	}

	public void setExentoIedtm(String exentoIedtm) {
		this.exentoIedtm = exentoIedtm;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaDigitalizacion() {
		return fechaDigitalizacion;
	}

	public void setFechaDigitalizacion(Fecha fechaDigitalizacion) {
		this.fechaDigitalizacion = fechaDigitalizacion;
	}

	public Fecha getFechaIedtm() {
		return fechaIedtm;
	}

	public void setFechaIedtm(Fecha fechaIedtm) {
		this.fechaIedtm = fechaIedtm;
	}

	public Fecha getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Fecha fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Fecha getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Fecha fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public String getFinancieraIedtm() {
		return financieraIedtm;
	}

	public void setFinancieraIedtm(String financieraIedtm) {
		this.financieraIedtm = financieraIedtm;
	}

	public String getIedtm() {
		return iedtm;
	}

	public void setIedtm(String iedtm) {
		this.iedtm = iedtm;
	}

	public JefaturaTraficoDto getJefaturaTraficoDto() {
		return jefaturaTraficoDto;
	}

	public void setJefaturaTraficoDto(JefaturaTraficoDto jefaturaTraficoDto) {
		this.jefaturaTraficoDto = jefaturaTraficoDto;
	}

	public String getModeloIedtm() {
		return modeloIedtm;
	}

	public void setModeloIedtm(String modeloIedtm) {
		this.modeloIedtm = modeloIedtm;
	}

	public String getRegIedtm() {
		return regIedtm;
	}

	public void setRegIedtm(String regIedtm) {
		this.regIedtm = regIedtm;
	}

	public String getNoSujecionIedtm() {
		return noSujecionIedtm;
	}

	public void setNoSujecionIedtm(String noSujecionIedtm) {
		this.noSujecionIedtm = noSujecionIedtm;
	}

	public String getNpasos() {
		return npasos;
	}

	public void setNpasos(String npasos) {
		this.npasos = npasos;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public Boolean getRenting() {
		return renting;
	}

	public void setRenting(Boolean renting) {
		this.renting = renting;
	}

	public List<EvolucionTramiteTraficoDto> getEvolucionTramiteTraficos() {
		return evolucionTramiteTraficos;
	}

	public void setEvolucionTramiteTraficos(List<EvolucionTramiteTraficoDto> evolucionTramiteTraficos) {
		this.evolucionTramiteTraficos = evolucionTramiteTraficos;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getYbestado() {
		return ybestado;
	}

	public void setYbestado(BigDecimal ybestado) {
		this.ybestado = ybestado;
	}

	public List<EvolucionTramiteTraficoDto> getEvolucionTramiteTrafico() {
		return evolucionTramiteTraficos;
	}

	public void setEvolucionTramiteTrafico(List<EvolucionTramiteTraficoDto> evolucionTramiteTraficos) {
		this.evolucionTramiteTraficos = evolucionTramiteTraficos;
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public TasaDto getTasa() {
		return tasa;
	}

	public void setTasa(TasaDto tasa) {
		this.tasa = tasa;
	}

	public YbpdfDto getYbpdfDto() {
		return ybpdfDto;
	}

	public void setYbpdfDto(YbpdfDto ybpdfDto) {
		this.ybpdfDto = ybpdfDto;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getSimultanea() {
		return simultanea;
	}

	public void setSimultanea(BigDecimal simultanea) {
		this.simultanea = simultanea;
	}

	public String getRespuestaGest() {
		return respuestaGest;
	}

	public void setRespuestaGest(String respuestaGest) {
		this.respuestaGest = respuestaGest;
	}

	public String getRespuestaDigitalizacionGdoc() {
		return respuestaDigitalizacionGdoc;
	}

	public void setRespuestaDigitalizacionGdoc(String respuestaDigitalizacionGdoc) {
		this.respuestaDigitalizacionGdoc = respuestaDigitalizacionGdoc;
	}

	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public VehiculoDto getVehiculoDto() {
		return vehiculoDto;
	}

	public void setVehiculoDto(VehiculoDto vehiculoDto) {
		this.vehiculoDto = vehiculoDto;
	}

	public TramiteTrafFacturacionDto getTramiteFacturacion() {
		return tramiteFacturacion;
	}

	public void setTramiteFacturacion(TramiteTrafFacturacionDto tramiteFacturacion) {
		this.tramiteFacturacion = tramiteFacturacion;
	}

	public String getIdYbpdf() {
		return idYbpdf;
	}

	public void setIdYbpdf(String idYbpdf) {
		this.idYbpdf = idYbpdf;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public Set<IntervinienteTraficoDto> getIntervinienteTraficos() {
		return intervinienteTraficos;
	}

	public void setIntervinienteTraficos(Set<IntervinienteTraficoDto> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}

	public DocBaseDto getDocumentoBase() {
		return documentoBase;
	}

	public void setDocumentoBase(DocBaseDto documentoBase) {
		this.documentoBase = documentoBase;
	}

	// Mantis 25415
	public BigDecimal getValorReal() {
		return valorReal;
	}

	// Mantis 25415
	public void setValorReal(BigDecimal valorReal) {
		this.valorReal = valorReal;
	}

	public Long getNumImpresionesPerm() {
		return numImpresionesPerm;
	}

	public void setNumImpresionesPerm(Long numImpresionesPerm) {
		this.numImpresionesPerm = numImpresionesPerm;
	}

	public Long getNumImpresionesFicha() {
		return numImpresionesFicha;
	}

	public void setNumImpresionesFicha(Long numImpresionesFicha) {
		this.numImpresionesFicha = numImpresionesFicha;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getFichaTecnica() {
		return fichaTecnica;
	}

	public void setFichaTecnica(String fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}

	public String getEstadoSolPerm() {
		return estadoSolPerm;
	}

	public void setEstadoSolPerm(String estadoSolPerm) {
		this.estadoSolPerm = estadoSolPerm;
	}

	public String getEstadoSolFicha() {
		return estadoSolFicha;
	}

	public void setEstadoSolFicha(String estadoSolFicha) {
		this.estadoSolFicha = estadoSolFicha;
	}

	public String getnSeriePermiso() {
		return nSeriePermiso;
	}

	public void setnSeriePermiso(String nSeriePermiso) {
		this.nSeriePermiso = nSeriePermiso;
	}

	public String getEstadoImpFicha() {
		return estadoImpFicha;
	}

	public void setEstadoImpFicha(String estadoImpFicha) {
		this.estadoImpFicha = estadoImpFicha;
	}

	public String getEstadoImpPerm() {
		return estadoImpPerm;
	}

	public void setEstadoImpPerm(String estadoImpPerm) {
		this.estadoImpPerm = estadoImpPerm;
	}

	public String getIdProvinciaCem() {
		return idProvinciaCem;
	}

	public void setIdProvinciaCem(String idProvinciaCem) {
		this.idProvinciaCem = idProvinciaCem;
	}

}