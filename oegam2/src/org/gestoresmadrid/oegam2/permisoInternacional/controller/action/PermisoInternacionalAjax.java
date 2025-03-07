package org.gestoresmadrid.oegam2.permisoInternacional.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class PermisoInternacionalAjax extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -2585741700520857288L;

	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; // para ajax

	private static final String PAG_TITULAR = "pagTitularPermInter";

	String nif;
	String numColegiado;
	PermisoInternacionalDto permisoInternacionalDto;

	boolean cambioDomicilio;

	@Autowired
	ServicioPermisoInternacional servicioPermisoInternacional;

	public String buscarTitular() {
		if (StringUtils.isNotBlank(nif) && StringUtils.isNotBlank(numColegiado)) {
			ResultadoPermInterBean resultado = servicioPermisoInternacional.getTitular(nif, numColegiado);
			if (!resultado.getError()) {
				permisoInternacionalDto = new PermisoInternacionalDto();
				if (resultado.getTitular() != null) {
					if (!cambioDomicilio) {
						resultado.getTitular().setIdDireccion(null);
						resultado.getTitular().setDireccion(null);
					}
					permisoInternacionalDto.setTitular(resultado.getTitular());
				} else {
					IntervinienteIntergaDto titular = new IntervinienteIntergaDto();
					PersonaDto persona = new PersonaDto();
					persona.setNif(nif);
					titular.setPersona(persona);
					permisoInternacionalDto.setTitular(titular);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		}
		return PAG_TITULAR;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.servletResponse = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public PermisoInternacionalDto getPermisoInternacionalDto() {
		return permisoInternacionalDto;
	}

	public void setPermisoInternacionalDto(PermisoInternacionalDto permisoInternacionalDto) {
		this.permisoInternacionalDto = permisoInternacionalDto;
	}

	public boolean isCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(boolean cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}
}
