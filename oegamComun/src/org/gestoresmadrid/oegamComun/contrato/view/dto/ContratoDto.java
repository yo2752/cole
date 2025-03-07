package org.gestoresmadrid.oegamComun.contrato.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class ContratoDto implements Serializable {

	private static final long serialVersionUID = 8308318310956386783L;

	private String anagramaContrato;

	private String cif;

	private String codPostal;

	private ColegioDto colegioDto;

	private ColegiadoDto colegiadoDto;

	private ProvinciaDto provinciaDto;

	private MunicipioDto municipio;

	private String correoElectronico;

	private String escalera;

	private String estadoContrato;

	private Fecha fechaInicio;

	private Fecha fechaFin;

	private BigDecimal idContrato;

	private String idMunicipio;

	private String idProvincia;

	private BigDecimal idTipoContrato;

	private String idTipoVia;

	private JefaturaTraficoDto jefaturaTraficoDto;

	private String letra;

	private String numero;

	private String piso;

	private String puerta;

	private String razonSocial;

	private Long telefono;

	private String via;

	private String observaciones;

	private List<AplicacionDto> aplicacionesDto;

	private List<ContratoPreferenciaDto> contratoPreferenciaDto;

	private List<UsuarioDto> usuarios;

	private List<UsuarioDto> usuariosContrato;

	private List<UsuarioDto> usuariosHabilitados;

	private String contratoProvincia;

	private String ncorpme;

	private String usuarioCorpme;

	private String passwordCorpme;
	
	private CorreoContratoTramiteDto correoContratoTramite;

	private List<CorreoContratoTramiteDto> correosTramites;
	
	private String codigoAplicacion;
	private String mobileGest;

	public String getAnagramaContrato() {
		return anagramaContrato;
	}

	public void setAnagramaContrato(String anagramaContrato) {
		this.anagramaContrato = anagramaContrato;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public ColegioDto getColegioDto() {
		return colegioDto;
	}

	public void setColegioDto(ColegioDto colegioDto) {
		this.colegioDto = colegioDto;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(BigDecimal idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public JefaturaTraficoDto getJefaturaTraficoDto() {
		return jefaturaTraficoDto;
	}

	public void setJefaturaTraficoDto(JefaturaTraficoDto jefaturaTraficoDto) {
		this.jefaturaTraficoDto = jefaturaTraficoDto;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public ColegiadoDto getColegiadoDto() {
		return colegiadoDto;
	}

	public void setColegiadoDto(ColegiadoDto colegiadoDto) {
		this.colegiadoDto = colegiadoDto;
	}

	public List<AplicacionDto> getAplicacionesDto() {
		return aplicacionesDto;
	}

	public void setAplicacionesDto(List<AplicacionDto> aplicacionesDto) {
		this.aplicacionesDto = aplicacionesDto;
	}

	public List<ContratoPreferenciaDto> getContratoPreferenciaDto() {
		return contratoPreferenciaDto;
	}

	public void setContratoPreferenciaDto(List<ContratoPreferenciaDto> contratoPreferenciaDto) {
		this.contratoPreferenciaDto = contratoPreferenciaDto;
	}

	public String getContratoProvincia() {
		return contratoProvincia;
	}

	public void setContratoProvincia(String contratoProvincia) {
		this.contratoProvincia = contratoProvincia;
	}

	public String getEstadoContrato() {
		return estadoContrato;
	}

	public void setEstadoContrato(String estadoContrato) {
		this.estadoContrato = estadoContrato;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public List<UsuarioDto> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDto> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioDto> getUsuariosContrato() {
		return usuariosContrato;
	}

	public void setUsuariosContrato(List<UsuarioDto> usuariosContrato) {
		this.usuariosContrato = usuariosContrato;
	}

	public List<UsuarioDto> getUsuariosHabilitados() {
		return usuariosHabilitados;
	}

	public void setUsuariosHabilitados(List<UsuarioDto> usuariosHabilitados) {
		this.usuariosHabilitados = usuariosHabilitados;
	}

	public ProvinciaDto getProvinciaDto() {
		return provinciaDto;
	}

	public void setProvinciaDto(ProvinciaDto provinciaDto) {
		this.provinciaDto = provinciaDto;
	}

	/**
	 * @return the ncorpme
	 */
	public String getNcorpme() {
		return ncorpme;
	}

	/**
	 * @param ncorpme the ncorpme to set
	 */
	public void setNcorpme(String ncorpme) {
		this.ncorpme = ncorpme;
	}

	/**
	 * @return the usuarioCorpme
	 */
	public String getUsuarioCorpme() {
		return usuarioCorpme;
	}

	/**
	 * @param usuarioCorpme the usuarioCorpme to set
	 */
	public void setUsuarioCorpme(String usuarioCorpme) {
		this.usuarioCorpme = usuarioCorpme;
	}

	/**
	 * @return the passwordCorpme
	 */
	public String getPasswordCorpme() {
		return passwordCorpme;
	}

	/**
	 * @param passwordCorpme the passwordCorpme to set
	 */
	public void setPasswordCorpme(String passwordCorpme) {
		this.passwordCorpme = passwordCorpme;
	}

	public MunicipioDto getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioDto municipio) {
		this.municipio = municipio;
	}

	public List<CorreoContratoTramiteDto> getCorreosTramites() {
		return correosTramites;
	}

	public void setCorreosTramites(List<CorreoContratoTramiteDto> correosTramites) {
		this.correosTramites = correosTramites;
	}

	public CorreoContratoTramiteDto getCorreoContratoTramite() {
		return correoContratoTramite;
	}

	public void setCorreoContratoTramite(CorreoContratoTramiteDto correoContratoTramite) {
		this.correoContratoTramite = correoContratoTramite;
	}

	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public String getMobileGest() {
		return mobileGest;
	}

	public void setMobileGest(String mobileGest) {
		this.mobileGest = mobileGest;
	}

}