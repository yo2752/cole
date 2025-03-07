package trafico.dto;

import hibernate.entities.general.Contrato;
import hibernate.entities.general.JefaturaTrafico;
import hibernate.entities.general.Usuario;
import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.EvolucionTramiteTrafico;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.JustificanteProf;
import hibernate.entities.trafico.TipoCreacion;
import hibernate.entities.trafico.TramiteTrafBaja;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.TramiteTrafTran;
import hibernate.entities.trafico.Vehiculo;
import hibernate.entities.yerbabuena.Ybpdf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;

import trafico.dto.matriculacion.TramiteTrafMatrDto;
import utilidades.estructuras.Fecha;

public class TramiteTraficoDto {

	private Long numExpediente;

	private String anotaciones;

	private String cambioDomicilio;

	private String cem;

	private String cema;

	private BigDecimal estado;

	private String exentoCem;

	private String exentoIedtm;

	private Fecha fechaAlta;

	private Fecha fechaDigitalizacion;

	private Fecha fechaIedtm;

	private Fecha fechaImpresion;

	private Fecha fechaPresentacion;

	private Fecha fechaUltModif;

	private String financieraIedtm;

	private Usuario usuario;

	private String iedtm;

	private JefaturaTrafico jefaturaTrafico;

	private TramiteTrafTran tramiteTrafTran;

	private TramiteTrafMatrDto tramiteTrafMatr;

	private TramiteTrafBaja tramiteTrafBaja;

	private List<IntervinienteTrafico> intervinienteTraficos;

	private Set<TramiteTrafSolInfo> tramiteTrafSolInfo;

	private String modeloIedtm;

	private String nRegIedtm;

	private String noSujecionIedtm;

	private String npasos;

	private String refPropia;

	private String renting;

	private String resCheckCtit;

	private String respuesta;

	private String tipoTramite;

	private BigDecimal ybestado;

	private List<EvolucionTramiteTrafico> evolucionTramiteTraficos;

	private TipoCreacion tipoCreacion;

	private List<JustificanteProf> justificanteProfs;

	private Tasa tasa;

	private Ybpdf ybpdf;

	private String numColegiado;

	private Vehiculo vehiculo;

	private Contrato contrato;

	private BigDecimal simultanea;

	private String respuestaGest;

	private String respuestaDigitalizacionGdoc;

	private String superTelematico;

	private TramiteTrafTranDto tramiteTrafTranDto;

	private Long numImpresionesPerm;

	private Long numImpresionesFicha;

	private String permiso;

	private String fichaTecnica;

	private String estadoSolPerm;

	private String estadoSolFicha;

	private String nSeriePermiso;

	private String estadoImpFicha;

	private String estadoImpPerm;

	private String excelKoImpr;

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
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

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getExentoCem() {
		return exentoCem;
	}

	public void setExentoCem(String exentoCem) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIedtm() {
		return iedtm;
	}

	public void setIedtm(String iedtm) {
		this.iedtm = iedtm;
	}

	public JefaturaTrafico getJefaturaTrafico() {
		return jefaturaTrafico;
	}

	public void setJefaturaTrafico(JefaturaTrafico jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}

	public TramiteTrafTran getTramiteTrafTran() {
		return tramiteTrafTran;
	}

	public void setTramiteTrafTran(TramiteTrafTran tramiteTrafTran) {
		this.tramiteTrafTran = tramiteTrafTran;
	}

	public TramiteTrafMatrDto getTramiteTrafMatr() {
		return tramiteTrafMatr;
	}

	public void setTramiteTrafMatr(TramiteTrafMatrDto tramiteTrafMatr) {
		this.tramiteTrafMatr = tramiteTrafMatr;
	}

	public TramiteTrafBaja getTramiteTrafBaja() {
		return tramiteTrafBaja;
	}

	public void setTramiteTrafBaja(TramiteTrafBaja tramiteTrafBaja) {
		this.tramiteTrafBaja = tramiteTrafBaja;
	}

	public List<IntervinienteTrafico> getIntervinienteTraficos() {
		return intervinienteTraficos;
	}

	public void setIntervinienteTraficos(List<IntervinienteTrafico> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}

	public Set<TramiteTrafSolInfo> getTramiteTrafSolInfo() {
		return tramiteTrafSolInfo;
	}

	public void setTramiteTrafSolInfo(Set<TramiteTrafSolInfo> tramiteTrafSolInfo) {
		this.tramiteTrafSolInfo = tramiteTrafSolInfo;
	}

	public String getModeloIedtm() {
		return modeloIedtm;
	}

	public void setModeloIedtm(String modeloIedtm) {
		this.modeloIedtm = modeloIedtm;
	}

	public String getnRegIedtm() {
		return nRegIedtm;
	}

	public void setnRegIedtm(String nRegIedtm) {
		this.nRegIedtm = nRegIedtm;
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

	public String getRenting() {
		return renting;
	}

	public void setRenting(String renting) {
		this.renting = renting;
	}

	public String getResCheckCtit() {
		return resCheckCtit;
	}

	public void setResCheckCtit(String resCheckCtit) {
		this.resCheckCtit = resCheckCtit;
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

	public List<EvolucionTramiteTrafico> getEvolucionTramiteTraficos() {
		return evolucionTramiteTraficos;
	}

	public void setEvolucionTramiteTraficos(List<EvolucionTramiteTrafico> evolucionTramiteTraficos) {
		this.evolucionTramiteTraficos = evolucionTramiteTraficos;
	}

	public TipoCreacion getTipoCreacion() {
		return tipoCreacion;
	}

	public void setTipoCreacion(TipoCreacion tipoCreacion) {
		this.tipoCreacion = tipoCreacion;
	}

	public List<JustificanteProf> getJustificanteProfs() {
		return justificanteProfs;
	}

	public void setJustificanteProfs(List<JustificanteProf> justificanteProfs) {
		this.justificanteProfs = justificanteProfs;
	}

	public Tasa getTasa() {
		return tasa;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	public Ybpdf getYbpdf() {
		return ybpdf;
	}

	public void setYbpdf(Ybpdf ybpdf) {
		this.ybpdf = ybpdf;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public TramiteTrafTranDto getTramiteTrafTranDto() {
		return tramiteTrafTranDto;
	}

	public void setTramiteTrafTranDto(TramiteTrafTranDto tramiteTrafTranDto) {
		this.tramiteTrafTranDto = tramiteTrafTranDto;
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

	public String getSuperTelematico() {
		return superTelematico;
	}

	public void setSuperTelematico(String superTelematico) {
		this.superTelematico = superTelematico;
	}

	public String getExcelKoImpr() {
		return excelKoImpr;
	}

	public void setExcelKoImpr(String excelKoImpr) {
		this.excelKoImpr = excelKoImpr;
	}

}