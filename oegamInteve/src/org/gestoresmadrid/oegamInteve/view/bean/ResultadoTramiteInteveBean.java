package org.gestoresmadrid.oegamInteve.view.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoInteveDto;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoSolInteveDto;

public class ResultadoTramiteInteveBean implements Serializable {

	private static final long serialVersionUID = -7524052407104665130L;

	Boolean error;
	String mensaje;
	List<String> listaMensajes;
	TramiteTraficoInteveDto tramiteInteve;
	BigDecimal numExpediente;
	List<Long> listaIdsSolicitudes;
	IntervinienteTraficoDto solicitante;
	IntervinienteTraficoDto representante;
	TramiteTraficoSolInteveDto tramiteSolicitudInteve;
	List<TasaDto> listaTasas; 
	Exception excepcion;
	List<TramiteTraficoSolInteveVO> listaSolicitudesInteve;
	IntervinienteTraficoVO solicitanteVO;
	ResumenTramiteInteveBean resumen;
	List<ResultadoWSInteveBean> listaResultadoWS;
	Long numErrorWS;
	Long numOkWS;
	File fichero;
	String nombreFichero;
	Boolean esZip;

	public void addNumErrorWS() {
		if (numErrorWS == null) {
			numErrorWS = 0L;
		}
		numErrorWS++;
	}

	public void addNumOkWS() {
		if (numOkWS == null) {
			numOkWS = 0L;
		}
		numOkWS++;
	}

	public void addListaResultadoWS(Long id, String estado, String respuesta) {
		if (listaResultadoWS == null || listaResultadoWS.isEmpty()) {
			listaResultadoWS = new ArrayList<>();
		}
		listaResultadoWS.add(new ResultadoWSInteveBean(id, estado, respuesta));
	}

	public void addResumenError(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenTramiteInteveBean();
		}
		resumen.addListaError(mensaje);
	}

	public void addResumenOk(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenTramiteInteveBean();
		}
		resumen.addListaOK(mensaje);
	}

	public ResultadoTramiteInteveBean(Boolean error) {
		super();
		this.error = error;
		this.esZip = Boolean.FALSE;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
	}

	public void addListaIdsSolicitudes(Long idSolicitudInteve) {
		if (listaIdsSolicitudes == null || listaIdsSolicitudes.isEmpty()) {
			listaIdsSolicitudes = new ArrayList<>();
		}
		listaIdsSolicitudes.add(idSolicitudInteve);
	}

	public void addListaSolicitudesInteve(TramiteTraficoSolInteveVO solicitud) {
		if (listaSolicitudesInteve == null || listaSolicitudesInteve.isEmpty()) {
			listaSolicitudesInteve = new ArrayList<>();
		}
		listaSolicitudesInteve.add(solicitud);
	}

	public Boolean getError() {
		return error;
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

	public TramiteTraficoInteveDto getTramiteInteve() {
		return tramiteInteve;
	}

	public void setTramiteInteve(TramiteTraficoInteveDto tramiteInteve) {
		this.tramiteInteve = tramiteInteve;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public List<Long> getListaIdsSolicitudes() {
		return listaIdsSolicitudes;
	}

	public void setListaIdsSolicitudes(List<Long> listaIdsSolicitudes) {
		this.listaIdsSolicitudes = listaIdsSolicitudes;
	}

	public IntervinienteTraficoDto getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteTraficoDto solicitante) {
		this.solicitante = solicitante;
	}

	public TramiteTraficoSolInteveDto getTramiteSolicitudInteve() {
		return tramiteSolicitudInteve;
	}

	public void setTramiteSolicitudInteve(TramiteTraficoSolInteveDto tramiteSolicitudInteve) {
		this.tramiteSolicitudInteve = tramiteSolicitudInteve;
	}

	public List<TasaDto> getListaTasas() {
		return listaTasas;
	}

	public void setListaTasas(List<TasaDto> listaTasas) {
		this.listaTasas = listaTasas;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public List<TramiteTraficoSolInteveVO> getListaSolicitudesInteve() {
		return listaSolicitudesInteve;
	}

	public void setListaSolicitudesInteve(List<TramiteTraficoSolInteveVO> listaSolicitudesInteve) {
		this.listaSolicitudesInteve = listaSolicitudesInteve;
	}

	public IntervinienteTraficoVO getSolicitanteVO() {
		return solicitanteVO;
	}

	public void setSolicitanteVO(IntervinienteTraficoVO solicitanteVO) {
		this.solicitanteVO = solicitanteVO;
	}

	public ResumenTramiteInteveBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenTramiteInteveBean resumen) {
		this.resumen = resumen;
	}

	public List<ResultadoWSInteveBean> getListaResultadoWS() {
		return listaResultadoWS;
	}

	public void setListaResultadoWS(List<ResultadoWSInteveBean> listaResultadoWS) {
		this.listaResultadoWS = listaResultadoWS;
	}

	public Long getNumErrorWS() {
		return numErrorWS;
	}

	public void setNumErrorWS(Long numErrorWS) {
		this.numErrorWS = numErrorWS;
	}

	public Long getNumOkWS() {
		return numOkWS;
	}

	public void setNumOkWS(Long numOkWS) {
		this.numOkWS = numOkWS;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

	public IntervinienteTraficoDto getRepresentante() {
		return representante;
	}

	public void setRepresentante(IntervinienteTraficoDto representante) {
		this.representante = representante;
	}

}