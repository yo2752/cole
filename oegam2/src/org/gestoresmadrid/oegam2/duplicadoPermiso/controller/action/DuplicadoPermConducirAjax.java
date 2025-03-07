package org.gestoresmadrid.oegam2.duplicadoPermiso.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class DuplicadoPermConducirAjax extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -218958913727579453L;

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private static final String PAG_TITULAR = "pagTitularDPC";

	String nif;
	String numColegiado;
	DuplicadoPermisoConducirDto duplicadoPermisoConducirDto;

	@Autowired
	ServicioDuplicadoPermCond servicioDuplicadoPermCond;

	@Autowired
	GestorPropiedades gestorPropiedades;

	public String buscarTitular() {
		if (StringUtils.isNotBlank(nif) && StringUtils.isNotBlank(numColegiado)) {
			ResultadoDuplPermCondBean resultado = servicioDuplicadoPermCond.getTitular(nif, numColegiado);
			if (!resultado.getError()) {
				duplicadoPermisoConducirDto = new DuplicadoPermisoConducirDto();
				if (resultado.getTitular() != null) {
					IntervinienteIntergaDto titular = resultado.getTitular();
					duplicadoPermisoConducirDto.setTitular(titular);
				} else {
					IntervinienteIntergaDto titular = new IntervinienteIntergaDto();
					PersonaDto persona = new PersonaDto();
					persona.setNif(nif);
					titular.setPersona(persona);
					duplicadoPermisoConducirDto.setTitular(titular);
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

	public DuplicadoPermisoConducirDto getDuplicadoPermisoConducirDto() {
		return duplicadoPermisoConducirDto;
	}

	public void setDuplicadoPermisoConducirDto(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		this.duplicadoPermisoConducirDto = duplicadoPermisoConducirDto;
	}
}