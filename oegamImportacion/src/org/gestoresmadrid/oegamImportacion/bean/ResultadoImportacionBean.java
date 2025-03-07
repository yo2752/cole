package org.gestoresmadrid.oegamImportacion.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamConversiones.jaxb.baja.FORMATOOEGAM2BAJA;
import org.gestoresmadrid.oegamConversiones.jaxb.cet.OptiCET620;
import org.gestoresmadrid.oegamConversiones.jaxb.ctit.FORMATOGA;
import org.gestoresmadrid.oegamConversiones.jaxb.duplicado.FORMATOOEGAM2DUPLICADO;
import org.gestoresmadrid.oegamConversiones.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import org.gestoresmadrid.oegamImportacion.cet.bean.AutoliquidacionBean;
import org.gestoresmadrid.oegamImportacion.cet.bean.PegatinaBean;
import org.gestoresmadrid.oegamImportacion.tasa.bean.TasaImportacionBean;

public class ResultadoImportacionBean implements Serializable {

	private static final long serialVersionUID = 5148299989916525110L;

	private Boolean error;
	private Boolean errorExcepcion;
	private String codigo;
	private String[] listaMensajesError;
	private String[] listaMensajesOk;

	private String nombreFichero;
	private Boolean esDescargable;
	private Date fechaGenExcel;
	private File fichero;

	private String mensaje;
	private List<String> listaMensajes;
	private ResumenImportacionBean resumen;
	private FORMATOOEGAM2BAJA formatoOegam2Baja;
	private List<TramiteTrafBajaVO> listaTramitesBaja;
	private FORMATOGA formatoOegam2Ctit;
	private List<TramiteTrafTranVO> listaTramitesCtit;
	private org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA formatoOegam2Matw;
	private FORMATOOEGAM2SOLICITUD formatoOegam2Solicitud;
	private List<TramiteTrafMatrVO> listaTramitesMatw;
	private FORMATOOEGAM2DUPLICADO formatoOegam2Duplicado;
	private List<TramiteTrafDuplicadoVO> listaTramitesDuplicado;
	private OptiCET620 formatoOegam2Cet;
	private List<AutoliquidacionBean> listaCets;
	private List<String> lineasImportSolicitud;

	private List<TramiteTraficoInteveVO> listaTramitesInteve;
	private List<TramiteTraficoSolInteveVO> listaSolicitudInteve;
	private List<String> mensajeTasa;

	private List<TramiteTrafSolInfoVehiculoVO> listaTramitesSolicitud;
	private IntervinienteTraficoVO solicitante;
	private List<String> lineasImportPegatinas;
	private List<PegatinaBean> listaPegatinas;
	private byte[] pegatinas;
	private BigDecimal numExpediente;
	private List<VehNoMatOegamVO> listaDuplicadosDstv;
	private List<Long> listaIdDupDstv;
	private Long idDupDistintivo;

	private String dataGraficos1;
	private String dataGraficos2;
	private String dataCategoria;
	private String dataGraficosError0;
	private String dataGraficosError1;
	private String dataGraficosError2;

	private List<TasaVO> listaTasas;
	private List<TasaImportacionBean> listaTasaBean;

	private Long idImportacionFich;

	private List<String> listaConversionesError;

	public ResultadoImportacionBean(Boolean error) {
		super();
		this.error = error;
		this.errorExcepcion = Boolean.FALSE;
	}

	public void addListaIdDupDstv(Long idDupDistintivo) {
		if (listaIdDupDstv == null) {
			listaIdDupDstv = new ArrayList<>();
		}
		listaIdDupDstv.add(idDupDistintivo);
	}

	public void addListaDuplicadosDsttv(VehNoMatOegamVO duplicado, Integer posicion) {
		if (listaDuplicadosDstv == null) {
			listaDuplicadosDstv = new ArrayList<>();
		}
		if (posicion != null) {
			listaDuplicadosDstv.add(posicion, duplicado);
		} else {
			listaDuplicadosDstv.add(duplicado);
		}
	}

	public void addListaTramiteBaja(TramiteTrafBajaVO tramite, Integer posicion) {
		if (listaTramitesBaja == null) {
			listaTramitesBaja = new ArrayList<>();
		}
		if (posicion != null) {
			listaTramitesBaja.add(posicion, tramite);
		} else {
			listaTramitesBaja.add(tramite);
		}
	}

	public void addListaTramiteCtit(TramiteTrafTranVO tramite, Integer posicion) {
		if (listaTramitesCtit == null) {
			listaTramitesCtit = new ArrayList<>();
		}
		if (posicion != null) {
			listaTramitesCtit.add(posicion, tramite);
		} else {
			listaTramitesCtit.add(tramite);
		}
	}

