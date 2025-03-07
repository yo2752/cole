package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;

public class ResultadoPermInterBean implements Serializable {

	private static final long serialVersionUID = -4499735109864626988L;

	private Boolean error;
	private String mensaje;
	private String codigoError;
	private List<String> listaMensajes;
	private ResumenPermisoInternacionalBean resumen;
	private Long idPermiso;
	private BigDecimal numExpediente;
	private IntervinienteIntergaDto titular;
	private String nombreFichero;
	private File fichero;
	private PermisoInternacionalDto permisoInternacional;

	private String estadoDeclaracion;
	private String estadoSolicitud;
	private String estadoCarnetConducir;
	private String estadoDni;
	private String estadoMandato;
	private String estadoOtroDocumento;
	private String estadoInterEscaneado;

	private ArrayList<File> documentacion;

	public ResultadoPermInterBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addResumenOK(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenPermisoInternacionalBean();
		}
		resumen.addListaResumenOK(mensaje);
	}

	public void addResumenError(String mensaje) {
		if (resumen == null) {
			resumen = new ResumenPermisoInternacionalBean();
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
			listaMensajes = new ArrayList<String>();
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

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
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

	public ResumenPermisoInternacionalBean getResumen() {
		return resumen;
	}

	public void setResumen(ResumenPermisoInternacionalBean resumen) {
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

	public PermisoInternacionalDto getPermisoInternacional() {
		return permisoInternacional;
	}

	public void setPermisoInternacional(PermisoInternacionalDto permisoInternacional) {
		this.permisoInternacional = permisoInternacional;
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

	public String getEstadoCarnetConducir() {
		return estadoCarnetConducir;
	}

	public void setEstadoCarnetConducir(String estadoCarnetConducir) {
		this.estadoCarnetConducir = estadoCarnetConducir;
	}

	public String getEstadoDni() {
		return estadoDni;
	}

	public void setEstadoDni(String estadoDni) {
		this.estadoDni = estadoDni;
	}

	public String getEstadoMandato() {
		return estadoMandato;
	}

	public void setEstadoMandato(String estadoMandato) {
		this.estadoMandato = estadoMandato;
	}

	public String getEstadoOtroDocumento() {
		return estadoOtroDocumento;
	}

	public void setEstadoOtroDocumento(String estadoOtroDocumento) {
		this.estadoOtroDocumento = estadoOtroDocumento;
	}

	public ArrayList<File> getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(ArrayList<File> documentacion) {
		this.documentacion = documentacion;
	}

	public String getEstadoInterEscaneado() {
		return estadoInterEscaneado;
	}

	public void setEstadoInterEscaneado(String estadoInterEscaneado) {
		this.estadoInterEscaneado = estadoInterEscaneado;
	}
}
