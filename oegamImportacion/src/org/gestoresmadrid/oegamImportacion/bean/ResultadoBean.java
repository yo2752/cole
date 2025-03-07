package org.gestoresmadrid.oegamImportacion.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.oegamConversiones.jaxb.pegatina.ParametrosPegatinaMatriculacion;

public class ResultadoBean implements Serializable {

	private static final long serialVersionUID = 8844782955686112270L;

	private Boolean error;
	private String mensaje;
	private List<String> listaMensaje;
	private BigDecimal numExpediente;
	private Long idVehiculo;
	private Long idDirVehiculo;
	private Long idPersona;
	private Long idDirPersona;
	private Long valorModificado;
	private EvolucionVehiculoVO evolucionVehiculo;
	private Boolean actualizar;
	private EvolucionPersonaVO evolucionPersona;
	private PersonaDireccionVO personaDir;
	private PersonaVO persona;
	private Boolean esNuevaDir;
	private ParametrosPegatinaMatriculacion parametros;

	public ResultadoBean(Boolean error) {
		super();
		this.error = error;
		this.actualizar = Boolean.FALSE;
		this.esNuevaDir = Boolean.FALSE;
	}

	public void addListaMensaje(String mensaje) {
		if (listaMensaje == null) {
			listaMensaje = new ArrayList<String>();
		}
		listaMensaje.add(mensaje);
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

	public List<String> getListaMensaje() {
		return listaMensaje;
	}

	public void setListaMensaje(List<String> listaMensaje) {
		this.listaMensaje = listaMensaje;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public Long getIdDirVehiculo() {
		return idDirVehiculo;
	}

	public void setIdDirVehiculo(Long idDirVehiculo) {
		this.idDirVehiculo = idDirVehiculo;
	}

	public Long getValorModificado() {
		return valorModificado;
	}

	public void setValorModificado(Long valorModificado) {
		this.valorModificado = valorModificado;
	}

	public EvolucionVehiculoVO getEvolucionVehiculo() {
		return evolucionVehiculo;
	}

	public void setEvolucionVehiculo(EvolucionVehiculoVO evolucionVehiculo) {
		this.evolucionVehiculo = evolucionVehiculo;
	}

	public Boolean getActualizar() {
		return actualizar;
	}

	public void setActualizar(Boolean actualizar) {
		this.actualizar = actualizar;
	}

	public EvolucionPersonaVO getEvolucionPersona() {
		return evolucionPersona;
	}

	public void setEvolucionPersona(EvolucionPersonaVO evolucionPersona) {
		this.evolucionPersona = evolucionPersona;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Long getIdDirPersona() {
		return idDirPersona;
	}

	public void setIdDirPersona(Long idDirPersona) {
		this.idDirPersona = idDirPersona;
	}

	public PersonaDireccionVO getPersonaDir() {
		return personaDir;
	}

	public void setPersonaDir(PersonaDireccionVO personaDir) {
		this.personaDir = personaDir;
	}

	public Boolean getEsNuevaDir() {
		return esNuevaDir;
	}

	public void setEsNuevaDir(Boolean esNuevaDir) {
		this.esNuevaDir = esNuevaDir;
	}

	public ParametrosPegatinaMatriculacion getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosPegatinaMatriculacion parametros) {
		this.parametros = parametros;
	}

	public PersonaVO getPersona() {
		return persona;
	}

	public void setPersona(PersonaVO persona) {
		this.persona = persona;
	}
}