	public void addListaTramiteMatw(TramiteTrafMatrVO tramite, Integer posicion) {
		if (listaTramitesMatw == null) {
			listaTramitesMatw = new ArrayList<>();
		}
		if (posicion != null) {
			listaTramitesMatw.add(posicion, tramite);
		} else {
			listaTramitesMatw.add(tramite);
		}
	}

	public void addListaTramiteDuplicado(TramiteTrafDuplicadoVO tramite, Integer posicion) {
		if (listaTramitesDuplicado == null) {
			listaTramitesDuplicado = new ArrayList<>();
		}
		if (posicion != null) {
			listaTramitesDuplicado.add(posicion, tramite);
		} else {
			listaTramitesDuplicado.add(tramite);
		}
	}

	public void addListaTasas(TasaVO tasa, Integer posicion) {
		if (listaTasas == null) {
			listaTasas = new ArrayList<>();
		}
		if (posicion != null) {
			listaTasas.add(posicion, tasa);
		} else {
			listaTasas.add(tasa);
		}
	}
	public void addListaTasasBean(TasaImportacionBean tasa, Integer posicion) {
		if (listaTasaBean == null) {
			listaTasaBean = new ArrayList<>();
		}
		if (posicion != null) {
			listaTasaBean.add(posicion, tasa);
		} else {
			listaTasaBean.add(tasa);
		}
	}

	public void addListaCets(AutoliquidacionBean bean, Integer posicion) {
		if (listaCets == null) {
			listaCets = new ArrayList<>();
		}
		if (posicion != null) {
			listaCets.add(posicion, bean);
		} else {
			listaCets.add(bean);
		}
	}

	public void addListaTramiteSolicitud(TramiteTrafSolInfoVehiculoVO tramite, Integer posicion) {
		if (listaTramitesSolicitud == null) {
			listaTramitesSolicitud = new ArrayList<>();
		}
		if (posicion != null) {
			listaTramitesSolicitud.add(posicion, tramite);
		} else {
			listaTramitesSolicitud.add(tramite);
		}
	}

	public void addListaTramiteInteve(TramiteTraficoInteveVO tramite, Integer posicion) {
		if (listaTramitesInteve == null) {
			listaTramitesInteve = new ArrayList<>();
		}
		if (posicion != null) {
			listaTramitesInteve.add(posicion, tramite);
		} else {
			listaTramitesInteve.add(tramite);
		}
	}

	public void addListaSolicitudInteve(TramiteTraficoSolInteveVO tramiteSol, Integer posicion) {
		if (listaSolicitudInteve == null) {
			listaSolicitudInteve = new ArrayList<>();
		}
		if (posicion != null) {
			listaSolicitudInteve.add(posicion, tramiteSol);
		} else {
			listaSolicitudInteve.add(tramiteSol);
		}
	}

	public void addConversionesError(String mensaje) {
		if (listaConversionesError == null) {
			listaConversionesError = new ArrayList<>();
		}
		listaConversionesError.add(mensaje);
	}

