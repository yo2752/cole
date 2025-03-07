package org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;

public class ResultadoDuplPermCondBean implements Serializable {

	private static final long serialVersionUID = -1975192207216797259L;

	private Boolean error;
	private String mensaje;
	private String codigoError;
	private List<String> listaMensajes;
	private ResumenDuplicadoPermCondBean resumen;
	private Long idDuplicadoPermisoCond;
	private BigDecimal numExpediente;
	private IntervinienteIntergaDto titular;
	private String nombreFichero;
	private File fichero;
	private DuplicadoPermisoConducirDto duplicadoPermisoConducirDto;

	private String estadoDeclaracion;
	private String estadoSolicitud;
	private String estadoDeclaracionTitular;
	private String estadoOtroDocumento;
	private String estadoMandato;

	public ResultadoDuplPermCondBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addResumenOK(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenDuplicadoPermCondBean();
		}
		resumen.addListaResumenOK(mensaje);
	}

	public void addResumenError(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenDuplicadoPermCondBean();
		}
		resumen.addListaResumenError(mensaje);
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
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

	public Long getIdDuplicadoPermisoCond() {
		return idDuplicadoPermisoCond;
	}

	public void setIdDuplicadoPermisoCond(Long idDuplicadoPermisoCond) {
		this.idDuplicadoPermisoCond = idDuplicadoPermisoCond;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public IntervinienteIntergaDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteIntergaDto titular) {
		this.titular = titular;
	}

	public ResumenDuplicadoPermCondBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenDuplicadoPermCondBean resumen) {
		this.resumen = resumen;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public DuplicadoPermisoConducirDto getDuplicadoPermisoConducirDto() {
		return duplicadoPermisoConducirDto;
	}

	public void setDuplicadoPermisoConducirDto(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		this.duplicadoPermisoConducirDto = duplicadoPermisoConducirDto;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getEstadoDeclaracion() {
		return estadoDeclaracion;
	}

	public void setEstadoDeclaracion(String estadoDeclaracion) {
		this.estadoDeclaracion = estadoDeclaracion;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoDeclaracionTitular() {
		return estadoDeclaracionTitular;
	}

	public void setEstadoDeclaracionTitular(String estadoDeclaracionTitular) {
		this.estadoDeclaracionTitular = estadoDeclaracionTitular;
	}

	public String getEstadoOtroDocumento() {
		return estadoOtroDocumento;
	}

	public void setEstadoOtroDocumento(String estadoOtroDocumento) {
		this.estadoOtroDocumento = estadoOtroDocumento;
	}

	public String getEstadoMandato() {
		return estadoMandato;
	}

	public void setEstadoMandato(String estadoMandato) {
		this.estadoMandato = estadoMandato;
	}
}