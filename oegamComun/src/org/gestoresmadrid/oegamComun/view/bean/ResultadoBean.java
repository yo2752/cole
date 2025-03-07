package org.gestoresmadrid.oegamComun.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;

public class ResultadoBean implements Serializable {

	private static final long serialVersionUID = -1734463055416412549L;

	private Boolean error;

	private String mensaje;

	private List<String> listaMensajes;

	private Long idCola;

	private IntervinienteIntergaVO interviniente;
	private String nombreDocumento;
	private BigDecimal numExpediente;
	private String estado;
	private String tipoCredito;
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	private String codigoTasa;

	public ResultadoBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
	}

	public void addListaMensajes(List<String> mensajes) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<>();
		}
		for (String mensaje : mensajes) {
			listaMensajes.add(mensaje);
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

	public Long getIdCola() {
		return idCola;
	}

	public void setIdCola(Long idCola) {
		this.idCola = idCola;
	}

	public IntervinienteIntergaVO getInterviniente() {
		return interviniente;
	}

	public void setInterviniente(IntervinienteIntergaVO interviniente) {
		this.interviniente = interviniente;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}
}