	public IntervinienteTraficoVO getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteTraficoVO solicitante) {
		this.solicitante = solicitante;
	}

	public void addListaTramitePegatina(PegatinaBean pegatina, Integer posicion) {
		if (listaPegatinas == null) {
			listaPegatinas = new ArrayList<>();
		}
		if (posicion != null) {
			listaPegatinas.add(posicion, pegatina);
		} else {
			listaPegatinas.add(pegatina);
		}
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
	}

	public void addResumenOK(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenImportacionBean(mensaje);
		}
		resumen.addListaOk(mensaje);
	}

	public void addResumenKO(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenImportacionBean(mensaje);
		}
		resumen.addListaKO(mensaje);
	}
	
	public void addResumenListaError(List<String> listaMensajeResumenError) {
		if(resumen == null) {
			resumen = new ResumenImportacionBean();
		}
		for(String mensaje : listaMensajeResumenError) {
			resumen.addListaKO(mensaje);
		}
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public ResumenImportacionBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenImportacionBean resumen) {
		this.resumen = resumen;
	}

	public FORMATOOEGAM2BAJA getFormatoOegam2Baja() {
		return formatoOegam2Baja;
	}

	public void setFormatoOegam2Baja(FORMATOOEGAM2BAJA formatoOegam2Baja) {
		this.formatoOegam2Baja = formatoOegam2Baja;
	}

	public List<TramiteTrafBajaVO> getListaTramitesBaja() {
		return listaTramitesBaja;
	}

	public void setListaTramitesBaja(List<TramiteTrafBajaVO> listaTramitesBaja) {
		this.listaTramitesBaja = listaTramitesBaja;
	}

	public Boolean getErrorExcepcion() {
		return errorExcepcion;
	}

	public void setErrorExcepcion(Boolean errorExcepcion) {
		this.errorExcepcion = errorExcepcion;
	}

	public FORMATOGA getFormatoOegam2Ctit() {
		return formatoOegam2Ctit;
	}

	public void setFormatoOegam2Ctit(FORMATOGA formatoOegam2Ctit) {
		this.formatoOegam2Ctit = formatoOegam2Ctit;
	}

	public List<TramiteTrafTranVO> getListaTramitesCtit() {
		return listaTramitesCtit;
	}

	public void setListaTramitesCtit(List<TramiteTrafTranVO> listaTramitesCtit) {
		this.listaTramitesCtit = listaTramitesCtit;
	}

	public org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA getFormatoOegam2Matw() {
		return formatoOegam2Matw;
	}

	public void setFormatoOegam2Matw(org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA formatoOegam2Matw) {
		this.formatoOegam2Matw = formatoOegam2Matw;
	}

	public List<TramiteTrafMatrVO> getListaTramitesMatw() {
		return listaTramitesMatw;
	}

	public void setListaTramitesMatw(List<TramiteTrafMatrVO> listaTramitesMatw) {
		this.listaTramitesMatw = listaTramitesMatw;
	}

	public FORMATOOEGAM2DUPLICADO getFormatoOegam2Duplicado() {
		return formatoOegam2Duplicado;
	}

	public void setFormatoOegam2Duplicado(FORMATOOEGAM2DUPLICADO formatoOegam2Duplicado) {
		this.formatoOegam2Duplicado = formatoOegam2Duplicado;
	}

	public List<TramiteTrafDuplicadoVO> getListaTramitesDuplicado() {
		return listaTramitesDuplicado;
	}

	public void setListaTramitesDuplicado(List<TramiteTrafDuplicadoVO> listaTramitesDuplicado) {
		this.listaTramitesDuplicado = listaTramitesDuplicado;
	}

	public OptiCET620 getFormatoOegam2Cet() {
		return formatoOegam2Cet;
	}

	public void setFormatoOegam2Cet(OptiCET620 formatoOegam2Cet) {
		this.formatoOegam2Cet = formatoOegam2Cet;
	}

	public List<AutoliquidacionBean> getListaCets() {
		return listaCets;
	}

	public void setListaCets(List<AutoliquidacionBean> listaCets) {
		this.listaCets = listaCets;
	}

	public List<String> getLineasImportSolicitud() {
		return lineasImportSolicitud;
	}

	public void setLineasImportSolicitud(List<String> lineasImportSolicitud) {
		this.lineasImportSolicitud = lineasImportSolicitud;
	}

	public List<TramiteTrafSolInfoVehiculoVO> getListaTramitesSolicitud() {
		return listaTramitesSolicitud;
	}

	public void setListaTramitesSolicitud(List<TramiteTrafSolInfoVehiculoVO> listaTramitesSolicitud) {
		this.listaTramitesSolicitud = listaTramitesSolicitud;
	}

	public List<String> getLineasImportPegatinas() {
		return lineasImportPegatinas;
	}

	public void setLineasImportPegatinas(List<String> lineasImportPegatinas) {
		this.lineasImportPegatinas = lineasImportPegatinas;
	}

	public List<PegatinaBean> getListaPegatinas() {
		return listaPegatinas;
	}

	public void setListaPegatinas(List<PegatinaBean> listaPegatinas) {
		this.listaPegatinas = listaPegatinas;
	}

	public byte[] getPegatinas() {
		return pegatinas;
	}

	public void setPegatinas(byte[] pegatinas) {
		this.pegatinas = pegatinas;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public List<VehNoMatOegamVO> getListaDuplicadosDstv() {
		return listaDuplicadosDstv;
	}

	public void setListaDuplicadosDstv(List<VehNoMatOegamVO> listaDuplicadosDstv) {
		this.listaDuplicadosDstv = listaDuplicadosDstv;
	}

	public List<Long> getListaIdDupDstv() {
		return listaIdDupDstv;
	}

	public void setListaIdDupDstv(List<Long> listaIdDupDstv) {
		this.listaIdDupDstv = listaIdDupDstv;
	}

	public Long getIdDupDistintivo() {
		return idDupDistintivo;
	}

	public void setIdDupDistintivo(Long idDupDistintivo) {
		this.idDupDistintivo = idDupDistintivo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String[] getListaMensajesError() {
		return listaMensajesError;
	}

	public void setListaMensajesError(String[] listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}

	public String[] getListaMensajesOk() {
		return listaMensajesOk;
	}

	public void setListaMensajesOk(String[] listaMensajesOk) {
		this.listaMensajesOk = listaMensajesOk;
	}

	public void addListaMensajesError(List<String> listaMensajes) {
		listaMensajesError = new String[listaMensajes.size()];
		for (int i = 0; i < listaMensajes.size(); i++) {
			listaMensajesError[i] = listaMensajes.get(i);
		}
	}

	public void addListaMensajesOk(List<String> listaMensajes) {
		listaMensajesOk = new String[listaMensajes.size()];
		for (int i = 0; i < listaMensajes.size(); i++) {
			listaMensajesOk[i] = listaMensajes.get(i);
		}
	}

	public void addMensajeError(String mensaje) {
		listaMensajesError = new String[1];
		listaMensajesError[0] = mensaje;
	}

	public void addMensajeTasa(String mensaje) {
		if (mensajeTasa == null || mensajeTasa.isEmpty()) {
			mensajeTasa = new ArrayList<String>();
		}
		mensajeTasa.add(mensaje);
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Boolean getEsDescargable() {
		return esDescargable;
	}

	public void setEsDescargable(Boolean esDescargable) {
		this.esDescargable = esDescargable;
	}

	public Date getFechaGenExcel() {
		return fechaGenExcel;
	}

	public void setFechaGenExcel(Date fechaGenExcel) {
		this.fechaGenExcel = fechaGenExcel;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getDataGraficos1() {
		return dataGraficos1;
	}

	public void setDataGraficos1(String dataGraficos1) {
		this.dataGraficos1 = dataGraficos1;
	}

	public String getDataGraficos2() {
		return dataGraficos2;
	}

	public void setDataGraficos2(String dataGraficos2) {
		this.dataGraficos2 = dataGraficos2;
	}

	public String getDataCategoria() {
		return dataCategoria;
	}

	public void setDataCategoria(String dataCategoria) {
		this.dataCategoria = dataCategoria;
	}

	public String getDataGraficosError1() {
		return dataGraficosError1;
	}

	public void setDataGraficosError1(String dataGraficosError1) {
		this.dataGraficosError1 = dataGraficosError1;
	}

	public String getDataGraficosError2() {
		return dataGraficosError2;
	}

	public void setDataGraficosError2(String dataGraficosError2) {
		this.dataGraficosError2 = dataGraficosError2;
	}

	public String getDataGraficosError0() {
		return dataGraficosError0;
	}

	public void setDataGraficosError0(String dataGraficosError0) {
		this.dataGraficosError0 = dataGraficosError0;
	}

	public Long getIdImportacionFich() {
		return idImportacionFich;
	}

	public void setIdImportacionFich(Long idImportacionFich) {
		this.idImportacionFich = idImportacionFich;
	}

	public List<TramiteTraficoInteveVO> getListaTramitesSolicitudInteve() {
		return listaTramitesInteve;
	}

	public void setListaTramitesSolicitudInteve(List<TramiteTraficoInteveVO> listaTramitesSolicitudInteve) {
		this.listaTramitesInteve = listaTramitesSolicitudInteve;
	}

	public FORMATOOEGAM2SOLICITUD getFormatoOegam2Solicitud() {
		return formatoOegam2Solicitud;
	}

	public void setFormatoOegam2Solicitud(FORMATOOEGAM2SOLICITUD formatoOegam2Solicitud) {
		this.formatoOegam2Solicitud = formatoOegam2Solicitud;
	}

	public List<TramiteTraficoInteveVO> getListaTramitesInteve() {
		return listaTramitesInteve;
	}

	public void setListaTramitesInteve(List<TramiteTraficoInteveVO> listaTramitesInteve) {
		this.listaTramitesInteve = listaTramitesInteve;
	}

	public List<TramiteTraficoSolInteveVO> getListaSolicitudInteve() {
		return listaSolicitudInteve;
	}

	public void setListaSolicitudInteve(List<TramiteTraficoSolInteveVO> listaSolicitudInteve) {
		this.listaSolicitudInteve = listaSolicitudInteve;
	}

	public List<String> getMensajeTasa() {
		return mensajeTasa;
	}

	public void setMensajeTasa(List<String> mensajeTasa) {
		this.mensajeTasa = mensajeTasa;
	}

	public List<TasaVO> getListaTasas() {
		return listaTasas;
	}

	public void setListaTasas(List<TasaVO> listaTasas) {
		this.listaTasas = listaTasas;
	}

	public List<TasaImportacionBean> getListaTasaBean() {
		return listaTasaBean;
	}

	public void setListaTasaBean(List<TasaImportacionBean> listaTasaBean) {
		this.listaTasaBean = listaTasaBean;
	}

	public List<String> getListaConversionesError() {
		return listaConversionesError;
	}

	public void setListaConversionesError(List<String> listaConversionesError) {
		this.listaConversionesError = listaConversionesError;
	}
}