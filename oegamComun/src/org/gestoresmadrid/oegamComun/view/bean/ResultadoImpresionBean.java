package org.gestoresmadrid.oegamComun.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ResultadoImpresionBean implements Serializable {

	private static final long serialVersionUID = 1400270270125942527L;

	private Boolean error;

	private String mensaje;

	private String tipoDocumento;

	private String tipoTramite;

	private String nombreDocumento;

	private String tipoTransferencia;

	private String nube;
	
	private String nubeConN;

	private List<String> listaMensajes;

	private List<BigDecimal> listaTramites;

	private List<BigDecimal> listaTramitesOk;

	private List<BigDecimal> listaTramitesError;

	private byte[] pdf;

	private Long idImpresion;

	private String[] numsExpedientes;

	public ResultadoImpresionBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addListaTramitesError(BigDecimal numExpediente) {
		if (listaTramitesError == null || listaTramitesError.isEmpty()) {
			listaTramitesError = new ArrayList<BigDecimal>();
		}
		listaTramitesError.add(numExpediente);
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

	public List<BigDecimal> getListaTramitesError() {
		return listaTramitesError;
	}

	public void setListaTramitesError(List<BigDecimal> listaTramitesError) {
		this.listaTramitesError = listaTramitesError;
	}

	public List<BigDecimal> getListaTramites() {
		return listaTramites;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public void setListaTramites(List<BigDecimal> listaTramites) {
		this.listaTramites = listaTramites;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoTransferencia() {
		return tipoTransferencia;
	}

	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}

	public List<BigDecimal> getListaTramitesOk() {
		return listaTramitesOk;
	}

	public void setListaTramitesOk(List<BigDecimal> listaTramitesOk) {
		this.listaTramitesOk = listaTramitesOk;
	}

	public Long getIdImpresion() {
		return idImpresion;
	}

	public void setIdImpresion(Long idImpresion) {
		this.idImpresion = idImpresion;
	}

	public String[] getNumsExpedientes() {
		return numsExpedientes;
	}

	public void setNumsExpedientes(String[] numsExpedientes) {
		this.numsExpedientes = numsExpedientes;
	}

	public String getNube() {
		return nube;
	}

	public void setNube(String nube) {
		if (this.nube == null) {
			this.nube = nube;
		} else {
			this.nube = this.nube + "\n\n" + nube;
		}
	}
	
	public String getNubeConN() {
		return nubeConN;
	}

	public void setNubeConN(String nubeConN) {
		if (this.nubeConN == null) {
			this.nubeConN = nubeConN;
		} else {
			this.nubeConN = this.nubeConN + "\n\n" + nube;
		}
	}
}
