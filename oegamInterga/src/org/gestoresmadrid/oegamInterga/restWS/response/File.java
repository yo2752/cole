package org.gestoresmadrid.oegamInterga.restWS.response;

import java.io.Serializable;

public class File implements Serializable {

	private static final long serialVersionUID = -172673110870555582L;

	private Long id;
	
	private Plataforma plataforma;

	private Colegio colegio;

	private Long fecha;

	private String estado;

	private String tipo;

	private String numReferencia;

	private String tipoTramite;

	private int gestoria;

	private String doiTitular;

	private String matricula;

	private Long fechaActualizacion;

	private String tasa;

	private String tasaAnotacion;

	private String doiTipo;

	private String doiCotitular;

	private String dirTipoVia;

	private String dirNombreVia;

	private String dirNumero;

	private String dirBloque;

	private String dirPortal;

	private String dirEscalera;

	private String dirPlanta;

	private String dirPuerta;

	private int dirKilometro;

	private int dirHectometro;

	private String dirCodigoPostal;

	private String dirLocalidad;

	private String dirMunicipio;

	private String dirProvincia;

	private Long fechaMatriculacion;

	private Long fechaItv;

	private String impresion;

	private String vehiculoImportacion;

	private String emailAute;

	private String observaciones;

	private Long fechaResolucion;

	private String resolucion;

	private String descripcion;

	private String tienePdf;

	private String tieneSpdf;

	private String tieneJpdf;

	private String tieneCpdf;

	private String tieneGpdf;

	private String tieneSolicitud;

	private String tieneMpdf;

	private String tieneApdf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Colegio getColegio() {
		return colegio;
	}

	public void setColegio(Colegio colegio) {
		this.colegio = colegio;
	}

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumReferencia() {
		return numReferencia;
	}

	public void setNumReferencia(String numReferencia) {
		this.numReferencia = numReferencia;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public int getGestoria() {
		return gestoria;
	}

	public void setGestoria(int gestoria) {
		this.gestoria = gestoria;
	}

	public String getDoiTitular() {
		return doiTitular;
	}

	public void setDoiTitular(String doiTitular) {
		this.doiTitular = doiTitular;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Long getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Long fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getTasa() {
		return tasa;
	}

	public void setTasa(String tasa) {
		this.tasa = tasa;
	}

	public String getTasaAnotacion() {
		return tasaAnotacion;
	}

	public void setTasaAnotacion(String tasaAnotacion) {
		this.tasaAnotacion = tasaAnotacion;
	}

	public String getDoiTipo() {
		return doiTipo;
	}

	public void setDoiTipo(String doiTipo) {
		this.doiTipo = doiTipo;
	}

	public String getDoiCotitular() {
		return doiCotitular;
	}

	public void setDoiCotitular(String doiCotitular) {
		this.doiCotitular = doiCotitular;
	}

	public String getDirTipoVia() {
		return dirTipoVia;
	}

	public void setDirTipoVia(String dirTipoVia) {
		this.dirTipoVia = dirTipoVia;
	}

	public String getDirNombreVia() {
		return dirNombreVia;
	}

	public void setDirNombreVia(String dirNombreVia) {
		this.dirNombreVia = dirNombreVia;
	}

	public String getDirNumero() {
		return dirNumero;
	}

	public void setDirNumero(String dirNumero) {
		this.dirNumero = dirNumero;
	}

	public String getDirBloque() {
		return dirBloque;
	}

	public void setDirBloque(String dirBloque) {
		this.dirBloque = dirBloque;
	}

	public String getDirPortal() {
		return dirPortal;
	}

	public void setDirPortal(String dirPortal) {
		this.dirPortal = dirPortal;
	}

	public String getDirEscalera() {
		return dirEscalera;
	}

	public void setDirEscalera(String dirEscalera) {
		this.dirEscalera = dirEscalera;
	}

	public String getDirPlanta() {
		return dirPlanta;
	}

	public void setDirPlanta(String dirPlanta) {
		this.dirPlanta = dirPlanta;
	}

	public String getDirPuerta() {
		return dirPuerta;
	}

	public void setDirPuerta(String dirPuerta) {
		this.dirPuerta = dirPuerta;
	}

	public int getDirKilometro() {
		return dirKilometro;
	}

	public void setDirKilometro(int dirKilometro) {
		this.dirKilometro = dirKilometro;
	}

	public int getDirHectometro() {
		return dirHectometro;
	}

	public void setDirHectometro(int dirHectometro) {
		this.dirHectometro = dirHectometro;
	}

	public String getDirCodigoPostal() {
		return dirCodigoPostal;
	}

	public void setDirCodigoPostal(String dirCodigoPostal) {
		this.dirCodigoPostal = dirCodigoPostal;
	}

	public String getDirLocalidad() {
		return dirLocalidad;
	}

	public void setDirLocalidad(String dirLocalidad) {
		this.dirLocalidad = dirLocalidad;
	}

	public String getDirMunicipio() {
		return dirMunicipio;
	}

	public void setDirMunicipio(String dirMunicipio) {
		this.dirMunicipio = dirMunicipio;
	}

	public String getDirProvincia() {
		return dirProvincia;
	}

	public void setDirProvincia(String dirProvincia) {
		this.dirProvincia = dirProvincia;
	}

	public Long getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(Long fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

	public Long getFechaItv() {
		return fechaItv;
	}

	public void setFechaItv(Long fechaItv) {
		this.fechaItv = fechaItv;
	}

	public String getImpresion() {
		return impresion;
	}

	public void setImpresion(String impresion) {
		this.impresion = impresion;
	}

	public String getVehiculoImportacion() {
		return vehiculoImportacion;
	}

	public void setVehiculoImportacion(String vehiculoImportacion) {
		this.vehiculoImportacion = vehiculoImportacion;
	}

	public String getEmailAute() {
		return emailAute;
	}

	public void setEmailAute(String emailAute) {
		this.emailAute = emailAute;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Long fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTienePdf() {
		return tienePdf;
	}

	public void setTienePdf(String tienePdf) {
		this.tienePdf = tienePdf;
	}

	public String getTieneSpdf() {
		return tieneSpdf;
	}

	public void setTieneSpdf(String tieneSpdf) {
		this.tieneSpdf = tieneSpdf;
	}

	public String getTieneJpdf() {
		return tieneJpdf;
	}

	public void setTieneJpdf(String tieneJpdf) {
		this.tieneJpdf = tieneJpdf;
	}

	public String getTieneCpdf() {
		return tieneCpdf;
	}

	public void setTieneCpdf(String tieneCpdf) {
		this.tieneCpdf = tieneCpdf;
	}

	public String getTieneGpdf() {
		return tieneGpdf;
	}

	public void setTieneGpdf(String tieneGpdf) {
		this.tieneGpdf = tieneGpdf;
	}

	public String getTieneSolicitud() {
		return tieneSolicitud;
	}

	public void setTieneSolicitud(String tieneSolicitud) {
		this.tieneSolicitud = tieneSolicitud;
	}

	public String getTieneMpdf() {
		return tieneMpdf;
	}

	public void setTieneMpdf(String tieneMpdf) {
		this.tieneMpdf = tieneMpdf;
	}

	public String getTieneApdf() {
		return tieneApdf;
	}

	public void setTieneApdf(String tieneApdf) {
		this.tieneApdf = tieneApdf;
	}

	public Plataforma getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}
}
