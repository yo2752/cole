package org.gestoresmadrid.oegam2comun.bien.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.sistemaExplotacion.view.dto.SistemaExplotacionDto;
import org.gestoresmadrid.oegam2comun.situacion.view.dto.SituacionDto;
import org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto.TipoInmuebleDto;
import org.gestoresmadrid.oegam2comun.usoRustico.view.dto.UsoRusticoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;

import utilidades.estructuras.Fecha;

public class BienDto implements Serializable {

	private static final long serialVersionUID = -5476784020706357385L;

	/** Datos comunes a bien **/
	// Esto es un nombre que se va a mostrar en una lista para hacer referencia al bien pero que no se guarda
	private String nombreBien;
	private Long idBien;
	private String tipoBien;
	private Long estado;
	private String refCatrastal;
	private TipoInmuebleDto tipoInmueble;
	private DireccionDto direccion;
	private Date fechaAlta;
	private String descripcion;
	private Long idufir;
	private Long numeroFinca;
	private Long numFincaDupl;
	private Long seccion;
	private String subnumFinca;
	/** Fin Datos comunes a bien **/

	/** Datos Bien Rustico **/

	private BigDecimal superficie;
	private String paraje;
	private String poligono;
	private String parcela;
	private String subParcela;
	private UsoRusticoDto usoRustico;
	private UnidadMetricaDto unidadMetrica;
	private SistemaExplotacionDto sistemaExplotacion;
	/** Fin Datos Rustico **/

	/** Datos Bien Urbanos **/
	private Boolean arrendamiento;
	private Boolean viviendaProtOficial;
	private Fecha fechaConstruccion;
	private String dupTri;
	private BigDecimal superficieConst;
	private BigDecimal anioContratacion;
	private Boolean descalificado;
	private BigDecimal precioMaximo;
	private SituacionDto situacion;
	private Boolean viHabitual;
	/** Fin Datos Bien Urbanos **/

	private BigDecimal valorDeclarado;
	private BigDecimal transmision;
	private BigDecimal valorTasacion;

	private List<ModeloBienDto> listaModelos;
	private List<BienRemesaDto> listaRemesas;
	private List<InmuebleDto> listaInmuebles;

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getRefCatrastal() {
		return refCatrastal;
	}

	public void setRefCatrastal(String refCatrastal) {
		this.refCatrastal = refCatrastal;
	}

	public TipoInmuebleDto getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(TipoInmuebleDto tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public BigDecimal getSuperficie() {
		return superficie;
	}

	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}

	public String getParaje() {
		return paraje;
	}

	public void setParaje(String paraje) {
		this.paraje = paraje;
	}

	public String getPoligono() {
		return poligono;
	}

	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getSubParcela() {
		return subParcela;
	}

	public void setSubParcela(String subParcela) {
		this.subParcela = subParcela;
	}

	public UsoRusticoDto getUsoRustico() {
		return usoRustico;
	}

	public void setUsoRustico(UsoRusticoDto usoRustico) {
		this.usoRustico = usoRustico;
	}

	public UnidadMetricaDto getUnidadMetrica() {
		return unidadMetrica;
	}

	public void setUnidadMetrica(UnidadMetricaDto unidadMetrica) {
		this.unidadMetrica = unidadMetrica;
	}

	public SistemaExplotacionDto getSistemaExplotacion() {
		return sistemaExplotacion;
	}

	public void setSistemaExplotacion(SistemaExplotacionDto sistemaExplotacion) {
		this.sistemaExplotacion = sistemaExplotacion;
	}

	public Boolean getArrendamiento() {
		return arrendamiento;
	}

	public void setArrendamiento(Boolean arrendamiento) {
		this.arrendamiento = arrendamiento;
	}

	public Boolean getViviendaProtOficial() {
		return viviendaProtOficial;
	}

	public void setViviendaProtOficial(Boolean viviendaProtOficial) {
		this.viviendaProtOficial = viviendaProtOficial;
	}

	public Fecha getFechaConstruccion() {
		return fechaConstruccion;
	}

	public void setFechaConstruccion(Fecha fechaConstruccion) {
		this.fechaConstruccion = fechaConstruccion;
	}

	public String getDupTri() {
		return dupTri;
	}

	public void setDupTri(String dupTri) {
		this.dupTri = dupTri;
	}

	public BigDecimal getSuperficieConst() {
		return superficieConst;
	}

	public void setSuperficieConst(BigDecimal superficieConst) {
		this.superficieConst = superficieConst;
	}

	public BigDecimal getAnioContratacion() {
		return anioContratacion;
	}

	public void setAnioContratacion(BigDecimal anioContratacion) {
		this.anioContratacion = anioContratacion;
	}

	public Boolean getDescalificado() {
		return descalificado;
	}

	public void setDescalificado(Boolean descalificado) {
		this.descalificado = descalificado;
	}

	public BigDecimal getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(BigDecimal precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public SituacionDto getSituacion() {
		return situacion;
	}

	public void setSituacion(SituacionDto situacion) {
		this.situacion = situacion;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public String getNombreBien() {
		return nombreBien;
	}

	public void setNombreBien(String nombreBien) {
		this.nombreBien = nombreBien;
	}

	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public BigDecimal getTransmision() {
		return transmision;
	}

	public void setTransmision(BigDecimal transmision) {
		this.transmision = transmision;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<ModeloBienDto> getListaModelos() {
		return listaModelos;
	}

	public void setListaModelos(List<ModeloBienDto> listaModelos) {
		this.listaModelos = listaModelos;
	}

	public List<BienRemesaDto> getListaRemesas() {
		return listaRemesas;
	}

	public void setListaRemesas(List<BienRemesaDto> listaRemesas) {
		this.listaRemesas = listaRemesas;
	}

	/**
	 * @return the valorTasacion
	 */
	public BigDecimal getValorTasacion() {
		return valorTasacion;
	}

	/**
	 * @param valorTasacion the valorTasacion to set
	 */
	public void setValorTasacion(BigDecimal valorTasacion) {
		this.valorTasacion = valorTasacion;
	}

	public Boolean getViHabitual() {
		return viHabitual;
	}

	public void setViHabitual(Boolean viHabitual) {
		this.viHabitual = viHabitual;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdufir() {
		return idufir;
	}

	public void setIdufir(Long idufir) {
		this.idufir = idufir;
	}

	public Long getNumeroFinca() {
		return numeroFinca;
	}

	public void setNumeroFinca(Long numeroFinca) {
		this.numeroFinca = numeroFinca;
	}

	public Long getNumFincaDupl() {
		return numFincaDupl;
	}

	public void setNumFincaDupl(Long numFincaDupl) {
		this.numFincaDupl = numFincaDupl;
	}

	public Long getSeccion() {
		return seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public String getSubnumFinca() {
		return subnumFinca;
	}

	public void setSubnumFinca(String subnumFinca) {
		this.subnumFinca = subnumFinca;
	}

	public List<InmuebleDto> getListaInmuebles() {
		return listaInmuebles;
	}

	public void setListaInmuebles(List<InmuebleDto> listaInmuebles) {
		this.listaInmuebles = listaInmuebles;
	}

}
