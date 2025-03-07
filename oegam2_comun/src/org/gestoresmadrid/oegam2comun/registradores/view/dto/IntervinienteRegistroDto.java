package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

/**
 * Representa los financiadores
 */
public class IntervinienteRegistroDto implements Serializable{


	private static final long serialVersionUID = -3102119545377511509L;
	
	private long idInterviniente;
	private BigDecimal idEscritura;
	private String nif;
	private long idRepresentado;
	private String numColegiado;
	private String tipoInterviniente;
	private String tipoPersona;

	private DatRegMercantilDto datRegMercantil;
	private List<TelefonoDto> telefonos;
	private PersonaDto persona;
	private String cargo;
	private NotarioRegistroDto notario;
	private List<TramiteRegRbmDto> tramites;
	private Long idDireccion;
	private DireccionDto direccion;
	private List<IntervinienteRegistroDto> representantes;
	
	public long getIdInterviniente() {
		return idInterviniente;
	}
	public void setIdInterviniente(long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}
	public BigDecimal getIdEscritura() {
		return idEscritura;
	}
	public void setIdEscritura(BigDecimal idEscritura) {
		this.idEscritura = idEscritura;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public long getIdRepresentado() {
		return idRepresentado;
	}
	public void setIdRepresentado(long idRepresentado) {
		this.idRepresentado = idRepresentado;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getTipoInterviniente() {
		return tipoInterviniente;
	}
	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
	public DatRegMercantilDto getDatRegMercantil() {
		return datRegMercantil;
	}
	public void setDatRegMercantil(DatRegMercantilDto datRegMercantil) {
		this.datRegMercantil = datRegMercantil;
	}
	public List<TelefonoDto> getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(List<TelefonoDto> telefonos) {
		this.telefonos = telefonos;
	}
	public PersonaDto getPersona() {
		return persona;
	}
	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public NotarioRegistroDto getNotario() {
		return notario;
	}
	public void setNotario(NotarioRegistroDto notario) {
		this.notario = notario;
	}
	public DireccionDto getDireccion() {
		if (direccion==null)
			direccion= new DireccionDto();
		return direccion;
	}
	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}
	public Long getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
	public List<TramiteRegRbmDto> getTramites() {
		return tramites;
	}
	public void setTramites(List<TramiteRegRbmDto> tramites) {
		this.tramites = tramites;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public List<IntervinienteRegistroDto> getRepresentantes() {
		if(null==representantes){
			representantes = new ArrayList<IntervinienteRegistroDto>();
		}
		return representantes;
	}
	public void setRepresentantes(List<IntervinienteRegistroDto> representantes) {
		this.representantes = representantes;
	}

	

}
